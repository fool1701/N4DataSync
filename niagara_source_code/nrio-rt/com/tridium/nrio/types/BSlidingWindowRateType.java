/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.types;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Queue;

import com.tridium.nrio.points.BNrioCounterInputProxyExt;

@NiagaraType
@NiagaraProperty(
  name = "windows",
  type = "int",
  defaultValue = "6",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(2))")
)
public class BSlidingWindowRateType extends BFixedWindowRateType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.types.BSlidingWindowRateType(4066416120)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "windows"

  /**
   * Slot for the {@code windows} property.
   * @see #getWindows
   * @see #setWindows
   */
  public static final Property windows = newProperty(0, 6, BFacets.make(BFacets.MIN, BInteger.make(2)));

  /**
   * Get the {@code windows} property.
   * @see #windows
   */
  public int getWindows() { return getInt(windows); }

  /**
   * Set the {@code windows} property.
   * @see #windows
   */
  public void setWindows(int v) { setInt(windows, v, null); }

  //endregion Property "windows"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSlidingWindowRateType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSlidingWindowRateType()
  {
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  public void changed(Property property, Context context)
  {
    super.changed(property, context);
    
    if (isRunning())
    {
      if (property.equals(windows)){
        resizeQueue();
        resetRate();
        if (ticket != null)
        {
          ticket.cancel();
        }

        ticket = Clock.schedulePeriodically(getCounterProxy(), BAbsTime.now(), 
            BRelTime.make(getWindowSize()), BNrioCounterInputProxyExt.recalculateRate, null); 
      }

      if (property.equals(interval))
      {
        resetRate();
        if (ticket != null)
        {
          ticket.cancel();
        }
                
        ticket = Clock.schedulePeriodically(getCounterProxy(), BAbsTime.now(), 
            BRelTime.make(getWindowSize()), BNrioCounterInputProxyExt.recalculateRate, null); 
      }  
    }
  }

  public void descendantsStarted()
    throws Exception
  {
    super.descendantsStarted();

    resizeQueue();
    resetRate();
    if (ticket != null)
    {
      ticket.cancel();
    }
    
    ticket = Clock.schedulePeriodically(getCounterProxy(), BAbsTime.now(), 
        BRelTime.make(getWindowSize()), BNrioCounterInputProxyExt.recalculateRate, null);
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

  public synchronized void resetRate()
  {
    queue.clear();
    lastTicks = 0;
    if (getCounterProxy() != null)
      getCounterProxy().setStale(true, null);
  }

  public synchronized BStatusNumeric calculateRate(long count)
  {
    BStatusNumeric srate = null;
    
    long ticks = Clock.ticks();

    if (lastTicks == 0){
      lastTicks = ticks;
      CountSample sample = new CountSample(ticks, count);
      queue.enqueue(sample);      
      return null;
    }

    long delta = ticks - lastTicks;      
    
    if (queue.size() == getWindows()){
      CountSample lastSample = (CountSample) queue.dequeue();        
      long countdelta = count - lastSample.getCount();
      float timedelta = (ticks - lastSample.getTicks()) / 1000f;
      float rate = countdelta / timedelta;    
      rate *= getScale();
      srate = new BStatusNumeric(rate);
      getCounterProxy().setRateCalcTime(BAbsTime.now());
    }
    
    CountSample sample = new CountSample(ticks, count);
    //if (queue.isFull())
    //  queue.dequeue();
        
    queue.enqueue(sample);

    lastTicks = ticks;
    
    if (srate == null)
      srate = (BStatusNumeric) getCounterProxy().getReadValue();
    
    return srate;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  private synchronized void resizeQueue()
  {
    Queue newq = new Queue(getWindows());
        
    if (queue != null){
      for (int i = 0; i < queue.size(); i++){
        newq.enqueue(queue.dequeue()); 
      }
    }
    
    queue = newq;
  }
  
  private synchronized long getWindowSize()
  {
    return getInterval().getSeconds() / getWindows() * 1000L;
  }
  
////////////////////////////////////////////////////////////////
// Inner classes
////////////////////////////////////////////////////////////////

  class CountSample
  {
    public CountSample(long ticks, long count)
    {
      this.ticks = ticks;
      this.count = count;
    }
     
    public long getTicks()
    {
      return ticks;
    }
     
    public long getCount()
    {
      return count;
    }
    
    public String toString()
    {
      return " tx:" + ticks + ",cnt:" + count; 
    }

    private long ticks;
    private long count;     
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Queue queue = new Queue(6);
  private long lastTicks = 0;
  private Clock.Ticket ticket = null;
}
