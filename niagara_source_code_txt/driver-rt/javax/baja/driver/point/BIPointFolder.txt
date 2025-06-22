/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIPointFolder is the common interface for 
 * BPointDeviceExt and BPointFolder.
 *
 * @author    Brian Frank       
 * @creation  29 Jun 04
 * @version   $Revision: 1$ $Date: 6/29/04 4:30:53 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIPointFolder
  extends BInterface
{                       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BIPointFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parent network.
   */
  public BDeviceNetwork getNetwork();

  /**
   * Get the parent device.
   */
  public BDevice getDevice();

  /**
   * Get the parent point device extension.
   */
  public BPointDeviceExt getDeviceExt();
  
  /**
   * Get the type of ProxyExt for this driver.
   */
  public Type getProxyExtType();

  /**
   * Get the type of PointFolder for this driver.
   */
  public Type getPointFolderType();
}
