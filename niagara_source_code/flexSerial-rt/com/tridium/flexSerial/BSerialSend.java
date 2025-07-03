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
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;

/**
 * BSerialSend will send a request and expose the response
 *
 * @author    Andy Saunders
 * @creation  16 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The intput is a string.
 */
@NiagaraProperty(
  name = "in",
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
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY
)
@NiagaraAction(
  name = "send"
)
public class BSerialSend
  extends BComponent
  implements BIStatus
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.BSerialSend(3914482915)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * The intput is a string.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.SUMMARY, new BStatusString(""), null);

  /**
   * Get the {@code in} property.
   * The intput is a string.
   * @see #in
   */
  public BStatusString getIn() { return (BStatusString)get(in); }

  /**
   * Set the {@code in} property.
   * The intput is a string.
   * @see #in
   */
  public void setIn(BStatusString v) { set(in, v, null); }

  //endregion Property "in"

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

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.SUMMARY, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Action "send"

  /**
   * Slot for the {@code send} action.
   * @see #send()
   */
  public static final Action send = newAction(0, null);

  /**
   * Invoke the {@code send} action.
   * @see #send
   */
  public void send() { invoke(send, null, null); }

  //endregion Action "send"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialSend.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  public void started()
  {
    network = getNetwork();
    setStatus(BStatus.makeFault(getStatus(), (network == null)));
  }

  public void changed(Property property, Context context) 
  {
    if(isRunning())
    {
      if(property == in) postSend();
    }
  }

  /**
   * Callback to post an async action.  Subclasses should call super.
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(send)) return postSend();
    return super.post(action, arg, cx);
  }


  protected IFuture postSend()
  {
	  if(this.isRunning())
	  	return getNetwork().postAsync(new Invocation(this, send, null, null));
	  return null;
  }

  
  public void doSend()
  {
    if(getNetwork() == null)
      return;
    String frameStart = new String(network.getMessageBlocks().getFrameStart().getByteArray());
    String frameEnd = new String(network.getMessageBlocks().getFrameEnd().getByteArray());
    String reqData = getIn().getValue();
    if(getAddFrameStart())
    {
      reqData = frameStart + reqData;
    }
    if(getAddFrameEnd())
    {
      reqData = reqData + frameEnd;
    }
    SerialMessage req = new SerialMessage(reqData);
    req.setResponseExpected(false);
    network.sendSync(req);
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

}
