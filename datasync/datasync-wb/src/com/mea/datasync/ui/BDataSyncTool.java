// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.tool.BWbTool;
import javax.baja.workbench.BWbShell;
import javax.baja.ui.CommandArtifact;

/**
 * BDataSyncTool serves as the entry point for the N4-DataSync module
 * within Niagara Workbench. It extends BWbTool to appear in the
 * Tools menu.
 */
@NiagaraType
public class BDataSyncTool extends BWbTool {

    /*-
    class BDataSyncTool
    {
    }
    -*/

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    /**
     * Required Type field for all BObject subclasses.
     * This registers your class with the Niagara Type system.
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
        System.out.println("N4-DataSync Tool invoked!");
        // TODO: Open your tool's dialog or view here
        // For now, just print a message to console
        return null;
    }
}
