// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.File;

/**
 * BExcelConnectionDetails stores Excel-specific connection configuration.
 * This component extends BConnectionDetails to provide Excel file-specific
 * properties and validation logic.
 * 
 * Properties specific to Excel connections:
 * - File path to the Excel file
 * - Default worksheet selection
 * - Read-only mode settings
 * - File format preferences
 */
@NiagaraType
@NiagaraProperty(
  name = "filePath",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "defaultWorksheet",
  type = "String",
  defaultValue = "Sheet1"
)
@NiagaraProperty(
  name = "readOnlyMode",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "allowedFormats",
  type = "String",
  defaultValue = "XLSX,XLS"
)
public class BExcelConnectionDetails extends BConnectionDetails {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BExcelConnectionDetails(437176641)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "filePath"

  /**
   * Slot for the {@code filePath} property.
   * @see #getFilePath
   * @see #setFilePath
   */
  public static final Property filePath = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code filePath} property.
   * @see #filePath
   */
  public String getFilePath() { return getString(filePath); }

  /**
   * Set the {@code filePath} property.
   * @see #filePath
   */
  public void setFilePath(String v) { setString(filePath, v, null); }

  //endregion Property "filePath"

  //region Property "defaultWorksheet"

  /**
   * Slot for the {@code defaultWorksheet} property.
   * @see #getDefaultWorksheet
   * @see #setDefaultWorksheet
   */
  public static final Property defaultWorksheet = newProperty(0, BString.make("Sheet1"), null);

  /**
   * Get the {@code defaultWorksheet} property.
   * @see #defaultWorksheet
   */
  public String getDefaultWorksheet() { return getString(defaultWorksheet); }

  /**
   * Set the {@code defaultWorksheet} property.
   * @see #defaultWorksheet
   */
  public void setDefaultWorksheet(String v) { setString(defaultWorksheet, v, null); }

  //endregion Property "defaultWorksheet"

  //region Property "readOnlyMode"

  /**
   * Slot for the {@code readOnlyMode} property.
   * @see #getReadOnlyMode
   * @see #setReadOnlyMode
   */
  public static final Property readOnlyMode = newProperty(0, BBoolean.TRUE.as(BBoolean.class).getBoolean(), null);

  /**
   * Get the {@code readOnlyMode} property.
   * @see #readOnlyMode
   */
  public boolean getReadOnlyMode() { return getBoolean(readOnlyMode); }

  /**
   * Set the {@code readOnlyMode} property.
   * @see #readOnlyMode
   */
  public void setReadOnlyMode(boolean v) { setBoolean(readOnlyMode, v, null); }

  //endregion Property "readOnlyMode"

  //region Property "allowedFormats"

  /**
   * Slot for the {@code allowedFormats} property.
   * @see #getAllowedFormats
   * @see #setAllowedFormats
   */
  public static final Property allowedFormats = newProperty(0, BString.make("XLSX,XLS"), null);

  /**
   * Get the {@code allowedFormats} property.
   * @see #allowedFormats
   */
  public String getAllowedFormats() { return getString(allowedFormats); }

  /**
   * Set the {@code allowedFormats} property.
   * @see #allowedFormats
   */
  public void setAllowedFormats(String v) { setString(allowedFormats, v, null); }

  //endregion Property "allowedFormats"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExcelConnectionDetails.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BExcelConnectionDetails() {
    super();
    System.out.println("📊 Excel Connection Details created");
  }

////////////////////////////////////////////////////////////////
// Abstract Method Implementations
////////////////////////////////////////////////////////////////

  @Override
  public String getConnectionSummary() {
    StringBuilder summary = new StringBuilder();
    summary.append("Excel File: ");
    
    String path = getFilePath();
    if (path != null && !path.trim().isEmpty()) {
      // Show just the filename for brevity
      File file = new File(path);
      summary.append(file.getName());
      summary.append(" (").append(path).append(")");
    } else {
      summary.append("No file specified");
    }
    
    summary.append(", Worksheet: ").append(getDefaultWorksheet());
    summary.append(", Mode: ").append(getReadOnlyMode() ? "Read-Only" : "Read-Write");
    
    return summary.toString();
  }

  @Override
  public ValidationResult validateConfiguration() {
    // Check file path
    String path = getFilePath();
    if (path == null || path.trim().isEmpty()) {
      return ValidationResult.error("File path is required");
    }
    
    // Check if path looks like a valid file path
    if (!isValidFilePath(path)) {
      return ValidationResult.error("Invalid file path format: " + path);
    }
    
    // Check file extension
    if (!hasValidExcelExtension(path)) {
      return ValidationResult.error("File must have .xlsx or .xls extension: " + path);
    }
    
    // Check worksheet name
    String worksheet = getDefaultWorksheet();
    if (worksheet == null || worksheet.trim().isEmpty()) {
      return ValidationResult.error("Default worksheet name is required");
    }
    
    // Check allowed formats
    String formats = getAllowedFormats();
    if (formats == null || formats.trim().isEmpty()) {
      return ValidationResult.error("Allowed formats must be specified");
    }
    
    return ValidationResult.success();
  }

////////////////////////////////////////////////////////////////
// Excel-Specific Methods
////////////////////////////////////////////////////////////////

  /**
   * Check if the file path has a valid Excel extension.
   * 
   * @param path the file path to check
   * @return true if the path has .xlsx or .xls extension
   */
  public boolean hasValidExcelExtension(String path) {
    if (path == null) return false;
    
    String lowerPath = path.toLowerCase();
    return lowerPath.endsWith(".xlsx") || lowerPath.endsWith(".xls");
  }

  /**
   * Get the Excel format based on file extension.
   * 
   * @return "XLSX", "XLS", or null if invalid
   */
  public String getExcelFormat() {
    String path = getFilePath();
    if (path == null) return null;
    
    String lowerPath = path.toLowerCase();
    if (lowerPath.endsWith(".xlsx")) {
      return "XLSX";
    } else if (lowerPath.endsWith(".xls")) {
      return "XLS";
    } else {
      return null;
    }
  }

  /**
   * Check if the specified format is allowed.
   * 
   * @param format the format to check (XLSX or XLS)
   * @return true if the format is in the allowed formats list
   */
  public boolean isFormatAllowed(String format) {
    if (format == null) return false;
    
    String allowedFormats = getAllowedFormats();
    if (allowedFormats == null) return false;
    
    return allowedFormats.toUpperCase().contains(format.toUpperCase());
  }

  /**
   * Get the file name without path.
   * 
   * @return filename or empty string if no path set
   */
  public String getFileName() {
    String path = getFilePath();
    if (path == null || path.trim().isEmpty()) {
      return "";
    }
    
    File file = new File(path);
    return file.getName();
  }

  /**
   * Get the directory containing the Excel file.
   * 
   * @return directory path or empty string if no path set
   */
  public String getFileDirectory() {
    String path = getFilePath();
    if (path == null || path.trim().isEmpty()) {
      return "";
    }
    
    File file = new File(path);
    String parent = file.getParent();
    return parent != null ? parent : "";
  }

////////////////////////////////////////////////////////////////
// Validation Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Validate file path when it's being set.
   * 
   * @param path the file path to validate
   * @throws IllegalArgumentException if path is invalid
   */
  private void validateFilePath(String path) {
    if (path != null && !path.trim().isEmpty()) {
      if (!isValidFilePath(path)) {
        throw new IllegalArgumentException("Invalid file path format: " + path);
      }
      if (!hasValidExcelExtension(path)) {
        throw new IllegalArgumentException("File must have .xlsx or .xls extension: " + path);
      }
    }
  }

  /**
   * Check if a string looks like a valid file path.
   * Basic validation to catch obvious errors.
   * 
   * @param path the path to validate
   * @return true if path looks valid
   */
  private boolean isValidFilePath(String path) {
    if (path == null || path.trim().isEmpty()) {
      return false;
    }
    
    // Check for invalid characters (basic check)
    String invalidChars = "<>:\"|?*";
    for (char c : invalidChars.toCharArray()) {
      if (path.indexOf(c) >= 0) {
        return false;
      }
    }
    
    // Must contain at least one path separator or be a simple filename
    return path.contains("/") || path.contains("\\") || !path.contains(" ") || path.contains(".");
  }
}
