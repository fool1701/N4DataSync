/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIEnum;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A multistate control schedule.
 * <b>Input</b><br>
 * If the "in" property is non-null then this value over-rides
 * the scheduled output.
 * @author Aaron Hansen
 * @creation Oct 2001
 * @version $Revision: 25$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.RANGE, BEnumRange.NULL)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BDynamicEnum.DEFAULT)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT
)
@NiagaraProperty(
  name = "in",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.nullStatus)",
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
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum(BDynamicEnum.DEFAULT)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
public class BEnumSchedule
  extends BWeeklySchedule
  implements BIEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BEnumSchedule(16968320)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.nullStatus), null);

  //endregion Property "defaultOutput"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, BFacets.make(BFacets.RANGE, BEnumRange.NULL), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT, new BStatusEnum(BDynamicEnum.DEFAULT), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusEnum getOut() { return (BStatusEnum)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusEnum v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1, new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.nullStatus), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusEnum getIn() { return (BStatusEnum)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusEnum v) { set(in, v, null); }

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
  public static final Property nextValue = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, new BStatusEnum(BDynamicEnum.DEFAULT), null);

  /**
   * Get the {@code nextValue} property.
   * @see #nextValue
   */
  public BStatusEnum getNextValue() { return (BStatusEnum)get(nextValue); }

  /**
   * Set the {@code nextValue} property.
   * @see #nextValue
   */
  public void setNextValue(BStatusEnum v) { set(nextValue, v, null); }

  //endregion Property "nextValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BEnumSchedule() { }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public BEnum getEnum()
  {
    return getOut().getValue();
  }

  @Override
  public BFacets getEnumFacets()
  {
    return getFacets();
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  protected void setNextVal(BStatusValue v)
  {
    setNextValue((BStatusEnum)v);
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


}//BEnumSchedule
