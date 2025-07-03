/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BWindowEvent indicates a change in window state.
 *
 * @author    Brian Frank
 * @creation  9 Jan 01
 * @version   $Revision: 5$ $Date: 3/3/09 8:48:53 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWindowEvent
  extends BWidgetEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BWindowEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWindowEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////
  public static final int WINDOW_MOVED       = 100; //ComponentEvent.COMPONENT_MOVED;
  public static final int WINDOW_RESIZED     = 101; //ComponentEvent.COMPONENT_RESIZED;

  public static final int WINDOW_ACTIVATED   = 205; //WindowEvent.WINDOW_ACTIVATED;
  public static final int WINDOW_CLOSED      = 202; //WindowEvent.WINDOW_CLOSED;
  public static final int WINDOW_CLOSING     = 201; //WindowEvent.WINDOW_CLOSING;
  public static final int WINDOW_DEACTIVATED = 206; //WindowEvent.WINDOW_DEACTIVATED;
  public static final int WINDOW_DEICONIFIED = 204; //WindowEvent.WINDOW_DEICONIFIED;
  public static final int WINDOW_OPENED      = 200; //WindowEvent.WINDOW_OPENED;
  public static final int WINDOW_ICONIFIED   = 203; //WindowEvent.WINDOW_ICONIFIED;
//////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Framework use only
   */
  public BWindowEvent()
  {
    super(0, null);
  }    
  
  /**
   * Construct a new window event.
   */
  public BWindowEvent(int id, BWindow window)
  {
    super(id, window);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the source window of this event.
   */
  public BWindow getWindow()
  {
    return (BWindow)getWidget();
  }
  
}
