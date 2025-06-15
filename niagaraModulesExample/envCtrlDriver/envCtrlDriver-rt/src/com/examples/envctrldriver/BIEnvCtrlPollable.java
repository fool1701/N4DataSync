/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.driver.util.BIPollable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Simple poll scheduler interface implemented by components
 * that wish to receive a poll callback from the poll scheduler.
 *
 * @author J. Spangler on Mar 20, 2013
 */
@NiagaraType
public interface BIEnvCtrlPollable
  extends BIPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BIEnvCtrlPollable(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIEnvCtrlPollable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * The poll() callback method called from {@link BEnvCtrlPollScheduler}
   * when it is time to poll this object.
   */
  public void poll();
}
