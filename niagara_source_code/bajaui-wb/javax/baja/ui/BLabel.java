/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BAlign;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.ui.theme.LabelTheme;
import com.tridium.ui.theme.Theme;

/**
 * BLabel is used to display text and/or an image.  BLabels
 * are always transparent, so their background is defined
 * by their parent.  Their preferred size is always an exact
 * fit to contain the text and/or image.  BLabels may be used
 * by themselves to display information which cannot respond
 * to user input, or they may be embedded in widgets such as
 * BButtons.
 *
 * @author    Brian Frank
 * @creation  1 Dec 00
 * @version   $Revision: 74$ $Date: 6/30/11 11:38:13 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Text to display on the label, or "" if no
 text should be rendered for this label.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = "BString.DEFAULT"
)
/*
 Image to display on the label.
 */
@NiagaraProperty(
  name = "image",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Font defines the font to use to render the label's
 text.  If it is set to BFont.NULL then a context
 sensitive default fallback will be used.
 */
@NiagaraProperty(
  name = "font",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
/*
 Brush to use to render the label's text.  If set
 to BBrush.NULL then a context sensitive default
 fallback will be used.
 */
@NiagaraProperty(
  name = "foreground",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Brush to fill background of label. Use BBrush.NULL
 to leave background transparent.
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 If the label is sized larger than its preferred
 size, this defines whether the text and/or image
 should be justified left, center, or to the
 right of the label's actual size.
 */
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.center"
)
/*
 If the label is sized larger than its preferred
 size, this defines whether the text and/or image
 should be justified top, center, or on the
 bottom the label's actual size.
 */
@NiagaraProperty(
  name = "valign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 This specifies the alignment of the label's
 text to the image if the label supports both.
 */
@NiagaraProperty(
  name = "textToIconAlign",
  type = "BAlign",
  defaultValue = "BAlign.right"
)
/*
 Number of pixels between the text and image
 if this label supports both.
 */
@NiagaraProperty(
  name = "textIconGap",
  type = "double",
  defaultValue = "4"
)
/*
 If blink it true then the label is flashed
 on and off to produce a blinking effect.
 */
@NiagaraProperty(
  name = "blink",
  type = "boolean",
  defaultValue = "false"
)
/*
 enables word wrapping on white space
 to force wrapping, call setSize with max width
 */
@NiagaraProperty(
  name = "wordWrapEnabled",
  type = "boolean",
  defaultValue = "false"
)
/*
 This topic fires a BWidgetEvent whenever the widget is clicked.
 @since Niagara 4.13
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public class BLabel
  extends BWidget
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BLabel(2379953772)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * Text to display on the label, or "" if no
   * text should be rendered for this label.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code text} property.
   * Text to display on the label, or "" if no
   * text should be rendered for this label.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * Text to display on the label, or "" if no
   * text should be rendered for this label.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "image"

  /**
   * Slot for the {@code image} property.
   * Image to display on the label.
   * @see #getImage
   * @see #setImage
   */
  public static final Property image = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code image} property.
   * Image to display on the label.
   * @see #image
   */
  public BImage getImage() { return (BImage)get(image); }

  /**
   * Set the {@code image} property.
   * Image to display on the label.
   * @see #image
   */
  public void setImage(BImage v) { set(image, v, null); }

  //endregion Property "image"

  //region Property "font"

  /**
   * Slot for the {@code font} property.
   * Font defines the font to use to render the label's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #getFont
   * @see #setFont
   */
  public static final Property font = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code font} property.
   * Font defines the font to use to render the label's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #font
   */
  public BFont getFont() { return (BFont)get(font); }

  /**
   * Set the {@code font} property.
   * Font defines the font to use to render the label's
   * text.  If it is set to BFont.NULL then a context
   * sensitive default fallback will be used.
   * @see #font
   */
  public void setFont(BFont v) { set(font, v, null); }

  //endregion Property "font"

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * Brush to use to render the label's text.  If set
   * to BBrush.NULL then a context sensitive default
   * fallback will be used.
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code foreground} property.
   * Brush to use to render the label's text.  If set
   * to BBrush.NULL then a context sensitive default
   * fallback will be used.
   * @see #foreground
   */
  public BBrush getForeground() { return (BBrush)get(foreground); }

  /**
   * Set the {@code foreground} property.
   * Brush to use to render the label's text.  If set
   * to BBrush.NULL then a context sensitive default
   * fallback will be used.
   * @see #foreground
   */
  public void setForeground(BBrush v) { set(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * Brush to fill background of label. Use BBrush.NULL
   * to leave background transparent.
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code background} property.
   * Brush to fill background of label. Use BBrush.NULL
   * to leave background transparent.
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * Brush to fill background of label. Use BBrush.NULL
   * to leave background transparent.
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Property "halign"

  /**
   * Slot for the {@code halign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified left, center, or to the
   * right of the label's actual size.
   * @see #getHalign
   * @see #setHalign
   */
  public static final Property halign = newProperty(0, BHalign.center, null);

  /**
   * Get the {@code halign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified left, center, or to the
   * right of the label's actual size.
   * @see #halign
   */
  public BHalign getHalign() { return (BHalign)get(halign); }

  /**
   * Set the {@code halign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified left, center, or to the
   * right of the label's actual size.
   * @see #halign
   */
  public void setHalign(BHalign v) { set(halign, v, null); }

  //endregion Property "halign"

  //region Property "valign"

  /**
   * Slot for the {@code valign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified top, center, or on the
   * bottom the label's actual size.
   * @see #getValign
   * @see #setValign
   */
  public static final Property valign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code valign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified top, center, or on the
   * bottom the label's actual size.
   * @see #valign
   */
  public BValign getValign() { return (BValign)get(valign); }

  /**
   * Set the {@code valign} property.
   * If the label is sized larger than its preferred
   * size, this defines whether the text and/or image
   * should be justified top, center, or on the
   * bottom the label's actual size.
   * @see #valign
   */
  public void setValign(BValign v) { set(valign, v, null); }

  //endregion Property "valign"

  //region Property "textToIconAlign"

  /**
   * Slot for the {@code textToIconAlign} property.
   * This specifies the alignment of the label's
   * text to the image if the label supports both.
   * @see #getTextToIconAlign
   * @see #setTextToIconAlign
   */
  public static final Property textToIconAlign = newProperty(0, BAlign.right, null);

  /**
   * Get the {@code textToIconAlign} property.
   * This specifies the alignment of the label's
   * text to the image if the label supports both.
   * @see #textToIconAlign
   */
  public BAlign getTextToIconAlign() { return (BAlign)get(textToIconAlign); }

  /**
   * Set the {@code textToIconAlign} property.
   * This specifies the alignment of the label's
   * text to the image if the label supports both.
   * @see #textToIconAlign
   */
  public void setTextToIconAlign(BAlign v) { set(textToIconAlign, v, null); }

  //endregion Property "textToIconAlign"

  //region Property "textIconGap"

  /**
   * Slot for the {@code textIconGap} property.
   * Number of pixels between the text and image
   * if this label supports both.
   * @see #getTextIconGap
   * @see #setTextIconGap
   */
  public static final Property textIconGap = newProperty(0, 4, null);

  /**
   * Get the {@code textIconGap} property.
   * Number of pixels between the text and image
   * if this label supports both.
   * @see #textIconGap
   */
  public double getTextIconGap() { return getDouble(textIconGap); }

  /**
   * Set the {@code textIconGap} property.
   * Number of pixels between the text and image
   * if this label supports both.
   * @see #textIconGap
   */
  public void setTextIconGap(double v) { setDouble(textIconGap, v, null); }

  //endregion Property "textIconGap"

  //region Property "blink"

  /**
   * Slot for the {@code blink} property.
   * If blink it true then the label is flashed
   * on and off to produce a blinking effect.
   * @see #getBlink
   * @see #setBlink
   */
  public static final Property blink = newProperty(0, false, null);

  /**
   * Get the {@code blink} property.
   * If blink it true then the label is flashed
   * on and off to produce a blinking effect.
   * @see #blink
   */
  public boolean getBlink() { return getBoolean(blink); }

  /**
   * Set the {@code blink} property.
   * If blink it true then the label is flashed
   * on and off to produce a blinking effect.
   * @see #blink
   */
  public void setBlink(boolean v) { setBoolean(blink, v, null); }

  //endregion Property "blink"

  //region Property "wordWrapEnabled"

  /**
   * Slot for the {@code wordWrapEnabled} property.
   * enables word wrapping on white space
   * to force wrapping, call setSize with max width
   * @see #getWordWrapEnabled
   * @see #setWordWrapEnabled
   */
  public static final Property wordWrapEnabled = newProperty(0, false, null);

  /**
   * Get the {@code wordWrapEnabled} property.
   * enables word wrapping on white space
   * to force wrapping, call setSize with max width
   * @see #wordWrapEnabled
   */
  public boolean getWordWrapEnabled() { return getBoolean(wordWrapEnabled); }

  /**
   * Set the {@code wordWrapEnabled} property.
   * enables word wrapping on white space
   * to force wrapping, call setSize with max width
   * @see #wordWrapEnabled
   */
  public void setWordWrapEnabled(boolean v) { setBoolean(wordWrapEnabled, v, null); }

  //endregion Property "wordWrapEnabled"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Label constructor with image and text.
   */
  public BLabel(BImage image, String text)
  {
    this(text, image, null, null, null);
  }

  /**
   * Label constructor with specified text.
   */
  public BLabel(String text)
  {
    this(text, null, null, null, null);
  }

  /**
   * Label constructor with specified image.
   */
  public BLabel(BImage image)
  {
    this(null, image, null, null, null);
  }

  /**
   * Label constructor with specified text and horizontal alignment.
   */
  public BLabel(String text, BHalign hAlign)
  {
    this(text, null, null, hAlign, null);
  }

  /**
   * Label constructor with specified text and vertical alignment.
   */
  public BLabel(String text, BValign vAlign)
  {
    this(text, null, null, null, vAlign);
  }

  /**
   * Label constructor with specified text and font.
   */
  public BLabel(String text, BFont font)
  {
    this(text, null, font, null, null);
  }

  /**
   * Label constructor with specified image, text, and font.
   */
  public BLabel(BImage image, String text, BFont font)
  {
    this(text, image, font, null, null);
  }

  /**
   * Label constructor with specified image and horizontal alignment.
   */
  public BLabel(BImage image, BHalign hAlign)
  {
    this(null, image, null, hAlign, null);
  }

  /**
   * Label constructor with specified image and vertical alignment.
   */
  public BLabel(BImage image, BValign vAlign)
  {
    this(null, image, null, null, vAlign);
  }

  /**
   * Label constructor with specified text, font, and horizontal alignment.
   */
  public BLabel(String text, BFont font, BHalign hAlign)
  {
    this(text, null, font, hAlign, null);
  }

  /**
   * Constructor to check all fields for null before attempting to use the property.
   */
  private BLabel(String text, BImage image, BFont font, BHalign hAlign, BValign vAlign)
  {
    if (text != null) setText(text);
    if (image != null) setImage(image);
    if (font != null) setFont(font);
    if (hAlign != null) setHalign(hAlign);
    if (vAlign != null) setValign(vAlign);
  }

  /**
   * No argument constructor.
   */
  public BLabel()
  {
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void setWordWrapEnabled(boolean enabled, double maxWidth)
  {
    setWordWrapEnabled(enabled);
    setSize(maxWidth, this.getHeight());
  }
  
  /**
   * Compute the preferred size of the label.
   */
  public void computePreferredSize()
  {
    // use a temp Layout so that this method is thread safe
    Layout temp = createLayout();
    temp.computeBounds();

    BInsets padding = getPadding();
    temp.aw += padding.left + padding.right;
    temp.ah += padding.top + padding.bottom;

    setPreferredSize(temp.aw, temp.ah);
  }

  /**
   * Compute the layout information for the label.
   */
  public void doLayout(BWidget[] kids)
  {
    forceLayout = false;

    double w = getWidth();
    double h = getHeight();

    BInsets padding = getPadding();
    w -= padding.left + padding.right;
    h -= padding.top + padding.bottom;

    layout.lines = null;
    layout.computeBounds();
    layout.computeAlignment(w,h);

    layout.xo += padding.left;
    layout.yo += padding.top;
  }

  public void changed(Property prop, Context context)
  {
    if (prop.equals(text))
    {
      forceLayout = true;
      relayout();
    }
    else if (prop.equals(styleClasses))
    {
      setImage(Theme.label().getIcon(this));
    }
    super.changed(prop, context);
  }

  /**
   * Get the padding between the widget bounds
   * and the label bounds.
   */
  public BInsets getPadding()
  {
    return padding;
  }

  /**
   * Explicitaly set the padding between the widget
   * bounds and the label bounds.
   */
  public void setPadding(BInsets padding)
  {
    this.padding = padding;
  }

////////////////////////////////////////////////////////////////
// Paint
////////////////////////////////////////////////////////////////

  /**
   * Get the Label Layout that provides all text and image positions.
   */
  public Layout getLabelLayout(){
    return this.layout;
  }

  /**
   * Paint the label.
   */
  public void paint(Graphics g)
  {
    // if blinking and this is an
    // offcycle then skip the paint
    if (getBlink() && blinkFrame <= 1) return;

    // we need this hook to really make sure we have done a
    // layout since we rely on layout to parse the text (this
    // probably deserved a more public solution since the
    // real problem is that relayout() short circuits if in
    // the middle of a layout)
    if (forceLayout)
    {
      forceLayout = false;
      relayoutSync();
    }
    
    LabelTheme theme = theme();

    // get layout data
    Layout q = this.layout;
    double tx = q.tx, ty = q.ty, tw = q.tw, th = q.th;
    double ix = q.ix, iy = q.iy;
    double xo = q.xo, yo = q.yo;

    // font
    BFont font = getFont();
    if (font.isNull()) font = theme().getTextFont(this);

    // paint background of text
    BBrush background = getBackground();
    paintBackground(g, background);

    // paint image
    BImage image = theme.getIcon(this);
    if (layout.ipaint && !image.isNull())
    {
      if (!isEnabled()) image = image.getDisabledImage();
      paintIcon(g, image, ix+xo, iy+yo);
    }

    // paint text last
    String text = getText();
    if (text.length() > 0 && q.lines != null)
    {
      BBrush brush = getForeground();
      if (brush.isNull()) brush = theme.getTextBrush(this);
      g.setBrush(brush);
      g.setFont(font);

      BHalign ha = getHalign();
      for (int i=0; i<q.lines.length; i++)
      {
        double ax = 0;
        if (ha == BHalign.center) ax = (tw - q.widths[i]) / 2;
        else if (ha == BHalign.right) ax = tw - q.widths[i];

        if (isEnabled())
          paintText(g, q.lines[i], tx+xo+ax, ty+yo+(i*q.lineHeight));
        else
          theme.paintDisabledText(g, this, q.lines[i], tx+xo+ax, ty+yo+(i*q.lineHeight));
      }
    }
  }

  /**
   * Paint the image.
   */
  protected void paintIcon(Graphics g, BImage image, double x, double y)
  {
    g.drawImage(image, x, y);
  }

  /**
   * Paint the background.
   */
  protected void paintBackground(Graphics g, BBrush background)
  {
    if (!background.isNull() && !(this instanceof BAbstractButton))
    {
      g.setBrush(background);
      g.fillRect(0,0,getWidth(),getHeight());
    }
  }
  
  
  /**
   * Paint the label's text.
   */
  protected void paintText(Graphics g, String text, double tx, double ty)
  {
    g.push();
    try
    {
      g.useFractionalFontMetrics(true);
      g.drawString(text, tx, ty);
    }
    finally
    {
      g.pop();
    }
  }

  /**
   * Animate the image if supported.
   */
  public void animate()
  {
    // check image
    BImage image = getImage();
    if (!image.isNull())
    {
      // if the image has been loaded since our last layout,
      // then call relayout; although we immediately reset the
      // iloaded flag because we can't count on doLayout being
      // called by all panes and we certainly don't want to get
      // stuck in a infinite relayout loop (we will still skip
      // paints with the ipaint flag until we finish layout)
      if (!layout.iloaded && image.isLoaded())
      {
        layout.iloaded = true;
        image.syncDimensions();
        relayout();
      }

      // if the image is animated and needs a repaint
      if (image.animate())
        repaint();
    }

    // if blinking
    if (getBlink())
    {
      blinkFrame = (blinkFrame+1) % 8;
      if (blinkFrame == 0 || blinkFrame == 2) repaint();
    }
  }
  
  public String getStyleSelector() { return "label"; }

  @Override
  public void mousePressed(BMouseEvent event)
  {
    fireActionPerformed(event);
  }

////////////////////////////////////////////////////////////////
// Theme
////////////////////////////////////////////////////////////////

  /**
   * Get our theme.
   */
  LabelTheme theme()
  {
    return Theme.label();
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return getImage().isNull()?icon:imageIcon; }
  private static final BIcon icon = BIcon.std("widgets/label.png");
  private static final BIcon imageIcon = BIcon.std("widgets/image.png");

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  /**
   * Labels do not receive input events
   * unless hasBindings() returns true.
   */
  public boolean receiveInputEvents()
  {
    return hasBindings();
  }

////////////////////////////////////////////////////////////////
// Debugging
////////////////////////////////////////////////////////////////

  public String getDebugString()
  {
    return "\"" + getText() + "\"; ";
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Layout provides all the code used to
   * compute text and image positions.
   */
  public class Layout
  {
    /**
     * Compute xo,yo offsets based on aggregate width
     */
    void computeAlignment(double w, double h)
    {
      switch(getHalign().getOrdinal())
      {
        case BHalign.LEFT:   xo = 0; break;
        case BHalign.CENTER: xo = (w- aw)/2; break;
        case BHalign.RIGHT:  xo = w - aw; break;
        case BHalign.FILL:   xo = (w- aw)/2; break;
      }

      switch(getValign().getOrdinal())
      {
        case BValign.TOP:    yo = 0; break;
        case BValign.CENTER: yo = (h- ah)/2; break;
        case BValign.BOTTOM: yo = h - ah; break;
        case BValign.FILL:   yo = (h- ah)/2; break;
      }
    }

    /**
     * Compute the text, image, and aggregate bounds.
     */
    void computeBounds()
    {
      // first compute the bounds text and image as
      // if they we top-left aligned by themselves
      computeImageBounds();
      computeTextBounds();

      // now if both text and image are
      // displayed we need to compound them
      if (tw > 0 && iw > 0)
        computeCompoundLayout();
      else
        { aw = Math.max(tw, iw); ah = Math.max(th, ih); }
    }

    /**
     * Compute the dimensions of the text top-left
     * aligned by itself.
     */
    private void computeTextBounds()
    {
      BFont font = getFont();

      if (font.isNull())
      {
        font = theme().getTextFont(BLabel.this);
      }

      double fontHeight = font.getHeight();
      double fontDescent = font.getDescent();
      lineHeight = fontHeight;
      layoutFont = font;

      String text = getText();
      if (text.length() == 0) { tw = 0; th = 0; return; }

      if (lines == null)
      {
        parse();
        widths = new double[lines.length];
      }

      tx = 0;
      ty = fontHeight - fontDescent;
      tw = 0;
      th = lineHeight * lines.length;

      for (int i=0; i<lines.length; i++)
      {
        widths[i] = font.fractionalWidth(lines[i]);
        tw = Math.max(tw, widths[i]);
      }

      // ensure the text bounds fall exactly on a pixel boundary
      tw = Math.ceil(tw);

      requiresMultipleLines = lines.length > 1;
    }

    /**
     * Return true if the last time computeTextBounds required the text to use multiple lines.
     * @since Niagara 4.10
     */
    public boolean requiresMultipleLines()
    {
      return requiresMultipleLines;
    }

    /**
     * Compute the bounds of the image top-left
     * aligned by itself.
     */
    private void computeImageBounds()
    {
      BImage image = getImage();
      ix = iy = 0;

      if (image.isNull() || !image.isDimensionsLoaded())
      {
        image.syncDimensions();
        iloaded = ipaint = false;
      }
      else
      {
        iloaded = ipaint = true;
        iw = image.getWidth();
        ih = image.getHeight();
      }
    }

    /**
     * Given our text and image bounds as if both
     * we top-left aligned by themselves, compute
     * their compound layout as if top-left aligned.
     */
    private void computeCompoundLayout()
    {
      double gap = getTextIconGap();
      aw = Math.max(tw, iw);
      ah = Math.max(th, ih);
      switch(getTextToIconAlign().getOrdinal())
      {
        case BAlign.TOP:
          tx += (aw-tw)/2;
          ix = (aw-iw)/2;
          iy = th + gap;
          ah = th + gap + ih;
          break;
        case BAlign.BOTTOM:
          tx += (aw-tw)/2;
          ix = (aw-iw)/2;
          ty += ih + gap;
          ah = th + gap + ih;
          break;
        case BAlign.LEFT:
          ty += (ah-th)/2;
          iy = (ah-ih)/2;
          ix = tw + gap;
          aw = tw + gap + iw;
          break;
        case BAlign.RIGHT:
          ty += (ah-th)/2;
          iy = (ah-ih)/2;
          tx += iw + gap;
          aw = tw + gap + iw;
          break;
        case BAlign.CENTER:
          tx += (aw-tw)/2;
          ty += (ah-th)/2;
          ix = (aw-iw)/2;
          iy = (ah-ih)/2;
          break;
      }
    }

//    protected void parse()
//    {
//      String text = getText();
//      if (text.length() == 0) { lines = new String[0]; return; }
//
//      StringTokenizer st = new StringTokenizer(text, "\n\r");
//      Vector temp = new Vector(2);
//      while(st.hasMoreTokens())
//        temp.addElement(st.nextToken());
//      lines = new String[temp.size()];
//      temp.copyInto(lines);
//    }
    
    /**
     * Parse the text into a set of lines.
     */
    protected void parse()
    {
      BFont font = getFont();
      if (font.isNull()) font = theme().getTextFont(BLabel.this);
      double w = getWidth();
      boolean causedOverflow = false;
      
      if (iw > 0)
      {
        w = w - getTextIconGap() - iw;
      }
      
      ArrayList<String> array = new ArrayList<>();
      
      StringTokenizer st;
      if (getWordWrapEnabled())
        st = new StringTokenizer(getText(), ALL_DELIM, true);
      else
        st = new StringTokenizer(getText(), HARD_DELIM, true);

      StringBuilder running = new StringBuilder();
      StringBuilder last = new StringBuilder();
      
      while (st.hasMoreTokens())
      {
        String next = st.nextToken();
        if (getWordWrapEnabled() && isSoftDelim(next))
        {
          last.append(next);
        }
        else if (isHardDelim(next))
        {
          if (!causedOverflow) {
            array.add(running.toString());
            last.setLength(0);
            running.setLength(0);
          }
        }
        else
        {
          String widthArg = running.toString() + last + next;
          double currentWidth = font.width(widthArg);
          if (w > 0 && currentWidth > w)
          {
            causedOverflow = true;
            if (running.length() > 0)
              array.add(running.toString());
            running.setLength(0);
            double nextWidth = font.width(next);
            if (nextWidth > w)
              array.add(next);
            else
              if (next.length() > 0)
                running.append(next);
          }
          else
          {
            causedOverflow = false;
            running.append(last).append(next);
          }
          last.setLength(0);
        }
      }
      if (running.length() > 0)
        array.add(running.toString());
      
      lines = array.toArray(new String[0]);
    }
    
    private boolean isSoftDelim(String text)
    {
      if (SOFT_DELIM.indexOf(text) >= 0) return true;
      return false;
    }

    private boolean isHardDelim(String text)
    {
      if (HARD_DELIM.indexOf(text) >= 0) return true;
      return false;
    }

    void dump()
    {
      System.out.println("  text:  " + tx + "," + ty + "," + tw + "," + th);
      System.out.println("  image: " + ix + "," + iy + "," + iw + "," + ih);
      System.out.println("  aggr:  " + aw + "," + ah);
      System.out.println("  off:   " + xo + "," + yo);
      System.out.println("  lineHt:" + lineHeight);
      System.out.println("  font:  " + font);
    }

    /**
     * Get the current RectGeom
     * @return
     */
    public RectGeom getTextGeom(){
      return new RectGeom(tx, ty, tw, th);
    }

    /**
     * Get the current Image RectGeom
     * @return
     */
    public RectGeom getImageGeom(){
      return new RectGeom(ix, iy, iw, ih);
    }

    /**
     * Get the x and y offsets for alignment
     * @return
     */
    public Point getAlignmentOffsets(){
      return new Point(xo, yo);
    }

    /**
     * Get the height of one line
     * @return
     */
    public double getLineHeight(){
      return lineHeight;
    }

    /**
     * Get the font used for Layout. If the Label call to getFont isNull, this Font will be the font used once
     * the theme determines which font to use.
     * @return
     */
    public BFont getLayoutFont(){
      return layoutFont;
    }


    BFont layoutFont = BFont.DEFAULT;
    boolean valid;
    double tx, ty, tw, th;  // text rectangle (top-left aligned)
    double ix, iy, iw, ih;  // image rectangle (top-left aligned)
    double aw, ah;          // aggregate width and height
    double xo = 0, yo = 0;          // x and y offsets for alignment
    boolean ipaint;         // should we paint the image
    boolean iloaded;        // has the image been loaded
    double  lineHeight;     // height of one line
    String[] lines = null;  // text parsed into lines
    double[] widths = null; // line widths
    boolean requiresMultipleLines;

    public static final String SOFT_DELIM = " \t";
    public static final String HARD_DELIM = "\n\r\f"; 
    public static final String ALL_DELIM = SOFT_DELIM + HARD_DELIM;
  }

  protected Layout createLayout()
  {
    return new Layout();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BInsets padding = BInsets.NULL;
  Layout layout = createLayout();
  boolean forceLayout;
  int blinkFrame = 2;   // 0-1 off, 2-7 on

  private static final double METRIC_MULTIPLIER = 100;
}
