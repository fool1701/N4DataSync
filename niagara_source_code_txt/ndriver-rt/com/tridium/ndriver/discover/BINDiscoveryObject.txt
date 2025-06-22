/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Top level interface for objects that appear in the discovery list of the auto
 * manager discovery pane.
 * <p>
 * There are two subclasses of BINDiscoveryObject:
 * <ol>
 *   <li>{@link BINDiscoveryGroup}</li>
 *   <li>{@link BINDiscoveryLeaf}</li>
 * </ol>
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public interface BINDiscoveryObject
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BINDiscoveryObject(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINDiscoveryObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
