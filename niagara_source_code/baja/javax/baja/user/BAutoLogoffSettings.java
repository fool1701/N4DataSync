/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * @author Patrick sager
 * @creation 1/13/2017
 * @since Niagara 4.4
 */
@NiagaraType
@NiagaraProperty(
  name = "autoLogoffEnabled",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "useDefaultAutoLogoffPeriod",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "autoLogoffPeriod",
  type = "BRelTime",
  defaultValue = "BRelTime.makeMinutes(15)",
  facets = {
    @Facet(name = "BFacets.SHOW_SECONDS", value = "false"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.makeMinutes(2)"),
    @Facet(name = "BFacets.MAX", value = "BRelTime.makeHours(4)")
  }
)
public class BAutoLogoffSettings
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BAutoLogoffSettings(2242572737)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "autoLogoffEnabled"

  /**
   * Slot for the {@code autoLogoffEnabled} property.
   * @see #getAutoLogoffEnabled
   * @see #setAutoLogoffEnabled
   */
  public static final Property autoLogoffEnabled = newProperty(0, true, null);

  /**
   * Get the {@code autoLogoffEnabled} property.
   * @see #autoLogoffEnabled
   */
  public boolean getAutoLogoffEnabled() { return getBoolean(autoLogoffEnabled); }

  /**
   * Set the {@code autoLogoffEnabled} property.
   * @see #autoLogoffEnabled
   */
  public void setAutoLogoffEnabled(boolean v) { setBoolean(autoLogoffEnabled, v, null); }

  //endregion Property "autoLogoffEnabled"

  //region Property "useDefaultAutoLogoffPeriod"

  /**
   * Slot for the {@code useDefaultAutoLogoffPeriod} property.
   * @see #getUseDefaultAutoLogoffPeriod
   * @see #setUseDefaultAutoLogoffPeriod
   */
  public static final Property useDefaultAutoLogoffPeriod = newProperty(0, true, null);

  /**
   * Get the {@code useDefaultAutoLogoffPeriod} property.
   * @see #useDefaultAutoLogoffPeriod
   */
  public boolean getUseDefaultAutoLogoffPeriod() { return getBoolean(useDefaultAutoLogoffPeriod); }

  /**
   * Set the {@code useDefaultAutoLogoffPeriod} property.
   * @see #useDefaultAutoLogoffPeriod
   */
  public void setUseDefaultAutoLogoffPeriod(boolean v) { setBoolean(useDefaultAutoLogoffPeriod, v, null); }

  //endregion Property "useDefaultAutoLogoffPeriod"

  //region Property "autoLogoffPeriod"

  /**
   * Slot for the {@code autoLogoffPeriod} property.
   * @see #getAutoLogoffPeriod
   * @see #setAutoLogoffPeriod
   */
  public static final Property autoLogoffPeriod = newProperty(0, BRelTime.makeMinutes(15), BFacets.make(BFacets.make(BFacets.make(BFacets.SHOW_SECONDS, false), BFacets.make(BFacets.MIN, BRelTime.makeMinutes(2))), BFacets.make(BFacets.MAX, BRelTime.makeHours(4))));

  /**
   * Get the {@code autoLogoffPeriod} property.
   * @see #autoLogoffPeriod
   */
  public BRelTime getAutoLogoffPeriod() { return (BRelTime)get(autoLogoffPeriod); }

  /**
   * Set the {@code autoLogoffPeriod} property.
   * @see #autoLogoffPeriod
   */
  public void setAutoLogoffPeriod(BRelTime v) { set(autoLogoffPeriod, v, null); }

  //endregion Property "autoLogoffPeriod"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAutoLogoffSettings.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
