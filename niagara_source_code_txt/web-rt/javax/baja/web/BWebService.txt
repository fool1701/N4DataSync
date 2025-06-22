/*
 * Copyright (c) 2014. Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import static javax.baja.security.dashboard.BSecurityItemStatus.securityStatusOK;
import static javax.baja.security.dashboard.BSecurityItemStatus.securityStatusWarning;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.firewall.BServerPort;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BCertificateAliasAndPassword;
import javax.baja.security.BPassword;
import javax.baja.security.crypto.BSslTlsEnum;
import javax.baja.security.crypto.BTlsCipherSuiteGroup;
import javax.baja.security.crypto.CertManagerFactory;
import javax.baja.security.dashboard.BISecurityDashboardProvider;
import javax.baja.security.dashboard.BSecurityItemStatus;
import javax.baja.security.dashboard.LexiconFormatInfo;
import javax.baja.security.dashboard.SecurityDashboardItem;
import javax.baja.security.dashboard.SecurityDashboardItemBuilder;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUserService;
import javax.baja.util.BFormat;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.BTypeSpec;
import javax.baja.web.http.BHttpHeaderProviders;

import com.tridium.crypto.core.cert.CertUtils;
import com.tridium.crypto.core.cert.KeyPurpose;
import com.tridium.crypto.core.io.ICoreKeyStore;
import com.tridium.nre.firewall.IpProtocol;
import com.tridium.security.BCertificateStatusEnum;
import com.tridium.security.BISecurityInfoSource;
import com.tridium.security.BSecurityInfo;
import com.tridium.security.BServerCertificateHealth;
import com.tridium.sys.Nre;
import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;
import com.tridium.util.ArrayUtil;
import com.tridium.util.CertAliasCasePropertyValidator;
import com.tridium.web.BAdditionalHttpsCert;
import com.tridium.web.BAdditionalHttpsCerts;
import com.tridium.web.BHostHeaderValidationSettings;
import com.tridium.web.BHostnameRedirectSettings;
import com.tridium.web.BSameSiteEnum;
import com.tridium.web.WebUtil;
import com.tridium.web.rpc.BUserDataConfig;
import com.tridium.web.servlets.UnauthenticatedCache;
import com.tridium.web.servlets.WebStartServlet;
import com.tridium.web.warmup.BWebWarmupConfig;

/**
 * BWebService encapsulates access to the HTTP {@link BWebServer web server}.
 * <p>
 * Beginning in Niagara4, the web service is simply a general configuration container. The actual
 * web server implementation is now pluggable. See {@link BWebServer} for details.
 *
 * @author Matthew Giannini
 */
@NiagaraType
/*
 Unsecured HTTP port.
 */
@NiagaraProperty(
  name = "httpPort",
  type = "BServerPort",
  defaultValue = "new BServerPort(80, IpProtocol.TCP)"
)
/*
 Enable unsecured connections to the web server on the http port?
 */
@NiagaraProperty(
  name = "httpEnabled",
  type = "boolean",
  defaultValue = "true",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Secured HTTPS port.
 */
@NiagaraProperty(
  name = "httpsPort",
  type = "BServerPort",
  defaultValue = "new BServerPort(443, IpProtocol.TCP)"
)
/*
 Enable secure connections to the web server on the https port?
 */
@NiagaraProperty(
  name = "httpsEnabled",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Redirect all http requests to the secure https port?
 */
@NiagaraProperty(
  name = "httpsOnly",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The minimum security protocol to use for SSL.
 */
@NiagaraProperty(
  name = "httpsMinProtocol",
  type = "BSslTlsEnum",
  defaultValue = "BSslTlsEnum.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The cipher suite group to use for the tls connections.
 */
@NiagaraProperty(
  name = "cipherSuiteGroup",
  type = "BTlsCipherSuiteGroup",
  defaultValue = "BTlsCipherSuiteGroup.recommended",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The alias of the cert to use. This property is no longer used and migrates to the
 certAliasAndPassword slot starting with Niagara 4.13
 */
@NiagaraProperty(
  name = "httpsCert",
  type = "String",
  defaultValue = "CertUtils.LEGACY_CERT_ALIAS",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "BString.make(\"workbench:CertificateAliasFE\")"),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "BString.make(\"webEditors:CertificateAliasEditor\")"),
    @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)"),
    @Facet("BFacets.make(\"purposeId\", \"SERVER_CERT\")")
  },
  deprecated = true
)
/*
 The certificate and optional password to use for tls connections
 @since Niagara 4.13
 */
@NiagaraProperty(
  name = "mainCertAliasAndPassword",
  type = "BCertificateAliasAndPassword",
  defaultValue = "BCertificateAliasAndPassword.DEFAULT",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Provide a status regarding the cert in case it can't use the configured one
 @since Niagara 4.13
 */
@NiagaraProperty(
  name = "serverCertificateHealth",
  type = "BServerCertificateHealth",
  defaultValue = "new BServerCertificateHealth()",
  flags = Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY
)
/*
 Require an HTTPS connection when setting passwords
 */
@NiagaraProperty(
  name = "requireHttpsForPasswords",
  type = "boolean",
  defaultValue = "true",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 The xframe option for preventing XFS attacks.
 */
@NiagaraProperty(
  name = "xFrameOptions",
  type = "BXFrameOptionsEnum",
  defaultValue = "BXFrameOptionsEnum.sameorigin",
  flags = Flags.HIDDEN | Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Makes the user id cookie persistent
 */
@NiagaraProperty(
  name = "rememberUserIdCookie",
  type = "boolean",
  defaultValue = "true"
)
/*
 Configure SameSite attribute for cookies
 */
@NiagaraProperty(
  name = "sameSite",
  type = "BSameSiteEnum",
  defaultValue = "BSameSiteEnum.lax",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Disables autocomplete on the username field of the login page if false
 */
@NiagaraProperty(
  name = "allowUsernameAutocomplete",
  type = "boolean",
  defaultValue = "true"
)
/*
 The template for the browser login page.  If null, the default template is used.
 */
@NiagaraProperty(
  name = "loginTemplate",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.NULL",
  facets = {
    @Facet(name = "BFacets.ALLOW_NULL", value = "BBoolean.TRUE"),
    @Facet(name = "BFacets.TARGET_TYPE", value = "BString.make(\"web:LoginTemplate\")")
  }
)
/*
 Enable gzip compression?
 */
@NiagaraProperty(
  name = "gzipEnabled",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
/*
 If the web server supports request logging, then it should write logs to this directory.
 */
@NiagaraProperty(
  name = "logFileDirectory",
  type = "BOrd",
  defaultValue = "BOrd.make(\"file:^^webLogs\")",
  flags = Flags.READONLY
)
/*
 Supported client environments.
 */
@NiagaraProperty(
  name = "clientEnvironments",
  type = "BClientEnvironments",
  defaultValue = "new BClientEnvironments()"
)
/*
 Will show exception stack traces in error responses when available if set to true.
 */
@NiagaraProperty(
  name = "showStackTrace",
  type = "boolean",
  defaultValue = "false"
)
/*
 If files aren't available locally, enable jx browser JAR files to be loaded from an external URI.
 @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 */
@NiagaraProperty(
  name = "appletModuleCachingType",
  type = "BAppletModuleCachingType",
  defaultValue = "BAppletModuleCachingType.host",
  deprecated = true
)
/*
 Configuration properties for clients using Java Web Start.
 @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 */
@NiagaraProperty(
  name = "webStartConfig",
  type = "BWebStartConfig",
  defaultValue = "new BWebStartConfig()",
  deprecated = true
)
/*
 Configuration properties for clients using file resources that want additional caching.
 */
@NiagaraProperty(
  name = "cacheConfig",
  type = "BCacheConfig",
  defaultValue = "new BCacheConfig()"
)
/*
 Configuration for Web Warmup
 */
@NiagaraProperty(
  name = "warmupConfig",
  type = "BWebWarmupConfig",
  defaultValue = "new BWebWarmupConfig()",
  flags = Flags.NON_CRITICAL
)
/*
 Configuration for hostname redirection
 */
@NiagaraProperty(
  name = "hostnameRedirectSettings",
  type = "BHostnameRedirectSettings",
  defaultValue = "new BHostnameRedirectSettings()"
)
/*
 HTTP header configuration
 */
@NiagaraProperty(
  name = "httpHeaderProviders",
  type = "BHttpHeaderProviders",
  defaultValue = "new BHttpHeaderProviders()"
)
@NiagaraProperty(
  name = "hostHeaderValidationSettings",
  type = "BHostHeaderValidationSettings",
  defaultValue = "new BHostHeaderValidationSettings()"
)
@NiagaraAction(
  name = "resetAllConnections",
  flags = Flags.CONFIRM_REQUIRED
)
@SuppressWarnings("deprecation")
public final class BWebService
  extends BAbstractService
  implements BIRestrictedComponent, BISecurityInfoSource, BISecurityDashboardProvider
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebService(659648700)1.0$ @*/
/* Generated Tue Mar 28 10:50:10 EDT 2023 by Slot-o-Matic (c) Tridium, Inc. 2012-2023 */

  //region Property "httpPort"

  /**
   * Slot for the {@code httpPort} property.
   * Unsecured HTTP port.
   * @see #getHttpPort
   * @see #setHttpPort
   */
  public static final Property httpPort = newProperty(0, new BServerPort(80, IpProtocol.TCP), null);

  /**
   * Get the {@code httpPort} property.
   * Unsecured HTTP port.
   * @see #httpPort
   */
  public BServerPort getHttpPort() { return (BServerPort)get(httpPort); }

  /**
   * Set the {@code httpPort} property.
   * Unsecured HTTP port.
   * @see #httpPort
   */
  public void setHttpPort(BServerPort v) { set(httpPort, v, null); }

  //endregion Property "httpPort"

  //region Property "httpEnabled"

  /**
   * Slot for the {@code httpEnabled} property.
   * Enable unsecured connections to the web server on the http port?
   * @see #getHttpEnabled
   * @see #setHttpEnabled
   */
  public static final Property httpEnabled = newProperty(0, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code httpEnabled} property.
   * Enable unsecured connections to the web server on the http port?
   * @see #httpEnabled
   */
  public boolean getHttpEnabled() { return getBoolean(httpEnabled); }

  /**
   * Set the {@code httpEnabled} property.
   * Enable unsecured connections to the web server on the http port?
   * @see #httpEnabled
   */
  public void setHttpEnabled(boolean v) { setBoolean(httpEnabled, v, null); }

  //endregion Property "httpEnabled"

  //region Property "httpsPort"

  /**
   * Slot for the {@code httpsPort} property.
   * Secured HTTPS port.
   * @see #getHttpsPort
   * @see #setHttpsPort
   */
  public static final Property httpsPort = newProperty(0, new BServerPort(443, IpProtocol.TCP), null);

  /**
   * Get the {@code httpsPort} property.
   * Secured HTTPS port.
   * @see #httpsPort
   */
  public BServerPort getHttpsPort() { return (BServerPort)get(httpsPort); }

  /**
   * Set the {@code httpsPort} property.
   * Secured HTTPS port.
   * @see #httpsPort
   */
  public void setHttpsPort(BServerPort v) { set(httpsPort, v, null); }

  //endregion Property "httpsPort"

  //region Property "httpsEnabled"

  /**
   * Slot for the {@code httpsEnabled} property.
   * Enable secure connections to the web server on the https port?
   * @see #getHttpsEnabled
   * @see #setHttpsEnabled
   */
  public static final Property httpsEnabled = newProperty(0, false, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code httpsEnabled} property.
   * Enable secure connections to the web server on the https port?
   * @see #httpsEnabled
   */
  public boolean getHttpsEnabled() { return getBoolean(httpsEnabled); }

  /**
   * Set the {@code httpsEnabled} property.
   * Enable secure connections to the web server on the https port?
   * @see #httpsEnabled
   */
  public void setHttpsEnabled(boolean v) { setBoolean(httpsEnabled, v, null); }

  //endregion Property "httpsEnabled"

  //region Property "httpsOnly"

  /**
   * Slot for the {@code httpsOnly} property.
   * Redirect all http requests to the secure https port?
   * @see #getHttpsOnly
   * @see #setHttpsOnly
   */
  public static final Property httpsOnly = newProperty(0, false, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code httpsOnly} property.
   * Redirect all http requests to the secure https port?
   * @see #httpsOnly
   */
  public boolean getHttpsOnly() { return getBoolean(httpsOnly); }

  /**
   * Set the {@code httpsOnly} property.
   * Redirect all http requests to the secure https port?
   * @see #httpsOnly
   */
  public void setHttpsOnly(boolean v) { setBoolean(httpsOnly, v, null); }

  //endregion Property "httpsOnly"

  //region Property "httpsMinProtocol"

  /**
   * Slot for the {@code httpsMinProtocol} property.
   * The minimum security protocol to use for SSL.
   * @see #getHttpsMinProtocol
   * @see #setHttpsMinProtocol
   */
  public static final Property httpsMinProtocol = newProperty(0, BSslTlsEnum.DEFAULT, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code httpsMinProtocol} property.
   * The minimum security protocol to use for SSL.
   * @see #httpsMinProtocol
   */
  public BSslTlsEnum getHttpsMinProtocol() { return (BSslTlsEnum)get(httpsMinProtocol); }

  /**
   * Set the {@code httpsMinProtocol} property.
   * The minimum security protocol to use for SSL.
   * @see #httpsMinProtocol
   */
  public void setHttpsMinProtocol(BSslTlsEnum v) { set(httpsMinProtocol, v, null); }

  //endregion Property "httpsMinProtocol"

  //region Property "cipherSuiteGroup"

  /**
   * Slot for the {@code cipherSuiteGroup} property.
   * The cipher suite group to use for the tls connections.
   * @see #getCipherSuiteGroup
   * @see #setCipherSuiteGroup
   */
  public static final Property cipherSuiteGroup = newProperty(0, BTlsCipherSuiteGroup.recommended, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code cipherSuiteGroup} property.
   * The cipher suite group to use for the tls connections.
   * @see #cipherSuiteGroup
   */
  public BTlsCipherSuiteGroup getCipherSuiteGroup() { return (BTlsCipherSuiteGroup)get(cipherSuiteGroup); }

  /**
   * Set the {@code cipherSuiteGroup} property.
   * The cipher suite group to use for the tls connections.
   * @see #cipherSuiteGroup
   */
  public void setCipherSuiteGroup(BTlsCipherSuiteGroup v) { set(cipherSuiteGroup, v, null); }

  //endregion Property "cipherSuiteGroup"

  //region Property "httpsCert"

  /**
   * Slot for the {@code httpsCert} property.
   * The alias of the cert to use. This property is no longer used and migrates to the
   * certAliasAndPassword slot starting with Niagara 4.13
   * @see #getHttpsCert
   * @see #setHttpsCert
   */
  @Deprecated
  public static final Property httpsCert = newProperty(0, CertUtils.LEGACY_CERT_ALIAS, BFacets.make(BFacets.make(BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, BString.make("workbench:CertificateAliasFE")), BFacets.make(BFacets.UX_FIELD_EDITOR, BString.make("webEditors:CertificateAliasEditor"))), BFacets.make(BFacets.SECURITY, BBoolean.TRUE)), BFacets.make("purposeId", "SERVER_CERT")));

  /**
   * Get the {@code httpsCert} property.
   * The alias of the cert to use. This property is no longer used and migrates to the
   * certAliasAndPassword slot starting with Niagara 4.13
   * @see #httpsCert
   */
  @Deprecated
  public String getHttpsCert() { return getString(httpsCert); }

  /**
   * Set the {@code httpsCert} property.
   * The alias of the cert to use. This property is no longer used and migrates to the
   * certAliasAndPassword slot starting with Niagara 4.13
   * @see #httpsCert
   */
  @Deprecated
  public void setHttpsCert(String v) { setString(httpsCert, v, null); }

  //endregion Property "httpsCert"

  //region Property "mainCertAliasAndPassword"

  /**
   * Slot for the {@code mainCertAliasAndPassword} property.
   * The certificate and optional password to use for tls connections
   * @since Niagara 4.13
   * @see #getMainCertAliasAndPassword
   * @see #setMainCertAliasAndPassword
   */
  public static final Property mainCertAliasAndPassword = newProperty(Flags.HIDDEN, BCertificateAliasAndPassword.DEFAULT, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code mainCertAliasAndPassword} property.
   * The certificate and optional password to use for tls connections
   * @since Niagara 4.13
   * @see #mainCertAliasAndPassword
   */
  public BCertificateAliasAndPassword getMainCertAliasAndPassword() { return (BCertificateAliasAndPassword)get(mainCertAliasAndPassword); }

  /**
   * Set the {@code mainCertAliasAndPassword} property.
   * The certificate and optional password to use for tls connections
   * @since Niagara 4.13
   * @see #mainCertAliasAndPassword
   */
  public void setMainCertAliasAndPassword(BCertificateAliasAndPassword v) { set(mainCertAliasAndPassword, v, null); }

  //endregion Property "mainCertAliasAndPassword"

  //region Property "serverCertificateHealth"

  /**
   * Slot for the {@code serverCertificateHealth} property.
   * Provide a status regarding the cert in case it can't use the configured one
   * @since Niagara 4.13
   * @see #getServerCertificateHealth
   * @see #setServerCertificateHealth
   */
  public static final Property serverCertificateHealth = newProperty(Flags.HIDDEN | Flags.TRANSIENT | Flags.READONLY, new BServerCertificateHealth(), null);

  /**
   * Get the {@code serverCertificateHealth} property.
   * Provide a status regarding the cert in case it can't use the configured one
   * @since Niagara 4.13
   * @see #serverCertificateHealth
   */
  public BServerCertificateHealth getServerCertificateHealth() { return (BServerCertificateHealth)get(serverCertificateHealth); }

  /**
   * Set the {@code serverCertificateHealth} property.
   * Provide a status regarding the cert in case it can't use the configured one
   * @since Niagara 4.13
   * @see #serverCertificateHealth
   */
  public void setServerCertificateHealth(BServerCertificateHealth v) { set(serverCertificateHealth, v, null); }

  //endregion Property "serverCertificateHealth"

  //region Property "requireHttpsForPasswords"

  /**
   * Slot for the {@code requireHttpsForPasswords} property.
   * Require an HTTPS connection when setting passwords
   * @see #getRequireHttpsForPasswords
   * @see #setRequireHttpsForPasswords
   */
  public static final Property requireHttpsForPasswords = newProperty(0, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code requireHttpsForPasswords} property.
   * Require an HTTPS connection when setting passwords
   * @see #requireHttpsForPasswords
   */
  public boolean getRequireHttpsForPasswords() { return getBoolean(requireHttpsForPasswords); }

  /**
   * Set the {@code requireHttpsForPasswords} property.
   * Require an HTTPS connection when setting passwords
   * @see #requireHttpsForPasswords
   */
  public void setRequireHttpsForPasswords(boolean v) { setBoolean(requireHttpsForPasswords, v, null); }

  //endregion Property "requireHttpsForPasswords"

  //region Property "xFrameOptions"

  /**
   * Slot for the {@code xFrameOptions} property.
   * The xframe option for preventing XFS attacks.
   * @see #getXFrameOptions
   * @see #setXFrameOptions
   */
  public static final Property xFrameOptions = newProperty(Flags.HIDDEN | Flags.READONLY, BXFrameOptionsEnum.sameorigin, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code xFrameOptions} property.
   * The xframe option for preventing XFS attacks.
   * @see #xFrameOptions
   */
  public BXFrameOptionsEnum getXFrameOptions() { return (BXFrameOptionsEnum)get(xFrameOptions); }

  /**
   * Set the {@code xFrameOptions} property.
   * The xframe option for preventing XFS attacks.
   * @see #xFrameOptions
   */
  public void setXFrameOptions(BXFrameOptionsEnum v) { set(xFrameOptions, v, null); }

  //endregion Property "xFrameOptions"

  //region Property "rememberUserIdCookie"

  /**
   * Slot for the {@code rememberUserIdCookie} property.
   * Makes the user id cookie persistent
   * @see #getRememberUserIdCookie
   * @see #setRememberUserIdCookie
   */
  public static final Property rememberUserIdCookie = newProperty(0, true, null);

  /**
   * Get the {@code rememberUserIdCookie} property.
   * Makes the user id cookie persistent
   * @see #rememberUserIdCookie
   */
  public boolean getRememberUserIdCookie() { return getBoolean(rememberUserIdCookie); }

  /**
   * Set the {@code rememberUserIdCookie} property.
   * Makes the user id cookie persistent
   * @see #rememberUserIdCookie
   */
  public void setRememberUserIdCookie(boolean v) { setBoolean(rememberUserIdCookie, v, null); }

  //endregion Property "rememberUserIdCookie"

  //region Property "sameSite"

  /**
   * Slot for the {@code sameSite} property.
   * Configure SameSite attribute for cookies
   * @see #getSameSite
   * @see #setSameSite
   */
  public static final Property sameSite = newProperty(0, BSameSiteEnum.lax, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code sameSite} property.
   * Configure SameSite attribute for cookies
   * @see #sameSite
   */
  public BSameSiteEnum getSameSite() { return (BSameSiteEnum)get(sameSite); }

  /**
   * Set the {@code sameSite} property.
   * Configure SameSite attribute for cookies
   * @see #sameSite
   */
  public void setSameSite(BSameSiteEnum v) { set(sameSite, v, null); }

  //endregion Property "sameSite"

  //region Property "allowUsernameAutocomplete"

  /**
   * Slot for the {@code allowUsernameAutocomplete} property.
   * Disables autocomplete on the username field of the login page if false
   * @see #getAllowUsernameAutocomplete
   * @see #setAllowUsernameAutocomplete
   */
  public static final Property allowUsernameAutocomplete = newProperty(0, true, null);

  /**
   * Get the {@code allowUsernameAutocomplete} property.
   * Disables autocomplete on the username field of the login page if false
   * @see #allowUsernameAutocomplete
   */
  public boolean getAllowUsernameAutocomplete() { return getBoolean(allowUsernameAutocomplete); }

  /**
   * Set the {@code allowUsernameAutocomplete} property.
   * Disables autocomplete on the username field of the login page if false
   * @see #allowUsernameAutocomplete
   */
  public void setAllowUsernameAutocomplete(boolean v) { setBoolean(allowUsernameAutocomplete, v, null); }

  //endregion Property "allowUsernameAutocomplete"

  //region Property "loginTemplate"

  /**
   * Slot for the {@code loginTemplate} property.
   * The template for the browser login page.  If null, the default template is used.
   * @see #getLoginTemplate
   * @see #setLoginTemplate
   */
  public static final Property loginTemplate = newProperty(0, BTypeSpec.NULL, BFacets.make(BFacets.make(BFacets.ALLOW_NULL, BBoolean.TRUE), BFacets.make(BFacets.TARGET_TYPE, BString.make("web:LoginTemplate"))));

  /**
   * Get the {@code loginTemplate} property.
   * The template for the browser login page.  If null, the default template is used.
   * @see #loginTemplate
   */
  public BTypeSpec getLoginTemplate() { return (BTypeSpec)get(loginTemplate); }

  /**
   * Set the {@code loginTemplate} property.
   * The template for the browser login page.  If null, the default template is used.
   * @see #loginTemplate
   */
  public void setLoginTemplate(BTypeSpec v) { set(loginTemplate, v, null); }

  //endregion Property "loginTemplate"

  //region Property "gzipEnabled"

  /**
   * Slot for the {@code gzipEnabled} property.
   * Enable gzip compression?
   * @see #getGzipEnabled
   * @see #setGzipEnabled
   */
  public static final Property gzipEnabled = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code gzipEnabled} property.
   * Enable gzip compression?
   * @see #gzipEnabled
   */
  public boolean getGzipEnabled() { return getBoolean(gzipEnabled); }

  /**
   * Set the {@code gzipEnabled} property.
   * Enable gzip compression?
   * @see #gzipEnabled
   */
  public void setGzipEnabled(boolean v) { setBoolean(gzipEnabled, v, null); }

  //endregion Property "gzipEnabled"

  //region Property "logFileDirectory"

  /**
   * Slot for the {@code logFileDirectory} property.
   * If the web server supports request logging, then it should write logs to this directory.
   * @see #getLogFileDirectory
   * @see #setLogFileDirectory
   */
  public static final Property logFileDirectory = newProperty(Flags.READONLY, BOrd.make("file:^^webLogs"), null);

  /**
   * Get the {@code logFileDirectory} property.
   * If the web server supports request logging, then it should write logs to this directory.
   * @see #logFileDirectory
   */
  public BOrd getLogFileDirectory() { return (BOrd)get(logFileDirectory); }

  /**
   * Set the {@code logFileDirectory} property.
   * If the web server supports request logging, then it should write logs to this directory.
   * @see #logFileDirectory
   */
  public void setLogFileDirectory(BOrd v) { set(logFileDirectory, v, null); }

  //endregion Property "logFileDirectory"

  //region Property "clientEnvironments"

  /**
   * Slot for the {@code clientEnvironments} property.
   * Supported client environments.
   * @see #getClientEnvironments
   * @see #setClientEnvironments
   */
  public static final Property clientEnvironments = newProperty(0, new BClientEnvironments(), null);

  /**
   * Get the {@code clientEnvironments} property.
   * Supported client environments.
   * @see #clientEnvironments
   */
  public BClientEnvironments getClientEnvironments() { return (BClientEnvironments)get(clientEnvironments); }

  /**
   * Set the {@code clientEnvironments} property.
   * Supported client environments.
   * @see #clientEnvironments
   */
  public void setClientEnvironments(BClientEnvironments v) { set(clientEnvironments, v, null); }

  //endregion Property "clientEnvironments"

  //region Property "showStackTrace"

  /**
   * Slot for the {@code showStackTrace} property.
   * Will show exception stack traces in error responses when available if set to true.
   * @see #getShowStackTrace
   * @see #setShowStackTrace
   */
  public static final Property showStackTrace = newProperty(0, false, null);

  /**
   * Get the {@code showStackTrace} property.
   * Will show exception stack traces in error responses when available if set to true.
   * @see #showStackTrace
   */
  public boolean getShowStackTrace() { return getBoolean(showStackTrace); }

  /**
   * Set the {@code showStackTrace} property.
   * Will show exception stack traces in error responses when available if set to true.
   * @see #showStackTrace
   */
  public void setShowStackTrace(boolean v) { setBoolean(showStackTrace, v, null); }

  //endregion Property "showStackTrace"

  //region Property "appletModuleCachingType"

  /**
   * Slot for the {@code appletModuleCachingType} property.
   * If files aren't available locally, enable jx browser JAR files to be loaded from an external URI.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #getAppletModuleCachingType
   * @see #setAppletModuleCachingType
   */
  @Deprecated
  public static final Property appletModuleCachingType = newProperty(0, BAppletModuleCachingType.host, null);

  /**
   * Get the {@code appletModuleCachingType} property.
   * If files aren't available locally, enable jx browser JAR files to be loaded from an external URI.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #appletModuleCachingType
   */
  @Deprecated
  public BAppletModuleCachingType getAppletModuleCachingType() { return (BAppletModuleCachingType)get(appletModuleCachingType); }

  /**
   * Set the {@code appletModuleCachingType} property.
   * If files aren't available locally, enable jx browser JAR files to be loaded from an external URI.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #appletModuleCachingType
   */
  @Deprecated
  public void setAppletModuleCachingType(BAppletModuleCachingType v) { set(appletModuleCachingType, v, null); }

  //endregion Property "appletModuleCachingType"

  //region Property "webStartConfig"

  /**
   * Slot for the {@code webStartConfig} property.
   * Configuration properties for clients using Java Web Start.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #getWebStartConfig
   * @see #setWebStartConfig
   */
  @Deprecated
  public static final Property webStartConfig = newProperty(0, new BWebStartConfig(), null);

  /**
   * Get the {@code webStartConfig} property.
   * Configuration properties for clients using Java Web Start.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #webStartConfig
   */
  @Deprecated
  public BWebStartConfig getWebStartConfig() { return (BWebStartConfig)get(webStartConfig); }

  /**
   * Set the {@code webStartConfig} property.
   * Configuration properties for clients using Java Web Start.
   * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
   * @see #webStartConfig
   */
  @Deprecated
  public void setWebStartConfig(BWebStartConfig v) { set(webStartConfig, v, null); }

  //endregion Property "webStartConfig"

  //region Property "cacheConfig"

  /**
   * Slot for the {@code cacheConfig} property.
   * Configuration properties for clients using file resources that want additional caching.
   * @see #getCacheConfig
   * @see #setCacheConfig
   */
  public static final Property cacheConfig = newProperty(0, new BCacheConfig(), null);

  /**
   * Get the {@code cacheConfig} property.
   * Configuration properties for clients using file resources that want additional caching.
   * @see #cacheConfig
   */
  public BCacheConfig getCacheConfig() { return (BCacheConfig)get(cacheConfig); }

  /**
   * Set the {@code cacheConfig} property.
   * Configuration properties for clients using file resources that want additional caching.
   * @see #cacheConfig
   */
  public void setCacheConfig(BCacheConfig v) { set(cacheConfig, v, null); }

  //endregion Property "cacheConfig"

  //region Property "warmupConfig"

  /**
   * Slot for the {@code warmupConfig} property.
   * Configuration for Web Warmup
   * @see #getWarmupConfig
   * @see #setWarmupConfig
   */
  public static final Property warmupConfig = newProperty(Flags.NON_CRITICAL, new BWebWarmupConfig(), null);

  /**
   * Get the {@code warmupConfig} property.
   * Configuration for Web Warmup
   * @see #warmupConfig
   */
  public BWebWarmupConfig getWarmupConfig() { return (BWebWarmupConfig)get(warmupConfig); }

  /**
   * Set the {@code warmupConfig} property.
   * Configuration for Web Warmup
   * @see #warmupConfig
   */
  public void setWarmupConfig(BWebWarmupConfig v) { set(warmupConfig, v, null); }

  //endregion Property "warmupConfig"

  //region Property "hostnameRedirectSettings"

  /**
   * Slot for the {@code hostnameRedirectSettings} property.
   * Configuration for hostname redirection
   * @see #getHostnameRedirectSettings
   * @see #setHostnameRedirectSettings
   */
  public static final Property hostnameRedirectSettings = newProperty(0, new BHostnameRedirectSettings(), null);

  /**
   * Get the {@code hostnameRedirectSettings} property.
   * Configuration for hostname redirection
   * @see #hostnameRedirectSettings
   */
  public BHostnameRedirectSettings getHostnameRedirectSettings() { return (BHostnameRedirectSettings)get(hostnameRedirectSettings); }

  /**
   * Set the {@code hostnameRedirectSettings} property.
   * Configuration for hostname redirection
   * @see #hostnameRedirectSettings
   */
  public void setHostnameRedirectSettings(BHostnameRedirectSettings v) { set(hostnameRedirectSettings, v, null); }

  //endregion Property "hostnameRedirectSettings"

  //region Property "httpHeaderProviders"

  /**
   * Slot for the {@code httpHeaderProviders} property.
   * HTTP header configuration
   * @see #getHttpHeaderProviders
   * @see #setHttpHeaderProviders
   */
  public static final Property httpHeaderProviders = newProperty(0, new BHttpHeaderProviders(), null);

  /**
   * Get the {@code httpHeaderProviders} property.
   * HTTP header configuration
   * @see #httpHeaderProviders
   */
  public BHttpHeaderProviders getHttpHeaderProviders() { return (BHttpHeaderProviders)get(httpHeaderProviders); }

  /**
   * Set the {@code httpHeaderProviders} property.
   * HTTP header configuration
   * @see #httpHeaderProviders
   */
  public void setHttpHeaderProviders(BHttpHeaderProviders v) { set(httpHeaderProviders, v, null); }

  //endregion Property "httpHeaderProviders"

  //region Property "hostHeaderValidationSettings"

  /**
   * Slot for the {@code hostHeaderValidationSettings} property.
   * @see #getHostHeaderValidationSettings
   * @see #setHostHeaderValidationSettings
   */
  public static final Property hostHeaderValidationSettings = newProperty(0, new BHostHeaderValidationSettings(), null);

  /**
   * Get the {@code hostHeaderValidationSettings} property.
   * @see #hostHeaderValidationSettings
   */
  public BHostHeaderValidationSettings getHostHeaderValidationSettings() { return (BHostHeaderValidationSettings)get(hostHeaderValidationSettings); }

  /**
   * Set the {@code hostHeaderValidationSettings} property.
   * @see #hostHeaderValidationSettings
   */
  public void setHostHeaderValidationSettings(BHostHeaderValidationSettings v) { set(hostHeaderValidationSettings, v, null); }

  //endregion Property "hostHeaderValidationSettings"

  //region Action "resetAllConnections"

  /**
   * Slot for the {@code resetAllConnections} action.
   * @see #resetAllConnections()
   */
  public static final Action resetAllConnections = newAction(Flags.CONFIRM_REQUIRED, null);

  /**
   * Invoke the {@code resetAllConnections} action.
   * @see #resetAllConnections
   */
  public void resetAllConnections() { invoke(resetAllConnections, null, null); }

  //endregion Action "resetAllConnections"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static final Logger log = Logger.getLogger(BWebService.class.getName());

  @Override
  public Type[] getServiceTypes()
  {
    return new Type[]{ TYPE };
  }

  @Override
  public Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "web");
  }

  public IWebEnv getWebEnv(WebOp op)
  {
    return getClientEnvironments().getWebEnv(op);
  }

  /**
   * Get the web server.
   *
   * @return the web server or {@code null} if there is no web server child.
   */
  public BWebServer getWebServer()
  {
    BWebServer[] servers = getChildren(BWebServer.class);
    return (servers.length == 0) ? null : servers[0];
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the first Web Service or throw ServiceNotFoundException.
   *
   * @since Niagara 4.10u2
   */
  public static BWebService getMainService()
  {
    return (BWebService)Sys.getService(TYPE);
  }

  ////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////
  @Override
  public void stationStarted()
    throws Exception
  {
    super.stationStarted();

    if (getHostHeaderValidationSettings().getValidHostHeaders().isEmpty())
    {
      getHostHeaderValidationSettings().autoPopulateValidHostHeaders();
    }
  }

  @Override
  public void serviceStarted()
    throws Exception
  {
    super.serviceStarted();
    getComponentSpace().enableMixIn(BWebProfileConfig.TYPE);

    // BUserService.secureOnlyPasswordSet changed to BWebService.requireHttpsForPasswords in 4.1
    BUserService us = (BUserService)Sys.getService(BUserService.TYPE);
    Property prop = us.getProperty("secureOnlyPasswordSet");
    if (prop != null && !us.get(prop).equals(prop.getDefaultValue()))
    {
      setRequireHttpsForPasswords(((BIBoolean)us.get(prop)).getBoolean());
    }

    if (Sys.isStationStarted() && getHostHeaderValidationSettings().getValidHostHeaders().isEmpty())
    {
      getHostHeaderValidationSettings().autoPopulateValidHostHeaders();
    }

    WebUtil.setStationTheme();
  }

  @Override
  public void serviceStopped()
    throws Exception
  {
    super.serviceStopped();
    getComponentSpace().disableMixIn(BWebProfileConfig.TYPE);
  }

  @Override
  protected void enabled()
  {
    super.enabled();
    getWebServer().scheduleRestart();
  }

  @Override
  protected void disabled()
  {
    super.disabled();

    BWebServer server = getWebServer();
    server.post(() -> server.stopWebServer(null));
  }

////////////////////////////////////////////////////////////////
// RestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * This service type is only allowed to live under the
   * station's frozen ServiceContainer, but multiple instances are allowed.
   */
  @Override
  public void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentIsServiceContainer(parent, this);
  }

////////////////////////////////////////////////////////////////
// BISecurityInfoSource
////////////////////////////////////////////////////////////////

  @Override
  public BSecurityInfo getSecurityInfo()
  {
    BSecurityInfo info = new BSecurityInfo();
    info.setSourceName(getDisplayName(null));
    if (isMounted())
    {
      info.setHyperlink(getSlotPathOrd());
    }

    info.add(BSecurityInfo.CERTIFICATE_ALIAS, BString.make(getMainCertAliasAndPassword().getAlias()));

    return info;
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);

    // this property can be set by starting or resetting the service, we NEVER need to
    // do anything with it here.
    if (property == serverCertificateHealth)
    {
      return;
    }

    if (isRunning())
    {
      UnauthenticatedCache.checkForInit(property.getName());

      if (property.isFrozen() || property.getType().is(BAdditionalHttpsCerts.TYPE))
      {
        BWebServer webServer = getWebServer();
        if (webServer != null)
        {
          webServer.webServiceConfigurationChanged(property, context);
          if (httpPort == property || httpsPort == property ||
            enabled == property || httpEnabled == property ||
            httpsEnabled == property)
          {
            updatePlatformSummaryFields();
          }
          else if (webStartConfig == property)
          {
            WebStartServlet.onWebStartConfigChanged();
          }
        }
      }
    }

    if (property.equals(xFrameOptions))
    {
      getHttpHeaderProviders().getXFrameOptions().setXFrameOptions(getXFrameOptions());
    }
  }

  /**
   * Overrides {@link BComponent} added method to restart web server if {@link BAdditionalHttpsCerts}
   * property is added.
   *
   * @since Niagara 4.11
   */
  @Override
  public void added(Property property, Context context)
  {
    super.added(property, context);

    if (!isRunning())
    {
      return;
    }

    UnauthenticatedCache.checkForInit(property.getName());

    if (property.getType().is(BAdditionalHttpsCerts.TYPE))
    {
      BWebServer webServer = getWebServer();
      BAdditionalHttpsCerts additionalHttpsCerts = (BAdditionalHttpsCerts)get(property);
      if (webServer != null && additionalHttpsCerts.hasNonEmptyAliases())
      {
        webServer.webServiceConfigurationChanged(property, context);
      }
    }
  }

  /**
   * Overrides {@link BComponent} removed method to restart web server if {@link BAdditionalHttpsCerts}
   * property is removed.
   *
   * @since Niagara 4.11
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);

    if (!isRunning())
    {
      return;
    }

    UnauthenticatedCache.checkForInit(property.getName());

    if (property.getType().is(BAdditionalHttpsCerts.TYPE))
    {
      BWebServer webServer = getWebServer();
      BAdditionalHttpsCerts additionalHttpsCerts = (BAdditionalHttpsCerts)oldValue;
      if (webServer != null && additionalHttpsCerts.hasNonEmptyAliases())
      {
        webServer.webServiceConfigurationChanged(property, context);
      }
    }
  }

  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    super.checkAdd(name, value, flags, facets, context);
    if ((value instanceof BWebServer) && (getWebServer() != null))
    {
      throw new LocalizableRuntimeException("web", "thereCanOnlyBeOne");
    }
  }

  @Override
  public void descendantsStarted()
    throws Exception
  {
    super.descendantsStarted();
    verifyWebServer();
  }

  @Override
  public void childParented(Property property, BValue newChild, Context context)
  {
    super.childParented(property, newChild, context);
    verifyWebServer();
  }

  @Override
  public void childUnparented(Property property, BValue oldChild, Context context)
  {
    super.childUnparented(property, oldChild, context);
    verifyWebServer();
  }

  private void verifyWebServer()
  {
    if (!isRunning())
    {
      return;
    }
    if (getWebServer() == null)
    {
      configFail(getLexicon().getText("web.noWebServer"));
    }
    else
    {
      configOk();
    }
  }

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.CHANGED:
        if (isRunning())
        {
          Property p = (Property)a;
          if (httpPort == p || httpsPort == p)
          {
            updatePlatformSummaryFields();
          }
        }
        break;

      case Fw.STATION_STARTED:
        updatePlatformSummaryFields();
        break;

      case Fw.STARTED:
        fwStarted();
        break;
    }

    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    if (getChildren(BUserDataConfig.class).length == 0)
    {
      add("UserDataConfig", new BUserDataConfig());
      setDisplayName(
        getProperty("UserDataConfig"),
        BFormat.make("%lexicon(web:userDataConfig)%"),
        null
      );
    }

    if (!Flags.has(this, xFrameOptions, Flags.USER_DEFINED_1))
    {
      getHttpHeaderProviders().getXFrameOptions().setXFrameOptions(getXFrameOptions());
      Flags.add(this, xFrameOptions, null, Flags.READONLY | Flags.HIDDEN | Flags.USER_DEFINED_1);
    }

    if (!Flags.has(this, httpsCert, Flags.USER_DEFINED_1))
    {
      getMainCertAliasAndPassword().setAlias(getHttpsCert());
      getMainCertAliasAndPassword().setPassword(BPassword.DEFAULT);
      Flags.add(this, httpsCert, null, Flags.READONLY | Flags.HIDDEN | Flags.USER_DEFINED_1);
    }

    if (!Flags.has(this, mainCertAliasAndPassword, Flags.USER_DEFINED_1))
    {
      Flags.add(this, mainCertAliasAndPassword, null, Flags.USER_DEFINED_1);
      setFlags(mainCertAliasAndPassword , getFlags(mainCertAliasAndPassword) & ~Flags.HIDDEN);
      setFlags(serverCertificateHealth , getFlags(serverCertificateHealth) & ~Flags.HIDDEN);

      getMainCertAliasAndPassword().setFacets(
        BCertificateAliasAndPassword.alias,
        BFacets.make("purposeId", KeyPurpose.SERVER_CERT.name()));
    }
  }

  private void updatePlatformSummaryFields()
  {
    // inform the platform about the http port and https port if they are enabled
    List<String> keys = new ArrayList<>();
    List<String> values = new ArrayList<>();

    int httpPort = -1;
    int httpsPort = -1;

    if (getHttpEnabled() && getEnabled())
    {
      httpPort = getHttpPort().getPublicServerPort();
    }

    if (getHttpsEnabled() && getEnabled())
    {
      httpsPort = getHttpsPort().getPublicServerPort();
    }

    keys.add("httpport");
    values.add(String.valueOf(httpPort));

    keys.add("httpsport");
    values.add(String.valueOf(httpsPort));

    Nre.getPlatform().reportSummaryFields(
      keys.toArray(new String[keys.size()]),
      values.toArray(new String[values.size()])
    );
  }

  /**
   * Return the HTTP Connection details for the given Context.
   * <p>
   * This method is invoked through Fox when a BWbWebView needs to make
   * a connection to the Station through the Web Server.
   * <p>
   * This method is reflectively invoked from the Fox Broker Channel
   * (BBrokerChannel#getHttpConnectionDetails(Context)).
   *
   * @param cx The Context used in the remote call.
   * @return BComponent HTTP Connection Details
   */
  @SuppressWarnings("unused")
  public BComponent getHttpConnectionDetails(Context cx)
  {
    BComponent details = new BComponent();

    //@since 4.4 these two properties have been included as well
    details.add("enabled", BBoolean.make(getEnabled()));
    details.add("httpEnabled", BBoolean.make(getHttpEnabled()));

    details.add("httpsEnabled", BBoolean.make(getHttpsEnabled()));
    details.add("httpsPort", BInteger.make(getHttpsPort().getPublicServerPort()));
    details.add("httpPort", BInteger.make(getHttpPort().getPublicServerPort()));

    return details;
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doResetAllConnections(Context context)
    throws Exception
  {
    new Thread()
    {
      @Override
      public void run()
      {
        BWebServer webServer = getWebServer();
        if (webServer != null)
        {
          webServer.invalidateAllSessions();
        }
      }
    }.start();
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("navOnly/webService.png");

////////////////////////////////////////////////////////////////
// SecurityDashboard
////////////////////////////////////////////////////////////////

  @Override
  public LexiconFormatInfo getSecurityDashboardSectionHeader(Context cx)
  {
    return LexiconFormatInfo.make(TYPE, "securityDashboard.sectionHeader");
  }

  @Override
  public BOrd getSecurityDashboardSectionHyperlinkOrd()
  {
    return getNavOrd().relativizeToSession();
  }

  @Override
  public int getSecurityDashboardItemsVersion()
  {
    return VERSION;
  }

  @Override
  public List<SecurityDashboardItem> getSecurityDashboardItems(Context cx)
  {
    List<SecurityDashboardItem> items = new ArrayList<>();
    addTlsItems(items);
    addCipherSuiteGroupItems(items);
    addStationCertificateItems(items);
    addPasswordParameters(items);
    addSameSiteItem(items);
    addHostHeaderValidationItem(items);
    return items;
  }

  private void addTlsItems(List<SecurityDashboardItem> items)
  {
    // check non TLS enabled and forwarding
    if (getHttpEnabled() && !getHttpsEnabled())
    {
      items.add(dashboardItemBuilder.makeAlert(
        "securityDashboard.webTlsProtocolOff.summary",
        "securityDashboard.webTlsProtocolOff.description"
      ));
    }

    if (!getHttpEnabled() && getHttpsEnabled())
    {
      items.add(dashboardItemBuilder.makeOk(
        "securityDashboard.webTlsProtocolOn.summary",
        "securityDashboard.webTlsProtocolOn.description"
      ));
    }

    if (getHttpEnabled() && getHttpsEnabled() && getHttpsOnly())
    {
      items.add(dashboardItemBuilder.makeWarning(
        "securityDashboard.webForwarding.summary",
        "securityDashboard.webForwarding.description"
      ));
    }

    if (getHttpEnabled() && getHttpsEnabled() && !getHttpsOnly())
    {
      items.add(dashboardItemBuilder.makeAlert(
        "securityDashboard.webNonTlsProtocol.summary",
        "securityDashboard.webNonTlsProtocol.description"
      ));
    }

    if (getHttpsEnabled())
    {
      BSslTlsEnum tlsVersion = getHttpsMinProtocol();
      BSecurityItemStatus itemStatus = isTlsVersionStrong(tlsVersion) ? securityStatusOK : securityStatusWarning;

      items.add(dashboardItemBuilder.make(itemStatus)
        .withSummary("securityDashboard.webTlsProtocol.summary", tlsVersion)
        .withDescription("securityDashboard.webTlsProtocol.description", BSslTlsEnum.tlsv1_2));
    }
  }

  private static boolean isTlsVersionStrong(BSslTlsEnum tlsVersion)
  {
    return tlsVersion.equals(BSslTlsEnum.tlsv1_2) || tlsVersion.equals(BSslTlsEnum.tlsv1_3);
  }

  private void addCipherSuiteGroupItems(List<SecurityDashboardItem> items)
  {
    if (getHttpsEnabled())
    {
      BSecurityItemStatus itemStatus = getCipherSuiteGroup().equals(BTlsCipherSuiteGroup.recommended) ?
        securityStatusOK :
        securityStatusWarning;
      items.add(dashboardItemBuilder.make(itemStatus)
        .withSummary("securityDashboard.cipherSuiteGroup.summary", getCipherSuiteGroup())
        .withDescription("securityDashboard.cipherSuiteGroup.description"));
    }
  }

  private void addStationCertificateItems(List<SecurityDashboardItem> items)
  {
    if (getHttpsEnabled())
    {
      addCertificateItems(getMainCertAliasAndPassword().getAlias(), items);

      for (BAdditionalHttpsCerts additionalHttpsCerts : getChildren(BAdditionalHttpsCerts.class))
      {
        for (BAdditionalHttpsCert additionalHttpsCert : additionalHttpsCerts.getChildren(BAdditionalHttpsCert.class))
        {
          if (additionalHttpsCert.getIsDuplicate())
          {
            items.add(dashboardItemBuilder.makeInfo()
              .withSummary("securityDashboard.additionalCert.duplicate", additionalHttpsCert.getCertAliasAndPassword().getAlias())
              .withDescription("securityDashboard.additionalCert.description"));
          }
          else if (additionalHttpsCert.getIsRejected())
          {
            items.add(dashboardItemBuilder.makeAlert()
              .withSummary("securityDashboard.additionalCert.rejected", additionalHttpsCert.getCertAliasAndPassword().getAlias())
              .withDescription("securityDashboard.additionalCert.description"));
          }
          else
          {
            addCertificateItems(additionalHttpsCert.getCertAliasAndPassword().getAlias(), items);
          }
        }
      }

      // add the cert health info
      BServerCertificateHealth health = getServerCertificateHealth();
      switch(health.getCertStatus().getOrdinal())
      {
        case BCertificateStatusEnum.OK:
          items.add(dashboardItemBuilder.makeOk()
            .withSummary("securityDashboard.certHealth.ok", health.getReturnedCert())
            .withDescription("securityDashboard.certHealth.description"));
          break;

        case BCertificateStatusEnum.BAD_PASSWORD:
          items.add(dashboardItemBuilder.makeAlert()
            .withSummary("securityDashboard.certHealth.badPassword", health.getRequestedCert(), health.getReturnedCert())
            .withDescription("securityDashboard.certHealth.description"));
          break;

        case BCertificateStatusEnum.BAD_KEY:
          items.add(dashboardItemBuilder.makeAlert()
            .withSummary("securityDashboard.certHealth.badKey", health.getRequestedCert(), health.getReturnedCert())
            .withDescription("securityDashboard.certHealth.description"));
          break;

        case BCertificateStatusEnum.MISSING_KEY:
          items.add(dashboardItemBuilder.makeAlert()
            .withSummary("securityDashboard.certHealth.missingKey", health.getRequestedCert(), health.getReturnedCert())
            .withDescription("securityDashboard.certHealth.description"));
          break;

        case BCertificateStatusEnum.BAD_DEFAULT:
          items.add(dashboardItemBuilder.makeAlert()
            .withSummary("securityDashboard.certHealth.badDefault", health.getRequestedCert(), health.getReturnedCert())
            .withDescription("securityDashboard.certHealth.description"));
          break;
      }
    }
  }

  private static void addCertificateItems(String certAlias, List<SecurityDashboardItem> items)
  {
    try
    {
      X509Certificate certificate = CertManagerFactory.getInstance().getKeyStore()
        .getCertificate(certAlias);
      addCertificateItems(certAlias, certificate, items);
    }
    // this exception can be ignored because the alerts are handled above
    // and the stacktrace doesn't provide anything useful either
    catch (Exception ignore)
    {
    }
  }

  private static void addCertificateItems(String certAlias, X509Certificate certificate, List<SecurityDashboardItem> items)
  {
    try
    {
      certificate.checkValidity();

      // Check near expiration
      if (certificate.getNotAfter().getTime() - new Date().getTime() < MILLIS_IN_NINETY_DAYS)
      {
        items.add(dashboardItemBuilder.makeWarning()
          .withSummary("securityDashboard.webCertificateNearExpiry.summary",
            certAlias, BAbsTime.make(certificate.getNotAfter().getTime())
          )
          .withDescription("securityDashboard.webCertificateNearExpiry.description"));
      }
      else
      {
        items.add(dashboardItemBuilder.makeOk()
          .withSummary("securityDashboard.webCertificateValid.summary", certAlias)
          .withDescription("securityDashboard.webCertificateValid.description"));
      }
    }
    catch (CertificateExpiredException e)
    {
      items.add(dashboardItemBuilder.makeAlert()
        .withSummary("securityDashboard.webCertificateExpired.summary", certAlias)
        .withDescription("securityDashboard.webCertificateExpired.description"));
    }
    catch (CertificateNotYetValidException e)
    {
      items.add(dashboardItemBuilder.makeAlert()
        .withSummary("securityDashboard.webCertificateNotYetValidException.summary",
          certAlias, BAbsTime.make(certificate.getNotBefore().getTime())
        )
        .withDescription("securityDashboard.webCertificateNotYetValidException.description"));
    }

    // Check self-signed
    if (isSelfSigned(certificate))
    {
      if (CertUtils.FACTORY_CERT_ALIAS.equalsIgnoreCase(certAlias))
      {
        items.add(dashboardItemBuilder.makeWarning()
          .withSummary("securityDashboard.webCertificateDefault.summary")
          .withDescription("securityDashboard.webCertificateDefault.description"));
      }
      else
      {
        items.add(dashboardItemBuilder.makeWarning()
          .withSummary("securityDashboard.webCertificateSelfSigned.summary", certAlias)
          .withDescription("securityDashboard.webCertificateSelfSigned.description"));
      }
    }
    else
    {
      items.add(dashboardItemBuilder.makeOk()
        .withSummary("securityDashboard.webCertificateNotSelfSigned.summary", certAlias)
        .withDescription("securityDashboard.webCertificateNotSelfSigned.description"));
    }

    // check if the cert is signed with the keystore cert
    try
    {
      ICoreKeyStore keyStore = (ICoreKeyStore)CertManagerFactory.getInstance().getKeyStore();
      if (CertUtils.isPrivateKeyGloballyEncrypted(certAlias, keyStore))
      {
        items.add(dashboardItemBuilder.makeWarning()
          .withSummary("securityDashboard.webCertificateGloballyEncrypted.summary", certAlias)
          .withDescription("securityDashboard.webCertificateEncrypted.description"));
      }
      else
      {
        items.add(dashboardItemBuilder.makeOk()
          .withSummary("securityDashboard.webCertificateUniquelyEncrypted.summary", certAlias)
          .withDescription("securityDashboard.webCertificateEncrypted.description"));
      }
    }
    catch (Exception e)
    {
      log.log(Level.WARNING, "unable to check private key for password", e);
    }
  }

  private void addPasswordParameters(List<SecurityDashboardItem> items)
  {
    String summaryKey;
    BSecurityItemStatus itemStatus;
    if (getRequireHttpsForPasswords())
    {
      summaryKey = "securityDashboard.requireHttpsForPasswordTrue.summary";
      itemStatus = securityStatusOK;
    }
    else
    {
      summaryKey = "securityDashboard.requireHttpsForPasswordFalse.summary";
      itemStatus = securityStatusWarning;
    }
    String descriptionKey = "securityDashboard.requireHttpsForPassword.description";
    items.add(dashboardItemBuilder.make(itemStatus, summaryKey, descriptionKey));
  }

  private void addSameSiteItem(List<SecurityDashboardItem> items)
  {
    BSameSiteEnum sameSite = getSameSite();
    BSecurityItemStatus itemStatus = sameSite == BSameSiteEnum.none ?
      securityStatusWarning : securityStatusOK;

    items.add(dashboardItemBuilder.make(itemStatus)
      .withSummary("securityDashboard.sameSite.summary", sameSite)
      .withDescription("securityDashboard.sameSite.description"));
  }

  /**
   * @param items The list of security dashboard items
   * @since Niagara 4.4u4
   */
  private void addHostHeaderValidationItem(List<SecurityDashboardItem> items)
  {
    if (getHostHeaderValidationSettings().getValidateHostHeader())
    {
      if (getHostHeaderValidationSettings().getValidHostHeaders().contains("*"))
      {
        items.add(dashboardItemBuilder.makeWarning(
          "securityDashboard.hostHeader.wildcard.summary",
          "securityDashboard.hostHeader.description"
        ));
      }
      else
      {
        items.add(dashboardItemBuilder.makeOk(
          "securityDashboard.hostHeader.ok.summary",
          "securityDashboard.hostHeader.description"
        ));
      }
    }
    else
    {
      items.add(dashboardItemBuilder.makeAlert(
        "securityDashboard.hostHeader.validationOff.summary",
        "securityDashboard.hostHeader.description"
      ));
    }
  }

  private static boolean isSelfSigned(X509Certificate certificate)
  {
    return certificate.getIssuerDN().equals(certificate.getSubjectDN());
  }

////////////////////////////////////////////////////////////////
// Property Validator
////////////////////////////////////////////////////////////////

  @Override
  public IPropertyValidator getPropertyValidator(Property[] properties, Context context)
  {
    if (ArrayUtil.indexOf(properties, mainCertAliasAndPassword) > -1)
    {
      return validator;
    }

    return super.getPropertyValidator(properties, context);
  }

  @Override
  public IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (mainCertAliasAndPassword.equals(property))
    {
      return validator;
    }

    return super.getPropertyValidator(property, context);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // Version 2: updated description for certificates in validity period (see NCCB-41313)
  // Version 3: updated entry for using default cert
  // Version 4: added entry for global vs unique cert password
  private static final int VERSION = 4;

  private static final double MILLIS_IN_NINETY_DAYS = BRelTime.makeDays(90).getMillis();

  private final CertAliasCasePropertyValidator validator = new CertAliasCasePropertyValidator(mainCertAliasAndPassword.getName());

  private static final SecurityDashboardItemBuilder dashboardItemBuilder = new SecurityDashboardItemBuilder(TYPE);
}
