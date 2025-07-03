/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIStatus is the interface implemented by BObjects 
 * which can return status information as BStatus.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 3$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIStatus
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BIStatus(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the status information.
   */
  public BStatus getStatus();
}
