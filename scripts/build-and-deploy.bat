@echo off
echo ========================================
echo N4-DataSync Module Build and Deploy
echo ========================================
echo.

echo Building and deploying module...
call gradlew :datasync-wb:buildAndDeploy

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo SUCCESS: Module built and deployed!
    echo ========================================
    echo.
    echo The module has been deployed to your Niagara modules directory.
    echo Check the Gradle output above for the exact location.
    echo.
    echo You can now:
    echo 1. Open Niagara Workbench
    echo 2. Go to Tools ^> DataSync
    echo 3. Create and test connection profiles
    echo.
) else (
    echo.
    echo ========================================
    echo ERROR: Build or deployment failed!
    echo ========================================
    echo Please check the error messages above.
    echo.
)

pause
