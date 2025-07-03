/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.sys.BajaException;

/**
 * A LonException is thrown when an error occurs in the
 * lonworks driver.
 *
 * @author    Robert Adams
 * @creation  19 Feb 01
 * @version   $Revision: 14$ $Date: 10/17/00 12:47:14 PM$
 * @since     Niagara 3.0
 */
public class LonException
  extends BajaException
{

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////
  /**
   * Constructor.
   * @param s String describing cause of exception.
   */
  public LonException(String s)
  {
    super(s,null);
  }

  /**
   * Constructor.
   * @param s String describing cause of exception.
   */
  public LonException(String s, Throwable cause)
  {
    super(s,cause);
  }
}
