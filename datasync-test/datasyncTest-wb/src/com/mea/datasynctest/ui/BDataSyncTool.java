package com.mea.datasynctest.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.tool.BWbTool;
import javax.baja.workbench.BWbShell;
import javax.baja.ui.CommandArtifact;

/**
 * Minimal Niagara Workbench tool that appears in Tools menu.
 */
@NiagaraType
public class BDataSyncTool extends BWbTool {

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    /**
     * Required Type field for all BObject subclasses.
     */
    public static final Type TYPE = Sys.loadType(BDataSyncTool.class);

    /**
     * Get the Type of this object.
     * @return the Type
     */
    public Type getType() {
        return TYPE;
    }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

    /**
     * Default constructor.
     */
    public BDataSyncTool() {
    }

////////////////////////////////////////////////////////////////
// BWbTool
////////////////////////////////////////////////////////////////

    /**
     * Override invoke to handle tool activation from Tools menu.
     * @param shell the workbench shell
     * @return CommandArtifact for undo support, or null
     */
    @Override
    public CommandArtifact invoke(BWbShell shell) {
        System.out.println("DataSync Tool invoked from Tools menu!");

        // Simple confirmation that the tool was invoked
        // In a real tool, you would open a dialog or view here

        return null;
    }
}
