/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLayoutDimension;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;

/**
 * BResponsivePane can be used to customize the layout of a widgets within the
 * BFlowPane. The MaxWidth LayoutDimension defaults to the content's preferred size,
 * but can be changed for better flow layout. For example, setting 4 sibling
 * BResponsivePanes to 25% each will try to fit the 4 children on the same row.
 * The minWidth defaults to 20 pixels, but should be customized to the smallest width
 * desired width for the inner content. The last widget on a row will shrink down
 * from the maxWidth to the minWidth and still fit on the row, but if there not enough space for the
 * minWidth the FlowPane will drop that child down to the next row.
 *
 * @author JJ Frankovich
 * @since Niagara 4.6
 */
@NiagaraType
/*
 The content of the pane.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 The maximum and preferred width of the pane.
 */
@NiagaraProperty(
  name = "maxWidth",
  type = "BLayoutDimension",
  defaultValue = "BLayoutDimension.make(0, BLayoutDimension.PREF)"
)
/*
 The minimum width allowed for the pane before we start wrapping.
 */
@NiagaraProperty(
  name = "minWidth",
  type = "BLayoutDimension",
  defaultValue = "BLayoutDimension.make(20, BLayoutDimension.ABS)"
)
/*
 If true, will force this widget to start on a new row, this can be useful
 for dividing widgets into different sections of a FlowPane like for a header or footer.
 */
@NiagaraProperty(
  name = "forceNewRow",
  type = "boolean",
  defaultValue = "false"
)
public class BResponsivePane
  extends BPane
{

  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BResponsivePane(2759778134)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * The content of the pane.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * The content of the pane.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * The content of the pane.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Property "maxWidth"

  /**
   * Slot for the {@code maxWidth} property.
   * The maximum and preferred width of the pane.
   * @see #getMaxWidth
   * @see #setMaxWidth
   */
  public static final Property maxWidth = newProperty(0, BLayoutDimension.make(0, BLayoutDimension.PREF), null);

  /**
   * Get the {@code maxWidth} property.
   * The maximum and preferred width of the pane.
   * @see #maxWidth
   */
  public BLayoutDimension getMaxWidth() { return (BLayoutDimension)get(maxWidth); }

  /**
   * Set the {@code maxWidth} property.
   * The maximum and preferred width of the pane.
   * @see #maxWidth
   */
  public void setMaxWidth(BLayoutDimension v) { set(maxWidth, v, null); }

  //endregion Property "maxWidth"

  //region Property "minWidth"

  /**
   * Slot for the {@code minWidth} property.
   * The minimum width allowed for the pane before we start wrapping.
   * @see #getMinWidth
   * @see #setMinWidth
   */
  public static final Property minWidth = newProperty(0, BLayoutDimension.make(20, BLayoutDimension.ABS), null);

  /**
   * Get the {@code minWidth} property.
   * The minimum width allowed for the pane before we start wrapping.
   * @see #minWidth
   */
  public BLayoutDimension getMinWidth() { return (BLayoutDimension)get(minWidth); }

  /**
   * Set the {@code minWidth} property.
   * The minimum width allowed for the pane before we start wrapping.
   * @see #minWidth
   */
  public void setMinWidth(BLayoutDimension v) { set(minWidth, v, null); }

  //endregion Property "minWidth"

  //region Property "forceNewRow"

  /**
   * Slot for the {@code forceNewRow} property.
   * If true, will force this widget to start on a new row, this can be useful
   * for dividing widgets into different sections of a FlowPane like for a header or footer.
   * @see #getForceNewRow
   * @see #setForceNewRow
   */
  public static final Property forceNewRow = newProperty(0, false, null);

  /**
   * Get the {@code forceNewRow} property.
   * If true, will force this widget to start on a new row, this can be useful
   * for dividing widgets into different sections of a FlowPane like for a header or footer.
   * @see #forceNewRow
   */
  public boolean getForceNewRow() { return getBoolean(forceNewRow); }

  /**
   * Set the {@code forceNewRow} property.
   * If true, will force this widget to start on a new row, this can be useful
   * for dividing widgets into different sections of a FlowPane like for a header or footer.
   * @see #forceNewRow
   */
  public void setForceNewRow(boolean v) { setBoolean(forceNewRow, v, null); }

  //endregion Property "forceNewRow"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BResponsivePane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * A BResponsivePane's parent must be a BFlowPane
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BFlowPane;
  }

  /**
   * BResponsivePane has a preferred size equal to
   * its child content component.
   */
  @Override
  public void computePreferredSize()
  {
    BWidget c = getContent();
    c.computePreferredSize();
    double w = c.getPreferredWidth();
    double h = c.getPreferredHeight();
    setPreferredSize(w, h);
  }

  /**
   * Layout the pane.
   */
  @Override
  public void doLayout(BWidget[] kids)
  {
    BWidget content = getContent();
    if (content.isNull())
    {
      content.setBounds(0, 0, 0, 0);
    }
    else
    {
      content.setBounds(0, 0, getWidth(), getHeight());
    }
  }
////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("widgets/constrainedPane.png");

}
