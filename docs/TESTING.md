# Testing Guide for N4-DataSync

## Overview

N4-DataSync uses Niagara's standard TestNG framework for automated testing. This guide explains how to run tests properly and where to find results.

## Test Structure

Tests are located in the standard Niagara test structure:
```
datasync/datasync-wb/
├── srcTest/
│   └── com/mea/datasync/test/
│       └── BProfileManagerTest.java
└── moduleTest-include.xml
```

## Running Tests

### Method 1: Direct Test Command (Recommended for Development)

The official Niagara approach using the direct test command:

```bash
# Build test module first
gradlew :datasync-wb:moduleTestJar

# Run all tests for the module
test datasync-wb

# Run specific test groups
test datasync-wb -groups:unit,datasync

# Run with verbose output (1-10, higher = more verbose)
test datasync-wb -v:5

# Run with custom output location
test datasync-wb -output:build/test-results

# Additional options
test datasync-wb -benchmark                    # Show 50 highest duration tests
test datasync-wb -generateJunitReport          # Generate JUnit XML reports
test datasync-wb -excludegroups:slow,manual    # Exclude specific groups
```

**Advantages:**
- ✅ Works reliably with self-signed certificates
- ✅ Official Niagara TestNG approach
- ✅ Fast execution and feedback
- ✅ Flexible command-line options

### Method 2: Gradle niagaraTest (Recommended for CI/CD)

The Gradle-integrated approach for enterprise environments:

```bash
# Run all tests with coverage
gradlew :datasync-wb:niagaraTest

# Generate coverage report
gradlew :datasync-wb:jacocoNiagaraTestReport

# Clean and run tests
gradlew :datasync-wb:clean :datasync-wb:niagaraTest
```

**Advantages:**
- ✅ Integrates with build pipeline
- ✅ Generates comprehensive reports
- ✅ Code coverage with JaCoCo
- ✅ Enterprise-grade security validation

**Note:** May require certificate configuration for automated environments.

## Test Results

### Standard Locations

**Gradle Test Results:**
- **HTML Report**: `datasync/datasync-wb/build/reports/tests/niagara/index.html`
- **XML Results**: `datasync/datasync-wb/build/test-results/niagara/`
- **JaCoCo Coverage**: `datasync/datasync-wb/build/reports/jacoco/niagaraTest/`

**Niagara Default Location:**
- **Default**: `{niagara_user_home}/reports/testng/`
- **Custom**: Specified with `-output:<path>` option

### Report Contents

Each test run generates:
- `index.html` - Main interactive HTML report
- `testng-results.xml` - Detailed XML results
- `emailable-report.html` - Summary report for sharing
- CSS/JS assets for report styling

## Test Groups

N4-DataSync tests are organized into groups for selective execution:

- **`datasync`** - All DataSync-related tests
- **`unit`** - Unit tests (fast, no external dependencies)
- **`integration`** - Integration tests (may require Niagara runtime)
- **`persistence`** - Profile persistence tests
- **`json`** - JSON serialization tests
- **`ui`** - User interface tests
- **`standalone`** - Tests that can run without full Niagara context

### Running Specific Groups

```bash
# Run only unit tests
gradlew :datasync-wb:niagaraTest -Pgroups=unit

# Run unit and persistence tests
gradlew :datasync-wb:niagaraTest -Pgroups=unit,persistence

# Exclude UI tests
gradlew :datasync-wb:niagaraTest -PexcludeGroups=ui
```

## Writing Tests

### Test Class Structure

```java
@NiagaraType
@Test(groups = {"datasync", "unit"})
public class BMyComponentTest extends BTestNg {
  
  @BeforeClass(alwaysRun = true)
  public void setup() {
    // Test setup
  }
  
  @Test(groups = {"unit", "persistence"})
  public void testBasicFunctionality() {
    // Given
    // When  
    // Then
  }
  
  @AfterClass(alwaysRun = true)
  public void cleanup() {
    // Test cleanup
  }
}
```

### Best Practices

1. **Extend BTestNg** - All test classes must extend `BTestNg`
2. **Use @NiagaraType** - Required for proper Niagara integration
3. **Group Tests** - Use `@Test(groups = {...})` for organization
4. **Given-When-Then** - Structure test methods clearly
5. **Resource Cleanup** - Always clean up in `@AfterClass` methods
6. **Descriptive Names** - Use clear, descriptive test method names

## Troubleshooting

### Common Issues

**Tests Not Found:**
- Verify test classes extend `BTestNg`
- Check `moduleTest-include.xml` includes test types
- Ensure test classes are in `srcTest/` directory

**Module Loading Errors:**
- Verify module dependencies in `datasync-wb.gradle.kts`
- Check that `moduleTestImplementation("Tridium:test-wb")` is present
- Ensure module builds successfully before running tests

**Permission Errors:**
- Check write permissions to test output directories
- Verify Niagara user home directory is accessible

### Debug Mode

```bash
# Run with maximum verbosity
gradlew :datasync-wb:niagaraTest -Pverbosity=10

# Enable debug logging
gradlew :datasync-wb:niagaraTest --debug

# Generate JUnit XML for CI integration
gradlew :datasync-wb:niagaraTest -PgenerateJunitReport=true
```

## Continuous Integration

For CI/CD pipelines:

```bash
# Standard CI test run
gradlew :datasync-wb:clean :datasync-wb:niagaraTest -PgenerateJunitReport=true

# Results for CI systems
# - JUnit XML: build/test-results/niagara/
# - Coverage: build/reports/jacoco/niagaraTest/
```

## References

- [Niagara TestNG Documentation](Niagara/Development/AutomatedTestingWithTestNg.md)
- [Niagara Build System](Niagara/Development/Build.md)
- [Development Patterns](NiagaraPatterns.md#testing-patterns)
