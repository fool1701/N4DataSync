/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.nrio;

/**
 * NrioException is the base exception class for functionality.
 *
 * @author    Bill Smith       
 * @creation  20 Jan 2004
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */

public class NrioException extends Exception
{
  public NrioException()
  {
    super();
  }

  public NrioException(String msg)
  {
    super(msg);
  }
}
