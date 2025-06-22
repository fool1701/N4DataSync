/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.types;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.points.BNrioCounterInputProxyExt;

@NiagaraType
@NiagaraProperty(
  name = "interval",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(60)",
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(1000))")
)
public class BFixedWindowRateType
  extends BAbstractRateType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.types.BFixedWindowRateType(3351117845)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "interval"

  /**
   * Slot for the {@code interval} property.
   * @see #getInterval
   * @see #setInterval
   */
  public static final Property interval = newProperty(0, BRelTime.makeSeconds(60), BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.make(1000)));

  /**
   * Get the {@code interval} property.
   * @see #interval
   */
  public BRelTime getInterval() { return (BRelTime)get(interval); }

  /**
   * Set the {@code interval} property.
   * @see #interval
   */
  public void setInterval(BRelTime v) { set(interval, v, null); }

  //endregion Property "interval"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFixedWindowRateType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFixedWindowRateType()
  {
  }

  public void descendantsStarted()
    throws Exception
  {
    super.descendantsStarted();
    
    resetRate();
    resetClockTicket();
  }
  
  public void stopped()
    throws Exception
  {
    super.stopped();
    
    resetRate();
    if (ticket != null)
    {
      ticket.cancel();
    }
    ticket = null;
  }

  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    
    if (isRunning())
    {
      if (property.equals(interval))
      {
        resetRate();
        resetClockTicket();
      }  
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
  	if(ticket == null)
  		resetClockTicket();
  	// return current rate if ticket not expired
  	
  	if(!ticket.isExpired())
  		return new BStatusNumeric(getCounterProxy().getRate());
  	
  	resetClockTicket();
    BStatusNumeric srate;
    
    long ticks = Clock.ticks();

    if (lastTicks == 0){
      lastTicks = ticks;
      lastCount = count;
      
      return null;
    }

    long tickDelta = ticks - lastTicks;

    float rate = 1000f * (float)(count - lastCount) / (float)tickDelta ;
    rate *= getScale();
    lastTicks = ticks;
    lastCount = count;    
      
    srate = new BStatusNumeric(rate);
    getCounterProxy().setRateCalcTime(BAbsTime.now());

    return srate;
  }

  private void resetClockTicket()
  {
    if (ticket != null)
    {
      ticket.cancel();
    }
    ticket = Clock.schedule(getCounterProxy(), getInterval(), 
    		BNrioCounterInputProxyExt.recalculateRate, null); 
  }
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private long lastCount = 0;
  private long lastTicks = 0;
  private Clock.Ticket ticket = null;
}
