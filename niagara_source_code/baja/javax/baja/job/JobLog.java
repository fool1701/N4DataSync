/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.baja.sys.BAbsTime;
import javax.baja.util.BFormat;

import com.tridium.util.ThrowableUtil;

/**
 * JobLog is a list of JobLogItems which provide a 
 * detailed account of a Job's execution.
 *
 * @author    Brian Frank
 * @creation  27 Jul 04
 * @version   $Revision: 5$ $Date: 11/3/09 4:49:07 AM EST$
 * @since     Baja 1.0
 */
public final class JobLog
{
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Default constructor. The sequence numbers of the items added
   * will begin from zero.
   */
  public JobLog()
  {
    this(-1);
  }

  /**
   * Create a new JobLog with an initial starting point for the
   * sequence numbers.
   *
   * @since Niagara 4.3
   * @param sequenceNumber  the initial seed for the sequence numbers. The
   *                        first item to be added to the log will be at
   *                        sequence number + 1.
   */
  public JobLog(long sequenceNumber)
  {
    this.sequenceNumber = sequenceNumber;
    this.initialSequenceNumber = sequenceNumber + 1;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
    
  /**
   * Get the number of items.
   */                        
  public int size()
  {
    synchronized (items)
    {
      return items.size();
    }
  }           
  
  /**
   * Get the log item at the specified index between 0 to size()-1. 
   */
  public JobLogItem getItem(int index)
  {                          
    synchronized (items)
    {
      return items.get(index);
    }
  }

  /**
   * Get a copy of the items as an array.
   */
  public JobLogItem[] getItems()
  {                                      
    synchronized (items)
    {
      return items.toArray(new JobLogItem[items.size()]);
    }
  }
  
  /**
   * Set a limit on the number of items that can be added to the Log
   * 
   * @param limit  the limit of the number of items that can be added to the Job.
   *               If the limit is '-1', the log is unlimited.
   *               
   * @since Niagara 3.5
   */
  public void setLimit(int limit)
  {
    synchronized (items)
    {
      this.limit = limit;

      if (limit > -1)
      {
        while (items.size() > limit)
          items.remove(0);
      }
    }
  }
  
  /**
   * Return the limit on the number of items that can be added to the Job
   * 
   * @see #setLimit
   * 
   * @since Niagara 3.5
   */
  public int getLimit()
  {
    return limit;
  }

////////////////////////////////////////////////////////////////
// Modification
////////////////////////////////////////////////////////////////

  /**
   * Add a RUNNING item, typically this method is followed by 
   * some work, and then a call to endSuccess() or endFailed().
   */
  public void start(String nonLocalizedMessage)
  {
    add(new JobLogItem(JobLogItem.RUNNING, nonLocalizedMessage));
  }                 
  
  /**
   * Add a RUNNING item, typically this method is followed by 
   * some work, and then a call to endSuccess() or endFailed().
   * 
   * @since Niagara 3.5
   */
  public void start(String lexModule, String lexKey, String[] lexArgPatterns)
  {
    add(new JobLogItem(JobLogItem.RUNNING, lexModule, lexKey, lexArgPatterns));
  }                 
  
  /**
   * Modify the last record added via start() from RUNNING 
   * to SUCCESS.  If append is non-null then append it to 
   * the original message.
   */
  public void endSuccess(String nonLocalizedAppendText)
  {                 
    end(JobLogItem.SUCCESS, nonLocalizedAppendText, null);
  }

  /**
   * Modify the last record added via start() from RUNNING 
   * to SUCCESS.  If appendLexModule is non-null then use it, appendLexKey 
   * and appendLexArgPatterns to append a format string to the original message.
   * 
   * @since Niagara 3.5
   */
  public void endSuccess(String appendLexModule, String appendLexKey, String[] appendLexArgPatterns)
  {                 
    end(JobLogItem.SUCCESS, appendLexModule, appendLexKey, appendLexArgPatterns, null);
  }
  
  /**
   * Modify the last record added via start() from RUNNING 
   * to SUCCESS.  
   */
  public void endSuccess()
  {                 
    end(JobLogItem.SUCCESS, null, null);
  }

  /**
   * Modify the last record added via start() from RUNNING 
   * to FAILED.  If append is non-null then append it to 
   * the original message.  It exception is non-null set it 
   * as the details.
   */
  public void endFailed(String nonLocalizedAppendText, Throwable exception)
  {                       
    end(JobLogItem.FAILED, nonLocalizedAppendText, exception);
  }

  /**
   * Modify the last record added via start() from RUNNING 
   * to FAILED.  If appendLexModule is non-null then use it, appendLexKey 
   * and appendLexArgPatterns to append a format string to the original message.
   * If exception is non-null set it as the details.
   * 
   * @since Niagara 3.5
   */
  public void endFailed(String appendLexModule, String appendLexKey, String[] appendLexArgPatterns, Throwable exception)
  {                       
    end(JobLogItem.FAILED, appendLexModule, appendLexKey, appendLexArgPatterns, exception);
  }

  /**
   * Modify the last record added via start() from RUNNING 
   * to FAILED.  It exception is non-null set it as the details.
   */
  public void endFailed(Throwable exception)
  {                       
    end(JobLogItem.FAILED, null, exception);
  }

  /**
   * Modify the last record added via start() from RUNNING 
   * to FAILED.  If appendLexModule is non-null then use it, appendLexKey 
   * and appendLexArgPatterns to append a format string to the original message.
   */
  public void endFailed(String nonLocalizedAppendText)
  {                       
    end(JobLogItem.FAILED, nonLocalizedAppendText, null);
  }
  
  /**
   * Modify the last record added via start() from RUNNING 
   * to FAILED.  If appendLexModule is non-null then use it, appendLexKey 
   * and appendLexArgPatterns to append a format string to the original 
   * message.
   * 
   * @since Niagara 3.5
   */
  public void endFailed(String appendLexModule, String appendLexKey, String[] appendLexArgPatterns)
  {                       
    end(JobLogItem.FAILED, appendLexModule, appendLexKey, appendLexArgPatterns, null);
  }
  
  /**
   * Modify the last record added via start() by changing 
   * its id.  If append is non-null then append it to the 
   * original message.  If exception is non-null then set
   * it as the details.
   */
  public void end(int id, String nonLocalizedAppendText, Throwable exception)
  {
    JobLogItem item = getLastItem();
    if (item == null)
    {
      return;
    }
    item.id = id;
    item.timestamp = BAbsTime.make();
    if (nonLocalizedAppendText != null && !nonLocalizedAppendText.isEmpty())
    {
      item.message = item.message + "; " + nonLocalizedAppendText;
    }
    if (exception != null)
    {
      item.details = ThrowableUtil.dumpToString(exception);
    }
  }      

  /**
   * Modify the last record added via start() by changing 
   * its id.  If appendLexModule is non-null then use it, appendLexKey 
   * and appendLexArgPatterns to append a format string to the original 
   * message.  If exception is non-null then set it as the details.
   * 
   * @since Niagara 3.5
   */
  public void end(int id, String appendLexModule, String appendLexKey, String[] appendLexArgPatterns, Throwable exception)
  {    
    JobLogItem item = getLastItem();
    if (item == null)
    {
      return;
    }
    item.id = id;
    item.timestamp = BAbsTime.make();
    if (appendLexModule != null && !appendLexModule.isEmpty())
    {
      String append = BFormat.getLexiconPattern(appendLexModule, appendLexKey, appendLexArgPatterns);
      item.message = item.message + "; " + append;
    }
    if (exception != null)
      item.details = ThrowableUtil.dumpToString(exception);
  }

  /**
   * Return the last item or null if the size is zero.
   * @since Niagara 4.13
   */
  private JobLogItem getLastItem()
  {
    synchronized (items)
    {
      int size = items.size();
      if (size == 0)
      {
        return null;
      }
      return items.get(size - 1);
    }
  }

  /**
   * Add a MESSAGE item.
   */
  public void message(String nonLocalizedMessage)
  {
    add(new JobLogItem(JobLogItem.MESSAGE, nonLocalizedMessage));
  }

  /**
   * Add a MESSAGE item.
   * 
   * @since Niagara 3.5
   */
  public void message(String lexModule, String lexKey, String[] lexArgPatterns)
  {
    add(new JobLogItem(JobLogItem.MESSAGE, lexModule, lexKey, lexArgPatterns));
  }

  /**
   * Add a MESSAGE item.
   * 
   * @since Niagara 3.5
   */
  public void message(String lexModule, String lexKey, String lexArgPattern)
  {
    add(new JobLogItem(JobLogItem.MESSAGE, lexModule, lexKey, new String[] { lexArgPattern }));
  }

  /**
   * Add a MESSAGE item.
   * 
   * @since Niagara 3.5
   */
  public void message(String lexModule, String lexKey)
  {
    add(new JobLogItem(JobLogItem.MESSAGE, lexModule, lexKey, null));
  }

  /**
   * Add a SUCCESS item.
   */
  public void success(String nonLocalizedMessage)
  {
    add(new JobLogItem(JobLogItem.SUCCESS, nonLocalizedMessage));
  }
  
  /**
   * Add a SUCCESS item.
   * 
   * @since Niagara 3.5
   */
  public void success(String lexModule, String lexKey, String lexArgPattern)
  {
    add(new JobLogItem(JobLogItem.SUCCESS, lexModule, lexKey, new String[] { lexArgPattern }));
  }

  /**
   * Add a SUCCESS item.
   * 
   * @since Niagara 3.5
   */
  public void success(String lexModule, String lexKey)
  {
    add(new JobLogItem(JobLogItem.SUCCESS, lexModule, lexKey, null));
  }

  /**
   * Add a SUCCESS item.
   * 
   * @since Niagara 3.5
   */
  public void success(String lexModule, String lexKey, String[] lexArgPatterns)
  {
    add(new JobLogItem(JobLogItem.SUCCESS, lexModule, lexKey, lexArgPatterns));
  }

  /**
   * Add a FAILED item.
   */
  public void failed(String nonLocalizedMessage)
  {
    add(new JobLogItem(JobLogItem.FAILED, nonLocalizedMessage));
  }
  
  /**
   * Add a FAILED item.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey, String[] lexArgPatterns)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, lexArgPatterns));
  }
  
  /**
   * Add a FAILED item.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey, String lexArgPattern)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, new String[] { lexArgPattern }));
  }
  
  /**
   * Add a FAILED item.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, null));
  }
  
  /**
   * Add a FAILED item with exception details.
   */
  public void failed(String msg, Throwable exception)
  {
    add(new JobLogItem(JobLogItem.FAILED, msg, exception));
  }

  /**
   * Add a FAILED item with exception details.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey, String[] lexArgPatterns, Throwable exception)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, lexArgPatterns, exception));
  }

  /**
   * Add a FAILED item with exception details.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey, String lexArgPattern, Throwable exception)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, new String[] { lexArgPattern }, exception));
  }

  /**
   * Add a FAILED item with exception details.
   * 
   * @since Niagara 3.5
   */
  public void failed(String lexModule, String lexKey, Throwable exception)
  {
    add(new JobLogItem(JobLogItem.FAILED, lexModule, lexKey, null, exception));
  }

  /**
   * Add an item to the end of the log.
   * <p>
   * If a limit has been set on the JobLog and this has been exceeded
   * then remove items from the beginning of the list
   */
  public void add(JobLogItem item)
  {
    synchronized (items)
    {
      items.add(item);
      sequenceNumber++;

      if (limit > -1)
      {
        while (items.size() > limit)
          items.remove(0);
      }
    }
  }

////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////
  
  /**
   * Encode into a list of JobLogItems separated by "\n".
   */
  public String encode()
  {                                                   
    StringBuilder s = new StringBuilder();
    synchronized (items)
    {
      for (JobLogItem item : items)
      {
        s.append(item.encode()).append('\n');
      }
    }
    return s.toString();
  }                      
  
  /**
   * Decode a list of JobLogItems created via encode().
   */
  public static JobLog decode(String s)
    throws Exception
  {            
    JobLog log = new JobLog();
    StringTokenizer st = new StringTokenizer(s, "\n");
    while(st.hasMoreTokens())
      log.add(JobLogItem.decode(st.nextToken()));
    return log;
  }

  /**
   * Set the reference to the job this log is associated with.
   * @since Niagara 4.3
   */
  void setJob(BJob job)
  {
    this.job = job;
  }

////////////////////////////////////////////////////////////////
// Sequence
////////////////////////////////////////////////////////////////

  /**
   * Return the items in the log starting from the given sequence number. If
   * the oldest item currently in the log has a sequence number greater than
   * the requested number, the returned sequence will begin with that oldest
   * item. The returned BJobLogSequence contains the encoded items
   * and the low and high sequence numbers of the returned item range.
   *
   * @since Niagara 4.3
   *
   * @param num the sequence number to retrieve items after (inclusive)
   * @return a BJobLogSequence with the encoded log items and the low and high sequence numbers.
   * Sequence numbers of -1 indicate an empty log or trying to read beyond the current highest
   * sequence number.
   */
  BJobLogSequence getSequenceFrom(long num)
  {
    synchronized (items)
    {
      if (size() == 0 || num > sequenceNumber)
        return new BJobLogSequence(new JobLogItem[0], initialSequenceNumber, -1, -1);

      JobLogItem[] arr = getItemsFrom(num);

      return new BJobLogSequence(arr, initialSequenceNumber,
        sequenceNumber - (arr.length - 1), sequenceNumber);
    }
  }

  /**
   * Return the sequence number of the first item in the log. This
   * may be greater than zero if the job has reset the log, or a fixed
   * capacity means old items have been removed. If the log is empty
   * this method will return -1.
   *
   * @since Niagara 4.3
   *
   * @return the sequence number of the first item, or -1 if there
   * are no items in the log.
   */
  private long firstSequenceNumber()
  {
    synchronized(items)
    {
      int size = size();
      if (size == 0) return -1;
      return sequenceNumber - (size - 1);
    }
  }

  /**
   * Return the items in the log with sequence numbers greater
   * than or equal to the given argument.
   *
   * @since Niagara 4.3
   *
   * @param num the sequence number to retrieve items after (inclusive)
   * @return an array of JobLogItems with sequence numbers equal
   * to or greater than or equal to 'num'.
   */
  private JobLogItem[] getItemsFrom(long num)
  {
    synchronized (items)
    {
      long firstSeq = firstSequenceNumber();

      if (firstSeq > -1)
      {
        num = Math.max(num, firstSeq);

        int firstIndex = (int) (num - firstSeq);
        int lastIndex = items.size();

        if (firstIndex >= 0 && firstIndex <= lastIndex)
        {
          JobLogItem[] arr = new JobLogItem[lastIndex - firstIndex];
          items.subList(firstIndex, lastIndex).toArray(arr);

          return arr;
        }
      }
    }

    return new JobLogItem[0];
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  final List<JobLogItem> items = new ArrayList<>();
  BJob job;
  int limit = -1;
  long sequenceNumber;
  long initialSequenceNumber;
}
