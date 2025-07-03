# N4-DataSync Scripts

This directory contains utility scripts for N4-DataSync development and deployment.

## Available Scripts

### `build-and-deploy.bat`

**Purpose**: Automated build and deployment script for Windows development.

**Usage**:
```bash
# From project root
scripts\build-and-deploy.bat
```

**What it does**:
1. Builds the N4-DataSync module using Gradle
2. Deploys the module to your configured Niagara installation
3. Provides success/failure feedback
4. Shows next steps for testing

**Requirements**:
- `niagara_home` property set in `gradle.properties` or `NIAGARA_HOME` environment variable
- Niagara installation accessible
- Write permissions to Niagara modules directory

## Alternative Commands

Instead of using scripts, you can use Gradle commands directly:

```bash
# Build only
gradlew :datasync-wb:jar

# Build and deploy
gradlew :datasync-wb:buildAndDeploy

# Clean and rebuild
gradlew :datasync-wb:clean :datasync-wb:jar

# Run tests
gradlew :datasync-wb:niagaraTest
```

## Cross-Platform Development

The batch scripts are Windows-specific. For cross-platform development:

### Linux/macOS Equivalent:
```bash
# Build and deploy
./gradlew :datasync-wb:buildAndDeploy

# Check status
./gradlew :datasync-wb:jar && echo "Build successful"
```

### PowerShell (Windows):
```powershell
# Build and deploy
.\gradlew.bat :datasync-wb:buildAndDeploy

# Check status with error handling
if ($LASTEXITCODE -eq 0) {
    Write-Host "Build successful" -ForegroundColor Green
} else {
    Write-Host "Build failed" -ForegroundColor Red
}
```

## Best Practices

1. **Use Gradle tasks directly** when possible for better cross-platform compatibility
2. **Set environment variables** instead of hardcoding paths
3. **Check build output** for deployment location confirmation
4. **Use IDE integration** for development builds
5. **Use scripts for automation** in CI/CD or repetitive tasks

## Troubleshooting

If scripts fail:

1. **Check Gradle configuration**: Verify `niagara_home` is set correctly
2. **Check permissions**: Ensure write access to Niagara modules directory
3. **Check Niagara installation**: Verify Niagara is properly installed
4. **Use manual commands**: Try Gradle commands directly for better error messages

For detailed troubleshooting, see [TROUBLESHOOTING.md](../docs/TROUBLESHOOTING.md).
