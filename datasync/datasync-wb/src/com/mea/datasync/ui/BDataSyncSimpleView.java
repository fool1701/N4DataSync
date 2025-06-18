// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.workbench.view.BWbView;

/**
 * BDataSyncSimpleView is a minimal test view for the DataSync tool.
 * Shows just a simple label to verify the view system is working.
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "DataSyncTool" }
  )
)
public class BDataSyncSimpleView extends BWbView {

    public static final Type TYPE = Sys.loadType(BDataSyncSimpleView.class);
    
    @Override
    public Type getType() { 
        return TYPE; 
    }

    public BDataSyncSimpleView() {
        BLabel label = new BLabel("DataSync Tool - Simple Test View");
        BEdgePane pane = new BEdgePane();
        pane.setCenter(label);
        setContent(pane);
    }
}