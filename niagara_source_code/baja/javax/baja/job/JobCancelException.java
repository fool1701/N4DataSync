/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import javax.baja.sys.*;

/**
 * JobCancelException is the desired exception to used to 
 * exit out of a method during a BJob.cancel.
 *
 * @author    Brian Frank
 * @creation  22 Jul 04
 * @version   $Revision: 2$ $Date: 9/5/07 10:18:56 AM EDT$
 * @since     Baja 1.0
 */
public class JobCancelException
  extends BajaRuntimeException
{

  /**
   * Construct a JobCancelException with the given message.
   */
  public JobCancelException(String msg)
  {
    super(msg);    
  }

  public JobCancelException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Construct a JobCancelException.
   */
  public JobCancelException()
  {
  }
    
}
