// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.status.BStatus;

/**
 * BDataSource serves as the base class for all data source
 * types in the N4-DataSync module. This concrete base class defines
 * the common interface and behavior for connecting to external data sources.
 *
 * Following Niagara patterns from BAbstractFile and BPingMonitor, this class
 * provides:
 * - Common health monitoring properties
 * - Auto-checking mechanism with configurable parameters
 * - Manual connection testing action
 * - Default implementations for type-specific connection logic
 *
 * Concrete implementations should extend this class for specific data source
 * types (Excel, CSV, Database, Google Sheets, etc.) and override the default
 * implementations as needed.
 *
 * ARCHITECTURAL NOTE: This class was converted from abstract to concrete to
 * comply with Niagara Framework requirements. Abstract classes cannot be used
 * in manager contexts because the framework needs to instantiate types via
 * Type.getInstance(), which fails for abstract classes.
 */
@NiagaraType
// Connection Details Component - Abstract property to be overridden by subclasses
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:Connection",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY
)
// Health Properties
@NiagaraProperty(
  name = "connectionStatus",
  type = "String",
  defaultValue = "Not Tested"
)
@NiagaraProperty(
  name = "lastConnectionTest",
  type = "baja:AbsTime",
  defaultValue = "null"
)
@NiagaraProperty(
  name = "lastSuccessfulConnection",
  type = "baja:AbsTime",
  defaultValue = "null"
)
@NiagaraProperty(
  name = "lastConnectionError",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "consecutiveFailures",
  type = "int",
  defaultValue = "0"
)
// Auto-Check Configuration Component
@NiagaraProperty(
  name = "autoCheckConfig",
  type = "datasync:AutoCheckConfig",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY
)
// Manual Connection Test Action
@NiagaraAction(
  name = "testConnection"
)
public class BDataSource extends BComponent {

  // Connection Status Constants
  public static final String STATUS_NOT_TESTED = "Not Tested";
  public static final String STATUS_CONNECTED = "Connected";
  public static final String STATUS_FAILED = "Failed";
  public static final String STATUS_TESTING = "Testing...";
  public static final String STATUS_AUTO_CHECK_DISABLED = "Auto-Check Disabled";

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataSource(2018014743)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionDetails"

  /**
   * Slot for the {@code connectionDetails} property.
   *  Connection Details Component - Abstract property to be overridden by subclasses
   * @see #getConnectionDetails
   * @see #setConnectionDetails
   */
  public static final Property connectionDetails = newProperty(Flags.READONLY | Flags.SUMMARY, new BConnection(), null);

  /**
   * Get the {@code connectionDetails} property.
   *  Connection Details Component - Abstract property to be overridden by subclasses
   * @see #connectionDetails
   */
  public BConnection getConnectionDetails() { return (BConnection)get(connectionDetails); }

  /**
   * Set the {@code connectionDetails} property.
   *  Connection Details Component - Abstract property to be overridden by subclasses
   * @see #connectionDetails
   */
  public void setConnectionDetails(BConnection v) { set(connectionDetails, v, null); }

  //endregion Property "connectionDetails"

  //region Property "connectionStatus"

  /**
   * Slot for the {@code connectionStatus} property.
   *  Health Properties
   * @see #getConnectionStatus
   * @see #setConnectionStatus
   */
  public static final Property connectionStatus = newProperty(0, BString.make("Not Tested"), null);

  /**
   * Get the {@code connectionStatus} property.
   *  Health Properties
   * @see #connectionStatus
   */
  public String getConnectionStatus() { return getString(connectionStatus); }

  /**
   * Set the {@code connectionStatus} property.
   *  Health Properties
   * @see #connectionStatus
   */
  public void setConnectionStatus(String v) { setString(connectionStatus, v, null); }

  //endregion Property "connectionStatus"

  //region Property "lastConnectionTest"

  /**
   * Slot for the {@code lastConnectionTest} property.
   * @see #getLastConnectionTest
   * @see #setLastConnectionTest
   */
  public static final Property lastConnectionTest = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastConnectionTest} property.
   * @see #lastConnectionTest
   */
  public BAbsTime getLastConnectionTest() { return (BAbsTime)get(lastConnectionTest); }

  /**
   * Set the {@code lastConnectionTest} property.
   * @see #lastConnectionTest
   */
  public void setLastConnectionTest(BAbsTime v) { set(lastConnectionTest, v, null); }

  //endregion Property "lastConnectionTest"

  //region Property "lastSuccessfulConnection"

  /**
   * Slot for the {@code lastSuccessfulConnection} property.
   * @see #getLastSuccessfulConnection
   * @see #setLastSuccessfulConnection
   */
  public static final Property lastSuccessfulConnection = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastSuccessfulConnection} property.
   * @see #lastSuccessfulConnection
   */
  public BAbsTime getLastSuccessfulConnection() { return (BAbsTime)get(lastSuccessfulConnection); }

  /**
   * Set the {@code lastSuccessfulConnection} property.
   * @see #lastSuccessfulConnection
   */
  public void setLastSuccessfulConnection(BAbsTime v) { set(lastSuccessfulConnection, v, null); }

  //endregion Property "lastSuccessfulConnection"

  //region Property "lastConnectionError"

  /**
   * Slot for the {@code lastConnectionError} property.
   * @see #getLastConnectionError
   * @see #setLastConnectionError
   */
  public static final Property lastConnectionError = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code lastConnectionError} property.
   * @see #lastConnectionError
   */
  public String getLastConnectionError() { return getString(lastConnectionError); }

  /**
   * Set the {@code lastConnectionError} property.
   * @see #lastConnectionError
   */
  public void setLastConnectionError(String v) { setString(lastConnectionError, v, null); }

  //endregion Property "lastConnectionError"

  //region Property "consecutiveFailures"

  /**
   * Slot for the {@code consecutiveFailures} property.
   * @see #getConsecutiveFailures
   * @see #setConsecutiveFailures
   */
  public static final Property consecutiveFailures = newProperty(0, BInteger.DEFAULT.as(BInteger.class).getInt(), null);

  /**
   * Get the {@code consecutiveFailures} property.
   * @see #consecutiveFailures
   */
  public int getConsecutiveFailures() { return getInt(consecutiveFailures); }

  /**
   * Set the {@code consecutiveFailures} property.
   * @see #consecutiveFailures
   */
  public void setConsecutiveFailures(int v) { setInt(consecutiveFailures, v, null); }

  //endregion Property "consecutiveFailures"

  //region Property "autoCheckConfig"

  /**
   * Slot for the {@code autoCheckConfig} property.
   *  Auto-Check Configuration Component
   * @see #getAutoCheckConfig
   * @see #setAutoCheckConfig
   */
  public static final Property autoCheckConfig = newProperty(Flags.READONLY | Flags.SUMMARY, new BAutoCheckConfig(), null);

  /**
   * Get the {@code autoCheckConfig} property.
   *  Auto-Check Configuration Component
   * @see #autoCheckConfig
   */
  public BAutoCheckConfig getAutoCheckConfig() { return (BAutoCheckConfig)get(autoCheckConfig); }

  /**
   * Set the {@code autoCheckConfig} property.
   *  Auto-Check Configuration Component
   * @see #autoCheckConfig
   */
  public void setAutoCheckConfig(BAutoCheckConfig v) { set(autoCheckConfig, v, null); }

  //endregion Property "autoCheckConfig"

  //region Action "testConnection"

  /**
   * Slot for the {@code testConnection} action.
   *  Manual Connection Test Action
   * @see #testConnection()
   */
  public static final Action testConnection = newAction(0, null);

  /**
   * Invoke the {@code testConnection} action.
   *  Manual Connection Test Action
   * @see #testConnection
   */
  public void testConnection() { invoke(testConnection, null, null); }

  //endregion Action "testConnection"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDataSource() {
    // Initialize with default auto-check configuration
    // Subclasses can override these defaults in their constructors
  }

////////////////////////////////////////////////////////////////
// Component Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Called when the component is started.
   * Initializes auto-checking if enabled.
   */
  @Override
  public void started() throws Exception {
    super.started();

    // Start auto-checking if enabled
    BAutoCheckConfig config = getAutoCheckConfig();
    if (config.getEnabled()) {
      startAutoChecking();
    }

    System.out.println("🔌 " + getClass().getSimpleName() + " started - Auto-check: " +
                      (config.getEnabled() ? "Enabled (" + config.getCheckInterval() + ")" : "Disabled"));
  }

  /**
   * Called when the component is stopped.
   * Stops auto-checking.
   */
  @Override
  public void stopped() throws Exception {
    stopAutoChecking();
    super.stopped();
    System.out.println("🔌 " + getClass().getSimpleName() + " stopped");
  }

  /**
   * Handle property changes, particularly auto-check configuration changes.
   */
  @Override
  public void changed(Property property, Context context) {
    super.changed(property, context);

    if (!isRunning()) return;

    // Handle auto-check configuration changes
    if (property.equals(autoCheckConfig)) {
      BAutoCheckConfig config = getAutoCheckConfig();
      if (config.getEnabled()) {
        startAutoChecking();
      } else {
        stopAutoChecking();
      }
      System.out.println("🔧 Auto-check configuration changed: " +
                        (config.getEnabled() ? "Enabled" : "Disabled"));
    }
  }

////////////////////////////////////////////////////////////////
// Default Implementations - Can be overridden by subclasses
////////////////////////////////////////////////////////////////

  /**
   * Perform the actual connection test for this specific data source type.
   * This default implementation provides a generic "not implemented" response.
   * Concrete subclasses should override this method to handle their specific
   * connection logic for their data source type.
   *
   * @return ConnectionTestResult containing success status and details
   */
  protected ConnectionTestResult performConnectionTest() {
    return new ConnectionTestResult(false,
      "Connection test not implemented for " + getDataSourceTypeName() +
      ". Please use a specific data source connection type (Excel, CSV, etc.)");
  }

  /**
   * Get the display name for this data source type.
   * This default implementation returns "DataSource" for the base class.
   * Concrete subclasses should override this method to return their specific type name.
   * Used in UI and logging.
   *
   * @return human-readable name for this data source type
   */
  public String getDataSourceTypeName() {
    return "DataSource";
  }

  /**
   * Get a summary of the connection configuration.
   * This default implementation provides basic information.
   * Concrete subclasses should override this method to provide specific details.
   * Used for navigation tree descriptions and logging.
   *
   * @return human-readable connection summary
   */
  public String getConnectionSummary() {
    BConnection details = getConnectionDetails();
    String detailsSummary = (details != null) ? details.getConnectionSummary() : "No connection details";
    return String.format("%s Connection - Status: %s - %s",
                        getDataSourceTypeName(),
                        getConnectionStatus(),
                        detailsSummary);
  }

////////////////////////////////////////////////////////////////
// Public API Methods
////////////////////////////////////////////////////////////////

  /**
   * Test the connection manually (invoked by the testConnection action).
   * This method coordinates the testing process and updates all health properties.
   */
  public void doTestConnection() {
    System.out.println("🧪 Testing connection for " + getDataSourceTypeName() + "...");

    setConnectionStatus(STATUS_TESTING);
    setLastConnectionTest(BAbsTime.now());

    try {
      ConnectionTestResult result = performConnectionTest();
      handleConnectionTestResult(result);
    } catch (Exception e) {
      handleConnectionTestResult(new ConnectionTestResult(false,
        "Unexpected error during connection test: " + e.getMessage()));
    }
  }

  /**
   * Check if the connection is currently healthy.
   *
   * @return true if the last connection test was successful
   */
  public boolean isConnectionHealthy() {
    return STATUS_CONNECTED.equals(getConnectionStatus());
  }

  /**
   * Get the current health status as a BStatus object.
   * Useful for integration with Niagara's status system.
   *
   * @return BStatus representing current connection health
   */
  public BStatus getHealthStatus() {
    String status = getConnectionStatus();

    if (STATUS_CONNECTED.equals(status)) {
      return BStatus.ok;
    } else if (STATUS_FAILED.equals(status)) {
      return BStatus.fault;
    } else if (STATUS_TESTING.equals(status)) {
      return BStatus.stale;
    } else {
      return BStatus.nullStatus; // Not tested
    }
  }

////////////////////////////////////////////////////////////////
// Auto-Checking Implementation
////////////////////////////////////////////////////////////////

  private Thread autoCheckThread;
  private volatile boolean autoCheckRunning = false;

  /**
   * Start the auto-checking mechanism.
   */
  private void startAutoChecking() {
    stopAutoChecking(); // Stop any existing auto-check

    BAutoCheckConfig config = getAutoCheckConfig();
    if (!config.getEnabled()) return;

    autoCheckRunning = true;
    autoCheckThread = new Thread(this::autoCheckLoop, "DataSourceAutoCheck-" + getName());
    autoCheckThread.setDaemon(true);
    autoCheckThread.start();

    System.out.println("🔄 Auto-checking started for " + getDataSourceTypeName() +
                      " (interval: " + config.getCheckInterval() + ")");
  }

  /**
   * Stop the auto-checking mechanism.
   */
  private void stopAutoChecking() {
    autoCheckRunning = false;
    if (autoCheckThread != null) {
      autoCheckThread.interrupt();
      autoCheckThread = null;
    }
  }

  /**
   * Auto-check loop - runs in background thread.
   * Based on BPingMonitor pattern.
   */
  private void autoCheckLoop() {
    BAutoCheckConfig config = getAutoCheckConfig();
    long intervalMs = config.getCheckInterval().getMillis();

    while (autoCheckRunning && isRunning()) {
      try {
        Thread.sleep(Math.min(intervalMs, 5000)); // Check at least every 5 seconds

        if (!autoCheckRunning || !isRunning()) break;

        // Check if it's time for an auto-check
        if (shouldPerformAutoCheck()) {
          System.out.println("🔄 Performing auto-check for " + getDataSourceTypeName());
          doTestConnection();
        }

      } catch (InterruptedException e) {
        break; // Thread was interrupted, exit loop
      } catch (Exception e) {
        System.err.println("❌ Error in auto-check loop for " + getDataSourceTypeName() + ": " + e.getMessage());
        // Continue the loop despite errors
      }
    }

    System.out.println("🛑 Auto-checking stopped for " + getDataSourceTypeName());
  }

  /**
   * Determine if an auto-check should be performed now.
   * Based on the configured interval and last check time.
   */
  private boolean shouldPerformAutoCheck() {
    BAutoCheckConfig config = getAutoCheckConfig();
    if (!config.getEnabled()) return false;

    BAbsTime lastCheck = getLastConnectionTest();
    if (lastCheck.isNull()) return true; // Never checked

    long intervalMs = config.getCheckInterval().getMillis();
    long timeSinceLastCheck = System.currentTimeMillis() - lastCheck.getMillis();

    return timeSinceLastCheck >= intervalMs;
  }

////////////////////////////////////////////////////////////////
// Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Handle the result of a connection test.
   * Updates all health-related properties based on the test result.
   */
  private void handleConnectionTestResult(ConnectionTestResult result) {
    if (result.isSuccess()) {
      setConnectionStatus(STATUS_CONNECTED);
      setLastSuccessfulConnection(BAbsTime.now());
      setLastConnectionError("");
      setConsecutiveFailures(0);

      System.out.println("✅ Connection test successful for " + getDataSourceTypeName());
    } else {
      setConnectionStatus(STATUS_FAILED);
      setLastConnectionError(result.getErrorMessage());
      setConsecutiveFailures(getConsecutiveFailures() + 1);

      System.out.println("❌ Connection test failed for " + getDataSourceTypeName() +
                        ": " + result.getErrorMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Inner Classes
////////////////////////////////////////////////////////////////

  /**
   * Result of a connection test operation.
   */
  protected static class ConnectionTestResult {
    private final boolean success;
    private final String errorMessage;

    public ConnectionTestResult(boolean success, String errorMessage) {
      this.success = success;
      this.errorMessage = errorMessage != null ? errorMessage : "";
    }

    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
  }
}
