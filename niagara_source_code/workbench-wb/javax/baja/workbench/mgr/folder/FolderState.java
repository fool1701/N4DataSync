/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.folder;

import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrState;

/**
 * FolderState is a MgrState to be used with BFolderManager.
 *
 * @author    Brian Frank
 * @creation  29 Jun 04
 * @version   $Revision: 3$ $Date: 3/28/05 1:41:00 PM EST$
 * @since     Baja 1.0
 */
public class FolderState
  extends MgrState
{
    
////////////////////////////////////////////////////////////////
// MgrState
////////////////////////////////////////////////////////////////

  protected void restoreForType(BAbstractManager manager)
  { 
    super.restoreForType(manager);
    
    FolderController controller = (FolderController)manager.getController();
    controller.allDescendants.setSelected(isAllDescendants);
  }

  protected void saveForType(BAbstractManager manager)
  {                  
    super.saveForType(manager);
    
    FolderController controller = (FolderController)manager.getController();
    isAllDescendants = controller.allDescendants.isSelected();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  boolean isAllDescendants; 

} 


