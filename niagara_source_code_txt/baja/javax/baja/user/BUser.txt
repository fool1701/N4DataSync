/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import static com.tridium.user.AutoLogoffSettingsTransferUtil.transferAutoLogoffSettings;

import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;

import javax.baja.authn.BAuthenticationScheme;
import javax.baja.category.BCategoryMask;
import javax.baja.license.Feature;
import javax.baja.license.LicenseException;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BNavFileNode;
import javax.baja.nav.NavFileDecoder;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.role.BIRole;
import javax.baja.role.BIRoleService;
import javax.baja.role.BRoleService;
import javax.baja.security.BAbstractAuthenticator;
import javax.baja.security.BIProtected;
import javax.baja.security.BPasswordAuthenticator;
import javax.baja.security.BPermissions;
import javax.baja.security.BPermissionsMap;
import javax.baja.security.PermissionException;
import javax.baja.space.BComponentSpace;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIUnlinkableSlotsContainer;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Slot;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.Validatable;
import javax.baja.util.BNotification;
import javax.baja.util.BServiceContainer;
import javax.baja.util.Lexicon;
import javax.baja.util.Queue;

import com.tridium.authn.BAuthenticationService;
import com.tridium.authn.LoginFailureCause;
import com.tridium.nre.util.SecurityManagerUtil;
import com.tridium.session.NiagaraSuperSession;
import com.tridium.session.SessionManager;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.user.BUserPasswordConfiguration;

/**
 * BUser is the component which represents a  user in the Baja framework.
 *
 * @author Brian Frank on 30 Oct 00
 * @since Baja 1.0
 */

@NiagaraType
/*
 Full name of the user.
 */
@NiagaraProperty(
  name = "fullName",
  type = "String",
  defaultValue = ""
)
/*
 If set to false, then the login attempts are blocked.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 This property indicates the an expiration time for
 the user.  Login attempts after the expires time are
 blocked.  If set to null then expiration is never.
 */
@NiagaraProperty(
  name = "expiration",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ExpirationFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:ExpirationEditor\"))")
)
/*
 This property is set to true if authentication user fails
 repeatedly.  While lockOut is true the user is unable to log in.
 */
@NiagaraProperty(
  name = "lockOut",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY
)
/*
 the permissions cached by the last changes to any Roles
 */
@NiagaraProperty(
  name = "permissions",
  type = "BPermissionsMap",
  defaultValue = "BPermissionsMap.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN
)
/*
 ISO 639 language code as two lower-case letters.
 */
@NiagaraProperty(
  name = "language",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_WIDTH, BInteger.make(6))")
)
/*
 Email address of the user.
 */
@NiagaraProperty(
  name = "email",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR
)
/*
 The method used to authenticate the user.
 */
@NiagaraProperty(
  name = "authenticator",
  type = "BAbstractAuthenticator",
  defaultValue = "new BPasswordAuthenticator()",
  flags = Flags.OPERATOR
)
/*
 These facets are used to provide user specific context information.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.NULL",
  flags = Flags.OPERATOR,
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"wbutil:UserFacetsFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"webEditors:UserFacetsEditor\""),
    @Facet(name = "\"enablePopOut\"", value = "false")
  }
)
/*
 This ord references a nav file to use for this user.
 */
@NiagaraProperty(
  name = "navFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 The name of the prototype that this user was created from.
 */
@NiagaraProperty(
  name = "prototypeName",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"workbench:UserPrototypeFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:UserPrototypeEditor\"))")
)
/*
 If true, then this user account can be synchronized to other stations
 on the network.
 */
@NiagaraProperty(
  name = "networkUser",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "\"wbutil:NetworkUserFE\"")
)
/*
 A string that identifies the version of this user.  The version is used
 in the synchronization process to determine if a corresponding user
 account in another station is in sync with the master account.
 */
@NiagaraProperty(
  name = "version",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 A string that identifies the prototype version for this user. The prototype version
 is used in the synchronization process to determine if a prototype user for this user has
 changed and requires another sync.
 */
@NiagaraProperty(
  name = "prototypeVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 Cell phone number of the user
 */
@NiagaraProperty(
  name = "cellPhoneNumber",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR
)
/*
 The name of the authentication scheme this user authenticating with
 */
@NiagaraProperty(
  name = "authenticationSchemeName",
  type = "String",
  defaultValue = "DigestScheme",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:AuthenticationSchemeFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:AuthenticationSchemeEditor\"), BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 comma delimited list of role identifiers
 */
@NiagaraProperty(
  name = "roles",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:RoleFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:RolesEditor\"), BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Allow this user to be connected with multiple sessions.
 If false, a new session will cause the old one to be invalidated.
 */
@NiagaraProperty(
  name = "allowConcurrentSessions",
  type = "boolean",
  defaultValue = "true",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "autoLogoffSettings",
  type = "BAutoLogoffSettings",
  defaultValue = "new BAutoLogoffSettings()",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Version of the type used to detect when extra operations might need to be performed on objects
 of this type.
 @since Niagara 4.4
 */
@NiagaraProperty(
  name = "typeVersion",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 Reset the lock out property to false.
 */
@NiagaraAction(
  name = "clearLockOut"
)
/*
 Update the lastModified property of this user.
 */
@NiagaraAction(
  name = "setModified"
)
public class BUser
  extends BComponent
  implements BIUnlinkableSlotsContainer, BIStatus, Context, Principal
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUser(145189101)1.0$ @*/
/* Generated Thu Sep 08 11:32:40 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fullName"

  /**
   * Slot for the {@code fullName} property.
   * Full name of the user.
   * @see #getFullName
   * @see #setFullName
   */
  public static final Property fullName = newProperty(0, "", null);

  /**
   * Get the {@code fullName} property.
   * Full name of the user.
   * @see #fullName
   */
  public String getFullName() { return getString(fullName); }

  /**
   * Set the {@code fullName} property.
   * Full name of the user.
   * @see #fullName
   */
  public void setFullName(String v) { setString(fullName, v, null); }

  //endregion Property "fullName"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * If set to false, then the login attempts are blocked.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * If set to false, then the login attempts are blocked.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * If set to false, then the login attempts are blocked.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "expiration"

  /**
   * Slot for the {@code expiration} property.
   * This property indicates the an expiration time for
   * the user.  Login attempts after the expires time are
   * blocked.  If set to null then expiration is never.
   * @see #getExpiration
   * @see #setExpiration
   */
  public static final Property expiration = newProperty(0, BAbsTime.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ExpirationFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:ExpirationEditor")));

  /**
   * Get the {@code expiration} property.
   * This property indicates the an expiration time for
   * the user.  Login attempts after the expires time are
   * blocked.  If set to null then expiration is never.
   * @see #expiration
   */
  public BAbsTime getExpiration() { return (BAbsTime)get(expiration); }

  /**
   * Set the {@code expiration} property.
   * This property indicates the an expiration time for
   * the user.  Login attempts after the expires time are
   * blocked.  If set to null then expiration is never.
   * @see #expiration
   */
  public void setExpiration(BAbsTime v) { set(expiration, v, null); }

  //endregion Property "expiration"

  //region Property "lockOut"

  /**
   * Slot for the {@code lockOut} property.
   * This property is set to true if authentication user fails
   * repeatedly.  While lockOut is true the user is unable to log in.
   * @see #getLockOut
   * @see #setLockOut
   */
  public static final Property lockOut = newProperty(Flags.READONLY, false, null);

  /**
   * Get the {@code lockOut} property.
   * This property is set to true if authentication user fails
   * repeatedly.  While lockOut is true the user is unable to log in.
   * @see #lockOut
   */
  public boolean getLockOut() { return getBoolean(lockOut); }

  /**
   * Set the {@code lockOut} property.
   * This property is set to true if authentication user fails
   * repeatedly.  While lockOut is true the user is unable to log in.
   * @see #lockOut
   */
  public void setLockOut(boolean v) { setBoolean(lockOut, v, null); }

  //endregion Property "lockOut"

  //region Property "permissions"

  /**
   * Slot for the {@code permissions} property.
   * the permissions cached by the last changes to any Roles
   * @see #getPermissions
   * @see #setPermissions
   */
  public static final Property permissions = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BPermissionsMap.DEFAULT, null);

  /**
   * Get the {@code permissions} property.
   * the permissions cached by the last changes to any Roles
   * @see #permissions
   */
  public BPermissionsMap getPermissions() { return (BPermissionsMap)get(permissions); }

  /**
   * Set the {@code permissions} property.
   * the permissions cached by the last changes to any Roles
   * @see #permissions
   */
  public void setPermissions(BPermissionsMap v) { set(permissions, v, null); }

  //endregion Property "permissions"

  //region Property "language"

  /**
   * Slot for the {@code language} property.
   * ISO 639 language code as two lower-case letters.
   * @see #getLanguage
   * @see #setLanguage
   */
  public static final Property language = newProperty(0, "", BFacets.make(BFacets.FIELD_WIDTH, BInteger.make(6)));

  /**
   * Get the {@code language} property.
   * ISO 639 language code as two lower-case letters.
   * @see #language
   */
  public String getLanguage() { return getString(language); }

  /**
   * Set the {@code language} property.
   * ISO 639 language code as two lower-case letters.
   * @see #language
   */
  public void setLanguage(String v) { setString(language, v, null); }

  //endregion Property "language"

  //region Property "email"

  /**
   * Slot for the {@code email} property.
   * Email address of the user.
   * @see #getEmail
   * @see #setEmail
   */
  public static final Property email = newProperty(Flags.OPERATOR, "", null);

  /**
   * Get the {@code email} property.
   * Email address of the user.
   * @see #email
   */
  public String getEmail() { return getString(email); }

  /**
   * Set the {@code email} property.
   * Email address of the user.
   * @see #email
   */
  public void setEmail(String v) { setString(email, v, null); }

  //endregion Property "email"

  //region Property "authenticator"

  /**
   * Slot for the {@code authenticator} property.
   * The method used to authenticate the user.
   * @see #getAuthenticator
   * @see #setAuthenticator
   */
  public static final Property authenticator = newProperty(Flags.OPERATOR, new BPasswordAuthenticator(), null);

  /**
   * Get the {@code authenticator} property.
   * The method used to authenticate the user.
   * @see #authenticator
   */
  public BAbstractAuthenticator getAuthenticator() { return (BAbstractAuthenticator)get(authenticator); }

  /**
   * Set the {@code authenticator} property.
   * The method used to authenticate the user.
   * @see #authenticator
   */
  public void setAuthenticator(BAbstractAuthenticator v) { set(authenticator, v, null); }

  //endregion Property "authenticator"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are used to provide user specific context information.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.OPERATOR, BFacets.NULL, BFacets.make(BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "wbutil:UserFacetsFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "webEditors:UserFacetsEditor")), BFacets.make("enablePopOut", false)));

  /**
   * Get the {@code facets} property.
   * These facets are used to provide user specific context information.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are used to provide user specific context information.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "navFile"

  /**
   * Slot for the {@code navFile} property.
   * This ord references a nav file to use for this user.
   * @see #getNavFile
   * @see #setNavFile
   */
  public static final Property navFile = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code navFile} property.
   * This ord references a nav file to use for this user.
   * @see #navFile
   */
  public BOrd getNavFile() { return (BOrd)get(navFile); }

  /**
   * Set the {@code navFile} property.
   * This ord references a nav file to use for this user.
   * @see #navFile
   */
  public void setNavFile(BOrd v) { set(navFile, v, null); }

  //endregion Property "navFile"

  //region Property "prototypeName"

  /**
   * Slot for the {@code prototypeName} property.
   * The name of the prototype that this user was created from.
   * @see #getPrototypeName
   * @see #setPrototypeName
   */
  public static final Property prototypeName = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, BString.make("workbench:UserPrototypeFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:UserPrototypeEditor")));

  /**
   * Get the {@code prototypeName} property.
   * The name of the prototype that this user was created from.
   * @see #prototypeName
   */
  public String getPrototypeName() { return getString(prototypeName); }

  /**
   * Set the {@code prototypeName} property.
   * The name of the prototype that this user was created from.
   * @see #prototypeName
   */
  public void setPrototypeName(String v) { setString(prototypeName, v, null); }

  //endregion Property "prototypeName"

  //region Property "networkUser"

  /**
   * Slot for the {@code networkUser} property.
   * If true, then this user account can be synchronized to other stations
   * on the network.
   * @see #getNetworkUser
   * @see #setNetworkUser
   */
  public static final Property networkUser = newProperty(0, false, BFacets.make(BFacets.FIELD_EDITOR, "wbutil:NetworkUserFE"));

  /**
   * Get the {@code networkUser} property.
   * If true, then this user account can be synchronized to other stations
   * on the network.
   * @see #networkUser
   */
  public boolean getNetworkUser() { return getBoolean(networkUser); }

  /**
   * Set the {@code networkUser} property.
   * If true, then this user account can be synchronized to other stations
   * on the network.
   * @see #networkUser
   */
  public void setNetworkUser(boolean v) { setBoolean(networkUser, v, null); }

  //endregion Property "networkUser"

  //region Property "version"

  /**
   * Slot for the {@code version} property.
   * A string that identifies the version of this user.  The version is used
   * in the synchronization process to determine if a corresponding user
   * account in another station is in sync with the master account.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY | Flags.HIDDEN, "", null);

  /**
   * Get the {@code version} property.
   * A string that identifies the version of this user.  The version is used
   * in the synchronization process to determine if a corresponding user
   * account in another station is in sync with the master account.
   * @see #version
   */
  public String getVersion() { return getString(version); }

  /**
   * Set the {@code version} property.
   * A string that identifies the version of this user.  The version is used
   * in the synchronization process to determine if a corresponding user
   * account in another station is in sync with the master account.
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

  //endregion Property "version"

  //region Property "prototypeVersion"

  /**
   * Slot for the {@code prototypeVersion} property.
   * A string that identifies the prototype version for this user. The prototype version
   * is used in the synchronization process to determine if a prototype user for this user has
   * changed and requires another sync.
   * @see #getPrototypeVersion
   * @see #setPrototypeVersion
   */
  public static final Property prototypeVersion = newProperty(Flags.READONLY | Flags.HIDDEN, "", null);

  /**
   * Get the {@code prototypeVersion} property.
   * A string that identifies the prototype version for this user. The prototype version
   * is used in the synchronization process to determine if a prototype user for this user has
   * changed and requires another sync.
   * @see #prototypeVersion
   */
  public String getPrototypeVersion() { return getString(prototypeVersion); }

  /**
   * Set the {@code prototypeVersion} property.
   * A string that identifies the prototype version for this user. The prototype version
   * is used in the synchronization process to determine if a prototype user for this user has
   * changed and requires another sync.
   * @see #prototypeVersion
   */
  public void setPrototypeVersion(String v) { setString(prototypeVersion, v, null); }

  //endregion Property "prototypeVersion"

  //region Property "cellPhoneNumber"

  /**
   * Slot for the {@code cellPhoneNumber} property.
   * Cell phone number of the user
   * @see #getCellPhoneNumber
   * @see #setCellPhoneNumber
   */
  public static final Property cellPhoneNumber = newProperty(Flags.OPERATOR, "", null);

  /**
   * Get the {@code cellPhoneNumber} property.
   * Cell phone number of the user
   * @see #cellPhoneNumber
   */
  public String getCellPhoneNumber() { return getString(cellPhoneNumber); }

  /**
   * Set the {@code cellPhoneNumber} property.
   * Cell phone number of the user
   * @see #cellPhoneNumber
   */
  public void setCellPhoneNumber(String v) { setString(cellPhoneNumber, v, null); }

  //endregion Property "cellPhoneNumber"

  //region Property "authenticationSchemeName"

  /**
   * Slot for the {@code authenticationSchemeName} property.
   * The name of the authentication scheme this user authenticating with
   * @see #getAuthenticationSchemeName
   * @see #setAuthenticationSchemeName
   */
  public static final Property authenticationSchemeName = newProperty(0, "DigestScheme", BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:AuthenticationSchemeFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:AuthenticationSchemeEditor"), BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code authenticationSchemeName} property.
   * The name of the authentication scheme this user authenticating with
   * @see #authenticationSchemeName
   */
  public String getAuthenticationSchemeName() { return getString(authenticationSchemeName); }

  /**
   * Set the {@code authenticationSchemeName} property.
   * The name of the authentication scheme this user authenticating with
   * @see #authenticationSchemeName
   */
  public void setAuthenticationSchemeName(String v) { setString(authenticationSchemeName, v, null); }

  //endregion Property "authenticationSchemeName"

  //region Property "roles"

  /**
   * Slot for the {@code roles} property.
   * comma delimited list of role identifiers
   * @see #getRoles
   * @see #setRoles
   */
  public static final Property roles = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:RoleFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:RolesEditor"), BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code roles} property.
   * comma delimited list of role identifiers
   * @see #roles
   */
  public String getRoles() { return getString(roles); }

  /**
   * Set the {@code roles} property.
   * comma delimited list of role identifiers
   * @see #roles
   */
  public void setRoles(String v) { setString(roles, v, null); }

  //endregion Property "roles"

  //region Property "allowConcurrentSessions"

  /**
   * Slot for the {@code allowConcurrentSessions} property.
   * Allow this user to be connected with multiple sessions.
   * If false, a new session will cause the old one to be invalidated.
   * @see #getAllowConcurrentSessions
   * @see #setAllowConcurrentSessions
   */
  public static final Property allowConcurrentSessions = newProperty(0, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code allowConcurrentSessions} property.
   * Allow this user to be connected with multiple sessions.
   * If false, a new session will cause the old one to be invalidated.
   * @see #allowConcurrentSessions
   */
  public boolean getAllowConcurrentSessions() { return getBoolean(allowConcurrentSessions); }

  /**
   * Set the {@code allowConcurrentSessions} property.
   * Allow this user to be connected with multiple sessions.
   * If false, a new session will cause the old one to be invalidated.
   * @see #allowConcurrentSessions
   */
  public void setAllowConcurrentSessions(boolean v) { setBoolean(allowConcurrentSessions, v, null); }

  //endregion Property "allowConcurrentSessions"

  //region Property "autoLogoffSettings"

  /**
   * Slot for the {@code autoLogoffSettings} property.
   * @since Niagara 4.4
   * @see #getAutoLogoffSettings
   * @see #setAutoLogoffSettings
   */
  public static final Property autoLogoffSettings = newProperty(0, new BAutoLogoffSettings(), BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code autoLogoffSettings} property.
   * @since Niagara 4.4
   * @see #autoLogoffSettings
   */
  public BAutoLogoffSettings getAutoLogoffSettings() { return (BAutoLogoffSettings)get(autoLogoffSettings); }

  /**
   * Set the {@code autoLogoffSettings} property.
   * @since Niagara 4.4
   * @see #autoLogoffSettings
   */
  public void setAutoLogoffSettings(BAutoLogoffSettings v) { set(autoLogoffSettings, v, null); }

  //endregion Property "autoLogoffSettings"

  //region Property "typeVersion"

  /**
   * Slot for the {@code typeVersion} property.
   * Version of the type used to detect when extra operations might need to be performed on objects
   * of this type.
   * @since Niagara 4.4
   * @see #getTypeVersion
   * @see #setTypeVersion
   */
  public static final Property typeVersion = newProperty(Flags.READONLY | Flags.HIDDEN, 0, null);

  /**
   * Get the {@code typeVersion} property.
   * Version of the type used to detect when extra operations might need to be performed on objects
   * of this type.
   * @since Niagara 4.4
   * @see #typeVersion
   */
  public int getTypeVersion() { return getInt(typeVersion); }

  /**
   * Set the {@code typeVersion} property.
   * Version of the type used to detect when extra operations might need to be performed on objects
   * of this type.
   * @since Niagara 4.4
   * @see #typeVersion
   */
  public void setTypeVersion(int v) { setInt(typeVersion, v, null); }

  //endregion Property "typeVersion"

  //region Action "clearLockOut"

  /**
   * Slot for the {@code clearLockOut} action.
   * Reset the lock out property to false.
   * @see #clearLockOut()
   */
  public static final Action clearLockOut = newAction(0, null);

  /**
   * Invoke the {@code clearLockOut} action.
   * Reset the lock out property to false.
   * @see #clearLockOut
   */
  public void clearLockOut() { invoke(clearLockOut, null, null); }

  //endregion Action "clearLockOut"

  //region Action "setModified"

  /**
   * Slot for the {@code setModified} action.
   * Update the lastModified property of this user.
   * @see #setModified()
   */
  public static final Action setModified = newAction(0, null);

  /**
   * Invoke the {@code setModified} action.
   * Update the lastModified property of this user.
   * @see #setModified
   */
  public void setModified() { invoke(setModified, null, null); }

  //endregion Action "setModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUser.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  public BUser()
  {
    authFailTimes = new Queue();
  }

  static BUser makeGuest()
  {
    BUser u = new BUser();
    u.setEnabled(false);
    u.checkGuest();
    return u;                             
  }

  private void checkGuest()
  {
    if (!isRunning())
      return; // Don't perform guest license checks unless its for a running station
    
    // If not licensed for the guest user, disable this guest user and 
    // set it to readonly
    if (checkLicense)
    {
      // verify licensed to use the guest account
      try
      {
        Feature stationFeature = Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "station");
        guestLicensed = stationFeature.getb("guestEnabled", false);
      }
      catch(LicenseException e)
      {
        guestLicensed = false;
      }
      checkLicense = false;
      
      if (!guestLicensed && log.isLoggable(Level.FINE))
        log.fine("Guest user account not licensed. Guest user is disabled.");        
    }
    
    if (guestLicensed)
    {
      // Un-hide, un-readonly
      BComplex userService = getParent();
      Property prop = getPropertyInParent();
      if (userService != null && prop != null)
      {
        int flags = userService.getFlags(prop);
        if ((flags & Flags.HIDDEN) != 0)
          flags &= ~Flags.HIDDEN;
        if ((flags & Flags.READONLY) != 0)
          flags &= ~Flags.READONLY;
        userService.setFlags(prop, flags);
        Flags.setAllReadonly(this, false, null);
      }
    }
    else
    {
      // Disable, readonly, hide
      if (getEnabled()) setEnabled(false);
      BComplex userService = getParent();
      Property prop = getPropertyInParent();
      if (userService != null && prop != null)
      {
        int flags = userService.getFlags(prop);
        if ((flags & Flags.HIDDEN) == 0)
          flags |= Flags.HIDDEN;
        if ((flags & Flags.READONLY) == 0)
          flags |= Flags.READONLY;
        userService.setFlags(prop, flags);
        Flags.setAllReadonly(this, true, null);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the username. Note that it is not SlotPath-escaped.
   */
  public String getUsername()
  {                           
    String name = getName();     
    if (name == null) return "";
    return SlotPath.unescape(name);
  }                     

  /**
   * True if the configured expiration has elapsed.
   */
  public boolean isExpired()
  {                                                  
    return isExpired(getExpiration());
  }                      

  /**
   * True if the configured expiration has elapsed.
   */
  public static boolean isExpired(BAbsTime expiration)
  {
    if (expiration.isNull()) return false;
    return expiration.getMillis() < Clock.millis();
  }
  
  /**
   * Get the user's home page.  Default implementation attempts
   * to use the root of the configured nav file.
   */
  public BOrd getHomePage()
  {                                                            
    try
    {      
      BOrd navFile = getNavFile();               
      if (!navFile.isNull()) 
      {
        BNavFileNode root = NavFileDecoder.load(navFile).getRootNode();
        return root.getOrdInSession();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return DEFAULT_HOME_PAGE;
  }

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////  

  @Override
  public final Context getBase() { return null; }

  @Override
  public final BUser getUser() { return this; }

  @Override
  public final BObject getFacet(String name) { return getFacets().get(name); }
  
////////////////////////////////////////////////////////////////
// IStatus
////////////////////////////////////////////////////////////////  

  @Override
  public BStatus getStatus()
  {                                       
    int bits = 0;
    if (!getEnabled()) bits |= BStatus.DISABLED;
    if (isExpired())   bits |= BStatus.FAULT;              
    if (getLockOut())  bits |= BStatus.ALARM;              
    return BStatus.make(bits);
  }

////////////////////////////////////////////////////////////////
// Authentication
////////////////////////////////////////////////////////////////  

  /**
   * Gets the user's {@link BAuthenticationScheme BAuthenticationScheme}
   * from the {@link BAuthenticationService BAuthenticationService}.
   *
   * @return The BAuthenticationScheme used by the user, or null if it could not be found.
   *
   */
  public BAuthenticationScheme getAuthenticationScheme()
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
    return authnService.getAuthenticationScheme(getAuthenticationSchemeName());
  }
  /**
   * This method should be called whenever an authentication
   * attempts is successful on this user.  It provides the 
   * hook necessary to enforce the lock out policy.
   */
  public void authenticateOk(BUserService service)
  {                  
    authFailTimes.clear();
  }
  
  /**
   * This method should be called whenever an authentication
   * attempts fails on this user.  It provides the hook 
   * necessary to enforce the lock out policy.
   */
  public void authenticateFailed(BUserService service)
  {
    // if lock out not enabled then bail
    if (!service.getLockOutEnabled()) return;
    
    // first, enqueue current failure time.
    BAbsTime now = BAbsTime.now();
    authFailTimes.enqueue(now);
    
    // now remove all failure times that are outside the lock-out window.
    BAbsTime startOfWindow = now.subtract(service.getLockOutWindow());
    while (((BAbsTime) authFailTimes.peek()).isBefore(startOfWindow))
      authFailTimes.dequeue();
    
    // The fail queue now contains the number of failures within the
    // lock out window. If that number is greater than or equal to the 
    // maximum allowed attempts, then lock the user out.
    if (authFailTimes.size() >= service.getMaxBadLoginsBeforeLockOut())
    {
      setLockOut(true);
      // Do this now to clear the state, when the lock out expires, we will
      // be back to initial state of no failed attempts.
      authenticateOk(service);
      if (lockoutTicket != null)
      {
        lockoutTicket.cancel();
      }
      lockoutTicket = Clock.schedule(this, service.getLockOutPeriod(), clearLockOut, null);
    }
  }           
  
  /**
   * Clear the lock out flag.
   */
  public void doClearLockOut()
  {                          
    setLockOut(false);
  }

////////////////////////////////////////////////////////////////
// Roles
////////////////////////////////////////////////////////////////
  
  public boolean hasRole(String roleId)
  {
    return getRoleIds().contains(roleId);
  }
  
  /**
   * Return the set of BIRole for the user
   * This method can only be called once
   * both the role service has been started.
   * 
   * @return a set containing the roles of this user,
   *         returns an empty set if the user has no roles
   */
  /*
   * If performance becomes an issue and this method
   * is updated to no longer create a new set every call,
   * make sure to update all the BRoleService.checkRemoveXXX()'s
   * that mutate the set to perform verifications
   */
  public Set<BIRole> getRoleSet()
  {
    try
    {
      return getRoleSet(BRoleService.getService()); 
        
    }catch(ServiceNotFoundException snfe)
    {
      //Handle the offline / wb use case here  
    }
    return Collections.emptySet();
  }
  
  private Set<BIRole> getRoleSet(BIRoleService service)
  {
    Set<BIRole> set = new TreeSet<>();
    String[] roles = getRoles().split(ROLE_DELIMITER);
    
    for(String role : roles)
    {
      BIRole r = service.getRole(role);
      if(r != null)
        set.add(r);
    }
    return set;
  }
  
  /**
   * Return the String role identifiers for this user
   * 
   * @return a set of <b>unescaped</b> string role identifiers for this user,
   *         an empty set if the user has no roles
   *
   */
  public Set<String> getRoleIds()
  {
    //If performance becomes an issue, 
    //cache the roles and role IDs, and clear the cache 
    //on any changes to the role property. 

    return splitRoles(getRoles());
  }
  
  /**
   * Split a delimited role string into a set of <b>unescaped</b> role identifiers
   *
   * @return a set of string role identifiers for this user,
   *         an empty set if the role string is empty
   */
  public static Set<String> splitRoles(String roles)
  {
    String[] roleArray = roles.split(ROLE_DELIMITER);
    if(roleArray.length > 0)
    {
      Set<String> set = new TreeSet<>();
      for(String role : roleArray)
      {
        if(role != null && !role.isEmpty())
          set.add(SlotPath.unescape(role));
      }
      return set;
    }
    return Collections.emptySet();
  }
  
  /**
   * Add a role identifier to this user.
   * Due to service startup sequencing the
   * the role cannot be validated. 
   * If a nonexistent role is added to a user 
   * there will be no side effects.
   * 
   * @param roleIdentifier the string id of the role (the <b>unescaped</b> slot name)
   */
  public void addRole(String roleIdentifier, Context cx)
  {
    synchronized(ROLE_LOCK)
    {
      //TODO: Require a SecurityPermission to add a role
      Set<String> set = getRoleIds();
      set.add(roleIdentifier);
      setRoles(delimitRoles(set, true));
    }
  }

  /**
   * Remove a role identifier from this user.
   *
   * @param source the role service the roles came from
   * @param roleIdentifier the string id of the role (the <b>unescaped</b> slot name)
   * @param cx the context
   */
  public void removeRole(BIRoleService source, String roleIdentifier, Context cx)
  {
    synchronized(ROLE_LOCK)
    {
      //TODO: Require a SecurityPermission to remove a role
      Set<String> set = getRoleIds();
      set.remove(roleIdentifier);
      setRoles(delimitRoles(set, true));
    }
  }

  /**
   * Renames a role identifier for this user.
   *
   * @param source the role service the roles came from
   * @param oldIdentifier the old string id of the role (the <b>unescaped</b> slot name)
   * @param newIdentifier the new string id of the role (the <b>unescaped</b> slot name)
   * @param cx the context
   */
  public void renameRole(BIRoleService source, String oldIdentifier, String newIdentifier, Context cx)
  {
    synchronized(ROLE_LOCK)
    {
      //TODO: Require a SecurityPermission to rename a role
      Set<String> set = getRoleIds();
      set.remove(oldIdentifier);
      set.add(newIdentifier);
      setRoles(delimitRoles(set, true));
    }
  }

  @Override
  public IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (property == roles)
    {
      return new IPropertyValidator()
      {

        @Override
        public void validateSet(Validatable validatable, Context context)
        {
          if (context != null && context.getUser() != null)
          {
            String oldRoles = ((BString)validatable.getExistingValue(roles)).getString();
            String newRoles = ((BString)validatable.getProposedValue(roles)).getString();
            checkRoleChange(context.getUser(), oldRoles, newRoles);
          }
        }
      };
    }
    return null;
  }

  public static void checkRoleChange(BUser user, String oldRoles, String newRoles)
  {
    if (user.getPermissions().isSuperUser()) return;

    Set<String> allowedRoles = splitRoles(user.getRoles());
    allowedRoles.addAll(splitRoles(oldRoles));
    if (!allowedRoles.containsAll(splitRoles(newRoles)))
    {
      throw new RuntimeException("cannot set role");
    }
  }

  /**
   * Callback to let the user know their role has changed.
   * @param source The role service on which the role was changed
   * @param roleIdentifier The <b>unescaped</b> role identifier
   * @param cx the context
   */
  public void changedRole(BIRoleService source, String roleIdentifier, Context cx)
  {
    if(hasRole(roleIdentifier)){
      updatePermissions();
    }
  }

  /**
   * Takes an <b>unescaped</b> Set of role names and generates a delimited string from them.
   * @param roles the roles to join
   * @return A String of all the roles joined with the ROLE_DELIMITER
   */
  public static String delimitRoles(Set<String> roles)
  {
    return delimitRoles(roles, false);
  }

  /**
   * Takes an <b>unescaped</b> Set of role names and generates a delimited string from them.
   * @param roles the roles to join
   * @param escape If true, escape the roles in the generated string
   * @return A String of all the roles joined with the ROLE_DELIMITER
   */
  private static String delimitRoles(Set<String> roles, boolean escape)
  {
    StringJoiner roleString = new StringJoiner(ROLE_DELIMITER);

    for(String role : roles)
    {
      if(role != null)
      {
        roleString.add(escape ? SlotPath.escape(role) : role);
      }
    }

    return roleString.toString();
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////  

  /**
   * Cache the RolePermissions in the transient Permissions Property.
   */
  protected void updatePermissions()
  {
    // NCCB-41405 - Ignore any changes when the station is not running. This, because the
    // downstream code will try to get the role service and, failing that, will cache default
    // permissions, which are likely incorrect. Without the check for a running station, this
    // code was inadvertently executing for leased users in workbench and corrupting permissions
    // as a result.
    if (isRunning())
    {
      setPermissions(getRoleBasedPermissions());
    }
  }

  /**
   * Based on the current roles, determine the current Permissions.
   * @return
   */
  public BPermissionsMap getRoleBasedPermissions()
  {
    BRoleService service;
    try
    {
      service = BRoleService.getService();
      if(service != null)
        return getPermissions(service);
      
    }catch(ServiceNotFoundException snfe)
    {
      //getRoleBasedPermissions should not be called remotely.
      //if RoleService is missing on the station, then fallback to BPermissionsMap.DEFAULT
    }

    return BPermissionsMap.DEFAULT;
  }
  
  /**
   * Provides a helper utility for client side mechanisms to 
   * get a preview of what the effective permissions 
   * are likely for the user. This is just to provide a hint to 
   * UI / client side functionality. 
   * 
   * Any permissions actions will be re-verified by the station.
   * Changes made to the station before proposed permissions
   * changes are made will take precedence over the results
   * of this method call. 
   * 
   * It is the caller's responsibility to provide a BIRoleService
   * that is leased. 
   * 
   * @param service
   * @return the likely effective permissions map for this BUser
   */
  public BPermissionsMap getPermissions(BIRoleService service)
  {
    BPermissionsMap rbac = BPermissionsMap.DEFAULT;
    for(BIRole role : getRoleSet(service))
    {
      if(role.getEnabled())
      {
        BPermissionsMap pMap = role.getPermissions();
        if(pMap.isSuperUser())
          return BPermissionsMap.SUPER_USER;

        rbac = rbac.or(pMap);
      }
    }
    return rbac;
  }

  /**
   * Get the permissions map for this user for the specified
   * target protected object.  This method checks for
   * installed permissions managers that can override the
   * user's normal permissions map.
   */
  @SuppressWarnings("deprecation")
  private BPermissionsMap getPermissionsMap(BIProtected target)
  {
    // Give each installed permissions manager a chance
    // to provide an alternate permissions map for the specified
    // target object.  If not alternate map is provided, then
    // the default map defined on the user is used.
    if (getParent() instanceof BUserService)
    {
      BUserService service = (BUserService)getParent();
      PermissionsManager[] pmgrs = AccessController.
        doPrivileged((PrivilegedAction<PermissionsManager[]>)() -> service.getPermissionsManagers());
      for (PermissionsManager pmgr : pmgrs)
      {
        BPermissionsMap altMap = pmgr.getPermissionsMap(this, target);
        if (altMap != null) return altMap;
      }
    }

    return getPermissions();
  }

  /**
   * Unless the specified context provides adminRead permission on
   * this user, we never grant any permissions unless the context
   * specifies this user.  This provides a simple scheme where you
   * can grant opWrite to the user service to allow users to access
   * their own account, but see no one else:
   * <pre>
   *  opRead: have readonly access only to your account
   *  opWrite: have read/write access only to your account
   *  adminRead: have readonly access to all accounts
   *  adminWrite: have full access to all accounts
   * </pre>
   */
  @Override
  public BPermissions getPermissions(Context cx)
  { 
    // use standard component algorithm               
    BPermissions permissions = super.getPermissions(cx);     
    
    // get user associated with context, if no user is
    // in context, then return default algorthm results
    BUser user = null;
    if (cx != null) user = cx.getUser(); 
    if (user == null) return permissions;
    
    // if the user attempting to access another user, 
    // then we need to do some special checks
    if (user != this)
    {
      // if the context doesn't have adminRead on this user, 
      // then hide all users except the user which is logged in
      if (!permissions.hasAdminRead())
        return BPermissions.none;
        
      // if the context doesn't have adminWrite on this user,
      // then remove opWrite too (otherwise rwR would allow
      // you to change other users passwords)
      if (!permissions.hasAdminWrite())
        permissions = BPermissions.make(permissions.getMask() & ~BPermissions.OPERATOR_WRITE);
      // Issue 21979 - Also, prevent a non-super user from having
      // any write access on a super user
      else if (getPermissions().isSuperUser() && !user.getPermissions().isSuperUser())
      {
        permissions = BPermissions.make(permissions.getMask() & ~BPermissions.ADMIN_WRITE);
        permissions = BPermissions.make(permissions.getMask() & ~BPermissions.OPERATOR_WRITE);
      }  
    }
    
    return permissions; 
  }

  /**
   * Get the BPermissions this user has for the specified object. 
   * Note this is a support method for BIProtected to call, clients
   * should check permissions using <code>IProtected.getCategoryPermissions()</code>.
   */
  public BPermissions getPermissionsFor(BIProtected target)
  {
    // get target category mask
    BCategoryMask categoryMask = target.getAppliedCategoryMask();
    
    if(target instanceof BComponent && 
        !((BComponent)target).isMounted() && 
        categoryMask.isNull())
      return BPermissions.all;
    
    // if not super user, allow apps to override permissions map
    BPermissionsMap pmap = getPermissions();
    if (!pmap.isSuperUser()) pmap = getPermissionsMap(target);
    
    // map the categories to permissions
    BPermissions permissions = pmap.getCategoryPermissions(categoryMask);

    // if the target is a component, guarantee op-read up the
    // tree if the user has any permissions under this component
    // by checking the "deep or" mask of the component
    if (!permissions.hasOperatorRead() && 
        (target instanceof BComponent || target instanceof BComponentSpace))
    {                                                                     
      BComponent comp;
      if (target instanceof BComponent)
        comp = (BComponent)target;
      else
        comp = ((BComponentSpace)target).getRootComponent();
        
      BCategoryMask deepOrCat = ((ComponentSlotMap)comp.fw(Fw.SLOT_MAP)).getDeepOrCategoryMask();                
      BPermissions deepOrPerm = pmap.getCategoryPermissions(deepOrCat);
      if (deepOrPerm.getMask() != 0) 
        permissions = BPermissions.operatorRead;
    }
    
    if (target instanceof BComplex)
    { // Let's check for the case where we have a child complex of a BUser.
      // In that case, we should also perform the super user check to follow.
      BComplex parent = ((BComplex)target).getParent();
      if (parent instanceof BUser)
        target = (BIProtected)parent;
    }
    
    if (target instanceof BUser && target != this &&
        !pmap.isSuperUser() && ((BUser)target).getPermissions().isSuperUser())
    {
      permissions = BPermissions.make(permissions.getMask() & ~BPermissions.ADMIN_WRITE);
      permissions = BPermissions.make(permissions.getMask() & ~BPermissions.OPERATOR_WRITE);
    }

    BAbstractAuthenticator authenticator = getAuthenticator();
    if (authenticator instanceof BPasswordAuthenticator
      && ((BPasswordAuthenticator)authenticator).getPasswordConfig().getForceResetAtNextLogin())
    {
      // User must change their password. Restrict permissions to just what they need to be able
      // to authenticate and change their password.
      if (target == getComponentSpace())
      {
        permissions = BPermissions.make(permissions.getMask() & BPermissions.OPERATOR_READ);
      }
      else
      {
        permissions = BPermissions.none;
      }
    }

    return permissions;
  }
  
  /**
   * This method checks if the user has the required permissions 
   * on the target instance.  This is semantically equivalent to 
   * <code>getPermissionsFor(target).has(required)</code>.  If
   * not then a PermissionException is thrown.
   */
  public void check(BIProtected target, BPermissions required)
    throws PermissionException
  {
    BPermissions actual = getPermissionsFor(target);
    if (!actual.has(required))
      throw new PermissionException(actual + " < " + required);
  }
  
  /**
   * Check that this user has the read permission on the
   * specified slot.  This requires operatorRead if the
   * slot has the operator flag set, otherwise the adminRead
   * permission.  If permission is not granted then throw
   * a PermissionException.
   */
  public void checkRead(BComponent target, Slot slot)
  {
    if (Flags.isOperator(target, slot))
      check(target, BPermissions.operatorRead);
    else
      check(target, BPermissions.adminRead);    
  }

  /**
   * Check that this user has the write permission on the
   * specified slot.  This requires operatorWrite if the
   * slot has the operator flag set, otherwise the adminWrite
   * permission.  If permission is not granted then throw
   * a PermissionException.
   */
  public void checkWrite(BComponent target, Slot slot)
  {
    if(target instanceof BUser && 
       target.isMounted() && 
       ((BUser)target).getPermissions().isSuperUser() &&
       !getPermissions().isSuperUser())
      throw new PermissionException("SuperUser required");
    
    if (Flags.isOperator(target, slot))
      check(target, BPermissions.operatorWrite);
    else
      check(target, BPermissions.adminWrite);    
  }

  /**
   * Check that this user has the invoke permission on the
   * specified slot.  This requires operatorInvoke if the
   * slot has the operator flag set, otherwise the adminInvoke
   * permission.  If permission is not granted then throw
   * a PermissionException.
   */
  public void checkInvoke(BComponent target, Slot slot)
  {
    if (Flags.isOperator(target, slot))
      check(target, BPermissions.operatorInvoke);
    else
      check(target, BPermissions.adminInvoke);    
  }                 
    
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:
        fwStarted();
        break;

      case Fw.DESCENDANTS_STARTED:
        fwDescendantsStarted();
        break;

      case Fw.CHANGED:
        BComponentSpace space = getComponentSpace();
        if (space == null || space.fireDirectCallbacks())
          fwChanged((Property)a, (Context)b);

        break;

      case Fw.STATION_STARTED:
        fwStationStarted();
        break;

      case Fw.STOPPED:
        fwStopped();
        break;

      case Fw.PARENTED:
        fwParented((Property) a, (BValue) b);
        break;

      case Fw.UNPARENTED:
        fwUnparented((Property) a, (BValue) b);
        break;
    }

    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    // never let admin user get disabled or demoted
    BValue parent = getParent();
    if (parent instanceof BUserService)
    {
      if (getName().equals("guest"))
      {
        checkGuest();
      }

      if (getLockOut())
      {
        Clock.schedule(this, ((BUserService) parent).getLockOutPeriod(), clearLockOut, null);
      }

      if (Sys.isStationStarted())
      {
        componentChildSubscriber.unsubscribeAll();
        for (BComponent child  : getChildComponents())
        {
          componentChildSubscriber.subscribe(child, CHILD_COMPONENT_SUBSCRIBER_DEPTH);
        }
      }
    }
    else
    {
      if (getLockOut())
      {
        setLockOut(false);
      }
    }

    if (getVersion().isEmpty())
    {
      updateVersion();
    }

    updatePermissions();
    if(pushNotification)
    {
      doPushNotification(getName());
      pushNotification = false;
    }
  }

  private void fwStopped()
  {
    componentChildSubscriber.unsubscribeAll();
  }

  private void fwChanged(Property prop, Context cx)
  {
    // never let admin user get disabled or demoted
    BValue parent = getParent();
    if (parent instanceof BUserService && "guest".equals(getName()))
    {
      checkGuest();
    }

    if(isRunning())
    {
      updatePermissions();

      if (prop.equals(prototypeName))
      {
        setPrototypeVersion("");
      }
    }
  }

  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);

    if (property.equals(authenticationSchemeName) && Sys.isStationStarted())
    {
      BAbstractAuthenticator auth = getAuthenticationScheme().getDefaultAuthenticator();
      if(!getAuthenticator().getType().getTypeSpec().equals(auth.getType().getTypeSpec()))
      {
        BAbstractAuthenticator oldAuth = getAuthenticator();
        // If both authenticators inherit BPasswordAuthenticator (like BGoogleAuthAuthenticator),
        // transfer the password and password config to the new authenticator so the user doesn't
        // have to re-set them.
        if (auth instanceof BPasswordAuthenticator && oldAuth instanceof BPasswordAuthenticator)
        {
          ((BPasswordAuthenticator)auth).setPassword(((BPasswordAuthenticator)oldAuth).getPassword());
          ((BPasswordAuthenticator)auth).setPasswordConfig((BUserPasswordConfiguration)((BPasswordAuthenticator)oldAuth).getPasswordConfig().newCopy(true));
        }
        set(authenticator, auth, authSchemeChanged);

        if(isRunning())
        {
          doPushNotification(getName());
        }
        else if(context == Context.decoding)
        {
          pushNotification = true;
        }
      }
    }

    // Check for property changes that should disable existing user sessions
    if (Sys.isStationStarted() && isMounted())
    {
      // When disabled or expired, invalidate all the user's sessions
      if ( (property.equals(enabled) && !getEnabled())
        || (property.equals(expiration) && isExpired()) )
      {
        AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
          SessionManager.invalidateSuperSessions(BUser.this);
          return null;
        });
      }

      if (property.equals(authenticator))
      {
        BAbstractAuthenticator authenticator = getAuthenticator();
        if (authenticator instanceof BPasswordAuthenticator
          && ((BPasswordAuthenticator)authenticator).getPasswordConfig().getForceResetAtNextLogin())
        {
          AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
            SessionManager.invalidateSuperSessions(BUser.this, LoginFailureCause.PASSWORD_RESET_REQUIRED);
            return null;
          });
        }
      }

      // When concurrent sessions or authentication scheme is changed, disable
      // all but the current session (if applicable)
      if ( (property.equals(allowConcurrentSessions) && !getAllowConcurrentSessions()
        || (property.equals(authenticationSchemeName))) )
      {
        Set<String> excludedIds = new HashSet<>();
        NiagaraSuperSession currentSession = SessionManager.getCurrentNiagaraSuperSession();
        if (currentSession != null)
          excludedIds.add(currentSession.getId());

        AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
          SessionManager.invalidateSuperSessions(BUser.this, excludedIds);
          return null;
        });
      }

      if (property.equals(autoLogoffSettings))
      {
        SessionManager.updateSessionTimeout(this);
      }
    }

    updateUserDefinedPrototypeFlag(property);
  }

  private void fwParented(Property property, BValue newChild)
  {
    if (getParent() instanceof BUserService && isRunning() && newChild instanceof BComponent)
    {
      componentChildSubscriber.subscribe((BComponent) newChild, CHILD_COMPONENT_SUBSCRIBER_DEPTH);
    }
  }

  private void fwUnparented(Property property, BValue oldChild)
  {
    if (oldChild instanceof BComponent && isRunning())
    {
      componentChildSubscriber.unsubscribe((BComponent) oldChild);
    }
  }

  private void fwDescendantsStarted()
  {
  }

  private void fwStationStarted()
  {
    // In Niagara 4.4, the auto logoff settings in a BWebProfileConfig mixin needed to be
    // transferred to the new autoLogoffSettings property.  This transfer is occurring at station
    // started so we can log audit events if the auto logoff period is coerced.  This code should be
    // removed in Niagara 5.0 if a migration tool is required to upgrade stations.
    if (getTypeVersion() == 0)
    {
      transferAutoLogoffSettings(this);
      setTypeVersion(1);
    }

    if (getParent() instanceof BUserService)
    {
      componentChildSubscriber.unsubscribeAll();
      for (BComponent child : getChildComponents())
      {
        componentChildSubscriber.subscribe(child, CHILD_COMPONENT_SUBSCRIBER_DEPTH);
      }
    }
  }

  @Override
  public String toString(Context cx)
  {
    return getUsername();
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("user.png");

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Update the version for this user.
   */
  public void doSetModified()
  {
    BComponent parent = (BComponent)getParent();
    if (parent instanceof BUserService)
      ((BUserService)parent).setModified(this);
    else
      updateVersion();
  }

  /**
   * Update the version.
   */
  public void updateVersion()
  {
    String station = "";
    if (Sys.getStation() != null)
      station = Sys.getStation().getStationName();
    setVersion(station + ':' + System.currentTimeMillis());
  }

////////////////////////////////////////////////////////////////
// Static authentication/authorization related methods
////////////////////////////////////////////////////////////////

  /**
   * Looks for a BUser in the supplied {@link Subject Subject}
   * @param subject The subject in which we're looking for a BUser
   * @return A BUser, or null if no BUser was found or if subject is null
   */
  public static BUser getUserFromSubject(Subject subject)
  {
    if (subject != null)
    {
      Set<BUser> principals = subject.getPrincipals(BUser.class);
      for (BUser user : principals)
      {
        if (user != null)
          return user;
      }
    }

    return null;
  }

  /**
   * Returns the authenticated user for the current session. This only applies station side.
   *
   * NOTE THAT IF THIS METHOD IS CALLED WITHIN A DO_PRIVILEGED BLOCK, IT WILL RETURN NULL.
   * @return The authenticated user for the current session. Returns null
   * if there is no user authenticated (e.g. client side)
   *
   */
  public static BUser getCurrentAuthenticatedUser()
  {
    Subject subject = SecurityManagerUtil.getCurrentAuthenticatedSubject();
    if (subject != null)
      return getUserFromSubject(subject);

    return null;
  }

  private void doPushNotification(String name)
  {
    BNotification notify = new BNotification();
    String title = Lexicon.make("baja").getText("user.authenticationScheme.changed.title");
    notify.add("title", BString.make(title));
    String message = Lexicon.make("baja").getText("user.authenticationScheme.changed.text", name);
    notify.add("message", BString.make(message));
    notify.raise(false);
  }

  /**
   * Sets the USER_DEFINED_1 flag on the supplied property if all the following apply:
   * 1) The user has a prototype
   * 2) The prototype corresponds to an existing BUserPrototype in the the User Prototypes folder
   * 3) The property corresponds to a property on the BUserPrototype
   * 4) The BUserPrototypeProperty is overridable
   *
   * Setting the USER_DEFINED_1 flag ensures that the property change will not get overridden when
   * the prototype is reapplied to the user on the next login.
   *
   * @param property The property on which to set the USER_DEFINED_1 flag.
   */
  private void updateUserDefinedPrototypeFlag(Property property)
  {
    BComplex parent = getParent();
    if (parent instanceof BUserService)
    {
      String prototypeName = getPrototypeName();
      BValue prototype = ((BUserService)parent).getUserPrototypes().get(prototypeName);
      if (prototype instanceof BUserPrototype )
      {
        BValue prop = ((BUserPrototype) prototype).get(property.getName());
        if (prop instanceof BUserPrototypeProperty && ((BUserPrototypeProperty)prop).getOverridable())
        {
          setFlags(property, getFlags(property) | Flags.USER_DEFINED_1);
        }
      }
    }
  }


//region Inner Classes

  /**
   * Subscribes to component children of this BUser, to ensure that we get callbacks when
   * descendant properties are changed and can set the USER_DEFINED_1 flag for use with
   * BUserPrototypes.
   */
  private static class ComponentChildSubscriber
    extends Subscriber
  {
    @Override
    public void event(BComponentEvent event)
    {
      if (event.getId() == BComponentEvent.PROPERTY_CHANGED
        || event.getId() == BComponentEvent.PROPERTY_ADDED
        || event.getId() == BComponentEvent.PROPERTY_REMOVED
        || event.getId() == BComponentEvent.PROPERTY_RENAMED)
      {
        BComplex child = event.getSourceComponent();
        BComplex parent = child.getParent();
        while (parent != null && !(parent instanceof BUser))
        {
          child = parent;
          parent = parent.getParent();
        }

        if (parent != null)
        {
          ((BUser) parent).updateUserDefinedPrototypeFlag(child.getPropertyInParent());
        }
      }
    }
  }
//endregion Inner Classes

  /**
   * @since Niagara 4.10u7
   */
  @Override
  public final Set<Slot> getUnlinkableSourceSlots(Context context)
  {
    return UNLINKABLE_SLOTS;
  }

  /**
   * @since Niagara 4.10u7
   */
  @Override
  public final Set<Slot> getUnlinkableTargetSlots(Context context)
  {
    return UNLINKABLE_SLOTS;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Unlinkable Slots on BUser are specified here
   *
   * @since Niagara 4.10u7
   */
  private static final Set<Slot> UNLINKABLE_SLOTS = Collections.singleton(roles);

  private boolean pushNotification = false;
  private Clock.Ticket lockoutTicket;
  static final Logger log = Logger.getLogger("sys.service");
  static final BOrd DEFAULT_HOME_PAGE = BOrd.make("station:|slot:/");
  private static volatile boolean checkLicense = true;
  private static boolean guestLicensed;
  private static final Context authSchemeChanged = new BasicContext()
  {
    public boolean equals(Object obj) { return this == obj; }
    public int hashCode() { return toString().hashCode(); }
    public String toString() { return "Context.authSchemeChanged"; }
  };
  private final ComponentChildSubscriber componentChildSubscriber = new ComponentChildSubscriber();
  {
    componentChildSubscriber.setMask(BComponentEventMask.make(
      (0x01 << BComponentEvent.PROPERTY_CHANGED) |
        (0x01 << BComponentEvent.PROPERTY_ADDED) |
        (0x01 << BComponentEvent.PROPERTY_REMOVED) |
        (0x01 << BComponentEvent.PROPERTY_RENAMED)));
  }
  private static final int CHILD_COMPONENT_SUBSCRIBER_DEPTH = Integer.MAX_VALUE;
  private final Object ROLE_LOCK = new Object();
  public static final String ROLE_DELIMITER = ",";


  Queue authFailTimes;
}
