# DataSync Persistence Implementation

## Overview

The DataSync tool now includes a robust persistence layer that stores connection profiles as JSON files in the Niagara shared user directory. This implementation follows Niagara best practices and provides a secure, portable solution for profile management.

## Architecture

### File Location Strategy
- **Base Directory**: `Sys.getNiagaraSharedUserHome() + "/N4DataSync/profiles/"`
- **Example Paths**:
  - Windows: `C:\Users\<username>\Niagara4.x\<brandId>\shared\N4DataSync\profiles\`
  - Linux: `/opt/niagara4/<brandId>/users/<username>/shared/N4DataSync/profiles/`

### Why This Location?
1. **✅ Security Compliant**: Full read/write permissions without Java Security Manager issues
2. **✅ User-Specific**: Each Niagara user gets their own profiles
3. **✅ Portable**: Profiles move with user backups/migrations
4. **✅ Upgrade-Safe**: Separate from Niagara installation files
5. **✅ No Conflicts**: Dedicated namespace prevents file collisions

## JSON Schema

Each connection profile is stored as a separate JSON file following this structure:

```json
{
  "profileName": "Building A HVAC",
  "sourceType": "Excel",
  "sourcePath": "C:\\Data\\BuildingA_HVAC.xlsx",
  "sheetName": "Equipment",
  "targetHost": "192.168.1.100",
  "targetUsername": "admin",
  "targetPath": "station:|slot:/Drivers",
  "status": "Success",
  "componentsCreated": 45,
  "lastError": "",
  "lastSyncTime": "2025-01-15T10:30:00.000Z"
}
```

## Implementation Classes

### ProfileManager
- **Location**: `com.mea.datasync.persistence.ProfileManager`
- **Purpose**: Handles all file I/O operations for connection profiles
- **Key Methods**:
  - `saveProfile(BConnectionProfile, String)`: Save profile to JSON file
  - `loadProfile(String)`: Load profile from JSON file
  - `listProfiles()`: Get list of available profile names
  - `deleteProfile(String)`: Delete a profile file
  - `profileExists(String)`: Check if profile exists

### Key Features
1. **Automatic Directory Creation**: Creates the profiles directory if it doesn't exist
2. **Filename Sanitization**: Removes invalid characters from profile names
3. **Error Handling**: Graceful handling of I/O errors without crashing the view
4. **JSON Serialization**: Uses Gson for robust JSON processing
5. **Type Conversion**: Handles conversion between Niagara types and JSON-friendly types

## Integration with BDataSyncProfileView

The view now automatically:
1. **Loads profiles on startup** from persistent storage
2. **Creates sample profiles** if none exist (first-time user experience)
3. **Displays profiles in table** with all persistent data
4. **Maintains data consistency** between UI and storage

## Sample Data

For first-time users, the system automatically creates three sample profiles:

1. **Building A HVAC**
   - Excel source: `C:\Data\BuildingA_HVAC.xlsx`
   - Sheet: Equipment
   - Target: 192.168.1.100
   - Status: Success (45 components created)

2. **Building B Lighting**
   - Excel source: `C:\Data\BuildingB_Lighting.xlsx`
   - Sheet: Points
   - Target: 192.168.1.101
   - Status: Error (23 components created)

3. **Chiller Plant**
   - Excel source: `C:\Data\ChillerPlant.xlsx`
   - Sheet: Chillers
   - Target: 192.168.1.102
   - Status: Never Synced (0 components)

## Dependencies

- **Gson 2.10.1**: Added to `datasync-wb.gradle.kts` for JSON processing
- **Niagara Sys**: Uses `Sys.getNiagaraSharedUserHome()` for directory location

## Security Considerations

### Current Implementation (V1)
- Passwords stored as plain text in JSON files
- Files protected by filesystem permissions
- Suitable for development and testing

### Future Enhancements
- Encrypt sensitive fields (passwords, API tokens)
- Use Niagara's built-in encryption utilities
- Add profile import/export with encryption

## File Management

### Naming Convention
- Profile files use sanitized names: `Building_A_HVAC.json`
- Invalid characters replaced with underscores
- Spaces converted to underscores

### Error Handling
- Missing files: Gracefully handled, profile not loaded
- Corrupt JSON: Error logged, profile skipped
- I/O errors: Logged but don't crash the application

## Testing

### Manual Testing
1. Open DataSync tool from Tools menu
2. Verify 3 sample profiles appear in table
3. Check that JSON files are created in the profiles directory
4. Restart Workbench and verify profiles persist

### File Locations to Check
- Windows: `%USERPROFILE%\Niagara4.x\<brand>\shared\N4DataSync\profiles\`
- Look for: `Building_A_HVAC.json`, `Building_B_Lighting.json`, `Chiller_Plant.json`

## Future Enhancements

1. **Profile Import/Export**: Allow users to share profiles
2. **Backup/Restore**: Automatic profile backups
3. **Encryption**: Secure storage of sensitive data
4. **Validation**: JSON schema validation on load
5. **Migration**: Handle schema changes between versions
6. **Audit Trail**: Track profile changes and usage

## Troubleshooting

### Common Issues
1. **Profiles not appearing**: Check file permissions on shared directory
2. **JSON errors**: Validate JSON syntax in profile files
3. **Directory not created**: Verify Niagara user has write permissions

### Debug Information
- Profile directory path logged on startup
- Save/load operations logged to console
- Error messages include full exception details
