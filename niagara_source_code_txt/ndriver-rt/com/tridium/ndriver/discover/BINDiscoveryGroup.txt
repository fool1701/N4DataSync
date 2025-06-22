/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Discovery groups appear in the discovery list of the auto managers. Unlike a
 * DiscoveryLeaf a DiscoveryGroup is expandable and can contain other discovery
 * groups or DiscoveryLeaves underneath them.
 * <p>
 * The containment is done by adding children to the discovery group component.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public interface BINDiscoveryGroup
  extends BINDiscoveryObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BINDiscoveryGroup(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINDiscoveryGroup.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Extending from BComponent gives this to you for free.
   */
  <T> T[] getChildren(Class<T> cls);
}
