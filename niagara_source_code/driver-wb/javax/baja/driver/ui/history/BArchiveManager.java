/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BArchiveFolder;
import javax.baja.driver.history.BIArchiveFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.folder.BFolderManager;

/**
 * BArchiveManager is an implementation of BAbstractManager
 * used to manage the transfer of histories between devices.
 *
 * @author    John Sublett
 * @creation  02 Jan 2004
 * @version   $Revision: 8$ $Date: 5/19/09 2:54:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BArchiveManager
  extends BFolderManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.history.BArchiveManager(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:24 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Returns true if history folders are supported by the driver
   * and should be included in the archive manager view.
   *
   * @since Niagara 3.5
   */
  boolean supportsArchiveFolders()
  {
    BObject val = getCurrentValue();
    if (val instanceof BIArchiveFolder)
    {
      BIArchiveFolder folder = (BIArchiveFolder)val;
      Type folderType = folder.getArchiveFolderType();
      if ((folderType.equals(BArchiveFolder.TYPE)) && 
          (!folder.getDeviceExt().supportsGenericArchiveFolder()))
        return false;
    }
    return true;
  }
}
