/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.timezone;

import javax.baja.sys.*;

/**
 * TimeZoneException is the base class for exceptions related to time zones. 
 *
 * @author    John Sublett
 * @creation  11 Mar 2004
 * @version   $Revision: 2$ $Date: 6/27/07 4:41:18 PM EDT$
 * @since     Baja 1.0
 */
public class TimeZoneException
  extends BajaRuntimeException
{
  /**
   * Construct a TimeZoneException with the given message.
   */
  public TimeZoneException(final String msg)
  {
    super(msg);    
  }

  /**
   * Construct a TimeZoneException.
   */
  public TimeZoneException()
  {
  }
}