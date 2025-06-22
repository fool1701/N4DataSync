/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

/**
 * A numeric control schedule.
 * <b>Input</b><br>
 * If the "in" property is non-null then this value over-rides the
 * scheduled output.
 * @author Aaron Hansen
 * @creation Oct 2001
 * @version $Revision: 29$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusNumeric(0.0, BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric(BUnit.NULL, BInteger.make(1), BFloat.NEGATIVE_INFINITY, BFloat.POSITIVE_INFINITY)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0,BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0,BStatus.nullStatus)",
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
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
public class BNumericSchedule
  extends BWeeklySchedule
  implements BINumeric
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BNumericSchedule(2942333077)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, new BStatusNumeric(0.0, BStatus.nullStatus), null);

  //endregion Property "defaultOutput"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, BFacets.makeNumeric(BUnit.NULL, BInteger.make(1), BFloat.NEGATIVE_INFINITY, BFloat.POSITIVE_INFINITY), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT, new BStatusNumeric(0.0,BStatus.nullStatus), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1, new BStatusNumeric(0.0,BStatus.nullStatus), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

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
  public static final Property nextValue = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, new BStatusNumeric(0), null);

  /**
   * Get the {@code nextValue} property.
   * @see #nextValue
   */
  public BStatusNumeric getNextValue() { return (BStatusNumeric)get(nextValue); }

  /**
   * Set the {@code nextValue} property.
   * @see #nextValue
   */
  public void setNextValue(BStatusNumeric v) { set(nextValue, v, null); }

  //endregion Property "nextValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BNumericSchedule() { }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public double getNumeric()
  {
    return getOut().getValue();
  }

  @Override
  public BFacets getNumericFacets()
  {
    return getFacets();
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  protected void setNextVal(BStatusValue v)
  {
    setNextValue((BStatusNumeric)v);
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


}
