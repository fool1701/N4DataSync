// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.File;
import java.net.URL;

/**
 * BDataSource represents connection information for accessing
 * external data sources. This component handles only connection concerns,
 * separated from data extraction settings.
 *
 * Supports multiple source types:
 * - Excel: Local file connections
 * - GoogleSheets: API-based connections (future)
 * - RDBMS: Database connections (future)
 * - CSV: File-based connections (future)
 */
@NiagaraType
@NiagaraProperty(
  name = "sourceType",
  type = "baja:String",
  defaultValue = "BString.make(\"Excel\")"
)
@NiagaraProperty(
  name = "sourcePath",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "connectionName",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "description",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "lastValidated",
  type = "baja:AbsTime",
  defaultValue = "BAbsTime.NULL"
)
@NiagaraProperty(
  name = "validationStatus",
  type = "baja:String",
  defaultValue = "BString.make(\"Not Validated\")"
)
public class BDataSource extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataSource(2047217648)1.0$ @*/
/* Generated Wed Jul 02 21:15:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "sourceType"

  /**
   * Slot for the {@code sourceType} property.
   * @see #getSourceType
   * @see #setSourceType
   */
  public static final Property sourceType = newProperty(0, BString.make("Excel"), null);

  /**
   * Get the {@code sourceType} property.
   * @see #sourceType
   */
  public String getSourceType() { return getString(sourceType); }

  /**
   * Set the {@code sourceType} property.
   * @see #sourceType
   */
  public void setSourceType(String v) {
    validateSourceType(v);
    setString(sourceType, v, null);
  }

  //endregion Property "sourceType"

  //region Property "sourcePath"

  /**
   * Slot for the {@code sourcePath} property.
   * @see #getSourcePath
   * @see #setSourcePath
   */
  public static final Property sourcePath = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code sourcePath} property.
   * @see #sourcePath
   */
  public String getSourcePath() { return getString(sourcePath); }

  /**
   * Set the {@code sourcePath} property.
   * @see #sourcePath
   */
  public void setSourcePath(String v) {
    validateSourcePath(v);
    setString(sourcePath, v, null);
  }

  //endregion Property "sourcePath"

  //region Property "connectionName"

  /**
   * Slot for the {@code connectionName} property.
   * @see #getConnectionName
   * @see #setConnectionName
   */
  public static final Property connectionName = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code connectionName} property.
   * @see #connectionName
   */
  public String getConnectionName() { return getString(connectionName); }

  /**
   * Set the {@code connectionName} property.
   * @see #connectionName
   */
  public void setConnectionName(String v) {
    validateConnectionName(v);
    setString(connectionName, v, null);
  }

  //endregion Property "connectionName"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "lastValidated"

  /**
   * Slot for the {@code lastValidated} property.
   * @see #getLastValidated
   * @see #setLastValidated
   */
  public static final Property lastValidated = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastValidated} property.
   * @see #lastValidated
   */
  public BAbsTime getLastValidated() { return (BAbsTime)get(lastValidated); }

  /**
   * Set the {@code lastValidated} property.
   * @see #lastValidated
   */
  public void setLastValidated(BAbsTime v) { set(lastValidated, v, null); }

  //endregion Property "lastValidated"

  //region Property "validationStatus"

  /**
   * Slot for the {@code validationStatus} property.
   * @see #getValidationStatus
   * @see #setValidationStatus
   */
  public static final Property validationStatus = newProperty(0, BString.make("Not Validated"), null);

  /**
   * Get the {@code validationStatus} property.
   * @see #validationStatus
   */
  public String getValidationStatus() { return getString(validationStatus); }

  /**
   * Set the {@code validationStatus} property.
   * @see #validationStatus
   */
  public void setValidationStatus(String v) { setString(validationStatus, v, null); }

  //endregion Property "validationStatus"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final String SOURCE_TYPE_EXCEL = "Excel";
  public static final String SOURCE_TYPE_GOOGLE_SHEETS = "GoogleSheets";
  public static final String SOURCE_TYPE_CSV = "CSV";
  public static final String SOURCE_TYPE_RDBMS = "RDBMS";

  public static final String VALIDATION_STATUS_NOT_VALIDATED = "Not Validated";
  public static final String VALIDATION_STATUS_VALID = "Valid";
  public static final String VALIDATION_STATUS_INVALID = "Invalid";
  public static final String VALIDATION_STATUS_ERROR = "Error";

////////////////////////////////////////////////////////////////
// Validation Methods
////////////////////////////////////////////////////////////////

  /**
   * Validate the source type.
   * @param sourceType the source type to validate
   * @throws IllegalArgumentException if the source type is invalid
   */
  private void validateSourceType(String sourceType) {
    if (sourceType == null || sourceType.trim().isEmpty()) {
      throw new IllegalArgumentException("Source type cannot be null or empty");
    }

    // TODO: Issue #6 - Add support for additional source types
    if (!SOURCE_TYPE_EXCEL.equals(sourceType) &&
        !SOURCE_TYPE_GOOGLE_SHEETS.equals(sourceType) &&
        !SOURCE_TYPE_CSV.equals(sourceType) &&
        !SOURCE_TYPE_RDBMS.equals(sourceType)) {
      throw new IllegalArgumentException("Unsupported source type: " + sourceType);
    }
  }

  /**
   * Validate the source path based on the source type.
   * @param path the source path to validate
   * @throws IllegalArgumentException if the path is invalid
   */
  private void validateSourcePath(String path) {
    if (path == null || path.trim().isEmpty()) {
      throw new IllegalArgumentException("Source path cannot be null or empty");
    }

    String currentSourceType = getSourceType();
    if (SOURCE_TYPE_EXCEL.equals(currentSourceType) || SOURCE_TYPE_CSV.equals(currentSourceType)) {
      validateFilePath(path);
    } else if (SOURCE_TYPE_GOOGLE_SHEETS.equals(currentSourceType)) {
      validateGoogleSheetsUrl(path);
    } else if (SOURCE_TYPE_RDBMS.equals(currentSourceType)) {
      validateJdbcUrl(path);
    }
  }

  /**
   * Validate connection name.
   * @param name the connection name to validate
   * @throws IllegalArgumentException if the name is invalid
   */
  private void validateConnectionName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Connection name cannot be null or empty");
    }

    if (name.length() > 100) {
      throw new IllegalArgumentException("Connection name cannot exceed 100 characters");
    }

    // Check for invalid characters
    if (!name.matches("^[a-zA-Z0-9\\s\\-_\\.]+$")) {
      throw new IllegalArgumentException("Connection name contains invalid characters");
    }
  }

  /**
   * Validate file path for Excel/CSV sources.
   */
  private void validateFilePath(String path) {
    try {
      File file = new File(path);
      String extension = getFileExtension(path);

      if (SOURCE_TYPE_EXCEL.equals(getSourceType())) {
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
          throw new IllegalArgumentException("Excel files must have .xlsx or .xls extension");
        }
      } else if (SOURCE_TYPE_CSV.equals(getSourceType())) {
        if (!extension.equals("csv")) {
          throw new IllegalArgumentException("CSV files must have .csv extension");
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid file path: " + e.getMessage());
    }
  }

  /**
   * Validate Google Sheets URL.
   */
  private void validateGoogleSheetsUrl(String url) {
    try {
      URL googleUrl = new URL(url);
      if (!url.contains("docs.google.com/spreadsheets")) {
        throw new IllegalArgumentException("Invalid Google Sheets URL format");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Google Sheets URL: " + e.getMessage());
    }
  }

  /**
   * Validate JDBC URL.
   */
  private void validateJdbcUrl(String url) {
    if (!url.startsWith("jdbc:")) {
      throw new IllegalArgumentException("JDBC URL must start with 'jdbc:'");
    }
  }

  /**
   * Get file extension from path.
   */
  private String getFileExtension(String path) {
    int lastDot = path.lastIndexOf('.');
    return lastDot > 0 ? path.substring(lastDot + 1).toLowerCase() : "";
  }

////////////////////////////////////////////////////////////////
// Connection Testing Methods
////////////////////////////////////////////////////////////////

  /**
   * Test the connection to the data source.
   * @return true if connection is successful, false otherwise
   */
  public boolean testConnection() {
    try {
      String currentSourceType = getSourceType();
      String currentPath = getSourcePath();

      if (SOURCE_TYPE_EXCEL.equals(currentSourceType) || SOURCE_TYPE_CSV.equals(currentSourceType)) {
        return testFileConnection(currentPath);
      } else if (SOURCE_TYPE_GOOGLE_SHEETS.equals(currentSourceType)) {
        return testGoogleSheetsConnection(currentPath);
      } else if (SOURCE_TYPE_RDBMS.equals(currentSourceType)) {
        return testDatabaseConnection(currentPath);
      }

      return false;
    } catch (Exception e) {
      setValidationStatus(VALIDATION_STATUS_ERROR);
      return false;
    }
  }

  /**
   * Test file-based connection (Excel/CSV).
   */
  private boolean testFileConnection(String path) {
    File file = new File(path);
    boolean exists = file.exists() && file.canRead();

    setValidationStatus(exists ? VALIDATION_STATUS_VALID : VALIDATION_STATUS_INVALID);
    setLastValidated(BAbsTime.now());

    return exists;
  }

  /**
   * Test Google Sheets connection.
   * TODO: Issue #6 - Implement Google Sheets API connection testing
   */
  private boolean testGoogleSheetsConnection(String url) {
    // Placeholder for future implementation
    setValidationStatus(VALIDATION_STATUS_NOT_VALIDATED);
    return false;
  }

  /**
   * Test database connection.
   * TODO: Issue #6 - Implement database connection testing
   */
  private boolean testDatabaseConnection(String jdbcUrl) {
    // Placeholder for future implementation
    setValidationStatus(VALIDATION_STATUS_NOT_VALIDATED);
    return false;
  }

  /**
   * Get a summary of the connection configuration.
   * @return human-readable connection summary
   */
  public String getConnectionSummary() {
    StringBuilder summary = new StringBuilder();
    summary.append("Type: ").append(getSourceType());
    summary.append(", Path: ").append(getSourcePath());
    summary.append(", Status: ").append(getValidationStatus());

    if (!getLastValidated().isNull()) {
      summary.append(", Last Validated: ").append(getLastValidated());
    }

    return summary.toString();
  }
}
