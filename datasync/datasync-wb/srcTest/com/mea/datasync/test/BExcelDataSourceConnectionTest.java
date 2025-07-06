// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.model.BExcelDataSource;
import com.mea.datasync.model.BExcelConnectionDetails;
import com.mea.datasync.model.BConnectionDetails;
import com.mea.datasync.test.utils.BaseTestClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Comprehensive unit tests for BExcelDataSource.
 * Tests Excel-specific connection functionality, file validation,
 * and connection testing logic.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "excel", "connection"})
public class BExcelDataSourceConnectionTest extends BaseTestClass {

  private BExcelDataSource excelConnection;
  private BExcelConnectionDetails connectionDetails;
  private File tempTestFile;

  @Override
  protected void performBaseSetup() throws Exception {
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BExcelDataSourceConnectionTest(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExcelDataSourceConnectionTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    logTestStep("Setting up Excel data source connection test");
    
    // Create Excel connection
    excelConnection = new BExcelDataSource();
    connectionDetails = excelConnection.getConnectionDetails();
    
    // Create temporary test file
    tempTestFile = createTempExcelFile();
    
    logTestStep("Excel test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up Excel test resources");
    
    if (excelConnection != null && excelConnection.isRunning()) {
      excelConnection.stop();
    }
    
    // Clean up temp file
    if (tempTestFile != null && tempTestFile.exists()) {
      tempTestFile.delete();
    }
    
    excelConnection = null;
    connectionDetails = null;
    tempTestFile = null;
    
    logTestStep("Excel test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Excel Connection Details Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"excel", "details", "validation"})
  public void testExcelConnectionDetailsDefaults() {
    logTestStep("Testing Excel connection details default values");
    
    // Given: New Excel connection details
    // When: Checking default values
    // Then: Should have expected defaults
    Assert.assertEquals(connectionDetails.getDefaultWorksheet(), "Sheet1");
    Assert.assertTrue(connectionDetails.getReadOnlyMode());
    Assert.assertEquals(connectionDetails.getAllowedFormats(), "XLSX,XLS");
    Assert.assertEquals(connectionDetails.getConnectionTimeout(), 30000);
  }

  @Test(groups = {"excel", "details", "validation"})
  public void testExcelFilePathValidation() {
    logTestStep("Testing Excel file path validation");
    
    // Test valid Excel file paths
    connectionDetails.setFilePath("test.xlsx");
    Assert.assertEquals(connectionDetails.getFilePath(), "test.xlsx");
    
    connectionDetails.setFilePath("C:\\Data\\spreadsheet.xls");
    Assert.assertEquals(connectionDetails.getFilePath(), "C:\\Data\\spreadsheet.xls");
    
    // Test invalid file extensions should throw exception
    try {
      connectionDetails.setFilePath("invalid.txt");
      Assert.fail("Should have thrown exception for invalid file extension");
    } catch (IllegalArgumentException e) {
      Assert.assertTrue(e.getMessage().contains("must have .xlsx or .xls extension"));
    }
  }

  @Test(groups = {"excel", "details", "format"})
  public void testExcelFormatDetection() {
    logTestStep("Testing Excel format detection");
    
    // Test XLSX format
    connectionDetails.setFilePath("test.xlsx");
    Assert.assertEquals(connectionDetails.getExcelFormat(), "XLSX");
    Assert.assertTrue(connectionDetails.hasValidExcelExtension("test.xlsx"));
    
    // Test XLS format
    connectionDetails.setFilePath("legacy.xls");
    Assert.assertEquals(connectionDetails.getExcelFormat(), "XLS");
    Assert.assertTrue(connectionDetails.hasValidExcelExtension("legacy.xls"));
    
    // Test invalid format
    Assert.assertFalse(connectionDetails.hasValidExcelExtension("invalid.txt"));
    Assert.assertFalse(connectionDetails.hasValidExcelExtension(null));
  }

  @Test(groups = {"excel", "details", "validation"})
  public void testExcelConnectionDetailsValidation() {
    logTestStep("Testing Excel connection details validation");
    
    // Test invalid configuration (no file path)
    BConnectionDetails.ValidationResult result = connectionDetails.validateConfiguration();
    Assert.assertFalse(result.isValid());
    Assert.assertTrue(result.getErrorMessage().contains("File path is required"));
    
    // Test valid configuration
    connectionDetails.setFilePath("valid.xlsx");
    connectionDetails.setConnectionName("Test Connection");
    result = connectionDetails.validateConfiguration();
    Assert.assertTrue(result.isValid());
    Assert.assertEquals(result.getErrorMessage(), "");
  }

////////////////////////////////////////////////////////////////
// Excel Connection Testing Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"excel", "connection", "testing"})
  public void testExcelConnectionWithValidFile() {
    logTestStep("Testing Excel connection with valid file");
    
    // Given: Valid Excel file path
    connectionDetails.setFilePath(tempTestFile.getAbsolutePath());
    connectionDetails.setConnectionName("Test Excel Connection");
    
    // When: Testing connection
    excelConnection.doTestConnection();
    
    // Then: Should succeed
    Assert.assertTrue(excelConnection.isConnectionHealthy());
    Assert.assertEquals(excelConnection.getConnectionStatus(), "Connected");
    Assert.assertFalse(excelConnection.getLastConnectionTest().isNull());
    Assert.assertFalse(excelConnection.getLastSuccessfulConnection().isNull());
  }

  @Test(groups = {"excel", "connection", "testing"})
  public void testExcelConnectionWithNonExistentFile() {
    logTestStep("Testing Excel connection with non-existent file");
    
    // Given: Non-existent file path
    connectionDetails.setFilePath("C:\\NonExistent\\file.xlsx");
    connectionDetails.setConnectionName("Non-existent File Test");
    
    // When: Testing connection
    excelConnection.doTestConnection();
    
    // Then: Should fail
    Assert.assertFalse(excelConnection.isConnectionHealthy());
    Assert.assertEquals(excelConnection.getConnectionStatus(), "Failed");
    Assert.assertTrue(excelConnection.getLastConnectionError().contains("does not exist"));
  }

  @Test(groups = {"excel", "connection", "testing"})
  public void testExcelConnectionWithEmptyPath() {
    logTestStep("Testing Excel connection with empty file path");
    
    // Given: Empty file path
    connectionDetails.setFilePath("");
    
    // When: Testing connection
    excelConnection.doTestConnection();
    
    // Then: Should fail with appropriate error
    Assert.assertFalse(excelConnection.isConnectionHealthy());
    Assert.assertTrue(excelConnection.getLastConnectionError().contains("File path is not specified"));
  }

  @Test(groups = {"excel", "connection", "testing"})
  public void testExcelConnectionWithInvalidConfiguration() {
    logTestStep("Testing Excel connection with invalid configuration");
    
    // Given: Invalid configuration (no file path)
    connectionDetails.setConnectionName("Invalid Config Test");
    // Don't set file path
    
    // When: Testing connection
    excelConnection.doTestConnection();
    
    // Then: Should fail with configuration error
    Assert.assertFalse(excelConnection.isConnectionHealthy());
    Assert.assertTrue(excelConnection.getLastConnectionError().contains("Configuration invalid"));
  }

////////////////////////////////////////////////////////////////
// Excel-Specific Feature Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"excel", "features"})
  public void testExcelDataSourceTypeName() {
    logTestStep("Testing Excel data source type name");
    
    String typeName = excelConnection.getDataSourceTypeName();
    Assert.assertEquals(typeName, "Excel");
  }

  @Test(groups = {"excel", "features"})
  public void testExcelConnectionSummary() {
    logTestStep("Testing Excel connection summary");
    
    // Given: Configured Excel connection
    connectionDetails.setConnectionName("Test Excel");
    connectionDetails.setFilePath("C:\\Data\\test.xlsx");
    
    // When: Getting connection summary
    String summary = excelConnection.getConnectionSummary();
    
    // Then: Should contain key information
    Assert.assertTrue(summary.contains("Excel Connection"));
    Assert.assertTrue(summary.contains("Test Excel"));
    Assert.assertTrue(summary.contains("test.xlsx"));
    Assert.assertTrue(summary.contains("Status:"));
  }

  @Test(groups = {"excel", "features"})
  public void testExcelConnectionFormatDetection() {
    logTestStep("Testing Excel format detection in connection");
    
    // Test XLSX format
    connectionDetails.setFilePath("test.xlsx");
    Assert.assertEquals(excelConnection.getExcelFormat(), "XLSX");
    
    // Test XLS format
    connectionDetails.setFilePath("legacy.xls");
    Assert.assertEquals(excelConnection.getExcelFormat(), "XLS");
    
    // Test unknown format
    connectionDetails.setFilePath(null);
    Assert.assertEquals(excelConnection.getExcelFormat(), "Unknown");
  }

  @Test(groups = {"excel", "features"})
  public void testExcelFileInfo() {
    logTestStep("Testing Excel file information");
    
    // Given: Valid Excel file
    connectionDetails.setFilePath(tempTestFile.getAbsolutePath());
    
    // When: Getting file info
    String fileInfo = excelConnection.getFileInfo();
    
    // Then: Should contain file information
    Assert.assertTrue(fileInfo.contains("Format:"));
    Assert.assertTrue(fileInfo.contains("Size:"));
    Assert.assertTrue(fileInfo.contains("Modified:"));
  }

  @Test(groups = {"excel", "features"})
  public void testExcelFileInfoWithInvalidPath() {
    logTestStep("Testing Excel file info with invalid path");
    
    // Given: Invalid file path
    connectionDetails.setFilePath("C:\\NonExistent\\file.xlsx");
    
    // When: Getting file info
    String fileInfo = excelConnection.getFileInfo();
    
    // Then: Should indicate file doesn't exist
    Assert.assertEquals(fileInfo, "File does not exist");
  }

////////////////////////////////////////////////////////////////
// Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Create a temporary Excel file for testing.
   */
  private File createTempExcelFile() throws IOException {
    File tempFile = File.createTempFile("test_excel_", ".xlsx");
    tempFile.deleteOnExit();
    
    // Write some basic content to make it a valid file
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("Test Excel File Content");
    }
    
    return tempFile;
  }
}
