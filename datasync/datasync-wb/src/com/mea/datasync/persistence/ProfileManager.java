// In: com.mea.datasync.persistence
package com.mea.datasync.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mea.datasync.model.BConnectionProfile;

/**
 * ProfileManager handles persistence of connection profiles to JSON files
 * in the Niagara shared user directory.
 */
public class ProfileManager {

    private static final String PROFILES_DIR = "datasync/profiles";
    private static final String PROFILE_EXTENSION = ".json";

    static {
        System.out.println("ProfileManager class loaded!");
    }
    
    private final File profilesDirectory;
    private final Gson gson;
    
    /**
     * Constructor initializes the profiles directory and JSON serializer.
     */
    public ProfileManager() {
        // Get the Niagara shared user home directory
        File sharedUserHome = null;
        try {
            sharedUserHome = Sys.getNiagaraSharedUserHome();
            System.out.println("Niagara Shared User Home: " + sharedUserHome.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error getting Niagara shared user home: " + e.getMessage());
            // Fallback to a reasonable default
            String userHome = System.getProperty("user.home");
            sharedUserHome = new File(userHome, "Niagara4.13/OptimizerSupervisor/shared");
            System.out.println("Using fallback directory: " + sharedUserHome.getAbsolutePath());
        }

        this.profilesDirectory = new File(sharedUserHome, PROFILES_DIR);
        System.out.println("Profiles Directory: " + profilesDirectory.getAbsolutePath());

        // Ensure the profiles directory exists
        if (!profilesDirectory.exists()) {
            System.out.println("Profiles directory doesn't exist, attempting to create: " + profilesDirectory.getAbsolutePath());

            // Try to create parent directories first
            File parent = profilesDirectory.getParentFile();
            if (parent != null && !parent.exists()) {
                System.out.println("Creating parent directories: " + parent.getAbsolutePath());
                boolean parentCreated = parent.mkdirs();
                System.out.println("Parent directories created: " + parentCreated);
            }

            boolean created = profilesDirectory.mkdirs();
            System.out.println("Created profiles directory: " + created + " at " + profilesDirectory.getAbsolutePath());
            if (!created) {
                System.err.println("CRITICAL: Failed to create profiles directory!");
                System.err.println("  Directory: " + profilesDirectory.getAbsolutePath());
                System.err.println("  Parent exists: " + (parent != null ? parent.exists() : "N/A"));
                System.err.println("  Parent writable: " + (parent != null ? parent.canWrite() : "N/A"));
            }
        } else {
            System.out.println("Profiles directory already exists: " + profilesDirectory.getAbsolutePath());
            System.out.println("  Can read: " + profilesDirectory.canRead());
            System.out.println("  Can write: " + profilesDirectory.canWrite());
        }

        // Configure Gson for pretty printing and date handling
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();
    }
    
    /**
     * Save a connection profile to a JSON file.
     * @param profile The profile to save
     * @param profileName The name to use for the profile
     * @return true if saved successfully, false otherwise
     */
    public boolean saveProfile(BConnectionProfile profile, String profileName) {
        try {
            System.out.println("Attempting to save profile: " + profileName);

            // Validate inputs
            if (profile == null) {
                System.err.println("ERROR: Profile is null");
                return false;
            }
            if (profileName == null || profileName.trim().isEmpty()) {
                System.err.println("ERROR: Profile name is null or empty");
                return false;
            }

            // Ensure directory exists and is writable
            if (!profilesDirectory.exists()) {
                System.out.println("Profiles directory doesn't exist, creating...");
                boolean created = profilesDirectory.mkdirs();
                if (!created) {
                    System.err.println("ERROR: Failed to create profiles directory: " + profilesDirectory.getAbsolutePath());
                    return false;
                }
            }

            if (!profilesDirectory.canWrite()) {
                System.err.println("ERROR: Cannot write to profiles directory: " + profilesDirectory.getAbsolutePath());
                return false;
            }

            // Create filename from profile name (sanitized)
            String filename = sanitizeFilename(profileName) + PROFILE_EXTENSION;
            File profileFile = new File(profilesDirectory, filename);
            System.out.println("Profile file path: " + profileFile.getAbsolutePath());

            // Convert BConnectionProfile to JSON-friendly object
            ProfileData profileData = convertToProfileData(profile, profileName);

            // Debug: Check if profileData has content
            System.out.println("ProfileData created, checking content...");
            System.out.println("  profileData.profileName: " + profileData.profileName);
            System.out.println("  profileData.sourceType: " + profileData.sourceType);

            // Convert to JSON string first to debug
            String jsonString = gson.toJson(profileData);
            System.out.println("JSON string length: " + jsonString.length());
            System.out.println("JSON content preview: " + jsonString.substring(0, Math.min(200, jsonString.length())));

            // Write JSON to file with better error handling
            try (FileWriter writer = new FileWriter(profileFile, false)) { // false = overwrite
                writer.write(jsonString);
                writer.flush(); // Ensure data is written
            } catch (IOException writeEx) {
                System.err.println("ERROR: Failed to write to file: " + writeEx.getMessage());
                throw writeEx;
            }

            // Verify file was written
            if (profileFile.exists() && profileFile.length() > 0) {
                System.out.println("Profile saved successfully: " + profileFile.getAbsolutePath() + " (" + profileFile.length() + " bytes)");
                return true;
            } else {
                System.err.println("ERROR: Profile file is empty or doesn't exist after save attempt!");
                return false;
            }

        } catch (Exception e) {
            // TODO: ERROR-HANDLING-001 - Replace generic exception handling (Issue #3)
            // Should use specific exceptions and proper logging framework
            System.err.println("ERROR: Exception saving profile '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Load a connection profile from a JSON file.
     * @param profileName The name of the profile to load
     * @return The loaded profile, or null if not found or error
     */
    public BConnectionProfile loadProfile(String profileName) {
        try {
            String filename = sanitizeFilename(profileName) + PROFILE_EXTENSION;
            File profileFile = new File(profilesDirectory, filename);
            
            if (!profileFile.exists()) {
                System.err.println("Profile file not found: " + profileFile.getAbsolutePath());
                return null;
            }
            
            // Read JSON from file
            ProfileData profileData;
            try (FileReader reader = new FileReader(profileFile)) {
                profileData = gson.fromJson(reader, ProfileData.class);
            }
            
            // Convert to BConnectionProfile
            return convertFromProfileData(profileData);
            
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error loading profile: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * List all available profile names.
     * @return List of profile names
     */
    public List<String> listProfiles() {
        List<String> profileNames = new ArrayList<>();

        System.out.println("Listing profiles in directory: " + profilesDirectory.getAbsolutePath());

        File[] files = profilesDirectory.listFiles((dir, name) ->
            name.toLowerCase().endsWith(PROFILE_EXTENSION));

        if (files != null) {
            System.out.println("Found " + files.length + " JSON files");
            for (File file : files) {
                String name = file.getName();
                System.out.println("  File: " + name + " (size: " + file.length() + " bytes)");
                // Remove extension
                String profileName = name.substring(0, name.length() - PROFILE_EXTENSION.length());
                profileNames.add(profileName);
                System.out.println("  Profile name: " + profileName);
            }
        } else {
            System.out.println("No files found or directory listing failed");
        }

        return profileNames;
    }
    
    /**
     * Delete a profile file.
     * @param profileName The name of the profile to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteProfile(String profileName) {
        try {
            String filename = sanitizeFilename(profileName) + PROFILE_EXTENSION;
            File profileFile = new File(profilesDirectory, filename);
            
            if (profileFile.exists()) {
                boolean deleted = profileFile.delete();
                if (deleted) {
                    System.out.println("Profile deleted: " + profileFile.getAbsolutePath());
                }
                return deleted;
            }
            return false;
            
        } catch (Exception e) {
            System.err.println("Error deleting profile: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if a profile exists.
     * @param profileName The name of the profile to check
     * @return true if the profile exists, false otherwise
     */
    public boolean profileExists(String profileName) {
        String filename = sanitizeFilename(profileName) + PROFILE_EXTENSION;
        File profileFile = new File(profilesDirectory, filename);
        return profileFile.exists();
    }


    
    /**
     * Get the profiles directory path.
     * @return The profiles directory
     */
    public File getProfilesDirectory() {
        return profilesDirectory;
    }
    
    /**
     * Sanitize a filename by removing invalid characters.
     * @param filename The original filename
     * @return A sanitized filename safe for filesystem use
     */
    private String sanitizeFilename(String filename) {
        if (filename == null) return "unnamed";

        // Replace invalid characters with underscores, but keep spaces as spaces
        String sanitized = filename.replaceAll("[<>:\"/\\\\|?*]", "_")
                                  .trim();

        // Remove leading/trailing underscores that might have been created
        sanitized = sanitized.replaceAll("^_+|_+$", "");

        // If the result is empty, use a default name
        if (sanitized.isEmpty()) {
            sanitized = "unnamed";
        }

        return sanitized;
    }
    
    /**
     * Convert BConnectionProfile to JSON-serializable ProfileData.
     */
    private ProfileData convertToProfileData(BConnectionProfile profile, String profileName) {
        System.out.println("Converting profile to JSON data: " + profileName);

        ProfileData data = new ProfileData();
        data.profileName = profileName;
        data.sourceType = profile.getSourceType();

        // Create nested sourceConfig object
        data.sourceConfig = new ProfileData.SourceConfig();
        data.sourceConfig.filePath = profile.getSourcePath();
        data.sourceConfig.sheetName = profile.getSheetName();
        data.sourceConfig.additionalProperties = new java.util.HashMap<>();

        // Create nested targetNiagaraStation object
        data.targetNiagaraStation = new ProfileData.TargetNiagaraStation();
        data.targetNiagaraStation.host = profile.getTargetHost();
        data.targetNiagaraStation.username = profile.getTargetUsername();
        data.targetNiagaraStation.basePath = profile.getTargetPath();
        data.targetNiagaraStation.port = 4911; // Default Niagara port
        data.targetNiagaraStation.useSSL = false; // Default

        // Create nested syncMetadata object
        data.syncMetadata = new ProfileData.SyncMetadata();
        data.syncMetadata.status = profile.getStatus();
        data.syncMetadata.componentsCreated = profile.getComponentsCreated();
        data.syncMetadata.lastError = profile.getLastError();
        data.syncMetadata.syncHistory = new java.util.ArrayList<>();
        data.syncMetadata.statistics = new java.util.HashMap<>();

        // Convert BAbsTime to ISO string
        if (profile.getLastSync() != null && !profile.getLastSync().isNull()) {
            data.syncMetadata.lastSyncTime = profile.getLastSync().toString();
        }

        // Set timestamps
        String currentTime = new java.util.Date().toString();
        if (data.syncMetadata.createdTime == null) {
            data.syncMetadata.createdTime = currentTime;
        }
        data.syncMetadata.modifiedTime = currentTime;

        System.out.println("Profile data conversion complete");
        return data;
    }
    
    /**
     * Convert ProfileData to BConnectionProfile.
     */
    private BConnectionProfile convertFromProfileData(ProfileData data) {
        BConnectionProfile profile = new BConnectionProfile();

        // Note: profileName will be set as the slot name when adding to parent
        if (data.sourceType != null) profile.setSourceType(data.sourceType);

        // Extract from nested sourceConfig
        if (data.sourceConfig != null) {
            if (data.sourceConfig.filePath != null) profile.setSourcePath(data.sourceConfig.filePath);
            if (data.sourceConfig.sheetName != null) profile.setSheetName(data.sourceConfig.sheetName);
        }

        // Extract from nested targetNiagaraStation
        if (data.targetNiagaraStation != null) {
            if (data.targetNiagaraStation.host != null) profile.setTargetHost(data.targetNiagaraStation.host);
            if (data.targetNiagaraStation.username != null) profile.setTargetUsername(data.targetNiagaraStation.username);
            if (data.targetNiagaraStation.basePath != null) profile.setTargetPath(data.targetNiagaraStation.basePath);
        }

        // Extract from nested syncMetadata
        if (data.syncMetadata != null) {
            if (data.syncMetadata.status != null) profile.setStatus(data.syncMetadata.status);
            if (data.syncMetadata.componentsCreated != null) profile.setComponentsCreated(data.syncMetadata.componentsCreated);
            if (data.syncMetadata.lastError != null) profile.setLastError(data.syncMetadata.lastError);

            // Convert ISO string back to BAbsTime
            if (data.syncMetadata.lastSyncTime != null && !data.syncMetadata.lastSyncTime.isEmpty()) {
                try {
                    profile.setLastSync(BAbsTime.make(data.syncMetadata.lastSyncTime));
                } catch (Exception e) {
                    System.err.println("Error parsing lastSyncTime: " + e.getMessage());
                }
            }
        }

        return profile;
    }

    /**
     * Diagnostic method to check ProfileManager setup and permissions.
     * @return diagnostic information as a string
     */
    public String diagnose() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ProfileManager Diagnostic ===\n");

        // Check directory
        sb.append("Profiles Directory: ").append(profilesDirectory.getAbsolutePath()).append("\n");
        sb.append("Directory exists: ").append(profilesDirectory.exists()).append("\n");
        sb.append("Directory is directory: ").append(profilesDirectory.isDirectory()).append("\n");
        sb.append("Directory can read: ").append(profilesDirectory.canRead()).append("\n");
        sb.append("Directory can write: ").append(profilesDirectory.canWrite()).append("\n");

        // Check parent directory
        File parent = profilesDirectory.getParentFile();
        if (parent != null) {
            sb.append("Parent directory: ").append(parent.getAbsolutePath()).append("\n");
            sb.append("Parent exists: ").append(parent.exists()).append("\n");
            sb.append("Parent can write: ").append(parent.canWrite()).append("\n");
        }

        // List existing files
        File[] files = profilesDirectory.listFiles();
        if (files != null) {
            sb.append("Files in directory: ").append(files.length).append("\n");
            for (File file : files) {
                sb.append("  - ").append(file.getName()).append(" (").append(file.length()).append(" bytes)\n");
            }
        } else {
            sb.append("Cannot list files in directory\n");
        }

        return sb.toString();
    }

    /**
     * Simple test method that doesn't require BConnectionProfile.
     * Tests basic file writing capabilities.
     * @return true if basic file operations work
     */
    public boolean testBasicFileOperations() {
        try {
            System.out.println("Testing basic file operations...");

            // Test writing a simple text file
            File testFile = new File(profilesDirectory, "test_file.txt");
            String testContent = "Test content: " + new java.util.Date();

            try (FileWriter writer = new FileWriter(testFile)) {
                writer.write(testContent);
                writer.flush();
            }

            // Verify file was written
            if (testFile.exists() && testFile.length() > 0) {
                System.out.println("Basic file write successful: " + testFile.getAbsolutePath());

                // Try to read it back
                try (java.io.FileReader reader = new java.io.FileReader(testFile)) {
                    char[] buffer = new char[(int) testFile.length()];
                    int charsRead = reader.read(buffer);
                    String readContent = new String(buffer, 0, charsRead);

                    if (testContent.equals(readContent)) {
                        System.out.println("Basic file read successful");

                        // Clean up
                        boolean deleted = testFile.delete();
                        System.out.println("Test file cleanup: " + deleted);

                        return true;
                    } else {
                        System.err.println("File content mismatch!");
                        return false;
                    }
                }
            } else {
                System.err.println("Basic file write failed!");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error in basic file operations test: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Test method to create a simple profile and save it.
     * This helps debug the JSON writing process.
     */
    public boolean testSaveProfile() {
        try {
            System.out.println("Testing profile save...");

            // Create a simple test profile
            BConnectionProfile testProfile = new BConnectionProfile();

            // Set values and immediately verify them
            testProfile.setSourceType("Excel");
            System.out.println("Set sourceType to 'Excel', got: '" + testProfile.getSourceType() + "'");

            testProfile.setSourcePath("C:\\Test\\test.xlsx");
            System.out.println("Set sourcePath to 'C:\\Test\\test.xlsx', got: '" + testProfile.getSourcePath() + "'");

            testProfile.setSheetName("TestSheet");
            System.out.println("Set sheetName to 'TestSheet', got: '" + testProfile.getSheetName() + "'");

            testProfile.setTargetHost("localhost");
            System.out.println("Set targetHost to 'localhost', got: '" + testProfile.getTargetHost() + "'");

            testProfile.setTargetPath("station:|slot:/Test");
            System.out.println("Set targetPath to 'station:|slot:/Test', got: '" + testProfile.getTargetPath() + "'");

            testProfile.setStatus("Test");
            System.out.println("Set status to 'Test', got: '" + testProfile.getStatus() + "'");

            testProfile.setComponentsCreated(42);
            System.out.println("Set componentsCreated to 42, got: " + testProfile.getComponentsCreated());

            // Save it
            boolean result = saveProfile(testProfile, "TestProfile");
            System.out.println("Test save result: " + result);

            return result;

        } catch (Exception e) {
            System.err.println("Error in test save: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inner class for JSON serialization.
     * This mirrors the JSON schema structure with full nested objects.
     */
    private static class ProfileData {
        String profileName;
        String sourceType;
        SourceConfig sourceConfig;
        TargetNiagaraStation targetNiagaraStation;
        SyncMetadata syncMetadata;
        String schemaVersion = "1.0";

        // Nested configuration objects
        static class SourceConfig {
            String filePath;
            String sheetName;
            String sheetId;
            String apiToken;
            String jdbcUrl;
            String dbUser;
            String dbPassword;
            java.util.Map<String, String> additionalProperties;
        }

        static class TargetNiagaraStation {
            String host;
            String username;
            // TODO: SECURITY-001 - Implement password encryption (Issue #1)
            // Currently storing passwords in plain text - major security vulnerability
            String password;
            String basePath;
            Integer port;
            Boolean useSSL;
        }

        static class SyncMetadata {
            String status;
            Integer componentsCreated;
            String lastError;
            String lastSyncTime;
            String createdTime;
            String modifiedTime;
            java.util.List<String> syncHistory;
            java.util.Map<String, Object> statistics;
        }
    }
}
