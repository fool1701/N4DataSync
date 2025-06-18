# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

**Note: Build commands should be run in Windows environment, not WSL, as they require Java and Niagara installation.**

**Primary Build Commands (Windows Command Prompt):**
```cmd
REM Build the main datasync module
gradlew :datasync:datasync-wb:jar

REM Build all modules
gradlew jar

REM Clean build artifacts
gradlew clean

REM Run slot-o-matic code generation
gradlew slotomatic

REM **Build test jar (does not run tests)**
gradlew moduleTestJar

REM **To run tests, use:**
REM **test <target>**

REM Build specific module
gradlew :<moduleName>:jar
```

**Development Commands:**
```cmd
REM Generate Javadoc
gradlew javadocJar

REM List available tasks
gradlew tasks
```

## Project Architecture

This is a **Niagara 4 Workbench module** that implements a data synchronization tool for BMS projects. Key architectural patterns:

### Core Components Structure
- **BDataSyncTool** - Entry point extending `BWbNavNodeTool` (appears in Tools menu)
- **BDataSyncManagerView** - Main UI view with BEdgePane layout
- **BDataSyncTable** - Custom table component with DynamicTableModel
- **ConnectionProfile** - Data model for sync configurations

### Niagara Type System Pattern
All classes follow the standard Niagara pattern:
```java
@NiagaraType
public class BClassName extends BSuperClass {
    public static final Type TYPE = Sys.loadType(BClassName.class);
    public Type getType() { return TYPE; }
}
```

### Module Configuration Files
- **module-include.xml** - Registers types, defs, and resources
- **module.lexicon** - Localization strings and display names  
- **module.palette** - **Defines a standard collection of public components (similar to a standard .bog file)**
- **meta-inf/module.xml** - **Module metadata and runtime profiles (the primary module manifest file)**

### Workbench-Only Architecture
- Runs entirely in Workbench JVM
- Communicates with stations via Fox protocol
- No station-side installation required
- Creates components: BACnet Networks, Devices, Controllers, Points

## Development Environment

### Required Configuration (Windows)
- **niagara_home** - Path to Niagara installation (set in gradle.properties)
- **niagara_user_home** - Path to user's Niagara data directory
- **JDK 8** - Required for Niagara development

### Module Dependencies
Key Niagara modules this project depends on:
- `Tridium:baja` - Core framework
- `Tridium:bajaui-wb` - Workbench UI components
- `Tridium:workbench-wb` - Workbench integration
- `Tridium:bacnet-rt/ux/wb` - BACnet protocol support
- `Tridium:control-rt/ux/wb` - Control framework

### Custom Signing
Module uses custom certificate located in `security/` directory. The signing configuration is in `datasync-wb.gradle.kts`:
```kotlin
niagaraSigning {
    signingProfileFile.set(rootProject.layout.projectDirectory.file("security/niagara.signing.xml"))
    aliases.set(listOf("myN4DataSyncCert"))
}
```

## Code Patterns

### UI Component Creation
Follow this pattern for new UI components:
1. Extend appropriate Niagara base class (`BWbView`, `BTable`, etc.)
2. Use `@NiagaraType` annotation and TYPE field
3. Register in `module-include.xml`
4. Add localization entries to `module.lexicon`
5. **Associate views with object types via agent registration in `module-include.xml`**

### **View-Object Association**
**Views are associated with object types through agent registration, not palette files:**
- **Agent Registration**: Views are declared as agents on specific Types using `<agent><on type="module:TypeName"/></agent>` in `module-include.xml`
- **Annotations**: For Niagara 4, use `@NiagaraType(agent=@AgentOn(types={"myModule:MyComponent"}))`
- **Registry**: The Niagara Registry manages these associations and discovers which views are available for each object type
- **Palette Purpose**: `module.palette` defines collections of components for drag-and-drop in Workbench, not view associations

### Data Synchronization Flow
The architecture follows this pattern:
```
External Data Source → IDM (Java POJOs) → Niagara Components
```

This intermediate data model (IDM) approach decouples external data from Niagara internals and enables comparison/diff operations.

### Testing
- Uses TestNG framework (`Tridium:test-wb` dependency)
- Test classes in `srcTest/` directories
- Follow BTestNg patterns for Niagara testing

## Important Notes

- This is a **Version 1 MVP** focused on component creation only
- Future versions will support bidirectional sync, updates, and deletions
- The project includes example modules in `niagaraModulesExample/` for reference
- Module runtime profile is set to `wb` (Workbench only)
- Vendor is set to "MEA" with version "1.0"
- **Build commands require Windows environment with Java and Niagara installed**