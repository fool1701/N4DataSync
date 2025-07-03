/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrModel;

/**
 * BHistoryImportManager is the base class plugin for managing the transfer
 * of histories from a remote device into the local station.
 *
 * @author    John Sublett
 * @creation  05 Jan 2004
 * @version   $Revision: 4$ $Date: 5/26/04 7:19:40 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BHistoryImportManager
  extends BArchiveManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.history.BHistoryImportManager(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:24 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryImportManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryImportManager()
  {
  }
 
  /**
   * Create the model for this manager.  This method calls makeImportModel().
   */
  protected final MgrModel makeModel()
  {
    return makeImportModel();
  }

  /**
   * Create the import model for this manager.
   */
  protected ImportModel makeImportModel()
  {
    return new ImportModel(this);
  }
}
