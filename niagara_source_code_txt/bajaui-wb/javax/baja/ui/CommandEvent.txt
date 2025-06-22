/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.ui.event.*;

/**
 * CommandEvent
 *
 * @author    Brian Frank
 * @creation  9 May 05
 * @version   $Revision: 1$ $Date: 5/9/05 3:33:20 PM EDT$
 * @since     Baja 1.0
 */
public class CommandEvent
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Create CommandEvent for the specified InputEvent.
   */
  public CommandEvent(BInputEvent event)
  {                                
    this.event = event;
  }                          
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the event which triggered this command, or 
   * null if not triggered off of user input.
   */
  public BInputEvent getInputEvent()
  {      
    return event;
  }

  /**
   * Convenience for <code>getInputEvent().isAltDown()</code>.
   * If event is null return false.
   */
  public boolean isAltDown()
  {                                                      
    if (event == null) return false;
    return event.isAltDown();
  }

  /**
   * Convenience for <code>getInputEvent().isAltGraphDown()</code>.
   * If event is null return false.
   */
  public boolean isAltGraphDown()
  {
    if (event == null) return false;
    return event.isAltGraphDown();
  }

  /**
   * Convenience for <code>getInputEvent().isControlDown()</code>.
   * If event is null return false.
   */
  public boolean isControlDown()
  {
    if (event == null) return false;
    return event.isControlDown();
  }

  /**
   * Convenience for <code>getInputEvent().isMetaDown()</code>.
   * If event is null return false.
   */
  public boolean isMetaDown()
  {
    if (event == null) return false;
    return event.isMetaDown();
  }

  /**
   * Convenience for <code>getInputEvent().isShiftDown()</code>.
   * If event is null return false.
   */
  public boolean isShiftDown()
  {
    if (event == null) return false;
    return event.isShiftDown();
  }
             
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  static final CommandEvent NULL = new CommandEvent(null);
  
  private BInputEvent event;
}
