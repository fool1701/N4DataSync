/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import javax.baja.sys.BajaRuntimeException;

/**
 * MigrationException is the root exception for all specialized checked
 * exceptions thrown by the migration tool.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 3/10/2015
 *         Time: 4:32 PM
 */
public class MigrationException
  extends BajaRuntimeException
{
  /**
   * Constructor with message and cause.
   * @param msg  Detail message
   * @param cause Nested exception
   */
  public MigrationException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with message and cause.
   * @param msg  Detail message
   */
  public MigrationException(String msg)
  {
    super(msg);
  }

  /**
   * Constructor with message and cause.
   * @param cause Nested exception
   */
  public MigrationException(Throwable cause)
  {
    super(cause);
  }

  /**
   * No argument constructor.
   */
  public MigrationException()
  {
    super();
  }

}
