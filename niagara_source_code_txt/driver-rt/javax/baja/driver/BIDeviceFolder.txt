/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIDeviceFolder is the common interface for 
 * BDeviceNetwork and BDeviceFolder.
 *
 * @author    Brian Frank       
 * @creation  12 Sept 04
 * @version   $Revision: 1$ $Date: 9/12/04 1:39:32 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIDeviceFolder
  extends BInterface
{                       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BIDeviceFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parent network.
   */
  public BDeviceNetwork getNetwork();

  /**
   * Get the type of Device for this driver.
   */
  public Type getDeviceType();

  /**
   * Get the type of DeviceFolder for this driver.
   */
  public Type getDeviceFolderType();
}
