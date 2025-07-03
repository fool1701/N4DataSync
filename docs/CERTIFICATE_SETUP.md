# Certificate Setup for N4-DataSync Module

## Overview

N4-DataSync uses the **standard Niagara signing approach** for maximum security and compatibility. This eliminates the need for custom certificates and follows Niagara development best practices.

## How It Works

The module is configured to use Niagara's default signing profile located at:
```
%USERPROFILE%\.tridium\security\niagara.signing.xml
```

This automatically uses the **Niagara4Modules** certificate that can be trusted by Niagara Workbench.

## Setup Instructions

### 1. Export the Niagara4Modules Certificate

From your project root directory, run:

```bash
gradlew exportCertificate --alias "Niagara4Modules" --pem-file Niagara4Modules.pem
```

This exports the certificate that Niagara uses to sign modules.

### 2. Import Certificate into Niagara Workbench

1. **Open Niagara Workbench**
2. **Go to Tools → Certificate Management**
3. **Select "User Trust Store"**
4. **Click "Import"**
5. **Browse and select the exported `Niagara4Modules.pem` file**
6. **Click "OK" to import**

### 3. Build and Deploy Module

```bash
# Build the module
gradlew :datasync-wb:jar

# Build and deploy in one step
gradlew :datasync-wb:buildAndDeploy
```

## Troubleshooting

### "Could not validate cert chain" Error

If you see this error when loading the module:

1. **Verify certificate import**: Check that Niagara4Modules certificate is in the User Trust Store
2. **Re-export certificate**: Run the export command again to get a fresh certificate
3. **Restart Workbench**: Close and reopen Niagara Workbench after importing
4. **Check certificate validity**: Ensure the certificate hasn't expired

### Module Not Loading

1. **Check module location**: Verify the module JAR is in the correct Niagara modules directory
2. **Check file permissions**: Ensure Niagara can read the module file
3. **Review Niagara logs**: Check the Niagara console for detailed error messages

### Build Signing Errors

If you encounter signing errors during build:

1. **Verify Niagara installation**: Ensure `niagara_home` property is correctly set
2. **Check default profile**: Verify that `%USERPROFILE%\.tridium\security\niagara.signing.xml` exists
3. **Regenerate profile**: If needed, create a new default signing profile

## Security Benefits

✅ **No sensitive data in source control**: All certificates and keys remain local  
✅ **Standard Niagara certificates**: Uses trusted, well-tested signing infrastructure  
✅ **Cross-environment compatibility**: Works consistently across developer machines  
✅ **Automatic updates**: Benefits from Niagara's certificate management  
✅ **Best practices compliance**: Follows official Niagara development guidelines  

## Alternative: Manual Certificate Creation

If you need to create a fresh default signing profile:

```bash
gradlew createProfile --profile-path "%USERPROFILE%\.tridium\security\niagara.signing.xml"
gradlew generateCertificate --profile-path "%USERPROFILE%\.tridium\security\niagara.signing.xml" --alias "Niagara4Modules"
```

## Gradle niagaraTest Certificate Issue Resolution

### Current Status
- ✅ **Direct test command works**: `test datasync-wb` runs successfully
- ❌ **Gradle niagaraTest fails**: Certificate validation error prevents execution
- 📁 **Certificate exported**: `Niagara4Modules.pem` is available in project root

### Solution: Manual Certificate Import

To enable the Gradle approach (`gradlew :datasync-wb:niagaraTest`), follow these steps:

#### Step 1: Open Niagara Workbench
```bash
# Start Niagara Workbench
C:\Honeywell\OptimizerSupervisor-N4.13.3.48\bin\wb.exe
```

#### Step 2: Import Certificate
1. **Open Certificate Management**:
   - Go to `Tools` → `Certificate Management`

2. **Select User Trust Store**:
   - Click on the `User Trust Store` tab

3. **Import Certificate**:
   - Click `Import` button
   - Browse to: `C:\Users\Chen\IdeaProjects\datasync\Niagara4Modules.pem`
   - Select the file and click `Open`

4. **Set Certificate Alias**:
   - Enter alias: `Niagara4Modules`
   - Review certificate details
   - Click `OK` to import

#### Step 3: Verify Import
The certificate should now appear in the User Trust Store as:
```
Niagara4Modules (Chen@Chen)
```

#### Step 4: Test Gradle Approach
```bash
# Clean and test
gradlew :datasync-wb:clean :datasync-wb:niagaraTest
```

### Alternative: Continue Using Direct Test Command

If certificate import doesn't resolve the issue, the direct test command is perfectly valid:

```bash
# This approach works reliably and is officially supported
gradlew :datasync-wb:moduleTestJar
test datasync-wb -groups:unit,datasync -v:5
```

### Troubleshooting

#### If Certificate Import Fails
1. **Restart Workbench**: Close and reopen Niagara Workbench
2. **Re-export Certificate**: Run `gradlew exportCertificate` again
3. **Check File Permissions**: Ensure the .pem file is readable
4. **Verify Certificate**: Open the .pem file in a text editor to confirm it's valid

#### If Gradle Tests Still Fail
1. **Check Certificate Trust**: Verify the certificate appears in User Trust Store
2. **Clear Gradle Cache**: Run `gradlew clean` and try again
3. **Use Direct Command**: Fall back to the working direct test approach

## Support

For additional help:
- Check the Niagara Developer Documentation
- Review `docs/Development/` for build guidance
- Consult Niagara Community forums for signing issues
- Use the direct test command as a reliable alternative
