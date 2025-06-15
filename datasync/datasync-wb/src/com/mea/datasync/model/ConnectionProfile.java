// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.sys.BAbsTime;

/**
 * ConnectionProfile represents a saved configuration for connecting to
 * an external data source (Excel file) and target Niagara station.
 */
public class ConnectionProfile {
    
    private String name;
    private String sourceType;
    private String sourcePath;
    private String sheetName;
    private String targetHost;
    private String targetUsername;
    private String targetPath;
    private BAbsTime lastSync;
    private SyncStatus status;
    private int componentsCreated;
    private String lastError;
    
    public enum SyncStatus {
        NEVER_SYNCED("Never Synced"),
        SUCCESS("Success"),
        ERROR("Error"),
        IN_PROGRESS("In Progress");
        
        private final String displayName;
        
        SyncStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    public ConnectionProfile(String name, String sourceType, String sourcePath, 
                           String sheetName, String targetHost, String targetUsername, 
                           String targetPath) {
        this.name = name;
        this.sourceType = sourceType;
        this.sourcePath = sourcePath;
        this.sheetName = sheetName;
        this.targetHost = targetHost;
        this.targetUsername = targetUsername;
        this.targetPath = targetPath;
        this.status = SyncStatus.NEVER_SYNCED;
        this.componentsCreated = 0;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
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
    
    public BAbsTime getLastSync() { return lastSync; }
    public void setLastSync(BAbsTime lastSync) { this.lastSync = lastSync; }
    
    public SyncStatus getStatus() { return status; }
    public void setStatus(SyncStatus status) { this.status = status; }
    
    public int getComponentsCreated() { return componentsCreated; }
    public void setComponentsCreated(int componentsCreated) { this.componentsCreated = componentsCreated; }
    
    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }
    
    @Override
    public String toString() {
        return name;
    }
}
