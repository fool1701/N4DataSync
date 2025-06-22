/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BFrozenEnumFE allows editing of BFrozenEnum.
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 3$ $Date: 3/28/05 1:40:35 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:FrozenEnum"
  )
)
public class BFrozenEnumFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BFrozenEnumFE(3761244989)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFrozenEnumFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BFrozenEnumFE()
  {    
    setContent(combo);
    linkTo("lk0", combo, BTextDropDown.valueModified, setModified);
    linkTo("lk1", combo, BTextDropDown.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    combo.setDropDownEnabled(!readonly);
  }  

  protected void doLoadValue(BObject v, Context cx)
  { 
    // save fields
    BEnum val = (BEnum)v;
    range = val.getRange();
    ordinals = range.getOrdinals();
    
    // populate list
    int sel = -1;
    combo.getList().removeAllItems();
    for(int i=0; i<ordinals.length; ++i)
    {
      int ordinal = ordinals[i];
      String displayTag = range.getDisplayTag(ordinal, null);
      if (isValidOrdinal(ordinal))
      {
        combo.getList().addItem(displayTag);
      }
      if (ordinal == val.getOrdinal()) sel = i;
    }               
    
    // set current index
    combo.setSelectedIndex(sel);
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {                
    int sel = combo.getSelectedIndex();
    return range.get(ordinals[sel]);
  }

////////////////////////////////////////////////////////////////
// Protected Methods
////////////////////////////////////////////////////////////////

  protected boolean isValidOrdinal(int ordinal)
  {
    return range.isOrdinal(ordinal);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final BListDropDown combo = new BListDropDown();
  private BEnumRange range;
  private int[] ordinals;
  
}
