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
            boolean created = profilesDirectory.mkdirs();
            System.out.println("Created profiles directory: " + created + " at " + profilesDirectory.getAbsolutePath());
            if (!created) {
                System.err.println("Failed to create profiles directory!");
            }
        } else {
            System.out.println("Profiles directory already exists: " + profilesDirectory.getAbsolutePath());
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
            System.out.println("JSON content preview: " + jsonString.substring(0, Math.min(100, jsonString.length())));

            // Write JSON to file
            try (FileWriter writer = new FileWriter(profileFile)) {
                writer.write(jsonString);
                writer.flush(); // Ensure data is written
            }

            // Verify file was written
            if (profileFile.exists() && profileFile.length() > 0) {
                System.out.println("Profile saved successfully: " + profileFile.getAbsolutePath() + " (" + profileFile.length() + " bytes)");
            } else {
                System.err.println("Profile file is empty or doesn't exist after save attempt!");
            }
            return true;

        } catch (IOException e) {
            System.err.println("Error saving profile '" + profileName + "': " + e.getMessage());
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

        // Debug each property
        data.sourceType = profile.getSourceType();
        System.out.println("  sourceType: " + data.sourceType);

        data.sourcePath = profile.getSourcePath();
        System.out.println("  sourcePath: " + data.sourcePath);

        data.sheetName = profile.getSheetName();
        System.out.println("  sheetName: " + data.sheetName);

        data.targetHost = profile.getTargetHost();
        System.out.println("  targetHost: " + data.targetHost);

        data.targetUsername = profile.getTargetUsername();
        System.out.println("  targetUsername: " + data.targetUsername);

        data.targetPath = profile.getTargetPath();
        System.out.println("  targetPath: " + data.targetPath);

        data.status = profile.getStatus();
        System.out.println("  status: " + data.status);

        data.componentsCreated = profile.getComponentsCreated();
        System.out.println("  componentsCreated: " + data.componentsCreated);

        data.lastError = profile.getLastError();
        System.out.println("  lastError: " + data.lastError);

        // Convert BAbsTime to ISO string
        if (profile.getLastSync() != null && !profile.getLastSync().isNull()) {
            data.lastSyncTime = profile.getLastSync().toString();
            System.out.println("  lastSyncTime: " + data.lastSyncTime);
        }

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
        if (data.sourcePath != null) profile.setSourcePath(data.sourcePath);
        if (data.sheetName != null) profile.setSheetName(data.sheetName);
        if (data.targetHost != null) profile.setTargetHost(data.targetHost);
        if (data.targetUsername != null) profile.setTargetUsername(data.targetUsername);
        if (data.targetPath != null) profile.setTargetPath(data.targetPath);
        if (data.status != null) profile.setStatus(data.status);
        if (data.componentsCreated != null) profile.setComponentsCreated(data.componentsCreated);
        if (data.lastError != null) profile.setLastError(data.lastError);

        // Convert ISO string back to BAbsTime
        if (data.lastSyncTime != null && !data.lastSyncTime.isEmpty()) {
            try {
                profile.setLastSync(BAbsTime.make(data.lastSyncTime));
            } catch (Exception e) {
                System.err.println("Error parsing lastSyncTime: " + e.getMessage());
            }
        }

        return profile;
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
     * This mirrors the JSON schema structure.
     */
    private static class ProfileData {
        String profileName;
        String sourceType;
        String sourcePath;
        String sheetName;
        String targetHost;
        String targetUsername;
        String targetPath;
        String status;
        Integer componentsCreated;
        String lastError;
        String lastSyncTime;
        // Future: Add sourceConfig, targetNiagaraStation, syncMetadata objects
    }
}
