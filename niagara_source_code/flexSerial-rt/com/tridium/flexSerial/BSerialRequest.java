/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;

/**
 * BSerialRequest will send a request and expose the response
 *
 * @author    Andy Saunders
 * @creation  16 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The intput is a request string.
 */
@NiagaraProperty(
  name = "request",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.OPERATOR | Flags.SUMMARY
)
/*
 If true start framing will automatically be added
 */
@NiagaraProperty(
  name = "addFrameStart",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true end framing will automatically be added
 */
@NiagaraProperty(
  name = "addFrameEnd",
  type = "boolean",
  defaultValue = "true"
)
/*
 The reply to the response
 */
@NiagaraProperty(
  name = "response",
  type = "BStatusString",
  defaultValue = "new BStatusString(\"\")",
  flags = Flags.OPERATOR | Flags.SUMMARY
)
@NiagaraProperty(
  name = "responseExpected",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true start framing will automatically be removed
 */
@NiagaraProperty(
  name = "stripFrameStart",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true end framing will automatically be removed
 */
@NiagaraProperty(
  name = "stripFrameEnd",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraAction(
  name = "sendRequest"
)
public class BSerialRequest
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.BSerialRequest(1421972937)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "request"

  /**
   * Slot for the {@code request} property.
   * The intput is a request string.
   * @see #getRequest
   * @see #setRequest
   */
  public static final Property request = newProperty(Flags.OPERATOR | Flags.SUMMARY, new BStatusString(""), null);

  /**
   * Get the {@code request} property.
   * The intput is a request string.
   * @see #request
   */
  public BStatusString getRequest() { return (BStatusString)get(request); }

  /**
   * Set the {@code request} property.
   * The intput is a request string.
   * @see #request
   */
  public void setRequest(BStatusString v) { set(request, v, null); }

  //endregion Property "request"

  //region Property "addFrameStart"

  /**
   * Slot for the {@code addFrameStart} property.
   * If true start framing will automatically be added
   * @see #getAddFrameStart
   * @see #setAddFrameStart
   */
  public static final Property addFrameStart = newProperty(0, true, null);

  /**
   * Get the {@code addFrameStart} property.
   * If true start framing will automatically be added
   * @see #addFrameStart
   */
  public boolean getAddFrameStart() { return getBoolean(addFrameStart); }

  /**
   * Set the {@code addFrameStart} property.
   * If true start framing will automatically be added
   * @see #addFrameStart
   */
  public void setAddFrameStart(boolean v) { setBoolean(addFrameStart, v, null); }

  //endregion Property "addFrameStart"

  //region Property "addFrameEnd"

  /**
   * Slot for the {@code addFrameEnd} property.
   * If true end framing will automatically be added
   * @see #getAddFrameEnd
   * @see #setAddFrameEnd
   */
  public static final Property addFrameEnd = newProperty(0, true, null);

  /**
   * Get the {@code addFrameEnd} property.
   * If true end framing will automatically be added
   * @see #addFrameEnd
   */
  public boolean getAddFrameEnd() { return getBoolean(addFrameEnd); }

  /**
   * Set the {@code addFrameEnd} property.
   * If true end framing will automatically be added
   * @see #addFrameEnd
   */
  public void setAddFrameEnd(boolean v) { setBoolean(addFrameEnd, v, null); }

  //endregion Property "addFrameEnd"

  //region Property "response"

  /**
   * Slot for the {@code response} property.
   * The reply to the response
   * @see #getResponse
   * @see #setResponse
   */
  public static final Property response = newProperty(Flags.OPERATOR | Flags.SUMMARY, new BStatusString(""), null);

  /**
   * Get the {@code response} property.
   * The reply to the response
   * @see #response
   */
  public BStatusString getResponse() { return (BStatusString)get(response); }

  /**
   * Set the {@code response} property.
   * The reply to the response
   * @see #response
   */
  public void setResponse(BStatusString v) { set(response, v, null); }

  //endregion Property "response"

  //region Property "responseExpected"

  /**
   * Slot for the {@code responseExpected} property.
   * @see #getResponseExpected
   * @see #setResponseExpected
   */
  public static final Property responseExpected = newProperty(0, true, null);

  /**
   * Get the {@code responseExpected} property.
   * @see #responseExpected
   */
  public boolean getResponseExpected() { return getBoolean(responseExpected); }

  /**
   * Set the {@code responseExpected} property.
   * @see #responseExpected
   */
  public void setResponseExpected(boolean v) { setBoolean(responseExpected, v, null); }

  //endregion Property "responseExpected"

  //region Property "stripFrameStart"

  /**
   * Slot for the {@code stripFrameStart} property.
   * If true start framing will automatically be removed
   * @see #getStripFrameStart
   * @see #setStripFrameStart
   */
  public static final Property stripFrameStart = newProperty(0, true, null);

  /**
   * Get the {@code stripFrameStart} property.
   * If true start framing will automatically be removed
   * @see #stripFrameStart
   */
  public boolean getStripFrameStart() { return getBoolean(stripFrameStart); }

  /**
   * Set the {@code stripFrameStart} property.
   * If true start framing will automatically be removed
   * @see #stripFrameStart
   */
  public void setStripFrameStart(boolean v) { setBoolean(stripFrameStart, v, null); }

  //endregion Property "stripFrameStart"

  //region Property "stripFrameEnd"

  /**
   * Slot for the {@code stripFrameEnd} property.
   * If true end framing will automatically be removed
   * @see #getStripFrameEnd
   * @see #setStripFrameEnd
   */
  public static final Property stripFrameEnd = newProperty(0, true, null);

  /**
   * Get the {@code stripFrameEnd} property.
   * If true end framing will automatically be removed
   * @see #stripFrameEnd
   */
  public boolean getStripFrameEnd() { return getBoolean(stripFrameEnd); }

  /**
   * Set the {@code stripFrameEnd} property.
   * If true end framing will automatically be removed
   * @see #stripFrameEnd
   */
  public void setStripFrameEnd(boolean v) { setBoolean(stripFrameEnd, v, null); }

  //endregion Property "stripFrameEnd"

  //region Action "sendRequest"

  /**
   * Slot for the {@code sendRequest} action.
   * @see #sendRequest()
   */
  public static final Action sendRequest = newAction(0, null);

  /**
   * Invoke the {@code sendRequest} action.
   * @see #sendRequest
   */
  public void sendRequest() { invoke(sendRequest, null, null); }

  //endregion Action "sendRequest"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialRequest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
    network = getNetwork();
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if(property == request) doSendRequest();
    }
  }

  public void doSendRequest()
  {
    if(getNetwork() == null)
      return;
    String frameStart = new String(network.getMessageBlocks().getFrameStart().getByteArray());
    String frameEnd = new String(network.getMessageBlocks().getFrameEnd().getByteArray());
    String reqData = getRequest().getValue();
    if(getAddFrameStart())
    {
      reqData = frameStart + reqData;
    }
    if(getAddFrameEnd())
    {
      reqData = reqData + frameEnd;
    }
    SerialMessage req = new SerialMessage(reqData);
    boolean isResponseExpected = getResponseExpected();
    req.setResponseExpected(isResponseExpected);
    if(isResponseExpected)
    {
      SerialResponse resp;
      resp = (SerialResponse)network.sendSync(req);
      if(resp == null)
        System.out.println(" null response received");
      if(resp != null && resp.getBytes() != null)
      {
        String respString = new String(resp.getBytes());
        if(getStripFrameStart())
        {
          if(respString.startsWith(frameStart))
          {
            respString = respString.substring(frameStart.length());
          }
        }
        if(getStripFrameEnd())
        {
          if(respString.endsWith(frameEnd))
          {
            respString = respString.substring(0,respString.length() - frameEnd.length());
          }
        }
        getResponse().setValue(respString);
        getResponse().setStatusDown(false);
      }
      else
        getResponse().setStatusDown(true);
    }
    else
    {
      network.sendSync(req);
      setResponse(nre);
    }
  }

  BFlexSerialNetwork getNetwork()
  {
    if(network != null) return network;
    BComplex parent = this.getParent();
    while(parent != null)
    {
      if(parent instanceof BFlexSerialNetwork)
      {
        network = (BFlexSerialNetwork)parent;
        return network;
      }
      parent = parent.getParent();
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/switch.png");
  */

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////  
  BFlexSerialNetwork network;
  static BStatusString nre = new BStatusString("Response Not Expected");

}
