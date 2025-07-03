package com.mea.datasync.test.data;

import com.mea.datasync.model.BConnectionProfile;
import javax.baja.sys.BAbsTime;
import java.util.UUID;
import java.util.Random;

/**
 * Factory for creating test data objects with various configurations.
 * 
 * This factory provides standardized test data creation methods that ensure:
 * - Consistent test data across all tests
 * - Unique identifiers to prevent test interference
 * - Various data scenarios (valid, invalid, edge cases)
 * - Performance-optimized test data for benchmarks
 * 
 * Usage Examples:
 * - BConnectionProfile profile = TestDataFactory.createValidProfile();
 * - BConnectionProfile invalid = TestDataFactory.createInvalidProfile();
 * - BConnectionProfile performance = TestDataFactory.createProfileForPerformanceTest();
 * 
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
public class TestDataFactory {
    
    // Constants for test data generation
    private static final String[] VALID_SOURCE_TYPES = {"Excel", "CSV", "Database"};
    private static final String[] VALID_SHEET_NAMES = {"Equipment", "Sensors", "Controllers", "Data", "TestSheet"};
    private static final String[] VALID_HOSTS = {"localhost", "192.168.1.100", "192.168.1.101", "test-server"};
    private static final String[] VALID_USERNAMES = {"testuser", "admin", "datasync", "operator"};
    private static final String[] VALID_PATHS = {
        "station:|slot:/Drivers",
        "station:|slot:/Controllers", 
        "station:|slot:/Test",
        "station:|slot:/Equipment"
    };
    private static final String[] VALID_STATUSES = {"Never Synced", "Success", "Error", "In Progress"};
    
    private static final Random random = new Random();
    
    /**
     * Create a valid connection profile with all required fields populated.
     * This is the most commonly used method for creating test profiles.
     * 
     * @return a valid BConnectionProfile for testing
     */
    public static BConnectionProfile createValidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        
        // Basic source configuration
        profile.setSourceType(getRandomElement(VALID_SOURCE_TYPES));
        profile.setSourcePath(generateValidSourcePath());
        profile.setSheetName(getRandomElement(VALID_SHEET_NAMES));
        
        // Target configuration
        profile.setTargetHost(getRandomElement(VALID_HOSTS));
        profile.setTargetUsername(getRandomElement(VALID_USERNAMES));
        profile.setTargetPath(getRandomElement(VALID_PATHS));
        
        // Status and metadata
        profile.setStatus(VALID_STATUSES[0]); // Default to "Never Synced"
        profile.setComponentsCreated(0);
        profile.setLastError("");
        
        return profile;
    }
    
    /**
     * Create an invalid connection profile with missing or invalid data.
     * Used for testing validation logic and error handling.
     * 
     * @return an invalid BConnectionProfile for negative testing
     */
    public static BConnectionProfile createInvalidProfile() {
        BConnectionProfile profile = new BConnectionProfile();
        
        // Intentionally leave required fields null or empty
        profile.setSourceType(""); // Invalid: empty source type
        profile.setSourcePath(null); // Invalid: null source path
        profile.setSheetName(""); // Invalid: empty sheet name
        profile.setTargetHost(""); // Invalid: empty host
        profile.setTargetUsername(null); // Invalid: null username
        profile.setTargetPath(""); // Invalid: empty path
        
        return profile;
    }
    
    /**
     * Create a profile with extremely long field values for boundary testing.
     * Tests system behavior with maximum length inputs.
     * 
     * @return a BConnectionProfile with long field values
     */
    public static BConnectionProfile createProfileWithLongValues() {
        BConnectionProfile profile = createValidProfile();
        
        // Create very long strings for boundary testing
        String longString = createRepeatedString("A", 255);
        String veryLongString = createRepeatedString("B", 1000);
        
        profile.setSourcePath("C:\\Test\\" + longString + ".xlsx");
        profile.setSheetName(longString.substring(0, 100)); // Reasonable sheet name length
        profile.setTargetHost("very-long-hostname-" + longString.substring(0, 50) + ".com");
        profile.setTargetUsername("user_" + longString.substring(0, 20));
        profile.setLastError(veryLongString); // Very long error message
        
        return profile;
    }
    
    /**
     * Create a profile optimized for performance testing.
     * Uses minimal data to reduce serialization overhead.
     * 
     * @return a BConnectionProfile optimized for performance tests
     */
    public static BConnectionProfile createProfileForPerformanceTest() {
        BConnectionProfile profile = new BConnectionProfile();
        
        // Minimal data for fast processing
        profile.setSourceType("Excel");
        profile.setSourcePath("C:\\Test\\Perf_" + System.nanoTime() + ".xlsx");
        profile.setSheetName("Data");
        profile.setTargetHost("localhost");
        profile.setTargetUsername("test");
        profile.setTargetPath("station:|slot:/Test");
        profile.setStatus("Never Synced");
        profile.setComponentsCreated(0);
        
        return profile;
    }
    
    /**
     * Create a profile with special characters for robustness testing.
     * Tests handling of unicode, symbols, and special characters.
     * 
     * @return a BConnectionProfile with special characters
     */
    public static BConnectionProfile createProfileWithSpecialCharacters() {
        BConnectionProfile profile = createValidProfile();
        
        // Include various special characters
        profile.setSourcePath("C:\\Test\\Special_äöü_测试_🚀_" + UUID.randomUUID() + ".xlsx");
        profile.setSheetName("Data_äöü_测试");
        profile.setTargetUsername("user_äöü_测试");
        profile.setLastError("Error with special chars: äöü 测试 🚀 @#$%^&*()");
        
        return profile;
    }
    
    /**
     * Create a profile with null values in optional fields.
     * Tests handling of partially populated profiles.
     * 
     * @return a BConnectionProfile with some null fields
     */
    public static BConnectionProfile createProfileWithNullOptionalFields() {
        BConnectionProfile profile = new BConnectionProfile();
        
        // Set only required fields
        profile.setSourceType("Excel");
        profile.setSourcePath("C:\\Test\\Minimal_" + UUID.randomUUID() + ".xlsx");
        profile.setTargetHost("localhost");
        
        // Leave optional fields null
        profile.setSheetName(null);
        profile.setTargetUsername(null);
        profile.setTargetPath(null);
        profile.setLastError(null);
        
        return profile;
    }
    
    /**
     * Create a profile that simulates a successful sync operation.
     * Used for testing post-sync scenarios.
     * 
     * @return a BConnectionProfile representing a successful sync
     */
    public static BConnectionProfile createSuccessfulSyncProfile() {
        BConnectionProfile profile = createValidProfile();
        
        profile.setStatus("Success");
        profile.setComponentsCreated(random.nextInt(100) + 1); // 1-100 components
        profile.setLastSync(BAbsTime.now()); // Current time
        profile.setLastError(""); // No error for successful sync
        
        return profile;
    }
    
    /**
     * Create a profile that simulates a failed sync operation.
     * Used for testing error handling scenarios.
     * 
     * @return a BConnectionProfile representing a failed sync
     */
    public static BConnectionProfile createFailedSyncProfile() {
        BConnectionProfile profile = createValidProfile();
        
        profile.setStatus("Error");
        profile.setComponentsCreated(0); // No components created due to error
        profile.setLastSync(BAbsTime.now()); // Current time
        profile.setLastError("Connection timeout: Unable to connect to target host");
        
        return profile;
    }
    
    /**
     * Create a profile for concurrency testing.
     * Includes thread-safe unique identifiers.
     * 
     * @param threadId identifier for the thread creating this profile
     * @return a BConnectionProfile for concurrency testing
     */
    public static BConnectionProfile createConcurrencyTestProfile(int threadId) {
        BConnectionProfile profile = createValidProfile();
        
        // Thread-safe unique identifiers
        long timestamp = System.nanoTime();
        profile.setSourcePath("C:\\Test\\Concurrent_T" + threadId + "_" + timestamp + ".xlsx");
        profile.setSheetName("Thread_" + threadId);
        profile.setTargetUsername("user_t" + threadId);
        
        return profile;
    }
    
    /**
     * Create an array of profiles for bulk testing operations.
     * 
     * @param count number of profiles to create
     * @return array of BConnectionProfile objects
     */
    public static BConnectionProfile[] createMultipleProfiles(int count) {
        BConnectionProfile[] profiles = new BConnectionProfile[count];
        
        for (int i = 0; i < count; i++) {
            profiles[i] = createValidProfile();
            // Make each profile unique
            profiles[i].setSourcePath("C:\\Test\\Bulk_" + i + "_" + System.nanoTime() + ".xlsx");
            profiles[i].setSheetName("Data_" + i);
        }
        
        return profiles;
    }
    
    // Helper methods
    
    /**
     * Get a random element from an array.
     */
    private static String getRandomElement(String[] array) {
        return array[random.nextInt(array.length)];
    }

    /**
     * Create a string by repeating a character/string multiple times.
     * Java 8 compatible alternative to String.repeat().
     */
    private static String createRepeatedString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Generate a valid source path with unique identifier.
     */
    private static String generateValidSourcePath() {
        String[] extensions = {".xlsx", ".xls", ".csv"};
        String extension = getRandomElement(extensions);
        return "C:\\Test\\TestData_" + UUID.randomUUID() + extension;
    }
    
    /**
     * Create a profile builder for fluent profile creation.
     * 
     * @return a new ProfileBuilder instance
     */
    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }
    
    /**
     * Builder pattern for creating customized profiles.
     * Provides fluent interface for profile creation.
     */
    public static class ProfileBuilder {
        private BConnectionProfile profile;
        
        public ProfileBuilder() {
            this.profile = new BConnectionProfile();
        }
        
        public ProfileBuilder withSourceType(String sourceType) {
            profile.setSourceType(sourceType);
            return this;
        }
        
        public ProfileBuilder withSourcePath(String sourcePath) {
            profile.setSourcePath(sourcePath);
            return this;
        }
        
        public ProfileBuilder withSheetName(String sheetName) {
            profile.setSheetName(sheetName);
            return this;
        }
        
        public ProfileBuilder withTargetHost(String targetHost) {
            profile.setTargetHost(targetHost);
            return this;
        }
        
        public ProfileBuilder withTargetUsername(String targetUsername) {
            profile.setTargetUsername(targetUsername);
            return this;
        }
        
        public ProfileBuilder withTargetPath(String targetPath) {
            profile.setTargetPath(targetPath);
            return this;
        }
        
        public ProfileBuilder withStatus(String status) {
            profile.setStatus(status);
            return this;
        }
        
        public ProfileBuilder withComponentsCreated(int count) {
            profile.setComponentsCreated(count);
            return this;
        }
        
        public ProfileBuilder withLastError(String error) {
            profile.setLastError(error);
            return this;
        }
        
        public ProfileBuilder asValidProfile() {
            BConnectionProfile valid = createValidProfile();
            // Copy all fields from valid profile
            profile.setSourceType(valid.getSourceType());
            profile.setSourcePath(valid.getSourcePath());
            profile.setSheetName(valid.getSheetName());
            profile.setTargetHost(valid.getTargetHost());
            profile.setTargetUsername(valid.getTargetUsername());
            profile.setTargetPath(valid.getTargetPath());
            profile.setStatus(valid.getStatus());
            profile.setComponentsCreated(valid.getComponentsCreated());
            return this;
        }
        
        public BConnectionProfile build() {
            return profile;
        }
    }
}
