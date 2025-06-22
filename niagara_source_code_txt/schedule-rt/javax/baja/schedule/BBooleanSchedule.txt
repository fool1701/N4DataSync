/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * A boolean control schedule. <p>
 * <b>Input</b><br>
 * If the "in" property is is non-null then this value over-rides
 * the scheduled output.
 * @author Aaron Hansen
 * @creation Oct 2001
 * @version $Revision: 26$ $Date: 7/17/09 10:08:34 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultOutput",
  type = "BStatusValue",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeBoolean(TRUE_TEXT, FALSE_TEXT)",
  flags = Flags.OPERATOR | Flags.USER_DEFINED_1,
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT
)
@NiagaraProperty(
  name = "in",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
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
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT
)
public class BBooleanSchedule
  extends BWeeklySchedule
  implements BIBoolean
{
  ////////////////////////////////////////////////////////////////
  // Constants
  ////////////////////////////////////////////////////////////////
  private static final Lexicon lex = Lexicon.make(BBooleanSchedule.class);
  private static final String TRUE_TEXT = lex.getText("boolean.trueText");
  private static final String FALSE_TEXT = lex.getText("boolean.falseText");

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BBooleanSchedule(1020895886)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultOutput"

  /**
   * Slot for the {@code defaultOutput} property.
   * @see #getDefaultOutput
   * @see #setDefaultOutput
   */
  public static final Property defaultOutput = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, new BStatusBoolean(false), null);

  //endregion Property "defaultOutput"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.OPERATOR | Flags.USER_DEFINED_1, BFacets.makeBoolean(TRUE_TEXT, FALSE_TEXT), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.NO_AUDIT, new BStatusBoolean(false), null);

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

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.SUMMARY | Flags.USER_DEFINED_1, new BStatusBoolean(false, BStatus.nullStatus), null);

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
  public static final Property nextValue = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.SUMMARY | Flags.TRANSIENT | Flags.NO_AUDIT, new BStatusBoolean(false), null);

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
  public static final Type TYPE = Sys.loadType(BBooleanSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BBooleanSchedule() { }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  @Override
  public boolean getBoolean()
  {
    return getOut().getValue();
  }

  @Override
  public BFacets getBooleanFacets()
  {
    return getFacets();
  }

  @Override
  public BEnum getEnum()
  {
    return getOut().getEnum();
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
    setNextValue((BStatusBoolean)v);
  }

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BBooleanSchedule
