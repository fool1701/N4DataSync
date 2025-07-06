# Data Source Connection Architecture

## Overview

This document describes the comprehensive Data Source Connection architecture for the N4-DataSync module. The architecture provides a robust, extensible framework for connecting to various external data sources with built-in health monitoring, auto-checking, and type-safe component organization.

## Architecture Components

### 1. Base Abstract Class: `BAbstractDataSourceConnection`

The foundation class that all data source connections extend.

#### Key Features:
- **Health Monitoring**: Complete set of health properties tracking connection status
- **Auto-Checking**: Configurable automatic connection testing based on BPingMonitor patterns
- **Manual Testing**: Action-based manual connection testing
- **Type-Specific Logic**: Abstract methods for subclass-specific implementation

#### Properties:
```java
// Connection Details Component (overridden by subclasses)
@NiagaraProperty(name = "connectionDetails", type = "datasync:ConnectionDetails")

// Health Properties
@NiagaraProperty(name = "connectionStatus", type = "baja:String")
@NiagaraProperty(name = "lastConnectionTest", type = "baja:AbsTime")
@NiagaraProperty(name = "lastSuccessfulConnection", type = "baja:AbsTime")
@NiagaraProperty(name = "lastConnectionError", type = "baja:String")
@NiagaraProperty(name = "consecutiveFailures", type = "baja:Integer")

// Auto-Check Configuration Component
@NiagaraProperty(name = "autoCheckConfig", type = "datasync:AutoCheckConfig")

// Manual Connection Test Action
@NiagaraAction(name = "testConnection")
```

#### Abstract Methods:
- `performConnectionTest()`: Type-specific connection testing logic
- `getDataSourceTypeName()`: Human-readable type name
- `getConnectionSummary()`: Connection configuration summary

### 2. Connection Details Components

#### `BConnectionDetails` (Abstract Base)
Common properties for all connection types:
- `connectionName`: Display name for the connection
- `description`: Optional description
- `connectionTimeout`: Connection timeout in milliseconds

#### `BExcelConnectionDetails` (Concrete Implementation)
Excel-specific connection configuration:
- `filePath`: Path to the Excel file
- `defaultWorksheet`: Default worksheet name
- `readOnlyMode`: Whether to open in read-only mode
- `allowedFormats`: Supported Excel formats (XLSX, XLS)

### 3. Auto-Check Configuration: `BAutoCheckConfig`

Configures automatic connection health checking:

#### Properties:
- `enabled`: Enable/disable auto-checking
- `checkInterval`: How often to check connections
- `retryCount`: Number of retries on failure
- `retryDelay`: Delay between retries
- `failureThreshold`: Consecutive failures before marking as failed
- `startupDelay`: Delay before starting auto-checks

#### Factory Methods:
- `createExcelDefault()`: Optimized settings for file-based sources
- `createDatabaseDefault()`: Optimized settings for database sources
- `createWebDefault()`: Optimized settings for web-based sources

### 4. Container Components

#### `BDataSourceFolder` (Frozen Property)
Main container for organizing data source connections:
- Type-safe child validation (only accepts `BAbstractDataSourceConnection` subclasses)
- Navigation tree integration
- Utility methods for connection management
- Designed as frozen property in `BDataSyncTool`

#### `BDataSourceConnectionsFolder` (Removable)
Removable folder for organizing connections:
- Same child validation as main container
- Configurable display name and description
- Supports nested folder structures
- Can be added/removed dynamically

## Implementation Examples

### Creating an Excel Data Source Connection

```java
// Create Excel connection details
BExcelConnectionDetails excelDetails = new BExcelConnectionDetails();
excelDetails.setConnectionName("BMS Points Data");
excelDetails.setFilePath("C:/Data/bms_points.xlsx");
excelDetails.setDefaultWorksheet("Points");

// Create Excel data source connection
BExcelDataSourceConnection excelConnection = new BExcelDataSourceConnection();
excelConnection.setConnectionDetails(excelDetails);

// Configure auto-checking (optional - defaults are provided)
BAutoCheckConfig autoCheck = BAutoCheckConfig.createExcelDefault();
autoCheck.setCheckInterval(BRelTime.make(15L * 60L * 1000L)); // 15 minutes
excelConnection.setAutoCheckConfig(autoCheck);

// Test connection manually
excelConnection.doTestConnection();
```

### Integration with DataSync Tool

Add to `BDataSyncTool`:

```java
@NiagaraProperty(
  name = "dataSourceConnections",
  type = "datasync:DataSourceFolder",
  defaultValue = "new BDataSourceFolder()",
  flags = Flags.READONLY | Flags.SUMMARY
)
```

## Extension Points

### Creating New Data Source Types

1. **Create Connection Details Class**:
   ```java
   public class BDatabaseConnectionDetails extends BConnectionDetails {
     // Database-specific properties: jdbcUrl, username, password, schema, etc.
   }
   ```

2. **Create Data Source Connection Class**:
   ```java
   public class BDatabaseDataSourceConnection extends BAbstractDataSourceConnection {
     // Override connectionDetails property type
     // Implement abstract methods with database-specific logic
   }
   ```

3. **Register in module-include.xml**:
   ```xml
   <type class="com.mea.datasync.model.BDatabaseConnectionDetails" name="DatabaseConnectionDetails"/>
   <type class="com.mea.datasync.model.BDatabaseDataSourceConnection" name="DatabaseDataSourceConnection"/>
   ```

## Benefits

### 1. **Type Safety**
- Compile-time validation of component relationships
- Clear separation of concerns between connection types

### 2. **Extensibility**
- Easy to add new data source types
- Consistent interface across all connection types

### 3. **Health Monitoring**
- Built-in connection health tracking
- Automatic failure detection and reporting

### 4. **Auto-Checking**
- Configurable automatic connection testing
- Based on proven BPingMonitor patterns
- Optimized defaults for different data source types

### 5. **Organization**
- Hierarchical folder structure for connection management
- Navigation tree integration
- Type-safe container validation

### 6. **Niagara Integration**
- Follows Niagara component patterns
- Property-driven architecture
- Action-based manual operations
- Navigation tree support

## Best Practices

### 1. **Connection Details Separation**
- Keep connection configuration separate from connection logic
- Use composition over inheritance for connection details

### 2. **Health Monitoring**
- Always update health properties in connection tests
- Provide meaningful error messages
- Track consecutive failures for reliability analysis

### 3. **Auto-Checking Configuration**
- Use appropriate intervals for different data source types
- Consider network latency and resource usage
- Provide reasonable defaults with customization options

### 4. **Error Handling**
- Catch and handle all exceptions in connection tests
- Provide user-friendly error messages
- Log detailed error information for troubleshooting

### 5. **Navigation Tree Integration**
- Implement BINavNode for all connection components
- Provide meaningful display names and descriptions
- Use appropriate icons for different connection states

## Future Enhancements

### 1. **Connection Pooling**
- Implement connection pooling for database sources
- Reuse connections for better performance

### 2. **Credential Management**
- Secure credential storage and retrieval
- Integration with Niagara security framework

### 3. **Connection Templates**
- Pre-configured connection templates for common scenarios
- Template-based connection creation wizard

### 4. **Monitoring Dashboard**
- Visual dashboard for connection health monitoring
- Historical connection status tracking

### 5. **Notification System**
- Alerts for connection failures
- Integration with Niagara alarm system
