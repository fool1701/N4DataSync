/**
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * CacheConfig provides configuration settings that allow users to prevent 304 not modified on file system ords.
 */
@NiagaraType
/*
 * Specifies whether or not to use the cache config
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Semicolon separated list of file extensions to include in full caching to prevent uneeded 304 NOT_MODIFIED.
 Use a single asterisk for all files and leave blank for no extensions. By default, all common image extensions
 will no longer revalidate on each request.
 */
@NiagaraProperty(
  name = "cachedFileExtensions",
  type = "String",
  defaultValue = "png,jpg,gif,svg"
)
public class BCacheConfig extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BCacheConfig(797782513)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * * Specifies whether or not to use the cache config
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * * Specifies whether or not to use the cache config
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * * Specifies whether or not to use the cache config
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "cachedFileExtensions"

  /**
   * Slot for the {@code cachedFileExtensions} property.
   * Semicolon separated list of file extensions to include in full caching to prevent uneeded 304 NOT_MODIFIED.
   * Use a single asterisk for all files and leave blank for no extensions. By default, all common image extensions
   * will no longer revalidate on each request.
   * @see #getCachedFileExtensions
   * @see #setCachedFileExtensions
   */
  public static final Property cachedFileExtensions = newProperty(0, "png,jpg,gif,svg", null);

  /**
   * Get the {@code cachedFileExtensions} property.
   * Semicolon separated list of file extensions to include in full caching to prevent uneeded 304 NOT_MODIFIED.
   * Use a single asterisk for all files and leave blank for no extensions. By default, all common image extensions
   * will no longer revalidate on each request.
   * @see #cachedFileExtensions
   */
  public String getCachedFileExtensions() { return getString(cachedFileExtensions); }

  /**
   * Set the {@code cachedFileExtensions} property.
   * Semicolon separated list of file extensions to include in full caching to prevent uneeded 304 NOT_MODIFIED.
   * Use a single asterisk for all files and leave blank for no extensions. By default, all common image extensions
   * will no longer revalidate on each request.
   * @see #cachedFileExtensions
   */
  public void setCachedFileExtensions(String v) { setString(cachedFileExtensions, v, null); }

  //endregion Property "cachedFileExtensions"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCacheConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
