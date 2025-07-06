// In: com.mea.datasync.test
package com.mea.datasync.test;

import com.mea.datasync.model.BDataSource;
import javax.baja.test.BTestNg;
import javax.baja.nre.annotations.NiagaraType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

/**
 * Unit tests for BDataSource component.
 * Tests connection validation, source type handling, and path validation.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "connection", "validation"})
public class BDataSourceConnectionTest extends BTestNg {

  private BDataSource connection;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    // Given: A new data source connection instance
    connection = new BDataSource();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    // Clean up resources
    if (connection != null) {
      connection = null;
    }
  }

////////////////////////////////////////////////////////////////
// Source Type Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"validation"})
  public void testValidSourceTypes() {
    // Given: Valid source types
    String[] validTypes = {
      BDataSourceConnection.SOURCE_TYPE_EXCEL,
      BDataSourceConnection.SOURCE_TYPE_GOOGLE_SHEETS,
      BDataSourceConnection.SOURCE_TYPE_CSV,
      BDataSourceConnection.SOURCE_TYPE_RDBMS
    };

    // When & Then: All valid types should be accepted
    for (String type : validTypes) {
      connection.setSourceType(type);
      Assert.assertEquals(connection.getSourceType(), type);
    }
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testInvalidSourceType() {
    // Given: An invalid source type
    String invalidType = "InvalidType";

    // When: Setting invalid source type
    // Then: Should throw IllegalArgumentException
    connection.setSourceType(invalidType);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testNullSourceType() {
    // Given: A null source type
    // When: Setting null source type
    // Then: Should throw IllegalArgumentException
    connection.setSourceType(null);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testEmptySourceType() {
    // Given: An empty source type
    // When: Setting empty source type
    // Then: Should throw IllegalArgumentException
    connection.setSourceType("");
  }

////////////////////////////////////////////////////////////////
// Source Path Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"validation"})
  public void testValidExcelPath() {
    // Given: Excel source type and valid Excel file path
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);
    String validPath = "C:\\Data\\test.xlsx";

    // When: Setting valid Excel path
    connection.setSourcePath(validPath);

    // Then: Path should be accepted
    Assert.assertEquals(connection.getSourcePath(), validPath);
  }

  @Test(groups = {"validation"})
  public void testValidCsvPath() {
    // Given: CSV source type and valid CSV file path
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_CSV);
    String validPath = "C:\\Data\\test.csv";

    // When: Setting valid CSV path
    connection.setSourcePath(validPath);

    // Then: Path should be accepted
    Assert.assertEquals(connection.getSourcePath(), validPath);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testInvalidExcelExtension() {
    // Given: Excel source type and invalid file extension
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);

    // When: Setting path with wrong extension
    // Then: Should throw IllegalArgumentException
    connection.setSourcePath("C:\\Data\\test.txt");
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testNullSourcePath() {
    // Given: Valid source type
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);

    // When: Setting null source path
    // Then: Should throw IllegalArgumentException
    connection.setSourcePath(null);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testEmptySourcePath() {
    // Given: Valid source type
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);

    // When: Setting empty source path
    // Then: Should throw IllegalArgumentException
    connection.setSourcePath("");
  }

////////////////////////////////////////////////////////////////
// Connection Name Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"validation"})
  public void testValidConnectionName() {
    // Given: Valid connection names
    String[] validNames = {
      "Production Excel Connection",
      "Test-Data_Source.1",
      "Building-A-HVAC",
      "Connection123"
    };

    // When & Then: All valid names should be accepted
    for (String name : validNames) {
      connection.setConnectionName(name);
      Assert.assertEquals(connection.getConnectionName(), name);
    }
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testConnectionNameTooLong() {
    // Given: A connection name that's too long
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 101; i++) {
      sb.append("a");
    }
    String longName = sb.toString();

    // When: Setting overly long name
    // Then: Should throw IllegalArgumentException
    connection.setConnectionName(longName);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testConnectionNameInvalidCharacters() {
    // Given: A connection name with invalid characters
    String invalidName = "Test@Connection#";

    // When: Setting name with invalid characters
    // Then: Should throw IllegalArgumentException
    connection.setConnectionName(invalidName);
  }

////////////////////////////////////////////////////////////////
// Connection Testing Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"connection"})
  public void testConnectionTestWithValidFile() {
    // Given: Excel connection with path to this test file (which exists)
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);
    connection.setConnectionName("Test Connection");

    // Use a file that should exist - the current test file
    String testFilePath = getClass().getProtectionDomain()
                                   .getCodeSource()
                                   .getLocation()
                                   .getPath() + "BDataSourceConnectionTest.class";

    // Create a mock Excel file path for testing
    String mockExcelPath = "test.xlsx";
    connection.setSourcePath(mockExcelPath);

    // When: Testing connection (will fail for non-existent file)
    boolean result = connection.testConnection();

    // Then: Should return false for non-existent file
    Assert.assertFalse(result);
    Assert.assertEquals(connection.getValidationStatus(),
                       BDataSourceConnection.VALIDATION_STATUS_INVALID);
  }

  @Test(groups = {"connection"})
  public void testConnectionSummary() {
    // Given: Configured connection
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_EXCEL);
    connection.setSourcePath("C:\\Data\\test.xlsx");
    connection.setConnectionName("Test Connection");

    // When: Getting connection summary
    String summary = connection.getConnectionSummary();

    // Then: Summary should contain key information
    Assert.assertTrue(summary.contains("Type: Excel"));
    Assert.assertTrue(summary.contains("Path: C:\\Data\\test.xlsx"));
    Assert.assertTrue(summary.contains("Status:"));
  }

////////////////////////////////////////////////////////////////
// Default Values Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"defaults"})
  public void testDefaultValues() {
    // Given: New connection instance
    BDataSourceConnection newConnection = new BDataSourceConnection();

    // When: Checking default values
    // Then: Should have expected defaults
    Assert.assertEquals(newConnection.getSourceType(), "Excel");
    Assert.assertEquals(newConnection.getValidationStatus(), "Not Validated");
    Assert.assertTrue(newConnection.getLastValidated().isNull());
  }

////////////////////////////////////////////////////////////////
// Google Sheets URL Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"validation"})
  public void testValidGoogleSheetsUrl() {
    // Given: Google Sheets source type and valid URL
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_GOOGLE_SHEETS);
    String validUrl = "https://docs.google.com/spreadsheets/d/1234567890/edit";

    // When: Setting valid Google Sheets URL
    connection.setSourcePath(validUrl);

    // Then: URL should be accepted
    Assert.assertEquals(connection.getSourcePath(), validUrl);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testInvalidGoogleSheetsUrl() {
    // Given: Google Sheets source type and invalid URL
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_GOOGLE_SHEETS);

    // When: Setting invalid Google Sheets URL
    // Then: Should throw IllegalArgumentException
    connection.setSourcePath("https://example.com/not-google-sheets");
  }

////////////////////////////////////////////////////////////////
// JDBC URL Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"validation"})
  public void testValidJdbcUrl() {
    // Given: RDBMS source type and valid JDBC URL
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_RDBMS);
    String validJdbcUrl = "jdbc:postgresql://localhost:5432/testdb";

    // When: Setting valid JDBC URL
    connection.setSourcePath(validJdbcUrl);

    // Then: URL should be accepted
    Assert.assertEquals(connection.getSourcePath(), validJdbcUrl);
  }

  @Test(groups = {"validation", "error-handling"},
        expectedExceptions = IllegalArgumentException.class)
  public void testInvalidJdbcUrl() {
    // Given: RDBMS source type and invalid JDBC URL
    connection.setSourceType(BDataSourceConnection.SOURCE_TYPE_RDBMS);

    // When: Setting invalid JDBC URL (missing jdbc: prefix)
    // Then: Should throw IllegalArgumentException
    connection.setSourcePath("postgresql://localhost:5432/testdb");
  }
}
