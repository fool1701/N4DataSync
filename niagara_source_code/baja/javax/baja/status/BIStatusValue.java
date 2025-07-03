/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIStatusValue which can return themselves as a BStatusValue.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIStatusValue
  extends BIStatus
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.status.BIStatusValue(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIStatusValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the object as a BStatusValue.
   */
  public BStatusValue getStatusValue();

  /**
   * Get facets for the status value or BFacets.NULL 
   * if not applicable.
   */
  public BFacets getStatusValueFacets();
}
