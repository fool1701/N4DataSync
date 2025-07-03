/*    
 * Copyright 2005 Tridium, Inc.  All rights reserved.
 */
package com.tridium.kitpx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.baja.agent.BAbstractPxView;
import javax.baja.agent.BDynamicPxView;
import javax.baja.converters.BObjectToString;
import javax.baja.gx.BSize;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IFilter;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BLink;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BLayout;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BCanvasPane;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.ui.px.PxEncoder;
import javax.baja.util.BFormat;

import com.tridium.kitpx.enums.BStatusEffect;

/**
 * BComponentToPx creates a PX representation for
 * the properties of a BComponent.
 *
 * @author    Mike Jarmy
 * @creation  12 Dec 06
 * @version   $Revision: 187$ $Date: 11/22/2006 2:32:16 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Whether to display properties of type baja:BLink.
 */
@NiagaraProperty(
  name = "showLinks",
  type = "boolean",
  defaultValue = "false"
)
/*
 Whether to display properties of type baja:BAbstractPxView.
 */
@NiagaraProperty(
  name = "showViews",
  type = "boolean",
  defaultValue = "false"
)
/*
 Number of rows in each column.
 */
@NiagaraProperty(
  name = "rowsPerColumn",
  type = "int",
  defaultValue = "10"
)
/*
 Spacing between columns (in pixels).
 */
@NiagaraProperty(
  name = "columnSpacing",
  type = "int",
  defaultValue = "40"
)
/*
 Height of each widget (in pixels).
 */
@NiagaraProperty(
  name = "widgetHeight",
  type = "int",
  defaultValue = "20"
)
/*
 Width of each name label (in pixels).
 */
@NiagaraProperty(
  name = "nameLabelWidth",
  type = "int",
  defaultValue = "100"
)
/*
 Horizontal alignment of each name label.
 */
@NiagaraProperty(
  name = "nameLabelAlign",
  type = "BHalign",
  defaultValue = "BHalign.left"
)
/*
 Width of each bound label (in pixels).
 */
@NiagaraProperty(
  name = "boundLabelWidth",
  type = "int",
  defaultValue = "150"
)
/*
 Horizontal alignment of each bound label.
 */
@NiagaraProperty(
  name = "boundLabelAlign",
  type = "BHalign",
  defaultValue = "BHalign.center"
)
/*
 Text format of each bound label.
 */
@NiagaraProperty(
  name = "boundLabelTextFormat",
  type = "String",
  defaultValue = "%.%"
)
public class BComponentToPx
  extends BDynamicPxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BComponentToPx(3069210439)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "showLinks"

  /**
   * Slot for the {@code showLinks} property.
   * Whether to display properties of type baja:BLink.
   * @see #getShowLinks
   * @see #setShowLinks
   */
  public static final Property showLinks = newProperty(0, false, null);

  /**
   * Get the {@code showLinks} property.
   * Whether to display properties of type baja:BLink.
   * @see #showLinks
   */
  public boolean getShowLinks() { return getBoolean(showLinks); }

  /**
   * Set the {@code showLinks} property.
   * Whether to display properties of type baja:BLink.
   * @see #showLinks
   */
  public void setShowLinks(boolean v) { setBoolean(showLinks, v, null); }

  //endregion Property "showLinks"

  //region Property "showViews"

  /**
   * Slot for the {@code showViews} property.
   * Whether to display properties of type baja:BAbstractPxView.
   * @see #getShowViews
   * @see #setShowViews
   */
  public static final Property showViews = newProperty(0, false, null);

  /**
   * Get the {@code showViews} property.
   * Whether to display properties of type baja:BAbstractPxView.
   * @see #showViews
   */
  public boolean getShowViews() { return getBoolean(showViews); }

  /**
   * Set the {@code showViews} property.
   * Whether to display properties of type baja:BAbstractPxView.
   * @see #showViews
   */
  public void setShowViews(boolean v) { setBoolean(showViews, v, null); }

  //endregion Property "showViews"

  //region Property "rowsPerColumn"

  /**
   * Slot for the {@code rowsPerColumn} property.
   * Number of rows in each column.
   * @see #getRowsPerColumn
   * @see #setRowsPerColumn
   */
  public static final Property rowsPerColumn = newProperty(0, 10, null);

  /**
   * Get the {@code rowsPerColumn} property.
   * Number of rows in each column.
   * @see #rowsPerColumn
   */
  public int getRowsPerColumn() { return getInt(rowsPerColumn); }

  /**
   * Set the {@code rowsPerColumn} property.
   * Number of rows in each column.
   * @see #rowsPerColumn
   */
  public void setRowsPerColumn(int v) { setInt(rowsPerColumn, v, null); }

  //endregion Property "rowsPerColumn"

  //region Property "columnSpacing"

  /**
   * Slot for the {@code columnSpacing} property.
   * Spacing between columns (in pixels).
   * @see #getColumnSpacing
   * @see #setColumnSpacing
   */
  public static final Property columnSpacing = newProperty(0, 40, null);

  /**
   * Get the {@code columnSpacing} property.
   * Spacing between columns (in pixels).
   * @see #columnSpacing
   */
  public int getColumnSpacing() { return getInt(columnSpacing); }

  /**
   * Set the {@code columnSpacing} property.
   * Spacing between columns (in pixels).
   * @see #columnSpacing
   */
  public void setColumnSpacing(int v) { setInt(columnSpacing, v, null); }

  //endregion Property "columnSpacing"

  //region Property "widgetHeight"

  /**
   * Slot for the {@code widgetHeight} property.
   * Height of each widget (in pixels).
   * @see #getWidgetHeight
   * @see #setWidgetHeight
   */
  public static final Property widgetHeight = newProperty(0, 20, null);

  /**
   * Get the {@code widgetHeight} property.
   * Height of each widget (in pixels).
   * @see #widgetHeight
   */
  public int getWidgetHeight() { return getInt(widgetHeight); }

  /**
   * Set the {@code widgetHeight} property.
   * Height of each widget (in pixels).
   * @see #widgetHeight
   */
  public void setWidgetHeight(int v) { setInt(widgetHeight, v, null); }

  //endregion Property "widgetHeight"

  //region Property "nameLabelWidth"

  /**
   * Slot for the {@code nameLabelWidth} property.
   * Width of each name label (in pixels).
   * @see #getNameLabelWidth
   * @see #setNameLabelWidth
   */
  public static final Property nameLabelWidth = newProperty(0, 100, null);

  /**
   * Get the {@code nameLabelWidth} property.
   * Width of each name label (in pixels).
   * @see #nameLabelWidth
   */
  public int getNameLabelWidth() { return getInt(nameLabelWidth); }

  /**
   * Set the {@code nameLabelWidth} property.
   * Width of each name label (in pixels).
   * @see #nameLabelWidth
   */
  public void setNameLabelWidth(int v) { setInt(nameLabelWidth, v, null); }

  //endregion Property "nameLabelWidth"

  //region Property "nameLabelAlign"

  /**
   * Slot for the {@code nameLabelAlign} property.
   * Horizontal alignment of each name label.
   * @see #getNameLabelAlign
   * @see #setNameLabelAlign
   */
  public static final Property nameLabelAlign = newProperty(0, BHalign.left, null);

  /**
   * Get the {@code nameLabelAlign} property.
   * Horizontal alignment of each name label.
   * @see #nameLabelAlign
   */
  public BHalign getNameLabelAlign() { return (BHalign)get(nameLabelAlign); }

  /**
   * Set the {@code nameLabelAlign} property.
   * Horizontal alignment of each name label.
   * @see #nameLabelAlign
   */
  public void setNameLabelAlign(BHalign v) { set(nameLabelAlign, v, null); }

  //endregion Property "nameLabelAlign"

  //region Property "boundLabelWidth"

  /**
   * Slot for the {@code boundLabelWidth} property.
   * Width of each bound label (in pixels).
   * @see #getBoundLabelWidth
   * @see #setBoundLabelWidth
   */
  public static final Property boundLabelWidth = newProperty(0, 150, null);

  /**
   * Get the {@code boundLabelWidth} property.
   * Width of each bound label (in pixels).
   * @see #boundLabelWidth
   */
  public int getBoundLabelWidth() { return getInt(boundLabelWidth); }

  /**
   * Set the {@code boundLabelWidth} property.
   * Width of each bound label (in pixels).
   * @see #boundLabelWidth
   */
  public void setBoundLabelWidth(int v) { setInt(boundLabelWidth, v, null); }

  //endregion Property "boundLabelWidth"

  //region Property "boundLabelAlign"

  /**
   * Slot for the {@code boundLabelAlign} property.
   * Horizontal alignment of each bound label.
   * @see #getBoundLabelAlign
   * @see #setBoundLabelAlign
   */
  public static final Property boundLabelAlign = newProperty(0, BHalign.center, null);

  /**
   * Get the {@code boundLabelAlign} property.
   * Horizontal alignment of each bound label.
   * @see #boundLabelAlign
   */
  public BHalign getBoundLabelAlign() { return (BHalign)get(boundLabelAlign); }

  /**
   * Set the {@code boundLabelAlign} property.
   * Horizontal alignment of each bound label.
   * @see #boundLabelAlign
   */
  public void setBoundLabelAlign(BHalign v) { set(boundLabelAlign, v, null); }

  //endregion Property "boundLabelAlign"

  //region Property "boundLabelTextFormat"

  /**
   * Slot for the {@code boundLabelTextFormat} property.
   * Text format of each bound label.
   * @see #getBoundLabelTextFormat
   * @see #setBoundLabelTextFormat
   */
  public static final Property boundLabelTextFormat = newProperty(0, "%.%", null);

  /**
   * Get the {@code boundLabelTextFormat} property.
   * Text format of each bound label.
   * @see #boundLabelTextFormat
   */
  public String getBoundLabelTextFormat() { return getString(boundLabelTextFormat); }

  /**
   * Set the {@code boundLabelTextFormat} property.
   * Text format of each bound label.
   * @see #boundLabelTextFormat
   */
  public void setBoundLabelTextFormat(String v) { setString(boundLabelTextFormat, v, null); }

  //endregion Property "boundLabelTextFormat"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponentToPx.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * generateXml
   */
  @NiagaraRpc(
    transports = @Transport(type = TransportType.fox),
    permissions = "r"
  )
  public String generateXml(Object arg, Context context)
  {
    final boolean showLinks = getShowLinks();
    final boolean showViews = getShowViews();

    int rows    = getRowsPerColumn();
    int spacing = getColumnSpacing();
    int height  = getWidgetHeight();

    int nameWidth  = getNameLabelWidth();
    int boundWidth = getBoundLabelWidth();
    BHalign nameAlign  = getNameLabelAlign();
    BHalign boundAlign = getBoundLabelAlign();

    String  boundText  = getBoundLabelTextFormat();

    // target is Component
    final BComponent comp = (BComponent) getParent();
    BOrd base = comp.getComponentSpace().getAbsoluteOrd();

    // get properties
    comp.lease();
    Property[] props = comp.getPropertiesArray();

    // remove hidden properties, and optionally BLinks and BPxViews
    props = (new Array<>(props)).filter(new IFilter()
    {
      public boolean accept(Object obj) 
      {
        Property p = (Property) obj;
        if (Flags.isHidden(comp, p)) return false;

        BValue v = comp.get(p);
        if (v instanceof BLink) return showLinks;
        if (v instanceof BAbstractPxView) return showViews;
        return true;
      }
    }).trim();

    // scroll
    BScrollPane scroll = new BScrollPane();

    // canvas
    int plen = props.length;
    int pr = plen/rows;
    if ((plen % rows) == 0) pr--;
    int cw = (((pr + 1) * (nameWidth + boundWidth)) + (pr * spacing));
    int ch = ((plen < rows) ? plen : rows) * height + (2 * height);

    BCanvasPane canvas = new BCanvasPane();
    canvas.setViewSize(BSize.make(cw, ch));
    scroll.setContent(canvas);

    // name of component
    BLabel label = new BLabel(comp.getDisplayName(context), nameAlign);
    label.setLayout(BLayout.makeAbs(0, 0, nameWidth, height));
    canvas.add(null, label);

    // properties
    for (int i = 0; i < plen; i++)
    {
      int x = (i / rows) * (nameWidth + boundWidth + spacing);
      int y = (i % rows) * height + (2 * height);

      // label
      label = new BLabel(comp.getDisplayName(props[i], context), nameAlign);
      label.setLayout(BLayout.makeAbs(x, y, nameWidth, height));
      canvas.add(null, label);

      // binding
      BOrd ord = BOrd.make(comp.getNavOrd().relativizeToSession().toString(null) + "|slot:" + props[i].getName()).normalize();

      BObjectToString text = new BObjectToString();
      text.setFormat(BFormat.make(boundText));

      BBoundLabelBinding bnd = new BBoundLabelBinding();
      bnd.setOrd(ord);
      bnd.setHyperlink(ord);
      bnd.setStatusEffect(BStatusEffect.none);
      bnd.add("text", text);

      label = new BBoundLabel();
      label.setHalign(boundAlign);
      label.setLayout(BLayout.makeAbs(x + nameWidth, y, boundWidth, height));
      label.add("binding", bnd);

      canvas.add(null, label);
    }

    // encode
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try { (new PxEncoder(out)).encodeDocument(scroll); }
    catch (IOException e) { throw new BajaRuntimeException(e); }
    return new String(out.toByteArray());
  }
}
