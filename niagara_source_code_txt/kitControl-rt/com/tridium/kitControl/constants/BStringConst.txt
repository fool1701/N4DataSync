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


/** String constant object
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
  defaultValue = "BFacets.DEFAULT"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
/*
 To set the output .
 */
@NiagaraAction(
  name = "set",
  parameterType = "BString",
  defaultValue = "BString.make(\"\")",
  flags = Flags.OPERATOR
)
public class BStringConst
  extends BComponent
  implements BIStatusValue
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.constants.BStringConst(2863800541)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

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
  public static final Property out = newProperty(Flags.SUMMARY, new BStatusString(), null);

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

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * To set the output .
   * @see #set(BString parameter)
   */
  public static final Action set = newAction(Flags.OPERATOR, BString.make(""), null);

  /**
   * Invoke the {@code set} action.
   * To set the output .
   * @see #set
   */
  public void set(BString parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringConst.class);

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

  public void doSet(BString value)
  {
    getOut().setValue(value.getString());
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
// BIStatusValue interface
////////////////////////////////////////////////////////////////

  public BStatusValue getStatusValue() { return getOut(); }

  public BFacets getStatusValueFacets() { return getOut().getStatusValueFacets(); }

}
