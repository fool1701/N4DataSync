/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

/**
 * A NCommTimeoutException is indicates a transaction timeout waiting for
 * response message.
 *
 * @author Robert Adams
 * @version $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @creation Mar 14, 2012
 * @since Niagara 3.7
 */
public class NCommTimeoutException
  extends NCommException
{

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   *
   * @param s String describing cause of exception.
   */
  public NCommTimeoutException()
  {
    super("Timed out waiting for response.", null);
  }

  public NCommTimeoutException(String traceString)
  {
    super("Timed out waiting for response.\n" + traceString, null);
  }

  private static final long serialVersionUID = 5924259180622259415L;
}
