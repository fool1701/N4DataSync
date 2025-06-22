/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import java.text.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStatusNumericToStatusEnum is a component that converts a StatusNumeric to a StatusEnum.
 *
 * @author    Andy Saunders
 * @creation  15 March 2006
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum()"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
public class BStatusNumericToStatusEnum
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusNumericToStatusEnum(4232340963)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeEnum(), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusEnum(), null);

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
  public static final Property in = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusNumericToStatusEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusNumericToStatusEnum()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    calculate();
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p.equals(in) )
    {
      calculate();
    }
  }

  void calculate()
  {
    BStatusEnum workingValue = getOut();
    double inValue = getIn().getValue();
    if(inValue <= (double)Integer.MAX_VALUE &&
       inValue >= (double)Integer.MIN_VALUE    )
    {
      workingValue.setValue(BDynamicEnum.make((int)getIn().getValue()) );
      workingValue.setStatusNull(false);
      workingValue.setStatusFault(false);
    }
    else    
    {
      workingValue.setStatusNull(true);
      workingValue.setStatusFault(true);
    }
    setOut(workingValue);
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == out) return getFacets();
    return super.getSlotFacets(slot);
  }


////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }
  

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

  
}
