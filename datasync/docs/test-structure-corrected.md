# Corrected Test Structure for DataSync Module

## Overview

The test files have been restructured to follow proper Niagara development conventions as specified in the Niagara documentation.

## Niagara Test Conventions

According to `/docs/Development/AutomatedTestingWithTestNg.md`, Niagara tests should follow these conventions:

### Directory Structure
- **Test Source**: `srcTest/` directory (not in main `src/`)
- **Package Structure**: Mirror the main package structure
- **Test Classes**: Extend `com.tridium.testng.BTestNg`
- **Test Registration**: Declare test types in `moduleTest-include.xml`

### Build Configuration
- **Test Dependencies**: Use `moduleTestImplementation` for test-only dependencies
- **Test Compilation**: Use `compileTestJava` task
- **Test Execution**: Use Niagara's `test` command

## Corrected File Structure

### Before (Incorrect)
```
datasync/
├── test-directory-creation.java          ❌ Wrong location
├── test-json-writing.java               ❌ Wrong location  
├── test-manual-profile-creation.java    ❌ Wrong location
└── test-profile-manager.java            ❌ Wrong location
```

### After (Correct)
```
datasync/datasync-wb/
├── srcTest/                              ✅ Proper test directory
│   └── com/mea/datasync/test/
│       └── BProfileManagerTest.java     ✅ Proper test class
├── moduleTest-include.xml                ✅ Test type registration
└── datasync-wb.gradle.kts               ✅ Test dependencies configured
```

## Test Class Structure

### BProfileManagerTest.java
- **Location**: `srcTest/com/mea/datasync/test/BProfileManagerTest.java`
- **Extends**: `com.tridium.testng.BTestNg`
- **Annotations**: `@NiagaraType` and `@Test`
- **Purpose**: Tests ProfileManager save/load functionality

### Key Features
1. **Proper Niagara Integration**: Uses Niagara type system and auto-generated code
2. **TestNG Annotations**: Uses `@Test` for test methods
3. **Comprehensive Testing**: Tests save, load, delete, and directory operations
4. **Error Handling**: Proper exception handling and cleanup

## Test Methods

### testProfileManagerSaveLoad()
- Creates a BConnectionProfile with test data
- Saves it using ProfileManager
- Loads it back and verifies data integrity
- Cleans up by deleting the test profile

### testProfileManagerDirectoryOperations()
- Tests directory creation and existence
- Tests profile listing functionality
- Verifies directory structure

## Build Configuration

### datasync-wb.gradle.kts
```kotlin
dependencies {
  // Test Niagara module dependencies
  moduleTestImplementation("Tridium:test-wb")
}
```

### moduleTest-include.xml
```xml
<types>
  <type name="ProfileManagerTest" class="com.mea.datasync.test.BProfileManagerTest"/>
</types>
```

## Running Tests

### Compile Tests
```bash
.\gradlew :datasync-wb:compileTestJava
```

### Build Module with Tests
```bash
.\gradlew :datasync-wb:jar
```

### Run Tests (when deployed to Niagara)
```bash
test datasync-wb
test datasync-wb:ProfileManagerTest
```

## Benefits of Proper Test Structure

1. **✅ Niagara Compliance**: Follows official Niagara conventions
2. **✅ IDE Integration**: Proper test discovery and execution
3. **✅ Build Integration**: Tests are part of the build process
4. **✅ Isolation**: Test code is separate from production code
5. **✅ Maintainability**: Easy to add more tests following the same pattern

## Next Steps

1. **Deploy Module**: Copy the JAR to Niagara modules directory
2. **Run Tests**: Execute tests using Niagara's test command
3. **Add More Tests**: Create additional test classes for other components
4. **CI Integration**: Include tests in continuous integration pipeline

## Test Coverage

The current test covers:
- ✅ ProfileManager instantiation
- ✅ Profile creation and property setting
- ✅ JSON serialization and file writing
- ✅ Profile loading and deserialization
- ✅ Profile deletion
- ✅ Directory operations
- ✅ Error handling and cleanup

## Future Test Additions

Consider adding tests for:
- BConnectionProfile property validation
- BDataSyncProfileView functionality
- Integration tests with actual Niagara components
- Performance tests for large numbers of profiles
- Error scenarios and edge cases
