/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import java.io.*;

import javax.baja.io.*;
import javax.baja.log.Log;
import javax.baja.nre.util.*;
import javax.baja.util.*;
import javax.baja.sys.*;

import com.tridium.basicdriver.message.*;
import com.tridium.basicdriver.comm.*;

import com.tridium.flexSerial.messages.*;

/**
 * The FlexSerialCommReceiver class overrides FlexSerialCommReceiver
 * to act as the Serial receive driver for the serial port.
 * It determines when a received message is complete, and
 * routes the received message to the Comm handler.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  05 June 2003
 * @version   $Revision: 1$ $Date: 09/05/02 12:47:14 PM$
 * @since     Niagara 3.0
 */
public class FlexSerialCommReceiver
  extends CommReceiver
  implements SerialMessageConst
{

 /**
  * Constructor
  public FlexSerialCommReceiver()
  {
  }
  */

  public void setFrameStart(byte[] start)
  {
    frameStart = start;
    //System.out.println("frameStart = " + ByteArrayUtil.toHexString(frameStart));
  }

  public void setFrameEnd(byte[] end)
  {
    frameEnd = end;
    //System.out.println("frameEnd = " + ByteArrayUtil.toHexString(frameEnd));
  }

  public void setStripReceiveFraming(boolean strip)
  {
    stripReceiveFraming = strip;
  }

  public void setMaxReceiveSilentTime(int time)
  {
    maxReceiveSilentTime = (long)time;
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
    if(frameStart.length == 0)
      state = WAIT_FOR_FRAME_START;
    else
      state = WAIT_FOR_FRAME_START;
    boolean done = false;
    rcvBuffer.reset();

    while (!done)
    {
      int charIn = getInputStream().read();
      boolean newChar= (charIn != -1);
      charIn = charIn & 0x00ff;

      // process flexSerial message
      if (newChar)
      {
        getLog().trace("state= " + state + "; rx= " + Integer.toHexString(charIn));
        switch(state)
        {
        case WAIT_FOR_FRAME_START:
            if(frameStart.length == 0)
            {
              rcvBuffer.reset();
              rcvBuffer.write((byte)charIn);
              state = READ_DATA;
              break;
            }
            if(charIn != (frameStart[0] & 0x0ff) )
              break;
            //System.out.println("frameStart match");
            rcvBuffer.reset();
            if(!stripReceiveFraming)
              rcvBuffer.write((byte)charIn);
            if(frameStart.length == 1)
              state = READ_DATA;
            else
              state = WAIT_FOR_FRAME_START+1;
            break;
          case WAIT_FOR_FRAME_START+1:
          case WAIT_FOR_FRAME_START+2:
          case WAIT_FOR_FRAME_START+3:
          case WAIT_FOR_FRAME_START+4:
          case WAIT_FOR_FRAME_START+5:
          case WAIT_FOR_FRAME_START+6:
          case WAIT_FOR_FRAME_START+7:
          case WAIT_FOR_FRAME_START+8:
          case WAIT_FOR_FRAME_START+9:
          case WAIT_FOR_FRAME_START+10:
            if(!stripReceiveFraming)
              rcvBuffer.write((byte)charIn);
            int index = state - WAIT_FOR_FRAME_START;
            //System.out.println("frameStart["+ index + "] = " + Integer.toHexString(frameStart[index]));
            if(charIn == (frameStart[index] & 0x0ff))
            {
              index++;
              state++;
              if(frameStart.length == index)
                state = READ_DATA;
            }
            else
              state = WAIT_FOR_FRAME_START;
            break;

          case READ_DATA:
            if(frameEnd.length > 0)
            {
              if(charIn == (frameEnd[0] & 0x0ff))
              {
                if(!stripReceiveFraming)
                  rcvBuffer.write((byte)charIn);
                if(frameEnd.length == 1)
                  done = true;
                else
                  state = READ_FRAME_END+1;
              }
              else
                rcvBuffer.write((byte)charIn);
            }
            else
              rcvBuffer.write((byte)charIn);
            break;
          default:
            if(!stripReceiveFraming)
              rcvBuffer.write((byte)charIn);
            index = state - READ_FRAME_END;
            //System.out.println("frameEnd["+ index + "] = " + Integer.toHexString(frameEnd[index]));
            if(charIn == (frameEnd[index] & 0x0ff))
            {
              index++;
              state++;
              if(frameEnd.length == (index))
                done = true;
            }
            else
              state = READ_DATA;
        }
        if(rcvBuffer.getLength() > 0)
          lastCharTicks = Clock.ticks();

      }
      if(frameEnd.length == 0)
      {
        if( (rcvBuffer.getLength() > 0) && (maxReceiveSilentTime > 0) )
        {
          if(Clock.ticks() - lastCharTicks > maxReceiveSilentTime)
            done = true;
        }
      }
    }

    if (msg == null)
      msg = new SerialReceivedMessage(rcvBuffer.getBytes(), rcvBuffer.getLength(), DEFAULT_TAG);
    else
    {
      msg.setBytes(rcvBuffer.getBytes());
      msg.setLength(rcvBuffer.getLength());
      msg.setTag(DEFAULT_TAG);
    }
    return msg;
  }

  Log getLog()
  {
    Log netLog = getComm().getNetwork().getLog();
    return Log.getLog(netLog.getLogName()+".rx");
  }

////////////////////////////////////////////////////////////////
// Static values
////////////////////////////////////////////////////////////////

  private static Integer DEFAULT_TAG = Integer.valueOf(-1);

  // Process state constants
  private static final int WAIT_FOR_FRAME_START  = 0;
  private static final int READ_DATA             = 20;
  private static final int READ_FRAME_END        = 30;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private long lastCharTicks = 0l;
  private long maxReceiveSilentTime = 0l;
  private byte[] frameStart = new byte[0];
  private byte[] frameEnd = { (byte)0x0d };
  private boolean stripReceiveFraming = false;
  private int state = WAIT_FOR_FRAME_START;
  private ByteBuffer rcvBuffer = new ByteBuffer();
  private SerialReceivedMessage msg = null;

} // end of class
