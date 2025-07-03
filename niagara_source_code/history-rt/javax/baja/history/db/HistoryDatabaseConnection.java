/**
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.db;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.history.BCapacity;
import javax.baja.history.BFullPolicy;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryEvent;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistorySummary;
import javax.baja.history.BIHistory;
import javax.baja.history.DuplicateHistoryException;
import javax.baja.history.HistoryException;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.naming.BOrd;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Cursor;
import com.tridium.history.HistoryConfigChange;
import com.tridium.history.HistoryConfigChanges;

public abstract class HistoryDatabaseConnection
  implements HistorySpaceConnection
{
  @Override
  public BHistorySummary getSummary(BIHistory history)
  {
    BHistorySummary summary = new BHistorySummary();
    summary.setId(history.getId());
    summary.setRecordCount(getRecordCount(history));
    summary.setFirstTimestamp(getFirstTimestamp(history));
    summary.setLastTimestamp(getLastTimestamp(history));

    return summary;
  }

////////////////////////////////////////////////////////////////
// HistoryDatabase Implementation
////////////////////////////////////////////////////////////////

  public abstract BHistoryDatabase getHistoryDatabase();

  @Override
  public final void deleteHistory(BHistoryId id)
  {
    doDeleteHistory(id);
  }

  /**
   * Subclass implementation of delete history.
   * Classes that implement doDeleteHistory() method are responsible for firing the appropriate history and nav events.
   */

  public abstract void doDeleteHistory(BHistoryId id);

  @Override
  public void deleteHistories(BOrd[] ords)
  {
    for (BOrd ord : ords)
    {
      try(Cursor<BHistoryId> idCursor = getHistoryDatabase().getHistoryIds(ord))
      {
        while (idCursor.next())
        {
          deleteHistory(idCursor.get());
        }
      }
    }
  }

  @Override
  public final void renameHistory(BHistoryId id, String historyName)
  {
    doRenameHistory(id, historyName);

    // Tell the listeners
    if (getHistoryDatabase().hasHistoryEventListeners())
    {
      getHistoryDatabase().fireHistoryEvent(BHistoryEvent.makeRenamed(id, historyName));
    }

    BOrd parentOrd = BOrd.make("local:|history:/" + id.getDeviceName());
    BNavRoot.INSTANCE
      .fireNavEvent(NavEvent.makeRenamed(parentOrd, id.getHistoryName(), historyName, null));
  }

  /**
   * Subclass rename.
   */
  public abstract void doRenameHistory(BHistoryId id, String historyName);

  /**
   * Clear all records from the specified history.
   */
  @Override
  public abstract void clearAllRecords(BHistoryId id);

  @Override
  public void clearAllRecords(BOrd[] ords)
  {
    for (int i = 0; i < ords.length; i++)
    {
      try (Cursor<BHistoryId> idCursor = getHistoryDatabase().getHistoryIds(ords[i]))
      {
        while (idCursor.next())
        {
          BHistoryId id = idCursor.get();
          clearAllRecords(id);
        }
      }
    }
  }

  @Override
  public abstract void clearOldRecords(BHistoryId id, BAbsTime before);

  @Override
  public void clearOldRecords(BOrd[] ords, BAbsTime before)
    throws HistoryException
  {
    for (int i = 0; i < ords.length; i++)
    {
      try (Cursor<BHistoryId> idCursor = getHistoryDatabase().getHistoryIds(ords[i]))
      {
        while (idCursor.next())
        {
          BHistoryId id = idCursor.get();
          clearOldRecords(id, before);
        }
      }
    }
  }

  @Override
  public final void createHistory(BHistoryConfig config)
    throws HistoryException
  {
    getHistoryDatabase().checkOpen();

    // first make sure the history does not already exist
    if (exists(config.getId()))
    {
      throw new DuplicateHistoryException(config.getId());
    }

    boolean deviceExists = getHistoryDatabase().deviceExists(config.getId().getDeviceName());

    doCreateHistory(config);

    // Tell the listeners
    if (getHistoryDatabase().hasHistoryEventListeners())
    {
      getHistoryDatabase().fireHistoryEvent(BHistoryEvent.makeCreated(config.getId(), config));
    }

    // If the device didn't already exist, fire the added nav event.
    if (!deviceExists)
    {
      BOrd parentOrd = BOrd.make("local:|history:");
      BNavRoot.INSTANCE
        .fireNavEvent(NavEvent.makeAdded(parentOrd, config.getId().getDeviceName(), null));
    }

    BOrd parentOrd = BOrd.make("local:|history:/" + config.getId().getDeviceName());
    BNavRoot.INSTANCE
      .fireNavEvent(NavEvent.makeAdded(parentOrd, config.getId().getHistoryName(), null));
  }

  /**
   * Subclass create.
   */
  protected abstract void doCreateHistory(BHistoryConfig config);

  /**
   * Recreate the history with the id specified in newConfig.
   *
   * @param newConfig The new configuration for the history.
   * @param saveOld If true, the existing history is saved before
   *   creating the new history.
   */
  public abstract void recreateHistory(BHistoryConfig newConfig, boolean saveOld);

  /**
   * Resize the specified history to a new capacity and full policy.
   *
   * @param id The id of the history to resize.
   * @param capacity The new capacity of the history.
   * @param fullPolicy The new full policy.
   */
  public abstract void resizeHistory(BHistoryId id,
                                     BCapacity capacity,
                                     BFullPolicy fullPolicy);

  /**
   * Reconfigure an existing history.
   */
  public void reconfigureHistory(BHistoryConfig newConfig)
  {
    if (!getHistoryDatabase().isOpen())
    {
      return;
    }

    BHistoryId id = newConfig.getId();
    BHistoryConfig oldConfig = getHistoryDatabase().getConfig(id);
    if (oldConfig == null)
    {
      return;
    }

    HistoryConfigChanges changes = HistoryConfigChanges.compare(oldConfig, newConfig);
    if (changes.getChangeCount() == 0)
    {
      return;
    }

    BHistoryConfig newConfigCopy = (BHistoryConfig)newConfig.newCopy(true);
    if (!newConfig.isMounted())
    {
      HistoryConfigChanges.addMissingMetadata(oldConfig, newConfigCopy, changes);
    }

    HistoryConfigChange change = changes.get("recordType");
    BIHistory hist = null; // 4/25/05 S. Hoye added check for empty histories.
                           // If the history is empty (no records), then there
                           // is no need to recreate the history (with another "_cfg"
                           // history).  Added code here to check for empty histories
                           // and short circuit the recreation as necessary
                           // Issue 6384
    if (change != null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(id + ": recordType changed, recreating history");
      }
      hist = getHistory(id);
      if ((hist != null) && (getRecordCount(hist) != 0))
      {
        recreateHistory(newConfigCopy, true);
        return;
      }
    }

    change = changes.get("interval");
    if (change != null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(id + ": interval changed, recreating history");
      }
      if (hist == null)
      {
        hist = getHistory(id);
      }
      if ((hist != null) && (getRecordCount(hist) != 0))
      {
        recreateHistory(newConfigCopy, true);
        return;
      }
    }

    change = changes.get("capacity");
    if (change != null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine(id + ": capacity changed, resizing history");
      }
      resizeHistory(id, newConfigCopy.getCapacity(), newConfigCopy.getFullPolicy());
    }

    getHistoryDatabase().setConfig(newConfigCopy);
  }

  /**
   * Close the database.
   */
  @Override
  public final void close()
  {
    if (!getHistoryDatabase().isOpen())
    {
      return;
    }
    //close the database resources
    doClose();
  }

  /**
   * Subclass close.
   */
  protected abstract void doClose();


  public static Logger log = Logger.getLogger("history.db");
  static final Logger eventLogger = Logger.getLogger("history.event");
}
