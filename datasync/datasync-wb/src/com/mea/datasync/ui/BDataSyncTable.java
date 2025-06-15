// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.table.TableSelection;

import com.mea.datasync.model.ConnectionProfile;

/**
 * BDataSyncTable displays connection profiles and their sync status
 * in a table format following Niagara UI patterns.
 */
@NiagaraType
public class BDataSyncTable extends BTable {

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    public static final Type TYPE = Sys.loadType(BDataSyncTable.class);
    
    @Override
    public Type getType() { 
        return TYPE; 
    }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

    public BDataSyncTable() {
        // Initialize with dummy data
        this.profiles = createDummyData();
        
        // Set up table components following Niagara pattern
        setModel(new DynamicTableModel(new Model()));
        setSelection(new Selection());
    }

////////////////////////////////////////////////////////////////
// Data Management
////////////////////////////////////////////////////////////////

    /**
     * Create dummy data for demonstration
     */
    private List<ConnectionProfile> createDummyData() {
        List<ConnectionProfile> data = new ArrayList<>();
        
        // Profile 1 - Successful sync
        ConnectionProfile profile1 = new ConnectionProfile(
            "Building A HVAC", 
            "Excel", 
            "C:\\Data\\BuildingA_HVAC.xlsx",
            "Equipment",
            "192.168.1.100",
            "admin",
            "station:|slot:/Drivers"
        );
        profile1.setLastSync(BAbsTime.now());
        profile1.setStatus(ConnectionProfile.SyncStatus.SUCCESS);
        profile1.setComponentsCreated(45);
        data.add(profile1);
        
        // Profile 2 - Error state
        ConnectionProfile profile2 = new ConnectionProfile(
            "Building B Lighting",
            "Excel",
            "C:\\Data\\BuildingB_Lighting.xlsx", 
            "Points",
            "192.168.1.101",
            "engineer",
            "station:|slot:/Drivers/Lighting"
        );
        profile2.setLastSync(BAbsTime.now());
        profile2.setStatus(ConnectionProfile.SyncStatus.ERROR);
        profile2.setLastError("Connection timeout to target station");
        data.add(profile2);
        
        // Profile 3 - Never synced
        ConnectionProfile profile3 = new ConnectionProfile(
            "Chiller Plant",
            "Excel",
            "C:\\Data\\ChillerPlant.xlsx",
            "Chillers", 
            "192.168.1.102",
            "admin",
            "station:|slot:/Drivers/HVAC"
        );
        data.add(profile3);
        
        return data;
    }

    /**
     * Get the selected connection profile or null if no selection.
     */
    public ConnectionProfile getSelectedProfile() {
        int sel = getSelection().getRow();
        if (sel < 0 || sel >= profiles.size()) return null;
        return profiles.get(sel);
    }

////////////////////////////////////////////////////////////////
// Table Model
////////////////////////////////////////////////////////////////

    class Model extends TableModel {
        
        @Override
        public int getRowCount() {
            return profiles.size();
        }
        
        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }
        
        @Override
        public String getColumnName(int col) {
            return COLUMN_NAMES[col];
        }
        
        @Override
        public Object getValueAt(int row, int col) {
            if (row < 0 || row >= profiles.size()) return "";
            
            ConnectionProfile profile = profiles.get(row);
            switch (col) {
                case COL_NAME: return profile.getName();
                case COL_SOURCE: return profile.getSourcePath();
                case COL_TARGET: return profile.getTargetHost();
                case COL_STATUS: return profile.getStatus();
                case COL_LAST_SYNC: 
                    BAbsTime lastSync = profile.getLastSync();
                    if (lastSync == null) return "Never";
                    return DATE_FORMAT.format(new Date(lastSync.getMillis()));
                case COL_COMPONENTS: return Integer.toString(profile.getComponentsCreated());
                default: return "";
            }
        }
        
        @Override
        public boolean isColumnSortable(int col) {
            return true;
        }
    }

////////////////////////////////////////////////////////////////
// Selection
////////////////////////////////////////////////////////////////

    class Selection extends TableSelection {
        @Override
        public void updateTable() {
            super.updateTable();
            // Update any command states here if needed
        }
    }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

    private static final int COL_NAME = 0;
    private static final int COL_SOURCE = 1;
    private static final int COL_TARGET = 2;
    private static final int COL_STATUS = 3;
    private static final int COL_LAST_SYNC = 4;
    private static final int COL_COMPONENTS = 5;
    
    private static final String[] COLUMN_NAMES = {
        "Profile Name",
        "Source File", 
        "Target Station",
        "Status",
        "Last Sync",
        "Components"
    };
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

    private List<ConnectionProfile> profiles;
}
