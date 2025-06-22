/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm;

import javax.baja.sys.BajaException;

/**
 * A NCommException is thrown when an error occurs in the comm stack.
 *
 * @author Robert Adams
 * @version $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @creation Oct 21, 2011
 * @since Niagara 3.7
 */
public class NCommException
  extends BajaException
{

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   *
   * @param s String describing cause of exception.
   */
  public NCommException(String s)
  {
    super(s, null);
  }

  /**
   * Constructor.
   *
   * @param s String describing cause of exception.
   */
  public NCommException(String s, Throwable cause)
  {
    super(s, cause);
  }

  private static final long serialVersionUID = -238365183218147481L;
}
