/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import javax.baja.file.BIDirectory;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.table.BTable;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.view.BIExportableTableView;
import javax.baja.workbench.view.BWbView;

/**
 * BDirectoryList is a listing of a BIDirectory.
 *
 * @author    Brian Frank       
 * @creation  27 Jan 03
 * @version   $Revision: 15$ $Date: 6/11/07 12:41:47 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:IDirectory",
    requiredPermissions = "r"
  )
)
public class BDirectoryList
  extends BWbView       
  implements BIExportableTableView
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BDirectoryList(3984413489)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDirectoryList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BDirectoryList()
  {
    table = new BDirTable();
    titlePane = BTitlePane.makePane("", table, "files");
    setContent(titlePane);
    setTransferWidget(table);
  }                 
  
  public BTable getExportTable()
  {
    return table;
  }

  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {          
    BIDirectory dir = (BIDirectory)value;           
    titlePane.setTitle(dir.getNavName());
    table.load(dir, cx);
    if (table.rows.length > 0)
    {
      table.getSelection().select(0);
      table.requestFocus();
    }      
  }
  
  BTitlePane titlePane;
  BDirTable table;  
  
}
