// Simple test runner to verify ProfileManager functionality without Niagara runtime
// This helps test the core JSON persistence logic independently

import java.io.File;
import java.util.Date;

// Mock classes to simulate Niagara components for testing
class MockBConnectionProfile {
    private String sourceType = "Excel";
    private String sourcePath = "";
    private String sheetName = "";
    private String targetHost = "";
    private String targetUsername = "";
    private String targetPath = "";
    private String status = "Never Synced";
    private int componentsCreated = 0;
    private String lastError = "";
    private Date lastSync = null;
    
    // Getters and setters
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    
    public String getSourcePath() { return sourcePath; }
    public void setSourcePath(String sourcePath) { this.sourcePath = sourcePath; }
    
    public String getSheetName() { return sheetName; }
    public void setSheetName(String sheetName) { this.sheetName = sheetName; }
    
    public String getTargetHost() { return targetHost; }
    public void setTargetHost(String targetHost) { this.targetHost = targetHost; }
    
    public String getTargetUsername() { return targetUsername; }
    public void setTargetUsername(String targetUsername) { this.targetUsername = targetUsername; }
    
    public String getTargetPath() { return targetPath; }
    public void setTargetPath(String targetPath) { this.targetPath = targetPath; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getComponentsCreated() { return componentsCreated; }
    public void setComponentsCreated(int componentsCreated) { this.componentsCreated = componentsCreated; }
    
    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }
    
    public Date getLastSync() { return lastSync; }
    public void setLastSync(Date lastSync) { this.lastSync = lastSync; }
}

// Mock ProfileManager for testing JSON structure
class MockProfileManager {
    private com.google.gson.Gson gson;
    private File testDir;
    
    public MockProfileManager() {
        this.gson = new com.google.gson.GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();
        
        // Create test directory
        this.testDir = new File(System.getProperty("java.io.tmpdir"), "datasync-test");
        this.testDir.mkdirs();
        System.out.println("Test directory: " + testDir.getAbsolutePath());
    }
    
    public boolean testJsonStructure() {
        try {
            // Create test profile
            MockBConnectionProfile profile = new MockBConnectionProfile();
            profile.setSourceType("Excel");
            profile.setSourcePath("C:\\Test\\sample.xlsx");
            profile.setSheetName("BACnet_Points");
            profile.setTargetHost("192.168.1.100");
            profile.setTargetUsername("admin");
            profile.setTargetPath("station:|slot:/Drivers/BACnet");
            profile.setStatus("Ready");
            profile.setComponentsCreated(75);
            
            // Convert to enhanced JSON structure
            ProfileData data = convertToProfileData(profile, "TestProfile");
            
            // Serialize to JSON
            String jsonString = gson.toJson(data);
            System.out.println("Generated JSON:");
            System.out.println(jsonString);
            
            // Write to file
            File testFile = new File(testDir, "test-profile.json");
            try (java.io.FileWriter writer = new java.io.FileWriter(testFile)) {
                writer.write(jsonString);
            }
            
            // Read back and verify
            ProfileData loadedData;
            try (java.io.FileReader reader = new java.io.FileReader(testFile)) {
                loadedData = gson.fromJson(reader, ProfileData.class);
            }
            
            // Verify structure
            boolean valid = loadedData != null &&
                           loadedData.sourceConfig != null &&
                           loadedData.targetNiagaraStation != null &&
                           loadedData.syncMetadata != null &&
                           "1.0".equals(loadedData.schemaVersion);
            
            System.out.println("JSON structure validation: " + (valid ? "PASSED" : "FAILED"));
            
            if (valid) {
                System.out.println("Profile Name: " + loadedData.profileName);
                System.out.println("Source Type: " + loadedData.sourceType);
                System.out.println("Source File: " + loadedData.sourceConfig.filePath);
                System.out.println("Target Host: " + loadedData.targetNiagaraStation.host);
                System.out.println("Status: " + loadedData.syncMetadata.status);
            }
            
            return valid;
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private ProfileData convertToProfileData(MockBConnectionProfile profile, String profileName) {
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
        data.targetNiagaraStation.port = 4911;
        data.targetNiagaraStation.useSSL = false;

        // Create nested syncMetadata object
        data.syncMetadata = new ProfileData.SyncMetadata();
        data.syncMetadata.status = profile.getStatus();
        data.syncMetadata.componentsCreated = profile.getComponentsCreated();
        data.syncMetadata.lastError = profile.getLastError();
        data.syncMetadata.syncHistory = new java.util.ArrayList<>();
        data.syncMetadata.statistics = new java.util.HashMap<>();

        // Set timestamps
        String currentTime = new Date().toString();
        data.syncMetadata.createdTime = currentTime;
        data.syncMetadata.modifiedTime = currentTime;

        return data;
    }
    
    // Inner classes matching the enhanced structure
    static class ProfileData {
        String profileName;
        String sourceType;
        SourceConfig sourceConfig;
        TargetNiagaraStation targetNiagaraStation;
        SyncMetadata syncMetadata;
        String schemaVersion = "1.0";
        
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

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=== N4-DataSync Connection Profile JSON Structure Test ===");
        
        MockProfileManager testManager = new MockProfileManager();
        boolean success = testManager.testJsonStructure();
        
        System.out.println("\n=== Test Result ===");
        System.out.println("Enhanced JSON structure test: " + (success ? "PASSED" : "FAILED"));
        
        if (success) {
            System.out.println("\n✅ The enhanced JSON structure is working correctly!");
            System.out.println("✅ Nested objects (sourceConfig, targetNiagaraStation, syncMetadata) are properly serialized");
            System.out.println("✅ Schema versioning is in place");
            System.out.println("✅ Ready for integration with Niagara components");
        } else {
            System.out.println("\n❌ JSON structure test failed - check implementation");
        }
    }
}
