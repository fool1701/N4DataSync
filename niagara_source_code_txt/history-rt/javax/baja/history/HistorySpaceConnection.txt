/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.collection.BITable;
import javax.baja.naming.BOrd;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Cursor;

/**
 * HistorySpaceConnection provides access to a HistorySpace. All reads, writes,
 * and deletes are done through the HistorySpaceConnection.
 *
 * Access methods from BIHistory and BHistorySpace in NiagaraAX have been moved
 * to HistorySpaceConnection for Niagara4.
 *
 * @author Blake Puhak
 * @creation 12/3/13
 * @since Niagara 4.0
 */
public interface HistorySpaceConnection
  extends AutoCloseable
{

  /**
   * Return a {@link BIHistory} with the specified historyId from the
   * {@link BHistorySpace} that we're connected to. This may open resources which
   * should be closed when no longer needed by calling
   * {@link BIHistory#close() BIHistory.close()}.
   *
   * @param historyId the unique identifier of the target BIHistory.
   * @return the BIHistory with the specified historyId or {@code null} if the
   *  history is not found.
   */
  public BIHistory getHistory(BHistoryId historyId);

////////////////////////////////////////////////////////////////
// BIHistory Implementation
////////////////////////////////////////////////////////////////

  /**
   * Get summary of the specified history.
   *
   * @param history the history to get the record count of.
   * @return the HistorySummary of the specified history.
   */
  public BHistorySummary getSummary(BIHistory history);

  /**
   * Get the number of records in the specified history.
   *
   * @param history the history to get the record count of.
   */
  public int getRecordCount(BIHistory history);

  /**
   * Get the timestamp of the first record in the specified history.
   *
   * @param history the history to get the timestamp from.
   * @return the timestamp of the first record in the specified history.
   */
  public BAbsTime getFirstTimestamp(BIHistory history);

  /**
   * Get the timestamp of the last record in the specified history.
   *
   * @param history the history to get the timestamp from.
   * @return the timestamp of the last record in the specified history.
   */
  public BAbsTime getLastTimestamp(BIHistory history);

  /**
   * Get the last record in the specified history.
   *
   * @param history the history to get the record from.
   * @return the last record in the specified history.
   */
  public BHistoryRecord getLastRecord(BIHistory history);

  /**
   * Append the specified records to the end of the history.
   *
   * @param history The history to append to.
   * @param newRecords The records to append.
   */
  public void append(BIHistory history, BIHistoryRecordSet newRecords);

  /**
   * Overwrite the specified record in the specified history.
   *
   * @param history The history to update the record in.
   * @param record The record to update.
   */
  public void update(BIHistory history, BHistoryRecord record);

  /**
   * Get every record in the history.
   * <p>
   * This is equivalent to {@code timeQuery(null, null, descending)}.
   *
   * @param history the history to query.
   * @return Cursor of BHistoryRecords
   */
  public Cursor<BHistoryRecord> scan(BIHistory history);

  /**
   * Get every record in the history.
   * <p>
   * This is equivalent to {@code timeQuery(null, null, descending)}.
   *
   * @param history the history to query.
   * @param descending if {@code true} the records will be returned in
   *  descending order by timestamp.
   * @return Cursor of BHistoryRecords
   */
  public Cursor<BHistoryRecord> scan(BIHistory history, boolean descending);

  /**
   * Query the history based on a time range.
   * <p>
   * The result is inclusive of the end points.  If either
   * endpoint is {@code null}, then the interval will
   * be considered open on that end, i.e. {@code query(null, null)}
   * is equivalent to a {@code timeQuery(startTime, endTime, false)}.
   *
   * @param history the history to query.
   * @param startTime The earliest time to include in the result.
   *                  If {@code null}, then no lower bound is enforced.
   * @param endTime   The latest time to include in the result.
   *                  If {@code null}, then no upper bound is enforced.
   * @return Returns a collection of the records that fall within
   *         the specified time range.
   */
  public BITable<BHistoryRecord> timeQuery(BIHistory history, BAbsTime startTime, BAbsTime endTime);

  /**
   * Query the history based on a time range.
   * <p>
   * The result is inclusive of the end points.  If either
   * endpoint is {@code null}, then the interval will
   * be considered open on that end, i.e. {@code query(null, null)}
   * is equivalent to a {@code timeQuery(startTime, endTime, false)}.
   *
   * @param history the history to query.
   * @param startTime The earliest time to include in the result.
   *                  If {@code null}, then no lower bound is enforced.
   * @param endTime   The latest time to include in the result.
   *                  If {@code null}, then no upper bound is enforced.
   * @param descending if {@code true} the records will be returned in
   *  descending order by timestamp.
   * @return Returns a collection of the records that fall within
   *         the specified time range.
   */
  public BITable<BHistoryRecord> timeQuery(BIHistory history, BAbsTime startTime, BAbsTime endTime, boolean descending);

  /**
   * Commit any unsaved changes.
   *
   * @param history the history to flush.
   */
  public void flush(BIHistory history);

////////////////////////////////////////////////////////////////
// BHistorySpace Implementation
////////////////////////////////////////////////////////////////

  /**
   * Determine whether a history exists with the specified id.
   *
   * @param id The unique identifier for the target history.
   * @return Returns true if a history exists for the specified
   *   id, false otherwise.
   */
  public boolean exists(BHistoryId id);

  /**
   * Create the specified history.
   *
   * @param config The history configuration to use for the new history.
   */
  public void createHistory(BHistoryConfig config)
    throws HistoryException;

  /**
   * Delete the history with the specified id.
   *
   * @param id The unique identifier for the target history.
   */
  public void deleteHistory(BHistoryId id)
    throws HistoryException;

  /**
   * Delete the histories with the specified ids.
   *
   * @param ords The ords of the histories to delete.
   */
  public void deleteHistories(BOrd[] ords)
    throws HistoryException;

  /**
   * Rename the history with the specified history id.
   *
   * @param id The id of the history to rename.
   * @param historyName The new name to assign to the history name.
   */
  public void renameHistory(BHistoryId id, String historyName)
    throws HistoryException;

  /**
   * Clear all records from the specified history.
   *
   * @param id the id of the history to clear.
   */
  public void clearAllRecords(BHistoryId id)
    throws HistoryException;

  /**
   * Clear all records from the specified histories.
   *
   * @param ords The ords of the histories to clear.
   */
  public void clearAllRecords(BOrd[] ords)
    throws HistoryException;

  /**
   * Clear all records from the specified history.
   *
   * @param id The id of the history to clear.
   * @param before The earliest time to keep in the result.  Records
   *   before this time will be removed from the specified histories.
   */
  public void clearOldRecords(BHistoryId id, BAbsTime before)
    throws HistoryException;

  /**
   * Clear all records from the specified histories that are before the
   * specified time.
   *
   * @param ords The ords of the histories to clear.
   * @param before The earliest time to keep in the result.  Records
   *   before this time will be removed from the specified histories.
   */
  public void clearOldRecords(BOrd[] ords, BAbsTime before)
    throws HistoryException;

////////////////////////////////////////////////////////////////
// AutoCloseable
////////////////////////////////////////////////////////////////

  /**
   * Close this connection to the HistorySpace.
   */
  @Override
  public void close();

}
