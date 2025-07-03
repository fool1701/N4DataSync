/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * A string control schedule. <p>
 * <b>Input</b><br>
 * If the "in" property is non-null then this value over-rides the
 * scheduled output.
 * @author Aaron Hansen
 * @creation Aug 2003
 * @version $Revision: 13$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT
)
@NiagaraProperty(
  name = "in",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\", BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "nextTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
@NiagaraProperty(
  name = "nextValue",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
public class BStringSchedule
  extends BWeeklySchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BStringSchedule(1051204308)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, new BStatusString("", BStatus.nullStatus), null);

  //endregion Property "defaultOutput"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT, new BStatusString(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusString getOut() { return (BStatusString)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusString v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1, new BStatusString("", BStatus.nullStatus), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusString getIn() { return (BStatusString)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusString v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "nextTime"

  /**
   * Slot for the {@code nextTime} property.
   * @see #getNextTime
   * @see #setNextTime
   */
  public static final Property nextTime = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, BAbsTime.NULL, null);

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
  public static final Property nextValue = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, new BStatusString(""), null);

  /**
   * Get the {@code nextValue} property.
   * @see #nextValue
   */
  public BStatusString getNextValue() { return (BStatusString)get(nextValue); }

  /**
   * Set the {@code nextValue} property.
   * @see #nextValue
   */
  public void setNextValue(BStatusString v) { set(nextValue, v, null); }

  //endregion Property "nextValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BStringSchedule() { }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  protected void setNextVal(BStatusValue v)
  {
    setNextValue((BStatusString)v);
  }

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


}//BStringSchedule
