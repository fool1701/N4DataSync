// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.nav.BINavNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.model.BDataSourceFolder;
import com.mea.datasync.model.BExcelDataSource;
import com.mea.datasync.model.BDataSource;
import com.mea.datasync.test.utils.BaseTestClass;

/**
 * Comprehensive unit tests for BDataSourceFolder container.
 * Tests container functionality, child validation, navigation tree integration,
 * and utility methods for managing data source connections.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "container", "connections"})
public class BDataSourceConnectionsTest extends BaseTestClass {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BDataSourceConnectionsTest(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceConnectionsTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BDataSourceFolder container;
  private BExcelDataSource excelConnection1;
  private BExcelDataSource excelConnection2;
  private BDataSourceFolder folder;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up data source connections container test");

    // Create container
    container = new BDataSourceFolder();

    // Create test connections
    excelConnection1 = new BExcelDataSource();
    excelConnection1.getConnectionDetails().setConnectionName("Excel Connection 1");

    excelConnection2 = new BExcelDataSource();
    excelConnection2.getConnectionDetails().setConnectionName("Excel Connection 2");

    // Create test folder
    folder = new BDataSourceFolder();
    folder.setDisplayName("Test Folder");

    logTestStep("Container test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up container test resources");

    // Clean up components
    if (container != null) {
      container.removeAll();
    }

    container = null;
    excelConnection1 = null;
    excelConnection2 = null;
    folder = null;

    logTestStep("Container test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Child Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"container", "validation", "child"})
  public void testValidChildTypes() {
    logTestStep("Testing valid child type acceptance");

    // Should accept data source connections
    Assert.assertTrue(container.isChildLegal(excelConnection1));

    // Should accept folders
    Assert.assertTrue(container.isChildLegal(folder));
  }

  @Test(groups = {"container", "validation", "child"})
  public void testInvalidChildTypes() {
    logTestStep("Testing invalid child type rejection");

    // Should reject generic BComponent
    BComponent invalidComponent = new BComponent();
    Assert.assertFalse(container.isChildLegal(invalidComponent));

    // Should reject other types (test with a different BComponent type)
    BComponent invalidComponent2 = new BComponent();
    Assert.assertFalse(container.isChildLegal(invalidComponent2));
  }

////////////////////////////////////////////////////////////////
// Container Management Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"container", "management"})
  public void testAddDataSourceConnections() {
    logTestStep("Testing adding data source connections");

    // Initially empty
    Assert.assertEquals(container.getDataSourceConnectionCount(), 0);

    // Add first connection
    container.add("excel1", excelConnection1);
    Assert.assertEquals(container.getDataSourceConnectionCount(), 1);

    // Add second connection
    container.add("excel2", excelConnection2);
    Assert.assertEquals(container.getDataSourceConnectionCount(), 2);

    // Verify connections are retrievable
    BDataSource[] connections = container.getAllDataSourceConnections();
    Assert.assertEquals(connections.length, 2);
  }

  @Test(groups = {"container", "management"})
  public void testAddFolders() {
    logTestStep("Testing adding folders");

    // Initially no folders
    Assert.assertEquals(container.getFolderCount(), 0);

    // Add folder
    container.add("testFolder", folder);
    Assert.assertEquals(container.getFolderCount(), 1);

    // Add connection to folder
    folder.add("excel1", excelConnection1);

    // Container should still report correct counts
    Assert.assertEquals(container.getDataSourceConnectionCount(), 0); // Direct children only
    Assert.assertEquals(container.getAllDataSourceConnections().length, 1); // Recursive
  }

  @Test(groups = {"container", "management"})
  public void testRemoveConnections() {
    logTestStep("Testing removing connections");

    // Add connections
    container.add("excel1", excelConnection1);
    container.add("excel2", excelConnection2);
    Assert.assertEquals(container.getDataSourceConnectionCount(), 2);

    // Remove one connection
    container.remove("excel1");
    Assert.assertEquals(container.getDataSourceConnectionCount(), 1);

    // Remove all
    container.removeAll();
    Assert.assertEquals(container.getDataSourceConnectionCount(), 0);
  }

////////////////////////////////////////////////////////////////
// Navigation Tree Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"container", "navigation"})
  public void testNavigationDisplayName() {
    logTestStep("Testing navigation display name");

    String displayName = container.getNavDisplayName(null);
    Assert.assertEquals(displayName, "Data Source Connections");
  }

  @Test(groups = {"container", "navigation"})
  public void testNavigationDescription() {
    logTestStep("Testing navigation description");

    // Empty container
    String desc = container.getNavDescription(null);
    Assert.assertEquals(desc, "Data Source Connections");

    // Add connections and folder
    container.add("excel1", excelConnection1);
    container.add("folder1", folder);

    desc = container.getNavDescription(null);
    Assert.assertTrue(desc.contains("1 connection"));
    Assert.assertTrue(desc.contains("1 folder"));
  }

  @Test(groups = {"container", "navigation"})
  public void testNavigationChildren() {
    logTestStep("Testing navigation children");

    // Initially no children
    Assert.assertFalse(container.hasNavChildren());
    Assert.assertEquals(container.getNavChildren().length, 0);

    // Add connections
    container.add("excel1", excelConnection1);
    container.add("folder1", folder);

    // Should have children
    Assert.assertTrue(container.hasNavChildren());
    BINavNode[] navChildren = container.getNavChildren();
    Assert.assertEquals(navChildren.length, 2);
  }

////////////////////////////////////////////////////////////////
// Utility Method Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"container", "utility", "health"})
  public void testHealthyConnectionsCheck() {
    logTestStep("Testing healthy connections check");

    // Initially no healthy connections
    Assert.assertFalse(container.hasHealthyConnections());

    // Add unhealthy connection
    container.add("excel1", excelConnection1);
    Assert.assertFalse(container.hasHealthyConnections());

    // Make connection healthy
    excelConnection1.setConnectionStatus(BDataSource.STATUS_CONNECTED);
    Assert.assertTrue(container.hasHealthyConnections());
  }

  @Test(groups = {"container", "utility", "type"})
  public void testGetConnectionsByType() {
    logTestStep("Testing get connections by type");

    // Add different types of connections
    container.add("excel1", excelConnection1);
    container.add("excel2", excelConnection2);

    // Get Excel connections
    BExcelDataSource[] excelConnections =
      container.getDataSourceConnectionsByType(BExcelDataSource.class);
    Assert.assertEquals(excelConnections.length, 2);

    // Get abstract connections (should include all)
    BDataSource[] allConnections =
      container.getDataSourceConnectionsByType(BDataSource.class);
    Assert.assertEquals(allConnections.length, 2);
  }

  @Test(groups = {"container", "utility", "recursive"})
  public void testRecursiveConnectionCollection() {
    logTestStep("Testing recursive connection collection");

    // Add direct connection
    container.add("excel1", excelConnection1);

    // Add folder with connection
    container.add("folder1", folder);
    folder.add("excel2", excelConnection2);

    // Direct count should be 1
    Assert.assertEquals(container.getDataSourceConnectionCount(), 1);

    // Recursive count should be 2
    BDataSource[] allConnections = container.getAllDataSourceConnections();
    Assert.assertEquals(allConnections.length, 2);
  }

////////////////////////////////////////////////////////////////
// Complex Scenario Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"container", "complex", "nested"})
  public void testNestedFolderStructure() {
    logTestStep("Testing nested folder structure");

    // Create nested structure
    BDataSourceFolder subFolder = new BDataSourceFolder();
    subFolder.setDisplayName("Sub Folder");

    // Add connections at different levels
    container.add("excel1", excelConnection1);
    container.add("folder1", folder);
    folder.add("excel2", excelConnection2);
    folder.add("subFolder", subFolder);

    BExcelDataSource excelConnection3 = new BExcelDataSource();
    subFolder.add("excel3", excelConnection3);

    // Test counts
    Assert.assertEquals(container.getDataSourceConnectionCount(), 1); // Direct only
    Assert.assertEquals(container.getFolderCount(), 1);
    Assert.assertEquals(container.getAllDataSourceConnections().length, 3); // Recursive
  }

  @Test(groups = {"container", "complex", "health"})
  public void testMixedHealthStatus() {
    logTestStep("Testing mixed health status scenario");

    // Add connections with different health status
    container.add("excel1", excelConnection1);
    container.add("excel2", excelConnection2);

    // Set different statuses
    excelConnection1.setConnectionStatus(BDataSource.STATUS_CONNECTED);
    excelConnection2.setConnectionStatus(BDataSource.STATUS_FAILED);

    // Should have healthy connections (at least one is healthy)
    Assert.assertTrue(container.hasHealthyConnections());

    // Make all unhealthy
    excelConnection1.setConnectionStatus(BDataSource.STATUS_FAILED);
    Assert.assertFalse(container.hasHealthyConnections());
  }

  @Test(groups = {"container", "complex", "lifecycle"})
  public void testContainerLifecycle() {
    logTestStep("Testing container lifecycle with connections");

    // Add connections
    container.add("excel1", excelConnection1);
    container.add("folder1", folder);
    folder.add("excel2", excelConnection2);

    // Verify structure
    Assert.assertEquals(container.getDataSourceConnectionCount(), 1);
    Assert.assertEquals(container.getFolderCount(), 1);
    Assert.assertEquals(container.getAllDataSourceConnections().length, 2);

    // Clear container
    container.removeAll();

    // Should be empty
    Assert.assertEquals(container.getDataSourceConnectionCount(), 0);
    Assert.assertEquals(container.getFolderCount(), 0);
    Assert.assertEquals(container.getAllDataSourceConnections().length, 0);
    Assert.assertFalse(container.hasNavChildren());
  }
}
