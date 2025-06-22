/**
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.IOException;
import javax.baja.naming.BOrdList;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.util.BUuid;

/**
 * AlarmSpaceConnection provides access to a BIAlarmSpace. All reads, writes,
 * and deletes are done through the AlarmSpaceConnection.
 *
 * @author Blake Puhak
 * @creation 11 June 2014
 * @since Niagara 4.0
 */
public interface AlarmSpaceConnection
  extends AutoCloseable
{  
  
///////////////////////////////////////////////////////////
// Flush
///////////////////////////////////////////////////////////

  /**
   * Commit any outstanding changes.
   */
  public void flush();

///////////////////////////////////////////////////////////
// Close
///////////////////////////////////////////////////////////

  /**
   * Close the connection.
   */
  @Override
  public void close();
  
////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  /**
   * Archive alarm callback.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   *
   * @param record The alarm record to store.
   */
  public void append(BAlarmRecord record)
    throws IOException, AlarmException;

  /**
   * Update an alarm with new information.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   */
  public void update(BAlarmRecord record)
    throws IOException, AlarmException;
    
////////////////////////////////////////////////////////////////
// Query
////////////////////////////////////////////////////////////////

  /**
   * Get the number of records in the database.
   */
  int getRecordCount();

  /**
   * Get a record by uuid.
   *
   * @param uuid The uuid of the record to retrieve.
   * @return Returns the target record or null if no
   *   record is found with a matching uuid.
   */
  public BAlarmRecord getRecord(BUuid uuid)
    throws IOException;

  /**
   * Get the list of alarm sources that currently have open alarms.
   * The result is a Cursor whose elements are of type BAlarmSource.
   */
  public Cursor<BAlarmSource> getOpenAlarmSources()
    throws IOException;

  /**
   * Get the open alarms in the database.  An alarm is considered open when:
   * <p>
   * not (acked and normal) and not (acked and alert)
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  public Cursor<BAlarmRecord> getOpenAlarms()
    throws IOException;

  /**
   * Get the ackPending alarms in the database.
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  public Cursor<BAlarmRecord> getAckPendingAlarms()
    throws IOException;

  /**
   * Get all alarms for the specified source.  The result
   * will be sorted in timestamp order with the oldest alarm first.
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  Cursor<BAlarmRecord> getAlarmsForSource(BOrdList alarmSource) throws IOException;

  /**
   * Get the open alarms for the specified source.  The result
   * will be sorted in timestamp order with the oldest alarm first.
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   *
   * @since Niagara 4.4
   */
  default Cursor<BAlarmRecord> getOpenAlarmsForSource(BOrdList alarmSource) throws IOException
  {
    Cursor<BAlarmRecord> c = getAlarmsForSource(alarmSource);
    return new Cursor<BAlarmRecord>()
    {
      @Override
      public Context getContext()
      {
        return c.getContext();
      }

      @Override
      public boolean next()
      {
        while (c.next())
        {
          if (c.get().isOpen())
          {
            return true;
          }
        }
        return false;
      }

      @Override
      public BAlarmRecord get()
      {
        return c.get();
      }

      @Override
      public void close()
      {
        c.close();
      }
    };
  }

  /**
   * Get a cursor for iterating through all record in the database.
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  public Cursor<BAlarmRecord> scan()
    throws IOException, AlarmException;

  /**
   * Get a cursor for iterating through all records between
   * the specified start and end times inclusive.
   * <p>
   * The alarm reference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   *
   * @param start The earliest timestamp that will be included
   *   in the result.
   * @param end The latest timestamp that will be included in
   *  the result.
   */
  public Cursor<BAlarmRecord> timeQuery(BAbsTime start, BAbsTime end)
    throws IOException, AlarmException;
  
}
