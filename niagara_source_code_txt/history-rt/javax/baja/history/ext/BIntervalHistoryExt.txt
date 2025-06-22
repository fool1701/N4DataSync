/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import java.io.IOException;
import java.util.logging.Level;

import javax.baja.history.BCollectionInterval;
import javax.baja.history.BHistoryRecord;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIntervalHistoryExt provides the basic interface needed to collect
 * history records on a fixed interval.
 *
 * @author    John Sublett
 * @creation  18 Nov 2004
 * @version   $Revision: 7$ $Date: 6/5/09 1:12:03 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The amount of time between records.
 */
@NiagaraProperty(
  name = "interval",
  type = "BRelTime",
  defaultValue = "BRelTime.makeMinutes(15)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1))")
)
/*
 Receive notification that the collection interval has expired and
 it's time to collect another record.
 */
@NiagaraAction(
  name = "intervalElapsed",
  flags = Flags.HIDDEN
)
public abstract class BIntervalHistoryExt
  extends BHistoryExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BIntervalHistoryExt(1140407020)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "interval"

  /**
   * Slot for the {@code interval} property.
   * The amount of time between records.
   * @see #getInterval
   * @see #setInterval
   */
  public static final Property interval = newProperty(0, BRelTime.makeMinutes(15), BFacets.make(BFacets.MIN, BRelTime.make(1)));

  /**
   * Get the {@code interval} property.
   * The amount of time between records.
   * @see #interval
   */
  public BRelTime getInterval() { return (BRelTime)get(interval); }

  /**
   * Set the {@code interval} property.
   * The amount of time between records.
   * @see #interval
   */
  public void setInterval(BRelTime v) { set(interval, v, null); }

  //endregion Property "interval"

  //region Action "intervalElapsed"

  /**
   * Slot for the {@code intervalElapsed} action.
   * Receive notification that the collection interval has expired and
   * it's time to collect another record.
   * @see #intervalElapsed()
   */
  public static final Action intervalElapsed = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code intervalElapsed} action.
   * Receive notification that the collection interval has expired and
   * it's time to collect another record.
   * @see #intervalElapsed
   */
  public void intervalElapsed() { invoke(intervalElapsed, null, null); }

  //endregion Action "intervalElapsed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIntervalHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void started()
    throws Exception
  {
    super.started();

    // make sure the intervals match at startup
    if (isValidInterval(getInterval()))
    {
      BCollectionInterval collectionInterval = BCollectionInterval.make(getInterval());
      if (!getHistoryConfig().getInterval().equals(collectionInterval))
        getHistoryConfig().setInterval(collectionInterval);
    }
  }

  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();
    synchronized(lock)
    {
      if (ticket != null)
      {
        ticket.cancel();
        ticket = null;
      }
    }
  }

  @Override
  public void activated(BAbsTime startTime, BAbsTime currentTime, BStatusValue value)
    throws IOException
  {
    if (log.isLoggable(Level.FINE))
    {
      String s =
        getSlotPath() +
        ": activated (start=" + startTime.toString(BHistoryRecord.TIMESTAMP_FACETS) +
        ", current=" + currentTime.toString(BHistoryRecord.TIMESTAMP_FACETS) + ")";
      log.fine(s);
    }

    // check if this is the actual start of the active period or if I'm coming in
    // in the middle
    if ((startTime.getMillis() + 5000) > currentTime.getMillis())
      writeRecord(currentTime, value);

    scheduleCollection(currentTime);
  }

  @Override
  public void deactivated(BAbsTime currentTime, BStatusValue value)
  {
    synchronized(lock)
    {
      if (ticket != null)
      {
        ticket.cancel();
        ticket = null;
      }
    }
  }

  @Override
  public void pointChanged(BAbsTime timestamp, BStatusValue out)
    throws IOException
  {
  }

  /**
   * Write a record for the specified timestamp and value.
   */
  protected abstract void writeRecord(BAbsTime timestamp, BStatusValue out)
    throws IOException;

  /**
   * Schedule the interval collection.
   */
  private void scheduleCollection(BAbsTime from)
  {
    BAbsTime start = null;
    BRelTime interval = getInterval();
    try
    {
      if (!isValidInterval(interval)) return;
      if (getActivePeriod().isActive(from))
      {
        start = getActivePeriod().getActiveStart(from);
        while (!start.isAfter(from))
          start = start.add(interval);
      }
      else
      {
        start = getActivePeriod().getNextActive(from);
        if (start == null)
        {
          start = BAbsTime.make(from, BTime.make(0,0,0,0));
          while (!start.isAfter(from))
            start = start.add(interval);
        }
      }

      if (log.isLoggable(Level.FINE))
      {
        String s = "Collection scheduled. " +
                   "(start=" + start.toString(BHistoryRecord.TIMESTAMP_FACETS) +
                   ", interval=" + getInterval().toString(BHistoryRecord.TIMESTAMP_FACETS) + ")";
        log.fine(s);
      }
    }
    catch(RuntimeException e)
    {
      synchronized(lock)
      {
        if (ticket != null)
        {
          ticket.cancel();
          ticket = null;
        }
      }
      throw e;
    }

    synchronized(lock)
    {
      if (ticket != null)
      {
        ticket.cancel();
        ticket = null;
      }

      if (isRunning())
        ticket = Clock.schedulePeriodically(this, start, interval, intervalElapsed, null);
    }
  }

  /**
   * Receive notification that the current interval has elasped.  This
   * causes a record to be written.
   */
  public void doIntervalElapsed()
  {
    try
    {
      writeRecord(BAbsTime.make(), getParentPoint().getStatusValue());
    }
    catch(Exception ex)
    {
    }
  }

  /**
   * This callback is invoked when the system clock is modified.
   * The shift parameter specifies the positive or negative change
   * in the clock's value.
   * Overridden here to reschedule the collection if necessary.
   */
  @Override
  public void clockChanged(BRelTime shift)
    throws Exception
  {
    // 5/11/05 S. Hoye added this method to fix pacman issue 6504

    super.clockChanged(shift);

    if (getActive())
      scheduleCollection(BAbsTime.make());
  }

  /**
   * Handle a property change.
   */
  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (cx == Context.decoding) return;
    if (!isRunning()) return;

    if (p.equals(interval))
    {
      if (!isValidInterval(getInterval())) return;
      getHistoryConfig().setInterval(BCollectionInterval.make(getInterval()));
      if (getActive())
        scheduleCollection(BAbsTime.make());
    }
  }

  /**
   * Update the status for this extension.
   */
  @Override
  public void updateStatus()
  {
    super.updateStatus();

    // if I'm already in fault, then let it ride
    if (getStatus().isFault()) return;

    if (!isValidInterval(getInterval()))
    {
      BStatus newStatus = BStatus.makeFault(getStatus(), true);
      if (!getStatus().equals(newStatus))
      {
        setStatus(newStatus);
        setFaultCause("Invalid interval: " + getInterval().toString());
      }
    }
    else
    {
      setFaultCause("");
    }
  }

  /**
   * Test the validity of the current interval.
   */
  private boolean isValidInterval(BRelTime interval)
  {
    return interval.getMillis() > 0;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Clock.Ticket ticket;
  private final Object lock = new Object();
}
