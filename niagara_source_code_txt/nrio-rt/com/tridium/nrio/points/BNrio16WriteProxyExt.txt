/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BControlPoint;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.basicdriver.util.BIBasicPollable;
import com.tridium.nrio.BIWritable;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioWriteAsyncRequest;


/**
 *
 * @author    Andy Saunders
 * @creation  21 Jan 02
 * @version   $Revision$ $Date: 8/29/2005 10:21:13 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNrio16WriteProxyExt
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
/*@ $com.tridium.nrio.points.BNrio16WriteProxyExt(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16WriteProxyExt.class);

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
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      //network.getUnsolicitedReceive().addProxyExt(this);
      undoInstance = getInstance();
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

  public boolean requiresPointSubscription()
  {
    return false;
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
        if(conflictPoint != null)
        {
          readFail(getLexicon().getText("readFail.pointInstanceConflict") + " " + conflictPoint.getName());
          setInstance(undoInstance);
        }
        else
        {
          undoInstance = getInstance();
        }
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
            readOk(getReadValue());
          }
          device().updateProxyValues();
        }
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
    
    // once we have a real value upsubscribe from poll scheduler
    // future updates will come from unsolicited message handler.
    read();
    if(!getStatus().isStale())
      network.getPollScheduler().unsubscribe(this);
  }
  

 /**
  * This method will read data from ioStatus BBlob property of the parent device.
  */
  private void read()
  {
	// device doesn't support reading back written values so just 
	// set read value to match write value.
    if(getParentPoint().isWritablePoint())
      readOk(getWriteValue());
    else
      device().updateProxyValues();
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
    BStatusValue out = getWriteValue();
    if(out.getStatus().isNull())
      return false; // always skip write if value is null

    BNrioNetwork network = network();
    //if (network.getLog().isTraceOn()) network.getLog().trace("write <" + this + "> " + out);
    
    // Post writes on the coalescing request queue!
    readOk(out);
//    if(network.isDownLoadInProcess())
//      return false;
    writeData(out);
//    network.postWrite(new NrioWriteAsyncRequest(this, out));
    return false;
  }

  /**
   * Callback from asynchronous thread to 
   * send a write to the device.
   */
  public void writeData(BStatusValue out)
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    if(network == null)
      return;
    if( !(device() instanceof BNrio16Module))
      return;
    int wrStatus = NrioMessageConst.MESSAGE_STATUS_ERR0R;
    if( this.isBoolean() )
    {
      wrStatus = device().setDoValue(((BStatusBoolean)out).getValue(), getInstance());
    }
    else // must be numeric
    {
      network.getLog().message("**** subclass should override writeData *******");
    }
    if(wrStatus == NrioMessageConst.MESSAGE_STATUS_OK)
        writeOk(out);
      else
        writeFail("writeError: " + wrStatus );
    return;
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

  protected BNrioDevice device()
  {
    return (BNrioDevice)getDevice();
  }
  
  protected BNrioNetwork network()
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
