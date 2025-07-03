/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.history;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIArchiveFolder is the common interface for 
 * BHistoryDeviceExt and BArchiveFolder.
 *
 * @author    Scott Hoye
 * @creation  15 May 09
 * @version   $Revision: 1$ $Date: 5/19/09 2:54:59 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
public interface BIArchiveFolder
  extends BInterface
{                       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BIArchiveFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIArchiveFolder.class);

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
   * Get the parent history device extension.
   */
  public BHistoryDeviceExt getDeviceExt();
  
  /**
   * Get the type of HistoryImport descriptor for this driver.
   */
  public Type getImportDescriptorType();
  
  /**
   * Get the type of HistoryImport descriptor for this driver.
   */
  public Type getExportDescriptorType();

  /**
   * Get the type of ArchiveFolder for this driver.
   */
  public Type getArchiveFolderType();
}
