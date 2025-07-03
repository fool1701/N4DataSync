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


/** Enum constant object
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
  defaultValue = "BFacets.makeEnum()"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
/*
 To set the output .
 */
@NiagaraAction(
  name = "set",
  parameterType = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT",
  flags = Flags.OPERATOR
)
public class BEnumConst
  extends BComponent
  implements BIStatus, BIEnum
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.constants.BEnumConst(1524571421)1.0$ @*/
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
  public static final Property out = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

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

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * To set the output .
   * @see #set(BDynamicEnum parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BDynamicEnum.DEFAULT, null);

  /**
   * Invoke the {@code set} action.
   * To set the output .
   * @see #set
   */
  public void set(BDynamicEnum parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumConst.class);

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

  public void doSet(BDynamicEnum value)
  {
    getOut().setValue(value);
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
// BIBoolean interface
////////////////////////////////////////////////////////////////

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getEnum(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }


}
