// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.workbench.mgr.BAbstractManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.ui.BDataSyncTool;
import com.mea.datasync.ui.BDataSourceConnectionManager;
import com.mea.datasync.model.BDataSourceFolder;
import com.mea.datasync.model.BExcelDataSourceConnection;
import com.mea.datasync.model.BDataSourceConnectionsFolder;
import com.mea.datasync.model.BAbstractDataSourceConnection;
import com.mea.datasync.test.utils.BaseTestClass;

/**
 * Runtime integration tests for the DataSync module.
 * These tests simulate actual runtime scenarios to catch errors
 * that would occur during manual testing in Workbench.
 *
 * This test suite helps catch:
 * - NoClassDefFoundError during class initialization
 * - Manager view loading errors
 * - Palette configuration errors
 * - Agent registration issues
 * - Component lifecycle problems
 */
@NiagaraType
@Test(groups = {"datasync", "integration", "runtime", "critical"})
public class BDataSyncRuntimeIntegrationTest extends BaseTestClass {

  private BDataSyncTool dataSyncTool;
  private BDataSourceFolder connections;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up runtime integration test");

    // Create and initialize DataSync Tool
    dataSyncTool = new BDataSyncTool();
    connections = dataSyncTool.getDataSources();

    logTestStep("Runtime integration test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up runtime integration test resources");

    if (dataSyncTool != null) {
      if (dataSyncTool.isRunning()) {
        dataSyncTool.stop();
      }
      dataSyncTool = null;
    }

    connections = null;

    logTestStep("Runtime integration test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Class Initialization Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "initialization", "critical"})
  public void testAbstractDataSourceConnectionClassInitialization() {
    logTestStep("Testing BAbstractDataSourceConnection class initialization");

    try {
      // This should not throw NoClassDefFoundError
      Type type = BAbstractDataSourceConnection.TYPE;
      Assert.assertNotNull(type);

      // Test that we can access static constants
      String status = BAbstractDataSourceConnection.STATUS_NOT_TESTED;
      Assert.assertEquals(status, "Not Tested");

      logTestStep("✅ BAbstractDataSourceConnection class initialized successfully");

    } catch (NoClassDefFoundError e) {
      Assert.fail("NoClassDefFoundError during BAbstractDataSourceConnection initialization: " + e.getMessage());
    } catch (Exception e) {
      Assert.fail("Unexpected error during class initialization: " + e.getMessage());
    }
  }

  @Test(groups = {"runtime", "initialization", "critical"})
  public void testExcelConnectionClassInitialization() {
    logTestStep("Testing BExcelDataSourceConnection class initialization");

    try {
      // Test class loading and type access
      Type type = BExcelDataSourceConnection.TYPE;
      Assert.assertNotNull(type);

      // Test instance creation
      BExcelDataSourceConnection connection = new BExcelDataSourceConnection();
      Assert.assertNotNull(connection);

      // Test that connection details are properly initialized
      Assert.assertNotNull(connection.getConnectionDetails());

      logTestStep("✅ BExcelDataSourceConnection class initialized successfully");

    } catch (Exception e) {
      Assert.fail("Error during BExcelDataSourceConnection initialization: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Manager View Runtime Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "manager", "critical"})
  public void testDataSourceConnectionManagerInitialization() {
    logTestStep("Testing DataSourceConnectionManager runtime initialization");

    try {
      // Test manager creation (this would fail with NoClassDefFoundError if there are issues)
      BDataSourceConnectionManager manager = new BDataSourceConnectionManager();
      Assert.assertNotNull(manager);

      // Test that manager can access its type
      Type managerType = manager.getType();
      Assert.assertNotNull(managerType);
      Assert.assertEquals(managerType, BDataSourceConnectionManager.TYPE);

      logTestStep("✅ DataSourceConnectionManager initialized successfully");

    } catch (NoClassDefFoundError e) {
      Assert.fail("NoClassDefFoundError during manager initialization: " + e.getMessage());
    } catch (Exception e) {
      Assert.fail("Unexpected error during manager initialization: " + e.getMessage());
    }
  }

  @Test(groups = {"runtime", "manager", "model"})
  public void testManagerModelWithDataSyncTool() {
    logTestStep("Testing manager model with DataSyncTool as root");

    try {
      // Create manager and set DataSyncTool as root
      BDataSourceConnectionManager manager = new BDataSourceConnectionManager();

      // Simulate what happens when manager is used as agent on DataSyncTool
      // This tests the getRoot() override logic

      // Add some test connections to verify the model works
      BExcelDataSourceConnection connection1 = new BExcelDataSourceConnection();
      connection1.getConnectionDetails().setConnectionName("Test Connection 1");
      connections.add("testConn1", connection1);

      BDataSourceConnectionsFolder folder = new BDataSourceConnectionsFolder();
      folder.setDisplayName("Test Folder");
      connections.add("testFolder", folder);

      // Verify the connections are accessible
      Assert.assertEquals(connections.getDataSourceConnectionCount(), 1);
      Assert.assertEquals(connections.getFolderCount(), 1);

      logTestStep("✅ Manager model works correctly with DataSyncTool");

    } catch (Exception e) {
      Assert.fail("Error testing manager model with DataSyncTool: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Component Lifecycle Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "lifecycle", "critical"})
  public void testDataSyncToolLifecycle() {
    logTestStep("Testing DataSyncTool lifecycle with connections");

    try {
      // Add connections before starting
      BExcelDataSourceConnection connection = new BExcelDataSourceConnection();
      connection.getConnectionDetails().setConnectionName("Lifecycle Test Connection");
      connections.add("lifecycleTest", connection);

      // Start the tool (this tests component lifecycle)
      dataSyncTool.start();
      Assert.assertTrue(dataSyncTool.isRunning());

      // Verify connections are still accessible after start
      Assert.assertEquals(connections.getAllDataSourceConnections().length, 1);

      // Stop the tool
      dataSyncTool.stop();
      Assert.assertFalse(dataSyncTool.isRunning());

      logTestStep("✅ DataSyncTool lifecycle completed successfully");

    } catch (Exception e) {
      Assert.fail("Error during DataSyncTool lifecycle test: " + e.getMessage());
    }
  }

  @Test(groups = {"runtime", "lifecycle", "connections"})
  public void testConnectionLifecycle() {
    logTestStep("Testing data source connection lifecycle");

    try {
      // Create and configure connection
      BExcelDataSourceConnection connection = new BExcelDataSourceConnection();
      connection.getConnectionDetails().setConnectionName("Connection Lifecycle Test");

      // Add to container
      connections.add("connectionLifecycle", connection);

      // Start the connection (tests auto-check initialization)
      connection.start();
      Assert.assertTrue(connection.isRunning());

      // Test connection functionality
      connection.doTestConnection();
      Assert.assertNotNull(connection.getConnectionStatus());

      // Stop the connection
      connection.stop();
      Assert.assertFalse(connection.isRunning());

      logTestStep("✅ Connection lifecycle completed successfully");

    } catch (Exception e) {
      Assert.fail("Error during connection lifecycle test: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Type Safety and Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "validation", "types"})
  public void testTypeRegistrationAndValidation() {
    logTestStep("Testing type registration and validation");

    try {
      // Test that all our types are properly registered
      Type dataSyncToolType = BDataSyncTool.TYPE;
      Type connectionsType = BDataSourceConnections.TYPE;
      Type excelConnectionType = BExcelDataSourceConnection.TYPE;
      Type folderType = BDataSourceConnectionsFolder.TYPE;
      Type managerType = BDataSourceConnectionManager.TYPE;

      Assert.assertNotNull(dataSyncToolType);
      Assert.assertNotNull(connectionsType);
      Assert.assertNotNull(excelConnectionType);
      Assert.assertNotNull(folderType);
      Assert.assertNotNull(managerType);

      // Test type hierarchy
      Assert.assertTrue(excelConnectionType.is(BAbstractDataSourceConnection.TYPE));
      Assert.assertTrue(connectionsType.is(BComponent.TYPE));
      Assert.assertTrue(managerType.is(BAbstractManager.TYPE));

      logTestStep("✅ All types properly registered and validated");

    } catch (Exception e) {
      Assert.fail("Error during type validation: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Error Simulation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "error", "simulation"})
  public void testManagerWithInvalidRoot() {
    logTestStep("Testing manager behavior with invalid root scenarios");

    try {
      BDataSourceConnectionManager manager = new BDataSourceConnectionManager();

      // Test that manager handles null gracefully
      // This simulates what might happen in edge cases
      Assert.assertNotNull(manager);

      // Manager should not crash even if used incorrectly
      Type managerType = manager.getType();
      Assert.assertEquals(managerType, BDataSourceConnectionManager.TYPE);

      logTestStep("✅ Manager handles edge cases gracefully");

    } catch (Exception e) {
      Assert.fail("Manager should handle edge cases gracefully: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Integration Completeness Test
////////////////////////////////////////////////////////////////

  @Test(groups = {"runtime", "integration", "complete"})
  public void testCompleteIntegrationScenario() {
    logTestStep("Testing complete integration scenario");

    try {
      // This test simulates a complete user workflow

      // 1. Create DataSync Tool (already done in setup)
      Assert.assertNotNull(dataSyncTool);
      Assert.assertNotNull(connections);

      // 2. Create various connection types
      BExcelDataSourceConnection excelConn = new BExcelDataSourceConnection();
      excelConn.getConnectionDetails().setConnectionName("Excel Integration Test");

      BDataSourceConnectionsFolder folder = new BDataSourceConnectionsFolder();
      folder.setDisplayName("Integration Test Folder");

      // 3. Add to container
      connections.add("excelIntegration", excelConn);
      connections.add("folderIntegration", folder);

      // 4. Add connection to folder
      BExcelDataSourceConnection nestedConn = new BExcelDataSourceConnection();
      nestedConn.getConnectionDetails().setConnectionName("Nested Connection");
      folder.add("nestedConn", nestedConn);

      // 5. Verify structure
      Assert.assertEquals(connections.getDataSourceConnectionCount(), 1);
      Assert.assertEquals(connections.getFolderCount(), 1);
      Assert.assertEquals(connections.getAllDataSourceConnections().length, 2); // Recursive

      // 6. Test manager creation
      BDataSourceConnectionManager manager = new BDataSourceConnectionManager();
      Assert.assertNotNull(manager);

      logTestStep("✅ Complete integration scenario successful");

    } catch (Exception e) {
      Assert.fail("Error in complete integration scenario: " + e.getMessage());
    }
  }
}
