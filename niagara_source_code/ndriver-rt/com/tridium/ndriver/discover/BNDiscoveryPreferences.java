/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNDiscoveryPreferences is superclass for drivers specific constraints on the
 * discovery implementation.  This object will be passed in call to
 * BINDiscoveryHost.getDiscoveryObjects().
 * <p>
 * Popup for user to change discoveryPreferences is controlled by doNotAskAgain.
 *  Default is true to prevent popup.  Drivers that desire user to configure
 * preferences during discovery should override default doNotAskAgain property
 * to false.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
/*
 controls if user is prompted to modify preferences.
 */
@NiagaraProperty(
  name = "doNotAskAgain",
  type = "boolean",
  defaultValue = "true"
)
public class BNDiscoveryPreferences
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BNDiscoveryPreferences(1985865765)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "doNotAskAgain"

  /**
   * Slot for the {@code doNotAskAgain} property.
   * controls if user is prompted to modify preferences.
   * @see #getDoNotAskAgain
   * @see #setDoNotAskAgain
   */
  public static final Property doNotAskAgain = newProperty(0, true, null);

  /**
   * Get the {@code doNotAskAgain} property.
   * controls if user is prompted to modify preferences.
   * @see #doNotAskAgain
   */
  public boolean getDoNotAskAgain() { return getBoolean(doNotAskAgain); }

  /**
   * Set the {@code doNotAskAgain} property.
   * controls if user is prompted to modify preferences.
   * @see #doNotAskAgain
   */
  public void setDoNotAskAgain(boolean v) { setBoolean(doNotAskAgain, v, null); }

  //endregion Property "doNotAskAgain"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDiscoveryPreferences.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Get the leaf type to use during discovery process.
   */
  public Type getDiscoveryLeafType()
  {
    return null;
  }

  @Deprecated
  public boolean getNeedsJob()
  {
    return true;
  }

  /**
   * Indicate if the job should make multiple calls to getDiscoveryObjects().
   *
   * @return true to indicate multiple calls to getDiscoveryObjects() default
   * returns false;
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public boolean isMultiStep()
  {
    return false;
  }

  /**
   * Access the BNDiscoveryJob instance.
   *
   * @since 3.8.38.1, 3.7.202, 3.6.503
   */
  public final BNDiscoveryJob getJob()
  {
    return job;
  }

  BNDiscoveryJob job = null;
}
