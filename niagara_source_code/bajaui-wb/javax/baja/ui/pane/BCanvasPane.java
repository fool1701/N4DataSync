/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BSize;
import javax.baja.gx.BTransform;
import javax.baja.gx.Graphics;
import javax.baja.gx.IGeom;
import javax.baja.gx.Point;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLayout;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BScaleMode;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.shape.BShape;

import com.tridium.gx.GraphPaper;
import com.tridium.gx.GxEnv;
import com.tridium.gx.Hatching;
import com.tridium.sys.schema.Fw;
import com.tridium.ui.util.LayoutUtil;
import com.tridium.ui.util.ScaledLayout;

/**
 * BCanvasPane is commonly used to provide absolute layout using
 * BLayout for most BWidgets and BGeom for BShapes.  BCanvasPane also
 * provides support for scaling and/or aligning its contents.
 *
 * @author    Brian Frank on 19 Nov 00
 * @version   $Revision: 18$ $Date: 2/4/08 10:12:33 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 View size specifies the logical size of the
 pane's coordinate system.
 */
@NiagaraProperty(
  name = "viewSize",
  type = "BSize",
  defaultValue = "BSize.make(100, 100)"
)
/*
 Specifies if/how to scale the viewSize to fit the pane's bounds.
 */
@NiagaraProperty(
  name = "scale",
  type = "BScaleMode",
  defaultValue = "BScaleMode.none"
)
/*
 When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
 Note that a ScrollPane must be be in the directly  ancestry of the BCanvasPane with only BBorderPanes allowed in between.
 */
@NiagaraProperty(
  name = "minScaleFactor",
  type = "double",
  defaultValue = "0"
)
/*
 When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
 */
@NiagaraProperty(
  name = "maxScaleFactor",
  type = "double",
  defaultValue = "0"
)
/*
 Defines how to align the view box horizontally.
 */
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.center"
)
/*
 Defines how to align the view box vertically.
 */
@NiagaraProperty(
  name = "valign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 Specifies how the pane's background is painted.  Background
 encompasses the pane's bounds, not it's view box.
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
public class BCanvasPane
  extends BPane
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BCanvasPane(3332070393)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "viewSize"

  /**
   * Slot for the {@code viewSize} property.
   * View size specifies the logical size of the
   * pane's coordinate system.
   * @see #getViewSize
   * @see #setViewSize
   */
  public static final Property viewSize = newProperty(0, BSize.make(100, 100), null);

  /**
   * Get the {@code viewSize} property.
   * View size specifies the logical size of the
   * pane's coordinate system.
   * @see #viewSize
   */
  public BSize getViewSize() { return (BSize)get(viewSize); }

  /**
   * Set the {@code viewSize} property.
   * View size specifies the logical size of the
   * pane's coordinate system.
   * @see #viewSize
   */
  public void setViewSize(BSize v) { set(viewSize, v, null); }

  //endregion Property "viewSize"

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * Specifies if/how to scale the viewSize to fit the pane's bounds.
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, BScaleMode.none, null);

  /**
   * Get the {@code scale} property.
   * Specifies if/how to scale the viewSize to fit the pane's bounds.
   * @see #scale
   */
  public BScaleMode getScale() { return (BScaleMode)get(scale); }

  /**
   * Set the {@code scale} property.
   * Specifies if/how to scale the viewSize to fit the pane's bounds.
   * @see #scale
   */
  public void setScale(BScaleMode v) { set(scale, v, null); }

  //endregion Property "scale"

  //region Property "minScaleFactor"

  /**
   * Slot for the {@code minScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * Note that a ScrollPane must be be in the directly  ancestry of the BCanvasPane with only BBorderPanes allowed in between.
   * @see #getMinScaleFactor
   * @see #setMinScaleFactor
   */
  public static final Property minScaleFactor = newProperty(0, 0, null);

  /**
   * Get the {@code minScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * Note that a ScrollPane must be be in the directly  ancestry of the BCanvasPane with only BBorderPanes allowed in between.
   * @see #minScaleFactor
   */
  public double getMinScaleFactor() { return getDouble(minScaleFactor); }

  /**
   * Set the {@code minScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * Note that a ScrollPane must be be in the directly  ancestry of the BCanvasPane with only BBorderPanes allowed in between.
   * @see #minScaleFactor
   */
  public void setMinScaleFactor(double v) { setDouble(minScaleFactor, v, null); }

  //endregion Property "minScaleFactor"

  //region Property "maxScaleFactor"

  /**
   * Slot for the {@code maxScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * @see #getMaxScaleFactor
   * @see #setMaxScaleFactor
   */
  public static final Property maxScaleFactor = newProperty(0, 0, null);

  /**
   * Get the {@code maxScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * @see #maxScaleFactor
   */
  public double getMaxScaleFactor() { return getDouble(maxScaleFactor); }

  /**
   * Set the {@code maxScaleFactor} property.
   * When BScaleMode of fitRatio is used, a non-zero value specifies the limit for the zoom level.
   * @see #maxScaleFactor
   */
  public void setMaxScaleFactor(double v) { setDouble(maxScaleFactor, v, null); }

  //endregion Property "maxScaleFactor"

  //region Property "halign"

  /**
   * Slot for the {@code halign} property.
   * Defines how to align the view box horizontally.
   * @see #getHalign
   * @see #setHalign
   */
  public static final Property halign = newProperty(0, BHalign.center, null);

  /**
   * Get the {@code halign} property.
   * Defines how to align the view box horizontally.
   * @see #halign
   */
  public BHalign getHalign() { return (BHalign)get(halign); }

  /**
   * Set the {@code halign} property.
   * Defines how to align the view box horizontally.
   * @see #halign
   */
  public void setHalign(BHalign v) { set(halign, v, null); }

  //endregion Property "halign"

  //region Property "valign"

  /**
   * Slot for the {@code valign} property.
   * Defines how to align the view box vertically.
   * @see #getValign
   * @see #setValign
   */
  public static final Property valign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code valign} property.
   * Defines how to align the view box vertically.
   * @see #valign
   */
  public BValign getValign() { return (BValign)get(valign); }

  /**
   * Set the {@code valign} property.
   * Defines how to align the view box vertically.
   * @see #valign
   */
  public void setValign(BValign v) { set(valign, v, null); }

  //endregion Property "valign"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * Specifies how the pane's background is painted.  Background
   * encompasses the pane's bounds, not it's view box.
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code background} property.
   * Specifies how the pane's background is painted.  Background
   * encompasses the pane's bounds, not it's view box.
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * Specifies how the pane's background is painted.  Background
   * encompasses the pane's bounds, not it's view box.
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCanvasPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * The preferred size is the viewSize scaled to the containing ScrollPane.
   * This scaling will be performed even if the shell manager disallows
   * scaling transforms (e.g. Hx). If not contained in a ScrollPane, the
   * preferred size is the size of the viewSize itself with no scaling
   * applied.
   */
  public void computePreferredSize()
  {
    BWidget offsetParent = LayoutUtil.getOffsetParent(this);
    BSize viewSize = getViewSize();
    if (offsetParent != null)
    {
      ScaledLayout scaledLayout =
        ScaledLayout.scaleToOffsetParent(this, offsetParent);
      setPreferredSize(Math.round(viewSize.width * scaledLayout.getScaleX()),
        Math.round(viewSize.height * scaledLayout.getScaleY()));
    }
    else
    {
      setPreferredSize(viewSize.width, viewSize.height);
    }
  }

  /**
   * Calculate the scaling and translation needed to have the view respect the
   * configured dimensions, alignment, and scale mode.
   * @param kids child widgets
   */
  public void doLayout(BWidget[] kids)
  {
    scaledLayout = ScaledLayout.scaleToSelf(this);
    layoutKids(kids);
  }

  private void layoutKids(BWidget[] kids)
  {
    BSize viewSize = getViewSize();
    double vw = viewSize.width;
    double vh = viewSize.height;

    // Layout Children
    for (BWidget kid : kids)
    {
      BLayout layout = kid.getLayout();
      if (layout.isNull())
      {
        continue;
      }

      kid.computePreferredSize();
      double cx = layout.getX();
      double cy = layout.getY();
      double cw = layout.getWidth();
      double ch = layout.getHeight();

      if (layout.getXUnit() == BLayout.PERCENT)
      {
        cx = (cx / 100) * vw;
      }
      if (layout.getYUnit() == BLayout.PERCENT)
      {
        cy = (cy / 100) * vh;
      }

      if (layout.getWidthUnit() == BLayout.PERCENT)
      {
        cw = (cw / 100) * vw;
      }
      else if (layout.getWidthUnit() == BLayout.PREF)
      {
        cw = kid.getPreferredWidth();
      }

      if (layout.getHeightUnit() == BLayout.PERCENT)
      {
        ch = (ch / 100) * vh;
      }
      else if (layout.getHeightUnit() == BLayout.PREF)
      {
        ch = kid.getPreferredHeight();
      }

      kid.setBounds(cx, cy, cw, ch);
    }
  }


////////////////////////////////////////////////////////////////
// Translation
////////////////////////////////////////////////////////////////

  public BWidget childAt(Point pt)
  {
    return super.childAt(getScaleTransform().getInverse().transform(pt, null));
  }

  /**
   * Get the immediate children of this widget which
   * are located at the specified coordinates relative
   * to this widget's coordindate space.  This method
   * automatically excludes all children widgets which
   * are not visible regardless of their current bounds.
   *
   * @param pt point in this widget's coordinate
   *    system.  The point is not guaranteed to
   *    remain immutable.
   * @return null if no immediate children
   *    contain the specified point.
   */
  public BWidget[] childrenAt(Point pt)
  {
    pt = getScaleTransform().getInverse().transform(pt, null);
    Array<BWidget> arr = new Array<>(BWidget.class);

    // start at top of z-order and work down
    BWidget[] children = getChildWidgets();
    for(int i=children.length-1; i>=0; --i)
    {
      // skip widgets which aren't visible
      BWidget child = children[i];
      if (!child.isVisible()) continue;

      // does the child contain this widget
      if (child.contains(pt.x-child.getX(), pt.y-child.getY()))
        arr.add(child);
    }

    if (arr.size() == 0) return null;
    return arr.trim();
  }

  public Point translateToChild(BWidget child, Point pt)
  {
    pt = getScaleTransform().getInverse().transform(pt, pt);
    return super.translateToChild(child, pt);
  }

  public Point translateFromChild(BWidget child, Point pt)
  {
    pt.x += child.getX();
    pt.y += child.getY();
    pt = getScaleTransform().transform(pt, pt);
    return pt;
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    double dx = scaledLayout.getOffsetX();
    double dy = scaledLayout.getOffsetY();
    double sx = scaledLayout.getScaleX();
    double sy = scaledLayout.getScaleY();
    BTransform transform = getScaleTransform();

    // draw the background
    BBrush brush = getBackground();
    if (!brush.isNull())
    {
      g.setBrush(brush);
      g.fillRect(0, 0, getWidth(), getHeight());
    }

    // draw the children
    g.push();
    try
    {
      g.transform(transform);
      g.clip(0, 0, getViewSize().width+1, getViewSize().height+1);

      if (paintGraphPaper)
      {
        graphPaper.fillRect(
          g,
          (dx < 0) ? -dx * sx : 0,
          (dy < 0) ? -dy * sy : 0,
          getWidth()  / sx,
          getHeight() / sy);
      }

      paintKids(g, paintHatching && hatchable());
    }
    finally
    {
      g.pop();
    }
  }

  /**
   * paintKids
   */
  @SuppressWarnings("WeakerAccess") protected void paintKids(Graphics g, boolean hatch)
  {
    IGeom clip = g.getClip();
    double dx = scaledLayout.getOffsetX();
    double dy = scaledLayout.getOffsetY();
    double sx = scaledLayout.getScaleX();
    double sy = scaledLayout.getScaleY();
    BTransform scale = BTransform.make(scaledLayout.getScale());

    // walk through all my BWidget children
    BWidget[] children = getChildWidgets();
    for (BWidget child : children)
    {
      if (!child.isVisible()) continue;

      double x = child.getX();
      double y = child.getY();
      double width = child.getWidth();
      double height = child.getHeight();

      // check if the clip intersects child's bounds
      if (!overrideClip && !clip.intersects(x, y, width, height)) continue;

      // Push copy of current grpahics state so that each
      // widget gets a clean slate without any left over
      // state from previous widget paint code
      g.push();
      try
      {
        // we now need to paint this child
        g.clip(x, y, width, height);
        g.translate(x, y);
        child.paint(g);

        if (hatch && !(child instanceof BShape))
        {
          g.transform(scale);
          hatching.fillRect(
            g,
            (dx + x) < 0 ? -(dx + x) * sx : 0,
            (dy + y) < 0 ? -(dy + y) * sy : 0,
            Math.ceil(width / sx),
            Math.ceil(height / sy));
        }
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
      finally
      {
        g.pop();
      }
    }
  }

  /**
   * a BCanvasPane is 'hatchable' if it is not the
   * descendant of any BTabbedPane, or if it is on the
   * active tab of each BTabbedPane that it is descended from
   */
  private boolean hatchable()
  {
    BWidget parent = this.getParentWidget();
    while (parent != null)
    {
      if (parent instanceof BCanvasPane)
      {
        return false;
      }
      else if ((parent instanceof BLabelPane) &&
        (parent.getParentWidget() instanceof BTabbedPane))
      {
        BLabelPane tab = (BLabelPane) parent;
        BTabbedPane pane = (BTabbedPane) tab.getParentWidget();
        if (pane.getSelectedLabelPane() != tab) return false;
      }
      parent = parent.getParentWidget();
    }
    return true;
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
    if (x == Fw.PX_EDITOR)
    {
      // graphPaper
      Object[] gp = (Object[]) a;
      paintGraphPaper = (Boolean) gp[0];
      if (paintGraphPaper)
      {
        if (graphPaper == null) graphPaper = GxEnv.get().makeGraphPaper();
        graphPaper.setSize((Integer) gp[1]);
        graphPaper.setColor((BColor) gp[2]);
      }

      // hatching
      Object[] ha = (Object[]) b;
      paintHatching = (Boolean) ha[0];
      if (paintHatching)
      {
        if (hatching == null) hatching = GxEnv.get().makeHatching();
        hatching.setColor((BColor) ha[1]);
      }
    }

    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/canvasPane.png");

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public BTransform getScaleTransform() { return scaledLayout.transform(); }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // FW use only
  public boolean overrideClip = false;

  private ScaledLayout scaledLayout = new ScaledLayout();

  // only used when inside the PxEditor
  private boolean paintGraphPaper;
  private boolean paintHatching;
  private GraphPaper graphPaper;
  private Hatching   hatching;
}
