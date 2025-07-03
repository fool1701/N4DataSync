/*
* Copyright 2000 Tridium, Inc. All Rights Reserved.
*/
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
* BSpinnerButton
*
* @author    Mike Jarmy
* @creation  26 Mar 01
* @version   $Revision: 29$ $Date: 6/30/11 5:11:34 PM EDT$
* @since     Baja 1.0
*/
@NiagaraType
@NiagaraAction(
  name = "handleUpMouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
@NiagaraAction(
  name = "handleDownMouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
@NiagaraTopic(
  name = "increment",
  eventType = "BWidgetEvent"
)
@NiagaraTopic(
  name = "decrement",
  eventType = "BWidgetEvent"
)
public class BSpinnerButton
  extends BWidget
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BSpinnerButton(1003858870)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "handleUpMouseEvent"

  /**
   * Slot for the {@code handleUpMouseEvent} action.
   * @see #handleUpMouseEvent(BMouseEvent parameter)
   */
  public static final Action handleUpMouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code handleUpMouseEvent} action.
   * @see #handleUpMouseEvent
   */
  public void handleUpMouseEvent(BMouseEvent parameter) { invoke(handleUpMouseEvent, parameter, null); }

  //endregion Action "handleUpMouseEvent"

  //region Action "handleDownMouseEvent"

  /**
   * Slot for the {@code handleDownMouseEvent} action.
   * @see #handleDownMouseEvent(BMouseEvent parameter)
   */
  public static final Action handleDownMouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code handleDownMouseEvent} action.
   * @see #handleDownMouseEvent
   */
  public void handleDownMouseEvent(BMouseEvent parameter) { invoke(handleDownMouseEvent, parameter, null); }

  //endregion Action "handleDownMouseEvent"

  //region Topic "increment"

  /**
   * Slot for the {@code increment} topic.
   * @see #fireIncrement
   */
  public static final Topic increment = newTopic(0, null);

  /**
   * Fire an event for the {@code increment} topic.
   * @see #increment
   */
  public void fireIncrement(BWidgetEvent event) { fire(increment, event, null); }

  //endregion Topic "increment"

  //region Topic "decrement"

  /**
   * Slot for the {@code decrement} topic.
   * @see #fireDecrement
   */
  public static final Topic decrement = newTopic(0, null);

  /**
   * Fire an event for the {@code decrement} topic.
   * @see #decrement
   */
  public void fireDecrement(BWidgetEvent event) { fire(decrement, event, null); }

  //endregion Topic "decrement"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpinnerButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BSpinnerButton()
  {
    up = new BButton(); 
    up.setFocusTraversable(false);
    add("up", up);
    
    down = new BButton(); 
    down.setFocusTraversable(false);
    add("down", down);
    
    linkTo(up, BButton.mouseEvent, handleUpMouseEvent);
    linkTo(down, BButton.mouseEvent, handleDownMouseEvent);
  }
  
////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


  /**
   * Handle a mouse up event.
   */
  public void doHandleUpMouseEvent(BMouseEvent event)
  {
    switch (event.getId())
    {
      case BMouseEvent.MOUSE_ENTERED:
        mUpInside = true;
        break;
      case BMouseEvent.MOUSE_EXITED:
        mUpInside = false;
        lastDownPulse = 0L; 
        break;
      case BMouseEvent.MOUSE_PRESSED:
        mUpPressed = true;
        break;
      case BMouseEvent.MOUSE_RELEASED:
        mUpPressed = false;
        lastUpPulse = 0L; 
        break;
      case BMouseEvent.MOUSE_PULSED:
        if (mUpInside)
        {
          long now = Clock.ticks();
          if ((now - lastUpPulse) > MIN_PULSE_ELAPSED)
          {
            fireIncrement(event);
            lastUpPulse = now;
          }
        }
        break;
    }
  }

  /**
   * Handle a mouse down event.
   */
  public void doHandleDownMouseEvent(BMouseEvent event)
  {
    switch (event.getId())
    {
      case BMouseEvent.MOUSE_ENTERED:
        mDownInside = true;
        break;
      case BMouseEvent.MOUSE_EXITED:
        mDownInside = false;
        lastDownPulse = 0L; 
        break;
      case BMouseEvent.MOUSE_PRESSED:
        mDownPressed = true;
        break;
      case BMouseEvent.MOUSE_RELEASED:
        mDownPressed = false;
        lastDownPulse = 0L; 
        break;
      case BMouseEvent.MOUSE_PULSED:
        if (mDownInside)
        {
          long now = Clock.ticks();
          if ((now - lastDownPulse) > MIN_PULSE_ELAPSED)
          {
            fireDecrement(event);
            lastDownPulse = now;
          }
        }
        break;
    }
  }

////////////////////////////////////////////////////////////////
// paint
////////////////////////////////////////////////////////////////

  /**
   * Paint.
   */
  public void paint(Graphics g)
  {
    SpinnerButtonTheme theme = Theme.spinner();

    double w = getWidth();
    double h = getHeight();

    g.setBrush(theme.getControlShadow(this));
    g.strokeLine(0, 0, w-1, 0);
    g.strokeLine(0, 0, 0, h-1);

    g.setBrush(theme.getControlHighlight(this));
    g.strokeLine(w-1, 1, w-1, h-1);
    g.strokeLine(1, h-1, w-1, h-1);

    // paint children
    paintChild(g, up);
    paintChild(g, down);

    // set arrow brush
    BBrush fg = theme.getControlForeground(this);
    g.setBrush(fg);

    theme.paintArrows(g, up, down,
      (mUpInside && mUpPressed), (mDownInside && mDownPressed));
  }
  
  public String getStyleSelector() { return "button spinner"; }

////////////////////////////////////////////////////////////////
// layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    setPreferredSize(getWidth(),getHeight());
  }

  /*
  *
  */
  public void doLayout(BWidget[] children)
  {
    double width = getWidth();
    double height = getHeight();
    double buttonHeight;
    double buttonWidth;
    if(Theme.spinner().isStacked())
    {
      buttonHeight = height / 2;
      buttonWidth = width - 2;
      up.setBounds(1, 1, buttonWidth, buttonHeight);
      down.setBounds(1, buttonHeight, buttonWidth, buttonHeight);
    }
    else
    {
      buttonHeight = height - 2;
      buttonWidth = width / 2;
      up.setBounds(1, 1, buttonWidth, buttonHeight);
      down.setBounds(buttonWidth, 1, buttonWidth, buttonHeight);
    }
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  BButton up;
  BButton down;
  boolean mUpInside = false;
  boolean mUpPressed = false;
  boolean mDownInside = false;
  boolean mDownPressed = false;
  
  long lastDownPulse = 0L;
  long lastUpPulse = 0L;
  
  static final long MIN_PULSE_ELAPSED = 250;
}
