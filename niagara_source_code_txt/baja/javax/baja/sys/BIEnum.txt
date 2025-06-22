/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIEnum is the interface implemented by BObjects 
 * which primarily represent an enumerated value.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 5$ $Date: 3/31/04 9:02:22 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIEnum
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIEnum(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the enum value.
   */
  public BEnum getEnum();

  /**
   * Get facets for the enum value or BFacets.NULL if not applicable.
   */
  public BFacets getEnumFacets();
}
