/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BMouseWheelEvents are triggered by the mouse wheel.
 *
 * @author    Brian Frank
 * @creation  3 Jul 02
 * @version   $Revision: 3$ $Date: 3/22/04 11:05:03 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMouseWheelEvent
  extends BMouseEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BMouseWheelEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMouseWheelEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BMouseWheelEvent(int id, BWidget source, int modifiers, 
                          double x, double y, int clickCount, 
                          boolean isPopupTrigger, int wheelRotation)
  {
    super(id, source, modifiers, x, y, clickCount, isPopupTrigger);
    this.wheelRotation = wheelRotation;
    this.preciseWheelRotation = wheelRotation;
    this.scrollType = WHEEL_UNIT_SCROLL;
    this.scrollAmount = 3;
  }

  public BMouseWheelEvent(int id, BWidget source, long when, int modifiers, 
      double x, double y, int clickCount, boolean isPopupTrigger, 
      int scrollType, int scrollAmount, int wheelRotation)
  {
    this(id, source, when, modifiers, x, y, clickCount, isPopupTrigger,
      scrollType, scrollAmount, wheelRotation, wheelRotation);
  }

  /**
   * @since Niagara 4.8
   */
  public BMouseWheelEvent(int id, BWidget source, long when, int modifiers,
                          double x, double y, int clickCount, boolean isPopupTrigger,
                          int scrollType, int scrollAmount, int wheelRotation,
                          double preciseWheelRotation)
  {
    super(id, source, when, modifiers, x, y, clickCount, isPopupTrigger);
    this.wheelRotation = wheelRotation;
    this.preciseWheelRotation = preciseWheelRotation;
    this.scrollType = scrollType;
    this.scrollAmount = scrollAmount;
  }

  public BMouseWheelEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the amount of wheel rotation. May be 0 if the user has a precise pointing device like a trackpad. See
   * {@code getPreciseWheelRotation}.
   */
  public int getWheelRotation()
  {
    return wheelRotation;
  }

  /**
   * Get the amount of precise wheel rotation. If not from a precise pointing
   * device (e.g. trackpad) it will be the same as the non-precise wheel
   * rotation.
   * @since Niagara 4.8
   * @return precise wheel rotation
   */
  public double getPreciseWheelRotation()
  {
    return preciseWheelRotation;
  }

  public int getScrollType()
  {
    return scrollType;
  }

  public int getScrollAmount()
  {
    return scrollAmount;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  public static final int WHEEL_UNIT_SCROLL = 0;
  public static final int WHEEL_BLOCK_SCROLL = 1;

  
  private int wheelRotation;
  private double preciseWheelRotation;
  private int scrollType;
  private int scrollAmount;
}
