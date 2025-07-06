# DataSync Tool Integration Example

## Overview

This document shows how to integrate the new Data Source Connection architecture with the existing `BDataSyncTool`. The integration adds a frozen property slot for managing data source connections while maintaining backward compatibility.

## Integration Steps

### 1. Add DataSourceConnections Property to BDataSyncTool

Add this property annotation to `BDataSyncTool.java`:

```java
@NiagaraProperty(
  name = "dataSourceConnections",
  type = "datasync:DataSourceConnections",
  defaultValue = "new BDataSourceConnections()",
  flags = Flags.READONLY | Flags.SUMMARY
)
```

### 2. Complete BDataSyncTool Integration

```java
// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.BIcon;
import javax.baja.workbench.tool.BWbNavNodeTool;
import com.mea.datasync.model.BDataSourceFolder;

@NiagaraType
@AgentOn(types = "workbench:Workbench")
// Add the DataSourceFolder property
@NiagaraProperty(
  name = "dataSourceConnections",
  type = "datasync:DataSourceFolder",
  defaultValue = "new BDataSourceFolder()",
  flags = Flags.READONLY | Flags.SUMMARY
)
public class BDataSyncTool extends BWbNavNodeTool {

  // ... existing auto-generated code ...

  /**
   * Get the dataSourceConnections property.
   */
  public BDataSourceConnections getDataSourceConnections() {
    return (BDataSourceConnections)get(dataSourceConnections);
  }

  /**
   * Set the dataSourceConnections property.
   */
  public void setDataSourceConnections(BDataSourceConnections v) {
    set(dataSourceConnections, v, null);
  }

  // ... existing methods ...

  /**
   * Enhanced navigation children to include data source connections.
   */
  @Override
  public BINavNode[] getNavChildren() {
    List<BINavNode> children = new ArrayList<>();

    // Add data source connections container
    BDataSourceConnections connections = getDataSourceConnections();
    if (connections != null) {
      children.add(connections);
    }

    // Add existing enhanced profiles (maintain backward compatibility)
    BEnhancedConnectionProfile[] profiles = getEnhancedProfiles();
    for (BEnhancedConnectionProfile profile : profiles) {
      children.add(profile);
    }

    return children.toArray(new BINavNode[0]);
  }

  /**
   * Enhanced tool description including data source connection status.
   */
  @Override
  public String getNavDescription(Context cx) {
    StringBuilder desc = new StringBuilder();
    desc.append("N4-DataSync Tool");

    // Add data source connection summary
    BDataSourceConnections connections = getDataSourceConnections();
    if (connections != null) {
      int connectionCount = connections.getDataSourceConnectionCount();
      int healthyCount = 0;

      // Count healthy connections
      BAbstractDataSourceConnection[] allConnections = connections.getAllDataSourceConnections();
      for (BAbstractDataSourceConnection conn : allConnections) {
        if (conn.isConnectionHealthy()) {
          healthyCount++;
        }
      }

      if (connectionCount > 0) {
        desc.append(" - ").append(connectionCount).append(" data source");
        if (connectionCount != 1) desc.append("s");
        desc.append(" (").append(healthyCount).append(" healthy)");
      }
    }

    return desc.toString();
  }
}
```

### 3. Update module-include.xml

Add the new component types to your module registration:

```xml
<!-- Data Source Connection Architecture -->
<type class="com.mea.datasync.model.BAbstractDataSourceConnection" name="AbstractDataSourceConnection"/>
<type class="com.mea.datasync.model.BConnectionDetails" name="ConnectionDetails"/>
<type class="com.mea.datasync.model.BAutoCheckConfig" name="AutoCheckConfig"/>

<!-- Excel Data Source Implementation -->
<type class="com.mea.datasync.model.BExcelDataSourceConnection" name="ExcelDataSourceConnection"/>
<type class="com.mea.datasync.model.BExcelConnectionDetails" name="ExcelConnectionDetails"/>

<!-- Container Components -->
<type class="com.mea.datasync.model.BDataSourceConnections" name="DataSourceConnections"/>
<type class="com.mea.datasync.model.BDataSourceConnectionsFolder" name="DataSourceConnectionsFolder"/>
```

## Usage Examples

### Creating Excel Data Source Connections

```java
// Get the DataSync Tool instance
BDataSyncTool tool = getDataSyncTool();
BDataSourceConnections connections = tool.getDataSourceConnections();

// Create Excel connection details
BExcelConnectionDetails excelDetails = new BExcelConnectionDetails();
excelDetails.setConnectionName("BMS Points Data");
excelDetails.setDescription("Main BMS points configuration file");
excelDetails.setFilePath("C:/DataSync/bms_points.xlsx");
excelDetails.setDefaultWorksheet("Points");

// Create Excel data source connection
BExcelDataSourceConnection excelConnection = new BExcelDataSourceConnection();
excelConnection.setConnectionDetails(excelDetails);

// Add to the connections container
connections.add("bmsPointsExcel", excelConnection);

// Test the connection
excelConnection.doTestConnection();
```

### Creating Organized Folder Structure

```java
BDataSourceConnections connections = tool.getDataSourceConnections();

// Create a folder for BMS data sources
BDataSourceConnectionsFolder bmsFolder = new BDataSourceConnectionsFolder();
bmsFolder.setDisplayName("BMS Data Sources");
bmsFolder.setDescription("All BMS-related data source connections");

// Add folder to main container
connections.add("bmsDataSources", bmsFolder);

// Add Excel connections to the folder
BExcelDataSourceConnection pointsConnection = createExcelConnection(
  "BMS Points", "C:/Data/points.xlsx");
BExcelDataSourceConnection devicesConnection = createExcelConnection(
  "BMS Devices", "C:/Data/devices.xlsx");

bmsFolder.add("points", pointsConnection);
bmsFolder.add("devices", devicesConnection);
```

### Monitoring Connection Health

```java
BDataSourceConnections connections = tool.getDataSourceConnections();

// Check overall health
boolean hasHealthyConnections = connections.hasHealthyConnections();
System.out.println("Has healthy connections: " + hasHealthyConnections);

// Get all connections and their status
BAbstractDataSourceConnection[] allConnections = connections.getAllDataSourceConnections();
for (BAbstractDataSourceConnection connection : allConnections) {
  System.out.println(connection.getDataSourceTypeName() + " - " +
                    connection.getConnectionStatus());
}

// Get Excel connections specifically
BExcelDataSourceConnection[] excelConnections =
  connections.getDataSourceConnectionsByType(BExcelDataSourceConnection.class);
System.out.println("Found " + excelConnections.length + " Excel connections");
```

## Navigation Tree Structure

With this integration, the navigation tree will show:

```
📊 DataSync Tool
├── 🗂️ Data Source Connections
│   ├── 📊 Excel Connection 1 (Connected)
│   ├── 📊 Excel Connection 2 (Failed)
│   └── 📁 BMS Data Sources
│       ├── 📊 BMS Points (Connected)
│       └── 📊 BMS Devices (Testing...)
└── 🔗 Enhanced Connection Profile 1 (existing)
```

## Benefits of This Integration

### 1. **Backward Compatibility**
- Existing enhanced profiles continue to work
- No breaking changes to current functionality

### 2. **Clear Separation of Concerns**
- Data source connections are separate from sync profiles
- Each has its own dedicated container and management

### 3. **Type Safety**
- Only valid data source connection types can be added
- Compile-time validation of component relationships

### 4. **Extensibility**
- Easy to add new data source types
- Folder organization for complex scenarios

### 5. **Health Monitoring**
- Built-in connection health tracking
- Visual indicators in navigation tree

### 6. **Auto-Checking**
- Automatic connection testing in background
- Configurable intervals and retry logic

## Migration Strategy

### Phase 1: Add New Architecture (Current)
- Add data source connection components
- Integrate with DataSync Tool
- Test with Excel connections

### Phase 2: Migrate Existing Connections
- Extract connection logic from existing BDataSourceConnection
- Create migration utility to convert existing connections
- Maintain backward compatibility

### Phase 3: Enhance and Optimize
- Add more data source types (Database, CSV, Web APIs)
- Implement connection pooling and caching
- Add monitoring dashboard

## Testing Recommendations

### Unit Tests
```java
public class BDataSourceConnectionsTest extends BTestNg {

  @Test
  public void testExcelConnectionCreation() {
    BExcelDataSourceConnection connection = new BExcelDataSourceConnection();
    BExcelConnectionDetails details = connection.getConnectionDetails();

    details.setFilePath("test.xlsx");
    details.setConnectionName("Test Connection");

    // Test connection validation
    BConnectionDetails.ValidationResult result = details.validateConfiguration();
    assertTrue(result.isValid());
  }

  @Test
  public void testConnectionContainerValidation() {
    BDataSourceConnections container = new BDataSourceConnections();
    BExcelDataSourceConnection excelConn = new BExcelDataSourceConnection();
    BComponent invalidConn = new BComponent();

    // Should accept valid connection types
    assertTrue(container.isChildLegal(excelConn));

    // Should reject invalid types
    assertFalse(container.isChildLegal(invalidConn));
  }
}
```

### Integration Tests
- Test auto-checking functionality
- Test navigation tree integration
- Test connection health monitoring
- Test folder organization features

This integration provides a solid foundation for managing data source connections while maintaining the flexibility to extend and enhance the system as requirements evolve.
