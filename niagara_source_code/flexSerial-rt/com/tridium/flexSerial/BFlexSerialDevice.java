/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial;

import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.basicdriver.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.flexSerial.point.*;

/**
 * BFlexSerialDevice represents a Modbus device
 * which supports Modbus Ascii communication.
 *
 * @author    Brian Frank       
 * @creation  21 Jan 02
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "address",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "initMessage",
  type = "BFlexRequestResponse",
  defaultValue = "new BFlexRequestResponse()"
)
@NiagaraProperty(
  name = "pingMessage",
  type = "BFlexRequestResponse",
  defaultValue = "new BFlexRequestResponse()"
)
@NiagaraProperty(
  name = "points",
  type = "BFlexPointDeviceExt",
  defaultValue = "new BFlexPointDeviceExt()"
)
public class BFlexSerialDevice
  extends BBasicDevice
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.BFlexSerialDevice(2384669873)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, "", null);

  /**
   * Get the {@code address} property.
   * @see #address
   */
  public String getAddress() { return getString(address); }

  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(String v) { setString(address, v, null); }

  //endregion Property "address"

  //region Property "initMessage"

  /**
   * Slot for the {@code initMessage} property.
   * @see #getInitMessage
   * @see #setInitMessage
   */
  public static final Property initMessage = newProperty(0, new BFlexRequestResponse(), null);

  /**
   * Get the {@code initMessage} property.
   * @see #initMessage
   */
  public BFlexRequestResponse getInitMessage() { return (BFlexRequestResponse)get(initMessage); }

  /**
   * Set the {@code initMessage} property.
   * @see #initMessage
   */
  public void setInitMessage(BFlexRequestResponse v) { set(initMessage, v, null); }

  //endregion Property "initMessage"

  //region Property "pingMessage"

  /**
   * Slot for the {@code pingMessage} property.
   * @see #getPingMessage
   * @see #setPingMessage
   */
  public static final Property pingMessage = newProperty(0, new BFlexRequestResponse(), null);

  /**
   * Get the {@code pingMessage} property.
   * @see #pingMessage
   */
  public BFlexRequestResponse getPingMessage() { return (BFlexRequestResponse)get(pingMessage); }

  /**
   * Set the {@code pingMessage} property.
   * @see #pingMessage
   */
  public void setPingMessage(BFlexRequestResponse v) { set(pingMessage, v, null); }

  //endregion Property "pingMessage"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BFlexPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * @see #points
   */
  public BFlexPointDeviceExt getPoints() { return (BFlexPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * @see #points
   */
  public void setPoints(BFlexPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexSerialDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
  * Overrides isParentLegal method.  BModbusAsciiDevices
  * must reside under a BModbusAsciiNetwork.
  */
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BFlexSerialNetwork  ||
            parent instanceof BFlexSerialDeviceFolder );
  }
  
  /**
   * Ping implementation.
   */
  public void doPing()
  {

    if(firstPing || isDown())
    {
      getInitMessage().sendMessage(BRelTime.makeSeconds(3));
      firstPing = false;
    }
    
    // if ping message not defined call pingOK()
    if( getPingMessage().isRequestDefined() )
    {  
      String results = getPingMessage().sendMessage(null);
      if(results.equals("OK"))
        pingOk();
      else
        pingFail(results);
    }
    else
      pingOk();
  }

  boolean firstPing = true;

}
