/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.sys.BajaRuntimeException;

/**
 * Base exception class for alarm.
 *
 * @author    John Sublett
 * @creation  22 Sep 2004
 * @version   $Revision: 1$ $Date: 10/1/04 3:21:16 PM EDT$
 * @since     Baja 1.0
 */
public class AlarmException
  extends BajaRuntimeException
{
  /**
   * Constructor with specified message and nested exception.
   */
  public AlarmException(String msg, Throwable e)
  {  
    super(msg, e);
  }

  /**
   * Constructor with specified nested exception.
   */
  public AlarmException(Throwable e)
  {  
    super(e);
  }

  /**
   * Construct a AlarmException with the given message.
   */
  public AlarmException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public AlarmException()
  {  
  }

}

