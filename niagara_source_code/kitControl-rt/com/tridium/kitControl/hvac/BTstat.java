/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.hvac;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.kitControl.enums.*;
import com.tridium.kitControl.logic.*;


/**
 * BTstat models a two position thermostat
 * with setpoint and differential inputs. 
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2004
  * @version   $Revision: 29$ $Date: 3/30/2004 3:40:36 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Input Control Variable
 */
@NiagaraProperty(
  name = "cv",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
/*
 Input Setpoint
 */
@NiagaraProperty(
  name = "sp",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
/*
 Input Differential
 */
@NiagaraProperty(
  name = "diff",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0, BStatus.nullStatus)"
)
@NiagaraProperty(
  name = "action",
  type = "BLoopAction",
  defaultValue = "BLoopAction.direct"
)
/*
 Output set to null when in control
 */
@NiagaraProperty(
  name = "nullOnInControl",
  type = "boolean",
  defaultValue = "true"
)
public class BTstat
  extends BLogic
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.hvac.BTstat(2309328954)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "cv"

  /**
   * Slot for the {@code cv} property.
   * Input Control Variable
   * @see #getCv
   * @see #setCv
   */
  public static final Property cv = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code cv} property.
   * Input Control Variable
   * @see #cv
   */
  public BStatusNumeric getCv() { return (BStatusNumeric)get(cv); }

  /**
   * Set the {@code cv} property.
   * Input Control Variable
   * @see #cv
   */
  public void setCv(BStatusNumeric v) { set(cv, v, null); }

  //endregion Property "cv"

  //region Property "sp"

  /**
   * Slot for the {@code sp} property.
   * Input Setpoint
   * @see #getSp
   * @see #setSp
   */
  public static final Property sp = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code sp} property.
   * Input Setpoint
   * @see #sp
   */
  public BStatusNumeric getSp() { return (BStatusNumeric)get(sp); }

  /**
   * Set the {@code sp} property.
   * Input Setpoint
   * @see #sp
   */
  public void setSp(BStatusNumeric v) { set(sp, v, null); }

  //endregion Property "sp"

  //region Property "diff"

  /**
   * Slot for the {@code diff} property.
   * Input Differential
   * @see #getDiff
   * @see #setDiff
   */
  public static final Property diff = newProperty(0, new BStatusNumeric(0, BStatus.nullStatus), null);

  /**
   * Get the {@code diff} property.
   * Input Differential
   * @see #diff
   */
  public BStatusNumeric getDiff() { return (BStatusNumeric)get(diff); }

  /**
   * Set the {@code diff} property.
   * Input Differential
   * @see #diff
   */
  public void setDiff(BStatusNumeric v) { set(diff, v, null); }

  //endregion Property "diff"

  //region Property "action"

  /**
   * Slot for the {@code action} property.
   * @see #getAction
   * @see #setAction
   */
  public static final Property action = newProperty(0, BLoopAction.direct, null);

  /**
   * Get the {@code action} property.
   * @see #action
   */
  public BLoopAction getAction() { return (BLoopAction)get(action); }

  /**
   * Set the {@code action} property.
   * @see #action
   */
  public void setAction(BLoopAction v) { set(action, v, null); }

  //endregion Property "action"

  //region Property "nullOnInControl"

  /**
   * Slot for the {@code nullOnInControl} property.
   * Output set to null when in control
   * @see #getNullOnInControl
   * @see #setNullOnInControl
   */
  public static final Property nullOnInControl = newProperty(0, true, null);

  /**
   * Get the {@code nullOnInControl} property.
   * Output set to null when in control
   * @see #nullOnInControl
   */
  public boolean getNullOnInControl() { return getBoolean(nullOnInControl); }

  /**
   * Set the {@code nullOnInControl} property.
   * Output set to null when in control
   * @see #nullOnInControl
   */
  public void setNullOnInControl(boolean v) { setBoolean(nullOnInControl, v, null); }

  //endregion Property "nullOnInControl"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTstat.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void onExecute(BStatusValue o, Context cx)
  {
    BStatusNumeric controlVariable = getCv();
    BStatusNumeric setpoint = getSp();
    BStatusBoolean out = (BStatusBoolean)o;

    BStatus sa = controlVariable.getStatus();
    BStatus sb = setpoint.getStatus();

    //  If either input is null, force the output
    //  to null
    if (sa.isNull() || sb.isNull())
    { 
      out.setValue(false);
      out.setStatus(BStatus.nullStatus);            
    }
    else
    {
      out.setStatus(propagate(BStatus.make(sa.getBits() | sb.getBits())));
  
      //  If either input is invalid, force the output
      //  to false
      if (!sa.isValid() || !sb.isValid())        
        out.setValue(false);
      else
        out.setValue(calculate());         
      if(getNullOnInactive() && !(out.getValue()) )
        out.setStatusNull(true);
      else if(getNullOnInControl() && inControl)
    	out.setStatusNull(true);
    }    
  }

  protected boolean calculate()
  {
    double halfDiff = (getDiff().getValue()/2.0d);
    double highValue = getSp().getValue() + halfDiff;
    double lowValue = getSp().getValue() - halfDiff;
    boolean returnValue = getOut().getValue();
    if (getAction() != BLoopAction.direct)
      returnValue = !returnValue;
    double currentValue = getCv().getValue();
    if(currentValue >= highValue)
      returnValue = true;
    else if (currentValue <= lowValue) 
      returnValue = false;

    if (getAction() != BLoopAction.direct)
      returnValue = !returnValue;
    inControl = currentValue > lowValue && currentValue < highValue;
    return returnValue;
  }

  boolean inControl = false;
}
