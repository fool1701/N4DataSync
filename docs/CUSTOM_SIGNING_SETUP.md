# Custom Signing Profile Setup for N4-DataSync

## Overview

N4-DataSync uses a custom signing profile instead of the default Niagara signing profile. This provides better control over certificate management and avoids conflicts with other Niagara projects.

## Custom Signing Profile Details

- **Profile File**: `datasync.signing.xml` (in project root)
- **Certificate Alias**: `DataSyncModules`
- **Certificate File**: `DataSyncModules.pem` (exported certificate)
- **Organization**: MEA / N4-DataSync Project
- **Purpose**: Development and testing of N4-DataSync modules

## How It Works

### 1. Custom Profile Creation
The custom signing profile was created using:
```bash
gradlew createProfile --profile-path "datasync.signing.xml"
gradlew generateCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules"
```

### 2. Build Configuration
The root `build.gradle.kts` is configured to use the custom profile:
```kotlin
niagaraSigning {
  signingProfileFile.set(project.layout.projectDirectory.file("datasync.signing.xml"))
  aliases.set(listOf("DataSyncModules"))
}
```

### 3. Certificate Export
The certificate is exported for import into Niagara:
```bash
gradlew exportCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules" --pem-file "DataSyncModules.pem"
```

## Testing Approaches

### Method 1: Direct Test Command (Recommended for Development)
This approach works reliably with the custom signing:

```bash
# Build test module
gradlew :datasync-wb:moduleTestJar

# Run tests directly
test datasync-wb -groups:unit,datasync
```

**Status**: ✅ **Working** - Tests run successfully with custom signing

### Method 2: Gradle niagaraTest (For CI/CD)
This approach requires certificate import:

```bash
gradlew :datasync-wb:niagaraTest
```

**Status**: ⚠️ **Requires Certificate Import** - See below for setup

## Certificate Import for Gradle Testing

To use the Gradle `niagaraTest` approach, you need to import the custom certificate:

### Option 1: Manual Import via Niagara Workbench
1. Open Niagara Workbench
2. Go to **Tools → Certificate Management**
3. Select **User Trust Store** tab
4. Click **Import**
5. Browse to and select `DataSyncModules.pem`
6. Enter alias: `DataSyncModules`
7. Click **OK**

### Option 2: Command Line Import (If Available)
```bash
# This may work depending on your Niagara installation
niagara -importCert DataSyncModules.pem -alias DataSyncModules
```

## Verification

### Check Certificate Import
After importing, verify the certificate appears in the Niagara Workbench User Trust Store.

### Test Both Approaches
1. **Direct Test**: `test datasync-wb` should work immediately
2. **Gradle Test**: `gradlew :datasync-wb:niagaraTest` should work after certificate import

## Benefits of Custom Signing

### ✅ Advantages
- **Project Isolation**: No conflicts with other Niagara projects
- **Version Control**: Signing profile is part of the project
- **Team Consistency**: All developers use the same certificate
- **Development Focus**: Optimized for development workflow

### ⚠️ Considerations
- **Certificate Import**: Gradle testing requires manual certificate import
- **Trust Management**: Each developer needs to import the certificate
- **Renewal**: Certificate expires after 365 days (can be regenerated)

## Troubleshooting

### Build Issues
If you see signing errors during build:
1. Verify `datasync.signing.xml` exists in project root
2. Check that the `DataSyncModules` alias exists in the profile
3. Regenerate certificate if needed:
   ```bash
   gradlew generateCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules"
   ```

### Test Issues
If Gradle tests fail with certificate validation:
1. Export the certificate: `gradlew exportCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules" --pem-file "DataSyncModules.pem"`
2. Import `DataSyncModules.pem` into Niagara Workbench User Trust Store
3. Restart Niagara Workbench
4. Try the Gradle test again

### Certificate Renewal
When the certificate expires (after 365 days):
1. Regenerate: `gradlew generateCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules"`
2. Export: `gradlew exportCertificate --profile-path "datasync.signing.xml" --alias "DataSyncModules" --pem-file "DataSyncModules.pem"`
3. Re-import into Niagara Workbench

## Security Notes

- The custom certificate is for **development purposes only**
- The certificate includes "For Development Purposes Only Do Not Distribute" in the organizational unit
- For production deployment, use proper CA-signed certificates
- The signing profile contains sensitive key material - keep it secure

## Files Created

- `datasync.signing.xml` - Custom signing profile (contains private keys)
- `DataSyncModules.pem` - Exported certificate for import
- Updated `build.gradle.kts` - Configured to use custom profile

## Next Steps

1. **For Development**: Use direct test command approach
2. **For CI/CD**: Set up automated certificate import in build pipeline
3. **For Production**: Consider using CA-signed certificates
4. **For Team**: Document certificate import process for new developers
