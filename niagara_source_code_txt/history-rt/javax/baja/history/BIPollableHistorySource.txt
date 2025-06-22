/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIPollableHistorySource is necessary due to how component subscription works.
 * For the purpose of on-demand history support, we need to be able to subscribe 
 * to a history, which in turn subscribes to the source component(s) of the 
 * history.  In some cases, a subscription causes the history source component to 
 * start "polling" in order to provide "real time" updates to the view of the history, hence on-demand.
 * The problem occurs when that same history source component is viewed some other way (such as
 * a property sheet view of the component).  In such cases, the component will also
 * become subscribed, but in that case, we don't want to start "polling" for updates
 * since no one may be looking at the history itself.  An example of this is history import 
 * descriptors.  We only want them to "poll" when they have been subscribed due to
 * someone looking at the history chart/history table view, but we don't want them
 * to poll if someone is just looking at them in the history import manager or 
 * property sheet view (which also causes them to be subscribed). 
 *
 * That's a lot of explanation, but the basic idea of this class is that we need some way
 * to put a history subscription counter on "pollable" history source components, so that we can increment/decrement
 * a history subscription counter for the "pollable" history source component as it is subscribed/unsubscribed by
 * a history view (such as the history chart/history table view).  The "pollable" history source
 * component can then check this counter to determine whether it needs to "poll" or
 * not whenever it is in a subscribed state.  Not all history sources will need to use this interface, as some history sources
 * (such as history extensions) don't need to do anything in a subscribed state, so we
 * don't care in such cases.  However, this interface should be implemented by any history source
 * components (ie. history import descriptors) that DO need to do work (ie. poll) when 
 * subscribed in order to update the history.  The behavior contract is that the implementing
 * class should only poll for updates to the history when the history subscription counter
 * is greater than zero.  Also, when the source component is unsubscribed(), it should force the
 * history subscription counter to zero.
 *
 * @author    Scott Hoye
 * @creation  1 May 2008
 * @version   $Revision: 2$ $Date: 9/3/08 11:51:37 PM EDT$
 * @since     Niagara 3.4
 */
@NiagaraType
public interface BIPollableHistorySource
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BIPollableHistorySource(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIPollableHistorySource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Returns true if this object supports history polling (subscription), 
   * or false if it does not (does not support subscription).
   */
  public boolean historyPollingEnabled();
  
  /**
   * This callback is made to cause the history subscription counter to
   * be incremented (positive change value) or decremented (negative change value).
   * The returning value is the current history subscription counter value
   * after the increment/decrement has been processed.
   *
   * NOTE: This method should be synchronized in some way (either the method
   * itself, or it's implementation), as concurrent threads may attempt to call this
   * method simultaneously.
   */
  public int updateHistorySubscriptionCount(int change);
}
