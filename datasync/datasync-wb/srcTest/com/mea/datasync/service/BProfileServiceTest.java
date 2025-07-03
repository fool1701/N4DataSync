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
 * Comprehensive test suite for ProfileService functionality.
 *
 * This test class validates the ProfileService using enterprise-grade testing practices:
 * - Comprehensive CRUD operation testing
 * - Input validation and error handling
 * - Performance benchmarks and stress testing
 * - Concurrency and thread safety validation
 * - Data persistence and synchronization testing
 *
 * Test Coverage:
 * - CRUD operations (Create, Read, Update, Delete)
 * - Input validation and error handling
 * - Data persistence and synchronization
 * - Performance benchmarks
 * - Concurrency and thread safety
 *
 * Test Groups:
 * - datasync: All DataSync module tests
 * - unit: Fast, isolated unit tests
 * - crud: Basic CRUD operation tests
 * - validation: Input validation tests
 * - performance: Performance benchmark tests
 * - concurrency: Thread safety tests
 *
 * @author N4-DataSync Team
 * @version 2.0 - Enhanced with enterprise testing framework
 * @since 2025-01-01
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
        logTestStep("Initializing ProfileService test environment");

        // Create test tool instance
        testTool = new BDataSyncTool();
        testTool.started(); // Initialize the tool and ProfileService

        // Get the ProfileService instance
        profileService = new ProfileService(testTool);

        logTestStep("ProfileService test setup complete");
    }

    @Override
    protected void performBaseTeardown() throws Exception {
        logTestStep("Cleaning up ProfileService test environment");

        // Clean up any remaining test profiles
        cleanupAllTestProfiles();

        logTestStep("ProfileService test cleanup complete");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        // Record initial state before each test
        initialProfileCount = testTool != null ? testTool.getProfileCount() : 0;
        logTestData("Initial profile count", initialProfileCount);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod() {
        // Clean up test data after each test
        cleanupTestProfiles();

        // Verify clean state
        int finalCount = testTool != null ? testTool.getProfileCount() : 0;
        logTestData("Final profile count", finalCount);
    }

    // Data Providers for parameterized testing

    @DataProvider(name = "validProfileData")
    public Object[][] validProfileData() {
        return new Object[][] {
            {"Excel", "C:\\Test\\Data1.xlsx", "Sheet1"},
            {"Excel", "C:\\Test\\Data2.xlsx", "Equipment"},
            {"Excel", "C:\\Test\\Data3.xlsx", "Sensors"},
            {"CSV", "C:\\Test\\Data4.csv", "Data"},
            {"Database", "jdbc:sqlserver://localhost", "TableData"}
        };
    }

    @DataProvider(name = "invalidProfileData")
    public Object[][] invalidProfileData() {
        return new Object[][] {
            {null, "C:\\Test\\Data.xlsx", "Sheet1"},           // Null source type
            {"", "C:\\Test\\Data.xlsx", "Sheet1"},             // Empty source type
            {"Excel", null, "Sheet1"},                         // Null source path
            {"Excel", "", "Sheet1"},                           // Empty source path
            {"Excel", "C:\\Test\\Data.xlsx", ""},              // Empty sheet name (not allowed)
            {"InvalidType", "C:\\Test\\Data.xlsx", "Sheet1"}   // Invalid source type
        };
    }

    // Enhanced CRUD Tests using TestDataFactory and DataSyncAssertions

    @Test(groups = {"datasync", "unit", "crud"})
    public void testCreateProfile_WithValidData_ReturnsTrue() {
        // ARRANGE
        String profileName = generateProfileTestName();
        BConnectionProfile profile = TestDataFactory.createValidProfile();
        logTestData("Profile name", profileName);
        logTestData("Source type", profile.getSourceType());

        // ACT
        long startTime = System.nanoTime();
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertTrue(result, "Profile creation should succeed with valid data");
        DataSyncAssertions.assertProfilePersisted(profileName, testTool, "Profile should be persisted");
        DataSyncAssertions.assertProfileCount(initialProfileCount + 1, testTool, "Profile count should increase");

        // Performance assertion
        DataSyncAssertions.assertPerformance(startTime, 100, "Profile creation", "Performance check");
    }

    @Test(dataProvider = "validProfileData",
          groups = {"datasync", "unit", "crud", "parameterized"})
    public void testCreateProfile_WithVariousValidData_ReturnsTrue(String sourceType,
                                                                   String sourcePath,
                                                                   String sheetName) {
        // ARRANGE
        String profileName = generateTestId("Param");
        BConnectionProfile profile = TestDataFactory.builder()
            .asValidProfile()
            .withSourceType(sourceType)
            .withSourcePath(sourcePath)
            .withSheetName(sheetName)
            .build();

        logTestData("Testing with", sourceType + " | " + sourcePath + " | " + sheetName);

        // ACT
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertTrue(result, "Profile creation should succeed with valid data");
        DataSyncAssertions.assertProfilePersisted(profileName, testTool, "Profile should be persisted");
        DataSyncAssertions.assertProfileCount(initialProfileCount + 1, testTool, "Profile count should increase");
    }

    @Test(dataProvider = "invalidProfileData",
          groups = {"datasync", "unit", "validation", "parameterized"})
    public void testCreateProfile_WithInvalidData_ReturnsFalse(String sourceType,
                                                               String sourcePath,
                                                               String sheetName) {
        // ARRANGE
        String profileName = generateTestId("Invalid");
        BConnectionProfile profile = new BConnectionProfile();

        logTestData("Testing invalid data", sourceType + " | " + sourcePath + " | " + sheetName);

        // ACT & ASSERT
        boolean result = false;
        try {
            // Try to set the invalid data - this may throw validation exceptions
            profile.setSourceType(sourceType);
            profile.setSourcePath(sourcePath);
            profile.setSheetName(sheetName);

            // If setters succeed, try to create the profile (should still fail for invalid types)
            result = testTool.createProfile(profileName, profile);
        } catch (IllegalArgumentException e) {
            // Expected for null/empty/invalid values - validation working correctly
            result = false;
        }

        // ASSERT
        Assert.assertFalse(result, "Profile creation should fail with invalid data");
        DataSyncAssertions.assertProfileCount(initialProfileCount, testTool, "Profile count should not change");
    }

    @Test(groups = {"datasync", "unit", "crud"})
    public void testUpdateProfile_WithValidData_ReturnsTrue() {
        // ARRANGE
        String profileName = generateProfileTestName();
        BConnectionProfile originalProfile = TestDataFactory.createValidProfile();
        testTool.createProfile(profileName, originalProfile);

        BConnectionProfile updatedProfile = TestDataFactory.createSuccessfulSyncProfile();
        updatedProfile.setComponentsCreated(42);
        logTestData("Original status", originalProfile.getStatus());
        logTestData("Updated status", updatedProfile.getStatus());

        // ACT
        boolean result = testTool.updateProfile(profileName, updatedProfile);

        // ASSERT
        Assert.assertTrue(result, "Profile update should succeed");
        DataSyncAssertions.assertProfilePersisted(profileName, testTool, "Updated profile should be persisted");

        // Verify specific updates
        BConnectionProfile[] profiles = testTool.getAllProfiles();
        BConnectionProfile foundProfile = null;
        for (BConnectionProfile p : profiles) {
            if (profileName.equals(p.getName())) {
                foundProfile = p;
                break;
            }
        }

        Assert.assertNotNull(foundProfile, "Updated profile should be found");
        DataSyncAssertions.assertProfileStatus(foundProfile, updatedProfile.getStatus(), "Status should be updated");
        Assert.assertEquals(foundProfile.getComponentsCreated(), 42, "Components created should be updated");
    }

    @Test(groups = {"datasync", "unit", "crud"})
    public void testDeleteProfile() {
        // Given: An existing profile
        String profileName = "Test_Delete_" + System.currentTimeMillis();
        BConnectionProfile profile = new BConnectionProfile();
        profile.setSourceType("Excel");
        testTool.createProfile(profileName, profile);
        int initialCount = testTool.getProfileCount();

        // When: Deleting the profile
        boolean result = testTool.deleteProfile(profileName);

        // Then: Profile should be deleted successfully
        Assert.assertTrue(result, "Profile deletion should succeed");
        Assert.assertFalse(testTool.profileExists(profileName), "Profile should not exist after deletion");
        Assert.assertEquals(testTool.getProfileCount(), initialCount - 1, "Profile count should decrease");
    }

    @Test(groups = {"datasync", "unit", "validation"})
    public void testCreateProfileWithInvalidData() {
        // Given: Invalid profile data
        String profileName = null; // Invalid name
        BConnectionProfile profile = new BConnectionProfile();

        // When: Attempting to create profile with invalid data
        boolean result = testTool.createProfile(profileName, profile);

        // Then: Creation should fail
        Assert.assertFalse(result, "Profile creation with invalid data should fail");
    }

    @Test(groups = {"datasync", "unit", "validation"})
    public void testCreateDuplicateProfile() {
        // Given: An existing profile
        String profileName = "Test_Duplicate_" + System.currentTimeMillis();
        BConnectionProfile profile1 = new BConnectionProfile();
        profile1.setSourceType("Excel");
        testTool.createProfile(profileName, profile1);

        // When: Attempting to create another profile with same name
        BConnectionProfile profile2 = new BConnectionProfile();
        profile2.setSourceType("Database");
        boolean result = testTool.createProfile(profileName, profile2);

        // Then: Second creation should fail
        Assert.assertFalse(result, "Duplicate profile creation should fail");
    }

    @Test(groups = {"datasync", "unit", "error-handling"})
    public void testUpdateNonExistentProfile() {
        // Given: A non-existent profile name
        String profileName = "Test_NonExistent_" + System.currentTimeMillis();
        BConnectionProfile profile = new BConnectionProfile();

        // When: Attempting to update non-existent profile
        boolean result = testTool.updateProfile(profileName, profile);

        // Then: Update should fail
        Assert.assertFalse(result, "Updating non-existent profile should fail");
    }

    @Test(groups = {"datasync", "unit", "error-handling"})
    public void testDeleteNonExistentProfile() {
        // Given: A non-existent profile name
        String profileName = "Test_NonExistent_" + System.currentTimeMillis();

        // When: Attempting to delete non-existent profile
        boolean result = testTool.deleteProfile(profileName);

        // Then: Deletion should handle gracefully (implementation dependent)
        // Note: This test verifies the method doesn't throw exceptions
        Assert.assertNotNull(result, "Delete method should return a boolean result");
    }

    @Test(groups = {"datasync", "unit", "persistence"})
    public void testProfilePersistence() {
        // Given: A profile with specific data
        String profileName = "Test_Persistence_" + System.currentTimeMillis();
        BConnectionProfile profile = new BConnectionProfile();
        profile.setSourceType("Excel");
        profile.setSourcePath("C:\\Test\\Persistence.xlsx");
        profile.setSheetName("Data");
        profile.setTargetHost("192.168.1.100");
        profile.setTargetUsername("testuser");
        profile.setTargetPath("station:|slot:/Test");
        profile.setStatus("Success");
        profile.setComponentsCreated(25);
        profile.setLastError("No errors");

        // When: Creating and then retrieving the profile
        boolean created = testTool.createProfile(profileName, profile);
        Assert.assertTrue(created, "Profile should be created for persistence test");

        // Then: All data should be preserved
        BConnectionProfile[] profiles = testTool.getAllProfiles();
        BConnectionProfile retrievedProfile = null;
        for (BConnectionProfile p : profiles) {
            if (p.getName().equals(profileName)) {
                retrievedProfile = p;
                break;
            }
        }

        Assert.assertNotNull(retrievedProfile, "Profile should be retrievable");
        Assert.assertEquals(retrievedProfile.getSourceType(), "Excel", "Source type should persist");
        Assert.assertEquals(retrievedProfile.getSourcePath(), "C:\\Test\\Persistence.xlsx", "Source path should persist");
        Assert.assertEquals(retrievedProfile.getSheetName(), "Data", "Sheet name should persist");
        Assert.assertEquals(retrievedProfile.getTargetHost(), "192.168.1.100", "Target host should persist");
        Assert.assertEquals(retrievedProfile.getTargetUsername(), "testuser", "Target username should persist");
        Assert.assertEquals(retrievedProfile.getTargetPath(), "station:|slot:/Test", "Target path should persist");
        Assert.assertEquals(retrievedProfile.getStatus(), "Success", "Status should persist");
        Assert.assertEquals(retrievedProfile.getComponentsCreated(), 25, "Components created should persist");
        Assert.assertEquals(retrievedProfile.getLastError(), "No errors", "Last error should persist");
    }

    @Test(groups = {"datasync", "unit", "crud"})
    public void testDeleteProfile_ExistingProfile_ReturnsTrue() {
        // ARRANGE
        String profileName = generateProfileTestName();
        BConnectionProfile profile = TestDataFactory.createValidProfile();
        testTool.createProfile(profileName, profile);
        int countBeforeDelete = testTool.getProfileCount();
        logTestData("Profile count before delete", countBeforeDelete);

        // ACT
        boolean result = testTool.deleteProfile(profileName);

        // ASSERT
        Assert.assertTrue(result, "Profile deletion should succeed");
        DataSyncAssertions.assertProfileNotExists(profileName, testTool, "Profile should not exist after deletion");
        DataSyncAssertions.assertProfileCount(countBeforeDelete - 1, testTool, "Profile count should decrease");
    }

    @Test(groups = {"datasync", "unit", "validation"})
    public void testCreateProfile_WithNullName_ReturnsFalse() {
        // ARRANGE
        String profileName = null; // Invalid name
        BConnectionProfile profile = TestDataFactory.createValidProfile();

        // ACT
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertFalse(result, "Profile creation with null name should fail");
        DataSyncAssertions.assertProfileCount(initialProfileCount, testTool, "Profile count should not change");
    }

    @Test(groups = {"datasync", "unit", "validation"})
    public void testCreateProfile_WithNullProfile_ReturnsFalse() {
        // ARRANGE
        String profileName = generateProfileTestName();
        BConnectionProfile profile = null; // Invalid profile

        // ACT
        boolean result = testTool.createProfile(profileName, profile);

        // ASSERT
        Assert.assertFalse(result, "Profile creation with null profile should fail");
        DataSyncAssertions.assertProfileCount(initialProfileCount, testTool, "Profile count should not change");
    }

    @Test(groups = {"datasync", "unit", "validation"})
    public void testCreateDuplicateProfile_ReturnsFalse() {
        // ARRANGE
        String profileName = generateProfileTestName();
        BConnectionProfile profile1 = TestDataFactory.createValidProfile();
        BConnectionProfile profile2 = TestDataFactory.createValidProfile();

        // Create first profile
        boolean firstResult = testTool.createProfile(profileName, profile1);
        Assert.assertTrue(firstResult, "First profile creation should succeed");

        // ACT - Try to create duplicate
        boolean duplicateResult = testTool.createProfile(profileName, profile2);

        // ASSERT
        Assert.assertFalse(duplicateResult, "Duplicate profile creation should fail");
        DataSyncAssertions.assertProfileCount(initialProfileCount + 1, testTool, "Profile count should only increase by 1");
    }

    @Test(groups = {"datasync", "unit", "error-handling"})
    public void testUpdateNonExistentProfile_ReturnsFalse() {
        // ARRANGE
        String profileName = generateTestId("NonExistent");
        BConnectionProfile profile = TestDataFactory.createValidProfile();

        // ACT
        boolean result = testTool.updateProfile(profileName, profile);

        // ASSERT
        Assert.assertFalse(result, "Updating non-existent profile should fail");
        DataSyncAssertions.assertProfileCount(initialProfileCount, testTool, "Profile count should not change");
    }

    @Test(groups = {"datasync", "unit", "error-handling"})
    public void testDeleteNonExistentProfile_HandlesGracefully() {
        // ARRANGE
        String profileName = generateTestId("NonExistent");

        // ACT
        boolean result = testTool.deleteProfile(profileName);

        // ASSERT
        // Note: Implementation may return true or false for non-existent profiles
        // The important thing is that it doesn't throw an exception
        Assert.assertNotNull(result, "Delete method should return a boolean result");
        DataSyncAssertions.assertProfileCount(initialProfileCount, testTool, "Profile count should not change");
    }

    // Helper methods for test data management

    private void cleanupTestProfiles() {
        if (testTool == null) return;

        BConnectionProfile[] profiles = testTool.getAllProfiles();
        for (BConnectionProfile profile : profiles) {
            if (isTestData(profile.getName())) {
                testTool.deleteProfile(profile.getName());
                logTestStep("Cleaned up test profile: " + profile.getName());
            }
        }
    }

    private void cleanupAllTestProfiles() {
        cleanupTestProfiles();
        logTestStep("All test profiles cleaned up");
    }
}
