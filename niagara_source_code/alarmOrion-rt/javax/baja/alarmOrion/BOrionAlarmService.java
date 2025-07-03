/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.io.IOException;
import java.util.logging.Level;

import javax.baja.alarm.BAlarmDatabase;
import javax.baja.alarm.BAlarmService;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.status.BStatus;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.alarmOrion.schema.Upgrade_1_0_to_1_1;
import com.tridium.alarmOrion.schema.Upgrade_1_2_to_1_3;
import com.tridium.orion.BIOrionApp;
import com.tridium.orion.BLocalOrionDatabase;
import com.tridium.orion.BOrionDatabase;
import com.tridium.orion.BOrionService;
import com.tridium.orion.BSchemaVersion;
import com.tridium.orion.ISchemaUpgrader;
import com.tridium.orion.OrionAppSchemaManager;
import com.tridium.orion.OrionType;

/**
 * An Orion based implementation of an alarm service.
 *
 * @author Lee Adcock
 * @creation March 18, 2009
 */
@NiagaraType
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT,
  override = true
)
/*
 The ord to the database used by this application.
 */
@NiagaraProperty(
  name = "database",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, BString.make(\"rdb:Rdbms\"))")
)
public class BOrionAlarmService
  extends BAlarmService
  implements BIOrionApp
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmService(3276460198)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  //endregion Property "faultCause"

  //region Property "database"

  /**
   * Slot for the {@code database} property.
   * The ord to the database used by this application.
   * @see #getDatabase
   * @see #setDatabase
   */
  public static final Property database = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, BString.make("rdb:Rdbms")));

  /**
   * Get the {@code database} property.
   * The ord to the database used by this application.
   * @see #database
   */
  public BOrd getDatabase() { return (BOrd)get(database); }

  /**
   * Set the {@code database} property.
   * The ord to the database used by this application.
   * @see #database
   */
  public void setDatabase(BOrd v) { set(database, v, null); }

  //endregion Property "database"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrionAlarmService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // TODO Does not correctly handle open alarms if source state is alert.

////////////////////////////////////////////////////////////////
// Alarm Service
////////////////////////////////////////////////////////////////

  @Override
  protected BAlarmDatabase createAlarmDb()
  {
    try
    {
      return new BOrionAlarmDatabase();
    }
    catch (Exception e)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.log(Level.SEVERE, "Failed to create Orion Alarm Database", e);
      }
      else
      {
        logger.log(Level.SEVERE, "Failed to create Orion Alarm Database");
      }
    }
    return null;
  }

  @Override
  public void changed(Property prop, Context cx)
  {
    if (!Sys.isStationStarted())
    {
      super.changed(prop, cx);
      return;
    }

    if (prop.equals(database))
    {
      try
      {
        // Restart the service if the ord changes
        serviceStopped();
        serviceStarted();
      }
      catch(Exception ex)
      {
        logger.log(Level.SEVERE, "Cannot change database driver to " + getDatabase() + ".", ex);
      }
    }
    /*
    (see 17750)
    else if (prop.equals(enforceCapacity))
    {
      if(getEnforceCapacity())
        ((BOrionAlarmDatabase)getAlarmDb()).setCapacity(getCapacity());
    }
    */
    else
      super.changed(prop, cx);
  }

////////////////////////////////////////////////////////////////
// Orion Service
////////////////////////////////////////////////////////////////


  @Override
  public void serviceStarted()
  {
    super.serviceStarted();

    // Validate the database configuration
    if (getDatabase().isNull())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Database ORD not configured.");
      return;
    }

    BRdbms rdbms;
    try
    {
      rdbms = (BRdbms)getDatabase().resolve(this).get();

      // Check for connection setting documented in issue 15402, we have
      // to do this in a way that doesn't create a dependency on sql server
      if(rdbms.getType().toString().equals("rdbSqlServer:SqlServerDatabase"))
      {
        String properties = rdbms.get("extraConnectionProperties").toString();
        if(!(properties.contains("prepareSQL=1") || properties.contains("prepareSQL=0")))
          BOrionAlarmDatabase.log.warning("Database connection misconfigured.  Reference issue 15402.");
      }

      // Register this application with its configured database.
      BOrionService service = (BOrionService)Sys.getService(BOrionService.TYPE);
      ((BLocalOrionDatabase)service.getOrionDatabase(rdbms)).registerApp(this);
      setStatus(BStatus.ok);
    }
    catch(UnresolvedException ex)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unable to resolve Orion database");
      logger.log(Level.SEVERE, "Unable to resolve Orion database", ex);
      super.serviceStarted();
      return;
    }
    catch(Exception ex)
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      setFaultCause("Unable to register Orion database");
      logger.log(Level.SEVERE, "Unable to register Orion database", ex);
      super.serviceStarted();
      return;
    }

    setStatus(BStatus.makeFault(getStatus(), false));
    return;
  }

  @Override
  public void orionReady(BOrionDatabase db)
  {
    try
    {
      if (!getStatus().isFault())
        getAlarmDb().open();

    } catch (IOException ioe) {
      throw new BajaRuntimeException("Unable to open alarm database", ioe);
    }
  }

  @Override
  public OrionType[] getOrionTypes()
  {
    return ORION_TYPES;
  }

  @Override
  public BSchemaVersion getSchemaVersion()
  {
    return VERSION;
  }

  @Override
  public void performSchemaUpgrade(BLocalOrionDatabase db,
      BSchemaVersion oldVersion) throws Exception
  {
    schemaManager.performSchemaUpgrade(db, oldVersion);
  }

////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////

  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }

  private static Type[] serviceTypes = new Type[] {
    TYPE,
    BAlarmService.TYPE
  };

  /**
   * The OrionAlarmService should not go into stations by default.
   * @return an empty list.
   */
  public String[] getApplicationIds()
  {
    return new String[] {};
  }

  private static final BSchemaVersion VERSION = BSchemaVersion.make("1.3");

  private final OrionAppSchemaManager schemaManager = new OrionAppSchemaManager(this, 
    new ISchemaUpgrader[] 
    {
      new Upgrade_1_0_to_1_1(),
      new Upgrade_1_2_to_1_3(),
    });  




  private static final OrionType[] ORION_TYPES = new OrionType[]
  {
    BOrionAlarmRecord.ORION_TYPE,
    BOrionAlarmClass.ORION_TYPE,
    BOrionAlarmFacetValue.ORION_TYPE,
    BOrionAlarmFacetName.ORION_TYPE,
    BOrionAlarmSource.ORION_TYPE,
    BOrionAlarmSourceOrder.ORION_TYPE
  };
}
