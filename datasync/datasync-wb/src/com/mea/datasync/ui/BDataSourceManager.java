// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.workbench.mgr.*;

import com.mea.datasync.model.BDataSource;
import com.mea.datasync.model.BDataSourceFolder;

/**
 * BDataSourceManager provides a standard Niagara manager view
 * for data sources. This follows the same pattern as BDeviceManager,
 * BDriverManager, and BPointManager.
 *
 * Features automatically provided by BAbstractManager:
 * - Table view of all data sources
 * - Add button for creating new data sources (via getNewTypes())
 * - Context menu support with "New" option
 * - Delete, copy, paste operations
 * - Drag and drop support
 * - Export functionality
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "datasync:DataSyncTool", "datasync:DataSourceFolder" }
  )
)
public class BDataSourceManager extends BAbstractManager {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.ui.BDataSourceManager(249813557)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSourceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BAbstractManager Implementation
////////////////////////////////////////////////////////////////

  @Override
  protected MgrModel makeModel() {
    return new DataSourceModel(this);
  }

  @Override
  protected MgrController makeController() {
    return new MgrController(this);
  }

////////////////////////////////////////////////////////////////
// Manager Model - Defines table structure and data
////////////////////////////////////////////////////////////////

  /**
   * DataSourceModel manages the logical model of data sources.
   * It defines the table columns, data display, and what types can be created.
   */
  class DataSourceModel extends MgrModel {

    DataSourceModel(BDataSourceManager manager) {
      super(manager);
    }

    @Override
    protected String makeTableTitle() {
      return "Data Sources";
    }

    @Override
    protected MgrColumn[] makeColumns() {
      return new MgrColumn[] {
        new MgrColumn.Name(),
        new MgrColumn.PropString("Type", "dataSourceTypeName", 0),
        new MgrColumn.PropString("Status", "connectionStatus", 0),
        new MgrColumn.PropString("Last Test", "lastConnectionTest", 0),
        new MgrColumn.PropString("Last Success", "lastSuccessfulConnection", 0),
        new MgrColumn.PropString("Failures", "consecutiveFailures", 0),
        new MgrColumn.PropString("Summary", "connectionSummary", 0)
      };
    }

    /**
     * Define what types can be created via "New" button and context menu.
     * This is the key method that enables automatic "New" functionality.
     * Using only BDataSource to avoid abstract class issues.
     */
    @Override
    public MgrTypeInfo[] getNewTypes() {
      return new MgrTypeInfo[] {
        MgrTypeInfo.make(BDataSource.TYPE),
        MgrTypeInfo.make(BDataSourceFolder.TYPE)
      };
    }

    /**
     * Define what types are included in this manager view.
     * Using only BDataSource and folder types to avoid any
     * abstract class initialization issues.
     */
    @Override
    public Type[] getIncludeTypes() {
      return new Type[] {
        BDataSource.TYPE,
        BDataSourceFolder.TYPE
      };
    }

    /**
     * Additional filtering for components displayed by this manager.
     * Accept only BDataSource and folders to avoid abstract class issues.
     */
    @Override
    public boolean accept(BComponent component) {
      return component instanceof BDataSource ||
             component instanceof BDataSourceFolder;
    }

    /**
     * Set subscription depth to monitor child components.
     */
    @Override
    public int getSubscribeDepth() {
      return 2; // Monitor connections and their details
    }

    /**
     * Override load to handle both DataSyncTool and DataSourceFolder as target.
     */
    @Override
    public void load(BComponent target) {
      // If target is DataSyncTool, load its DataSources instead
      if (target instanceof com.mea.datasync.ui.BDataSyncTool) {
        com.mea.datasync.ui.BDataSyncTool tool = (com.mea.datasync.ui.BDataSyncTool) target;
        super.load(tool.getDataSources());
      } else {
        // If target is already DataSourceFolder, use it directly
        super.load(target);
      }
    }
  }
}
