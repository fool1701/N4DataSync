// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.File;

/**
 * BExcelDataSourceConnection provides connection functionality specifically
 * for Excel (.xlsx, .xls) data sources. This concrete implementation of
 * BAbstractDataSourceConnection handles Excel-specific connection details
 * and testing logic.
 * 
 * Features:
 * - File path validation and existence checking
 * - Excel format validation
 * - File accessibility testing
 * - Excel-optimized auto-check settings
 * - Support for both .xlsx and .xls formats
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "new BExcelConnectionDetails()",
  flags = Flags.READONLY | Flags.SUMMARY,
  override = true
)
public class BExcelDataSourceConnection extends BAbstractDataSourceConnection {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BExcelDataSourceConnection(2047217648)1.0$ @*/
/* Generated Wed Jul 05 11:00:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionDetails"

  /**
   * Slot for the {@code connectionDetails} property.
   * @see #getConnectionDetails
   * @see #setConnectionDetails
   */
  public static final Property connectionDetails = newProperty(0, new BExcelConnectionDetails(), null);

  /**
   * Get the {@code connectionDetails} property.
   * @see #connectionDetails
   */
  public BExcelConnectionDetails getConnectionDetails() { return (BExcelConnectionDetails)get(connectionDetails); }

  /**
   * Set the {@code connectionDetails} property.
   * @see #connectionDetails
   */
  public void setConnectionDetails(BExcelConnectionDetails v) { set(connectionDetails, v, null); }

  //endregion Property "connectionDetails"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExcelDataSourceConnection.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BExcelDataSourceConnection() {
    super();
    
    // Set Excel-optimized auto-check configuration
    setAutoCheckConfig(BAutoCheckConfig.createExcelDefault());
    
    System.out.println("📊 Excel Data Source Connection created");
  }

////////////////////////////////////////////////////////////////
// Abstract Method Implementations
////////////////////////////////////////////////////////////////

  @Override
  protected ConnectionTestResult performConnectionTest() {
    BExcelConnectionDetails details = getConnectionDetails();
    
    try {
      // Validate configuration first
      BConnectionDetails.ValidationResult validation = details.validateConfiguration();
      if (!validation.isValid()) {
        return new ConnectionTestResult(false, "Configuration invalid: " + validation.getErrorMessage());
      }
      
      String filePath = details.getFilePath();
      
      // Check if file path is provided
      if (filePath == null || filePath.trim().isEmpty()) {
        return new ConnectionTestResult(false, "File path is not specified");
      }
      
      // Check if file exists
      File file = new File(filePath);
      if (!file.exists()) {
        return new ConnectionTestResult(false, "File does not exist: " + filePath);
      }
      
      // Check if file is readable
      if (!file.canRead()) {
        return new ConnectionTestResult(false, "File is not readable: " + filePath);
      }
      
      // Check if it's a file (not a directory)
      if (!file.isFile()) {
        return new ConnectionTestResult(false, "Path is not a file: " + filePath);
      }
      
      // Check file extension
      if (!isExcelFile(filePath)) {
        return new ConnectionTestResult(false, "File is not a valid Excel file (.xlsx or .xls): " + filePath);
      }
      
      // Check file size (basic sanity check)
      long fileSize = file.length();
      if (fileSize == 0) {
        return new ConnectionTestResult(false, "File is empty: " + filePath);
      }
      
      // If we get here, the connection test passed
      return new ConnectionTestResult(true, "Excel file connection successful");
      
    } catch (SecurityException e) {
      return new ConnectionTestResult(false, "Security error accessing file: " + e.getMessage());
    } catch (Exception e) {
      return new ConnectionTestResult(false, "Unexpected error testing Excel connection: " + e.getMessage());
    }
  }

  @Override
  public String getDataSourceTypeName() {
    return "Excel";
  }

  @Override
  public String getConnectionSummary() {
    BExcelConnectionDetails details = getConnectionDetails();
    StringBuilder summary = new StringBuilder();
    
    summary.append("Excel Connection: ");
    summary.append(details.getDisplayName());
    
    String filePath = details.getFilePath();
    if (filePath != null && !filePath.trim().isEmpty()) {
      summary.append(" (").append(filePath).append(")");
    }
    
    summary.append(" - Status: ").append(getConnectionStatus());
    
    if (!getLastConnectionTest().isNull()) {
      summary.append(", Last Tested: ").append(getLastConnectionTest());
    }
    
    return summary.toString();
  }

////////////////////////////////////////////////////////////////
// Excel-Specific Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Check if the file has a valid Excel extension.
   * 
   * @param filePath the file path to check
   * @return true if the file has .xlsx or .xls extension
   */
  private boolean isExcelFile(String filePath) {
    if (filePath == null) return false;
    
    String lowerPath = filePath.toLowerCase();
    return lowerPath.endsWith(".xlsx") || lowerPath.endsWith(".xls");
  }

  /**
   * Get the Excel file format based on extension.
   * 
   * @return "XLSX" or "XLS" based on file extension, or "Unknown" if invalid
   */
  public String getExcelFormat() {
    BExcelConnectionDetails details = getConnectionDetails();
    String filePath = details.getFilePath();
    
    if (filePath == null) return "Unknown";
    
    String lowerPath = filePath.toLowerCase();
    if (lowerPath.endsWith(".xlsx")) {
      return "XLSX";
    } else if (lowerPath.endsWith(".xls")) {
      return "XLS";
    } else {
      return "Unknown";
    }
  }

  /**
   * Get file information for display purposes.
   * 
   * @return file information string or error message
   */
  public String getFileInfo() {
    BExcelConnectionDetails details = getConnectionDetails();
    String filePath = details.getFilePath();
    
    if (filePath == null || filePath.trim().isEmpty()) {
      return "No file specified";
    }
    
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        return "File does not exist";
      }
      
      StringBuilder info = new StringBuilder();
      info.append("Format: ").append(getExcelFormat());
      info.append(", Size: ").append(formatFileSize(file.length()));
      info.append(", Modified: ").append(BAbsTime.make(file.lastModified()));
      
      return info.toString();
      
    } catch (Exception e) {
      return "Error reading file info: " + e.getMessage();
    }
  }

  /**
   * Format file size in human-readable format.
   * 
   * @param bytes file size in bytes
   * @return formatted file size string
   */
  private String formatFileSize(long bytes) {
    if (bytes < 1024) return bytes + " B";
    if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
    if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
  }
}
