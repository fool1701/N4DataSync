/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BEnumLatch is used to latch an enum value on the rising edge of the Clock property.
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
  defaultValue = "BFacets.makeEnum()",
  override = true
)
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
public class BEnumLatch
  extends BLatch
  implements BIStatus, BIEnum
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BEnumLatch(2895045876)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeEnum(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusEnum(), null);

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

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumLatch.class);

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
    setOut((BStatusEnum)value);
  }

  public final BStatusValue getInStatusValue() 
  { 
    return (BStatusValue)(getIn().newCopy());
  }

  public String toString(Context cx) { return getOut().toString(cx); }

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

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/enumPoint.png");


}
