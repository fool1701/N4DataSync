/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BDefaultProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.point.conv.BReversePolarityConversion;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.basicdriver.util.BIBasicPollable;
import com.tridium.nrio.BIWritable;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioInputOutputModule;
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
@NiagaraProperty(
  name = "conversion",
  type = "BProxyConversion",
  defaultValue = "BDefaultProxyConversion.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, \"nrio:NrioProxyConversionFE\")"),
  override = true
)
/*
 Poll frequency bucket
 */
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
@NiagaraProperty(
  name = "instance",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "isStrike",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "isSdi",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN | Flags.READONLY
)
@NiagaraAction(
  name = "postChangeTimeout",
  flags = Flags.HIDDEN
)
public class BNrioProxyExt
  extends BProxyExt
  implements BIBasicPollable,
             BIWritable,
             NrioMessageConst
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioProxyExt(1688717649)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "conversion"

  /**
   * Slot for the {@code conversion} property.
   * @see #getConversion
   * @see #setConversion
   */
  public static final Property conversion = newProperty(0, BDefaultProxyConversion.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, "nrio:NrioProxyConversionFE"));

  //endregion Property "conversion"

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "instance"

  /**
   * Slot for the {@code instance} property.
   * @see #getInstance
   * @see #setInstance
   */
  public static final Property instance = newProperty(0, 0, null);

  /**
   * Get the {@code instance} property.
   * @see #instance
   */
  public int getInstance() { return getInt(instance); }

  /**
   * Set the {@code instance} property.
   * @see #instance
   */
  public void setInstance(int v) { setInt(instance, v, null); }

  //endregion Property "instance"

  //region Property "isStrike"

  /**
   * Slot for the {@code isStrike} property.
   * @see #getIsStrike
   * @see #setIsStrike
   */
  public static final Property isStrike = newProperty(Flags.HIDDEN | Flags.READONLY, false, null);

  /**
   * Get the {@code isStrike} property.
   * @see #isStrike
   */
  public boolean getIsStrike() { return getBoolean(isStrike); }

  /**
   * Set the {@code isStrike} property.
   * @see #isStrike
   */
  public void setIsStrike(boolean v) { setBoolean(isStrike, v, null); }

  //endregion Property "isStrike"

  //region Property "isSdi"

  /**
   * Slot for the {@code isSdi} property.
   * @see #getIsSdi
   * @see #setIsSdi
   */
  public static final Property isSdi = newProperty(Flags.HIDDEN | Flags.READONLY, false, null);

  /**
   * Get the {@code isSdi} property.
   * @see #isSdi
   */
  public boolean getIsSdi() { return getBoolean(isSdi); }

  /**
   * Set the {@code isSdi} property.
   * @see #isSdi
   */
  public void setIsSdi(boolean v) { setBoolean(isSdi, v, null); }

  //endregion Property "isSdi"

  //region Action "postChangeTimeout"

  /**
   * Slot for the {@code postChangeTimeout} action.
   * @see #postChangeTimeout()
   */
  public static final Action postChangeTimeout = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code postChangeTimeout} action.
   * @see #postChangeTimeout
   */
  public void postChangeTimeout() { invoke(postChangeTimeout, null, null); }

  //endregion Action "postChangeTimeout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioProxyExt.class);

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
      BControlPoint conflictPoint = device().checkForProxyExtConflicts(this.getParentPoint());
      if(conflictPoint != null)
      {
        this.setEnabled(false);
        readFail(getLexicon().getText("readFail.pointInstanceConflict") + " " + conflictPoint.getName());
      }
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      //network.getUnsolicitedReceive().addProxyExt(this);
      undoInstance = getInstance();
      if(getEnabled())
        network.getPollScheduler().subscribe(this);
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
    return getParentPoint().isWritablePoint() ? BReadWriteMode.writeonly : BReadWriteMode.readonly;
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
      BNrioNetwork network = (BNrioNetwork)getNetwork();
      
      if(prop.equals(instance))
      {
        BControlPoint conflictPoint = device().checkForProxyExtConflicts(getParentPoint());
        if(conflictPoint != null)
        {
          readFail(getLexicon().getText("readFail.pointInstanceConflict") + " " + conflictPoint.getName());
          setEnabled(false);
        }
        else
        {
        // force write if writable point
          if(getParentPoint().isWritablePoint())
          {
            device().setDoValue(false, undoInstance);
            try {write(null); } catch(Exception e){e.printStackTrace(); }
        }
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
            device().updateProxyValues();
          }
          else
          {
            device().updateProxyValues();
            setPostChangeTicket();
            // force write if writable point
            if(getParentPoint().isWritablePoint())
              try {write(null); } catch(Exception e){e.printStackTrace(); }
          }
        }
      }
      else if(prop.equals(conversion))
      {
        device().updateProxyValues();
        setPostChangeTicket();
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
   * Copy the deviceValue to the proxyValue.  The default implementation 
   * routes to <code>getConversion().convertDeviceToProxy()</code>.
   */
  protected void convertDeviceToProxy(BStatusValue deviceValue, BStatusValue proxyValue)
  {
    if(deviceValue.getStatus().isFault() && getIsSdi())
    {
      if( getConversion().getType().is(BReversePolarityConversion.TYPE) && 
          deviceValue.getStatus().isFault() )
      {
        ((BStatusBoolean)deviceValue).setValue(true);
      }
    }
    super.convertDeviceToProxy(deviceValue, proxyValue);
  }
  
  /**
   * Override implementation of <code>BIBasicPollable</code>.
   * Causes communication to read the value of the
   * point from the device.
   */
  public void poll()
  {
    BNrioNetwork network = network();
    if (network.getLog().isTraceOn()) network.getLog().trace(this + ".poll()");

    read();
    if(!getStatus().isStale())
      network.getPollScheduler().unsubscribe(this);
  }


 /**
  * This method will read data from ioStatus BBlob property of the parent device.
  */
  private void read()
  {
    if(getParentPoint() instanceof BBooleanWritable)
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
    if(network.isDownLoadInProcess())
      return false;
    network.postWrite(new NrioWriteAsyncRequest(this, out));
    return true;
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
    if(device() instanceof BNrioInputOutputModule)
    {
      int wrStatus = device().setDoValue(((BStatusBoolean)out).getValue(), getInstance());
      if(wrStatus == NrioMessageConst.MESSAGE_STATUS_OK)
        writeOk(out);
      else
        writeFail("writeError: " + wrStatus );
    }
    else if(device() instanceof BNrio16Module)
    {
      int wrStatus = device().setDoValue(((BStatusBoolean)out).getValue(), getInstance());
      if(wrStatus == NrioMessageConst.MESSAGE_STATUS_OK)
        writeOk(out);
      else
        writeFail("writeError: " + wrStatus );
    }
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

  private BNrioDevice device()
  {
    return (BNrioDevice)getDevice();
  }

  private BNrioNetwork network()
  {
    return (BNrioNetwork)(getDevice().getNetwork());
  }

  public boolean isBoolean()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusBoolean;
  }

  public boolean isNumeric()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusNumeric;
  }

  public boolean isEnum()
  {
    return getParentPoint().getOutStatusValue() instanceof BStatusEnum;
  }

  public void doPostChangeTimeout()
  {
    BControlPoint pp = getParentPoint();
    if(pp != null)
    {
      pp.execute();
    }
  }
  
  private void setPostChangeTicket()
  {
    if(postChangeTicket != null)
      postChangeTicket.cancel();
    postChangeTicket = Clock.schedule(this, BRelTime.make(500l), postChangeTimeout, null);
  }
  
  Clock.Ticket postChangeTicket = null;
  public static final byte[] ACTIVE_DATA = {(byte)1 };
  public static final byte[] INACTIVE_DATA = {(byte)0 };
  private int undoInstance = -1;
}
