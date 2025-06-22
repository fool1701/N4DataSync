/*
 * Copyright 2002 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * @author Aaron Hansen
 * @creation Aug 2002
 * @version $Revision: 13$ $Date: 11/30/05 3:14:17 PM EST$
 */
@NiagaraType
@NiagaraProperty(
  name = "alwaysEffective",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1 | Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.HIDDEN | Flags.TRANSIENT,
  override = true
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.TRUE_TEXT,BString.make(\"true\"), BFacets.FALSE_TEXT,BString.make(\"false\"))",
  flags = Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "union",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "in",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "nextTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "nextValue",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT
)
public class BCalendarSchedule
  extends BControlSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BCalendarSchedule(3677678181)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alwaysEffective"

  /**
   * Slot for the {@code alwaysEffective} property.
   * @see #getAlwaysEffective
   * @see #setAlwaysEffective
   */
  public static final Property alwaysEffective = newProperty(Flags.USER_DEFINED_1 | Flags.HIDDEN, false, null);

  //endregion Property "alwaysEffective"

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.HIDDEN | Flags.TRANSIENT, new BStatusBoolean(false), null);

  //endregion Property "defaultOutput"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.USER_DEFINED_1, BFacets.make(BFacets.TRUE_TEXT,BString.make("true"), BFacets.FALSE_TEXT,BString.make("false")), null);

  //endregion Property "facets"

  //region Property "union"

  /**
   * Slot for the {@code union} property.
   * @see #getUnion
   * @see #setUnion
   */
  public static final Property union = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.USER_DEFINED_1, true, null);

  //endregion Property "union"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY | Flags.USER_DEFINED_1, new BStatusBoolean(false, BStatus.nullStatus), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusBoolean getIn() { return (BStatusBoolean)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusBoolean v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(false), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusBoolean getOut() { return (BStatusBoolean)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusBoolean v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "nextTime"

  /**
   * Slot for the {@code nextTime} property.
   * @see #getNextTime
   * @see #setNextTime
   */
  public static final Property nextTime = newProperty(Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT, BAbsTime.NULL, null);

  /**
   * Get the {@code nextTime} property.
   * @see #nextTime
   */
  public BAbsTime getNextTime() { return (BAbsTime)get(nextTime); }

  /**
   * Set the {@code nextTime} property.
   * @see #nextTime
   */
  public void setNextTime(BAbsTime v) { set(nextTime, v, null); }

  //endregion Property "nextTime"

  //region Property "nextValue"

  /**
   * Slot for the {@code nextValue} property.
   * @see #getNextValue
   * @see #setNextValue
   */
  public static final Property nextValue = newProperty(Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT, new BStatusBoolean(false), null);

  /**
   * Get the {@code nextValue} property.
   * @see #nextValue
   */
  public BStatusBoolean getNextValue() { return (BStatusBoolean)get(nextValue); }

  /**
   * Set the {@code nextValue} property.
   * @see #nextValue
   */
  public void setNextValue(BStatusBoolean v) { set(nextValue, v, null); }

  //endregion Property "nextValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCalendarSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BCalendarSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * Removes expired events.
   */
  @Override
  public void doCleanup()
  {
    BAbstractSchedule[] ary = getSchedules();
    for (int i = ary.length; --i >= 0; )
    {
      if (isExpired(ary[i]))
      {
        //Issue 21891, grab name before removing event
        String name = ary[i].getName();
        remove(ary[i]);
        log.info(toPathString() + " removing expired event  " + name);
      }
    }
  }

  /**
   * Returns the defaultOutput if there is no scheduled output.
   */
  @Override
  public BStatusValue getOutput(BAbsTime at)
  {
    return new BStatusBoolean(isEffective(at));
  }


  @Override
  public BAbstractSchedule getOutputSource(BAbsTime at)
  {
    BAbstractSchedule[] children = getSchedules();
    int len = children.length;
    for (int i = 0; i < len; i++)
    {
      if (children[i].isEffective(at))
        return children[i];
    }
    return this;
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  protected BStatusValue getOutput(BAbstractSchedule sch)
  {
    if (sch == this)
      return new BStatusBoolean(false);
    return new BStatusBoolean(true);
  }

  @Override
  protected void setNextVal(BStatusValue v)
  {
    setNextValue((BStatusBoolean)v);
  }

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

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BCalendarSchedule
