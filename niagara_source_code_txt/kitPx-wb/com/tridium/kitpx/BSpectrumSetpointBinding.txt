/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BINumeric;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BValueBinding;

/**
 * BSpectrumSetpointBinding may be used in conjunction with a SpecturmBinding
 * animate the setpoint property of the SpectrumBinding itself.
 *
 * @author    Brian Frank       
 * @creation  11 Sept 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
public class BSpectrumSetpointBinding
  extends BValueBinding
{                          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BSpectrumSetpointBinding(249336046)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpectrumSetpointBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public void targetChanged()
  {                
    if (isBound() && get() instanceof BINumeric)
    {                                          
      double value = ((BINumeric)get()).getNumeric();
      BBinding[] bindings = getWidget().getBindings();
      for(int i=0; i<bindings.length; ++i)
        if (bindings[i] instanceof BSpectrumBinding)
          ((BSpectrumBinding)bindings[i]).setSetpoint(value);
    }    
    super.targetChanged();
  }
          
}
