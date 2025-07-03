/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPollScheduler is used to configure and manage a group
 * of BComponents to be polled.  The BPollScheduler provides
 * a flexible polling algorthim based on four "buckets".
 * <p>
 * Each BIPollable assigned to a BPollScheduler is configured
 * in one of three buckets: fast, normal, or slow.  In addition
 * there is a forth bucket called the "dibs stack".  Whenever
 * a BIPollable is subscribed it immediately gets "first dibs"
 * and goes on the top of the dibs stack.  The poll scheduler
 * always polls the dibs before doing anything else.  The dibs
 * stack is polled last-in, first-out (LIFO).  As long as entries
 * are in the dibs stack they are polled as fast as possible
 * with no artificial delays.
 * <p>
 * When the dibs stack is empty the scheduler attempts to
 * poll the components in each bucket using an algorthim
 * designed to create uniform network traffic.  For example
 * if the fast rate is configured to 10sec and there are 5
 * components currently subscribed in the fast bucket, then
 * the scheduler will attempt to poll one component every 2sec.
 * <p>
 * Every ten seconds the poll scheduler rechecks the buckets
 * for configuration changes.  So if a BIPollable's configuration
 * is changed from slow to fast, it takes at most ten seconds
 * for the change to take effect.  Statistics are also updated
 * every ten seconds.  Statistics may be manually reset using
 * the resetStatistics action.
 *
 * @author    Brian Frank
 * @creation  21 Jan 02
 * @version   $Revision: 26$ $Date: 4/14/11 9:38:34 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The frequency used to poll components set to fast.
 */
@NiagaraProperty(
  name = "fastRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(1000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
/*
 The frequency used to poll components set to normal.
 */
@NiagaraProperty(
  name = "normalRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(5000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
/*
 The frequency used to poll components set to slow.
 */
@NiagaraProperty(
  name = "slowRate",
  type = "BRelTime",
  defaultValue = "BRelTime.make(30000)",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
/*
 Last reset time of statistics.
 */
@NiagaraProperty(
  name = "statisticsStart",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Average time spent in each poll.
 */
@NiagaraProperty(
  name = "averagePoll",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Percentage of time spent busy doing polls.
 */
@NiagaraProperty(
  name = "busyTime",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Total number of polls made and time
 spent waiting for polls to execute.
 */
@NiagaraProperty(
  name = "totalPolls",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Total number of polls made processing the dibs stack.
 */
@NiagaraProperty(
  name = "dibsPolls",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Total number of polls made processing fast queue.
 */
@NiagaraProperty(
  name = "fastPolls",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Total number of polls made processing normal queue.
 */
@NiagaraProperty(
  name = "normalPolls",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Total number of polls made processing slow queue.
 */
@NiagaraProperty(
  name = "slowPolls",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Current and average number of components in dibs stack.
 */
@NiagaraProperty(
  name = "dibsCount",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Current and average number of components in fast queue.
 */
@NiagaraProperty(
  name = "fastCount",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Current and average number of components in normal queue.
 */
@NiagaraProperty(
  name = "normalCount",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Current and average number of components in slow queue.
 */
@NiagaraProperty(
  name = "slowCount",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Average cycle time of the fast queue.
 */
@NiagaraProperty(
  name = "fastCycleTime",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Average cycle time of the normal queue.
 */
@NiagaraProperty(
  name = "normalCycleTime",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Average cycle time of the slow queue.
 */
@NiagaraProperty(
  name = "slowCycleTime",
  type = "String",
  defaultValue = "-",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Reset all the statistics properties.
 */
@NiagaraAction(
  name = "resetStatistics",
  flags = Flags.CONFIRM_REQUIRED
)
public abstract class BPollScheduler
  extends BAbstractPollService
  implements Runnable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BPollScheduler(1843394388)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fastRate"

  /**
   * Slot for the {@code fastRate} property.
   * The frequency used to poll components set to fast.
   * @see #getFastRate
   * @see #setFastRate
   */
  public static final Property fastRate = newProperty(0, BRelTime.make(1000), BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code fastRate} property.
   * The frequency used to poll components set to fast.
   * @see #fastRate
   */
  public BRelTime getFastRate() { return (BRelTime)get(fastRate); }

  /**
   * Set the {@code fastRate} property.
   * The frequency used to poll components set to fast.
   * @see #fastRate
   */
  public void setFastRate(BRelTime v) { set(fastRate, v, null); }

  //endregion Property "fastRate"

  //region Property "normalRate"

  /**
   * Slot for the {@code normalRate} property.
   * The frequency used to poll components set to normal.
   * @see #getNormalRate
   * @see #setNormalRate
   */
  public static final Property normalRate = newProperty(0, BRelTime.make(5000), BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code normalRate} property.
   * The frequency used to poll components set to normal.
   * @see #normalRate
   */
  public BRelTime getNormalRate() { return (BRelTime)get(normalRate); }

  /**
   * Set the {@code normalRate} property.
   * The frequency used to poll components set to normal.
   * @see #normalRate
   */
  public void setNormalRate(BRelTime v) { set(normalRate, v, null); }

  //endregion Property "normalRate"

  //region Property "slowRate"

  /**
   * Slot for the {@code slowRate} property.
   * The frequency used to poll components set to slow.
   * @see #getSlowRate
   * @see #setSlowRate
   */
  public static final Property slowRate = newProperty(0, BRelTime.make(30000), BFacets.make(BFacets.MIN, BRelTime.make(1), BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code slowRate} property.
   * The frequency used to poll components set to slow.
   * @see #slowRate
   */
  public BRelTime getSlowRate() { return (BRelTime)get(slowRate); }

  /**
   * Set the {@code slowRate} property.
   * The frequency used to poll components set to slow.
   * @see #slowRate
   */
  public void setSlowRate(BRelTime v) { set(slowRate, v, null); }

  //endregion Property "slowRate"

  //region Property "statisticsStart"

  /**
   * Slot for the {@code statisticsStart} property.
   * Last reset time of statistics.
   * @see #getStatisticsStart
   * @see #setStatisticsStart
   */
  public static final Property statisticsStart = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code statisticsStart} property.
   * Last reset time of statistics.
   * @see #statisticsStart
   */
  public BAbsTime getStatisticsStart() { return (BAbsTime)get(statisticsStart); }

  /**
   * Set the {@code statisticsStart} property.
   * Last reset time of statistics.
   * @see #statisticsStart
   */
  public void setStatisticsStart(BAbsTime v) { set(statisticsStart, v, null); }

  //endregion Property "statisticsStart"

  //region Property "averagePoll"

  /**
   * Slot for the {@code averagePoll} property.
   * Average time spent in each poll.
   * @see #getAveragePoll
   * @see #setAveragePoll
   */
  public static final Property averagePoll = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code averagePoll} property.
   * Average time spent in each poll.
   * @see #averagePoll
   */
  public String getAveragePoll() { return getString(averagePoll); }

  /**
   * Set the {@code averagePoll} property.
   * Average time spent in each poll.
   * @see #averagePoll
   */
  public void setAveragePoll(String v) { setString(averagePoll, v, null); }

  //endregion Property "averagePoll"

  //region Property "busyTime"

  /**
   * Slot for the {@code busyTime} property.
   * Percentage of time spent busy doing polls.
   * @see #getBusyTime
   * @see #setBusyTime
   */
  public static final Property busyTime = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code busyTime} property.
   * Percentage of time spent busy doing polls.
   * @see #busyTime
   */
  public String getBusyTime() { return getString(busyTime); }

  /**
   * Set the {@code busyTime} property.
   * Percentage of time spent busy doing polls.
   * @see #busyTime
   */
  public void setBusyTime(String v) { setString(busyTime, v, null); }

  //endregion Property "busyTime"

  //region Property "totalPolls"

  /**
   * Slot for the {@code totalPolls} property.
   * Total number of polls made and time
   * spent waiting for polls to execute.
   * @see #getTotalPolls
   * @see #setTotalPolls
   */
  public static final Property totalPolls = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code totalPolls} property.
   * Total number of polls made and time
   * spent waiting for polls to execute.
   * @see #totalPolls
   */
  public String getTotalPolls() { return getString(totalPolls); }

  /**
   * Set the {@code totalPolls} property.
   * Total number of polls made and time
   * spent waiting for polls to execute.
   * @see #totalPolls
   */
  public void setTotalPolls(String v) { setString(totalPolls, v, null); }

  //endregion Property "totalPolls"

  //region Property "dibsPolls"

  /**
   * Slot for the {@code dibsPolls} property.
   * Total number of polls made processing the dibs stack.
   * @see #getDibsPolls
   * @see #setDibsPolls
   */
  public static final Property dibsPolls = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code dibsPolls} property.
   * Total number of polls made processing the dibs stack.
   * @see #dibsPolls
   */
  public String getDibsPolls() { return getString(dibsPolls); }

  /**
   * Set the {@code dibsPolls} property.
   * Total number of polls made processing the dibs stack.
   * @see #dibsPolls
   */
  public void setDibsPolls(String v) { setString(dibsPolls, v, null); }

  //endregion Property "dibsPolls"

  //region Property "fastPolls"

  /**
   * Slot for the {@code fastPolls} property.
   * Total number of polls made processing fast queue.
   * @see #getFastPolls
   * @see #setFastPolls
   */
  public static final Property fastPolls = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code fastPolls} property.
   * Total number of polls made processing fast queue.
   * @see #fastPolls
   */
  public String getFastPolls() { return getString(fastPolls); }

  /**
   * Set the {@code fastPolls} property.
   * Total number of polls made processing fast queue.
   * @see #fastPolls
   */
  public void setFastPolls(String v) { setString(fastPolls, v, null); }

  //endregion Property "fastPolls"

  //region Property "normalPolls"

  /**
   * Slot for the {@code normalPolls} property.
   * Total number of polls made processing normal queue.
   * @see #getNormalPolls
   * @see #setNormalPolls
   */
  public static final Property normalPolls = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code normalPolls} property.
   * Total number of polls made processing normal queue.
   * @see #normalPolls
   */
  public String getNormalPolls() { return getString(normalPolls); }

  /**
   * Set the {@code normalPolls} property.
   * Total number of polls made processing normal queue.
   * @see #normalPolls
   */
  public void setNormalPolls(String v) { setString(normalPolls, v, null); }

  //endregion Property "normalPolls"

  //region Property "slowPolls"

  /**
   * Slot for the {@code slowPolls} property.
   * Total number of polls made processing slow queue.
   * @see #getSlowPolls
   * @see #setSlowPolls
   */
  public static final Property slowPolls = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code slowPolls} property.
   * Total number of polls made processing slow queue.
   * @see #slowPolls
   */
  public String getSlowPolls() { return getString(slowPolls); }

  /**
   * Set the {@code slowPolls} property.
   * Total number of polls made processing slow queue.
   * @see #slowPolls
   */
  public void setSlowPolls(String v) { setString(slowPolls, v, null); }

  //endregion Property "slowPolls"

  //region Property "dibsCount"

  /**
   * Slot for the {@code dibsCount} property.
   * Current and average number of components in dibs stack.
   * @see #getDibsCount
   * @see #setDibsCount
   */
  public static final Property dibsCount = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code dibsCount} property.
   * Current and average number of components in dibs stack.
   * @see #dibsCount
   */
  public String getDibsCount() { return getString(dibsCount); }

  /**
   * Set the {@code dibsCount} property.
   * Current and average number of components in dibs stack.
   * @see #dibsCount
   */
  public void setDibsCount(String v) { setString(dibsCount, v, null); }

  //endregion Property "dibsCount"

  //region Property "fastCount"

  /**
   * Slot for the {@code fastCount} property.
   * Current and average number of components in fast queue.
   * @see #getFastCount
   * @see #setFastCount
   */
  public static final Property fastCount = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code fastCount} property.
   * Current and average number of components in fast queue.
   * @see #fastCount
   */
  public String getFastCount() { return getString(fastCount); }

  /**
   * Set the {@code fastCount} property.
   * Current and average number of components in fast queue.
   * @see #fastCount
   */
  public void setFastCount(String v) { setString(fastCount, v, null); }

  //endregion Property "fastCount"

  //region Property "normalCount"

  /**
   * Slot for the {@code normalCount} property.
   * Current and average number of components in normal queue.
   * @see #getNormalCount
   * @see #setNormalCount
   */
  public static final Property normalCount = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code normalCount} property.
   * Current and average number of components in normal queue.
   * @see #normalCount
   */
  public String getNormalCount() { return getString(normalCount); }

  /**
   * Set the {@code normalCount} property.
   * Current and average number of components in normal queue.
   * @see #normalCount
   */
  public void setNormalCount(String v) { setString(normalCount, v, null); }

  //endregion Property "normalCount"

  //region Property "slowCount"

  /**
   * Slot for the {@code slowCount} property.
   * Current and average number of components in slow queue.
   * @see #getSlowCount
   * @see #setSlowCount
   */
  public static final Property slowCount = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code slowCount} property.
   * Current and average number of components in slow queue.
   * @see #slowCount
   */
  public String getSlowCount() { return getString(slowCount); }

  /**
   * Set the {@code slowCount} property.
   * Current and average number of components in slow queue.
   * @see #slowCount
   */
  public void setSlowCount(String v) { setString(slowCount, v, null); }

  //endregion Property "slowCount"

  //region Property "fastCycleTime"

  /**
   * Slot for the {@code fastCycleTime} property.
   * Average cycle time of the fast queue.
   * @see #getFastCycleTime
   * @see #setFastCycleTime
   */
  public static final Property fastCycleTime = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code fastCycleTime} property.
   * Average cycle time of the fast queue.
   * @see #fastCycleTime
   */
  public String getFastCycleTime() { return getString(fastCycleTime); }

  /**
   * Set the {@code fastCycleTime} property.
   * Average cycle time of the fast queue.
   * @see #fastCycleTime
   */
  public void setFastCycleTime(String v) { setString(fastCycleTime, v, null); }

  //endregion Property "fastCycleTime"

  //region Property "normalCycleTime"

  /**
   * Slot for the {@code normalCycleTime} property.
   * Average cycle time of the normal queue.
   * @see #getNormalCycleTime
   * @see #setNormalCycleTime
   */
  public static final Property normalCycleTime = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code normalCycleTime} property.
   * Average cycle time of the normal queue.
   * @see #normalCycleTime
   */
  public String getNormalCycleTime() { return getString(normalCycleTime); }

  /**
   * Set the {@code normalCycleTime} property.
   * Average cycle time of the normal queue.
   * @see #normalCycleTime
   */
  public void setNormalCycleTime(String v) { setString(normalCycleTime, v, null); }

  //endregion Property "normalCycleTime"

  //region Property "slowCycleTime"

  /**
   * Slot for the {@code slowCycleTime} property.
   * Average cycle time of the slow queue.
   * @see #getSlowCycleTime
   * @see #setSlowCycleTime
   */
  public static final Property slowCycleTime = newProperty(Flags.READONLY | Flags.TRANSIENT, "-", null);

  /**
   * Get the {@code slowCycleTime} property.
   * Average cycle time of the slow queue.
   * @see #slowCycleTime
   */
  public String getSlowCycleTime() { return getString(slowCycleTime); }

  /**
   * Set the {@code slowCycleTime} property.
   * Average cycle time of the slow queue.
   * @see #slowCycleTime
   */
  public void setSlowCycleTime(String v) { setString(slowCycleTime, v, null); }

  //endregion Property "slowCycleTime"

  //region Action "resetStatistics"

  /**
   * Slot for the {@code resetStatistics} action.
   * Reset all the statistics properties.
   * @see #resetStatistics()
   */
  public static final Action resetStatistics = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code resetStatistics} action.
   * Reset all the statistics properties.
   * @see #resetStatistics
   */
  public void resetStatistics() { invoke(resetStatistics, null, null); }

  //endregion Action "resetStatistics"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPollScheduler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Invoke the poll callback and log any errors.  We
   * also keep stats on the time taken for each poll.
   */
  public final void poll(BIPollable p)
  {
    long t1 = Clock.ticks();

    try
    {
      doPoll(p);
    }
    catch(Throwable e)
    {
      // TODO need a robust poll logging mechanism that
      // doesn't litter standard output with messages
      e.printStackTrace();
    }

    long t2 = Clock.ticks();
    totalPollCount += 1;
    totalPollTime += (t2 - t1);
    average = (double)totalPollTime / (double)totalPollCount;
  }

  /**
   * Poll the specified point synchronously, blocking
   * until polled successfully or an failure is encountered.
   * Failures should throw an exception.
   */
  public abstract void doPoll(BIPollable p)
    throws Exception;

////////////////////////////////////////////////////////////////
// Thread
////////////////////////////////////////////////////////////////

  /**
   * Default for started() is to call startThread().
   */
  public void started() { if (Sys.isStationStarted()) startThread(); }
  
  /**
   * Overridden starting in Niagara 3.4 in order to delay
   * the polling until after the station has started
   */
  public void stationStarted()
    throws Exception
  { 
    super.stationStarted(); 
    startThread(); 
  }

  /**
   * Default for stopped() is to call stopThread().
   */
  public void stopped() { stopThread(); }

  /**
   * Start the poll scheduler on its own thread.
   */
  public void startThread()
  {
    doResetStatistics();
    stopThread();
    isAlive = true;
    thread = new Thread(this, "Poll:" + getParent().getName());
    thread.start();
  }

  /**
   * Stop the poll scheduler thread.
   */
  public void stopThread()
  {
    isAlive = false;
    if (thread != null) thread.interrupt();
  }


  /**
   * Main loop of poll scheduler.
   */
  public void run()
  {
    long stats = Clock.ticks() + 10000;
    while(isAlive)
    {
      try
      {
        // Allow pollEnable to short circuit poll
        if(!getPollEnabled())
        {
          Thread.sleep(1000);
          continue;
        }

        // poll everything in the dibs queue
        pollDibs();

        // poll the next guy in our regular queues
        pollQueues();

        // sleep for a required bit of time
        long sleep = computeSleep();
        if (sleep > 0) Thread.sleep(sleep);

        // take stats every 10sec
        if (Clock.ticks() > stats)
        {
          checkBucketConfig();
          updateStats();
          stats = Clock.ticks() + 10000;
        }
      }
      catch(InterruptedException e)
      {
      }
      catch(Throwable e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Poll everything in the dibs queue immediately
   * in a tight loop.  We don't return until the
   * dibs queue is empty.
   */
  private void pollDibs()
  {
    while(true)
    {
      BIPollable next = null;
      synchronized(lock)
      {
        if (dibs.empty()) return;
        next = dibs.pop();
        dibsPollTotal++;
      }
      poll(next);
    }
  }

  /**
   * Poll the next guy on our standard queues.
   */
  private void pollQueues()
  {
    pollQueue(fast);
    pollQueue(norm);
    pollQueue(slow);
  }

  /**
   * Check if the deadline has been met for
   * polling the next guy in the specified bucket.
   */
  private void pollQueue(Bucket bucket)
  {
    // bail if we haven't met our deadline,
    // but allow a small fudge factor of 5ms
    if (bucket.nextTicks > Clock.ticks() + 5) return;

    // get the next guy in the list
    BIPollable p = null;
    int size = 0;
    synchronized(lock)
    {
      int index = bucket.index;
      size = bucket.q.size();
      if (size > 0)
      {
        if (index >= size) { index = 0; bucket.cycleTotal++; }
        p = bucket.q.get(index);
        bucket.index = index + 1;
      }
      else
      {
        bucket.cycleTotal++;
      }
    }

    // poll the little bastard
    long lastPollTime = 0;

    if (p != null)
    {
      long t1 = Clock.ticks();
      poll(p);
      lastPollTime = Clock.ticks() - t1;

      bucket.pollTotal++;
    }

    // compute our sleep time
    long rate = ((BRelTime)get(bucket.rateProp)).getMillis();
    long sleep = rate;
    if (size > 0)
    {
      double dRate = (double)rate;
      double dSize = (double)size;
      //sleep = (long)((dRate - dSize*average) / dSize);
      sleep = (long)((dRate - dSize*lastPollTime) / dSize);
    }
    else
      sleep = 1000;

    // set our next deadline
    bucket.nextTicks = Clock.ticks() + sleep;
  }

  /**
   * Compute the sleep time.
   */
  private long computeSleep()
  {
    long now = Clock.ticks();
    long sleep = 1000;
    sleep = Math.min(fast.nextTicks-now, sleep);
    sleep = Math.min(norm.nextTicks-now, sleep);
    sleep = Math.min(slow.nextTicks-now, sleep);
    return sleep;
  }

////////////////////////////////////////////////////////////////
// Bucket Config
////////////////////////////////////////////////////////////////

  /**
   * Every 10sec we check to make sure each pollable
   * is in its configured bucket.
   */
  private void checkBucketConfig()
  {
    synchronized(lock)
    {
      ArrayList<BIPollable> newFast = new ArrayList<>();
      ArrayList<BIPollable> newNorm = new ArrayList<>();
      ArrayList<BIPollable> newSlow = new ArrayList<>();

      reSort(fast.q, newFast, newNorm, newSlow);
      reSort(norm.q, newFast, newNorm, newSlow);
      reSort(slow.q, newFast, newNorm, newSlow);

      fast.q = newFast;
      norm.q = newNorm;
      slow.q = newSlow;
    }
  }

  /**
   * Resort the buckets.
   */
  private static void reSort(ArrayList<BIPollable> orig, ArrayList<BIPollable> newFast, ArrayList<BIPollable> newNorm, ArrayList<BIPollable> newSlow)
  {
    int size = orig.size();
    for(int i=0; i<size; ++i)
    {
      BIPollable p = orig.get(i);
      switch(p.getPollFrequency().getOrdinal())
      {
        case BPollFrequency.FAST:   newFast.add(p); break;
        case BPollFrequency.NORMAL: newNorm.add(p); break;
        case BPollFrequency.SLOW:   newSlow.add(p); break;
        default: throw new IllegalStateException();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Statistics
////////////////////////////////////////////////////////////////

  /**
   * Action implementation for resetStatistics().
   */
  public void doResetStatistics()
  {
    totalPollTime  = 0;
    totalPollCount = 0;
    average        = 0;
    statsCount     = 0;
    dibsPollTotal  = 0;
    dibsSizeTotal  = 0;
    fast.reset();
    norm.reset();
    slow.reset();
    start = Clock.ticks();
    setStatisticsStart(Clock.time());
    updateStats();
  }

  /**
   * Update all the statistics fields.
   */
  private void updateStats()
  {
    long now = Clock.ticks();
    long uptime = now - start;
    statsCount++;

    // average poll time
    setAveragePoll(timeFormat.format(average));

    // busy time
    if (totalPollTime > 0)
    {
      setBusyTime(""+(int)(100d * ((double)totalPollTime/(double)uptime)) +
        "% (" + duration(totalPollTime) + "/" + duration(uptime) + ")");
    }

    // total polls for each bucket
    setTotalPolls("" + count(totalPollCount) + " over " + duration(totalPollTime));
    setDibsPolls(toPollTotal(dibsPollTotal));
    setFastPolls(toPollTotal(fast.pollTotal));
    setNormalPolls(toPollTotal(norm.pollTotal));
    setSlowPolls(toPollTotal(slow.pollTotal));

    // current and average bucket counts
    setDibsCount(toCount(dibs.size(), dibsSizeTotal)); dibsSizeTotal += dibs.size();
    setFastCount(toCount(fast.q.size(), fast.sizeTotal)); fast.sizeTotal += fast.q.size();
    setNormalCount(toCount(norm.q.size(), norm.sizeTotal)); norm.sizeTotal += norm.q.size();
    setSlowCount(toCount(slow.q.size(), slow.sizeTotal)); slow.sizeTotal += slow.q.size();

    // cycle times
    setFastCycleTime(toCycle(fast, uptime));
    setNormalCycleTime(toCycle(norm, uptime));
    setSlowCycleTime(toCycle(slow, uptime));
  }

  /**
   * Get a string for poll totals.
   */
  private String toPollTotal(int bucketTotal)
  {
    int total = totalPollCount;

    StringBuilder s = new StringBuilder();
    if (total == 0)
      s.append('-');
    else
      s.append((int)(100d * (double)bucketTotal/(double)total));

    s.append("% (").append(count(bucketTotal)).append('/').append(count(total)).append(')');
    return s.toString();
  }

  /**
   * Get a string for current and average count
   */
  private String toCount(int current, int total)
  {
    return "current=" + current + " average=" + (total/statsCount);
  }

  /**
   * Get a string for cycle stats.
   */
  private String toCycle(Bucket bucket, long uptime)
  {
    int cycles = bucket.cycleTotal;
    if (cycles == 0) return "-";
    return "average = " + (uptime/cycles) + "ms";
  }

  private String count(int count)
  {
    if (count < 10000) return String.valueOf(count);
    else return String.valueOf(count/1000) + "k";
  }

  private String duration(long duration)
  {
    if (duration < 10000L) return String.valueOf(duration) + "ms";
    else return String.valueOf(duration/1000) + "sec";
  }

////////////////////////////////////////////////////////////////
// Subscriptions
////////////////////////////////////////////////////////////////

  /**
   * Subscribe the pollable and start polling it
   * until it is unsubscrbed.
   */
  public void subscribe(BIPollable p)
  {
    synchronized(lock)
    {
      // push it on the first dibs stack
      // to get polled immediately
      dibs.push(p);

      // add it to its configured queue
      switch(p.getPollFrequency().getOrdinal())
      {
        case BPollFrequency.FAST:   fast.q.add(p); break;
        case BPollFrequency.NORMAL: norm.q.add(p); break;
        case BPollFrequency.SLOW:   slow.q.add(p); break;
        default: throw new IllegalStateException();
      }
    }
  }

  /**
   * Unsubscribe the pollable and stop polling it.
   * @return true if the pollable point was subscribed,
   *          false if the point was not in any buckets.
   */
  public boolean unsubscribe(BIPollable p)
  {
    synchronized(lock)
    {
      // remove it from the dibs stack
      for(int i=0; i<dibs.size(); ++i)
        if (dibs.get(i) == p) { dibs.remove(i); break; }

      // remove it from the queues, just to
      // be safe we check all of them
      if (fast.remove(p)) return true;
      if (norm.remove(p)) return true;
      if (slow.remove(p)) return true;
      return false;
    }
  }


////////////////////////////////////////////////////////////////
// Bucket
////////////////////////////////////////////////////////////////

  static class Bucket
  {
    Bucket(Property rateProp)
    {
      this.q = new ArrayList<>();
      this.rateProp = rateProp;
    }

    boolean remove(BIPollable p)
    {
      int size = q.size();
      for(int i=0; i<size; ++i)
        if (q.get(i) == p) { q.remove(i); return true; }
      return false;
    }

    void reset()
    {
      index      = 0;
      nextTicks  = 0;
      pollTotal  = 0;
      sizeTotal  = 0;
      cycleTotal = 0;
    }

    Property rateProp;  // property for rate config
    ArrayList<BIPollable> q; // list of pollables
    int index;          // next index into queue
    long nextTicks;     // ticks of next poll
    int pollTotal;      // total number of polls
    int sizeTotal;      // total of q.size()
    int cycleTotal;     // total number of cycles completed
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final DecimalFormat timeFormat = new DecimalFormat("0.0#ms");

  boolean isAlive;              // thread alive flag
  Thread thread;                // poll thread
  long start;                   // ticks at start of poll thread
  long totalPollTime;           // total time spent in poll
  int totalPollCount;           // total number of polls
  double average;               // average time taken in each poll
  Stack<BIPollable> dibs = new Stack<>(); // first dips (those just subscribed)
  int dibsPollTotal;            // total polls on dibs
  int dibsSizeTotal;            // total size of dibs stack
  Object lock = new Object();   // synchronization monitor
  int statsCount;               // number of statistics updates

  Bucket fast = new Bucket(fastRate);
  Bucket norm = new Bucket(normalRate);
  Bucket slow = new Bucket(slowRate);

}
