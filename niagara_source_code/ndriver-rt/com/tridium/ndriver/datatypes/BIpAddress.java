/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import java.net.InetAddress;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * IpAddress contains ip address and port number.
 *
 * @author Robert A Adams
 * @creation Oct 26, 2011
 */
@NiagaraType
/*
 device ip address
 */
@NiagaraProperty(
  name = "ipAddress",
  type = "String",
  defaultValue = ""
)
/*
 device port.
 */
@NiagaraProperty(
  name = "port",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.make(BFacets.makeInt(null, -1, 65536),BFacets.make(BFacets.FIELD_EDITOR, \"ndriver:IpPortFE\"))")
)
/*
 Used in session oriented protocols like tcp to allow
 messages to be sent in a session initiated by a client
 */
@NiagaraProperty(
  name = "sessionId",
  type = "int",
  defaultValue = "UNUSED_SESSION_ID",
  flags = Flags.TRANSIENT | Flags.HIDDEN | Flags.READONLY
)
public class BIpAddress
  extends BAddress
{
  public static final int UNUSED_SESSION_ID = -1;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BIpAddress(3009866635)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ipAddress"

  /**
   * Slot for the {@code ipAddress} property.
   * device ip address
   * @see #getIpAddress
   * @see #setIpAddress
   */
  public static final Property ipAddress = newProperty(0, "", null);

  /**
   * Get the {@code ipAddress} property.
   * device ip address
   * @see #ipAddress
   */
  public String getIpAddress() { return getString(ipAddress); }

  /**
   * Set the {@code ipAddress} property.
   * device ip address
   * @see #ipAddress
   */
  public void setIpAddress(String v) { setString(ipAddress, v, null); }

  //endregion Property "ipAddress"

  //region Property "port"

  /**
   * Slot for the {@code port} property.
   * device port.
   * @see #getPort
   * @see #setPort
   */
  public static final Property port = newProperty(0, 0, BFacets.make(BFacets.makeInt(null, -1, 65536),BFacets.make(BFacets.FIELD_EDITOR, "ndriver:IpPortFE")));

  /**
   * Get the {@code port} property.
   * device port.
   * @see #port
   */
  public int getPort() { return getInt(port); }

  /**
   * Set the {@code port} property.
   * device port.
   * @see #port
   */
  public void setPort(int v) { setInt(port, v, null); }

  //endregion Property "port"

  //region Property "sessionId"

  /**
   * Slot for the {@code sessionId} property.
   * Used in session oriented protocols like tcp to allow
   * messages to be sent in a session initiated by a client
   * @see #getSessionId
   * @see #setSessionId
   */
  public static final Property sessionId = newProperty(Flags.TRANSIENT | Flags.HIDDEN | Flags.READONLY, UNUSED_SESSION_ID, null);

  /**
   * Get the {@code sessionId} property.
   * Used in session oriented protocols like tcp to allow
   * messages to be sent in a session initiated by a client
   * @see #sessionId
   */
  public int getSessionId() { return getInt(sessionId); }

  /**
   * Set the {@code sessionId} property.
   * Used in session oriented protocols like tcp to allow
   * messages to be sent in a session initiated by a client
   * @see #sessionId
   */
  public void setSessionId(int v) { setInt(sessionId, v, null); }

  //endregion Property "sessionId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIpAddress.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor
   */
  public BIpAddress() {}

  /**
   * constructor
   */
  public BIpAddress(InetAddress iadr, int port)
  {
    setIpAddress(iadr.getHostAddress());
    setPort(port);
  }

  /**
   * constructor
   */
  public BIpAddress(String ipAddress, int port)
  {
    setIpAddress(ipAddress);
    setPort(port);
  }

  public InetAddress getInetAddress()
  {
    try
    {
      return InetAddress.getByName(getIpAddress());
    }
    catch (Exception e)
    {
      return null;
    }
  }

  @Override
  public Object getAddress()
  {
    return getInetAddress();
  }

  @Override
  public boolean sameDevice(BAddress comp)
  {
    if (!(comp instanceof BIpAddress))
    {
      return false;
    }
    return getInetAddress().equals(((BIpAddress)comp).getInetAddress());
  }

  @Override
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getIpAddress()).append(':').append(getPort());
    int sid = getSessionId();
    if (sid != UNUSED_SESSION_ID)
    {
      sb.append("{").append(sid).append("}");
    }
    return sb.toString();
  }

  @Override
  public String toHashString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getInetAddress().getHostAddress()).append(':').append(getPort());
    return sb.toString();
  }

  @Override
  public boolean isValid()
  {
    return getIpAddress().length() > 0;
  }

  public boolean sameSessionId(BIpAddress comp)
  {
    return getSessionId() >= 0 && (getSessionId() == comp.getSessionId());
  }
}
