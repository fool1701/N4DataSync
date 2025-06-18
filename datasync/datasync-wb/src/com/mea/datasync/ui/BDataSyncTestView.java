// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BButton;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.workbench.view.BWbView;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.event.WidgetSubscriber;

/**
 * BDataSyncTestView is a basic test view with interactive elements.
 * Used to verify the view system and event handling are working.
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "datasync:DataSyncTool" }
  )
)
public class BDataSyncTestView extends BWbView {

    public static final Type TYPE = Sys.loadType(BDataSyncTestView.class);
    
    @Override
    public Type getType() { 
        return TYPE; 
    }

    public BDataSyncTestView() {
        initializeComponents();
    }

    private void initializeComponents() {
        // Create components
        BLabel titleLabel = new BLabel("DataSync Test View");
        statusLabel = new BLabel("Status: Ready");
        BButton testButton = new BButton("Test Action");
        
        // Set up event handler
        new WidgetSubscriber() {
            public void actionPerformed(BWidgetEvent e) {
                onTestAction();
            }
        }.subscribe(testButton);
        
        // Layout - keep it simple
        BEdgePane mainPane = new BEdgePane();
        mainPane.setTop(titleLabel);
        mainPane.setCenter(statusLabel);
        mainPane.setBottom(testButton);
        
        setContent(mainPane);
    }
    
    private void onTestAction() {
        statusLabel.setText("Status: Test button clicked!");
        System.out.println("DataSync Test View: Button clicked");
    }

    private BLabel statusLabel;
}