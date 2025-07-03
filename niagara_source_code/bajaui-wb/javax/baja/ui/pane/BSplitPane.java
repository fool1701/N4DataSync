/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;

import com.tridium.sys.schema.*;
import com.tridium.ui.theme.*;

/**
 * BSplitPane is a pane with a divider between two child widgets.
 * The children can be laid out horizontally or vertically.
 *
 * @author    John Sublett
 * @creation  11 Dec 2000
 * @version   $Revision: 53$ $Date: 7/8/11 3:52:59 PM EDT$
 * @since     Niagara 1.0
 */
@NiagaraType
/*
 the left or top
 */
@NiagaraProperty(
  name = "widget1",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 the right or bottom
 */
@NiagaraProperty(
  name = "widget2",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 the number of pixels which separate widget1 and widget2
 */
@NiagaraProperty(
  name = "dividerWidth",
  type = "double",
  defaultValue = "6",
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(0))")
)
/*
 If true, then the divider is moveable.  If false, then it
 it is not.
 */
@NiagaraProperty(
  name = "moveableDivider",
  type = "boolean",
  defaultValue = "true"
)
/*
 The divider position as a percentage of the display area.
 For example, a value of 50 places the divider in the
 center of the display area.
 */
@NiagaraProperty(
  name = "dividerPosition",
  type = "double",
  defaultValue = "50",
  facets = @Facet("BFacets.make(BFacets.MIN, BDouble.make(0))")
)
/*
 If orientation is horizontal, widget1 will be on the left
 and widget2 will be on the right.  If orientation is vertical,
 widget1 will be on the top and widget2 will be on the bottom.
 */
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.horizontal"
)
/*
 Indicates whether the child widget layouts should be updated
 as the divider is moved or only after the move is complete.
 If continuous, the child widgets are updated as the divider
 is moved.
 */
@NiagaraProperty(
  name = "continuousLayout",
  type = "boolean",
  defaultValue = "true"
)
/*
 Fired when the divider is moved with the mouse.
 */
@NiagaraTopic(
  name = "dividerMoved",
  eventType = "BWidgetEvent"
)
public class BSplitPane
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BSplitPane(2794865589)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "widget1"

  /**
   * Slot for the {@code widget1} property.
   * the left or top
   * @see #getWidget1
   * @see #setWidget1
   */
  public static final Property widget1 = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code widget1} property.
   * the left or top
   * @see #widget1
   */
  public BWidget getWidget1() { return (BWidget)get(widget1); }

  /**
   * Set the {@code widget1} property.
   * the left or top
   * @see #widget1
   */
  public void setWidget1(BWidget v) { set(widget1, v, null); }

  //endregion Property "widget1"

  //region Property "widget2"

  /**
   * Slot for the {@code widget2} property.
   * the right or bottom
   * @see #getWidget2
   * @see #setWidget2
   */
  public static final Property widget2 = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code widget2} property.
   * the right or bottom
   * @see #widget2
   */
  public BWidget getWidget2() { return (BWidget)get(widget2); }

  /**
   * Set the {@code widget2} property.
   * the right or bottom
   * @see #widget2
   */
  public void setWidget2(BWidget v) { set(widget2, v, null); }

  //endregion Property "widget2"

  //region Property "dividerWidth"

  /**
   * Slot for the {@code dividerWidth} property.
   * the number of pixels which separate widget1 and widget2
   * @see #getDividerWidth
   * @see #setDividerWidth
   */
  public static final Property dividerWidth = newProperty(0, 6, BFacets.make(BFacets.MIN, BDouble.make(0)));

  /**
   * Get the {@code dividerWidth} property.
   * the number of pixels which separate widget1 and widget2
   * @see #dividerWidth
   */
  public double getDividerWidth() { return getDouble(dividerWidth); }

  /**
   * Set the {@code dividerWidth} property.
   * the number of pixels which separate widget1 and widget2
   * @see #dividerWidth
   */
  public void setDividerWidth(double v) { setDouble(dividerWidth, v, null); }

  //endregion Property "dividerWidth"

  //region Property "moveableDivider"

  /**
   * Slot for the {@code moveableDivider} property.
   * If true, then the divider is moveable.  If false, then it
   * it is not.
   * @see #getMoveableDivider
   * @see #setMoveableDivider
   */
  public static final Property moveableDivider = newProperty(0, true, null);

  /**
   * Get the {@code moveableDivider} property.
   * If true, then the divider is moveable.  If false, then it
   * it is not.
   * @see #moveableDivider
   */
  public boolean getMoveableDivider() { return getBoolean(moveableDivider); }

  /**
   * Set the {@code moveableDivider} property.
   * If true, then the divider is moveable.  If false, then it
   * it is not.
   * @see #moveableDivider
   */
  public void setMoveableDivider(boolean v) { setBoolean(moveableDivider, v, null); }

  //endregion Property "moveableDivider"

  //region Property "dividerPosition"

  /**
   * Slot for the {@code dividerPosition} property.
   * The divider position as a percentage of the display area.
   * For example, a value of 50 places the divider in the
   * center of the display area.
   * @see #getDividerPosition
   * @see #setDividerPosition
   */
  public static final Property dividerPosition = newProperty(0, 50, BFacets.make(BFacets.MIN, BDouble.make(0)));

  /**
   * Get the {@code dividerPosition} property.
   * The divider position as a percentage of the display area.
   * For example, a value of 50 places the divider in the
   * center of the display area.
   * @see #dividerPosition
   */
  public double getDividerPosition() { return getDouble(dividerPosition); }

  /**
   * Set the {@code dividerPosition} property.
   * The divider position as a percentage of the display area.
   * For example, a value of 50 places the divider in the
   * center of the display area.
   * @see #dividerPosition
   */
  public void setDividerPosition(double v) { setDouble(dividerPosition, v, null); }

  //endregion Property "dividerPosition"

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * If orientation is horizontal, widget1 will be on the left
   * and widget2 will be on the right.  If orientation is vertical,
   * widget1 will be on the top and widget2 will be on the bottom.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.horizontal, null);

  /**
   * Get the {@code orientation} property.
   * If orientation is horizontal, widget1 will be on the left
   * and widget2 will be on the right.  If orientation is vertical,
   * widget1 will be on the top and widget2 will be on the bottom.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * If orientation is horizontal, widget1 will be on the left
   * and widget2 will be on the right.  If orientation is vertical,
   * widget1 will be on the top and widget2 will be on the bottom.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Property "continuousLayout"

  /**
   * Slot for the {@code continuousLayout} property.
   * Indicates whether the child widget layouts should be updated
   * as the divider is moved or only after the move is complete.
   * If continuous, the child widgets are updated as the divider
   * is moved.
   * @see #getContinuousLayout
   * @see #setContinuousLayout
   */
  public static final Property continuousLayout = newProperty(0, true, null);

  /**
   * Get the {@code continuousLayout} property.
   * Indicates whether the child widget layouts should be updated
   * as the divider is moved or only after the move is complete.
   * If continuous, the child widgets are updated as the divider
   * is moved.
   * @see #continuousLayout
   */
  public boolean getContinuousLayout() { return getBoolean(continuousLayout); }

  /**
   * Set the {@code continuousLayout} property.
   * Indicates whether the child widget layouts should be updated
   * as the divider is moved or only after the move is complete.
   * If continuous, the child widgets are updated as the divider
   * is moved.
   * @see #continuousLayout
   */
  public void setContinuousLayout(boolean v) { setBoolean(continuousLayout, v, null); }

  //endregion Property "continuousLayout"

  //region Topic "dividerMoved"

  /**
   * Slot for the {@code dividerMoved} topic.
   * Fired when the divider is moved with the mouse.
   * @see #fireDividerMoved
   */
  public static final Topic dividerMoved = newTopic(0, null);

  /**
   * Fire an event for the {@code dividerMoved} topic.
   * Fired when the divider is moved with the mouse.
   * @see #dividerMoved
   */
  public void fireDividerMoved(BWidgetEvent event) { fire(dividerMoved, event, null); }

  //endregion Topic "dividerMoved"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSplitPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with orientation.
   */
  public BSplitPane(BOrientation orientation, double dividerPosition)
  {
    setOrientation(orientation);
    setDividerPosition(dividerPosition);
  }

  /**
   * Constructor with widgets.
   */
  public BSplitPane(BWidget widget1, BWidget widget2)
  {
    setWidget1(widget1);
    setWidget2(widget2);
  }

  /**
   * No arg constructor.
   */
  public BSplitPane()
  {
  }

  /**
   * Get the absolute divider location.  This method
   * is only valid after the pane has been laid out.
   */
  public double getAbsoluteDividerLocation()
  {
    double pos = getDividerPosition();

    if (getOrientation() == BOrientation.horizontal)
    {
      double w = getWidth() - getDividerWidth();
      return w * (pos / 100);
    }
    else
    {
      double h = getHeight() - getDividerWidth();
      return h * (pos / 100);
    }
  }

  /**
   * Set the absolute divider location.  This method
   * is only valid after the pane has been laid out.
   */
  public void setAbsoluteDividerLocation(double loc)
  {
    if (getOrientation() == BOrientation.horizontal)
    {
      double w = getWidth() - getDividerWidth();
      loc = Math.max(loc, 0);
      loc = Math.min(loc, w);
      setDividerPosition((loc / w) * 100);
    }
    else
    {
      double h = getHeight() - getDividerWidth();
      loc = Math.max(loc, 0);
      loc = Math.min(loc, h);
      setDividerPosition((loc / h) * 100);
    }
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Short circuit the relayout because the split pane
   * layout doesn't depend on its children.
   */
  public void childCalledRelayout(BWidget child)
  {
  }

  /**
   * Compute the preferred size of the pane.
   */
  public void computePreferredSize()
  {
    double w = 0;
    double h = 0;
    
    BWidget widget1 = getWidget1();
    BWidget widget2 = getWidget2();
    
    int visCount = 0;
    if (widget1.isVisible())
      visCount++;
    if (widget2.isVisible())
      visCount++;
    
    if (widget1.isVisible())
      widget1.computePreferredSize();
    if (widget2.isVisible())
      widget2.computePreferredSize();
    
    if (getOrientation() == BOrientation.vertical)
    {
      h = (visCount == 2) ? getDividerWidth() : 0;
      if (widget1.isVisible())
      {
        h += widget1.getPreferredHeight();
        w = widget1.getPreferredWidth();
      }
      if (widget2.isVisible())
      {
        h += widget2.getPreferredHeight();
        w = Math.max(w, widget2.getPreferredWidth());
      }
    }
    else if (getOrientation() == BOrientation.horizontal)
    {
      w = h = (visCount == 2) ? getDividerWidth() : 0;
      if (widget1.isVisible())
      {
        w += widget1.getPreferredWidth();
        h = widget1.getPreferredHeight();
      }
      if (widget2.isVisible())
      {
        w += widget2.getPreferredWidth();
        h = Math.max(h, widget2.getPreferredHeight());
      }
    }
    
    setPreferredSize(w, h);
  }

  /**
   * Layout the pane.
   */
  public void doLayout(BWidget[] kids)
  {
    if ((getWidth() == 0) || (getHeight() == 0))
      return;

    if (getOrientation() == BOrientation.horizontal)
      layoutHorizontal();
    else if (getOrientation() == BOrientation.vertical)
      layoutVertical();
  }


  /**
   * Layout the pane with horizontal orientation.
   */    
  private void layoutHorizontal()
  {
    double w = getWidth();
    double h = getHeight();
    
    BWidget widget1 = getWidget1();
    BWidget widget2 = getWidget2();
    
    divRect = new RectGeom(0, 0, getDividerWidth(), h);
    
    // Check visibility of the child widgets.  If one widget is
    // not visible, fill the entire display with the other.
    visibleDivider = getDividerWidth() > 0;
    if (!widget1.isVisible())
    {
      widget1.setBounds(0, 0, 0, 0);
      visibleDivider = false;
      if (widget2.isVisible())
        widget2.setBounds(0, 0, (int)w, (int)h);
      return;
    }
    
    if (!widget2.isVisible())
    {
      widget2.setBounds(0, 0, 0, 0);
      visibleDivider = false;
      if (widget1.isVisible())
        widget1.setBounds(0, 0, (int)w, (int)h);
      return;
    }
    
    double divPos = getDividerPosition();
    if (divPos > 100) divPos = 50;

    // If both child widgets are visible, lay them out accounting for
    // initial position, etc, when appropriate.
    double divLoc = (w - divRect.width) * (divPos / 100);
    divRect.x = divLoc;
    widget1.setBounds(0, 0, (int)divRect.x, (int)h);
    double w2 = w - divRect.x - divRect.width;
    widget2.setBounds((int)(divRect.x + divRect.width), 0, (int)w2, (int)h);
  }

  /**
   * Layout the pane with vertical orientation.
   */  
  private void layoutVertical()
  {
    double w = getWidth();
    double h = getHeight();
    
    BWidget widget1 = getWidget1();
    BWidget widget2 = getWidget2();

    divRect = new RectGeom(0, 0, w, getDividerWidth());
    
    // Check visibility of the child widgets.  If one widget is
    // not visible, fill the entire display with the other.
    visibleDivider = getDividerWidth() > 0;
    if (!widget1.isVisible())
    {
      widget1.setBounds(0, 0, 0, 0);
      visibleDivider = false;
      if (widget2.isVisible())
        widget2.setBounds(0, 0, (int)w, (int)h);
      return;
    }
    
    if (!widget2.isVisible())
    {
      widget2.setBounds(0, 0, 0, 0);
      visibleDivider = false;
      if (widget1.isVisible())
        widget1.setBounds(0, 0, (int)w, (int)h);
      return;
    }

    double divPos = getDividerPosition();
    
    // If both child widgets are visible, lay them out accounting for
    // initial position, etc, when appropriate.
    double divLoc = (h - divRect.height) * (divPos / 100);
    divRect.y = divLoc;
    widget1.setBounds(0, 0, (int)w, (int)divRect.y);
    double h2 = h - divRect.y - divRect.height;
    widget2.setBounds(0, (int)(divRect.y + divRect.height), (int)w, (int)h2);
  }

  /**
   * Is the point in the divider.
   */  
  private boolean inDivider(double x, double y)
  {
    if (visibleDivider)
      return divRect.contains(x, y);
    else
      return false;
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    //super.paint(g); <-- holy cpu cycles batman
    SplitPaneTheme theme    = Theme.splitPane();
    double         w        = getWidth();
    double         h        = getHeight();    
    BWidget        widget1  = getWidget1();
    BWidget        widget2  = getWidget2();

    // widget1
    if (widget1.isNull())
    {
      g.setBrush(theme.getWindowBackground());
      if (getOrientation() == BOrientation.horizontal)
        g.fillRect(0, 0, divRect.x, h);
      else
        g.fillRect(0, 0, w, divRect.y);
      paintChild(g, widget1);
    }
    else if (widget1.isVisible())
      paintChild(g, widget1);
    
    // widget2
    if (widget2.isNull())
    {
      g.setBrush(theme.getWindowBackground());
      if (getOrientation() == BOrientation.horizontal)
        g.fillRect(divRect.x + divRect.width, 0, w - divRect.x - divRect.width, h);
      else
        g.fillRect(0, divRect.y + divRect.height, w, h - divRect.y - divRect.height);
      paintChild(g, widget2);
    }
    else if (widget2.isVisible())
      paintChild(g, widget2);

    // divider
    if (visibleDivider)
    {
      boolean doubleDiv = !getContinuousLayout() && inDrag;
      if (doubleDiv)
      {
        theme.paintDivider(g, this, origDivRect, doubleDiv, getMoveableDivider());
        g.setBrush(shadowDivBrush);
        g.fill(divRect);
      }
      else
        theme.paintDivider(g, this, divRect, doubleDiv, getMoveableDivider());
    }
  }

  /**
   * Get the child at the specified point.  Children are not
   * exposed during a drag.
   */  
  public BWidget childAt(Point pt)
  {
    if (inDrag)
      return null;
    else
      return super.childAt(pt);
  }
  
  public String getStyleSelector() {
    return "pane split-pane";
  }

////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////

  /**
   * Handle a mouse entry.
   */
  public void mouseEntered(BMouseEvent evt)
  {
    MouseCursor c = checkCursor(evt.getX(), evt.getY());
    if (c != null) setMouseCursor(c);
  }

  /**
   * Handle a mouse exit.
   */
  public void mouseExited(BMouseEvent evt)
  {
    if (!inDrag)
      resetCursor();
  }

  /**
   * Handle a mouse press.
   */  
  public void mousePressed(BMouseEvent evt)
  {
    if (!inDivider(evt.getX(), evt.getY()))
      return;

    if (!getMoveableDivider())
      return;
    
    if (!getContinuousLayout())
      origDivRect = new RectGeom(divRect);      
    if (getOrientation() == BOrientation.horizontal)
      dragOffset = evt.getX() - divRect.x;
    else
      dragOffset = evt.getY() - divRect.y;
    inDrag = true;
  }
  
  /**
   * Handle a mouse release.
   */
  public void mouseReleased(BMouseEvent evt)
  {
    dragOffset = 0;
    inDrag = false;
    
    if (!getContinuousLayout())
    {
      origDivRect = null;
      relayout();
    }

    fireDividerMoved(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    
    MouseCursor c = checkCursor(evt.getX(), evt.getY());
    if (c != null) setMouseCursor(c);
  }

  /**
   * Handle a mouse move.
   */  
  public void mouseMoved(BMouseEvent evt)
  {
    MouseCursor c = checkCursor(evt.getX(), evt.getY());
    if (c != null) setMouseCursor(c);
  }
  
  /**
   * Handle a mouse drag.
   */
  public void mouseDragged(BMouseEvent evt)
  {
    if (!inDrag) return;
    
    double newPos;
    if (getOrientation() == BOrientation.horizontal)
    {
      newPos = Math.max(0, evt.getX() - dragOffset);
      newPos = Math.min(newPos, getWidth() - divRect.width);
      divRect.x = newPos;
    }
    else
    {
      newPos = Math.max(0, evt.getY() - dragOffset);
      newPos = Math.min(newPos, getHeight() - divRect.height);
      divRect.y = newPos;
    }
    
    setDividerPosition(fromAbsolute(newPos));
    if (getContinuousLayout())
      relayout();
    else
      repaint();
  }

  private double fromAbsolute(double absPos)
  {
    if (getOrientation() == BOrientation.horizontal)
    {
      double w = getWidth() - getDividerWidth();
      if (w <= 0) return 0;
      return (absPos / w) * 100;
    }
    else
    {
      double h = getHeight() - getDividerWidth();
      if (h <= 0) return 0;
      return (absPos / h) * 100;
    }
  }

  /**
   * Set the cursor back to normal.
   */
  private void resetCursor()
  {
    if (!getMoveableDivider()) return;
    setMouseCursor(MouseCursor.normal);
  }

  /**
   * Update the cursor for the specified location.
   */  
  private MouseCursor checkCursor(double x, double y)
  {
    if (!getMoveableDivider()) return null;
    
    if (inDivider(x, y))
    {
      if (getOrientation() == BOrientation.horizontal)
        return MouseCursor.eResize;
      else
        return MouseCursor.nResize;
    }
    else
    {
      return MouseCursor.normal;
    }
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    // are we in the px editor?
    if (x != Fw.PX_EDITOR) return super.fw(x, a, b, c, d);

    // check the cursor status on behalf of the px editor.
    Point p = (Point) a;
    return checkCursor(p.x, p.y);
  }
  
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/splitPane.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BBrush shadowDivBrush = BColor.make(0,0,0,75).toBrush();

  private RectGeom origDivRect;
  private RectGeom divRect;
  private boolean  visibleDivider;
  private boolean  inDrag = false;
  private double   dragOffset = 0;

////////////////////////////////////////////////////////////////
// Main
////////////////////////////////////////////////////////////////

//  public static void main(String[] args)
//  {
//    BSplitPane split = new BSplitPane();
//    //split.setContinuousLayout(false);
//    split.setOrientation(BOrientation.horizontal);
//    split.setWidget1(new BScrollPane(new BLabel("Widget 1")));
//    split.setWidget2(new BScrollPane(new BLabel("Widget 2")));
//    
//    BFrame f = new BFrame();
//    f.setContent(split);
//    f.setScreenBounds(200, 10, 700, 500);
//    f.open();
//  }
  
}
