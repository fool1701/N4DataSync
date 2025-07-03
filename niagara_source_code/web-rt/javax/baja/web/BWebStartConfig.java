/**
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

/**
 * BWebStartConfig provides several configuration settings that allow users to
 * launch a remote Workbench view using Java Web Start technology, which is an alternative
 * to using Java Applets and the Java Plug In.
 * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 */
@NiagaraType
/*
 Specifies whether users are allowed to use the Java Web Start application
 as a remote client. This will enable a link on the log in page for
 the user to download a jnlp file used to launch the application.
 */
@NiagaraProperty(
  name = "webStartEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 A format string used to format a title for the web start application.
 The resulting string will be written to the jnlp file and will be used
 as the text for the shortcut in the user's start menu. It will also be used in the
 application cache in the Java control panel. The value of this property
 should be unique per station, as one shortcut link will be generated for each.
 The default string is based on the station name and the host name of the http server.
 @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
 */
@NiagaraProperty(
  name = "appTitle",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN,
  deprecated = true
)
/*
 Enables the creation of a shortcut in the user's start menu when they open the
 Web Start jnlp file.
 @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
 */
@NiagaraProperty(
  name = "shortcutFolderName",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN,
  deprecated = true
)
@Deprecated
public class BWebStartConfig
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebStartConfig(837641100)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "webStartEnabled"

  /**
   * Slot for the {@code webStartEnabled} property.
   * Specifies whether users are allowed to use the Java Web Start application
   * as a remote client. This will enable a link on the log in page for
   * the user to download a jnlp file used to launch the application.
   * @see #getWebStartEnabled
   * @see #setWebStartEnabled
   */
  public static final Property webStartEnabled = newProperty(0, true, null);

  /**
   * Get the {@code webStartEnabled} property.
   * Specifies whether users are allowed to use the Java Web Start application
   * as a remote client. This will enable a link on the log in page for
   * the user to download a jnlp file used to launch the application.
   * @see #webStartEnabled
   */
  public boolean getWebStartEnabled() { return getBoolean(webStartEnabled); }

  /**
   * Set the {@code webStartEnabled} property.
   * Specifies whether users are allowed to use the Java Web Start application
   * as a remote client. This will enable a link on the log in page for
   * the user to download a jnlp file used to launch the application.
   * @see #webStartEnabled
   */
  public void setWebStartEnabled(boolean v) { setBoolean(webStartEnabled, v, null); }

  //endregion Property "webStartEnabled"

  //region Property "appTitle"

  /**
   * Slot for the {@code appTitle} property.
   * A format string used to format a title for the web start application.
   * The resulting string will be written to the jnlp file and will be used
   * as the text for the shortcut in the user's start menu. It will also be used in the
   * application cache in the Java control panel. The value of this property
   * should be unique per station, as one shortcut link will be generated for each.
   * The default string is based on the station name and the host name of the http server.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #getAppTitle
   * @see #setAppTitle
   */
  @Deprecated
  public static final Property appTitle = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BFormat.DEFAULT, null);

  /**
   * Get the {@code appTitle} property.
   * A format string used to format a title for the web start application.
   * The resulting string will be written to the jnlp file and will be used
   * as the text for the shortcut in the user's start menu. It will also be used in the
   * application cache in the Java control panel. The value of this property
   * should be unique per station, as one shortcut link will be generated for each.
   * The default string is based on the station name and the host name of the http server.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #appTitle
   */
  @Deprecated
  public BFormat getAppTitle() { return (BFormat)get(appTitle); }

  /**
   * Set the {@code appTitle} property.
   * A format string used to format a title for the web start application.
   * The resulting string will be written to the jnlp file and will be used
   * as the text for the shortcut in the user's start menu. It will also be used in the
   * application cache in the Java control panel. The value of this property
   * should be unique per station, as one shortcut link will be generated for each.
   * The default string is based on the station name and the host name of the http server.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #appTitle
   */
  @Deprecated
  public void setAppTitle(BFormat v) { set(appTitle, v, null); }

  //endregion Property "appTitle"

  //region Property "shortcutFolderName"

  /**
   * Slot for the {@code shortcutFolderName} property.
   * Enables the creation of a shortcut in the user's start menu when they open the
   * Web Start jnlp file.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #getShortcutFolderName
   * @see #setShortcutFolderName
   */
  @Deprecated
  public static final Property shortcutFolderName = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.HIDDEN, BFormat.DEFAULT, null);

  /**
   * Get the {@code shortcutFolderName} property.
   * Enables the creation of a shortcut in the user's start menu when they open the
   * Web Start jnlp file.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #shortcutFolderName
   */
  @Deprecated
  public BFormat getShortcutFolderName() { return (BFormat)get(shortcutFolderName); }

  /**
   * Set the {@code shortcutFolderName} property.
   * Enables the creation of a shortcut in the user's start menu when they open the
   * Web Start jnlp file.
   * @deprecated WebStart no longer supported in Niagara 4.9+. Property remains only for legacy BOG file decoding.
   * @see #shortcutFolderName
   */
  @Deprecated
  public void setShortcutFolderName(BFormat v) { set(shortcutFolderName, v, null); }

  //endregion Property "shortcutFolderName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebStartConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
