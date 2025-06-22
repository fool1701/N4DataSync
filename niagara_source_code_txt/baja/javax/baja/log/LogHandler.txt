/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.log;

/**
 * LogHandler is implemented by objects which
 * wish to receive log callbacks.
 *
 * @author    Brian Frank
 * @creation  9 Apr 00
 * @version   $Revision: 3$ $Date: 11/15/00 8:08:40 AM EST$
 * @since     Baja 1.0
 * @deprecated as of Niagara 4.0.
 */
@Deprecated
public interface LogHandler
{

  /**
   * Publish a log record.
   */
  public void publish(LogRecord record);

}
