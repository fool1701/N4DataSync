/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.bog;

import javax.baja.sys.BajaRuntimeException;

/**
 * @author    Brian Frank       
 * @creation  14 Apr 03
 * @version   $Revision: 1$ $Date: 4/14/03 10:42:47 AM EDT$
 * @since     Baja 1.0
 */
public class CannotLoadBogException
  extends BajaRuntimeException
{

  public CannotLoadBogException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
  
  public CannotLoadBogException(String msg)
  {
    super(msg);
  }
  
}
