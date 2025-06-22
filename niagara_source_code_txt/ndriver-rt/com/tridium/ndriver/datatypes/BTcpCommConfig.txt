/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.tcp.TcpLinkLayer;

/**
 * Communications configuration parameters for tcp link layer.
 *
 * @author Robert A Adams
 * @creation Oct 23, 2011
 */
@NiagaraType
@NiagaraProperty(
  name = "address",
  type = "BIpAddress",
  defaultValue = "new BIpAddress(\"local\",0)"
)
@NiagaraProperty(
  name = "serverEnabled",
  type = "boolean",
  defaultValue = "false"
)
/*
 timeout used for listening on sockets created for outgoing messages
 0 for no timeout
 */
@NiagaraProperty(
  name = "sendSocketTO",
  type = "int",
  defaultValue = "20",
  facets = @Facet("BFacets.makeInt(UnitDatabase.getUnit(\"second\"))")
)
/*
 timeout used for listening on server sockets - 0 for no timeout
 */
@NiagaraProperty(
  name = "serverSocketTO",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(UnitDatabase.getUnit(\"second\"))")
)
public class BTcpCommConfig
  extends BCommConfig
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BTcpCommConfig(1261036351)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, new BIpAddress("local",0), null);

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

  //region Property "serverEnabled"

  /**
   * Slot for the {@code serverEnabled} property.
   * @see #getServerEnabled
   * @see #setServerEnabled
   */
  public static final Property serverEnabled = newProperty(0, false, null);

  /**
   * Get the {@code serverEnabled} property.
   * @see #serverEnabled
   */
  public boolean getServerEnabled() { return getBoolean(serverEnabled); }

  /**
   * Set the {@code serverEnabled} property.
   * @see #serverEnabled
   */
  public void setServerEnabled(boolean v) { setBoolean(serverEnabled, v, null); }

  //endregion Property "serverEnabled"

  //region Property "sendSocketTO"

  /**
   * Slot for the {@code sendSocketTO} property.
   * timeout used for listening on sockets created for outgoing messages
   * 0 for no timeout
   * @see #getSendSocketTO
   * @see #setSendSocketTO
   */
  public static final Property sendSocketTO = newProperty(0, 20, BFacets.makeInt(UnitDatabase.getUnit("second")));

  /**
   * Get the {@code sendSocketTO} property.
   * timeout used for listening on sockets created for outgoing messages
   * 0 for no timeout
   * @see #sendSocketTO
   */
  public int getSendSocketTO() { return getInt(sendSocketTO); }

  /**
   * Set the {@code sendSocketTO} property.
   * timeout used for listening on sockets created for outgoing messages
   * 0 for no timeout
   * @see #sendSocketTO
   */
  public void setSendSocketTO(int v) { setInt(sendSocketTO, v, null); }

  //endregion Property "sendSocketTO"

  //region Property "serverSocketTO"

  /**
   * Slot for the {@code serverSocketTO} property.
   * timeout used for listening on server sockets - 0 for no timeout
   * @see #getServerSocketTO
   * @see #setServerSocketTO
   */
  public static final Property serverSocketTO = newProperty(0, 0, BFacets.makeInt(UnitDatabase.getUnit("second")));

  /**
   * Get the {@code serverSocketTO} property.
   * timeout used for listening on server sockets - 0 for no timeout
   * @see #serverSocketTO
   */
  public int getServerSocketTO() { return getInt(serverSocketTO); }

  /**
   * Set the {@code serverSocketTO} property.
   * timeout used for listening on server sockets - 0 for no timeout
   * @see #serverSocketTO
   */
  public void setServerSocketTO(int v) { setInt(serverSocketTO, v, null); }

  //endregion Property "serverSocketTO"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTcpCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor
   */
  public BTcpCommConfig() {}

  @Deprecated
  public BTcpCommConfig(ICommListener defaultListener)
  {
    super();
    setDefaultListener(defaultListener);
  }

  /**
   * @return new {@code TcpLinkLayer}
   */
  @Override
  public ILinkLayer makeLinkLayer(NComm comm)
  {
    return new TcpLinkLayer(comm, this);
  }

  /**
   * @return default resourcePrefix with ".Tcp" appended
   */
  @Override
  public String getResourcePrefix()
  {
    return super.getResourcePrefix() + ".Tcp";
  }
}
