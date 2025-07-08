// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.sys.*;
import javax.baja.sys.BIcon;

/**
 * BDataSourceFolder serves as a universal container/folder component for organizing
 * data source connections within the DataSync Tool. This component can be used both
 * as a frozen property slot in BDataSyncTool and as removable folders for organization.
 *
 * This unified folder component:
 * - Can be used as frozen property (root folder) or removable folder (organization)
 * - Accepts BDataSource subclasses and nested BDataSourceFolder components
 * - Has configurable display name (defaults to "Data Sources" for root, customizable for nested)
 * - Supports nested folder structures with full navigation tree integration
 * - Automatically handles parent notification for persistence when used as root folder
 *
 * This follows Niagara's folder organization pattern and provides:
 * - Navigation tree integration with dynamic icons and descriptions
 * - Type-safe child component validation with backward compatibility
 * - Organized display of data source connections with health summaries
 * - Support for unlimited nested folder structures
 */
@NiagaraType
@NiagaraProperty(
  name = "displayName",
  type = "baja:String",
  defaultValue = "BString.make(\"Data Sources\")"
)
public class BDataSourceFolder extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataSourceFolder(1760409707)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "displayName"

  /**
   * Slot for the {@code displayName} property.
   * @see #getDisplayName
   * @see #setDisplayName
   */
  public static final Property displayName = newProperty(0, BString.make("Data Sources"), null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDataSourceFolder() {
    super();
    System.out.println("🗂️ Data Source Folder container created");
  }

////////////////////////////////////////////////////////////////
// Component Relationship Management
////////////////////////////////////////////////////////////////

  /**
   * Control which child components are allowed.
   * Accept BDataSource subclasses and nested BDataSourceFolder.
   */
  @Override
  public boolean isChildLegal(BComponent child) {
    // Allow any data source type
    if (child instanceof BDataSource) {
      return true;
    }

    // Allow nested folders for organization (same class, different usage)
    if (child instanceof BDataSourceFolder) {
      return true;
    }

    // Reject all other component types
    return false;
  }

  /**
   * Handle when data source connections are added.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BDataSource) {
      BDataSource connection = (BDataSource) newChild;
      System.out.println("🔌 Data source added: " + connection.getDataSourceTypeName() +
                        " (" + property.getName() + ")");

      // Notify parent tool of changes for persistence
      notifyParentOfChanges();
    } else if (newChild instanceof BDataSourceFolder) {
      BDataSourceFolder folder = (BDataSourceFolder) newChild;
      String displayName = folder.getDisplayName();
      System.out.println("📁 Data source folder added: " + displayName + " (" + property.getName() + ")");
      notifyParentOfChanges();
    }
  }

  /**
   * Handle when data source connections are removed.
   */
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);

    if (oldChild instanceof BDataSource) {
      BDataSource connection = (BDataSource) oldChild;
      System.out.println("🔌 Data source removed: " + connection.getDataSourceTypeName() +
                        " (" + property.getName() + ")");
      notifyParentOfChanges();
    } else if (oldChild instanceof BDataSourceFolder) {
      BDataSourceFolder folder = (BDataSourceFolder) oldChild;
      String displayName = folder.getDisplayName();
      System.out.println("📁 Data source folder removed: " + displayName + " (" + property.getName() + ")");
      notifyParentOfChanges();
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
    int connectionCount = getDataSourceConnectionCount();
    int folderCount = getFolderCount();

    StringBuilder desc = new StringBuilder();
    desc.append(getDisplayName());

    // Add content summary
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
   * Get the count of data source connections (not including folders).
   *
   * @return number of data source connections
   */
  public int getDataSourceConnectionCount() {
    int count = 0;
    BComponent[] children = getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BDataSource) {
        count++;
      }
    }

    return count;
  }

  /**
   * Get the count of folders.
   *
   * @return number of folders
   */
  public int getFolderCount() {
    int count = 0;
    BComponent[] children = getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BDataSourceFolder) {
        count++;
      }
    }

    return count;
  }

  /**
   * Get all data source connections (recursively including those in folders).
   *
   * @return array of all data source connections
   */
  public BDataSource[] getAllDataSourceConnections() {
    java.util.List<BDataSource> connections = new java.util.ArrayList<>();
    collectDataSourceConnections(this, connections);
    return connections.toArray(new BDataSource[0]);
  }

  /**
   * Recursively collect data source connections from this container and any subfolders.
   */
  private void collectDataSourceConnections(BComponent container, java.util.List<BDataSource> connections) {
    BComponent[] children = container.getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BDataSource) {
        connections.add((BDataSource) child);
      } else if (child instanceof BDataSourceFolder) {
        // Recursively collect from subfolders
        collectDataSourceConnections(child, connections);
      }
    }
  }

  /**
   * Get data source connections by type.
   *
   * @param connectionType the class type to filter by
   * @return array of connections of the specified type
   */
  @SuppressWarnings("unchecked")
  public <T extends BDataSource> T[] getDataSourceConnectionsByType(Class<T> connectionType) {
    java.util.List<T> connections = new java.util.ArrayList<>();
    BDataSource[] allConnections = getAllDataSourceConnections();

    for (BDataSource connection : allConnections) {
      if (connectionType.isInstance(connection)) {
        connections.add((T) connection);
      }
    }

    return connections.toArray((T[]) java.lang.reflect.Array.newInstance(connectionType, 0));
  }

  /**
   * Check if there are any healthy (connected) data source connections.
   *
   * @return true if at least one connection is healthy
   */
  public boolean hasHealthyConnections() {
    BDataSource[] connections = getAllDataSourceConnections();

    for (BDataSource connection : connections) {
      if (connection.isConnectionHealthy()) {
        return true;
      }
    }

    return false;
  }

////////////////////////////////////////////////////////////////
// Helper Methods
////////////////////////////////////////////////////////////////

  /**
   * Notify the parent DataSync Tool of changes for persistence.
   */
  private void notifyParentOfChanges() {
    BComplex parent = getParent();
    if (parent instanceof com.mea.datasync.ui.BDataSyncTool) {
      // TODO: Implement enhanced persistence notification
      System.out.println("📝 Notifying DataSync Tool of data source connection changes");
    }
  }
}
