/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentFilter;
import javax.baja.collection.BITable;
import javax.baja.dataRecovery.BIDataRecoveryService;
import javax.baja.dataRecovery.BIDataRecoverySourceService;
import javax.baja.history.db.BArchiveHistoryProviders;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import javax.baja.util.Queue;
import javax.baja.util.QueueFullException;
import javax.baja.util.Worker;

import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.history.BDataRecoveryHistoryRecorder;
import com.tridium.history.BHistoryExtStatusJob;
import com.tridium.history.db.BLocalHistoryDatabase;
import com.tridium.history.fox.BFoxHistorySpace;
import com.tridium.history.fox.BHistoryChannel;
import com.tridium.history.log.BLogHistoryService;
import com.tridium.platform.BSystemPlatformService;
import com.tridium.sys.Nre;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.service.BServiceEvent;
import com.tridium.sys.service.ServiceListener;
import com.tridium.sys.service.ServiceManager;
import com.tridium.sys.station.Station;

/**
 * The history service enables collection
 * and storage of histories for a single station.
 * It manages creation of the database and
 * coordination of all history point extensions
 * in the database.
 *
 * @author    John Sublett
 * @creation  02 Apr 02
 * @version   $Revision: 159$ $Date: 11/10/10 9:57:49 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Contains BArchiveHistoryProvider instances that can be optionally
 installed to supplement the local history database with archived history
 data from another source.
 @since Niagara 4.11
 */
@NiagaraProperty(
  name = "archiveHistoryProviders",
  type = "BArchiveHistoryProviders",
  defaultValue = "new BArchiveHistoryProviders()"
)
/*
 When populated with history groups, the navigation
 of the history space will attempt to organize by properties
 assigned to the history configs.  If no history group
 children are found specifying the properties to
 order by, then the navigation will revert back to default
 behavior, or navigation by history id.
 */
@NiagaraProperty(
  name = "historyGroupings",
  type = "BHistoryGroupings",
  defaultValue = "new BHistoryGroupings()"
)
@NiagaraAction(
  name = "saveDb"
)
/*
 Close all histories that have not been accessed within
 the max open time.
 */
@NiagaraAction(
  name = "closeUnusedHistories",
  flags = Flags.ASYNC
)
/*
 Set into service all of the extensions referenced
 in the specified list of ords.
 */
@NiagaraAction(
  name = "enableExtensions",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Set disabled all of the extensions referenced
 in the specified list of ords.
 */
@NiagaraAction(
  name = "disableExtensions",
  parameterType = "BVector",
  defaultValue = "new BVector()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
@SuppressWarnings("squid:S1448")
public class BHistoryService
  extends BComponent
  implements BIService, BIDataRecoverySourceService, BIRestrictedComponent
{
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryService(3394973655)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "archiveHistoryProviders"

  /**
   * Slot for the {@code archiveHistoryProviders} property.
   * Contains BArchiveHistoryProvider instances that can be optionally
   * installed to supplement the local history database with archived history
   * data from another source.
   * @since Niagara 4.11
   * @see #getArchiveHistoryProviders
   * @see #setArchiveHistoryProviders
   */
  public static final Property archiveHistoryProviders = newProperty(0, new BArchiveHistoryProviders(), null);

  /**
   * Get the {@code archiveHistoryProviders} property.
   * Contains BArchiveHistoryProvider instances that can be optionally
   * installed to supplement the local history database with archived history
   * data from another source.
   * @since Niagara 4.11
   * @see #archiveHistoryProviders
   */
  public BArchiveHistoryProviders getArchiveHistoryProviders() { return (BArchiveHistoryProviders)get(archiveHistoryProviders); }

  /**
   * Set the {@code archiveHistoryProviders} property.
   * Contains BArchiveHistoryProvider instances that can be optionally
   * installed to supplement the local history database with archived history
   * data from another source.
   * @since Niagara 4.11
   * @see #archiveHistoryProviders
   */
  public void setArchiveHistoryProviders(BArchiveHistoryProviders v) { set(archiveHistoryProviders, v, null); }

  //endregion Property "archiveHistoryProviders"

  //region Property "historyGroupings"

  /**
   * Slot for the {@code historyGroupings} property.
   * When populated with history groups, the navigation
   * of the history space will attempt to organize by properties
   * assigned to the history configs.  If no history group
   * children are found specifying the properties to
   * order by, then the navigation will revert back to default
   * behavior, or navigation by history id.
   * @see #getHistoryGroupings
   * @see #setHistoryGroupings
   */
  public static final Property historyGroupings = newProperty(0, new BHistoryGroupings(), null);

  /**
   * Get the {@code historyGroupings} property.
   * When populated with history groups, the navigation
   * of the history space will attempt to organize by properties
   * assigned to the history configs.  If no history group
   * children are found specifying the properties to
   * order by, then the navigation will revert back to default
   * behavior, or navigation by history id.
   * @see #historyGroupings
   */
  public BHistoryGroupings getHistoryGroupings() { return (BHistoryGroupings)get(historyGroupings); }

  /**
   * Set the {@code historyGroupings} property.
   * When populated with history groups, the navigation
   * of the history space will attempt to organize by properties
   * assigned to the history configs.  If no history group
   * children are found specifying the properties to
   * order by, then the navigation will revert back to default
   * behavior, or navigation by history id.
   * @see #historyGroupings
   */
  public void setHistoryGroupings(BHistoryGroupings v) { set(historyGroupings, v, null); }

  //endregion Property "historyGroupings"

  //region Action "saveDb"

  /**
   * Slot for the {@code saveDb} action.
   * @see #saveDb()
   */
  public static final Action saveDb = newAction(0, null);

  /**
   * Invoke the {@code saveDb} action.
   * @see #saveDb
   */
  public void saveDb() { invoke(saveDb, null, null); }

  //endregion Action "saveDb"

  //region Action "closeUnusedHistories"

  /**
   * Slot for the {@code closeUnusedHistories} action.
   * Close all histories that have not been accessed within
   * the max open time.
   * @see #closeUnusedHistories()
   */
  public static final Action closeUnusedHistories = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code closeUnusedHistories} action.
   * Close all histories that have not been accessed within
   * the max open time.
   * @see #closeUnusedHistories
   */
  public void closeUnusedHistories() { invoke(closeUnusedHistories, null, null); }

  //endregion Action "closeUnusedHistories"

  //region Action "enableExtensions"

  /**
   * Slot for the {@code enableExtensions} action.
   * Set into service all of the extensions referenced
   * in the specified list of ords.
   * @see #enableExtensions(BVector parameter)
   */
  public static final Action enableExtensions = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code enableExtensions} action.
   * Set into service all of the extensions referenced
   * in the specified list of ords.
   * @see #enableExtensions
   */
  public BOrd enableExtensions(BVector parameter) { return (BOrd)invoke(enableExtensions, parameter, null); }

  //endregion Action "enableExtensions"

  //region Action "disableExtensions"

  /**
   * Slot for the {@code disableExtensions} action.
   * Set disabled all of the extensions referenced
   * in the specified list of ords.
   * @see #disableExtensions(BVector parameter)
   */
  public static final Action disableExtensions = newAction(Flags.HIDDEN, new BVector(), null);

  /**
   * Invoke the {@code disableExtensions} action.
   * Set disabled all of the extensions referenced
   * in the specified list of ords.
   * @see #disableExtensions
   */
  public BOrd disableExtensions(BVector parameter) { return (BOrd)invoke(disableExtensions, parameter, null); }

  //endregion Action "disableExtensions"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BHistoryService()
  {
  }

  /**
   * Get the history database.
   */
  public final BHistoryDatabase getDatabase()
  {
    if (!dbOpen) return null;

    return db;
  }

  /**
   * Create the database instance.
   */
  protected BHistoryDatabase createDatabase()
  {
    return new BLocalHistoryDatabase(this);
  }

  /**
   * Initialize the local history database so that
   * data recovery history events can be restored,
   * but don't allow any new history events to occur
   * until serviceStarted() is called.
   *
   * @since Niagara 3.6
   */
  @Override
  public synchronized void initDataRecoverySource(BIDataRecoveryService dataRecoveryService)
  {
    if (dbInitialized) return;
    dbInitialized = true;

    BSystemPlatformService sys =
      (BSystemPlatformService)Sys.getService(BSystemPlatformService.TYPE);
    if (sys != null)
    {
      if (sys.archiveEnabled(BLocalHistoryDatabase.ARCHIVE_ID))
        sys.extractArchive(BLocalHistoryDatabase.ARCHIVE_ID);
    }

    // create and open the history database
    try
    {
      db = createDatabase();
      db.open();

      if (dataRecoveryService != null)
      {
        try
        { // Look up the BDataRecoveryHistoryRecorder agent for the history
          // database from the registry
          recorder = (BDataRecoveryHistoryRecorder)(db.getAgents().
                     filter(AgentFilter.is(BDataRecoveryHistoryRecorder.TYPE)).
                     getDefault().getInstance());
        }
        catch(Exception x) {}

        if (recorder != null)
        { // Found it, so initialize
          db.fw(Fw.INIT_DATA_RECOVERY_RESTORE, recorder, null, null, null);
          recorder.setDataRecoveryService(dataRecoveryService);
          recorder.setHistorySpace(db);
          try { recorder.setLogHistoryService((BLogHistoryService)Sys.getService(BLogHistoryService.TYPE)); }
          catch(Throwable t) {}
        }
      }
    }
    catch(IOException e)
    {
      throw new HistoryException(e);
    }

    // Make sure the local history database is mounted under the local host
    BLocalHost.INSTANCE.addNavChild(db);
    BLocalHost.INSTANCE.mountSpace(db);
  }

  /**
   * Start the history service.
   */
  @Override
  public synchronized void serviceStarted()
  {
    initDataRecoverySource(getDataRecoveryService());
    if (serviceStarted) return;
    serviceStarted = true;

    // create the history channel if needed
    BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
    if (registry.get("history") == null)
    {
      registry.add("history", new BHistoryChannel());
    }


    if ((db != null) && (recorder != null))
    {
      synchronized(serviceListener)
      {
        db.addHistoryEventListener(recorder);
      }
    }

    dbOpen = true;

    // add myself to receive station.save callbacks
    Station.addSaveListener(saveListener);

    // add myself to receive Service events (so I can check for DataRecoveryService changes)
    AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).addServiceListener(serviceListener);

    if ((db instanceof BLocalHistoryDatabase) &&
        (BLocalHistoryDatabase.WARM_UP_THREAD != null))
      BLocalHistoryDatabase.WARM_UP_THREAD.start();
  }

  @Override
  public void started()
  {
    // 8/9/05 added to fix issue 7265
    cuhQueue = new Queue(1); // Queue size only needs to be 1 to disallow simultaneous requests
    cuhWorker = new CloseUnusedHistoriesWorker(cuhQueue);
    cuhWorker.start("CloseUnusedHistoriesWorker");
    // 8/9/05 end issue 7265 additions

    lastClose = Clock.ticks();
    closeTicket =
      Clock.schedulePeriodically(this, BRelTime.makeMinutes(1), closeUnusedHistories, null);

    try
    {
      if ((recorder != null) && (recorder.getLogHistoryService() == null))
        recorder.setLogHistoryService((BLogHistoryService)Sys.getService(BLogHistoryService.TYPE));
    }
    catch(Throwable t) {}
  }

  @Override
  public void stationStarted()
  {
    try
    {
      if ((recorder != null) && (recorder.getLogHistoryService() == null))
        recorder.setLogHistoryService((BLogHistoryService)Sys.getService(BLogHistoryService.TYPE));
    }
    catch(Throwable t) {}
  }

  @Override
  public synchronized void serviceStopped()
  {
    Station.removeSaveListener(saveListener);

    // remove myself from receiving Service events
    AccessController.doPrivileged((PrivilegedAction<ServiceManager>)() -> Nre.getServiceManager()).removeServiceListener(serviceListener);

    //remove history channel
    BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
    if (registry.get("history") != null)
    {
      registry.remove("history");
    }

    try { remove("onStationSave"); } catch(Exception e) {}
    if (db != null)
    {
      synchronized(serviceListener)
      {
        if (recorder != null)
        {
          db.fw(Fw.INIT_DATA_RECOVERY_RESTORE, null, null, null, null);
          db.removeHistoryEventListener(recorder);
          recorder = null;
        }
      }
      db.close();
      try { BLocalHost.INSTANCE.removeNavChild(db); } catch(Exception e) { }
      try { BLocalHost.INSTANCE.unmountSpace(db); } catch(Exception e) { }
    }

    dbInitialized = false;
    dbOpen = false;

    // unregister the HistoryShortcuts mix in
//    getComponentSpace().disableMixIn(BHistoryShortcutsMixIn.TYPE);
    serviceStarted = false;
  }

  @Override
  public void stopped()
  {
    if (closeTicket != null) closeTicket.cancel();

    cuhWorker.stop(); // 8/9/05 added to fix issue 7265
  }

  /**
   * The history service is registered under "history:HistoryService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }

  /**
   * Test whether the specified history name is unique within this station.
   */
  private boolean isHistoryNameUnique(String histName)
  {
    // first check the history extensions
    BOrd query = BOrd.make("local:|station:|slot:/|bql:select from history:HistoryExt where historyConfig.historyName = '" + histName + "'");
    BITable<?> match = (BITable<?>)query.resolve().get();
    try(Cursor<?> c = match.cursor())
    {
      if (c.next())
      {
        return false;
      }
    }

    // now check the database
    BHistoryId id = BHistoryId.make(Sys.getStation().getStationName(), histName);
    try (HistoryDatabaseConnection conn = getDatabase().getDbConnection(null))
    {
      if (conn.exists(id)) return false;
    }

    return true;
  }

  /**
   * This is a callback when an async action is invoked.  It is
   * overridden here to post a closeUnusedHistories action to a
   * separate worker thread for processing.
   *
   * @return always return null
   */
  @Override
  public IFuture post(Action action, BValue argument, Context cx)
  { // 8/9/05 added to fix issue 7265
    if (action.equals(closeUnusedHistories))
    {
      if (!(cuhWorker.isProcessing())) // Don't allow multiple simultaneous requests!
      {
        try
        {
          cuhQueue.enqueue(new Invocation(this, action, argument, cx));
        }
        catch (QueueFullException qfe) {} // Disregard if queue full
      }
      return null;
    }
    return super.post(action, argument, cx);
  }

  /**
   * Save the database.
   */
  public void doSaveDb()
  {
    if (!serviceStarted) return;
    try
    {
      db.flush();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    BSystemPlatformService sys =
      (BSystemPlatformService)Sys.getService(BSystemPlatformService.TYPE);
    if (sys != null)
    {
      // archive will be shortcircuited if not enabled
      // on platform
      if (db instanceof BLocalHistoryDatabase)
      {
        try
        {
          ((BLocalHistoryDatabase)db).createArchive();
        }
        catch(IOException ex)
        {
          logger.log(Level.SEVERE, "Could not create history archive.", ex);
        }
      }
    }

    // Tell the listeners
    if ((db != null) && (db.hasHistoryEventListeners()))
      db.fireHistoryEvent(BHistoryEvent.makeDbSaved());
  }

  /**
   * Close all histories that have not been accessed recently.
   */
  public void doCloseUnusedHistories()
  {
    // piggyback on the same clock ticket to check the report cache
    flushReportCache();
    long now = Clock.ticks();
    if ((now - lastClose) > getLingerTime() / 2)
    {
      if (db instanceof BLocalHistoryDatabase)
      {
        ((BLocalHistoryDatabase)db).closeUnusedTables(getLingerTime());
      }
      lastClose = Clock.ticks(); // added to fix pacman issue 7952
    }
  }

  public long getLingerTime()
  {
    return HISTORY_LINGER;
  }

  /**
   * Enable the specified history extensions.
   */
  public BOrd doEnableExtensions(BVector extOrds, Context cx)
  {
    return new BHistoryExtStatusJob(this, extOrds, true).submit(cx);
  }

  /**
   * Disable the specified history extensions.
   */
  public BOrd doDisableExtensions(BVector extOrds, Context cx)
  {
    return new BHistoryExtStatusJob(this, extOrds, false).submit(cx);
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Report cache
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int a, Object b, Object c, Object d)
  {
    switch(a)
    {
      case ADD_TO_CACHE: return Integer.valueOf(addToReportCache((byte[])b));
      case GET_FROM_CACHE: return getFromReportCache(((Integer)b).intValue());
      default:
        return null;
    }
  }

  /**
   * Add a report to the cache.
   *
   * @param
   */
  private synchronized int addToReportCache(byte[] file)
  {
    int id = getUniqueId();
    reportCache.put(id, new ReportRec(file));
    return id;
  }

  /**
   * Get the specified report from the cache.  If the
   * report does not exist, null is returned.
   */
  private synchronized byte[] getFromReportCache(int id)
  {
    ReportRec report = reportCache.get(id);
    if (report == null) return null;
    report.touch();
    return report.file;
  }

  /**
   * Get a unique report id.
   */
  private synchronized int getUniqueId()
  {
    int id = (int)(rand.nextFloat() * (float)1000000);
    while (reportCache.get(id) != null)
      id = (int)(rand.nextFloat() * (float)1000000);

    return id;
  }

  /**
   * Remove all expired reports from the cache.
   */
  private synchronized void flushReportCache()
  {
    long now = Clock.ticks();
    ArrayList<Integer> expired = null;
    Iterator<Integer> i = reportCache.keySet().iterator();
    while (i.hasNext())
    {
      Integer key = i.next();
      ReportRec report = reportCache.get(key);
      if (report.isExpired(now))
      {
        if (expired == null) expired = new ArrayList<Integer>(1);
        expired.add(key);
      }
    }

    if (expired == null) return;
    int expCount = expired.size();
    for (int e = 0; e < expCount; e++)
    {
      reportCache.remove(expired.get(e));
    }
  }

  private static class ReportRec
  {
    ReportRec(byte[] file)
    {
      lastTouch = Clock.ticks();
      this.file = file;
    }

    public boolean isExpired(long now)
    {
      return (now - lastTouch) > BRelTime.MILLIS_IN_MINUTE*2;
    }

    public void touch()
    {
      lastTouch = Clock.ticks();
    }

    long lastTouch;
    byte[] file;
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/historyService.png");

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if(x == Fw.RR && db != null)
    {
      db.fw(x, a, b, c, d);
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Convenience method to retrieve the history group category names.
   * Can be called on both the client and server sides, given a base
   * object that exists in the current VM's station space.
   *
   * @since Niagara 3.5
   */
  public static String[] getHistoryGroupNames(BObject base)
  {
    // If called on the proxy side, use the fox history channel to
    // load the history group names
    BHistorySpace space = null;
    try
    {
      if (base instanceof BHistorySpace)
        space = (BHistorySpace)base;
      else
        space = (BHistorySpace)BOrd.make("history:").get(base);
    }
    catch(Exception e) { }

    if (space instanceof BFoxHistorySpace)
    {
      try
      {
        return ((BFoxHistorySpace)space).channel().getHistoryGroupNames();
      }
      catch(Exception e)
      {
        // Fall to code below
      }
    }

    try
    {
      BOrd ord = BOrd.make("service:history:HistoryService");
      BHistoryService service = (BHistoryService)ord.get(base);
      boolean lease = !service.isRunning();
      if (lease) service.lease(1);
      BHistoryGroupings groupings = service.getHistoryGroupings();
      if (lease) groupings.lease(1);

      List<String> result = new ArrayList<String>();
      SlotCursor<Property> c = groupings.getProperties();
      while (c.next(BHistoryGroup.class))
      {
        BHistoryGroup def = (BHistoryGroup)(c.get());
        if ((def != null) && (def.getEnabled()))
          result.add(SlotPath.unescape(c.property().getName()));
      }

      if (result.size() > 0)
        return result.toArray(new String[result.size()]);
      else
        return null;
    }
    catch (Exception e) { e.printStackTrace(); }

    return null;
  }

  /**
   * Convenience method to retrieve the history property names for the given
   * history group category name.  Can be called on both the client and server
   * sides, given a base object that exists in the current VM's station space.
   *
   * @since Niagara 3.5
   */
  public static String[] getSortPropertiesForGroup(BObject base, String historyGroupName)
  {
    // If called on the proxy side, use the fox history channel to
    // load the sort properties for the given history group name
    BHistorySpace space = null;
    try
    {
      if (base instanceof BHistorySpace)
        space = (BHistorySpace)base;
      else
        space = (BHistorySpace)BOrd.make("history:").get(base);
    }
    catch(Exception e) { }

    if (space instanceof BFoxHistorySpace)
    {
      try
      {
        return ((BFoxHistorySpace)space).channel().getSortPropertiesForGroup(historyGroupName);
      }
      catch(Exception e)
      {
        // Fall to code below
      }
    }

    try
    {
      BOrd ord = BOrd.make("service:history:HistoryService");
      BHistoryService service = (BHistoryService)ord.get(base);
      boolean lease = !service.isRunning();
      if (lease) service.lease(1);
      BHistoryGroupings groupings = service.getHistoryGroupings();
      if (lease) groupings.lease(1);

      BValue val = groupings.get(SlotPath.escape(historyGroupName));
      if (val instanceof BHistoryGroup)
      {
        BHistoryGroup def = (BHistoryGroup)val;
        if (def.getEnabled())
          return def.getHistoryPropertiesToGroupBy().getNames();
      }
    }
    catch (Exception e) { e.printStackTrace(); }

    return null;
  }


////////////////////////////////////////////////////////////////
// DataRecoveryService support
////////////////////////////////////////////////////////////////

  private BIDataRecoveryService getDataRecoveryService()
  {
    if (dataRecoveryService == null)
    {
      try
      {
        dataRecoveryService = (BIDataRecoveryService)Sys.getService(BIDataRecoveryService.TYPE);
        if (!dataRecoveryService.isEnabled())
        {
          dataRecoveryService = null;
        }
      }
      catch(ServiceNotFoundException se) { dataRecoveryService = null; }
      catch(Exception e) { e.printStackTrace(); }
    }

    return dataRecoveryService;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final int ADD_TO_CACHE   = 0;
  private static final int GET_FROM_CACHE = 1;
  private static final long HISTORY_LINGER = AccessController.doPrivileged((PrivilegedAction<Long>)
    () -> Long.getLong("niagara.history.localDb.lingerTime",
    BRelTime.makeMinutes(5).getMillis()));

  private boolean serviceStarted = false;
  public static Logger logger = Logger.getLogger("history");


  private static Type[] serviceTypes = new Type[] { TYPE };

  private boolean dbOpen = false;
  private boolean dbInitialized = false;
  private BIDataRecoveryService dataRecoveryService = null;
  private BDataRecoveryHistoryRecorder recorder = null;
  private final ServiceListener serviceListener = new ServiceListener()
  {
    @Override
    public void serviceEvent(BServiceEvent event)
    { // If the data recovery service is added/removed at runtime, then we are notified
      // here.  This gives the history service a chance to init/teardown the history
      // event notifications used to store data recovery.
      if (event.getServiceType().is(BIDataRecoveryService.TYPE))
      {
        BIDataRecoveryService cds = (BIDataRecoveryService)(event.getService());
        if (event.getId() == BServiceEvent.SERVICE_ADDED)
        { // DataRecoveryService just added, so initialize the history event listener
          synchronized(serviceListener)
          {
            if (recorder == null && cds.isEnabled())
            {
              dataRecoveryService = cds;

              try
              { // Look up the BDataRecoveryHistoryRecorder agent for the history
                // database from the registry
                recorder = (BDataRecoveryHistoryRecorder)(db.getAgents().
                           filter(AgentFilter.is(BDataRecoveryHistoryRecorder.TYPE)).
                           getDefault().getInstance());
              }
              catch(Exception x) {}

              if (recorder != null)
              { // Found it, so initialize
                recorder.setDataRecoveryService(dataRecoveryService);
                recorder.setHistorySpace(db);
                try
                {
                  recorder.setLogHistoryService((BLogHistoryService)Sys.getService(BLogHistoryService.TYPE));
                }
                catch(Throwable t) {}
                db.addHistoryEventListener(recorder);
              }
            }
          }
        }
        else if (event.getId() == BServiceEvent.SERVICE_REMOVED)
        { // DataRecoveryService just removed, so teardown the history event listener
          synchronized(serviceListener)
          {
            if ((recorder != null) &&
                ((dataRecoveryService == null) || (dataRecoveryService == cds)))
            {
              dataRecoveryService = null;
              db.removeHistoryEventListener(recorder);
              recorder = null;
            }
          }
        }
      }
      else if (event.getServiceType().is(BLogHistoryService.TYPE))
      {
        try
        {
          if (recorder != null)
            recorder.setLogHistoryService((BLogHistoryService)(event.getService()));
        }
        catch(Throwable t) {}
      }
    }
    public String toString() { return "ServiceListener for HistoryService " + getNavOrd(); }
  };

  private BHistoryDatabase db;
  private Clock.Ticket closeTicket;
  private long lastClose;
  private Map<Integer,ReportRec> reportCache = new HashMap<>();
  private Random rand = new Random();

  private Station.SaveListener saveListener = new Station.SaveListener()
  {
    @Override
    public void stationSave() { saveDb(); }
    @Override
    public void stationSaveOk() {}
    @Override
    public void stationSaveFail(String cause) {}
    public String toString() { return "HistoryService " + getNavOrd(); }
  };

  private Queue cuhQueue; // 8/9/05 added to fix issue 7265
  private CloseUnusedHistoriesWorker cuhWorker; // 8/9/05 added to fix issue 7265

  // 8/9/05 added to fix issue 7265
  private class CloseUnusedHistoriesWorker
    extends Worker
  {
    /**
     * Construct a CloseUnusedHistoriesWorker.
     */
    CloseUnusedHistoriesWorker(ITodo todo)
    {
      super(todo);
    }

    /**
     * Returns true if an item of work is currently
     * being processed.
     */
    public boolean isProcessing()
    {
      return processing;
    }

    /**
     * This method is called on the worker's thread when a
     * new item of work is available.  If the todo timed out
     * then null is passed.
     */
    @Override
    protected void process(Runnable work)
      throws Exception
    {
      processing = true;
      try
      {
        super.process(work);
      }
      catch (Exception e)
      {
        processing = false;
        throw e;
      }
      processing = false;
    }

    // Flag to keep track of work in progress
    private boolean processing = false;
  }
}
