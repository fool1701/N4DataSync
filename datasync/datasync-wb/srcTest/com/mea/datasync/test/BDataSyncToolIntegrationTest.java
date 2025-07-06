// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.nav.BINavNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.model.BDataSourceFolder;

import com.mea.datasync.model.BExcelDataSource;
import com.mea.datasync.model.BExcelConnectionDetails;
import com.mea.datasync.model.BAbstractDataSource;
import com.mea.datasync.test.utils.BaseTestClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Integration tests for BDataSyncTool with the new Data Source Connection architecture.
 * Tests the complete integration including navigation tree, connection management,
 * and tool functionality.
 */
@NiagaraType
@Test(groups = {"datasync", "integration", "tool", "connections"})
public class BDataSyncToolIntegrationTest extends BaseTestClass {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BDataSyncToolIntegrationTest(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSyncToolIntegrationTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BDataSyncTool dataSyncTool;
  private BDataSourceFolder connections;
  private File tempExcelFile;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up DataSync Tool integration test");

    // Create DataSync Tool
    dataSyncTool = new BDataSyncTool();
    connections = dataSyncTool.getDataSources();

    // Create temporary Excel file for testing
    tempExcelFile = createTempExcelFile();

    logTestStep("DataSync Tool integration test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up DataSync Tool integration test resources");

    if (dataSyncTool != null) {
      if (dataSyncTool.isRunning()) {
        dataSyncTool.stop();
      }
      dataSyncTool = null;
    }

    if (tempExcelFile != null && tempExcelFile.exists()) {
      tempExcelFile.delete();
    }

    connections = null;
    tempExcelFile = null;

    logTestStep("DataSync Tool integration test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Tool Integration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"integration", "tool", "initialization"})
  public void testDataSyncToolInitialization() {
    logTestStep("Testing DataSync Tool initialization");

    // Tool should be created successfully
    Assert.assertNotNull(dataSyncTool);

    // Should have data source connections container
    Assert.assertNotNull(connections);

    // Container should be empty initially
    Assert.assertEquals(connections.getDataSourceConnectionCount(), 0);
    Assert.assertEquals(connections.getFolderCount(), 0);
  }

  @Test(groups = {"integration", "tool", "navigation"})
  public void testNavigationTreeIntegration() {
    logTestStep("Testing navigation tree integration");

    // Initially should have data source connections in nav tree
    BINavNode[] navChildren = dataSyncTool.getNavChildren();
    Assert.assertTrue(navChildren.length >= 1); // At least the connections container

    // First child should be the data source connections container
    boolean foundConnectionsContainer = false;
    for (BINavNode child : navChildren) {
      if (child instanceof BDataSourceFolder) {
        foundConnectionsContainer = true;
        break;
      }
    }
    Assert.assertTrue(foundConnectionsContainer);
  }

  @Test(groups = {"integration", "tool", "description"})
  public void testToolDescriptionWithConnections() {
    logTestStep("Testing tool description with connections");

    // Initially no connections
    String desc = dataSyncTool.getNavDescription(null);
    Assert.assertTrue(desc.contains("N4-DataSync Tool"));

    // Add a connection
    BExcelDataSource excelConnection = createTestExcelConnection();
    connections.add("testExcel", excelConnection);

    // Description should include connection count
    desc = dataSyncTool.getNavDescription(null);
    Assert.assertTrue(desc.contains("1 data source"));
    Assert.assertTrue(desc.contains("0 healthy")); // Not tested yet

    // Make connection healthy
    excelConnection.setConnectionStatus(BAbstractDataSource.STATUS_CONNECTED);
    desc = dataSyncTool.getNavDescription(null);
    Assert.assertTrue(desc.contains("1 healthy"));
  }

////////////////////////////////////////////////////////////////
// Connection Management Integration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"integration", "connections", "management"})
  public void testAddExcelConnectionToTool() {
    logTestStep("Testing adding Excel connection to tool");

    // Create and configure Excel connection
    BExcelDataSource excelConnection = createTestExcelConnection();

    // Add to tool's connections container
    connections.add("bmsData", excelConnection);

    // Verify integration
    Assert.assertEquals(connections.getDataSourceConnectionCount(), 1);
    BAbstractDataSource[] allConnections = connections.getAllDataSourceConnections();
    Assert.assertEquals(allConnections.length, 1);
    Assert.assertEquals(allConnections[0], excelConnection);
  }

  @Test(groups = {"integration", "connections", "folders"})
  public void testOrganizedConnectionStructure() {
    logTestStep("Testing organized connection structure in tool");

    // Create folder structure
    BDataSourceFolder bmsFolder = new BDataSourceFolder();
    bmsFolder.setDisplayName("BMS Data Sources");

    // Add folder to tool
    connections.add("bmsFolder", bmsFolder);

    // Add connections to folder
    BExcelDataSource pointsConnection = createTestExcelConnection();
    pointsConnection.getConnectionDetails().setConnectionName("BMS Points");
    bmsFolder.add("points", pointsConnection);

    BExcelDataSource devicesConnection = createTestExcelConnection();
    devicesConnection.getConnectionDetails().setConnectionName("BMS Devices");
    bmsFolder.add("devices", devicesConnection);

    // Verify structure
    Assert.assertEquals(connections.getFolderCount(), 1);
    Assert.assertEquals(connections.getDataSourceConnectionCount(), 0); // Direct children
    Assert.assertEquals(connections.getAllDataSourceConnections().length, 2); // Recursive

    // Test navigation tree
    BINavNode[] navChildren = dataSyncTool.getNavChildren();
    boolean foundConnectionsContainer = false;
    for (BINavNode child : navChildren) {
      if (child instanceof BDataSourceFolder) {
        BDataSourceFolder container = (BDataSourceFolder) child;
        Assert.assertTrue(container.hasNavChildren());
        foundConnectionsContainer = true;
        break;
      }
    }
    Assert.assertTrue(foundConnectionsContainer);
  }

////////////////////////////////////////////////////////////////
// Connection Testing Integration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"integration", "connections", "testing"})
  public void testConnectionTestingIntegration() {
    logTestStep("Testing connection testing integration");

    // Create Excel connection with valid file
    BExcelDataSource excelConnection = new BExcelDataSource();
    BExcelConnectionDetails details = excelConnection.getConnectionDetails();
    details.setConnectionName("Test Integration Connection");
    details.setFilePath(tempExcelFile.getAbsolutePath());

    // Add to tool
    connections.add("testConnection", excelConnection);

    // Test connection
    excelConnection.doTestConnection();

    // Verify results
    Assert.assertTrue(excelConnection.isConnectionHealthy());
    Assert.assertEquals(excelConnection.getConnectionStatus(), "Connected");

    // Tool description should reflect healthy connection
    String desc = dataSyncTool.getNavDescription(null);
    Assert.assertTrue(desc.contains("1 data source"));
    Assert.assertTrue(desc.contains("1 healthy"));
  }

  @Test(groups = {"integration", "connections", "health"})
  public void testMixedConnectionHealthIntegration() {
    logTestStep("Testing mixed connection health integration");

    // Create healthy connection
    BExcelDataSource healthyConnection = new BExcelDataSource();
    healthyConnection.getConnectionDetails().setFilePath(tempExcelFile.getAbsolutePath());
    healthyConnection.doTestConnection(); // Should succeed

    // Create unhealthy connection
    BExcelDataSource unhealthyConnection = new BExcelDataSource();
    unhealthyConnection.getConnectionDetails().setFilePath("C:\\NonExistent\\file.xlsx");
    unhealthyConnection.doTestConnection(); // Should fail

    // Add both to tool
    connections.add("healthy", healthyConnection);
    connections.add("unhealthy", unhealthyConnection);

    // Verify mixed health status
    Assert.assertTrue(connections.hasHealthyConnections());

    String desc = dataSyncTool.getNavDescription(null);
    Assert.assertTrue(desc.contains("2 data source"));
    Assert.assertTrue(desc.contains("1 healthy"));
  }

////////////////////////////////////////////////////////////////
// Tool Lifecycle Integration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"integration", "tool", "lifecycle"})
  public void testToolLifecycleWithConnections() throws Exception {
    logTestStep("Testing tool lifecycle with connections");

    // Add connections before starting
    BExcelDataSource connection1 = createTestExcelConnection();
    BExcelDataSource connection2 = createTestExcelConnection();

    connections.add("conn1", connection1);
    connections.add("conn2", connection2);

    // Start tool
    dataSyncTool.start();
    Assert.assertTrue(dataSyncTool.isRunning());

    // Connections should be accessible
    Assert.assertEquals(connections.getAllDataSourceConnections().length, 2);

    // Stop tool
    dataSyncTool.stop();
    Assert.assertFalse(dataSyncTool.isRunning());

    // Connections should still be accessible
    Assert.assertEquals(connections.getAllDataSourceConnections().length, 2);
  }

////////////////////////////////////////////////////////////////
// Complex Integration Scenarios
////////////////////////////////////////////////////////////////

  @Test(groups = {"integration", "complex", "scenario"})
  public void testCompleteDataSourceManagementScenario() {
    logTestStep("Testing complete data source management scenario");

    // Create organized structure
    BDataSourceFolder bmsFolder = new BDataSourceFolder();
    bmsFolder.setDisplayName("BMS Systems");

    BDataSourceFolder hvacFolder = new BDataSourceFolder();
    hvacFolder.setDisplayName("HVAC Systems");

    // Add folders to tool
    connections.add("bms", bmsFolder);
    connections.add("hvac", hvacFolder);

    // Add connections to folders
    BExcelDataSource bmsPoints = createTestExcelConnection();
    bmsPoints.getConnectionDetails().setConnectionName("BMS Points Data");
    bmsFolder.add("points", bmsPoints);

    BExcelDataSource hvacSchedules = createTestExcelConnection();
    hvacSchedules.getConnectionDetails().setConnectionName("HVAC Schedules");
    hvacFolder.add("schedules", hvacSchedules);

    // Test the complete structure
    Assert.assertEquals(connections.getFolderCount(), 2);
    Assert.assertEquals(connections.getAllDataSourceConnections().length, 2);

    // Test navigation tree depth
    BINavNode[] toolChildren = dataSyncTool.getNavChildren();
    BDataSourceFolder connectionsContainer = null;
    for (BINavNode child : toolChildren) {
      if (child instanceof BDataSourceFolder) {
        connectionsContainer = (BDataSourceFolder) child;
        break;
      }
    }

    Assert.assertNotNull(connectionsContainer);
    Assert.assertTrue(connectionsContainer.hasNavChildren());

    BINavNode[] containerChildren = connectionsContainer.getNavChildren();
    Assert.assertEquals(containerChildren.length, 2); // Two folders
  }

////////////////////////////////////////////////////////////////
// Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Create a test Excel connection with basic configuration.
   */
  private BExcelDataSource createTestExcelConnection() {
    BExcelDataSource connection = new BExcelDataSource();
    BExcelConnectionDetails details = connection.getConnectionDetails();
    details.setConnectionName("Test Excel Connection");
    details.setFilePath(tempExcelFile.getAbsolutePath());
    details.setDefaultWorksheet("Sheet1");
    return connection;
  }

  /**
   * Create a temporary Excel file for testing.
   */
  private File createTempExcelFile() throws IOException {
    File tempFile = File.createTempFile("datasync_test_", ".xlsx");
    tempFile.deleteOnExit();

    // Write some basic content
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("Test Excel File for DataSync Integration Tests");
    }

    return tempFile;
  }
}
