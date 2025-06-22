/*
 * Copyright (c) 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIIndirectNavNode is a nav node that may target an entity at a different location.  In some
 * situations, the nav ord of the node is necessary for finding the node within the nav tree.  This
 * is necessary for expanding the nav tree within a web browser, for example.  In other situations,
 * the target entity is required, such as when creating widget bindings.  These nodes do not always
 * have a target entity.  BLevelElem in the hierarchy module, for example, may target an entity or
 * may represent a group under which entity BLevelElems are added.
 *
 * @author Eric Anderson
 * @creation 9/5/2017
 * @since Niagara 4.4
 */
@NiagaraType
public interface BIIndirectNavNode extends BINavNode
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BIIndirectNavNode(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIIndirectNavNode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the nav ord of the node's target entity or null if the node does not have a target.
   */
  BOrd getTargetOrd();

  /**
   * If this nav node has a target entity, return its nav ord.  Otherwise, return the nav ord of the
   * node itself.
   */
  default BOrd getTargetOrNavOrd()
  {
    BOrd ord = getTargetOrd();
    return ord != null ? ord : getNavOrd();
  }
}
