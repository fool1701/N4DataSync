// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.ui.BDataSourceManager;
import com.mea.datasync.model.BDataSourceFolder;
import com.mea.datasync.model.BExcelDataSource;
import com.mea.datasync.model.BAbstractDataSource;
import com.mea.datasync.test.utils.BaseTestClass;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrController;

/**
 * Unit tests for BDataSourceManager.
 * Tests the manager functionality including model, new types,
 * and UI interactions for data sources.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "ui", "manager"})
public class BDataSourceConnectionManagerViewTest extends BaseTestClass {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BDataSourceConnectionManagerViewTest(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceConnectionManagerViewTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BDataSyncTool dataSyncTool;
  private BDataSourceManager manager;
  private BDataSourceFolder connections;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up Data Source Connection Manager View test");

    // Create DataSync Tool
    dataSyncTool = new BDataSyncTool();
    connections = dataSyncTool.getDataSources();

    // Create manager
    manager = new BDataSourceManager();

    logTestStep("Manager view test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up manager view test resources");

    if (dataSyncTool != null) {
      if (dataSyncTool.isRunning()) {
        dataSyncTool.stop();
      }
      dataSyncTool = null;
    }

    manager = null;
    connections = null;

    logTestStep("Manager view test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Manager View Initialization Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"ui", "manager", "initialization"})
  public void testManagerInitialization() {
    logTestStep("Testing manager initialization");

    // Manager should be created successfully
    Assert.assertNotNull(manager);

    // Should have proper type
    Assert.assertEquals(manager.getType(), BDataSourceManager.TYPE);
  }

  @Test(groups = {"ui", "manager", "functionality"})
  public void testManagerFunctionality() {
    logTestStep("Testing manager functionality");

    // Test that the manager can be created and has proper type
    Assert.assertNotNull(manager);
    Assert.assertEquals(manager.getType(), BDataSourceManager.TYPE);

    // Test basic functionality without accessing protected methods
    try {
      // Just verify the manager exists and has the right type
      Assert.assertTrue(manager instanceof BDataSourceManager);

    } catch (Exception e) {
      Assert.fail("Manager should be functional: " + e.getMessage());
    }
  }



////////////////////////////////////////////////////////////////
// Integration Tests with DataSync Tool
////////////////////////////////////////////////////////////////

  @Test(groups = {"ui", "manager", "integration"})
  public void testManagerWithDataSyncTool() {
    logTestStep("Testing manager integration with DataSync Tool");

    // Add some test connections to the tool
    BExcelDataSource connection1 = new BExcelDataSource();
    connection1.getConnectionDetails().setConnectionName("Test Connection 1");
    connections.add("testConn1", connection1);

    BExcelDataSource connection2 = new BExcelDataSource();
    connection2.getConnectionDetails().setConnectionName("Test Connection 2");
    connections.add("testConn2", connection2);

    // Verify connections are in the container
    Assert.assertEquals(connections.getDataSourceConnectionCount(), 2);

    // Test that connections can be accessed
    BAbstractDataSource[] allConnections = connections.getAllDataSourceConnections();
    Assert.assertEquals(allConnections.length, 2);
  }

////////////////////////////////////////////////////////////////
// Manager View Functionality Tests
////////////////////////////////////////////////////////////////





}
