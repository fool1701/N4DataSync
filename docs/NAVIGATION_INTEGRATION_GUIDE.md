# Navigation Integration Guide

## Overview

This guide explains how to implement professional navigation tree integration for Niagara components, focusing on the BINavNode interface and navigation best practices.

## Navigation Tree Hierarchy

### **Understanding the Nav Tree Structure**

```
Niagara Navigation Tree:
├── Local Host
│   ├── Stations
│   │   └── MyStation
│   │       ├── Services
│   │       ├── Drivers
│   │       └── ...
│   ├── Tools                    ← Tools appear here
│   │   ├── DataSync Tool        ← Our tool
│   │   │   ├── Connection Profiles
│   │   │   │   ├── Profile 1
│   │   │   │   └── Profile 2
│   │   │   └── Target Stations
│   │   │       ├── Station A
│   │   │       └── Station B
│   │   └── Other Tools
│   └── Files
└── Remote Hosts
```

## Core Navigation Methods

### **1. Navigation Identity**

```java
// Unique name within parent (must be valid slot name)
public String getNavName() {
  return getName(); // Usually component's name property
}

// Display text in nav tree (can be dynamic)
public String getNavDisplayName(Context cx) {
  String baseName = "DataSync Tool";
  int profileCount = getProfileCount();
  
  if (profileCount == 0) {
    return baseName + " (No Profiles)";
  } else {
    return baseName + " (" + profileCount + " profiles)";
  }
}

// Tooltip/description (appears on hover)
public String getNavDescription(Context cx) {
  return "Synchronizes data from external sources to Niagara stations";
}
```

### **2. Navigation Hierarchy**

```java
// Parent in navigation tree
public BINavNode getNavParent() {
  return (BComponent) getParent(); // Automatic for BComponent
}

// Children in navigation tree
public BINavNode[] getNavChildren() {
  List<BINavNode> children = new ArrayList<>();
  
  // Method 1: Add organized folders
  BComponent profilesFolder = getProfilesFolder();
  if (profilesFolder != null) {
    children.add(profilesFolder);
  }
  
  // Method 2: Add direct children
  BEnhancedConnectionProfile[] profiles = getProfiles();
  for (BEnhancedConnectionProfile profile : profiles) {
    children.add(profile);
  }
  
  // Method 3: Add virtual/computed children
  if (hasRecentSyncHistory()) {
    children.add(new SyncHistoryNode());
  }
  
  return children.toArray(new BINavNode[0]);
}

// Performance optimization - check before computing children
public boolean hasNavChildren() {
  return getProfileCount() > 0 || hasRecentSyncHistory();
}
```

### **3. Navigation Addressing**

```java
// Absolute navigation address (for hyperlinking)
public BOrd getNavOrd() {
  // Framework automatically constructs ordinal like:
  // "tool:datasync:DataSyncTool|slot:/"
  BComponentSpace space = getComponentSpace();
  if (space == null) return null;
  
  BOrd spaceOrd = space.getAbsoluteOrd();
  if (spaceOrd == null) return null;
  
  SlotPath path = getSlotPath();
  if (path == null) return null;
  
  return BOrd.make(spaceOrd, path);
}

// Visual representation
public BIcon getNavIcon() {
  // Different icons based on state
  if (hasErrors()) {
    return BIcon.make("module://icons/x16/datasync-error.png");
  } else if (isSyncing()) {
    return BIcon.make("module://icons/x16/datasync-active.png");
  } else {
    return BIcon.make("module://icons/x16/datasync.png");
  }
}
```

## Advanced Navigation Patterns

### **1. Folder Organization Pattern**

```java
@NiagaraType
public class BProfilesFolder extends BComponent {
  
  @Override
  public String getNavDisplayName(Context cx) {
    int count = getChildCount(BEnhancedConnectionProfile.class);
    return "Connection Profiles (" + count + ")";
  }
  
  @Override
  public BIcon getNavIcon() {
    return BIcon.make("module://icons/x16/folder-profiles.png");
  }
  
  @Override
  public boolean isChildLegal(BComponent child) {
    return child instanceof BEnhancedConnectionProfile;
  }
  
  @Override
  public BINavNode[] getNavChildren() {
    // Return only profile children, sorted by name
    BEnhancedConnectionProfile[] profiles = getChildren(BEnhancedConnectionProfile.class);
    Arrays.sort(profiles, (a, b) -> a.getName().compareTo(b.getName()));
    return profiles;
  }
  
  @Override
  public boolean hasNavChildren() {
    return getChildCount(BEnhancedConnectionProfile.class) > 0;
  }
}
```

### **2. Dynamic/Virtual Children Pattern**

```java
public class BDataSyncTool extends BWbNavNodeTool {
  
  @Override
  public BINavNode[] getNavChildren() {
    List<BINavNode> children = new ArrayList<>();
    
    // Always show profiles folder
    children.add(getProfilesFolder());
    
    // Conditionally show error folder
    if (hasErrorProfiles()) {
      children.add(new ErrorProfilesVirtualFolder());
    }
    
    // Show recent sync history (virtual node)
    if (hasRecentSyncHistory()) {
      children.add(new RecentSyncHistoryNode());
    }
    
    return children.toArray(new BINavNode[0]);
  }
  
  // Virtual folder that doesn't exist as a component
  private class ErrorProfilesVirtualFolder implements BINavNode {
    
    public String getNavName() { return "errorProfiles"; }
    
    public String getNavDisplayName(Context cx) {
      return "Profiles with Errors (" + getErrorProfileCount() + ")";
    }
    
    public BINavNode[] getNavChildren() {
      return getProfilesWithErrors();
    }
    
    public BIcon getNavIcon() {
      return BIcon.make("module://icons/x16/folder-error.png");
    }
    
    // ... implement other BINavNode methods
  }
}
```

### **3. Context-Sensitive Navigation**

```java
public String getNavDisplayName(Context cx) {
  StringBuilder name = new StringBuilder("DataSync Tool");
  
  // Add status indicator
  if (isRunning()) {
    if (isSyncing()) {
      name.append(" (Syncing...)");
    } else {
      name.append(" (Ready)");
    }
  } else {
    name.append(" (Stopped)");
  }
  
  // Add profile count
  int profileCount = getProfileCount();
  if (profileCount > 0) {
    name.append(" [").append(profileCount).append(" profiles]");
  }
  
  return name.toString();
}

public BIcon getNavIcon() {
  // Dynamic icons based on state
  if (!isRunning()) {
    return BIcon.make("module://icons/x16/datasync-stopped.png");
  } else if (hasErrors()) {
    return BIcon.make("module://icons/x16/datasync-error.png");
  } else if (isSyncing()) {
    return BIcon.make("module://icons/x16/datasync-syncing.png");
  } else {
    return BIcon.make("module://icons/x16/datasync-ready.png");
  }
}
```

## Navigation Events and Updates

### **Automatic Updates**

The framework automatically updates the nav tree when:

```java
// When children are added/removed
public void childParented(Property property, BValue newChild, Context context) {
  super.childParented(property, newChild, context);
  // Nav tree automatically refreshes - no manual code needed
}

public void childUnparented(Property property, BValue oldChild, Context context) {
  super.childUnparented(property, oldChild, context);
  // Nav tree automatically refreshes - no manual code needed
}

// When properties change that affect display
public void changed(Property property, Context context) {
  super.changed(property, context);
  
  if (property == name) {
    // Nav tree automatically refreshes display name
  }
}
```

### **Manual Refresh (Rarely Needed)**

```java
// Force nav tree refresh (only if automatic doesn't work)
public void refreshNavTree() {
  BNavRoot.INSTANCE.fireNavEvent(
    new NavEvent(this, NavEvent.CHILDREN_CHANGED)
  );
}
```

## Navigation Best Practices

### **1. Performance Optimization**

```java
// Cache expensive computations
private BINavNode[] cachedChildren = null;
private long lastChildrenUpdate = 0;

public BINavNode[] getNavChildren() {
  long now = System.currentTimeMillis();
  
  // Refresh cache every 5 seconds
  if (cachedChildren == null || (now - lastChildrenUpdate) > 5000) {
    cachedChildren = computeChildren();
    lastChildrenUpdate = now;
  }
  
  return cachedChildren;
}

// Invalidate cache when structure changes
public void childParented(Property property, BValue newChild, Context context) {
  super.childParented(property, newChild, context);
  cachedChildren = null; // Force recomputation
}
```

### **2. User-Friendly Display Names**

```java
public String getNavDisplayName(Context cx) {
  // Use meaningful, descriptive names
  String baseName = getDisplayName(cx);
  
  // Add context information
  if (isConfigured()) {
    String sourceType = getDataSourceConnection().getSourceType();
    String targetCount = String.valueOf(getTargetStations().length);
    return baseName + " (" + sourceType + " → " + targetCount + " targets)";
  } else {
    return baseName + " (Not Configured)";
  }
}
```

### **3. Consistent Icon Usage**

```java
// Define icon constants
private static final BIcon ICON_READY = BIcon.make("module://icons/x16/datasync-ready.png");
private static final BIcon ICON_ERROR = BIcon.make("module://icons/x16/datasync-error.png");
private static final BIcon ICON_SYNCING = BIcon.make("module://icons/x16/datasync-syncing.png");

public BIcon getNavIcon() {
  // Use consistent icon logic across all components
  if (hasErrors()) return ICON_ERROR;
  if (isSyncing()) return ICON_SYNCING;
  return ICON_READY;
}
```

## Integration with DataSync Tool

### **Complete Implementation Example**

```java
@NiagaraType
@AgentOn(types = "workbench:Workbench")
public class BDataSyncTool extends BWbNavNodeTool {
  
  @Override
  public String getNavDisplayName(Context cx) {
    return "DataSync Tool (" + getProfileCount() + " profiles)";
  }
  
  @Override
  public BIcon getNavIcon() {
    if (hasActiveSync()) return BIcon.make("module://icons/x16/datasync-active.png");
    if (hasErrors()) return BIcon.make("module://icons/x16/datasync-error.png");
    return BIcon.make("module://icons/x16/datasync.png");
  }
  
  @Override
  public BINavNode[] getNavChildren() {
    List<BINavNode> children = new ArrayList<>();
    
    // Add profiles folder
    children.add(getProfilesFolder());
    
    // Add target stations folder
    children.add(getTargetStationsFolder());
    
    // Add sync history (if available)
    if (hasSyncHistory()) {
      children.add(getSyncHistoryFolder());
    }
    
    return children.toArray(new BINavNode[0]);
  }
  
  @Override
  public boolean hasNavChildren() {
    return true; // Always has at least the folders
  }
}
```

This navigation integration provides a professional, intuitive user experience that feels native to the Niagara platform.

---

*This guide covers navigation integration patterns for the N4-DataSync project. Update as new patterns are discovered.*
