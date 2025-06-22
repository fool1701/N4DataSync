/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.Lexicon;

import com.tridium.kitControl.BKitBooleanPoint;

/**
 * BBooleanSwitch uses a boolean to switch between two booleans.
 *
 * @author    Lee Adcock
 * @creation  18 June 09
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
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
/*
 Value to output when in inSwitch is false.
 */
@NiagaraProperty(
  name = "inFalse",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
public class BBooleanSwitch
  extends BKitBooleanPoint
{  

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BBooleanSwitch(3707225878)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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
  public static final Property inTrue = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code inTrue} property.
   * Value to output when in inSwitch is true.
   * @see #inTrue
   */
  public BStatusBoolean getInTrue() { return (BStatusBoolean)get(inTrue); }

  /**
   * Set the {@code inTrue} property.
   * Value to output when in inSwitch is true.
   * @see #inTrue
   */
  public void setInTrue(BStatusBoolean v) { set(inTrue, v, null); }

  //endregion Property "inTrue"

  //region Property "inFalse"

  /**
   * Slot for the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #getInFalse
   * @see #setInFalse
   */
  public static final Property inFalse = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #inFalse
   */
  public BStatusBoolean getInFalse() { return (BStatusBoolean)get(inFalse); }

  /**
   * Set the {@code inFalse} property.
   * Value to output when in inSwitch is false.
   * @see #inFalse
   */
  public void setInFalse(BStatusBoolean v) { set(inFalse, v, null); }

  //endregion Property "inFalse"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanSwitch.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue out, Context cx)
  {
    String invalidFacet = Lexicon.make(getType().getModule(), cx).getText("booleanSwitch.invalidInSwitch");
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

  BStatusBoolean workingValue = new BStatusBoolean();

}
