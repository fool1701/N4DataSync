/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.baja.file.BIFile;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.pane.BTextEditorPane;
import javax.baja.workbench.view.BWbView;

/**
 * BHexFileEditor
 *
 * @author    Brian Frank       
 * @creation  25 Jul 01
 * @version   $Revision: 10$ $Date: 3/28/05 1:40:38 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:IDataFile",
    requiredPermissions = "r"
  )
)
public class BHexFileEditor
  extends BWbView
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BHexFileEditor(4265906083)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHexFileEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {
    BIFile f = (BIFile)value;
    
    byte[] buf = f.read();
    CharArrayWriter caw = new CharArrayWriter();
    PrintWriter out = new PrintWriter(caw);
    ByteArrayUtil.hexDump(out, buf, 0, buf.length);
    out.flush();
    char[] text = caw.toCharArray();
    
    BTextEditorPane area = new BTextEditorPane("", 80, 40, false);
    area.getEditor().getModel().read(text);
    setContent(area);
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    throw new IllegalStateException();
  }
        
}
