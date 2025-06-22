/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.history;

import static com.tridium.rdb.history.RdbmsHistoryUtil.fixQuotes;
import static com.tridium.rdb.history.RdbmsHistoryUtil.isNewSchema;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.baja.driver.history.BHistoryExport;
import javax.baja.driver.util.BDescriptorState;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.BTrendRecord;
import javax.baja.history.HistoryNotFoundException;
import javax.baja.history.HistoryQuery;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.rdb.BRdbms;
import javax.baja.rdb.BRdbmsTimestampStorage;
import javax.baja.security.BPassword;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BLong;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.nre.util.tuple.Pair;
import com.tridium.rdb.BRdbmsDeprecatedDialect;
import com.tridium.rdb.history.RdbmsHistoryUtil;
import com.tridium.rdb.jdbc.RdbmsDialect;

//NOTE: Re-slotting this code will result in a redundant BBoolean cast of 'exportInvalidValues' as defined by
//      NCCB-38318. Please be sure to manually correct the cast prior to commitment to avoid a compile warning.

/**
 * BRdbmsHistoryExport defines an archive action for transferring
 * one or more histories from the local source to a relational database.
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 28$ $Date: 1/12/11 9:20:53 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The user name that is used to login to the database.
 If defined, this will be used instead of the loginName
 that is defined on the relational database.
 */
@NiagaraProperty(
  name = "userName",
  type = "String",
  defaultValue = ""
)
/*
 The password that is used to login to the database.
 If defined, this will be used instead of the loginPassword
 that is defined on the relational database.
 */
@NiagaraProperty(
  name = "password",
  type = "BPassword",
  defaultValue = "BPassword.DEFAULT"
)
/*
 Represents the maximum timestamp value of any exported record.
 If this value is null, then all the history records will be exported.
 Otherwise, only those history records with a timestamp newer than this
 value will be exported.
 After a successful export, this value is set to the maximum
 timestamp value of any of the records that were exported.
 */
@NiagaraProperty(
  name = "lastTimestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 A flag that allows skipping of nan and +/- inf values from being exported
 in most cases if a infinite value is exported the database will store the max or min possible value
 if nan is exported it will go into the database as a null
 */
@NiagaraProperty(
  name = "exportInvalidValues",
  type = "boolean",
  defaultValue = "true"
)
public abstract class BRdbmsHistoryExport
  extends BHistoryExport
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.history.BRdbmsHistoryExport(1090094232)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "userName"

  /**
   * Slot for the {@code userName} property.
   * The user name that is used to login to the database.
   * If defined, this will be used instead of the loginName
   * that is defined on the relational database.
   * @see #getUserName
   * @see #setUserName
   */
  public static final Property userName = newProperty(0, "", null);

  /**
   * Get the {@code userName} property.
   * The user name that is used to login to the database.
   * If defined, this will be used instead of the loginName
   * that is defined on the relational database.
   * @see #userName
   */
  public String getUserName() { return getString(userName); }

  /**
   * Set the {@code userName} property.
   * The user name that is used to login to the database.
   * If defined, this will be used instead of the loginName
   * that is defined on the relational database.
   * @see #userName
   */
  public void setUserName(String v) { setString(userName, v, null); }

  //endregion Property "userName"

  //region Property "password"

  /**
   * Slot for the {@code password} property.
   * The password that is used to login to the database.
   * If defined, this will be used instead of the loginPassword
   * that is defined on the relational database.
   * @see #getPassword
   * @see #setPassword
   */
  public static final Property password = newProperty(0, BPassword.DEFAULT, null);

  /**
   * Get the {@code password} property.
   * The password that is used to login to the database.
   * If defined, this will be used instead of the loginPassword
   * that is defined on the relational database.
   * @see #password
   */
  public BPassword getPassword() { return (BPassword)get(password); }

  /**
   * Set the {@code password} property.
   * The password that is used to login to the database.
   * If defined, this will be used instead of the loginPassword
   * that is defined on the relational database.
   * @see #password
   */
  public void setPassword(BPassword v) { set(password, v, null); }

  //endregion Property "password"

  //region Property "lastTimestamp"

  /**
   * Slot for the {@code lastTimestamp} property.
   * Represents the maximum timestamp value of any exported record.
   * If this value is null, then all the history records will be exported.
   * Otherwise, only those history records with a timestamp newer than this
   * value will be exported.
   * After a successful export, this value is set to the maximum
   * timestamp value of any of the records that were exported.
   * @see #getLastTimestamp
   * @see #setLastTimestamp
   */
  public static final Property lastTimestamp = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BAbsTime.NULL, null);

  /**
   * Get the {@code lastTimestamp} property.
   * Represents the maximum timestamp value of any exported record.
   * If this value is null, then all the history records will be exported.
   * Otherwise, only those history records with a timestamp newer than this
   * value will be exported.
   * After a successful export, this value is set to the maximum
   * timestamp value of any of the records that were exported.
   * @see #lastTimestamp
   */
  public BAbsTime getLastTimestamp() { return (BAbsTime)get(lastTimestamp); }

  /**
   * Set the {@code lastTimestamp} property.
   * Represents the maximum timestamp value of any exported record.
   * If this value is null, then all the history records will be exported.
   * Otherwise, only those history records with a timestamp newer than this
   * value will be exported.
   * After a successful export, this value is set to the maximum
   * timestamp value of any of the records that were exported.
   * @see #lastTimestamp
   */
  public void setLastTimestamp(BAbsTime v) { set(lastTimestamp, v, null); }

  //endregion Property "lastTimestamp"

  //region Property "exportInvalidValues"

  /**
   * Slot for the {@code exportInvalidValues} property.
   * A flag that allows skipping of nan and +/- inf values from being exported
   * in most cases if a infinite value is exported the database will store the max or min possible value
   * if nan is exported it will go into the database as a null
   * @see #getExportInvalidValues
   * @see #setExportInvalidValues
   */
  public static final Property exportInvalidValues = newProperty(0, true, null);

  /**
   * Get the {@code exportInvalidValues} property.
   * A flag that allows skipping of nan and +/- inf values from being exported
   * in most cases if a infinite value is exported the database will store the max or min possible value
   * if nan is exported it will go into the database as a null
   * @see #exportInvalidValues
   */
  public boolean getExportInvalidValues() { return getBoolean(exportInvalidValues); }

  /**
   * Set the {@code exportInvalidValues} property.
   * A flag that allows skipping of nan and +/- inf values from being exported
   * in most cases if a infinite value is exported the database will store the max or min possible value
   * if nan is exported it will go into the database as a null
   * @see #exportInvalidValues
   */
  public void setExportInvalidValues(boolean v) { setBoolean(exportInvalidValues, v, null); }

  //endregion Property "exportInvalidValues"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsHistoryExport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BDescriptor
////////////////////////////////////////////////////////////////

  /**
   * Export the table to the target database.
   */
  @Override
  public void doExecute()
  {
    devicelet = (BRdbmsHistoryDeviceExt) getDeviceExt();
    db = (BRdbms) devicelet.getDevice();

    if (!db.getEnabled())
    {
      logTrace("database disabled, on thread " + Thread.currentThread().getName());
      setState(BDescriptorState.idle);
      updateStatus();
      return;
    }

    logTrace("begin export on thread " + Thread.currentThread().getName());
    long t0 = System.currentTimeMillis();
    conn = null;
    boolean isClosed = false;
    executeInProgress();

    try
    {
      dialect = BRdbmsDeprecatedDialect.make(db);
      String    userName = devicelet.getUserName(db, this);
      BPassword password = devicelet.getPassword(db, this);

      // get connection to remote database
      conn = db.getConnection(userName, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();

      // get local database
      BHistoryDatabase localDb =
        ((BHistoryService)Sys.getService(BHistoryService.TYPE)).getDatabase();

      // look up info about the history
      BHistoryId hid = getHistoryId();
      try (HistorySpaceConnection conn = localDb.getConnection(null))
      {
        history = conn.getHistory(hid);
      }
      if (history == null)
        throw new HistoryNotFoundException();

      config = history.getConfig();
      template = config.makeRecord();
      
      // use a different timezone, if necessary
      if (devicelet.getUseHistoryConfigTimeZone())
      {
        // If we are using station-side timestamps, then we are OK.
        if (devicelet.getUseLastTimestamp())
        {
          dialect.setTimeZone(config.getTimeZone());
        }
        // Else if we are using database-side timestamps, then we cannot proceed.
        // That is because we cannot reliably fetch the timezone data
        // from the database.
        else
        {
          throw new BajaRuntimeException(
            "Export failed: " +
            "useHistoryConfigTimeZone=true and " +
            "useLastTimestamp=false.");
        }
      }

      // export the records
      exportRecords();

      // shut down
      stmt.close();
      conn.close();
      isClosed = true;
      executeOk();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      executeFail(e);
    }
    finally
    {
      if ((conn != null) && !isClosed)
      {
        try
        {
          conn.close();
        }
        catch(Exception e)
        {
          logError("Couldn't close connection: " + e.getMessage());
        }
      }

      long ms = System.currentTimeMillis() - t0;
      logTrace("end export (" + ms + "ms)");
    }
  }

  /**
   * postExecute
   */
  @Override
  protected IFuture postExecute(Action action, BValue arg, Context cx)
  {
    BRdbms db = (BRdbms)getDevice();
    if (db != null) db.getWorker().postAsync(new Invocation(this, action, arg, cx));

    return null;
  }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

  /**
   * exportRecords
   */
  private void exportRecords() throws Exception
  {
    String metaTableName = getMetaTableName();

    // Make sure the metaTable exists.  If it doesn't exist,
    // then apparently we have not yet done any exports at all.
    if (!dialect.tableExists(db, conn, metaTableName))
    {
      isNewSchema = true;
      logTrace("table '" + metaTableName + "' does not exist.");
      createMetaTable();
    }
    else
    {
      isNewSchema = isNewSchema(conn, metaTableName);
    }
    
    // Fetch the meta record.
    ResultSet meta = getMetaRecord();
    String tableName = null;
    
    if (meta.next())
    {
      // If the meta record exists, just get the table name.
      tableName = meta.getString("TABLE_NAME");
      
      //at this point we need to update any fields in the meta record in case the user changed them on the
      //history config. For now we'll only check for updates to 'VALUEFACETS'
      if(columnExists(metaTableName, "VALUEFACETS"))
      {
        String dbValueFacets = meta.getString("VALUEFACETS");
        BValue valuefacets = config.get("valueFacets");

        if(valuefacets != null && valuefacets instanceof BFacets)
        {
          String configValueFacets = valuefacets.asSimple().encodeToString();
          
          if(dbValueFacets != null && !dbValueFacets.equals(configValueFacets))
          {
            String updateFacetsSql = dialect.makeUpdateSql(metaTableName, 
                                                           new Property[]{config.getProperty("valueFacets")}, 
                                                           new String[0], 
                                                           dialect.mangleIdentifier(BHistoryConfig.id.getName()));
            
            try (PreparedStatement stmt = conn.prepareStatement(updateFacetsSql))
            {
              stmt.setString(1, configValueFacets); //SET
              stmt.setString(2, config.getId().toString()); //WHERE
              stmt.execute();
            }
          }
        }
      }
      meta.close();
    }
    // Otherwise, we must have never exported records for this
    // particular history (or history type) yet.
    // In that case, create a meta record. We may also
    // need to create a table.
    else
    {
      meta.close();
      insertMetaRecord();

      meta = getMetaRecord();
      if (!meta.next())
        throw new BajaRuntimeException(
          "Could not create metadata record for history " +
          config.getId().getDeviceName() + " ::: " +
          config.getId().getHistoryName());

      tableName = meta.getString("TABLE_NAME");
      int tableId = meta.getInt("ID");
      meta.close();

      // If we are exporting by history id, we will always
      // need to make a table.  If we are exporting by history
      // type, the table may already exist. Its simplest to
      // just check if the table exists, and create it if it doesn't.
      if (!dialect.tableExists(db, conn, tableName))
      {
        logTrace("table '" + tableName + "' does not exist.");
        createTable(tableName, tableId);
      }
    }

    // insert the records
    BAbsTime oldStamp = getLastTimestamp();
    BAbsTime newStamp = insertRecords(oldStamp, tableName);
    setLastTimestamp(newStamp);
  }

  /**
   * createTable
   */
  private void createTable(String tableName, int tableId) throws SQLException
  {
    // get the template
    Property[] propTemplate = template.getPropertiesArray();

    // set up extra fields
    Array<String> extraFields = new Array<>(String.class);
    Array<Type> extraFieldTypes = new Array<>(Type.class);
    Array<BFacets> extraFacets = new Array<>(BFacets.class);

    //add export mode-specific columns
    if (db.getExportMode().getOrdinal() == BRdbmsHistoryExportMode.BY_HISTORY_TYPE)
    {
      extraFields.add("HISTORY_ID");
      extraFieldTypes.add(BString.TYPE);
      extraFacets.add(BFacets.NULL);
    }

    if (template instanceof BTrendRecord)
    {
      //add extra columns common to all trend records
      extraFields.addAll(new String[]{ "TRENDFLAGS_TAG", "STATUS_TAG" });
      extraFieldTypes.addAll(new Type[]{ BString.TYPE, BString.TYPE });
      extraFacets.addAll(new BFacets[]{ BFacets.NULL, BFacets.NULL });
    }
    
    /*Commented out: UTC_OFFSET column not being used
    //add UTC_OFFSET column if the database supports SQL TIMESTAMPS
    if(dialect.supportsTimestamp())
    {
      extraFields.add("UTC_OFFSET");
      extraFieldTypes.add(BString.TYPE);
      extraFacets.add(BFacets.NULL);
    }*/
    
    
    // make the table
    String sql = dialect.makeCreateTableSql(
      tableName,
      propTemplate,
      extraFields.trim(),
      extraFieldTypes.trim(),
      extraFacets.trim());
    
    stmt.executeUpdate(sql);

    // make the sequence if need be
    if (dialect.hasSequences())
    {
      stmt.executeUpdate("CREATE SEQUENCE " + tableName + "_Q");
    }

    if (!devicelet.getUseLastTimestamp() || devicelet.getAlwaysCreateIndexForNewTables())
    {
      Pair<String, String> indexNameAndColumns = dialect.getNewIndexNameAndColumns(String.valueOf(tableId));
      RdbmsHistoryUtil.createIndex(conn, dialect, tableName, indexNameAndColumns);
    }

    conn.commit();
    logTrace("created table '" + tableName + "'.");
  }

  private boolean columnExists(String tableName, String columnName) throws SQLException
  {
    DatabaseMetaData metadata = conn.getMetaData();
    try (ResultSet rs = metadata.getColumns(null, null, tableName, columnName))
    {
      return rs.next();
    }
  }
  
  /**
   * insertRecords
   */
  private BAbsTime insertRecords(BAbsTime lastTime, String tableName)
  throws SQLException
  {    
    // get the history data that we care about
    BAbsTime since = lookupMaxTimestamp(tableName);

    if (since != null)
    {
      // use the timestamp accuray to increment the value,
      // to make sure we don't reinsert anything.
      since = BAbsTime.make(since.getMillis() + dialect.getTimestampAccuracy());
      logTrace("maxTimestamp " + since.toString(SHOW_MILLIS));
    }
    
    BHistoryDatabase localDb =
        ((BHistoryService)Sys.getService(BHistoryService.TYPE)).getDatabase();
    // For Rdbms History Exports, we are always excluding archive history data
    // and only considering local history data via the Context used to retrieve
    // the HistorySpaceConnection
    try (HistorySpaceConnection historyConn =
           localDb.getConnection(HistoryQuery.makeExcludeArchiveDataContext(null));
         Cursor<BHistoryRecord> records = historyConn.timeQuery(history, since, null).cursor())
    {
      // set up extra values
      Array<String> extraFields = new Array<String>(String.class);
      BObject[] extraValues;
      BFacets[] extraFacets;

      if (db.getExportMode().getOrdinal() == BRdbmsHistoryExportMode.BY_HISTORY_TYPE)
      {
        extraFields.add("HISTORY_ID");
      }
      if (template instanceof BTrendRecord)
      {
        extraFields.addAll(new String[]{ "TRENDFLAGS_TAG", "STATUS_TAG" });
      }

      //check if the UTC_OFFSET column exists (to maintain backwards compatibility with old databases)
      //Note: disable adding the UTC_OFFSET column for now
      //because the presence of this extra column may complicate
      //sql scheme queries involving timestamps
      // TODO: Need to confirm if the "UTC_OFFSET" column addition from
      //  NCCB-2334 ever got released to customers in 3.8 before it was reverted.
      //  If it was never released, this column existence check is probably not
      //  necessary and might make exports run just a little faster.

      boolean addUtcOffsetEntry = false && dialect.supportsTimestamp() && columnExists(tableName, "UTC_OFFSET");
   
      //check if the UTC_OFFSET column exists and add a field for that if it does
      if(addUtcOffsetEntry)
        extraFields.add("UTC_OFFSET");
      
      int extraFieldsSize = extraFields.size();
      extraValues = new BObject[extraFieldsSize];
      extraFacets = new BFacets[extraFieldsSize];
      
      Property[] propTemplate = template.getPropertiesArray();
      // insert
      String sql = dialect.makeInsertSql(tableName, propTemplate, extraFields.trim());
      int totalCount = 0;
      int count = 0;

      try (PreparedStatement ps = conn.prepareStatement(sql))
      {
        while (records.next())
        {
          BHistoryRecord rec = records.get();
          BAbsTime ts = rec.getTimestamp();

          if (lastTime.compareTo(ts) < 0)
            lastTime = ts;


          switch (db.getExportMode().getOrdinal())
          {
            // byHistory
            case BRdbmsHistoryExportMode.BY_HISTORY_ID:
              if (template instanceof BTrendRecord)
              {
                BTrendRecord t = (BTrendRecord) rec;
                extraValues[0] = BString.make(t.getTrendFlags().toString());
                extraValues[1] = BString.make(t.getStatus().toString());
              }
              break;

            // byType
            case BRdbmsHistoryExportMode.BY_HISTORY_TYPE:
              if (extraValues.length > 0)
                extraValues[0] = BString.make(history.getId().toString());

              if (template instanceof BTrendRecord)
              {
                BTrendRecord t = (BTrendRecord) rec;
                extraValues[1] = BString.make(t.getTrendFlags().toString());
                extraValues[2] = BString.make(t.getStatus().toString());
              }
              break;

            // oops
            default:
              throw new IllegalStateException();
          }

          //Add UTC millis offset for all records (if the column exists)
          if (addUtcOffsetEntry)
          {
            //calculate the long UTC offset
            RdbmsDialect context = (RdbmsDialect) db.getRdbmsContext();
            BTimeZone timezone = context
              .useUtcTimestamps() ? BTimeZone.UTC : devicelet
              .getUseHistoryConfigTimeZone() ? config.getTimeZone() : BTimeZone
              .getLocal();
            BLong utcOffset = BLong
              .make(timezone.getJavaTimeZone().getOffset(ts.getMillis()));
            extraValues[extraValues.length - 1] = utcOffset; //this assumes the UTC_OFFSET column is the last column in the extra fields
          }
          if (getExportInvalidValues() || !hasInvalidValues(rec, propTemplate, extraValues))
          {
            dialect.insertRecord(ps, rec, propTemplate, extraValues, extraFacets);
            count++;
            totalCount++;
          }

          if (count >= EXPORT_BATCH_SIZE)
          {
            logTrace("sending batch at " + totalCount + " records");
            ps.executeBatch();
            ps.clearBatch();
            count = 0;
          }
        }

        if (count > 0)
        {
          ps.executeBatch();
        }
      }
      conn.commit();
      logTrace("inserted " + totalCount + " records");
    }
    
    return lastTime;
  }

  private boolean hasInvalidValues(BHistoryRecord rec, Property[] propTemplate, BObject[] extraValues)
  {

    for(Property p: propTemplate)
    {
      if (rec.get(p).getType().is(BDouble.TYPE) &&
        !Double.isFinite(((BDouble)rec.get(p)).getDouble()))
      {
        return true;
      }
      if (rec.get(p).getType().is(BFloat.TYPE) &&
        !Float.isFinite(((BFloat)rec.get(p)).getFloat()))
      {
        return true;
      }
    }
    for (BObject b: extraValues)
    {
      if (b.getType().is(BDouble.TYPE) &&
        !Double.isFinite(((BDouble)b.asValue()).getDouble()))
      {
        return true;
      }
      if (b.getType().is(BFloat.TYPE) &&
        !Float.isFinite(((BFloat)b.asValue()).getFloat()))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * we can assume there will always be a timestamp field.
   */
  private BAbsTime lookupMaxTimestamp(String tableName) throws SQLException
  {
    // use lastExportTime
    if (devicelet.getUseLastTimestamp() /* && !db.getUseUtcTimestamps()*/)
    {
      BAbsTime time = getLastTimestamp();

      // if the timestamp is already set, then return it
      if (!time.equals(BAbsTime.NULL))
        return time;

      // everything is fine -- this must be the first export
    }
    
    // query the database
    else
    {
      String sql = "SELECT MAX(TIMESTAMP) AS MAX_TIMESTAMP FROM " + tableName;

      switch (db.getExportMode().getOrdinal())
      {
        // byHistory
        case BRdbmsHistoryExportMode.BY_HISTORY_ID:
          break;

          // byType
        case BRdbmsHistoryExportMode.BY_HISTORY_TYPE:
          sql += " WHERE HISTORY_ID = '" + fixQuotes(history.getId().toString()) + '\'';
          break;

          // oops
        default: throw new IllegalStateException();
      }
      
      /*BTimeZone dbtimezone = getExportTimeZone(); //retrieve timezone stored in DB_TIMEZONE column in meta table
      System.out.println("the db timezone = " + dbtimezone);*/
      Timestamp ts = null;
      
      try (ResultSet rs = stmt.executeQuery(sql))
      {
        if (rs.next())
        {
          if (dialect.supportsTimestamp())
          {
            Calendar cal = db.getTimestampStorage().equals(BRdbmsTimestampStorage.utcTimestamp) ? Calendar.getInstance(BTimeZone.UTC.getJavaTimeZone()) :
              devicelet.getUseHistoryConfigTimeZone() && devicelet.getUseLastTimestamp() ? Calendar.getInstance(config.getTimeZone().getJavaTimeZone()) : Calendar.getInstance();

            ts = rs.getTimestamp("MAX_TIMESTAMP", cal);
            if (ts == null)
            {
              return null;
            }
          }

          else
          {
            String str = rs.getString("MAX_TIMESTAMP");
            if (str == null)
            {
              return null;
            }

            ts = new Timestamp(Long.parseLong(str));
          }

          long millis = ts.getTime(); //could be UTC millis, station time millis or history config millis
          //based on what timezone was used to export the record to the database
          return BAbsTime.make(millis);
        }
      }
    }
    return null;
  }
  
  private BTimeZone getExportTimeZone() throws SQLException
  {
    ResultSet rs = getMetaRecord();
    BTimeZone timezone = BTimeZone.NULL;
    
    //if we're using the new schema then the stored timezone information is available
    //from the DB_TIMEZONE column for the meta record
    if(rs.next() && isNewSchema)
    {
      try
      {
        String timeZoneId = rs.getString("DB_TIMEZONE");
        timeZoneId = timeZoneId.substring(0, timeZoneId.indexOf("(")).trim();
        timezone = BTimeZone.getTimeZone(timeZoneId);
      }
      
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }
    
    //else check if the records were exported with the 'use history config timezone' option
    //enabled. Use that timezone if this is the case
    else if (devicelet.getUseHistoryConfigTimeZone() && devicelet.getUseLastTimestamp())
      timezone = config.getTimeZone();
    
    //else default to the local station timezone
    else timezone = BTimeZone.getLocal();
    
    rs.close();
    
    return timezone;
  }

////////////////////////////////////////////////////////////////
// private - meta data
////////////////////////////////////////////////////////////////

  /**
   * getMetaTableName
   */
  private String getMetaTableName()
  {
    switch (db.getExportMode().getOrdinal())
    {
      case BRdbmsHistoryExportMode.BY_HISTORY_ID: return "HISTORY_CONFIG";
      case BRdbmsHistoryExportMode.BY_HISTORY_TYPE:    return "HISTORY_TYPE_MAP";
      default: throw new IllegalStateException();
    }
  }

  /**
   * createMetaTable
   */
  private void createMetaTable()
  throws SQLException
  {
    String name = getMetaTableName();
    
    Property[] metaTemplate = makeMetaTemplate();
    
    String sql = dialect.makeCreateTableSql(
                                            name,
                                            metaTemplate,
                                            new String[] { "TABLE_NAME", "DB_TIMEZONE" },
                                            new Type[]   { BString.TYPE, BString.TYPE }, 
                                            new BFacets[]{ BFacets.NULL, BFacets.NULL }); 
                                          
    stmt.executeUpdate(sql);
    
    // make the sequence if need be
    if (dialect.hasSequences())
      stmt.executeUpdate("CREATE SEQUENCE " + name + "_Q");

    conn.commit();
    logTrace("created table '" + name +"'.");
  }

  /**
   * insertMetaRecord
   */
  private void insertMetaRecord()
  throws SQLException
  {
    
    try
    {
      Property[] template = makeMetaTemplate();
 
      try (PreparedStatement ps = conn.prepareStatement(
             dialect.makeInsertSql(
               getMetaTableName(),
               template,
               isNewSchema ?  new String[] { "TABLE_NAME", "DB_TIMEZONE" } :  
                              new String[] { "TABLE_NAME" } )))
      {
        // invent a table name
        String tableName = inventTableName();                       
        BString tableNameStr = BString.make(tableName);
        
        
        //initialize DB_TIMEZONE
        RdbmsDialect context = (RdbmsDialect)db.getRdbmsContext();
        BTimeZone dbTimeZone = context.useUtcTimestamps() ? BTimeZone.UTC : 
                               devicelet.getUseHistoryConfigTimeZone() ? 
                               config.getTimeZone(): BTimeZone.getLocal();
      
        BString timeZoneStr  = BString.make(dbTimeZone.toString());
        
        BObject[] tablePlusTz = {tableNameStr, timeZoneStr};
        BObject[] table = {tableNameStr};
      
        // insert the record
        dialect.insertRecord(
          ps,
          config,
          template,
          isNewSchema ? tablePlusTz : table,
          isNewSchema ? new BFacets[] { BFacets.NULL, BFacets.NULL } : new BFacets[] { BFacets.NULL });
  
        ps.executeBatch();
      }

      conn.commit();
    }
    catch (Exception e)
    {
      throw new BajaRuntimeException(e);
    }
  }

  /**
   * getMetaRecord
   */
  private ResultSet getMetaRecord()
  throws SQLException
  {
    //check the existence of the meta record by history id from the relevant
    //meta table
    
    String sql = null;
    String metaTableName = getMetaTableName();
    boolean exportingById = db.getExportMode().equals(BRdbmsHistoryExportMode.byHistoryId);
    
    //select on the ID_ column if this is the HISTORY_CONFIG table or if the table
    //is using the new schema
    if(exportingById || isNewSchema)
    {
      sql = "SELECT * FROM " + metaTableName +  
        " WHERE ID_ = '" + fixQuotes(config.getId().toString()) + "'";
    }
    
    else
    {
      //no schema upgrade has been performed and this isn't the HISTORY_CONFIG table
      //so this must be the HISTORY_TYPE_MAP table with the old schema which only
      //contains information about the history record type
      sql = "SELECT * FROM " + metaTableName +  
          " WHERE RECORDTYPE = '" + fixQuotes(config.getRecordType().toString()) + "'";
    }
    
    return stmt.executeQuery(sql);
  }

  /**
   * inventTableName
   */
  private String inventTableName()
  throws SQLException
  {
    int max = dialect.getMaxTableName();

    // kludge: if we have sequences, then the table name can be at
    // most two less than the max sequence name, because we create
    // the sequence name from the tableName plus "_Q".
    if (dialect.hasSequences())
    {
      int seq = dialect.getMaxSequenceName();
      if (max > (seq - 2)) max = seq-2;
    }

    // make up a name
    String tableName;
    switch (db.getExportMode().getOrdinal())
    {
      // byHistory
      case BRdbmsHistoryExportMode.BY_HISTORY_ID:
        
         //NCCB-4553 - Unescape names before calls to toUpperCase()
        String devName  = SlotPath.unescape(config.getId().getDeviceName()).toUpperCase();
        String histName = SlotPath.unescape(config.getId().getHistoryName()).toUpperCase();

        tableName = TextUtil.truncate(
          dialect.mangleIdentifier(devName) + "_" +
          dialect.mangleIdentifier(histName),
          max);
        break;

      // byType
      case BRdbmsHistoryExportMode.BY_HISTORY_TYPE:
        tableName = TextUtil.truncate(
          dialect.mangleIdentifier(config.getRecordType().toString()),
          max);
        break;

      // oops
      default: throw new IllegalStateException();
    }

    // make sure its really unique (but only for HISTORY_CONFIG table)
    if (db.getExportMode().equals(BRdbmsHistoryExportMode.byHistoryId))
    {
      /*
       * In case of export by History_Id, tables are created with the generated table name here.
       * If the generated table name is longer than the object character limit of the database in use
       * the table name is trimmed to fit within the max table name limit. This may result in duplicate names.
       * To avoid duplicate names, a counter is used here which is appended to the trimmed table name. In case the newly
       * generated name is already present, increment the counter and check again.
       */
      if(metaRecordExists(tableName))
      {
        String fullName = tableName;

        int n = 1;
        do {
          String nn = Integer.toString(n++);
          tableName = TextUtil.truncate(fullName, max - nn.length()) + nn;
        } while (metaRecordExists(tableName));
      }
    }

    return tableName;
  }

  /**
   * metaRecordExists
   */
  private boolean metaRecordExists(String tableName)
  throws SQLException
  {
    ResultSet rs = stmt.executeQuery(
      "SELECT * FROM " + getMetaTableName() + " WHERE " +
      "TABLE_NAME = '" + tableName + "'");

    boolean hasNext = rs.next();
    rs.close();
    return hasNext;
  }

  /**
   * makeMetaTemplate
   *
   * @throws SQLException
   */
  private Property[] makeMetaTemplate() throws SQLException
  {
    //add a valueFacets slot to the history config if it doesn't contain one
    //this will usually apply to the log and audit histories, trend records already have the property
    if (config.getProperty("valueFacets") == null)
    { config.add("valueFacets", BFacets.NULL); }

    Array<Property> arr = new Array<>(Property.class);
    SlotCursor<Property> historyConfigProperties = config.getProperties();

    boolean exportingByType= db.getExportMode()
      .equals(BRdbmsHistoryExportMode.byHistoryType);

    while (historyConfigProperties.next())
    {
      Property property = historyConfigProperties.property();
      String name = property.getName();

      if (exportingByType)
      {
        //properties for export by type HISTORY_TYPE_MAP table
        if (isNewSchema)
        {
          if (name.equals(BHistoryConfig.id.getName()) ||
            name.equals(BHistoryConfig.timeZone.getName()) ||
            name.equals(BHistoryConfig.recordType.getName()))
          {
            arr.add(property);
            continue;
          }
        }
        else if (name.equals(BHistoryConfig.recordType.getName()))
        {
          arr.add(property);
          continue;
        }
      }
      else //exporting by id
      {
        //properties for export by id HISTORY_CONFIG table
        if (name.equals(BHistoryConfig.id.getName()) ||
          name.equals(BHistoryConfig.historyName.getName()) ||
          name.equals(BHistoryConfig.source.getName()) ||
          name.equals(BHistoryConfig.sourceHandle.getName()) ||
          name.equals(BHistoryConfig.timeZone.getName()) ||
          name.equals(BHistoryConfig.interval.getName()) ||
          name.equals(BHistoryConfig.systemTags.getName()))
        {
          arr.add(property);
          continue;
        }
      }

      //add the 'valueFacets' dynamic property
      if (property.isDynamic())
      {
        if (!name.equals("valueFacets"))
        {
          continue; //skip all dynamic properties except 'valueFacets'
        }

        String metaTableName = getMetaTableName();
        String facetsColumn = dialect.mangleIdentifier(name);

        //if the meta table doesn't exist add the property to the list of columns to create (during createMetaTable())
        //or if the meta table exists and the facets column is present add the property to the list of columns to populate during an insert (during insertMetaRecord())
        if (!dialect.tableExists(db, conn, metaTableName) || columnExists(metaTableName, facetsColumn))
        { arr.add(property); }

      }
    }

    return arr.trim();
  }

  /**
   * logTrace
   */
  private void logTrace(String str)
  {
    LOG.fine("HistoryExport " + getHistoryId() + " " + str);
  }

  /**
   * logError
   */
  private void logError(String str)
  {
    LOG.severe("HistoryExport " + getHistoryId() + " " + str);
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  private BRdbmsHistoryDeviceExt devicelet;
  private BRdbms db;
  private BRdbmsDeprecatedDialect dialect;
  private Connection conn;
  private Statement stmt;

  private BIHistory history;
  private BHistoryConfig config;
  private BHistoryRecord template;

  private static final Logger LOG = Logger.getLogger("rdb");

  private static final BFacets SHOW_MILLIS = BFacets.make(
    BFacets.SHOW_MILLISECONDS, true);
  
  private boolean isNewSchema;

  /*
   * Initialize batch size for history export from the System property if set.
   * If invalid/no property is provided 1000 will be used as a default value.
   */
  private static final int DEFAULT_EXPORT_BATCH_SIZE = 1000;
  private static final Integer EXPORT_BATCH_SIZE =
    AccessController.doPrivileged(
      (PrivilegedAction<Integer>) () -> Integer
        .getInteger("niagara.rdb.historyExport.batchSize",
          DEFAULT_EXPORT_BATCH_SIZE));
}
