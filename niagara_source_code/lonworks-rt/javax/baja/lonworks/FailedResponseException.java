/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

/**
 * A FailedResponseException is thrown when and error response
 * message is received for a request LonMessage. It is thrown 
 * in the toResponse() of LonMessage subclasses.
 *
 * @author    Robert Adams
 * @creation  29 Oct 02
 * @version   $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @since     Niagara 3.0
 */

public class FailedResponseException
  extends LonException
{
  /**  Constructor.  */
  public FailedResponseException()
  {
    super("Received error response.",null);
  }

}
