/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;

import javax.baja.gx.BFont;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.Theme;

/**
 * BButtonCE 
 *
 * @author    Mike Jarmy
 * @creation  13 Aug 02
 * @version   $Revision: 1$ $Date: 8/15/07 3:38:59 PM EDT$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraAction(
  name = "buttonMouseEvent",
  parameterType = "BMouseEvent",
  defaultValue = "new BMouseEvent()"
)
public abstract class BButtonCE
  extends BWbCellEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BButtonCE(3221015929)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "buttonMouseEvent"

  /**
   * Slot for the {@code buttonMouseEvent} action.
   * @see #buttonMouseEvent(BMouseEvent parameter)
   */
  public static final Action buttonMouseEvent = newAction(0, new BMouseEvent(), null);

  /**
   * Invoke the {@code buttonMouseEvent} action.
   * @see #buttonMouseEvent
   */
  public void buttonMouseEvent(BMouseEvent parameter) { invoke(buttonMouseEvent, parameter, null); }

  //endregion Action "buttonMouseEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BButtonCE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BButtonCE()
  {
    add("btn", button);
    linkTo("lk1", button, BButton.mouseEvent, buttonMouseEvent);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   *  doSetReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    button.setEnabled(!readonly);
    relayout();
  }

  /**
   * doSaveValue
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException, Exception
  {
    return this.value;
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  /**
   * Paint component.
   */
  public void paint(Graphics g)
  {
    paintBackground(g);
    paintButton(g);
    if (value != null) drawString(g, value.toString());
  }
  
  /**
   * doLayout
   */
  public void doLayout(BWidget[] children)
  {
    double w = getWidth();
    double h = getHeight();

    if (isReadonly())
    {
      button.setBounds(0, 0, 0, 0);
      textClip = new RectGeom(0, 0, w-1, h);
    }
    else
    {
      button.setBounds(w-h-2, 2, h, h-4);
      textClip = new RectGeom(0, 0, w-h-5, h);
    }
  }

  /**
   * mousePressed
   */
  public final void mousePressed(BMouseEvent event) 
  {
    requestFocus();
    cellSelected();

    if (button.isEnabled())
    {
      if (event.isButton1Down()) 
        buttonPressed();
      else if (event.isButton3Down()) 
        cellPopup(event);
    }
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  public final void buttonPressed()
  {
    if (isReadonly()) return;

    try
    {
      BObject obj = dialog();
      if (obj != null)
      {
        this.value = obj;
        setModified();
      }
    }
    catch (Exception e)
    {
      BDialog.error(this, BDialog.TITLE_ERROR, text("buttonCE.dialogError"), e);
    }
  }

////////////////////////////////////////////////////////////////
// protected
////////////////////////////////////////////////////////////////

  /**
   * drawString
   */
  protected final void drawString(Graphics g, String text)
  {
    g.push();
    try
    {
      g.clip(textClip);
      g.setBrush(Theme.widget().getTextBrush());
      g.setFont(FONT);    
      g.drawString(text, 3, FONT.getAscent()+3);
    }
    finally
    {
      g.pop();
    }
  }

  /**
   * paintButton
   */
  protected void paintButton(Graphics g)
  {
    if (isReadonly()) return;

    paintChild(g, button);

    double x = button.getX() + 8;
    double y = button.getY() + 15;
    if (isPressed)
    {
      Point offset = new Point(1, 1);
      x += offset.x;
      y += offset.y;
    }

    if (button.getEnabled()) g.setBrush(Theme.widget().getControlForeground());
    else g.setBrush(Theme.widget().getControlShadow());

    for (int i = 0; i < 3; i++)
    {
      g.fillRect(x + i*4, y, 2, 2);
    }
  }

  /**
   * dialog
   */
  protected BObject dialog() throws Exception
  {
    return BWbFieldEditor.dialog(this, getPropertyName(), this.value, getCurrentContext());
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////
  
  public void doButtonMouseEvent(BMouseEvent event)
  {    
    if (event.getId() == BMouseEvent.MOUSE_PRESSED)  
    {
      mousePressed(event);
      isPressed = true;
    }
    else if (event.getId() == BMouseEvent.MOUSE_RELEASED) 
    {
      isPressed = false;
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Lexicon lexicon = Lexicon.make("wbutil");
  public static String text(String s) { return lexicon.getText(s); };

  private static BFont FONT = Theme.widget().getTextFont();

  private BButton button = new BButton();
  private boolean isPressed = false;
  private RectGeom textClip;
}
