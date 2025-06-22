/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBooleanLatch is used to latch a boolean value on the rising edge of the Clock property.
 *
 * @author    Andy Saunders
 * @creation  16 April 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeBoolean()",
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
public class BBooleanLatch
  extends BLatch
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BBooleanLatch(3474719311)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeBoolean(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

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
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusBoolean getIn() { return (BStatusBoolean)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusBoolean v) { set(in, v, null); }

  //endregion Property "in"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanLatch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  /**
   * Get the control output value.
   */
  public final void setOutStatusValue(BStatusValue value) 
  { 
    setOut((BStatusBoolean)value);
  }

  public final BStatusValue getInStatusValue() 
  { 
    return (BStatusValue)(getIn().newCopy());
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/booleanPoint.png");


}
