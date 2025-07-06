// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.nav.BINavNode;

/**
 * BDataSourceConnections serves as a container/folder component for organizing
 * data source connections within the DataSync Tool. This component acts as a
 * frozen property slot that accepts both BAbstractDataSourceConnection
 * subclasses (new architecture) and BDataSourceConnection (legacy) components,
 * as well as BDataSourceConnectionsFolder components for organization.
 *
 * This follows Niagara's folder organization pattern and provides:
 * - Navigation tree integration
 * - Type-safe child component validation with backward compatibility
 * - Organized display of data source connections
 * - Support for nested folder structures
 *
 * The component is designed to be used as a frozen property in BDataSyncTool,
 * providing a dedicated space for managing all data source connections.
 */
@NiagaraType
public class BDataSourceConnections extends BComponent implements BINavNode {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BDataSourceConnections(2047217648)1.0$ @*/
/* Generated Wed Jul 05 11:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceConnections.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDataSourceConnections() {
    super();
    System.out.println("🗂️ Data Source Connections container created");
  }

////////////////////////////////////////////////////////////////
// Component Relationship Management
////////////////////////////////////////////////////////////////

  /**
   * Control which child components are allowed.
   * Accept BAbstractDataSourceConnection subclasses, BDataSourceConnection (legacy), and BDataSourceConnectionsFolder.
   */
  @Override
  public boolean isChildLegal(BComponent child) {
    // Allow any data source connection type (new architecture)
    if (child instanceof BAbstractDataSourceConnection) {
      return true;
    }

    // Allow legacy data source connection type for backward compatibility
    if (child instanceof BDataSourceConnection) {
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
   * Handle when data source connections are added.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BAbstractDataSourceConnection) {
      BAbstractDataSourceConnection connection = (BAbstractDataSourceConnection) newChild;
      System.out.println("🔌 Data source connection added: " + connection.getDataSourceTypeName() +
                        " (" + property.getName() + ")");

      // Notify parent tool of changes for persistence
      notifyParentOfChanges();
    } else if (newChild instanceof BDataSourceConnection) {
      BDataSourceConnection connection = (BDataSourceConnection) newChild;
      System.out.println("🔌 Legacy data source connection added: " + connection.getSourceType() +
                        " (" + property.getName() + ")");

      // Notify parent tool of changes for persistence
      notifyParentOfChanges();
    } else if (newChild instanceof BDataSourceConnectionsFolder) {
      System.out.println("📁 Data source connections folder added: " + property.getName());
      notifyParentOfChanges();
    }
  }

  /**
   * Handle when data source connections are removed.
   */
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);

    if (oldChild instanceof BAbstractDataSourceConnection) {
      BAbstractDataSourceConnection connection = (BAbstractDataSourceConnection) oldChild;
      System.out.println("🔌 Data source connection removed: " + connection.getDataSourceTypeName() + 
                        " (" + property.getName() + ")");
      notifyParentOfChanges();
    } else if (oldChild instanceof BDataSourceConnectionsFolder) {
      System.out.println("📁 Data source connections folder removed: " + property.getName());
      notifyParentOfChanges();
    }
  }

////////////////////////////////////////////////////////////////
// Navigation Tree Integration (BINavNode)
////////////////////////////////////////////////////////////////

  @Override
  public String getNavDisplayName(Context cx) {
    return "Data Source Connections";
  }

  @Override
  public BIcon getNavIcon() {
    return BIcon.std("folder.png");
  }

  @Override
  public String getNavDescription(Context cx) {
    int connectionCount = getDataSourceConnectionCount();
    int folderCount = getFolderCount();
    
    StringBuilder desc = new StringBuilder();
    desc.append("Data Source Connections");
    
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
  public BINavNode[] getNavChildren() {
    // Return all child components that implement BINavNode
    BComponent[] children = getChildComponents();
    java.util.List<BINavNode> navChildren = new java.util.ArrayList<>();

    for (BComponent child : children) {
      if (child instanceof BINavNode) {
        navChildren.add((BINavNode) child);
      }
    }

    return navChildren.toArray(new BINavNode[0]);
  }

  @Override
  public boolean hasNavChildren() {
    BComponent[] children = getChildComponents();
    for (BComponent child : children) {
      if (child instanceof BINavNode) {
        return true;
      }
    }
    return false;
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
      if (child instanceof BAbstractDataSourceConnection) {
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
      if (child instanceof BDataSourceConnectionsFolder) {
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
  public BAbstractDataSourceConnection[] getAllDataSourceConnections() {
    java.util.List<BAbstractDataSourceConnection> connections = new java.util.ArrayList<>();
    collectDataSourceConnections(this, connections);
    return connections.toArray(new BAbstractDataSourceConnection[0]);
  }

  /**
   * Recursively collect data source connections from this container and any subfolders.
   */
  private void collectDataSourceConnections(BComponent container, java.util.List<BAbstractDataSourceConnection> connections) {
    BComponent[] children = container.getChildComponents();

    for (BComponent child : children) {
      if (child instanceof BAbstractDataSourceConnection) {
        connections.add((BAbstractDataSourceConnection) child);
      } else if (child instanceof BDataSourceConnectionsFolder) {
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
  public <T extends BAbstractDataSourceConnection> T[] getDataSourceConnectionsByType(Class<T> connectionType) {
    java.util.List<T> connections = new java.util.ArrayList<>();
    BAbstractDataSourceConnection[] allConnections = getAllDataSourceConnections();
    
    for (BAbstractDataSourceConnection connection : allConnections) {
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
    BAbstractDataSourceConnection[] connections = getAllDataSourceConnections();
    
    for (BAbstractDataSourceConnection connection : connections) {
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
