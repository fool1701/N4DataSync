/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

/**
 * IMessageFactory is interface for driver specific message factory that will
 * turn LinkMessages into NMessages. BCommConfig.makeMessageFactory() is
 * overridden to create a drivers specific implementation for use by NComm.
 *
 * @author Robert A Adams
 * @creation Feb 16, 2012
 */
public interface IMessageFactory
{
  /**
   * Create the appropriate NMessage from specified the LinkMessage.
   *
   * @throws Exception
   */
  NMessage makeMessage(LinkMessage lm) throws Exception;
}
