/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.log;

/**
 * LogRecord contains the information
 * associated with a logged message.
 *
 * @author    Brian Frank
 * @creation  9 Apr 00
 * @version   $Revision: 5$ $Date: 4/27/11 12:51:28 PM EDT$
 * @since     Baja 1.0
 * @deprecated as of Niagara 4.0.
*/
@Deprecated
public class LogRecord
{

  public LogRecord(String logName, int severity,
                   String message, Throwable exception)
  {
    this(logName, severity, message, exception, null);
  }

  public LogRecord(String logName, int severity,
                   String message, Throwable exception,
                   byte[] buffer)
  {
    this.logName = logName;
    this.severity = severity;
    this.message = message;
    this.exception = exception;
    this.timestamp = System.currentTimeMillis();
    this.buffer = buffer;
  }

  public final String logName;
  public final int severity;
  public final String message;
  public final Throwable exception;
  public final long timestamp;
  public final byte[] buffer;

}
