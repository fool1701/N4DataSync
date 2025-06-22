/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.history;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.baja.driver.history.BHistoryDeviceExt;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryService;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.security.BPassword;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.rdb.BRdbmsDiscoverTablesJob;
import com.tridium.rdb.history.BRdbmsMigrateIndexesJob;

/**
 * BRdbmsHistoryDeviceExt maps historical data into a relational database.
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 13$ $Date: 1/12/11 9:20:53 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Whether or not to make use of the value of the maxTimestamp property on each
 BRdbmsHistoryExport object.
 If useLastTimestamp is false, then queries will be issued on the database
 to find the last export time.  In addition, database timestamp indexes will
 be created at the time that the database tables are created.
 If useLastTimestamp is true, then the value of the maxTimestamp property on each
 BRdbmsHistoryExport object will be used during the history export process
 to find the last export time.  In addition, database indexes will <i><b>NOT</b></i>
 be created at the time that the database tables are created.
 */
@NiagaraProperty(
  name = "useLastTimestamp",
  type = "boolean",
  defaultValue = "false"
)
/*
 Whether or not to use the timezone property of a history's historyConfig
 when exported records.
 If useHistoryConfigTimeZone is false, then the timezone of the supervisor
 that is actually doing the export will be used when timestamps are exported.
 If useHistoryConfigTimeZone is true, then the timezone property of the
 historyConfig object will be used when timestamps are exported.
 NOTE: if the parent database, namely MySQL, does not support timestamps
 natively, then the value of this property will be ignored.
 */
@NiagaraProperty(
  name = "useHistoryConfigTimeZone",
  type = "boolean",
  defaultValue = "false"
)
/*
 Create optimized indexes for the new history export record tables.
 @since Niagara 4.11
 */
@NiagaraProperty(
  name = "alwaysCreateIndexForNewTables",
  type = "boolean",
  defaultValue = "false"
)
/*
 This action is called programmatically to invoke the job
 for discovering the rdb tables available for import.
 */
@NiagaraAction(
  name = "submitRdbTableDiscoveryJob",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
/*
 Updates the lastTimestamp field on all the BRdbmsHistoryExport objects,
 by querying the database.
 */
@NiagaraAction(
  name = "updateLastTimestamp",
  flags = Flags.HIDDEN | Flags.CONFIRM_REQUIRED
)
/*
 Set the lastTimestamp field on all the BRdbmsHistoryExport objects
 to NULL.
 */
@NiagaraAction(
  name = "clearLastTimestamp",
  flags = Flags.HIDDEN | Flags.CONFIRM_REQUIRED
)
/*
 Create optimized index on the history exports under the device depending on the export mode.
 In case the export mode is by HistoryId, an index is created on the Timestamp column.
 In case the export mode is by HistoryType, a composite index is created on the
 HistoryId and the Timestamp column which makes it quicker to do searches over the table.
 It drops the existing index (based on just TIMESTAMP OR HISTORY_ID) if any
 before creating the new index on the table.
 @since Niagara 4.11
 */
@NiagaraAction(
  name = "migrateToOptimizedTableIndexes",
  returnType = "BOrd",
  flags = Flags.CONFIRM_REQUIRED
)
public abstract class BRdbmsHistoryDeviceExt
  extends BHistoryDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.history.BRdbmsHistoryDeviceExt(2727728523)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "useLastTimestamp"

  /**
   * Slot for the {@code useLastTimestamp} property.
   * Whether or not to make use of the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object.
   * If useLastTimestamp is false, then queries will be issued on the database
   * to find the last export time.  In addition, database timestamp indexes will
   * be created at the time that the database tables are created.
   * If useLastTimestamp is true, then the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object will be used during the history export process
   * to find the last export time.  In addition, database indexes will <i><b>NOT</b></i>
   * be created at the time that the database tables are created.
   * @see #getUseLastTimestamp
   * @see #setUseLastTimestamp
   */
  public static final Property useLastTimestamp = newProperty(0, false, null);

  /**
   * Get the {@code useLastTimestamp} property.
   * Whether or not to make use of the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object.
   * If useLastTimestamp is false, then queries will be issued on the database
   * to find the last export time.  In addition, database timestamp indexes will
   * be created at the time that the database tables are created.
   * If useLastTimestamp is true, then the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object will be used during the history export process
   * to find the last export time.  In addition, database indexes will <i><b>NOT</b></i>
   * be created at the time that the database tables are created.
   * @see #useLastTimestamp
   */
  public boolean getUseLastTimestamp() { return getBoolean(useLastTimestamp); }

  /**
   * Set the {@code useLastTimestamp} property.
   * Whether or not to make use of the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object.
   * If useLastTimestamp is false, then queries will be issued on the database
   * to find the last export time.  In addition, database timestamp indexes will
   * be created at the time that the database tables are created.
   * If useLastTimestamp is true, then the value of the maxTimestamp property on each
   * BRdbmsHistoryExport object will be used during the history export process
   * to find the last export time.  In addition, database indexes will <i><b>NOT</b></i>
   * be created at the time that the database tables are created.
   * @see #useLastTimestamp
   */
  public void setUseLastTimestamp(boolean v) { setBoolean(useLastTimestamp, v, null); }

  //endregion Property "useLastTimestamp"

  //region Property "useHistoryConfigTimeZone"

  /**
   * Slot for the {@code useHistoryConfigTimeZone} property.
   * Whether or not to use the timezone property of a history's historyConfig
   * when exported records.
   * If useHistoryConfigTimeZone is false, then the timezone of the supervisor
   * that is actually doing the export will be used when timestamps are exported.
   * If useHistoryConfigTimeZone is true, then the timezone property of the
   * historyConfig object will be used when timestamps are exported.
   * NOTE: if the parent database, namely MySQL, does not support timestamps
   * natively, then the value of this property will be ignored.
   * @see #getUseHistoryConfigTimeZone
   * @see #setUseHistoryConfigTimeZone
   */
  public static final Property useHistoryConfigTimeZone = newProperty(0, false, null);

  /**
   * Get the {@code useHistoryConfigTimeZone} property.
   * Whether or not to use the timezone property of a history's historyConfig
   * when exported records.
   * If useHistoryConfigTimeZone is false, then the timezone of the supervisor
   * that is actually doing the export will be used when timestamps are exported.
   * If useHistoryConfigTimeZone is true, then the timezone property of the
   * historyConfig object will be used when timestamps are exported.
   * NOTE: if the parent database, namely MySQL, does not support timestamps
   * natively, then the value of this property will be ignored.
   * @see #useHistoryConfigTimeZone
   */
  public boolean getUseHistoryConfigTimeZone() { return getBoolean(useHistoryConfigTimeZone); }

  /**
   * Set the {@code useHistoryConfigTimeZone} property.
   * Whether or not to use the timezone property of a history's historyConfig
   * when exported records.
   * If useHistoryConfigTimeZone is false, then the timezone of the supervisor
   * that is actually doing the export will be used when timestamps are exported.
   * If useHistoryConfigTimeZone is true, then the timezone property of the
   * historyConfig object will be used when timestamps are exported.
   * NOTE: if the parent database, namely MySQL, does not support timestamps
   * natively, then the value of this property will be ignored.
   * @see #useHistoryConfigTimeZone
   */
  public void setUseHistoryConfigTimeZone(boolean v) { setBoolean(useHistoryConfigTimeZone, v, null); }

  //endregion Property "useHistoryConfigTimeZone"

  //region Property "alwaysCreateIndexForNewTables"

  /**
   * Slot for the {@code alwaysCreateIndexForNewTables} property.
   * Create optimized indexes for the new history export record tables.
   * @since Niagara 4.11
   * @see #getAlwaysCreateIndexForNewTables
   * @see #setAlwaysCreateIndexForNewTables
   */
  public static final Property alwaysCreateIndexForNewTables = newProperty(0, false, null);

  /**
   * Get the {@code alwaysCreateIndexForNewTables} property.
   * Create optimized indexes for the new history export record tables.
   * @since Niagara 4.11
   * @see #alwaysCreateIndexForNewTables
   */
  public boolean getAlwaysCreateIndexForNewTables() { return getBoolean(alwaysCreateIndexForNewTables); }

  /**
   * Set the {@code alwaysCreateIndexForNewTables} property.
   * Create optimized indexes for the new history export record tables.
   * @since Niagara 4.11
   * @see #alwaysCreateIndexForNewTables
   */
  public void setAlwaysCreateIndexForNewTables(boolean v) { setBoolean(alwaysCreateIndexForNewTables, v, null); }

  //endregion Property "alwaysCreateIndexForNewTables"

  //region Action "submitRdbTableDiscoveryJob"

  /**
   * Slot for the {@code submitRdbTableDiscoveryJob} action.
   * This action is called programmatically to invoke the job
   * for discovering the rdb tables available for import.
   * @see #submitRdbTableDiscoveryJob()
   */
  public static final Action submitRdbTableDiscoveryJob = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code submitRdbTableDiscoveryJob} action.
   * This action is called programmatically to invoke the job
   * for discovering the rdb tables available for import.
   * @see #submitRdbTableDiscoveryJob
   */
  public BOrd submitRdbTableDiscoveryJob() { return (BOrd)invoke(submitRdbTableDiscoveryJob, null, null); }

  //endregion Action "submitRdbTableDiscoveryJob"

  //region Action "updateLastTimestamp"

  /**
   * Slot for the {@code updateLastTimestamp} action.
   * Updates the lastTimestamp field on all the BRdbmsHistoryExport objects,
   * by querying the database.
   * @see #updateLastTimestamp()
   */
  public static final Action updateLastTimestamp = newAction(Flags.HIDDEN | Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code updateLastTimestamp} action.
   * Updates the lastTimestamp field on all the BRdbmsHistoryExport objects,
   * by querying the database.
   * @see #updateLastTimestamp
   */
  public void updateLastTimestamp() { invoke(updateLastTimestamp, null, null); }

  //endregion Action "updateLastTimestamp"

  //region Action "clearLastTimestamp"

  /**
   * Slot for the {@code clearLastTimestamp} action.
   * Set the lastTimestamp field on all the BRdbmsHistoryExport objects
   * to NULL.
   * @see #clearLastTimestamp()
   */
  public static final Action clearLastTimestamp = newAction(Flags.HIDDEN | Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code clearLastTimestamp} action.
   * Set the lastTimestamp field on all the BRdbmsHistoryExport objects
   * to NULL.
   * @see #clearLastTimestamp
   */
  public void clearLastTimestamp() { invoke(clearLastTimestamp, null, null); }

  //endregion Action "clearLastTimestamp"

  //region Action "migrateToOptimizedTableIndexes"

  /**
   * Slot for the {@code migrateToOptimizedTableIndexes} action.
   * Create optimized index on the history exports under the device depending on the export mode.
   * In case the export mode is by HistoryId, an index is created on the Timestamp column.
   * In case the export mode is by HistoryType, a composite index is created on the
   * HistoryId and the Timestamp column which makes it quicker to do searches over the table.
   * It drops the existing index (based on just TIMESTAMP OR HISTORY_ID) if any
   * before creating the new index on the table.
   * @since Niagara 4.11
   * @see #migrateToOptimizedTableIndexes()
   */
  public static final Action migrateToOptimizedTableIndexes = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code migrateToOptimizedTableIndexes} action.
   * Create optimized index on the history exports under the device depending on the export mode.
   * In case the export mode is by HistoryId, an index is created on the Timestamp column.
   * In case the export mode is by HistoryType, a composite index is created on the
   * HistoryId and the Timestamp column which makes it quicker to do searches over the table.
   * It drops the existing index (based on just TIMESTAMP OR HISTORY_ID) if any
   * before creating the new index on the table.
   * @since Niagara 4.11
   * @see #migrateToOptimizedTableIndexes
   */
  public BOrd migrateToOptimizedTableIndexes() { return (BOrd)invoke(migrateToOptimizedTableIndexes, null, null); }

  //endregion Action "migrateToOptimizedTableIndexes"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsHistoryDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * Get the Type for import descriptors managed by this devicelet.
   * If null, then the devicelet does not support imports.
   *
   * @return Returns the protocol specific import descriptor type
   *   or null if this devicelet does not support history imports.
   */
  @Override
  public Type getImportDescriptorType()
  {
    return BRdbmsHistoryImport.TYPE;
  }

  /**
   * The BRdbmsHistoryDeviceExt returns true for this method by default,
   * indicating it supports the generic BArchiveFolder, and its agent
   * views can be safely applied to the generic BArchiveFolder.  If this
   * is not true for a subclass, this method should be overridden to false.
   *
   * @since Niagara 3.5
   */
  @Override
  public boolean supportsGenericArchiveFolder()
  {
    return true;
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Submits the job to discover the rdb tables available for
   * import.
   *
   * @return Returns the Ord to the RdbmsDiscoverTablesJob instance.
   */
  public BOrd doSubmitRdbTableDiscoveryJob(Context cx)
  {
    if ((getDevice().isFatalFault()) ||
        (getDevice().isDown()) ||
        (getDevice().isDisabled()))
      return null;

    return new BRdbmsDiscoverTablesJob(this).submit(cx);
  }

  /**
   * Updates the lastTimestamp field on all the BRdbmsHistoryExport objects,
   * by querying the database.
   * Note that this method always uses the username and password on the rdbms,
   * rather than the ones on each descriptor.
   */
  public void doUpdateLastTimestamp()
  {
    LOG.fine("Beginning timestamp update...");
    long t0 = System.currentTimeMillis();

    BRdbms db = (BRdbms) getDevice();
    BHistoryDatabase localDb = ((BHistoryService)
      Sys.getService(BHistoryService.TYPE)).getDatabase();

    if (db.getExportMode().getOrdinal() == BRdbmsHistoryExportMode.BY_HISTORY_ID)
    {
      throw new BajaRuntimeException(
        "updateLastTimestamp does not work when the " +
        "export mode is set to BY_HISTORY_ID.");
    }

    ///////////////////////////////////////////////////////////

    Map<String, BAbsTime> timestampMap = makeTimestampMap(db);

    BRdbmsHistoryExport[] exports = getChildren(BRdbmsHistoryExport.class);

    for (int i = 0; i < exports.length; i++)
    {
      BHistoryId id = exports[i].getHistoryId();
      BAbsTime timestamp = timestampMap.get(id.toString());

      exports[i].setLastTimestamp((timestamp == null) ?
        BAbsTime.NULL :
        timestamp);
    }

    long ms = System.currentTimeMillis() - t0;
    LOG.fine("Updated " + exports.length + " timestamps (" + ms + "ms)");
  }

  /**
   * makeTimestampMap
   */
  private Map<String, BAbsTime> makeTimestampMap(BRdbms db)
  {
    try
    {
      Map<String, BAbsTime> map = new HashMap<String, BAbsTime>();
      try (Connection con = db.getConnection(db.getUserName(), db.getPassword());
           Statement tablesStatement = con.createStatement())
      {
        ResultSet tables = tablesStatement.executeQuery("SELECT DISTINCT TABLE_NAME FROM HISTORY_TYPE_MAP");
        while (tables.next())
        {
          try (Statement maxTimestampStatement = con.createStatement())
          {
            ResultSet rs = maxTimestampStatement.executeQuery(
              "SELECT MAX(TIMESTAMP) AS MAX_TIMESTAMP, HISTORY_ID " +
                "FROM " + tables.getString("TABLE_NAME") + " GROUP BY HISTORY_ID");
            while (rs.next())
            {
              // TODO -- this won't work in MySQL!!!
              BAbsTime timestamp = BAbsTime.make(
                rs.getTimestamp("MAX_TIMESTAMP").getTime());

              map.put(rs.getString("HISTORY_ID"), timestamp);
            }
          }
        }
      }
      return map;
    }
    catch (Exception e)
    {
      throw new BajaRuntimeException(e);
    }
  }

  /**
   * Set the lastTimestamp field on all the BRdbmsHistoryExport objects
   * to NULL.
   */
  public void doClearLastTimestamp()
  {
    BRdbmsHistoryExport[] exports = getChildren(BRdbmsHistoryExport.class);

    for (int i = 0; i < exports.length; i++)
      exports[i].setLastTimestamp(BAbsTime.NULL);

    LOG.fine("Cleared " + exports.length + " timestamps.");
  }

  /**
   * Invoke the job to create the composite indexes on the history export tables
   * @param cx the context for this job submission.
   * @since Niagara 4.11
   */
  public BOrd doMigrateToOptimizedTableIndexes(Context cx) throws Exception
  {
    return new BRdbmsMigrateIndexesJob(this).submit(cx);
  }

////////////////////////////////////////////////////////////////
// db utilities
////////////////////////////////////////////////////////////////

  /**
   * getUserName
   */
  public String getUserName( BRdbms database, BRdbmsHistoryExport descriptor)
  {
    String str = descriptor.getUserName();
    if ((str != null) && (!str.equals(""))) return str;

    str = database.getUserName();
    if ((str != null) && (!str.equals(""))) return str;

    return "";
  }

  /**
   * getPassword
   */
  public BPassword getPassword(BRdbms database, BRdbmsHistoryExport descriptor)
  {
    BPassword password = descriptor.getPassword();
    if ((password != null) && (!password.equals(BPassword.DEFAULT)))
    {
      return password;
    }

    password = database.getPassword();
    if ((password != null) && (!password.equals(BPassword.DEFAULT)))
      return password;

    return BPassword.DEFAULT;
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  private static final Logger LOG = Logger.getLogger("rdb");
}
