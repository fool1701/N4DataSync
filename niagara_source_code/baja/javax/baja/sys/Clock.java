/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

import com.tridium.nre.platform.IPlatformProvider;
import com.tridium.nre.platform.PlatformUtil;
import com.tridium.nre.security.NiagaraBasicPermission;
import com.tridium.sys.Nre;

/**
 * Clock provides access to the system clock.
 *
 * @author    Brian Frank
 * @creation  9 Aug 00
 * @version   $Revision: 25$ $Date: 7/1/11 11:29:43 AM EDT$
 * @since     Baja 1.0
 */
public class Clock
{

////////////////////////////////////////////////////////////////
// Time
////////////////////////////////////////////////////////////////

  /**
   * Get the number of system time ticks in milliseconds.
   * Ticks are independent of changes to system time.
   */
  public static long ticks()
  {
    return PlatformProviderHolder._PLATFORM_PROVIDER_INSTANCE.getTickCount();
  }

  /**
   * Get the number of system time ticks in nanoseconds.
   * Ticks are independent of changes to system time.
   *
   * @return the current value of the running Niagara Platform's
   *         high-resolution time source, in nanoseconds
   */
  public static long nanoTicks()
  {
    return PlatformProviderHolder._PLATFORM_PROVIDER_INSTANCE.getNanoCount();
  }

  /**
   * Get the system time as the number of milliseconds
   * since the epoch.  This method is equivalent to
   * {@code System.currentTimeMillis()}.
   *
   * @return the difference, measured in milliseconds,
   *    between the current time and midnight,
   *    January 1, 1970 UTC.
   */
  public static long millis()
  {
    return System.currentTimeMillis();
  }

  /**
   * Get an instance of {@code BAbsTime} with the current
   * time within the specified tolerance.  This allows
   * operations which need a {@code BAbsTime} to share a
   * single immutable instance when millisecond accuracy is not
   * important.
   *
   * @param tolerance number of milliseconds which
   *    the resulting time may be off from the actual
   *    current time.  The greater the tolerance, the
   *    more likely the ability to share an existing
   *    instance and increase performance.
   * @return {@code BAbsTime} for the current time
   *    accurate up with {@code tolerance} milliseconds.
   */
  public static BAbsTime time(int tolerance)
  {
    synchronized(timeLock)
    {
      long now = System.currentTimeMillis();
      long delta = now - lastMillis;
      if (delta > tolerance || -delta > tolerance)
      {
        lastMillis = now;
        lastTime = BAbsTime.make(now);
      }
      return lastTime;
    }
  }

  /**
   * Convenience method for time(int) with a
   * default tolerance of 100 milliseconds.
   */
  public static BAbsTime time()
  {
    return time(100);
  }

  /**
   * Get an absolute time for top of the next minute.
   */
  public static BAbsTime nextTopOfMinute()
  {
    long nowMillis = System.currentTimeMillis();
    BAbsTime now = BAbsTime.make(nowMillis);
    return BAbsTime.make(nowMillis + 60000 - now.getSecond()*1000 - now.getMillisecond());
  }

  /**
   * Set the current system time.
   *
   * @throws AccessControlException if the calling stack does
   *         not have the NiagaraBasicPermission.SET_TIME permission.
   * @param time BAbsTime initialized to desired UTC millis
   *             for system clock. The Time Zone of the BAbsTime
   *             is not applied to the system.
   */
  public static void setTime(BAbsTime time)
  {
    //Verify permission to change time
    NiagaraBasicPermission setTimePermission = new NiagaraBasicPermission("SET_TIME");
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) sm.checkPermission(setTimePermission);
    
    //Verify that we have the ability to change time
    AccessController.doPrivileged((PrivilegedAction<Void>) () ->
    {
      if (!PlatformUtil.getPlatformProvider().isSystemTimeReadonly())
      {
        log.info("Changing system time " + time(0) + " -> " + time);
        PlatformUtil.getPlatformProvider().setSystemTime(time.getMillis());
        return null;
      }
      else
      {
        log.info("System time readonly, can not process setTime() request");
        return null;
      }
    });
  }

////////////////////////////////////////////////////////////////
// Scheduling
////////////////////////////////////////////////////////////////

  /**
   * Schedule an single action at the specified
   * relative time from now.  This action is
   * scheduled independently of changes to the
   * system clock.
   *
   * @param target BComponent to invoke the
   *   action upon.
   * @param time relative time from now
   *    to invoke the action.
   * @param action the action to invoke on
   *    this schedule's component.
   * @param arg argument to pass to the action
   *    or null if no argument is required.
   * @return Ticket instance which may be used to
   *    cancel the scheduled action.
   * @throws IllegalArgumentException if time less than
   *   or equal to zero.
   */
  public static Ticket schedule(BComponent target, BRelTime time, Action action, BValue arg)
  {
    return Nre.getEngineManager().schedule(target, time, action, arg);
  }

  /**
   * Schedule an single action at the specified
   * absolute point in time.  If the given time
   * has already occurred, the action is called as
   * soon as possible.  The specified time is
   * dependent on changes to the system clock.
   *
   * @param target BComponent to invoke the
   *   action upon.
   * @param time absolute point in time at
   *    which to invoke the action.
   * @param action the action to invoke on
   *    this schedule's component.
   * @param arg argument to pass to the action
   *    or null if no argument is required.
   * @return Ticket instance which may be used to
   *    cancel the scheduled action.
   * @throws IllegalArgumentException if time less than
   *   or equal to zero.
   */
  public static Ticket schedule(BComponent target, BAbsTime time, Action action, BValue arg)
  {
    return Nre.getEngineManager().schedule(target, time, action, arg);
  }

  /**
   * Schedule a reoccurring call to the action using the
   * specified interval period.  Actions are scheduled
   * based relative to the actual time of the last
   * invocation.  This guarantees fairly regular intervals,
   * but introduces a drift in the actual frequency
   * over time.  Actions scheduled using this method
   * are independent of changes to the system clock.
   *
   * @param target BComponent to invoke the
   *   action upon.
   * @param period fixed interval over which to
   *    repeatedly invoke the specified action.
   * @param action the action to invoke on
   *    this schedule's component.
   * @param arg argument to pass to the action
   *    or null if no argument is required.
   * @return Ticket instance which may be used to
   *    cancel the scheduled action.
   * @throws IllegalArgumentException if period less than
   *   or equal to zero.
   */
  public static Ticket schedulePeriodically(BComponent target, BRelTime period, Action action, BValue arg)
  {
    return Nre.getEngineManager().schedulePeriodically(target, period, action, arg);
  }

  /**
   * Schedule a reoccurring call to the action based on
   * a fixed starting time with the specified interval
   * period.  Actions are scheduled based relative
   * to the scheduled time of the last invocation, not
   * the actual time.  This introduces slight variations
   * in the actual interval, but guarantees regular
   * invokes over time on start+period*n.  Actions
   * using this method are dependent on changes to the
   * system clock; components are required to realign
   * themselves with the system clock.  See the method
   * BComponent.clockChanged().
   *
   * @param target BComponent to invoke the
   *   action upon.
   * @param start absolute point in time in which to
   *    base interval times.  The start time should
   *    be at a point in the future, otherwise behavior
   *    is undefined.
   * @param period fixed interval over which to
   *    repeatedly invoke the specified action.
   * @param action the action to invoke on
   *    this schedule's component.
   * @param arg argument to pass to the action
   *    or null if no argument is required.
   * @return Ticket instance which may be used to
   *    cancel the scheduled action.
   * @throws IllegalArgumentException if start or period less than
   *   or equal to zero.
   */
  public static Ticket schedulePeriodically(BComponent target, BAbsTime start, BRelTime period, Action action, BValue arg)
  {
    return Nre.getEngineManager().schedulePeriodically(target, start, period, action, arg);
  }

////////////////////////////////////////////////////////////////
// Ticket
////////////////////////////////////////////////////////////////

  /**
   * Ticket is returned from the timer scheduling methods.
   * A ticket may be used to inspect the scheduled action
   * and also to cancel it.
   */
  public interface Ticket
  {
    /**
     * Cancel all scheduled action.  After this call the
     * Ticket is expired and should be discarded for garbage
     * collection.  This method may be called safely even
     * after the ticket has expired with no effect.
     */
    public void cancel();

    /**
     * Return false if there is an outstanding scheduled
     * action.  Return true is this is a ticket to an
     * expired one shot timer, or if the ticket has been
     * canceled.
     */
    public boolean isExpired();

    /**
     * Get the component this schedule invoke actions on.
     *
     * @throws ExpiredTicketException if the ticket has expired.
     */
    public BComponent getComponent();

    /**
     * Get the action which is scheduled to be invoked.
     *
     * @throws ExpiredTicketException if the ticket has expired.
     */
    public Action getAction();

    /**
     * Get the argument to the scheduled action.
     *
     * @throws ExpiredTicketException if the ticket has expired.
     */
    public BValue getActionArgument();
  }

////////////////////////////////////////////////////////////////
// ExpiredTicket - since Niagara 3.6
////////////////////////////////////////////////////////////////

  private static class ExpiredTicket implements Clock.Ticket
  {
    @Override
    public void cancel() {}
    @Override
    public boolean isExpired() { return true; }
    @Override
    public BComponent getComponent() { return null; }
    @Override
    public Action getAction() { return null; }
    @Override
    public BValue getActionArgument() { return null; }
    @Override
    public String toString() { return "Ticket: expired"; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Logger log = Logger.getLogger("sys.clock");

  private static final Object timeLock = new Object();
  private static long lastMillis;
  private static BAbsTime lastTime;

  public static final Ticket expiredTicket = new ExpiredTicket();
  
  //Cache an instance of the platform provider so that the access lookup check
  //happens only once. Clock will use that instance indefinitely for ticks() and nanos()
  //to prevent security check happening every time Clock.getTicks() or Clock.getNanos()
  //occurs.
  private static final class PlatformProviderHolder
  {
    public static final IPlatformProvider _PLATFORM_PROVIDER_INSTANCE = AccessController.doPrivileged((PrivilegedAction<IPlatformProvider>)PlatformUtil::getPlatformProvider);
  }
}
