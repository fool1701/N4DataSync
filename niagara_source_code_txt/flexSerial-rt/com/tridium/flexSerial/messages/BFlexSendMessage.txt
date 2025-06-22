/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;

import javax.baja.io.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.enums.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.program.*;

/**
 * BFlexSendMessage defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "enable",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean(true)"
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 time stamp of last received data
 */
@NiagaraProperty(
  name = "timestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "send",
  flags = Flags.ASYNC
)
public class BFlexSendMessage
  extends BFlexRequestMessage
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexSendMessage(534492256)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enable"

  /**
   * Slot for the {@code enable} property.
   * @see #getEnable
   * @see #setEnable
   */
  public static final Property enable = newProperty(0, new BStatusBoolean(true), null);

  /**
   * Get the {@code enable} property.
   * @see #enable
   */
  public BStatusBoolean getEnable() { return (BStatusBoolean)get(enable); }

  /**
   * Set the {@code enable} property.
   * @see #enable
   */
  public void setEnable(BStatusBoolean v) { set(enable, v, null); }

  //endregion Property "enable"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);

  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * time stamp of last received data
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(Flags.TRANSIENT | Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code timestamp} property.
   * time stamp of last received data
   * @see #timestamp
   */
  public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * time stamp of last received data
   * @see #timestamp
   */
  public void setTimestamp(BAbsTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Action "send"

  /**
   * Slot for the {@code send} action.
   * @see #send()
   */
  public static final Action send = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code send} action.
   * @see #send
   */
  public void send() { invoke(send, null, null); }

  //endregion Action "send"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexSendMessage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

  public void doSend()
  {
    if(! getEnable().getBoolean())
    {
      return;
    }
    BFlexMessageBlock msg = (BFlexMessageBlock)this.get("instance");
    if(msg == null)
      this.doCreateInstance();
    msg = (BFlexMessageBlock)this.get("instance");
    if(msg == null)
    {
      setFaultCause("RequestMessage instance not found.");
      return;
    }
    BComplex parent = getParent();
    if( parent == null || !(parent instanceof BFlexSerialNetwork))
    {
      setFaultCause("Parent not a flex serial network.");
      return;
    }
    BFlexSerialNetwork network = (BFlexSerialNetwork)parent; 
    FlexOutputStream out = new FlexOutputStream();
    msg.writeTo(this, out);
    this.setByteArray(BBlob.make(out.toByteArray()));
    SerialMessage sendReq = new SerialMessage(out.toByteArray());
    sendReq.setResponseExpected(false);
    network.sendSync(sendReq);
    setFaultCause("");
    setTimestamp(Clock.time());
    
  }
  
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if(!isRunning())
      return;
  }

}
