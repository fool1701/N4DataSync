/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.file;

import javax.baja.file.BIFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIFileDevice is the common interface that should be implemented
 * by a device that supports files (i.e. useful for file
 * synchronization, importing, exporting, etc).
 * It might commonly be implemented by
 * devices that have a file system, and you can resolve
 * files in the device's system given relative Ords.
 *
 * @author    Scott Hoye
 * @creation  15 May 06
 * @version   $Revision: 1$ $Date: 5/16/06 12:40:34 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIFileDevice
  extends BInterface
{                       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.file.BIFileDevice(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIFileDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Resolve a BIFile in this file device
   * given an Ord.  In many cases, the given Ord
   * may be relative to the device's file system,
   * so the device must resolve the file and return
   * a BIFile instance.
   */
  public BIFile resolveFile(BOrd ord);
}
