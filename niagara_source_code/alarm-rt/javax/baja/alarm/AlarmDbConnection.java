/**
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import com.tridium.alarm.AlarmsToSourcesCursor;
import com.tridium.bql.BBqlExtent;
import com.tridium.bql.Range;
import com.tridium.bql.RangeSet;
import com.tridium.bql.SelectQuery;

import javax.baja.bql.BqlQuery;
import javax.baja.collection.AbstractCursor;
import javax.baja.naming.BOrdList;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.util.BUuid;
import java.io.IOException;
import java.util.ArrayList;

/**
 * AlarmDbConnection provides access to an Alarm Database. All reads, writes,
 * and deletes are done through the AlarmDbConnection.
 *
 * Access methods from BAlarmDatabase in NiagaraAX have been moved 
 * to AlarmDbConnection for Niagara4. 
 *
 * @author Blake Puhak
 * @creation 11 June 2014
 * @since Niagara 4.0
 */
public abstract class AlarmDbConnection
  implements AlarmSpaceConnection
{  
  
  /**
   * return a reference to the Alarm Database that we're connected to.
   */
  public abstract BAlarmDatabase getAlarmDatabase();
  
  /**
   * Handle the specified query.  The returned cursor doesn't necessarily
   * contain the exact result of the query.  The query engine will
   * filter any records that don't meet the query parameters.  This
   * method should not be called directly, but instead should
   * be called by the BQL query engine.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  public synchronized Cursor<BAlarmRecord> doBqlQuery(BqlQuery query)
    throws IOException, AlarmException
  {
    SelectQuery select = (SelectQuery)query;
    RangeSet range = null;

    // Use an existing data set
    if(((BBqlExtent)select.getSelect().getExtent()).getUnparsed().equals("openAlarms"))
      return getOpenAlarms();
    if(((BBqlExtent)select.getSelect().getExtent()).getUnparsed().equals("ackPendingAlarms"))
      return getAckPendingAlarms();

    // If there is no predicate, do a scan.
    if (!select.hasPredicate())
      return scan();

    try
    {
      // try uuid range first.
      range = select.getRange("uuid", BUuid.TYPE, true);
      if (!range.isAll())
      {
        ArrayList<BAlarmRecord> list = new ArrayList<>(1);
        int count = range.getCount();
        for (int i = 0; i < count; i++)
        {
          Range r = range.getRange(i);
          BAlarmRecord rec = getRecord((BUuid) r.getSingleton());
          if (rec != null)
            list.add((BAlarmRecord)rec.newCopy(true));
        }
        BAlarmRecord[] recs =
            list.toArray(new BAlarmRecord[list.size()]);
        return new ArrayCursor(recs);
      }

      // next, try a source predicate
      range = select.getRange("source", BOrdList.TYPE, true);
      if (!range.isAll() && (range.getCount() == 1))
        return getAlarmsForSource((BOrdList)range.getRange(0).getSingleton());

      // finally, try a timestamp range.
      range = select.getRange("timestamp", BAbsTime.TYPE, false);
      if (range.isAll())
        return scan();
      else
      {
        // even if the range is disjoint, we might be able to get a more
        // efficient query by getting the absolute minimum time, and the
        // absolute maximum time.
        range.sort();
        Range min = range.getRange(0);
        Range max = range.getRange(range.getCount()-1);
        return timeQuery((BAbsTime)min.min, (BAbsTime)max.max);
      }
    }
    catch (IllegalStateException x)
    {
      x.printStackTrace();
      return scan();
    }
  }
  
///////////////////////////////////////////////////////////
// Flush
///////////////////////////////////////////////////////////

  /**
   * Commit any outstanding changes.
   */
  @Override
  public abstract void flush();

///////////////////////////////////////////////////////////
// Close
///////////////////////////////////////////////////////////

  /**
   * Close the connection.
   */
  @Override
  public abstract void close();
  
////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  /**
   * Archive alarm callback.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   *
   * @param ar The alarm record to store.
   */
  @Override
  public abstract void append(BAlarmRecord ar)
    throws IOException, AlarmException;

  /**
   * Update an alarm with new information.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   */
  @Override
  public abstract void update(BAlarmRecord record)
    throws IOException, AlarmException;
    
////////////////////////////////////////////////////////////////
// Query
////////////////////////////////////////////////////////////////

  /**
   * Get the number of records in the database.
   */
  @Override
  public abstract int getRecordCount();

  /**
   * Get a record by uuid.
   *
   * @param uuid The uuid of the record to retrieve.
   * @return Returns the target record or null if no
   *   record is found with a matching uuid.
   */
  @Override
  public abstract BAlarmRecord getRecord(BUuid uuid)
    throws IOException;

  /**
   * Get the list of alarm sources that currently have open alarms.
   * The result is a Cursor whose elements are of type BAlarmSource.
   */
  @Override
  public Cursor<BAlarmSource> getOpenAlarmSources()
    throws IOException
  {
    Cursor<BAlarmRecord> alarms = getOpenAlarms();
    return new AlarmsToSourcesCursor(alarms);
  }

  /**
   * Get the open alarms in the database.  An alarm is considered open when:
   * <p>
   * not (acked and normal) and not (acked and alert)
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public abstract Cursor<BAlarmRecord> getOpenAlarms()
    throws IOException;

  /**
   * Get the ackPending alarms in the database.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public abstract Cursor<BAlarmRecord> getAckPendingAlarms()
    throws IOException;

  /**
   * Get all alarms for the specified source.  The result will be sorted in timestamp order
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public abstract Cursor<BAlarmRecord> getAlarmsForSource(BOrdList alarmSource) throws IOException;

  /**
   * Get a cursor for iterating through all record in the database.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public abstract Cursor<BAlarmRecord> scan()
    throws IOException, AlarmException;

  /**
   * Get a cursor for iterating through all records between
   * the specified start and end times inclusive.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   *
   * @param start The earliest timestamp that will be included
   *   in the result.
   * @param end The latest timestamp that will be included in
   *  the result.
   */
  @Override
  public abstract Cursor<BAlarmRecord> timeQuery(BAbsTime start, BAbsTime end)
    throws IOException, AlarmException;
  
////////////////////////////////////////////////////////////////
// Maintenance
////////////////////////////////////////////////////////////////

  /**
   * Clear all records from the database.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   */
  public abstract void clearAllRecords(Context cx)
    throws IOException;

  /**
   * Clear all records with a timestamp before the specified time.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   *
   * @param before The earliest time to keep in the result.  Records
   *   before this time will be removed.
   */
  public abstract void clearOldRecords(BAbsTime before, Context cx)
    throws IOException;

  /**
   * Clear the record with the given uuid.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   *
   * @param uuid the Uuid of the Alarm Record to remove from the database.
   */
  public abstract void clearRecord(BUuid uuid, Context cx)
    throws IOException;

  ////////////////////////////////////////////////////////////////
// Cursors
////////////////////////////////////////////////////////////////

  private static class ArrayCursor extends AbstractCursor<BAlarmRecord>
  {
    public ArrayCursor(BAlarmRecord[] recs)
    {
      this.recs = recs;
      index = -1;
    }

    @Override
    protected BAlarmRecord doGet()
    {
      if (index == -1)
      {
        throw new IllegalStateException("get() before next()");
      }
      return recs[index];
    }

    @Override
    protected boolean advanceCursor()
    {
      if (index == recs.length)
      {
        return false;
      }
      index++;
      return index != recs.length;
    }

    final BAlarmRecord[] recs;
    int index;
  }
}
