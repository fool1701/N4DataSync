/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.bql.BIRelational;
import javax.baja.bql.BqlQuery;
import javax.baja.bql.Queryable;
import javax.baja.category.BCategoryMask;
import javax.baja.category.BCategoryService;
import javax.baja.collection.BITable;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BSpace;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BUuid;
import javax.baja.util.LexiconText;

import com.tridium.alarm.db.AlarmQuery;
import com.tridium.alarm.db.BAckPendingAlarmTable;
import com.tridium.alarm.db.BAlarmDbQueryResult;
import com.tridium.alarm.db.BOpenAlarmTable;

/**
 * BAlarmDatabase stores both a history of alarms and a
 * list of unacked alarm persistently.
 *
 * @author    John Sublett
 */
@NiagaraType
@SuppressWarnings("rawtypes")
public abstract class BAlarmDatabase
  extends BSpace
  implements Queryable, BIRelational, BIProtected, BIAlarmSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmDatabase(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmDatabase.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  protected BAlarmDatabase()
  {
    super("alarm", LexiconText.make("alarm", "space.alarm"));
  }

  /**
   * Parameterized constructor
   * @param name module name
   * @param lexiconText place holder for the module name
   * @since Niagara 4.11
   */
  protected BAlarmDatabase(String name, LexiconText lexiconText)
  {
    super(name, lexiconText);
  }

  @Override
  public AlarmSpaceConnection getConnection(Context cx)
  {
    return getDbConnection(cx);
  }
  
  public abstract AlarmDbConnection getDbConnection(Context cx);
  
  /**
   * Open the database.
   */
  public synchronized final void open()
    throws IOException
  {
    doOpen();
    open = true;
  }

  /**
   * Subclass override for open.
   *
   * This method must also initializes the totalAlarmCount, unackedAlarmCount,
   * openAlarmCount, and inAlarmCount properties on all BAlarmClasses.
   */
  protected void doOpen()
    throws IOException
  {
  }

  /**
   * Is the database open?
   */
  public boolean isOpen()
  {
    return open;
  }

  public synchronized final void save()
    throws IOException
  {
    if (!isOpen()) return;
    flush();
    doSave();
  }

  /**
   * Save the database.  In some cases, flush is sufficient.
   * The default implementation of doSave does nothing. It is
   * guaranteed that flush() has been called before a call to
   * doSave(), and no changes have been made since flush()
   * was called.
   */
  protected void doSave()
    throws IOException
  {
  }

  /**
   * Commit any outstanding changes.
   */
  public synchronized final void flush()
    throws IOException
  {
    if (!isOpen()) return;

    doFlush();
  }

  /**
   * Subclass override for flush.
   */
  protected void doFlush()
    throws IOException
  {
  }

  /**
   * Make sure the database is open.  If not, an AlarmException is thrown.
   */
  protected synchronized void assertOpen()
  {
     if (!isOpen())
      throw new AlarmException("Operation not allowed while database is closed.");
  }

  /**
   * Close the database.
   */
  public synchronized final void close()
  {
    if (!isOpen()) return;

    try
    {
      doClose();
    }
    catch(Exception e)
    {
      log.log(Level.SEVERE, "Cannot close alarm database.", e);
    }

    open = false;
  }

  /**
   * Subclass override for close.
   */
  protected void doClose()
  {
  }

  /**
   * Update the database with the new configuration.
   *
   * @param config new BAlarmDbConfig
   * @param p Property to update
   * @since Niagara 4.0
   */
  public abstract void updateConfig(BAlarmDbConfig config, Property p)
    throws AlarmException;

  /**
   * Generate and route toNormal notifications for all current offnormal and
   * fault alarms.
   *
   * If the alarm source is an AlarmSourceExt, this method will fire the
   * source's fireToNormal method with the most recent alarm record as the
   * parameter.
   *
   * This is a server side only call and a potentially long running method.
   *  It should never be called from the Nre:Engine thread.
   *
   * @since Niagara 3.6
   */
  public void toNormal(BAlarmRecord normalRecord)
  {
    if (log.isLoggable(Level.FINE) && Thread.currentThread().getName().equals("Nre:Engine"))
    {
      try
      {
        throw new AlarmException("BAlarmDatabase.toNormal called from Nre:Engine Thread");
      }
      catch(AlarmException ae)
      {
        log.log(Level.FINE, "Potential Performance Degradation", ae);
      }
    }
    BAlarmService alarmService = ((BAlarmService)Sys.getService(BAlarmService.TYPE));

    BAlarmRecord last = null;

    //query all not-normal alarms from the source
    StringBuilder queryStr = new StringBuilder("alarm:|bql:select * from openAlarms where ");
    queryStr.append("source = OrdList '");
    queryStr.append(SlotPath.escape(normalRecord.getSource().encodeToString()));
    queryStr.append("' and sourceState != alarm:SourceState.normal and sourceState != alarm:SourceState.alert");

    BOrd query = BOrd.make(queryStr.toString());
    try(Cursor<BAlarmRecord> result = ((BITable<BAlarmRecord>)query.resolve(alarmService).get()).cursor())
    {
      //check toOffnormal as well if offnormal hasn't been acked...
      //assumes that we get the records returned in order
      if(result.next())
      {
        do
        {
          BAlarmRecord record = (BAlarmRecord)((BValue)result.get()).newCopy(true);

          //alerts have no normal state
          if (record.getSourceState() == BSourceState.alert)
            continue;

          record.setSource(normalRecord.getSource());
          record.setAlarmClass(normalRecord.getAlarmClass());
          record.setNormalTime(Clock.time());
          record.setSourceState(BSourceState.normal);
          record.setAckRequired(record.getAckRequired() || normalRecord.getAckRequired());
          record.setAlarmData(BFacets.make(record.getAlarmData(), normalRecord.getAlarmData()));
          if (normalRecord.getAckRequired()) record.setAckState(BAckState.unacked);
          try
          {
            alarmService.doRouteAlarm(record);
          }
          catch (Exception e)
          {
            log.log(Level.SEVERE, "Unable to route alarm", e);
          }
          last = record;
        }
        while (result.next());
      }
    }

    if(last==null)
    {
      BAlarmRecord record = new BAlarmRecord(BUuid.make());
      if (normalRecord.getAckRequired()) record.setAckState(BAckState.unacked);
      record.setSource(normalRecord.getSource());
      record.setAlarmClass(normalRecord.getAlarmClass());
      record.setNormalTime(Clock.time());
      record.setSourceState(BSourceState.normal);
      record.setAckRequired(normalRecord.getAckRequired());
      record.setAlarmData(normalRecord.getAlarmData());
      try
      {
        alarmService.doRouteAlarm(record);
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, "Unable to route alarm", e);
      }
      last = record;
    }

    BOrdList list = normalRecord.getSource();
    BObject obj = list.get(0).resolve().get();
    if(obj instanceof BAlarmSourceExt)
    {
      BAlarmSourceExt ext = (BAlarmSourceExt)obj;
      ext.fireToNormal(last);
    }
  }

////////////////////////////////////////////////////////////////
// Bql
////////////////////////////////////////////////////////////////

  @Override
  public BObject bqlQuery(OrdTarget base, OrdQuery query)
  {
    if (log.isLoggable(Level.FINE) && Thread.currentThread().getName().equals("Nre:Engine"))
    {
      try
      {
        throw new AlarmException("BAlarmDatabase.bqlQuery called from Nre;Engine Thread");
      }
      catch(AlarmException ae)
      {
        log.log(Level.FINE, "Potential Performance Degradation", ae);
      }
    }
    return new BAlarmDbQueryResult(this, (BqlQuery)query);
  }

////////////////////////////////////////////////////////////////
// BIRelational
////////////////////////////////////////////////////////////////

  @Override
  public BITable getRelation(String id, Context cx)
  {
    if (id.equals("openAlarms"))
    {
      return new BOpenAlarmTable(this);
    }
    else if (id.equals("ackPendingAlarms"))
    {
      return new BAckPendingAlarmTable(this);
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Nav
////////////////////////////////////////////////////////////////

  /**
   * @since Niagara 4.11
   * @return
   */
  protected BAlarmService getAlarmService()
  {
    if (cachedAlarmService == null || cachedAlarmService.getComponentSpace() == null)
    {
      cachedAlarmService = (BAlarmService)ALARM_SERVICE_ORD.get(this);
    }
    return cachedAlarmService;
  }

  /**
   * The ord in the session is always "alarm:".
   */
  @Override
  public BOrd getOrdInSession()
  { 
    return ordInSession;
  }

  /**
   * Overridden to return if alarm archive is present
   * @since Niagara 4.11
   */
  @Override
  public boolean hasNavChildren()
  {
    return getAlarmArchive().isPresent();
  }

  /**
   * Get the alarm archive from the AlarmService's AlarmArchiveProvider
   * @return AlarmArchive if present
   * @since Niagara 4.11
   */
  protected Optional<BAlarmArchive> getAlarmArchive()
  {
    return getAlarmService().getAlarmArchive();
  }

  @Override
  public BINavNode[] getNavChildren()
  {
    Optional<BAlarmArchive> archive = getAlarmArchive();
    if (archive.isPresent())
    {
      return new BINavNode[]{ archive.get() };
    }
    return new BINavNode[0];
  }

  @Override
  public BINavNode getNavChild(String navName)
  {
    if (navName.equalsIgnoreCase(BAlarmArchive.NAV_NAME))
    {
      Optional<BAlarmArchive> archive = getAlarmArchive();
      if (archive.isPresent())
      {
        return archive.get();
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// ICategorizable
////////////////////////////////////////////////////////////////
  
  /**
   * Files are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return BCategoryService.getService().getCategoryMask(getOrdInSession());
  }  

  /**
   * Files are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return BCategoryService.getService().getAppliedCategoryMask(getOrdInSession());
  }  
  
////////////////////////////////////////////////////////////////
// BIProtected
////////////////////////////////////////////////////////////////

  @Override
  public BPermissions getPermissions(Context cx)
  { 
    if (cx != null && cx.getUser() != null)
    {
      return cx.getUser().getPermissionsFor(this);
    }
    else
    {
      return BPermissions.all;
    }
  }
  
  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorRead();
  }
  
  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasAdminWrite();
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Display
////////////////////////////////////////////////////////////////
  
  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("alarm.png");

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    return filterAgents(super.getAgents(cx));
  }

  /**
   * Return a filtered agent list for the alarm database.
   * <p>
   * Please note, this method is also used in
   * {@link BAlarmService#getAgents(javax.baja.sys.Context)} and
   * {@link BArchiveAlarmProvider#getAgents(javax.baja.sys.Context)}.
   * </p>
   *
   * @param list The AgentList to be filtered.
   * @return The filtered agent list.
   */
  public static AgentList filterAgents(AgentList list)
  {
    int db = list.indexOf("alarm:AlarmDbMaintenance");

    if (db > -1)
    {
      int uxDb = list.indexOf("alarm:DatabaseMaintenance");

      if (uxDb > -1)
        list.add(db, "alarm:DatabaseMaintenance");
    }

    return list;
  }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private static BOrd ordInSession = BOrd.make("alarm:");
  public static final Logger log = Logger.getLogger("alarm.database");
  private boolean open;
  private static final BOrd ALARM_SERVICE_ORD = BOrd.make("service:alarm:AlarmService");
  private BAlarmService cachedAlarmService;
}
