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
import javax.baja.util.*;

import com.tridium.kitControl.*;

/**
 * BEnumSwitch uses a boolean to switch between two StatusEnums.
 *
 * @author    Andy Saunders
 * @creation  4 Sept 01
 * @version   $Revision: 19$ $Date: 3/30/2004 3:43:05 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Value to control which input to output.
 */
@NiagaraProperty(
  name = "inSwitch",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(false)",
  flags = Flags.SUMMARY
)
/*
 Value to output when in inSwitch is true.
 */
@NiagaraProperty(
  name = "inTrue",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
/*
 Value to output when in inSwitch is false.
 */
@NiagaraProperty(
  name = "inFalse",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY
)
public class BEnumSwitch
  extends BKitEnumPoint
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BEnumSwitch(1702144141)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inSwitch"

  /**
   * Slot for the {@code inSwitch} property.
   * Value to control which input to output.
   * @see #getInSwitch
   * @see #setInSwitch
   */
  public static final Property inSwitch = newProperty(Flags.SUMMARY, new BStatusBoolean(false), null);

  /**
   * Get the {@code inSwitch} property.
   * Value to control which input to output.
   * @see #inSwitch
   */
  public BStatusBoolean getInSwitch() { return (BStatusBoolean)get(inSwitch); }

  /**
   * Set the {@code inSwitch} property.
   * Value to control which input to output.
   * @see #inSwitch
   */
  public void setInSwitch(BStatusBoolean v) { set(inSwitch, v, null); }

  //endregion Property "inSwitch"

  //region Property "inTrue"

  /**
   * Slot for the {@code inTrue} property.
   * Value to output when in inSwitch is true.
   * @see #getInTrue
   * @see #setInTrue
   */
  public static final Property inTrue = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inTrue} property.
   * Value to output when in inSwitch is true.
   * @see #inTrue
   */
  public BStatusEnum getInTrue() { return (BStatusEnum)get(inTrue); }

  /**
   * Set the {@code inTrue} property.
   * Value to output when in inSwitch is true.
   * @see #inTrue
   */
  public void setInTrue(BStatusEnum v) { set(inTrue, v, null); }

  //endregion Property "inTrue"

  //region Property "inFalse"

  /**
   * Slot for the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #getInFalse
   * @see #setInFalse
   */
  public static final Property inFalse = newProperty(Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #inFalse
   */
  public BStatusEnum getInFalse() { return (BStatusEnum)get(inFalse); }

  /**
   * Set the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #inFalse
   */
  public void setInFalse(BStatusEnum v) { set(inFalse, v, null); }

  //endregion Property "inFalse"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumSwitch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().startsWith("in")) return getFacets();
    return super.getSlotFacets(slot);
  }



  public void onExecute(BStatusValue out, Context cx)
  {
    String invalidFacet = Lexicon.make(getType().getModule(), cx).getText("numericSwitch.invalidInSwitch");
    if(getInSwitch().getStatus().isValid())
    {
      if (getInSwitch().getValue())
      {
        workingValue.copyFrom(getInTrue());
        workingValue.setStatus(propagate(getInTrue().getStatus()));
      }
      else
      {
        workingValue.copyFrom(getInFalse());
        workingValue.setStatus(propagate(getInFalse().getStatus()));
      }
      BStatus s = workingValue.getStatus();
      workingValue.setStatus(BStatus.make(s.getBits(), BFacets.makeRemove(s.getFacets(), invalidFacet)));
    }
    else
    {
      workingValue.copyFrom(getOut());
      workingValue.setStatus(BStatus.make(propagate(getInSwitch().getStatus()), invalidFacet, true));
    }
    out.copyFrom(workingValue);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");

  BStatusEnum workingValue = new BStatusEnum();

}
