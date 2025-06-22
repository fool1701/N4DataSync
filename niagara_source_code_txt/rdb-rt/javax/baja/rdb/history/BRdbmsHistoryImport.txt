/*
 * Copyright 2006, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.history;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;

import javax.baja.driver.history.BHistoryImport;
import javax.baja.history.BBooleanTrendRecord;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryRecord;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.BNumericTrendRecord;
import javax.baja.history.BStringTrendRecord;
import javax.baja.history.HistoryNotFoundException;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.rdb.BRdbms;
import javax.baja.rdb.RdbmsContext;
import javax.baja.space.BComponentSpace;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;
import javax.baja.util.BTypeSpec;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;
import javax.baja.util.Lexicon;

import com.tridium.rdb.BRdbmsDeprecatedDialect;
import com.tridium.rdb.BRdbmsOptionalColumnSelection;
import com.tridium.rdb.jdbc.RdbmsDialect;
import com.tridium.rdb.sql.SqlParser;

/**
 * BRdbmsHistoryImport defines an archive action for transferring
 * a history from the relational database to the local source.
 *
 * @author    Scott Hoye
 * @creation  06 Apr 06
 * @version   $Revision: 6$ $Date: 3/25/09 1:38:49 PM EDT$
 * @since     Baja 3.1
 */
@NiagaraType
/*
 The name of the rdb table to import
 */
@NiagaraProperty(
  name = "rdbTableName",
  type = "String",
  defaultValue = ""
)
/*
 The name of the rdb catalog where the table to import exists
 */
@NiagaraProperty(
  name = "rdbCatalogName",
  type = "String",
  defaultValue = ""
)
/*
 The name of the rdb schema where the table to import exists
 */
@NiagaraProperty(
  name = "rdbSchemaName",
  type = "String",
  defaultValue = ""
)
/*
 The rdb table column to map to a history's timestamp column
 */
@NiagaraProperty(
  name = "timestampColumn",
  type = "BRdbmsColumnSelection",
  defaultValue = "new BRdbmsColumnSelection()"
)
/*
 The rdb table column to map to a history's value column
 */
@NiagaraProperty(
  name = "valueColumn",
  type = "BRdbmsColumnSelection",
  defaultValue = "new BRdbmsColumnSelection()"
)
/*
 The rdb table column to map to a history's status column
 */
@NiagaraProperty(
  name = "statusColumn",
  type = "BRdbmsColumnSelection",
  defaultValue = "new BRdbmsOptionalColumnSelection()"
)
/*
 An optional SQL query predicate that the user might want to narrow
 the import query.  Default (blank) is no additional predicate.
 */
@NiagaraProperty(
  name = "queryPredicate",
  type = "String",
  defaultValue = ""
)
/*
 If true, each execute will query for the full table (clears/recreates history)
 If false, each execute will only query for new data since the last recorded
 timestamp (appends new records to existing history)
 */
@NiagaraProperty(
  name = "fullImportOnExecute",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("BFacets.makeBoolean(lex.getText(\"import.fullImport.enabled\"), lex.getText(\"import.fullImport.disabled\"))")
)
public class BRdbmsHistoryImport
  extends BHistoryImport
{
  private static Lexicon lex = Lexicon.make("driver");

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.history.BRdbmsHistoryImport(3022388698)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "rdbTableName"

  /**
   * Slot for the {@code rdbTableName} property.
   * The name of the rdb table to import
   * @see #getRdbTableName
   * @see #setRdbTableName
   */
  public static final Property rdbTableName = newProperty(0, "", null);

  /**
   * Get the {@code rdbTableName} property.
   * The name of the rdb table to import
   * @see #rdbTableName
   */
  public String getRdbTableName() { return getString(rdbTableName); }

  /**
   * Set the {@code rdbTableName} property.
   * The name of the rdb table to import
   * @see #rdbTableName
   */
  public void setRdbTableName(String v) { setString(rdbTableName, v, null); }

  //endregion Property "rdbTableName"

  //region Property "rdbCatalogName"

  /**
   * Slot for the {@code rdbCatalogName} property.
   * The name of the rdb catalog where the table to import exists
   * @see #getRdbCatalogName
   * @see #setRdbCatalogName
   */
  public static final Property rdbCatalogName = newProperty(0, "", null);

  /**
   * Get the {@code rdbCatalogName} property.
   * The name of the rdb catalog where the table to import exists
   * @see #rdbCatalogName
   */
  public String getRdbCatalogName() { return getString(rdbCatalogName); }

  /**
   * Set the {@code rdbCatalogName} property.
   * The name of the rdb catalog where the table to import exists
   * @see #rdbCatalogName
   */
  public void setRdbCatalogName(String v) { setString(rdbCatalogName, v, null); }

  //endregion Property "rdbCatalogName"

  //region Property "rdbSchemaName"

  /**
   * Slot for the {@code rdbSchemaName} property.
   * The name of the rdb schema where the table to import exists
   * @see #getRdbSchemaName
   * @see #setRdbSchemaName
   */
  public static final Property rdbSchemaName = newProperty(0, "", null);

  /**
   * Get the {@code rdbSchemaName} property.
   * The name of the rdb schema where the table to import exists
   * @see #rdbSchemaName
   */
  public String getRdbSchemaName() { return getString(rdbSchemaName); }

  /**
   * Set the {@code rdbSchemaName} property.
   * The name of the rdb schema where the table to import exists
   * @see #rdbSchemaName
   */
  public void setRdbSchemaName(String v) { setString(rdbSchemaName, v, null); }

  //endregion Property "rdbSchemaName"

  //region Property "timestampColumn"

  /**
   * Slot for the {@code timestampColumn} property.
   * The rdb table column to map to a history's timestamp column
   * @see #getTimestampColumn
   * @see #setTimestampColumn
   */
  public static final Property timestampColumn = newProperty(0, new BRdbmsColumnSelection(), null);

  /**
   * Get the {@code timestampColumn} property.
   * The rdb table column to map to a history's timestamp column
   * @see #timestampColumn
   */
  public BRdbmsColumnSelection getTimestampColumn() { return (BRdbmsColumnSelection)get(timestampColumn); }

  /**
   * Set the {@code timestampColumn} property.
   * The rdb table column to map to a history's timestamp column
   * @see #timestampColumn
   */
  public void setTimestampColumn(BRdbmsColumnSelection v) { set(timestampColumn, v, null); }

  //endregion Property "timestampColumn"

  //region Property "valueColumn"

  /**
   * Slot for the {@code valueColumn} property.
   * The rdb table column to map to a history's value column
   * @see #getValueColumn
   * @see #setValueColumn
   */
  public static final Property valueColumn = newProperty(0, new BRdbmsColumnSelection(), null);

  /**
   * Get the {@code valueColumn} property.
   * The rdb table column to map to a history's value column
   * @see #valueColumn
   */
  public BRdbmsColumnSelection getValueColumn() { return (BRdbmsColumnSelection)get(valueColumn); }

  /**
   * Set the {@code valueColumn} property.
   * The rdb table column to map to a history's value column
   * @see #valueColumn
   */
  public void setValueColumn(BRdbmsColumnSelection v) { set(valueColumn, v, null); }

  //endregion Property "valueColumn"

  //region Property "statusColumn"

  /**
   * Slot for the {@code statusColumn} property.
   * The rdb table column to map to a history's status column
   * @see #getStatusColumn
   * @see #setStatusColumn
   */
  public static final Property statusColumn = newProperty(0, new BRdbmsOptionalColumnSelection(), null);

  /**
   * Get the {@code statusColumn} property.
   * The rdb table column to map to a history's status column
   * @see #statusColumn
   */
  public BRdbmsColumnSelection getStatusColumn() { return (BRdbmsColumnSelection)get(statusColumn); }

  /**
   * Set the {@code statusColumn} property.
   * The rdb table column to map to a history's status column
   * @see #statusColumn
   */
  public void setStatusColumn(BRdbmsColumnSelection v) { set(statusColumn, v, null); }

  //endregion Property "statusColumn"

  //region Property "queryPredicate"

  /**
   * Slot for the {@code queryPredicate} property.
   * An optional SQL query predicate that the user might want to narrow
   * the import query.  Default (blank) is no additional predicate.
   * @see #getQueryPredicate
   * @see #setQueryPredicate
   */
  public static final Property queryPredicate = newProperty(0, "", null);

  /**
   * Get the {@code queryPredicate} property.
   * An optional SQL query predicate that the user might want to narrow
   * the import query.  Default (blank) is no additional predicate.
   * @see #queryPredicate
   */
  public String getQueryPredicate() { return getString(queryPredicate); }

  /**
   * Set the {@code queryPredicate} property.
   * An optional SQL query predicate that the user might want to narrow
   * the import query.  Default (blank) is no additional predicate.
   * @see #queryPredicate
   */
  public void setQueryPredicate(String v) { setString(queryPredicate, v, null); }

  //endregion Property "queryPredicate"

  //region Property "fullImportOnExecute"

  /**
   * Slot for the {@code fullImportOnExecute} property.
   * If true, each execute will query for the full table (clears/recreates history)
   * If false, each execute will only query for new data since the last recorded
   * timestamp (appends new records to existing history)
   * @see #getFullImportOnExecute
   * @see #setFullImportOnExecute
   */
  public static final Property fullImportOnExecute = newProperty(0, false, BFacets.makeBoolean(lex.getText("import.fullImport.enabled"), lex.getText("import.fullImport.disabled")));

  /**
   * Get the {@code fullImportOnExecute} property.
   * If true, each execute will query for the full table (clears/recreates history)
   * If false, each execute will only query for new data since the last recorded
   * timestamp (appends new records to existing history)
   * @see #fullImportOnExecute
   */
  public boolean getFullImportOnExecute() { return getBoolean(fullImportOnExecute); }

  /**
   * Set the {@code fullImportOnExecute} property.
   * If true, each execute will query for the full table (clears/recreates history)
   * If false, each execute will only query for new data since the last recorded
   * timestamp (appends new records to existing history)
   * @see #fullImportOnExecute
   */
  public void setFullImportOnExecute(boolean v) { setBoolean(fullImportOnExecute, v, null); }

  //endregion Property "fullImportOnExecute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsHistoryImport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

/////////////////////////////////////////////////////////////////
// LIFECYCLE
/////////////////////////////////////////////////////////////////

  @Override
  public final void started()
    throws Exception
  {
    super.started();
    
    checkLicense();                 
    if (importingLicensed)
      importDescriptorStarted();
  }

  /**
   * The importDescriptorStarted() method is called when this descriptor's
   * running state moves to true and all license checks have been passed.
   * Components are started top-down, children after their parent.
   */
  public void importDescriptorStarted()
    throws Exception
  {
  }

/////////////////////////////////////////////////////////////////
// LICENSE CHECK
/////////////////////////////////////////////////////////////////

  private void checkLicense()
  {
    try
    {
      Feature feature = ((BRdbms)getDevice()).getLicenseFeature();
      if (feature != null) feature.check();
      importingLicensed = feature.getb("historyImport", false);

      if (importingLicensed)
      {
        // use the feature to map to a global limit pool
        // so that you can't by-pass limits by using multiple
        // network instances in the same station
        String key = feature.getVendorName() + ":" + feature.getFeatureName();
        key = TextUtil.toLowerCase(key);
        Integer importCount = importCounts.get(key);
        int count = 0;
        if (importCount != null) count = importCount.intValue();
        count++;

        importCounts.put(key, Integer.valueOf(count));

        // parse limit
        String val = feature.get("history.limit", "0");
        int limit = Integer.MAX_VALUE;
        if (val != null && !TextUtil.toLowerCase(val).equals("none"))
          limit = Integer.parseInt(val);

        if (limit == 0)
        {
          importingLicensed = false;
          licenseFailure = "Unlicensed: history.limit is zero or non-existent";
        }
        else if (count > limit)
        {
          importingLicensed = false;
          licenseFailure = "Unlicensed: Exceeded history.limit of " + limit;
        }
      }
      else
        licenseFailure = "Unlicensed: Missing or disabled historyImport license attribute";
    }
    catch(Exception e)
    {
      importingLicensed = false;
      licenseFailure = "Unlicensed: " + e;
    }

    if (!importingLicensed)
      executeFail(licenseFailure);
  }

/////////////////////////////////////////////////////////////////
// EXECUTION
/////////////////////////////////////////////////////////////////

  /**
   * Import the table from the target database.
   */
  @Override
  public final void doExecute()
  {
    if (importingLicensed)
      executeRdbImport();
    else
      executeFail(licenseFailure);
  }

  public void executeRdbImport()
  {     
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    executeInProgress();

    try
    {
      BHistoryId id = getHistoryId();
      if (id.isNull())
      {
        executeFail(lex.getText("import.error.nullId"));
        return;
      }

      // get local database
      BHistoryDatabase localDb =
        ((BHistoryService)Sys.getService(BHistoryService.TYPE)).getDatabase();

      if (localDb == null)
      {
        executeFail(lex.getText("import.error.dbNotAvailable"));
        return;
      }

      devicelet = (BRdbmsHistoryDeviceExt)getDeviceExt();
      db = (BRdbms)devicelet.getDevice();
      dialect = BRdbmsDeprecatedDialect.make(db);
      
      // get connection to remote database
      connection = db.getConnection();
      connection.setCatalog(getRdbCatalogName());

      String schemaName = getRdbSchemaName();
      String tableName = getRdbTableName();
      rdbmsContext = (RdbmsDialect) db.getRdbmsContext();
      
      String timestampCol = getColumnIdentifier(schemaName, tableName, SlotPath.unescape(getTimestampColumn().getColumn().getTag()), rdbmsContext);
      String valueCol     = getColumnIdentifier(schemaName, tableName, SlotPath.unescape(getValueColumn().getColumn().getTag()), rdbmsContext);
      String statusCol    = getColumnIdentifier(schemaName, tableName, SlotPath.unescape(getStatusColumn().getColumn().getTag()), rdbmsContext);

      tableName = getTableIdentifier(schemaName, tableName, rdbmsContext); // reuse tableName, as the full table identifier now

      //timezone preference UTC --> History Config Timezone --> Local time zone
      BTimeZone timezone = getImportTimeZone();
      Calendar importCalender = Calendar.getInstance(timezone.getJavaTimeZone());
      
      // Get the optional predicate statement and massage as needed (remove the "where" at the beginning if necessary)
      String additionalPredicate = getQueryPredicate().trim();

      if (additionalPredicate.toUpperCase().startsWith("WHERE "))
        additionalPredicate = additionalPredicate.substring(6).trim();

      else if (additionalPredicate.equalsIgnoreCase("WHERE"))
        additionalPredicate = null;
      
      boolean predicateExists = (additionalPredicate != null) && !additionalPredicate.isEmpty();
      
      //NCCB-3460: check query predicate for SQL injection
      if (predicateExists)
      {
        SqlParser parser = new SqlParser(new StringReader("SELECT X FROM Y WHERE " + additionalPredicate.toUpperCase()));
        if(parser.parse() != 0) throw new Exception("Invalid query predicate");
      }

      //determine if TIMESTAMP column has been specified
      boolean timestampColExists = true;                                         
      if (getTimestampColumn().getType().is(BRdbmsOptionalColumnSelection.TYPE))
        timestampColExists = (!(((BRdbmsOptionalColumnSelection)getTimestampColumn()).getUnspecified()));
      
      //determine if the status column has been specified
      boolean statusColExists = true;
      if (getStatusColumn().getType().is(BRdbmsOptionalColumnSelection.TYPE))
        statusColExists = (!(((BRdbmsOptionalColumnSelection)getStatusColumn()).getUnspecified()));
      
      
      //determine if the UTC_OFFSET column exists (Note: Removed requirement for UTC_OFFSET column as it conflicts with sql scheme query requirements)
      //see NCCB-2632
      //boolean utcOffsetColumnExists = false;
      //DatabaseMetaData metaData = con.getMetaData();
      //ResultSet utcColumnRs = metaData.getColumns(null, null, getRdbTableName(), "UTC_OFFSET");
      //utcOffsetColumnExists = utcColumnRs.next();
      //utcColumnRs.close();
      
      // Here's where I build up the query
      StringBuilder query = new StringBuilder();
      
      //SELECT CLAUSE
      query.append("SELECT " + valueCol);
      
      if (timestampColExists) // if there is a timestamp column, get it
      {
        query.append("," + timestampCol);
        
        //append UTC_OFFSET column if it exists
        //if(utcOffsetColumnExists) query.append(",UTC_OFFSET");  
      }
      
      if (statusColExists) // if there is a status column, get it
        query.append("," + statusCol);
      
      
      //FROM CLAUSE
      query.append(" FROM " + tableName);

      BHistoryConfig config = null;
      BTypeSpec recordType = null;
      BIHistory history = null;

      try (HistoryDatabaseConnection historyDbConn = localDb.getDbConnection(null))
      {
        if (!historyDbConn.exists(id))
        {
          //WHERE CLAUSE
          if (predicateExists)
            query.append(" WHERE " + additionalPredicate);
  
          //ORDER BY CLAUSE (this creates problems around the dst/est transition)
          //if (timestampColExists) // if there is a timestamp column, order in ascending timestamp order
          //  query.append(" ORDER BY " + timestampCol + " ASC"); // order by timestamp ascending
          
          stmt = connection.createStatement();
          String sqlQuery = query.toString();

          if (db.getLogger().isLoggable(Level.FINE))
          {
            db.getLogger().fine("Issuing the following SQL query to import data for history: " + id);
            db.getLogger().fine("    " + sqlQuery);
          }
          
          rs = stmt.executeQuery(sqlQuery);
          ResultSetMetaData rsmd = rs.getMetaData();
          recordType = getRecordType(rsmd, 1);
          
          if (timestampColExists) // if there is a timestamp column, get its type
          {
            timestampColumnType = rsmd.getColumnType(2);
            timestampColumnTypeInitialized = true;
          }
          
          if (recordType == null)
          {
            //executeFail("Could not find a matching history record type for value column " + valueCol + ".");
            Object[] args = new Object[1];
            args[0] = valueCol;
            executeFail(lex.getText("import.error.invalidRecordType", args));
            return;
          }
          
          config = makeLocalConfig(makeLocalRdbConfig(id, recordType));
          historyDbConn.createHistory(config);
          history = historyDbConn.getHistory(id);
        }
        
        else
        {
          //query the database for the history records that were exported since the timestamp
          //for the last successfull history import
          
          if (getFullImportOnExecute())
            historyDbConn.clearAllRecords(id);
  
          config = makeLocalConfig(historyDbConn.getHistory(id).getConfig());
          historyDbConn.reconfigureHistory(config);
          history = historyDbConn.getHistory(id);
          if (history == null)
            throw new HistoryNotFoundException();

          BAbsTime lastTimestamp = historyDbConn.getLastTimestamp(history);
          
          if (timestampColExists) // if there is a timestamp column, use it
          {
            if ((lastTimestamp != null) && (!getFullImportOnExecute())) // Only get new records
            {
              query.append(" WHERE (" + timestampCol + " > ?)");// + new Timestamp(lastTimestamp.getMillis()));
              if (predicateExists)
                query.append(" AND (" + additionalPredicate + ")");
            }
            
            else if (predicateExists)
              query.append(" WHERE " + additionalPredicate);
            
            //query.append(" ORDER BY " + timestampCol + " ASC"); // order by timestamp ascending
          }
          
          else if (predicateExists)
            query.append(" WHERE " + additionalPredicate);
  
          String sqlQuery = query.toString();
          
          if (db.getLogger().isLoggable(Level.FINE))
          {
            db.getLogger().fine("Issuing the following SQL query to import data for history: " + id);
            db.getLogger().fine("    " + sqlQuery);
          }

          ps = connection.prepareStatement(sqlQuery);
          
          if (timestampColExists && (lastTimestamp != null) && (!getFullImportOnExecute())) // if there is a timestamp column, get its type
          {
            //NCCB-3439: Need to check timestampColumnType initialization here to fix the situation where a BRbmsHistoryImport
            //is created and the history already exists in the db in which case the timestampColumnType
            //initialization that takes place in the 'if' part of the condition never occurs.
            if(!timestampColumnTypeInitialized)
            {
              ResultSet columnRs = connection.getMetaData().getColumns(null, null, getRdbTableName(), getTimestampColumn().getColumn().getTag());
              
              if(columnRs.next())
                timestampColumnType = columnRs.getInt("DATA_TYPE");
              
              else throw new Exception("Unable to determine timestamp column type");
            }
            
            if (timestampColumnType == Types.TIMESTAMP)  
              ps.setTimestamp(1, new Timestamp(lastTimestamp.getMillis()), importCalender);
            
            else
              ps.setLong(1, lastTimestamp.getMillis());
          }
          
          rs = ps.executeQuery();
        }

        // In case the user overrides the record type, lets get it again from the config
        recordType = config.getRecordType();
        String recTypeName = recordType.getTypeName();
        BHistoryRecord rec = config.makeRecord();
  
  //      BIHistory history = localDb.resolveHistory(id);
  //      BHistoryConfig config = history.getConfig();
  //      BHistoryRecord template = config.makeRecord();
  
  //      int duplicateCounter = 0;
        boolean firstAppend = true;
        BAbsTime duplicateTimestamp = null;
        
        /*if(timezone == null)  
          timezone = getImportTimeZone(con, getRdbTableName());
        */
        
        while (rs.next()) 
        {
          BAbsTime tstamp = null;
          
          if (timestampColExists) // if there is a timestamp column, get its type
          {
            Timestamp ts = null;
            long millis = -1;
            
            try
            {           
              ts = rs.getTimestamp(2, importCalender);
             
              if (ts == null)
                continue;
              
              millis = ts.getTime();
                  
            }
            
            catch (Exception ex)
            {
              millis = rs.getLong(2); 
              
            }
           
            //UTC readjustment (commented out since the UTC_OFFSET column requirement has been removed)
            //long utcOffsetMillis = utcOffsetColumnExists ? rs.getLong("UTC_OFFSET") : 0;
            //millis -= utcOffsetMillis; //adjust millis to UTC
            
            tstamp = BAbsTime.make(millis);
            
          }
          
          else
            tstamp = BAbsTime.now();
  
          BStatus status = BStatus.ok;
          if (statusColExists)
            status = BStatus.make(rs.getInt(getStatusColumn().getColumn().getTag()));
          
  
          if ((!firstAppend) && (rec.getTimestamp().equals(tstamp)))
          { // Fail fast until come up with better solution for issue 8497
            //BAbsTime[] args = new BAbsTime[1];
            //args[0] = tstamp;
            //executeFail(lex.getText("import.error.duplicateTimestamp", args));
            duplicateTimestamp = tstamp;
            continue;
          }
  
          if (recTypeName.equals("NumericTrendRecord"))
          {
            double val = rs.getDouble(1);
  /*          if (!firstAppend && (rec.getTimestamp().equals(tstamp)))
            {
              // First check the status
              if (status.isValid())
              {
                if (!((BNumericTrendRecord)rec).getStatus().isValid())
                {
                  // Replace with this one
                  history.update(((BNumericTrendRecord)rec).set(tstamp, val, status), null);
                }
                else // Both are valid
                {
                  duplicateCounter++;
                  switch(getDuplicateTimestampResolution().getOrdinal())
                  {
                    case BRdbmsDuplicateTimestampResolutionEnum.LAST_IN:
                      history.update(((BNumericTrendRecord)rec).set(tstamp, val, status), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.AVG:
                      double avg = (((BNumericTrendRecord)rec).getValue() + val) / duplicateCounter;
                      history.update(((BNumericTrendRecord)rec).set(tstamp, avg, BStatus.make(status.getBits() | ((BNumericTrendRecord)rec).getStatus().getBits())), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.MIN:
                      if (val < ((BNumericTrendRecord)rec).getValue())
                        history.update(((BNumericTrendRecord)rec).set(tstamp, val, status), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.MAX:
                      if (val > ((BNumericTrendRecord)rec).getValue())
                        history.update(((BNumericTrendRecord)rec).set(tstamp, val, status), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.SUM:
                      double sum = ((BNumericTrendRecord)rec).getValue() + val;
                      history.update(((BNumericTrendRecord)rec).set(tstamp, sum, BStatus.make(status.getBits() | ((BNumericTrendRecord)rec).getStatus().getBits())), null);
                      break;
                  }
                }
              }
            }
            else
            {
              duplicateCounter = 1;*/
              historyDbConn.append(history, ((BNumericTrendRecord)rec).set(tstamp, val, status));
  //          }
          }
          else if (recTypeName.equals("BooleanTrendRecord"))
          {
            boolean val = rs.getBoolean(1);
  /*          if (!firstAppend && (rec.getTimestamp().equals(tstamp)))
            {
              // First check the status
              if (status.isValid())
              {
                if (!((BBooleanTrendRecord)rec).getStatus().isValid())
                {
                  // Replace with this one
                  history.update(((BBooleanTrendRecord)rec).set(tstamp, val, status), null);
                }
                else // Both are valid
                {
                  switch(getDuplicateTimestampResolution().getOrdinal())
                  {
                    case BRdbmsDuplicateTimestampResolutionEnum.LAST_IN:
                      history.update(((BBooleanTrendRecord)rec).set(tstamp, val, status), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.AND:
                      history.update(((BBooleanTrendRecord)rec).set(tstamp, val && ((BBooleanTrendRecord)rec).getValue(),
                                                                    BStatus.make(status.getBits() | ((BBooleanTrendRecord)rec).getStatus().getBits())), null);
                      break;
                    case BRdbmsDuplicateTimestampResolutionEnum.OR:
                      history.update(((BBooleanTrendRecord)rec).set(tstamp, val || ((BBooleanTrendRecord)rec).getValue(),
                                                                    BStatus.make(status.getBits() | ((BBooleanTrendRecord)rec).getStatus().getBits())), null);
                      break;
                  }
                }
              }
            }
            else*/
              historyDbConn.append(history, ((BBooleanTrendRecord)rec).set(tstamp, val, status));
          }
          else if (recTypeName.equals("StringTrendRecord"))
          {
            String val = rs.getString(1);
  /*          if (!firstAppend && (rec.getTimestamp().equals(tstamp)))
            {
              // First check the status
              if (status.isValid())
              {
                if (!((BStringTrendRecord)rec).getStatus().isValid())
                {
                  // Replace with this one
                  history.update(((BStringTrendRecord)rec).set(tstamp, val, status), null);
                }
                else // Both are valid
                {
                  switch(getDuplicateTimestampResolution().getOrdinal())
                  {
                    case BRdbmsDuplicateTimestampResolutionEnum.LAST_IN:
                      history.update(((BStringTrendRecord)rec).set(tstamp, val, status), null);
                      break;
                  }
                }
              }
            }
            else*/
              historyDbConn.append(history, ((BStringTrendRecord)rec).set(tstamp, val, status));
          }
  
          firstAppend = false;
        }

        // All done
        if (duplicateTimestamp != null)
        {
          executeFail(lex.getText("import.error.duplicateTimestamp", new Object [] { duplicateTimestamp } ));
        }
        else
          executeOk();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      executeFail(e);
    }
    finally
    {
      // Don't forget to close all the SQL connection stuff!
      if (stmt != null)
      {
        try { stmt.close(); } catch(Exception e) {}
      }
      if (ps != null)
      {
        try { ps.close(); } catch(Exception e) {}
      }
      if (rs != null)
      {
        try { rs.close(); } catch(Exception e) {}
      }
      if (connection != null)
      {
        try { connection.close(); } catch(Exception e) {}
      }
    }
  }

  @Override
  protected final IFuture postExecute(Action action, BValue arg, Context cx)
  {
    BRdbms db = (BRdbms)getDevice();
    if (db != null) db.getWorker().postAsync(new Invocation(this, action, arg, cx));

    return null;
  }
  
  private boolean columnExists(Connection conn, String tableName, String columnName) throws SQLException
  {
    DatabaseMetaData metadata = conn.getMetaData();
    try (ResultSet rs = metadata.getColumns(null, null, tableName, columnName))
    {
      return rs.next();
    }
  }
  
  private BTimeZone getImportTimeZone() throws SQLException
  {
    /*
     *  1. get the table name to query from the import descriptor
     * 
     *  2. check the HISTORY_CONFIG table first
     *     
     *     SELECT <timezone column> FROM HISTORY_CONFIG where TABLE_NAME = <table name>
     *     If the DB_TIMEZONE column isn't present, fallback to the TIMEZONE column
     *     
     *  3. if no results then check HISTORY_TYPE_MAP table
     *  
     *     SELECT <timezone column> FROM HISTORY_TYPE_MAP where
     *     TABLE_NAME = '<table name>'
     *     If the DB_TIMEZONE column isn't present, fallback to the TIMEZONE column
     * 
     * 4. if no results then return local time zone
     */
    
    if(rdbmsContext.useUtcTimestamps()) return BTimeZone.UTC;
    
    String tableName = "'" + getRdbTableName() + "'";
    BTimeZone timezone = BTimeZone.getLocal(); //fallback option
    
    try (Statement stmt = connection.createStatement())
    {
      boolean tzFound = false;
      
      if(dialect.tableExists(db, connection, HISTORY_CONFIG_TABLE))
      {  
        try
        {
          String tzColumn = columnExists(connection, HISTORY_CONFIG_TABLE, DB_TIMEZONE_COLUMN) ? DB_TIMEZONE_COLUMN : 
                            devicelet.getUseHistoryConfigTimeZone() && columnExists(connection, HISTORY_CONFIG_TABLE, TIMEZONE_COLUMN) ? TIMEZONE_COLUMN : null; 
          
          if(tzColumn != null)
          {
            String sql = "SELECT " + tzColumn + " FROM " + HISTORY_CONFIG_TABLE + " WHERE TABLE_NAME = " + tableName;
            
            try (ResultSet results = stmt.executeQuery(sql))
            {
              if(results.next())
              {
                String tz = results.getString(tzColumn);
                tz = tz.substring(0, tz.indexOf("(")).trim();
                timezone = BTimeZone.getTimeZone(tz);
                tzFound = true;
              }
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      
      if(!tzFound)
      {
        if(dialect.tableExists(db, connection, HISTORY_TYPE_MAP_TABLE))
        {
          try
          {
            String tzColumn = columnExists(connection, HISTORY_TYPE_MAP_TABLE, DB_TIMEZONE_COLUMN) ? DB_TIMEZONE_COLUMN : 
                              devicelet.getUseHistoryConfigTimeZone() && columnExists(connection, HISTORY_TYPE_MAP_TABLE, TIMEZONE_COLUMN) ? TIMEZONE_COLUMN : null; 
            
            if(tzColumn != null)
            {
              String sql = "SELECT " + tzColumn + " FROM " + HISTORY_TYPE_MAP_TABLE + " WHERE TABLE_NAME = " + tableName;
              
              try (ResultSet results = stmt.executeQuery(sql))
              {
                if(results.next())
                {
                  String tz = results.getString(tzColumn);
                  tz = tz.substring(0, tz.indexOf("(")).trim();
                  timezone = BTimeZone.getTimeZone(tz);
                }
              }
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
      }
    }
    
    return timezone;
  }


////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  /**
   * Get the ord to use as the source for the history.
   */
  private BOrd getSourceOrd()
  {
    BComponentSpace cs = getComponentSpace();
    if (cs == null) return null;
    BOrd base = cs.getOrdInSession();
    if (base == null) return null;
    return BOrd.make(base, getSlotPathOrd());
  }

  /**
   * Return a new BHistoryConfig instance initialized appropriately
   */
  private BHistoryConfig makeLocalRdbConfig(BHistoryId id, BTypeSpec recordType)
  {
    BHistoryConfig result = new BHistoryConfig(id, recordType);

    result.setSource(BOrdList.make(getSourceOrd()));
    result.setSourceHandle(getHandleOrd());
    result.setTimeZone(BTimeZone.getLocal()); // remember to set the timezone

//    BFacets facets = BFacets.make(BFacets.TIME_ZONE, BTimeZone.getLocal());

//    BTrendRecord rec = (BTrendRecord)result.makeRecord();
//    javax.baja.sys.Property valueProp = rec.getValueProperty();
//    String facetsName = valueProp.getName() + "Facets";
//    result.add(facetsName, facets);
    // Do I need to do this?
//    BTrendRecord rec = (BTrendRecord)result.makeRecord();
//    javax.baja.sys.Property valueProp = rec.getValueProperty();
//    String facetsName = valueProp.getName() + "Facets";
//    result.add(facetsName, facets);

    return result;
  }

  /**
   * Returns the history record type based on the value column's
   * type
   */
  private static BTypeSpec getRecordType(ResultSetMetaData rsmd, int column)
    throws SQLException
  {
    if (rsmd == null) return null;
    int columnType = rsmd.getColumnType(column);
    BTypeSpec recordType = null;
    switch(columnType)
    {
      case Types.TINYINT:
      case Types.SMALLINT:
      case Types.BIGINT:
      case Types.REAL:
      case Types.FLOAT:
      case Types.INTEGER:
      case Types.DECIMAL:
      case Types.DOUBLE:
      case Types.NUMERIC:
        recordType = BNumericTrendRecord.TYPE.getTypeSpec();
        break;

      case Types.CHAR:
      case Types.VARCHAR:
      case Types.LONGVARCHAR:
        recordType = BStringTrendRecord.TYPE.getTypeSpec();
        break;

      case Types.BIT:
      case Types.BOOLEAN:
        recordType = BBooleanTrendRecord.TYPE.getTypeSpec();
        break;
    }
    return recordType;
  }

  /**
   * Returns a String formatted to identify a column for use within a SQL query.
   * The reason for this is because the various RDBMS support slightly different
   * forms.  For example, in SQL server or Oracle, a select statement might look
   * like this:
   *
   *   SELECT tableName."columnName" FROM tableName
   *
   * while the same query in MySQL would look like this:
   *
   *   SELECT tableName.`columnName` FROM tableName
   *
   * Notice the difference between these two examples (the first has the columnName
   * surrounded by double quotes, but the second has the column name surrounded by
   * a tick `).
   *
   * The result of this method is simply the formatted column identifier.  Following
   * the examples above, the result of this method would be:
   *
   * For SQL Server/Oracle:  tableName."columnName"
   * For MySQL:              tableName.`columnName`
   *
   * Note that the schemaName is optional, if it is null or an empty string, it should
   * be disregarded.  Otherwise, it can be prepended to the result String, such as:
   *
   * For SQL Server:  schemaName.tableName."columnName"
   *
   * @since Niagara 3.5
   */
  private static String getColumnIdentifier(String schemaName, String tableName, String columnName, RdbmsContext rCx)
  {
    if (rCx instanceof RdbmsDialect) return ((RdbmsDialect)rCx).getColumnIdentifier(schemaName, tableName, columnName);

    // Default to double quotes, but will probably never get here, since all RdbmsContexts should
    // also be RdbmsDialect instances
    if ((schemaName != null) && (schemaName.length() > 0))
      return schemaName + "." + tableName + ".\"" + columnName + "\"";

    return tableName + ".\"" + columnName + "\"";
  }

  /**
   * Returns a String formatted to identify a table for use within a SQL query.
   *
   * Note that the schemaName is optional, if it is null or an empty string, it should
   * be disregarded (simply return the tableName in that case).  Otherwise, it can be
   * prepended to the result String, as appropriate for the RDBMS, such as:
   *
   * schemaName.tableName
   *
   * @since Niagara 3.5
   */
  private static String getTableIdentifier(String schemaName, String tableName, RdbmsContext rCx)
  {
    if (rCx instanceof RdbmsDialect) return ((RdbmsDialect)rCx).getTableIdentifier(schemaName, tableName);

    if ((schemaName != null) && (schemaName.length() > 0))
      return schemaName + "." + tableName;

    return tableName;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private Connection connection;
  private RdbmsDialect rdbmsContext;
  private BRdbms db;
  private BRdbmsHistoryDeviceExt devicelet;
  private BRdbmsDeprecatedDialect dialect;
  
  private int timestampColumnType = Types.TIMESTAMP;
  private boolean timestampColumnTypeInitialized = false;
  private boolean importingLicensed = false;
  private String licenseFailure = null;
  private static HashMap<String, Integer> importCounts = new HashMap<>();
  private static final TimeZone localTimeZone = BTimeZone.getLocal().getJavaTimeZone();
  private static final Calendar utcCalender = Calendar.getInstance(BTimeZone.UTC.getJavaTimeZone());
  
  private static final String HISTORY_CONFIG_TABLE = "HISTORY_CONFIG";
  private static final String HISTORY_TYPE_MAP_TABLE = "HISTORY_TYPE_MAP";
  private static final String TIMEZONE_COLUMN = "TIMEZONE";
  private static final String DB_TIMEZONE_COLUMN = "DB_TIMEZONE";
 
  
}
