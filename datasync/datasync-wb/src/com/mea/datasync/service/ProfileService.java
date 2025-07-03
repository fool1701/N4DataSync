// In: com.mea.datasync.service
package com.mea.datasync.service;

import java.util.List;
import java.util.ArrayList;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import com.mea.datasync.model.BConnectionProfile;
import com.mea.datasync.persistence.ProfileManager;

/**
 * ProfileService provides centralized management of Connection Profiles.
 * This service coordinates between the component tree (runtime) and JSON persistence (storage).
 * 
 * Design Pattern: Service Layer + Repository Pattern
 * - Encapsulates business logic for profile management
 * - Coordinates between different persistence mechanisms
 * - Provides clean API for UI components
 */
public class ProfileService {
    
    private final BComponent profileContainer;
    private final ProfileManager persistenceManager;
    
    /**
     * Create a ProfileService for the given container component.
     * @param container The component that will hold profile children (typically BDataSyncTool)
     */
    public ProfileService(BComponent container) {
        this.profileContainer = container;
        this.persistenceManager = new ProfileManager();
    }
    
    /**
     * Initialize profiles - load from JSON and sync to component tree.
     * Call this during tool startup.
     */
    public void initializeProfiles() {
        try {
            System.out.println("ProfileService: Initializing profiles");
            
            // Check if profiles already exist in component tree
            BConnectionProfile[] existing = getProfilesFromComponentTree();
            if (existing.length > 0) {
                System.out.println("ProfileService: Found " + existing.length + " existing profiles in component tree");
                // Ensure they're also saved to JSON for portability
                syncComponentTreeToJson();
                return;
            }
            
            // Load from JSON storage
            List<String> jsonProfiles = persistenceManager.listProfiles();
            System.out.println("ProfileService: Found " + jsonProfiles.size() + " profiles in JSON storage");
            
            if (!jsonProfiles.isEmpty()) {
                // Load from JSON and add to component tree
                syncJsonToComponentTree();
            } else {
                // Create initial sample profiles
                createInitialSampleProfiles();
            }
            
            System.out.println("ProfileService: Profile initialization complete");
            
        } catch (Exception e) {
            System.err.println("ProfileService: Error initializing profiles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create a new profile and add it to both component tree and JSON storage.
     * @param profileName Unique name for the profile
     * @param profile The profile to add
     * @return true if successful
     */
    public boolean createProfile(String profileName, BConnectionProfile profile) {
        try {
            System.out.println("ProfileService: Creating profile: " + profileName);
            
            // Validate inputs
            if (profileName == null || profileName.trim().isEmpty()) {
                throw new IllegalArgumentException("Profile name cannot be empty");
            }
            if (profile == null) {
                throw new IllegalArgumentException("Profile cannot be null");
            }
            
            // Check if profile already exists
            if (profileExists(profileName)) {
                System.err.println("ProfileService: Profile already exists: " + profileName);
                return false;
            }
            
            // Add to component tree (this triggers childParented events)
            String componentName = sanitizeComponentName(profileName);
            profileContainer.add(componentName, profile);
            
            // Save to JSON storage
            boolean saved = persistenceManager.saveProfile(profile, profileName);
            if (!saved) {
                // Rollback component tree change
                profileContainer.remove(componentName);
                System.err.println("ProfileService: Failed to save profile to JSON, rolled back component tree");
                return false;
            }
            
            System.out.println("ProfileService: Successfully created profile: " + profileName);
            return true;
            
        } catch (Exception e) {
            System.err.println("ProfileService: Error creating profile '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update an existing profile in both component tree and JSON storage.
     * @param profileName Name of the profile to update
     * @param profile Updated profile data
     * @return true if successful
     */
    public boolean updateProfile(String profileName, BConnectionProfile profile) {
        try {
            System.out.println("ProfileService: Updating profile: " + profileName);
            
            if (!profileExists(profileName)) {
                System.err.println("ProfileService: Profile does not exist: " + profileName);
                return false;
            }
            
            // Update in component tree
            String componentName = sanitizeComponentName(profileName);
            BValue existingValue = profileContainer.get(componentName);
            if (existingValue instanceof BConnectionProfile) {
                // Copy properties from updated profile to existing component
                copyProfileProperties(profile, (BConnectionProfile) existingValue);
            }
            
            // Save to JSON storage
            boolean saved = persistenceManager.saveProfile(profile, profileName);
            if (!saved) {
                System.err.println("ProfileService: Failed to save updated profile to JSON");
                return false;
            }
            
            System.out.println("ProfileService: Successfully updated profile: " + profileName);
            return true;
            
        } catch (Exception e) {
            System.err.println("ProfileService: Error updating profile '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a profile from both component tree and JSON storage.
     * @param profileName Name of the profile to delete
     * @return true if successful
     */
    public boolean deleteProfile(String profileName) {
        try {
            System.out.println("ProfileService: Deleting profile: " + profileName);
            
            // Remove from component tree
            String componentName = sanitizeComponentName(profileName);
            BValue existingValue = profileContainer.get(componentName);
            if (existingValue instanceof BComponent) {
                profileContainer.remove(componentName);
            }
            
            // Remove from JSON storage
            boolean deleted = persistenceManager.deleteProfile(profileName);
            
            System.out.println("ProfileService: Profile deletion result: " + deleted);
            return deleted;
            
        } catch (Exception e) {
            System.err.println("ProfileService: Error deleting profile '" + profileName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all profiles from the component tree.
     * @return Array of connection profiles
     */
    public BConnectionProfile[] getAllProfiles() {
        return getProfilesFromComponentTree();
    }
    
    /**
     * Check if a profile exists.
     * @param profileName Name to check
     * @return true if profile exists
     */
    public boolean profileExists(String profileName) {
        String componentName = sanitizeComponentName(profileName);
        BValue value = profileContainer.get(componentName);
        return value instanceof BConnectionProfile;
    }
    
    /**
     * Get profile count.
     * @return Number of profiles
     */
    public int getProfileCount() {
        return getProfilesFromComponentTree().length;
    }
    
    // Private helper methods
    
    private BConnectionProfile[] getProfilesFromComponentTree() {
        return profileContainer.getChildren(BConnectionProfile.class);
    }
    
    private void syncJsonToComponentTree() {
        List<String> profileNames = persistenceManager.listProfiles();
        for (String profileName : profileNames) {
            BConnectionProfile profile = persistenceManager.loadProfile(profileName);
            if (profile != null) {
                String componentName = sanitizeComponentName(profileName);
                profileContainer.add(componentName, profile);
                System.out.println("ProfileService: Loaded profile from JSON: " + profileName);
            }
        }
    }
    
    private void syncComponentTreeToJson() {
        BConnectionProfile[] profiles = getProfilesFromComponentTree();
        for (BConnectionProfile profile : profiles) {
            String profileName = profile.getName();
            persistenceManager.saveProfile(profile, profileName);
            System.out.println("ProfileService: Saved profile to JSON: " + profileName);
        }
    }
    
    private void createInitialSampleProfiles() {
        System.out.println("ProfileService: Creating initial sample profiles");
        
        // Sample Profile 1
        BConnectionProfile profile1 = new BConnectionProfile();
        profile1.setSourceType("Excel");
        profile1.setSourcePath("C:\\Data\\BuildingA_HVAC.xlsx");
        profile1.setSheetName("Equipment");
        profile1.setTargetHost("192.168.1.100");
        profile1.setTargetPath("station:|slot:/Drivers");
        profile1.setStatus("Success");
        profile1.setComponentsCreated(45);
        createProfile("Building A HVAC", profile1);
        
        // Sample Profile 2
        BConnectionProfile profile2 = new BConnectionProfile();
        profile2.setSourceType("Excel");
        profile2.setSourcePath("C:\\Data\\Lighting.xlsx");
        profile2.setSheetName("Fixtures");
        profile2.setTargetHost("192.168.1.101");
        profile2.setTargetPath("station:|slot:/Drivers/Lighting");
        profile2.setStatus("Error");
        profile2.setComponentsCreated(12);
        profile2.setLastError("Connection timeout");
        createProfile("Lighting System", profile2);
        
        System.out.println("ProfileService: Initial sample profiles created");
    }
    
    private void copyProfileProperties(BConnectionProfile source, BConnectionProfile target) {
        target.setSourceType(source.getSourceType());
        target.setSourcePath(source.getSourcePath());
        target.setSheetName(source.getSheetName());
        target.setTargetHost(source.getTargetHost());
        target.setTargetUsername(source.getTargetUsername());
        target.setTargetPath(source.getTargetPath());
        target.setStatus(source.getStatus());
        target.setLastSync(source.getLastSync());
        target.setComponentsCreated(source.getComponentsCreated());
        target.setLastError(source.getLastError());
    }
    
    private String sanitizeComponentName(String name) {
        if (name == null) return "unnamed";
        return name.replaceAll("[^a-zA-Z0-9_]", "_").replaceAll("^_+|_+$", "");
    }
}
