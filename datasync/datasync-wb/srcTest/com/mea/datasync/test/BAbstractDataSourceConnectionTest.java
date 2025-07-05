// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.status.BStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mea.datasync.model.BAbstractDataSourceConnection;
import com.mea.datasync.model.BAutoCheckConfig;
import com.mea.datasync.model.BConnectionDetails;
import com.mea.datasync.test.utils.BaseTestClass;

/**
 * Comprehensive unit tests for BAbstractDataSourceConnection.
 * Tests the base functionality including health monitoring, auto-checking,
 * and abstract method contracts.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "connection", "abstract"})
public class BAbstractDataSourceConnectionTest extends BaseTestClass {

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
                       BAbstractDataSourceConnection.STATUS_NOT_TESTED);
    Assert.assertTrue(testConnection.getLastConnectionTest().isNull());
    Assert.assertTrue(testConnection.getLastSuccessfulConnection().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 0);
  }

  @Test(groups = {"health", "properties"})
  public void testHealthStatusMapping() {
    logTestStep("Testing health status mapping to BStatus");
    
    // Test different connection statuses
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_CONNECTED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.ok);
    
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_FAILED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.fault);
    
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_TESTING);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.stale);
    
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_NOT_TESTED);
    Assert.assertEquals(testConnection.getHealthStatus(), BStatus.nullStatus);
  }

  @Test(groups = {"health", "connection"})
  public void testConnectionHealthyCheck() {
    logTestStep("Testing connection healthy check");
    
    // Initially not healthy
    Assert.assertFalse(testConnection.isConnectionHealthy());
    
    // Set to connected
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_CONNECTED);
    Assert.assertTrue(testConnection.isConnectionHealthy());
    
    // Set to failed
    testConnection.setConnectionStatus(BAbstractDataSourceConnection.STATUS_FAILED);
    Assert.assertFalse(testConnection.isConnectionHealthy());
  }

////////////////////////////////////////////////////////////////
// Connection Testing Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"connection", "testing"})
  public void testSuccessfulConnectionTest() {
    logTestStep("Testing successful connection test");
    
    // Given: Connection configured for success
    testConnection.setShouldSucceed(true);
    
    // When: Testing connection
    testConnection.doTestConnection();
    
    // Then: Should be marked as connected
    Assert.assertEquals(testConnection.getConnectionStatus(), 
                       BAbstractDataSourceConnection.STATUS_CONNECTED);
    Assert.assertFalse(testConnection.getLastConnectionTest().isNull());
    Assert.assertFalse(testConnection.getLastSuccessfulConnection().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 0);
    Assert.assertTrue(testConnection.isConnectionHealthy());
  }

  @Test(groups = {"connection", "testing"})
  public void testFailedConnectionTest() {
    logTestStep("Testing failed connection test");
    
    // Given: Connection configured for failure
    testConnection.setShouldSucceed(false);
    testConnection.setErrorMessage("Test connection failure");
    
    // When: Testing connection
    testConnection.doTestConnection();
    
    // Then: Should be marked as failed
    Assert.assertEquals(testConnection.getConnectionStatus(), 
                       BAbstractDataSourceConnection.STATUS_FAILED);
    Assert.assertFalse(testConnection.getLastConnectionTest().isNull());
    Assert.assertEquals(testConnection.getLastConnectionError(), "Test connection failure");
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 1);
    Assert.assertFalse(testConnection.isConnectionHealthy());
  }

  @Test(groups = {"connection", "testing"})
  public void testConsecutiveFailureTracking() {
    logTestStep("Testing consecutive failure tracking");
    
    // Given: Connection configured for failure
    testConnection.setShouldSucceed(false);
    testConnection.setErrorMessage("Consecutive failure test");
    
    // When: Testing connection multiple times
    testConnection.doTestConnection();
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 1);
    
    testConnection.doTestConnection();
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 2);
    
    testConnection.doTestConnection();
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 3);
    
    // When: Connection succeeds
    testConnection.setShouldSucceed(true);
    testConnection.doTestConnection();
    
    // Then: Consecutive failures should reset
    Assert.assertEquals(testConnection.getConsecutiveFailures(), 0);
  }

////////////////////////////////////////////////////////////////
// Auto-Check Configuration Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "configuration"})
  public void testAutoCheckConfigurationDefaults() {
    logTestStep("Testing auto-check configuration defaults");
    
    BAutoCheckConfig config = testConnection.getAutoCheckConfig();
    Assert.assertNotNull(config);
    
    // Should have reasonable defaults
    Assert.assertNotNull(config.getCheckInterval());
    Assert.assertTrue(config.getCheckInterval().getMillis() > 0);
    Assert.assertTrue(config.getRetryCount() >= 0);
    Assert.assertTrue(config.getFailureThreshold() > 0);
  }

  @Test(groups = {"autocheck", "lifecycle"})
  public void testAutoCheckLifecycle() throws Exception {
    logTestStep("Testing auto-check lifecycle management");
    
    // Given: Auto-check enabled
    BAutoCheckConfig config = testConnection.getAutoCheckConfig();
    config.setEnabled(true);
    config.setCheckInterval(BRelTime.make(1000)); // 1 second for testing
    
    // When: Starting component
    testConnection.start();
    
    // Then: Should start successfully
    Assert.assertTrue(testConnection.isRunning());
    
    // When: Stopping component
    testConnection.stop();
    
    // Then: Should stop cleanly
    Assert.assertFalse(testConnection.isRunning());
  }

////////////////////////////////////////////////////////////////
// Abstract Method Contract Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"abstract", "contract"})
  public void testAbstractMethodImplementation() {
    logTestStep("Testing abstract method implementations");
    
    // Test data source type name
    String typeName = testConnection.getDataSourceTypeName();
    Assert.assertNotNull(typeName);
    Assert.assertFalse(typeName.trim().isEmpty());
    Assert.assertEquals(typeName, "Test");
    
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
   * Concrete test implementation of BAbstractDataSourceConnection
   * for testing purposes.
   */
  public static class TestDataSourceConnection extends BAbstractDataSourceConnection {
    
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
    protected ConnectionTestResult performConnectionTest() {
      if (shouldSucceed) {
        return new ConnectionTestResult(true, "Test connection successful");
      } else {
        return new ConnectionTestResult(false, errorMessage);
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
