// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BAutoCheckConfig configures the automatic connection health checking
 * mechanism for data source connections. This component encapsulates
 * all settings related to periodic connection testing.
 * 
 * Based on patterns from BPingMonitor, this provides:
 * - Enable/disable auto-checking
 * - Configurable check interval
 * - Retry configuration
 * - Failure threshold settings
 * 
 * Default settings provide reasonable behavior for most data sources
 * while allowing customization for specific requirements.
 */
@NiagaraType
@NiagaraProperty(
  name = "enabled",
  type = "baja:Boolean",
  defaultValue = "BBoolean.TRUE"
)
@NiagaraProperty(
  name = "checkInterval",
  type = "baja:RelTime",
  defaultValue = "BRelTime.make(5L*60L*1000L)" // 5 minutes default
)
@NiagaraProperty(
  name = "retryCount",
  type = "baja:Integer",
  defaultValue = "BInteger.make(3)"
)
@NiagaraProperty(
  name = "retryDelay",
  type = "baja:RelTime",
  defaultValue = "BRelTime.make(30L*1000L)" // 30 seconds default
)
@NiagaraProperty(
  name = "failureThreshold",
  type = "baja:Integer",
  defaultValue = "BInteger.make(3)"
)
@NiagaraProperty(
  name = "startupDelay",
  type = "baja:RelTime",
  defaultValue = "BRelTime.make(60L*1000L)" // 1 minute default
)
public class BAutoCheckConfig extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BAutoCheckConfig(2362368597)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, BBoolean.TRUE.as(BBoolean.class).getBoolean(), null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "checkInterval"

  /**
   * Slot for the {@code checkInterval} property.
   * @see #getCheckInterval
   * @see #setCheckInterval
   */
  public static final Property checkInterval = newProperty(0, BRelTime.make(5L*60L*1000L), null);

  /**
   * Get the {@code checkInterval} property.
   * @see #checkInterval
   */
  public BRelTime getCheckInterval() { return (BRelTime)get(checkInterval); }

  /**
   * Set the {@code checkInterval} property.
   * @see #checkInterval
   */
  public void setCheckInterval(BRelTime v) { set(checkInterval, v, null); }

  //endregion Property "checkInterval"

  //region Property "retryCount"

  /**
   * Slot for the {@code retryCount} property.
   * @see #getRetryCount
   * @see #setRetryCount
   */
  public static final Property retryCount = newProperty(0, BInteger.make(3).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code retryCount} property.
   * @see #retryCount
   */
  public int getRetryCount() { return getInt(retryCount); }

  /**
   * Set the {@code retryCount} property.
   * @see #retryCount
   */
  public void setRetryCount(int v) { setInt(retryCount, v, null); }

  //endregion Property "retryCount"

  //region Property "retryDelay"

  /**
   * Slot for the {@code retryDelay} property.
   * @see #getRetryDelay
   * @see #setRetryDelay
   */
  public static final Property retryDelay = newProperty(0, BRelTime.make(30L*1000L), null);

  /**
   * Get the {@code retryDelay} property.
   * @see #retryDelay
   */
  public BRelTime getRetryDelay() { return (BRelTime)get(retryDelay); }

  /**
   * Set the {@code retryDelay} property.
   * @see #retryDelay
   */
  public void setRetryDelay(BRelTime v) { set(retryDelay, v, null); }

  //endregion Property "retryDelay"

  //region Property "failureThreshold"

  /**
   * Slot for the {@code failureThreshold} property.
   * @see #getFailureThreshold
   * @see #setFailureThreshold
   */
  public static final Property failureThreshold = newProperty(0, BInteger.make(3).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code failureThreshold} property.
   * @see #failureThreshold
   */
  public int getFailureThreshold() { return getInt(failureThreshold); }

  /**
   * Set the {@code failureThreshold} property.
   * @see #failureThreshold
   */
  public void setFailureThreshold(int v) { setInt(failureThreshold, v, null); }

  //endregion Property "failureThreshold"

  //region Property "startupDelay"

  /**
   * Slot for the {@code startupDelay} property.
   * @see #getStartupDelay
   * @see #setStartupDelay
   */
  public static final Property startupDelay = newProperty(0, BRelTime.make(60L*1000L), null);

  /**
   * Get the {@code startupDelay} property.
   * @see #startupDelay
   */
  public BRelTime getStartupDelay() { return (BRelTime)get(startupDelay); }

  /**
   * Set the {@code startupDelay} property.
   * @see #startupDelay
   */
  public void setStartupDelay(BRelTime v) { set(startupDelay, v, null); }

  //endregion Property "startupDelay"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAutoCheckConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BAutoCheckConfig() {
    // Default constructor with reasonable defaults
  }

////////////////////////////////////////////////////////////////
// Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * Get a human-readable summary of the auto-check configuration.
   * 
   * @return configuration summary string
   */
  public String getConfigurationSummary() {
    if (!getEnabled()) {
      return "Auto-check disabled";
    }
    
    return String.format("Auto-check enabled: %s interval, %d retries, %d failure threshold",
      getCheckInterval().toString(),
      getRetryCount(),
      getFailureThreshold());
  }

  /**
   * Check if the configuration is valid.
   * 
   * @return true if configuration is valid
   */
  public boolean isConfigurationValid() {
    if (getCheckInterval().getMillis() < 1000) { // Less than 1 second
      return false;
    }
    if (getRetryCount() < 0 || getRetryCount() > 10) {
      return false;
    }
    if (getFailureThreshold() < 1 || getFailureThreshold() > 100) {
      return false;
    }
    return true;
  }

  /**
   * Create a default configuration for Excel data sources.
   * 
   * @return BAutoCheckConfig with Excel-optimized settings
   */
  public static BAutoCheckConfig createExcelDefault() {
    BAutoCheckConfig config = new BAutoCheckConfig();
    config.setEnabled(true);
    config.setCheckInterval(BRelTime.make(10L * 60L * 1000L)); // 10 minutes for files
    config.setRetryCount(2);
    config.setFailureThreshold(2);
    return config;
  }

  /**
   * Create a default configuration for database data sources.
   * 
   * @return BAutoCheckConfig with database-optimized settings
   */
  public static BAutoCheckConfig createDatabaseDefault() {
    BAutoCheckConfig config = new BAutoCheckConfig();
    config.setEnabled(true);
    config.setCheckInterval(BRelTime.make(2L * 60L * 1000L)); // 2 minutes for databases
    config.setRetryCount(3);
    config.setFailureThreshold(3);
    return config;
  }

  /**
   * Create a default configuration for web-based data sources.
   * 
   * @return BAutoCheckConfig with web-optimized settings
   */
  public static BAutoCheckConfig createWebDefault() {
    BAutoCheckConfig config = new BAutoCheckConfig();
    config.setEnabled(true);
    config.setCheckInterval(BRelTime.make(5L * 60L * 1000L)); // 5 minutes for web APIs
    config.setRetryCount(3);
    config.setFailureThreshold(5); // Web services can be more flaky
    return config;
  }
}
