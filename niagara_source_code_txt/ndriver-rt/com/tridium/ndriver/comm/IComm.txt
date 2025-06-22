/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import javax.baja.spy.SpyWriter;
import com.tridium.ndriver.datatypes.BCommConfig;

/**
 * IComm is interface for comm stack objects that are configured by subclasses
 * of CommConfig.
 *
 * @author Robert A Adams
 * @creation June 13, 2012
 */
public interface IComm
{
  /**
   * Start the comm stack components so that messages can be processed.
   */
  void start() throws Exception;

  /**
   * Stop the comm stack components.
   */
  void stop() throws Exception;

  /**
   * Called when comm config parameter changes to allow any resources to be
   * reinitialized and if needed restarted.
   */
  void verifySettings(BCommConfig comCfg) throws Exception;

  /**
   * Provide some spy debug
   */
  void spy(SpyWriter out) throws Exception;

  /**
   * Reset any statistical counters.
   */
  void resetStats();

  /**
   * Set the default listener.
   */
  void setDefaultListener(ICommListener listener);
}
