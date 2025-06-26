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
import com.mea.datasync.persistence.ProfileManager;

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

  // ProfileManager for portable JSON storage
  private ProfileManager profileManager;

////////////////////////////////////////////////////////////////
// Component Change Listeners
////////////////////////////////////////////////////////////////

  /**
   * Called when a child component is parented to the target.
   * Save new profiles to JSON storage.
   */
  @Override
  public void childParented(Property property, BValue newChild, Context context) {
    super.childParented(property, newChild, context);

    if (newChild instanceof BConnectionProfile) {
      System.out.println("DataSyncProfileView: Profile added, saving to JSON: " + property.getName());
      saveProfileToJson((BConnectionProfile) newChild, property.getName());
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
      System.out.println("DataSyncProfileView: Profile removed, deleting from JSON: " + property.getName());
      deleteProfileFromJson(property.getName());
    }
  }

  /**
   * Called when a property changes on this view or its children.
   * Save modified profiles to JSON storage.
   */
  @Override
  public void changed(Property property, Context context) {
    super.changed(property, context);

    // Check if the change is on a profile component
    BValue value = get(property);
    if (value instanceof BConnectionProfile) {
      System.out.println("DataSyncProfileView: Profile property changed, saving to JSON: " + property.getName());
      saveProfileToJson((BConnectionProfile) value, property.getName());
    }
  }

  /**
   * Save a single profile to JSON storage.
   */
  private void saveProfileToJson(BConnectionProfile profile, String profileName) {
    try {
      if (profileManager == null) {
        profileManager = new ProfileManager();
      }
      boolean saved = profileManager.saveProfile(profile, profileName);
      System.out.println("Profile save result for '" + profileName + "': " + (saved ? "SUCCESS" : "FAILED"));
    } catch (Exception e) {
      System.err.println("Error saving profile '" + profileName + "' to JSON: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Delete a profile from JSON storage.
   */
  private void deleteProfileFromJson(String profileName) {
    try {
      if (profileManager == null) {
        profileManager = new ProfileManager();
      }
      boolean deleted = profileManager.deleteProfile(profileName);
      System.out.println("Profile delete result for '" + profileName + "': " + (deleted ? "SUCCESS" : "FAILED"));
    } catch (Exception e) {
      System.err.println("Error deleting profile '" + profileName + "' from JSON: " + e.getMessage());
      e.printStackTrace();
    }
  }



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
     * Load connection profiles using hybrid approach:
     * 1. Load from portable JSON files (if they exist)
     * 2. Sync to Niagara component tree for UI display
     * 3. Fall back to component tree if no JSON files
     */
    private void loadProfilesFromTarget(BComponent target) {
      try {
        System.out.println("DataSyncProfileView: Loading profiles using hybrid approach");
        System.out.println("Target component: " + target.getSlotPath());

        // Initialize ProfileManager for portable storage
        BDataSyncProfileView view = (BDataSyncProfileView) getManager();
        if (view.profileManager == null) {
          view.profileManager = new ProfileManager();
        }

        // Try to load from portable JSON files first
        java.util.List<String> jsonProfiles = view.profileManager.listProfiles();
        System.out.println("Found " + jsonProfiles.size() + " profiles in JSON storage");

        if (!jsonProfiles.isEmpty()) {
          // Load from JSON and sync to component tree
          syncJsonToComponents(target, view.profileManager, jsonProfiles);
        } else {
          // Check if we have profiles in component tree
          BComponent[] existingProfiles = target.getChildren(BConnectionProfile.class);
          System.out.println("Found " + existingProfiles.length + " existing profiles in component tree");

          if (existingProfiles.length == 0) {
            // Create initial samples and save to both locations
            System.out.println("No profiles found, creating initial sample profiles");
            createInitialSampleProfiles(target);
            syncComponentsToJson(target, view.profileManager);
          } else {
            // Sync existing components to JSON for portability
            syncComponentsToJson(target, view.profileManager);
          }
        }

        // List all profiles for debugging
        BComponent[] allProfiles = target.getChildren(BConnectionProfile.class);
        System.out.println("Total profiles after hybrid loading: " + allProfiles.length);
        for (BComponent profile : allProfiles) {
          System.out.println("  Profile: " + profile.getSlotPath().toString());
        }

      } catch (Exception e) {
        System.err.println("Error loading profiles: " + e.getMessage());
        e.printStackTrace();
      }
    }

    /**
     * Sync profiles from JSON storage to component tree.
     */
    private void syncJsonToComponents(BComponent target, ProfileManager pm, java.util.List<String> profileNames) {
      System.out.println("Syncing " + profileNames.size() + " profiles from JSON to component tree");

      // Clear existing components
      BComponent[] existing = target.getChildren(BConnectionProfile.class);
      for (BComponent comp : existing) {
        target.remove(comp);
      }

      // Load from JSON and add to component tree
      for (String profileName : profileNames) {
        BConnectionProfile profile = pm.loadProfile(profileName);
        if (profile != null) {
          String componentName = sanitizeComponentName(profileName);
          target.add(componentName, profile);
          System.out.println("  Synced: " + profileName + " -> " + componentName);
        }
      }
    }

    /**
     * Sync profiles from component tree to JSON storage.
     */
    private void syncComponentsToJson(BComponent target, ProfileManager pm) {
      BComponent[] profiles = target.getChildren(BConnectionProfile.class);
      System.out.println("Syncing " + profiles.length + " profiles from component tree to JSON");

      for (BComponent comp : profiles) {
        if (comp instanceof BConnectionProfile) {
          BConnectionProfile profile = (BConnectionProfile) comp;
          String profileName = comp.getSlotPath().toString();
          if (profileName.contains(":")) {
            profileName = profileName.substring(profileName.lastIndexOf(":") + 1);
          }
          boolean saved = pm.saveProfile(profile, profileName);
          System.out.println("  Synced: " + profileName + " -> " + (saved ? "SUCCESS" : "FAILED"));
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

      // Sync all current profiles to JSON when user interacts with table
      // This ensures persistence when profiles are modified via UI
      BComponent target = getManager().getTarget();
      if (target != null) {
        syncAllProfilesToJson(target);
      }
    }

    /**
     * Sync all profiles from the target component to JSON storage.
     */
    private void syncAllProfilesToJson(BComponent target) {
      try {
        BDataSyncProfileView view = (BDataSyncProfileView) getManager();
        if (view.profileManager == null) {
          view.profileManager = new ProfileManager();
        }

        BComponent[] profiles = target.getChildren(BConnectionProfile.class);
        System.out.println("DataSyncProfileView: Syncing " + profiles.length + " profiles to JSON");

        for (BComponent comp : profiles) {
          if (comp instanceof BConnectionProfile) {
            BConnectionProfile profile = (BConnectionProfile) comp;
            String profileName = comp.getName();
            boolean saved = view.profileManager.saveProfile(profile, profileName);
            System.out.println("  Profile sync result for '" + profileName + "': " + (saved ? "SUCCESS" : "FAILED"));
          }
        }
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
