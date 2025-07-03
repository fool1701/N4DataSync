/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BEnumRangeFE allows editing of the BEnumRange.
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 8$ $Date: 3/28/05 1:40:34 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:EnumRange"
  )
)
public class BEnumRangeFE
  extends BDialogFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BEnumRangeFE(3927460801)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumRangeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BEnumRangeFE()
  {    
    setContent(getEditButton());
  }

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject v, Context cx)
  {
    this.value = (BEnumRange)v;
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {
    return value;
  }

////////////////////////////////////////////////////////////////
// Edit Button
////////////////////////////////////////////////////////////////

  public void doEditPressed()
  {
    BEnumRange result = BEnumRangeDialog.open(this, value, !isReadonly());
    if (result != null)
    {
      value = result;
      setModified();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BEnumRange value;
  
}
