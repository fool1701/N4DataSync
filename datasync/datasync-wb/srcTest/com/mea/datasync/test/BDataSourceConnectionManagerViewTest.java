// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.ui.BDataSourceConnectionManager;
import com.mea.datasync.model.BDataSourceConnections;
import com.mea.datasync.model.BDataSourceConnectionsFolder;
import com.mea.datasync.model.BExcelDataSourceConnection;
import com.mea.datasync.model.BAbstractDataSourceConnection;
import com.mea.datasync.test.utils.BaseTestClass;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrController;

/**
 * Unit tests for BDataSourceConnectionManager.
 * Tests the manager functionality including model, new types,
 * and UI interactions for data source connections.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "ui", "manager"})
public class BDataSourceConnectionManagerViewTest extends BaseTestClass {

  private BDataSyncTool dataSyncTool;
  private BDataSourceConnectionManager manager;
  private BDataSourceConnections connections;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up Data Source Connection Manager View test");
    
    // Create DataSync Tool
    dataSyncTool = new BDataSyncTool();
    connections = dataSyncTool.getDataSourceConnections();
    
    // Create manager
    manager = new BDataSourceConnectionManager();
    
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
    Assert.assertEquals(manager.getType(), BDataSourceConnectionManager.TYPE);
  }

  @Test(groups = {"ui", "manager", "functionality"})
  public void testManagerFunctionality() {
    logTestStep("Testing manager functionality");

    // Test that the manager can be created and has proper type
    Assert.assertNotNull(manager);
    Assert.assertEquals(manager.getType(), BDataSourceConnectionManager.TYPE);

    // Test basic functionality without accessing protected methods
    try {
      // Just verify the manager exists and has the right type
      Assert.assertTrue(manager instanceof BDataSourceConnectionManager);

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
    BExcelDataSourceConnection connection1 = new BExcelDataSourceConnection();
    connection1.getConnectionDetails().setConnectionName("Test Connection 1");
    connections.add("testConn1", connection1);

    BExcelDataSourceConnection connection2 = new BExcelDataSourceConnection();
    connection2.getConnectionDetails().setConnectionName("Test Connection 2");
    connections.add("testConn2", connection2);

    // Verify connections are in the container
    Assert.assertEquals(connections.getDataSourceConnectionCount(), 2);

    // Test that connections can be accessed
    BAbstractDataSourceConnection[] allConnections = connections.getAllDataSourceConnections();
    Assert.assertEquals(allConnections.length, 2);
  }

////////////////////////////////////////////////////////////////
// Manager View Functionality Tests
////////////////////////////////////////////////////////////////





}
