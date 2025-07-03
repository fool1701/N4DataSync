// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BDataExtractionSettings represents configuration for extracting data
 * from external sources. This component handles only data extraction concerns,
 * separated from connection information.
 * 
 * Supports various extraction configurations:
 * - Sheet/Table selection
 * - Column mapping definitions
 * - Data filtering rules
 * - Schema validation settings
 */
@NiagaraType
@NiagaraProperty(
  name = "sheetName",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "startRow",
  type = "baja:Integer",
  defaultValue = "BInteger.make(1)"
)
@NiagaraProperty(
  name = "endRow",
  type = "baja:Integer",
  defaultValue = "BInteger.make(-1)"
)
@NiagaraProperty(
  name = "hasHeaderRow",
  type = "baja:Boolean",
  defaultValue = "BBoolean.TRUE"
)
@NiagaraProperty(
  name = "skipEmptyRows",
  type = "baja:Boolean",
  defaultValue = "BBoolean.TRUE"
)
@NiagaraProperty(
  name = "validateSchema",
  type = "baja:Boolean",
  defaultValue = "BBoolean.TRUE"
)
@NiagaraProperty(
  name = "extractionMode",
  type = "baja:String",
  defaultValue = "BString.make(\"Full\")"
)
public class BDataExtractionSettings extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataExtractionSettings(2047217648)1.0$ @*/
/* Generated Wed Jul 02 21:20:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "sheetName"

  /**
   * Slot for the {@code sheetName} property.
   * @see #getSheetName
   * @see #setSheetName
   */
  public static final Property sheetName = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code sheetName} property.
   * @see #sheetName
   */
  public String getSheetName() { return getString(sheetName); }

  /**
   * Set the {@code sheetName} property.
   * @see #sheetName
   */
  public void setSheetName(String v) { 
    validateSheetName(v);
    setString(sheetName, v, null); 
  }

  //endregion Property "sheetName"

  //region Property "startRow"

  /**
   * Slot for the {@code startRow} property.
   * @see #getStartRow
   * @see #setStartRow
   */
  public static final Property startRow = newProperty(0, BInteger.make(1).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code startRow} property.
   * @see #startRow
   */
  public int getStartRow() { return getInt(startRow); }

  /**
   * Set the {@code startRow} property.
   * @see #startRow
   */
  public void setStartRow(int v) { 
    validateStartRow(v);
    setInt(startRow, v, null); 
  }

  //endregion Property "startRow"

  //region Property "endRow"

  /**
   * Slot for the {@code endRow} property.
   * @see #getEndRow
   * @see #setEndRow
   */
  public static final Property endRow = newProperty(0, BInteger.make(-1).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code endRow} property.
   * @see #endRow
   */
  public int getEndRow() { return getInt(endRow); }

  /**
   * Set the {@code endRow} property.
   * @see #endRow
   */
  public void setEndRow(int v) { 
    validateEndRow(v);
    setInt(endRow, v, null); 
  }

  //endregion Property "endRow"

  //region Property "hasHeaderRow"

  /**
   * Slot for the {@code hasHeaderRow} property.
   * @see #getHasHeaderRow
   * @see #setHasHeaderRow
   */
  public static final Property hasHeaderRow = newProperty(0, BBoolean.TRUE, null);

  /**
   * Get the {@code hasHeaderRow} property.
   * @see #hasHeaderRow
   */
  public boolean getHasHeaderRow() { return getBoolean(hasHeaderRow); }

  /**
   * Set the {@code hasHeaderRow} property.
   * @see #hasHeaderRow
   */
  public void setHasHeaderRow(boolean v) { setBoolean(hasHeaderRow, v, null); }

  //endregion Property "hasHeaderRow"

  //region Property "skipEmptyRows"

  /**
   * Slot for the {@code skipEmptyRows} property.
   * @see #getSkipEmptyRows
   * @see #setSkipEmptyRows
   */
  public static final Property skipEmptyRows = newProperty(0, BBoolean.TRUE, null);

  /**
   * Get the {@code skipEmptyRows} property.
   * @see #skipEmptyRows
   */
  public boolean getSkipEmptyRows() { return getBoolean(skipEmptyRows); }

  /**
   * Set the {@code skipEmptyRows} property.
   * @see #skipEmptyRows
   */
  public void setSkipEmptyRows(boolean v) { setBoolean(skipEmptyRows, v, null); }

  //endregion Property "skipEmptyRows"

  //region Property "validateSchema"

  /**
   * Slot for the {@code validateSchema} property.
   * @see #getValidateSchema
   * @see #setValidateSchema
   */
  public static final Property validateSchema = newProperty(0, BBoolean.TRUE, null);

  /**
   * Get the {@code validateSchema} property.
   * @see #validateSchema
   */
  public boolean getValidateSchema() { return getBoolean(validateSchema); }

  /**
   * Set the {@code validateSchema} property.
   * @see #validateSchema
   */
  public void setValidateSchema(boolean v) { setBoolean(validateSchema, v, null); }

  //endregion Property "validateSchema"

  //region Property "extractionMode"

  /**
   * Slot for the {@code extractionMode} property.
   * @see #getExtractionMode
   * @see #setExtractionMode
   */
  public static final Property extractionMode = newProperty(0, BString.make("Full"), null);

  /**
   * Get the {@code extractionMode} property.
   * @see #extractionMode
   */
  public String getExtractionMode() { return getString(extractionMode); }

  /**
   * Set the {@code extractionMode} property.
   * @see #extractionMode
   */
  public void setExtractionMode(String v) { 
    validateExtractionMode(v);
    setString(extractionMode, v, null); 
  }

  //endregion Property "extractionMode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataExtractionSettings.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final String EXTRACTION_MODE_FULL = "Full";
  public static final String EXTRACTION_MODE_INCREMENTAL = "Incremental";
  public static final String EXTRACTION_MODE_RANGE = "Range";
  
  public static final int END_ROW_ALL = -1;
  public static final int MIN_START_ROW = 1;

////////////////////////////////////////////////////////////////
// Validation Methods
////////////////////////////////////////////////////////////////

  /**
   * Validate the sheet name.
   * @param name the sheet name to validate
   * @throws IllegalArgumentException if the sheet name is invalid
   */
  private void validateSheetName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Sheet name cannot be null or empty");
    }
    
    if (name.length() > 255) {
      throw new IllegalArgumentException("Sheet name cannot exceed 255 characters");
    }
    
    // Check for invalid characters in sheet names
    if (name.contains("[") || name.contains("]") || name.contains("*") || 
        name.contains("?") || name.contains("/") || name.contains("\\") ||
        name.contains(":")) {
      throw new IllegalArgumentException("Sheet name contains invalid characters: [ ] * ? / \\ :");
    }
  }

  /**
   * Validate the start row.
   * @param row the start row to validate
   * @throws IllegalArgumentException if the start row is invalid
   */
  private void validateStartRow(int row) {
    if (row < MIN_START_ROW) {
      throw new IllegalArgumentException("Start row must be >= " + MIN_START_ROW);
    }
    
    int currentEndRow = getEndRow();
    if (currentEndRow != END_ROW_ALL && row > currentEndRow) {
      throw new IllegalArgumentException("Start row cannot be greater than end row");
    }
  }

  /**
   * Validate the end row.
   * @param row the end row to validate
   * @throws IllegalArgumentException if the end row is invalid
   */
  private void validateEndRow(int row) {
    if (row != END_ROW_ALL && row < MIN_START_ROW) {
      throw new IllegalArgumentException("End row must be >= " + MIN_START_ROW + " or -1 for all rows");
    }
    
    int currentStartRow = getStartRow();
    if (row != END_ROW_ALL && row < currentStartRow) {
      throw new IllegalArgumentException("End row cannot be less than start row");
    }
  }

  /**
   * Validate the extraction mode.
   * @param mode the extraction mode to validate
   * @throws IllegalArgumentException if the extraction mode is invalid
   */
  private void validateExtractionMode(String mode) {
    if (mode == null || mode.trim().isEmpty()) {
      throw new IllegalArgumentException("Extraction mode cannot be null or empty");
    }
    
    if (!EXTRACTION_MODE_FULL.equals(mode) && 
        !EXTRACTION_MODE_INCREMENTAL.equals(mode) &&
        !EXTRACTION_MODE_RANGE.equals(mode)) {
      throw new IllegalArgumentException("Invalid extraction mode: " + mode);
    }
  }

////////////////////////////////////////////////////////////////
// Configuration Validation Methods
////////////////////////////////////////////////////////////////

  /**
   * Validate the extraction settings against a data source connection.
   * @param connection the data source connection to validate against
   * @return true if settings are compatible with the connection
   */
  public boolean validateSettings(BDataSourceConnection connection) {
    if (connection == null) {
      return false;
    }
    
    try {
      String sourceType = connection.getSourceType();
      
      // Validate sheet name is required for Excel and Google Sheets
      if ((BDataSourceConnection.SOURCE_TYPE_EXCEL.equals(sourceType) || 
           BDataSourceConnection.SOURCE_TYPE_GOOGLE_SHEETS.equals(sourceType)) &&
          (getSheetName() == null || getSheetName().trim().isEmpty())) {
        return false;
      }
      
      // Validate row ranges
      if (getStartRow() < MIN_START_ROW) {
        return false;
      }
      
      if (getEndRow() != END_ROW_ALL && getEndRow() < getStartRow()) {
        return false;
      }
      
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Get the effective row range for extraction.
   * @return array with [startRow, endRow] where endRow is -1 for all rows
   */
  public int[] getRowRange() {
    return new int[] { getStartRow(), getEndRow() };
  }

  /**
   * Check if the extraction will process all rows.
   * @return true if extracting all rows from start row to end of data
   */
  public boolean isExtractingAllRows() {
    return getEndRow() == END_ROW_ALL;
  }

  /**
   * Get the number of rows to extract.
   * @return number of rows, or -1 if extracting all rows
   */
  public int getRowCount() {
    if (isExtractingAllRows()) {
      return -1;
    }
    return getEndRow() - getStartRow() + 1;
  }

  /**
   * Get a summary of the extraction configuration.
   * @return human-readable extraction summary
   */
  public String getExtractionSummary() {
    StringBuilder summary = new StringBuilder();
    
    if (getSheetName() != null && !getSheetName().trim().isEmpty()) {
      summary.append("Sheet: ").append(getSheetName()).append(", ");
    }
    
    summary.append("Rows: ").append(getStartRow());
    if (isExtractingAllRows()) {
      summary.append(" to end");
    } else {
      summary.append(" to ").append(getEndRow());
    }
    
    summary.append(", Mode: ").append(getExtractionMode());
    
    if (getHasHeaderRow()) {
      summary.append(", Has Headers");
    }
    
    if (getSkipEmptyRows()) {
      summary.append(", Skip Empty");
    }
    
    if (getValidateSchema()) {
      summary.append(", Validate Schema");
    }
    
    return summary.toString();
  }

////////////////////////////////////////////////////////////////
// Future Extension Points
////////////////////////////////////////////////////////////////

  /**
   * TODO: Issue #6 - Add column mapping support
   * This method will be implemented to handle column mappings
   * from source columns to target Niagara properties.
   */
  public void addColumnMapping(String sourceColumn, String targetProperty) {
    // Future implementation
    throw new UnsupportedOperationException("Column mapping not yet implemented");
  }

  /**
   * TODO: Issue #6 - Add data filtering support
   * This method will be implemented to handle row-level filtering
   * based on data values and business rules.
   */
  public void addDataFilter(String column, String operator, String value) {
    // Future implementation
    throw new UnsupportedOperationException("Data filtering not yet implemented");
  }

  /**
   * TODO: Issue #6 - Add schema validation support
   * This method will be implemented to validate data against
   * expected schemas and data types.
   */
  public boolean validateDataSchema(Object[] rowData) {
    // Future implementation
    return true;
  }
}
