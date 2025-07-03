/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.kitControl.*;

/**
 * BNumericMask is the base class for Numeric Bit Mask objects.
 *
 * @author    Andy Saunders
 * @creation  20 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.makeNumeric(0), BFacets.make(\"radix\", BInteger.make(16)))",
  override = true
)
/*
 The output of a BNumericPoint is a BStatusNumeric
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.makeNumeric(0)")
)
@NiagaraProperty(
  name = "mask",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.makeNumeric(0)")
)
public abstract class BNumericMask
  extends BKitNumeric
  implements BIStatus, BINumeric
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BNumericMask(1861231380)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.make(BFacets.makeNumeric(0), BFacets.make("radix", BInteger.make(16))), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * The output of a BNumericPoint is a BStatusNumeric
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusNumeric(), BFacets.makeNumeric(0));

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

  //region Property "mask"

  /**
   * Slot for the {@code mask} property.
   * @see #getMask
   * @see #setMask
   */
  public static final Property mask = newProperty(Flags.SUMMARY, new BStatusNumeric(), BFacets.makeNumeric(0));

  /**
   * Get the {@code mask} property.
   * @see #mask
   */
  public BStatusNumeric getMask() { return (BStatusNumeric)get(mask); }

  /**
   * Set the {@code mask} property.
   * @see #mask
   */
  public void setMask(BStatusNumeric v) { set(mask, v, null); }

  //endregion Property "mask"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericMask.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
    calculate();
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      calculate();
      setStatus();
    }
  }

  public abstract void doCalculate();
  
  /**
   * Default implementation is to do nothing.
   */
  public void calculate()
  {
    doCalculate();
  }

  public void setStatus()
  {
    int statusBits = getIn().getStatus().getBits() | getMask().getStatus().getBits();
    getOut().setStatus(BStatus.make(statusBits & getPropagateFlags().getBits()));
  }

  public BFacets getSlotFacets(Slot slot)
  {
    //if (slot.getName().startsWith("out")) return getFacets();
    if(slot == out || slot == in || slot == mask) return getFacets();
    return super.getSlotFacets(slot);
  }

  public String toString(Context cx) { return getOut().toString(cx); }
////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getFacets(); }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");


}
