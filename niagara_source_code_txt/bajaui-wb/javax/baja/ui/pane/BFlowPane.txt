/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.BBrush;
import javax.baja.gx.BInsets;
import javax.baja.gx.BSize;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLayoutDimension;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;

import com.tridium.ui.ShellManager;
import com.tridium.ui.util.LayoutUtil;

/**
 * BFlowPane lays out its children from left to right and top to
 * bottom fitting as many children as possible in each row.  It
 * is based on the java.awt.FlowLayout. Starting in Niagara 4.6,
 * a BResponsivePane can be used to further customize the positioning
 * of the widgets.
 *
 * @author John Sublett and JJ Frankovich
 * @since Niagara 4.0
 */
@NiagaraType
/*
 How are children aligned horizontally on each row. When fill is used, BResponsivePane children with a Percent maxWidth will
 spread out to fill the entire row. Pref, Abs, and non-Responsive Pane children will just align left.
 */
@NiagaraProperty(
  name = "align",
  type = "BHalign",
  defaultValue = "BHalign.left"
)
/*
 Determines how to use extra vertical space within rows.
 */
@NiagaraProperty(
  name = "rowAlign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 Horizontal gap is the space in pixels between each child on a
 row.  It does not affect the space between a child and the
 pane boundary.
 */
@NiagaraProperty(
  name = "hgap",
  type = "int",
  defaultValue = "4"
)
/*
 Vertical gap is the space in pixels between row.  It does
 not affect the space between a child and the pane boundary.
 */
@NiagaraProperty(
  name = "vgap",
  type = "int",
  defaultValue = "4"
)
/*
 Specifies how the pane's background is painted.
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
public class BFlowPane
  extends BPane
{

  


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BFlowPane(2885889114)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "align"

  /**
   * Slot for the {@code align} property.
   * How are children aligned horizontally on each row. When fill is used, BResponsivePane children with a Percent maxWidth will
   * spread out to fill the entire row. Pref, Abs, and non-Responsive Pane children will just align left.
   * @see #getAlign
   * @see #setAlign
   */
  public static final Property align = newProperty(0, BHalign.left, null);

  /**
   * Get the {@code align} property.
   * How are children aligned horizontally on each row. When fill is used, BResponsivePane children with a Percent maxWidth will
   * spread out to fill the entire row. Pref, Abs, and non-Responsive Pane children will just align left.
   * @see #align
   */
  public BHalign getAlign() { return (BHalign)get(align); }

  /**
   * Set the {@code align} property.
   * How are children aligned horizontally on each row. When fill is used, BResponsivePane children with a Percent maxWidth will
   * spread out to fill the entire row. Pref, Abs, and non-Responsive Pane children will just align left.
   * @see #align
   */
  public void setAlign(BHalign v) { set(align, v, null); }

  //endregion Property "align"

  //region Property "rowAlign"

  /**
   * Slot for the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #getRowAlign
   * @see #setRowAlign
   */
  public static final Property rowAlign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #rowAlign
   */
  public BValign getRowAlign() { return (BValign)get(rowAlign); }

  /**
   * Set the {@code rowAlign} property.
   * Determines how to use extra vertical space within rows.
   * @see #rowAlign
   */
  public void setRowAlign(BValign v) { set(rowAlign, v, null); }

  //endregion Property "rowAlign"

  //region Property "hgap"

  /**
   * Slot for the {@code hgap} property.
   * Horizontal gap is the space in pixels between each child on a
   * row.  It does not affect the space between a child and the
   * pane boundary.
   * @see #getHgap
   * @see #setHgap
   */
  public static final Property hgap = newProperty(0, 4, null);

  /**
   * Get the {@code hgap} property.
   * Horizontal gap is the space in pixels between each child on a
   * row.  It does not affect the space between a child and the
   * pane boundary.
   * @see #hgap
   */
  public int getHgap() { return getInt(hgap); }

  /**
   * Set the {@code hgap} property.
   * Horizontal gap is the space in pixels between each child on a
   * row.  It does not affect the space between a child and the
   * pane boundary.
   * @see #hgap
   */
  public void setHgap(int v) { setInt(hgap, v, null); }

  //endregion Property "hgap"

  //region Property "vgap"

  /**
   * Slot for the {@code vgap} property.
   * Vertical gap is the space in pixels between row.  It does
   * not affect the space between a child and the pane boundary.
   * @see #getVgap
   * @see #setVgap
   */
  public static final Property vgap = newProperty(0, 4, null);

  /**
   * Get the {@code vgap} property.
   * Vertical gap is the space in pixels between row.  It does
   * not affect the space between a child and the pane boundary.
   * @see #vgap
   */
  public int getVgap() { return getInt(vgap); }

  /**
   * Set the {@code vgap} property.
   * Vertical gap is the space in pixels between row.  It does
   * not affect the space between a child and the pane boundary.
   * @see #vgap
   */
  public void setVgap(int v) { setInt(vgap, v, null); }

  //endregion Property "vgap"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * Specifies how the pane's background is painted.
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code background} property.
   * Specifies how the pane's background is painted.
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * Specifies how the pane's background is painted.
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlowPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BFlowPane with default properties.
   */
  public BFlowPane()
  {
  }

  /**
   * Constructor with specified horizontal alignment.
   */
  public BFlowPane(BHalign alignment)
  {
    setAlign(alignment);
  }

  /**
   * Constructor with specified horizontal alignment and gaps.
   */
  public BFlowPane(BHalign alignment, int hgap, int vgap)
  {
    setAlign(alignment);
    setHgap(hgap);
    setVgap(vgap);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the pane.
   */
  @Override
  public void computePreferredSize()
  {
    double w = 0;
    double h = 0;

    double maxWidth = 0;
    double maxHeight = 0;

    BWidget[] kids = getChildWidgets();

    double hgap = getHgap();

    for (int i = 0; i < kids.length; ++i)
    {
      BWidget k = kids[i];
      if (!k.isVisible()) { continue; }
      k.computePreferredSize();
      if (i > 0) { w += hgap; }
      w += k.getPreferredWidth();
      h = Math.max(h, k.getPreferredHeight());

      maxWidth = Math.max(maxWidth, getMinWidth(k, true));
      maxHeight = Math.max(maxHeight, k.getY() + k.getPreferredHeight());
    }

    BScrollPane scrollPane = LayoutUtil.getParentScrollPane(this);
    if (scrollPane != null)
    {
      BInsets border = LayoutUtil.getAccumulatedBorderAndScrollSize(this);

      BSize targetSize = BSize.make(
        scrollPane.getWidth() - border.left - border.right,
        scrollPane.getHeight() - border.top - border.bottom);

      setPreferredSize(Math.max(targetSize.width(), maxWidth), Math.max(targetSize.height(), maxHeight));
    }
    else
    {
      setPreferredSize(w, h);
    }
  }

  @Override
  public void paint(Graphics g)
  {
    // draw the background
    BBrush brush = getBackground();
    if (!brush.isNull())
    {
      g.setBrush(brush);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
    super.paint(g);
  }

  /**
   * Layout the pane.
   */
  @Override
  public void doLayout(BWidget[] children)
  {
    doLayout(children, true);
  }

  /**
   * Layout the pane; you can prevent additional recursive calls to doLayout by setting the
   * firstFixHeight parameter to false.
   *
   */
  protected void doLayout(BWidget[] children, boolean firstFixHeight)
  {
    double currentMaxHeight = 0;

    double x, y;
    double rowTop = 0;
    BHalign alignment = getAlign();
    BValign valignment = getRowAlign();

    double w = getWidth();
    double hgap = getHgap();
    double vgap = getVgap();

    int rowStart = 0;
    int rowEnd = -1;

    double rowEndResponsiveWidth = -1;
    int rowEndResponsiveId = -1;

    while (rowStart < children.length)
    {
      // compute the number of children that will fit on the current
      // row and compute the minimum width and height of the row
      // that would contain the children
      double rowWidth = 0;
      double rowHeight = 0;
      boolean firstItem = true;
      double[] preferredWidths = new double[children.length];
      double rowWidthUsedByPercent = 0;
      double rowWidthUsedByAbs = 0;
      for (int i = rowStart; i < children.length; i++)
      {
        BWidget child = children[i];
        if (!child.isVisible()) { continue; }

        preferredWidths[i] = getPreferredWidth(child);
        double moreWidth = preferredWidths[i] + (firstItem ? 0 : hgap);
        double moreWidthLeast = getMinWidth(child, true) + (firstItem ? 0 : hgap);
        if(!firstItem && isForceNewRow(child))
        {
          break;
        }
        else if (moreWidth + rowWidth <= w)
        {
          rowWidth += moreWidth;
          rowHeight = Math.max(rowHeight, child.getPreferredHeight());
          rowEnd = i;
          firstItem = false;
        }
        else if (moreWidthLeast + rowWidth <= w)
        {
          rowEndResponsiveWidth = w - rowWidth - (firstItem ? 0 : hgap);
          rowEndResponsiveId = i;
          rowWidth = w;
          rowHeight = Math.max(rowHeight, child.getPreferredHeight());
          rowEnd = i;
          firstItem = false;
        }
        else
        {
          break;
        }
      }

      if (rowEnd < rowStart)
      {
        fixLastHeight(currentMaxHeight, firstFixHeight);
        return;
      }

      // layout the children on the current row
      if (alignment == BHalign.left)
      {
        x = 0;
      }
      else if (alignment == BHalign.right)
      {
        x = w - rowWidth;
      }
      else if (alignment == BHalign.center)
      {
        x = (w - rowWidth) / 2;
      }
      else //fill
      {
        x = 0;
      }

      boolean usedByFirstItem = true;
      for (int i = rowStart; i <= rowEnd; i++)
      {
        BWidget child = children[i];

        if (!child.isVisible()) { continue; }

        double childHeight = child.getPreferredHeight();

        if (valignment == BValign.top)
        {
          y = rowTop;
        }
        else if (valignment == BValign.center)
        {
          y = rowTop + (rowHeight - childHeight) / 2;
        }
        else if (valignment == BValign.fill)
        {
          y = rowTop;
          childHeight=rowHeight;
        }
        else //bottom
        {
          y = rowTop + rowHeight - childHeight;
        }

        double boundWidth = preferredWidths[i];
        if (rowEndResponsiveId == i)
        {
          boundWidth = rowEndResponsiveWidth;
        }

        child.setBounds(x, y,
          boundWidth,
          childHeight);

        child.computePreferredSize();
        x += boundWidth + hgap;

        //designate the width into into either a percent or absolute bucket (percent buckets can get spread out)
        if(alignment == BHalign.fill)
        {
          if (isPercentMaxWidth(child))
          {
            rowWidthUsedByPercent += boundWidth;
          }
          else
          {
            rowWidthUsedByAbs += boundWidth;
          }
          if (!usedByFirstItem)
          {
            rowWidthUsedByAbs += hgap;
          }
        }

        currentMaxHeight = Math.max(currentMaxHeight, y + childHeight);
        usedByFirstItem=false;
      }

      //when we use fill and have some percent items, do a second pass to spread everything out
      if(alignment == BHalign.fill && rowWidthUsedByPercent > 0)
      {
        double specificRatio = w == 0 || rowWidthUsedByPercent==0? 1: (w - rowWidthUsedByAbs) / rowWidthUsedByPercent;


        double newX = 0;
        for (int i = rowStart; i <= rowEnd; i++)
        {
          BWidget child = children[i];
          if (!child.isVisible()) { continue; }

          double oldWidth = child.getWidth();
          double newWidth = oldWidth;

          if (isPercentMaxWidth(child))
          {
            newWidth *= specificRatio;
          }

          child.setBounds(newX, child.getY(),
            newWidth,
            child.getHeight());
          if (oldWidth != newWidth)
          {
            child.computePreferredSize();
          }

          newX += newWidth + hgap;
        }
      }

      rowTop += rowHeight + vgap;
      rowStart = rowEnd + 1;
    }

    fixLastHeight(currentMaxHeight, firstFixHeight);
  }

  private static boolean isPercentMaxWidth(BWidget widget)
  {
    if (widget instanceof BResponsivePane)
    {
      BResponsivePane responsivePane = (BResponsivePane) widget;
      return responsivePane.getMaxWidth().getUnit() == BLayoutDimension.PERCENT;
    }
    return false;
  }

  private static boolean isForceNewRow(BWidget widget)
  {
    if (widget instanceof BResponsivePane)
    {
      BResponsivePane responsivePane = (BResponsivePane) widget;
      return responsivePane.getForceNewRow();
    }
    return false;
  }

  /**
   * Get the preferred width of a widget child based on any customization by the
   * responsive pane if its present.
   * @since Niagara 4.6
   */
  public double getPreferredWidth(BWidget widget)
  {
    if (widget instanceof BResponsivePane)
    {
      BResponsivePane responsivePane = (BResponsivePane) widget;
      double preferredWidth;
      BLayoutDimension maxWidth = responsivePane.getMaxWidth();
      if (maxWidth.getUnit() == BLayoutDimension.PREF)
      {
        preferredWidth = responsivePane.getPreferredWidth();
      }
      else if (maxWidth.getUnit() == BLayoutDimension.ABS)
      {
        preferredWidth = maxWidth.getValue();
      }
      else
      {
        preferredWidth = getWidthFromPercentage(maxWidth.getValue());
      }

      double min = getMinWidth(widget, false);

      return Math.max(preferredWidth, min);
    }
    else
    {
      return widget.getPreferredWidth(); //not responsive, but still allowed
    }
  }

  /**
   * parent width is percentage of responsive pane preferred width if scroll pane is present which includes
   * offset for borders and scrollbars. If no scrollPane, just used width.
   * @since Niagara 4.6
   */
  public double getWidthFromPercentage(double percent)
  {
    double parentWidth = LayoutUtil.getParentScrollPane(this) != null ? getPreferredWidth() : getWidth();
    //estimate the width taken up by the gaps at this percent, 100% has no gaps, 50% has 1, 25% has 3, etc
    double gapWith = percent == 0 ? 0 : ((int) 100d / percent) - 1;
    parentWidth -= gapWith * getHgap();
    return percent / 100.0d * parentWidth;
  }

  private double getMinWidth(BWidget widget, boolean requiresComputePreferred)
  {
    if (widget instanceof BResponsivePane)
    {
      BResponsivePane responsivePane = (BResponsivePane) widget;
      double min;
      BLayoutDimension minWidth = responsivePane.getMinWidth();
      if (minWidth.getUnit() == BLayoutDimension.PREF)
      {
        if (requiresComputePreferred) { responsivePane.computePreferredSize(); }
        min = responsivePane.getPreferredWidth();
      }
      else if (minWidth.getUnit() == BLayoutDimension.ABS)
      {
        min = minWidth.getValue();
      }
      else
      {
        min = getWidthFromPercentage(minWidth.getValue());
      }
      //don't let min go below zero
      min = Math.max(min, 0);
      return min;
    }
    else
    {
      if (requiresComputePreferred) { widget.computePreferredSize(); }
      return widget.getPreferredWidth();
    }
  }

  /**
   * When layout change is detected, we'll need to relayout the ScrollPane and then the BFlowPane again so make
   * sure scroll bars are visible and then relayout based on the changes to the ScrollPane.
   */
  private void fixLastHeight(double currentMaxHeight, boolean firstFixHeight)
  {
    if (lastMaxHeight != currentMaxHeight)
    {
      BWidget parent = LayoutUtil.getParentScrollPane(this);
      while (parent instanceof BScrollPane || parent instanceof BBorderPane || parent instanceof BFlowPane)
      {
        ShellManager manager = getShellManager();
        //only call layout if bounds is set
        if (parent.getWidth() > 0 && parent.getHeight() > 0)
        {
          if (manager == null || manager.forceFreshLayout())
          {
            //relayoutSync does not work from Awt Layout thread
            if (parent instanceof BFlowPane)
            {
               if(firstFixHeight)
               {
                 ((BFlowPane) parent).doLayout(parent.getChildWidgets(), false);
               }
            }
            else
            {
              parent.doLayout(parent.getChildWidgets());
            }
          }
          else
          {
            //for hx
            parent.relayoutSync();
          }
        }
        parent = (BWidget) parent.get("content");
        if (parent instanceof BFlowPane)
        {
          lastMaxHeight = currentMaxHeight;
        }
      }
    }
  }

  protected double lastMaxHeight = 0;

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("widgets/flowPane.png");

}
