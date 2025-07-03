/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BDefaultProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.basicdriver.util.BIBasicPollable;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioNetwork;
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
public abstract class BNrio16ProxyExt
  extends BProxyExt
  implements BIBasicPollable,
             NrioMessageConst
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrio16ProxyExt(734988802)1.0$ @*/
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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16ProxyExt.class);

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
    return BNrio16Points.TYPE;
  }

  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public BReadWriteMode getMode()
  {
    return getParentPoint().isWritablePoint() ? BReadWriteMode.readWrite : BReadWriteMode.readonly;
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


  public void configFail(String cause)
  {
//    setSynced(false);
    configFault = true;
    readFail(cause);
  }

  public void configOk()
  {
    configFault = false;
    readReset();
  }


////////////////////////////////////////////////////////////////
//Status
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

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

  private boolean configFault = true;

  public static final byte[] ACTIVE_DATA = {(byte)1 };
  public static final byte[] INACTIVE_DATA = {(byte)0 };
}
