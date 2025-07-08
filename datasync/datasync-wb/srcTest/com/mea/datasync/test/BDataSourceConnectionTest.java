// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.status.BStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mea.datasync.model.BDataSource;
import com.mea.datasync.model.BAutoCheckConfig;
import com.mea.datasync.model.BConnection;
import com.mea.datasync.test.utils.BaseTestClass;

/**
 * Comprehensive unit tests for BDataSource.
 * Tests the base functionality including health monitoring, auto-checking,
 * and abstract method contracts.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "connection", "abstract"})
public class BDataSourceConnectionTest extends BaseTestClass {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BDataSourceConnectionTest(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceConnectionTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private TestDataSourceConnection testConnection;
  private BAutoCheckConfig autoCheckConfig;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up test data source connection");
    
    // Create test implementation
    testConnection = new TestDataSourceConnection();
    
    // Create auto-check configuration
    autoCheckConfig = new BAutoCheckConfig();
    autoCheckConfig.setEnabled(false); // Disable for testing
    testConnection.setAutoCheckConfig(autoCheckConfig);
    
    logTestStep("Test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up test resources");
    
    if (testConnection != null && testConnection.isRunning()) {
      testConnection.stop();
    }
    testConnection = null;
    autoCheckConfig = null;
    
    logTestStep("Test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Health Properties Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"health", "properties"})
  public void testDefaultHealthProperties() {
    logTestStep("Testing default health property values");
    
    // Given: New connection instance
    // When: Checking default values
    // Then: Should have expected defaults
    Assert.assertEquals(testConnection.getConnectionStatus(), 
                       BDataSource.STATUS_NOT_TESTED);
    Assert.assertTrue(testConnection.getLastConnectionTest().isNull());
    Assert.assertTrue(testConnection.getLastSuccessfulConnection().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 0);
  }

  @Test(groups = {"health", "properties"})
  public void testHealthStatusMapping() {
    logTestStep("Testing health status mapping to BStatus");
    
    // Test different connection statuses
    testConnection.setConnectionStatus(BDataSource.STATUS_CONNECTED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.ok);

    testConnection.setConnectionStatus(BDataSource.STATUS_FAILED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.fault);

    testConnection.setConnectionStatus(BDataSource.STATUS_TESTING);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.stale);

    testConnection.setConnectionStatus(BDataSource.STATUS_NOT_TESTED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.nullStatus);
  }

  @Test(groups = {"health", "connection"})
  public void testConnectionHealthyCheck() {
    logTestStep("Testing connection healthy check");
    
    // Initially not healthy
    Assert.assertFalse(testConnection.isConnectionHealthy());
    
    // Set to connected
    testConnection.setConnectionStatus(BDataSource.STATUS_CONNECTED);
    Assert.assertTrue(testConnection.isConnectionHealthy());

    // Set to failed
    testConnection.setConnectionStatus(BDataSource.STATUS_FAILED);
    Assert.assertFalse(testConnection.isConnectionHealthy());
  }

////////////////////////////////////////////////////////////////
// Connection Testing Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"connection", "testing"})
  public void testSuccessfulConnectionTest() {
    logTestStep("Testing successful connection test");
    
    // Given: Connection configured to succeed
    testConnection.setShouldSucceed(true);
    
    // When: Testing connection
    testConnection.doTestConnection();
    
    // Then: Should be marked as connected
    Assert.assertEquals(testConnection.getConnectionStatus(),
                       BDataSource.STATUS_CONNECTED);
    Assert.assertFalse(testConnection.getLastConnectionTest().isNull());
    Assert.assertFalse(testConnection.getLastSuccessfulConnection().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 0);
    Assert.assertTrue(testConnection.isConnectionHealthy());
  }

  @Test(groups = {"connection", "testing"})
  public void testFailedConnectionTest() {
    logTestStep("Testing failed connection test");
    
    // Given: Connection configured to fail
    testConnection.setShouldSucceed(false);
    testConnection.setErrorMessage("Test connection failure");
    
    // When: Testing connection
    testConnection.doTestConnection();
    
    // Then: Should be marked as failed
    Assert.assertEquals(testConnection.getConnectionStatus(),
                       BDataSource.STATUS_FAILED);
    Assert.assertFalse(testConnection.getLastConnectionTest().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "Test connection failure");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 1);
    Assert.assertFalse(testConnection.isConnectionHealthy());
  }

////////////////////////////////////////////////////////////////
// Auto-Check Configuration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "configuration"})
  public void testAutoCheckConfiguration() {
    logTestStep("Testing auto-check configuration");
    
    // Given: Auto-check configuration
    BAutoCheckConfig config = testConnection.getAutoCheckConfig();
    Assert.assertNotNull(config);
    
    // When: Modifying configuration
    config.setEnabled(true);
    config.setCheckInterval(BRelTime.make(60000)); // 1 minute
    
    // Then: Should be properly configured
    Assert.assertTrue(config.getEnabled());
    Assert.assertEquals(config.getCheckInterval().getMillis(), 60000);
  }

////////////////////////////////////////////////////////////////
// Type Name and Summary Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"metadata", "display"})
  public void testDataSourceTypeName() {
    logTestStep("Testing data source type name");
    
    // Test type name
    String typeName = testConnection.getDataSourceTypeName();
    Assert.assertNotNull(typeName);
    Assert.assertEquals(typeName, "Test");
  }

  @Test(groups = {"metadata", "display"})
  public void testConnectionSummary() {
    logTestStep("Testing connection summary");
    
    // Test connection summary
    String summary = testConnection.getConnectionSummary();
    Assert.assertNotNull(summary);
    Assert.assertFalse(summary.trim().isEmpty());
    Assert.assertTrue(summary.contains("Test"));
  }

////////////////////////////////////////////////////////////////
// Test Implementation Class
////////////////////////////////////////////////////////////////

  /**
   * Concrete test implementation of BDataSource
   * for testing purposes.
   */
  public static class TestDataSourceConnection extends BDataSource {
    
    private boolean shouldSucceed = true;
    private String errorMessage = "Test error";
    
    public TestDataSourceConnection() {
      super();
    }
    
    public void setShouldSucceed(boolean shouldSucceed) {
      this.shouldSucceed = shouldSucceed;
    }
    
    public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
    }
    
    @Override
    protected BDataSource.ConnectionTestResult performConnectionTest() {
      if (shouldSucceed) {
        return new BDataSource.ConnectionTestResult(true, "Test connection successful");
      } else {
        return new BDataSource.ConnectionTestResult(false, errorMessage);
      }
    }
    
    @Override
    public String getDataSourceTypeName() {
      return "Test";
    }
    
    @Override
    public String getConnectionSummary() {
      return "Test Connection - Status: " + getConnectionStatus();
    }
    
    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(TestDataSourceConnection.class);
  }
}
