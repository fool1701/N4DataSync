/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BLimitedFrozenEnumFE allows editing of a BFrozenEnum and allows
 * a subset of the range to be displayed in the drop down
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 2$ $Date: 4/2/08 2:48:36 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLimitedFrozenEnumFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BLimitedFrozenEnumFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLimitedFrozenEnumFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BLimitedFrozenEnumFE()
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
    this.val = (BEnum)v;    
    BEnumRange range = val.getRange();
    
    // if range specified via context, then 
    // that trumps range on enum itself
    if (cx != null)
    {
      BEnumRange r = (BEnumRange)cx.getFacet(BFacets.RANGE);
      if (r != null) range = r;
    }

    this.ordinals = range.getOrdinals();

    int sel = -1;
    combo.getList().removeAllItems();
    for(int i=0; i<ordinals.length; ++i)
    {
      int ordinal = ordinals[i];
      String displayTag = range.getDisplayTag(ordinal, null);
      combo.getList().addItem(displayTag);
      if (ordinal == val.getOrdinal()) sel = i;
    }               
    
    // set current index
    combo.setSelectedIndex(sel);
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {                
    int sel = combo.getSelectedIndex();
    return val.getRange().get(ordinals[sel]);
  }        

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BListDropDown combo = new BListDropDown();
  private BEnum val;
  private int[] ordinals;
}
