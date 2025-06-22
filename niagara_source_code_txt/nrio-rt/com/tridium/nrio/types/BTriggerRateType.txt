/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.types;

import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
public class BTriggerRateType extends BAbstractRateType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.types.BTriggerRateType(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTriggerRateType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTriggerRateType()
  {
  }

  public void initType()
  {
    try
    {
      BProxyExt ext = (BProxyExt) getParent();
      
      ext.getParentPoint().add("recalculateRate", new BRecalcRateAction());
    }
    catch(Exception e)
    {
      log.error("Unable to add RecalcRateAction", e);
    }
  }
  
  public void cleanupType()
  {
    try
    {
      BProxyExt ext = (BProxyExt) getParent();
      
      ext.getParentPoint().remove("recalculateRate");
    }
    catch(Exception e)
    {
      log.error("Unable to remove RecalcRateAction", e);
    }
  }
  
  public synchronized void resetRate()
  {
    lastCount = 0;
    lastTicks = 0;
    if (getCounterProxy() != null)
      getCounterProxy().setStale(true, null);
  }
  
  public synchronized BStatusNumeric calculateRate(long count)
  {
    BStatusNumeric srate;
    
    long ticks = Clock.ticks();

    if (lastTicks == 0){
      lastTicks = ticks;
      lastCount = count;      
      return null;
    }

    long tickDelta = ticks - lastTicks;

    float rate = 1000f * (float)(count - lastCount) / (float)tickDelta;        
    rate *= getScale();

    lastTicks = ticks;
    lastCount = count;    
      
    srate = new BStatusNumeric(rate);
    getCounterProxy().setRateCalcTime(BAbsTime.now());

    return srate;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private long lastCount = 0;
  private long lastTicks = 0;
}
