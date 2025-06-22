/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver;

import javax.baja.driver.BDevice;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

/**
 * BNDevice
 *
 * @author Robert A Adams
 * @creation Jan 9, 2012
 * @since Niagara 3.7
 */
@NiagaraType
public abstract class BNDevice
  extends BDevice
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.BNDevice(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BDevice
////////////////////////////////////////////////////////////////
  @Override
  public abstract Type getNetworkType();

////////////////////////////////////////////////////////////////
// BNDevice
////////////////////////////////////////////////////////////////
  @Override
  public abstract void doPing() throws Exception;

  /**
   * Override point to specify the subscribe depth used by auto point manager.
   *
   * @return default returns 2
   * @since Niagara 4.8
   */
  public int getPointManagerSubscribeDepth()
  {
    return 2;
  }
  
  /**
   * Convenience for {@code getNetwork().postAsync(r)}.
   */
  public IFuture postAsync(Runnable r)
  {
    return ((BNNetwork)getNetwork()).postAsync(r);
  }

  /**
   * Post a ping Invocation.
   */
  @Override
  protected IFuture postPing()
  {
    return postAsync(new Invocation(this, ping, null, null));
  }
}
