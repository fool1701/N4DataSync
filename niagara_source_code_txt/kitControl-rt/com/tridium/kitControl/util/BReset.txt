/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.util;

import static java.lang.Double.isNaN;

import static com.tridium.kitControl.enums.BResetLimitsExceededMode.setStatusToNull;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.kitControl.enums.BResetLimitsExceededMode;
import com.tridium.kitControl.math.BMath;

 /**
 *  This function performs a "reset" on the value inA.
 * "Reset" is a HVAC term for scaling a number 
 * between two limits.  
 *
 * When inputLowLimit < inA < inputHighLimit, 
 * the output value  scales linearly 
 * between outputLowLimit and outputHighLimit.  
 *
 * If inA < inputLowLimit, the value is capped
 * at outputLowLimit.  
 * 
 * If inA > inputHighLimit, the value is capped 
 * at outputHighLimit.
 * 
 * To calculate out, the following equation is used:
 * <pre>
 *       (D-B)
 *   y = ----- * (x - A) + B
 *       (C-A)  
 *
 *  where 
 *     A = inputLowLimit
 *     B = outputLowLimit
 *     C = inputHighLimit
 *     D = outputHighLimit
 *     x = inA
 *     y = out
 * </pre>
 * 
 *  The equation can be derived by solving the 
 * three linear equations below simultaneously:
 *
 *   B = mA + b
 *   D = mC + b
 *   y = mx + b
 *
 * The proof is left as an exercise to the reader...
 *
 * @author    Dan Giorgis
 * @creation  30 Aug 2001
 * @version   $Revision: 13$ $Date: 3/30/2004 3:42:29 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Input A
 */
@NiagaraProperty(
  name = "inA",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
/*
 Input low limit
 */
@NiagaraProperty(
  name = "inputLowLimit",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
/*
 Input high limit
 */
@NiagaraProperty(
  name = "inputHighLimit",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
/*
 Output low limit
 */
@NiagaraProperty(
  name = "outputLowLimit",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
/*
 Output high limit
 */
@NiagaraProperty(
  name = "outputHighLimit",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()"
)
/*
 Indicates the desired behavior of the Out property when
 inA exceeds the inputLowLimit or inputHighLimit.
 @since Niagara 4.12
 */
@NiagaraProperty(
  name = "inputLimitsExceededMode",
  type = "BResetLimitsExceededMode",
  defaultValue = "BResetLimitsExceededMode.useExceededLimit"
)
public class BReset
  extends BMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BReset(3897048966)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inA"

  /**
   * Slot for the {@code inA} property.
   * Input A
   * @see #getInA
   * @see #setInA
   */
  public static final Property inA = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code inA} property.
   * Input A
   * @see #inA
   */
  public BStatusNumeric getInA() { return (BStatusNumeric)get(inA); }

  /**
   * Set the {@code inA} property.
   * Input A
   * @see #inA
   */
  public void setInA(BStatusNumeric v) { set(inA, v, null); }

  //endregion Property "inA"

  //region Property "inputLowLimit"

  /**
   * Slot for the {@code inputLowLimit} property.
   * Input low limit
   * @see #getInputLowLimit
   * @see #setInputLowLimit
   */
  public static final Property inputLowLimit = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code inputLowLimit} property.
   * Input low limit
   * @see #inputLowLimit
   */
  public BStatusNumeric getInputLowLimit() { return (BStatusNumeric)get(inputLowLimit); }

  /**
   * Set the {@code inputLowLimit} property.
   * Input low limit
   * @see #inputLowLimit
   */
  public void setInputLowLimit(BStatusNumeric v) { set(inputLowLimit, v, null); }

  //endregion Property "inputLowLimit"

  //region Property "inputHighLimit"

  /**
   * Slot for the {@code inputHighLimit} property.
   * Input high limit
   * @see #getInputHighLimit
   * @see #setInputHighLimit
   */
  public static final Property inputHighLimit = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code inputHighLimit} property.
   * Input high limit
   * @see #inputHighLimit
   */
  public BStatusNumeric getInputHighLimit() { return (BStatusNumeric)get(inputHighLimit); }

  /**
   * Set the {@code inputHighLimit} property.
   * Input high limit
   * @see #inputHighLimit
   */
  public void setInputHighLimit(BStatusNumeric v) { set(inputHighLimit, v, null); }

  //endregion Property "inputHighLimit"

  //region Property "outputLowLimit"

  /**
   * Slot for the {@code outputLowLimit} property.
   * Output low limit
   * @see #getOutputLowLimit
   * @see #setOutputLowLimit
   */
  public static final Property outputLowLimit = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code outputLowLimit} property.
   * Output low limit
   * @see #outputLowLimit
   */
  public BStatusNumeric getOutputLowLimit() { return (BStatusNumeric)get(outputLowLimit); }

  /**
   * Set the {@code outputLowLimit} property.
   * Output low limit
   * @see #outputLowLimit
   */
  public void setOutputLowLimit(BStatusNumeric v) { set(outputLowLimit, v, null); }

  //endregion Property "outputLowLimit"

  //region Property "outputHighLimit"

  /**
   * Slot for the {@code outputHighLimit} property.
   * Output high limit
   * @see #getOutputHighLimit
   * @see #setOutputHighLimit
   */
  public static final Property outputHighLimit = newProperty(0, new BStatusNumeric(), null);

  /**
   * Get the {@code outputHighLimit} property.
   * Output high limit
   * @see #outputHighLimit
   */
  public BStatusNumeric getOutputHighLimit() { return (BStatusNumeric)get(outputHighLimit); }

  /**
   * Set the {@code outputHighLimit} property.
   * Output high limit
   * @see #outputHighLimit
   */
  public void setOutputHighLimit(BStatusNumeric v) { set(outputHighLimit, v, null); }

  //endregion Property "outputHighLimit"

  //region Property "inputLimitsExceededMode"

  /**
   * Slot for the {@code inputLimitsExceededMode} property.
   * Indicates the desired behavior of the Out property when
   * inA exceeds the inputLowLimit or inputHighLimit.
   * @since Niagara 4.12
   * @see #getInputLimitsExceededMode
   * @see #setInputLimitsExceededMode
   */
  public static final Property inputLimitsExceededMode = newProperty(0, BResetLimitsExceededMode.useExceededLimit, null);

  /**
   * Get the {@code inputLimitsExceededMode} property.
   * Indicates the desired behavior of the Out property when
   * inA exceeds the inputLowLimit or inputHighLimit.
   * @since Niagara 4.12
   * @see #inputLimitsExceededMode
   */
  public BResetLimitsExceededMode getInputLimitsExceededMode() { return (BResetLimitsExceededMode)get(inputLimitsExceededMode); }

  /**
   * Set the {@code inputLimitsExceededMode} property.
   * Indicates the desired behavior of the Out property when
   * inA exceeds the inputLowLimit or inputHighLimit.
   * @since Niagara 4.12
   * @see #inputLimitsExceededMode
   */
  public void setInputLimitsExceededMode(BResetLimitsExceededMode v) { set(inputLimitsExceededMode, v, null); }

  //endregion Property "inputLimitsExceededMode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReset.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric inA = getInA();
    BStatus sa = inA.getStatus();                     
    BStatusNumeric out = (BStatusNumeric)o;
    BStatusNumeric inputLowLimit = getInputLowLimit();
    BStatusNumeric inputHighLimit = getInputHighLimit();
    BStatusNumeric outputLowLimit = getOutputLowLimit();
    BStatusNumeric outputHighLimit = getOutputHighLimit();

    BStatus ilStatus = inputLowLimit.getStatus();
    BStatus ihStatus = inputHighLimit.getStatus();
    BStatus olStatus = outputLowLimit.getStatus();
    BStatus ohStatus = outputHighLimit.getStatus();

    //  if any of the limits is null or in the input is
    //  null, force the output to null  
    if (ilStatus.isNull() || ihStatus.isNull() || 
        olStatus.isNull() || ohStatus.isNull() || sa.isNull())
    {
      out.setValue(Double.NaN);
      out.setStatus(BStatus.nullStatus);
    }
    // else if the input limits exceeded mode is setStatusToNull
    // and the value of inA, or inputLowLimit, or inputHighLimit is NaN,
    // force the output to null
    else if (getInputLimitsExceededMode().equals(setStatusToNull)
      && (isNaN(inA.getValue()) || isNaN(inputLowLimit.getValue()) || isNaN(inputHighLimit.getValue())))
    {
      out.setValue(Double.NaN);
      out.setStatus(BStatus.nullStatus);
    }
    else
    {
      reset(
        inA,
        out,
        inputLowLimit.getValue(),
        inputHighLimit.getValue(),
        outputLowLimit.getValue(),
        outputHighLimit.getValue());
    }
  }

  private void reset(
    BStatusNumeric inA,
    BStatusNumeric out,
    double inputLowLimit,
    double inputHighLimit,
    double outputLowLimit,
    double outputHighLimit)
  {
    // Assign an 'out' value ...
    // noting if an input limit was exceeded.
    boolean limitExceeded = false;

    double in = inA.getValue();

    if (in < inputLowLimit)
    {
      out.setValue(outputLowLimit);
      limitExceeded = true;
    }
    else if (in > inputHighLimit)
    {
      out.setValue(outputHighLimit);
      limitExceeded = true;
    }
    else
    {
      double result = (outputHighLimit - outputLowLimit) / (inputHighLimit - inputLowLimit) * (in - inputLowLimit) + outputLowLimit;

      out.setValue(result);
    }

    // If an input limit was exceeded and the mode is setStatusToNull set status to null.
    // Otherwise, propagate in input status.
    if (limitExceeded && getInputLimitsExceededMode().equals(setStatusToNull))
    {
      out.setStatus(BStatus.nullStatus);
    }
    else
    {
      out.setStatus(propagate(inA.getStatus()));
    }
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/math/reset.png");
}
