/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.gx.BInsets;
import javax.baja.gx.BPen;
import javax.baja.gx.BSize;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;

import com.tridium.ui.theme.Theme;

/**
 * BBorderPane provides a border content for a widget similar
 * to the CSS Box Model.  Margin defines the space between the
 * bounds and the border.  Padding defines the space between 
 * the border and the child bounds.  Border defines the look 
 * and width of the border around each of the sides.
 *
 * @author    Andy Frank
 * @creation  14 May 04
 * @version   $Revision: 34$ $Date: 6/20/11 9:34:25 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Label widget to display on border If a label is
 set then the label always displayed at the top left.
 */
@NiagaraProperty(
  name = "label",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Widget content to display inside border.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 Space to leave between BorderPane bounds and border.
 */
@NiagaraProperty(
  name = "margin",
  type = "BInsets",
  defaultValue = "BInsets.make(0,0,0,0)"
)
/*
 Space to leave between border and content bounds.
 */
@NiagaraProperty(
  name = "padding",
  type = "BInsets",
  defaultValue = "BInsets.make(10,10,10,10)"
)
/*
 Border to use.
 */
@NiagaraProperty(
  name = "border",
  type = "BBorder",
  defaultValue = "BBorder.none"
)
/*
 Color to fill background. Fill only affects the
 area of the widget inside the margin.  Use
 BBrush.NULL to leave background transparent.
 */
@NiagaraProperty(
  name = "fill",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
public class BBorderPane
  extends BPane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BBorderPane(2821452740)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "label"

  /**
   * Slot for the {@code label} property.
   * Label widget to display on border If a label is
   * set then the label always displayed at the top left.
   * @see #getLabel
   * @see #setLabel
   */
  public static final Property label = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code label} property.
   * Label widget to display on border If a label is
   * set then the label always displayed at the top left.
   * @see #label
   */
  public BWidget getLabel() { return (BWidget)get(label); }

  /**
   * Set the {@code label} property.
   * Label widget to display on border If a label is
   * set then the label always displayed at the top left.
   * @see #label
   */
  public void setLabel(BWidget v) { set(label, v, null); }

  //endregion Property "label"

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * Widget content to display inside border.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * Widget content to display inside border.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * Widget content to display inside border.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Property "margin"

  /**
   * Slot for the {@code margin} property.
   * Space to leave between BorderPane bounds and border.
   * @see #getMargin
   * @see #setMargin
   */
  public static final Property margin = newProperty(0, BInsets.make(0,0,0,0), null);

  /**
   * Get the {@code margin} property.
   * Space to leave between BorderPane bounds and border.
   * @see #margin
   */
  public BInsets getMargin() { return (BInsets)get(margin); }

  /**
   * Set the {@code margin} property.
   * Space to leave between BorderPane bounds and border.
   * @see #margin
   */
  public void setMargin(BInsets v) { set(margin, v, null); }

  //endregion Property "margin"

  //region Property "padding"

  /**
   * Slot for the {@code padding} property.
   * Space to leave between border and content bounds.
   * @see #getPadding
   * @see #setPadding
   */
  public static final Property padding = newProperty(0, BInsets.make(10,10,10,10), null);

  /**
   * Get the {@code padding} property.
   * Space to leave between border and content bounds.
   * @see #padding
   */
  public BInsets getPadding() { return (BInsets)get(padding); }

  /**
   * Set the {@code padding} property.
   * Space to leave between border and content bounds.
   * @see #padding
   */
  public void setPadding(BInsets v) { set(padding, v, null); }

  //endregion Property "padding"

  //region Property "border"

  /**
   * Slot for the {@code border} property.
   * Border to use.
   * @see #getBorder
   * @see #setBorder
   */
  public static final Property border = newProperty(0, BBorder.none, null);

  /**
   * Get the {@code border} property.
   * Border to use.
   * @see #border
   */
  public BBorder getBorder() { return (BBorder)get(border); }

  /**
   * Set the {@code border} property.
   * Border to use.
   * @see #border
   */
  public void setBorder(BBorder v) { set(border, v, null); }

  //endregion Property "border"

  //region Property "fill"

  /**
   * Slot for the {@code fill} property.
   * Color to fill background. Fill only affects the
   * area of the widget inside the margin.  Use
   * BBrush.NULL to leave background transparent.
   * @see #getFill
   * @see #setFill
   */
  public static final Property fill = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code fill} property.
   * Color to fill background. Fill only affects the
   * area of the widget inside the margin.  Use
   * BBrush.NULL to leave background transparent.
   * @see #fill
   */
  public BBrush getFill() { return (BBrush)get(fill); }

  /**
   * Set the {@code fill} property.
   * Color to fill background. Fill only affects the
   * area of the widget inside the margin.  Use
   * BBrush.NULL to leave background transparent.
   * @see #fill
   */
  public void setFill(BBrush v) { set(fill, v, null); }

  //endregion Property "fill"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBorderPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for {@code BBorderPane(content,
   * new BLabel(label))}.
   */
  public BBorderPane(BWidget content, String label)
  {
    this(content, new BLabel(label, Theme.widget().getBoldText()));      
  }
  
  /**
   * Convenience for {@code BBorderPane(content, label,
   * BBorder.make(BBorder.GROOVE))}.
   */
  public BBorderPane(BWidget content, BLabel label)
  {
    this(content, label, BBorder.make(BBorder.GROOVE));
  } 
  
  /**
   * Set content, label, and border style.
   */
  public BBorderPane(BWidget content, BLabel label, BBorder border)
  {
    setContent(content);
    setLabel(label);
    setBorder(border);
  }
  
  /**
   * Set the content and border style.
   */
  public BBorderPane(BWidget content, BBorder border)
  {
    setContent(content);
    setBorder(border);
  }

  /**
   * Set the content and border style and padding.
   */
  public BBorderPane(BWidget content, BBorder border, BInsets padding)
  {
    setContent(content);
    setBorder(border);
    setPadding(padding);
  }
  
  /**
   * Set the content and padding.
   */
  public BBorderPane(BWidget content, double top, double right, double bottom, double left)
  {
    setContent(content);
    setPadding(BInsets.make(top,right,bottom,left));
  }

  /**
   * Set the content and padding.
   */
  public BBorderPane(BWidget content, BInsets padding)
  {
    setContent(content);
    setPadding(padding);
  }
  
  /**
   * Set the content.
   */
  public BBorderPane(BWidget content)
  {
    setContent(content);
  }

  /**
   * No argument constructor.
   */
  public BBorderPane()
  {
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    BWidget c = getContent();
    c.computePreferredSize();
    
    BWidget l = getLabel();
    l.computePreferredSize();
    
    BInsets m = getMargin();
    BInsets p = getPadding();
    BBorder b = getBorder();
    
    double pw = 
      Math.max(c.getPreferredWidth(), l.getPreferredWidth()) + 
      m.left + m.right +
      p.left + p.right  + 
      b.leftWidth + b.rightWidth;
      
    double ph = 
      c.getPreferredHeight() + (l.getPreferredHeight()/2) +
      m.top + m.bottom +
      p.top + p.bottom +
      b.topWidth + b.bottomWidth;
      
    setPreferredSize(pw, ph);      
  }

  /**
   * Get the total width and height taken by the border which is not
   * included in the content.
   *
   * @since Niagara 4.6
   */
  public BInsets getAccumulatedBorder()
  {
    BWidget l = getLabel();
    l.computePreferredSize();
    BInsets m = getMargin();
    BInsets p = getPadding();
    BBorder b = getBorder();

    return BInsets.make(
      l.getPreferredHeight() / 2 + m.top + p.top + b.topWidth,
      m.right + p.right + b.rightWidth,
      m.bottom + p.bottom + b.bottomWidth,
      m.left + p.left + b.leftWidth
    );
  }

  public void doLayout(BWidget[] children)
  {
    double w = getWidth();
    double h = getHeight();
    double y = 0;
    
    BInsets m = getMargin();
    BInsets p = getPadding();
    BBorder b = getBorder();
    
    BWidget label = getLabel();
    if (!label.isNull())
    {
      label.computePreferredSize();
      h -= label.getPreferredHeight()/2;
      y += label.getPreferredHeight()/2;
      label.setBounds(5+m.left,m.top,label.getPreferredWidth(),label.getPreferredHeight());
    }
    
    double cx = m.left + p.left + b.leftWidth;
    double cy = m.top + p.top + b.topWidth + y;
    double cw = w - 
      (m.left + m.right) - 
      (p.left + p.right) - 
      (b.leftWidth + b.rightWidth);
    double ch = h -
      (m.top + m.bottom) -
      (p.top + p.bottom) -
      (b.topWidth + b.bottomWidth);
    
    double bx = m.left;
    double by = m.top + y;
    double bw = w - (m.left + m.right);
    double bh = h - (m.top + m.bottom);
    
    if (b.leftWidth   > 0) { bx++; bw--; }
    if (b.topWidth    > 0) { by++; bh--; }
    if (b.rightWidth  > 0) { bw--; }
    if (b.bottomWidth > 0) { bh--; }
    
    getContent().setBounds(cx,cy,cw,ch);
    background.set(bx,by,bw,bh);
  }

////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    RectGeom r = background;

    // Fill
    BBrush fill = getFill();
    if (!fill.isNull())
    {
      g.setBrush(fill);
      g.fill(background);
    }

    //Paint separator lines if embedded in BTitlePane
    if(getStyleClasses().equals("title"))
    {
      //label underline
      g.setPen(BPen.make(3.5));
      g.setBrush(Theme.toolPane().getControlHighlight(this));
      BLabel label = (BLabel)((BEdgePane)getContent()).getLeft();
      BFont font = !label.getFont().isNull() ? label.getFont() : Theme.label().getTextFont(label);
      double labelW = font.width(label.getText()) + 20;
      g.strokeLine(r.x, r.y + r.height, r.x + labelW, r.y + r.height);

      //fill separator region
      g.setBrush(Theme.toolPane().getControlForeground(this));
      g.fillRect(r.x + labelW, r.y, r.width - r.x - labelW, r.height);

      //horizontal and vertical separator lines
      g.setBrush(Theme.toolPane().getControlShadow(this));

      //horizontal line
      g.strokeLine(r.x + labelW, r.y + r.height, r.x + r.width, r.y + r.height);

      g.setPen(BPen.make(1.5));

      //vertical line
      g.strokeLine(r.x + labelW, r.y + r.height, r.x + labelW, r.y);

    }

    // Content
    paintChild(g, getContent());

    BWidget w = getLabel();

    // Border
    if (w.isNull())
      getBorder().paint(g, r.x, r.y, r.width, r.height);
    else
      getBorder().paint(g, r.x, r.y, r.width, r.height, w.getX(), w.getWidth());
      
    // Label
    if (!getLabel().isNull()) 
      paintChild(g, getLabel());  
  }

  @Override
  public String getStyleSelector()
  {
    return "pane border-pane";
  }

  ///////////////////////////////////////////////////////////
// Overrides
///////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/borderPane.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private RectGeom background = new RectGeom();
}
