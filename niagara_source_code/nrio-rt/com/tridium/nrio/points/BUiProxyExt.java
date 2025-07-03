/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.basicdriver.util.BIBasicPollable;
import com.tridium.nrio.BIWritable;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.components.BINrioIoStatus;
import com.tridium.nrio.components.BNrio16Status;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
import com.tridium.nrio.messages.NrioMessageConst;


/**
 *
 * @author    Andy Saunders
 * @creation  21 Jan 02
 * @version   $Revision$ $Date: 8/29/2005 10:21:13 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "uiType",
  type = "BEnum",
  defaultValue = "BUniversalInputTypeEnum.undefined"
)
public class BUiProxyExt
  extends BNrio16ProxyExt
  implements BIBasicPollable,
             BIWritable,
             NrioMessageConst
{ 

////////////////////////////////////////////////////////////////
// Statics
////////////////////////////////////////////////////////////////
  
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BUiProxyExt(349494729)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "uiType"

  /**
   * Slot for the {@code uiType} property.
   * @see #getUiType
   * @see #setUiType
   */
  public static final Property uiType = newProperty(0, BUniversalInputTypeEnum.undefined, null);

  /**
   * Get the {@code uiType} property.
   * @see #uiType
   */
  public BEnum getUiType() { return (BEnum)get(uiType); }

  /**
   * Set the {@code uiType} property.
   * @see #uiType
   */
  public void setUiType(BEnum v) { set(uiType, v, null); }

  //endregion Property "uiType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUiProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void started()
  throws Exception
  {
    super.started();
    if(isRunning())
    {
      if(Sys.atSteadyState())
      {
        // it will come through this code if the parent point is added
        // to a running station.
        BNrioNetwork network = (BNrioNetwork)getNetwork();
        //network.getUnsolicitedReceive().addProxyExt(this);
        BNrio16Module device = (BNrio16Module)getDevice();
        device.setUiConfig(getInstance(), getUiType().getOrdinal());
        // init undoInstance;
        undoInstance = getInstance();
        // nccb-33499 e333968 03/26/2018
        // clear total counts when the point is added (duplicated)
        int instance = undoInstance;
        BINrioIoStatus ioStatus = (BINrioIoStatus)device.getIoStatus();
        if(device instanceof BNrio34Module)
        {
          if(instance > 8)
          {
            ioStatus = (BINrioIoStatus)((BNrio34Module)device).getIo34Sec().getIoStatus();
            instance = instance-8;
          }
        }
        // clear total count in the parent device ioStatus structure.
        ioStatus.setTotalCounts(instance, 0);
        // end nccb-333499 e333968 03/26/2018
      }
    }    
  }

  public void stopped()
  throws Exception
  {
    if(isRunning())
    {
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      network.getPollScheduler().unsubscribe(this);
    }
    super.stopped();
  }
  public void atSteadyState()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    //network.getUnsolicitedReceive().addProxyExt(this);
    BNrio16Module device = (BNrio16Module)getDevice();
    device.setUiConfig(getInstance(), getUiType().getOrdinal());
    undoInstance = getInstance();

    // nccb-333499 e333968 03/26/2018
    // if this isn't a CounterInputProxyExt, clear total count in the parent device ioStatus structure.
    if( !(this instanceof BNrioCounterInputProxyExt))
    {
      int instance = undoInstance;
      BINrioIoStatus ioStatus = (BINrioIoStatus)device.getIoStatus();
      if (device instanceof BNrio34Module)
      {
        if (instance > 8)
        {
          ioStatus = (BINrioIoStatus)((BNrio34Module)device).getIo34Sec().getIoStatus();
          instance = instance - 8;
        }
      }
      ioStatus.setTotalCounts(instance, 0);
    }
    // end nccb-333499 e333968 03/26/2018
  }

  public boolean requiresPointSubscription()
  {
    return false;
  }

  /**
   * Get the parent PointDeviceExt type this proxy 
   * extension belongs under (and by deduction which
   * device and network).
   */
  public Type getDeviceExtType()
  {
    return BNrioPointDeviceExt.TYPE;
  }
  
  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public BReadWriteMode getMode()
  {
    return getParentPoint().isWritablePoint() ? BReadWriteMode.readWrite : BReadWriteMode.readonly;
  }  

  public void doReadPoint()
  {
    read();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /**
   * This callback is made when the point enters a subscribed 
   * state based on the current status and tuning.  The driver 
   * should register for changes or begin polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.  The result of reads should be to call the
   * readOk() or readFail() method.
   */
  public void readSubscribed(Context cx)
    throws Exception
  {
  }
  
  /**
   * This callback is made when the point exits the subscribed
   * state based on the current status and tuning.  The driver
   * should unregister for changes of cease polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.
   */
  public void readUnsubscribed(Context cx)
    throws Exception
  {
  }

  /**
   * Check for a data address change, and if detected, set the point stale
   * and request a new write, if the point is writable.
   */
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if (isRunning() && context != Context.decoding)
    {
      if(prop.equals(instance))
      {
        BControlPoint conflictPoint = device().checkForProxyExtConflicts(getParentPoint());
        if(getInstance() < 1 || getInstance() > 8)
        	readFail(getLexicon().getText("readFail.invalidInstanceOrData"));
        else if(conflictPoint != null || getInstance() == 0 || getInstance() > 8)
        {
          readFail(getLexicon().getText("readFail.pointInstanceConflict") + " " + conflictPoint.getName());
          setInstance(undoInstance);
        }
        else
        {
          undoInstance = getInstance();
        }
        
        device().setUiConfig(getInstance(), getUiType().getOrdinal());
        device().updateProxyValues();
      }
      else if(prop.equals(enabled))
      {
        if(getEnabled())
        {
          BControlPoint conflictPoint = device().checkForProxyExtConflicts(getParentPoint()); 
          if(conflictPoint != null)
          {
            readFail(getLexicon().getText("readFail.pointInstanceConflict") + " " + conflictPoint.getName());
            setEnabled(false);
          }
          else
          {
            device().setUiConfig(getInstance(), getUiType().getOrdinal());
            readOk(getReadValue());
          }
          device().updateProxyValues();
        }
      }
      else if(prop.equals(uiType))
      {
          device().setUiConfig(getInstance(), getUiType().getOrdinal());
      }
    }
    /*
    if (prop == dataAddress)
    {
      setStale(true, null);
      //if (getParentPoint().isWritablePoint())
      //  getTuning().writeDesired();
    }
    */
  }

  /**
   * Override implementation of <code>BIBasicPollable</code>.
   * Causes communication to read the value of the
   * point from the GeM6 device.
   */
  public void poll()
  {
    BNrioNetwork network = network();
    if (network.getLog().isTraceOn()) network.getLog().trace(this + ".poll()");
      
    read();
//    if(!getStatus().isStale())
//      network.getPollScheduler().unsubscribe(this);
  }
  

 /**
  * This method will read data from ioStatus BBlob property of the parent device.
  */
  private void read()
  {

    if(getParentPoint() instanceof BBooleanWritable)
      readOk(getWriteValue());
    else
    {
      BINrioIoStatus ioStatus = (BINrioIoStatus)((BNrio16Module)getDevice()).getIoStatus();
      if(ioStatus.getIoStatus().length() != 0)
        ioValueChanged();
      else
      {
        readReset();
      }
    }
  } 
  
  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  The value to write is the current
   * value of the writeValue property.  Any IO should be done 
   * asynchronously on another thread - never block the calling 
   * thread.  If the write is enqueued then return true and call 
   * writeOk() or writeFail() once it has been processed.  If the 
   * write is canceled immediately for other reasons then return false. 
   *
   * @return true if a write is now pending
   */
  public boolean write(Context cx)
    throws Exception
  {
	System.out.println("**** subclass should override writeData *******");
	return false;
  }

  /**
   * Callback from asynchronous thread to 
   * send a write to the device.
   */
  public void writeData(BStatusValue out)
  {
    System.out.println("**** subclass should override writeData *******");
  } 

  /**
   * Subclasses must implement this method.  
   * The subclass must calculate the appropriate BStatusValue from 
   * the integer rawValue provided and call ReadOk().
   */
  
  public void ioValueChanged()
  {
    System.out.println(" ***********  subclass must override ioValueChanged method ********");
  }
  
////////////////////////////////////////////////////////////////
//Status
////////////////////////////////////////////////////////////////
 
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////
  
 /**
  * Get a string representation of this extension.
  */
  public String toString(Context cx) 
  { 
    return super.toString(cx);
  }
  
////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////  

  private BNrio16Module device()
  {
    return (BNrio16Module)getDevice();
  }
  
  private BNrioNetwork network()
  {
    return (BNrioNetwork)(getDevice().getNetwork());
  }
  
  public boolean isBoolean()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusBoolean;
  }

  public boolean isEnum()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusEnum;
  }


  public static final byte[] ACTIVE_DATA = {(byte)1 };
  public static final byte[] INACTIVE_DATA = {(byte)0 };
  private int undoInstance = -1;
}
