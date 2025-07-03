/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

/**
 * The link listener interface is implemented by objects that
 * wish to register to receive LonMessages from lonComm
 * <p>
 *  
 * @author    Robert Adams
 * @creation  14 Feb 02
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
public interface LonListener
{
  /**
   * Receive an unsolicited LonMessage from LonComm.
   * 
   *  @param msg
   */
  public void receiveLonMessage(LonMessage msg);

}