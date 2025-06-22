/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BLimitEnable 
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 5$ $Date: 10/23/03 8:36:16 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "lowLimitEnable",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "highLimitEnable",
  type = "boolean",
  defaultValue = "false"
)
public class BLimitEnable
  extends BStruct  
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BLimitEnable(1815285647)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "lowLimitEnable"

  /**
   * Slot for the {@code lowLimitEnable} property.
   * @see #getLowLimitEnable
   * @see #setLowLimitEnable
   */
  public static final Property lowLimitEnable = newProperty(0, false, null);

  /**
   * Get the {@code lowLimitEnable} property.
   * @see #lowLimitEnable
   */
  public boolean getLowLimitEnable() { return getBoolean(lowLimitEnable); }

  /**
   * Set the {@code lowLimitEnable} property.
   * @see #lowLimitEnable
   */
  public void setLowLimitEnable(boolean v) { setBoolean(lowLimitEnable, v, null); }

  //endregion Property "lowLimitEnable"

  //region Property "highLimitEnable"

  /**
   * Slot for the {@code highLimitEnable} property.
   * @see #getHighLimitEnable
   * @see #setHighLimitEnable
   */
  public static final Property highLimitEnable = newProperty(0, false, null);

  /**
   * Get the {@code highLimitEnable} property.
   * @see #highLimitEnable
   */
  public boolean getHighLimitEnable() { return getBoolean(highLimitEnable); }

  /**
   * Set the {@code highLimitEnable} property.
   * @see #highLimitEnable
   */
  public void setHighLimitEnable(boolean v) { setBoolean(highLimitEnable, v, null); }

  //endregion Property "highLimitEnable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLimitEnable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public boolean isHighLimitEnabled() { return getHighLimitEnable(); }
  public boolean isLowLimitEnabled() { return getLowLimitEnable(); }

}
