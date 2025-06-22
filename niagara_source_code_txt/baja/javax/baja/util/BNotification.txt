/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.job.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.util.*;

/**
 * BNotifications are used to broadcast events in a station
 * to a single workbench instance or all currently connected
 * workbench instances.
 *
 * Clients can customize how they respond to notifications using
 * the BNotificationHandler API in javax.baja.workbench.util.
 *
 * @author    Andy Frank       
 * @creation  7 Dec 06
 * @version   $Revision: 3$ $Date: 12/18/06 3:56:19 PM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
public class BNotification
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BNotification(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNotification.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Default constructor.
   */
  public BNotification()
  {
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Raise this notification.  If <code>broadcast</code> is true
   * this notification will propogate to all workbench shells
   * currently connected to this station.  If its false, it will
   * attempt to only propogate to the user who instanciated the
   * transaction where this notification was raised.  If you are
   * attempting to target the notification, you must call 
   * <code>raise()</code> on the same thread where you performing
   * the transaction, otherwise an exception will be thrown.
   */
  public final void raise(boolean broadcast)
  {
    // If not broadcast, add the VmUuid to the notification
    if (!broadcast)
    {
      try
      {
        Thread cur = Thread.currentThread();
        Context cx = ((ContextThread)cur).getContext();
        BString uid = (BString)cx.getFacets().get("foxRemoteVmUuid");
        
        // in case someone re-raises a notification instance
        if (get("vmUuid") == null) add("vmUuid", uid);
        else set("vmUuid", uid);
      }
      catch (Exception e)
      {
        throw new BajaRuntimeException("raise() failed. Cannot find vmUuid", e);
      }
    }
        
    // Piggy back on the JobService since it already handles
    // keeping workbench subscribed to the station    
    ((BJobService)BJobService.getService()).fireNotification(this);
  }

}
