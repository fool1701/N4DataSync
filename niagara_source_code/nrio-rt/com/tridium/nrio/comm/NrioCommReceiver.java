/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.comm;

import javax.baja.nre.util.ByteBuffer;
import com.tridium.basicdriver.comm.CommReceiver;
import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;


/**
 * The NrioCommReceiver class overrides CommReceiver
 * to act as the GeM6 receive driver for the serial port.
 * It determines when a received message is complete, and
 * routes the received message to the Comm handler.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  05 June 2003
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$
 * @since     Niagara 3.0
 */
public class NrioCommReceiver
  extends CommReceiver
  implements NrioMessageConst
{

 /**
  * Constructor
  */
  public NrioCommReceiver()
  {
  }

   public void initReceiveState(Message msg)
   {
     rcvBuffer.reset();
     state = WAIT_FOR_SOH;
   }

  /**
   * Execution of the receive driver.  Continously called to
   * look for received characters on the serial comm line
   * and once a complete message is received, returns the
   * message to the parent Comm for processing.
   */
  protected ReceivedMessage receive()
    throws Exception
  {
    // Initialize the state machine.
    state = WAIT_FOR_SOH;
    boolean done = false;

    while (!done)
    {
      int charIn = getInputStream().read();
      boolean newChar= (charIn != -1);
      charIn = charIn & 0x00ff;
      // process message
      if (newChar)
      {
        //System.out.println(" NrioCommReceiver received char: " + Integer.toHexString(charIn) + " State = " + state + " length = " + length);
        switch(state)
        {
        case IDLE:
          break;

        case WAIT_FOR_SOH:
          if(charIn != SOH)
            break;
          rcvBuffer.reset();
          rcvBuffer.write((byte)charIn);
          state = READ_LENGTH;
          break;
        case READ_LENGTH:
          length = charIn;
          rcvBuffer.write((byte)charIn);
          state = READ_XID;
            break;
        case READ_XID:
          rcvBuffer.write((byte)charIn);
          transactionId = charIn;
          length--;
          state = READ_DATA;
          break;
        case READ_DATA:
          rcvBuffer.write((byte)charIn);
          if(--length <= 0)
            done = true;
          break;
        }
      }
    }

    Integer tag = Integer.valueOf(-1);
    if (msg == null)
      msg = new NrioReceivedMessage(rcvBuffer.getBytes(), rcvBuffer.getLength(), tag);
    else
    {
      msg.setBytes(rcvBuffer.getBytes());
      msg.setLength(rcvBuffer.getLength());
      msg.setTag(tag);
    }
    state = WAIT_FOR_SOH;
//        System.out.println(" Returning message: " + msg);
    return msg;
  }


////////////////////////////////////////////////////////////////
// Static values
////////////////////////////////////////////////////////////////

  // Process state constants
  private static final int IDLE         = 0;
  private static final int WAIT_FOR_SOH = 1;
  private static final int READ_LENGTH  = 2;
  private static final int READ_XID     = 3;
  private static final int READ_DATA    = 4;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int transactionId = 0;
  private int state = IDLE;
  private int length = 0;
  private ByteBuffer rcvBuffer = new ByteBuffer();
  private NrioReceivedMessage msg = null;

} // end of class
