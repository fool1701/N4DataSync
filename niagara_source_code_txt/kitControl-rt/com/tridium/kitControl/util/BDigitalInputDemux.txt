/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.util;

import javax.baja.log.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * Analogue to Digital Demultiplexor
 * 
 * @author    Gareth Johnson
 * @creation  30 Nov 2006
 * @version   $Revision: 1$ $Date: 01/29/2007 12:20 AM$
 * @since     Niagara 3.4
 */
@NiagaraType
/*
 defines which input status flags will be propagated from
 input to output.
 */
@NiagaraProperty(
  name = "propagateFlags",
  type = "BStatus",
  defaultValue = "BStatus.ok"
)
/*
 Analogue input
 */
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
/*
 The offset for the input
 */
@NiagaraProperty(
  name = "inOffset",
  type = "double",
  defaultValue = "0.00"
)
/*
 Boolean output
 */
@NiagaraProperty(
  name = "out1",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
/*
 Boolean output
 */
@NiagaraProperty(
  name = "out2",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
/*
 Boolean output
 */
@NiagaraProperty(
  name = "out3",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
/*
 Boolean output
 */
@NiagaraProperty(
  name = "out4",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
/*
 Analogue value
 */
@NiagaraProperty(
  name = "out1Value",
  type = "double",
  defaultValue = "4.8"
)
/*
 Analogue value
 */
@NiagaraProperty(
  name = "out2Value",
  type = "double",
  defaultValue = "2.4"
)
/*
 Analogue value
 */
@NiagaraProperty(
  name = "out3Value",
  type = "double",
  defaultValue = "1.2"
)
/*
 Analogue value
 */
@NiagaraProperty(
  name = "out4Value",
  type = "double",
  defaultValue = "0.6"
)
/*
 The deadband
 */
@NiagaraProperty(
  name = "deadBand",
  type = "double",
  defaultValue = "0.1"
)
/*
 Execute the component
 */
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public final class BDigitalInputDemux extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BDigitalInputDemux(4112177004)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propagateFlags"

  /**
   * Slot for the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #getPropagateFlags
   * @see #setPropagateFlags
   */
  public static final Property propagateFlags = newProperty(0, BStatus.ok, null);

  /**
   * Get the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public BStatus getPropagateFlags() { return (BStatus)get(propagateFlags); }

  /**
   * Set the {@code propagateFlags} property.
   * defines which input status flags will be propagated from
   * input to output.
   * @see #propagateFlags
   */
  public void setPropagateFlags(BStatus v) { set(propagateFlags, v, null); }

  //endregion Property "propagateFlags"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * Analogue input
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code in} property.
   * Analogue input
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * Analogue input
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "inOffset"

  /**
   * Slot for the {@code inOffset} property.
   * The offset for the input
   * @see #getInOffset
   * @see #setInOffset
   */
  public static final Property inOffset = newProperty(0, 0.00, null);

  /**
   * Get the {@code inOffset} property.
   * The offset for the input
   * @see #inOffset
   */
  public double getInOffset() { return getDouble(inOffset); }

  /**
   * Set the {@code inOffset} property.
   * The offset for the input
   * @see #inOffset
   */
  public void setInOffset(double v) { setDouble(inOffset, v, null); }

  //endregion Property "inOffset"

  //region Property "out1"

  /**
   * Slot for the {@code out1} property.
   * Boolean output
   * @see #getOut1
   * @see #setOut1
   */
  public static final Property out1 = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out1} property.
   * Boolean output
   * @see #out1
   */
  public BStatusBoolean getOut1() { return (BStatusBoolean)get(out1); }

  /**
   * Set the {@code out1} property.
   * Boolean output
   * @see #out1
   */
  public void setOut1(BStatusBoolean v) { set(out1, v, null); }

  //endregion Property "out1"

  //region Property "out2"

  /**
   * Slot for the {@code out2} property.
   * Boolean output
   * @see #getOut2
   * @see #setOut2
   */
  public static final Property out2 = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out2} property.
   * Boolean output
   * @see #out2
   */
  public BStatusBoolean getOut2() { return (BStatusBoolean)get(out2); }

  /**
   * Set the {@code out2} property.
   * Boolean output
   * @see #out2
   */
  public void setOut2(BStatusBoolean v) { set(out2, v, null); }

  //endregion Property "out2"

  //region Property "out3"

  /**
   * Slot for the {@code out3} property.
   * Boolean output
   * @see #getOut3
   * @see #setOut3
   */
  public static final Property out3 = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out3} property.
   * Boolean output
   * @see #out3
   */
  public BStatusBoolean getOut3() { return (BStatusBoolean)get(out3); }

  /**
   * Set the {@code out3} property.
   * Boolean output
   * @see #out3
   */
  public void setOut3(BStatusBoolean v) { set(out3, v, null); }

  //endregion Property "out3"

  //region Property "out4"

  /**
   * Slot for the {@code out4} property.
   * Boolean output
   * @see #getOut4
   * @see #setOut4
   */
  public static final Property out4 = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out4} property.
   * Boolean output
   * @see #out4
   */
  public BStatusBoolean getOut4() { return (BStatusBoolean)get(out4); }

  /**
   * Set the {@code out4} property.
   * Boolean output
   * @see #out4
   */
  public void setOut4(BStatusBoolean v) { set(out4, v, null); }

  //endregion Property "out4"

  //region Property "out1Value"

  /**
   * Slot for the {@code out1Value} property.
   * Analogue value
   * @see #getOut1Value
   * @see #setOut1Value
   */
  public static final Property out1Value = newProperty(0, 4.8, null);

  /**
   * Get the {@code out1Value} property.
   * Analogue value
   * @see #out1Value
   */
  public double getOut1Value() { return getDouble(out1Value); }

  /**
   * Set the {@code out1Value} property.
   * Analogue value
   * @see #out1Value
   */
  public void setOut1Value(double v) { setDouble(out1Value, v, null); }

  //endregion Property "out1Value"

  //region Property "out2Value"

  /**
   * Slot for the {@code out2Value} property.
   * Analogue value
   * @see #getOut2Value
   * @see #setOut2Value
   */
  public static final Property out2Value = newProperty(0, 2.4, null);

  /**
   * Get the {@code out2Value} property.
   * Analogue value
   * @see #out2Value
   */
  public double getOut2Value() { return getDouble(out2Value); }

  /**
   * Set the {@code out2Value} property.
   * Analogue value
   * @see #out2Value
   */
  public void setOut2Value(double v) { setDouble(out2Value, v, null); }

  //endregion Property "out2Value"

  //region Property "out3Value"

  /**
   * Slot for the {@code out3Value} property.
   * Analogue value
   * @see #getOut3Value
   * @see #setOut3Value
   */
  public static final Property out3Value = newProperty(0, 1.2, null);

  /**
   * Get the {@code out3Value} property.
   * Analogue value
   * @see #out3Value
   */
  public double getOut3Value() { return getDouble(out3Value); }

  /**
   * Set the {@code out3Value} property.
   * Analogue value
   * @see #out3Value
   */
  public void setOut3Value(double v) { setDouble(out3Value, v, null); }

  //endregion Property "out3Value"

  //region Property "out4Value"

  /**
   * Slot for the {@code out4Value} property.
   * Analogue value
   * @see #getOut4Value
   * @see #setOut4Value
   */
  public static final Property out4Value = newProperty(0, 0.6, null);

  /**
   * Get the {@code out4Value} property.
   * Analogue value
   * @see #out4Value
   */
  public double getOut4Value() { return getDouble(out4Value); }

  /**
   * Set the {@code out4Value} property.
   * Analogue value
   * @see #out4Value
   */
  public void setOut4Value(double v) { setDouble(out4Value, v, null); }

  //endregion Property "out4Value"

  //region Property "deadBand"

  /**
   * Slot for the {@code deadBand} property.
   * The deadband
   * @see #getDeadBand
   * @see #setDeadBand
   */
  public static final Property deadBand = newProperty(0, 0.1, null);

  /**
   * Get the {@code deadBand} property.
   * The deadband
   * @see #deadBand
   */
  public double getDeadBand() { return getDouble(deadBand); }

  /**
   * Set the {@code deadBand} property.
   * The deadband
   * @see #deadBand
   */
  public void setDeadBand(double v) { setDouble(deadBand, v, null); }

  //endregion Property "deadBand"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * Execute the component
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code execute} action.
   * Execute the component
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDigitalInputDemux.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Called when the component is started
   */
  public void started() throws Exception
  {
    execute();
  }
    
  /**
   * Called when one of the properties is changed
   */
  public void changed(Property prop, Context cx)
  {
    if (isRunning())
    {
      if (prop.equals(in) || prop.equals(deadBand) ||
          prop.equals(out1Value) || prop.equals(out2Value) ||
          prop.equals(out3Value) || prop.equals(out4Value) || prop.equals(inOffset)) 
      {
        execute();
      }
    }
  }
  
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.getName().equals("propagateFlags"))
    {
      return PROPAGATE_FACETS;
    }
    else return super.getSlotFacets(slot);
  }
  
  public BIcon getIcon()
  {
    return icon;
  }
  
////////////////////////////////////////////////////////////////
// Execution
////////////////////////////////////////////////////////////////
 
  public void doExecute() throws Exception
  {
    // Input value
    double inVal = getIn().getValue();  
    
    if (getIn().getStatus().isNull())
      inVal = 0.00;
    else
      inVal += getInOffset();
    
    // Get output values
    double a = getOut1Value(); 
    double b = getOut2Value();
    double c = getOut3Value();
    double d = getOut4Value();   
    
    // Flags
    boolean aOut = false;
    boolean bOut = false;
    boolean cOut = false;
    boolean dOut = false;   
    
    // Efficiency
    tempDeadBand = getDeadBand();
    
    if (tempDeadBand > 0.00)
      tempDeadBand /= 2;
      
    if (isEqual(inVal, a))
    {
      aOut = true;      
    }
    else if (isEqual(inVal, (a + b)))  
    {
      aOut = true;
      bOut = true;
    }
    else if (isEqual(inVal, (a + c)))  
    {
      aOut = true;
      cOut = true;
    }
    else if (isEqual(inVal, (a + d)))  
    {
      aOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, (a + b + c)))  
    {
      aOut = true;
      bOut = true;
      cOut = true;
    }
    else if (isEqual(inVal, (a + b + d)))  
    {
      aOut = true;
      bOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, (a + c + d)))  
    {
      aOut = true;
      cOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, (a + b + c + d)))  
    {
      aOut = true;
      bOut = true;
      cOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, b))  
    {
      bOut = true;
    }
    else if (isEqual(inVal, (b + c)))  
    {
      bOut = true;
      cOut = true;
    }
    else if (isEqual(inVal, (b + d)))  
    {
      bOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, (b + c + d)))  
    {
      bOut = true;
      cOut = true;
      dOut = true;
    }
    else if (isEqual(inVal, c))  
    {
      cOut = true;
    }
    else if (isEqual(inVal, c + d))  
    {
      cOut = true; 
      dOut = true;
    }
    else if (isEqual(inVal, d))  
    {
      dOut = true;
    }
    else if (!isEqual(inVal, 0.00)) 
    {
      // Error 
      log.warning("Input value for digitalInputDemux: " + inVal);
    }
    
    getOut1().setValue(aOut);
    getOut2().setValue(bOut);
    getOut3().setValue(cOut);
    getOut4().setValue(dOut);
    
    // Propogate status
    BStatus newStatus = propagate(getIn().getStatus());
    
    getOut1().setStatus(newStatus);
    getOut2().setStatus(newStatus);
    getOut3().setStatus(newStatus);
    getOut4().setStatus(newStatus);
  }
  
  private boolean isEqual(double a, double b)
  {    
    double b1 = b + tempDeadBand;
    double b2 = b - tempDeadBand;
    
    return ((Math.abs(b1 - a) < 0.0001) || b1 > a) && ((Math.abs(b2 - a) < 0.0001) || b2 < a);
  } 
  
  /**
   * Create a new status by masking out only the standard
   * flags which should be propagated from inputs to outputs.
   * See PROPOGATE_MASK for the flags which are propagated.
   *
   * @return <code>make(s.getBits() & PROPOGATE_MASK)</code>
   */
  private BStatus propagate(BStatus s)
  {
    return BStatus.make(s.getBits() & getPropagateFlags().getBits());
  }
 
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////
  
  private double tempDeadBand = 0.00;
  private static final Log log = Log.getLog("kitControl.digitalInputDemux");
  
  private static final BIcon icon = BIcon.std("control/control.png");
  
  private static final BFacets PROPAGATE_FACETS = BFacets.make(BFacets.FIELD_EDITOR, BString.make("kitControl:PropagateFlagsFE"), BFacets.UX_FIELD_EDITOR, BString.make("kitControl:PropagateFlagsEditor"));
}
