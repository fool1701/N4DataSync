package com.mea.datasync.test.utils;

import com.mea.datasync.model.BConnectionProfile;
import org.testng.Assert;
import java.util.Objects;

/**
 * Custom assertions for DataSync domain objects.
 * 
 * This class provides domain-specific assertion methods that:
 * - Offer more meaningful error messages than generic assertions
 * - Encapsulate complex validation logic
 * - Provide consistent validation across all tests
 * - Support both positive and negative test scenarios
 * 
 * Usage Examples:
 * - DataSyncAssertions.assertProfileEquals(expected, actual, "Profile comparison");
 * - DataSyncAssertions.assertProfileValid(profile, "Profile validation");
 * - DataSyncAssertions.assertProfilePersisted(name, tool, "Persistence check");
 * 
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
public class DataSyncAssertions {
    
    /**
     * Assert that two connection profiles are equal in all relevant fields.
     * Provides detailed comparison with specific field-level error messages.
     * 
     * @param expected the expected profile
     * @param actual the actual profile
     * @param message base message for assertion failures
     */
    public static void assertProfileEquals(BConnectionProfile expected, 
                                         BConnectionProfile actual, 
                                         String message) {
        // Null checks
        Assert.assertNotNull(actual, message + ": Actual profile should not be null");
        Assert.assertNotNull(expected, message + ": Expected profile should not be null");
        
        // Field-by-field comparison with specific error messages
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
        Assert.assertEquals(actual.getStatus(), expected.getStatus(), 
                           message + ": Status mismatch");
        Assert.assertEquals(actual.getComponentsCreated(), expected.getComponentsCreated(), 
                           message + ": Components created count mismatch");
        Assert.assertEquals(actual.getLastError(), expected.getLastError(), 
                           message + ": Last error mismatch");
    }
    
    /**
     * Assert that a connection profile contains valid data.
     * Validates all required fields and business rules.
     * 
     * @param profile the profile to validate
     * @param message base message for assertion failures
     */
    public static void assertProfileValid(BConnectionProfile profile, String message) {
        Assert.assertNotNull(profile, message + ": Profile should not be null");
        
        // Source configuration validation
        Assert.assertNotNull(profile.getSourceType(), message + ": Source type is required");
        Assert.assertFalse(profile.getSourceType().trim().isEmpty(), 
                           message + ": Source type cannot be empty");
        
        Assert.assertNotNull(profile.getSourcePath(), message + ": Source path is required");
        Assert.assertFalse(profile.getSourcePath().trim().isEmpty(), 
                           message + ": Source path cannot be empty");
        
        // Target configuration validation
        Assert.assertNotNull(profile.getTargetHost(), message + ": Target host is required");
        Assert.assertFalse(profile.getTargetHost().trim().isEmpty(), 
                           message + ": Target host cannot be empty");
        
        // Business rule validation
        if (profile.getSourceType().equals("Excel")) {
            Assert.assertTrue(profile.getSourcePath().toLowerCase().endsWith(".xlsx") || 
                             profile.getSourcePath().toLowerCase().endsWith(".xls"),
                             message + ": Excel files must have .xlsx or .xls extension");
        }
        
        // Status validation
        String[] validStatuses = {"Never Synced", "Success", "Error", "In Progress"};
        boolean validStatus = false;
        for (String status : validStatuses) {
            if (Objects.equals(profile.getStatus(), status)) {
                validStatus = true;
                break;
            }
        }
        Assert.assertTrue(validStatus, message + ": Invalid status: " + profile.getStatus());
        
        // Components created should not be negative
        Assert.assertTrue(profile.getComponentsCreated() >= 0, 
                         message + ": Components created cannot be negative");
    }
    
    /**
     * Assert that a profile is invalid (for negative testing).
     * Validates that the profile violates expected business rules.
     * 
     * @param profile the profile to validate as invalid
     * @param message base message for assertion failures
     */
    public static void assertProfileInvalid(BConnectionProfile profile, String message) {
        Assert.assertNotNull(profile, message + ": Profile should not be null for invalid testing");
        
        // Check for at least one invalid condition
        boolean hasInvalidCondition = false;
        
        // Check for null or empty required fields
        if (profile.getSourceType() == null || profile.getSourceType().trim().isEmpty()) {
            hasInvalidCondition = true;
        }
        if (profile.getSourcePath() == null || profile.getSourcePath().trim().isEmpty()) {
            hasInvalidCondition = true;
        }
        if (profile.getTargetHost() == null || profile.getTargetHost().trim().isEmpty()) {
            hasInvalidCondition = true;
        }
        
        // Check for invalid business rules
        if (profile.getComponentsCreated() < 0) {
            hasInvalidCondition = true;
        }
        
        Assert.assertTrue(hasInvalidCondition, 
                         message + ": Profile should have at least one invalid condition");
    }
    
    /**
     * Assert that a profile exists and is properly persisted in the system.
     * Validates both component tree and persistence layer storage.
     * 
     * @param profileName the name of the profile to check
     * @param tool the DataSync tool instance
     * @param message base message for assertion failures
     */
    public static void assertProfilePersisted(String profileName, 
                                             com.mea.datasync.ui.BDataSyncTool tool, 
                                             String message) {
        Assert.assertNotNull(profileName, message + ": Profile name should not be null");
        Assert.assertNotNull(tool, message + ": Tool should not be null");
        
        // Check existence in tool
        Assert.assertTrue(tool.profileExists(profileName), 
                         message + ": Profile '" + profileName + "' should exist in tool");
        
        // Check presence in component tree
        BConnectionProfile[] profiles = tool.getAllProfiles();
        boolean foundInComponentTree = false;
        BConnectionProfile foundProfile = null;
        
        for (BConnectionProfile profile : profiles) {
            if (profileName.equals(profile.getName())) {
                foundInComponentTree = true;
                foundProfile = profile;
                break;
            }
        }
        
        Assert.assertTrue(foundInComponentTree, 
                         message + ": Profile '" + profileName + "' should be in component tree");
        Assert.assertNotNull(foundProfile, 
                           message + ": Found profile should not be null");
        
        // Validate the found profile
        assertProfileValid(foundProfile, message + ": Persisted profile should be valid");
    }
    
    /**
     * Assert that a profile does not exist in the system.
     * Used for testing deletion and negative scenarios.
     * 
     * @param profileName the name of the profile that should not exist
     * @param tool the DataSync tool instance
     * @param message base message for assertion failures
     */
    public static void assertProfileNotExists(String profileName, 
                                            com.mea.datasync.ui.BDataSyncTool tool, 
                                            String message) {
        Assert.assertNotNull(profileName, message + ": Profile name should not be null");
        Assert.assertNotNull(tool, message + ": Tool should not be null");
        
        // Check non-existence in tool
        Assert.assertFalse(tool.profileExists(profileName), 
                          message + ": Profile '" + profileName + "' should not exist in tool");
        
        // Check absence from component tree
        BConnectionProfile[] profiles = tool.getAllProfiles();
        for (BConnectionProfile profile : profiles) {
            Assert.assertNotEquals(profileName, profile.getName(), 
                                 message + ": Profile '" + profileName + "' should not be in component tree");
        }
    }
    
    /**
     * Assert that profile count matches expected value.
     * Useful for testing bulk operations and state changes.
     * 
     * @param expectedCount the expected number of profiles
     * @param tool the DataSync tool instance
     * @param message base message for assertion failures
     */
    public static void assertProfileCount(int expectedCount, 
                                        com.mea.datasync.ui.BDataSyncTool tool, 
                                        String message) {
        Assert.assertNotNull(tool, message + ": Tool should not be null");
        Assert.assertTrue(expectedCount >= 0, message + ": Expected count cannot be negative");
        
        int actualCount = tool.getProfileCount();
        Assert.assertEquals(actualCount, expectedCount, 
                           message + ": Profile count mismatch");
        
        // Verify consistency with getAllProfiles()
        BConnectionProfile[] profiles = tool.getAllProfiles();
        Assert.assertEquals(profiles.length, expectedCount, 
                           message + ": getAllProfiles() count should match getProfileCount()");
    }
    
    /**
     * Assert that a profile has specific status.
     * Useful for testing workflow state transitions.
     * 
     * @param profile the profile to check
     * @param expectedStatus the expected status
     * @param message base message for assertion failures
     */
    public static void assertProfileStatus(BConnectionProfile profile, 
                                         String expectedStatus, 
                                         String message) {
        Assert.assertNotNull(profile, message + ": Profile should not be null");
        Assert.assertNotNull(expectedStatus, message + ": Expected status should not be null");
        
        Assert.assertEquals(profile.getStatus(), expectedStatus, 
                           message + ": Profile status mismatch");
    }
    
    /**
     * Assert that a profile operation completed within expected time.
     * Used for performance testing and validation.
     * 
     * @param startTime operation start time in nanoseconds
     * @param maxDurationMs maximum allowed duration in milliseconds
     * @param operation description of the operation
     * @param message base message for assertion failures
     */
    public static void assertPerformance(long startTime, 
                                       long maxDurationMs, 
                                       String operation, 
                                       String message) {
        long durationMs = (System.nanoTime() - startTime) / 1_000_000;
        Assert.assertTrue(durationMs <= maxDurationMs, 
                         message + ": " + operation + " took " + durationMs + 
                         "ms, expected <= " + maxDurationMs + "ms");
    }
    
    /**
     * Assert that two profile arrays contain the same profiles.
     * Useful for testing bulk operations and data consistency.
     * 
     * @param expected array of expected profiles
     * @param actual array of actual profiles
     * @param message base message for assertion failures
     */
    public static void assertProfileArrayEquals(BConnectionProfile[] expected, 
                                              BConnectionProfile[] actual, 
                                              String message) {
        Assert.assertNotNull(expected, message + ": Expected array should not be null");
        Assert.assertNotNull(actual, message + ": Actual array should not be null");
        
        Assert.assertEquals(actual.length, expected.length, 
                           message + ": Array length mismatch");
        
        // Compare each profile (order-independent comparison)
        for (BConnectionProfile expectedProfile : expected) {
            boolean found = false;
            for (BConnectionProfile actualProfile : actual) {
                if (profilesEqual(expectedProfile, actualProfile)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue(found, message + ": Expected profile not found in actual array: " + 
                            expectedProfile.getName());
        }
    }
    
    /**
     * Helper method to check if two profiles are equal.
     * Used internally for array comparisons.
     */
    private static boolean profilesEqual(BConnectionProfile profile1, BConnectionProfile profile2) {
        if (profile1 == null && profile2 == null) return true;
        if (profile1 == null || profile2 == null) return false;
        
        return Objects.equals(profile1.getSourceType(), profile2.getSourceType()) &&
               Objects.equals(profile1.getSourcePath(), profile2.getSourcePath()) &&
               Objects.equals(profile1.getSheetName(), profile2.getSheetName()) &&
               Objects.equals(profile1.getTargetHost(), profile2.getTargetHost()) &&
               Objects.equals(profile1.getTargetUsername(), profile2.getTargetUsername()) &&
               Objects.equals(profile1.getTargetPath(), profile2.getTargetPath()) &&
               Objects.equals(profile1.getStatus(), profile2.getStatus()) &&
               profile1.getComponentsCreated() == profile2.getComponentsCreated() &&
               Objects.equals(profile1.getLastError(), profile2.getLastError());
    }
}
