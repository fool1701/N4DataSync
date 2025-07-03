/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.points;

import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.examples.envctrldriver.BEnvCtrlDevice;

/**
 * Our Point Device Extension which will contain the Control Points
 * that reprsent point data on our remote EnvController device. The
 * points in this point extension must contain our special
 * {@link BEnvCtrlPointProxyExt} extension.
 *
 * @author J. Spangler on Mar 7, 2013
 */
@NiagaraType
public class BEnvCtrlPointDeviceExt
  extends BPointDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.points.BEnvCtrlPointDeviceExt(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parent device Type.
   */
  public Type getDeviceType()
  {
    return BEnvCtrlDevice.TYPE;
  }

  /**
   * Get the Type of proxy extensions for this device.
   */
  public Type getProxyExtType()
  {
    return BEnvCtrlPointProxyExt.TYPE;
  }

  /**
   * Get the Type of point folder for this device.
   */
  public Type getPointFolderType()
  {
    return BEnvCtrlPointFolder.TYPE;
  }

}
