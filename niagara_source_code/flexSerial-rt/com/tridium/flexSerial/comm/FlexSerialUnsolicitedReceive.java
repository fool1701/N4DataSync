/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import java.util.*;
import java.io.*;

import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.log.*;
import javax.baja.util.*;

import com.tridium.basicdriver.*;
import com.tridium.basicdriver.message.*;
import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.flexSerial.comm.*;

/**
 * The receive thread for processing unsolicited received messages 
 * from the flexSerial driver.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  29 Feb 00
 * @author    Scott Hoye (Upgraded for R3)
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 11/13/02 12:47:14 PM$  
 * @since     Niagara 3.0 flexSerial 1.0     
 */
public class FlexSerialUnsolicitedReceive 
  implements Runnable, UnsolicitedMessageListener, SerialMessageConst
{

////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////
  public FlexSerialUnsolicitedReceive(BFlexSerialNetwork host)
  {
    this.host = host;
  }
  

  /**********************************************
  * Initialize
  **********************************************/
  public final void init()
  {
  }

  /**********************************************
  * Cleanup the service.
  **********************************************/
  public final void cleanup()
  {
  }

  /**********************************************
  * Start the engine thread.
  **********************************************/
  public final void start()
  {
    timeToDie = false;
    myThread = new Thread(this, "FlexSerialUnsolicitedReceive");
    myThread.start();
  }

  /**********************************************
  * Stop the engine thread.
  **********************************************/
  public final void stop()
  {
    timeToDie = true;
    myThread.interrupt();
    //myThread.stop();
  }

  public boolean isDying()
  {
    return timeToDie;
  }

////////////////////////////////////////////////////////////////
//  Required run method
////////////////////////////////////////////////////////////////
  public void run()
  {
    SerialReceivedMessage newMessage;
    //AppBufferElement unsolicitedMsg;
    MessageElement unsolicitedMsg;

    //
    //  Initialize the unsolicitedMessageManager
    //
    unsolicitedMessageManager = new TLinkedListManager("FlexSerialUnsolicitedReceive Manager");

    while(!timeToDie)
    {

      try
      {
        unsolicitedMsg = (MessageElement)unsolicitedMessageManager.removeFromHead(-1);
      }
      catch( InterruptedException e)
      {
        System.out.println("\n processing message");
        //host.getSerialLog().error("FlexSerialUnsolicitedReceive.run Problem with " + unsolicitedMessageManager.getName() + ": ", e);
        unsolicitedMsg = null;
      }
      
      try
      {

        if ( unsolicitedMsg != null)
        {
          newMessage = unsolicitedMsg.getMessage();
          processUnsolicitedMessage(newMessage);
        }
          
      }
      catch(Exception e)
      {
        System.out.println(" FlexSerialUnsolicitedReceive thread caught Exception: " + e);
        //host.getSerialLog().error(" FlexSerialUnsolicitedReceive thread caught Exception: ", e);
      }
    }

  } // end of run

  /**********************************************
  *  Process the unsolicited message
  *
  *  @param  rcvMessage incoming SerialDriver message 
  **********************************************/
  protected synchronized void processUnsolicitedMessage( SerialReceivedMessage message )
  {
    String rcvMessage = message.getStringMessage();
//    System.out.println("received message = " + rcvMessage);
    host.getUnsolicitedMessage().setValue(rcvMessage);
    host.setUnsolicitedByteArray(BBlob.make(message.getBytes(), 0, message.getLength()));
    host.fireUnsolicitedMessageReceived(null);
  }
  
  ////////////////////////////////////////////////////
  // Access method
  ////////////////////////////////////////////////////
  /**********************************************
  *  Access point called by the PhastOpsDriver 
  *  receive API to place unsolicited messages  
  *  on the UnsolicitedMessages queue
  *
  *  @param  rcvMessage incoming PhastOps message 
  **********************************************/
  public void receiveMessage( ReceivedMessage message)
  {
//    System.out.println("****** entered receiveMessage *****");
    MessageElement msgElement = new MessageElement((SerialReceivedMessage)message);

    //
    //  Make sure the receive thread has defined
    //  the manager
    //
    if ( unsolicitedMessageManager != null)
    {
      unsolicitedMessageManager.addToTail( msgElement);

    }

  }
  private static Integer DEFAULT_TAG = Integer.valueOf(-1);
  public Object getUnsolicitedListenerCode()
  {
    return DEFAULT_TAG;
  }

  /////////////////////////////////////////////////////////////
  //  Attributes of FlexSerialUnsolicitedReceive class
  /////////////////////////////////////////////////////////////
  private TLinkedListManager  unsolicitedMessageManager = null;
  private BFlexSerialNetwork  host;
  private boolean             timeToDie = true;
  private Thread              myThread;

} // end of class FlexSerialUnsolicitedReceive
