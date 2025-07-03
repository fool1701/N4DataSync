/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.tcp;

import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * ITcpEventListener is interface for objects desiring to register to receive
 * tcp event callbacks.
 *
 * @author Robert A Adams
 * @creation Feb 28, 2012
 */
public interface ITcpEventListener
{
  /**
   * Callback when a socket connection is terminated.
   *
   * @param addr   - address of socket host
   * @param server - true if connection initiated by host
   */
  void socketTerminated(BIpAddress addr, boolean server);
}
