/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BInputEvent is the base class for widget
 * events which are triggered by user input.
 *
 * @author    Brian Frank
 * @creation  21 Nov 00
 * @version   $Revision: 7$ $Date: 3/3/09 10:19:17 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BInputEvent
  extends BWidgetEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BInputEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInputEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Modifiers
////////////////////////////////////////////////////////////////

  /**
   * The Alt key modifier constant.
   *
   * It is recommended that ALT_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int ALT_MASK     = 8;    //InputEvent.ALT_MASK

  /**
   * The AltGraph key modifier constant.
   *
   * It is recommended that ALT_GRAPH_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int ALT_GRAPH_MASK = 32; //InputEvent.ALT_GRAPH_MASK

  /**
   * The Mouse Button1 modifier constant.
   *
   * It is recommended that BUTTON1_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int BUTTON1_MASK = 16;   //InputEvent.BUTTON1_MASK

  /**
   * The Mouse Button2 modifier constant.
   *
   * It is recommended that BUTTON2_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int BUTTON2_MASK = 8;    //InputEvent.BUTTON2_MASK

  /**
   * The Mouse Button3 modifier constant.
   *
   * It is recommended that BUTTON3_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int BUTTON3_MASK = 4;    //InputEvent.BUTTON3_MASK

  /**
   * The Control key modifier constant.
   *
   * It is recommended that CTRL_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int CTRL_MASK    = 2;    //InputEvent.CTRL_MASK

  /**
   * The Meta key modifier constant.
   *
   * It is recommended that META_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int META_MASK    = 4;    //InputEvent.META_MASK

  /**
   * The Shift key modifier constant.
   *
   * It is recommended that SHIFT_DOWN_MASK and {@link #getModifiersEx()} be used instead.
   */
  public static final int SHIFT_MASK   = 1;    //InputEvent.SHIFT_MASK

  /**
   * The Shift key extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int SHIFT_DOWN_MASK = 1 << 6;

  /**
   * The Control key extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int CTRL_DOWN_MASK = 1 << 7;

  /**
   * The Meta key extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int META_DOWN_MASK = 1 << 8;

  /**
   * The Alt key extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int ALT_DOWN_MASK = 1 << 9;

  /**
   * The Mouse Button1 extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int BUTTON1_DOWN_MASK = 1 << 10;

  /**
   * The Mouse Button2 extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int BUTTON2_DOWN_MASK = 1 << 11;

  /**
   * The Mouse Button3 extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int BUTTON3_DOWN_MASK = 1 << 12;

  /**
   * The AltGraph key extended modifier constant.
   * @since Niagara 4.12
   */
  public static final int ALT_GRAPH_DOWN_MASK = 1 << 13;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new widget event for the specified source widget.
   *
   * @param id integer indicating the type of event
   * @param source The BWidget that originated the event
   * @param modifiers The modifier keys down during event (shift, ctrl, alt, meta). Zero value means that no modifiers were passed
   *                  Starting in Niagara 4.12, either an extended _DOWN_MASK or old _MASK modifiers can be used,
   *                  however do not mix models in the one event.
   * @see java.awt.event.InputEvent
   */
  public BInputEvent(int id, BWidget source, int modifiers)
  {   
    super(id, source);
    this.modifiers = modifiers;
    this.when = Clock.millis();
    configureModifiers();
  }

  public BInputEvent(int id, BWidget source, long when, int modifiers)
  {   
    super(id, source);
    this.modifiers = modifiers;
    this.when = when;
    configureModifiers();
  }

  /**
   * No arg constructor
   */
  public BInputEvent()
  {
  }

  private void configureModifiers()
  {
    if (getModifiers() != 0 && getModifiersEx() == 0) {
      setNewModifiers();
    } else if (getModifiers() == 0 && getModifiersEx() != 0) {
      setOldModifiers();
    }
  }

  /**
   * Sets the new modifiers by the old ones.
   *
   * @since Niagara 4.12
   */
  protected void setNewModifiers() {
    if ((modifiers & SHIFT_MASK) != 0) {
      modifiers |= SHIFT_DOWN_MASK;
    }
    if ((modifiers & ALT_MASK) != 0) {
      modifiers |= ALT_DOWN_MASK;
    }
    if ((modifiers & CTRL_MASK) != 0) {
      modifiers |= CTRL_DOWN_MASK;
    }
    if ((modifiers & META_MASK) != 0) {
      modifiers |= META_DOWN_MASK;
    }
    if ((modifiers & ALT_GRAPH_MASK) != 0) {
      modifiers |= ALT_GRAPH_DOWN_MASK;
    }
    if ((modifiers & BUTTON1_MASK) != 0) {
      modifiers |= BUTTON1_DOWN_MASK;
    }
  }

  /**
   * Sets the old modifiers by the new ones.
   *
   * @since Niagara 4.12
   */
  protected void setOldModifiers() {
    if ((modifiers & SHIFT_DOWN_MASK) != 0) {
      modifiers |= SHIFT_MASK;
    }
    if ((modifiers & ALT_DOWN_MASK) != 0) {
      modifiers |= ALT_MASK;
    }
    if ((modifiers & CTRL_DOWN_MASK) != 0) {
      modifiers |= CTRL_MASK;
    }
    if ((modifiers & META_DOWN_MASK) != 0) {
      modifiers |= META_MASK;
    }
    if ((modifiers & ALT_GRAPH_DOWN_MASK) != 0) {
      modifiers |= ALT_GRAPH_MASK;
    }
    if ((modifiers & BUTTON1_DOWN_MASK) != 0) {
      modifiers |= BUTTON1_MASK;
    }
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the modifier mask for this event.
   *
   * It is recommended that extended modifier keys and
   *             {@link #getModifiersEx()} be used instead.
   */
  public int getModifiers() {
    return modifiers & (JDK_1_3_MODIFIERS | HIGH_MODIFIERS);
  }


  /**
   * Returns the extended modifier mask for this event.
   *
   * @since Niagara 4.12
   */
  public int getModifiersEx() {
    return modifiers & ~JDK_1_3_MODIFIERS;
  }

  /**
   * Get the timestamp in millis when the event occurred
   */
  public long getWhen()
  {
    return when;
  }
  
  /**
   * Returns whether the alt modifier is set.
   */
  public boolean isAltDown()
  {
    return (modifiers & ALT_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the alt graph modifier is set.
   */
  public boolean isAltGraphDown()
  {
    return (modifiers & ALT_GRAPH_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the control modifier is set.
   */
  public boolean isControlDown()
  {
    return (modifiers & CTRL_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the meta modifier is set.
   */
  public boolean isMetaDown()
  {
    return (modifiers & META_DOWN_MASK) != 0;
  }

  /**
   * Returns whether the shift modifier is set.
   */
  public boolean isShiftDown()
  {
    return (modifiers & SHIFT_DOWN_MASK) != 0;
  }

  /**
   * Get a debug string for the modifiers set.
   */
  public String modifiersToString()
  {                      
    String s = "";
    if (isAltDown())      s += "Alt ";
    if (isAltGraphDown()) s += "AltGraph ";
    if (isControlDown())  s += "Ctrl ";
    if (isMetaDown())     s += "Meta ";
    if (isShiftDown())    s += "Shift ";
    return s.trim();
  }

  /**
   * Return true if this key event was consumed.
   */
  public boolean isConsumed()
  {
    return consumed;
  }
  
  /**
   * This method is called to consume the key event
   * so that it is not propogated to other controls 
   * such as focus traversal, accelerators, or 
   * default buttons.
   */
  public void consume()
  {
    consumed = true;
  }               
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  int modifiers;
  private boolean consumed;
  private long when;

  static final int FIRST_HIGH_BIT = 1 << 31;

  static final int JDK_1_3_MODIFIERS = SHIFT_DOWN_MASK - 1;
  static final int HIGH_MODIFIERS = ~( FIRST_HIGH_BIT - 1 );

}
