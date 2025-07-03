/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

/**
 * ICommListener is interface for objects desiring  to receive
 * specific message types. ICommListener implementations are passed
 * as arguments to NComm.
 *
 * @author Robert A Adams
 * @creation Oct 21, 2011
 *
 */
public interface ICommListener
{
  /**
   * Receive an unsolicited message from comm.
   * 
   *  @param Message msg 
   */
  void receiveMessage(NMessage msg);
}
