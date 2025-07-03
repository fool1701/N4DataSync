/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import com.tridium.ui.*;

/**
 * MouseCursor encapsulates a mouse cursor.
 * 
 * @author    Brian Frank
 * @creation  16 Aug 01
 * @version   $Revision: 9$ $Date: 12/3/08 9:45:06 AM EST$
 * @since     Baja 1.0
 */
public abstract class MouseCursor
{                        

  public static final MouseCursor normal     = make(UiEnv.CURSOR_DEFAULT);
  public static final MouseCursor crosshair  = make(UiEnv.CURSOR_CROSSHAIR);
  public static final MouseCursor hand       = make(UiEnv.CURSOR_HAND);
  public static final MouseCursor move       = make(UiEnv.CURSOR_MOVE);
  public static final MouseCursor text       = make(UiEnv.CURSOR_TEXT);
  public static final MouseCursor wait       = make(UiEnv.CURSOR_WAIT);      
  
  public static final MouseCursor eResize    = make(UiEnv.CURSOR_E_RESIZE);
  public static final MouseCursor wResize    = make(UiEnv.CURSOR_W_RESIZE);
  public static final MouseCursor nResize    = make(UiEnv.CURSOR_N_RESIZE);
  public static final MouseCursor sResize    = make(UiEnv.CURSOR_S_RESIZE);
  public static final MouseCursor neResize   = make(UiEnv.CURSOR_NE_RESIZE);
  public static final MouseCursor nwResize   = make(UiEnv.CURSOR_NW_RESIZE);
  public static final MouseCursor seResize   = make(UiEnv.CURSOR_SE_RESIZE);
  public static final MouseCursor swResize   = make(UiEnv.CURSOR_SW_RESIZE);
  
  public static final MouseCursor grabHand   = make(UiEnv.CURSOR_GRAB_HAND);      
  public static final MouseCursor openHand   = make(UiEnv.CURSOR_OPEN_HAND);
  public static final MouseCursor doNotEnter = make(UiEnv.CURSOR_DO_NOT_ENTER);
  public static final MouseCursor stack      = make(UiEnv.CURSOR_STACK);
  public static final MouseCursor dropLeft   = make(UiEnv.CURSOR_DROP_LEFT);      
  public static final MouseCursor dropRight  = make(UiEnv.CURSOR_DROP_RIGHT);
  public static final MouseCursor dropTop    = make(UiEnv.CURSOR_DROP_TOP);
  public static final MouseCursor dropBottom = make(UiEnv.CURSOR_DROP_BOTTOM);
  public static final MouseCursor dropper    = make(UiEnv.CURSOR_DROPPER);
  public static final MouseCursor magnify    = make(UiEnv.CURSOR_MAGNIFY);

  public static final MouseCursor linkLeft   = make(UiEnv.CURSOR_LINK_LEFT);
  public static final MouseCursor linkRight  = make(UiEnv.CURSOR_LINK_RIGHT);

  static MouseCursor make(int id)
  {
    return UiEnv.get().makeCursor(id);
  }

}

