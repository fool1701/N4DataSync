# N4-DataSync Testing Implementation Guide

## Quick Start Implementation

This guide provides step-by-step instructions to implement the comprehensive testing strategy defined in `TESTING_STRATEGY.md`.

## Phase 1: Foundation Setup (Week 1)

### 1.1 Test Infrastructure Setup

#### Create Test Utilities
```bash
# Create test utility structure
mkdir -p datasync/datasync-wb/srcTest/com/mea/datasync/test/utils
mkdir -p datasync/datasync-wb/srcTest/com/mea/datasync/test/data
mkdir -p datasync/datasync-wb/srcTest/com/mea/datasync/test/fixtures
```

#### Test Base Classes
Create `BaseTestClass.java`:
```java
package com.mea.datasync.test.utils;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base class for all DataSync tests providing common setup and utilities.
 */
@NiagaraType
public abstract class BaseTestClass extends BTestNg {
    
    public static final Type TYPE = Sys.loadType(BaseTestClass.class);
    @Override public Type getType() { return TYPE; }
    
    protected static final String TEST_PREFIX = "Test_";
    protected long testStartTime;
    
    @BeforeClass(alwaysRun = true)
    public void baseSetUp() throws Exception {
        testStartTime = System.currentTimeMillis();
        System.out.println("=== Starting " + getClass().getSimpleName() + " ===");
        performBaseSetup();
    }
    
    @AfterClass(alwaysRun = true)
    public void baseTearDown() throws Exception {
        performBaseTeardown();
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("=== Completed " + getClass().getSimpleName() + 
                          " in " + duration + "ms ===");
    }
    
    protected abstract void performBaseSetup() throws Exception;
    protected abstract void performBaseTeardown() throws Exception;
    
    protected String generateTestId() {
        return TEST_PREFIX + getClass().getSimpleName() + "_" + System.currentTimeMillis();
    }
}
```

### 1.2 Test Data Factory

Create `TestDataFactory.java`:
```java
package com.mea.datasync.test.data;

import com.mea.datasync.model.BConnectionProfile;
import java.util.UUID;

/**
 * Factory for creating test data objects with various configurations.
 */
public class TestDataFactory {
    
    public static BConnectionProfile createValidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        profile.setSourceType("Excel");
        profile.setSourcePath("C:\\Test\\Valid_" + UUID.randomUUID() + ".xlsx");
        profile.setSheetName("TestData");
        profile.setTargetHost("localhost");
        profile.setTargetUsername("testuser");
        profile.setTargetPath("station:|slot:/Test");
        profile.setStatus("Never Synced");
        profile.setComponentsCreated(0);
        return profile;
    }
    
    public static BConnectionProfile createInvalidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        // Intentionally leave required fields null/empty
        profile.setSourceType("");
        profile.setSourcePath(null);
        return profile;
    }
    
    public static BConnectionProfile createProfileWithLongName() {
        BConnectionProfile profile = createValidProfile();
        profile.setSourcePath("C:\\Test\\" + "A".repeat(255) + ".xlsx");
        return profile;
    }
    
    public static BConnectionProfile createProfileForPerformanceTest() {
        BConnectionProfile profile = createValidProfile();
        profile.setSourcePath("C:\\Test\\Performance_" + System.nanoTime() + ".xlsx");
        return profile;
    }
}
```

### 1.3 Custom Assertions

Create `DataSyncAssertions.java`:
```java
package com.mea.datasync.test.utils;

import com.mea.datasync.model.BConnectionProfile;
import org.testng.Assert;

/**
 * Custom assertions for DataSync domain objects.
 */
public class DataSyncAssertions {
    
    public static void assertProfileEquals(BConnectionProfile expected, 
                                         BConnectionProfile actual, 
                                         String message) {
        Assert.assertNotNull(actual, message + ": Actual profile should not be null");
        Assert.assertEquals(actual.getSourceType(), expected.getSourceType(), 
                           message + ": Source type mismatch");
        Assert.assertEquals(actual.getSourcePath(), expected.getSourcePath(), 
                           message + ": Source path mismatch");
        Assert.assertEquals(actual.getSheetName(), expected.getSheetName(), 
                           message + ": Sheet name mismatch");
        Assert.assertEquals(actual.getTargetHost(), expected.getTargetHost(), 
                           message + ": Target host mismatch");
        Assert.assertEquals(actual.getTargetUsername(), expected.getTargetUsername(), 
                           message + ": Target username mismatch");
        Assert.assertEquals(actual.getTargetPath(), expected.getTargetPath(), 
                           message + ": Target path mismatch");
    }
    
    public static void assertProfileValid(BConnectionProfile profile, String message) {
        Assert.assertNotNull(profile, message + ": Profile should not be null");
        Assert.assertNotNull(profile.getSourceType(), message + ": Source type required");
        Assert.assertFalse(profile.getSourceType().trim().isEmpty(), 
                           message + ": Source type cannot be empty");
        Assert.assertNotNull(profile.getSourcePath(), message + ": Source path required");
        Assert.assertFalse(profile.getSourcePath().trim().isEmpty(), 
                           message + ": Source path cannot be empty");
    }
    
    public static void assertProfilePersisted(String profileName, 
                                             com.mea.datasync.ui.BDataSyncTool tool, 
                                             String message) {
        Assert.assertTrue(tool.profileExists(profileName), 
                         message + ": Profile should exist in tool");
        
        BConnectionProfile[] profiles = tool.getAllProfiles();
        boolean found = false;
        for (BConnectionProfile profile : profiles) {
            if (profile.getName().equals(profileName)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, message + ": Profile should be in component tree");
    }
}
```

## Phase 2: Enhanced Test Implementation (Week 2)

### 2.1 Comprehensive ProfileService Tests

Update `BProfileServiceTest.java` with advanced patterns:
```java
package com.mea.datasync.service;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mea.datasync.model.BConnectionProfile;
import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.test.utils.BaseTestClass;
import com.mea.datasync.test.data.TestDataFactory;
import com.mea.datasync.test.utils.DataSyncAssertions;

/**
 * Comprehensive test suite for ProfileService with enterprise-grade coverage.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "profile-service"})
public class BProfileServiceTest extends BaseTestClass {

    public static final Type TYPE = Sys.loadType(BProfileServiceTest.class);
    @Override public Type getType() { return TYPE; }

    private BDataSyncTool testTool;
    private ProfileService profileService;
    private int initialProfileCount;

    @Override
    protected void performBaseSetup() throws Exception {
        testTool = new BDataSyncTool();
        testTool.started();
        profileService = new ProfileService(testTool);
    }

    @Override
    protected void performBaseTeardown() throws Exception {
        cleanupAllTestProfiles();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        initialProfileCount = testTool.getProfileCount();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod() {
        cleanupTestProfiles();
    }

    // Data Provider for parameterized tests
    @DataProvider(name = "validProfileData")
    public Object[][] validProfileData() {
        return new Object[][] {
            {"Excel", "C:\\Test\\Data1.xlsx", "Sheet1"},
            {"Excel", "C:\\Test\\Data2.xlsx", "Equipment"},
            {"Excel", "C:\\Test\\Data3.xlsx", "Sensors"}
        };
    }

    @DataProvider(name = "invalidProfileData")
    public Object[][] invalidProfileData() {
        return new Object[][] {
            {null, "C:\\Test\\Data.xlsx", "Sheet1"},
            {"", "C:\\Test\\Data.xlsx", "Sheet1"},
            {"Excel", null, "Sheet1"},
            {"Excel", "", "Sheet1"},
            {"Excel", "C:\\Test\\Data.xlsx", null}
        };
    }

    // Parameterized test example
    @Test(dataProvider = "validProfileData", 
          groups = {"datasync", "unit", "crud", "parameterized"})
    public void testCreateProfile_WithVariousValidData_ReturnsTrue(String sourceType, 
                                                                   String sourcePath, 
                                                                   String sheetName) {
        // ARRANGE
        String profileName = generateTestId();
        BConnectionProfile profile = TestDataFactory.createValidProfile();
        profile.setSourceType(sourceType);
        profile.setSourcePath(sourcePath);
        profile.setSheetName(sheetName);

        // ACT
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertTrue(result, "Profile creation should succeed with valid data");
        DataSyncAssertions.assertProfilePersisted(profileName, testTool, 
                                                  "Profile should be persisted");
        Assert.assertEquals(testTool.getProfileCount(), initialProfileCount + 1, 
                           "Profile count should increase");
    }

    @Test(dataProvider = "invalidProfileData", 
          groups = {"datasync", "unit", "validation", "parameterized"})
    public void testCreateProfile_WithInvalidData_ReturnsFalse(String sourceType, 
                                                               String sourcePath, 
                                                               String sheetName) {
        // ARRANGE
        String profileName = generateTestId();
        BConnectionProfile profile = new BConnectionProfile();
        profile.setSourceType(sourceType);
        profile.setSourcePath(sourcePath);
        profile.setSheetName(sheetName);

        // ACT
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertFalse(result, "Profile creation should fail with invalid data");
        Assert.assertEquals(testTool.getProfileCount(), initialProfileCount, 
                           "Profile count should not change");
    }

    @Test(groups = {"datasync", "unit", "performance"}, timeOut = 100)
    public void testCreateProfile_Performance_CompletesWithin100ms() {
        // ARRANGE
        String profileName = generateTestId();
        BConnectionProfile profile = TestDataFactory.createProfileForPerformanceTest();

        // ACT & ASSERT (timeOut annotation enforces performance requirement)
        long startTime = System.nanoTime();
        boolean result = testTool.createProfile(profileName, profile);
        long duration = (System.nanoTime() - startTime) / 1_000_000; // Convert to ms

        Assert.assertTrue(result, "Profile creation should succeed");
        Assert.assertTrue(duration < 100, 
                         "Profile creation took " + duration + "ms, should be < 100ms");
    }

    @Test(groups = {"datasync", "unit", "stress"})
    public void testCreateMultipleProfiles_StressTest_HandlesLoad() {
        // ARRANGE
        int profileCount = 100;
        String[] profileNames = new String[profileCount];

        // ACT
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < profileCount; i++) {
            profileNames[i] = generateTestId() + "_" + i;
            BConnectionProfile profile = TestDataFactory.createValidProfile();
            boolean result = testTool.createProfile(profileNames[i], profile);
            Assert.assertTrue(result, "Profile " + i + " creation should succeed");
        }
        long duration = System.currentTimeMillis() - startTime;

        // ASSERT
        Assert.assertEquals(testTool.getProfileCount(), initialProfileCount + profileCount,
                           "All profiles should be created");
        Assert.assertTrue(duration < 5000, 
                         "Creating " + profileCount + " profiles took " + duration + 
                         "ms, should be < 5000ms");

        // Cleanup
        for (String profileName : profileNames) {
            testTool.deleteProfile(profileName);
        }
    }

    @Test(groups = {"datasync", "unit", "concurrency"})
    public void testConcurrentProfileCreation_ThreadSafety_MaintainsConsistency() {
        // ARRANGE
        int threadCount = 10;
        int profilesPerThread = 5;
        Thread[] threads = new Thread[threadCount];
        boolean[] results = new boolean[threadCount * profilesPerThread];

        // ACT
        for (int t = 0; t < threadCount; t++) {
            final int threadIndex = t;
            threads[t] = new Thread(() -> {
                for (int p = 0; p < profilesPerThread; p++) {
                    String profileName = generateTestId() + "_T" + threadIndex + "_P" + p;
                    BConnectionProfile profile = TestDataFactory.createValidProfile();
                    results[threadIndex * profilesPerThread + p] = 
                        testTool.createProfile(profileName, profile);
                }
            });
            threads[t].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join(5000); // 5 second timeout
            } catch (InterruptedException e) {
                Assert.fail("Thread interrupted during concurrent test");
            }
        }

        // ASSERT
        for (int i = 0; i < results.length; i++) {
            Assert.assertTrue(results[i], "Profile creation " + i + " should succeed");
        }
        
        int expectedCount = initialProfileCount + (threadCount * profilesPerThread);
        Assert.assertEquals(testTool.getProfileCount(), expectedCount,
                           "All concurrent profile creations should succeed");
    }

    // Helper methods
    private void cleanupTestProfiles() {
        BConnectionProfile[] profiles = testTool.getAllProfiles();
        for (BConnectionProfile profile : profiles) {
            if (profile.getName().startsWith(TEST_PREFIX)) {
                testTool.deleteProfile(profile.getName());
            }
        }
    }

    private void cleanupAllTestProfiles() {
        cleanupTestProfiles();
    }
}
```

## Phase 3: Integration & E2E Tests (Week 3)

### 3.1 Integration Test Structure

Create `BProfileIntegrationTest.java`:
```java
package com.mea.datasync.integration;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.model.BConnectionProfile;
import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.persistence.ProfileManager;
import com.mea.datasync.test.utils.BaseTestClass;
import com.mea.datasync.test.data.TestDataFactory;

/**
 * Integration tests for complete ProfileService workflow.
 */
@NiagaraType
@Test(groups = {"datasync", "integration"})
public class BProfileIntegrationTest extends BaseTestClass {

    public static final Type TYPE = Sys.loadType(BProfileIntegrationTest.class);
    @Override public Type getType() { return TYPE; }

    private BDataSyncTool testTool;
    private ProfileManager profileManager;

    @Override
    protected void performBaseSetup() throws Exception {
        testTool = new BDataSyncTool();
        testTool.started();
        profileManager = new ProfileManager();
    }

    @Override
    protected void performBaseTeardown() throws Exception {
        // Cleanup
    }

    @Test(groups = {"datasync", "integration", "persistence"})
    public void testCompleteProfileLifecycle_CreateUpdateDelete_MaintainsConsistency() {
        // ARRANGE
        String profileName = generateTestId();
        BConnectionProfile originalProfile = TestDataFactory.createValidProfile();

        // ACT & ASSERT - Create
        boolean created = testTool.createProfile(profileName, originalProfile);
        Assert.assertTrue(created, "Profile should be created successfully");
        
        // Verify in component tree
        Assert.assertTrue(testTool.profileExists(profileName), 
                         "Profile should exist in component tree");
        
        // Verify in JSON storage
        Assert.assertTrue(profileManager.profileExists(profileName), 
                         "Profile should exist in JSON storage");

        // ACT & ASSERT - Update
        BConnectionProfile updatedProfile = TestDataFactory.createValidProfile();
        updatedProfile.setStatus("Updated");
        updatedProfile.setComponentsCreated(42);
        
        boolean updated = testTool.updateProfile(profileName, updatedProfile);
        Assert.assertTrue(updated, "Profile should be updated successfully");

        // Verify update persisted
        BConnectionProfile loadedProfile = profileManager.loadProfile(profileName);
        Assert.assertEquals(loadedProfile.getStatus(), "Updated", 
                           "Status should be updated in JSON");
        Assert.assertEquals(loadedProfile.getComponentsCreated(), 42, 
                           "Components created should be updated in JSON");

        // ACT & ASSERT - Delete
        boolean deleted = testTool.deleteProfile(profileName);
        Assert.assertTrue(deleted, "Profile should be deleted successfully");
        
        // Verify deletion from both storages
        Assert.assertFalse(testTool.profileExists(profileName), 
                          "Profile should not exist in component tree");
        Assert.assertFalse(profileManager.profileExists(profileName), 
                          "Profile should not exist in JSON storage");
    }
}
```

## Phase 4: Test Automation & CI/CD (Week 4)

### 4.1 Gradle Test Configuration

Update `datasync-wb.gradle.kts`:
```kotlin
// Test configuration
test {
    useTestNG {
        // Configure test groups
        includeGroups("unit", "integration")
        excludeGroups("e2e", "manual")
        
        // Parallel execution
        parallel = "methods"
        threadCount = 4
    }
    
    // Test reporting
    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
    
    // Test JVM settings
    jvmArgs("-Xmx1g", "-XX:MaxPermSize=256m")
    
    // Fail fast on first test failure
    failFast = true
    
    // Test output
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
}

// Separate task for different test types
tasks.register<Test>("unitTests") {
    useTestNG {
        includeGroups("unit")
    }
    description = "Run unit tests only"
    group = "verification"
}

tasks.register<Test>("integrationTests") {
    useTestNG {
        includeGroups("integration")
    }
    description = "Run integration tests only"
    group = "verification"
    shouldRunAfter("unitTests")
}

tasks.register<Test>("performanceTests") {
    useTestNG {
        includeGroups("performance")
    }
    description = "Run performance tests only"
    group = "verification"
    timeout.set(Duration.ofMinutes(10))
}

// Test coverage
jacoco {
    toolVersion = "0.8.7"
}

jacocoTestReport {
    dependsOn(test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    
    finalizedBy(jacocoTestCoverageVerification)
}

jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.85".toBigDecimal() // 85% minimum coverage
            }
        }
        
        rule {
            element = "CLASS"
            includes = ["com.mea.datasync.service.*", "com.mea.datasync.model.*"]
            limit {
                minimum = "0.90".toBigDecimal() // 90% for critical classes
            }
        }
    }
}
```

### 4.2 Pre-commit Hook

Create `.git/hooks/pre-commit`:
```bash
#!/bin/bash
# Pre-commit hook for N4-DataSync

echo "🔍 Running pre-commit quality checks..."

# Run unit tests
echo "Running unit tests..."
./gradlew unitTests --quiet
if [ $? -ne 0 ]; then
    echo "❌ Unit tests failed. Commit rejected."
    exit 1
fi

# Check test coverage
echo "Checking test coverage..."
./gradlew jacocoTestCoverageVerification --quiet
if [ $? -ne 0 ]; then
    echo "❌ Test coverage below minimum threshold. Commit rejected."
    exit 1
fi

# Run static analysis (if configured)
echo "Running static analysis..."
./gradlew checkstyleMain --quiet
if [ $? -ne 0 ]; then
    echo "⚠️  Code style issues detected. Please review."
fi

echo "✅ All quality checks passed. Commit approved."
exit 0
```

## Implementation Checklist

### Week 1: Foundation
- [ ] Create test utility classes (BaseTestClass, TestDataFactory, DataSyncAssertions)
- [ ] Set up test directory structure
- [ ] Implement basic test infrastructure
- [ ] Create first comprehensive test class

### Week 2: Comprehensive Testing
- [ ] Implement parameterized tests
- [ ] Add performance tests with timeouts
- [ ] Create stress and concurrency tests
- [ ] Implement custom assertion methods

### Week 3: Integration & E2E
- [ ] Create integration test suite
- [ ] Implement end-to-end workflow tests
- [ ] Add cross-component validation tests
- [ ] Test data persistence across layers

### Week 4: Automation & CI/CD
- [ ] Configure Gradle test tasks
- [ ] Set up test coverage reporting
- [ ] Implement pre-commit hooks
- [ ] Create CI/CD pipeline configuration

### Ongoing: Maintenance
- [ ] Weekly test review and cleanup
- [ ] Monthly performance benchmark review
- [ ] Quarterly test strategy review
- [ ] Continuous test improvement

## Success Metrics

- **85%+ test coverage** across all production code
- **100% test coverage** for critical business logic
- **< 5 minute** complete test suite execution
- **Zero flaky tests** in the main test suite
- **100% test pass rate** on main branch

This implementation guide provides the roadmap to achieve enterprise-grade testing standards for your N4-DataSync project.
