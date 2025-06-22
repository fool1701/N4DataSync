/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BICategorizable is implemented by BObjects which can be assigned to
 * one or more categories through the use of a BCategoryMask.
 *
 * @author    Brian Frank
 * @creation  12 Feb 05
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:54 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BICategorizable
  extends BInterface     
{             
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.category.BICategorizable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BICategorizable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the category mask to actually use for this object.  This
   * may be different from {@code getCategoryMask()} if using
   * category inheritance.
   */
  BCategoryMask getAppliedCategoryMask();
  
  /**
   * Get the raw category mask for this object.  If the object 
   * supports category inheritance, then this method should return 
   * {@code BCategoryMask.NULL} and return the inherited
   * mask for {@code getAppliedCategoryMask()}.
   */
  BCategoryMask getCategoryMask();
}
