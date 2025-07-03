/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.driver.history.BArchiveDescriptor;
import javax.baja.driver.history.BArchiveFolder;
import javax.baja.driver.history.BIArchiveFolder;
import javax.baja.sys.Property;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.folder.FolderModel;

/**
 * ArchiveModel is the MgrModel implementation for the archive manager
 * of the HistoryDeviceExt.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 11$ $Date: 5/19/09 2:54:57 PM EDT$
 * @since     Baja 1.0
 */
public class ArchiveModel
  extends FolderModel
{
  public ArchiveModel(BArchiveManager manager)
  {
    super(manager);
  }
  
  /**
   * Get the list of columns that are appropriate for
   * all archive descriptors.
   */
  protected MgrColumn[] makeColumns()
  {
    return cols;
  }
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Get the folder/container Type.
   */
  public Type getFolderType()
  {
    if (archiveFolderType != null) return archiveFolderType;
    
    try
    {
      BArchiveManager manager = (BArchiveManager)getManager();
      BIArchiveFolder folder = (BIArchiveFolder)(manager.getCurrentValue());
      archiveFolderType = folder.getArchiveFolderType();
    }
    catch(Exception e)
    {
      // this occurs when using offline
    }
    return archiveFolderType;
  }

////////////////////////////////////////////////////////////////
// Columns
////////////////////////////////////////////////////////////////
  
  private static Property[] timePath =
    new Property[] { BArchiveDescriptor.executionTime, BTimeTrigger.triggerMode };
  
  public MgrColumn idCol =
    new MgrColumn.Prop(BArchiveDescriptor.historyId, MgrColumn.EDITABLE);
  
  public MgrColumn timeCol =
    new MgrColumn.PropPath(BArchiveDescriptor.executionTime.getDefaultDisplayName(null),
                           timePath,
                           MgrColumn.EDITABLE | MgrColumn.UNSEEN);

  MgrColumn[] cols =
  {
    new MgrColumn.Name(MgrColumn.EDITABLE), // Now that we've added history folder support (3.5), unhide the name column by default
    idCol,
    timeCol,
    new MgrColumn.Prop(BArchiveDescriptor.enabled, MgrColumn.EDITABLE | MgrColumn.UNSEEN),
    new MgrColumn.Prop(BArchiveDescriptor.status, 0),
    new MgrColumn.Prop(BArchiveDescriptor.state),
    new MgrColumn.Prop(BArchiveDescriptor.lastAttempt, MgrColumn.UNSEEN),
    new MgrColumn.Prop(BArchiveDescriptor.lastSuccess),
    new MgrColumn.Prop(BArchiveDescriptor.lastFailure, MgrColumn.UNSEEN),
    new MgrColumn.Prop(BArchiveDescriptor.faultCause, MgrColumn.UNSEEN)
  };
  
  Type archiveFolderType = BArchiveFolder.TYPE;
}
