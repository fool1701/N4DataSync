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
import javax.baja.units.*;

import com.tridium.bql.filter.*;
import com.tridium.kitControl.enums.BNullValueOverrideSelect;
/**
 * BStatusEnumToInt is a component that converts a StatusEnum to a Integer.
 *
 * @author    Andy Saunders
 * @creation  18 May 2005
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "outFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeInt()"
)
/*
 These facets are applied against the in property.
 */
@NiagaraProperty(
  name = "inFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum()"
)
@NiagaraProperty(
  name = "out",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "onNullInValue",
  type = "BNullValueOverrideSelect",
  defaultValue = "BNullValueOverrideSelect.useInValue"
)
public class BStatusEnumToInt
  extends BStatusValueToValue
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusEnumToInt(751123351)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "outFacets"

  /**
   * Slot for the {@code outFacets} property.
   * These facets are applied against the out property.
   * @see #getOutFacets
   * @see #setOutFacets
   */
  public static final Property outFacets = newProperty(0, BFacets.makeInt(), null);

  /**
   * Get the {@code outFacets} property.
   * These facets are applied against the out property.
   * @see #outFacets
   */
  public BFacets getOutFacets() { return (BFacets)get(outFacets); }

  /**
   * Set the {@code outFacets} property.
   * These facets are applied against the out property.
   * @see #outFacets
   */
  public void setOutFacets(BFacets v) { set(outFacets, v, null); }

  //endregion Property "outFacets"

  //region Property "inFacets"

  /**
   * Slot for the {@code inFacets} property.
   * These facets are applied against the in property.
   * @see #getInFacets
   * @see #setInFacets
   */
  public static final Property inFacets = newProperty(0, BFacets.makeEnum(), null);

  /**
   * Get the {@code inFacets} property.
   * These facets are applied against the in property.
   * @see #inFacets
   */
  public BFacets getInFacets() { return (BFacets)get(inFacets); }

  /**
   * Set the {@code inFacets} property.
   * These facets are applied against the in property.
   * @see #inFacets
   */
  public void setInFacets(BFacets v) { set(inFacets, v, null); }

  //endregion Property "inFacets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, 0, null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public int getOut() { return getInt(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(int v) { setInt(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusEnum(), null);

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

  //region Property "onNullInValue"

  /**
   * Slot for the {@code onNullInValue} property.
   * @see #getOnNullInValue
   * @see #setOnNullInValue
   */
  public static final Property onNullInValue = newProperty(0, BNullValueOverrideSelect.useInValue, null);

  /**
   * Get the {@code onNullInValue} property.
   * @see #onNullInValue
   */
  public BNullValueOverrideSelect getOnNullInValue() { return (BNullValueOverrideSelect)get(onNullInValue); }

  /**
   * Set the {@code onNullInValue} property.
   * @see #onNullInValue
   */
  public void setOnNullInValue(BNullValueOverrideSelect v) { set(onNullInValue, v, null); }

  //endregion Property "onNullInValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusEnumToInt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusEnumToInt()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    execute();
  }

  /**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == in)
    {
      execute();
    }
    else super.changed(p, cx);
  }

  public void execute()
  {
    BValue calcValue = calculate(getIn());
    if(calcValue.getType().is(BDynamicEnum.TYPE))
      setOut( ((BDynamicEnum)calcValue).getOrdinal() );
    else
      setOut( ((BInteger)calcValue).getInt() );
  }

  public Type getOutType()
  {
    return BInteger.TYPE;
  }
  
  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == out) return getOutFacets();
    if (slot == in) return getInFacets();
    return super.getSlotFacets(slot);
  }


//  void calculate()
//  {
//    //BEnum enumValue =  getIn().getValue();
//    setOut( getIn().getValue().getOrdinal() );
//  }

  public String toString(Context cx)
  {
    StringBuilder s = new StringBuilder();
    s.append(getOut());
    return s.toString();
  }
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
}
