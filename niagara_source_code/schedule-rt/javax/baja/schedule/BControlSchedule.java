/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.schedule.*;
import com.tridium.sys.metrics.IMetricResource;

/**
 * Schedule that provides continuous output.<p>
 * <b>Default Output</b><br>
 * This is the output when the schedule is ineffective.<p>
 * <b>Subclassing</b>
 * <ul>
 * <li>Define the "in" and "out" properties</li>
 * <li>Set the default output</li>
 * <li>Add child schedules</li>
 * </ul>
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 52$ $Date: 5/17/11 3:36:04 PM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "alwaysEffective",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.HIDDEN | Flags.OPERATOR | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "cleanupExpiredEvents",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1
)
/*
 Limits how far into the future to search for the next change.
 */
@NiagaraProperty(
  name = "scanLimit",
  type = "BRelTime",
  defaultValue = "Chronometer._90_DAYS",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  facets = @Facet("BFacets.make(\"showDay\", BBoolean.TRUE, BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.makeHours(24))")
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.NULL",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "lastModified",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.NO_AUDIT
)
@NiagaraAction(
  name = "cleanup",
  flags = Flags.ASYNC
)
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public abstract class BControlSchedule
  extends BCompositeSchedule
  implements BIStatusValue, IMetricResource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BControlSchedule(1280790451)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alwaysEffective"

  /**
   * Slot for the {@code alwaysEffective} property.
   * @see #getAlwaysEffective
   * @see #setAlwaysEffective
   */
  public static final Property alwaysEffective = newProperty(Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN, false, null);

  //endregion Property "alwaysEffective"

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1 | Flags.OPERATOR | Flags.HIDDEN, false, null);

  //endregion Property "union"

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.HIDDEN | Flags.OPERATOR | Flags.USER_DEFINED_1, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code defaultOutput} property.
   * @see #defaultOutput
   */
  public BStatusValue getDefaultOutput() { return (BStatusValue)get(defaultOutput); }

  /**
   * Set the {@code defaultOutput} property.
   * @see #defaultOutput
   */
  public void setDefaultOutput(BStatusValue v) { set(defaultOutput, v, null); }

  //endregion Property "defaultOutput"

  //region Property "cleanupExpiredEvents"

  /**
   * Slot for the {@code cleanupExpiredEvents} property.
   * @see #getCleanupExpiredEvents
   * @see #setCleanupExpiredEvents
   */
  public static final Property cleanupExpiredEvents = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, true, null);

  /**
   * Get the {@code cleanupExpiredEvents} property.
   * @see #cleanupExpiredEvents
   */
  public boolean getCleanupExpiredEvents() { return getBoolean(cleanupExpiredEvents); }

  /**
   * Set the {@code cleanupExpiredEvents} property.
   * @see #cleanupExpiredEvents
   */
  public void setCleanupExpiredEvents(boolean v) { setBoolean(cleanupExpiredEvents, v, null); }

  //endregion Property "cleanupExpiredEvents"

  //region Property "scanLimit"

  /**
   * Slot for the {@code scanLimit} property.
   * Limits how far into the future to search for the next change.
   * @see #getScanLimit
   * @see #setScanLimit
   */
  public static final Property scanLimit = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, Chronometer._90_DAYS, BFacets.make("showDay", BBoolean.TRUE, BFacets.SHOW_SECONDS, BBoolean.FALSE, BFacets.MIN, BRelTime.makeHours(24)));

  /**
   * Get the {@code scanLimit} property.
   * Limits how far into the future to search for the next change.
   * @see #scanLimit
   */
  public BRelTime getScanLimit() { return (BRelTime)get(scanLimit); }

  /**
   * Set the {@code scanLimit} property.
   * Limits how far into the future to search for the next change.
   * @see #scanLimit
   */
  public void setScanLimit(BRelTime v) { set(scanLimit, v, null); }

  //endregion Property "scanLimit"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, BFacets.NULL, null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "lastModified"

  /**
   * Slot for the {@code lastModified} property.
   * @see #getLastModified
   * @see #setLastModified
   */
  public static final Property lastModified = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.NO_AUDIT, BAbsTime.NULL, null);

  /**
   * Get the {@code lastModified} property.
   * @see #lastModified
   */
  public BAbsTime getLastModified() { return (BAbsTime)get(lastModified); }

  /**
   * Set the {@code lastModified} property.
   * @see #lastModified
   */
  public void setLastModified(BAbsTime v) { set(lastModified, v, null); }

  //endregion Property "lastModified"

  //region Action "cleanup"

  /**
   * Slot for the {@code cleanup} action.
   * @see #cleanup()
   */
  public static final Action cleanup = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code cleanup} action.
   * @see #cleanup
   */
  public void cleanup() { invoke(cleanup, null, null); }

  //endregion Action "cleanup"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code execute} action.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BControlSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BControlSchedule() {}

  public BControlSchedule(BStatusValue defaultOut)
  {
    setDefaultOutput(defaultOut);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public void clockChanged(BRelTime shift)
    throws Exception
  {
    super.clockChanged(shift);
    execute();
  }

  /**
   * Callback for deleting expired schedules.
   */
  public void doCleanup() {}

  /**
   * Sets the value of the OUT property and schedules callbacks to this
   * method with a javax.baja.sys.Scheduler.
   */
  public void doExecute()
  {
    if (ticket != null)
      ticket.cancel();
    if (!isRunning() || !isMounted())
      return;
    BAbsTime now = Clock.time();
    BStatusValue valNow = null;
    BStatusValue in = (BStatusValue) get(IN);
    if (!in.getStatus().isNull())
    {
      valNow = (BStatusValue) get(getProperty(IN));
      currentOutputSourceIsInput();
    }
    if (valNow == null)
    {
      BAbstractSchedule src = getOutputSource(now);
      currentOutputSource(src);
      valNow = getOutput(src);
    }
    BStatusValue cur = (BStatusValue) get(getProperty(OUT));
    if (!cur.equivalent(valNow))
      set(getProperty(OUT),valNow.newCopy(true));
    BAbsTime nxt = nextEvent(now);
    if (nxt != null)
      ticket = Clock.schedule(this,nxt,execute,null);
    else //play it safe, re-calc in a day
      ticket = Clock.schedule(this,now.add(BRelTime.DAY),execute,null);
    nxt = nextCov(now);
    if (nxt == null)
    {
      setNextTime(BAbsTime.NULL);
      setNextVal((BStatusValue)valNow.newCopy(true));
    }
    else
    {
      setNextTime(nxt);
      setNextVal((BStatusValue)getOutput(nxt).newCopy(true));
    }
    if (getCleanupExpiredEvents())
      cleanup();
  }

  /**
   * Returns the defaultOutput if there is no scheduled output.
   */
  @Override
  public BStatusValue getOutput(BAbsTime at)
  {
    BAbstractSchedule s = getOutputSource(at);
    if (s == this)
      return getDefaultOutput();
    return s.getEffectiveValue();
  }

  /**
   * Will return this if the default output is going to be used.
   */
  @Override
  public BAbstractSchedule getOutputSource(BAbsTime at)
  {
    BAbstractSchedule s = super.getOutputSource(at);
    return (s == null) ? this : s;
  }

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.isProperty())
    {
      if (get(slot.asProperty()) instanceof BStatusValue)
        return getFacets();
    }
    return super.getSlotFacets(slot);
  }

  @Override
  public BStatus getStatus()
  {
    return getStatusValue().getStatus();
  }

  @Override
  public BStatusValue getStatusValue()
  {
    return (BStatusValue) get(OUT);
  }

  @Override
  public BFacets getStatusValueFacets()
  {
    return getFacets();
  }

  /**
   * Configuration convenience.
   * @return this
   */
  public BControlSchedule initDefaultOutput(BStatusValue o)
  {
    setDefaultOutput(o);
    return this;
  }

  /**
   * Always true.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return true;
  }

  /**
   * Scans for up to 90 days for the next change of value, starting
   * at the current time.
   */
  public BAbsTime nextCov()
  {
    return nextCov(BAbsTime.now());
  }

  /**
   * Scans for up to 90 days for the next change of value.
   */
  public BAbsTime nextCov(BAbsTime after)
  {
    BValue startVal = getOutput(after);
    BValue curVal;
    BAbsTime time = nextEvent(after);
    BAbsTime end = after.add(getScanLimit());
    while ((time != null) && !time.isAfter(end))
    {
      curVal = getOutput(time);
      if (!curVal.equivalent(startVal))
        return time;
      time = nextEvent(time);
    }
    return time;
  }

  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    pool.enqueue(new Invocation(this,action,arg,cx));
    return null;
  }

  @Override
  public void started()
    throws Exception
  {
    super.started();
    execute();
  }

  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();
    if (ticket != null)
      ticket.cancel();
  }

  @Override
  public String toString(Context cx)
  {
    return propertyValueToString(getProperty(OUT),cx);
  }

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toBottom("webChart:ChartWidget");
    return list;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected void currentOutputSource(BAbstractSchedule sch) {}
  protected void currentOutputSourceIsInput() {}

  protected boolean isExpired(BAbstractSchedule sch)
  {
    if (sch instanceof BDateSchedule)
    {
      return isExpired((BDateSchedule)sch);
    }
    if (sch instanceof BDateRangeSchedule)
    {
      BDateRangeSchedule dr = (BDateRangeSchedule) sch;
      return isExpired(dr.getEnd());
    }
    return false;
  }

  protected BStatusValue getOutput(BAbstractSchedule sch)
  {
    if (sch == this)
      return getDefaultOutput();
    return sch.getEffectiveValue();
  }

  /**
   * Increments the modification property.
   */
  @Override
  protected void modified()
  {
    setLastModified(Clock.time());
    execute();
    super.modified();
  }

  protected abstract void setNextTime(BAbsTime t);
  protected abstract void setNextVal(BStatusValue v);


  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  static BCompositeSchedule makeOverridableWeekly()
  {
    BCompositeSchedule retern = new BCompositeSchedule();
    retern.add("overrides", new BCompositeSchedule());
    retern.add("weekly", new BWeekSchedule());
    return retern;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  private boolean isExpired(BDateSchedule sch)
  {
    BAbsTime now = Clock.time();
    //check year
    int schTmp = sch.getYear();
    int nowTmp = now.getYear();
    if (schTmp == -1)
      return false;
    if (schTmp > nowTmp)
      return false;
    if (schTmp < nowTmp)
      return true;
    //years are equal, now check month
    schTmp = sch.getMonth();
    nowTmp = now.getMonth().getOrdinal();
    if (schTmp == -1)
      return false;
    if (schTmp > nowTmp)
      return false;
    if (schTmp < nowTmp)
      return true;
    //months are equal, now check days
    schTmp = sch.getDay();
    nowTmp = now.getDay();
    if (schTmp == -1)
      return false;
    if (schTmp > nowTmp)
      return false;
    if (schTmp < nowTmp)
      return true;
    //days are equal
    return false;
  }

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "w"
  )
  @Override
  public boolean auditableCopyFrom(Object scheduleCopy, Context cx)
  {
    super.auditableCopyFrom(scheduleCopy, cx);
    execute();
    return true;
  }

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /**
   * Name of the input property - "in".
   */
  public static final String IN = "in";

  /**
   * Name of the output property - "out".
   */
  public static final String OUT = "out";


  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  static ExecutionQueue pool = new ExecutionQueue("Schedule:Execution",true);
  protected Clock.Ticket ticket = null;


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BControlSchedule
