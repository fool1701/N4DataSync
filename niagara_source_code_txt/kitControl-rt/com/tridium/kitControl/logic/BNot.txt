/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BAnd performs a logical NOT on all inputs and writes
 * the result to the out property.
 *
 * @author    Bill Smith
 * @creation  5 Feb 2004
 * @version   $Revision: 2$ $Date: 2/6/2004 8:19:30 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "in",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false, BStatus.nullStatus)",
  flags = Flags.SUMMARY
)
public class BNot
  extends BLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BNot(2682546648)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusBoolean(false, BStatus.nullStatus), null);

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
  public static final Type TYPE = Sys.loadType(BNot.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusBoolean in = getIn();
    BStatus aStatus = in.getStatus();

    int a = 0;
    BStatusBoolean out = (BStatusBoolean)o;
    
    if (!aStatus.isNull()){
      a = aStatus.getBits();
      out.setValue(calculate(in));   
      out.setStatus(propagate(BStatus.make(a)));        

      if (getNullOnInactive() && !(out.getValue()))
        out.setStatusNull(true);
    }
    else{
      out.setValue(false);
      out.setStatus(BStatus.nullStatus);
    }
  }

  protected boolean calculate(BStatusBoolean a)
  {
    if (a.getStatus().isValid())
      return !a.getValue();
    else
      return false;
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/not.png");

}
