/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import static javax.baja.rpc.TransportType.box;
import static javax.baja.rpc.TransportType.web;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.baja.authn.BAuthenticationScheme;
import javax.baja.authn.BPasswordAuthenticationScheme;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SecurityUtil;
import javax.baja.role.BIRole;
import javax.baja.role.BIRoleListener;
import javax.baja.role.BIRoleService;
import javax.baja.role.BRoleService;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.security.AuditEvent;
import javax.baja.security.Auditor;
import javax.baja.security.BAbstractAuthenticator;
import javax.baja.security.BPassword;
import javax.baja.security.BPasswordAuthenticator;
import javax.baja.security.BPermissionsMap;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIService;
import javax.baja.sys.BIUnlinkableSlotsContainer;
import javax.baja.sys.BIcon;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.sys.Validatable;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BServiceContainer;

import com.tridium.authn.BAuthenticationService;
import com.tridium.authn.BDigestAuthenticationScheme;
import com.tridium.json.JSONObject;
import com.tridium.nre.security.NiagaraBasicPermission;
import com.tridium.session.SessionManager;
import com.tridium.sys.license.BLicenseExpirationNotificationSettings;
import com.tridium.sys.license.BSMANotificationSettings;
import com.tridium.util.ArrayUtil;

/**
 * BUserService is the service used to manage 
 * SecurityDomains, Profiles, and Users.
 *
 * @author    Brian Frank
 * @creation  30 Oct 00
 * @version   $Revision: 41$ $Date: 5/17/11 4:55:37 PM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 If enabled then repeated authentication failures
 will temporarily set the lockOut property of the user.
 Using lockOut makes it difficult to automate guessing
 of passwords.
 */
@NiagaraProperty(
  name = "lockOutEnabled",
  type = "boolean",
  defaultValue = "true",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 If lockOut is enabled, then this is the period of
 time a user account is locked out before being reset.
 */
@NiagaraProperty(
  name = "lockOutPeriod",
  type = "BRelTime",
  defaultValue = "BRelTime.make(10L*1000L)",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 If lock out is enabled, then this number, in conjunction with the
 lock-out window, indicates how many failed login attempts are tolerated
 within a certain amount of time before locking the user out.
 */
@NiagaraProperty(
  name = "maxBadLoginsBeforeLockOut",
  type = "int",
  defaultValue = "5",
  facets = {
    @Facet(name = "BFacets.MIN", value = "1"),
    @Facet(name = "BFacets.MAX", value = "10"),
    @Facet(name = "BFacets.SECURITY", value = "true")
  }
)
/*
 When lock out is enabled a user will be locked out for lock-out
 period of time if maxBadLoginsBeforeLockOut occur within this
 amount of time.
 */
@NiagaraProperty(
  name = "lockOutWindow",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(30)",
  facets = {
    @Facet(name = "BFacets.MIN", value = "BRelTime.makeSeconds(1)"),
    @Facet(name = "BFacets.MAX", value = "BRelTime.makeHours(24)"),
    @Facet(name = "BFacets.SECURITY", value = "true")
  }
)
@NiagaraProperty(
  name = "defaultAutoLogoffPeriod",
  type = "BRelTime",
  defaultValue = "BRelTime.makeMinutes(15)",
  facets = {
    @Facet(name = "BFacets.SHOW_SECONDS", value = "false"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.makeMinutes(2)"),
    @Facet(name = "BFacets.MAX", value = "BRelTime.makeHours(4)"),
    @Facet(name = "BFacets.SECURITY", value = "true")
  }
)
/*
 Only allow setting or resetting passwords over secure connections
 */
@NiagaraProperty(
  name = "secureOnlyPasswordSet",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The container for prototype users.  A prototype user
 defines configuration properties common to many users.  Prototypes
 are also used when synchronizing user accounts between stations.
 In this case, the prototype defines which properties are defined
 locally rather than being copied with the user account.
 */
@NiagaraProperty(
  name = "userPrototypes",
  type = "BUserPrototypes",
  defaultValue = "new BUserPrototypes()"
)
/*
 SMA Notification settings can be set here
 */
@NiagaraProperty(
  name = "SMANotificationSettings",
  type = "BSMANotificationSettings",
  defaultValue = "new BSMANotificationSettings()"
)
/*
 Maintain license expiration warning notifications.
 @since Niagara 4.12
 */
@NiagaraProperty(
  name = "licenseExpirationNotificationSettings",
  type = "BLicenseExpirationNotificationSettings",
  defaultValue = "new BLicenseExpirationNotificationSettings()"
)
@NiagaraTopic(
  name = "userEvent",
  eventType = "BUserEvent"
)
public class BUserService
  extends BComponent
  implements BIService, BIRoleListener, BIRestrictedComponent, IPropertyValidator, BIUnlinkableSlotsContainer
{            

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserService(651769728)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "lockOutEnabled"

  /**
   * Slot for the {@code lockOutEnabled} property.
   * If enabled then repeated authentication failures
   * will temporarily set the lockOut property of the user.
   * Using lockOut makes it difficult to automate guessing
   * of passwords.
   * @see #getLockOutEnabled
   * @see #setLockOutEnabled
   */
  public static final Property lockOutEnabled = newProperty(0, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code lockOutEnabled} property.
   * If enabled then repeated authentication failures
   * will temporarily set the lockOut property of the user.
   * Using lockOut makes it difficult to automate guessing
   * of passwords.
   * @see #lockOutEnabled
   */
  public boolean getLockOutEnabled() { return getBoolean(lockOutEnabled); }

  /**
   * Set the {@code lockOutEnabled} property.
   * If enabled then repeated authentication failures
   * will temporarily set the lockOut property of the user.
   * Using lockOut makes it difficult to automate guessing
   * of passwords.
   * @see #lockOutEnabled
   */
  public void setLockOutEnabled(boolean v) { setBoolean(lockOutEnabled, v, null); }

  //endregion Property "lockOutEnabled"

  //region Property "lockOutPeriod"

  /**
   * Slot for the {@code lockOutPeriod} property.
   * If lockOut is enabled, then this is the period of
   * time a user account is locked out before being reset.
   * @see #getLockOutPeriod
   * @see #setLockOutPeriod
   */
  public static final Property lockOutPeriod = newProperty(0, BRelTime.make(10L*1000L), BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code lockOutPeriod} property.
   * If lockOut is enabled, then this is the period of
   * time a user account is locked out before being reset.
   * @see #lockOutPeriod
   */
  public BRelTime getLockOutPeriod() { return (BRelTime)get(lockOutPeriod); }

  /**
   * Set the {@code lockOutPeriod} property.
   * If lockOut is enabled, then this is the period of
   * time a user account is locked out before being reset.
   * @see #lockOutPeriod
   */
  public void setLockOutPeriod(BRelTime v) { set(lockOutPeriod, v, null); }

  //endregion Property "lockOutPeriod"

  //region Property "maxBadLoginsBeforeLockOut"

  /**
   * Slot for the {@code maxBadLoginsBeforeLockOut} property.
   * If lock out is enabled, then this number, in conjunction with the
   * lock-out window, indicates how many failed login attempts are tolerated
   * within a certain amount of time before locking the user out.
   * @see #getMaxBadLoginsBeforeLockOut
   * @see #setMaxBadLoginsBeforeLockOut
   */
  public static final Property maxBadLoginsBeforeLockOut = newProperty(0, 5, BFacets.make(BFacets.make(BFacets.make(BFacets.MIN, 1), BFacets.make(BFacets.MAX, 10)), BFacets.make(BFacets.SECURITY, true)));

  /**
   * Get the {@code maxBadLoginsBeforeLockOut} property.
   * If lock out is enabled, then this number, in conjunction with the
   * lock-out window, indicates how many failed login attempts are tolerated
   * within a certain amount of time before locking the user out.
   * @see #maxBadLoginsBeforeLockOut
   */
  public int getMaxBadLoginsBeforeLockOut() { return getInt(maxBadLoginsBeforeLockOut); }

  /**
   * Set the {@code maxBadLoginsBeforeLockOut} property.
   * If lock out is enabled, then this number, in conjunction with the
   * lock-out window, indicates how many failed login attempts are tolerated
   * within a certain amount of time before locking the user out.
   * @see #maxBadLoginsBeforeLockOut
   */
  public void setMaxBadLoginsBeforeLockOut(int v) { setInt(maxBadLoginsBeforeLockOut, v, null); }

  //endregion Property "maxBadLoginsBeforeLockOut"

  //region Property "lockOutWindow"

  /**
   * Slot for the {@code lockOutWindow} property.
   * When lock out is enabled a user will be locked out for lock-out
   * period of time if maxBadLoginsBeforeLockOut occur within this
   * amount of time.
   * @see #getLockOutWindow
   * @see #setLockOutWindow
   */
  public static final Property lockOutWindow = newProperty(0, BRelTime.makeSeconds(30), BFacets.make(BFacets.make(BFacets.make(BFacets.MIN, BRelTime.makeSeconds(1)), BFacets.make(BFacets.MAX, BRelTime.makeHours(24))), BFacets.make(BFacets.SECURITY, true)));

  /**
   * Get the {@code lockOutWindow} property.
   * When lock out is enabled a user will be locked out for lock-out
   * period of time if maxBadLoginsBeforeLockOut occur within this
   * amount of time.
   * @see #lockOutWindow
   */
  public BRelTime getLockOutWindow() { return (BRelTime)get(lockOutWindow); }

  /**
   * Set the {@code lockOutWindow} property.
   * When lock out is enabled a user will be locked out for lock-out
   * period of time if maxBadLoginsBeforeLockOut occur within this
   * amount of time.
   * @see #lockOutWindow
   */
  public void setLockOutWindow(BRelTime v) { set(lockOutWindow, v, null); }

  //endregion Property "lockOutWindow"

  //region Property "defaultAutoLogoffPeriod"

  /**
   * Slot for the {@code defaultAutoLogoffPeriod} property.
   * @see #getDefaultAutoLogoffPeriod
   * @see #setDefaultAutoLogoffPeriod
   */
  public static final Property defaultAutoLogoffPeriod = newProperty(0, BRelTime.makeMinutes(15), BFacets.make(BFacets.make(BFacets.make(BFacets.make(BFacets.SHOW_SECONDS, false), BFacets.make(BFacets.MIN, BRelTime.makeMinutes(2))), BFacets.make(BFacets.MAX, BRelTime.makeHours(4))), BFacets.make(BFacets.SECURITY, true)));

  /**
   * Get the {@code defaultAutoLogoffPeriod} property.
   * @see #defaultAutoLogoffPeriod
   */
  public BRelTime getDefaultAutoLogoffPeriod() { return (BRelTime)get(defaultAutoLogoffPeriod); }

  /**
   * Set the {@code defaultAutoLogoffPeriod} property.
   * @see #defaultAutoLogoffPeriod
   */
  public void setDefaultAutoLogoffPeriod(BRelTime v) { set(defaultAutoLogoffPeriod, v, null); }

  //endregion Property "defaultAutoLogoffPeriod"

  //region Property "secureOnlyPasswordSet"

  /**
   * Slot for the {@code secureOnlyPasswordSet} property.
   * Only allow setting or resetting passwords over secure connections
   * @see #getSecureOnlyPasswordSet
   * @see #setSecureOnlyPasswordSet
   */
  public static final Property secureOnlyPasswordSet = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code secureOnlyPasswordSet} property.
   * Only allow setting or resetting passwords over secure connections
   * @see #secureOnlyPasswordSet
   */
  public boolean getSecureOnlyPasswordSet() { return getBoolean(secureOnlyPasswordSet); }

  /**
   * Set the {@code secureOnlyPasswordSet} property.
   * Only allow setting or resetting passwords over secure connections
   * @see #secureOnlyPasswordSet
   */
  public void setSecureOnlyPasswordSet(boolean v) { setBoolean(secureOnlyPasswordSet, v, null); }

  //endregion Property "secureOnlyPasswordSet"

  //region Property "userPrototypes"

  /**
   * Slot for the {@code userPrototypes} property.
   * The container for prototype users.  A prototype user
   * defines configuration properties common to many users.  Prototypes
   * are also used when synchronizing user accounts between stations.
   * In this case, the prototype defines which properties are defined
   * locally rather than being copied with the user account.
   * @see #getUserPrototypes
   * @see #setUserPrototypes
   */
  public static final Property userPrototypes = newProperty(0, new BUserPrototypes(), null);

  /**
   * Get the {@code userPrototypes} property.
   * The container for prototype users.  A prototype user
   * defines configuration properties common to many users.  Prototypes
   * are also used when synchronizing user accounts between stations.
   * In this case, the prototype defines which properties are defined
   * locally rather than being copied with the user account.
   * @see #userPrototypes
   */
  public BUserPrototypes getUserPrototypes() { return (BUserPrototypes)get(userPrototypes); }

  /**
   * Set the {@code userPrototypes} property.
   * The container for prototype users.  A prototype user
   * defines configuration properties common to many users.  Prototypes
   * are also used when synchronizing user accounts between stations.
   * In this case, the prototype defines which properties are defined
   * locally rather than being copied with the user account.
   * @see #userPrototypes
   */
  public void setUserPrototypes(BUserPrototypes v) { set(userPrototypes, v, null); }

  //endregion Property "userPrototypes"

  //region Property "SMANotificationSettings"

  /**
   * Slot for the {@code SMANotificationSettings} property.
   * SMA Notification settings can be set here
   * @see #getSMANotificationSettings
   * @see #setSMANotificationSettings
   */
  public static final Property SMANotificationSettings = newProperty(0, new BSMANotificationSettings(), null);

  /**
   * Get the {@code SMANotificationSettings} property.
   * SMA Notification settings can be set here
   * @see #SMANotificationSettings
   */
  public BSMANotificationSettings getSMANotificationSettings() { return (BSMANotificationSettings)get(SMANotificationSettings); }

  /**
   * Set the {@code SMANotificationSettings} property.
   * SMA Notification settings can be set here
   * @see #SMANotificationSettings
   */
  public void setSMANotificationSettings(BSMANotificationSettings v) { set(SMANotificationSettings, v, null); }

  //endregion Property "SMANotificationSettings"

  //region Property "licenseExpirationNotificationSettings"

  /**
   * Slot for the {@code licenseExpirationNotificationSettings} property.
   * Maintain license expiration warning notifications.
   * @since Niagara 4.12
   * @see #getLicenseExpirationNotificationSettings
   * @see #setLicenseExpirationNotificationSettings
   */
  public static final Property licenseExpirationNotificationSettings = newProperty(0, new BLicenseExpirationNotificationSettings(), null);

  /**
   * Get the {@code licenseExpirationNotificationSettings} property.
   * Maintain license expiration warning notifications.
   * @since Niagara 4.12
   * @see #licenseExpirationNotificationSettings
   */
  public BLicenseExpirationNotificationSettings getLicenseExpirationNotificationSettings() { return (BLicenseExpirationNotificationSettings)get(licenseExpirationNotificationSettings); }

  /**
   * Set the {@code licenseExpirationNotificationSettings} property.
   * Maintain license expiration warning notifications.
   * @since Niagara 4.12
   * @see #licenseExpirationNotificationSettings
   */
  public void setLicenseExpirationNotificationSettings(BLicenseExpirationNotificationSettings v) { set(licenseExpirationNotificationSettings, v, null); }

  //endregion Property "licenseExpirationNotificationSettings"

  //region Topic "userEvent"

  /**
   * Slot for the {@code userEvent} topic.
   * @see #fireUserEvent
   */
  public static final Topic userEvent = newTopic(0, null);

  /**
   * Fire an event for the {@code userEvent} topic.
   * @see #userEvent
   */
  public void fireUserEvent(BUserEvent event) { fire(userEvent, event, null); }

  //endregion Topic "userEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the UserService or throw ServiceNotFoundException.
   */
  public static BUserService getService()
  {
    return (BUserService) Sys.getService(TYPE);
  }

////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////

  /**
   * Register this component under "baja:UserService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static Type[] serviceTypes = new Type[] { TYPE };

  /**
   * Service start.
   */
  @Override
  public void serviceStarted()
    throws Exception
  {

  }

  /**
   * Service stop.
   */
  @Override
  public void serviceStopped()
    throws Exception
  {

  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// User Management
////////////////////////////////////////////////////////////////

  /**
   * Test to see if this user service supports distribution of
   * user accounts to other stations.  Subclasses may override to
   * disable the standard distribution behavior.  This is used
   * for implementations like LDAP that has its own mechanism
   * for distributing user accounts.
   */
  public boolean isDistributable()
  {
    return true;
  }

  /**
   * Force admin and guest to be their programmatic, username.
   */
  @Override
  public String getDisplayName(Slot slot, Context cx)
  {
    return super.getDisplayName(slot, cx);
  }
  
  /**
   * Get a user by name or return null.
   */
  public BUser getUser(String name)
  {
    BUser user = (BUser)get(SlotPath.escape(name));
    if (user == null)
    {
      return null;
    }
    if (!SecurityUtil.equals(user.getUsername(), name))
    {
      return null;
    }
    return user;
  }

  /**
   * Get all users in the station.
   * @since Niagara 4.6
   */
  public BUser[] getUsers()
  {
    return getChildren(BUser.class);
  }

  /**
   * In Niagara4 there is no requirement
   * for there to be a user named "admin".
   * 
   * This method now returns the 'admin' user
   * only if it is a superuser. If there is no 'admin'
   * user then the first enabled super user is returned.
   * 
  * There must always be at least one enabled 
   * superuser.
   * 
   * @return the 'admin' user if superuser and enabled
   *         otherwise return the first enabled superuser
   */
  public BUser getAdmin()
  {
    BValue obj = get("admin");
    if (obj instanceof BUser)
    {
      BUser admin = (BUser) obj;
      if (admin.getEnabled() &&
          admin.getPermissions().isSuperUser())
      {
        return admin;
      }
    }
    
    //"admin" user not found, enabled or a super user
    //find the first enabled super user account:
    SlotCursor<Property> c = getProperties();
    while(c.next(BUser.class))
    {
      BUser user = (BUser) c.get();
      if (user != null &&
          user.getEnabled() &&
          user.getPermissions().isSuperUser())
      {
        return user;
      }
    }
    //Station must contain at least one user
    //with superuser permissions so this 
    //should not happen.
    return null;
  }
  
  /**
   * Can the specified user currently login into this station.
   */
  public boolean canLogin(BUser user)
  {                    
    if (!user.getEnabled())
    {
      return false;
    }
    if (user.getLockOut())
    {
      return false;
    }
    if (user.isExpired())
    {
      return false;
    }

    //TODO is this still needed ?
    if ("BACnet".equals(user.getName()))
    {
      return false;
    }

    return user.getAuthenticator().canLogin(user);
  }

  /**
   * Returns the authentication scheme for the requested username. If no
   * user by the name exists, returns the station default authentication
   * scheme.
   *
   * @param username The name of the user for which we want the authentication scheme.
   * @return The authentication scheme associated with the specified user, or the station
   *         default scheme if the user doesn't exist.
   */
  public BAuthenticationScheme getAuthenticationSchemeForUser(String username)
  {
    BUser user = (username == null) ? null : getUser(username);
    return getAuthenticationSchemeForUser(user);
  }

  /**
   * Returns the authentication scheme for the requested username. If no
   * user by the name exists, returns the station default authentication
   * scheme.
   *
   * @param user The user for which we want the authentication scheme.
   * @return The authentication scheme associated with the specified user, or the station
   *         default scheme if the user is null;
   */
  public BAuthenticationScheme getAuthenticationSchemeForUser(BUser user)
  {
    user = (user == null) ? null :getUser(user.getUsername());
    if (user == null)
    {
      // if user is null, return the first remote scheme, or a new digest scheme if there
      // are no remote schemes
      BAuthenticationService authnService = (BAuthenticationService)Sys.getService(BAuthenticationService.TYPE);
      List<BAuthenticationScheme> schemes = authnService.getRemoteSchemes();
      if (!schemes.isEmpty())
      {
        return schemes.get(0);
      }
      else
      {
        return new BDigestAuthenticationScheme();
      }
    }
    else
    {
      return user.getAuthenticationScheme();
    }
  }
  
  /**
   * Records a standard formatted audit history record for a user 
   * authentication event (login attempt).
   * <p>
   * The <b>loginSuccessful</b> boolean parameter specifies whether the
   * login attempt was successful (true) or failed (false).
   * </p>
   * <p>
   * The <b>user</b> parameter specifies the user that attempted authentication.
   * </p>
   * <p>
   * The <b>auditContext</b> parameter is used to configure how the
   * audit trail will be handled when invoking this method.
   * If this Context is null, then no audit trail record will be
   * recorded for this call.  When the Context is not null, an
   * audit trail will be recorded for this authentication attempt
   * in which the following facets are checked on the Context for 
   * formatting the audit record: 
   * </p>
   *  <pre>
   *   Name                     Value    Function
   *   ----                     -----    --------
   *
   *   "target"                 String   This optional String will be used for the 
   *                                     "Target" column of the audit record (by 
   *                                     convention, this field is often used to specify
   *                                     a short String description of the service 
   *                                     attempting authentication).  When this key doesn't 
   *                                     exist, a blank String will be used for the 
   *                                     "Target" field.
   *
   *   "slotName"               String   This optional String will be used for the 
   *                                     "Slot Name" column of the audit record (by 
   *                                     convention, this field is often used to specify 
   *                                     the host attempting authentication).  When
   *                                     this key doesn't exist, a blank String will be
   *                                     used for the "Slot Name" field.
   *
   *   "oldValue"               String   This optional String will be used for the
   *                                     "Old Value" column of the audit record.  When
   *                                     this key doesn't exist, a blank String will be
   *                                     used for the "Old Value" field.
   * </pre>
   *
   * @since Niagara 3.3
   */
  public final void auditLoginAttempt(boolean loginSuccessful, BUser user, Context auditContext)
  {
    if (auditContext == null)
    {
      return;  // always skip if the audit context is null
    }
    try
    {
      Auditor auditor = Sys.getAuditor();
      if (auditor != null)
      {
        BFacets facets = auditContext.getFacets();
        auditor.audit(new AuditEvent(loginSuccessful?AuditEvent.LOGIN:AuditEvent.LOGIN_FAILURE,
                                     facets.gets("target", ""), 
                                     facets.gets("slotName", ""), 
                                     facets.gets("oldValue", ""), 
                                     loginSuccessful?"":Integer.toString(user.authFailTimes.size()),
                                     user.getUsername()));
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if (context != Context.decoding && value instanceof BUser)
    {
      if (isRunning() && context != null && context.getUser() != null)
      {
        BUser user = context.getUser();
        String newRoles = ((BUser)value).getRoles();
        BUser.checkRoleChange(user, "", newRoles);
      }

      BUser newUser = (BUser)value;
      BAbstractAuthenticator authenticator = newUser.getAuthenticator();
      if (authenticator instanceof BPasswordAuthenticator)
      {
        BPassword newPassword = ((BPasswordAuthenticator) authenticator).getPassword();
        if (newPassword.getPasswordEncoder().isReversible())
        {
          BAuthenticationScheme scheme = getAuthenticationScheme(newUser.getAuthenticationSchemeName());
          if (scheme instanceof BPasswordAuthenticationScheme)
          {
            BPasswordAuthenticator.checkPassword(newUser, (BPasswordAuthenticationScheme) scheme, ((BPasswordAuthenticator) authenticator).getPasswordConfig(), newPassword, context);
          }
        }
      }
    }
    super.checkAdd(name, value, flags, facets, context);
  }

  private BAuthenticationScheme getAuthenticationScheme(String schemeName)
  {
    BAuthenticationService authnService = null;
    try
    {
      authnService = (BAuthenticationService)Sys.getService(BAuthenticationService.TYPE);
    }
    catch(ServiceNotFoundException e)
    {
      // If we can't get the Authentication Service via the Sys.getService(BAuthenticationService.TYPE)
      // ord, just try to go the Services container and get it directly.
      // This assumes Authentication Service hasn't been renamed and that the user is in the UserService.

      // Get the service container
      BComplex parent = getParent();
      while ( parent != null && !(parent instanceof BServiceContainer) )
        parent = parent.getParent();

      // Get the authentication service from the service container
      if (parent != null)
      {
        BServiceContainer serviceContainer = (BServiceContainer)parent;
        serviceContainer.lease(3);
        Object[] services = serviceContainer.getChildren(BAuthenticationService.class);
        if (services.length > 0)
          authnService = (BAuthenticationService)services[0];
      }
    }

    if (authnService == null)
      return null;

    // Finally, get the scheme from the authentication service
    authnService.lease(3);
    return authnService.getAuthenticationScheme(schemeName);
  }
  
  @Override
  public void checkRename(Property property, String newName, Context context) 
  {
    BValue value = get(property);
    if(value instanceof BUser)
    {
      if(((BUser)value).getPermissions().isSuperUser() &&
          context!=null &&
          context.getUser()!=null &&
          !context.getUser().getPermissions().isSuperUser())
      {
        throw new LocalizableRuntimeException("wbutil", "superUser.canNotRenameSuperuser");
      }
    }
    super.checkRename(property, newName, context);
  }
  
  @Override
  public void checkRemove(Property property, Context context)
  {
    BComponentSpace space = getComponentSpace();
    // Never perform super user check on the proxy space side (it should
    // only be done on the server side component space or bog space)
    if (space == null || !space.isProxyComponentSpace())
    {
      BValue value = get(property);
      if (value instanceof BUser)
      {
        if (checkSuperuser((BUser)value))
        {
          // Make sure there's at least one other superuser
          long superUserCount = getProperties().stream()
            .filter(Objects::nonNull)
            .filter(prop -> !prop.equals(property))
            .flatMap(prop -> prop.getType().equals(BUser.TYPE) ? Stream.of((BUser)get(prop)) : Stream.empty())
            .filter(this::checkSuperuser)
            .count();

          if (superUserCount <= 0)
          {
            throw new LocalizableRuntimeException("wbutil", "superUser.canNotDeleteOnlySuperuser");
          }
        }
      }
    }
    super.checkRemove(property, context);
  }

  /**
   * Return true if the given user is a SuperUser, or false otherwise.
   *
   * @param user the user to check
   * @return true if the user is a SuperUser, false otherwise
   * @since Niagara 4.8
   */
  public boolean checkSuperuser(BUser user)
  {
    user.lease();
    // Quick check: if our permissions map says superuser, we're a superuser
    BPermissionsMap permissions = user.getPermissions();
    if (permissions.isSuperUser())
    {
      return true;
    }
    
    // Else, check our roles against the role service.
    BRoleService roleService = null;
    try 
    {
      roleService = (BRoleService)Sys.getService(BRoleService.TYPE);
    }
    catch (ServiceNotFoundException snfe)
    {
      // Offline station? See if we can find a roleService in the component graph
      BComplex parent = getParent();
      while (parent != null)
      {
        if (parent instanceof BServiceContainer)
        {
          BRoleService[] services = ((BComponent)parent).getChildren(BRoleService.class);
          if (services != null && services.length > 0)
          {
            roleService = services[0];
          }
          break;
        }
        parent = parent.getParent();
      }
    }
    
    // If we have a RoleService, get its superuser roles
    List<String> superUserRoles = new LinkedList<>();
    if (roleService != null)
    {
      for (String roleId: roleService.getEnabledRoleIds())
      {
        BIRole role = roleService.getRole(roleId);
        if (role != null && role.getPermissions().isSuperUser())
        {
          superUserRoles.add(roleId);
        }
      }
    }
    else 
    {
      // If nothing else, check for the frozen "admin" role
      superUserRoles.add(BRoleService.ADMIN_ROLE);
    }
    
    // Check the user's roles against the role service's roles
    Set<String> userRoles = user.getRoleIds();
    for (String roleId : superUserRoles)
    {
      if (userRoles.contains(SlotPath.unescape(roleId)))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the auto log off settings for the user. The period could either be default or user
   * specific if overridden
   * @param username - The current (logged in) user
   * @param cx
   * @return JSONObject - returns a jsonobject of this example form
   *        {
   *          autoLogoffEnabled : true,
   *          autoLogoffPeriod : 300000
   *        }
   */
  @NiagaraRpc( permissions = "unrestricted",
    transports = { @Transport(type = box), @Transport(type = web) }
  )
  public static JSONObject getAutoLogoffSettings(String username, Context cx)
  {
    BUserService userSvc = getService();
    BUser user = userSvc.getUser(username);
    JSONObject obj = new JSONObject();
    if (user != null)
    {
      BAutoLogoffSettings autoLogoffSettings = user.getAutoLogoffSettings();
      obj.put("autoLogoffEnabled", autoLogoffSettings.getAutoLogoffEnabled());
      if (autoLogoffSettings.getUseDefaultAutoLogoffPeriod())
      {
        obj.put("autoLogoffPeriod", userSvc.getDefaultAutoLogoffPeriod().getMillis());
      }
      else
      {
        obj.put("autoLogoffPeriod", autoLogoffSettings.getAutoLogoffPeriod().getMillis());
      }
    }
    return obj;
  }

////////////////////////////////////////////////////////////////
// IPropertyValidator
////////////////////////////////////////////////////////////////

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      return this;
    }
    else
    {
      return null;
    }
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void validateSet(Validatable validatable, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      Property[] props = validatable.getModifiedProperties();
      if (ArrayUtil.indexOf(props, userPrototypes) >= 0)
      {
        BUserPrototypes prototypeContainer = (BUserPrototypes)validatable.getProposedValue(userPrototypes);
        BComponent[] prototypes = prototypeContainer.getPrototypes();
        for (BComponent prototype : prototypes)
        {
          BUserPrototypes.checkRoleChange(prototype, "", context);
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
// PermissionsManager
////////////////////////////////////////////////////////////////

  /**
   * Add a custom permissions manager.  Each installed PermissionsManager
   * gets a chance to override a user's default permissions map for
   * a protected object.  A custom permissions managers is typically
   * installed by an application specific service on startup.
   *
   * @deprecated Will be removed in a future release
   */
  @Deprecated
  public synchronized void addPermissionsManager(PermissionsManager pmgr)
  {
    checkPermissionsManagerAccess();

    if (permissionsManagers.length == 0)
    {
      permissionsManagers = new PermissionsManager[] { pmgr };
    }
    else
    {
      List<PermissionsManager> temp = new ArrayList<>(Arrays.asList(permissionsManagers));
      temp.add(pmgr);
      permissionsManagers = temp.toArray(new PermissionsManager[temp.size()]);
    }
  }

  /**
   * Remove the specified permissions manager.
   *
   * @deprecated Will be removed in a future release
   */
  @Deprecated
  public synchronized void removePermissionsManager(PermissionsManager pmgr)
  {
    checkPermissionsManagerAccess();

    List<PermissionsManager> temp = new ArrayList<>(Arrays.asList(permissionsManagers));
    temp.remove(pmgr);
    permissionsManagers = temp.toArray(new PermissionsManager[temp.size()]);
  }

  /**
   * Get the list of installed permissions managers.  For performance
   * reasons, this returns the internal array of managers and must
   * not be modified by the caller.
   *
   * @deprecated Will be removed in a future release
   */
  @Deprecated
  public synchronized PermissionsManager[] getPermissionsManagers()
  {
    checkPermissionsManagerAccess();
    return permissionsManagers;
  }

  private void checkPermissionsManagerAccess()
  {
    Permission permission = new NiagaraBasicPermission("NIAGARA_PERMISSIONS_MANAGER");
    SecurityManager sm = System.getSecurityManager();
    if (sm != null)
    {
      sm.checkPermission(permission);
    }
  }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  public void setModified(BUser user)
  {
    monitor.setModified(user);
  }
  
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (monitor != null)
    {
      monitor.fw(x, a, b, c, d);
    }
    return super.fw(x, a, b, c, d);
  }
  
  @Override
  public void removeRole(BIRoleService service, String roleIdentifier)
  {
    SlotCursor<Property> sc = getProperties();
    while (sc.next(BUser.class))
    {
      BUser user = (BUser)sc.get();
      user.removeRole(service, SlotPath.unescape(roleIdentifier), Context.skipValidate);
    }
  }

  @Override
  public void renameRole(BIRoleService service, String oldRoleIdentifier, String newRoleIdentifier)
  {
    String unescapedOldRoleIdentifier = SlotPath.unescape(oldRoleIdentifier);
    String unescapedNewRoleIdentifier = SlotPath.unescape(newRoleIdentifier);
    SlotCursor<Property> sc = getProperties();
    while (sc.next(BUser.class))
    {
      BUser user = (BUser)sc.get();
      if(user.getRoleIds().contains(unescapedOldRoleIdentifier))
      {
        user.renameRole(service, unescapedOldRoleIdentifier, unescapedNewRoleIdentifier, Context.skipValidate);
      }
    }
  }

  @Override
  public void changedRole(BIRoleService service, String roleIdentifier)
  {
    SlotCursor<Property> sc = getProperties();
    while (sc.next(BUser.class))
    {
      BUser user = (BUser)sc.get();
      user.changedRole(service, SlotPath.unescape(roleIdentifier), Context.skipValidate);
    }
  }

  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);

    if (!(oldValue instanceof BUser))
    {
      return;
    }

    AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
      SessionManager.invalidateSuperSessions((BUser)oldValue);
      return null;
    });
  }

  @Override
  public void changed(Property property, Context context)
  {
    if (Sys.isStationStarted() && isMounted())
    {
      if (property.equals(defaultAutoLogoffPeriod))
      {
        SessionManager.updateSessionTimeout(null);
      }
    }
  }

  /**
   * Overridden to specify properties that are not allowed to be the target of a link.
   *
   * @since Niagara 4.10u8
   */
  @Override
  public final Set<Slot> getUnlinkableTargetSlots(Context context)
  {
    return UNLINKABLE_TARGET_SLOTS;
  }

  private static final Set<Slot> UNLINKABLE_TARGET_SLOTS = Collections.unmodifiableSet(
      Stream.of(maxBadLoginsBeforeLockOut, lockOutWindow, defaultAutoLogoffPeriod
    ).collect(Collectors.toSet()));

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/userService.png");

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private final UserMonitor monitor = new UserMonitor(this);

  private PermissionsManager[] permissionsManagers = new PermissionsManager[0];

  static final Logger log = Logger.getLogger("sys.service");
}
