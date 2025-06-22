// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BWidget;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.mea.datasync.model.BConnectionProfile;

/**
 * BDataSyncProfileView provides a manager-style view for the DataSync tool
 * that displays and manages connection profiles in a table format.
 * This follows the same pattern as BDeviceManager, BDriverManager, and BPointManager.
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "datasync:DataSyncTool" }
  )
)
public class BDataSyncProfileView extends BAbstractManager {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.ui.BDataSyncProfileView(184651739)1.0$ @*/
/* Generated Mon Jun 23 01:46:59 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSyncProfileView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BAbstractManager Implementation
////////////////////////////////////////////////////////////////

  @Override
  protected MgrModel makeModel() { return new DataSyncModel(this); }

  @Override
  protected MgrController makeController() { return new DataSyncController(this); }

  @Override
  protected MgrState makeState() { return new DataSyncState(); }

  /**
   * Override to provide a custom title for the manager view.
   */
  public String getDisplayName() {
    return "DataSync Connection Profiles";
  }

  /**
   * Override to add custom UI elements like status panels.
   * Note: BAbstractManager doesn't have doInitialize, so we'll add status in the model load instead.
   */

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  /**
   * DataSyncModel manages the logical model of connection profiles
   * in the DataSync tool. It defines the table columns and data display.
   */
  class DataSyncModel extends MgrModel {

    DataSyncModel(BDataSyncProfileView manager) {
      super(manager);
    }

    protected String makeTableTitle() {
      return "DataSync Connection Profiles";
    }

    protected MgrColumn[] makeColumns() {
      return new MgrColumn[] {
        new MgrColumn.Name(),
        new MgrColumn.Prop(BConnectionProfile.sourceType),
        new MgrColumn.Prop(BConnectionProfile.sourcePath),
        new MgrColumn.Prop(BConnectionProfile.sheetName),
        new MgrColumn.Prop(BConnectionProfile.targetHost),
        new MgrColumn.Prop(BConnectionProfile.targetPath),
        new MgrColumn.Prop(BConnectionProfile.status),
        new MgrColumn.Prop(BConnectionProfile.lastSync),
        new MgrColumn.Prop(BConnectionProfile.componentsCreated),
      };
    }

    public Type[] getIncludeTypes() {
      return new Type[] { BConnectionProfile.TYPE };
    }

    public MgrTypeInfo[] getNewTypes() {
      return MgrTypeInfo.makeArray(BConnectionProfile.TYPE);
    }

    /**
     * Get the base type supported by the new operation.
     */
    public Type getBaseNewType() {
      return BConnectionProfile.TYPE;
    }

    /**
     * Override to add some sample data for demonstration.
     * In a real implementation, this would load from the station database.
     */
    public void load(BComponent target) {
      super.load(target);

      // Add some sample connection profiles for demonstration
      // In a real implementation, these would be loaded from the station
      createSampleProfiles(target);
    }

    private void createSampleProfiles(BComponent target) {
      try {
        // Only create samples if no profiles exist yet
        if (target.getChildren(BConnectionProfile.class).length == 0) {

          // Sample Profile 1 - Building A HVAC
          BConnectionProfile profile1 = new BConnectionProfile();
          profile1.setSourceType("Excel");
          profile1.setSourcePath("C:\\Data\\BuildingA_HVAC.xlsx");
          profile1.setSheetName("Equipment");
          profile1.setTargetHost("192.168.1.100");
          profile1.setTargetPath("station:|slot:/Drivers");
          profile1.setStatus("Success");
          profile1.setComponentsCreated(45);
          target.add("BuildingA_HVAC", profile1);

          // Sample Profile 2 - Building B Lighting
          BConnectionProfile profile2 = new BConnectionProfile();
          profile2.setSourceType("Excel");
          profile2.setSourcePath("C:\\Data\\BuildingB_Lighting.xlsx");
          profile2.setSheetName("Points");
          profile2.setTargetHost("192.168.1.101");
          profile2.setTargetPath("station:|slot:/Drivers/Lighting");
          profile2.setStatus("Error");
          profile2.setComponentsCreated(23);
          target.add("BuildingB_Lighting", profile2);

          // Sample Profile 3 - Chiller Plant
          BConnectionProfile profile3 = new BConnectionProfile();
          profile3.setSourceType("Excel");
          profile3.setSourcePath("C:\\Data\\ChillerPlant.xlsx");
          profile3.setSheetName("Chillers");
          profile3.setTargetHost("192.168.1.102");
          profile3.setTargetPath("station:|slot:/Drivers/HVAC");
          profile3.setStatus("Never Synced");
          profile3.setComponentsCreated(0);
          target.add("ChillerPlant", profile3);
        }
      } catch (Exception e) {
        // Log error but don't fail the view
        System.err.println("Error creating sample profiles: " + e.getMessage());
      }
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  /**
   * DataSyncController handles user interactions with the connection profiles table.
   */
  class DataSyncController extends MgrController {

    DataSyncController(BDataSyncProfileView manager) {
      super(manager);
    }

    public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col) {
      BComponent comp = table.getComponentAt(row);
      BWbShell shell = getWbShell();
      if (comp != null && shell != null)
        shell.hyperlink(new HyperlinkInfo(comp.getNavOrd(), event));
    }

    /**
     * Provide tooltip information for table cells.
     * Note: Removing this method as the base class doesn't have getToolTipText method.
     * Tooltips can be added through other mechanisms if needed.
     */
  }

////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////

  /**
   * DataSyncState manages view state and preferences for the DataSync manager.
   */
  class DataSyncState extends MgrState {

    /**
     * Override to provide custom state management for DataSync profiles.
     * This could include view preferences, column widths, sort orders, etc.
     */
    // Default implementation is sufficient for now
    // State management methods are inherited from MgrState
  }
}
