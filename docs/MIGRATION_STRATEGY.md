# Migration Strategy: From Monolithic to Component-Based Architecture

## **Overview**

This document outlines the migration strategy from the current monolithic `BConnectionProfile` to the new component-based architecture using `BEnhancedConnectionProfile` and the separated component classes.

## **Current State vs Target State**

### **Current Architecture (Monolithic)**
```
BConnectionProfile
├── sourceType (String)
├── sourcePath (String)  
├── sheetName (String)
├── targetHost (String)
├── targetUsername (String)
├── targetPath (String)
├── status (String)
├── lastSync (BAbsTime)
├── componentsCreated (int)
└── lastError (String)
```

### **Target Architecture (Component-Based)**
```
BEnhancedConnectionProfile
├── dataSourceConnection (BDataSourceConnection) [unremovable]
│   ├── sourceType
│   ├── sourcePath
│   ├── connectionName
│   ├── validationStatus
│   └── lastValidated
├── extractionSettings (BDataExtractionSettings) [unremovable]
│   ├── sheetName
│   ├── startRow/endRow
│   ├── hasHeaderRow
│   ├── skipEmptyRows
│   └── extractionMode
├── targetStation1 (BTargetNiagaraStation) [removable]
│   ├── stationName
│   ├── hostAddress
│   ├── port
│   ├── username
│   ├── basePath
│   └── connectionStatus
├── targetStation2 (BTargetNiagaraStation) [removable]
├── status (String)
├── lastSync (BAbsTime)
├── componentsCreated (int)
└── lastError (String)
```

## **Migration Phases**

### **Phase 1: Parallel Implementation (Current)**
- ✅ **COMPLETED**: Created new component classes
  - `BDataSourceConnection`
  - `BDataExtractionSettings` 
  - `BTargetNiagaraStation`
  - `BEnhancedConnectionProfile`
- ✅ **COMPLETED**: Comprehensive unit tests for new components
- ✅ **COMPLETED**: Documentation and patterns

### **Phase 2: Data Migration Utilities**
Create utilities to migrate existing profiles to new format:

```java
public class ProfileMigrationUtility {
  
  /**
   * Migrate a legacy BConnectionProfile to BEnhancedConnectionProfile.
   */
  public static BEnhancedConnectionProfile migrate(BConnectionProfile legacy) {
    BEnhancedConnectionProfile enhanced = new BEnhancedConnectionProfile();
    
    // Migrate data source connection
    BDataSourceConnection connection = enhanced.getDataSourceConnection();
    connection.setSourceType(legacy.getSourceType());
    connection.setSourcePath(legacy.getSourcePath());
    connection.setConnectionName(legacy.getName() + " Connection");
    
    // Migrate extraction settings
    BDataExtractionSettings settings = enhanced.getExtractionSettings();
    settings.setSheetName(legacy.getSheetName());
    settings.setHasHeaderRow(true); // Default assumption
    settings.setSkipEmptyRows(true); // Default assumption
    
    // Migrate target station
    if (legacy.getTargetHost() != null && !legacy.getTargetHost().isEmpty()) {
      BTargetNiagaraStation station = new BTargetNiagaraStation();
      station.setStationName("Primary Station");
      station.setHostAddress(legacy.getTargetHost());
      station.setUsername(legacy.getTargetUsername());
      station.setBasePath(legacy.getTargetPath());
      enhanced.addTargetStation("primaryStation", station);
    }
    
    // Migrate metadata
    enhanced.setStatus(legacy.getStatus());
    enhanced.setLastSync(legacy.getLastSync());
    enhanced.setComponentsCreated(legacy.getComponentsCreated());
    enhanced.setLastError(legacy.getLastError());
    
    return enhanced;
  }
  
  /**
   * Migrate JSON files from legacy to new format.
   */
  public static void migrateJsonFiles() {
    ProfileManager pm = new ProfileManager();
    List<String> profileNames = pm.listProfiles();
    
    for (String name : profileNames) {
      try {
        // Load legacy profile
        BConnectionProfile legacy = pm.loadProfile(name);
        if (legacy != null) {
          // Migrate to new format
          BEnhancedConnectionProfile enhanced = migrate(legacy);
          
          // Save in new format (with backup)
          pm.backupProfile(name);
          pm.saveEnhancedProfile(enhanced, name);
          
          System.out.println("Migrated profile: " + name);
        }
      } catch (Exception e) {
        System.err.println("Failed to migrate profile " + name + ": " + e.getMessage());
      }
    }
  }
}
```

### **Phase 3: UI Updates**
Update views to work with new component structure:

```java
@NiagaraType
@AgentOn(types = "datasync:EnhancedConnectionProfile")
public class BEnhancedProfileView extends BAbstractManager {
  
  // Tabbed interface for different concerns
  // Tab 1: Data Source Connection
  // Tab 2: Extraction Settings  
  // Tab 3: Target Stations
  // Tab 4: Sync Status & History
}
```

### **Phase 4: Tool Integration**
Update `BDataSyncTool` to support both formats during transition:

```java
public class BDataSyncTool extends BWbNavNodeTool {
  
  /**
   * Support both legacy and enhanced profiles during migration.
   */
  @Override
  public boolean isChildLegal(BComponent child) {
    return child instanceof BConnectionProfile ||
           child instanceof BEnhancedConnectionProfile;
  }
  
  /**
   * Migrate profiles on tool startup.
   */
  @Override
  public void started() throws Exception {
    super.started();
    
    // Check if migration is needed
    if (needsMigration()) {
      performMigration();
    }
    
    profileService.initializeProfiles();
  }
}
```

### **Phase 5: Deprecation and Cleanup**
- Mark `BConnectionProfile` as `@Deprecated`
- Add migration warnings in UI
- Remove legacy code after migration period

## **Migration Timeline**

### **Week 1-2: Infrastructure**
- ✅ Create new component classes
- ✅ Implement unit tests
- ✅ Document patterns

### **Week 3-4: Migration Tools**
- [ ] Create `ProfileMigrationUtility`
- [ ] Add JSON format versioning
- [ ] Implement backup/restore functionality
- [ ] Test migration with sample data

### **Week 5-6: UI Updates**
- [ ] Create enhanced profile views
- [ ] Update tool to support both formats
- [ ] Add migration UI prompts
- [ ] Test user workflows

### **Week 7-8: Production Migration**
- [ ] Deploy migration tools
- [ ] Migrate existing installations
- [ ] Monitor for issues
- [ ] Provide user support

### **Week 9-10: Cleanup**
- [ ] Remove legacy code
- [ ] Update documentation
- [ ] Final testing
- [ ] Release notes

## **Risk Mitigation**

### **Data Safety**
- **Automatic Backups**: All profiles backed up before migration
- **Rollback Capability**: Can revert to legacy format if needed
- **Validation**: Verify migrated data integrity
- **Gradual Rollout**: Test with subset of users first

### **Compatibility**
- **Dual Support**: Support both formats during transition
- **Version Detection**: Automatically detect profile format
- **Graceful Degradation**: Handle missing components gracefully
- **Clear Messaging**: Inform users about migration status

### **Performance**
- **Lazy Migration**: Migrate profiles on first access
- **Batch Processing**: Migrate multiple profiles efficiently
- **Progress Tracking**: Show migration progress to users
- **Error Recovery**: Handle partial migration failures

## **Testing Strategy**

### **Unit Tests**
- ✅ Component validation tests
- ✅ Relationship management tests
- [ ] Migration utility tests
- [ ] JSON serialization tests

### **Integration Tests**
- [ ] End-to-end migration tests
- [ ] UI workflow tests
- [ ] Performance tests
- [ ] Compatibility tests

### **User Acceptance Tests**
- [ ] Migration user experience
- [ ] New component workflows
- [ ] Error handling scenarios
- [ ] Documentation accuracy

## **Success Criteria**

### **Technical**
- [ ] 100% successful migration of existing profiles
- [ ] No data loss during migration
- [ ] Performance equal or better than legacy
- [ ] All tests passing

### **User Experience**
- [ ] Intuitive component organization
- [ ] Clear migration messaging
- [ ] Improved workflow efficiency
- [ ] Positive user feedback

### **Maintainability**
- [ ] Clean separation of concerns
- [ ] Extensible architecture
- [ ] Comprehensive documentation
- [ ] Reduced technical debt

## **Rollback Plan**

If migration issues occur:

1. **Immediate**: Stop migration process
2. **Restore**: Revert to backup profiles
3. **Investigate**: Analyze failure causes
4. **Fix**: Address issues in migration code
5. **Retry**: Resume migration with fixes
6. **Communicate**: Keep users informed of status

## **Communication Plan**

### **Development Team**
- Weekly migration status updates
- Technical review sessions
- Code review requirements
- Testing coordination

### **Users**
- Migration announcement
- Progress notifications
- Completion confirmation
- Support documentation

### **Stakeholders**
- Migration timeline updates
- Risk assessment reports
- Success metrics tracking
- Post-migration analysis
