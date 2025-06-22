/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.driver.point.BProxyConversion;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Discovery leaves that appear in NPointManager should implement this
 * interface.
 * <p>
 * These methods customize the initial values of proxyPoint extensions when
 * control points are added to the station from NPointManager.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public interface BINPointDiscoveryLeaf
  extends BINDiscoveryLeaf
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BINPointDiscoveryLeaf(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINPointDiscoveryLeaf.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Return deviceFacets to assign to a new control point or null if no
   * deviceFacets.
   */
  BFacets getDiscoveryDeviceFacets();

  /**
   * Return pointFacets to assign to a new control point or null if no
   * pointFacets.
   */
  BFacets getDiscoveryPointFacets();

  /**
   * Return discoveryConversion to assign to a new control point or null if no
   * discoveryConversion.
   */
  BProxyConversion getDiscoveryConversion();

  /**
   * Return name of tuningPolicy to assign to a new control point or null if no
   * tuningPolicy.
   */
  String getDiscoveryTuningPolicyName();
}
