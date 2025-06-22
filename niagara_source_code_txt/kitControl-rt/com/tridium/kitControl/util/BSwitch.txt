/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BSwitch is the base class for muxes and demuxes
 *   It is a 3 to 10 position switch. 
 *
 * @author    Andy Saunders
 * @creation  16 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against all out properties.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 defines which input status flags will be propagated from
 input to output.
 */
@NiagaraProperty(
  name = "propagateFlags",
  type = "BStatus",
  defaultValue = "BStatus.nullStatus"
)
/*
 The intput that selects one of several values
 */
@NiagaraProperty(
  name = "select",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY
)
/*
 number of switch positions.
 */
@NiagaraProperty(
  name = "numberValues",
  type = "int",
  defaultValue = "3",
  facets = @Facet("BFacets.makeInt(null, 3, 10)")
)
/*
 If true, a select ordinal value of zero will select the
 first input value.  Otherwise, a ordinal value <= one will select
 the first input value.
 */
@NiagaraProperty(
  name = "zeroBasedSelect",
  type = "boolean",
  defaultValue = "false"
)
public abstract class BSwitch
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BSwitch(4129687289)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against all out properties.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.nullStatus, null);

  /**
   * Get the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public BStatus getPropagateFlags() { return (BStatus)get(propagateFlags); }

  /**
   * Set the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public void setPropagateFlags(BStatus v) { set(propagateFlags, v, null); }

  //endregion Property "propagateFlags"

  //region Property "select"

  /**
   * Slot for the {@code select} property.
   * The intput that selects one of several values
   * @see #getSelect
   * @see #setSelect
   */
  public static final Property select = newProperty(Flags.OPERATOR | Flags.TRANSIENT | Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code select} property.
   * The intput that selects one of several values
   * @see #select
   */
  public BStatusEnum getSelect() { return (BStatusEnum)get(select); }

  /**
   * Set the {@code select} property.
   * The intput that selects one of several values
   * @see #select
   */
  public void setSelect(BStatusEnum v) { set(select, v, null); }

  //endregion Property "select"

  //region Property "numberValues"

  /**
   * Slot for the {@code numberValues} property.
   * number of switch positions.
   * @see #getNumberValues
   * @see #setNumberValues
   */
  public static final Property numberValues = newProperty(0, 3, BFacets.makeInt(null, 3, 10));

  /**
   * Get the {@code numberValues} property.
   * number of switch positions.
   * @see #numberValues
   */
  public int getNumberValues() { return getInt(numberValues); }

  /**
   * Set the {@code numberValues} property.
   * number of switch positions.
   * @see #numberValues
   */
  public void setNumberValues(int v) { setInt(numberValues, v, null); }

  //endregion Property "numberValues"

  //region Property "zeroBasedSelect"

  /**
   * Slot for the {@code zeroBasedSelect} property.
   * If true, a select ordinal value of zero will select the
   * first input value.  Otherwise, a ordinal value <= one will select
   * the first input value.
   * @see #getZeroBasedSelect
   * @see #setZeroBasedSelect
   */
  public static final Property zeroBasedSelect = newProperty(0, false, null);

  /**
   * Get the {@code zeroBasedSelect} property.
   * If true, a select ordinal value of zero will select the
   * first input value.  Otherwise, a ordinal value <= one will select
   * the first input value.
   * @see #zeroBasedSelect
   */
  public boolean getZeroBasedSelect() { return getBoolean(zeroBasedSelect); }

  /**
   * Set the {@code zeroBasedSelect} property.
   * If true, a select ordinal value of zero will select the
   * first input value.  Otherwise, a ordinal value <= one will select
   * the first input value.
   * @see #zeroBasedSelect
   */
  public void setZeroBasedSelect(boolean v) { setBoolean(zeroBasedSelect, v, null); }

  //endregion Property "zeroBasedSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSwitch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
    initNumberValues();
    calculate();
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if(property == numberValues)
      {
        initNumberValues();
      }
      else if(property.getName().startsWith("in") || 
              property.equals(select)             ||
              property.equals(propagateFlags)     ||
              property.equals(zeroBasedSelect))
      {
        calculate();
      }
    }
  }

  //public abstract BStatusValue getOutStatusValue();
  //public abstract BStatusValue getInStatusValue();
  public abstract BStatusValue getNullInstance();
  //public abstract BStatusValue getInStatusValue(int select);

  //public abstract void setOutputs(BStatusValue[] values);
  //public abstract void setOutput(BStatusValue value);
  public abstract void doCalculate(int value);
  public abstract void initNumberValues();
  public abstract BStatus getOutStatus();
  public abstract void setOutStatus(BStatus status);

  //public abstract boolean isMuxSwitch();

  /**
   * Default implementation is to do nothing.
   */
  public void calculate()
  {
    String invalidFacet = Lexicon.make(getType().getModule(), Sys.getLanguage()).getText("muxSwitch.invalidSelect");
    if(getSelect().getStatus().isValid())
    {
      int selectValue = getSelect().getValue().getOrdinal();
      if(getZeroBasedSelect())
        selectValue++;
      int maxSelect = getNumberValues();
      if(selectValue < 1) selectValue = 1;
      else if(selectValue >= maxSelect) selectValue = maxSelect;
      doCalculate(selectValue);
      BStatus s = getOutStatus();
      setOutStatus(BStatus.make(s.getBits(), BFacets.makeRemove(s.getFacets(), invalidFacet)));
    }
    else
    {
      setOutStatus(BStatus.make(propagate(getSelect().getStatus()), invalidFacet, true));
    }
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().startsWith("out")) return getFacets();
    return super.getSlotFacets(slot);
  }

  /**
   * Create a new status by masking out only the standard
   * flags which should be propagated from inputs to outputs.
   * See PROPOGATE_MASK for the flags which are propagated.
   *
   * @return <code>make(s.getBits() & PROPOGATE_MASK)</code>
   */
  public BStatus propagate(BStatus s)
  {
    return BStatus.make(s.getBits() & getPropagateFlags().getBits());
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");

  int numValues = 3;
}
