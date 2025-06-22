/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.IInsets;
import javax.baja.gx.IPoint;
import javax.baja.gx.Point;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.ui.UiEnv;
import com.tridium.ui.theme.AbstractButtonTheme;
import com.tridium.ui.theme.LabelTheme;
import com.tridium.ui.theme.Theme;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BAbstractButton provides common behavior for
 * the various button widgets.
 *
 * @author    Brian Frank       
 * @creation  17 Nov 00
 * @version   $Revision: 66$ $Date: 6/21/11 11:28:52 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This property determines if the button is
 focus traversable.  By default it is true.
 */
@NiagaraProperty(
  name = "focusTraversable",
  type = "boolean",
  defaultValue = "true"
)
/*
 This property defines the style for how the
 button should look and behave.
 */
@NiagaraProperty(
  name = "buttonStyle",
  type = "BButtonStyle",
  defaultValue = "BButtonStyle.normal"
)
/*
 The programatic hook for invoking an action
 which always results in the actionPerformed
 topic being fired.
 */
@NiagaraAction(
  name = "invokeAction"
)
public abstract class BAbstractButton
  extends BLabel
  implements BMenu.MenuCloseListener
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BAbstractButton(2413347959)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "focusTraversable"

  /**
   * Slot for the {@code focusTraversable} property.
   * This property determines if the button is
   * focus traversable.  By default it is true.
   * @see #getFocusTraversable
   * @see #setFocusTraversable
   */
  public static final Property focusTraversable = newProperty(0, true, null);

  /**
   * Get the {@code focusTraversable} property.
   * This property determines if the button is
   * focus traversable.  By default it is true.
   * @see #focusTraversable
   */
  public boolean getFocusTraversable() { return getBoolean(focusTraversable); }

  /**
   * Set the {@code focusTraversable} property.
   * This property determines if the button is
   * focus traversable.  By default it is true.
   * @see #focusTraversable
   */
  public void setFocusTraversable(boolean v) { setBoolean(focusTraversable, v, null); }

  //endregion Property "focusTraversable"

  //region Property "buttonStyle"

  /**
   * Slot for the {@code buttonStyle} property.
   * This property defines the style for how the
   * button should look and behave.
   * @see #getButtonStyle
   * @see #setButtonStyle
   */
  public static final Property buttonStyle = newProperty(0, BButtonStyle.normal, null);

  /**
   * Get the {@code buttonStyle} property.
   * This property defines the style for how the
   * button should look and behave.
   * @see #buttonStyle
   */
  public BButtonStyle getButtonStyle() { return (BButtonStyle)get(buttonStyle); }

  /**
   * Set the {@code buttonStyle} property.
   * This property defines the style for how the
   * button should look and behave.
   * @see #buttonStyle
   */
  public void setButtonStyle(BButtonStyle v) { set(buttonStyle, v, null); }

  //endregion Property "buttonStyle"

  //region Action "invokeAction"

  /**
   * Slot for the {@code invokeAction} action.
   * The programatic hook for invoking an action
   * which always results in the actionPerformed
   * topic being fired.
   * @see #invokeAction()
   */
  public static final Action invokeAction = newAction(0, null);

  /**
   * Invoke the {@code invokeAction} action.
   * The programatic hook for invoking an action
   * which always results in the actionPerformed
   * topic being fired.
   * @see #invokeAction
   */
  public void invokeAction() { invoke(invokeAction, null, null); }

  //endregion Action "invokeAction"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * If cmd is a ToggleCommand then make a new BToggleButton
   * for it, otherwise make a new BButton.
   */
  public static BAbstractButton make(Command cmd, boolean useLabel, boolean useIcon)
  {
    if (cmd instanceof ToggleCommand)
      return new BToggleButton((ToggleCommand)cmd, useLabel, useIcon);
    else
      return new BButton(cmd, useLabel, useIcon);
  }
  
  /**
   * Convenience for <code>make(cmd, true, true)</code>
   */
  public static BAbstractButton make(Command cmd)
  {                              
    return make(cmd, true, true);
  }

////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////
    
  /**
   * Return true if the mouse is currently over the button.
   */
  public boolean isMouseOver()
  {
    return mouseOver;
  }
  
  /**
   * Return true if the button is currently pressed down.
   */
  public boolean isPressed()
  {
    return pressed;
  }
    
  /**
   * Return true if the menu button is currently pressed down.
   */  
  public boolean isMenuPressed()
  {
    return menuPressed;
  }
   
////////////////////////////////////////////////////////////////
// Visual
////////////////////////////////////////////////////////////////

  /**
   * Return the label offset.
   */  
  public IPoint getLabelOffset()
  {
    if (getButtonStyle() == BButtonStyle.hyperlink)
      return new Point(0, 0);
    return buttonTheme().getLabelOffset(this);
  }
  
  /**
   * Reutrn the menu button width.
   */  
  public double getMenuWidth()
  {
    if (getButtonStyle() == BButtonStyle.hyperlink)
      return 0;
    return buttonTheme().getMenuWidth();
  } 
  
  /**
   * Get the padding between the widget bounds
   * and the label bounds.
   */
  public BInsets getPadding()
  {
    // Overrides BLabel.getPadding to return theme.
    if (padding.isNull()) 
    {
      if (getButtonStyle() == BButtonStyle.hyperlink)
        return super.getPadding();
      IInsets temp = buttonTheme().getPadding(this);
      return BInsets.make(temp);
    }
    return padding;
  }  
  
  /**
   * Paint the background of the button.
   */  
  public void paintBackground(Graphics g)
  {
    if (getButtonStyle() != BButtonStyle.none && getButtonStyle() != BButtonStyle.hyperlink)
      buttonTheme().paintBackground(g, this);
  }  
    
////////////////////////////////////////////////////////////////
// Command
////////////////////////////////////////////////////////////////
 
  /**
   * If the button has a command associated with 
   * it, return the Command instance, otherwise
   * return null.
   */
  public Command getCommand()
  {
    return command;
  }
  
  /**
   * Install the command object for this button. 
   */
  public void setCommand(Command command, boolean useLabel, boolean useIcon)
  {                     
    // unregister from old command
    if (this.command != null)
      this.command.unregister(this);

    if (command == null) return;
    
    // update label text
    if (useLabel && command.getLabel() != null)
      setText(command.getLabel());
      
    // update label icon
    if (useIcon && command.getIcon() != null)
      setImage(command.getIcon());
    
    if (command.getKeyBase() != null)
    {
      setStyleId(command.getKeyBase().replace('.', '-'));
      // on the off chance that this button has already had styling applied,
      // update the image
      setImage(Theme.button().getIcon(this));
      //System.out.println("set styleId to " + getStyleId());
    }
    

    // register with new command  
    this.command = command;
    command.register(this);
  }

////////////////////////////////////////////////////////////////
// Menu
////////////////////////////////////////////////////////////////

  /**
   * Get the installed menu controller or return null.
   */
  public MenuController getMenuController()
  {
    return menuController;
  }

  /**
   * Set the menu controller or pass null for no menu.
   * When a menu controller is installed the button contains
   * a dropdown arrow.
   */
  public void setMenuController(MenuController menuController)
  {
    this.menuController = menuController;
    relayout();
  }

  /**
   * Open the menu using the menu returned from the menu controller.
   */
  public void openMenu()
  {
    if (menuController != null)
    {
      BMenu menu = menuController.getMenu(this);
      if (menu != null) openMenu(menu);
    }
  }

  /**
   * Open the menu as a drop down to this button.
   */
  public void openMenu(BMenu menu)
  {
    menu.open(this, 0, getHeight());
  }

////////////////////////////////////////////////////////////////
// BMenu.MenuCloseListener
////////////////////////////////////////////////////////////////

  /**
   * If the BWidget passed to the open() method implements
   * this interface, then it will receive the menuClosed
   * callback when the BMenu is closed.
   */
  public void menuClosed(BMenu menu)
  {
    menuPressed = false;
    repaint();
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /** 
   * Convenience for <code>doInvokeAction(null)</code>.
   */
  public final void doInvokeAction()
  {     
    doInvokeAction(null);
  }

  /**
   * This is the implementation which all action
   * invokes are routed to.  It can be used as a
   * consistent override point for catching all
   * programatic and user driven invocations.
   */
  public void doInvokeAction(CommandEvent event)
  {
    fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
    repaint();
  }
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  /**
   * Compute the preferred size of the button.
   */
  public void computePreferredSize()
  {    
    super.computePreferredSize();
    double pw = getPreferredWidth();
    double ph = getPreferredHeight();
    if (menuController != null) pw += getMenuWidth();
    setPreferredSize(pw, ph);
  }
  
  /**
   * Layout the button.
   */
  public void doLayout(BWidget[] children)
  {
    forceLayout = false;
    
    double w = getWidth();
    double h = getHeight();
    
    if (menuController != null) 
      w -= getMenuWidth();
    
    IInsets padding = getPadding();
    w -= padding.left() + padding.right();
    h -= padding.top() + padding.bottom();       
    
    layout.lines = null;
    layout.computeBounds();   
    layout.computeAlignment(w,h);
    
    layout.xo += padding.left();
    layout.yo += padding.top();
  }
  
  public void changed(Property prop, Context context) {
    if (prop == buttonStyle)
    {
      if (this.get(prop) == BButtonStyle.toolBar)
      {
        StyleUtils.addStyleClass(this, "toolbar");
        StyleUtils.removeStyleClass(this, "hyperlink");
      }
      else if (this.get(prop) == BButtonStyle.hyperlink)
      {
        StyleUtils.addStyleClass(this, "hyperlink");
        StyleUtils.removeStyleClass(this, "toolbar");
      }
      else
      {
        StyleUtils.removeStyleClass(this, "toolbar");
        StyleUtils.removeStyleClass(this, "hyperlink");
      }
    }
    super.changed(prop, context);
  }
  
////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  /**
   * Paint the button.
   */
  public void paint(Graphics g)
  {                    
    paintBackground(g);
    IPoint offset = getLabelOffset();
    g.translate(offset.x(), offset.y());    
    super.paint(g);    
  }
  
  public String getStyleSelector() 
  {
    if (getButtonStyle() == BButtonStyle.hyperlink)
      return "label";
    
    return "button"; 
  }
  
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////

  public boolean isFocusTraversable()
  {
    return getFocusTraversable();
  }
    
////////////////////////////////////////////////////////////////
// Keyboard Eventing
////////////////////////////////////////////////////////////////  

  public boolean receiveInputEvents()
  {
    return true;
  }  

  public void keyPressed(BKeyEvent event)
  {
    if (event.getKeyCode() == BKeyEvent.VK_SPACE) 
    { 
      event.consume(); 
      doInvokeAction(new CommandEvent(event)); 
    }
  }
    
////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////
  
  /**
   * Handle mousePressed event.
   */
  public void mousePressed(BMouseEvent event) 
  {
    if (menuController != null)
      if (!menuController.isMenuDistinct() || (event.getX() > getWidth() - getMenuWidth()))
      {
        menuPressed = true;
        repaint();
        openMenu();
        return;
      }
      
    pressed = true;
    StyleUtils.addStyleClass(this, "pressed");
    repaint();
  }
  
  /**
   * Handle mouseReleased event.
   */
  public void mouseReleased(BMouseEvent event) 
  {
    if (mouseOver && !menuPressed) doInvokeAction(new CommandEvent(event));
    pressed = false;
    StyleUtils.removeStyleClass(this, "pressed");
    repaint();
  }
  
  /**
   * Handle mouseEntered event.
   */
  public void mouseEntered(BMouseEvent event) 
  {
    BWidgetShell shell = getShell();
    String tip = getToolTip();
    if (shell != null && tip != null) 
      shell.showStatus(tip);
    if (shell != null && getButtonStyle() == BButtonStyle.hyperlink)
      setMouseCursor(MouseCursor.hand);
      
    mouseOver = true;
    StyleUtils.addStyleClass(this, "hover");
    repaint();
  }
  
  /**
   * Handle mouseExited event.
   */
  public void mouseExited(BMouseEvent event) 
  {
    UiEnv.get().closeBubbleHelp();
    BWidgetShell shell = getShell();
    if (shell != null) shell.showStatus(null);
    if (shell != null && getButtonStyle() == BButtonStyle.hyperlink)
      setMouseCursor(MouseCursor.normal);
    
    mouseOver = false;
    StyleUtils.removeStyleClass(this, "hover");
    repaint();
  }
    
  /**
   * Handle mouseHover event.
   */
  public void mouseHover(BMouseEvent event) 
  {
    if (getButtonStyle() == BButtonStyle.toolBar && command != null)
    {
      String text = command.getLabel();
      
      //Issue 19145 - Never show an empty bubble help item
      if (text != null && text.length() > 0)
      {                                                  
        double x = event.getX();
        double y = event.getY();
        y = Math.max(getHeight()+1, y+16);
        UiEnv.get().openBubbleHelp(this, x, y, text);
      }
    }
  }
  
  /**
   * Get the text to display for the tool tip.  By 
   * default this is the command's description.  If
   * no command is installed then return the button's
   * text.  Return null if no tool tip should be 
   * displayed.
   */
  public String getToolTip()
  {
    // try command first
    if (command != null)
    {
      String label = command.getLabel();
      String description = command.getDescription();
      
      if (label != null && label.length() > 0) 
      {
        if (label.endsWith("...")) label = label.substring(0, label.length()-3);
        if (label.endsWith("..")) label = label.substring(0, label.length()-2);
        
        if (description != null && description.length() > 0)
          return label + ": " + description;
        else
          return label;
      }
      else if (description != null && description.length() > 0)
      {
        return description;      
      }
    }
    
    // fallback to label text
    String text = getText();
    if (text != null && text.length() > 0)
      return text;
    
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Theme
////////////////////////////////////////////////////////////////  
  
  /**
   * Override label theme.
   */
  LabelTheme theme()
  {
    return buttonTheme();
  }  
  
  /**
   * Package protected theme access.
   */
  abstract AbstractButtonTheme buttonTheme();

////////////////////////////////////////////////////////////////
// MenuController
////////////////////////////////////////////////////////////////

  /**
   * MenuController is used to register a drop 
   * down menu when the button is pressed.
   */
  public interface MenuController
  {
  
    /**
     * If the menu is distinct it means that pressing the 
     * button body is a distinct command from the menu 
     * drop down.  Otherwise whole button is used to drop 
     * down the menu.
     */
    public boolean isMenuDistinct();
  
    /**
     * Get the menu to display or return null.
     */
    public BMenu getMenu(BAbstractButton button);
    
  }

////////////////////////////////////////////////////////////////
// Debugging
////////////////////////////////////////////////////////////////

  public String getDebugString()
  {
    return "\"" + getText() + "\"; ";
  }             
  
//  int bug = 0;
//  public void animate()
//  {
//    super.animate();
//    bug++;
//    if ((bug % 50) != 0) return;
//    setEnabled(!getEnabled());
//  }
      
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private boolean mouseOver   = false;
  private boolean pressed     = false;  
  private boolean menuPressed = false;
    
  Command command;
  MenuController menuController;    
}
