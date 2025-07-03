/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;

/**
 * BHistoryExportManager is the base class plugin for managing the transfer
 * of histories to a remote device from the local station.
 *
 * @author    John Sublett
 * @creation  05 Jan 2004
 * @version   $Revision: 5$ $Date: 5/26/04 7:19:34 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BHistoryExportManager
  extends BArchiveManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.history.BHistoryExportManager(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:24 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryExportManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryExportManager()
  {
  }

  /**
   * Create the model for this manager.  This method calls makeExportModel().
   */
  protected final MgrModel makeModel()
  {
    return makeExportModel();
  }

  /**
   * Create the export model for this manager.
   */
  protected ExportModel makeExportModel()
  {
    return new ExportModel(this);
  }
  
  protected MgrLearn makeLearn()
  {
    return new ExportLearn(this);
  }
  
  protected MgrController makeController()
  {
    return new ArchiveManagerController(this);
  }
}
