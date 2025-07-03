/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.license;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BILicensed is the interface used to represent object
 * that are licensed using the standard licensing
 * mechanism.
 *
 * @author    Mike Jarmy
 * @creation  11 Oct 11
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BILicensed 
  extends BInterface
{   
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.license.BILicensed(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BILicensed.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Implement this method to return the Feature, or 
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  public Feature getLicenseFeature();
}
