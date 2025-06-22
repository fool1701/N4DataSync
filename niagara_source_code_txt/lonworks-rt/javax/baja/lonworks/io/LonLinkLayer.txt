/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.io;

/**
 *  The BILonLinkLayer is interface used to provide a mechanism for pluggable link layers.
 *  
 * <p>
 *  
 * @author    Robert Adams
 * @creation  22 May 2006
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
public interface LonLinkLayer
{  
  /** Comm config parameter change.  Check if link layer needs restart. */
  public void verifySettings() throws Exception;
 
  /** Start linklayer  */
  public void start() throws Exception;
  
  /** Stop linklayer  */
  public void stop();

  /** Entry point for LonComm to send message to link layer*/
  public void sendLonMessage(AppBuffer msg);
  
}
