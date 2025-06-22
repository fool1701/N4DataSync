/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.point;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.collection.Column;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Clock.Ticket;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.rdb.BResultSetTable;

/**
 * BRdbmsPointQuery is a container with a defined SQL query to be executed on a regular interval
 *
 * @author Lee Adcock
 * @creation 18 Dec 07
 * @version $Revision: 6$ $Date: 12/16/09 3:28:43 PM EST$
 * @since Baja 1.0
 */
@NiagaraType
/*
 The status of the query
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.nullStatus",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Provides a description if the query fails
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Used to manually enable and disable this query
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 How often to refresh the data from the database
 */
@NiagaraProperty(
  name = "updateFrequency",
  type = "BRelTime",
  defaultValue = "BRelTime.HOUR",
  facets = @Facet("BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0))")
)
/*
 The last time this point updated
 */
@NiagaraProperty(
  name = "lastUpdate",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY | Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE)")
)
/*
 The sql query used to retrieve data for points
 */
@NiagaraProperty(
  name = "sql",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 Name of key column
 */
@NiagaraProperty(
  name = "keyColumn1",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, \"rdb:RdbmsColumnNamePickerFE\")")
)
/*
 Name of key column (optional)
 */
@NiagaraProperty(
  name = "keyColumn2",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, \"rdb:RdbmsColumnNamePickerFE\")")
)
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC
)
public class BRdbmsPointQuery
  extends BRdbmsPointFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.point.BRdbmsPointQuery(2471180488)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of the query
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.nullStatus, null);

  /**
   * Get the {@code status} property.
   * The status of the query
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of the query
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a description if the query fails
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a description if the query fails
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a description if the query fails
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Used to manually enable and disable this query
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Used to manually enable and disable this query
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Used to manually enable and disable this query
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "updateFrequency"

  /**
   * Slot for the {@code updateFrequency} property.
   * How often to refresh the data from the database
   * @see #getUpdateFrequency
   * @see #setUpdateFrequency
   */
  public static final Property updateFrequency = newProperty(0, BRelTime.HOUR, BFacets.make(BFacets.MIN, BRelTime.makeSeconds(0)));

  /**
   * Get the {@code updateFrequency} property.
   * How often to refresh the data from the database
   * @see #updateFrequency
   */
  public BRelTime getUpdateFrequency() { return (BRelTime)get(updateFrequency); }

  /**
   * Set the {@code updateFrequency} property.
   * How often to refresh the data from the database
   * @see #updateFrequency
   */
  public void setUpdateFrequency(BRelTime v) { set(updateFrequency, v, null); }

  //endregion Property "updateFrequency"

  //region Property "lastUpdate"

  /**
   * Slot for the {@code lastUpdate} property.
   * The last time this point updated
   * @see #getLastUpdate
   * @see #setLastUpdate
   */
  public static final Property lastUpdate = newProperty(Flags.READONLY | Flags.TRANSIENT, BAbsTime.NULL, BFacets.make(BFacets.SHOW_SECONDS, BBoolean.TRUE));

  /**
   * Get the {@code lastUpdate} property.
   * The last time this point updated
   * @see #lastUpdate
   */
  public BAbsTime getLastUpdate() { return (BAbsTime)get(lastUpdate); }

  /**
   * Set the {@code lastUpdate} property.
   * The last time this point updated
   * @see #lastUpdate
   */
  public void setLastUpdate(BAbsTime v) { set(lastUpdate, v, null); }

  //endregion Property "lastUpdate"

  //region Property "sql"

  /**
   * Slot for the {@code sql} property.
   * The sql query used to retrieve data for points
   * @see #getSql
   * @see #setSql
   */
  public static final Property sql = newProperty(0, BFormat.make(""), BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code sql} property.
   * The sql query used to retrieve data for points
   * @see #sql
   */
  public BFormat getSql() { return (BFormat)get(sql); }

  /**
   * Set the {@code sql} property.
   * The sql query used to retrieve data for points
   * @see #sql
   */
  public void setSql(BFormat v) { set(sql, v, null); }

  //endregion Property "sql"

  //region Property "keyColumn1"

  /**
   * Slot for the {@code keyColumn1} property.
   * Name of key column
   * @see #getKeyColumn1
   * @see #setKeyColumn1
   */
  public static final Property keyColumn1 = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, "rdb:RdbmsColumnNamePickerFE"));

  /**
   * Get the {@code keyColumn1} property.
   * Name of key column
   * @see #keyColumn1
   */
  public String getKeyColumn1() { return getString(keyColumn1); }

  /**
   * Set the {@code keyColumn1} property.
   * Name of key column
   * @see #keyColumn1
   */
  public void setKeyColumn1(String v) { setString(keyColumn1, v, null); }

  //endregion Property "keyColumn1"

  //region Property "keyColumn2"

  /**
   * Slot for the {@code keyColumn2} property.
   * Name of key column (optional)
   * @see #getKeyColumn2
   * @see #setKeyColumn2
   */
  public static final Property keyColumn2 = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, "rdb:RdbmsColumnNamePickerFE"));

  /**
   * Get the {@code keyColumn2} property.
   * Name of key column (optional)
   * @see #keyColumn2
   */
  public String getKeyColumn2() { return getString(keyColumn2); }

  /**
   * Set the {@code keyColumn2} property.
   * Name of key column (optional)
   * @see #keyColumn2
   */
  public void setKeyColumn2(String v) { setString(keyColumn2, v, null); }

  //endregion Property "keyColumn2"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code execute} action.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsPointQuery.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BRdbmsPointQuery()
  {

  }

////////////////////////////////////////////////////////////////
// BFolder
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);

    if (!this.isRunning())
      return;

    // If the query changed, re-run it
    if (prop.equals(BRdbmsPointQuery.sql))
      execute();

    // If the updateFrequency changes, set our scheduled event with
    // the new timing values
    if (prop.equals(BRdbmsPointQuery.updateFrequency) ||
      prop.equals(BRdbmsPointQuery.enabled) ||
      prop.getName().startsWith("keyColumn")
      )
    {
      if (updateTicket != null)
      {
        updateTicket.cancel();
        updateTicket = null;
      }
      if (this.getEnabled() && this.isSubscribed())
      {
        updateTicket = Clock.schedulePeriodically(this, getUpdateFrequency(), execute, null);
        this.invoke(execute, null);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action == execute)
    {
      BRdbms rdbms = (BRdbms) this.getDevice();
      if (rdbms!=null && rdbms.isRunning())
        return rdbms.getWorker().postAsync(new Invocation(this, action, arg, cx));
    }
    return super.post(action, arg, cx);
  }

  public BResultSetTable<? extends BComponent> getData()
  {
    String sql;

    // Get Sql
    try
    {
      // Apply BFormating
      sql = getSql().format(this);

      // Remove line breaks
      sql = sql.replace('\n', ' ');
    } catch (Exception e)
    {
      this.setStatus(BStatus.fault);
      this.setFaultCause("Invalid formatting");
      getLog().log(Level.SEVERE, "Invalid formatting.", e);
      return null;
    }

    // Create Ord
    BOrd ord = BOrd.make(getDevice().getAbsoluteOrd(), "sql:" + sql);

    // Resolve ord to get data
    try
    {
      getLog().fine("Executing query: "+sql);
      return (BResultSetTable<? extends BComponent>) ord.get(getDevice(), null);
    } catch (Exception e)
    {
      this.setStatus(BStatus.fault);
      this.setFaultCause("Unable to execute query.");
      getLog().log(Level.SEVERE, "Unable to execute query.", e);
      return null;
    }
  }

  public void doExecute()
  {
    if(!this.isRunning())
      return;

    Logger log = getLog();
    log.fine("Executing query " + this.getName());

    BRdbms rdbms = (BRdbms) this.getDevice();
    BStatus status = rdbms.getStatus();
    
    if(!status.isValid()) 
    {
      if(log.isLoggable(Level.FINE))
        log.fine("Cannot execute query: database status is " + status);
      
      this.setStatus(rdbms.getStatus());
      return;
    }

    BResultSetTable<? extends BComponent> table = getData();

    // Failed
    if(table==null)
    {
      if(log.isLoggable(Level.FINE))
        log.fine("Cannot execute query: result set table is null");
      return;
    }

    // Success
    this.setStatus(rdbms.getStatus());
    this.setFaultCause(rdbms.getFaultCause());
    this.setLastUpdate(BAbsTime.now());
    int controlPoints = executeChildren(table);
//    if(controlPoints > table.size()*1.5 && table.size()>15)
//      getLog().warning("Query "+this.getName()+" returned more data than neccessary.");
    resultColumns = table.getColumns().list();

  }

////////////////////////////////////////////////////////////////
// RPC hooks
////////////////////////////////////////////////////////////////

  /**
   * RPC hook for getting a column name
   *
   * @param columnIndex
   *          A BInteger containing the column index
   * @param unused
   *          Unused
   */
  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "r"
  )
  public String getColumnName(Object columnIndex, Context unused)
  {
    if(resultColumns!=null && resultColumns.length>0)
      return resultColumns[((BInteger) columnIndex).getInt()].getName();
    else
      return null;
  }

  /**
   * RPC hook for getting the number of data set columns
   *
   * @param unused1
   *          Unused
   * @param unused2
   *          Unused
   * @return
   */
  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "r"
  )
  public int getColumnCount(Object unused1, Context unused2)
  {
    if(resultColumns!=null && resultColumns.length>0)
      return resultColumns.length;
    else
      return 0;
  }


////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  @Override
  public void subscribed()
  {
    if (this.getEnabled())
    {
      // Ensure the update ticket is scheduled
      if (updateTicket == null && this.isRunning())
      {
        if(this.getLastUpdate().add(getUpdateFrequency()).isBefore(BAbsTime.now()))
          invoke(execute, null);
        updateTicket = Clock.schedulePeriodically(this, getUpdateFrequency(), execute, null);
      }
    } else
    {
      // Disabled, so cancel the ticket (if one exists)
      if (updateTicket != null)
      {
        updateTicket.cancel();
        updateTicket = null;
      }
    }
  }

  @Override
  public void unsubscribed()
  {
    if (updateTicket != null)
    {
      updateTicket.cancel();
      updateTicket = null;
    }
    super.unsubscribed();
  }

////////////////////////////////////////////////////////////////
// Logging
////////////////////////////////////////////////////////////////

  Logger getLog()
  {
    BRdbms device = (BRdbms) getDevice();
    if (device != null)
      return device.getLogger();
    else
      return null;
  }

////////////////////////////////////////////////////////////////
// Poll Group
////////////////////////////////////////////////////////////////

  /**
   * Add a proxy extension to the poll group. This query will execute as long as it is running and either it is
   * subscribed to or there are items in the poll group
   */
  public void addToPollGroup(BRdbmsProxyExt rdbmsProxyExt)
  {
    pollGroup.put(rdbmsProxyExt, rdbmsProxyExt);
    subscribed();
  }

  /**
   * Remove a proxy extension from the poll group. This query will execute as long as it is running and either it is
   * subscribed to or there are items in the poll group
   */
  public void removeFromPollGroup(BRdbmsProxyExt rdbmsProxyExt)
  {
    pollGroup.remove(rdbmsProxyExt);
    if (pollGroup.size() == 0)
      unsubscribed();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Ticket updateTicket;
  private static final BIcon icon = BIcon.std("widgets/table.png");
  private Column[] resultColumns;

  HashMap<BRdbmsProxyExt, BRdbmsProxyExt> pollGroup = new HashMap<>();

}
