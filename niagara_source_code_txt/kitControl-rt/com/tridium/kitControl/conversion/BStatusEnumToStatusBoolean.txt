/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import java.text.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.units.*;

import com.tridium.bql.filter.*;
/**
 * BStatusEnumToStatusBoolean is a component that converts a StatusEnum to a StatusBoolean.
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
  defaultValue = "BFacets.makeBoolean()"
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
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
/*
 Set of all offnormal states
 */
@NiagaraProperty(
  name = "activeValues",
  type = "BEnumRange",
  defaultValue = "BEnumRange.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:EnumAlarmRangeFE\"))")
)
public class BStatusEnumToStatusBoolean
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BStatusEnumToStatusBoolean(894972372)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "outFacets"

  /**
   * Slot for the {@code outFacets} property.
   * These facets are applied against the out property.
   * @see #getOutFacets
   * @see #setOutFacets
   */
  public static final Property outFacets = newProperty(0, BFacets.makeBoolean(), null);

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
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusBoolean(), null);

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

  //region Property "activeValues"

  /**
   * Slot for the {@code activeValues} property.
   * Set of all offnormal states
   * @see #getActiveValues
   * @see #setActiveValues
   */
  public static final Property activeValues = newProperty(0, BEnumRange.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:EnumAlarmRangeFE")));

  /**
   * Get the {@code activeValues} property.
   * Set of all offnormal states
   * @see #activeValues
   */
  public BEnumRange getActiveValues() { return (BEnumRange)get(activeValues); }

  /**
   * Set the {@code activeValues} property.
   * Set of all offnormal states
   * @see #activeValues
   */
  public void setActiveValues(BEnumRange v) { set(activeValues, v, null); }

  //endregion Property "activeValues"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusEnumToStatusBoolean.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStatusEnumToStatusBoolean()
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

    if ( p.equals(in) || p.equals(inFacets) || p.equals(activeValues) )
    {
      calculate();
    }
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == out) return getOutFacets();
    if (slot == in) return getInFacets();
    if (slot == activeValues) return BFacets.make(getInFacets(), super.getSlotFacets(slot));
    return super.getSlotFacets(slot);
  }


  void calculate()
  {
    //BEnum enumValue =  getIn().getValue();
    int currentOrdinal = getIn().getValue().getOrdinal();
    int[] activeOrdinals = getActiveValues().getOrdinals();
    boolean setValue = false;
    for( int i = 0; i < activeOrdinals.length; i++)
    {
      if( currentOrdinal == activeOrdinals[i] )
      {
        setValue = true;
        break;
      }
    }
    getOut().setValue( setValue );
    getOut().setStatus(BStatus.make(getIn().getStatus().getBits()));
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }


////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }
  

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
}
