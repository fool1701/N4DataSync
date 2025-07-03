/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.history;

import javax.baja.driver.util.BIPollable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIHistoryPollable is the interface implemented by archive
 * descriptors that can register with the history poll scheduler
 * for polling.
 *
 * @author    Scott Hoye
 * @creation  3 Apr 08
 * @version   $Revision 1$ $Date: 5/15/08 4:15:22 PM EDT$
 * @since     Niagara 3.4
 */
@NiagaraType
public interface BIHistoryPollable
  extends BIPollable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BIHistoryPollable(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIHistoryPollable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Callback when the poll scheduler says it's time
   * to poll this object.
   */
  public void poll();
}
