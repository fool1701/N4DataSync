/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BBlob;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.text.BTextEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BBlobFE allows viewing and editing of a BBlob where
 * the bytes are entered and displayed in hex.
 *
 * @author    Andy Frank       
 * @creation  20 Dec 07
 * @version   $Revision: 1$ $Date: 12/20/07 9:51:52 AM EST$
 * @since     Niagara 3.4
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Blob"
  )
)
public class BBlobFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BBlobFE(1487636660)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBlobFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBlobFE()
  {    
    BGridPane grid = new BGridPane(2);
    grid.add(null, new BLabel("0x"));
    grid.add(null, field = new BTextField());
    setContent(grid);    
    
    linkTo(field, BTextEditor.textModified, setModified);
    linkTo(field, BTextField.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////
  
  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
  }  

  protected void doLoadValue(BObject value, Context cx)
  {
    BBlob blob = (BBlob)value;
    byte[] bytes = blob.copyBytes();
    
    StringBuilder s = new StringBuilder(bytes.length*2);
    for (int i=0; i<bytes.length; i++)
      s.append(TextUtil.byteToHexString(bytes[i]));
    field.setText(s.toString());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    String s = field.getText();
    
    int len = s.length();
    boolean odd = len % 2 != 0;
    if (odd) len++;
    len = len / 2;    
    byte[] bytes = new byte[len];

    for (int i=0,j=0; i<s.length();)
    {
      int h,l;      
      if (i == 0 && odd)
      {    
        h = 0;
        l = TextUtil.hexCharToInt(s.charAt(i++));
      }
      else
      {
        h = TextUtil.hexCharToInt(s.charAt(i++));
        l = TextUtil.hexCharToInt(s.charAt(i++));
      }      
      bytes[j++] = (byte)((h << 4) | l);
    }
  
    return BBlob.make(bytes);
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  BTextField field;
    
}
