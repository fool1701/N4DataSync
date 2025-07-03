/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.sys.*;

/**
 * WidgetSubscriber is a Subscriber for component events with tailored
 * callbacks for Widget events.  It provides a convenient mechanism to
 * listen for Widget events using inner classes:
 *
 * <pre> 
 *
 *   new WidgetSubscriber() 
 *   {            
 *     public void actionPerformed(BWidgetEvent e) { System.out.println("actionPerformed"); }
 *   }.subscribe(button);
 *   
 *   new WidgetSubscriber() 
 *   {            
 *     public void windowClosing(BWindowEvent e) { getWindow().close(); }
 *   }.subscribe(window);
 *
 * </pre>
 *
 * @author    Brian Frank
 * @creation  20 Oct 04
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:25 AM EST$
 * @since     Baja 1.0
 */
public class WidgetSubscriber
  extends Subscriber
{  
  
////////////////////////////////////////////////////////////////
// Subscriber
////////////////////////////////////////////////////////////////

  /**
   * If the event is a WidgetEvent route to widgetEvent().
   */    
  public void event(BComponentEvent event)
  {                     
    if (event.getId() == BComponentEvent.TOPIC_FIRED)
    {
      Object topicEvent = event.getValue();
      if (topicEvent instanceof BWidgetEvent)
        widgetEvent((BWidgetEvent)topicEvent);
    }
  }            
  
  /**
   * Route a WidgetEvent to a specific callback.  
   * Return true if routed, or false otherwise.
   */
  public boolean widgetEvent(BWidgetEvent event)
  {
    switch(event.getId())
    {
      // WidgetEvent
      case BWidgetEvent.MODIFIED:           modified(event); return true;
      case BWidgetEvent.ACTION_PERFORMED:   actionPerformed(event); return true;
      // FocusEvent
      case BFocusEvent.FOCUS_GAINED:        focusGained((BFocusEvent)event); return true;
      case BFocusEvent.FOCUS_LOST:          focusLost((BFocusEvent)event); return true;
      // KeyEvent
      case BKeyEvent.KEY_TYPED:             keyTyped((BKeyEvent)event); return true;
      case BKeyEvent.KEY_PRESSED:           keyPressed((BKeyEvent)event); return true;
      case BKeyEvent.KEY_RELEASED:          keyReleased((BKeyEvent)event); return true;
      // MouseEvent
      case BMouseEvent.MOUSE_PRESSED:       mousePressed((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_RELEASED:      mouseReleased((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_MOVED:         mouseMoved((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_ENTERED:       mouseEntered((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_EXITED:        mouseExited((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_DRAGGED:       mouseDragged((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_WHEEL:         mouseWheel((BMouseWheelEvent)event); return true;
      case BMouseEvent.MOUSE_PULSED:        mousePulsed((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_DRAG_STARTED:  mouseDragStarted((BMouseEvent)event); return true;
      case BMouseEvent.MOUSE_HOVER:         mouseHover((BMouseEvent)event); return true;
      // ScrollEvent
      case BScrollEvent.POSITION_CHANGED:   scrollEvent((BScrollEvent)event); return true;
      // SliderEvent
      case BSliderEvent.VALUE_CHANGED:      sliderEvent((BSliderEvent)event); return true;
      // WindowEvent
      case BWindowEvent.WINDOW_ACTIVATED:   windowActivated((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_CLOSED:      windowClosed((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_CLOSING:     windowClosing((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_DEACTIVATED: windowDeactivated((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_DEICONIFIED: windowDeiconified((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_OPENED:      windowOpened((BWindowEvent)event); return true;
      case BWindowEvent.WINDOW_ICONIFIED:   windowIconified((BWindowEvent)event); return true;
    } 
    return false;
  }              
    
////////////////////////////////////////////////////////////////
// WidgetEvent
////////////////////////////////////////////////////////////////

  public void modified(BWidgetEvent event) {}
  public void actionPerformed(BWidgetEvent event) {}

////////////////////////////////////////////////////////////////
// FocusEvent
////////////////////////////////////////////////////////////////

  public void focusGained(BFocusEvent event) {}
  public void focusLost(BFocusEvent event) {}

////////////////////////////////////////////////////////////////
// KeyEvent
////////////////////////////////////////////////////////////////

  public void keyTyped(BKeyEvent event) {}
  public void keyPressed(BKeyEvent event) {}
  public void keyReleased(BKeyEvent event) {}

////////////////////////////////////////////////////////////////
// MouseEvent
////////////////////////////////////////////////////////////////

  public void mousePressed(BMouseEvent event) {}
  public void mouseReleased(BMouseEvent event) {}
  public void mouseMoved(BMouseEvent event) {}
  public void mouseEntered(BMouseEvent event) {}
  public void mouseExited(BMouseEvent event) {}
  public void mouseDragged(BMouseEvent event) {}
  public void mouseWheel(BMouseWheelEvent event) {}
  public void mousePulsed(BMouseEvent event) {}
  public void mouseDragStarted(BMouseEvent event) {}
  public void mouseHover(BMouseEvent event) {}

////////////////////////////////////////////////////////////////
// ScrollEvent
////////////////////////////////////////////////////////////////

  public void scrollEvent(BScrollEvent event) {}

////////////////////////////////////////////////////////////////
// SliderEvent
////////////////////////////////////////////////////////////////

  public void sliderEvent(BSliderEvent event) {}

////////////////////////////////////////////////////////////////
// WindowEvent
////////////////////////////////////////////////////////////////

  public void windowActivated(BWindowEvent event) {}
  public void windowClosed(BWindowEvent event) {}
  public void windowClosing(BWindowEvent event) {}
  public void windowDeactivated(BWindowEvent event) {}
  public void windowDeiconified(BWindowEvent event) {}
  public void windowOpened(BWindowEvent event) {}
  public void windowIconified(BWindowEvent event) {}   
  
////////////////////////////////////////////////////////////////
// Test
////////////////////////////////////////////////////////////////
  
  /*
  public static void main(String args[])
  {
    BFrame f = new BFrame();
    BButton b = new BButton("Press Me");

    new WidgetSubscriber() 
    {            
      public void actionPerformed(BWidgetEvent e) { System.out.println("actionPerformed"); }
    }.subscribe(b);
    
    new WidgetSubscriber() 
    {            
      public void windowClosing(BWindowEvent e) { System.exit(0); }
    }.subscribe(f);
    
    f.setContent(b);
    f.open(100,100,400,400);
  }   
  */       
  
  
}
