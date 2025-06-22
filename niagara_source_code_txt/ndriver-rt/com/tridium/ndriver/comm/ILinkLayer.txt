/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import javax.baja.spy.SpyWriter;
import com.tridium.ndriver.datatypes.BCommConfig;

/**
 * ILinkLayer is interface implemented by linkLayers which are coupled to
 * NComm.
 *
 * @author Robert A Adams
 * @creation Oct 21, 2011
 */
public interface ILinkLayer
{
  /**
   * Comm config parameter change.  Check if link layer needs restart. - called
   * from NComm.verifySettings()
   */
  void verifySettings(BCommConfig comCfg) throws Exception;

  /**
   * Start linklayer - called from NComm.start()
   */
  void start() throws Exception;

  /**
   * Stop linklayer  - called from NComm.stop()
   */
  void stop();

  /**
   * Entry point for NComm to send message to link layer
   */
  void sendMessage(LinkMessage msg) throws Exception;

  /**
   * Provide some spy debug - called from NComm.spy()
   */
  void spy(SpyWriter out) throws Exception;

  /**
   * Reset any statistical counters - called from NComm.resetStats()
   */
  void resetStats();
}
