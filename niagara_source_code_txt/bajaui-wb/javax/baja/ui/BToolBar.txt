/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BOrientation;
import javax.baja.ui.toolbar.BIToolBar;

import com.tridium.ui.theme.Theme;

/**
 * BToolBar groups a row of BButtons and BSeparators.
 *
 * @author    Brian Frank       
 * @creation  4 Jan 01
 * @version   $Revision: 28$ $Date: 3/28/05 10:32:20 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Orientation defines whether the toolbar is
 horizonal or vertical.
 */
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.horizontal"
)
public class BToolBar
  extends BAbstractBar implements BIToolBar
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BToolBar(1690404914)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * Orientation defines whether the toolbar is
   * horizonal or vertical.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.horizontal, null);

  /**
   * Get the {@code orientation} property.
   * Orientation defines whether the toolbar is
   * horizonal or vertical.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * Orientation defines whether the toolbar is
   * horizonal or vertical.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BToolBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BIToolBar
////////////////////////////////////////////////////////////////

  public void setId(String id) {}
  
  /**
   * Add a BButton or BToggleButton using the
   * specified command.
   */
  @SuppressWarnings("deprecation")
  public BAbstractButton add(String name, Command command)
  {
    BAbstractButton b = BAbstractButton.make(command, false, true);
    b.setFocusTraversable(false);
    add(name, b, null);
    return b;
  }

  public BAbstractButton addButton(String buttonName, Command command)
  {
    return add(buttonName, command);
  }

  public BAbstractButton getButton(String buttonName)
  {
    return (BAbstractButton)get(buttonName);
  }

  public BAbstractButton removeButton(String buttonName)
  {
    BAbstractButton btnToRemove = (BAbstractButton) get(buttonName);
    remove(buttonName);
    return btnToRemove;
  }

  public void removeAllButtons()
  {
    removeAll();
  }

  public void addSeparator()
  {
    add("sep?", new BSeparator());
  }

  public BWidget asWidget()
  {
    return this;
  }

 ////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Modify the style if the property added is a 
   * BAsbstractButton.
   */
  public void added(Property prop, Context context)
  {
    super.added(prop, context);
    BObject value = get(prop);
    if (value instanceof BAbstractButton)
      ((BAbstractButton)value).setButtonStyle(BButtonStyle.toolBar);
  }
  
  /**
   * Compute the menu bar's preferred size.
   */
  public void computePreferredSize()
  {
    BInsets insets = Theme.toolBar().getInsets();
    double gap = Theme.toolBar().getGap();
    double w = 0, h = 0;
    BOrientation orientation = getOrientation();

    BWidget[] items = getChildWidgets();
    for(int i=0; i<items.length; ++i)
    {
      BWidget item = items[i];
      item.computePreferredSize();
      if (orientation == BOrientation.horizontal)
      {
        w += item.getPreferredWidth() + gap;
        h = Math.max(h, item.getPreferredHeight());
      }
      else
      {
        w = Math.max(w, item.getPreferredWidth());
        h += item.getPreferredHeight() + gap;
      }
    }

    w += insets.left + insets.right;
    h += insets.top + insets.bottom;
    setPreferredSize(w, h);
  }
  
  /**
   * Layout the menu bar.
   */
  public void doLayout(BWidget[] kids)
  { 
    BInsets insets = Theme.toolBar().getInsets();
    double gap = Theme.toolBar().getGap();
    double x = insets.left, y = insets.top;
    double w = getWidth() - insets.left - insets.right;
    double h = getHeight() - insets.top - insets.bottom;
    BOrientation orientation = getOrientation();
    
    BWidget[] items = getChildWidgets();
    for(int i=0; i<items.length; ++i)
    {
      BWidget item = items[i];
      if (item instanceof BSeparator)
      {
        if (orientation == BOrientation.horizontal)
          ((BSeparator)item).setOrientation(BOrientation.vertical);
        else
          ((BSeparator)item).setOrientation(BOrientation.horizontal);
      }
      if (orientation == BOrientation.horizontal)
      {
        item.setBounds(x, y, item.getPreferredWidth(), h);
        x += item.getPreferredWidth() + gap;
      }
      else
      {
        item.setBounds(x, y, w, item.getPreferredHeight());
        y += item.getPreferredHeight() + gap;
      }
    }
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////
  
  /**
   * Paint the tool bar.
   */ 
  public void paint(Graphics g)
  {
    Theme.toolBar().paintBackground(g, this);
    paintChildren(g);
  }

////////////////////////////////////////////////////////////////
// Overrides
//////////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/toolbar.png");
}
