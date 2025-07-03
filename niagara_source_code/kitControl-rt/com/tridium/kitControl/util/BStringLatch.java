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
 * BStringLatch is used to latch a string value on the rising edge of the Clock property.
 *
 * @author    Andy Saunders
 * @creation  16 April 2004
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
public class BStringLatch
  extends BLatch
  implements BIStatus
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringLatch(1391732630)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusString(), null);

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

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusString getIn() { return (BStatusString)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusString v) { set(in, v, null); }

  //endregion Property "in"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringLatch.class);

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
    setOut((BStatusString)value);
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
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/stringPoint.png");


}
