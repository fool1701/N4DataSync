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
 * BDailyTriggerMode allows the trigger to be fired at a specific time
 * on selected days of the week.  It also provides randomization interval
 * so that the trigger is not fired at exactly the same time every day.
 * <p>
 * When the next trigger time is computed, a random amount of time between
 * zero milliseconds and the randomization interval is added to the
 * to configured time of day.
 *
 * @author    John Sublett
 * @creation  07 Jan 2004
 * @version   $Revision: 11$ $Date: 4/20/09 5:27:05 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDailyTriggerMode
  extends BTriggerMode
{
  /**
   * Default constructor.  2:00AM with all days of week
   * selected and no randomization.
   */
  private BDailyTriggerMode()
  {
  }

  /**
   * Create a default instance.  2:00AM with all days of week
   * selected and no randomization.
   */
  public static BDailyTriggerMode make()
  {
    return DEFAULT;
  }

  /**
   * Create a new fixed daily source at the specified time on
   * all days of the week.
   */
  public static BDailyTriggerMode make(BTime timeOfDay)
  {
    BDailyTriggerMode m = new BDailyTriggerMode();
    m.timeOfDay = timeOfDay;
    return m;
  }
  
  /**
   * Create a new daily trigger time at the specified time on
   * the specified days of the week.
   *
   * @param timeOfDay The time of day that the trigger will fire.
   * @param daysOfWeek The days of the week that the trigger will fire.
   */
  public static BDailyTriggerMode make(BTime timeOfDay,
                                       BDaysOfWeekBits daysOfWeek)
  {
    BDailyTriggerMode m = new BDailyTriggerMode();
    m.timeOfDay = timeOfDay;
    m.daysOfWeek = daysOfWeek;
    return m;
  }

  /**
   * Create a new fixed daily source at the specified time on
   * the specified days of the week.
   *
   * @param timeOfDay The time of day that the trigger will fire.  This
   *   time may differ from the calculated trigger time by as much as
   *   the randomization interval.
   * @param daysOfWeek The days of the week that the trigger will fire.
   * @param randInterval The randomization interval.  When the next
   *   time is calculated, a random amount of time between zero milliseconds
   *   and the randomization interval is added to the timeOfDay.
   */
  public static BDailyTriggerMode make(BTime timeOfDay,
                                       BDaysOfWeekBits daysOfWeek,
                                       BRelTime randInterval)
  {
    BDailyTriggerMode m = new BDailyTriggerMode();
    m.timeOfDay = timeOfDay;
    m.daysOfWeek = daysOfWeek;
    m.randInterval = randInterval;
    return m;
  }

  /**
   * Get the display name for this trigger time.
   */
  public String getDisplayName(Context cx)
  {
    return dispName.getText(cx);
  }

  /**
   * Get the time of day that the trigger will fire.
   */
  public BTime getTimeOfDay()
  {
    return timeOfDay;
  }

  /**
   * Get the days of the week that the trigger will fire.
   */
  public BDaysOfWeekBits getDaysOfWeek()
  {
    return daysOfWeek;
  }

  /**
   * Get the randomization interval.  When the next time is calculated,
   * a random amount of time between zero milliseconds
   * and the randomization interval is added to the timeOfDay.
   */
  public BRelTime getRandomizationInterval()
  {
    return randInterval;
  }

  /**
   * Make a scheduler for this mode.
   */
  public TriggerScheduler makeScheduler(BTimeTrigger trigger)
  {
    return new DailyTriggerScheduler(trigger);
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
    return timeOfDay.hashCode() ^ daysOfWeek.hashCode() ^ randInterval.hashCode();
  }
  
  public boolean equals(Object o)
  {
    if (o instanceof BDailyTriggerMode)
    {
      BDailyTriggerMode dtt = (BDailyTriggerMode)o;
      return timeOfDay.equals(dtt.timeOfDay) &&
             daysOfWeek.equals(dtt.daysOfWeek) &&
             randInterval.equals(dtt.randInterval);
    }
    return false;
  }

  public void encode(DataOutput out)
    throws IOException
  {
    timeOfDay.encode(out);
    daysOfWeek.encode(out);
    randInterval.encode(out);
  }
  
  public BObject decode(DataInput in)
    throws IOException
  {
    return make((BTime)BTime.DEFAULT.decode(in),
                (BDaysOfWeekBits)BDaysOfWeekBits.DEFAULT.decode(in),
                (BRelTime)BRelTime.DEFAULT.decode(in));
  }

  public String encodeToString()
  {
    return timeOfDay.encodeToString() + ";" +
           daysOfWeek.encodeToString() + ";" +
           randInterval.encodeToString();
  }

  public BObject decodeFromString(String s)
    throws IOException
  {
    StringTokenizer st = new StringTokenizer(s, ";");
    
    return make((BTime)BTime.DEFAULT.decodeFromString(st.nextToken()),
                (BDaysOfWeekBits)BDaysOfWeekBits.DEFAULT.decodeFromString(st.nextToken()),
                (BRelTime)BRelTime.DEFAULT.decodeFromString(st.nextToken()));
  }

  public String toString(Context cx)
  {
    StringBuilder s = new StringBuilder();
    s.append(timeOfDay.toString(cx));
    s.append(' ');
    s.append(daysOfWeek.toString(cx));
    if (randInterval.getMillis() != 0)
      s.append(" +~").append(randInterval.toString(cx));

    return s.toString();
  }

////////////////////////////////////////////////////////////////
// DailyTriggerScheduler
////////////////////////////////////////////////////////////////

  private class DailyTriggerScheduler
    extends TriggerScheduler
  {
    public DailyTriggerScheduler(BTimeTrigger trigger)
    {
      super(trigger);
    }
    
    /**
     * Start checking the time.
     */
    public void start()
    {
      // Use getNextExecution() as the previous trigger since this is the startup case.
      // On startup, it may be the case that was would have been the next trigger
      // is now in the past.
      BAbsTime now = Clock.time();
      scheduledTime = getNextTriggerTime(now, getTrigger().getLastTrigger());
      ticket = Clock.schedule(getTrigger(), scheduledTime, BTimeTrigger.checkTime, null);
    }
  
    /**
     * Stop updates.
     */
    public void stop()
    {
      if (ticket != null) ticket.cancel();
      ticket = null;
    }
    
    /**
     * Does the specified time indicate that it is time to fire the trigger?
     */
    public boolean isTriggerTime(BAbsTime time)
    {
      boolean fireTime = isFireTime(time);
      
      // reschedule the trigger
      if (ticket != null)
      {
        scheduledTime = fireTime ? getNextTriggerTime(time, time) : getNextTriggerTime(time, getTrigger().getLastTrigger());
        ticket.cancel();
        ticket = Clock.schedule(getTrigger(), scheduledTime, BTimeTrigger.checkTime, null);
      }
  
      return fireTime;
    }

    BAbsTime getScheduledTriggerTime()
    {
      return scheduledTime;
    }

    /**
     * Get the next trigger time after the specified start time.
     *
     * @param start The earliest time that can be returned.  This
     *    method will find the first trigger time after the specified start time.
     * @param previous The time of the last trigger.  This may be after the
     *    current time if the system clock has changed.
     */
    public BAbsTime getNextTriggerTime(BAbsTime start, BAbsTime previous)
    {
      if (daysOfWeek.isEmpty()) return BAbsTime.END_OF_TIME;
      
      BAbsTime t = BAbsTime.make(start, timeOfDay);
  
      // If the start time is included in the day of week selection,
      // see if the time has already passed.
      if (daysOfWeek.includes(start))
      {
        // If the time has not yet passed then the next archive time
        // is on the start day at the configured time.
        if (!t.isBefore(start))
        {
          // now randomize if necessary
          if (randInterval.getMillis() == 0)
            return t;
          else
          {
            long randMillis = (long)((double)randInterval.getMillis() * rand.nextDouble());
            BAbsTime randTime = t.add(BRelTime.make(randMillis));
            if (randTime.isAfter(BAbsTime.now())) return randTime;
            else return t;
          }
        }
      }

      // Before advancing to the next day, we need to check for the system clock change
      // case, so we don't skip a trigger that should happen today.  So we need to look
      // at the previous trigger time to determine if the next trigger should still happen
      // today.
      long previousMillis = ((previous != null) && (!previous.equals(BAbsTime.END_OF_TIME))) ? previous.getMillis() : 0L;
      long elapsed = t.getMillis() - previousMillis; // time expired since last trigger
      if ((daysOfWeek.includes(t)) && (elapsed >= (BRelTime.MILLIS_IN_DAY - (randInterval.getMillis() + 60000L)))) // give it a 1min buffer
      { // We haven't executed today yet, so we need to trigger very soon (probably due
        // to a system clock change)
        BAbsTime maxT = t.add(randInterval);
        if ((scheduledTime != null) && (!scheduledTime.isBefore(start)) && (maxT.getMillis() >= scheduledTime.getMillis()))
        {
          return scheduledTime; // just reuse the scheduledTime
        }
        else if (!maxT.isBefore(start))
        {
          return maxT; // Use the maximum randomization
        }
      }

      // If the time has already passed today, move to the next
      // enabled day. This is safe because I've already bailed if
      // weekdays is empty.
      t = t.nextDay();      
      while (!daysOfWeek.includes(t))
        t = t.nextDay();
  
      // now randomize if necessary
      if (randInterval.getMillis() == 0)
        return t;
      else
      {
        long randMillis = (long)((double)randInterval.getMillis() * rand.nextDouble());
        BAbsTime randTime = t.add(BRelTime.make(randMillis));
        if (randTime.isAfter(BAbsTime.now())) return randTime;
        else return t;
      }
    }

    /**
     * Convenience method returning true if the time is equal to or after
     * the next trigger time, indicating the time trigger should be fired.
     *
     * @since Niagara 4.10u6, 4.12
     *
     * @param time the time to check against next trigger time
     * @return boolean
     */
    private boolean isFireTime(BAbsTime time)
    {
      return !time.isBefore(getTrigger().getNextTrigger());
    }

    private Clock.Ticket ticket;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final BDailyTriggerMode DEFAULT = new BDailyTriggerMode();

  public static final Type TYPE = Sys.loadType(BDailyTriggerMode.class);
  public Type getType() { return TYPE; }

  private BTime timeOfDay = BTime.make(2,0,0);
  private BDaysOfWeekBits daysOfWeek = BDaysOfWeekBits.DEFAULT;
  private BRelTime randInterval = BRelTime.make(0);
  private BAbsTime scheduledTime;
  
  private static final Random rand = new Random();
  private static final LexiconText dispName = LexiconText.make("control", "trigger.daily");
}
