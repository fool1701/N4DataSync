/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BTimeFormatFE is used to configure a time format string.
 *
 * @author    Brian Frank       
 * @creation  3 May 01
 * @version   $Revision: 3$ $Date: 3/28/05 1:40:37 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BTimeFormatFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTimeFormatFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeFormatFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTimeFormatFE()
  {                     
    BTextDropDown field = new BTextDropDown(); 
    for(int i=0; i<predefined.length; ++i)
      field.getList().addItem(predefined[i]);
    linkTo("linkA", field, BTextDropDown.valueModified, setModified);
    linkTo("linkB", field, BTextDropDown.actionPerformed, actionPerformed);
    setContent(field);
  }

////////////////////////////////////////////////////////////////
// Editor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    BTextDropDown field = (BTextDropDown)getContent();
    field.getEditor().setEditable(!readonly);
    field.setDropDownEnabled(!readonly);
  }

  protected void doLoadValue(BObject value, Context context)
  {
    String text = value.toString();
    if (text.equals("")) text = DEFAULT;
    BTextDropDown field = (BTextDropDown)getContent();
    field.setText(text);
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    BTextDropDown field = (BTextDropDown)getContent();
    String text = field.getText();
    if (text.equalsIgnoreCase(DEFAULT)) text = ""; 
    return BString.make(text);
  }

////////////////////////////////////////////////////////////////
// Predefined
////////////////////////////////////////////////////////////////

  static final String DEFAULT = "(default)";
  
  static final String[] predefined =
  { 
    DEFAULT,
    "D-MMM-YY h:mm:ss a z",
    "D-M-YY h:mm:ss a z",
    "D-MMM-YY HH:mm:ss z",
    "D-M-YY HH:mm:ss z",
    
    "MMM/D/YY h:mm:ss a z",
    "M/D/YY h:mm:ss a z",
    "MMM/D/YY HH:mm:ss z",
    "M/D/YY HH:mm:ss z",
    
    "h:mm:ss a D-MMM-YY z",
    "h:mm:ss a D-M-YY z",
    "HH:mm:ss D-MMM-YY z",
    "HH:mm:ss D-M-YY z",
    
    "h:mm:ss a MMM/D/YY z",
    "h:mm:ss a M/D/YY z",
    "HH:mm:ss MMM/D/YY z",
    "HH:mm:ss M/D/YY z",
  };                 
  
}
