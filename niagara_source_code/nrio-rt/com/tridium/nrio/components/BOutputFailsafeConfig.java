/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

import com.tridium.nrio.enums.BSdiEnum;

/**
 * BOutputFailsafeConfig - This is a structure to configure control timer values for output default
 * value state detection.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2016
 */

@NiagaraType
@NiagaraProperty(
  name = "commLossTimeout",
  type = "int",
  defaultValue = "8",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BFacets.makeInt(BUnit.getUnit(\"second\"), 8, 900)")
)
@NiagaraProperty(
  name = "startupTimeout",
  type = "int",
  defaultValue = "600",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BFacets.makeInt(BUnit.getUnit(\"second\"), 8, 900)")
)

public class BOutputFailsafeConfig
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BOutputFailsafeConfig(136035945)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "commLossTimeout"

  /**
   * Slot for the {@code commLossTimeout} property.
   * @see #getCommLossTimeout
   * @see #setCommLossTimeout
   */
  public static final Property commLossTimeout = newProperty(Flags.DEFAULT_ON_CLONE, 8, BFacets.makeInt(BUnit.getUnit("second"), 8, 900));

  /**
   * Get the {@code commLossTimeout} property.
   * @see #commLossTimeout
   */
  public int getCommLossTimeout() { return getInt(commLossTimeout); }

  /**
   * Set the {@code commLossTimeout} property.
   * @see #commLossTimeout
   */
  public void setCommLossTimeout(int v) { setInt(commLossTimeout, v, null); }

  //endregion Property "commLossTimeout"

  //region Property "startupTimeout"

  /**
   * Slot for the {@code startupTimeout} property.
   * @see #getStartupTimeout
   * @see #setStartupTimeout
   */
  public static final Property startupTimeout = newProperty(Flags.DEFAULT_ON_CLONE, 600, BFacets.makeInt(BUnit.getUnit("second"), 8, 900));

  /**
   * Get the {@code startupTimeout} property.
   * @see #startupTimeout
   */
  public int getStartupTimeout() { return getInt(startupTimeout); }

  /**
   * Set the {@code startupTimeout} property.
   * @see #startupTimeout
   */
  public void setStartupTimeout(int v) { setInt(startupTimeout, v, null); }

  //endregion Property "startupTimeout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutputFailsafeConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BOutputFailsafeConfig(int commLossTimeout, int powerupTimeout)
  {
    setCommLossTimeout(commLossTimeout);
    setStartupTimeout(powerupTimeout);
  }

  public BOutputFailsafeConfig()
  {
  }
}
