/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import com.tridium.basicdriver.serial.*;
import com.tridium.flexSerial.*;

/**
 * The FlexSerialComm class is used to synchronize access to a
 * Serial network and handles the synchronization of the serial
 * communication.
 *
 * @author    Andy Saunders
 * @creation  05 June 2003
 * @version   $Revision: 1$ $Date: 03/26/02 12:47:14 PM$
 * @since     Niagara 3.0 basicdriver 1.0
 */

public class FlexSerialComm
  extends SerialComm
{

 /**
  * Constructor - initializes the SerialComm with a specified BModbusAsciiNetwork
  * Uses the ModbusAsciiCommReceiver + CommTransmitter + CommTransactionManager.
  */
  public FlexSerialComm(BFlexSerialNetwork network)
  {
    super(network, new FlexSerialCommReceiver(), new FlexSerialCommTransmitter());
  }

  public void setSendMessageSetup(BMessageDef messageDef)
  {
    FlexSerialCommTransmitter transmitter = (FlexSerialCommTransmitter)getCommTransmitter();
    transmitter.setFrameStart(messageDef.getFrameStartBytes());
    transmitter.setFrameEnd(messageDef.getFrameEndBytes());
  }

  public void setReceiveMessageSetup(BMessageDef messageDef)
  {
    /*
    FlexSerialCommReceiver receiver = (FlexSerialCommReceiver)getCommReceiver();
    receiver.setFrameStart(messageDef.getFrameStartBytes());
    receiver.setFrameEnd(messageDef.getFrameEndBytes());
    */
  }

  public void setReceiveMessageSetup(byte[] frameStart, byte[] frameEnd)
  {
    FlexSerialCommReceiver receiver = (FlexSerialCommReceiver)getCommReceiver();
    receiver.setFrameStart(frameStart);
    receiver.setFrameEnd(frameEnd);
  }

  public void setStripReceiveFraming(boolean strip)
  {
    FlexSerialCommReceiver receiver = (FlexSerialCommReceiver)getCommReceiver();
    receiver.setStripReceiveFraming(strip);
  }

  public void setMaxReceiveSilentTime(int time)
  {
    FlexSerialCommReceiver receiver = (FlexSerialCommReceiver)getCommReceiver();
    receiver.setMaxReceiveSilentTime(time);
  }
}