/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Target components that use ndriver auto manager views may implement this
 * interface to add auto discovery functionality.
 * <p>
 * The BNPointDeviceExtension extension implement this interface.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
public interface BINDiscoveryHost
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BINDiscoveryHost(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINDiscoveryHost.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Implements action submitDiscoveryJob.
   *
   * @param instance of BNDiscoveryPreferences
   * @return BOrd to the job that is running in the station.
   */
  BOrd submitDiscoveryJob(BNDiscoveryPreferences discoveryParams);

  /**
   * To persist user selections add discoveryPreferences slot with this
   * implemented as getter.
   *
   * @return auto discovery job preferences
   */
  BNDiscoveryPreferences getDiscoveryPreferences();


  /**
   * Call back for discoveryJob to get an array of discovery objects. Override
   * point for driver specific discovery.
   */
  BINDiscoveryObject[] getDiscoveryObjects(BNDiscoveryPreferences prefs)
    throws Exception;
}
