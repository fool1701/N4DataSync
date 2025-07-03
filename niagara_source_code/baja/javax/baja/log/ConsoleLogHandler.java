/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.log;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.baja.nre.util.ByteArrayUtil;

import com.tridium.util.ThrowableUtil;

/**
 * DefaultLogHandler provides a simple implementation
 * of the LogHandler to dump log messages to standard
 * output.
 *
 * @author    Brian Frank
 * @creation  9 Apr 00
 * @version   $Revision: 11$ $Date: 4/27/11 11:58:54 AM EDT$
 * @since     Baja 1.0
 * @deprecated As of Niagara 4.0.
 */
@Deprecated
public class ConsoleLogHandler
  implements LogHandler
{

  /**
   * Print a log record to standard output.
   */
  @Override
  public void publish(LogRecord record)
  {
    synchronized(System.out)
    {
      write(System.out, record);
    }
  }

  /**
   * Write record to the specified stream.
   */
  public void write(PrintStream out, LogRecord record)
  {
    // severity
    switch(record.severity)
    {
      case Log.ERROR:   out.print("ERROR ["); break;
      case Log.WARNING: out.print("WARNING ["); break;
      case Log.MESSAGE: out.print("MESSAGE ["); break;
      case Log.TRACE:   out.print("TRACE ["); break;
    }

    // timestamp
    StringBuilder prefix = new StringBuilder(format.format(new Date(record.timestamp)));
    prefix.append("][").append(record.logName);
    prefix.append("] ");

    // message
    if (record.message != null && record.message.length() > 0)
    {
      out.print(prefix.toString());
      out.println(record.message);
    }

    // byte array
    if (record.buffer != null && record.buffer.length > 0)
    {
      StringWriter swriter = new StringWriter();
      PrintWriter pwriter = new PrintWriter(swriter);
      ByteArrayUtil.hexDump(prefix.toString(), pwriter, record.buffer, 0, record.buffer.length);
    }
    // exception
    ThrowableUtil.dump(out, record.exception);
  }

  private final DateFormat format = new SimpleDateFormat("HH:mm:ss dd-MMM-yy z");
}
