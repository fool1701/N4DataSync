// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BButton;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BFlowPane;
import javax.baja.workbench.view.BWbView;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.event.WidgetSubscriber;

import com.mea.datasync.model.ConnectionProfile;

/**
 * BDataSyncManagerView is a simple view for the N4-DataSync tool.
 * It displays the DataSync table in a basic layout.
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "datasync:DataSyncTool" }
  )
)
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
        
        // Create action buttons
        createButton = new BButton("New Profile");
        editButton = new BButton("Edit Profile");
        deleteButton = new BButton("Delete Profile");
        syncButton = new BButton("Sync Now");
        
        // Add event subscribers using Niagara's event system
        new WidgetSubscriber() {
            public void actionPerformed(BWidgetEvent e) {
                onCreateProfile();
            }
        }.subscribe(createButton);
        
        new WidgetSubscriber() {
            public void actionPerformed(BWidgetEvent e) {
                onEditProfile();
            }
        }.subscribe(editButton);
        
        new WidgetSubscriber() {
            public void actionPerformed(BWidgetEvent e) {
                onDeleteProfile();
            }
        }.subscribe(deleteButton);
        
        new WidgetSubscriber() {
            public void actionPerformed(BWidgetEvent e) {
                onSyncProfile();
            }
        }.subscribe(syncButton);
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
        
        // Action buttons at bottom using BEdgePane
        BEdgePane buttonPane = new BEdgePane();
        buttonPane.setLeft(createButton);
        buttonPane.setCenter(editButton);
        buttonPane.setRight(deleteButton);
        // For now, put sync button in a separate area or combine differently
        
        // Alternative: Create a simple container with manual layout
        BEdgePane buttonContainer = new BEdgePane();
        BEdgePane leftButtons = new BEdgePane();
        leftButtons.setLeft(createButton);
        leftButtons.setCenter(editButton);
        BEdgePane rightButtons = new BEdgePane();
        rightButtons.setLeft(deleteButton);
        rightButtons.setCenter(syncButton);
        
        buttonContainer.setLeft(leftButtons);
        buttonContainer.setRight(rightButtons);
        edgePane.setBottom(buttonContainer);
        
        setContent(edgePane);
    }

////////////////////////////////////////////////////////////////
// Action Handlers
////////////////////////////////////////////////////////////////

    private void onCreateProfile() {
        // TODO: Open profile creation dialog
        System.out.println("Create new profile");
    }
    
    private void onEditProfile() {
        // TODO: Open profile edit dialog for selected profile
        ConnectionProfile selected = profileTable.getSelectedProfile();
        if (selected != null) {
            System.out.println("Edit profile: " + selected.getName());
        }
    }
    
    private void onDeleteProfile() {
        // TODO: Delete selected profile with confirmation
        ConnectionProfile selected = profileTable.getSelectedProfile();
        if (selected != null) {
            System.out.println("Delete profile: " + selected.getName());
        }
    }
    
    private void onSyncProfile() {
        // TODO: Execute sync for selected profile
        ConnectionProfile selected = profileTable.getSelectedProfile();
        if (selected != null) {
            System.out.println("Sync profile: " + selected.getName());
        }
    }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

    private BDataSyncTable profileTable;
    private BLabel titleLabel;
    private BButton createButton;
    private BButton editButton;
    private BButton deleteButton;
    private BButton syncButton;
}
