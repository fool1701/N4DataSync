/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

/**
 * ICommFilter is interface for message filters passed when registering an
 * {@link ICommListener}.
 *
 * @author Robert A Adams
 * @creation Oct 21, 2011
 */
public interface ICommFilter
{
  /**
   * Return true if the specified messages matches this filters criteria.
   *
   * @param Message msg
   */
  boolean accept(NMessage msg);
}
