/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.timer;

import javax.baja.control.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

/**
 * BTimeDifference will calculate the difference between two BAbsTime inputs in milliseconds.
 *    out = in1 - in2
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0)",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY,
  facets = @Facet("BFacets.makeNumeric(UnitDatabase.getUnit(\"millisecond\"), 0)")
)
@NiagaraProperty(
  name = "in1",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, true)")
)
@NiagaraProperty(
  name = "in2",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, true)")
)
public class BTimeDifference
  extends BComponent
  implements BIStatus, BINumeric
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.timer.BTimeDifference(1672910106)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusNumeric(0.0), BFacets.makeNumeric(UnitDatabase.getUnit("millisecond"), 0));

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

  //region Property "in1"

  /**
   * Slot for the {@code in1} property.
   * @see #getIn1
   * @see #setIn1
   */
  public static final Property in1 = newProperty(Flags.SUMMARY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_MILLISECONDS, true));

  /**
   * Get the {@code in1} property.
   * @see #in1
   */
  public BAbsTime getIn1() { return (BAbsTime)get(in1); }

  /**
   * Set the {@code in1} property.
   * @see #in1
   */
  public void setIn1(BAbsTime v) { set(in1, v, null); }

  //endregion Property "in1"

  //region Property "in2"

  /**
   * Slot for the {@code in2} property.
   * @see #getIn2
   * @see #setIn2
   */
  public static final Property in2 = newProperty(Flags.SUMMARY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_MILLISECONDS, true));

  /**
   * Get the {@code in2} property.
   * @see #in2
   */
  public BAbsTime getIn2() { return (BAbsTime)get(in2); }

  /**
   * Set the {@code in2} property.
   * @see #in2
   */
  public void setIn2(BAbsTime v) { set(in2, v, null); }

  //endregion Property "in2"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeDifference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTimeDifference()
  {
  }
  

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    double outValue = Double.NaN;
    if ( p.equals(in1) || p.equals(in2) )
    {
      if( !getIn1().isNull()  && !getIn2().isNull() )
        outValue = (double)(getIn1().getMillis() - getIn2().getMillis());
      getOut().setValue(outValue);
    }
    else
    {
      super.changed(p, cx);
    }
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getOut().getStatus().getFacets(); }


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/trigger.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  
}
