/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.io.IOException;
import java.util.logging.Level;
import javax.baja.alarm.AlarmDbConnection;
import javax.baja.alarm.AlarmException;
import javax.baja.alarm.BAckState;
import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmDatabase;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmSource;
import javax.baja.bql.BqlQuery;
import javax.baja.data.BIDataValue;
import javax.baja.data.DataUtil;
import javax.baja.naming.BOrdList;
import javax.baja.query.BExpression;
import javax.baja.query.BOrdering;
import javax.baja.query.util.Columns;
import javax.baja.query.util.Exprs;
import javax.baja.query.util.Funcs;
import javax.baja.query.util.Predicates;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDate;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.util.BUuid;
import com.tridium.alarm.AlarmsToSourcesCursor;
import com.tridium.alarmOrion.OrionAlarmCursor;
import com.tridium.alarmOrion.transactions.AppendAlarmRecord;
import com.tridium.alarmOrion.transactions.ClearAlarmRecord;
import com.tridium.alarmOrion.transactions.UpdateAlarmRecord;
import com.tridium.bql.Range;
import com.tridium.bql.RangeSet;
import com.tridium.bql.SelectQuery;
import com.tridium.orion.BRef;
import com.tridium.orion.OrionCursor;
import com.tridium.orion.OrionSession;
import com.tridium.orion.priv.model.BDynamicOrionObject;
import com.tridium.orion.sql.BExtentProjection;
import com.tridium.orion.sql.BSqlExtent;
import com.tridium.orion.sql.BSqlField;
import com.tridium.orion.sql.BSqlJoin;
import com.tridium.orion.sql.BSqlQuery;
import com.tridium.orion.sql.PropertyValue;
import com.tridium.orion.sql.SqlColumns;

/**
 * Connection to a BOrionAlarmDatabase.
 *
 * @since Niagara 4.0
 */
public class OrionAlarmDbConnection extends AlarmDbConnection
{
  public OrionAlarmDbConnection(BOrionAlarmDatabase db, OrionSession session)
  {
    this.db = db;
    this.session = session;
  }
  
  /**
   * return a reference to the Alarm Database that we're connected to.
   */
  @Override
  public BAlarmDatabase getAlarmDatabase()
  {
    return db;
  }
  
  /**
   * Return the underlaying OrionSession
   */
  public OrionSession getOrionSession()
  {
    return session;
  }
  
///////////////////////////////////////////////////////////
// Flush
///////////////////////////////////////////////////////////

  /**
   * Commit any outstanding changes.
   */
  @Override
  public void flush()
  {

  }

///////////////////////////////////////////////////////////
// Close
///////////////////////////////////////////////////////////

  /**
   * Close the connection.
   */
  @Override
  public void close()
  {
    if (session.isOpen())
    {
      if (!session.getAutoCommit())
      {
        session.commit();
      }
      session.close();
    }
  }
  
////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  /**
   * Archive alarm callback.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   *
   * @param alarmRecord The alarm record to store.
   */
  @Override
  public void append(BAlarmRecord alarmRecord)
    throws IOException, AlarmException
  {
    if(BOrionAlarmDatabase.log.isLoggable(Level.FINE))
      BOrionAlarmDatabase.log.fine("Appending record "+alarmRecord.getUuid());

    if(!db.isOpen())
      throw new AlarmException("Unable to append, database unavailable.");

    if(alarmRecord.getSource().size()==0)
      throw new AlarmException("Alarm must have a source "+alarmRecord.getUuid());

    //do this synchronously
    new AppendAlarmRecord(alarmRecord, db).run();
  }

  /**
   * Update an alarm with new information.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties on the record's BAlarmClass.
   */
  @Override
  public void update(BAlarmRecord alarmRecord)
    throws IOException, AlarmException
  {
    if(BOrionAlarmDatabase.log.isLoggable(Level.FINE))
      BOrionAlarmDatabase.log.fine("Updating record "+alarmRecord.getUuid());

    if(!db.isOpen())
      throw new AlarmException("Unable to update, database unavailable.");

    if(alarmRecord.getSource().size()==0)
      throw new AlarmException("Alarm must have a source "+alarmRecord.getUuid());

    //do this synchronously
    new UpdateAlarmRecord(alarmRecord, db).run();
  }
    
////////////////////////////////////////////////////////////////
// Query
////////////////////////////////////////////////////////////////

  /**
   * Get the number of records in the database.
   */
  @Override
  public int getRecordCount()
  {
    BSqlQuery countQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = Exprs.field(BOrionAlarmRecord.id);
    countQuery.select(SqlColumns.projection(
      SqlColumns.make(Funcs.count(expression.newExprCopy())).as("recordCount")));

    int count = -1;
    try (OrionCursor cursor = session.select(countQuery))
    {
      if (cursor.next())
      {
        BDynamicOrionObject temp = (BDynamicOrionObject) cursor.get();
        count = ((BInteger) temp.get("recordCount")).getInt();
      }
    }

    return count;
  }

  /**
   * @param alarmSource
   * @return
   * @since Niagara 4.4
   */
  public int getOpenRecordCount(BOrdList alarmSource)
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery countQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = getOpenAlarmsForSourceExpression(alarmSource, alarmExt, countQuery);
    countQuery.where(expression);

    countQuery.select(SqlColumns.projection(
        SqlColumns.make(Funcs.count(expression.newExprCopy())).as("openCount")));

    int count = -1;
    try (OrionCursor cursor = session.select(countQuery))
    {
      if (cursor.next())
      {
        BDynamicOrionObject temp = (BDynamicOrionObject) cursor.get();
        count = ((BInteger) temp.get("openCount")).getInt();
      }
    }
    return count;
  }

  /**
   * @param alarmSource
   * @return
   * @since Niagara 4.4
   */
  public int getOpenAckPendingRecordCount(BOrdList alarmSource)
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery countQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = null;
    for(int i=0; i < alarmSource.size(); i++)
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
      BSqlExtent sourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

      countQuery.join(new BSqlJoin(
          new BSqlField(alarmExt, BOrionAlarmRecord.id),
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)
      ));

      countQuery.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
          new BSqlField(sourceExt, BOrionAlarmSource.id)
      ));

      BExpression joinExpression = Exprs.builder(
          Predicates.eq(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
          Predicates.eq(new BSqlField(sourceExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

      BExpression ackPendExpression = Exprs.builder(
          Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.ackState), BAckState.ackPending)).and(
          Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)).getExpression();

      BExpression orExpression = Exprs.builder(ackPendExpression).getExpression();
      BExpression andExpression = Exprs.builder(joinExpression).and(orExpression).getExpression();

      if(expression==null)
      {
        expression = andExpression;
      }
      else
      {
        expression = Exprs.builder(expression).and(andExpression).getExpression();
      }
    }

    countQuery.where(expression);
    countQuery.select(SqlColumns.projection(
        SqlColumns.make(Funcs.count(expression.newExprCopy())).as("openAckCount")));

    int count = -1;
    try (OrionCursor cursor = session.select(countQuery))
    {
      if (cursor.next())
      {
        BDynamicOrionObject temp = (BDynamicOrionObject) cursor.get();
        count = ((BInteger) temp.get("openAckCount")).getInt();
      }
    }
    return count;
  }

  /**
   * @param alarmSource
   * @return
   * @since Niagara 4.4
   */
  public int getOpenUnackedRecordCount(BOrdList alarmSource)
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery countQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = null;
    for(int i=0; i < alarmSource.size(); i++)
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
      BSqlExtent sourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

      countQuery.join(new BSqlJoin(
          new BSqlField(alarmExt, BOrionAlarmRecord.id),
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)
      ));

      countQuery.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
          new BSqlField(sourceExt, BOrionAlarmSource.id)
      ));

      BExpression joinExpression = Exprs.builder(
          Predicates.eq(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
          Predicates.eq(new BSqlField(sourceExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

      BExpression openUnackedExpression = Exprs.builder(
          Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.ackState), BAckState.unacked)).and(
          Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)).getExpression();

      BExpression orExpression = Exprs.builder(openUnackedExpression).getExpression();
      BExpression andExpression = Exprs.builder(joinExpression).and(orExpression).getExpression();

      if(expression==null)
      {
        expression = andExpression;
      }
      else
      {
        expression = Exprs.builder(expression).and(andExpression).getExpression();
      }
    }

    countQuery.where(expression);

    countQuery.select(SqlColumns.projection(
        SqlColumns.make(Funcs.count(expression.newExprCopy())).as("unackCount")));

    int count = -1;
    try (OrionCursor cursor = session.select(countQuery))
    {
      if (cursor.next())
      {
        BDynamicOrionObject temp = (BDynamicOrionObject) cursor.get();
        count = ((BInteger) temp.get("unackCount")).getInt();
      }
    }

    return count;
  }

  /**
   * @param alarmSource
   * @return
   * @since Niagara 4.4
   */
  public int getOpenAckedRecordCount(BOrdList alarmSource)
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery countQuery = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = null;
    for(int i=0; i < alarmSource.size(); i++)
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
      BSqlExtent sourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

      countQuery.join(new BSqlJoin(
          new BSqlField(alarmExt, BOrionAlarmRecord.id),
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)
      ));

      countQuery.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
          new BSqlField(sourceExt, BOrionAlarmSource.id)
      ));

      BExpression joinExpression1 = Exprs.builder(
          Predicates.eq(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
          Predicates.eq(new BSqlField(sourceExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

      BExpression openAckedExpression = Exprs.builder(
        Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.ackState), BAckState.acked)).and(
        Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)).getExpression();

      BExpression andExpression = Exprs.builder(joinExpression1).and(openAckedExpression).getExpression();

      if(expression==null)
      {
        expression = andExpression;
      }
      else
      {
        expression = Exprs.builder(expression).and(andExpression).getExpression();
      }
    }

    countQuery.where(expression);
    countQuery.select(SqlColumns.projection(
        SqlColumns.make(Funcs.count(expression.newExprCopy())).as("ackCount")));

    int count = -1;
    try (OrionCursor cursor = session.select(countQuery))
    {
      if (cursor.next())
      {
        BDynamicOrionObject temp = (BDynamicOrionObject) cursor.get();
        count = ((BInteger) temp.get("ackCount")).getInt();
      }
    }

    return count;
  }

  /**
   * Get a record by uuid.
   *
   * @param uuid The uuid of the record to retrieve.
   * @return Returns the target record or null if no
   *   record is found with a matching uuid.
   */
  @Override
  public BAlarmRecord getRecord(BUuid uuid)
  {
    return getRecord(uuid, session);
  }

  public BAlarmRecord getRecord(BUuid uuid, OrionSession session)
  {
    BOrionAlarmRecord record = getOrionRecord(uuid, session);
    if(record!=null)
      return record.getAlarmRecord(session);
    else
      return null;
  }

  public BOrionAlarmRecord getOrionRecord(BUuid uuid)
  {
    return getOrionRecord(uuid, session);
  }

  public BOrionAlarmRecord getOrionRecord(BUuid uuid, OrionSession session)
  {
    OrionCursor cursor = session.select(BOrionAlarmRecord.ORION_TYPE, new PropertyValue[] {
        new PropertyValue(BOrionAlarmRecord.uuidHash, BInteger.make(uuid.hashCode())),
        new PropertyValue(BOrionAlarmRecord.uuid, uuid),
      });
    try
    {
      if(cursor.next())
      {
        return ((BOrionAlarmRecord)cursor.get());
      } else {
        return null;
      }
    } finally {
      cursor.close();
    }
  }

  /**
   * Get the open alarms in the database.  An alarm is considered open when:
   * <p>
   * not (acked and normal) and not (acked and alert)
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public Cursor<BAlarmRecord> getOpenAlarms()
    throws IOException
  {
    // This method is optimized to retrieve all records, facets, and sources with
    // only three sql queries being run against the database.

    // Synchronized to avoid timing issues if another thread inserts alarm records
    // during the time these three queries are executing

    // Alarm Records
    BSqlQuery queryRecords = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    {
      queryRecords.where(Exprs.builder(Predicates.eq(BOrionAlarmRecord.isOpen, BBoolean.TRUE)));
      BOrdering ordering = new BOrdering();
      ordering.add(Columns.orderBy(Exprs.field(BOrionAlarmRecord.id)).asc());
      queryRecords.orderBy(ordering);
    }

    // Facets
    BSqlQuery queryFacets = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);
    {
      BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      queryFacets.join(new BSqlJoin(
          new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm),
          new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
          ));
      queryFacets.where(Exprs.builder(Predicates.eq(new BSqlField(alarmRecordExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)));
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm)).asc());
        queryFacets.orderBy(ordering);
      }
    }

    // Sources
    BSqlQuery querySources = BSqlQuery.make(BOrionAlarmSourceOrder.ORION_TYPE);
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      querySources.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm),
          new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
          ));
      querySources.where(Exprs.builder(Predicates.eq(new BSqlField(alarmRecordExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)));
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)).asc());
        ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder)));
        querySources.orderBy(ordering);
      }
    }

    synchronized (db.exclusiveAccessMutex)
    {
      OrionCursor records = session.select(queryRecords);
      OrionCursor facets = session.select(queryFacets);
      OrionCursor sources = session.select(querySources);
      return new OrionAlarmCursor(records, facets, sources, session, false);
    }
  }

  /**
   * Get the ackPending alarms in the database.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public Cursor<BAlarmRecord> getAckPendingAlarms()
    throws IOException
  {
    // This method is optimized to retrieve all records, facets, and sources with
    // only three sql queries being run against the database.

    // Synchronized to avoid timing issues if another thread inserts alarm records
    // during the time these three queries are executing

    // Alarm Records
    BSqlQuery queryRecords = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    {
      queryRecords.where(Predicates.eq(BOrionAlarmRecord.ackState, BAckState.ackPending));
      BOrdering ordering = new BOrdering();
      ordering.add(Columns.orderBy(Exprs.field(BOrionAlarmRecord.id)).asc());
      queryRecords.orderBy(ordering);
    }

    // Facets
    BSqlQuery queryFacets = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);
    {
      BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      queryFacets.where(Predicates.eq(BOrionAlarmRecord.ackState, BAckState.ackPending));
      queryFacets.join(new BSqlJoin(
          new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm),
          new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
          ));
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)).asc());
        queryFacets.orderBy(ordering);
      }
    }

    // Sources
    BSqlQuery querySources = BSqlQuery.make(BOrionAlarmSourceOrder.ORION_TYPE);
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      querySources.where(Predicates.eq(BOrionAlarmRecord.ackState, BAckState.ackPending));
      querySources.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm),
          new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
          ));
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)).asc());
        ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder)));
        querySources.orderBy(ordering);
      }
    }

    synchronized (db.exclusiveAccessMutex)
    {
      return new OrionAlarmCursor(session.select(queryRecords), session.select(queryFacets), session.select(querySources), session, false);
    }
  }
  
  @Override
  public Cursor<BAlarmSource> getOpenAlarmSources()
    throws IOException
  {
    // TODO: This is a brute force implementation.  In the future, the database structure needs to be modified to
    // TODO: better track alarms by source.
    Cursor<BAlarmRecord> alarms = getOpenAlarms();
    return new AlarmsToSourcesCursor(alarms);
  }

  /**
   * Get all alarms for the specified source.  The result
   * will be sorted in timestamp order with the oldest
   * alarm first.
   * <p>
   * The AlarmRefrence may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public Cursor<BAlarmRecord> getAlarmsForSource(BOrdList alarmSource)
    throws IOException
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery query = generateAlarmsForSourceQuery(alarmSource, alarmExt);
    query.orderBy(BOrdering.make(Columns.orderBy(new BSqlField(alarmExt, BOrionAlarmRecord.id)).asc()));
    OrionCursor cursor = session.select(query);
    return new OrionAlarmCursor(cursor, null, null, session, false);
  }

  @Override
  public Cursor<BAlarmRecord> getOpenAlarmsForSource(BOrdList alarmSource) throws IOException
  {
    BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
    BSqlQuery query = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = getOpenAlarmsForSourceExpression(alarmSource, alarmExt, query);

    query.where(expression);
    query.orderBy(BOrdering.make(Columns.orderBy(new BSqlField(alarmExt, BOrionAlarmRecord.id)).asc()));
    OrionCursor cursor = session.select(query);
    return new OrionAlarmCursor(cursor, null, null, session, false);
  }

  private BExpression getAlarmsForSourceExpression(BOrdList alarmSource, BSqlExtent alarmExt, BSqlQuery query)
  {
    BExpression expression = null;
    for(int i=0; i < alarmSource.size(); i++)
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
      BSqlExtent sourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

      query.join(new BSqlJoin(
        new BSqlField(alarmExt, BOrionAlarmRecord.id),
        new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)
      ));

      query.join(new BSqlJoin(
        new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
        new BSqlField(sourceExt, BOrionAlarmSource.id)
      ));

      BExpression joinExpression = Exprs.builder(
        Predicates.eq(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
        Predicates.eq(new BSqlField(sourceExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

      if(expression==null)
        expression = joinExpression;
      else
        expression = Exprs.builder(expression).and(joinExpression).getExpression();
    }

    return expression;
  }

  private BSqlQuery generateAlarmsForSourceQuery(BOrdList alarmSource, BSqlExtent alarmExt)
  {
    BSqlQuery query = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    BExpression expression = getAlarmsForSourceExpression(alarmSource, alarmExt, query);
    query.where(expression);
    return query;
  }

  private BExpression getOpenAlarmsForSourceExpression(BOrdList alarmSource, BSqlExtent alarmExt, BSqlQuery query)
  {
    BExpression expression = null;
    for(int i=0; i < alarmSource.size(); i++)
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
      BSqlExtent sourceExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

      query.join(new BSqlJoin(
          new BSqlField(alarmExt, BOrionAlarmRecord.id),
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)
      ));

      query.join(new BSqlJoin(
          new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarmSource),
          new BSqlField(sourceExt, BOrionAlarmSource.id)
      ));

      BExpression joinExpression1 = Exprs.builder(
          Predicates.eq(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
          Predicates.eq(new BSqlField(sourceExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

      BExpression isOpenExpression = Exprs.builder(
        Predicates.eq(new BSqlField(alarmExt, BOrionAlarmRecord.isOpen), BBoolean.TRUE)).getExpression();

      BExpression andExpression = Exprs.builder(joinExpression1).and(isOpenExpression).getExpression();

      if(expression==null)
        expression = andExpression;
      else
        expression = Exprs.builder(expression).and(andExpression).getExpression();
    }

    return expression;
  }

  /**
   * Get a cursor for iterating through all record in the database.
   * <p>
   * The AlarmReference may be reused so to store a copy of the BAlarmRecord, you must use newCopy().
   */
  @Override
  public Cursor<BAlarmRecord> scan()
    throws IOException, AlarmException
  {
    // This method is optimized to retrieve all records, facets, and sources with
    // only three sql queries being run against the database.

    // Synchronized to avoid timing issues if another thread inserts alarm records
    // during the time these three queries are executing

    // Alarm Records
    BSqlQuery queryRecords = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    {
      BOrdering ordering = new BOrdering();
      ordering.add(Columns.orderBy(Exprs.field(BOrionAlarmRecord.id)));
      queryRecords.orderBy(ordering);
    }

    // Facets
    BSqlQuery queryFacets = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);
    {
      BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      queryFacets.join(new BSqlJoin(
        new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm),
        new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
      ));
    }

    // Sources
    BSqlQuery querySources = BSqlQuery.make(BOrionAlarmSourceOrder.ORION_TYPE);
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      querySources.join(new BSqlJoin(
        new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm),
        new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
      ));
      BOrdering ordering = new BOrdering();
      ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder)));
      querySources.orderBy(ordering);
    }

    synchronized (db.exclusiveAccessMutex)
    {
      OrionCursor records = session.select(queryRecords);
      OrionCursor facets = session.select(queryFacets);
      OrionCursor sources = session.select(querySources);
      return new OrionAlarmCursor(records, facets, sources, session, false);
    }
  }

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
  public Cursor<BAlarmRecord> timeQuery(BAbsTime start, BAbsTime end)
    throws IOException, AlarmException
  {
    // This method is optimized to retrieve all records, facets, and sources with
    // only three sql queries being run against the database.
        
    // Alarm Records
    BSqlQuery queryRecords = BSqlQuery.make(BOrionAlarmRecord.ORION_TYPE);
    {
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      queryRecords.where(
        Exprs.builder(Predicates.ge(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), start)) //start The earliest timestamp that will be included
          .and(Predicates.le(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), end)));          //end The latest timestamp that will be included
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)).asc());
        queryRecords.orderBy(ordering);
      }
    }

    // Facets
    BSqlQuery queryFacets = BSqlQuery.make(BOrionAlarmFacetValue.ORION_TYPE);
    {
      BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      queryFacets.join(new BSqlJoin(
        new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm),
        new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
      ));
      queryFacets.where(
        Exprs.builder(Predicates.ge(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), start)) //start The earliest timestamp that will be included
        .and(Predicates.le(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), end)));          //end The latest timestamp that will be included
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm)).asc());
        queryFacets.orderBy(ordering);
      }
    }

    // Sources
    BSqlQuery querySources = BSqlQuery.make(BOrionAlarmSourceOrder.ORION_TYPE);
    {
      BSqlExtent sourceOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE);
      BSqlExtent alarmRecordExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE);
      querySources.join(new BSqlJoin(
        new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm),
        new BSqlField(alarmRecordExt, BOrionAlarmRecord.id)
      ));
      querySources.where(
        Exprs.builder(Predicates.ge(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), start)) //start The earliest timestamp that will be included
        .and(Predicates.le(new BSqlField(alarmRecordExt, BOrionAlarmRecord.timestamp), end)));          //end The latest timestamp that will be included
      {
        BOrdering ordering = new BOrdering();
        ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.alarm)).asc());
        ordering.add(Columns.orderBy(new BSqlField(sourceOrderExt, BOrionAlarmSourceOrder.sourceOrder)));
        querySources.orderBy(ordering);
      }
    }

    // Synchronized to avoid timing issues if another thread inserts alarm records 
    // during the time these three queries are executing    
    
    synchronized(db.exclusiveAccessMutex)
    {
      OrionCursor records = session.select(queryRecords);
      OrionCursor facets = session.select(queryFacets);
      OrionCursor sources = session.select(querySources);
      return new OrionAlarmCursor(records, facets, sources, session, false);
    }
  }
  
  @Override
  public Cursor<BAlarmRecord> doBqlQuery(BqlQuery bql)
    throws AlarmException
  {
    if(BOrionAlarmDatabase.log.isLoggable(Level.FINE))
      BOrionAlarmDatabase.log.fine("Executing bql "+bql.toString());

    try
    {
      SelectQuery select = (SelectQuery)bql;
      BSqlQuery query = (BSqlQuery)new BSqlQuery().select(new BExtentProjection());
      query.getProjection().setDistinct(true);

      // TODO Add support for applying sorting parameters to the Orion
      // query, as taken from the bql query
      // BOrdering ordering = query.getOrdering();

      BExpression expression = null;

      BSqlExtent alarmExt = new BSqlExtent(BOrionAlarmRecord.ORION_TYPE).alias("alarms");

      // Alarm record properties
      {
        SlotCursor<Property> slotCursor = new BOrionAlarmRecord().getProperties();
        while(slotCursor.next())
        {
          Property property = slotCursor.property();

          if(!property.getType().is(BRef.TYPE))
          {
            RangeSet rangeSet = select.getRange(property.getName(), property.getType(), false);

            if (!rangeSet.isAll())
            {
              if(expression==null)
                expression = createExpression(rangeSet, property);
              else
                expression = Exprs.builder(expression).and(createExpression(rangeSet, property)).getExpression();

              // Optimize to use date stamp in conjunction with time stamp
              {
                if(property.equals(BOrionAlarmRecord.timestamp))
                {
                  // Even if the range is disjoint, we might be able to get a more
                  // efficient query by getting the absolute minimum time, and the
                  // absolute maximum time.

                  // Find the rangeset's min, max
                  BAbsTime max = (BAbsTime)rangeSet.getRange(0).max;
                  BAbsTime min = (BAbsTime)rangeSet.getRange(0).min;
                  for(int i=1; i<rangeSet.getCount(); i++)
                  {
                    Range range = rangeSet.getRange(i);
                    if(max!=null && (range.max==null || ((BAbsTime)range.max).isAfter(max))) max = (BAbsTime)range.max;
                    if(min!=null && (range.min==null || ((BAbsTime)range.min).isBefore(min))) min= (BAbsTime)range.min;
                  }

                  // Narrow down the results by datestamp (indexed), so that searching timestamp (unindexed) will
                  // have fewer rows to scan
                  if(min!=null)
                    expression = Exprs.builder(expression).and(Predicates.gt(BOrionAlarmRecord.datestamp, BDate.make(min).prevDay())).getExpression();
                  if(max!=null)
                    expression = Exprs.builder(expression).and(Predicates.lt(BOrionAlarmRecord.datestamp, BDate.make(max).nextDay())).getExpression();
                }
              }
            }
          }
        }
      }

      // Source (BOrdList)
      {
        RangeSet rangeSet = select.getRange("source", BOrdList.TYPE, true);
        if (!rangeSet.isAll() && rangeSet.getCount() == 1)
        {
          BOrdList alarmSource = (BOrdList)rangeSet.getRange(0).getMin();

          for(int i=0; i<alarmSource.size(); i++)
          {
            BSqlExtent sourceListOrderExt = new BSqlExtent(BOrionAlarmSourceOrder.ORION_TYPE).alias("sourceOrder"+i);
            BSqlExtent sourceListExt = new BSqlExtent(BOrionAlarmSource.ORION_TYPE).alias("alarmSource"+i);

            query.join(new BSqlJoin(
                new BSqlField(alarmExt, BOrionAlarmRecord.id),
                new BSqlField(sourceListOrderExt, BOrionAlarmSourceOrder.alarm)
                ));

            query.join(new BSqlJoin(
                new BSqlField(sourceListOrderExt, BOrionAlarmSourceOrder.alarmSource),
                new BSqlField(sourceListExt, BOrionAlarmSource.id)
                ));

            BExpression joinExpression = Exprs.builder(
                Predicates.eq(new BSqlField(sourceListOrderExt, BOrionAlarmSourceOrder.sourceOrder), BInteger.make(i))).and(
                    Predicates.eq(new BSqlField(sourceListExt, BOrionAlarmSource.source), alarmSource.get(i))).getExpression();

            if(expression==null)
              expression = joinExpression;
            else
              expression = Exprs.builder(expression).and(joinExpression).getExpression();
          }
        }
      }

      // Alarm class name (BString)
      {
        // Do we limit the records returned based on alarm record?  //TODO - I'm not sure that this is correct
        RangeSet rangeSet = select.getRange("alarmClass", BString.TYPE, true);
        if (!rangeSet.isAll() && (rangeSet.getCount() == 1))
        {
          BString className = (BString)rangeSet.getRange(0).getMin();

          BSqlExtent alarmClassExt = new BSqlExtent(BOrionAlarmClass.ORION_TYPE);

          query.join(new BSqlJoin(
              new BSqlField(alarmExt, BOrionAlarmRecord.alarmClass),
              new BSqlField(alarmClassExt, BOrionAlarmClass.id)
              ));

          BExpression joinExpression = Exprs.builder(
              Predicates.eq(new BSqlField(alarmClassExt, BOrionAlarmClass.alarmClass), className)).getExpression();

          if(expression==null)
            expression = joinExpression;
          else
            expression = Exprs.builder(expression).and(joinExpression).getExpression();
        }
      }

      // Data facets (BIDataValue)
      {
        try (OrionCursor facetCursor = session.scan(BOrionAlarmFacetName.ORION_TYPE))
        {
          BExpression facetsExpression = null;

          // TODO This approach only works for reasonable numbers of facet names because
          // it requires iterating through each possible fact to see if it is included in
          // the BQL expression.  As the number of facet names increases, this will take
          // longer to execute.  It is unknown at what point the number of facet names
          // makes performance unacceptable.
          while (facetCursor.next())
          {
            BOrionAlarmFacetName facetName = (BOrionAlarmFacetName) facetCursor
              .get();

            // Do we limit the records returned based on the value of this facet?
            RangeSet rangeSet = select
              .getRange(facetName.getFacetName(), BIDataValue.TYPE, true);
            if (!rangeSet.isAll() && rangeSet.getCount() == 1)
            {
              // Join the facet value table and create an expression

              BSqlExtent facetValueExt = new BSqlExtent(BOrionAlarmFacetValue.ORION_TYPE)
                .alias("value_" + facetName.getFacetName());

              query.join(new BSqlJoin(
                new BSqlField(alarmExt, BOrionAlarmRecord.id),
                new BSqlField(facetValueExt, BOrionAlarmFacetValue.alarm)
              ));

              BExpression facetExpression;
              try
              {
                facetExpression = Exprs.builder(
                  Predicates
                    .eq(new BSqlField(facetValueExt, BOrionAlarmFacetValue.value), BString
                      .make(DataUtil.marshal(rangeSet.getRange(0).getMin()))))
                  .and(
                    Predicates
                      .eq(new BSqlField(facetValueExt, BOrionAlarmFacetValue.facetName), BRef
                        .make(facetName))).getExpression();
              }
              catch (IOException ioe)
              {
                throw new BajaRuntimeException("Unable to marshal facet expression: " + rangeSet
                  .getRange(0).getMin(), ioe);
              }
              if (expression == null)
                facetsExpression = facetExpression;
              else
                facetsExpression = Exprs.builder(facetsExpression)
                  .or(facetExpression).getExpression();
            }
          }
          if (expression == null)
            expression = facetsExpression;
          else
            expression = Exprs.builder(expression).and(facetsExpression)
              .getExpression();
        }
      }

      query.where(expression);
      query = (BSqlQuery)query.from(alarmExt);
      query.orderBy(BOrdering.make(Columns.orderBy(new BSqlField(alarmExt, BOrionAlarmRecord.id)).asc()));

      synchronized(db.exclusiveAccessMutex)
      {
        // Get alarm cursor - autoClose since bql queries won't call connection.close()
        return new OrionAlarmCursor(session.select(query), null, null, session, true);
      }
    } catch (RuntimeException e) {
      throw new BajaRuntimeException("Unable to execute BQL against alarm database.", e);
    }
  }
  
////////////////////////////////////////////////////////////////
// Maintenance
////////////////////////////////////////////////////////////////

  /**
   * Clear all records from the database.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   */
  @Override
  public void clearAllRecords(Context cx)
    throws IOException
  {
    synchronized (db.exclusiveAccessMutex)
    {
      session.delete(BOrionAlarmRecord.ORION_TYPE, null);

      try (OrionCursor cursor = session.scan(BOrionAlarmClass.ORION_TYPE))
      {
        while (cursor.next())
        {
          session.delete((BOrionAlarmClass) cursor.get());
        }
      }
    }

    try
    {
      BOrionAlarmService service = (BOrionAlarmService) Sys
        .getService(BOrionAlarmService.TYPE);

      BAlarmClass[] alarmClasses = service.getAlarmClasses();
      for (int i = 0; i < alarmClasses.length; i++)
      {
        // This alarm class no longer has alarms, so zero them out
        BAlarmClass alarmClass = alarmClasses[i];
        alarmClass.setUnackedAlarmCount(0);
        alarmClass.setOpenAlarmCount(0);
        alarmClass.setInAlarmCount(0);
        alarmClass.setTotalAlarmCount(0);
      }
    }
    catch (Exception ignore)
    {

    }
  }

  /**
   * Clear all records with a timestamp before the specified time.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   *
   * @param before The earliest time to keep in the result.  Records
   *   before this time will be removed.
   */
  @Override
  public void clearOldRecords(BAbsTime before, Context cx)
    throws IOException
  {
    synchronized (db.exclusiveAccessMutex)
    {
      // Optimize using datestamp
      BExpression dateExpression = Exprs.builder(
          Predicates.lt(BOrionAlarmRecord.datestamp, BDate.make(before).nextDay())).and(
              Predicates.lt(BOrionAlarmRecord.timestamp, before)).getExpression();

      session.delete(BOrionAlarmRecord.ORION_TYPE, dateExpression);
    }

    try
    {
      db.recalculateAlarmClassStatistics(); // expensive on large databases!
    }
    catch (Exception e)
    {
      if(BOrionAlarmDatabase.log.isLoggable(Level.SEVERE))
        BOrionAlarmDatabase.log.log(Level.SEVERE, "Failed to update Alarm Class Statistics", e);
    }
  }

  /**
   * Clear the record with the given uuid.
   * Also update the totalAlarmCount, unackedAlarmCount, openAlarmCount,
   * and inAlarmCount properties all BAlarmClasses.
   *
   * @param uuid the Uuid of the Alarm Record to remove from the database.
   */
  @Override
  public void clearRecord(BUuid uuid, Context cx)
    throws IOException
  {
    if(BOrionAlarmDatabase.log.isLoggable(Level.FINE))
      BOrionAlarmDatabase.log.fine("Clearing record "+uuid);

    if(!db.isOpen())
      throw new AlarmException("Unable to update, database unavailable.");

    //do this synchronously
    new ClearAlarmRecord(uuid, db).run();
  }

///////////////////////////////////////////////////////////
// Internal methods
///////////////////////////////////////////////////////////

  /**
   * Translate a RangeSet into a BExpression for the provided property
   */
  private BExpression createExpression(RangeSet rangeSet, Property property)
  {
    BExpression rangeSetExpression = null;
    for(int i=0; i<rangeSet.getCount(); i++)
    {
      Range range = rangeSet.getRange(i);
      BExpression rangeExpression = null;
      if(range.getMin()!=null)
      {
        if(range.getMax()!=null)
        {
          if(range.getMinInclusive())
          {
            if(range.getMaxInclusive())
            {
              if(range.getMin().equals(range.getMax()))
              {
                // [min-min]
                rangeExpression = Predicates.eq(property, range.getMin());
              } else {
                // [min-max]
                rangeExpression = Exprs.builder(
                    Predicates.ge(property, range.getMin())).and(
                    Predicates.le(property, range.getMax())).getExpression();
              }
            } else {
              // [min-max)
              rangeExpression = Exprs.builder(
                  Predicates.ge(property, range.getMin())).and(
                  Predicates.lt(property, range.getMax())).getExpression();
            }
          } else {
            if(range.getMaxInclusive())
            {
              // (min-max]
              rangeExpression = Exprs.builder(
                  Predicates.gt(property, range.getMin())).and(
                  Predicates.le(property, range.getMax())).getExpression();
            } else {
              // (min-max)
              rangeExpression = Exprs.builder(
                  Predicates.gt(property, range.getMin())).and(
                  Predicates.lt(property, range.getMax())).getExpression();
            }
          }
        } else {
          if(range.getMinInclusive())
          {
            // [min-inf)
            rangeExpression = Predicates.ge(property, range.getMin());
          } else {
            // (min-inf)
            rangeExpression = Predicates.gt(property, range.getMin());
          }
        }
      } else if(range.getMax()!=null)
      {
        if(range.getMaxInclusive())
        {
          // (inf-max]
          rangeExpression = Predicates.le(property, range.getMax());
        } else {
          // (inf-max)
          rangeExpression = Predicates.lt(property, range.getMax());
        }
      }
      if(rangeSetExpression==null)
        rangeSetExpression = rangeExpression;
      else
        rangeSetExpression = Exprs.builder(rangeSetExpression).or(rangeExpression).getExpression();
    }
    return rangeSetExpression;
  }
  
///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private BOrionAlarmDatabase db;  
  private OrionSession session;
  
}
