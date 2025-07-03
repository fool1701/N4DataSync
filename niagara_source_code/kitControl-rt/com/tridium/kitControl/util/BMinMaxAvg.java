/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMinMaxAvg uses a numeric to switch between multiple numerics.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeNumeric()",
  override = true
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "min",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY,
  override = true
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "max",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY,
  override = true
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "avg",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY,
  override = true
)
public class BMinMaxAvg
  extends BDecaInputNumeric
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BMinMaxAvg(2234382546)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeNumeric(), null);

  //endregion Property "facets"

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  //endregion Property "max"

  //region Property "avg"

  /**
   * Slot for the {@code avg} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getAvg
   * @see #setAvg
   */
  public static final Property avg = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  //endregion Property "avg"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMinMaxAvg.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.isProperty())
    {
      if( slot.asProperty().equals(min) ||
          slot.asProperty().equals(max) ||
          slot.asProperty().equals(avg)   )
        return getFacets();
    }
    return super.getSlotFacets(slot);
  }


  public void doCalculate()
  {
    double min = Double.POSITIVE_INFINITY;
    double max = Double.NEGATIVE_INFINITY;
    double avg = 0.0;
    double avgCount = 0;
    for(int i = 0; i < getNumberValues(); i++)
    {
      BStatusNumeric value = getInStatusValue(i);
      if(value.getStatus().isValid() && !(Double.isNaN(value.getValue())))
      {
        if(value.getValue() < min) min = value.getValue();
        if(value.getValue() > max) max = value.getValue();
        avg = avg + value.getValue();
        avgCount++;
      }
    }
    if(min == Double.POSITIVE_INFINITY) min = Double.NaN;
    if(max == Double.NEGATIVE_INFINITY) min = Double.NaN;
    avg = avg / avgCount;
    getMin().setValue(min);
    getMax().setValue(max);
    getAvg().setValue(avg);
  }

  public final BStatusNumeric getInStatusValue(int select)
  {
    switch(select)
    {
    case 0: return getInA();
    case 1: return getInB();
    case 2: return getInC();
    case 3: return getInD();
    case 4: return getInE();
    case 5: return getInF();
    case 6: return getInG();
    case 7: return getInH();
    case 8: return getInI();
    case 9: return getInJ();
    }
    return new BStatusNumeric();
  }
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");


}
