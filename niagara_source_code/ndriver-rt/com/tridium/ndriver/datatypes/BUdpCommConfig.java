/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.udp.UdpLinkLayer;

/**
 * Communications configuration parameters for udp link layer.
 *
 * @author Robert A Adams
 * @creation Dec 13, 2011
 */
@NiagaraType
@NiagaraProperty(
  name = "address",
  type = "BIpAddress",
  defaultValue = "new BIpAddress(\"local\",-1)"
)
public class BUdpCommConfig
  extends BCommConfig
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BUdpCommConfig(664733167)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, new BIpAddress("local",-1), null);

  /**
   * Get the {@code address} property.
   * @see #address
   */
  public BIpAddress getAddress() { return (BIpAddress)get(address); }

  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(BIpAddress v) { set(address, v, null); }

  //endregion Property "address"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUdpCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor
   */
  public BUdpCommConfig() {}

  @Deprecated
  public BUdpCommConfig(ICommListener defaultListener)
  {
    super();
    setDefaultListener(defaultListener);
  }

  /**
   * @return new {@code UdpLinkLayer}
   */
  @Override
  public ILinkLayer makeLinkLayer(NComm comm)
  {
    return new UdpLinkLayer(comm, this);
  }

  /**
   * @return default resourcePrefix with ".Udp" appended
   */
  @Override
  public String getResourcePrefix()
  {
    return super.getResourcePrefix() + ".Udp";
  }

  @Override
  public String toString(Context cx)
  {
    return getAddress().toString(cx);
  }

  /**
   * Should this config attempt a reconnect on socket failure. Implementers may override
   * to configure the {@link UdpLinkLayer} to disconnect and reconnect on failure, rather than
   * just faulting.
   *
   * @return True if this config should attempt to reconnect, false otherwise.
   *
   * @since Niagara 4.10U4
   */
  public boolean getReconnectOnFailure()
  {
    return false;
  }
}
