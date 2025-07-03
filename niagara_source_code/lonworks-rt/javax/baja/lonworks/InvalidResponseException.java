/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

/**
 * A InvalidResponseException is thrown when a response other
 * success or failed. It is thrown in the toResponse() of 
 * LonMessage subclasses. 
 *
 * An invalid response indicates a Neuron processing error.  
 * A failed response is sent by the target device when it is unable
 * to process the request.  
 *
 * @author    Robert Adams
 * @creation  8 Oct 2007
 * @version   $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @since     Niagara 3.0
 */

public class InvalidResponseException
  extends LonException
{
  /**  Constructor.  */
  public InvalidResponseException(int code)
  {
    super("Invalid message code " + Integer.toString(code,16), null);
  }
}
