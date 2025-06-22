/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.control.trigger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.util.LexiconText;

/**
 * BIntervalTriggerMode allows the trigger to be fired at a specific interval.
 * In contrast to the BDailyTriggerMode, BIntervalTriggerMode is intended
 * for use when firing the trigger several times per day (e.g. every 5 minutes).
 *
 * @author    John Sublett
 * @creation  07 Jan 2004
 * @version   $Revision: 14$ $Date: 11/3/09 1:19:48 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BIntervalTriggerMode
  extends BTriggerMode
{
  /**
   * Create a interval trigger mode.  By default, every minute with
   * all days of week selected.
   */
  public static BIntervalTriggerMode make()
  {
    return DEFAULT;
  }

  /**
   * Create an interval trigger that fires each time the specified interval
   * has elapsed.
   *
   * @param interval The amount of time between triggers.
   */
  public static BIntervalTriggerMode make(BRelTime interval)
  {
    BIntervalTriggerMode m = new BIntervalTriggerMode();
    m.interval = interval;
    return m;
  }

  /**
   * Create an interval trigger that fires each time the specified interval
   * has elapsed, but only on the specified days of the week.
   *
   * @param interval The amount of time between triggers.
   * @param daysOfWeek The days of the week that the trigger will fire.
   */
  public static BIntervalTriggerMode make(BRelTime interval,
                                          BDaysOfWeekBits daysOfWeek)
  {
    BIntervalTriggerMode m = new BIntervalTriggerMode();
    m.interval = interval;
    m.daysOfWeek = daysOfWeek;
    return m;
  }

  /**
   * Create an interval trigger that fires each time the specified interval
   * has elapsed, but only between the specified times on the specified
   * days of the week.
   *
   * @param startTime The start time of the active range.
   * @param endTime The end time of the active range. 
   * @param interval The amount of time between triggers.
   * @param daysOfWeek The days of the week that the trigger will fire.
   */
  public static BIntervalTriggerMode make(BTime startTime,
                                          BTime endTime,
                                          BRelTime interval,
                                          BDaysOfWeekBits daysOfWeek)
  {
    return make(true, startTime, endTime, interval, daysOfWeek);
  }

  /**
   * Create an interval trigger that fires each time the specified interval
   * has elapsed.
   *
   * @param rangeEnabled Should the start and end times be enforced.
   * @param startTime The start time of the active range.
   * @param endTime The end time of the active range. 
   * @param interval The amount of time between triggers.
   * @param daysOfWeek The days of the week that the trigger will fire.
   */
  public static BIntervalTriggerMode make(boolean rangeEnabled,
                                          BTime startTime,
                                          BTime endTime,
                                          BRelTime interval,
                                          BDaysOfWeekBits daysOfWeek)
  {
    BIntervalTriggerMode m = new BIntervalTriggerMode();
    m.rangeEnabled = rangeEnabled;
    m.startTime = startTime;
    m.endTime = endTime;
    m.interval = interval;
    m.daysOfWeek = daysOfWeek;
    return m;
  }

  /**
   * Default constructor.
   */
  private BIntervalTriggerMode()
  {
  }

  public String getDisplayName(Context cx)
  {
    return dispName.getText(cx);
  }

  /**
   * Is the time of day range enabled?
   */
  public boolean isRangeEnabled()
  {
    return rangeEnabled;
  }

  /**
   * Get the start time of the active range.
   */
  public BTime getStartTime()
  {
    return startTime;
  }
  
  /**
   * Get the end time of the active range.
   */
  public BTime getEndTime()
  {
    return endTime;
  }

  /**
   * Get the amount of time between triggers.
   */
  public BRelTime getInterval()
  {
    return interval;
  }

  /**
   * Get the days of the week that the trigger will fire.
   */
  public BDaysOfWeekBits getDaysOfWeek()
  {
    return daysOfWeek;
  }

  public TriggerScheduler makeScheduler(BTimeTrigger trigger)
  {
    return new IntervalTriggerScheduler(trigger);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Hashcode implementation.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    return startTime.hashCode() ^ 
           endTime.hashCode() ^ 
           interval.hashCode() ^
           daysOfWeek.hashCode();
  }
  
  public boolean equals(Object o)
  {
    if (o instanceof BIntervalTriggerMode)
    {
      BIntervalTriggerMode itt = (BIntervalTriggerMode)o;
      return (rangeEnabled == itt.rangeEnabled) &&
             startTime.equals(itt.startTime) &&
             endTime.equals(itt.endTime) &&
             interval.equals(itt.interval) &&
             daysOfWeek.equals(itt.daysOfWeek);
    }
    return false;
  }

  public void encode(DataOutput out)
    throws IOException
  {
    out.writeBoolean(rangeEnabled);
    startTime.encode(out);
    endTime.encode(out);
    interval.encode(out);
    daysOfWeek.encode(out);
  }
  
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readBoolean(), // rangeEnabled
                (BTime)BTime.DEFAULT.decode(in), // startTime
                (BTime)BTime.DEFAULT.decode(in), // endTime
                (BRelTime)BRelTime.DEFAULT.decode(in), // interval
                (BDaysOfWeekBits)BDaysOfWeekBits.DEFAULT.decode(in)); // daysOfWeek
  }

  public String encodeToString()
  {
    StringBuilder s = new StringBuilder();
    s.append(BBoolean.encode(rangeEnabled));
    s.append(';').append(startTime.encodeToString());
    s.append(';').append(endTime.encodeToString());
    s.append(';').append(interval.encodeToString());
    s.append(';').append(daysOfWeek.encodeToString());

    return s.toString();
  }

  public BObject decodeFromString(String s)
    throws IOException
  {
    StringTokenizer st = new StringTokenizer(s, ";");
    
    return make(BBoolean.decode(st.nextToken()),
                (BTime)BTime.DEFAULT.decodeFromString(st.nextToken()),
                (BTime)BTime.DEFAULT.decodeFromString(st.nextToken()),
                (BRelTime)BRelTime.DEFAULT.decodeFromString(st.nextToken()),
                (BDaysOfWeekBits)BDaysOfWeekBits.DEFAULT.decodeFromString(st.nextToken()));
  }

  public String toString(Context cx)
  {
    return interval.toString(cx) + " " + daysOfWeek.toString(cx);
  }

////////////////////////////////////////////////////////////////
// IntervalTriggerScheduler
////////////////////////////////////////////////////////////////

  private class IntervalTriggerScheduler
    extends TriggerScheduler
  {
    public IntervalTriggerScheduler(BTimeTrigger trigger)
    {
      super(trigger);
    }
    
    /**
     * Start checking the time.
     */
    public void start()
    {
      // Use getNextTrigger() as the previous trigger since this is the startup case.
      // On startup, it may be the case that what would have been the next trigger
      // is now in the past.
      scheduledTime = getNextTriggerTime(Clock.time(), getTrigger().getLastTrigger());
      if (interval.getMillis() > 0L) 
        ticket = Clock.schedulePeriodically(getTrigger(), scheduledTime, interval, BTimeTrigger.checkTime, null);
    }
  
    /**
     * Stop updates.
     */
    public void stop()
    {
      if (ticket != null) ticket.cancel();
    }
    
    /**
     * Does the specified time indicate that it is time to fire the trigger?
     */
    public boolean isTriggerTime(BAbsTime time)
    {
      BAbsTime next = getTrigger().getNextTrigger();
      boolean fireIt = !time.isBefore(next);
      if (fireIt)
      {
        scheduledTime = getNextTriggerTime(time, next);
        return true;
      }
      else
      {
        // if it's not yet time, reschedule the ticket for the next time
        if (ticket != null)
        {
          scheduledTime = next;
          ticket.cancel();
          ticket = Clock.schedulePeriodically(getTrigger(), next, getInterval(), BTimeTrigger.checkTime, null);
        }
        
        return false;
      }
    }

    BAbsTime getScheduledTriggerTime() { return scheduledTime; }

    /**
     * Compute the next trigger time after the specified start time.
     *
     * @param from The earliest time that can be returned.  This
     *    method will find the first trigger time after the specified start time.
     * @param previous The time of the last trigger.  This may be after the
     *    current time if the system clock has changed.
     */
    public BAbsTime getNextTriggerTime(BAbsTime from, BAbsTime previous)
    {
      BDaysOfWeekBits daysOfWeek = getDaysOfWeek();
      
      if (daysOfWeek.isEmpty()) return BAbsTime.END_OF_TIME;
      if (previous.isAfter(from)) previous = BAbsTime.NULL;
      
      BRelTime interval = getInterval();
      
      if (interval.getMillis() <= 0L) return BAbsTime.END_OF_TIME;
      
      // if the time of day range isn't enabled, base the next trigger off of
      // the last trigger if possible or just off of the from time
      if (!isRangeEnabled())
      {
        BAbsTime result = null;
        
        // If the trigger has never been fired, return something between
        // the from time and the interval.
        if (previous.isNull())
          result = from.add(BRelTime.make((long)(interval.getMillis() * rand.nextDouble())));
        else
        {
          // If the trigger has fired before, try to base the next time
          // off of the last trigger.  If the last trigger was more than
          // three intervals before the from time, return something between
          // the from time and the interval.
          BRelTime diff = from.delta(previous).abs();
          if (diff.getMillis() > (interval.getMillis() * 3))
          {
            result = from.add(BRelTime.make((long)(interval.getMillis() * rand.nextDouble())));
          }
  
          // If the last trigger is close to the from time (i.e. within
          // three intervals), just increment the last trigger until
          // it is after the from time and return that.
          else
          {
            result = previous.add(interval);
            while (result.isBefore(from))
              result = result.add(interval);
          }
        }
        
        // now make sure it's on a trigger day
        while (!daysOfWeek.includes(result))
          result = result.add(interval);
        
        return result;
      }
      
      else
      {
        BTime startTime = getStartTime();
        BTime endTime = getEndTime();
        
        // sanity check
        if (endTime.isBefore(startTime)) return BAbsTime.END_OF_TIME;
  
        // the result can never be before the start time on the trigger day
        BAbsTime result = BAbsTime.make(from, startTime);
        
        // if from is after the end time, advance the day
        if (BTime.make(from).isAfter(endTime))
          result = result.nextDay();
        
        if (!daysOfWeek.includes(result))
        {
          while (!daysOfWeek.includes(result))
            result = result.nextDay();
        }
  
        // At this point, I'm at the start time on the trigger day.
        // Now just move forward to the first time equal to
        // or later than the from time.
        // FIXX - I need to be smarter here.  I need to use
        // the previous time to avoid starting at the beginning
        // of the interval and looping to the time.
        while (result.isBefore(from))
          result = result.add(interval);
  
        return result;
      }
    }
    
    private Clock.Ticket ticket;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final BIntervalTriggerMode DEFAULT = new BIntervalTriggerMode();

  public static final Type TYPE = Sys.loadType(BIntervalTriggerMode.class);
  public Type getType() { return TYPE; }

  private boolean rangeEnabled = false;
  private BTime startTime = BTime.make(0,0,0);
  private BTime endTime = BTime.make(23,59,59,999);
  private BRelTime interval = BRelTime.makeMinutes(1);
  private BDaysOfWeekBits daysOfWeek = BDaysOfWeekBits.DEFAULT;
  private BAbsTime scheduledTime;
  
  private static Random rand = new Random();
  private static LexiconText dispName = LexiconText.make("control", "trigger.interval");
}
