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


/** Boolean constant object
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
  defaultValue = "BFacets.makeBoolean()"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
/*
 To set an active output.
 */
@NiagaraAction(
  name = "active",
  flags = Flags.OPERATOR
)
/*
 To set an inactive output.
 */
@NiagaraAction(
  name = "inactive",
  flags = Flags.OPERATOR
)
/*
 To set the output.
 */
@NiagaraAction(
  name = "set",
  parameterType = "BBoolean",
  defaultValue = "BBoolean.FALSE",
  flags = Flags.OPERATOR
)
public class BBooleanConst
  extends BComponent
  implements BIStatus, BIBoolean
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.constants.BBooleanConst(4086534679)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeBoolean(), null);

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
  public static final Property out = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

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

  //region Action "active"

  /**
   * Slot for the {@code active} action.
   * To set an active output.
   * @see #active()
   */
  public static final Action active = newAction(Flags.OPERATOR, null);

  /**
   * Invoke the {@code active} action.
   * To set an active output.
   * @see #active
   */
  public void active() { invoke(active, null, null); }

  //endregion Action "active"

  //region Action "inactive"

  /**
   * Slot for the {@code inactive} action.
   * To set an inactive output.
   * @see #inactive()
   */
  public static final Action inactive = newAction(Flags.OPERATOR, null);

  /**
   * Invoke the {@code inactive} action.
   * To set an inactive output.
   * @see #inactive
   */
  public void inactive() { invoke(inactive, null, null); }

  //endregion Action "inactive"

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * To set the output.
   * @see #set(BBoolean parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BBoolean.FALSE, null);

  /**
   * Invoke the {@code set} action.
   * To set the output.
   * @see #set
   */
  public void set(BBoolean parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanConst.class);

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

  public void doActive()
  {
    getOut().setValue(true);
  }

  public void doInactive()
  {
    getOut().setValue(false);
  }

  public void doSet(BBoolean value)
  {
    getOut().setValue(value.getBoolean());
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

  public boolean getBoolean() { return getOut().getValue(); }

  public final BFacets getBooleanFacets() { return getFacets(); }

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getEnum(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }



}
