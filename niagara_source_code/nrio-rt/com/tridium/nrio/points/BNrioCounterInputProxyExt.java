/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BNumericPoint;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLong;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.components.BINrioIoStatus;
import com.tridium.nrio.enums.BNrio16CounterSelectEnum;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
import com.tridium.nrio.types.BAbstractRateType;
import com.tridium.nrio.types.BFixedWindowRateType;

/**
 * @author    Bill Smith
 * @creation  3 Feb 2004
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "uiType",
  type = "BEnum",
  defaultValue = "BUniversalInputTypeEnum.di_HighSpeed",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "outputSelect",
  type = "BNrio16CounterSelectEnum",
  defaultValue = "BNrio16CounterSelectEnum.count"
)
@NiagaraProperty(
  name = "total",
  type = "long",
  defaultValue = "-1",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "rate",
  type = "double",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "rateCalcType",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.make(\"nrio:FixedWindowRateType\")",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, BString.make(\"nrio:AbstractRateType\"))")
)
@NiagaraProperty(
  name = "rateCalc",
  type = "BAbstractRateType",
  defaultValue = "new BFixedWindowRateType()",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "rateCalcTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.DEFAULT",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraAction(
  name = "reset"
)
@NiagaraAction(
  name = "set",
  parameterType = "BLong",
  defaultValue = "BLong.make(0)"
)
@NiagaraAction(
  name = "recalculateRate",
  flags = Flags.HIDDEN
)
public class BNrioCounterInputProxyExt extends BUiProxyExt
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioCounterInputProxyExt(3773682054)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "uiType"

  /**
   * Slot for the {@code uiType} property.
   * @see #getUiType
   * @see #setUiType
   */
  public static final Property uiType = newProperty(Flags.READONLY, BUniversalInputTypeEnum.di_HighSpeed, null);

  //endregion Property "uiType"

  //region Property "outputSelect"

  /**
   * Slot for the {@code outputSelect} property.
   * @see #getOutputSelect
   * @see #setOutputSelect
   */
  public static final Property outputSelect = newProperty(0, BNrio16CounterSelectEnum.count, null);

  /**
   * Get the {@code outputSelect} property.
   * @see #outputSelect
   */
  public BNrio16CounterSelectEnum getOutputSelect() { return (BNrio16CounterSelectEnum)get(outputSelect); }

  /**
   * Set the {@code outputSelect} property.
   * @see #outputSelect
   */
  public void setOutputSelect(BNrio16CounterSelectEnum v) { set(outputSelect, v, null); }

  //endregion Property "outputSelect"

  //region Property "total"

  /**
   * Slot for the {@code total} property.
   * @see #getTotal
   * @see #setTotal
   */
  public static final Property total = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code total} property.
   * @see #total
   */
  public long getTotal() { return getLong(total); }

  /**
   * Set the {@code total} property.
   * @see #total
   */
  public void setTotal(long v) { setLong(total, v, null); }

  //endregion Property "total"

  //region Property "rate"

  /**
   * Slot for the {@code rate} property.
   * @see #getRate
   * @see #setRate
   */
  public static final Property rate = newProperty(Flags.READONLY | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code rate} property.
   * @see #rate
   */
  public double getRate() { return getDouble(rate); }

  /**
   * Set the {@code rate} property.
   * @see #rate
   */
  public void setRate(double v) { setDouble(rate, v, null); }

  //endregion Property "rate"

  //region Property "rateCalcType"

  /**
   * Slot for the {@code rateCalcType} property.
   * @see #getRateCalcType
   * @see #setRateCalcType
   */
  public static final Property rateCalcType = newProperty(0, BTypeSpec.make("nrio:FixedWindowRateType"), BFacets.make(BFacets.TARGET_TYPE, BString.make("nrio:AbstractRateType")));

  /**
   * Get the {@code rateCalcType} property.
   * @see #rateCalcType
   */
  public BTypeSpec getRateCalcType() { return (BTypeSpec)get(rateCalcType); }

  /**
   * Set the {@code rateCalcType} property.
   * @see #rateCalcType
   */
  public void setRateCalcType(BTypeSpec v) { set(rateCalcType, v, null); }

  //endregion Property "rateCalcType"

  //region Property "rateCalc"

  /**
   * Slot for the {@code rateCalc} property.
   * @see #getRateCalc
   * @see #setRateCalc
   */
  public static final Property rateCalc = newProperty(Flags.READONLY, new BFixedWindowRateType(), null);

  /**
   * Get the {@code rateCalc} property.
   * @see #rateCalc
   */
  public BAbstractRateType getRateCalc() { return (BAbstractRateType)get(rateCalc); }

  /**
   * Set the {@code rateCalc} property.
   * @see #rateCalc
   */
  public void setRateCalc(BAbstractRateType v) { set(rateCalc, v, null); }

  //endregion Property "rateCalc"

  //region Property "rateCalcTime"

  /**
   * Slot for the {@code rateCalcTime} property.
   * @see #getRateCalcTime
   * @see #setRateCalcTime
   */
  public static final Property rateCalcTime = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.DEFAULT, null);

  /**
   * Get the {@code rateCalcTime} property.
   * @see #rateCalcTime
   */
  public BAbsTime getRateCalcTime() { return (BAbsTime)get(rateCalcTime); }

  /**
   * Set the {@code rateCalcTime} property.
   * @see #rateCalcTime
   */
  public void setRateCalcTime(BAbsTime v) { set(rateCalcTime, v, null); }

  //endregion Property "rateCalcTime"

  //region Action "reset"

  /**
   * Slot for the {@code reset} action.
   * @see #reset()
   */
  public static final Action reset = newAction(0, null);

  /**
   * Invoke the {@code reset} action.
   * @see #reset
   */
  public void reset() { invoke(reset, null, null); }

  //endregion Action "reset"

  //region Action "set"

  /**
   * Slot for the {@code set} action.
   * @see #set(BLong parameter)
   */
  public static final Action set = newAction(0, BLong.make(0), null);

  /**
   * Invoke the {@code set} action.
   * @see #set
   */
  public void set(BLong parameter) { invoke(set, parameter, null); }

  //endregion Action "set"

  //region Action "recalculateRate"

  /**
   * Slot for the {@code recalculateRate} action.
   * @see #recalculateRate()
   */
  public static final Action recalculateRate = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code recalculateRate} action.
   * @see #recalculateRate
   */
  public void recalculateRate() { invoke(recalculateRate, null, null); }

  //endregion Action "recalculateRate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioCounterInputProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  public void started()
  throws Exception
  {
    super.started();
    if(isRunning())
    {
      if(getTotal() < 0)
        return;
      BNrio16Module device = (BNrio16Module)getDevice();
      // init ioStatus total count.
      int instance = getInstance();
      BINrioIoStatus ioStatus = (BINrioIoStatus)device.getIoStatus();
      if(device instanceof BNrio34Module)
      {
        if(instance > 8)
        {
          ioStatus = (BINrioIoStatus)((BNrio34Module)device).getIo34Sec().getIoStatus();
          instance = instance-8;
        }
      }
      ioStatus.setTotalCounts(instance, getTotal());
      doRecalculateRate();
    }    
  }

  
  public boolean isParentLegal(BComponent parent)
  {
    if (super.isParentLegal(parent))
      if (parent instanceof BNumericPoint)
        return true;

    return false;
  }

  public BReadWriteMode getMode()
  {
    return BReadWriteMode.readonly;
  }

  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    
    if (isRunning()){
      if (prop.equals(rateCalcType)){
        BAbstractRateType type = (BAbstractRateType) getRateCalcType().getInstance();
        getRateCalc().cleanupType();
        setRateCalc(type);      
        getRateCalc().initType();
        setStale(true, null);  
      }
      
      else if (prop.equals(outputSelect))
      {
        setStale(true, null); 
      }
      
      else if(prop.equals(total))
      {
      	doRecalculateRate();
      }
    }
  }

//  public synchronized void read()
//  {
//    if (!pointSynchronized()) return;
//
//    try{
//      BStatusNumeric value = readNumericValue();
//      if (getOutputSelect() == BNrio16CounterSelectEnum.count)
//        readOk(value);        
//    }
//    catch(Exception e){
//      readFail(e.getMessage());
//    }      
//  }

  public void ioValueChanged()
  {
    BINrioIoStatus ioStatus = (BINrioIoStatus)((BNrio16Module)getDevice()).getIoStatus();
    int maxUiInstance = ioStatus.getMaxUiInstance();
    if(getUiType().getOrdinal() != BUniversalInputTypeEnum.DI_HIGH_SPEED  || getInstance() < 1 || getInstance() > maxUiInstance)
    {
      readFail("readFail.invalidInstanceOrData");
    }
    else
    {
      try
      {
        long value = ioStatus.getTotalCounts(getInstance());

        // if total hasn't changed force calculate rate to cause
        // stale flag to be cleared.
        if(value == getTotal())
          recalculateRate();
        else  // set total and changed will cause rate to be recalculated.
          setTotal(value);
      }
      catch(Exception e)
      {
        readFail(e.getMessage());
      }
    }
  }
  
  
////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////  
  
  public void doReset()
  {
    doSet(BLong.make(0));
  }
 
  public synchronized void doSet(BLong param)
  {
    long setValue = param.getLong();
    BNrio16Module device = (BNrio16Module)getDevice();
    BINrioIoStatus ioStatus = (BINrioIoStatus)device.getIoStatus();
    int instance = getInstance();
    if(device instanceof BNrio34Module)
    {
      if (instance > 8) // would have to be an IO34 Secondary device.
      {
        ioStatus = (BINrioIoStatus)((BNrio34Module)device).getIo34Sec().getIoStatus();
        instance = instance-8;
      }
    }
    ioStatus.setTotalCounts(instance, setValue);
    getRateCalc().resetRate();
    setTotal(setValue);
    
//    read();
  }  

  public void doRecalculateRate()
  {
//    if (!pointSynchronized()) return;

    try{
//      readNumericValue();
      BStatusNumeric value = getRateCalc().calculateRate(getTotal());
      if (value != null)
      {  
        setRate(value.getValue());     
        if (getOutputSelect() == BNrio16CounterSelectEnum.rate)
          readOk(value);
        else
        	readOk(new BStatusNumeric(getTotal()));
      }
      else
        if (getOutputSelect() == BNrio16CounterSelectEnum.rate)
          setStale(true, null);
    }
    catch(Exception e){
      readFail(e.getMessage());
    }      
  }
  
////////////////////////////////////////////////////////////////
// Abstract
////////////////////////////////////////////////////////////////  

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////    

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
}
