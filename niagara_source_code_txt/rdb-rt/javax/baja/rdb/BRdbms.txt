/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.baja.driver.BDevice;
import javax.baja.license.BILicensed;
import javax.baja.license.Feature;
import javax.baja.naming.BHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.history.BRdbmsHistoryExportMode;
import javax.baja.rdb.point.BRdbmsPointDeviceExt;
import javax.baja.security.BPassword;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;

import com.tridium.rdb.aes.AesSysKeyEncoder;
import com.tridium.rdb.aes.BRdbSecuritySettings;
import com.tridium.rdb.jdbc.RdbmsDialect;

/**
 * BRdbms models a relational database.
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 26$ $Date: 6/22/11 3:10:45 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The address of the computer that hosts the database.
 */
@NiagaraProperty(
  name = "hostAddress",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = {
    @Facet(name = "BFacets.ORD_RELATIVIZE", value = "BBoolean.FALSE"),
    @Facet(name = "BFacets.FIELD_EDITOR", value = "BString.make(\"workbench:HostOrdFE\")"),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "BString.make(\"webEditors:HostOrdEditor\")")
  }
)
/*
 deprecated
 */
@NiagaraProperty(
  name = "ownerName",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN
)
/*
 indicate whether the connection should use SSL/TLS
 */
@NiagaraProperty(
  name = "useEncryptedConnection",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The user name that is used to login to the database.
 */
@NiagaraProperty(
  name = "userName",
  type = "String",
  defaultValue = ""
)
/*
 The password that is used to login to the database.
 */
@NiagaraProperty(
  name = "password",
  type = "BPassword",
  defaultValue = "BPassword.DEFAULT"
)
@NiagaraProperty(
  name = "worker",
  type = "BRdbmsWorker",
  defaultValue = "new BRdbmsWorker()"
)
/*
 Whether histories will be exported into this database as
 one table per History Id, or one table per BHistoryRecord type.
 The default is 'byHistoryId', but choosing 'byHistoryType' will
 make the data much easier to query once it has been exported.
 */
@NiagaraProperty(
  name = "exportMode",
  type = "BRdbmsHistoryExportMode",
  defaultValue = "BRdbmsHistoryExportMode.byHistoryId"
)
@NiagaraProperty(
  name = "useUnicodeEncodingScheme",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1
)
/*
 How to store the BAbsTimes:
 dialectDefault is the default assignment prefered by the dialect
 LocalTimestamp is convenient for Time that never changes TimeZones as long as it has millis precision
 UtcTimestamp   is convenient for TimeZone if it changes as long as it has millis precision
 utcMillis  is the most convenient for orion and other Rdbms that require import and export, but dates are hard to read within sql
 */
@NiagaraProperty(
  name = "timestampStorage",
  type = "BRdbmsTimestampStorage",
  defaultValue = "BRdbmsTimestampStorage.dialectDefault",
  flags = Flags.USER_DEFINED_1
)
/*
 Proxy point mappings
 */
@NiagaraProperty(
  name = "points",
  type = "BRdbmsPointDeviceExt",
  defaultValue = "new BRdbmsPointDeviceExt()"
)
@NiagaraProperty(
  name = "sqlSchemeEnabled",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "rdbSecuritySettings",
  type = "BRdbSecuritySettings",
  defaultValue = "new BRdbSecuritySettings()"
)
@NiagaraAction(
  name = "allowDialectModifications",
  flags = Flags.CONFIRM_REQUIRED
)
public abstract class BRdbms
  extends BDevice
  implements BILicensed
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbms(4232181812)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "hostAddress"

  /**
   * Slot for the {@code hostAddress} property.
   * The address of the computer that hosts the database.
   * @see #getHostAddress
   * @see #setHostAddress
   */
  public static final Property hostAddress = newProperty(0, BOrd.NULL, BFacets.make(BFacets.make(BFacets.make(BFacets.ORD_RELATIVIZE, BBoolean.FALSE), BFacets.make(BFacets.FIELD_EDITOR, BString.make("workbench:HostOrdFE"))), BFacets.make(BFacets.UX_FIELD_EDITOR, BString.make("webEditors:HostOrdEditor"))));

  /**
   * Get the {@code hostAddress} property.
   * The address of the computer that hosts the database.
   * @see #hostAddress
   */
  public BOrd getHostAddress() { return (BOrd)get(hostAddress); }

  /**
   * Set the {@code hostAddress} property.
   * The address of the computer that hosts the database.
   * @see #hostAddress
   */
  public void setHostAddress(BOrd v) { set(hostAddress, v, null); }

  //endregion Property "hostAddress"

  //region Property "ownerName"

  /**
   * Slot for the {@code ownerName} property.
   * deprecated
   * @see #getOwnerName
   * @see #setOwnerName
   */
  public static final Property ownerName = newProperty(Flags.HIDDEN, "", null);

  /**
   * Get the {@code ownerName} property.
   * deprecated
   * @see #ownerName
   */
  public String getOwnerName() { return getString(ownerName); }

  /**
   * Set the {@code ownerName} property.
   * deprecated
   * @see #ownerName
   */
  public void setOwnerName(String v) { setString(ownerName, v, null); }

  //endregion Property "ownerName"

  //region Property "useEncryptedConnection"

  /**
   * Slot for the {@code useEncryptedConnection} property.
   * indicate whether the connection should use SSL/TLS
   * @see #getUseEncryptedConnection
   * @see #setUseEncryptedConnection
   */
  public static final Property useEncryptedConnection = newProperty(0, false, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code useEncryptedConnection} property.
   * indicate whether the connection should use SSL/TLS
   * @see #useEncryptedConnection
   */
  public boolean getUseEncryptedConnection() { return getBoolean(useEncryptedConnection); }

  /**
   * Set the {@code useEncryptedConnection} property.
   * indicate whether the connection should use SSL/TLS
   * @see #useEncryptedConnection
   */
  public void setUseEncryptedConnection(boolean v) { setBoolean(useEncryptedConnection, v, null); }

  //endregion Property "useEncryptedConnection"

  //region Property "userName"

  /**
   * Slot for the {@code userName} property.
   * The user name that is used to login to the database.
   * @see #getUserName
   * @see #setUserName
   */
  public static final Property userName = newProperty(0, "", null);

  /**
   * Get the {@code userName} property.
   * The user name that is used to login to the database.
   * @see #userName
   */
  public String getUserName() { return getString(userName); }

  /**
   * Set the {@code userName} property.
   * The user name that is used to login to the database.
   * @see #userName
   */
  public void setUserName(String v) { setString(userName, v, null); }

  //endregion Property "userName"

  //region Property "password"

  /**
   * Slot for the {@code password} property.
   * The password that is used to login to the database.
   * @see #getPassword
   * @see #setPassword
   */
  public static final Property password = newProperty(0, BPassword.DEFAULT, null);

  /**
   * Get the {@code password} property.
   * The password that is used to login to the database.
   * @see #password
   */
  public BPassword getPassword() { return (BPassword)get(password); }

  /**
   * Set the {@code password} property.
   * The password that is used to login to the database.
   * @see #password
   */
  public void setPassword(BPassword v) { set(password, v, null); }

  //endregion Property "password"

  //region Property "worker"

  /**
   * Slot for the {@code worker} property.
   * @see #getWorker
   * @see #setWorker
   */
  public static final Property worker = newProperty(0, new BRdbmsWorker(), null);

  /**
   * Get the {@code worker} property.
   * @see #worker
   */
  public BRdbmsWorker getWorker() { return (BRdbmsWorker)get(worker); }

  /**
   * Set the {@code worker} property.
   * @see #worker
   */
  public void setWorker(BRdbmsWorker v) { set(worker, v, null); }

  //endregion Property "worker"

  //region Property "exportMode"

  /**
   * Slot for the {@code exportMode} property.
   * Whether histories will be exported into this database as
   * one table per History Id, or one table per BHistoryRecord type.
   * The default is 'byHistoryId', but choosing 'byHistoryType' will
   * make the data much easier to query once it has been exported.
   * @see #getExportMode
   * @see #setExportMode
   */
  public static final Property exportMode = newProperty(0, BRdbmsHistoryExportMode.byHistoryId, null);

  /**
   * Get the {@code exportMode} property.
   * Whether histories will be exported into this database as
   * one table per History Id, or one table per BHistoryRecord type.
   * The default is 'byHistoryId', but choosing 'byHistoryType' will
   * make the data much easier to query once it has been exported.
   * @see #exportMode
   */
  public BRdbmsHistoryExportMode getExportMode() { return (BRdbmsHistoryExportMode)get(exportMode); }

  /**
   * Set the {@code exportMode} property.
   * Whether histories will be exported into this database as
   * one table per History Id, or one table per BHistoryRecord type.
   * The default is 'byHistoryId', but choosing 'byHistoryType' will
   * make the data much easier to query once it has been exported.
   * @see #exportMode
   */
  public void setExportMode(BRdbmsHistoryExportMode v) { set(exportMode, v, null); }

  //endregion Property "exportMode"

  //region Property "useUnicodeEncodingScheme"

  /**
   * Slot for the {@code useUnicodeEncodingScheme} property.
   * @see #getUseUnicodeEncodingScheme
   * @see #setUseUnicodeEncodingScheme
   */
  public static final Property useUnicodeEncodingScheme = newProperty(Flags.USER_DEFINED_1, false, null);

  /**
   * Get the {@code useUnicodeEncodingScheme} property.
   * @see #useUnicodeEncodingScheme
   */
  public boolean getUseUnicodeEncodingScheme() { return getBoolean(useUnicodeEncodingScheme); }

  /**
   * Set the {@code useUnicodeEncodingScheme} property.
   * @see #useUnicodeEncodingScheme
   */
  public void setUseUnicodeEncodingScheme(boolean v) { setBoolean(useUnicodeEncodingScheme, v, null); }

  //endregion Property "useUnicodeEncodingScheme"

  //region Property "timestampStorage"

  /**
   * Slot for the {@code timestampStorage} property.
   * How to store the BAbsTimes:
   * dialectDefault is the default assignment prefered by the dialect
   * LocalTimestamp is convenient for Time that never changes TimeZones as long as it has millis precision
   * UtcTimestamp   is convenient for TimeZone if it changes as long as it has millis precision
   * utcMillis  is the most convenient for orion and other Rdbms that require import and export, but dates are hard to read within sql
   * @see #getTimestampStorage
   * @see #setTimestampStorage
   */
  public static final Property timestampStorage = newProperty(Flags.USER_DEFINED_1, BRdbmsTimestampStorage.dialectDefault, null);

  /**
   * Get the {@code timestampStorage} property.
   * How to store the BAbsTimes:
   * dialectDefault is the default assignment prefered by the dialect
   * LocalTimestamp is convenient for Time that never changes TimeZones as long as it has millis precision
   * UtcTimestamp   is convenient for TimeZone if it changes as long as it has millis precision
   * utcMillis  is the most convenient for orion and other Rdbms that require import and export, but dates are hard to read within sql
   * @see #timestampStorage
   */
  public BRdbmsTimestampStorage getTimestampStorage() { return (BRdbmsTimestampStorage)get(timestampStorage); }

  /**
   * Set the {@code timestampStorage} property.
   * How to store the BAbsTimes:
   * dialectDefault is the default assignment prefered by the dialect
   * LocalTimestamp is convenient for Time that never changes TimeZones as long as it has millis precision
   * UtcTimestamp   is convenient for TimeZone if it changes as long as it has millis precision
   * utcMillis  is the most convenient for orion and other Rdbms that require import and export, but dates are hard to read within sql
   * @see #timestampStorage
   */
  public void setTimestampStorage(BRdbmsTimestampStorage v) { set(timestampStorage, v, null); }

  //endregion Property "timestampStorage"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * Proxy point mappings
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BRdbmsPointDeviceExt(), null);

  /**
   * Get the {@code points} property.
   * Proxy point mappings
   * @see #points
   */
  public BRdbmsPointDeviceExt getPoints() { return (BRdbmsPointDeviceExt)get(points); }

  /**
   * Set the {@code points} property.
   * Proxy point mappings
   * @see #points
   */
  public void setPoints(BRdbmsPointDeviceExt v) { set(points, v, null); }

  //endregion Property "points"

  //region Property "sqlSchemeEnabled"

  /**
   * Slot for the {@code sqlSchemeEnabled} property.
   * @see #getSqlSchemeEnabled
   * @see #setSqlSchemeEnabled
   */
  public static final Property sqlSchemeEnabled = newProperty(0, false, null);

  /**
   * Get the {@code sqlSchemeEnabled} property.
   * @see #sqlSchemeEnabled
   */
  public boolean getSqlSchemeEnabled() { return getBoolean(sqlSchemeEnabled); }

  /**
   * Set the {@code sqlSchemeEnabled} property.
   * @see #sqlSchemeEnabled
   */
  public void setSqlSchemeEnabled(boolean v) { setBoolean(sqlSchemeEnabled, v, null); }

  //endregion Property "sqlSchemeEnabled"

  //region Property "rdbSecuritySettings"

  /**
   * Slot for the {@code rdbSecuritySettings} property.
   * @see #getRdbSecuritySettings
   * @see #setRdbSecuritySettings
   */
  public static final Property rdbSecuritySettings = newProperty(0, new BRdbSecuritySettings(), null);

  /**
   * Get the {@code rdbSecuritySettings} property.
   * @see #rdbSecuritySettings
   */
  public BRdbSecuritySettings getRdbSecuritySettings() { return (BRdbSecuritySettings)get(rdbSecuritySettings); }

  /**
   * Set the {@code rdbSecuritySettings} property.
   * @see #rdbSecuritySettings
   */
  public void setRdbSecuritySettings(BRdbSecuritySettings v) { set(rdbSecuritySettings, v, null); }

  //endregion Property "rdbSecuritySettings"

  //region Action "allowDialectModifications"

  /**
   * Slot for the {@code allowDialectModifications} action.
   * @see #allowDialectModifications()
   */
  public static final Action allowDialectModifications = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code allowDialectModifications} action.
   * @see #allowDialectModifications
   */
  public void allowDialectModifications() { invoke(allowDialectModifications, null, null); }

  //endregion Action "allowDialectModifications"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbms.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BDevice
////////////////////////////////////////////////////////////////

  @Override
  public Type getNetworkType()  { return BRdbmsNetwork.TYPE; }

  @Override
  protected IFuture postPing() { doPing(); return null; }

  @Override
  public void doPing()
  {
    try (Connection conn = getConnection();
         Statement statement = conn.createStatement())
    {
      // Issue a validation query.  This ensures that the connection
      // which we obtained from the pool can actually talk to the database.
      RdbmsDialect dialect = (RdbmsDialect)getRdbmsContext();

        statement.execute(dialect.getValidationQuery());
        setStatus(BStatus.makeDown(getStatus(), false));
        pingOk();
    }
    catch (Throwable e)
    {
      setStatus(BStatus.makeDown(getStatus(), true));
      pingFail(e.getMessage());
      e.printStackTrace();
    }
  }
  /**
   * Descendant classes are not allowed to override this method directly.
   * However, this method calls {@link #rdbmsStarted()}, which may
   * be overridden.
   */
  @Override
  public final void started()
    throws Exception
  {
    super.started();
    checkLicense();
    rdbmsStarted();
  }

  /**
   * a method for initialization code that needs to be run on child rdbms devices
   * @since 4.4_u4, 4.7_u2, 4.8_u1, 4.9
   */
  public void rdbmsStarted()
  throws Exception
  {
  }
  
  public void pingOk()
  {
    super.pingOk();
    preventDialectModifications();
  }
  
  public void preventDialectModifications()
  {
    Property[] props = getPropertiesArray();
    for(int i=0; i<props.length; i++)
    {
      if(Flags.isUserDefined1(this, props[i]) && !Flags.isReadonly(this, props[i]))
        setFlags(props[i], getFlags(props[i]) | Flags.READONLY); 
    }  
  }
  
  /**
   * Confirm Allowance for Dialect Modification.
   */
  public void doAllowDialectModifications(Context cx)
  {
    Property[] props = getPropertiesArray();
    for(int i=0; i<props.length; i++)
    {
      if(Flags.isUserDefined1(this, props[i]) && Flags.isReadonly(this, props[i]))
        setFlags(props[i], getFlags(props[i]) &~ Flags.READONLY); 
    }  
  }

/////////////////////////////////////////////////////////////////
// licensing
/////////////////////////////////////////////////////////////////

  /**
   * get the feature code used to license this database type.
   */
  @Override
  public abstract Feature getLicenseFeature();

  private void checkLicense()
  {
    try
    {
      Feature feature = getLicenseFeature();
      if (feature != null) feature.check();
    }
    catch(Exception e)
    {
      getLogger().log(Level.SEVERE, "Unlicensed: " + toPathString(), e);
      configFatal("Unlicensed: " + e);
    }
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  /**
   * Get a database connection using the default user name and
   * password configured on this instance.
   */
  public Connection getConnection()
    throws SQLException
  {
    return getConnection(getUserName(), getPassword());
  }

  /**
   * get a SQL connection instance to communicate with the database
   */
  public abstract Connection getConnection(String userName, BPassword password)
    throws SQLException;

  /**
   * This method is deprecated as of Niagara 4.5. RDBMS now uses the default SSLContext and
   * calling this method is no longer necessary. This method is a no-op.
   */
  @Deprecated
  public void initSSLContext()
  {
  }

  /**
   * get the hostname from the hostAddress
   */
  public final String getHostname()
  {
    if (getHostAddress().isNull()) return "";

    try
    {
      return ((BHost) getHostAddress().get()).getHostname();
    }
    // This can be thrown by BHost.mount(BHost host) if there is
    // more than one thread running.
    catch (IllegalArgumentException e)
    {
      return "";
    }
  }

  /**
   * get the RdbmsContext for the database.
   */
  public abstract RdbmsContext getRdbmsContext();
  private static boolean sslCtxInitialized;
  /**
   * Abstract method to implement aes sys key encoder.
   * @return
   */
  public AesSysKeyEncoder getEncoder() {
    return getRdbSecuritySettings().getEncoder();
  }

  /**
   * Returns the fetch size to use on ResultSets from queries made to this
   * Rdbms.  The default value returned is zero, which means the JDBC driver
   * ignores the value and is free to make its own best guess as to what
   * the fetch size should be. Subclasses can optionally override this method
   * to return a non-negative fetch size value to optimize query performance.
   * Subclass implementations of this method should never throw a runtime
   * exception for any reason, and they should avoid any expensive operations
   * to lookup the result.
   *
   * @see java.sql.ResultSet#setFetchSize(int)
   *
   * @since Niagara 4.11
   */
  public int getResultSetFetchSize()
  {
    return 0;
  }
}
