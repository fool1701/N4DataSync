/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.ext;

import javax.baja.control.BNumericPoint;
import javax.baja.control.BPointExtension;
import javax.baja.control.enums.BTotalizationInterval;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNumericTotalizerExt is a standard point extension used to
 * for integrating a numeric point value over time.  
 *
 * For example, a totalizer with a minutely totalization
 * interval can convert an instantaneous flow reading 
 * in cubic feet per minute (cfm) into a value representing
 * total cubic feet consumed.
 *
 * @author    Dan Giorgis
 * @creation  9 Nov 00
 * @version   $Revision: 31$ $Date: 6/23/09 10:22:28 AM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 These facets are applied against the total property.
 */
@NiagaraProperty(
  name = "totalFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()"
)
/*
 defines which input status flags will be propagated from
 input to output.
 */
@NiagaraProperty(
  name = "propagateFlags",
  type = "BStatus",
  defaultValue = "BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED | BStatus.STALE)"
)
/*
 defines which input status flags will denote invalid input
 values that should not be included in the total
 */
@NiagaraProperty(
  name = "invalidValueFlags",
  type = "BStatus",
  defaultValue = "BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED)"
)
/*
 Total accumulated value since last reset
 */
@NiagaraProperty(
  name = "total",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0)",
  flags = Flags.READONLY
)
/*
 Time of last reset
 */
@NiagaraProperty(
  name = "timeOfTotalReset",
  type = "BAbsTime",
  defaultValue = "BAbsTime.make()",
  flags = Flags.READONLY
)
/*
 Interval over which to accumulate values
 */
@NiagaraProperty(
  name = "totalizationInterval",
  type = "BTotalizationInterval",
  defaultValue = "BTotalizationInterval.minutely"
)
@NiagaraAction(
  name = "timerExpired"
)
/*
 Reset the total to zero
 */
@NiagaraAction(
  name = "resetTotal"
)
public class BNumericTotalizerExt
  extends BPointExtension
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.ext.BNumericTotalizerExt(48810392)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "totalFacets"

  /**
   * Slot for the {@code totalFacets} property.
   * These facets are applied against the total property.
   * @see #getTotalFacets
   * @see #setTotalFacets
   */
  public static final Property totalFacets = newProperty(0, BFacets.makeNumeric(), null);

  /**
   * Get the {@code totalFacets} property.
   * These facets are applied against the total property.
   * @see #totalFacets
   */
  public BFacets getTotalFacets() { return (BFacets)get(totalFacets); }

  /**
   * Set the {@code totalFacets} property.
   * These facets are applied against the total property.
   * @see #totalFacets
   */
  public void setTotalFacets(BFacets v) { set(totalFacets, v, null); }

  //endregion Property "totalFacets"

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED | BStatus.STALE), null);

  /**
   * Get the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public BStatus getPropagateFlags() { return (BStatus)get(propagateFlags); }

  /**
   * Set the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public void setPropagateFlags(BStatus v) { set(propagateFlags, v, null); }

  //endregion Property "propagateFlags"

  //region Property "invalidValueFlags"

  /**
   * Slot for the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #getInvalidValueFlags
   * @see #setInvalidValueFlags
   */
  public static final Property invalidValueFlags = newProperty(0, BStatus.make(BStatus.FAULT | BStatus.DOWN | BStatus.DISABLED), null);

  /**
   * Get the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #invalidValueFlags
   */
  public BStatus getInvalidValueFlags() { return (BStatus)get(invalidValueFlags); }

  /**
   * Set the {@code invalidValueFlags} property.
   * defines which input status flags will denote invalid input
   * values that should not be included in the total
   * @see #invalidValueFlags
   */
  public void setInvalidValueFlags(BStatus v) { set(invalidValueFlags, v, null); }

  //endregion Property "invalidValueFlags"

  //region Property "total"

  /**
   * Slot for the {@code total} property.
   * Total accumulated value since last reset
   * @see #getTotal
   * @see #setTotal
   */
  public static final Property total = newProperty(Flags.READONLY, new BStatusNumeric(0), null);

  /**
   * Get the {@code total} property.
   * Total accumulated value since last reset
   * @see #total
   */
  public BStatusNumeric getTotal() { return (BStatusNumeric)get(total); }

  /**
   * Set the {@code total} property.
   * Total accumulated value since last reset
   * @see #total
   */
  public void setTotal(BStatusNumeric v) { set(total, v, null); }

  //endregion Property "total"

  //region Property "timeOfTotalReset"

  /**
   * Slot for the {@code timeOfTotalReset} property.
   * Time of last reset
   * @see #getTimeOfTotalReset
   * @see #setTimeOfTotalReset
   */
  public static final Property timeOfTotalReset = newProperty(Flags.READONLY, BAbsTime.make(), null);

  /**
   * Get the {@code timeOfTotalReset} property.
   * Time of last reset
   * @see #timeOfTotalReset
   */
  public BAbsTime getTimeOfTotalReset() { return (BAbsTime)get(timeOfTotalReset); }

  /**
   * Set the {@code timeOfTotalReset} property.
   * Time of last reset
   * @see #timeOfTotalReset
   */
  public void setTimeOfTotalReset(BAbsTime v) { set(timeOfTotalReset, v, null); }

  //endregion Property "timeOfTotalReset"

  //region Property "totalizationInterval"

  /**
   * Slot for the {@code totalizationInterval} property.
   * Interval over which to accumulate values
   * @see #getTotalizationInterval
   * @see #setTotalizationInterval
   */
  public static final Property totalizationInterval = newProperty(0, BTotalizationInterval.minutely, null);

  /**
   * Get the {@code totalizationInterval} property.
   * Interval over which to accumulate values
   * @see #totalizationInterval
   */
  public BTotalizationInterval getTotalizationInterval() { return (BTotalizationInterval)get(totalizationInterval); }

  /**
   * Set the {@code totalizationInterval} property.
   * Interval over which to accumulate values
   * @see #totalizationInterval
   */
  public void setTotalizationInterval(BTotalizationInterval v) { set(totalizationInterval, v, null); }

  //endregion Property "totalizationInterval"

  //region Action "timerExpired"

  /**
   * Slot for the {@code timerExpired} action.
   * @see #timerExpired()
   */
  public static final Action timerExpired = newAction(0, null);

  /**
   * Invoke the {@code timerExpired} action.
   * @see #timerExpired
   */
  public void timerExpired() { invoke(timerExpired, null, null); }

  //endregion Action "timerExpired"

  //region Action "resetTotal"

  /**
   * Slot for the {@code resetTotal} action.
   * Reset the total to zero
   * @see #resetTotal()
   */
  public static final Action resetTotal = newAction(0, null);

  /**
   * Invoke the {@code resetTotal} action.
   * Reset the total to zero
   * @see #resetTotal
   */
  public void resetTotal() { invoke(resetTotal, null, null); }

  //endregion Action "resetTotal"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericTotalizerExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.equals(total))
      return getTotalFacets();
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  PointExtension
////////////////////////////////////////////////////////////////

  public boolean requiresPointSubscription()
  {
    return true;
  }

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////  

  /**
   * Parent must be NumericPoint.
   */
  public boolean isParentLegal(BComponent parent)
  {                        
    if (!super.isParentLegal(parent)) return false;
    return parent instanceof BNumericPoint;
  }

////////////////////////////////////////////////////////////////
//  Initialization
////////////////////////////////////////////////////////////////

  /**
   * Callback for when the extension is started.
   */
  public void started()
    throws Exception
  {
    super.started();
    // Initialize state variables
    lastExecuteTime = Clock.ticks();
    ticket = Clock.schedulePeriodically(this, BRelTime.makeSeconds(10), timerExpired, null);
  }
  
  public void stopped()
  {
    if(ticket!=null)
      ticket.cancel();
  }

////////////////////////////////////////////////////////////////
// Update Methods
////////////////////////////////////////////////////////////////

  /**
   * Callback for timer expired.
   */
  public void doTimerExpired()
  {
    onExecute(getParentPoint().getOutStatusValue(), null);
  }

  /** 
   * Called when either me or my parent control 
   * point is updated.
   */ 
  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric out = (BStatusNumeric)o;

    BStatus status = out.getStatus();
    double value = out.getValue();
    long now = Clock.ticks();
    
    double interval;
    if (getTotalizationInterval().equals(BTotalizationInterval.minutely))
      interval = 60000;  // milliseconds 
    else 
      interval = (60 * 60000);
    
    // Only include in total if input is valid
    if((status.getBits() & getInvalidValueFlags().getBits())==0 && !Double.isNaN(value))
    {
      // Only integrate if our previous value was also valid
      if((lastStatus.getBits() & getInvalidValueFlags().getBits())==0)
      {
        //  Calculate the totalization since the last
        //  time we updated.    
        //  (area under rectangle : deltaT * lastValue)    
        double thisTotal = (value * (now - lastExecuteTime)) / interval;
    
        getTotal().setValue(getTotal().getValue()+thisTotal);
      }
    } 
      
    if (Double.isNaN(value))
      getTotal().setStatus(BStatus.make(BStatus.FAULT | (status.getBits() & getPropagateFlags().getBits())));
    else
      getTotal().setStatus(BStatus.make(status.getBits() & getPropagateFlags().getBits()));
    
    lastStatus = out.getStatus(); 
    lastExecuteTime = now;    
  }

  public void changed(Property property, Context context)
  {    
    if(isRunning() && property.equals(totalizationInterval))
      doResetTotal();
    super.changed(property, context);
  }  
  
////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doResetTotal()
  {
    getTotal().setValue(0);
    setTimeOfTotalReset(BAbsTime.now());
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Clock.Ticket ticket;

  /* last time the totalizer updated */
  private long lastExecuteTime;   

  /* last status from parent object */
  private BStatus lastStatus = BStatus.ok;
}
