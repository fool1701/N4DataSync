/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.metrics.Metrics;
import com.tridium.sys.schema.Fw;

/**
 * A container of schedules. <p>
 * <b>Union Property</b><br>
 * Determines how effectiveness is calculated for a group schedules.  True
 * means any of the schedules have to be effective, false means they all
 * have to be effective.  It may be easier to think of this property
 * in terms of intersection (the opposite of union).
 *
 * @author Aaron Hansen
 * @version $Revision: 21$ $Date: 5/3/05 5:26:49 PM EDT$
 * @creation Sept 2001
 */
@NiagaraType
/*
 Status of the device.  This property should
 never be set directly.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a short message why the schedule is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.USER_DEFINED_1
)
public class BCompositeSchedule
    extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BCompositeSchedule(3586398590)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the device.  This property should
   * never be set directly.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a short message why the schedule is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a short message why the schedule is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a short message why the schedule is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.USER_DEFINED_1, true, null);

  /**
   * Get the {@code union} property.
   * @see #union
   */
  public boolean getUnion() { return getBoolean(union); }

  /**
   * Set the {@code union} property.
   * @see #union
   */
  public void setUnion(boolean v) { setBoolean(union, v, null); }

  //endregion Property "union"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCompositeSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BCompositeSchedule()
  {
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public void add(BAbstractSchedule sch)
  {
    String name = "sch";
    int i = 0;
    while (get(name + i) != null)
      i++;
    add((name) + i, sch);
  }

  @Override
  public BAbstractSchedule getOutputSource(BAbsTime at)
  {
    if (!isEffective(at))
      return null;
    if (getEffectiveValue() != null)
      return this;
    BAbstractSchedule[] children = getSchedules();
    int len = children.length;
    BAbstractSchedule out;
    for (int i = 0; i < len; i++)
    {
      out = children[i].getOutputSource(at);
      if (out != null)
        return out;
    }
    return null;
  }

  /**
   * Configuration convenience.
   *
   * @return this
   */
  public BCompositeSchedule initUnion(boolean value)
  {
    setUnion(value);
    return this;
  }

  @Override
  public boolean isEffective(BAbsTime at)
  {
    if (fatalFault) return false;

    if (getAlwaysEffective())
      return true;
    boolean val;
    BAbstractSchedule[] children = getSchedules();
    int i = children.length;
    boolean younion = getUnion();
    boolean effective = true;
    if (younion)
      effective = false;
    while (--i >= 0)
    {
      val = children[i].isEffective(at);
      if (younion)
      {
        effective = (effective || val);
        if (effective)
          return true;
      }
      else
      {
        effective = (effective && val);
      }
    }
    return effective;
  }

  /**
   * Clears the cache of schedule children.
   */
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.STARTED:
        fwStarted();
        break;

      case Fw.CHANGED:
        if (get((Property)a) instanceof BAbstractSchedule)
          cache = null;
        break;

      case Fw.ADDED:
      case Fw.REMOVED:
      case Fw.REORDERED:
        cache = null;
        break;

      case Fw.STOPPED:
        cache = null;
        break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    if (!Metrics.incrementSchedule(this))
    {
      fatalFault = true;
      BAbstractSchedule.log.severe("Exceeded schedule limit for globalCapacity");
      setFaultCause("Exceeded schedule limit for globalCapacity");
      setStatus(BStatus.fault);

      // Set out property to fault as well -- if there is one,
      // which is generally the case.
      BValue out = get("out");
      if ((out instanceof BStatusBoolean))
        ((BStatusBoolean)out).setStatusFault(true);
    }
  }

  /**
   * All descendants who subclass BAbstractSchedule, frozen and dynamic.
   */
  public BAbstractSchedule[] getSchedules()
  {
    if (cache != null) return cache;
    BAbstractSchedule[] ret = null;
    SlotCursor<Property> c = loadSlots().getProperties();
    int count = 0;
    BObject o;
    while (c.next())
    {
      o = c.get();
      if (o instanceof BAbstractSchedule)
      {
        if (ret == null)
          ret = new BAbstractSchedule[hint];
        ret[count] = (BAbstractSchedule)o;
        if (++count == hint)
        { //grow
          hint += 20;
          BAbstractSchedule[] tmp = new BAbstractSchedule[hint];
          System.arraycopy(ret, 0, tmp, 0, count);
          ret = tmp;
        }
      }
    }
    if (count == 0)
    {
      cache = new BAbstractSchedule[0];
      return cache;
    }
    if (ret.length != count)
    { //trim
      BAbstractSchedule[] tmp = new BAbstractSchedule[count];
      System.arraycopy(ret, 0, tmp, 0, count);
      ret = tmp;
    }
    if (count > 0)
      hint = count; //optimize next call
    cache = ret;
    return ret;
  }

  @Override
  public BAbsTime nextEvent(BAbsTime after)
  {
    if (fatalFault) return null;

    BAbstractSchedule[] children = getSchedules();
    if (children.length == 0)
      return null;
    boolean intersection = !getUnion();
    boolean effective = false;
    int len = children.length;
    BAbsTime soonest = null;
    BAbsTime tmp = null;
    for (int i = 0; i < len; i++)
    {
      tmp = children[i].nextEvent(after);
      if (tmp == null)
      {
        effective = children[i].isEffective(after);
        if (intersection)
        {
          //if child is not effective, will never change and the schedule
          //is intersecting; then there will never be another cov event.
          if (!effective)
            return null;
        }
        else
        {
          //if child is effective, will never change and the schedule
          //is a union; then there will never be another cov event.
          if (effective)
            return null;
        }
      }
      else
      {
        if (soonest == null)
          soonest = tmp;
        else if (tmp.compareTo(soonest) < 0)
          soonest = tmp;
      }
    }
    return soonest;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  private BAbstractSchedule[] cache = null;
  private int hint = 20;

  private boolean fatalFault;


  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BCompositeSchedule
