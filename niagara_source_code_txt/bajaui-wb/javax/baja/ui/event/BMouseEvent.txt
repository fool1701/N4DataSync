/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import java.awt.event.MouseEvent;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDouble;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * BMouseEvents are triggered by mouse movement 
 * and button changes.
 *
 * @author    Brian Frank
 * @creation  21 Nov 00
 * @version   $Revision: 11$ $Date: 3/28/05 10:32:25 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMouseEvent
  extends BInputEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BMouseEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMouseEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  public static final int MOUSE_PRESSED  = 501;
  public static final int MOUSE_RELEASED = 502;
  public static final int MOUSE_MOVED    = 503;
  public static final int MOUSE_ENTERED  = 504;
  public static final int MOUSE_EXITED   = 505;
  public static final int MOUSE_DRAGGED  = 506;
  public static final int MOUSE_WHEEL    = 507;
  public static final int MOUSE_PULSED   = 510;
  public static final int MOUSE_DRAG_STARTED = 511;
  public static final int MOUSE_HOVER    = 512;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new mouse event.
   */
  public BMouseEvent(int id, BWidget source, int modifiers, 
                     double x, double y, int clickCount, boolean isPopupTrigger)
  {
    super(id, source, modifiers);
    this.x = x;
    this.y = y;
    this.clickCount = clickCount;
    this.isPopupTrigger = isPopupTrigger;
  }

  public BMouseEvent(int id, BWidget source, long when, int modifiers, 
      double x, double y, int clickCount, boolean isPopupTrigger)  
  {
    super(id, source, when, modifiers);
    this.x = x;
    this.y = y;
    this.clickCount = clickCount;
    this.isPopupTrigger = isPopupTrigger;
  }
  
  /**
   * No arg constructor
   */
  public BMouseEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  @Override
  protected void setNewModifiers()
  {
    super.setNewModifiers();

    if ((modifiers & BUTTON2_MASK) != 0)
    {
      modifiers |= BUTTON2_DOWN_MASK;
    }
    if ((modifiers & BUTTON3_MASK) != 0)
    {
      modifiers |= BUTTON3_DOWN_MASK;
    }
  }

  @Override
  protected void setOldModifiers()
  {
    super.setOldModifiers();
    if ((modifiers & BUTTON2_DOWN_MASK) != 0)
    {
      modifiers |= BUTTON2_MASK;
    }
    if ((modifiers & BUTTON3_DOWN_MASK) != 0)
    {
      modifiers |= BUTTON3_MASK;
    }
  }

  /**
   * Returns whether the button1 modifier is set.
   */
  public boolean isButton1Down()
  {
    return (getModifiersEx() & BUTTON1_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the button2 modifier is set.
   */
  public boolean isButton2Down()
  {
    return (getModifiersEx() & BUTTON2_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the button3 modifier is set.
   */
  public boolean isButton3Down()
  {
    return (getModifiersEx() & BUTTON3_DOWN_MASK) != 0;
  }

  /**
   * Get the x coordinate of the event relative
   * to the source widget's coordinate space.
   */
  public double getX()
  {
    return x;
  }

  /**
   * Get the y coordinate of the event relative
   * to the source widget's coordinate space.
   */
  public double getY()
  {
    return y;
  }
  
  /**
   * Get the number of times the mouse button
   * has been clicked.
   */
  public int getClickCount()
  {
    return clickCount;
  }

  /**
   * Is this mouse event a popup trigger.
   */
  public boolean isPopupTrigger()
  {
    return isPopupTrigger;
  }

  /**
   * Get a string representation.
   */
  public String toString(Context cx)
  {               
    if (getWidget() == null) return "null";
    
    String id = "?";
    switch(getId())
    {
      case MOUSE_PRESSED:      id = "MousePressed"; break;
      case MOUSE_RELEASED:     id = "MouseReleased"; break;
      case MOUSE_MOVED:        id = "MouseMoved"; break;
      case MOUSE_ENTERED:      id = "MouseEntered"; break;
      case MOUSE_EXITED:       id = "MouseExited"; break;
      case MOUSE_DRAGGED:      id = "MouseDragged"; break;
      case MOUSE_WHEEL:        id = "MouseWheel"; break;
      case MOUSE_PULSED:       id = "MousePulsed"; break;
      case MOUSE_DRAG_STARTED: id = "MouseDragStarted"; break;
      case MOUSE_HOVER:        id = "MouseHover"; break;
    }       
    
    return id +                                                                  
           " src=" + getWidget().getType() +
           " pt=" + BDouble.toString(x, null) + "," + BDouble.toString(y, null) +
           " mods=" + modifiersToString();
  }

  /**
   * Get a debug string for the modifiers set.
   * Starting in Niagara 4.12, this method now includes both the key modifiers and the button modifiers.
   * @since Niagara 4.12
   */
  @Override
  public String modifiersToString()
  {
    String s = super.modifiersToString();
    s+=" ";
    if (isButton1Down()) {s += "1 ";}
    if (isButton2Down()) {s += "2 ";}
    if (isButton3Down()) {s += "3 ";}
    return s.trim();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private double x;
  private double y;
  private int clickCount;
  private boolean isPopupTrigger;
}
