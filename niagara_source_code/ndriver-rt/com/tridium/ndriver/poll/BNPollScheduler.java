/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.poll;

import javax.baja.driver.util.BIPollable;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BNPollScheduler is an implementation of BPollScheduler that polls BINPollable
 * implementations.  This allows a generic implementation of the doPoll() method
 * that calls doPoll() on the pollable object.
 *
 * @author Robert A Adams
 * @creation Dec 19, 2011
 */
@NiagaraType
public class BNPollScheduler
  extends BPollScheduler
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.poll.BNPollScheduler(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPollScheduler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Poll the specified point synchronously, blocking until polled successfully
   * or a failure is encountered. Failures should throw an exception.
   */
  @Override
  public void doPoll(BIPollable p)
    throws Exception
  {
    try
    {
      ((BINPollable)p).doPoll();
    }
    catch (Throwable e)
    {
      // TODO - log this
      e.printStackTrace();
    }
  }

  /**
   * Override to block subscription of {@link IPollables} that do not implement
   * {@link BINPollable}.
   */
  @Override
  public void subscribe(BIPollable p)
  {
    if (!(p instanceof BINPollable))
    {
      throw new BajaRuntimeException(p.getClass().getName() + " must implement BINPollable");
    }
    super.subscribe(p);
  }

}
