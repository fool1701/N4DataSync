/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.naming.BOrd;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.Sys;
import com.tridium.logging.BILoggingService;
import com.tridium.logging.LogSettings;

/**
 * Log is used to log system events.
 *
 * Use of this class is strongly discouraged. {@code java.util.logging} should
 * be used instead.
 *
 * @author    Brian Frank
 * creation  9 Apr 00
 * @version   $Revision: 14$ $Date: 4/28/11 1:56:05 PM EDT$
 * @since     Baja 1.0
 */
@SuppressWarnings("deprecation")
public final class Log
{

////////////////////////////////////////////////////////////////
// Severity Constants
////////////////////////////////////////////////////////////////

  /** Severtiy constant of 4 */
  public static final int NONE = 4;

  /** Severtiy constant of 3 */
  public static final int ERROR = 3;

  /** Severtiy constant of 2 */
  public static final int WARNING = 2;

  /** Severtiy constant of 1 */
  public static final int MESSAGE = 1;

  /** Severtiy constant of 0 */
  public static final int TRACE = 0;

  /**
   * Get a string constant for the specified
   * severity level.
   */
  public static String severityToString(int severity)
  {
    switch(severity)
    {
      case TRACE:   return "trace";
      case MESSAGE: return "message";
      case WARNING: return "warning";
      case ERROR:   return "error";
      case NONE:    return "none";
      default: throw new IllegalArgumentException();
    }
  }

  /**
   * Convert a severity string constant back
   * into a severity integer constant.
   */
  public static int severityFromString(String s)
  {
    if ("trace".equals(s))
    {
      return TRACE;
    }
    if ("message".equals(s))
    {
      return MESSAGE;
    }
    if ("warning".equals(s))
    {
      return WARNING;
    }
    if ("error".equals(s))
    {
      return ERROR;
    }
    if ("none".equals(s))
    {
      return NONE;
    }
    throw new IllegalArgumentException();
  }

////////////////////////////////////////////////////////////////
// Log Management
////////////////////////////////////////////////////////////////

  /**
   * Get the list of all the registered Logs.
   */
  public synchronized static Log[] getLogs()
  {
    Log[] a = new Log[logs.size()];
    String[] names = new String[a.length];
    Enumeration<Log> e = logs.elements();
    for(int i=0; e.hasMoreElements(); ++i)
    {
      a[i] = e.nextElement();
      names[i] = a[i].getLogName().toLowerCase();
    }

    SortUtil.sort(names, a);
    return a;
  }

  public synchronized static boolean isLog(String logName)
  {
    return logs.containsKey(logName);
  }

  /**
   * Get the Log with the specified name.  If the Log
   * doesn't exist yet, then it is created.  By convention
   * this name should match the package name of the code
   * using this Log instance.
   * <p>
   * The severity level of the log is no longer initialized
   * from "log.properties" file as it was in r38.  It is
   * now derived from the Level that JUL returns when we
   * invoke getLevel on the Logger with the same name.
   */
  public synchronized static Log getLog(String logName)
  {
    Log log = logs.get(logName);
    if (log != null)
    {
      return log;
    }

    Logger logger = Logger.getLogger(logName);
    Level level = logger.getLevel();  //result *can* be null
    if (level == null)
    {
      Logger parentLogger = logger.getParent();
      while (parentLogger != null)
      {
        level = parentLogger.getLevel();
        if (level != null)
        {
          break;
        }
        parentLogger = parentLogger.getParent();
      }
      if (level == null)
      {
        // we should never get here since all Loggers are children of the root logger; but in case
        // I'm wrong, get the level from the root logger
        level = Logger.getLogger("").getLevel();
      }
    }
    int severity = levelToSeverity(level);

    // NCCB-26016: In the Call to Log.getLog, we need to wrap the call to "the private log constructor"
    // in an AccessControl Privilege block which calls "delegate.setLevel" which causes the permissions error.
    log = AccessController.doPrivileged((PrivilegedAction<Log>)() -> new Log(logName, severity));

    logs.put(logName, log);
    return log;
  }

  /**
   * Delete the specified log.
   */
  public synchronized static void deleteLog(String logName)
  {
    Log log = logs.get(logName);
    if (log != null)
    {
      log.delegate.setLevel(Level.OFF);
      BILoggingService loggingService = getLoggingService();
       try
      {
        LogSettings settings = new LogSettings(loggingService.readLogSettings());
        settings.removeLogSettings(logName);
        loggingService.reload(settings.getRawProperties());
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }

    }
    logs.remove(logName);
  }

  public static int levelToSeverity(final Level level)
  {
    if(level == null)
      return NONE;
    int value = level.intValue();
    if (value >= Level.OFF.intValue())
    {
      return NONE;
    }
    if (value >= Level.SEVERE.intValue())
    {
      return ERROR;
    }
    if (value >= Level.WARNING.intValue())
    {
      return WARNING;
    }
    if (value >= Level.INFO.intValue())
    {
      return MESSAGE;
    }
    return TRACE;
  }


  /**
   * Private constructor.
   */
  private Log(String logName, int maxSeverity)
  {
    delegate = Logger.getLogger(logName);
    delegate.setLevel(severityToLevel(maxSeverity));
    this.logName = logName;
//    this.maxSeverity = maxSeverity;
  }

  private static Level severityToLevel(final int maxSeverity)
  {
    switch (maxSeverity)
    {
      case NONE: return Level.OFF;
      case ERROR: return Level.SEVERE;
      case WARNING: return Level.WARNING;
      case MESSAGE: return Level.INFO;
      case TRACE: return Level.FINE;
      default: return Level.ALL;
    }
  }

  private static BILoggingService getLoggingService()
  {
    BILoggingService loggingService;
    if (Sys.getStation() != null)
    {
      loggingService = (BILoggingService) Sys.getService(BILoggingService.TYPE);
    } else
    {
      loggingService = (BILoggingService) BOrd.make("workbench:/tools/workbench:LoggerConfigurationTool").get();
    }
    return loggingService;

  }

////////////////////////////////////////////////////////////////
// Log Config
////////////////////////////////////////////////////////////////

  /**
   * Get the name of this log.  By convention the name
   * should match the Java package name of the software
   * utilizing this Log instance.
   */
  public String getLogName()
  {
    return logName;
  }

  /**
   * Is the specified severity currently
   * enabled to be logged.
   */
  public boolean isLoggable(int severity)
  {
    return delegate.isLoggable(Log.severityToLevel(severity));
  }

  /**
   * Return true if trace is on.
   */
  public boolean isTraceOn()
  {
    return delegate.isLoggable(Level.FINE);
  }

  /**
   * Get the maximum severity which is currently
   * enabled to be logged by this Log.
   */
  public int getSeverity()
  {
    Level level = delegate.getLevel();
    Logger parent = delegate;
    while(level == null && parent != null)
    {
      parent = parent.getParent();
      level = parent.getLevel();
    }
    return Log.levelToSeverity(level);
  }

  /**
   * Ge the maximum serverity enabled as a String.
   */
  public String getSeverityString()
  {
    return severityToString(getSeverity());
  }

  /**
   * Set the maximum severity which will be
   * logged by this Log.
   */
  public void setSeverity(int maxSeverity)
  {
    delegate.setLevel(severityToLevel(maxSeverity));
    if(delegate.getLevel().equals(Level.OFF))
      return;
    BILoggingService loggingService = getLoggingService();
    try
    {
      LogSettings settings = new LogSettings(loggingService.readLogSettings());
//      loggingService.reload(settings.getRawProperties());
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
//    this.maxSeverity = maxSeverity;
  }

////////////////////////////////////////////////////////////////
// Log
////////////////////////////////////////////////////////////////

  /**
   * Log a message with an ERROR severity.
   */
  public void error(String msg)
  {
    delegate.severe(msg);
  }

  /**
   * Log a message with an ERROR severity.
   */
  public void error(String msg, Throwable ex)
  {
    delegate.log(Level.SEVERE, msg, ex);
  }

  /**
   * Log a message with an WARNING severity.
   */
  public void warning(String msg)
  {
    delegate.warning(msg);
  }

  /**
   * Log a message with an WARNING severity.
   */
  public void warning(String msg, Throwable ex)
  {
    delegate.log(Level.WARNING, msg, ex);
  }

  /**
   * Log a message with an MESSAGE severity.
   */
  public void message(String msg)
  {
    delegate.info(msg);
  }

  /**
   * Log a message with an MESSAGE severity.
   */
  public void message(String msg, Throwable ex)
  {
    delegate.log(Level.INFO, msg, ex);
  }

  /**
   * Log a message with an TRACE severity.
   */
  public void trace(String msg)
  {
    delegate.fine(msg);
  }

  /**
   * Log a message with an TRACE severity.
   */
  public void trace(String msg, Throwable ex)
  {
    delegate.log(Level.FINE, msg, ex);
  }

  public void trace(byte[] buf)
  {
    // must use a zero length string in case someone else
    // has implemented their own LogHandler and can't handle
    // a null msg
    log(TRACE, "", null, buf, 0, buf.length);
  }

  public void trace(byte[] buf, int offset, int length)
  {
    // must use a zero length string in case someone else
    // has implemented their own LogHandler and can't handle
    // a null msg
    log(TRACE, "", null, buf, offset, length);
  }

  public void trace(String msg, byte[] buf)
  {
    log(TRACE, msg, null, buf, 0, buf.length);
  }

  public void trace(String msg, byte[] buf, int offset, int length)
  {
    log(TRACE, msg, null, buf, offset, length);
  }

  /**
   * Log a message with the specified severity,
   * String message, and exception.
   */
  public void log(int severity, String msg, Throwable ex)
  {
    delegate.log(severityToLevel(severity), msg, ex);
  }

  public void log(int severity, String msg, Throwable ex, byte[] buf)
  {
    log(severity, msg, ex, buf, 0, buf.length);
  }

  public void log(int severity, String msg, Throwable ex, byte[] buf, int offset, int length)
  {
    String m = msg == null ? "" : msg;
    if (buf != null && buf.length > 0)
    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ByteArrayUtil.hexDump(pw, buf, offset, length);
      m += '\n' + sw.toString();
    }
    delegate.log(severityToLevel(severity), m, ex);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Hashtable<String, Log> logs = new Hashtable<>();
  private static Properties props;

  private final Logger delegate;
  private final String logName;
//  private int maxSeverity;

}
