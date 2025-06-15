// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.workbench.view.BWbView;

/**
 * BDataSyncManagerView is a simple view for the N4-DataSync tool.
 * It displays the DataSync table in a basic layout.
 */
@NiagaraType
public class BDataSyncManagerView extends BWbView {

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    public static final Type TYPE = Sys.loadType(BDataSyncManagerView.class);
    
    @Override
    public Type getType() { 
        return TYPE; 
    }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

    public BDataSyncManagerView() {
        initializeComponents();
        layoutComponents();
    }

////////////////////////////////////////////////////////////////
// Component Initialization
////////////////////////////////////////////////////////////////

    private void initializeComponents() {
        // Create main table
        profileTable = new BDataSyncTable();
        
        // Create title label
        titleLabel = new BLabel("N4-DataSync Connection Profiles");
    }

    private void layoutComponents() {
        // Use BEdgePane for layout
        BEdgePane edgePane = new BEdgePane();
        
        // Title at top
        edgePane.setTop(titleLabel);
        
        // Main table in center with scroll pane
        BScrollPane scrollPane = new BScrollPane();
        scrollPane.setContent(profileTable);
        edgePane.setCenter(scrollPane);
        
        setContent(edgePane);
    }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

    private BDataSyncTable profileTable;
    private BLabel titleLabel;
}
