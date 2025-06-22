# PowerShell script to convert all .java files to .txt files in the niagara_source_code_txt directory
# This script recursively finds all .java files and renames them to .txt files while preserving
# the directory structure and file contents.

param(
    [string]$TargetDirectory = "niagara_source_code_txt"
)

function Convert-JavaToTxt {
    param(
        [string]$RootDirectory
    )
    
    # Check if directory exists
    if (-not (Test-Path $RootDirectory -PathType Container)) {
        Write-Error "Error: Directory '$RootDirectory' does not exist."
        return $false
    }
    
    Write-Host "Converting .java files to .txt files in: $RootDirectory" -ForegroundColor Green
    Write-Host ("-" * 60) -ForegroundColor Gray
    
    # Find all .java files recursively
    $javaFiles = Get-ChildItem -Path $RootDirectory -Filter "*.java" -Recurse -File
    
    if ($javaFiles.Count -eq 0) {
        Write-Host "No .java files found in '$RootDirectory'" -ForegroundColor Yellow
        return $true
    }
    
    Write-Host "Found $($javaFiles.Count) .java files to convert..." -ForegroundColor Cyan
    
    $convertedCount = 0
    $failedCount = 0
    
    foreach ($javaFile in $javaFiles) {
        try {
            # Create the new .txt file path by changing the extension
            $txtFile = [System.IO.Path]::ChangeExtension($javaFile.FullName, ".txt")
            
            # Rename the file
            Rename-Item -Path $javaFile.FullName -NewName $txtFile -ErrorAction Stop
            
            $convertedCount++
            $relativePath = $javaFile.FullName.Replace((Get-Item $RootDirectory).FullName, "").TrimStart('\')
            $newRelativePath = $relativePath -replace '\.java$', '.txt'
            Write-Host "Converted: $relativePath -> $newRelativePath" -ForegroundColor White
        }
        catch {
            $failedCount++
            $relativePath = $javaFile.FullName.Replace((Get-Item $RootDirectory).FullName, "").TrimStart('\')
            Write-Host "Failed to convert $relativePath`: $($_.Exception.Message)" -ForegroundColor Red
        }
    }
    
    Write-Host ""
    Write-Host "Conversion complete!" -ForegroundColor Green
    Write-Host "Successfully converted: $convertedCount files" -ForegroundColor Green
    
    if ($failedCount -gt 0) {
        Write-Host "Failed conversions: $failedCount files" -ForegroundColor Red
        return $false
    }
    
    return $true
}

# Main execution
Write-Host "Java to TXT File Converter" -ForegroundColor Magenta
Write-Host "=========================" -ForegroundColor Magenta
Write-Host ""

$success = Convert-JavaToTxt -RootDirectory $TargetDirectory

if ($success) {
    Write-Host ""
    Write-Host "All conversions completed successfully!" -ForegroundColor Green
    exit 0
} else {
    Write-Host ""
    Write-Host "Some conversions failed. Please check the error messages above." -ForegroundColor Red
    exit 1
}
