// In: com.mea.datasync.ui
package com.mea.datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.tool.BWbNavNodeTool;

/**
 * BDataSyncTool serves as the entry point for the N4-DataSync module
 * within Niagara Workbench. It extends BWbNavNodeTool to appear in the
 * Tools menu and as a navigable node under the 'tool:' scheme.
 *
 * CRITICAL: This tool MUST be registered as an agent on "workbench:Workbench"
 * in module-include.xml to appear in the Tools menu. Views register as agents
 * on "datasync:DataSyncTool" to appear when the tool is opened.
 *
 * When selected from the Tools menu, it automatically opens the default
 * view associated with this tool, which will be the DataSync Manager view.
 */
@NiagaraType
public class BDataSyncTool extends BWbNavNodeTool {

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
     * Tools are typically singletons, so no public constructor is needed for external instantiation.
     */
    public BDataSyncTool() {
    }

    // Note: BWbNavNodeTool automatically handles the invoke() method
    // by hyperlinking to the default view associated with this tool's ORD.
    // The tool's ORD will be "tool:com.mea.datasync.ui.BDataSyncTool|slot:/"
}
