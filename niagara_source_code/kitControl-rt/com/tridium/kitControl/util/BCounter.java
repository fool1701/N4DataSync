/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.kitControl.*;
/**
 * BCounter is a component that count boolean input transitions.
 * <pre>
 *    Positive transitions on the countUp property will increment the out property.
 *    Positive transitions on the countDown property will decrement the out property.
 * </pre>
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "countUp",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "countDown",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()"
)
@NiagaraProperty(
  name = "presetIn",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()"
)
@NiagaraProperty(
  name = "clearIn",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()"
)
@NiagaraProperty(
  name = "presetValue",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
@NiagaraProperty(
  name = "countIncrement",
  type = "float",
  defaultValue = "1.0f"
)
@NiagaraAction(
  name = "preset",
  flags = Flags.ASYNC
)
@NiagaraAction(
  name = "clear",
  flags = Flags.ASYNC
)
public class BCounter
  extends BKitNumeric
  implements BIStatus, BINumeric
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BCounter(890751318)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "countUp"

  /**
   * Slot for the {@code countUp} property.
   * @see #getCountUp
   * @see #setCountUp
   */
  public static final Property countUp = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code countUp} property.
   * @see #countUp
   */
  public BStatusBoolean getCountUp() { return (BStatusBoolean)get(countUp); }

  /**
   * Set the {@code countUp} property.
   * @see #countUp
   */
  public void setCountUp(BStatusBoolean v) { set(countUp, v, null); }

  //endregion Property "countUp"

  //region Property "countDown"

  /**
   * Slot for the {@code countDown} property.
   * @see #getCountDown
   * @see #setCountDown
   */
  public static final Property countDown = newProperty(0, new BStatusBoolean(), null);

  /**
   * Get the {@code countDown} property.
   * @see #countDown
   */
  public BStatusBoolean getCountDown() { return (BStatusBoolean)get(countDown); }

  /**
   * Set the {@code countDown} property.
   * @see #countDown
   */
  public void setCountDown(BStatusBoolean v) { set(countDown, v, null); }

  //endregion Property "countDown"

  //region Property "presetIn"

  /**
   * Slot for the {@code presetIn} property.
   * @see #getPresetIn
   * @see #setPresetIn
   */
  public static final Property presetIn = newProperty(0, new BStatusBoolean(), null);

  /**
   * Get the {@code presetIn} property.
   * @see #presetIn
   */
  public BStatusBoolean getPresetIn() { return (BStatusBoolean)get(presetIn); }

  /**
   * Set the {@code presetIn} property.
   * @see #presetIn
   */
  public void setPresetIn(BStatusBoolean v) { set(presetIn, v, null); }

  //endregion Property "presetIn"

  //region Property "clearIn"

  /**
   * Slot for the {@code clearIn} property.
   * @see #getClearIn
   * @see #setClearIn
   */
  public static final Property clearIn = newProperty(0, new BStatusBoolean(), null);

  /**
   * Get the {@code clearIn} property.
   * @see #clearIn
   */
  public BStatusBoolean getClearIn() { return (BStatusBoolean)get(clearIn); }

  /**
   * Set the {@code clearIn} property.
   * @see #clearIn
   */
  public void setClearIn(BStatusBoolean v) { set(clearIn, v, null); }

  //endregion Property "clearIn"

  //region Property "presetValue"

  /**
   * Slot for the {@code presetValue} property.
   * @see #getPresetValue
   * @see #setPresetValue
   */
  public static final Property presetValue = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code presetValue} property.
   * @see #presetValue
   */
  public BStatusNumeric getPresetValue() { return (BStatusNumeric)get(presetValue); }

  /**
   * Set the {@code presetValue} property.
   * @see #presetValue
   */
  public void setPresetValue(BStatusNumeric v) { set(presetValue, v, null); }

  //endregion Property "presetValue"

  //region Property "countIncrement"

  /**
   * Slot for the {@code countIncrement} property.
   * @see #getCountIncrement
   * @see #setCountIncrement
   */
  public static final Property countIncrement = newProperty(0, 1.0f, null);

  /**
   * Get the {@code countIncrement} property.
   * @see #countIncrement
   */
  public float getCountIncrement() { return getFloat(countIncrement); }

  /**
   * Set the {@code countIncrement} property.
   * @see #countIncrement
   */
  public void setCountIncrement(float v) { setFloat(countIncrement, v, null); }

  //endregion Property "countIncrement"

  //region Action "preset"

  /**
   * Slot for the {@code preset} action.
   * @see #preset()
   */
  public static final Action preset = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code preset} action.
   * @see #preset
   */
  public void preset() { invoke(preset, null, null); }

  //endregion Action "preset"

  //region Action "clear"

  /**
   * Slot for the {@code clear} action.
   * @see #clear()
   */
  public static final Action clear = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code clear} action.
   * @see #clear
   */
  public void clear() { invoke(clear, null, null); }

  //endregion Action "clear"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCounter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BCounter()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    //getOut().setValue( calculate() );
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == countUp || p == countDown || p == presetIn || p == clearIn)
    {
      getOut().setValue( calculate() );
      getOut().setStatus( propagateStatus() );
    }
  }

  public BStatus propagateStatus()
  {
    int status   = getCountUp().getStatus().getBits();
    status       = status | getCountDown().getStatus().getBits();
    status       = status | getPresetIn().getStatus().getBits();
    status       = status | getClearIn().getStatus().getBits();
    status       = status & getPropagateFlags().getBits();
    return BStatus.make(status);
  }

  public double calculate()
  {
    boolean countUp;
    boolean countDown;
    boolean preset;
    boolean clear;
    if(getCountUp().getStatus().isValid())
      countUp = getCountUp().getValue();
    else
      countUp = lastCountUp;
    if(getCountDown().getStatus().isValid())
      countDown = getCountDown().getValue();
    else
      countDown = lastCountDown;
    if(getPresetIn().getStatus().isValid())
      preset = getPresetIn().getValue();
    else
      preset = lastPreset;
    if(getClearIn().getStatus().isValid())
      clear = getClearIn().getValue();
    else
      clear = lastClear;

    double count = getOut().getValue();

    if(clear && !lastClear)
      count = 0f;
    if(preset && !lastPreset)
      count = getPresetValue().getValue();
    if(countUp && !lastCountUp)
      count = count + getCountIncrement() ;
    if(countDown && !lastCountDown)
      count = count - getCountIncrement() ;
    lastCountUp = countUp;
    lastCountDown = countDown;
    lastPreset = preset;
    lastClear = clear;
    return count;
  }

  public void doPreset()
  {
    getOut().setValue( getPresetValue().getValue() );
  }

  public void doClear()
  {
    getOut().setValue( 0f );
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }


  public BFacets getSlotFacets(Slot slot)
  {
    if ( slot.getName().equals("out") ) 
    {
      return getFacets();
    }
    else return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getOut().getStatus().getFacets(); }


  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  boolean lastCountUp;
  boolean lastCountDown;
  boolean lastPreset;
  boolean lastClear;
  
}
