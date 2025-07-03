/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import javax.baja.alarm.AlarmDbConnection;
import javax.baja.alarm.AlarmException;
import javax.baja.alarm.BAckState;
import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmDatabase;
import javax.baja.alarm.BAlarmDbConfig;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BSourceState;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.query.BGroupByColumn;
import javax.baja.query.BGrouping;
import javax.baja.query.BProjection;
import javax.baja.query.util.Exprs;
import javax.baja.query.util.Funcs;
import javax.baja.rdb.BRdbms;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Queue;

import com.tridium.alarmOrion.transactions.AlarmRecordTransaction;
import com.tridium.alarmOrion.transactions.BOrionAlarmTransactionStatistics;
import com.tridium.alarmOrion.transactions.ToNormal;
import com.tridium.orion.BOrionDatabase;
import com.tridium.orion.BOrionService;
import com.tridium.orion.BOrionSpace;
import com.tridium.orion.OrionCursor;
import com.tridium.orion.OrionException;
import com.tridium.orion.OrionSession;
import com.tridium.orion.priv.model.BDynamicOrionObject;
import com.tridium.orion.sql.BSqlCase;
import com.tridium.orion.sql.BSqlExtent;
import com.tridium.orion.sql.BSqlField;
import com.tridium.orion.sql.BSqlJoin;
import com.tridium.orion.sql.BSqlQuery;
import com.tridium.orion.sql.BatchStatement;
import com.tridium.orion.sql.SqlColumns;
import com.tridium.sys.schema.Fw;

/**
 * An Orion based implementation of an alarm database.
 *
 * @author Lee Adcock on March 18, 2009
 */

@NiagaraType
public class BOrionAlarmDatabase
  extends BAlarmDatabase
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmDatabase(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:11:17 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrionAlarmDatabase.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Alarm Database
////////////////////////////////////////////////////////////////

  @Override
  public boolean isOpen()
  {
    try
    {
      BOrionService orionService = (BOrionService)Sys.getService(BOrionService.TYPE);
      return open && super.isOpen() && orionService.isOrionReady() && getStatus().isValid();
    }
    catch (OrionException | ServiceNotFoundException e)
    {
      //if the OrionService doesn't exist or getStatus() throws an OrionException,
      // the rdb and/or orion are not configured, so return false
      return false;
    }
  }

  @Override
  protected void doOpen()
    throws IOException
  {
    // This method WILL be called multiple times, once as the alarms service starts
    // and a second time as Orion starts.

    BOrionService orionService = (BOrionService)Sys.getService(BOrionService.TYPE);
    if (orionService.isOrionReady())
    {
      // Calculate how long it takes to load the database
      BAbsTime startTimestamp = BAbsTime.now();

      int totalDatabaseAlarms = 0;
      try (OrionSession session = getOrionSession(null);
           OrionCursor cursor = session.scan(BOrionAlarmClass.ORION_TYPE))
      {
        // Update the alarm class components with the values we are now
        // pulling from the database
        while (cursor.next())
        {
          BOrionAlarmClass alarmClass = (BOrionAlarmClass)cursor.get();
          alarmClass.updateAlarmClass(session);
          totalDatabaseAlarms += alarmClass.getTotalAlarmCount();
        }
      }
      finally
      {
        log.info("Loaded " + totalDatabaseAlarms + " alarms (" + startTimestamp
          .delta(BAbsTime.now()) + ")");
      }

      // TODO Update the database capacity.  Depending on whether the capacity is enforced,
      // this may or may not do anything. (see 17750)
      /*
      BOrionAlarmService orionAlarmService = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);
      setCapacity(orionAlarmService.getCapacity());
      */
    }

    open = true;
  }

  @Override
  protected void doClose()
  {
    open = false;

    Queue queue = getQueue();
    if (log.isLoggable(Level.FINE))
    {
      log.info("Flushing queued transactions.");
    }

    // Wait until the queue is empty.
    while (!queue.isEmpty())
    {
      try
      {
        Thread.sleep(50);
      }
      catch (InterruptedException ignore)
      {
      }
    }
  }

  /**
   * @since Niagara 4.0
   */
  public AlarmDbConnection getDbConnection(Context cx)
  {
    if (cx != null && cx.getUser() != null && !cx.getUser().getPermissionsFor(this).hasOperatorRead())
    {
      throw new AlarmException("user needs alarm space permissions");
    }
    return new OrionAlarmDbConnection(this, getOrionSession(cx));
  }

  /**
   * Update the database with the new configuration.
   *
   * @param config new BAlarmDbConfig
   * @param p      Property to update
   * @since Niagara 4.0
   */
  public void updateConfig(BAlarmDbConfig config, Property p)
    throws AlarmException
  {
  }

  /**
   * Recalculate the alarm statistics for each alarm class.  This is a very expensive operation
   * on large databases because it involves recalculating based off of the state of each alarm
   * in the database.  This should only be done on a separate thread.
   */
  public void recalculateAlarmClassStatistics()
  {
    try (OrionSession session = getOrionSession(null))
    {
      BOrionAlarmService service = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);

      {
        // Create a query that will return all the information we need about each of
        // the alarm classes in our database.
        BSqlQuery alarmStatisticsQuery;
        {
          alarmStatisticsQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
          BSqlExtent alarmClassExt = new BSqlExtent(BOrionAlarmClass.ORION_TYPE).alias("alarmClass");
          {
            BProjection projection = new BProjection();

            // unacked
            // this case clause as sql will look like:
            // CASE WHEN (record.ackState != 'Acked' AND (record.sourceState != 'Normal' OR record.ackRequired = TRUE ) THEN 1 ELSE null END
            {
              BSqlCase unackedCase = new BSqlCase();
              unackedCase.whenThen(
                Exprs.builder(Exprs.binary(Exprs.field(BOrionAlarmRecord.ackState), "!=", Exprs.simple(BAckState.acked)))
                  .and(
                    Exprs.binary(
                      Exprs.binary(Exprs.field(BOrionAlarmRecord.sourceState), "!=", Exprs.simple(BSourceState.normal)),
                      "OR",
                      Exprs.binary(Exprs.field(BOrionAlarmRecord.ackRequired), "=", Exprs.simple(BBoolean.TRUE))
                    )
                  ).getExpression(),
                Exprs.simple(BInteger.make(1))
              );
              unackedCase.setElse(Exprs.Null());
              projection.add(SqlColumns.make(Funcs.count(unackedCase)).as("unackedAlarms"));
            }

            // open
            {
              BSqlCase openCase = new BSqlCase(new BSqlField(BOrionAlarmRecord.ORION_TYPE, BOrionAlarmRecord.isOpen));
              openCase.whenThen(Exprs.simple(BBoolean.TRUE), Exprs.simple(BInteger.make(1)));
              openCase.setElse(Exprs.Null());
              projection.add(SqlColumns.make(Funcs.count(openCase)).as("openAlarms"));
            }

            // in alarm
            {
              BSqlCase alarmCase = new BSqlCase(new BSqlField(BOrionAlarmRecord.ORION_TYPE, BOrionAlarmRecord.sourceState));
              alarmCase.whenThen(Exprs.simple(BSourceState.normal), Exprs.Null());
              alarmCase.setElse(Exprs.simple(BInteger.make(1)));
              projection.add(SqlColumns.make(Funcs.count(alarmCase)).as("inAlarm"));
            }

            // total number of alarms
            projection.add(SqlColumns.make(Funcs.count(Exprs.simple(BInteger.make(1)))).as("totalAlarms"));//FIXX: not BInteger?

            // most recent alarm
            projection.add(SqlColumns.make(Funcs.make("MAX", BAbsTime.TYPE, BOrionAlarmRecord.timestamp.getName())).as("lastAlarm"));

            // alarm class
            projection.add(SqlColumns.make(new BSqlField(alarmClassExt, BOrionAlarmClass.alarmClass)));
            alarmStatisticsQuery.select(projection);
          }
          alarmStatisticsQuery.join(new BSqlJoin(
            new BSqlField(BOrionAlarmRecord.ORION_TYPE, BOrionAlarmRecord.alarmClass),
            new BSqlField(alarmClassExt, BOrionAlarmClass.id)
          ));
          {
            BGrouping group = new BGrouping();
            group.add(new BGroupByColumn(new BSqlField(alarmClassExt, BOrionAlarmClass.id)));
            group.add(new BGroupByColumn(new BSqlField(alarmClassExt, BOrionAlarmClass.alarmClass)));
            alarmStatisticsQuery.groupBy(group);
          }
        }

        // Reset alarm statistics
        {
          // Store data from query in a temporary hashmap for quick access
          HashMap<String, BDynamicOrionObject> alarmClassMap = new HashMap<>();
          try (OrionCursor cursor = session.select(alarmStatisticsQuery))
          {
            while (cursor.next())
            {
              BDynamicOrionObject result = (BDynamicOrionObject)cursor.get();
              BString alarmClassName = (BString)result
                .get(BOrionAlarmRecord.alarmClass);
              alarmClassMap.put(alarmClassName.toString(), result);
            }
          }

          // Update the alarm class statistics stored in the Orion database
          {
            // These transactions will be batched for faster database access
            BatchStatement alarmClassUpdater = session.batchUpdate(BOrionAlarmClass.ORION_TYPE);
            BatchStatement alarmClassDeleter = session.batchDelete(BOrionAlarmClass.ORION_TYPE);

            // Get a cursor of all alarm classes currently stored in the database, we'll either
            // update ones we still need or remove ones we don't.
            try (OrionCursor c = session.scan(BOrionAlarmClass.ORION_TYPE))
            {
              while (c.next())
              {
                BOrionAlarmClass alarmClass = (BOrionAlarmClass)c.get();
                BDynamicOrionObject result = alarmClassMap.get(alarmClass.getAlarmClass());
                if (result == null)
                {
                  // This alarm class no longer has alarms, so remove it
                  alarmClassDeleter.add(alarmClass);
                }
                else
                {
                  // This alarm class should be updated, first get the data from
                  // our result.
                  BInteger unackedAlarms = (BInteger)result.get("unackedAlarms");
                  BInteger openAlarms = (BInteger)result.get("openAlarms");
                  BInteger inAlarm = (BInteger)result.get("inAlarm");
                  BInteger totalAlarms = (BInteger)result.get("totalAlarms");
                  BAbsTime lastAlarm = (BAbsTime)result.get("lastAlarm");

                  // Update the class
                  alarmClass.setUnackedAlarmCount(unackedAlarms.getInt());
                  alarmClass.setOpenAlarmCount(openAlarms.getInt());
                  alarmClass.setInAlarmCount(inAlarm.getInt());
                  alarmClass.setTotalAlarmCount(totalAlarms.getInt());
                  alarmClass.setTimeOfLastAlarm(lastAlarm);

                  // Save changes
                  alarmClassUpdater.add(alarmClass);
                }
              }
            }

            // Execute batch processing
            alarmClassUpdater.execute();
            alarmClassDeleter.execute();
          }

          // Update the alarm class statistics stored on the station's alarm class components
          {
            BAlarmClass[] alarmClasses = service.getAlarmClasses();
            for (BAlarmClass alarmClass : alarmClasses)
            {
              BDynamicOrionObject result = alarmClassMap.get(alarmClass.getName());
              if (result == null)
              {
                // This alarm class no longer has alarms, so zero them out
                alarmClass.setUnackedAlarmCount(0);
                alarmClass.setOpenAlarmCount(0);
                alarmClass.setInAlarmCount(0);
                alarmClass.setTotalAlarmCount(0);
              }
              else
              {
                // This alarm class should be updated, first get the data from
                // our result.
                BInteger unackedAlarms = (BInteger)result.get("unackedAlarms");
                BInteger openAlarms = (BInteger)result.get("openAlarms");
                BInteger inAlarm = (BInteger)result.get("inAlarm");
                BInteger totalAlarms = (BInteger)result.get("totalAlarms");
                BAbsTime lastAlarm = (BAbsTime)result.get("lastAlarm");

                // Update the alarm class
                alarmClass.setUnackedAlarmCount(unackedAlarms.getInt());
                alarmClass.setOpenAlarmCount(openAlarms.getInt());
                alarmClass.setInAlarmCount(inAlarm.getInt());
                alarmClass.setTotalAlarmCount(totalAlarms.getInt());
                alarmClass.setTimeOfLastAlarm(lastAlarm);
              }
            }
          }
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
//Queueing
////////////////////////////////////////////////////////////////

  private Queue getQueue()
  {
    BAlarmService service = (BAlarmService)Sys.getService(BAlarmService.TYPE);
    return (Queue)service.fw(Fw.GET_ALARM_QUEUE);
  }

  @Override
  public void toNormal(BAlarmRecord alarmRecord)
  {
    if (log.isLoggable(Level.FINE) && alarmRecord.getSource().size() > 0)
    {
      log.fine("Returning to normal " + alarmRecord.getSource().get(0));
    }

    if (!isOpen())
    {
      throw new AlarmException("Unable to append, database unavailable.");
    }

    enqueue(new ToNormal(alarmRecord));
  }

  private void enqueue(AlarmRecordTransaction transaction)
  {
    Queue queue = getQueue();
    if (queue.isFull())
    {
      throw new AlarmException("Alarm queue is full, unable to enqueue " + transaction);
    }

    BOrionAlarmService service = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);
    Object[] stats = service.getChildren(BOrionAlarmTransactionStatistics.class);
    if (stats.length > 0)
    {
      ((BOrionAlarmTransactionStatistics)stats[0]).transactionEnqueued();
    }

    queue.enqueue(transaction);
  }

  /**
   * TODO Update the database with the defined capacity, taken from the service. (see 17750)
   */
  /*
  public void setCapacity(int capacity)
  {
    BOrionAlarmService alarmService = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);
    if(isOpen() && alarmService.getEnforceCapacity())
      enqueue(new ResizeDatabase());
  }
  */


////////////////////////////////////////////////////////////////
//Framework
////////////////////////////////////////////////////////////////
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// Orion
////////////////////////////////////////////////////////////////

  /**
   * Get the status of the Rdbms that this application is configured to use.
   */
  public BStatus getStatus()
  {
    BOrionAlarmService alarmService = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);
    if (alarmService.getDatabase().isNull())
    {
      throw new OrionException("Database not configured.");
    }
    BRdbms rdbms = (BRdbms)alarmService.getDatabase().resolve(alarmService).get();
    return rdbms.getStatus();
  }

  /**
   * Get the orion database to be used by this application.
   */
  public BOrionDatabase getOrionDatabase()
  {
    BOrionAlarmService alarmService = (BOrionAlarmService)Sys.getService(BOrionAlarmService.TYPE);
    if (alarmService.getDatabase().isNull())
    {
      throw new OrionException("Database not configured.");
    }
    BRdbms rdbms = (BRdbms)alarmService.getDatabase().resolve(alarmService).get();
    BOrionSpace orionSpace = (BOrionSpace)BOrd.make("orion:").resolve(alarmService).get();
    return orionSpace.getOrionDatabase(rdbms);
  }

  /**
   * Create a session to the configured database.
   */
  private OrionSession getOrionSession(Context cx)
  {
    return getOrionDatabase().createSession(cx);
  }

  public final Object exclusiveAccessMutex = new Object();
  public boolean open = false;
}
