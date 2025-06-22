/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStringTrim is a component that trims string.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
public class BStringTrim
  extends BComponent
  implements BIStatus
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringTrim(234639897)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusString(), null);

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
  public static final Type TYPE = Sys.loadType(BStringTrim.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringTrim()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    getOut().setValue( calculate() );
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == in)
    {
      getOut().setValue( calculate() );
      getOut().setStatus(getIn().getStatus());
    }
  }

  public String calculate()
  {
    return getIn().getValue().trim();
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
