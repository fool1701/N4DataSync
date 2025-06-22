/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

/**
 * IFragmentable interface must be implemented by NMessage subclasses that
 * represent messages that can be received in fragments.
 *
 * @author Robert A Adams
 * @creation Feb 23, 2012
 */
public interface IFragmentable
{
  /**
   * Merge the specified fragment into this message.  Called as each fragment
   * after the first is received.
   *
   * @param IFragmentable frag
   */
  void merge(IFragmentable frag) throws Exception;

  /**
   * Return true if this is the last or only fragment for a message.
   */
  boolean isFinalFragment();

  /**
   * Get hash object used to match fragments.  Hash must be able to distinguish
   * between fragments for different messages and be the same for fragments of
   * the same message.
   */
  Object getHash();

  /**
   * Get time to allow to receive all fragments in milliseconds.
   */
  int getTimeout();

  /**
   * Current size of message as it is being reconstructed. Used for spy.
   */
  int size();

  /**
   * Get fragment ack message if the protocol requires one. The fragment ack
   * message will be sent immediately to the link layer. <p>
   *
   * @return fragment ack message or null.
   */
  NMessage getFragmentAck();
}
