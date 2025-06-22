/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.status.BStatusValue;
import javax.baja.sync.Transaction;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.BWeekday;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.schedule.ScheduleUtil;
import com.tridium.schedule.SimpleSortedSet;

/**
 * Skeletal schedule implementation.
 * <p>
 * <b>Always Effective Property</b><br>
 * Use this to "wild card" a schedule.  When true, no matter what, the
 * schedule in question will be effective.
 * <p>
 * <b>Effective Value Property</b><br>
 * The way to assign output to a schedule is to create the dynamic
 * property "effectiveValue."  When the root schedule is effective, the
 * effectiveValue of highest precedent effective descent becomes the
 * output of the entire schedule.
 * <p>
 * <b>Subclasses</b><br>
 * <ul>
 * <li>Subclass must implement isEffective(BAbsTime) and nextEvent(BAbsTime)</li>
 * <li>All non-BAbstractSchedule (or subclass) properties that
 * would require a master slave synchronization must have the
 * user_defined_1 flag set.</li>
 * </ul>
 * @creation Sept 2001
 * @version $Revision: 69$ $Date: 6/23/10 11:59:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "alwaysEffective",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1
)
public abstract class BAbstractSchedule
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BAbstractSchedule(4082448515)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alwaysEffective"

  /**
   * Slot for the {@code alwaysEffective} property.
   * @see #getAlwaysEffective
   * @see #setAlwaysEffective
   */
  public static final Property alwaysEffective = newProperty(Flags.USER_DEFINED_1, false, null);

  /**
   * Get the {@code alwaysEffective} property.
   * @see #alwaysEffective
   */
  public boolean getAlwaysEffective() { return getBoolean(alwaysEffective); }

  /**
   * Set the {@code alwaysEffective} property.
   * @see #alwaysEffective
   */
  public void setAlwaysEffective(boolean v) { setBoolean(alwaysEffective, v, null); }

  //endregion Property "alwaysEffective"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BAbstractSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public void added(Property p, Context c)
  {
    if (c != Context.decoding)
      trackModifications(p);
  }

  @Override
  public void changed(Property p, Context c)
  {
    if (c != Context.decoding)
    {
      trackModifications(p);
    }
  }

  /**
   * Use this to copy schedules.  Override to preserve certain
   * fields that shouldn't be copied over.  This makes new copies of all
   * objects being copied into this schedule.
   */
  public void copyFrom(BAbstractSchedule schedule, Context cx)
  {
    Context txn = Transaction.start(this, cx);
    Property[] props = getPropertiesArray();
    int i = props.length;
    while (--i >= 0)
    {
      if (props[i].isDynamic())
      {
        if (get(props[i]) instanceof BAbstractSchedule)
          remove(props[i],cx);
      }
    }
    SlotCursor<Property> c = schedule.getProperties();
    BValue o;
    Property p;
    while (c.next())
    {
      p = c.property();
      o = c.get();
      if ((o instanceof BAbstractSchedule) || Flags.isUserDefined1(schedule,p))
      {
        if (o instanceof BComplex)
          o = o.newCopy(true);
        if (get(p.getName()) != null)
          set(p,o,cx);
        else
          add(p.getName(),o,cx);
      }
    }
    try
    {
      Transaction.end(this, txn);
    }
    catch (Exception x)
    {
      throw new BajaRuntimeException(x);
    }
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("schedule.png");

  /**
   * If the dynamic property effectiveValue exists, its value is returned.
   */
  public BStatusValue getEffectiveValue()
  {
    return (BStatusValue) get(EFFECTIVE_VALUE);
  }

  /**
   * Convenience method which returns the effectiveValue property of the
   * component returned from the getOutputSource method.
   */
  public BStatusValue getOutput(BAbsTime at)
  {
    BAbstractSchedule s = getOutputSource(at);
    return (s == null) ? null : s.getEffectiveValue();
  }

  /**
   * Returns the schedule whose effectiveValue property should be used,
   * otherwise null.  The default implementation returns 'this' if effective
   * and has a property named "effectiveValue".
   */
  public BAbstractSchedule getOutputSource(BAbsTime at)
  {
    if ((getEffectiveValue() != null) && (isEffective(at)))
      return this;
    return null;
  }

  /**
   * Ascends parents until the topmost schedule is reached.
   */
  public BAbstractSchedule getRootSchedule()
  {
    BAbstractSchedule ret = null;
    BComplex cur = this;
    while (cur instanceof BAbstractSchedule)
    {
      ret = (BAbstractSchedule) cur;
      cur = cur.getParent();
    }
    return ret;
  }

  /**
   * Configuration convenience which returns this.
   * @return this
   */
  public BAbstractSchedule initAlwaysEffective(boolean value)
  {
    setAlwaysEffective(value);
    return this;
  }

  /**
   * Configuration convenience which returns this.
   * @return this
   */
  public BAbstractSchedule initEffectiveValue(BStatusValue o)
  {
    setEffectiveValue(o);
    return this;
  }

  /**
   * Whether or not the fields represented by the given date-time are
   * effective in this schedule.  Must return true if the
   * alwaysEffective property is true.
   * @param at A date-timestamp who represents some field (such as minute
   *             or hour) that the schedule measures to determine effectiveness.
   * @return True if effective.
   */
  public abstract boolean isEffective(BAbsTime at);

  /**
   * The next time the schedule TRANSITIONS into the given effective state.  If
   * the schedule is in the desired state at the from time, the returned
   * time will be after the schedule transitions out of and back into the
   * desired state. <p>
   * The query starts after the {@code after} time and ends at the
   * <code>to</code> time.  The {@code to} endpoint is to prevent
   * infinite queries for states that will never be reached, however the
   * {@code to} time is optional.
   * @param effective The desired state, true is effective.
   * @param after Exclusive and required.
   * @param to First excluded endpoint, optional.
   * @return The next time the schedule transitions into the desired state.
   */
  public BAbsTime next(boolean effective, BAbsTime after, BAbsTime to)
  {
    if (isEffective(after) == effective) //need to transition out
      after = next(!effective,after,to);
    if (after == null)
      return null;
    BAbsTime cur = nextEvent(after); //now find when we transition into
    while (cur != null)
    {
      if ((to != null) && cur.compareTo(to) >= 0)
        return null;
      if (isEffective(cur) == effective)
        return cur;
      cur = nextEvent(cur);
    }
    return null;
  }

  /**
   * The next time the source of the output value changes after
   * the given time. <p>
   * The query starts after the {@code after} time and ends at the
   * <code>to</code> time.  The {@code to} endpoint is to prevent
   * infinite queries for states that will never be reached, however the
   * {@code to} time is optional.
   * @param after Exclusive and required.
   * @param to Inclusive and optional endpoint.
   * @return Next schedule to provide output.
   */
  public BAbsTime nextOutputSource(BAbsTime after, BAbsTime to)
  {
    Object src = getOutputSource(after);
    BAbsTime cur = nextEvent(after);
    while (cur != null)
    {
      if ((to != null) && cur.isAfter(to))
        return null;
      if (getOutputSource(cur) != src)
        return cur;
      cur = nextEvent(cur);
    }
    return null;
  }

  /**
   * The next time after the given that the schedule MAY change state.  If
   * alwaysEffective, this must return null;
   * @param after Exclusive.
   * @return Null, or the next time the schedule may change state.
   */
  public abstract BAbsTime nextEvent(BAbsTime after);

  @Override
  public void removed(Property p, BValue v, Context c)
  {
    if (c != Context.decoding)
      trackModifications(p);
  }

  public void removed(Property p, Context c)
  {
    if (c != Context.decoding)
      trackModifications(p);
  }

  public void reorded(Property p, Context c)
  {
    if (c != Context.decoding)
      trackModifications(p);
  }

  /**
   * Creates the effectiveValue dynamic property.
   */
  public void setEffectiveValue(BStatusValue o)
  {
    if (get(EFFECTIVE_VALUE) == null)
      add(EFFECTIVE_VALUE,o, Flags.OPERATOR | Flags.USER_DEFINED_1);
    else
    {
      set(EFFECTIVE_VALUE, o);
    }
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected synchronized void addReference(BScheduleReference ref)
  {
    if (references == null)
    {
      references = new SimpleSortedSet()
      {
        @Override
        protected final int compare(Object o1, Object o2)
        {
          if (o1 == o2) return 0;
          int h1 = System.identityHashCode(o1);
          int h2 = System.identityHashCode(o2);
          if (h1 < h2)
            return -1;
          if (h1 > h2)
            return 1;
          //dup hashcodes are possible, so only use identity for equality
          return -1;
        }
      };
    }
    references.add(ref);
  }

  /**
   * Called when a change occurs that would require a remote synchronization.
   * At the component where the change occurs - this will be called if
   * the property is a schedule or it's slot flags include USER_DEFINED_1.
   * This method calls the same method on a parent schedule and
   * also calls modified() on any BScheduleReferences pointing to this.
   */
  protected void modified()
  {
    if (getParent() instanceof BAbstractSchedule)
      ((BAbstractSchedule)getParent()).modified();
    if (references != null)
    {
      SimpleSortedSet.Iterator i = references.iterator();
      while (i.hasNext())
      {
        ((BScheduleReference)i.next()).modified();
      }
    }
  }

  protected synchronized void removeReference(BScheduleReference ref)
  {
    if (references != null)
      references.remove(ref);
  }

  private void trackModifications(Property p)
  {
    if (p.getType().is(BAbstractSchedule.TYPE) || p.getName().equals( EFFECTIVE_VALUE) ||
      (getFlags(p) & Flags.USER_DEFINED_1) == Flags.USER_DEFINED_1)
    {
      modified();
    }
  }

  /**
   * Audit schedule changes after its been edited by a Scheduler
   *
   * @param scheduleCopy
              Schedule edits from Workbench Scheduler
   * @param cx
   *          User used for audit
   * @return always return true so we know the remote audit worked correctly.
   */
  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "w"
  )
  public boolean auditableCopyFrom(Object scheduleCopy, Context cx)
  {
    ScheduleUtil.stationSideAuditableCopyFrom(this, (BAbstractSchedule)scheduleCopy, cx);
    return true;
  }

  /**
   * Used by BWeekOfMonth Schedule as a RPC to get the firstDayOfWeek of the station.
   *
   * @param obj not used, needed for RPC
   * @param cx not used, needed for RPC
   * @return
   */
  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "r"
  )
  public BWeekday getFirstDayOfWeek(Object obj, Context cx)
  {
    return BWeekday.getFirstDayOfWeek(null);
  }

  /**
   * Name of the slot used to store the output value of a
   * schedule.
   */
  protected static final String EFFECTIVE_VALUE = "effectiveValue";


  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  private SimpleSortedSet references;
  static Logger log = Logger.getLogger("schedule");
  static Lexicon lex  = Lexicon.make(Sys.getModuleForClass(BAbstractSchedule.class).getModuleName());


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BAbstractSchedule
