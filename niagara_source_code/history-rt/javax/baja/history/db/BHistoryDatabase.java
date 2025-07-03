/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bql.BIRelational;
import javax.baja.collection.BITable;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryDevice;
import javax.baja.history.BHistoryEvent;
import javax.baja.history.BHistorySpace;
import javax.baja.history.BIHistory;
import javax.baja.history.DatabaseClosedException;
import javax.baja.history.HistoryEventListener;
import javax.baja.history.HistoryException;
import javax.baja.history.HistoryQuery;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.history.InvalidHistoryIdException;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.history.db.BSysHistories;

/**
 * BHistoryDatabase manages the storage of and access to historical
 * data.  It is a local implementation of BHistorySpace.
 *
 * @author    John Sublett
 */
@NiagaraType
public abstract class BHistoryDatabase
  extends BHistorySpace
  implements BIRelational<BIObject>, BITable<BHistoryConfig>
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.db.BHistoryDatabase(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryDatabase.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create a database with a default load balancer.
   */
  protected BHistoryDatabase()
  {
  }

  @Override
  public HistorySpaceConnection getConnection(Context cx)
    throws HistoryException
  {
    return getDbConnection(cx);
  }

  public abstract HistoryDatabaseConnection getDbConnection(Context cx);

  /**
   * Create a connection to the history database, specifying whether or not
   * to exclude archive history data (e.g. history data archived to the cloud
   * or some other archive provider) during any subsequent queries run by the
   * connection.
   *
   * @param excludeArchiveData When true, archive history data will not be used
   *                          by any queries run by the returned connection
   *                          (only local history data will be considered). When
   *                          false, archive history data will be used to
   *                          supplement local data for any queries run by the
   *                          returned connection.
   * @param cx The Context to use in association with the connection.
   * @return A connection to the history database.
   *
   * @since Niagara 4.11
   */
  public final HistoryDatabaseConnection getDbConnection(boolean excludeArchiveData,
                                                         Context cx)
  {
    Context context = excludeArchiveData?HistoryQuery.makeExcludeArchiveDataContext(cx):cx;
    return getDbConnection(context);
  }

///////////////////////////////////////////////////////////
// BIRelational
///////////////////////////////////////////////////////////

  /**
   * Get the relation with the specified identifier.
   *
   * @param id A string identifier for the relation.  The format
   *   of the string is implementation specific.
   *
   * @param cx The Context associated with this request.
   *   This parameter was added starting in Niagara 4.
   *
   * @return Returns the relation identified by the id or null
   *   if the relation cannot be found.
   */
  @SuppressWarnings("unchecked")
  @Override
  public BITable<BIObject> getRelation(String id, Context cx)
  {
    // Check for a system table.
    if (id.startsWith("sys."))
    {
      BITable<BHistoryConfig> sysTable = getSystemTable(id, cx);
      if (sysTable != null)
      {
        return (BITable)sysTable;
      }
    }

    BOrd histOrd = BOrd.make(new HistoryQuery(id));
    try
    {
      BObject object = histOrd.get(this, cx);
      if(object instanceof BITable)
      {
        if (cx != null && cx.getUser() != null && (object instanceof BIProtected))
        {
          if (!cx.getUser().getPermissionsFor((BIProtected)object).hasOperatorRead())
            return null;
        }
        return (BITable)object;
      }
      else
      {
        throw new IllegalStateException("Unexpected type " + object.getType());
      }
    }
    catch(UnresolvedException e)
    {
      return null;
    }
  }

  @Override
  public BFacets getTableFacets()
  {
    return BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Is this database open?
   *
   * @return Returns true if the database is open, false otherwise.  The
   *   open state of the database and its segments are independent, so
   *   an open database may have segments that are closed.
   */
  public synchronized boolean isOpen()
  {
    return open;
  }

  /**
   * Make sure that the database is open.
   */
  protected void checkOpen()
    throws DatabaseClosedException
  {
    if (!open)
    {
      throw new DatabaseClosedException
        ("The requested operation is not allowed while the database is closed.");
    }
  }

  /**
   * Open the database.
   */
  public final synchronized void open()
    throws IOException
  {
    if (open)
    {
      return;
    }
    doOpen();
    open = true;
    // Tell the listeners
    if (hasHistoryEventListeners())
    {
      fireHistoryEvent(BHistoryEvent.makeDbOpened());
    }
  }

  /**
   * Subclass open.
   */
  protected abstract void doOpen()
    throws IOException;

  /**
   * Close the database.
   */
  public final void close()
  {
    if (!open)
    {
      return;
    }
    doClose();
    open = false;

    if (hasHistoryEventListeners())
    {
      fireHistoryEvent(BHistoryEvent.makeDbClosed());
    }
  }

  /**
   * Subclass close.
   */
  protected abstract void doClose();

  /**
   * Flush all uncommited changes to histories in this database.
   */
  public final void flush()
    throws IOException, HistoryException
  {
    if (!open)
    {
      return;
    }
    doFlush();

    if (hasHistoryEventListeners())
    {
      fireHistoryEvent(BHistoryEvent.makeDbFlushed());
    }
  }

  /**
   * Subclass flush.
   */
  protected abstract void doFlush();

////////////////////////////////////////////////////////////////
// BHistorySpace
////////////////////////////////////////////////////////////////

  /**
   * Get the name of the device that contains the histories
   * in this space.
   */
  @Override
  public String getDeviceName()
  {
    return Sys.getStation().getStationName();
  }

  /**
   * Set the configuration of an existing history.
   */
  public abstract void setConfig(BHistoryConfig newConfig);

////////////////////////////////////////////////////////////////
// System tables
////////////////////////////////////////////////////////////////

  /**
   * Get the system table with the specified id.
   */
  public BITable<BHistoryConfig> getSystemTable(String id)
  {
    return getSystemTable(id, null);
  }

  /**
   * Get the system table with the specified id with Context parameter.
   */
  public BITable<BHistoryConfig> getSystemTable(String id, Context cx)
  {
    if (id.equalsIgnoreCase("sys.histories"))
    {
      return new BSysHistories(this, cx);
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Validate the history configuration before attempting
   * to open or create it.
   */
  private void validateConfig(BHistoryConfig config)
    throws HistoryException
  {
    if (!config.getId().isValid())
    {
      throw new InvalidHistoryIdException(config.getId());
    }
  }

  /**
   * Get all the histories associated with this database.
   *
   * @since Niagara 3.4
   */
  public BIHistory[] getHistories()
  {
    checkOpen();

    List<BIHistory> arr = new ArrayList<BIHistory>();

    BHistoryDevice[] devices = listDevices();
    for (int i = 0; i < devices.length; i++)
    {
      BHistoryDevice dev = devices[i];
      BHistorySpace hs = (BHistorySpace) dev.getSpace();
      arr.addAll(Arrays.asList(hs.listHistories(dev)));
    }

    return arr.toArray(new BIHistory[arr.size()]);
  }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Returns true only if there are any registered HistoryEventListeners
   *
   * @since Niagara 3.4.47
   */
  public boolean hasHistoryEventListeners()
  {
    return listeners.size() > 0;
  }

  /**
   * Get a list of the current HistoryEventListeners.
   *
   * @since Niagara 3.4.47
   */
  public HistoryEventListener[] getHistoryEventListeners()
  {
    HistoryEventListener[] r = null;
    synchronized(listeners)
    {
      r = listeners.toArray(new HistoryEventListener[listeners.size()]);
    }
    return r;
  }

  /**
   * Add a history event listener.
   *
   * @since Niagara 3.4.47
   */
  public void addHistoryEventListener(HistoryEventListener listener)
  {
    synchronized(listeners)
    {
      for(int i=0; i<listeners.size(); ++i)
      {
        if (listeners.get(i) == listener)
        {
          return;
        }
      }
      listeners.add(listener);
    }
  }

  /**
   * Remove a history event listener.
   *
   * @since Niagara 3.4.47
   */
  public void removeHistoryEventListener(HistoryEventListener listener)
  {
    synchronized(listeners)
    {
      for(int i=0; i<listeners.size(); ++i)
      {
        if (listeners.get(i) == listener)
        {
          listeners.remove(i);
          break;
        }
      }
    }
  }

  /**
   * Fire a BHistoryEvent to all the current listeners.
   *
   * @since Niagara 3.4.47
   */
  public void fireHistoryEvent(BHistoryEvent event)
  {
    if (eventLogger.isLoggable(Level.FINE))
    {
      eventLogger.fine(event.toString());
    }

    // get safe copy of listeners
    HistoryEventListener[] x = getHistoryEventListeners();

    // fire to my listeners
    for(int i=0; i<x.length; ++i)
    {
      try
      {
        x[i].historyEvent(event);
      }
      catch(Throwable e)
      {
        e.printStackTrace();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Display
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("historyDatabase.png");

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  public static Logger log = Logger.getLogger("history.db");
  static final Logger eventLogger = Logger.getLogger("history.event");

  private boolean open = false;
  private final ArrayList<HistoryEventListener> listeners = new ArrayList<HistoryEventListener>();
}
