/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.datatypes.BSubnetNode;
import javax.baja.lonworks.datatypes.LonAddress;

/**
 * LonComm defines the api for the lonworks communication
 * stack. It provides a means to send and receive messages
 * using the various service types. It also provides the
 * ability for a <code>LonListener</code> to register to
 * receive unsolicited messages.
 *
 * @author    Robert Adams
 * @creation  19 Feb 02
 * @version   $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @since     Niagara 3.0
 */
public interface LonComm
{ 

  /**
   *  Send a message using request/response service to
   *  the specified address. Block the calling thread 
   *  until the response is received or the transaction times out. 
   *  Return the response or null if there is a transmission error or
   *  failed response. 
   */
  public LonMessage sendRequest( LonAddress destAddr, LonMessage netRequest)
    throws LonException;

  /**
   *  Send a message using acknowledged service to
   *  the specified address. Block the calling thread 
   *  until the ack is received or the transaction times out.  
   */
  public void sendAcked( LonAddress destAddr, LonMessage netRequest)
    throws LonException;

  /**
   *  Send a response to message received from lonComm. The original
   *  message will contain the neccessary information to complete the
   *  transaction.
   */
  public void sendResponse( LonMessage  origMsg, LonMessage netResponse);

  /**
   *  Send a message using unacknowledged service to
   *  the specified address. Block the calling thread 
   *  until the message is sent(Neuron returns completion event).  
   */
  public void sendUnacknowledged( LonAddress destAddr, LonMessage netRequest)
    throws LonException;

  /**
   *  Send a message using unacknowledged repeat service to
   *  the specified address. Block the calling thread 
   *  until the message is sent(Neuron returns completion event).  
   */
  public void sendUnackRepeat( LonAddress destAddr, LonMessage netRequest)
    throws LonException;
 
  
  /**
   * Register a <code>LonListener</code> to receive unsolicited messages.
   *
   * @param listner Reference to object which implements the LonListener interface.
   *                Will receive <code>receiveLonMessage()</code> callbacks.<p>
   * @param msgCode The message code of messages to route to listener. The same 
   *                listner may be registered for multiple message codes. Multiple
   *                listners may register for the same message code.<p>
   * @param address Optional reference to address of device from which to receive messages
   *                  from.  If null all messages of the specified msgCode will be
   *                  routed to listner.<p>
   * @param messageClass Optional subclass of LonMessage for decoding received 
   *                     message.  If null a <code>LonMessage</code> will be passed in 
   *                     receiveLonMessage() callback.  Specifying the messageClass
   *                     will make process more efficient.
   */
  public void registerLonListener(LonListener listner, int msgCode, BSubnetNode address, Class<?> messageClass);
  
  /**
   * Unregister the specified <code>LonListener</code>.
   * 
   * @param listner <code>LonListener</code> passed in <code>registerLonListener</code> call to be unregistered.<p>
   * @param msgCode The message code of message type to stop listening for.  If -1 
   *                then unregister listener for all msgCodes currently registered for.<p>
   * @param address The address passed in <code>registerLonListener</code> call.  If null
   *                then unregister listner for all cases it is registered for specified
   *                msgCode. 
   */
  public void unregisterLonListener(LonListener listner, int msgCode, BSubnetNode address);


  /** Convenience method to access the network object. */
  public  BLonNetwork lonNetwork();
       
}
