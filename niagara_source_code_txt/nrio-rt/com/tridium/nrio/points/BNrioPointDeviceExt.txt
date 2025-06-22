/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import java.util.logging.Logger;

import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.fault.BStatusFaultAlgorithm;
import javax.baja.alarm.ext.offnormal.BBooleanChangeOfStateAlgorithm;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.driver.point.conv.BReversePolarityConversion;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.comm.NrioUnsolicitedReceive;
import com.tridium.nrio.components.BIoStatus;
import com.tridium.nrio.components.BSdiValueConfig;
import com.tridium.nrio.enums.BSdiEnum;


/**
 * BNrioPointDeviceExt is the implementation of BPointDeviceExt
 * which provides a container for Modbus Ascii proxy points.
 *
 * @author    Andy Saunders       
 * @creation  22 Jan 02
 * @version   $Revision$ $Date: 8/29/2005 10:21:13 AM$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraAction(
  name = "pushToPoints",
  flags = Flags.HIDDEN
)

public class BNrioPointDeviceExt
  extends BPointDeviceExt
  implements com.tridium.nrio.messages.NrioMessageConst
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioPointDeviceExt(3509304711)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "pushToPoints"

  /**
   * Slot for the {@code pushToPoints} action.
   * @see #pushToPoints()
   */
  public static final Action pushToPoints = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code pushToPoints} action.
   * @see #pushToPoints
   */
  public void pushToPoints() { invoke(pushToPoints, null, null); }

  //endregion Action "pushToPoints"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the parent device Type (BNrioDevice).
   */
  public Type getDeviceType()
  {
    return BNrioDevice.TYPE;
  }

  /**
   * Returns the BGpOutPutProxyExt type.
   */
  public Type getProxyExtType()
  {
    return BNrioProxyExt.TYPE;
  }
  
  /**
   * Returns the BNrioPointFolder type.
   */
  public Type getPointFolderType()
  {
    return BNrio16PointFolder.TYPE;
  }

  /**
   * BNrioPointDeviceExt can only be contained in a BNrioDevice.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNrioDevice;
  }
  

  public void doPushToPoints()
  {
    long ptpDelta = Clock.ticks() - lastPushToPointsTicks;
    if(ptpLog == null)
    {
      ptpLog = Logger.getLogger(getNrioNetwork().getName() + '.' + getNrioDevice().getName() + ".ptp");
    }
//    ptpLog.fine("entry delta: " +ptpDelta + " thread: " + Thread.currentThread().getName());
    if(ptpDelta < getNrioNetwork().getMinPushTime())
    {
      if(ptpTicket == null || ptpTicket.isExpired())
      {
//        System.out.println(this.getParent().getName() + " - setting ptpTicket with: " + (MIN_PUSH_TO_POINTS_TICKS-ptpDelta));
        ptpTicket = Clock.schedule(this.asComponent(), BRelTime.make(getNrioNetwork().getMinPushTime()-ptpDelta), pushToPoints, null);
      }
//      else
//      {
//        System.out.println(this.getParent().getName() + " - wtf: " + (MIN_PUSH_TO_POINTS_TICKS-ptpDelta));
//      }
    }
    else
    {
      if(ptpLog == null)
      {
        ptpLog = Logger.getLogger(getNrioNetwork().getName() + '.' + getNrioDevice().getName() + ".ptp");
      }
      ptpLog.fine("delta: " +(Clock.ticks()-lastPushToPointsTicks) + " thread: " + Thread.currentThread().getName());
      setDynamicPoints();
      getNrioNetwork().setProcessedUnsolicitedMsgCount(getNrioNetwork().incUnsolicitedProcessedCount());
      lastPushToPointsTicks = Clock.ticks();

    }
  }
  /**
   * added
   */
  
  public void setDynamicPoints()
  {
    BControlPoint[] cps = this.getPoints();
    for(int i = 0; i < cps.length; i++)
    {
      //if( cps[i].getPropertyInParent().isFrozen() )
      //  continue;
      BAbstractProxyExt proxy = cps[i].getProxyExt();
      if(proxy instanceof BNrioProxyExt)
      {
        BNrioProxyExt proxyExt = (BNrioProxyExt)proxy;
        
        if(!proxyExt.getEnabled() || getDevice().getStatus().isDisabled())
        {
          continue;
        }
        int instance = proxyExt.getInstance();
        if(cps[i] instanceof BBooleanPoint  && !cps[i].isWritablePoint())
        {
          if(proxyExt.getIsSdi())
          {
            int value = getSdiValue(instance);
            if(value < 0)
              proxyExt.readFail(getLexicon().getText("readFail.invalidInstanceOrData"));
            else
            {
              BSdiEnum enumValue = getSdiEnumValue(value);
              BStatusBoolean sbValue = (BStatusBoolean)proxyExt.getReadValue().newCopy();
              String cutText = getLexicon().getText("sdi.fault.cut");
              String shortedText = getLexicon().getText("sdi.fault.shorted");
              switch(enumValue.getOrdinal())
              {
              case BSdiEnum.CUT    : sbValue.setValue(false); sbValue.setStatus(BStatus.make(BStatus.FAULT, BFacets.make(cutText, true))); break;
              case BSdiEnum.SHORTED: sbValue.setValue(false); sbValue.setStatus(BStatus.make(BStatus.FAULT, BFacets.make(shortedText, true))); break;
              case BSdiEnum.OPEN   : sbValue.setValue(false); sbValue.setStatus(BStatus.ok); break;
              case BSdiEnum.CLOSED : sbValue.setValue(true); sbValue.setStatus(BStatus.ok); break;
              }
              proxyExt.readOk(sbValue);
            }

          }
          else
          {
            boolean value = false;
            BIoStatus ioStatus = (BIoStatus)getNrioDevice().getIoStatus();
            switch (instance) 
            {
            case 1: proxyExt.readOk(new BStatusBoolean(ioStatus.getDi1())); break;
            case 2: proxyExt.readOk(new BStatusBoolean(ioStatus.getDi2())); break;
            case 3: proxyExt.readOk(new BStatusBoolean(ioStatus.getDi3())); break;
            default: proxyExt.readFail(getLexicon().getText("readFail.invalidInstance")); continue;
            }
          }
        }
        else
        {
          int value = getSdiValue(instance);
          if(value < 0 && !cps[i].isWritablePoint())
            proxyExt.readFail(getLexicon().getText("readFail.invalidInstanceOrData"));
          else if(cps[i] instanceof BEnumPoint)
          {
             proxyExt.readOk(new BStatusEnum(getSdiEnumValue(value)));
          }  
          else if(cps[i] instanceof BNumericPoint)
          {
             proxyExt.readOk(new BStatusNumeric((double)value));
          }  
        }
      }
    }
  }

  private BSdiEnum getSdiEnumValue(int value)
  {
    if(sdiValues == null)
      sdiValues = getNrioNetwork().getSdiValueConfig();
     return sdiValues.getEnumValue(value);
  }

  private int getSdiValue(int instance)
  {
 BIoStatus ioStatus = (BIoStatus)getNrioDevice().getIoStatus();
    switch (instance) 
    {
    case 1: return ioStatus.getSdi1(); 
    case 2: return ioStatus.getSdi2(); 
    case 3: return ioStatus.getSdi3(); 
    case 4: return ioStatus.getSdi4(); 
    case 5: return ioStatus.getSdi5(); 
    case 6: return ioStatus.getSdi6(); 
    case 7: return ioStatus.getSdi7(); 
    case 8: return ioStatus.getSdi8(); 
    default: return -1;
    }
  }

  public BNrioDevice getNrioDevice()
  {
    return (BNrioDevice)getDevice();
  }

  public BNrioNetwork getNrioNetwork()
  {
    return (BNrioNetwork)getNetwork();
  }

  /*
  *  create a BooleanWritable point that maps to a relay with given name.
  *     It will create the point, set the proxyExt, set the proxyExt.instance,
  *     , create the facets for active and inactive text, and set the conversion
  *     if reverse action.
  */
  public void addRoPoint(String name, int instance, 
                         String trueText, String falseText,
                         boolean isReverse)
  throws Exception
  {
    BBooleanWritable point = (BBooleanWritable)get(add(name, new BBooleanWritable()));
    initBooleanProxyExt(point, instance, false, false, trueText, falseText, isReverse,
                        false, false, true, "");
    point.getFallback().setValue(false);
    point.getFallback().setStatusNull(false);
  }

  /*
  *  create a Boolean point that maps to a Digital Input with given name.
  *     It will create the point, set the proxyExt, set the proxyExt.instance,
  *     , create the facets for active and inactive text, set the conversion
  *     if reverse action. It will also add an alarm extension if desired.
  */
  public void addDIPoint(String name, int instance, 
                         String trueText, String falseText,
                         boolean isReverse,
                         boolean addAlarm, boolean alarmState)
  throws Exception
  {
    BBooleanPoint point = (BBooleanPoint)get(add(name, new BBooleanPoint()));
    initBooleanProxyExt(point, instance, false, false, trueText, falseText, isReverse,
                        addAlarm, alarmState, true,
                        "%parent.parent.parent.getDisplayName%.%parent.displayName%");

  }

  /*
  *  create a Boolean point that maps to a Supervised Digital Input with given name.
  *     It will create the point, set the proxyExt, set the proxyExt.instance,
  *     , create the facets for active and inactive text, set the conversion
  *     if reverse action. It will also add an alarm extension if desired.
  */
  public void addSDIPoint(String name, int instance, 
                          String trueText, String falseText,
                          boolean isReverse,
                          boolean addAlarm, boolean alarmState)
  throws Exception
  {
    BBooleanPoint point = (BBooleanPoint)get(add(name, new BBooleanPoint()));
    initBooleanProxyExt(point, instance, false, true, trueText, falseText, isReverse,
                        addAlarm, alarmState, true,
                        "%parent.parent.parent.getDisplayName%.%parent.displayName%");

  }

  public void initBooleanProxyExt(BBooleanPoint point, int instance,
                                  boolean isStrike, boolean isSdi,
                                  String trueText, String falseText, 
                                  boolean isReverse, 
                                  boolean addAlarm, boolean alarmState,
                                  boolean isEnabledOnDefault,
                                  String sourceName)
  {
    if( !(point.getProxyExt() instanceof BNrioProxyExt) )
    {
      BNrioProxyExt proxy = new BNrioProxyExt();
      proxy.setInstance(instance);
      proxy.setIsStrike(isStrike);
      proxy.setIsSdi(isSdi);
      proxy.setReadValue(new BStatusBoolean());
      proxy.setWriteValue(new BStatusBoolean());
      
      if(isReverse)
        proxy.setConversion(BReversePolarityConversion.DEFAULT);
      if(!isEnabledOnDefault)
       proxy.setEnabled(false);
      point.setProxyExt(proxy);
      point.setFacets(BFacets.makeBoolean(trueText, falseText));
    }
    if(isSdi)
    {
      BAlarmSourceExt faultExt = null;
      try
      {
        faultExt = (BAlarmSourceExt)point.get("FaultExt");
      }
      catch(Exception e){}
      
      if( faultExt == null )
      {
        faultExt = new BAlarmSourceExt();
        if( !sourceName.equals("") )
          faultExt.setSourceName(BFormat.make(sourceName));
        BStatusFaultAlgorithm faultAlgo = new BStatusFaultAlgorithm();
        faultAlgo.setFaultValues(BStatus.fault);
        faultExt.setFaultAlgorithm(faultAlgo);
        faultExt.setToFaultText(BFormat.make(getLexicon().getText("sdi.toFaultText")));
        faultExt.setToNormalText(BFormat.make(getLexicon().getText("sdi.toNormalText")));
        
        point.add("FaultExt", faultExt);
      }
      else if(!(faultExt.getFaultAlgorithm() instanceof BStatusFaultAlgorithm) )
      {
        BStatusFaultAlgorithm faultAlgo = new BStatusFaultAlgorithm();
        faultAlgo.setFaultValues(BStatus.fault);
        faultExt.setFaultAlgorithm(faultAlgo);
      }      
    }
    
    if(addAlarm)
    {
      BAlarmSourceExt alarmExt = null;
      try
      {
        alarmExt = (BAlarmSourceExt)point.get("AlarmExt");
      }
      catch(Exception e){}
      
      if( alarmExt == null )
      {
        alarmExt = new BAlarmSourceExt();
        alarmExt.setOffnormalAlgorithm(new BBooleanChangeOfStateAlgorithm());
        ((BBooleanChangeOfStateAlgorithm)alarmExt.getOffnormalAlgorithm()).setAlarmValue(alarmState);
        if( !sourceName.equals("") )
          alarmExt.setSourceName(BFormat.make(sourceName));
        point.add("AlarmExt", alarmExt);
      } 
      else if( ((BBooleanChangeOfStateAlgorithm)alarmExt.getOffnormalAlgorithm()).getAlarmValue() != alarmState)
        ((BBooleanChangeOfStateAlgorithm)alarmExt.getOffnormalAlgorithm()).setAlarmValue(alarmState);
    }
  }
  
  
//  public String getDisplayName(Slot slot, Context cx)
//  {
//    if(slot.isProperty() && 
//    (slot.asProperty().getType().is(BNrioDoor.TYPE) ||
//     slot.asProperty().getType().is(BNrioReader.TYPE) ||
//     slot.asProperty().getType().is(BNrioElevator.TYPE)))
//    {    
//      String locationString = ((BComponent)get(slot.asProperty())).get("description").toString();
//  
//      if( locationString.length() == 0)
//        return super.getDisplayName(slot, cx);
//      return SlotPath.unescape(locationString);
//    }
//    return super.getDisplayName(slot, cx);
//  }
  
  BSdiValueConfig sdiValues;
  long lastPushToPointsTicks = 0;
  Clock.Ticket ptpTicket = null;

//  public long MIN_PUSH_TO_POINTS_TICKS = 300;

  Logger ptpLog = null;
}
