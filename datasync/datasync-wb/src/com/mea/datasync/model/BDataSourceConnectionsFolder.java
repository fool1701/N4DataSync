// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BDataSourceConnectionsFolder provides a removable folder component for
 * organizing data source connections within the DataSync Tool. This component
 * can be added and removed dynamically, allowing users to create custom
 * organizational structures for their data source connections.
 *
 * This folder component:
 * - Can be added/removed dynamically (unlike the main BDataSourceFolder)
 * - Accepts the same child types as BDataSourceFolder
 * - Provides navigation tree integration
 * - Supports nested folder structures
 * - Has a configurable display name and description
 */
@NiagaraType
@NiagaraProperty(
  name = "displayName",
  type = "baja:String",
  defaultValue = "BString.make(\"Data Source Folder\")"
)
@NiagaraProperty(
  name = "description",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
public class BDataSourceConnectionsFolder extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataSourceConnectionsFolder(2047217648)1.0$ @*/
/* Generated Wed Jul 05 11:45:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "displayName"

  /**
   * Slot for the {@code displayName} property.
   * @see #getDisplayName
   * @see #setDisplayName
   */
  public static final Property displayName = newProperty(0, BString.make("Data Source Folder"), null);

  /**
   * Get the {@code displayName} property.
   * @see #displayName
   */
  public String getDisplayName() { return getString(displayName); }

  /**
   * Set the {@code displayName} property.
   * @see #displayName
   */
  public void setDisplayName(String v) { setString(displayName, v, null); }

  //endregion Property "displayName"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(1, BString.DEFAULT, null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceConnectionsFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDataSourceConnectionsFolder() {
    super();
    System.out.println("📁 Data Source Connections Folder created");
  }

////////////////////////////////////////////////////////////////
// Component Relationship Management
////////////////////////////////////////////////////////////////

  /**
   * Control which child components are allowed.
   * Same rules as BDataSourceConnections - only data source connections and folders.
   */
  @Override
  public boolean isChildLegal(BComponent child) {
    // Allow any data source type
    if (child instanceof BAbstractDataSource) {
      return true;
    }

    // Allow nested folders for organization
    if (child instanceof BDataSourceConnectionsFolder) {
      return true;
    }

    // Reject all other component types
    return false;
  }

  /**
   * Handle when data source connections are added to this folder.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BAbstractDataSource) {
      BAbstractDataSource connection = (BAbstractDataSource) newChild;
      System.out.println("🔌 Data source added to folder '" + getDisplayName() + "': " +
                        connection.getDataSourceTypeName() + " (" + property.getName() + ")");
    } else if (newChild instanceof BDataSourceConnectionsFolder) {
      System.out.println("📁 Subfolder added to folder '" + getDisplayName() + "': " + property.getName());
    }
  }

  /**
   * Handle when data source connections are removed from this folder.
   */
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);

    if (oldChild instanceof BAbstractDataSource) {
      BAbstractDataSource connection = (BAbstractDataSource) oldChild;
      System.out.println("🔌 Data source removed from folder '" + getDisplayName() + "': " +
                        connection.getDataSourceTypeName() + " (" + property.getName() + ")");
    } else if (oldChild instanceof BDataSourceConnectionsFolder) {
      System.out.println("📁 Subfolder removed from folder '" + getDisplayName() + "': " + property.getName());
    }
  }

////////////////////////////////////////////////////////////////
// Navigation Tree Integration (BINavNode)
////////////////////////////////////////////////////////////////

  @Override
  public String getNavDisplayName(Context cx) {
    String name = getDisplayName();
    return (name != null && !name.trim().isEmpty()) ? name : "Data Source Folder";
  }

  @Override
  public BIcon getNavIcon() {
    // Use different icon based on whether folder has contents
    if (hasNavChildren()) {
      return BIcon.std("folderOpen.png");
    } else {
      return BIcon.std("folder.png");
    }
  }

  @Override
  public String getNavDescription(Context cx) {
    StringBuilder desc = new StringBuilder();

    // Start with custom description if provided
    String customDesc = getDescription();
    if (customDesc != null && !customDesc.trim().isEmpty()) {
      desc.append(customDesc);
    } else {
      desc.append("Data Source Connections Folder");
    }

    // Add content summary
    int connectionCount = getDataSourceConnectionCount();
    int folderCount = getFolderCount();

    if (connectionCount > 0 || folderCount > 0) {
      desc.append(" (");
      if (connectionCount > 0) {
        desc.append(connectionCount).append(" connection");
        if (connectionCount != 1) desc.append("s");
      }
      if (folderCount > 0) {
        if (connectionCount > 0) desc.append(", ");
        desc.append(folderCount).append(" folder");
        if (folderCount != 1) desc.append("s");
      }
      desc.append(")");
    }

    return desc.toString();
  }

  @Override
  public BComponent[] getNavChildren() {
    // Return all child components (BComponent already implements BINavNode)
    return getChildComponents();
  }

  @Override
  public boolean hasNavChildren() {
    // Return true if we have any child components (all BComponents implement BINavNode)
    return getChildComponents().length > 0;
  }

////////////////////////////////////////////////////////////////
// Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the count of data source connections in this folder (not including subfolders).
   *
   * @return number of direct data source connections
   */
  public int getDataSourceConnectionCount() {
    int count = 0;
    BComponent[] children = getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BAbstractDataSource) {
        count++;
      }
    }

    return count;
  }

  /**
   * Get the count of subfolders in this folder.
   *
   * @return number of subfolders
   */
  public int getFolderCount() {
    int count = 0;
    BComponent[] children = getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BDataSourceConnectionsFolder) {
        count++;
      }
    }

    return count;
  }

  /**
   * Get all data source connections in this folder (recursively including subfolders).
   *
   * @return array of all data source connections
   */
  public BAbstractDataSource[] getAllDataSourceConnections() {
    java.util.List<BAbstractDataSource> connections = new java.util.ArrayList<>();
    collectDataSourceConnections(this, connections);
    return connections.toArray(new BAbstractDataSource[0]);
  }

  /**
   * Recursively collect data source connections from this folder and any subfolders.
   */
  private void collectDataSourceConnections(BComponent container, java.util.List<BAbstractDataSource> connections) {
    BComponent[] children = container.getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BAbstractDataSource) {
        connections.add((BAbstractDataSource) child);
      } else if (child instanceof BDataSourceConnectionsFolder) {
        // Recursively collect from subfolders
        collectDataSourceConnections(child, connections);
      }
    }
  }

  /**
   * Check if this folder or any subfolder contains healthy connections.
   *
   * @return true if at least one connection is healthy
   */
  public boolean hasHealthyConnections() {
    BAbstractDataSource[] connections = getAllDataSourceConnections();

    for (BAbstractDataSource connection : connections) {
      if (connection.isConnectionHealthy()) {
        return true;
      }
    }

    return false;
  }

  /**
   * Get a summary of the folder's health status.
   *
   * @return health summary string
   */
  public String getHealthSummary() {
    BAbstractDataSource[] connections = getAllDataSourceConnections();
    if (connections.length == 0) {
      return "No connections";
    }

    int healthy = 0;
    int failed = 0;
    int untested = 0;

    for (BAbstractDataSource connection : connections) {
      String status = connection.getConnectionStatus();
      if (BAbstractDataSource.STATUS_CONNECTED.equals(status)) {
        healthy++;
      } else if (BAbstractDataSource.STATUS_FAILED.equals(status)) {
        failed++;
      } else {
        untested++;
      }
    }

    StringBuilder summary = new StringBuilder();
    summary.append(connections.length).append(" connection");
    if (connections.length != 1) summary.append("s");

    if (healthy > 0) {
      summary.append(", ").append(healthy).append(" healthy");
    }
    if (failed > 0) {
      summary.append(", ").append(failed).append(" failed");
    }
    if (untested > 0) {
      summary.append(", ").append(untested).append(" untested");
    }

    return summary.toString();
  }
}
