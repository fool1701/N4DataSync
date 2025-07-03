# Niagara Patterns by Use Case

This document organizes Niagara development patterns by specific use cases relevant to the N4-DataSync module. Use this as a quick reference when implementing specific features.

## Table of Contents
- [Component Development](#component-development)
- [UI Development](#ui-development)
- [Data Persistence](#data-persistence)
- [External Data Integration](#external-data-integration)
- [Synchronization](#synchronization)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Security](#security)
- [Documentation](#documentation)

## Component Development

When creating Niagara components (like BConnectionProfile):

1. **Component Model Pattern** - Base pattern for all Niagara components
2. **Slot-o-matic Pattern** - For defining component properties
3. **Introspection Pattern** - Understanding how Niagara discovers properties
4. **BajaObjectGraph (BOG) Pattern** - For component serialization
5. **Composite Pattern** - For hierarchical component structures

Example:
```java
@NiagaraType
@NiagaraProperty(
  name = "sourceType",
  type = "String",
  defaultValue = "Excel"
)
@NiagaraProperty(
  name = "sourcePath",
  type = "String",
  defaultValue = ""
)
public class BConnectionProfile extends BComponent {
  // Slot-o-matic will generate getters/setters
}
```

## UI Development

When creating the DataSync tool UI:

1. **Workbench Tool Pattern** - For creating the tool entry point
2. **Workbench View Pattern** - For creating the tool's views
3. **Module-View-Controller (MVC) Variant** - For structuring UI components
4. **Agent Registration Pattern** - For registering views on tools
5. **Px View Pattern** - For declarative UI layouts
6. **Observer Pattern** - For updating UI when data changes

Example:
```java
@NiagaraType
@AgentOn(types = "workbench:Workbench")
public class BDataSyncTool extends BWbNavNodeTool {
  // Tool implementation
}

@NiagaraType
@AgentOn(types = "datasync:DataSyncTool")
public class BDataSyncProfileView extends BAbstractManager {
  // View implementation
}
```

## Data Persistence

When implementing profile persistence:

1. **Persistence Strategy Pattern** - For storing connection profiles
2. **Repository Pattern** - For managing profile data access
3. **BajaObjectGraph (BOG) Pattern** - For component serialization
4. **Data Transfer Object (DTO) Pattern** - For data exchange

Example:
```java
public class ProfileManager {
  private File getProfilesDirectory() {
    return new File(Sys.getNiagaraSharedUserHome(), "datasync/profiles");
  }
  
  public boolean saveProfile(BConnectionProfile profile, String name) {
    // Implementation
  }
}
```

## External Data Integration

When implementing external data source readers:

1. **Factory Pattern** - For creating appropriate reader implementations
2. **Adapter Pattern** - For converting external formats to IDM
3. **Strategy Pattern** - For different reading strategies
4. **Mapper Pattern** - For transforming data between models

Example:
```java
public interface IExternalSourceReader {
  List<IdmComponent> readSource(String path);
}

public class ExcelSourceReader implements IExternalSourceReader {
  // Implementation
}

public class ReaderFactory {
  public static IExternalSourceReader createReader(String sourceType) {
    // Implementation
  }
}
```

## Synchronization

When implementing synchronization with Niagara stations:

1. **Fox Remote Programming Pattern** - For interacting with remote stations
2. **Command Pattern** - For encapsulating sync operations
3. **Ord Navigation Pattern** - For addressing components
4. **Future/Promise Pattern** - For async operations
5. **Thread Pool Pattern** - For concurrent operations

Example:
```java
public class NiagaraFoxSyncWriter {
  public void createComponent(IdmComponent component, String targetPath) {
    BOrd target = BOrd.make("station:|slot:" + targetPath);
    // Implementation using Fox protocol for component creation
  }
}
```

## Error Handling

When implementing robust error handling:

1. **Circuit Breaker Pattern** - For preventing cascading failures
2. **Retry Pattern** - For handling transient failures
3. **Decorator Pattern** - For adding error handling to operations

Example:
```java
public class ResilientFoxOperation {
  private int maxRetries = 3;
  
  public void executeWithRetry(Runnable operation) {
    // Implementation with retry logic
  }
}
```

## Testing

When creating tests for your module:

1. **Niagara Test Pattern** - For Niagara-specific testing
2. **TestNG Pattern** - For test organization and execution

Example:
```java
@Test(groups = {"datasync", "unit", "json"})
public void testProfileSerialization() {
  // Test implementation
}
```

## Security

When implementing secure connections and module signing:

1. **Security Pattern** - For secure Fox connections to stations
2. **Module Signing Pattern** - For trusted module deployment

Example:
```java
// Secure Fox connection
BFoxSession session = BFoxSession.make(
  "fox://username:password@hostname:1911/"
);

// Module signing configuration in build.gradle.kts
// Uses default Niagara signing profile
```

## Documentation

When creating documentation:

1. **Help Documentation Pattern** - For user documentation
2. **Localization Pattern** - For internationalization

Example:
```java
// In module-include.xml
<dependency>
  <name>datasync-doc</name>
  <version>1.0</version>
</dependency>
```

## References

- See full pattern descriptions in [NiagaraPatterns.md](NiagaraPatterns.md)
- [Niagara Developer Documentation](Niagara/)
- [N4-DataSync Full Feature Specification & Roadmap](../N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)
- [Certificate Setup Guide](CERTIFICATE_SETUP.md)