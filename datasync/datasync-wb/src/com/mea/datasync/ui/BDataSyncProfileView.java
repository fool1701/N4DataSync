// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
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
// import com.mea.datasync.persistence.ProfileManager; // No longer needed

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

  static {
    System.out.println("BDataSyncProfileView class loaded!");
  }

  // Note: ProfileManager removed - now delegating to BDataSyncTool
  // private ProfileManager profileManager;

////////////////////////////////////////////////////////////////
// Component Change Listeners
////////////////////////////////////////////////////////////////

  /**
   * Called when a child component is parented to the target.
   * Note: Profile management now handled by BDataSyncTool via ProfileService.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BConnectionProfile) {
      System.out.println("DataSyncProfileView: Profile added (managed by tool): " + property.getName());
      // No direct JSON saving - tool handles this via ProfileService
    }
  }

  /**
   * Called when a child component is unparented from the target.
   * Delete profile from JSON storage.
   */
  @Override
  public void childUnparented(Property property, BValue oldChild, Context context) {
    super.childUnparented(property, oldChild, context);

    if (oldChild instanceof BConnectionProfile) {
      System.out.println("DataSyncProfileView: Profile removed (managed by tool): " + property.getName());
      // Profile deletion now handled by BDataSyncTool via ProfileService
    }
  }

  /**
   * Called when a property changes on this view or its children.
   * Note: Profile persistence now handled by BDataSyncTool via ProfileService.
   */
  @Override
  public void changed(Property property, Context context) {
    super.changed(property, context);

    // Check if the change is on a profile component
    BValue value = get(property);
    if (value instanceof BConnectionProfile) {
      System.out.println("DataSyncProfileView: Profile property changed (managed by tool): " + property.getName());
      // Tool's ProfileService handles persistence automatically
    }
  }

  // Note: Profile persistence methods removed - now handled by BDataSyncTool via ProfileService



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
   * Export all profiles to a portable JSON file.
   * This allows users to backup and transfer profiles between installations.
   */
  public void exportProfiles() {
    try {
      BComponent target = getTarget();
      if (target == null) return;

      BComponent[] profiles = target.getChildren(BConnectionProfile.class);
      if (profiles.length == 0) {
        System.out.println("No profiles to export");
        return;
      }

      // Create export directory in shared user home (portable location)
      java.io.File sharedHome = Sys.getNiagaraSharedUserHome();
      java.io.File exportDir = new java.io.File(sharedHome, "N4DataSync/export");
      exportDir.mkdirs();

      // Create export file with timestamp
      String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
      java.io.File exportFile = new java.io.File(exportDir, "datasync_profiles_" + timestamp + ".json");

      // Export profiles using Gson
      com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
      java.util.List<java.util.Map<String, Object>> exportData = new java.util.ArrayList<>();

      for (BComponent profile : profiles) {
        if (profile instanceof BConnectionProfile) {
          BConnectionProfile cp = (BConnectionProfile) profile;
          java.util.Map<String, Object> profileData = new java.util.HashMap<>();
          profileData.put("name", profile.getSlotPath().toString());
          profileData.put("sourceType", cp.getSourceType());
          profileData.put("sourcePath", cp.getSourcePath());
          profileData.put("sheetName", cp.getSheetName());
          profileData.put("targetHost", cp.getTargetHost());
          profileData.put("targetUsername", cp.getTargetUsername());
          profileData.put("targetPath", cp.getTargetPath());
          profileData.put("status", cp.getStatus());
          profileData.put("componentsCreated", cp.getComponentsCreated());
          profileData.put("lastError", cp.getLastError());
          exportData.add(profileData);
        }
      }

      try (java.io.FileWriter writer = new java.io.FileWriter(exportFile)) {
        gson.toJson(exportData, writer);
      }

      System.out.println("Exported " + profiles.length + " profiles to: " + exportFile.getAbsolutePath());

    } catch (Exception e) {
      System.err.println("Error exporting profiles: " + e.getMessage());
      e.printStackTrace();
    }
  }



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
     * Override to load connection profiles from persistent storage.
     */
    public void load(BComponent target) {
      super.load(target);

      // Load profiles from target component (standard Niagara pattern)
      loadProfilesFromTarget(target);


    }

    /**
     * Load connection profiles from BDataSyncTool.
     * Profiles are now managed by the tool via ProfileService.
     */
    private void loadProfilesFromTarget(BComponent target) {
      try {
        System.out.println("DataSyncProfileView: Loading profiles from tool (new architecture)");
        System.out.println("Target component: " + target.getSlotPath());

        // Profiles are managed by BDataSyncTool via ProfileService
        // The tool handles all initialization, persistence, and synchronization
        if (target instanceof com.mea.datasync.ui.BDataSyncTool) {
          com.mea.datasync.ui.BDataSyncTool tool = (com.mea.datasync.ui.BDataSyncTool) target;

          // Get profile information from the tool
          int profileCount = tool.getProfileCount();
          System.out.println("DataSyncProfileView: Tool reports " + profileCount + " profiles available");

          // The tool's ProfileService has already loaded profiles into the component tree
          // We just need to display them
          com.mea.datasync.model.BConnectionProfile[] profiles = tool.getAllProfiles();
          System.out.println("DataSyncProfileView: Retrieved " + profiles.length + " profiles from tool");

          for (com.mea.datasync.model.BConnectionProfile profile : profiles) {
            System.out.println("  Profile: " + profile.getName() + " (" + profile.getStatus() + ")");
          }
        } else {
          System.err.println("DataSyncProfileView: Target is not a BDataSyncTool: " + target.getClass().getSimpleName());
        }

      } catch (Exception e) {
        System.err.println("Error loading profiles from tool: " + e.getMessage());
        e.printStackTrace();
      }
    }

    /**
     * Sync profiles from JSON storage to component tree.
     */
    // DEPRECATED: Replaced by ProfileService
    private void syncJsonToComponents_DEPRECATED(BComponent target, Object pm, java.util.List<String> profileNames) {
      System.out.println("Syncing " + profileNames.size() + " profiles from JSON to component tree");

      // Clear existing components
      BComponent[] existing = target.getChildren(BConnectionProfile.class);
      for (BComponent comp : existing) {
        target.remove(comp);
      }

      // DEPRECATED: ProfileService handles this
      System.out.println("  DEPRECATED: syncJsonToComponents - use ProfileService instead");
    }

    /**
     * Sync profiles from component tree to JSON storage.
     */
    // DEPRECATED: Replaced by ProfileService
    private void syncComponentsToJson_DEPRECATED(BComponent target, Object pm) {
      BComponent[] profiles = target.getChildren(BConnectionProfile.class);
      System.out.println("Syncing " + profiles.length + " profiles from component tree to JSON");

      for (BComponent comp : profiles) {
        if (comp instanceof BConnectionProfile) {
          BConnectionProfile profile = (BConnectionProfile) comp;
          String profileName = comp.getSlotPath().toString();
          if (profileName.contains(":")) {
            profileName = profileName.substring(profileName.lastIndexOf(":") + 1);
          }
          // DEPRECATED: ProfileService handles this
          System.out.println("  DEPRECATED: syncComponentsToJson - use ProfileService instead");
        }
      }
    }

    /**
     * Create initial sample profiles for first-time users.
     * Add them directly to the target component (standard Niagara pattern).
     */
    private void createInitialSampleProfiles(BComponent target) {
      try {
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
        System.out.println("Sample profile 1 added to target");

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
        System.out.println("Sample profile 2 added to target");

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
        System.out.println("Sample profile 3 added to target");

        System.out.println("Created initial sample profiles in target component");

      } catch (Exception e) {
        System.err.println("Error creating initial sample profiles: " + e.getMessage());
        e.printStackTrace();
      }
    }

    /**
     * Sanitize a profile name for use as a component name.
     */
    private String sanitizeComponentName(String name) {
      if (name == null) return "unnamed";
      return name.replaceAll("[^a-zA-Z0-9_]", "_");
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

      // Profile persistence is now handled automatically by BDataSyncTool via ProfileService
      // No manual syncing needed
    }

    /**
     * Sync all profiles from the target component to JSON storage.
     */
    private void syncAllProfilesToJson(BComponent target) {
      try {
        // DEPRECATED: ProfileService now handles all persistence
        System.out.println("DataSyncProfileView: Sync handled by ProfileService - no action needed");
      } catch (Exception e) {
        System.err.println("Error syncing profiles to JSON: " + e.getMessage());
        e.printStackTrace();
      }
    }

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
