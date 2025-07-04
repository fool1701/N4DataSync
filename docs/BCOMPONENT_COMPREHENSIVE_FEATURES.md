# BComponent Comprehensive Features Guide

## Overview

This document provides a complete reference for all the built-in features available when extending `BComponent` in the Niagara framework. Understanding these features is essential for building professional, integrated Niagara applications.

## 1. Agent System - Complete Understanding

### **Two-Part Agent System**

The agent system requires **both** registration AND retrieval to work:

#### **Part 1: Agent Registration** (Declaration)
```xml
<!-- module-include.xml - DECLARES "I can work with X" -->
<type class="com.mea.datasync.ui.BDataSyncProfileView" name="DataSyncProfileView">
  <agent>
    <on type="datasync:DataSyncTool"/>  <!-- "I can work with DataSyncTool" -->
  </agent>
</type>
```

#### **Part 2: Agent Retrieval** (Selection)
```java
// BDataSyncTool.getAgents() - DECIDES "What should be available for me?"
@Override
public AgentList getAgents(Context cx) {
  AgentList agents = Sys.getRegistry().getAgents(BComponent.TYPE.getTypeInfo());
  agents.add("datasync:DataSyncProfileView");  // Include our custom view
  return agents;  // This list populates the dropdown
}
```

#### **How They Work Together:**
1. **Registration**: Views declare they CAN work with certain types
2. **Registry**: Framework stores all registered agents
3. **Retrieval**: Components decide which agents SHOULD be available
4. **Framework**: Matches them up and populates UI dropdowns

#### **For an Agent to Appear:**
✅ **Must be registered** in module-include.xml  
✅ **Must be returned** by target component's `getAgents()`  
✅ **Module must be loaded** and Workbench restarted  
✅ **Permissions must allow** access to the agent

### **Agent Types Available:**
- **Views**: UI components for viewing/editing
- **Tools**: Standalone utilities
- **Exporters**: Data export functionality
- **Importers**: Data import functionality
- **Validators**: Property validation
- **Converters**: Type conversion

## 2. Navigation Integration Features

### **Core Navigation Methods**

#### **A. Basic Navigation Identity**
```java
// Unique identifier within parent
public String getNavName() {
  return getName(); // Usually the component's name property
}

// Display text in nav tree
public String getNavDisplayName(Context cx) {
  return "DataSync Tool (" + getProfileCount() + " profiles)";
}

// Tooltip/description text
public String getNavDescription(Context cx) {
  return "Synchronizes data from external sources to Niagara stations";
}
```

#### **B. Navigation Hierarchy**
```java
// Parent in nav tree
public BINavNode getNavParent() {
  return (BComponent) getParent(); // Automatic parent relationship
}

// Children in nav tree
public BINavNode[] getNavChildren() {
  List<BINavNode> children = new ArrayList<>();
  
  // Add organized folders
  children.add(getProfilesFolder());
  children.add(getTargetStationsFolder());
  
  // Add direct children
  BEnhancedConnectionProfile[] profiles = getProfiles();
  for (BEnhancedConnectionProfile profile : profiles) {
    children.add(profile);
  }
  
  return children.toArray(new BINavNode[0]);
}

// Whether this node has children (for tree expansion)
public boolean hasNavChildren() {
  return getProfileCount() > 0;
}
```

#### **C. Navigation Addressing**
```java
// Absolute navigation address
public BOrd getNavOrd() {
  // Framework automatically constructs: "tool:datasync:DataSyncTool|slot:/"
  BComponentSpace space = getComponentSpace();
  BOrd spaceOrd = space.getAbsoluteOrd();
  SlotPath path = getSlotPath();
  return BOrd.make(spaceOrd, path);
}

// Visual representation
public BIcon getNavIcon() {
  return BIcon.make("module://icons/x16/datasync.png");
}
```

### **Advanced Navigation Features**

#### **A. Dynamic Children**
```java
// Children computed at runtime
public BINavNode[] getNavChildren() {
  List<BINavNode> children = new ArrayList<>();
  
  // Add profiles grouped by status
  for (BEnhancedConnectionProfile profile : getProfiles()) {
    if (profile.getStatus().equals("Active")) {
      children.add(profile);
    }
  }
  
  // Add virtual folders
  if (hasErrorProfiles()) {
    children.add(new ErrorProfilesFolder());
  }
  
  return children.toArray(new BINavNode[0]);
}
```

#### **B. Context-Sensitive Display**
```java
public String getNavDisplayName(Context cx) {
  // Different names based on context
  if (isRunning()) {
    return "DataSync Tool (Running)";
  } else {
    return "DataSync Tool (Stopped)";
  }
}
```

#### **C. Navigation Events**
```java
// Automatically fired when nav structure changes
public void childParented(Property property, BValue newChild, Context context) {
  super.childParented(property, newChild, context);
  
  // Framework automatically updates nav tree
  // No manual refresh needed
}
```

## 3. Persistence System

### **A. Built-in BOG Persistence**

Every BComponent automatically gets:
- **Serialization**: Component state saved to BOG files
- **Deserialization**: Component state restored on startup
- **Property Persistence**: All @NiagaraProperty values saved
- **Child Persistence**: Child components automatically saved

```java
// Automatic - no code needed
// Framework handles save/load of all properties and children
```

### **B. Custom Persistence Hooks**
```java
// Custom serialization
public void encode(DataOutput encoder, Context cx) throws Exception {
  super.encode(encoder, cx);
  
  // Add custom data to BOG file
  encoder.writeUTF(customData);
}

// Custom deserialization  
public void decode(DataInput decoder, Context cx) throws Exception {
  super.decode(decoder, cx);
  
  // Read custom data from BOG file
  customData = decoder.readUTF();
}
```

### **C. External File Persistence**

For JSON files in user directory:

```java
public class BDataSyncTool extends BWbNavNodeTool {
  
  // Get module-specific directory
  private File getDataSyncDirectory() {
    File userShared = new File(System.getProperty("user.home"), ".niagara/shared");
    File moduleDir = new File(userShared, "modules/datasync");
    if (!moduleDir.exists()) {
      moduleDir.mkdirs();
    }
    return moduleDir;
  }
  
  // Save profiles as JSON
  public void saveProfileToJson(BEnhancedConnectionProfile profile, String name) {
    try {
      File profilesDir = new File(getDataSyncDirectory(), "profiles");
      profilesDir.mkdirs();
      
      File profileFile = new File(profilesDir, name + ".json");
      String json = profileToJson(profile);
      Files.write(profileFile.toPath(), json.getBytes());
      
    } catch (Exception e) {
      System.err.println("Failed to save profile: " + e.getMessage());
    }
  }
  
  // Load on startup
  @Override
  public void started() throws Exception {
    super.started();
    loadProfilesFromJson();
  }
  
  // Auto-save on changes
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);
    
    if (newChild instanceof BEnhancedConnectionProfile) {
      saveProfileToJson((BEnhancedConnectionProfile) newChild, property.getName());
    }
  }
}
```

### **D. Persistence Locations**

#### **Station BOG Files:**
```
station:|slot:/          → config.bog (main configuration)
station:|slot:/Services  → services.bog (services)
station:|slot:/Drivers   → drivers.bog (drivers)
```

#### **User Shared Directory:**
```
%USERPROFILE%/.niagara/shared/modules/datasync/
├── profiles/           # JSON profile files
│   ├── profile1.json
│   └── profile2.json
├── settings/           # Tool settings
│   └── preferences.json
├── cache/              # Temporary data
│   └── sync-history.json
└── logs/               # Module logs
    └── datasync.log
```

#### **Module Directory:**
```
%NIAGARA_HOME%/modules/datasync-wb.jar
├── resources/          # Static resources
├── icons/              # UI icons
└── help/               # Documentation
```

## 4. Component Lifecycle

### **A. Startup Sequence**
```java
public void started() throws Exception {
  super.started();
  
  // 1. Initialize resources
  initializeConnections();
  
  // 2. Load saved state
  loadConfiguration();
  
  // 3. Start background tasks
  startSyncScheduler();
}

public void descendantsStarted() throws Exception {
  super.descendantsStarted();
  
  // Called after ALL children have started
  // Good for cross-component initialization
}

public void stationStarted() throws Exception {
  super.stationStarted();
  
  // Called after entire station is running
  // Good for station-wide initialization
}
```

### **B. Shutdown Sequence**
```java
public void stopped() throws Exception {
  // 1. Stop background tasks
  stopSyncScheduler();
  
  // 2. Save current state
  saveConfiguration();
  
  // 3. Cleanup resources
  closeConnections();
  
  super.stopped();
}

public void descendantsStopped() throws Exception {
  super.descendantsStopped();
  
  // Called after ALL children have stopped
  // Good for final cleanup
}
```

## 5. Property System

### **A. Property Change Notifications**
```java
public void changed(Property property, Context context) {
  super.changed(property, context);
  
  if (property == sourcePath) {
    validateConnection();
  } else if (property == syncInterval) {
    rescheduleSync();
  }
}
```

### **B. Property Validation**
```java
// Pre-change validation
public void checkSet(Property property, BValue value, Context context) {
  super.checkSet(property, value, context);
  
  if (property == port) {
    int portNum = ((BInteger) value).getInt();
    if (portNum < 1024 || portNum > 65535) {
      throw new IllegalArgumentException("Port must be between 1024-65535");
    }
  }
}
```

### **C. Dynamic Property Facets**
```java
public BFacets getSlotFacets(Slot slot) {
  if (slot == port) {
    return BFacets.make(
      BFacets.RANGE, BRange.make(1024, 65535),
      BFacets.HELP, "FoxS protocol port number",
      BFacets.UNITS, "port"
    );
  }
  return super.getSlotFacets(slot);
}
```

## Summary

BComponent provides a comprehensive foundation with:

1. **Agent System**: Rich UI integration with views, tools, exporters
2. **Navigation**: Full nav tree integration with dynamic children
3. **Persistence**: Automatic BOG persistence + custom file persistence
4. **Lifecycle**: Startup/shutdown hooks for resource management
5. **Properties**: Change notifications, validation, dynamic facets
6. **Security**: Built-in permission system
7. **Events**: Component event system for communication

This foundation allows you to build professional, integrated Niagara applications with minimal custom infrastructure code.

---

*This document covers the essential BComponent features for the N4-DataSync project. Update as new features are discovered or implemented.*
