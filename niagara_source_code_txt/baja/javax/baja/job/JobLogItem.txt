/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import java.text.*;
import java.util.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.util.*;

/**
 * JobLogItem is a record within a JobLog.
 *
 * @author    Brian Frank
 * @creation  27 Jul 04
 * @version   $Revision: 5$ $Date: 8/19/09 11:00:38 AM EDT$
 * @since     Baja 1.0
 */
public final class JobLogItem
{

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MESSAGE  = 0;
  public static final int RUNNING  = 1;
  public static final int CANCELED = 2;  
  public static final int SUCCESS  = 3;
  public static final int FAILED   = 4;  
  
  private static final String[] IDS = 
    { "Message", "Running", "Canceled", "Success", "Failed" };

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct an item with all four fields.
   */
  public JobLogItem(int id, BAbsTime timestamp, String messageFormatPattern, String details)
  {                        
    if (id < 0 || id >= IDS.length || messageFormatPattern == null)
      throw new IllegalArgumentException();
    
    if (details != null && details.length() == 0) 
      details = null;
                        
    this.id        = id;
    this.timestamp = timestamp;
    this.message   = messageFormatPattern; 
    this.details   = details;
  }
  
  /**
   * Construct with timestamp set to current time. The 
   * specified exception's dump is used for the details.
   */
  public JobLogItem(int id, String messageFormatPattern, Throwable exception)
  {                                               
    this(id, BAbsTime.make(), messageFormatPattern, ThrowableUtil.dumpToString(exception));
  }

  /**
   * Construct with timestamp set to current time. The 
   * specified exception's dump is used for the details.
   * 
   * @since Niagara 3.5
   */
  public JobLogItem(int id, String lexModule, String lexKey, String[] lexArgPatterns, Throwable exception)
  {                                               
    this(id, BAbsTime.make(), BFormat.getLexiconPattern(lexModule, lexKey, lexArgPatterns), ThrowableUtil.dumpToString(exception));
  }
  
  /**
   * Construct with timestamp set to current time.
   */
  public JobLogItem(int id, String messageFormatPattern, String details)
  {                                               
    this(id, BAbsTime.make(), messageFormatPattern, details);
  }

  /**
   * Construct with timestamp set to current time.
   * 
   * @since Niagara 3.5
   */
  public JobLogItem(int id, String lexModule, String lexKey, String[] lexArgPatterns, String details)
  {                                               
    this(id, BAbsTime.make(), BFormat.getLexiconPattern(lexModule, lexKey, lexArgPatterns), details);
  }
  
  /**
   * Construct with timestamp set to current time and no details.
   */
  public JobLogItem(int id, String messageFormatPattern)
  {                                               
    this(id, BAbsTime.make(), messageFormatPattern, null);
  }
  
  /**
   * Construct with timestamp set to current time and no details.
   * 
   * @since Niagara 3.5
   */
  public JobLogItem(int id, String lexModule, String lexKey, String[] lexArgPatterns)
  {
    this(id, BAbsTime.make(), BFormat.getLexiconPattern(lexModule, lexKey, lexArgPatterns), null);
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the id constant.
   */
  public int getId()
  {
    return id;
  }           
  
  /**
   * Get the id as a string.
   */
  public String getIdString()
  {
    return getIdString(null);
  } 

  /**
   * Get the BFormat pattern for the item's ID string
   * 
   * @since Niagara 3.5
   */
  public String getIdStringPattern()
  {
    return IDS[id];
  }
  
  /**
   * Get the id as a localized String
   * 
   * @since Niagara 3.5
   */
  public String getIdString(Context cx)
  {
    return Lexicon.make("baja", cx).get(IDS[id].toLowerCase());
  }
  
  /**
   * Get the timestamp of the item.
   */
  public BAbsTime getTimestamp()
  {              
    return timestamp;
  }                  
  
  /**
   * Get the message string.
   */
  public String getMessage()
  {                                                      
    return getMessage(null);
  } 
  
  /**
   * Get the BFormat pattern for the item's message
   * 
   * @since Niagara 3.5
   */
  public String getMessagePattern()
  {                                                      
    return message;
  } 
  
  /**
   * Get the message as a localized string
   * 
   * @since Niagara 3.5
   */
  public String getMessage(Context cx)
  {
    return BFormat.format(message, this, cx);
  }
  
  /**
   * Get the details or return null if no details are available.
   * Typically details contain a stack dump of an exception.
   */
  public String getDetails()
  {                                              
    return getDetails(null);
  }
  
  /**
   * Get the BFormat pattern for the item's details, or null if no details are
   * available.
   * 
   * @since Niagara 3.5
   */
  public String getDetailsPattern()
  {                                              
    return details;
  }
  
  /**
   * Get the details as a localized String or return null if no details are available.
   * Typically details contain a stack dump of an exception.
   * 
   * @since Niagara 3.5
   */
  public String getDetails(Context cx)
  {
    if (details == null)
      return null;
    else
      return BFormat.format(details, this, cx);
  }
  
  /**
   * Return a string for the item.
   */
  public String toString()
  {
    return toString(null);
  }
  
  /**
   * Return a localized string for the item.
   * 
   * @since Niagara 3.5
   */
  public String toString(Context cx)
  {
    String s = getIdString(cx) + " [" + format.format(new Date(timestamp.getMillis())) + "] " + getMessage(cx);
    if (details != null) s += "\n" + getDetails(cx);
    return s;
  }

////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////
  
  /**
   * Encode into a string line.
   */
  public String encode()
  {
    StringBuilder s = new StringBuilder();
    s.append(IDS[id]).append('|')
     .append(timestamp.encodeToString()).append('|')
     .append(escape(message)).append('|')
     .append(escape(details == null ? "" : details));
    return s.toString();
  }
  
  /**
   * Decode from a string line.
   */
  public static JobLogItem decode(String line)
    throws Exception
  {               
    // tokenize
    String[] tok = unescape(line);   
    String idStr   = tok[0];
    String tsStr   = tok[1];
    String message = tok[2]; 
    String details = tok[3]; 
    
    // parse id
    int id = 0;
    for(int i=0; i<IDS.length; ++i)
      if (idStr.equals(IDS[i])) { id = i; break; }
    
    // parse timestamp
    BAbsTime ts = (BAbsTime)BAbsTime.DEFAULT.decodeFromString(tsStr);
    
    return new JobLogItem(id, ts, message, details);
  }            
  
  static String escape(String s)
  {
    StringBuilder buf = new StringBuilder();
    for(int i=0; i<s.length(); ++i)
    {
      char c = s.charAt(i);
      if (c == '\\') buf.append("\\\\");
      else if (c == '|') buf.append("\\|");
      else if (c == '\n') buf.append("\\n");
      else buf.append(c);
    }
    return buf.toString();
  }              
  
  static String[] unescape(String s)
  {
    String[] r = new String[10];
    StringBuilder buf = new StringBuilder();
    int n = 0;
    for(int i=0; i<s.length(); ++i)
    {
      char c = s.charAt(i);
      if (c == '\\') 
      { 
        ++i; 
        c = s.charAt(i); 
        if (c == 'n') buf.append('\n');
        else buf.append(c);
      }
      else if (c == '|') { r[n++] = buf.toString(); buf = new StringBuilder(); }
      else buf.append(c);
    }       
    r[n++] = buf.toString();   
    return r;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private DateFormat format = new SimpleDateFormat("HH:mm:ss dd-MMM-yy");

  int id;
  BAbsTime timestamp;
  String message;    
  String details;
}
