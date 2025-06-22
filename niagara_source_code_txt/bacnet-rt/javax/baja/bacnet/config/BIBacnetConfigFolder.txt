/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIBacnetConfigFolder is the common interface for
 * BLocalBacnetDevice and BIBacnetConfigFolder.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 30 Nov 2004
 * @since Niagara 3 BACnet 1.0
 */
@NiagaraType
public interface BIBacnetConfigFolder
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BIBacnetConfigFolder(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:31 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetConfigFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parent network.
   */
  public BBacnetConfigDeviceExt getConfig();
}
