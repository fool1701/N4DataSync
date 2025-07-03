# Component Relationship Patterns for N4-DataSync

## **1. Nav Tree Management Pattern**

### **Current Implementation Analysis**
Your `BDataSyncTool` correctly extends `BWbNavNodeTool` and is registered as an agent on `workbench:Workbench`. This follows the standard Niagara pattern for tools that appear in the nav tree.

### **Recommended Nav Tree Structure**
```
Tools/
└── DataSync Tool/
    ├── Connection Profiles/
    │   ├── Building A HVAC/
    │   │   ├── dataSourceConnection (unremovable child)
    │   │   ├── extractionSettings (unremovable child)
    │   │   └── targetStations/ (folder for multiple targets)
    │   │       ├── Station1 (removable)
    │   │       └── Station2 (removable)
    │   └── Building B Lighting/
    │       ├── dataSourceConnection (unremovable child)
    │       ├── extractionSettings (unremovable child)
    │       └── targetStations/
    │           └── Station1 (removable)
    └── Sync History/
        ├── Recent Operations
        └── Error Log
```

### **Implementation Pattern**

#### **Enhanced BDataSyncTool with Nav Tree Support**
```java
@NiagaraType
@AgentOn(types = "workbench:Workbench")
public class BDataSyncTool extends BWbNavNodeTool {
  
  @Override
  public BINavNode[] getNavChildren() {
    // Return organized children for nav tree
    List<BINavNode> children = new ArrayList<>();
    
    // Add profiles folder
    BComponent profilesFolder = getProfilesFolder();
    if (profilesFolder != null) {
      children.add(profilesFolder);
    }
    
    // Add sync history folder
    BComponent historyFolder = getSyncHistoryFolder();
    if (historyFolder != null) {
      children.add(historyFolder);
    }
    
    return children.toArray(new BINavNode[0]);
  }
  
  @Override
  public String getNavDisplayName(Context cx) {
    return "DataSync Tool";
  }
  
  @Override
  public BIcon getNavIcon() {
    return BIcon.make("module://icons/x16/datasync.png");
  }
}
```

## **2. Component Relationship Patterns**

### **Parent-Child Relationship Pattern (BComponent Composition)**

Based on `BComponent.java` analysis, here's how to implement the relationships:

#### **Pattern 1: Unremovable Child Components**
For `DataSourceConnection` and `ExtractionSettings` that should always exist:

```java
@NiagaraType
@NiagaraProperty(
  name = "dataSourceConnection",
  type = "datasync:DataSourceConnection",
  defaultValue = "new BDataSourceConnection()",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "extractionSettings", 
  type = "datasync:DataExtractionSettings",
  defaultValue = "new BDataExtractionSettings()",
  flags = Flags.READONLY | Flags.HIDDEN
)
public class BConnectionProfile extends BComponent {
  
  // Slot-o-matic generated getters/setters
  
  @Override
  public boolean isChildLegal(BComponent child) {
    // Allow target stations to be added dynamically
    if (child instanceof BTargetNiagaraStation) {
      return true;
    }
    // Prevent removal of core components
    if (child instanceof BDataSourceConnection || 
        child instanceof BDataExtractionSettings) {
      return false; // These are managed internally
    }
    return super.isChildLegal(child);
  }
  
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);
    
    if (newChild instanceof BTargetNiagaraStation) {
      System.out.println("Target station added: " + property.getName());
      // Notify listeners, update UI, etc.
    }
  }
  
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);
    
    if (oldChild instanceof BTargetNiagaraStation) {
      System.out.println("Target station removed: " + property.getName());
      // Cleanup, notify listeners, etc.
    }
  }
}
```

#### **Pattern 2: Dynamic Child Management (Target Stations)**
For components that can be added/removed:

```java
public class BConnectionProfile extends BComponent {
  
  /**
   * Add a target station to this profile.
   * @param name Unique name for the station
   * @param station The target station configuration
   * @return The added property
   */
  public Property addTargetStation(String name, BTargetNiagaraStation station) {
    // Validate name uniqueness
    if (get(name) != null) {
      throw new IllegalArgumentException("Station name already exists: " + name);
    }
    
    // Add as dynamic property
    return add(name, station, Flags.SUMMARY);
  }
  
  /**
   * Remove a target station from this profile.
   * @param name Name of the station to remove
   * @return true if removed successfully
   */
  public boolean removeTargetStation(String name) {
    BValue station = get(name);
    if (station instanceof BTargetNiagaraStation) {
      remove(name);
      return true;
    }
    return false;
  }
  
  /**
   * Get all target stations for this profile.
   * @return Array of target stations
   */
  public BTargetNiagaraStation[] getTargetStations() {
    return getChildren(BTargetNiagaraStation.class);
  }
  
  /**
   * Get the primary (first) target station.
   * @return Primary target station or null
   */
  public BTargetNiagaraStation getPrimaryTargetStation() {
    BTargetNiagaraStation[] stations = getTargetStations();
    return stations.length > 0 ? stations[0] : null;
  }
}
```

### **Pattern 3: Folder Organization Pattern**

For organizing components in the nav tree:

```java
@NiagaraType
public class BProfilesFolder extends BComponent {
  
  @Override
  public String getNavDisplayName(Context cx) {
    return "Connection Profiles";
  }
  
  @Override
  public BIcon getNavIcon() {
    return BIcon.make("module://icons/x16/folder.png");
  }
  
  @Override
  public boolean isChildLegal(BComponent child) {
    return child instanceof BConnectionProfile;
  }
  
  @Override
  public BINavNode[] getNavChildren() {
    // Return only BConnectionProfile children
    return getChildren(BConnectionProfile.class);
  }
}

@NiagaraType  
public class BTargetStationsFolder extends BComponent {
  
  @Override
  public String getNavDisplayName(Context cx) {
    return "Target Stations";
  }
  
  @Override
  public boolean isChildLegal(BComponent child) {
    return child instanceof BTargetNiagaraStation;
  }
}
```

## **3. Navigation and Selection Patterns**

### **Nav Tree Integration Pattern**

```java
public class BConnectionProfile extends BComponent implements BINavNode {
  
  @Override
  public String getNavDisplayName(Context cx) {
    String name = getName();
    if (name != null && !name.isEmpty()) {
      return name;
    }
    
    // Fallback to source information
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      return connection.getConnectionName();
    }
    
    return "Unnamed Profile";
  }
  
  @Override
  public BINavNode[] getNavChildren() {
    List<BINavNode> children = new ArrayList<>();
    
    // Always show core components
    children.add(getDataSourceConnection());
    children.add(getExtractionSettings());
    
    // Add target stations folder if any exist
    BTargetNiagaraStation[] stations = getTargetStations();
    if (stations.length > 0) {
      BTargetStationsFolder folder = getTargetStationsFolder();
      if (folder != null) {
        children.add(folder);
      }
    }
    
    return children.toArray(new BINavNode[0]);
  }
  
  @Override
  public boolean hasNavChildren() {
    return true; // Always has dataSource and extractionSettings
  }
  
  @Override
  public BIcon getNavIcon() {
    // Icon based on connection status
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      String status = connection.getValidationStatus();
      if ("Valid".equals(status)) {
        return BIcon.make("module://icons/x16/profile-valid.png");
      } else if ("Invalid".equals(status)) {
        return BIcon.make("module://icons/x16/profile-invalid.png");
      }
    }
    return BIcon.make("module://icons/x16/profile.png");
  }
}
```

## **4. Property Flags and Behavior Control**

### **Component Visibility and Behavior Flags**

```java
// For unremovable core components
@NiagaraProperty(
  name = "dataSourceConnection",
  type = "datasync:DataSourceConnection", 
  defaultValue = "new BDataSourceConnection()",
  flags = Flags.READONLY | Flags.SUMMARY  // Visible but not editable
)

// For removable target stations (added dynamically)
public Property addTargetStation(String name, BTargetNiagaraStation station) {
  return add(name, station, 
    Flags.SUMMARY |           // Show in property sheet
    Flags.TRANSIENT |         // Don't persist in BOG (we use JSON)
    0                         // Allow editing and removal
  );
}

// For internal organization (folders)
public Property addTargetStationsFolder() {
  return add("targetStations", new BTargetStationsFolder(),
    Flags.HIDDEN |            // Don't show in property sheet
    Flags.READONLY |          // Can't be removed
    Flags.TRANSIENT           // Don't persist
  );
}
```

## **5. Event Handling and Synchronization**

### **Component Change Notification Pattern**

```java
public class BConnectionProfile extends BComponent {
  
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);
    
    // Notify parent tool of changes
    BComponent parent = getParent();
    if (parent instanceof BDataSyncTool) {
      ((BDataSyncTool) parent).onProfileChanged(this, "child_added", property.getName());
    }
    
    // Update nav tree
    fireNavEvent(BNavEvent.ADDED, this);
  }
  
  @Override
  public void changed(Property property, Context context) {
    super.changed(property, context);
    
    // Persist changes
    BComponent parent = getParent();
    if (parent instanceof BDataSyncTool) {
      ((BDataSyncTool) parent).saveProfileToJson(this, getName());
    }
    
    // Update nav tree display
    fireNavEvent(BNavEvent.CHANGED, this);
  }
}
```

## **6. Best Practices Summary**

1. **Use Composition over Inheritance**: Core components as properties, not inheritance
2. **Leverage BComponent's Built-in Patterns**: Use `childParented()`, `isChildLegal()`, etc.
3. **Control Visibility with Flags**: Use `Flags.READONLY`, `Flags.HIDDEN`, etc.
4. **Implement BINavNode**: For proper nav tree integration
5. **Use Dynamic Properties**: For removable components like target stations
6. **Organize with Folders**: Group related components logically
7. **Handle Events Properly**: Use lifecycle callbacks for persistence and UI updates
8. **Follow Niagara Naming**: Use proper ORD schemes and naming conventions

This pattern gives you a clean, extensible architecture that follows Niagara conventions while providing the flexibility you need for your DataSync tool.
