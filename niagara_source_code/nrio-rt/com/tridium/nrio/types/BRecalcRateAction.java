/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.types;

import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAction;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.points.BNrioCounterInputProxyExt;

/**
 * @author    Bill Smith
 * @creation  30 Aug 2004
 * @version   $Revision$ $Date$
 * @since     Niagara 3
 */
@NiagaraType
public class BRecalcRateAction
  extends BAction
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.types.BRecalcRateAction(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRecalcRateAction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Type getParameterType()
  {
    return null;
  }

  public BValue getParameterDefault()
  {
    return null;
  }
 
  public BValue invoke(BComponent target, BValue arg)
  {
    doRecalculateRate(target, arg);
    
    return null;
  }

  public void doRecalculateRate(BComponent target, BValue arg)
  {
    if (!(target instanceof BControlPoint))
      throw new IllegalArgumentException("BRecalcRateAction cannot be invoked on " + target.getType());    
    BControlPoint point = (BControlPoint) target;
    
    BAbstractProxyExt ext = point.getProxyExt();
    if (!(ext instanceof BNrioCounterInputProxyExt))
      throw new IllegalArgumentException("BRecalcRateAction cannot be invoked on " + ext.getType());    
    BNrioCounterInputProxyExt rate = (BNrioCounterInputProxyExt) ext;
    
    rate.recalculateRate();
  }

  /**
   * Get the return type for the action, or
   * null if the action doesn't return a value.
   */
  public Type getReturnType()
  { 
    return null; 
  }
}
