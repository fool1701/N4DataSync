/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.view;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.table.BTable;

/**
 * BIExportableTableView is an interface implemented by WbViews which consist 
 * primarily of a BTable.  Views which implement this interface automatically 
 * provide view level exporters based on the table returned from the
 * <code>getExportTable()</code> method.
 *
 * @author    Brian Frank       
 * @creation  22 May 04
 * @version   $Revision: 1$ $Date: 11/16/04 10:17:50 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIExportableTableView
  extends BInterface
{                                                                   
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.view.BIExportableTableView(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIExportableTableView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTable getExportTable();


}
