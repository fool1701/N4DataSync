// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nav.BINavNode;
import javax.baja.sys.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BEnhancedConnectionProfile demonstrates the Component Relationship Pattern
 * for managing complex component hierarchies in Niagara. This profile uses
 * composition to separate concerns:
 *
 * - BDataSourceConnection: Connection information (unremovable child)
 * - BTargetNiagaraStation[]: Target stations (removable children, multiple allowed)
 *
 * This follows Niagara best practices for nav tree integration and component
 * lifecycle management, providing a clean separation of concerns while
 * maintaining proper parent-child relationships.
 */
@NiagaraType
@NiagaraProperty(
  name = "dataSourceConnection",
  type = "datasync:DataSourceConnection",
  defaultValue = "new BDataSourceConnection()",
  flags = Flags.READONLY | Flags.SUMMARY
)

@NiagaraProperty(
  name = "status",
  type = "baja:String",
  defaultValue = "BString.make(\"Never Synced\")"
)
@NiagaraProperty(
  name = "lastSync",
  type = "baja:AbsTime",
  defaultValue = "BAbsTime.NULL"
)
@NiagaraProperty(
  name = "componentsCreated",
  type = "baja:Integer",
  defaultValue = "BInteger.DEFAULT"
)
@NiagaraProperty(
  name = "lastError",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
public class BEnhancedConnectionProfile extends BComponent implements BINavNode {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BEnhancedConnectionProfile(2047217648)1.0$ @*/
/* Generated Wed Jul 03 10:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "dataSourceConnection"

  /**
   * Slot for the {@code dataSourceConnection} property.
   * @see #getDataSourceConnection
   * @see #setDataSourceConnection
   */
  public static final Property dataSourceConnection = newProperty(0, new BDataSourceConnection(), null);

  /**
   * Get the {@code dataSourceConnection} property.
   * @see #dataSourceConnection
   */
  public BDataSourceConnection getDataSourceConnection() { return (BDataSourceConnection)get(dataSourceConnection); }

  /**
   * Set the {@code dataSourceConnection} property.
   * @see #dataSourceConnection
   */
  public void setDataSourceConnection(BDataSourceConnection v) { set(dataSourceConnection, v, null); }

  //endregion Property "dataSourceConnection"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(0, BString.make("Never Synced"), null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public String getStatus() { return getString(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(String v) { setString(status, v, null); }

  //endregion Property "status"

  //region Property "lastSync"

  /**
   * Slot for the {@code lastSync} property.
   * @see #getLastSync
   * @see #setLastSync
   */
  public static final Property lastSync = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastSync} property.
   * @see #lastSync
   */
  public BAbsTime getLastSync() { return (BAbsTime)get(lastSync); }

  /**
   * Set the {@code lastSync} property.
   * @see #lastSync
   */
  public void setLastSync(BAbsTime v) { set(lastSync, v, null); }

  //endregion Property "lastSync"

  //region Property "componentsCreated"

  /**
   * Slot for the {@code componentsCreated} property.
   * @see #getComponentsCreated
   * @see #setComponentsCreated
   */
  public static final Property componentsCreated = newProperty(0, BInteger.DEFAULT, null);

  /**
   * Get the {@code componentsCreated} property.
   * @see #componentsCreated
   */
  public int getComponentsCreated() { return getInt(componentsCreated); }

  /**
   * Set the {@code componentsCreated} property.
   * @see #componentsCreated
   */
  public void setComponentsCreated(int v) { setInt(componentsCreated, v, null); }

  //endregion Property "componentsCreated"

  //region Property "lastError"

  /**
   * Slot for the {@code lastError} property.
   * @see #getLastError
   * @see #setLastError
   */
  public static final Property lastError = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code lastError} property.
   * @see #lastError
   */
  public String getLastError() { return getString(lastError); }

  /**
   * Set the {@code lastError} property.
   * @see #lastError
   */
  public void setLastError(String v) { setString(lastError, v, null); }

  //endregion Property "lastError"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnhancedConnectionProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Component Relationship Management
////////////////////////////////////////////////////////////////

  /**
   * Control which child components are allowed.
   * Core components (dataSourceConnection, extractionSettings) are managed internally.
   * Target stations can be added/removed dynamically.
   */
  @Override
  public boolean isChildLegal(BComponent child) {
    // Allow target stations to be added dynamically
    if (child instanceof BTargetNiagaraStation) {
      return true;
    }
    
    // Core components are managed through properties, not dynamic children
    if (child instanceof BDataSourceConnection) {
      return false; // These are managed as properties
    }
    
    return super.isChildLegal(child);
  }

  /**
   * Handle when target stations are added to this profile.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BTargetNiagaraStation) {
      System.out.println("Target station added to profile: " + property.getName());

      // Notify parent tool of changes for persistence
      BComplex parent = getParent();
      if (parent instanceof com.mea.datasync.ui.BDataSyncTool) {
        // Trigger profile save - TODO: Need enhanced profile save method
        // ((com.mea.datasync.ui.BDataSyncTool) parent).saveEnhancedProfileToJson(this, getName());
        System.out.println("Enhanced profile persistence not yet implemented");
      }
    }
  }

  /**
   * Handle when target stations are removed from this profile.
   */
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);

    if (oldChild instanceof BTargetNiagaraStation) {
      System.out.println("Target station removed from profile: " + property.getName());

      // Notify parent tool of changes for persistence
      BComplex parent = getParent();
      if (parent instanceof com.mea.datasync.ui.BDataSyncTool) {
        // Trigger profile save - TODO: Need enhanced profile save method
        // ((com.mea.datasync.ui.BDataSyncTool) parent).saveEnhancedProfileToJson(this, getName());
        System.out.println("Enhanced profile persistence not yet implemented");
      }
    }
  }

////////////////////////////////////////////////////////////////
// Target Station Management
////////////////////////////////////////////////////////////////

  /**
   * Add a target station to this profile.
   * @param name Unique name for the station
   * @param station The target station configuration
   * @return The added property
   */
  public Property addTargetStation(String name, BTargetNiagaraStation station) {
    // Validate name uniqueness
    if (get(name) != null) {
      throw new IllegalArgumentException("Station name already exists: " + name);
    }
    
    // Add as dynamic property (visible, editable, removable)
    return add(name, station, Flags.SUMMARY);
  }

  /**
   * Remove a target station from this profile.
   * @param name Name of the station to remove
   * @return true if removed successfully
   */
  public boolean removeTargetStation(String name) {
    BValue station = get(name);
    if (station instanceof BTargetNiagaraStation) {
      remove(name);
      return true;
    }
    return false;
  }

  /**
   * Get all target stations for this profile.
   * @return Array of target stations
   */
  public BTargetNiagaraStation[] getTargetStations() {
    return getChildren(BTargetNiagaraStation.class);
  }

  /**
   * Get the primary (first) target station.
   * @return Primary target station or null
   */
  public BTargetNiagaraStation getPrimaryTargetStation() {
    BTargetNiagaraStation[] stations = getTargetStations();
    return stations.length > 0 ? stations[0] : null;
  }

////////////////////////////////////////////////////////////////
// BINavNode Implementation (Nav Tree Integration)
////////////////////////////////////////////////////////////////

  @Override
  public String getNavDisplayName(Context cx) {
    String name = getName();
    if (name != null && !name.isEmpty()) {
      return name;
    }
    
    // Fallback to connection information
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null && connection.getConnectionName() != null) {
      return connection.getConnectionName();
    }
    
    return "Unnamed Profile";
  }

  @Override
  public BINavNode[] getNavChildren() {
    List<BINavNode> children = new ArrayList<>();
    
    // Always show core components
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      children.add(connection);
    }
    
    // Add target stations
    BTargetNiagaraStation[] stations = getTargetStations();
    for (BTargetNiagaraStation station : stations) {
      children.add(station);
    }
    
    return children.toArray(new BINavNode[0]);
  }

  @Override
  public boolean hasNavChildren() {
    return true; // Always has dataSourceConnection and extractionSettings
  }

  @Override
  public BIcon getNavIcon() {
    // Icon based on connection status
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      String validationStatus = connection.getValidationStatus();
      if ("Valid".equals(validationStatus)) {
        return BIcon.make("module://icons/x16/profile-valid.png");
      } else if ("Invalid".equals(validationStatus)) {
        return BIcon.make("module://icons/x16/profile-invalid.png");
      }
    }
    return BIcon.make("module://icons/x16/profile.png");
  }

  @Override
  public String getNavDescription(Context cx) {
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      return connection.getConnectionSummary();
    }
    return "DataSync Connection Profile";
  }

////////////////////////////////////////////////////////////////
// Convenience Methods
////////////////////////////////////////////////////////////////

  /**
   * Get a summary of this profile's configuration.
   * @return Human-readable profile summary
   */
  public String getProfileSummary() {
    StringBuilder summary = new StringBuilder();
    
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection != null) {
      summary.append("Source: ").append(connection.getConnectionSummary()).append("\n");
    }
    
    BTargetNiagaraStation[] stations = getTargetStations();
    summary.append("Targets: ").append(stations.length).append(" station(s)\n");
    
    summary.append("Status: ").append(getStatus());
    
    return summary.toString();
  }

  /**
   * Validate the complete profile configuration.
   * @return true if all components are properly configured
   */
  public boolean validateConfiguration() {
    BDataSourceConnection connection = getDataSourceConnection();
    if (connection == null || !connection.testConnection()) {
      return false;
    }
    
    BTargetNiagaraStation[] stations = getTargetStations();
    if (stations.length == 0) {
      return false; // Need at least one target
    }
    
    for (BTargetNiagaraStation station : stations) {
      if (!station.isValidConfiguration()) {
        return false;
      }
    }
    
    return true;
  }
}
