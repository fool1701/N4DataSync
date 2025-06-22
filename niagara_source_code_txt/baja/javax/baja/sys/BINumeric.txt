/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BINumeric is the interface implemented by BObjects 
 * which primarily represent a numeric value stored
 * as a double.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 5$ $Date: 3/28/05 9:23:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BINumeric
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BINumeric(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINumeric.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the numeric as double value.
   */
  public double getNumeric();

  /**
   * Get facets for the numeric value or BFacets.NULL 
   * if not applicable.
   */
  public BFacets getNumericFacets();
}
