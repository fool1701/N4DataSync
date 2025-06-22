/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

import com.tridium.ui.*;
import com.tridium.ui.theme.*;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BDropDown combines two widgets.  The "display" widget is always
 * visible, and usually has a drop down button on its right side.
 * The "drop down" widget is displayed when the BDropDown is
 * opened in its own popup window.
 *
 * @author    Brian Frank       
 * @creation  8 Jul 02
 * @version   $Revision: 17$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The widget displayed all the time.
 */
@NiagaraProperty(
  name = "displayWidget",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 The widget displayed in the drop down popup window.
 */
@NiagaraProperty(
  name = "dropDownWidget",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 If this property is set to false, then
 access to the list drop down is disabled, and
 the drop down button is hidden.
 */
@NiagaraProperty(
  name = "dropDownEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Open the drop down popup window.
 */
@NiagaraAction(
  name = "openDropDown"
)
/*
 Close the drop down popup window.
 */
@NiagaraAction(
  name = "closeDropDown"
)
/*
 Fired whenever the current value of the drop down is modified.
 */
@NiagaraTopic(
  name = "valueModified",
  eventType = "BWidgetEvent"
)
/*
 This topic is fired if the user presses enter while the
 drop down has focus.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public class BDropDown
  extends BWidget
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BDropDown(3385630710)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "displayWidget"

  /**
   * Slot for the {@code displayWidget} property.
   * The widget displayed all the time.
   * @see #getDisplayWidget
   * @see #setDisplayWidget
   */
  public static final Property displayWidget = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code displayWidget} property.
   * The widget displayed all the time.
   * @see #displayWidget
   */
  public BWidget getDisplayWidget() { return (BWidget)get(displayWidget); }

  /**
   * Set the {@code displayWidget} property.
   * The widget displayed all the time.
   * @see #displayWidget
   */
  public void setDisplayWidget(BWidget v) { set(displayWidget, v, null); }

  //endregion Property "displayWidget"

  //region Property "dropDownWidget"

  /**
   * Slot for the {@code dropDownWidget} property.
   * The widget displayed in the drop down popup window.
   * @see #getDropDownWidget
   * @see #setDropDownWidget
   */
  public static final Property dropDownWidget = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code dropDownWidget} property.
   * The widget displayed in the drop down popup window.
   * @see #dropDownWidget
   */
  public BWidget getDropDownWidget() { return (BWidget)get(dropDownWidget); }

  /**
   * Set the {@code dropDownWidget} property.
   * The widget displayed in the drop down popup window.
   * @see #dropDownWidget
   */
  public void setDropDownWidget(BWidget v) { set(dropDownWidget, v, null); }

  //endregion Property "dropDownWidget"

  //region Property "dropDownEnabled"

  /**
   * Slot for the {@code dropDownEnabled} property.
   * If this property is set to false, then
   * access to the list drop down is disabled, and
   * the drop down button is hidden.
   * @see #getDropDownEnabled
   * @see #setDropDownEnabled
   */
  public static final Property dropDownEnabled = newProperty(0, true, null);

  /**
   * Get the {@code dropDownEnabled} property.
   * If this property is set to false, then
   * access to the list drop down is disabled, and
   * the drop down button is hidden.
   * @see #dropDownEnabled
   */
  public boolean getDropDownEnabled() { return getBoolean(dropDownEnabled); }

  /**
   * Set the {@code dropDownEnabled} property.
   * If this property is set to false, then
   * access to the list drop down is disabled, and
   * the drop down button is hidden.
   * @see #dropDownEnabled
   */
  public void setDropDownEnabled(boolean v) { setBoolean(dropDownEnabled, v, null); }

  //endregion Property "dropDownEnabled"

  //region Action "openDropDown"

  /**
   * Slot for the {@code openDropDown} action.
   * Open the drop down popup window.
   * @see #openDropDown()
   */
  public static final Action openDropDown = newAction(0, null);

  /**
   * Invoke the {@code openDropDown} action.
   * Open the drop down popup window.
   * @see #openDropDown
   */
  public void openDropDown() { invoke(openDropDown, null, null); }

  //endregion Action "openDropDown"

  //region Action "closeDropDown"

  /**
   * Slot for the {@code closeDropDown} action.
   * Close the drop down popup window.
   * @see #closeDropDown()
   */
  public static final Action closeDropDown = newAction(0, null);

  /**
   * Invoke the {@code closeDropDown} action.
   * Close the drop down popup window.
   * @see #closeDropDown
   */
  public void closeDropDown() { invoke(closeDropDown, null, null); }

  //endregion Action "closeDropDown"

  //region Topic "valueModified"

  /**
   * Slot for the {@code valueModified} topic.
   * Fired whenever the current value of the drop down is modified.
   * @see #fireValueModified
   */
  public static final Topic valueModified = newTopic(0, null);

  /**
   * Fire an event for the {@code valueModified} topic.
   * Fired whenever the current value of the drop down is modified.
   * @see #valueModified
   */
  public void fireValueModified(BWidgetEvent event) { fire(valueModified, event, null); }

  //endregion Topic "valueModified"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * This topic is fired if the user presses enter while the
   * drop down has focus.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * This topic is fired if the user presses enter while the
   * drop down has focus.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDropDown.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a drop down with the two widgets.
   */
  public BDropDown(BWidget displayWidget, BWidget dropDownWidget)
  {
    setDisplayWidget(displayWidget);
    setDropDownWidget(dropDownWidget);
  }

  /**
   * Default constructor.
   */
  public BDropDown()
  {
  }
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Relayout when properties change.
   */
  public void changed(Property prop, Context context)
  {
    if (prop == dropDownEnabled) {
      BWidget widget = this.getDropDownWidget();
      if (widget != null && !widget.isNull()) {
        StyleUtils.toggleStyleClass(widget, "readonly", !this.getBoolean(dropDownEnabled));
      }
      widget = this.getDisplayWidget();
      if (widget != null && !widget.isNull()) {
        StyleUtils.toggleStyleClass(widget, "readonly", !this.getBoolean(dropDownEnabled));
      }

    }
    relayout();
  }

  /**
   * Compute the preferred size of the drop down.  This is
   * the display widget, border size, and drop down button size.
   */
  public void computePreferredSize()
  {
    // display
    BWidget display = getDisplayWidget();
    display.computePreferredSize();
    double pw = display.getPreferredWidth();
    double ph = display.getPreferredHeight();
    
    // insets
    BInsets insets = getBorderInsets();
    pw += insets.left + insets.right;
    ph += insets.top + insets.bottom;
    
    // drop down button
    if (getDropDownEnabled())
      pw += ph;
    
    setPreferredSize(pw, ph);
  }

  /**
   * Layout the kids.
   */
  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();

    BWidget display = getDisplayWidget();
    display.computePreferredSize();
    BInsets insets = getBorderInsets();
    
    if (getDropDownEnabled())
    {
      double ph = Math.min(h - insets.top - insets.right, display.getPreferredHeight());
      buttonBounds.width = Math.min(ph, 16.0); // why not 16? it looks good.
      buttonBounds.height = h - insets.top - insets.right;
      buttonBounds.x = w - insets.right - buttonBounds.width;
      buttonBounds.y = insets.top;
    }
    else
    {
      buttonBounds.x = 0;
      buttonBounds.y = 0;
      buttonBounds.width = 0;
      buttonBounds.height = 0;
    }
    
    double dw = w - insets.left - insets.right - buttonBounds.width;
    double dh = h - insets.top - insets.bottom;
    display.setBounds(insets.left, insets.right, dw, dh);
    
    if (!isDropDownOpen())
    {
      BWidget dropDown = getDropDownWidget();
      dropDown.setBounds(0, h, 0, 0);
    }    
  }
  
  /**
   * Get the border insets.
   */
  public BInsets getBorderInsets()
  {
    return Theme.dropDown().getBorderInsets();
  }
  
  /**
   * Set the enabled state.
   */
  public void setEnabled(boolean v)
  {
    super.setEnabled(v);
    getDisplayWidget().setEnabled(v);
    setDropDownEnabled(v);
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  /**
   * Paint the drop down.
   */
  public void paint(Graphics g)
  {
    paintChild(g, getDisplayWidget());
    paintBorder(g);
    if (getDropDownEnabled())
      paintButton(g);
  }
  
  /**
   * Paint the drop down's border.
   */
  void paintBorder(Graphics g)
  {
    Theme.dropDown().paintBorder(g, this);
  }

  /**
   * Paint the drop down's button.
   */
  void paintButton(Graphics g)
  {
    Theme.dropDown().paintButton(g, this, buttonBounds, isDropDownOpen());
  }
  
  public String getStyleSelector() { return "drop-down"; }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Is the drop down currently open.
   */
  public boolean isDropDownOpen()
  {
    return UiEnv.get().isPopupOpen(getDropDownWidget());
  }

  /**
   * Implementation of closeDropDown().
   */
  public void doCloseDropDown() 
  {
    UiEnv.get().closePopup(null);
    repaint();
  }

  /**
   * Implementation of openDropDown().
   */
  public void doOpenDropDown()
  {
    if (!getDropDownEnabled()) return;
    if (isDropDownOpen()) return;
    
    BWidget dropDown = getDropDownWidget();
    
    int fixedHeight = -1;
    dropDown.computePreferredSize();
    
    double width = Math.max(getWidth(), dropDown.getPreferredWidth());
    double ph = dropDown.getPreferredHeight(); 
    if (ph > 300) fixedHeight = 308; // match this to BList.ensureItemIsVisible()
    
    UiEnv.get().openPopup(dropDown, this, 0, getHeight(), width, fixedHeight);
    
    repaint();
  }  

////////////////////////////////////////////////////////////////
// Mouse Events
////////////////////////////////////////////////////////////////  

  public void mousePressed(BMouseEvent event) 
  {
    //if (buttonBounds.contains(event.getX(), event.getY()))
      openDropDown();
  }
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/comboBox.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private RectGeom buttonBounds = new RectGeom();
  
}
