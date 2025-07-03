/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */ 
package javax.baja.workbench.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.pane.BEdgePane;

import com.tridium.workbench.util.NotifyManager;

/**
 * BNotifyPane is used for async notifications.
 * 
 * @author    Andy Frank
 * @creation  26 Jul 04
 * @version   $Revision: 2$ $Date: 3/28/05 1:41:02 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNotifyPane
  extends BEdgePane
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.util.BNotifyPane(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNotifyPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Open notification pane with default timeout.
   */
  public void open()
  {
    open(DEFAULT_TIMEOUT);
  }
  
  /**
   * Open notification pane with given timeout in 
   * millis. Use -1 to disable timeout.
   */
  public void open(int timeout)
  {
    NotifyManager.get().open(this, timeout);
  }
  
  /**
   * Close the notification window.
   */  
  public void close()
  {
    NotifyManager.get().close(this);
  }  
  
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /** Default timeout in millis. */
  private static final int DEFAULT_TIMEOUT = 5000;
}
