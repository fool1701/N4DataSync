@echo off
REM N4-DataSync Test Runner Script
REM This script provides easy access to both testing approaches

echo.
echo ========================================
echo N4-DataSync Test Runner
echo ========================================
echo.

if "%1"=="gradle" goto gradle_approach
if "%1"=="direct" goto direct_approach
if "%1"=="help" goto show_help
if "%1"=="" goto show_menu

:show_menu
echo Choose testing approach:
echo.
echo 1. Direct Test Command (Recommended - Always Works)
echo 2. Gradle niagaraTest (Enterprise - Requires Certificate)
echo 3. Show Help
echo.
set /p choice="Enter choice (1-3): "

if "%choice%"=="1" goto direct_approach
if "%choice%"=="2" goto gradle_approach
if "%choice%"=="3" goto show_help
echo Invalid choice. Please enter 1, 2, or 3.
goto show_menu

:direct_approach
echo.
echo ========================================
echo Using Direct Test Command (Official Niagara Approach)
echo ========================================
echo.

echo Step 1: Building test module...
call gradlew :datasync-wb:moduleTestJar
if errorlevel 1 (
    echo ERROR: Failed to build test module
    pause
    exit /b 1
)

echo.
echo Step 2: Running tests...
echo Command: test datasync-wb -groups:unit,datasync -v:5 -output:build\test-results
echo.

C:\Honeywell\OptimizerSupervisor-N4.13.3.48\bin\test datasync-wb -groups:unit,datasync -v:5 -output:build\test-results

echo.
echo ========================================
echo Test Results Summary
echo ========================================
echo.
echo Results saved to: build\test-results\
echo.
echo For detailed analysis:
echo - Check console output above
echo - Review test result files in build\test-results\
echo.
pause
exit /b 0

:gradle_approach
echo.
echo ========================================
echo Using Gradle niagaraTest (Enterprise Approach)
echo ========================================
echo.

echo Step 1: Checking certificate...
if not exist "Niagara4Modules.pem" (
    echo Certificate not found. Exporting...
    call gradlew exportCertificate --profile-path "%%USERPROFILE%%\.tridium\security\niagara.signing.xml" --alias "Niagara4Modules" --pem-file Niagara4Modules.pem
)

echo.
echo Step 2: Running Gradle tests...
echo Command: gradlew :datasync-wb:clean :datasync-wb:niagaraTest
echo.

call gradlew :datasync-wb:clean :datasync-wb:niagaraTest

if errorlevel 1 (
    echo.
    echo ========================================
    echo CERTIFICATE VALIDATION ERROR
    echo ========================================
    echo.
    echo The Gradle approach failed due to certificate validation.
    echo.
    echo SOLUTION OPTIONS:
    echo.
    echo 1. Import certificate manually:
    echo    - Open Niagara Workbench
    echo    - Go to Tools ^> Certificate Management
    echo    - User Trust Store tab ^> Import
    echo    - Select: %CD%\Niagara4Modules.pem
    echo    - Alias: Niagara4Modules
    echo.
    echo 2. Use direct test command instead:
    echo    - Run: scripts\run-tests.bat direct
    echo.
    echo 3. See detailed instructions:
    echo    - Open: docs\CERTIFICATE_SETUP.md
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Test Results Summary
echo ========================================
echo.
echo HTML Report: datasync\datasync-wb\build\reports\tests\niagara\index.html
echo XML Results: datasync\datasync-wb\build\test-results\niagara\
echo Coverage: datasync\datasync-wb\build\reports\jacoco\niagaraTest\html\index.html
echo.
pause
exit /b 0

:show_help
echo.
echo ========================================
echo N4-DataSync Test Runner Help
echo ========================================
echo.
echo USAGE:
echo   scripts\run-tests.bat [approach]
echo.
echo APPROACHES:
echo   direct    - Use direct test command (recommended, always works)
echo   gradle    - Use Gradle niagaraTest (enterprise, requires certificate)
echo   help      - Show this help message
echo.
echo EXAMPLES:
echo   scripts\run-tests.bat direct
echo   scripts\run-tests.bat gradle
echo   scripts\run-tests.bat
echo.
echo TESTING APPROACHES COMPARISON:
echo.
echo Direct Test Command:
echo   ✅ Official Niagara TestNG approach
echo   ✅ Works with self-signed certificates
echo   ✅ Fast execution and feedback
echo   ✅ Flexible command-line options
echo   ✅ Perfect for development
echo.
echo Gradle niagaraTest:
echo   ✅ Enterprise-grade security validation
echo   ✅ Integrates with build pipeline
echo   ✅ Code coverage with JaCoCo
echo   ✅ Comprehensive HTML/XML reports
echo   ❌ Requires certificate trust configuration
echo.
echo DOCUMENTATION:
echo   - Testing Guide: docs\TESTING.md
echo   - Certificate Setup: docs\CERTIFICATE_SETUP.md
echo   - Official Niagara Docs: docs\Niagara\Development\
echo.
pause
exit /b 0
