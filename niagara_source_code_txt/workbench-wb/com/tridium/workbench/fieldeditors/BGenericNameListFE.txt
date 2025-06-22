/*
 * Copyright 2009, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.util.BNameList;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * A generic (optional) field editor intended for a BNameList value.
 *
 * @author  Scott Hoye
 * @creation  25 Sep 2009
 * @version  1
 * @since   Niagara 3.5
 */
@NiagaraType
public class BGenericNameListFE
  extends BMultiRowFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BGenericNameListFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGenericNameListFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  


////////////////////////////////////////////////////////////////
// BMultiRowFE Overrides
////////////////////////////////////////////////////////////////  
    
  protected Object[] makeRowValues(BObject val, Context cx) throws Exception
  {
    String[] escaped = ((BNameList)val).getNames();
    int len = (escaped != null)?escaped.length:0;
    String[] unescaped = new String[len];
    for (int i = 0; i < len; i++)
      unescaped[i] = SlotPath.unescape(escaped[i]);
    return unescaped;
  }
  
  protected BObject makeSaveValue(BWidget[] rows, BObject val, Context cx) throws CannotSaveException, Exception
  {
    Array<String> nameArray = new Array<>(String.class);
    
    for (int i = 0; i < rows.length; ++i)
    {
      BWbFieldEditor compFe = (BWbFieldEditor)rows[i];
      BString s = (BString)compFe.saveValue(cx);
      
      if (s.getString().length() > 0)
        nameArray.add(SlotPath.escape(s.getString()));
    }
    
    return BNameList.make(nameArray.trim());
  }
  
  protected BWidget addRowWidget(BWidget parentWidget, Object rowVal, Context cx)
  {
    String name = (String)rowVal;
    
    if (name == null)
      name = "";
    
    BStringFE compFe = new BStringFE();
    parentWidget.add(null, compFe);
    
    compFe.loadValue(BString.make(name), cx);
    compFe.setReadonly(isReadonly());
    
    linkTo(compFe, BWbFieldEditor.setModified, setModified);
    
    return compFe;
  }
}
