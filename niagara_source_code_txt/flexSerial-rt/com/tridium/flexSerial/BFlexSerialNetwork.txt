/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial;

import javax.baja.license.Feature;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.BStatusString;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;

import com.tridium.basicdriver.comm.Comm;
import com.tridium.basicdriver.serial.BSerialNetwork;
import com.tridium.flexSerial.comm.FlexSerialComm;
import com.tridium.flexSerial.comm.FlexSerialUnsolicitedReceive;
import com.tridium.flexSerial.messages.BFlexMessageBlockFolder;
import com.tridium.flexSerial.messages.BFlexMessageFolder;
import com.tridium.flexSerial.messages.BFlexRequestResponse;
import com.tridium.sys.license.LicenseUtil;

/**
 * BFlexSerialNetwork is the base container for a serial network.  It contains the infrastructure
 * required to communicate to one or more serially connected devices.
 *
 * @author Andy Saunders on 22 April 2004
 * @since Niagara 3.0
 */
@NiagaraType
/*
 Defines the maximum number of milliseconds that the serial receive can be silent
 once receive data has started.  Typically used with protocols whose packet framing
 is defined by silent time.  A value of 0 will cause no timing to be done.
 */
@NiagaraProperty(
  name = "maxReceiveSilentTime",
  type = "int",
  defaultValue = "0"
)
/*
 When an unsolicited message is received it will be set in this property.
 */
@NiagaraProperty(
  name = "unsolicitedMessage",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "unsolicitedByteArray",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT",
  flags = Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:FlexBlobFE\"))")
)
/*
 This is a special folder to contain user defined FlexMessageBlocks.
 FlexMessageBlocks are a collection of one or more FlexMessageElements.
 */
@NiagaraProperty(
  name = "messageBlocks",
  type = "BFlexMessageBlockFolder",
  defaultValue = "new BFlexMessageBlockFolder()"
)
/*
 This is a special folder to conatin user defined FlexMessages.
 A FlexMessage is a collection of FlexMessageBlock references and
 FlexMessageElements used to model a native device message.
 */
@NiagaraProperty(
  name = "messages",
  type = "BFlexMessageFolder",
  defaultValue = "new BFlexMessageFolder()"
)
@NiagaraProperty(
  name = "initMessage",
  type = "BFlexRequestResponse",
  defaultValue = "new BFlexRequestResponse()"
)
@NiagaraTopic(
  name = "unsolicitedMessageReceived",
  eventType = "BValue"
)
public class BFlexSerialNetwork
  extends BSerialNetwork
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.BFlexSerialNetwork(852900024)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "maxReceiveSilentTime"

  /**
   * Slot for the {@code maxReceiveSilentTime} property.
   * Defines the maximum number of milliseconds that the serial receive can be silent
   * once receive data has started.  Typically used with protocols whose packet framing
   * is defined by silent time.  A value of 0 will cause no timing to be done.
   * @see #getMaxReceiveSilentTime
   * @see #setMaxReceiveSilentTime
   */
  public static final Property maxReceiveSilentTime = newProperty(0, 0, null);

  /**
   * Get the {@code maxReceiveSilentTime} property.
   * Defines the maximum number of milliseconds that the serial receive can be silent
   * once receive data has started.  Typically used with protocols whose packet framing
   * is defined by silent time.  A value of 0 will cause no timing to be done.
   * @see #maxReceiveSilentTime
   */
  public int getMaxReceiveSilentTime() { return getInt(maxReceiveSilentTime); }

  /**
   * Set the {@code maxReceiveSilentTime} property.
   * Defines the maximum number of milliseconds that the serial receive can be silent
   * once receive data has started.  Typically used with protocols whose packet framing
   * is defined by silent time.  A value of 0 will cause no timing to be done.
   * @see #maxReceiveSilentTime
   */
  public void setMaxReceiveSilentTime(int v) { setInt(maxReceiveSilentTime, v, null); }

  //endregion Property "maxReceiveSilentTime"

  //region Property "unsolicitedMessage"

  /**
   * Slot for the {@code unsolicitedMessage} property.
   * When an unsolicited message is received it will be set in this property.
   * @see #getUnsolicitedMessage
   * @see #setUnsolicitedMessage
   */
  public static final Property unsolicitedMessage = newProperty(Flags.TRANSIENT | Flags.READONLY, new BStatusString(), null);

  /**
   * Get the {@code unsolicitedMessage} property.
   * When an unsolicited message is received it will be set in this property.
   * @see #unsolicitedMessage
   */
  public BStatusString getUnsolicitedMessage() { return (BStatusString)get(unsolicitedMessage); }

  /**
   * Set the {@code unsolicitedMessage} property.
   * When an unsolicited message is received it will be set in this property.
   * @see #unsolicitedMessage
   */
  public void setUnsolicitedMessage(BStatusString v) { set(unsolicitedMessage, v, null); }

  //endregion Property "unsolicitedMessage"

  //region Property "unsolicitedByteArray"

  /**
   * Slot for the {@code unsolicitedByteArray} property.
   * @see #getUnsolicitedByteArray
   * @see #setUnsolicitedByteArray
   */
  public static final Property unsolicitedByteArray = newProperty(Flags.TRANSIENT, BBlob.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:FlexBlobFE")));

  /**
   * Get the {@code unsolicitedByteArray} property.
   * @see #unsolicitedByteArray
   */
  public BBlob getUnsolicitedByteArray() { return (BBlob)get(unsolicitedByteArray); }

  /**
   * Set the {@code unsolicitedByteArray} property.
   * @see #unsolicitedByteArray
   */
  public void setUnsolicitedByteArray(BBlob v) { set(unsolicitedByteArray, v, null); }

  //endregion Property "unsolicitedByteArray"

  //region Property "messageBlocks"

  /**
   * Slot for the {@code messageBlocks} property.
   * This is a special folder to contain user defined FlexMessageBlocks.
   * FlexMessageBlocks are a collection of one or more FlexMessageElements.
   * @see #getMessageBlocks
   * @see #setMessageBlocks
   */
  public static final Property messageBlocks = newProperty(0, new BFlexMessageBlockFolder(), null);

  /**
   * Get the {@code messageBlocks} property.
   * This is a special folder to contain user defined FlexMessageBlocks.
   * FlexMessageBlocks are a collection of one or more FlexMessageElements.
   * @see #messageBlocks
   */
  public BFlexMessageBlockFolder getMessageBlocks() { return (BFlexMessageBlockFolder)get(messageBlocks); }

  /**
   * Set the {@code messageBlocks} property.
   * This is a special folder to contain user defined FlexMessageBlocks.
   * FlexMessageBlocks are a collection of one or more FlexMessageElements.
   * @see #messageBlocks
   */
  public void setMessageBlocks(BFlexMessageBlockFolder v) { set(messageBlocks, v, null); }

  //endregion Property "messageBlocks"

  //region Property "messages"

  /**
   * Slot for the {@code messages} property.
   * This is a special folder to conatin user defined FlexMessages.
   * A FlexMessage is a collection of FlexMessageBlock references and
   * FlexMessageElements used to model a native device message.
   * @see #getMessages
   * @see #setMessages
   */
  public static final Property messages = newProperty(0, new BFlexMessageFolder(), null);

  /**
   * Get the {@code messages} property.
   * This is a special folder to conatin user defined FlexMessages.
   * A FlexMessage is a collection of FlexMessageBlock references and
   * FlexMessageElements used to model a native device message.
   * @see #messages
   */
  public BFlexMessageFolder getMessages() { return (BFlexMessageFolder)get(messages); }

  /**
   * Set the {@code messages} property.
   * This is a special folder to conatin user defined FlexMessages.
   * A FlexMessage is a collection of FlexMessageBlock references and
   * FlexMessageElements used to model a native device message.
   * @see #messages
   */
  public void setMessages(BFlexMessageFolder v) { set(messages, v, null); }

  //endregion Property "messages"

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

  //region Topic "unsolicitedMessageReceived"

  /**
   * Slot for the {@code unsolicitedMessageReceived} topic.
   * @see #fireUnsolicitedMessageReceived
   */
  public static final Topic unsolicitedMessageReceived = newTopic(0, null);

  /**
   * Fire an event for the {@code unsolicitedMessageReceived} topic.
   * @see #unsolicitedMessageReceived
   */
  public void fireUnsolicitedMessageReceived(BValue event) { fire(unsolicitedMessageReceived, event, null); }

  //endregion Topic "unsolicitedMessageReceived"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexSerialNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  /**
   * If this driver is to be licensed using the standard licensing
   * mechanism then override this method to return the Feature or
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  public Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "flexSerial");
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Returns the BModbusAsciiDevice type.
   */
  public Type getDeviceType()
  {
    return BFlexSerialDevice.TYPE;
  }

  /**
   * Returns folder type.
   */
  public Type getDeviceFolderType()
  {
    return BFlexSerialDeviceFolder.TYPE;
  }

  /**
   * Return a new instance of the custom ModbusAscii communication
   * handler
   */
  protected Comm makeComm()
  {
    return new FlexSerialComm(this);
  }

  public void started()
  throws Exception
  {
    super.started();
    FlexSerialComm comm = (FlexSerialComm)getComm();
    messageDefChanged();
    comm.setMaxReceiveSilentTime(getMaxReceiveSilentTime());

    getInitMessage().forceMessage();
    unsolicitedReceive = new FlexSerialUnsolicitedReceive(this);
    unsolicitedReceive.init();
    comm.registerListener(unsolicitedReceive);
    unsolicitedReceive.start();

  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if(!isRunning()) return;

    //System.out.println(" serialNetworkChanged with: " + p);
    //if(p.equals(sendMessageSetup) || p.equals(receiveMessageSetup))
    //{
    //  messageDefChanged();
    //}
    //else if(p.equals(stripReceiveFraming))
    //{
    //  FlexSerialComm comm = (FlexSerialComm)getComm();
    //  comm.setStripReceiveFraming(getStripReceiveFraming());
    //}
    //else if(p.equals(maxReceiveSilentTime))
    if(p.equals(maxReceiveSilentTime))
    {
      ((FlexSerialComm)getComm()).setMaxReceiveSilentTime(getMaxReceiveSilentTime());
    }

  }

  public void messageDefChanged()
  {
    FlexSerialComm comm = (FlexSerialComm)getComm();
    //comm.setSendMessageSetup(getSendMessageSetup());
    //comm.setReceiveMessageSetup(getReceiveMessageSetup());
    comm.setReceiveMessageSetup(getMessageBlocks().getFrameStart().getByteArray(),
                                getMessageBlocks().getFrameEnd().getByteArray()    );
  }

  //public void doPing()
  //{
  //  pingFail("ping fail");
  //}


  /**
   * Filter out frozen slots which except for message folders.
   *
   */
  public BINavNode[] getNavChildren()
  {
    //BINavNode[] kids = super.getNavChildren();
    BINavNode[] kids = getNavChildren1();
    Array<BINavNode> acc = new Array<>(BINavNode.class);
    for(int i=0; i<kids.length; ++i)
    {
      BComponent kid = (BComponent)kids[i];
      //System.out.println("getNavChildren: " + kid.getName());
      if (kid.getPropertyInParent().isFrozen()      &&
          !(kid instanceof BFlexMessageBlockFolder) &&
          !(kid instanceof BFlexMessageFolder)              ) continue;
      acc.add(kid);
    }
    return acc.trim();
  }


  /**
   * Return all the non-hidden child components..
   */
  public BINavNode[] getNavChildren1()
  {
    loadSlots();
    BComponent[] temp = new BComponent[getSlotCount()];
    SlotCursor<Property> c = getProperties();
    int count = 0;
    while(c.nextComponent())
    {
      BComponent kid = (BComponent)c.get();
      //System.out.println("getNavChildrenxxx: " + kid.getName());
      if (Flags.isHidden(this, c.property())) continue;
      if (!kid.isNavChild()) continue;
      temp[count++] = kid;
    }

    BComponent[] result = new BComponent[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;
  }

  public static BFlexSerialNetwork getParentFlexNetwork(BComplex child)
  {
    BComplex parent = child.getParent();
    //System.out.println(" getParentFlexNetwork -- child = " + child);
    while (parent != null)
    {
      //stem.out.println(" getParentFlexNetwork -- parent = " + parent.getName());
      if(parent instanceof BFlexSerialNetwork)
        return (BFlexSerialNetwork)parent;
      parent = parent.getParent();
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  FlexSerialUnsolicitedReceive unsolicitedReceive;
}
