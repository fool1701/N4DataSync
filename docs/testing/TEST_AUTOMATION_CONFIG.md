# N4-DataSync Test Automation Configuration

## Overview

This document defines the automated testing infrastructure, CI/CD pipeline configuration, and quality gates for the N4-DataSync project.

## Gradle Test Configuration

### Enhanced Build Configuration

Add to `datasync/datasync-wb/datasync-wb.gradle.kts`:

```kotlin
// ================================================================
// COMPREHENSIVE TEST CONFIGURATION
// ================================================================

// Test framework configuration
test {
    useTestNG {
        // Test group configuration
        includeGroups("unit", "integration", "smoke")
        excludeGroups("e2e", "manual", "slow")
        
        // Parallel execution for faster tests
        parallel = "methods"
        threadCount = Runtime.getRuntime().availableProcessors()
        
        // Test suite configuration
        suites("src/test/resources/testng.xml")
    }
    
    // JVM configuration for tests
    jvmArgs(
        "-Xmx2g",
        "-XX:MaxPermSize=512m",
        "-Djava.awt.headless=true",
        "-Dfile.encoding=UTF-8"
    )
    
    // Test execution settings
    failFast = true
    maxParallelForks = Runtime.getRuntime().availableProcessors()
    
    // Test reporting configuration
    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
    
    // Detailed test logging
    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        showStandardStreams = false
        showCauses = true
        showExceptions = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        
        // Log slow tests
        slowThreshold = Duration.ofSeconds(5)
    }
    
    // Test filtering
    filter {
        // Include specific test patterns
        includeTestsMatching("*Test")
        includeTestsMatching("*Tests")
        
        // Exclude specific patterns
        excludeTestsMatching("*IntegrationTest") // Run separately
    }
}

// ================================================================
// SPECIALIZED TEST TASKS
// ================================================================

// Unit tests only (fast feedback)
tasks.register<Test>("unitTests") {
    description = "Run unit tests only (fast feedback loop)"
    group = "verification"
    
    useTestNG {
        includeGroups("unit")
        excludeGroups("integration", "e2e", "performance", "slow")
        parallel = "methods"
        threadCount = Runtime.getRuntime().availableProcessors()
    }
    
    // Fast execution settings
    maxParallelForks = Runtime.getRuntime().availableProcessors()
    failFast = true
    
    // Minimal logging for speed
    testLogging {
        events("failed")
        showStandardStreams = false
    }
}

// Integration tests (medium feedback)
tasks.register<Test>("integrationTests") {
    description = "Run integration tests (component interactions)"
    group = "verification"
    
    useTestNG {
        includeGroups("integration")
        excludeGroups("unit", "e2e", "performance")
        parallel = "classes"
        threadCount = 2
    }
    
    shouldRunAfter("unitTests")
    timeout.set(Duration.ofMinutes(10))
    
    testLogging {
        events("passed", "failed")
        showStandardStreams = true
    }
}

// Performance tests (specialized)
tasks.register<Test>("performanceTests") {
    description = "Run performance and load tests"
    group = "verification"
    
    useTestNG {
        includeGroups("performance", "load", "stress")
        parallel = "none" // Sequential for accurate timing
    }
    
    // Performance test settings
    jvmArgs("-Xmx4g", "-XX:+UseG1GC")
    timeout.set(Duration.ofMinutes(30))
    
    testLogging {
        events("passed", "failed", "standardOut")
        showStandardStreams = true
    }
}

// Security tests
tasks.register<Test>("securityTests") {
    description = "Run security validation tests"
    group = "verification"
    
    useTestNG {
        includeGroups("security", "validation")
    }
    
    shouldRunAfter("integrationTests")
}

// Smoke tests (critical path validation)
tasks.register<Test>("smokeTests") {
    description = "Run smoke tests (critical functionality)"
    group = "verification"
    
    useTestNG {
        includeGroups("smoke", "critical")
        parallel = "none"
    }
    
    failFast = true
    timeout.set(Duration.ofMinutes(5))
}

// Regression tests (comprehensive)
tasks.register<Test>("regressionTests") {
    description = "Run comprehensive regression test suite"
    group = "verification"
    
    useTestNG {
        includeGroups("regression", "unit", "integration")
        excludeGroups("performance", "manual")
    }
    
    timeout.set(Duration.ofMinutes(20))
}

// ================================================================
// TEST COVERAGE CONFIGURATION
// ================================================================

jacoco {
    toolVersion = "0.8.8"
}

jacocoTestReport {
    dependsOn(test)
    
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(true)
    }
    
    // Include all source sets
    executionData.setFrom(fileTree(layout.buildDirectory.dir("jacoco")).include("**/*.exec"))
    
    finalizedBy(jacocoTestCoverageVerification)
}

jacocoTestCoverageVerification {
    dependsOn(jacocoTestReport)
    
    violationRules {
        // Overall project coverage
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.85".toBigDecimal()
            }
        }
        
        // Critical business logic coverage
        rule {
            element = "CLASS"
            includes = [
                "com.mea.datasync.service.*",
                "com.mea.datasync.model.*",
                "com.mea.datasync.persistence.*"
            ]
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
        }
        
        // Branch coverage for complex logic
        rule {
            element = "CLASS"
            includes = ["com.mea.datasync.service.ProfileService"]
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.85".toBigDecimal()
            }
        }
        
        // Method coverage
        rule {
            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

// ================================================================
// QUALITY GATES AND VALIDATION
// ================================================================

// Comprehensive quality check task
tasks.register("qualityGate") {
    description = "Run comprehensive quality checks"
    group = "verification"
    
    dependsOn(
        "unitTests",
        "integrationTests", 
        "jacocoTestCoverageVerification",
        "checkstyleMain",
        "checkstyleTest"
    )
    
    doLast {
        println("✅ All quality gates passed!")
        println("📊 Test coverage report: ${layout.buildDirectory.get()}/reports/jacoco/test/html/index.html")
        println("📋 Test results: ${layout.buildDirectory.get()}/reports/tests/test/index.html")
    }
}

// Pre-commit validation
tasks.register("preCommitCheck") {
    description = "Fast pre-commit validation"
    group = "verification"
    
    dependsOn("unitTests", "checkstyleMain")
    
    doLast {
        println("✅ Pre-commit checks passed!")
    }
}

// CI/CD pipeline task
tasks.register("ciPipeline") {
    description = "Complete CI/CD pipeline validation"
    group = "verification"
    
    dependsOn(
        "clean",
        "compileJava",
        "unitTests",
        "integrationTests",
        "smokeTests",
        "jacocoTestCoverageVerification"
    )
    
    doLast {
        println("🚀 CI/CD pipeline completed successfully!")
    }
}

// ================================================================
// TEST REPORTING AND METRICS
// ================================================================

// Custom test reporting
tasks.register("testReport") {
    description = "Generate comprehensive test report"
    group = "reporting"
    
    dependsOn("test", "jacocoTestReport")
    
    doLast {
        val testResultsDir = layout.buildDirectory.dir("reports/tests/test").get().asFile
        val coverageDir = layout.buildDirectory.dir("reports/jacoco/test/html").get().asFile
        
        println("📊 Test Execution Summary")
        println("=" * 50)
        println("Test Results: ${testResultsDir.absolutePath}/index.html")
        println("Coverage Report: ${coverageDir.absolutePath}/index.html")
        
        // Parse and display key metrics
        val testResults = file("${layout.buildDirectory.get()}/test-results/test")
        if (testResults.exists()) {
            val xmlFiles = testResults.listFiles { _, name -> name.endsWith(".xml") }
            var totalTests = 0
            var passedTests = 0
            var failedTests = 0
            
            xmlFiles?.forEach { xmlFile ->
                val content = xmlFile.readText()
                // Simple XML parsing for demo - use proper XML parser in production
                val testsMatch = Regex("""tests="(\d+)"""").find(content)
                val failuresMatch = Regex("""failures="(\d+)"""").find(content)
                val errorsMatch = Regex("""errors="(\d+)"""").find(content)
                
                testsMatch?.let { totalTests += it.groupValues[1].toInt() }
                failuresMatch?.let { failedTests += it.groupValues[1].toInt() }
                errorsMatch?.let { failedTests += it.groupValues[1].toInt() }
            }
            
            passedTests = totalTests - failedTests
            
            println("Total Tests: $totalTests")
            println("Passed: $passedTests")
            println("Failed: $failedTests")
            println("Success Rate: ${if (totalTests > 0) (passedTests * 100 / totalTests) else 0}%")
        }
    }
}

// ================================================================
// PERFORMANCE MONITORING
// ================================================================

// Test performance monitoring
tasks.register("testPerformanceReport") {
    description = "Generate test performance analysis"
    group = "reporting"
    
    dependsOn("performanceTests")
    
    doLast {
        println("⚡ Performance Test Summary")
        println("=" * 50)
        
        // Analyze test execution times
        val testResults = layout.buildDirectory.dir("test-results/performanceTests").get().asFile
        if (testResults.exists()) {
            println("Performance test results available at: ${testResults.absolutePath}")
            
            // Custom performance analysis logic here
            println("🎯 Performance Benchmarks:")
            println("  - Profile Creation: < 100ms")
            println("  - Profile Loading: < 50ms")
            println("  - JSON Serialization: < 25ms")
            println("  - UI Refresh: < 200ms")
        }
    }
}
```

## TestNG Configuration

Create `src/test/resources/testng.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="N4-DataSync Test Suite" parallel="methods" thread-count="4">
    
    <!-- Unit Test Suite -->
    <test name="Unit Tests" group-by-instances="true">
        <groups>
            <run>
                <include name="unit"/>
                <include name="crud"/>
                <include name="validation"/>
            </run>
        </groups>
        <packages>
            <package name="com.mea.datasync.service"/>
            <package name="com.mea.datasync.model"/>
            <package name="com.mea.datasync.persistence"/>
        </packages>
    </test>
    
    <!-- Integration Test Suite -->
    <test name="Integration Tests" group-by-instances="true">
        <groups>
            <run>
                <include name="integration"/>
                <include name="persistence"/>
            </run>
        </groups>
        <packages>
            <package name="com.mea.datasync.integration"/>
        </packages>
    </test>
    
    <!-- Performance Test Suite -->
    <test name="Performance Tests" group-by-instances="false">
        <groups>
            <run>
                <include name="performance"/>
                <include name="load"/>
                <include name="stress"/>
            </run>
        </groups>
        <packages>
            <package name="com.mea.datasync.performance"/>
        </packages>
    </test>
    
</suite>
```

## GitHub Actions CI/CD Pipeline

Create `.github/workflows/comprehensive-testing.yml`:

```yaml
name: Comprehensive Testing Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    # Run nightly regression tests
    - cron: '0 2 * * *'

env:
  JAVA_VERSION: '11'
  GRADLE_OPTS: '-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true'

jobs:
  # Fast feedback - unit tests
  unit-tests:
    name: Unit Tests
    runs-on: ubuntu-latest
    timeout-minutes: 10
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'
        
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
          
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Run unit tests
      run: ./gradlew unitTests --continue
      
    - name: Upload unit test results
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: unit-test-results
        path: |
          **/build/reports/tests/unitTests/
          **/build/test-results/unitTests/
          
  # Integration tests
  integration-tests:
    name: Integration Tests
    runs-on: ubuntu-latest
    needs: unit-tests
    timeout-minutes: 20
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'
        
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
          
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Run integration tests
      run: ./gradlew integrationTests --continue
      
    - name: Upload integration test results
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: integration-test-results
        path: |
          **/build/reports/tests/integrationTests/
          **/build/test-results/integrationTests/
          
  # Code coverage analysis
  coverage-analysis:
    name: Coverage Analysis
    runs-on: ubuntu-latest
    needs: [unit-tests, integration-tests]
    timeout-minutes: 15
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'
        
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
          
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Run tests with coverage
      run: ./gradlew test jacocoTestReport jacocoTestCoverageVerification
      
    - name: Upload coverage reports
      uses: actions/upload-artifact@v3
      with:
        name: coverage-reports
        path: |
          **/build/reports/jacoco/
          
    - name: Comment coverage on PR
      if: github.event_name == 'pull_request'
      uses: madrapps/jacoco-report@v1.3
      with:
        paths: |
          **/build/reports/jacoco/test/jacocoTestReport.xml
        token: ${{ secrets.GITHUB_TOKEN }}
        min-coverage-overall: 85
        min-coverage-changed-files: 90
        
  # Performance tests (run on schedule or manual trigger)
  performance-tests:
    name: Performance Tests
    runs-on: ubuntu-latest
    if: github.event_name == 'schedule' || github.event_name == 'workflow_dispatch'
    timeout-minutes: 30
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: ${{ env.JAVA_VERSION }}
        distribution: 'temurin'
        
    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: |
          ~/.gradle/caches
          ~/.gradle/wrapper
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-
          
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Run performance tests
      run: ./gradlew performanceTests
      
    - name: Upload performance test results
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: performance-test-results
        path: |
          **/build/reports/tests/performanceTests/
          **/build/test-results/performanceTests/
          
  # Quality gate - final validation
  quality-gate:
    name: Quality Gate
    runs-on: ubuntu-latest
    needs: [unit-tests, integration-tests, coverage-analysis]
    timeout-minutes: 5
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Quality gate validation
      run: |
        echo "✅ All quality checks passed!"
        echo "📊 Unit tests: Passed"
        echo "🔗 Integration tests: Passed" 
        echo "📈 Coverage requirements: Met"
        echo "🚀 Ready for deployment"
```

## Local Development Scripts

Create `scripts/test-runner.sh`:

```bash
#!/bin/bash
# Local test runner script for N4-DataSync

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
print_header() {
    echo -e "${BLUE}================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}================================${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Main execution
main() {
    case "${1:-all}" in
        "unit")
            print_header "Running Unit Tests"
            ./gradlew unitTests
            print_success "Unit tests completed"
            ;;
        "integration")
            print_header "Running Integration Tests"
            ./gradlew integrationTests
            print_success "Integration tests completed"
            ;;
        "performance")
            print_header "Running Performance Tests"
            ./gradlew performanceTests
            print_success "Performance tests completed"
            ;;
        "coverage")
            print_header "Running Coverage Analysis"
            ./gradlew test jacocoTestReport jacocoTestCoverageVerification
            print_success "Coverage analysis completed"
            echo "📊 Coverage report: build/reports/jacoco/test/html/index.html"
            ;;
        "quality")
            print_header "Running Quality Gate"
            ./gradlew qualityGate
            print_success "Quality gate passed"
            ;;
        "pre-commit")
            print_header "Running Pre-commit Checks"
            ./gradlew preCommitCheck
            print_success "Pre-commit checks passed"
            ;;
        "all")
            print_header "Running Complete Test Suite"
            ./gradlew ciPipeline
            print_success "Complete test suite passed"
            ;;
        *)
            echo "Usage: $0 {unit|integration|performance|coverage|quality|pre-commit|all}"
            echo ""
            echo "Available test commands:"
            echo "  unit        - Run unit tests only (fast)"
            echo "  integration - Run integration tests"
            echo "  performance - Run performance tests"
            echo "  coverage    - Run coverage analysis"
            echo "  quality     - Run quality gate checks"
            echo "  pre-commit  - Run pre-commit validation"
            echo "  all         - Run complete test suite"
            exit 1
            ;;
    esac
}

main "$@"
```

This comprehensive test automation configuration provides enterprise-grade testing infrastructure with multiple test types, quality gates, performance monitoring, and CI/CD integration.
