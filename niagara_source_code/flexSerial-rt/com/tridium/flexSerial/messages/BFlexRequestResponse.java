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
import com.tridium.program.*;

/**
 * BFlexRequestResponse defines a collection of message items.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "request",
  type = "BFlexRequestMessage",
  defaultValue = "new BFlexRequestMessage()"
)
@NiagaraProperty(
  name = "response",
  type = "BFlexResponseMessage",
  defaultValue = "new BFlexResponseMessage()"
)
@NiagaraAction(
  name = "forceMessage",
  flags = Flags.ASYNC
)
public class BFlexRequestResponse
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexRequestResponse(1423963075)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "request"

  /**
   * Slot for the {@code request} property.
   * @see #getRequest
   * @see #setRequest
   */
  public static final Property request = newProperty(0, new BFlexRequestMessage(), null);

  /**
   * Get the {@code request} property.
   * @see #request
   */
  public BFlexRequestMessage getRequest() { return (BFlexRequestMessage)get(request); }

  /**
   * Set the {@code request} property.
   * @see #request
   */
  public void setRequest(BFlexRequestMessage v) { set(request, v, null); }

  //endregion Property "request"

  //region Property "response"

  /**
   * Slot for the {@code response} property.
   * @see #getResponse
   * @see #setResponse
   */
  public static final Property response = newProperty(0, new BFlexResponseMessage(), null);

  /**
   * Get the {@code response} property.
   * @see #response
   */
  public BFlexResponseMessage getResponse() { return (BFlexResponseMessage)get(response); }

  /**
   * Set the {@code response} property.
   * @see #response
   */
  public void setResponse(BFlexResponseMessage v) { set(response, v, null); }

  //endregion Property "response"

  //region Action "forceMessage"

  /**
   * Slot for the {@code forceMessage} action.
   * @see #forceMessage()
   */
  public static final Action forceMessage = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code forceMessage} action.
   * @see #forceMessage
   */
  public void forceMessage() { invoke(forceMessage, null, null); }

  //endregion Action "forceMessage"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexRequestResponse.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////
  /**
   * Callback to post an async action.  Subclasses should call super.
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(forceMessage)) return postForceMessage();
    return super.post(action, arg, cx);
  }


  public IFuture postForceMessage()
  {
	  if(this.isRunning())
	  	return getNetwork().postAsync(new Invocation(this, forceMessage, null, null));
	  return null;
  }


  public void doForceMessage()
  {
    sendMessage(null);
  }

  public boolean isRequestDefined()
  {
    BFlexRequestMessage reqMsg = getRequest();
    if(reqMsg == null) return false;
    return !(reqMsg.getMessage().getMessageSelect().equals(""));
  }
  
  public boolean isResponseDefined()
  {
    BFlexResponseMessage respMsg = getResponse();
    if(respMsg == null) return false;
    return !(respMsg.getMessage().getMessageSelect().equals(""));
  }
  
 
  public String sendMessage(BRelTime timeOut)
  {
    BFlexRequestMessage reqMsg = getRequest();
    if(reqMsg == null)
      return "RequestMessage not defined";
    BFlexMessageBlock msg = (BFlexMessageBlock)reqMsg.get("instance");
    if(msg == null)
      reqMsg.doCreateInstance();
    msg = (BFlexMessageBlock)reqMsg.get("instance");
    if(msg == null)
      return "RequestMessage instance not found.";
    FlexOutputStream out = new FlexOutputStream();
    msg.writeTo(this, out);
    reqMsg.setByteArray(BBlob.make(out.toByteArray()));
    BFlexResponseMessage responseMsg = getResponse();
    responseMsg.setByteArray(BBlob.DEFAULT);
    SerialResponse respMsg;
    SerialMessage sendReq = new SerialMessage(out.toByteArray());
    if(!isResponseDefined())
      sendReq.setResponseExpected(false);
    if(timeOut == null)
      respMsg = (SerialResponse)getNetwork().sendSync(sendReq);
    else
      respMsg = (SerialResponse)getNetwork().sendSync(sendReq, timeOut, 0);
    if(!isResponseDefined())
      return "OK";
    if(respMsg == null)
      return "response timeout";
    BFlexMessageBlock responseBlock = (BFlexMessageBlock)responseMsg.get("instance");
    if(responseBlock != null)
    {
      byte[] data = respMsg.getBytes();
      if( data.length > 0)
      {
        responseBlock.readFrom(this, new FlexInputStream(respMsg.getBytes()));
        responseMsg.setByteArray(BBlob.make(respMsg.getBytes()));

      }
    }
    BOrd errorCheckOrd = responseMsg.getMessageValidate();
    if(!errorCheckOrd.equals(BOrd.NULL))
    {
      BObject obj = errorCheckOrd.get(this);
      if(obj instanceof BProgram)
      {
        try
        {
          BProgram errorCheckPgm = (BProgram)obj;
          errorCheckPgm.set("requestByteArray", BBlob.make(reqMsg.getByteArray().copyBytes()));
          errorCheckPgm.set("responseByteArray", BBlob.make(respMsg.getBytes()));
          errorCheckPgm.doExecute();
          BValue results = errorCheckPgm.get("results");
          String error = ((BString)results).getString();
          if(error != null && error.length() >=0)
            return error;
        }
        catch(Exception e)
        {
          return "ErrorCheck program object must have requestByteArray, responseByteArray & results slots!";
        }

      }
    }
    return "OK";
  }

  public BFlexSerialNetwork getNetwork()
  {
    return BFlexSerialNetwork.getParentFlexNetwork(this);
  }
  
  public String toString(Context cx)
  {
    return getRequest().getMessage().getMessageSelect() + " : " + getResponse().getMessage().getMessageSelect();
  }
  
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageRequestResponse.png");


}
