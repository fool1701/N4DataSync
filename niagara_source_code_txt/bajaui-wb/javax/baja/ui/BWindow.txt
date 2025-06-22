/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.ui.enums.BWindowSizeEnum;
import javax.baja.ui.event.*;

import com.tridium.ui.*;
import com.tridium.ui.util.LayoutUtil;

/**
 * BWindow is the base class for BWidgetShells which are 
 * roots for stand alone windows in the native windowing
 * system.  It may also be used directly as an "undecorated"
 * window.
 *
 * @author    Brian Frank on 9 Jan 01
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This call first routes the event to one the widget
 windowX(BWindowEvent) callbacks, then fires it for any
 potential links.
 */
@NiagaraTopic(
  name = "windowEvent",
  eventType = "BWindowEvent"
)

/*
 Any Actions or topics can be linked to this resizeWindow action. The
 parameter used for resizing will be `BWindowSizeEnum.PREFERRED_SIZE` unless the
 parameters is an instanceof BWindowSizeEnum. This flexibility allows any action or
 topic to be linked.
 */
@NiagaraAction(
  name = "resizeWindow",
  parameterType = "BValue",
  defaultValue = "BWindowSizeEnum.PREFERRED_SIZE",
  flags =  Flags.HIDDEN
)
@NoSlotomatic //custom fireWindowEvent implementation
public class BWindow
  extends BWidgetShell
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BWindow(3535681070)1.0$ @*/
/* Generated Wed Jul 20 08:51:56 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "resizeWindow"

  /**
   * Slot for the {@code resizeWindow} action.
   * @see #resizeWindow(BValue parameter)
   */
  public static final Action resizeWindow = newAction(Flags.HIDDEN, BWindowSizeEnum.DEFAULT, null);

  /**
   * Invoke the {@code resizeWindow} action.
   * @see #resizeWindow
   */
  public void resizeWindow(BValue parameter) { invoke(resizeWindow, parameter, null); }

  //endregion Action "resizeWindow"

  //region Topic "windowEvent"

  /**
   * Slot for the {@code windowEvent} topic.
   * This call first routes the event to one the widget
   * windowX(BWindowEvent) callbacks, then fires it for any
   * potential links.
   * @see #fireWindowEvent
   */
  public static final Topic windowEvent = newTopic(0, null);

  //endregion Topic "windowEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWindow.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Fire an event for the {@code windowEvent} topic.
   * This call first routes the event to one the widget
   * windowX(BWindowEvent) callbacks, then fires it for any
   * potential links.
   * @see #windowEvent
   */
  public void fireWindowEvent(BWindowEvent event)
  {
    switch(event.getId())
    {
      case BWindowEvent.WINDOW_ACTIVATED:   windowActivated(event); break;
      case BWindowEvent.WINDOW_CLOSED:      windowClosed(event); break;
      case BWindowEvent.WINDOW_CLOSING:     windowClosing(event); break;
      case BWindowEvent.WINDOW_DEACTIVATED: windowDeactivated(event); break;
      case BWindowEvent.WINDOW_DEICONIFIED: windowDeiconified(event); break;
      case BWindowEvent.WINDOW_ICONIFIED:   windowIconified(event); break;
      case BWindowEvent.WINDOW_OPENED:      windowOpened(event); break;
    }
    fire(windowEvent, event, null);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a BWindow with the specified owner and content.
   */
  public BWindow(BWidget owner, BWidget content)
  {
    super(UiEnv.get().makeWindowPeer(owner));
    setContent(content);
  }

  /**
   * Construct a BWindow with the specified owner.
   */
  public BWindow(BWidget owner)
  {
    super(UiEnv.get().makeWindowPeer(owner));
  }

  /**
   * No argument constructor.
   */
  public BWindow()
  {
    super(UiEnv.get().makeWindowPeer(null));
  }

  /**
   * Package private constuctor.
   */
  BWindow(WindowPeer peer)
  {
    super(peer);
  }

////////////////////////////////////////////////////////////////
// Window
////////////////////////////////////////////////////////////////

  /**
   * Get the rectangle this frame occupies on the screen.
   */
  public IRectGeom getScreenBounds()
  {
    return getShellPeer().getScreenBounds();
  }

  /**
   * Set the window bounds.
   */
  public void setScreenBounds(double x, double y, double w, double h)
  {
    ((WindowPeer)getShellPeer()).setScreenBounds(x, y, w, h);
  }

  /**
   * Set the window's bounds, then call open().
   */
  public void open(double x, double y, double w, double h)
  {
    setScreenBounds(x, y, w, h);
    open();
  }

  /**
   * Open the window its current screen bounds.
   */
  public void open()
  {
    ((WindowPeer)getShellPeer()).open();
  }

  /**
   * Close the window.
   */
  public void close()
  {
    ((WindowPeer)getShellPeer()).close();
  }

  /**
   * Return if this window is showing on the screen.
   */
  public boolean isShowing()
  {
    return ((WindowPeer)getShellPeer()).isShowing();
  }

  /**
   * Bring this window to the front.
   */
  public void toFront()
  {
    ((WindowPeer)getShellPeer()).toFront();
  }

  /**
   * Bring this window to the back.
   */
  public void toBack()
  {
    ((WindowPeer)getShellPeer()).toBack();
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Set the dialog bounds to be centered on
   * the screen.
   */
  public void setBoundsCenteredOnScreen()
  {
    setBoundsCenteredOn(UiEnv.get().getScreenBounds(this));
  }

  /**
   * Set the bounds of this dialog centered over the
   * specified rectangle in the screen's coordinate
   * system.  Attempt to ensure that the dialog doesn't
   * hang over the edge of the screen.
   */
  public void setBoundsCenteredOn(IRectGeom rect)
  {
    // get window insets
    Insets insets = UiEnv.get().getWindowInsets(this);

    // compute preferred size
    computePreferredSize();
    double w = getPreferredWidth() + insets.left + insets.right;
    double h = getPreferredHeight() + insets.top + insets.bottom;
    double x = rect.x() + (rect.width()-w)/2;
    double y = rect.y() + (rect.height()-h)/2;

    // ensure on screen
    IRectGeom screen = UiEnv.get().getScreenBounds(this);
    double sx = screen.x();
    double sy = screen.y();
    double sw = screen.width();
    double sh = screen.height();

    // if bigger than screen, then clip to screen size
    if (w > sw) w = sw;
    if (h > sh) h = sh;

    // if we are off the edge, then we
    // need to reposition ourselves
    if (x+w > sx+sw) x = sw-w;
    if (y+h > sy+sh) y = sh-h;
    if (x < sx) x = sx;
    if (y < sy) y = sy;

    // set the bounds
    setScreenBounds(x, y, w, h);
  }

  /**
   * Set this dialog's screen size to the preferred
   * size of the dialog.
   */
  public void setScreenSizeToPreferredSize()
  {
    LayoutUtil.resizeWindow(this, BWindowSizeEnum.preferredSize);
  }

  /**
   * Set this dialog's screen size to the preferred size of the dialog based
   * on the BWindowSizeEnum provided.
   * @since Niagara 4.13
   */
  public void setScreenSizeToPreferredSize(BWindowSizeEnum windowSizeEnum)
  {
    LayoutUtil.resizeWindow(this, windowSizeEnum);
  }

  /**
   * Set this dialog's screen size to the preferred size of the dialog based
   * on the argument provided.
   * @since Niagara 4.13
   */
  public void doResizeWindow(BValue argument)
  {
    BWindowSizeEnum windowSizeEnum = BWindowSizeEnum.preferredSize;
    if(argument instanceof BWindowSizeEnum)
    {
      windowSizeEnum = (BWindowSizeEnum) argument;
    }
    LayoutUtil.resizeWindow(this, windowSizeEnum);
  }

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_ACTIVATED.
   */
  public void windowActivated(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_CLOSED.
   */
  public void windowClosed(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_CLOSING.
   */
  public void windowClosing(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_DEACTIVATED.
   */
  public void windowDeactivated(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_DEICONIFIED.
   */
  public void windowDeiconified(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_ICONIFIED.
   */
  public void windowIconified(BWindowEvent event) {}

  /**
   * This callback is invoked when the fireWindowEvent() is 
   * invoked with a BWindowEvent with an id of WINDOW_OPENED.
   */
  public void windowOpened(BWindowEvent event) {}
}
