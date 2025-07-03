# N4-DataSync Testing Strategy & Standards

## Overview

This document defines the **comprehensive testing strategy** for the N4-DataSync project, establishing enterprise-grade testing practices, conventions, and quality gates that ensure production-ready software reliability.

## Testing Philosophy

### Core Principles
1. **Quality First**: No code ships without comprehensive tests
2. **Test-Driven Mindset**: Tests guide design and validate behavior
3. **Fail Fast**: Catch issues at the earliest possible stage
4. **Continuous Validation**: Automated testing at every commit
5. **Documentation Through Tests**: Tests serve as living specifications

### Quality Gates
- **Minimum 85% code coverage** for all production code
- **100% coverage** for critical business logic (ProfileService, persistence)
- **Zero tolerance** for flaky tests
- **All tests must pass** before merge to main branch
- **Performance regression detection** for critical paths

## Testing Pyramid Strategy

```
                    /\
                   /  \     E2E Tests (5%)
                  /____\    - Full workflow validation
                 /      \   - UI integration testing
                /________\  Integration Tests (20%)
               /          \ - Component interaction
              /____________\ - External system integration
             /              \ Unit Tests (75%)
            /________________\ - Business logic validation
                               - Edge case coverage
```

### Test Distribution
- **75% Unit Tests**: Fast, isolated, comprehensive
- **20% Integration Tests**: Component interactions, data flow
- **5% End-to-End Tests**: Critical user journeys

## Testing Standards & Conventions

### Test Class Naming
```java
// Pattern: B{ComponentName}Test
BProfileServiceTest.java        // ✅ Correct
BConnectionProfileTest.java     // ✅ Correct
ProfileServiceTest.java         // ❌ Missing 'B' prefix
TestProfileService.java         // ❌ Wrong pattern
```

### Test Method Naming
```java
// Pattern: test{Action}_{Condition}_{ExpectedResult}
@Test
public void testCreateProfile_WithValidData_ReturnsTrue() { }

@Test  
public void testCreateProfile_WithNullName_ThrowsException() { }

@Test
public void testUpdateProfile_NonExistentProfile_ReturnsFalse() { }
```

### Test Groups (Mandatory)
Every test MUST specify groups for selective execution:

```java
@Test(groups = {"datasync", "unit", "crud", "validation"})
public void testCreateProfile_WithInvalidData_ReturnsFalse() {
    // Test implementation
}
```

#### Standard Test Groups
- **Module**: `datasync` (all tests)
- **Type**: `unit`, `integration`, `e2e`
- **Functionality**: `crud`, `validation`, `persistence`, `ui`, `security`
- **Performance**: `performance`, `load`, `stress`
- **Stability**: `smoke`, `regression`, `critical`

### Test Structure (AAA Pattern - Mandatory)
```java
@Test(groups = {"datasync", "unit", "crud"})
public void testCreateProfile_WithValidData_ReturnsTrue() {
    // ARRANGE (Given) - Setup test data and dependencies
    String profileName = "Test_Profile_" + System.currentTimeMillis();
    BConnectionProfile profile = createValidTestProfile();
    
    // ACT (When) - Execute the method under test
    boolean result = profileService.createProfile(profileName, profile);
    
    // ASSERT (Then) - Verify the expected outcome
    Assert.assertTrue(result, "Profile creation should succeed with valid data");
    Assert.assertTrue(profileService.profileExists(profileName), 
                     "Profile should exist after creation");
    
    // CLEANUP (Optional) - Clean up test data if needed
    profileService.deleteProfile(profileName);
}
```

## Test Categories & Requirements

### 1. Unit Tests (Mandatory for all classes)

#### Coverage Requirements
- **100% method coverage** for public methods
- **90% line coverage** for business logic
- **100% branch coverage** for conditional logic

#### Component-Specific Testing Requirements

##### **Model Layer Testing**
- **BConnectionProfile**: Property validation, serialization, business rules
- **IDM (Intermediate Data Model)**: Data transformation, validation
- **Configuration Objects**: Settings validation, defaults

##### **Service Layer Testing**
- **ProfileService**: CRUD operations, validation, error handling
- **ExcelReaderService**: File parsing, data extraction, error handling
- **ComponentCreatorService**: Niagara component generation
- **SyncService**: Data synchronization, conflict resolution

##### **Persistence Layer Testing**
- **ProfileManager**: JSON serialization, file operations
- **DatabaseConnector**: JDBC operations, connection management
- **FileSystemManager**: File operations, path validation

##### **UI Layer Testing**
- **BDataSyncTool**: Tool lifecycle, user interactions
- **BDataSyncProfileView**: View rendering, data binding
- **BDataSyncTable**: Table operations, sorting, filtering
- **Wizards and Dialogs**: User workflow validation

#### Test Scenarios (Minimum Required)
```java
// For each public method, test:
@Test public void test{Method}_WithValidInput_ReturnsExpectedResult() { }
@Test public void test{Method}_WithNullInput_ThrowsException() { }
@Test public void test{Method}_WithEmptyInput_HandlesGracefully() { }
@Test public void test{Method}_WithInvalidInput_ReturnsError() { }
@Test public void test{Method}_WithBoundaryValues_HandlesCorrectly() { }
```

### 2. Integration Tests (Required for component interactions)

#### Scope
- ProfileService ↔ ProfileManager integration
- ProfileManager ↔ JSON persistence integration  
- UI ↔ ProfileService integration
- Component tree ↔ JSON synchronization

#### Example Structure
```java
@Test(groups = {"datasync", "integration", "persistence"})
public void testProfilePersistence_CreateUpdateDelete_MaintainsConsistency() {
    // Test complete data flow through all layers
}
```

### 3. Performance Tests (Required for critical paths)

#### Performance Benchmarks
- Profile creation: < 100ms
- Profile loading: < 50ms  
- JSON serialization: < 25ms
- UI refresh: < 200ms

```java
@Test(groups = {"datasync", "performance"}, timeOut = 100)
public void testCreateProfile_Performance_CompletesWithin100ms() {
    // Performance validation
}
```

### 4. Security Tests (Required for data handling)

```java
@Test(groups = {"datasync", "security", "validation"})
public void testCreateProfile_WithMaliciousInput_SanitizesData() {
    // Test injection attacks, XSS, etc.
}
```

## Test Data Management

### Test Data Principles
1. **Isolated**: Each test creates its own data
2. **Predictable**: Same input always produces same output
3. **Minimal**: Only create data needed for the test
4. **Clean**: Always clean up after tests

### Test Data Patterns
```java
// Test Data Factory Pattern
public class TestDataFactory {
    public static BConnectionProfile createValidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        profile.setSourceType("Excel");
        profile.setSourcePath("C:\\Test\\Valid.xlsx");
        // ... set all required fields
        return profile;
    }
    
    public static BConnectionProfile createInvalidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        // Intentionally missing required fields
        return profile;
    }
}

// Test Data Builder Pattern  
public class ProfileBuilder {
    private BConnectionProfile profile = new BConnectionProfile();
    
    public ProfileBuilder withSourceType(String type) {
        profile.setSourceType(type);
        return this;
    }
    
    public ProfileBuilder withInvalidData() {
        profile.setSourcePath(null);
        return this;
    }
    
    public BConnectionProfile build() {
        return profile;
    }
}
```

### Test Naming Convention for Data
```java
// Pattern: {TestClass}_{Scenario}_{Timestamp}
String profileName = "BProfileServiceTest_CreateValid_" + System.currentTimeMillis();
String fileName = "TestData_InvalidPath_" + UUID.randomUUID();
```

## Error Handling & Edge Cases

### Mandatory Error Test Scenarios
```java
// Null Input Handling
@Test(expectedExceptions = IllegalArgumentException.class)
public void testCreateProfile_WithNullName_ThrowsException() { }

// Empty Input Handling  
@Test
public void testCreateProfile_WithEmptyName_ReturnsFalse() { }

// Boundary Value Testing
@Test
public void testCreateProfile_WithMaxLengthName_HandlesCorrectly() { }

// Resource Exhaustion
@Test
public void testCreateManyProfiles_WithLimitedMemory_HandlesGracefully() { }

// Concurrent Access
@Test
public void testCreateProfile_ConcurrentAccess_MaintainsConsistency() { }
```

## Extended Test Infrastructure

### Specialized Test Base Classes

#### **BaseTestClass** - Universal Foundation
- Standard test lifecycle management
- Performance tracking and logging
- Test data naming conventions
- Common utilities for all test types

#### **UITestBase** - UI Component Testing
- View lifecycle management
- Widget interaction simulation
- UI state validation
- Event handling verification

#### **IntegrationTestBase** - Component Integration
- Multi-component workflow testing
- Data flow validation across layers
- External system integration testing
- End-to-end scenario validation

#### **PerformanceTestBase** - Performance & Load Testing
- Execution time measurement
- Memory usage monitoring
- Throughput testing
- Load testing with concurrent operations

### Extended Test Data Factories

#### **TestDataFactory** - Core Business Objects
- BConnectionProfile creation with various scenarios
- Builder pattern for fluent object creation
- Validation and edge case data generation

#### **ExcelTestDataFactory** - File Processing
- Excel file creation for various test scenarios
- Malformed file generation for error testing
- Large file creation for performance testing
- Special character and encoding testing

## Test Environment & Setup

### Test Environment Requirements
1. **Isolated**: Tests don't affect each other
2. **Repeatable**: Same results every run
3. **Fast**: Complete test suite runs in < 5 minutes
4. **Reliable**: No flaky tests allowed
5. **Scalable**: Framework supports all project components

### Setup/Teardown Patterns
```java
@BeforeClass(alwaysRun = true)
public void setUpClass() {
    // One-time setup for all tests in class
    testTool = new BDataSyncTool();
    testTool.started();
}

@BeforeMethod(alwaysRun = true)  
public void setUpMethod() {
    // Setup before each test method
    testProfileCount = profileService.getProfileCount();
}

@AfterMethod(alwaysRun = true)
public void tearDownMethod() {
    // Cleanup after each test method
    cleanupTestProfiles();
}

@AfterClass(alwaysRun = true)
public void tearDownClass() {
    // Final cleanup for the class
    if (testTool != null) {
        // Cleanup resources
    }
}
```

## Assertion Standards

### Assertion Best Practices
```java
// ✅ Good: Descriptive assertion messages
Assert.assertTrue(result, "Profile creation should succeed with valid input");
Assert.assertEquals(actualCount, expectedCount, 
                   "Profile count should increase after creation");

// ❌ Bad: No assertion message
Assert.assertTrue(result);
Assert.assertEquals(actualCount, expectedCount);

// ✅ Good: Specific assertions
Assert.assertNotNull(profile, "Created profile should not be null");
Assert.assertEquals(profile.getSourceType(), "Excel", 
                   "Source type should match input");

// ❌ Bad: Generic assertions
Assert.assertTrue(profile != null);
```

### Custom Assertion Methods
```java
// Create domain-specific assertions
public static void assertProfileEquals(BConnectionProfile expected, 
                                     BConnectionProfile actual, 
                                     String message) {
    Assert.assertEquals(actual.getSourceType(), expected.getSourceType(), 
                       message + ": Source type mismatch");
    Assert.assertEquals(actual.getSourcePath(), expected.getSourcePath(), 
                       message + ": Source path mismatch");
    // ... check all relevant fields
}
```

## Test Documentation Requirements

### Test Class Documentation
```java
/**
 * Comprehensive test suite for ProfileService functionality.
 * 
 * Test Coverage:
 * - CRUD operations (Create, Read, Update, Delete)
 * - Input validation and error handling
 * - Data persistence and synchronization
 * - Performance benchmarks
 * - Security validation
 * 
 * Test Groups:
 * - datasync: All DataSync module tests
 * - unit: Fast, isolated unit tests
 * - crud: Basic CRUD operation tests
 * - validation: Input validation tests
 * - performance: Performance benchmark tests
 * 
 * Dependencies:
 * - Requires BDataSyncTool initialization
 * - Uses temporary test data with cleanup
 * 
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "profile-service"})
public class BProfileServiceTest extends BTestNg {
```

### Test Method Documentation
```java
/**
 * Validates profile creation with valid input data.
 * 
 * Test Scenario:
 * - Given: Valid profile name and profile data
 * - When: Creating a new profile via ProfileService
 * - Then: Profile is created successfully and persisted
 * 
 * Validation Points:
 * - Method returns true for successful creation
 * - Profile exists in component tree
 * - Profile is persisted to JSON storage
 * - Profile count increases by 1
 * 
 * @throws Exception if test setup fails
 */
@Test(groups = {"datasync", "unit", "crud"})
public void testCreateProfile_WithValidData_ReturnsTrue() throws Exception {
```

## Continuous Integration Requirements

### Pre-Commit Hooks
```bash
#!/bin/bash
# .git/hooks/pre-commit

echo "Running pre-commit quality checks..."

# Run fast unit tests
./gradlew test --tests "*Test" -Dgroups="unit"
if [ $? -ne 0 ]; then
    echo "❌ Unit tests failed. Commit rejected."
    exit 1
fi

# Check test coverage
./gradlew jacocoTestReport
coverage=$(grep -o 'Total.*[0-9]\+%' build/reports/jacoco/test/html/index.html | grep -o '[0-9]\+%' | head -1 | grep -o '[0-9]\+')
if [ "$coverage" -lt 85 ]; then
    echo "❌ Test coverage ($coverage%) below minimum (85%). Commit rejected."
    exit 1
fi

echo "✅ All quality checks passed. Commit approved."
```

### CI/CD Pipeline Requirements
```yaml
# .github/workflows/testing.yml
name: Comprehensive Testing

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Unit Tests
        run: ./gradlew test -Dgroups="unit"
        
      - name: Integration Tests  
        run: ./gradlew test -Dgroups="integration"
        
      - name: Performance Tests
        run: ./gradlew test -Dgroups="performance"
        
      - name: Coverage Report
        run: ./gradlew jacocoTestReport
        
      - name: Quality Gate
        run: |
          if [ "$COVERAGE" -lt 85 ]; then
            echo "Coverage below threshold"
            exit 1
          fi
```

## Test Metrics & Monitoring

### Key Metrics to Track
1. **Test Coverage**: Line, branch, method coverage
2. **Test Execution Time**: Total and per-test timing
3. **Test Reliability**: Pass/fail rates, flaky test detection
4. **Code Quality**: Cyclomatic complexity, maintainability index

### Reporting Requirements
- **Daily**: Automated test execution reports
- **Weekly**: Test coverage and quality trends
- **Monthly**: Test suite performance analysis
- **Release**: Comprehensive test execution summary

## Test Review & Maintenance

### Code Review Requirements for Tests
- [ ] Test follows naming conventions
- [ ] Proper test groups specified
- [ ] AAA pattern implemented correctly
- [ ] Adequate assertion messages
- [ ] Proper setup/cleanup
- [ ] Edge cases covered
- [ ] Performance considerations addressed

### Test Maintenance Schedule
- **Weekly**: Review and fix flaky tests
- **Monthly**: Refactor slow or complex tests
- **Quarterly**: Update test data and scenarios
- **Annually**: Review and update testing strategy

## Enforcement & Compliance

### Automated Enforcement
- **Build fails** if coverage drops below 85%
- **Pre-commit hooks** prevent low-quality commits
- **CI/CD pipeline** blocks deployment without passing tests
- **Code review** requires test review approval

### Manual Review Points
- New feature development requires test plan
- Bug fixes require regression tests
- Performance changes require benchmark tests
- Security changes require security tests

---

**This testing strategy is a living document that evolves with the project. All team members are responsible for maintaining these standards and continuously improving our testing practices.**
