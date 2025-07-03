/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import javax.baja.sys.*;

/**
 * UnitException is the base class of unit exceptions.
 *
 * @author    Brian Frank
 * @creation  18 Dec 01
 * @version   $Revision: 1$ $Date: 12/18/01 9:27:48 AM EST$
 * @since     Baja 1.0
 */
public class UnitException
  extends BajaRuntimeException
{

  /**
   * Construct a UnitException with the given message.
   */
  public UnitException(String msg)
  {
    super(msg);    
  }

  /**
   * Construct a UnitException.
   */
  public UnitException()
  {
  }
    
}
