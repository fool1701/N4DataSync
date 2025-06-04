// In: com.mea.n4datasync.ui
package com.mea.n4datasync.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.tool.BWbNavNodeTool;
import javax.baja.workbench.BWbShell; // For the invoke method if you need it

/**
 * BDataSyncTool serves as the entry point for the N4-DataSync module
 * within Niagara Workbench. It extends BWbNavNodeTool to appear in the
 * Tools menu and as a navigable node under the 'tool:' scheme.
 */
@NiagaraType // Required annotation for Niagara to recognize this as a Type
public class BDataSyncTool extends BWbNavNodeTool {

    /*-
    class BDataSyncTool
    {
    }
    -*/
    // The above-commented block is for Slot-o-matic if you were using it
    // to generate boilerplate Type code. For now, we'll manually add the TYPE field.

    /**
     * Required Type field for all BObject subclasses.
     * This registers your class with the Niagara Type system.
     */
    public static final Type TYPE = Sys.loadType(BDataSyncTool.class);

    @Override
    public Type getType() {
        return TYPE;
    }

    /**
     * Default constructor.
     * Tools are typically singletons, so no public constructor is needed for external instantiation.
     */
    protected BDataSyncTool() {
    }

    // You can override the 'invoke' method if you want specific code to run
    // when the tool is selected from the Tools menu.
    // However, if you register a default view for this tool (which we will do),
    // Workbench will automatically open that view.
    /*
    @Override
    public void invoke(BWbShell shell) {
        // Example: If you wanted to open a specific dialog directly
        // MyConnectionManagerDialog.open(shell);
        System.out.println("N4-DataSync Tool invoked!");
        // Typically, you'd let the Workbench open the default view associated with this tool's ORD.
    }
    */
}
