// In: com.mea.datasync.test
package com.mea.datasync.test;

import java.io.File;
import java.util.List;

import com.mea.datasync.model.BConnectionProfile;
import com.mea.datasync.persistence.ProfileManager;

/**
 * ProfileManagerStandaloneTest tests the ProfileManager functionality
 * without requiring the full Niagara runtime environment.
 * 
 * This is a simple Java class that can be run directly to debug
 * persistence issues.
 */
public class ProfileManagerStandaloneTest {

    public static void main(String[] args) {
        System.out.println("=== ProfileManager Standalone Test ===");
        
        ProfileManagerStandaloneTest test = new ProfileManagerStandaloneTest();
        try {
            test.runTests();
            System.out.println("=== All tests completed successfully ===");
        } catch (Exception e) {
            System.err.println("=== Test failed with exception ===");
            e.printStackTrace();
        }
    }
    
    public void runTests() throws Exception {
        System.out.println("\n1. Testing ProfileManager initialization...");
        testProfileManagerInitialization();
        
        System.out.println("\n2. Testing basic file operations...");
        testBasicFileOperations();

        System.out.println("\n3. Testing directory operations...");
        testDirectoryOperations();

        System.out.println("\n4. Testing profile save/load...");
        testProfileSaveLoad();
        
        System.out.println("\n5. Testing profile listing...");
        testProfileListing();

        System.out.println("\n6. Testing ProfileManager testSaveProfile method...");
        testProfileManagerTestSaveProfile();
    }
    
    private void testProfileManagerInitialization() {
        System.out.println("Creating ProfileManager instance...");
        ProfileManager pm = new ProfileManager();

        // Use the new diagnose method
        System.out.println(pm.diagnose());
    }

    private void testBasicFileOperations() {
        ProfileManager pm = new ProfileManager();

        System.out.println("Testing basic file operations...");
        boolean result = pm.testBasicFileOperations();
        System.out.println("Basic file operations result: " + result);

        if (!result) {
            System.err.println("ERROR: Basic file operations failed!");
        }
    }

    private void testDirectoryOperations() {
        ProfileManager pm = new ProfileManager();
        
        List<String> profiles = pm.listProfiles();
        System.out.println("Found " + profiles.size() + " existing profiles:");
        for (String profile : profiles) {
            System.out.println("  - " + profile);
        }
    }
    
    private void testProfileSaveLoad() {
        System.out.println("Creating test profile...");
        ProfileManager pm = new ProfileManager();
        
        // Create a test profile
        BConnectionProfile testProfile = new BConnectionProfile();
        testProfile.setSourceType("Excel");
        testProfile.setSourcePath("C:\\Test\\standalone_test.xlsx");
        testProfile.setSheetName("TestSheet");
        testProfile.setTargetHost("localhost");
        testProfile.setTargetPath("station:|slot:/Test");
        testProfile.setStatus("Test Status");
        testProfile.setComponentsCreated(123);
        testProfile.setLastError("Test Error");
        
        String profileName = "StandaloneTest_" + System.currentTimeMillis();
        System.out.println("Saving profile: " + profileName);
        
        // Save the profile
        boolean saveResult = pm.saveProfile(testProfile, profileName);
        System.out.println("Save result: " + saveResult);
        
        if (saveResult) {
            // Check if profile exists
            boolean exists = pm.profileExists(profileName);
            System.out.println("Profile exists after save: " + exists);
            
            // Try to load it back
            System.out.println("Loading profile back...");
            BConnectionProfile loadedProfile = pm.loadProfile(profileName);
            
            if (loadedProfile != null) {
                System.out.println("Profile loaded successfully!");
                System.out.println("  Source Type: " + loadedProfile.getSourceType());
                System.out.println("  Source Path: " + loadedProfile.getSourcePath());
                System.out.println("  Sheet Name: " + loadedProfile.getSheetName());
                System.out.println("  Target Host: " + loadedProfile.getTargetHost());
                System.out.println("  Target Path: " + loadedProfile.getTargetPath());
                System.out.println("  Status: " + loadedProfile.getStatus());
                System.out.println("  Components Created: " + loadedProfile.getComponentsCreated());
                System.out.println("  Last Error: " + loadedProfile.getLastError());
            } else {
                System.err.println("ERROR: Failed to load profile back!");
            }
            
            // Clean up
            System.out.println("Cleaning up test profile...");
            boolean deleteResult = pm.deleteProfile(profileName);
            System.out.println("Delete result: " + deleteResult);
        } else {
            System.err.println("ERROR: Failed to save profile!");
        }
    }
    
    private void testProfileListing() {
        ProfileManager pm = new ProfileManager();
        
        System.out.println("Listing all profiles after test...");
        List<String> profiles = pm.listProfiles();
        System.out.println("Total profiles: " + profiles.size());
        for (String profile : profiles) {
            System.out.println("  - " + profile);
        }
    }
    
    private void testProfileManagerTestSaveProfile() {
        ProfileManager pm = new ProfileManager();

        System.out.println("Running ProfileManager.testSaveProfile()...");
        boolean testSaveResult = pm.testSaveProfile();
        System.out.println("testSaveProfile() result: " + testSaveResult);
        
        if (testSaveResult) {
            // Check if the test profile was created
            boolean exists = pm.profileExists("TestProfile");
            System.out.println("TestProfile exists: " + exists);
            
            if (exists) {
                // Clean up
                boolean deleteResult = pm.deleteProfile("TestProfile");
                System.out.println("TestProfile cleanup result: " + deleteResult);
            }
        }
    }
}
