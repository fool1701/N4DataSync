/*
 * Copyright 2000 Tridium Inc. All Rights Reserved.
 */
package javax.baja.lonworks.ext;

import javax.baja.driver.util.BIPollable;
import javax.baja.driver.util.BPollScheduler;
import javax.baja.lonworks.BLonNetwork;
import javax.baja.lonworks.BNetworkVariable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *   The BLonPollService class provides a concrete class
 *  for polling lonworks devices.
 * <p>
 *
 * @author    Robert Adams
 * @creation  23 January 02
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:44 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BLonPollService
  extends BPollScheduler
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.ext.BLonPollService(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPollService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Poll the specified point synchronously, blocking
   * until polled successfully or a failure is encountered.
   * Failures should throw an exception.
   */
  public void doPoll(BIPollable p)
    throws Exception
  {
    if( !((BLonNetwork)getParent()).isServiceRunning()) return;

    try
    {
      ((BNetworkVariable)p).pollNv();
    }
    catch(Throwable e)
    {
      // Failure of read already report - no need to pass this exception
    }
  }


}
