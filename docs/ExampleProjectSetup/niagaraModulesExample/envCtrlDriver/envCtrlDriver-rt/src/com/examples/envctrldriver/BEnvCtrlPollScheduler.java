/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.util.BIPollable;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

@NiagaraType
public class BEnvCtrlPollScheduler
  extends BPollScheduler
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.BEnvCtrlPollScheduler(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnvCtrlPollScheduler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * This method is called by the Poll Scheduler on components
   * which implement the {@link BIPollable} interface.
   */
  public void doPoll(BIPollable p)
    throws Exception
  {
    //flag indicating whether we should poll
    boolean shouldPoll = true;
    try
    {
      //check the state of our network to determine if we should
      //perform a poll
      BDeviceNetwork net = (BDeviceNetwork)getParent();
      shouldPoll = (!net.isDisabled()) && (!net.isDown()) && (!net.isFault());
    }
    catch (Exception e)
    {
      shouldPoll = true;
    }

    //return if we're not performing a poll
    if (!shouldPoll)
    {
      return;
    }

    //poll the component which is an implementation of our interface
    BIEnvCtrlPollable dev = (BIEnvCtrlPollable)p;
    try
    {
      dev.poll();
    }
    catch (NotRunningException e)
    {
      //if we run into a problem, unsubscribe
      unsubscribe(dev);
    }
  }
}
