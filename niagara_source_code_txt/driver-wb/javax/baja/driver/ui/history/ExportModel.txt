/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BHistoryExport;
import javax.baja.driver.history.BIArchiveFolder;
import javax.baja.sys.BObject;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrTypeInfo;

/**
 * ExportModel is the history model for managing export descriptors.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 7$ $Date: 5/19/09 2:54:58 PM EDT$
 * @since     Baja 1.0
 */
public class ExportModel
  extends ArchiveModel
{
  public ExportModel(BHistoryExportManager manager)
  {
    super(manager);
  }
  
  /**
   * The export manager only displays instances of BHistoryExport.
   */
  public Type[] getIncludeTypes()
  {
    BArchiveManager mgr = (BArchiveManager)getManager();
    if (mgr.supportsArchiveFolders())
    {
      BObject val = mgr.getCurrentValue();
      if (val instanceof BIArchiveFolder)
      {
        BIArchiveFolder folder = (BIArchiveFolder)val;
        Type folderType = folder.getArchiveFolderType();
        return new Type[] { BHistoryExport.TYPE, folderType };
      }    
    }
    
    return new Type[] { BHistoryExport.TYPE };
  }

  /**
   * Get the list of types supported by the new operation.  The
   * first entry in the list should be the default type.
   */  
  public MgrTypeInfo[] getNewTypes()
  {
    return MgrTypeInfo.makeArray(BHistoryExport.TYPE);
  }
}