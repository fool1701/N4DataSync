# ProfileService Testing Guide

## Overview

This document describes testing approaches for the ProfileService architecture, including both automated TestNG tests and manual verification procedures.

## Automated Testing (Recommended)

### Running ProfileService Tests

```bash
# Run all ProfileService tests
./gradlew test --tests "*BProfileServiceTest*"

# Run specific test groups
./gradlew test --tests "*BProfileServiceTest*" -Dgroups="datasync,unit"

# Run CRUD operation tests only
./gradlew test --tests "*BProfileServiceTest*" -Dgroups="crud"
```

### Test Coverage

The `BProfileServiceTest` class covers:

- ✅ **CRUD Operations**: Create, Read, Update, Delete profiles
- ✅ **Validation**: Invalid data handling, duplicate prevention
- ✅ **Error Handling**: Non-existent profile operations
- ✅ **Persistence**: Data integrity across operations
- ✅ **Component Tree**: Synchronization verification

### Test Groups

| Group | Purpose | Tests |
|-------|---------|-------|
| `datasync` | All DataSync module tests | All |
| `unit` | Unit tests (fast, isolated) | All |
| `crud` | Create/Read/Update/Delete operations | Basic operations |
| `validation` | Input validation tests | Invalid data scenarios |
| `error-handling` | Error condition tests | Exception scenarios |
| `persistence` | Data persistence tests | JSON storage verification |

## Manual Testing (Fallback)

If automated tests fail due to environment issues, you can manually verify:

### 1. Basic Functionality Test

```java
// In Niagara Workbench Console:
// Navigate to: tool:com.mea.datasync.ui.BDataSyncTool|slot:/

// Check initial state
getProfileCount()

// Create test profile
BConnectionProfile profile = new BConnectionProfile();
profile.setSourceType("Excel");
profile.setSourcePath("C:\\Test\\Manual.xlsx");
createProfile("Manual_Test", profile);

// Verify creation
getProfileCount()
getAllProfiles()

// Clean up
deleteProfile("Manual_Test");
```

### 2. UI Integration Test

1. Open **Tools** → **DataSyncTool**
2. Verify profiles display in table
3. Check that operations work through UI
4. Confirm persistence across workbench restarts

## Test Data Management

### Test Profile Naming Convention

All test profiles use prefix `Test_` followed by operation and timestamp:
- `Test_Create_1234567890`
- `Test_Update_1234567890`
- `Test_Delete_1234567890`

### Cleanup Strategy

- `@AfterClass` automatically removes test profiles
- Test profiles are identified by `Test_` prefix
- Manual cleanup available via `deleteProfile()` method

## Troubleshooting Tests

### Common Issues

1. **Module Signature Validation Errors**
   ```
   SEVERE: Module datasync-wbTest failed signature validation
   ```
   **Solution**: Use proper Niagara signing certificate (see `/docs/CERTIFICATE_SETUP.md`)

2. **ProfileService Not Initialized**
   ```
   ProfileService not initialized
   ```
   **Solution**: Ensure `BDataSyncTool.started()` is called before tests

3. **File Permission Errors**
   ```
   Failed to save profile to JSON
   ```
   **Solution**: Check write permissions to Niagara user directory

### Debug Commands

```java
// Check tool state
testTool.getProfileCount()
testTool.getAllProfiles()

// Check component tree
testTool.getChildren(BConnectionProfile.class)

// Manual cleanup
testTool.deleteProfile("Test_ProfileName")
```

## Integration with CI/CD

### GitHub Actions Integration

The tests integrate with the existing quality check workflow:

```yaml
# In .github/workflows/quality-check.yml
- name: Run ProfileService tests
  run: ./gradlew test --tests "*BProfileServiceTest*"
```

### Test Reports

Test results available at:
- **HTML Report**: `build/reports/tests/test/index.html`
- **XML Results**: `build/test-results/test/`

## Best Practices

### Writing Additional Tests

When adding new ProfileService functionality:

1. **Follow Niagara conventions**:
   ```java
   @Test(groups = {"datasync", "unit", "your-feature"})
   public void testYourFeature() {
       // Given-When-Then pattern
   }
   ```

2. **Use descriptive test names**:
   - `testCreateProfileWithValidData()`
   - `testUpdateProfileWithInvalidHost()`
   - `testDeleteProfileCleansUpResources()`

3. **Include proper cleanup**:
   ```java
   @AfterMethod
   public void cleanupTestData() {
       // Remove test-specific data
   }
   ```

4. **Test both success and failure cases**:
   - Happy path scenarios
   - Edge cases and error conditions
   - Invalid input handling

### Test Maintenance

- **Review tests** when ProfileService API changes
- **Update test data** to reflect real-world scenarios  
- **Monitor test performance** - keep unit tests fast
- **Group tests logically** for selective execution

## Next Steps

1. **Run the automated tests** to verify ProfileService functionality
2. **Add integration tests** for UI components
3. **Implement performance tests** for large profile sets
4. **Add security tests** for credential handling (Issue #1)
5. **Create end-to-end tests** for complete workflows
