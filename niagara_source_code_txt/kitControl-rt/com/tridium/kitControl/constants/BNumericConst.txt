/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.constants;

import java.io.*;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;


/** Numeric constant object
 *
 * 
 * @author    Andy Saunders
 * @creation  14 Sept 2004
 * @version   $Revision: 21$ $Date: 11/5/2003 5:12:11 PM$
 * @since     Baja 1.0
 */
 
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
/*
 To set the output .
 */
@NiagaraAction(
  name = "set",
  parameterType = "BDouble",
  defaultValue = "BDouble.make(0d)",
  flags = Flags.OPERATOR
)
public class BNumericConst
  extends BComponent
  implements BIStatus, BINumeric
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.constants.BNumericConst(3022321958)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(), null);

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
  public static final Property out = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

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

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * To set the output .
   * @see #set(BDouble parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BDouble.make(0d), null);

  /**
   * Invoke the {@code set} action.
   * To set the output .
   * @see #set
   */
  public void set(BDouble parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericConst.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().equals("out") || slot == set)
    {
      return getFacets();
    }
    else return super.getSlotFacets(slot);
  }

  public BValue getActionParameterDefault(Action action)
  {
    if (action == set) 
      return getOut().getValueValue();
    return super.getActionParameterDefault(action);
  }

  public void doSet(BDouble value)
  {
    getOut().setValue(value.getDouble());
  }

  public String toString(Context cx)
  {
    return propertyValueToString(out, cx);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getFacets(); }



}
