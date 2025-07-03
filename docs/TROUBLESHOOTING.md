# N4-DataSync Troubleshooting Guide

## Common Issues and Solutions

### Installation Issues

#### ❌ "DataSync not appearing in Tools menu"

**Symptoms**: After installing the module, DataSync doesn't appear in Workbench Tools menu.

**Solutions**:
1. **Restart Workbench completely** (most common fix)
2. **Check module installation**:
   - Go to Tools → Software Manager
   - Verify "datasync-wb" is listed and enabled
3. **Check Niagara version**: Requires N4.11 or later
4. **Verify module file**: Ensure you have the correct `.jar` file

#### ❌ "Module failed signature validation"

**Symptoms**: Error during installation about certificate validation.

**Solutions**:
1. **Import certificate**: Follow [Certificate Setup Guide](CERTIFICATE_SETUP.md)
2. **Export fresh certificate**:
   ```bash
   gradlew exportCertificate --alias "Niagara4Modules" --pem-file Niagara4Modules.pem
   ```
3. **Import in Workbench**: Tools → Certificate Management → User Trust Store

### Connection Issues

#### ❌ "Cannot connect to station"

**Symptoms**: Profile creation fails with connection errors.

**Solutions**:
1. **Check station status**: Ensure target station is running
2. **Verify credentials**: Test username/password in Workbench
3. **Check network connectivity**: Ping the station IP
4. **Verify Fox port**: Default is 1911, check firewall
5. **Test connection manually**: Connect via Workbench first

#### ❌ "Authentication failed"

**Symptoms**: Connection attempts fail with authentication errors.

**Solutions**:
1. **Verify credentials**: Check username/password
2. **Check user permissions**: User needs component creation rights
3. **Test in Workbench**: Try connecting manually first
4. **Check station security**: Verify security settings allow connections

### Excel File Issues

#### ❌ "Cannot read Excel file"

**Symptoms**: Error when trying to read Excel data.

**Solutions**:
1. **Check file format**: Must be `.xlsx` (not `.xls`)
2. **Verify file path**: Use full absolute path
3. **Check file permissions**: Ensure file is not locked/open
4. **Validate sheet name**: Sheet name must match exactly (case-sensitive)

#### ❌ "Column not found" or "Invalid data format"

**Symptoms**: Excel file loads but data parsing fails.

**Solutions**:
1. **Check column headers**: Must match expected format exactly:
   ```
   Network Name | Device Name | Point Name | Point Type | Units | Default Value
   ```
2. **Remove empty rows**: Delete any blank rows in data
3. **Check data types**: Ensure numeric values are properly formatted
4. **Verify required columns**: All required columns must be present

### Component Creation Issues

#### ❌ "Component creation failed"

**Symptoms**: Sync process starts but components are not created.

**Solutions**:
1. **Check target path**: Verify path exists (e.g., "station:|slot:/Drivers")
2. **Verify permissions**: User needs write access to target location
3. **Check component conflicts**: Ensure components don't already exist
4. **Review station logs**: Check Niagara station logs for errors

#### ❌ "Partial component creation"

**Symptoms**: Some components created, others failed.

**Solutions**:
1. **Check individual errors**: Review sync report for specific failures
2. **Verify data consistency**: Ensure all required fields have values
3. **Check naming conflicts**: Duplicate names may cause failures
4. **Review target capacity**: Station may have resource limits

### Performance Issues

#### ❌ "Sync process very slow"

**Symptoms**: Component creation takes much longer than expected.

**Solutions**:
1. **Check network latency**: Test connection speed to station
2. **Reduce batch size**: Process smaller sets of components
3. **Verify station resources**: Check CPU/memory usage on target
4. **Close unnecessary applications**: Free up Workbench resources

#### ❌ "Workbench becomes unresponsive"

**Symptoms**: Workbench freezes during sync process.

**Solutions**:
1. **Increase Java heap size**: Modify Workbench startup parameters
2. **Process smaller batches**: Break large datasets into smaller chunks
3. **Close other views**: Reduce Workbench memory usage
4. **Restart Workbench**: Fresh start may resolve memory issues

### Profile Management Issues

#### ❌ "Profiles not saving"

**Symptoms**: Connection profiles don't persist between sessions.

**Solutions**:
1. **Check directory permissions**: Verify write access to user directory
2. **Check disk space**: Ensure sufficient space for profile files
3. **Verify user home**: Check Niagara user home directory setting
4. **Manual directory creation**: Create `datasync/profiles` directory manually

#### ❌ "Cannot load saved profiles"

**Symptoms**: Previously saved profiles don't appear.

**Solutions**:
1. **Check profile directory**: Verify files exist in correct location
2. **Check file permissions**: Ensure read access to profile files
3. **Validate JSON format**: Profile files may be corrupted
4. **Check Niagara user home**: Verify correct user directory

## System Status Verification

### Quick Status Check Commands

Use these Gradle commands to verify your installation:

```bash
# Check if module builds correctly
gradlew :datasync-wb:jar

# Build and deploy module
gradlew :datasync-wb:buildAndDeploy

# Run tests to verify functionality
gradlew :datasync-wb:niagaraTest

# Check all available tasks
gradlew tasks
```

### Manual Installation Verification

#### 1. Check Module Installation
1. **Open Niagara Workbench**
2. **Go to Tools → Software Manager**
3. **Look for "datasync-wb"** in the installed modules list
4. **Verify version** matches your build

#### 2. Check Module Availability
1. **In Workbench, go to Tools menu**
2. **Look for "DataSync"** option
3. **If missing**: Module not properly installed or registered

#### 3. Check Profile Storage
1. **Navigate to**: `{niagara_user_home}/datasync/profiles/`
2. **Windows example**: `C:\Users\{username}\Niagara4.x\{brand}\shared\datasync\profiles\`
3. **Directory created automatically** when first profile is saved

#### 4. Check Build Output
1. **Verify build artifacts exist**:
   - `datasync/datasync-wb/build/libs/datasync-wb.jar`
2. **If missing**: Run `gradlew :datasync-wb:jar`

## Diagnostic Information

### Collecting Debug Information

When reporting issues, include:

1. **Niagara Version**: Help → About Niagara Workbench
2. **Module Version**: Tools → Software Manager → datasync-wb
3. **Java Version**: Check Workbench startup logs
4. **Operating System**: Windows version and architecture
5. **Error Messages**: Exact error text and stack traces
6. **Log Files**: Workbench and station log files
7. **Build Output**: Results of `gradlew :datasync-wb:jar`

### Log File Locations

- **Workbench Logs**: `{niagara_user_home}/log/`
- **Station Logs**: `{station_home}/log/`
- **Test Logs**: `{niagara_user_home}/reports/testng/`

### Useful Commands

```bash
# Check Gradle tasks
gradlew tasks

# Run tests with verbose output
gradlew :datasync-wb:niagaraTest --verbosity 5

# Export certificate
gradlew exportCertificate --alias "Niagara4Modules" --pem-file cert.pem

# Check module status
gradlew :datasync-wb:jar
```

## Getting Additional Help

### Documentation Resources

- **[Quick Start Guide](QUICK_START.md)** - Basic setup
- **[Certificate Setup](CERTIFICATE_SETUP.md)** - Security configuration
- **[Testing Guide](TESTING.md)** - Development testing
- **[Developer Documentation](README.md)** - Complete developer guide

### Support Channels

- **Project Issues**: Check project repository for known issues
- **Niagara Community**: Niagara developer forums
- **Documentation**: Official Niagara documentation

### Before Reporting Issues

1. **Check this troubleshooting guide** first
2. **Search existing issues** in the project repository
3. **Try the quick start guide** to verify basic setup
4. **Collect diagnostic information** as listed above
5. **Provide clear reproduction steps** when reporting
