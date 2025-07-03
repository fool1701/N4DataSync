/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BEdgePane is a container with a layout like the 
 * java.awt.BorderLayout.  It only supports five potential
 * children in the frozen slots top, bottom, left, right,
 * and center.  The top and bottom widgets fill the pane horizontally 
 * use their preferred height.  The left and right widgets use 
 * their preferred width, and occupy the vertical space between 
 * the top and bottom.  The center widget gets all the remainder 
 * space.  Any of the widgets may be a BNullWidget.
 *
 * @author    Brian Frank
 * @creation  17 Nov 00
 * @version   $Revision: 14$ $Date: 3/28/05 10:32:27 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Widget to layout with its preferred height
 on the top of the pane.
 */
@NiagaraProperty(
  name = "top",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget to layout with its preferred widget
 on the left of the pane.
 */
@NiagaraProperty(
  name = "left",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget to layout in the center of pane with all
 space left after laying out top, bottom, left,
 and right.
 */
@NiagaraProperty(
  name = "center",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget to layout with its preferred widget
 on the right of the pane.
 */
@NiagaraProperty(
  name = "right",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget to layout with its preferred height
 on the bottom of the pane.
 */
@NiagaraProperty(
  name = "bottom",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
public class BEdgePane
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BEdgePane(2082736627)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "top"

  /**
   * Slot for the {@code top} property.
   * Widget to layout with its preferred height
   * on the top of the pane.
   * @see #getTop
   * @see #setTop
   */
  public static final Property top = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code top} property.
   * Widget to layout with its preferred height
   * on the top of the pane.
   * @see #top
   */
  public BWidget getTop() { return (BWidget)get(top); }

  /**
   * Set the {@code top} property.
   * Widget to layout with its preferred height
   * on the top of the pane.
   * @see #top
   */
  public void setTop(BWidget v) { set(top, v, null); }

  //endregion Property "top"

  //region Property "left"

  /**
   * Slot for the {@code left} property.
   * Widget to layout with its preferred widget
   * on the left of the pane.
   * @see #getLeft
   * @see #setLeft
   */
  public static final Property left = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code left} property.
   * Widget to layout with its preferred widget
   * on the left of the pane.
   * @see #left
   */
  public BWidget getLeft() { return (BWidget)get(left); }

  /**
   * Set the {@code left} property.
   * Widget to layout with its preferred widget
   * on the left of the pane.
   * @see #left
   */
  public void setLeft(BWidget v) { set(left, v, null); }

  //endregion Property "left"

  //region Property "center"

  /**
   * Slot for the {@code center} property.
   * Widget to layout in the center of pane with all
   * space left after laying out top, bottom, left,
   * and right.
   * @see #getCenter
   * @see #setCenter
   */
  public static final Property center = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code center} property.
   * Widget to layout in the center of pane with all
   * space left after laying out top, bottom, left,
   * and right.
   * @see #center
   */
  public BWidget getCenter() { return (BWidget)get(center); }

  /**
   * Set the {@code center} property.
   * Widget to layout in the center of pane with all
   * space left after laying out top, bottom, left,
   * and right.
   * @see #center
   */
  public void setCenter(BWidget v) { set(center, v, null); }

  //endregion Property "center"

  //region Property "right"

  /**
   * Slot for the {@code right} property.
   * Widget to layout with its preferred widget
   * on the right of the pane.
   * @see #getRight
   * @see #setRight
   */
  public static final Property right = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code right} property.
   * Widget to layout with its preferred widget
   * on the right of the pane.
   * @see #right
   */
  public BWidget getRight() { return (BWidget)get(right); }

  /**
   * Set the {@code right} property.
   * Widget to layout with its preferred widget
   * on the right of the pane.
   * @see #right
   */
  public void setRight(BWidget v) { set(right, v, null); }

  //endregion Property "right"

  //region Property "bottom"

  /**
   * Slot for the {@code bottom} property.
   * Widget to layout with its preferred height
   * on the bottom of the pane.
   * @see #getBottom
   * @see #setBottom
   */
  public static final Property bottom = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code bottom} property.
   * Widget to layout with its preferred height
   * on the bottom of the pane.
   * @see #bottom
   */
  public BWidget getBottom() { return (BWidget)get(bottom); }

  /**
   * Set the {@code bottom} property.
   * Widget to layout with its preferred height
   * on the bottom of the pane.
   * @see #bottom
   */
  public void setBottom(BWidget v) { set(bottom, v, null); }

  //endregion Property "bottom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEdgePane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BEdgePane()
  {
    super();
  }

  public BEdgePane(BWidget topWidget,
                   BWidget bottomWidget,
                   BWidget leftWidget,
                   BWidget rightWidget,
                   BWidget centerWidget)
  {
    super();
    if (topWidget    != null) setTop   (topWidget);
    if (bottomWidget != null) setBottom(bottomWidget);
    if (leftWidget   != null) setLeft  (leftWidget);
    if (rightWidget  != null) setRight (rightWidget);
    if (centerWidget != null) setCenter(centerWidget);
  }
    
  public void computePreferredSize()
  {
    double w = 0, h = 0;
    BWidget c;
    
    if (!(c = getRight()).isNull() && c.isVisible()) 
    { 
      c.computePreferredSize(); 
      w += c.getPreferredWidth(); 
      h = Math.max(h, c.getPreferredHeight()); 
    }
    if (!(c = getLeft()).isNull() && c.isVisible()) 
    { 
      c.computePreferredSize(); 
      w += c.getPreferredWidth(); 
      h = Math.max(h, c.getPreferredHeight()); 
    }
    if (!(c = getCenter()).isNull() && c.isVisible()) 
    { 
      c.computePreferredSize(); 
      w += c.getPreferredWidth(); 
      h = Math.max(h, c.getPreferredHeight()); 
    }
    if (!(c = getTop()).isNull() && c.isVisible()) 
    { 
      c.computePreferredSize(); 
      w = Math.max(w, c.getPreferredWidth());
      h += c.getPreferredHeight(); 
    }
    if (!(c = getBottom()).isNull() && c.isVisible()) 
    { 
      c.computePreferredSize(); 
      w = Math.max(w, c.getPreferredWidth());
      h += c.getPreferredHeight(); 
    }
    
    setPreferredSize(w,h);
  }

  public void childCalledRelayout(BWidget child)
  {
    if (child == getCenter()) return;
    relayout();
  }
      
  public void doLayout(BWidget[] children)
  {    
    double w = getWidth();   // my width
    double h = getHeight();  // my height
    double top = 0, bottom = h;
    double left = 0, right = w;
    BWidget c;
    
    if (!(c = getTop()).isNull() && c.isVisible())
    {
      c.computePreferredSize();
      double ch = Math.min(bottom-top, c.getPreferredHeight());
      c.setBounds(left, top, right-left, ch);
      top += ch;
    }
    if (!(c = getBottom()).isNull() && c.isVisible())
    {
      c.computePreferredSize();
      double ch = Math.min(bottom-top, c.getPreferredHeight());
      c.setBounds(left, bottom-ch, right-left, ch);
      bottom -= ch;
    }
    if (!(c = getLeft()).isNull() && c.isVisible())
    {
      c.computePreferredSize();
      double cw = Math.min(right-left, c.getPreferredWidth());
      c.setBounds(left, top, cw, bottom-top);
      left += cw;
    }
    if (!(c = getRight()).isNull() && c.isVisible())
    {
      c.computePreferredSize();
      double cw = Math.min(right-left, c.getPreferredWidth());
      c.setBounds(right-cw, top, cw, bottom-top);
      right -= cw;
    }
    if (!(c = getCenter()).isNull() && c.isVisible())
    {
      c.setBounds(left, top, right-left, bottom-top);
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/edgePane.png");
  
}
