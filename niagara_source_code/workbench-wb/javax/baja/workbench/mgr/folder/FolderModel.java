/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.folder;

import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrModel;

/**
 * FolderModel is a MgrModel to be used with BFolderManager.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 5$ $Date: 3/28/05 1:41:00 PM EST$
 * @since     Baja 1.0
 */
public abstract class FolderModel
  extends MgrModel
{
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public FolderModel(BFolderManager manager)
  {
    super(manager);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Return if the all descendents toggle is currently true.
   */
  public final boolean isAllDescendants()                           
  {                                                         
    return ((FolderController)getManager().getController()).allDescendants.isSelected();
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Get the folder/container Type.
   */
  public abstract Type getFolderType(); 
              
} 


