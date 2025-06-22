/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.file.BExporter;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nav.BINavNode;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BDouble;
import javax.baja.ui.BBorder;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BAlign;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.web.UserAgent;
import javax.baja.xml.XWriter;

import org.owasp.encoder.Encode;
import com.tridium.hx.HxHyperlinkInfo;
import com.tridium.nre.ConsumerWithException;
import com.tridium.session.NiagaraSuperSession;
import com.tridium.session.SessionManager;
import com.tridium.ui.theme.Theme;
import com.tridium.web.WebUtil;
import com.tridium.web.servlets.ViewAllOrdServlet;

/**
 * HxUtil.
 *
 * @author    Andy Frank 4 Jan 05
 * @version   $Revision: 40$ $Date: 7/19/10 4:36:53 PM EDT$
 * @since     Baja 1.0
 */
public class HxUtil
{
////////////////////////////////////////////////////////////////
// Views
////////////////////////////////////////////////////////////////

  /**
   * Get the default view for this context.
   */
  public static AgentInfo getDefaultView(HxOp op)
  {
    try
    {
      return op.getWebEnv().getDefaultView(op, getViews(op));
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the list of views available for this context.
   */
  public static AgentList getViews(HxOp op)
  {
    return HxHyperlinkInfo.getViews(op);
  }

  /**
   * Return false is the ViewSelector is a BExporter
   * @param info AgentInfo that may appear in the view selector dropdown
   * @return true if the view should be included
   */
  private static boolean isValidForViewSelector(AgentInfo info)
  {
    return !info.getAgentType().is(BExporter.TYPE);
  }

  /**
   * Get the list of views available for use with a View Selector. This call
   * removes any entries that would be duplicates.
   * @since Niagara 4.4
   */
  public static AgentList getViewsForViewSelector(HxOp op)
    throws Exception
  {
    AgentList agents = getViews(op);
    ViewAllOrdServlet.removeDuplicateAgents(op, agents);
    agents = agents.filter(info -> isValidForViewSelector(info));
    return agents;
  }

////////////////////////////////////////////////////////////////
// GX
////////////////////////////////////////////////////////////////

  /**
   * Encode to an HTML-friendly font that can be used for the value of the font style attribute:
   * For example the return String for a bold italic 14 px Source Sans Pro Font: "bold italic 14px Source Sans Pro"
   */
  public static String makeFont(BFont font)
  {
    String name = getCSSFontName(font);
    double size = font.getSize();
    int style   = font.getStyle();

    StringBuilder s = new StringBuilder();
    if ((style & BFont.BOLD) != 0) s.append("bold ");
    if ((style & BFont.ITALIC) != 0) s.append("italic ");
    s.append(BDouble.encode(size)).append("px ");
    s.append(name);
    return s.toString();
  }

  /**
   * @param font a font to be used in an Hx graphic
   * @return a CSS-safe font name that can be applied as a font-family attribute
   * @since Niagara 4.8
   */
  public static String getCSSFontName(BFont font)
  {
    String fontName = font.getName();
    if ("sansserif".equalsIgnoreCase(fontName)) { return "sans-serif"; }
    if ("monospaced".equalsIgnoreCase(fontName)) { return "monospace"; }
    return fontName;
  }

  /**
   * Get the color from a brush, provide a BColor default value when Brush is null
   */
  public static BColor makeColor(BBrush brush, BColor def)
    throws Exception
  {
    if (brush.isNull()) return def;
    if (brush.getPaint() instanceof BBrush.Solid)
    {
      BColor c = ((BBrush.Solid)brush.getPaint()).getColor();
      return c;
    }
    if (brush.getPaint() instanceof BBrush.Gradient)
    {
      // Average color
      BBrush.Stop[] stops = ((BBrush.Gradient)brush.getPaint()).getStops();
      int red = 0;
      int blue = 0;
      int green = 0;

      for(int stop = 0; stop < stops.length; stop++)
      {
        BColor color = stops[stop].getColor();
        red += color.getRed();
        blue += color.getBlue();
        green += color.getGreen();
      }

      return BColor.make(
          red/stops.length,
          green/stops.length,
          blue/stops.length);
    }
    return def;
  }

  /**
   * Get the color from a brush, provide a String default value when Brush is null
   */
  public static String makeColor(BBrush brush, String def)
    throws Exception
  {
    BColor color = makeColor(brush, BColor.NULL);
    if(color.equals(BColor.NULL))
      return def;
    else
      return color.toHtmlStringWithAlpha();
  }

  /**
   * Encode a BInsets to CSS-style (margin/padding).
   */
  public static String makeInsets(BInsets insets)
    throws Exception
  {
    StringBuilder s = new StringBuilder();
    s.append(insets.top).append("px ");
    s.append(insets.right).append("px ");
    s.append(insets.bottom).append("px ");
    s.append(insets.left).append("px");
    return s.toString();
  }

  /**
   * Encode a BBorder to CSS-style.
   */
  public static String makeBorder(BBorder border)
    throws Exception
  {
    // TODO - optimize when all equal

    BBorder b = border;
    StringBuilder s = new StringBuilder();
    s.append("border-top:");
    makeBorder(b.topWidth, b.topStyle, b.topBrush, s);
    s.append("border-left:");
    makeBorder(b.leftWidth, b.leftStyle, b.leftBrush, s);
    s.append("border-right:");
    makeBorder(b.rightWidth, b.rightStyle, b.rightBrush, s);
    s.append("border-bottom:");
    makeBorder(b.bottomWidth, b.bottomStyle, b.bottomBrush, s);
    return s.toString();
  }

  /**
   * makeBorder() support.
   */
  private static void makeBorder(double width, int style, BBrush brush, StringBuilder s)
    throws Exception
  {
    // Width
    s.append(width).append("px ");

    // Style
    switch (style)
    {
      case BBorder.NONE:
        s.append("none ");
        break;
      case BBorder.SOLID:
        s.append("solid ");
        break;
      case BBorder.DOTTED:
        s.append("dotted ");
        break;
      case BBorder.DASHED:
        s.append("dashed ");
        break;
      case BBorder.GROOVE:
        s.append("groove ");
        break;
      case BBorder.RIDGE:
        s.append("solid ");
        break;
      case BBorder.INSET:
        s.append("inset ");
        break;
      case BBorder.OUTSET:
        s.append("outset ");
        break;
    }

    // Color
    if (style == BBorder.INSET || style == BBorder.OUTSET)
      s.append("#ccc");
    else if (style == BBorder.RIDGE)
      s.append(((BBrush.Solid)Theme.widget().getControlHighlight().getPaint()).getColor().toHtmlString());
    else
      s.append(makeColor(brush, BColor.black).toHtmlString());

    // Semi-colon
    s.append(";");
  }


  /**
   * makeAlignment() support for separate BHalign and BValign
   *
   * @param ha
   * @param va
   * @param style Style for Dom Element
   */
  public static void makeAlignment(BHalign ha, BValign va, PropertiesCollection style)
  {
    if (ha == BHalign.center)
      style.add("textAlign", "center");
    else if (ha == BHalign.right)
      style.add("textAlign", "right");
    else if (ha != null)
      style.add("textAlign", "left");

    if (va == BValign.center)
      style.add("verticalAlign", "middle");
    else if (va == BValign.top)
      style.add("verticalAlign", "top");
    else if (va != null)
      style.add("verticalAlign", "bottom");
  }

  /**
   * makeAlignment() support for a BAlign
   *
   * @param align
   * @param style
   */
  public static void makeAlignment(BAlign align, PropertiesCollection style)
  {
    if (align == BAlign.center)
      style.add("textAlign", "center");
    else if (align == BAlign.right)
      style.add("textAlign", "right");
    else if(align !=null)
      style.add("textAlign", "left");

    if (align == BAlign.center)
      style.add("verticalAlign", "middle");
    else if (align == BAlign.bottom)
      style.add("verticalAlign", "bottom");
    else if (align != null)
      style.add("verticalAlign", "top");
  }

  /**
   * Make a font for an Html Element's style and properties PropertiesCollections.
   * The additional properties parameter is required because if the font is null, then the style is only used for the wrap and color. The properties PropertiesCollection is then used to apply a css className of
   *              "defaultNssFont" so that the default font can be taken from the theme. This allows Niagara 4 to better supports hx themes by not always using the dom style element. If deprecated makeFont calls are
   *              used or properties is null, the, the previous AX behavior will be used and fontStyle will  be ''none'.
   *
   * @param foreground The color for the font
   * @param font The font we want to use. If font.isNull() then defaultNssFont className will be given to the element so that theme.css can provide the font
   * @param style The PropertiesCollection for the style of a given html element.
   * @param properties The PropertiesCollection for the properties of the same html element.
   * @param wrap determines if the text for the styled html element wrap or not
   * @param op The HxOp is required because the behavior for foreground fonts is based on which browser is being used
   * @throws Exception
   * @since Niagara 4.2
   */
  public static void makeFont(BBrush foreground, BFont font, PropertiesCollection style, PropertiesCollection properties, boolean wrap, HxOp op)
    throws Exception
  {
    if(!wrap)
      style.add("whiteSpace", "nowrap");

    if(!foreground.isNull())
    {
      style.add("color", HxUtil.makeColor(foreground, BColor.black).toHtmlStringWithAlpha());
      if(foreground.getPaint() instanceof BBrush.Gradient)
      {
        String gradient = makeGradientString((BBrush.Gradient) foreground.getPaint());
        style.add("background", gradient);
        style.add("-webkit-background-clip", "text");
        style.add("-webkit-text-fill-color", "transparent");
      }
      else
      {
        style.add("background", "");
        style.add("-webkit-background-clip", "");
        style.add("-webkit-text-fill-color", "");
      }
    }
    else
    {
      //NCCB-15046: remove any inline style, but allow css rules to continue to work if present
      style.add("color", "");
      style.add("background", "");
      style.add("-webkit-background-clip", "");
      style.add("-webkit-text-fill-color", "");
    }

    //if a propertiesCollection is provided, we'll give the html element a defaultNssFont class so that the
    //default font can be obtained from the theme
    if (font.isNull() && properties != null)
    {
      properties.append("className", "defaultNssFont");

      //NCCB-15046: remove any inline style, but allow css rules to continue to work if present
      style.add("fontSize", "");
      style.add("fontFamily", "");
      style.add("fontWeight", "");
      style.add("textDecoration", "");
      style.add("fontStyle", "");
      return;
    }

    // Font styling (bold, underline, italics)
    if(!font.isNull())
    {
      style.add("fontSize", ((int)font.getSize())+"px");
      if (properties == null)
      {
        style.add("fontFamily", getCSSFontName(font));
      }
      else
      {
        switch (font.getName().toLowerCase()) {
          case "sansserif":
          case "sans-serif":
            properties.append("className", "ux-font-family-sans-serif");
            break;
          case "monospaced":
          case "monospace":
            properties.append("className", "ux-font-family-monospace");
            break;
          case "serif":
            properties.append("className", "ux-font-family-serif");
            break;
          default:
            style.add("fontFamily", getCSSFontName(font));
        }
      }
    }
    else
    {
      style.add("fontSize", "");
      style.add("fontFamily", "");
    }
    if(!font.isNull() && font.isBold())
      style.add("fontWeight", "bold");
    else
      style.add("fontWeight", "normal");
    if(!font.isNull() && font.isUnderline())
      style.add("textDecoration", "underline");
    else
      style.add("textDecoration", "");
    if(!font.isNull() && font.isItalic())
      style.add("fontStyle", "italic");
    else
      style.add("fontStyle", "");

  }

  /**
   * make a background based on the brush and apply to the DOM element's style PropertyCollection
   *
   * @param bg
   * @param def - used when bg isNull()
   * @param style
   * @param op
   * @throws Exception
   */
  public static void makeBackground(BBrush bg, BBrush def, PropertiesCollection style, HxOp op)
    throws Exception
    {
    boolean unsafe =false;
    if (!bg.isNull())
    {
      StringBuilder background = new StringBuilder();

      // Background color
      if (bg.getPaint() instanceof BBrush.Solid)
      {
        background.append(HxUtil.makeColor(bg, BColor.NULL).toHtmlStringWithAlpha());
      }
      else if (bg.getPaint() instanceof BBrush.Gradient) //Gradient background support
      {
        unsafe=true; //this is required to pass the css gradient function to the background
        background.append(makeGradientString((BBrush.Gradient) bg.getPaint()));
      }

      // Background image
      if (bg.getPaint() instanceof BBrush.Image)
      {
        BBrush.Image brush = (BBrush.Image) bg.getPaint();
        BImage backgroundImage = brush.getImage();

        if(backgroundImage == null || backgroundImage.getOrdList().size() <  1)
        {
          //NCCB-15046: remove any inline style, but allow css selector in theme to continue to work if present
          style.add("backgroundImage", "");
          style.add("backgroundColor", "");
          return;
        }

        style.setSnoopEnabled(false);
        background.append("url(").append(HxUtil.escapeJsStringLiteral((WebUtil.toUri(op, op.getRequest(), backgroundImage.getOrdList().get(0))))).append(") ");

        switch (brush.getTile())
        {
        case BBrush.TILE_FALSE:
          background.append("no-repeat ");
          break;
        case BBrush.TILE_TRUE:
          background.append("repeat ");
          break;
        case BBrush.TILE_X:
          background.append("repeat-x ");
          break;
        case BBrush.TILE_Y:
          background.append("repeat-y ");
          break;
        }

        switch (brush.getValign())
        {
        case BBrush.CENTER:
          switch (brush.getHalign())
          {
          case BBrush.LEFT:
            background.append("center left ");
            break;
          case BBrush.RIGHT:
            background.append("center right ");
            break;
          case BBrush.CENTER:
            background.append("center center ");
            break;
          }
          break;
        case BBrush.TOP:
          switch (brush.getHalign())
          {
          case BBrush.LEFT:
            background.append("top left ");
            break;
          case BBrush.RIGHT:
            background.append("top right ");
            break;
          case BBrush.CENTER:
            background.append("top center ");
            break;
          }
          break;
        case BBrush.BOTTOM:
          switch (brush.getHalign())
          {
          case BBrush.LEFT:
            background.append("bottom left ");
            break;
          case BBrush.RIGHT:
            background.append("bottom right ");
            break;
          case BBrush.CENTER:
            background.append("bottom center ");
            break;
          }
          break;
        }
      }

      if(unsafe) //gradients require no quotes
      {
        style.addUnsafe("background", background.toString());
      }
      else
        style.add("background", background.toString());
    }
    else
    {
      if (def != null && !def.isNull())
        makeBackground(def, null, style, op);
      else
      {
        //NCCB-15046: remove any inline style, but allow css selector in theme to continue to work if present
        style.add("backgroundImage", "");
        style.add("backgroundColor", "");
      }
    }
  }

  /**
   * Return the String to use for a Html5 based background gradient.
   *
   * @param gradient
   * @return
   */
  public static String makeGradientString(BBrush.Gradient gradient)
  {
    if(gradient instanceof BBrush.LinearGradient)
    {
      BBrush.LinearGradient linear = (BBrush.LinearGradient) gradient;
      StringBuilder b = new StringBuilder();
      String cssAngle = "" + (90 -linear.getAngle()); //conversion from gx linear to HTML5 linear angle

      BBrush.Stop[] stops = linear.getStops();

      for(BBrush.Stop stop : stops)
      {
        b.append(",");
        b.append(stop.getColor().toHtmlStringWithAlpha() + " " + stop.getOffset() + "%");
      }

      return "linear-gradient(" +  cssAngle + "deg " + b + ")";
    }
    else if(gradient instanceof BBrush.RadialGradient)
    {
      BBrush.RadialGradient radial = (BBrush.RadialGradient) gradient;
      BBrush.Stop[] stops = radial.getStops();
      StringBuilder b = new StringBuilder();

      b.append("radial-gradient( ");

      b.append(radial.getRadius());
      b.append("% ");
      b.append(radial.getRadius());
      b.append("% ");

      b.append("at ");
      b.append(radial.getCenter().x());
      b.append("% ");
      b.append(radial.getCenter().y());
      b.append("% ");

      //Note that he radial.getFocal() is not used or supported due to current limitation in css3 implementation
      //HxPxMedia will warns then Focal is different than Center.

      for(BBrush.Stop stop : stops)
      {
        b.append(",");
        b.append(stop.getColor().toHtmlStringWithAlpha() + " " + stop.getOffset() + "%");
      }

      b.append(")");

      return b.toString();
    }
    //custom gradients do not exist and not yet supported
    return "";
  }

  /**
   * A Widget is effectively visible if getVisible is true, or if its a child of a ScrollPane. If height and width are known,
   * see isWidgetEffectivelyVisible(BWidget widget, int , int) for a more comprehensive answer.
   */
  public static boolean isWidgetEffectivelyVisible(BWidget widget)
  {
    return widget.getVisible() || widget.getParentWidget() instanceof BScrollPane;
  }

  /**
   * A Widget is effectively visible if the height and width are not zero and either getVisible is true, or if its a child of a ScrollPane.
   *
   * @since Niagara 4.7
   */
  public static boolean isWidgetEffectivelyVisible(BWidget widget, int width, int height)
  {
    return width != 0 && height != 0 && (widget.getVisible() || widget.getParentWidget() instanceof BScrollPane);
  }

  private static String getBorderWidth(double width)
  {
    return width+ "px";
  }

  /**
   * Apply a BBorder to DOM Elements style's PropertiesCollection.
   *
   * @since Niagara 4.2 allow user to pass on the width and height of the dom in case a radial gradient needs them
   */
  public static void makeBorder(BBorder b, PropertiesCollection style)
    throws Exception
  {
    if (b.isNull())
    {
      //NCCB-15046: remove any inline style, but allow css selector in theme to continue to work if present
      style.add("borderTopWidth", "");
      style.add("borderTopStyle", "");
      style.add("borderTopColor", "");

      style.add("borderLeftWidth", "");
      style.add("borderLeftStyle", "");
      style.add("borderLeftColor", "");

      style.add("borderRightWidth", "");
      style.add("borderRightStyle", "");
      style.add("borderRightColor", "");

      style.add("borderBottomWidth", "");
      style.add("borderBottomStyle", "");
      style.add("borderBottomColor", "");

      style.add("borderImage", "");
      return;
    }

    //border gradient is supported when gradient is sam on all sides, bottom is used in encoding check since there
    //is no other easier way to check for equal sides on BBorder
    if (b.encodeToString().indexOf("bottom(") == -1 && b.topBrush.getPaint() instanceof BBrush.Gradient)
    {

      BBrush.Gradient gradient = (BBrush.Gradient) b.topBrush.getPaint();
      String gradientString = HxUtil.makeGradientString(gradient) + " " + ((int)b.topWidth) + " stretch";
      style.add("borderStyle", "solid");
      style.addUnsafe("borderImage", gradientString );

      style.add("borderWidth", getBorderWidth(b.topWidth));

      return;
    }

    style.add("borderTopWidth", getBorderWidth(b.topWidth));
    style.add("borderTopColor", HxUtil.makeColor(b.topBrush, BColor.NULL).toHtmlStringWithAlpha());
    switch (b.topStyle)
    {
      case BBorder.NONE:
        style.add("borderTopStyle", "none");
        break;
      case BBorder.SOLID:
        style.add("borderTopStyle", "solid");
        break;
      case BBorder.DOTTED:
        style.add("borderTopStyle", "dotted");
        break;
      case BBorder.DASHED:
        style.add("borderTopStyle", "dashed");
        break;
      case BBorder.GROOVE:
        style.add("borderTopStyle", "groove");
        style.add("borderTopColor", "inherit");
        break;
      case BBorder.RIDGE:
        style.add("borderTopStyle", "ridge");
        style.add("borderTopColor", "inherit");
        break;
      case BBorder.INSET:
        style.add("borderTopStyle", "inset");
        style.add("borderTopColor", "inherit");
        break;
      case BBorder.OUTSET:
        style.add("borderTopStyle", "outset");
        style.add("borderTopColor", "inherit");
        break;
    }

    style.add("borderLeftWidth", getBorderWidth(b.leftWidth));
    style.add("borderLeftColor", HxUtil.makeColor(b.leftBrush, BColor.NULL).toHtmlStringWithAlpha());
    switch (b.leftStyle)
    {
      case BBorder.NONE:
        style.add("borderLeftStyle", "none");
        break;
      case BBorder.SOLID:
        style.add("borderLeftStyle", "solid");
        break;
      case BBorder.DOTTED:
        style.add("borderLeftStyle", "dotted");
        break;
      case BBorder.DASHED:
        style.add("borderLeftStyle", "dashed");
        break;
      case BBorder.GROOVE:
        style.add("borderLeftStyle", "groove");
        style.add("borderLeftColor", "inherit");
        break;
      case BBorder.RIDGE:
        style.add("borderLeftStyle", "ridge");
        style.add("borderLeftColor", "inherit");
        break;
      case BBorder.INSET:
        style.add("borderLeftStyle", "inset");
        style.add("borderLeftColor", "inherit");
        break;
      case BBorder.OUTSET:
        style.add("borderLeftStyle", "outset");
        style.add("borderLeftColor", "inherit");
        break;
    }

    style.add("borderRightWidth", getBorderWidth(b.rightWidth));
    style.add("borderRightColor",  HxUtil.makeColor(b.rightBrush, BColor.NULL).toHtmlStringWithAlpha());
    switch (b.rightStyle)
    {
      case BBorder.NONE:
        style.add("borderRightStyle", "none");
        break;
      case BBorder.SOLID:
        style.add("borderRightStyle", "solid");
        break;
      case BBorder.DOTTED:
        style.add("borderRightStyle", "dotted");
        break;
      case BBorder.DASHED:
        style.add("borderRightStyle", "dashed");
        break;
      case BBorder.GROOVE:
        style.add("borderRightStyle", "groove");
        style.add("borderRightColor", "inherit");
        break;
      case BBorder.RIDGE:
        style.add("borderRightStyle", "ridge");
        style.add("borderRightColor", "inherit");
        break;
      case BBorder.INSET:
        style.add("borderRightStyle", "inset");
        style.add("borderRightColor", "inherit");
        break;
      case BBorder.OUTSET:
        style.add("borderRightStyle", "outset");
        style.add("borderRightColor", "inherit");
        break;
    }

    style.add("borderBottomWidth", getBorderWidth(b.bottomWidth));
    style.add("borderBottomColor",  HxUtil.makeColor(b.bottomBrush, BColor.NULL).toHtmlStringWithAlpha());
    switch (b.bottomStyle)
    {
      case BBorder.NONE:
        style.add("borderBottomStyle", "none");
        break;
      case BBorder.SOLID:
        style.add("borderBottomStyle", "solid");
        break;
      case BBorder.DOTTED:
        style.add("borderBottomStyle", "dotted");
        break;
      case BBorder.DASHED:
        style.add("borderBottomStyle", "dashed");
        break;
      case BBorder.GROOVE:
        style.add("borderBottomStyle", "groove");
        style.add("borderBottomColor", "inherit");
        break;
      case BBorder.RIDGE:
        style.add("borderBottomStyle", "ridge");
        style.add("borderBottomColor", "inherit");
        break;
      case BBorder.INSET:
        style.add("borderBottomStyle", "inset");
        style.add("borderBottomColor", "inherit");
        break;
      case BBorder.OUTSET:
        style.add("borderBottomStyle", "outset");
        style.add("borderBottomColor", "inherit");
        break;
    }
  }

  /**
   * Apply a padding to DOM Elements style's PropertiesCollection.
   */
  public static void makePadding(BInsets padding, PropertiesCollection style)
    throws Exception
  {
    style.add("paddingTop", ((int)padding.top)+"px");
    style.add("paddingBottom", ((int)padding.bottom)+"px");
    style.add("paddingLeft", ((int)padding.left)+"px");
    style.add("paddingRight", ((int)padding.right)+"px");
  }

  /**
   * Apply a Margin to DOM Elements style's PropertiesCollection.
   */
  public static void makeMargin(BInsets padding, PropertiesCollection style)
    throws Exception
  {
    style.add("marginTop", ((int)padding.top)+"px");
    style.add("marginBottom", ((int)padding.bottom)+"px");
    style.add("marginLeft", ((int)padding.left)+"px");
    style.add("marginRight", ((int)padding.right)+"px");
  }

  /**
   * write a form value that is not visible to the user
   */
  public static void writeFormValue(String name, String value, HxOp op)
    throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("<input ");
    out.attr("style", "display: none");
    out.w(" ");
    out.attr("type", "text");
    out.w(" ");
    out.attr("id",    op.scope(name));
    out.w(" ");
    out.attr("name",  op.scope(name));
    out.w(" ");
    out.attr("value",  value);
    out.w("/>");
  }

  /**
   * persistFormValue will rewrite the form value if its not null (useful for dialogs)
   */
  public static void persistFormValue(String name, HxOp op)
    throws Exception
  {
    if(op.getFormValue(name) != null)
        writeFormValue(name, op.getFormValue(name), op);

  }

////////////////////////////////////////////////////////////////
// Decoding
////////////////////////////////////////////////////////////////

  /**
   * Decode form value.
   */
  public static String decode(String s)
  {
    StringBuilder buf = new StringBuilder(s.length() + 10);
    char[] c = s.toCharArray();
    for(int i=0; i<c.length; ++i)
    {
      if (c[i] == '+') buf.append(" ");
      else if (c[i] == '%')
      {
        i++;
        int val = 0;
        val += fromHex(c[i++]) * 16;
        val += fromHex(c[i]);

        // UTF-8 (0x80-0x7FF)
        if ((val & 0xe0) == 0xc0)
        {
          int high = val;
          int low  = 0;

          i++; // skip next %
          low += fromHex(c[++i]) * 16;
          low += fromHex(c[++i]);

          low  = ((high & 0x0001) << 6) | (low & 0x3f);
          high = (high >> 1) & 0x0f;

          val = (high << 7) | low;
        }
        // UTF-8 (0x800-0xFFFF)
        else if ((val & 0xe0) == 0xe0)
        {
          int three = val;
          int two   = 0;
          int one   = 0;

          i++; // skip next %
          two += fromHex(c[++i]) * 16;
          two += fromHex(c[++i]);

          i++; // skip next %
          one += fromHex(c[++i]) * 16;
          one += fromHex(c[++i]);

          val = ((three & 0x0f) << 12) | ((two & 0x3f) << 6) | ((one & 0x3f) << 0);
        }

        buf.append((char)val);
      }
      else buf.append(c[i]);
    }
    return buf.toString();
  }

  /**
   * Convert from hex char to int.
   */
  private static int fromHex(char ch)
  {
    if ((ch >= '0') && (ch <= '9'))
      return (int)ch - '0';
    else if ((ch >= 'A') && (ch <= 'F'))
      return (int)ch - 'A' + 10;
    else
      throw new IllegalArgumentException("Invalid hex character: " + ch);
  }

  /**
   * This method previously ensured that touch scrolling was provided to a particular DOM elements, but these browser are
   * no longer supported, so this method does nothing and is deprecated.
   * @deprecated since Niagara 4.10U2
   */
  @Deprecated
  public static void addTouchScroll(String elemID, HxOp op)
  {
  }

  /**
   * Is the request a POST or GET. Get cooresponds to HxView.write and POST applies to HxView's update, process,
   * and save methods.
   */
  public static boolean isPost(HxOp op)
  {
    String method = op.getRequest().getMethod().toLowerCase();
    return !method.equals("get");
  }

  /**
   * Preferred Outer Quote so that it doesn't interfere with GET/Post hx quote semantics
   */
  public static String getOuterQuote(HxOp op)
  {
    String quote = (!isPost(op)) ? "\"" : "&quot;";
    return quote;
  }

  /**
   * Preferred Inner Quote so that it doesn't interfere with GET/Post hx quote semantics
   */
  public static String getInnerQuote(HxOp op)
  {
    String quote = (!isPost(op)) ? "&quot;" : "'";
    return quote;
  }

////////////////////////////////////////////////////////////////
// Images
////////////////////////////////////////////////////////////////

  /**
   * makeImage
   *
   * @deprecated use {@link #makeImageJS(BImage, HxOp)}. Will be removed in Niagara 5.0
   */
  @Deprecated
  public static void makeImage(BImage image, HxOp op)
    throws Exception
  {
    makeImage(image, null, "", op);
  }

  /**
   * makeImage
   *
   * @deprecated use {@link #makeImageJS(BImage, String, HxOp)}. Will be removed in Niagara 5.0
   */
  @Deprecated
  public static void makeImage(BImage image, String attrs, HxOp op)
    throws Exception
  {
    makeImage(image, attrs, "", op);
  }

  /**
   * makeImage
   *
   * @deprecated use {@link #makeImageJS(BImage, String, String, HxOp)}. Will be removed in Niagara 5.0
   */
  @Deprecated
  public static void makeImage(BImage image, String attrs, String alt, HxOp op)
    throws Exception
  {
    makeImage(image, attrs, alt, null, op);
  }

  /**
   * makeImage, allows user to pass in quote character in case it needs to
   * be custom for a POST. Defaults to a single quote if none is provide or
   * original makeImage method is called.
   *
   * @deprecated use {@link #makeImageJS(BImage, String, String, String, HxOp)}. Will be removed in Niagara 5.0
   * @param image The image to render
   * @param attrs If provided, any additional attributes for the img. Use the alt
   *              parameter for adding alt and title text. Please note, this
   *              parameter won't be processed for possible XSS attacks.
   *              @see Encode#forHtml(String)
   * @param alt The standard alt identifier, also used as the title attribute
   * @param quote If provided, the quote style to use, defaults to a single quote
   * @param op The HxOp for generating the Html
   * @since Niagara 4.2
   */
  @Deprecated
  public static void makeImage(BImage image, String attrs, String alt, String quote, HxOp op)
    throws Exception
  {
    if(image == null || image.getOrdList().size() == 0)
      return;

    HtmlWriter out = op.getHtmlWriter();
    String ord = image.getOrdList().get(0).toString();

    if(alt == null)
      alt = "";

    out.w("<img");
    HxUtil.attr(" alt", alt, quote, out);
    if(alt != null && alt.length() > 0)
      HxUtil.attr(" title", alt, quote, out);

    HxUtil.attr(" src", ord, quote, out);

    if (attrs != null && attrs.length() > 0) out.w(" ").w(attrs);
    out.w("/>");
  }

  /**
   * makeImageJS
   *
   * @since Niagara 4.4
   */
  public static void makeImageJS(BImage image, HxOp op)
    throws Exception
  {
    makeImageJS(image, null, "", op);
  }

  /**
   * makeImageJS
   *
   * @since Niagara 4.4
   */
  public static void makeImageJS(BImage image, String attrs, HxOp op)
    throws Exception
  {
    makeImageJS(image, attrs, "", op);
  }

  /**
   * makeImageJS
   *
   * @since Niagara 4.4
   */
  public static void makeImageJS(BImage image, String attrs, String alt, HxOp op)
    throws Exception
  {
    makeImageJS(image, attrs, alt, null, op);
  }

  /**
   * Render an image. A sprite sheet will be used if the requested image is
   * found in a sprite sheet. See javax/baja/hx/hx.js#makeImage. Setting the
   * "hx.spritesheets.disabled" system property to true will revert to the
   * original makeImage() behavior (no use of sprite sheets).
   *
   * Allows the user to pass in a quote character in case it needs to
   * be custom for a POST. Defaults to a single quote if none is provide or
   * original makeImageJS method is called.
   *
   * {@link #addJavascriptOnload(HxOp)} may need to be called if you
   * are appending the image to your own DOM.innerHTML in a POST.
   *
   * @param image The image to render
   * @param attrs If provided, any additional attributes for the img (excluding id).
   *              Use the alt parameter for adding alt and title text. Please note,
   *              this parameter won't be processed for possible XSS attacks.
   *              @see Encode#forHtml(String)
   * @param alt The standard alt identifier, also used as the title attribute
   * @param quote If provided, the quote style to use, defaults to a single quote
   * @param op The HxOp for generating the HTML
   * @throws Exception
   * @since Niagara 4.4
   */
  public static void makeImageJS(BImage image, String attrs, String alt, String quote, HxOp op)
    throws Exception
  {
    if(image == null || image.getOrdList().size() == 0)
      return;

    if(attrs == null)
      attrs = "";

    if(alt == null)
      alt = "";

    if(quote == null)
      quote = "'";

    if(spriteSheetsDisabled)
    {
      String ord = image.getOrdList().get(0).toString();

      HtmlWriter out = op.getHtmlWriter();

      out.w("<span class=" + quote + "hxImageWrapper" + quote + ">");
      out.w("<span>");
      out.w("<img");

      HxUtil.attr(" alt", alt, quote, out);
      if(!alt.isEmpty())
        HxUtil.attr(" title", alt, quote, out);

      HxUtil.attr(" src", ord, quote, out);

      if(!attrs.isEmpty())
        out.w(" ").w(attrs);

      out.w("/>");
      out.w("</span>");
      out.w("</span>");
    }

    else
    {
      HtmlWriter html = op.getHtmlWriter();

      html.w("<span class=" + quote + "hxImageWrapper" + quote + ">");
      StringBuilder ordArray = new StringBuilder("[");
      StringBuilder opArray = new StringBuilder("[");
      for(int i = 0; i < image.getOrdList().size(); i++)
      {
        HxOp scopeOp = op.make("hx_image_" + imageId.getAndIncrement(), op);

        if(i != 0)
        {
          ordArray.append(", ");
          opArray.append(", ");
        }

        ordArray.append("'" + HxUtil.escapeJsStringLiteral((WebUtil.toUri(op, op.getRequest(), image.getOrdList().get(i)))) + "'");
        opArray.append("'" + HxUtil.escapeJsStringLiteral(scopeOp.scope("value")) + "'");

        html.w("<span ");
        HxUtil.attr("id", scopeOp.scope("value"), quote, html);
        html.w("></span>");
      }
      ordArray.append("]");
      opArray.append("]");
      html.w("</span>");

      Writer writer = startOnloadWriter(op);
      try
      {
        HtmlWriter js = op.getHtmlWriter();

        if (quote.equals("'"))
        {
          js.w("hx.makeImage(" + ordArray.toString() + ", " + opArray
            .toString() +
            ", \"" + XWriter
            .safeToString(alt, false) + "\", \"" + attrs + "\")" +
            ".catch(function (err) { console.error(err); });");
        }

        else
        {
          js.w("hx.makeImage(" + ordArray.toString() + ", " + opArray
            .toString() +
            ", '" + XWriter.safeToString(alt, false) + "', '" + attrs + "')" +
            ".catch(function (err) { console.error(err); });");
        }
      }
      finally
      {
        finishOnloadWriter(writer, op);
      }
    }
  }

  /**
   * Change an image created by
   * {@link #makeImageJS(BImage, String, String, String, HxOp)}
   * to another image.
   *
   * @param parent The id of the parent element containing the generated image HTML
   * @param image The image to render
   * @param attrs If provided, any additional attributes for the img (excluding id)
   * @param op The HxOp for generating the HTML
   * @throws Exception
   * @since Niagara 4.4
   */
  public static void changeImageJS(String parent, BImage image, String attrs, HxOp op)
    throws Exception
  {
    HtmlWriter html = op.getHtmlWriter();
    html.w(changeImageJSInvokeCode(parent, image, attrs, op));
  }

  /**
   * Get the invoke code to change an image created by
   * {@link #makeImageJS(BImage, String, String, String, HxOp)}
   * to another image.
   *
   * @param parent The id of the parent element containing the generated image HTML
   * @param image The image to render
   * @param attrs If provided, any additional attributes for the img (excluding id)
   * @param op The HxOp for generating the HTML
   * @since Niagara 4.4
   */
  public static String changeImageJSInvokeCode(String parent, BImage image, String attrs, HxOp op)
  {
    if(attrs == null)
      attrs = "";

    StringBuilder ordArray = new StringBuilder("[");
    for(int i = 0; i < image.getOrdList().size(); i++)
    {
      if(i != 0)
      {
        ordArray.append(", ");
      }

      ordArray.append("'" + HxUtil.escapeJsStringLiteral(WebUtil.toUri(op, op.getRequest(), image.getOrdList().get(i))) + "'");
    }
    ordArray.append("]");

    return "hx.changeImage('" + parent + "', " + ordArray.toString() +
      ", \"" + attrs + "\")" + ".catch(function (err) { console.error(err); });";
  }

  /**
   * Same as HtmlWriter.attr, but allows user to pass in a custom quote. Defaults to a single quote if quote is null.
   */
  public static final HtmlWriter attr(String name, String value, String quote, HtmlWriter out)
  {
    if(quote == null)
      quote = "'";
    return out.w(name).w('=').w(quote).safe(value).w(quote);
  }

  /**
   * Start having the HxOp.getHtmlWriter() write to a buffer for when you want to obtain the contents of the buffer
   * for use with the addOnload. When you have the content you need, call 'finishOnloadWriter(Writer, op) in a
   * finally statement to ensure the op always gets reset.
   * @since Niagara 4.4
   * @return The writer that you need to pass into 'finishOnloadWriter'.
   */
  public static Writer startOnloadWriter(HxOp op)
    throws IOException
  {
    Writer contentHx = new StringWriter();
    op.setWriter(new PrintWriter(contentHx));
    return contentHx;
  }

  /**
   * finishOnloadWriter will return the HxOp writer to the original writer and append the
   * current writer contents to HxOp.addOnload().
   * @since Niagara 4.4
   */
  public static void finishOnloadWriter(Writer writer, HxOp op)
      throws IOException
  {
    op.resetWriter();
    op.addOnload(writer.toString());
  }

////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////

  /**
   * This method previously detected old mobile version like Android 2.3 and IO4 but this method always returns false
   * since all supported browsers have touch scrolling now.
   * @deprecated since Niagara 4.10U2 - will be removed in Niagara 5.0
   */
  @Deprecated
  public static boolean isUserAgentWithoutInnerDivTouchScroll(HxOp op)
  {
    return false;
  }

  /**
   * Return the Hx poll frequency.
   */
  public static int getPollFreq()
  {
    return pollFreq;
  }

  /**
   * Utility for encoding a single Quote in some text
   *
   * @param text
   * @return
   * @since Niagara 4.3
   */
  public static String encodeSingleQuotes(String text)
  {
    return encodeQuotes(text, "'");
  }

  /**
   * Utility for encoding a double Quote in some text
   *
   * @param text
   * @return
   * @since Niagara 4.3
   */
  public static String encodeDoubleQuotes(String text)
  {
    return encodeQuotes(text, "\"");
  }

  /**
   * Encode text if you know the quote being used around it. If that quote Character is not present in the
   * text, then no changes will be made to the encoding.
   *
   * @param text
   * @param quoteCharacter
   * @return
   */
  private static String encodeQuotes(String text, String quoteCharacter)
  {
    return text.replace(quoteCharacter, "\\" + quoteCharacter);
  }

  /**
   * This method just replaces &quot; with \".
   * Prior to 4.4, the HxOp.addOnload was added inline within single quotes and required escaping quotes,
   * this is no longer the case and this method helps with backward compatibility.
   *
   * @since Niagara 4.4
   */
  public static String unescapeJsForInvocation(String s)
  {

    if (s == null)
    {
      return null;
    }
    s = s.replaceAll("&quot;", "\"");
    return s;
  }

  /**
   * This method writes an anchor that won't get snooped in case the hyperlink
   * contains HTML characters like {@code '<'} or {@code '>'}. the displayText will be HTML escaped.
   *
   * @since Niagara 4.10
   */
  public static void writeSafeAnchor(BOrd ord, String displayText, HxOp op)
    throws Exception
  {
    writeSafeAnchorStart(ord, op);
    HtmlWriter out = op.getHtmlWriter();
    out.w(">");
    out.safe(displayText);
    out.w("</a>");
  }

  /**
   * This method starts an anchor that won't get snooped in case the hyperlink
   * contains HTML characters like {@code '<'} or {@code '>'}.
   *
   * Make sure to close the tag after writing any other necessary HTML attributes to the anchor.
   * @since Niagara 4.10
   */
  public static void writeSafeAnchorStart(BOrd ord, HxOp op)
    throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();

    out.w("<!-- @noSnoop --><a href='"+ encodeOrdForHref(ord, op)+ '\'');
  }

  /**
   * This method encodes problematic characters that could be in a BOrd and also protects against XSS by URL encoding other characters like
   * single and double quotes.
   *
   * @since Niagara 4.10
   */
  public static String encodeOrdForHref(BOrd ord, HxOp op) {
    String s = op.toUri(ord);
    int codePointCount = s.codePointCount(0, s.length());
    StringBuilder b = new StringBuilder(s.length());

    //These are the characters not yet encoded by Http.encodeUrl(), but needed when setting an href
    for(int i = 0; i < codePointCount; ++i) {
      int codePoint = s.codePointAt(i);
      switch(codePoint) {
        case '"': b.append("%22"); break;
        case '\'': b.append("%27"); break;
        case '\\': b.append("%5C"); break;
        case '\n': b.append("%0A"); break; //Currently Invalid for Ord
        case '\r': b.append("%0D"); break; //Currently Invalid for Ord
        case '\u2028': b.append("%E2%80%A8"); break;
        case '\u2029': b.append("%E2%80%A9"); break;
        default:
          b.appendCodePoint(codePoint);
      }
    }

    return b.toString();
  }

  /**
   * When setting an href on an HtmlElement, url encode if the text contains a single or double quote.
   * This uses URLEncoder.encode to protect against XSS. If you have an BOrd, its better to call
   * encodeOrdForHref so certain characters like `|` are not double escaped if you have previously
   * called WebOp.toUri() to obtain the string.
   *
   * @since Niagara 4.3U1
   */
  public static String encodeURLForHref(String link)
    throws Exception
  {

    if(link == null || (!link.contains("'") && !link.contains("\""))) {
      return link;
    }

    return URLEncoder.encode(link, "UTF-8");
  };

  /**
   * Escapes all characters that are not valid in a JavaScript string literal,
   * and returns a string that is safe to wrap in either single or double quotes
   * and embed in JavaScript code.
   * @since Niagara 4.3
   */
  public static String escapeJsStringLiteral(String s) {
    return WebUtil.escapeJsStringLiteral(s);
  }

  /**
   * Override the HxOp writer such that anything written to the op in the CheckedConsumer is returned as a string.
   * @since Niagara 4.8
   */
  public static String marshal(ConsumerWithException<? super HxOp, Exception> r, HxOp op)
    throws Exception
  {

    StringWriter content = new StringWriter();
    op.setWriter(new PrintWriter(content));
    try
    {
      r.accept(op);
    }
    finally
    {
      op.resetWriter();
    }
    return content.toString();
  }

  /**
   * encode label text, maintaining line breaks
   *
   * @param text
   * @return
   * @throws Exception
   */
  public static String encodeText(String text)
    throws Exception
  {
    StringBuilder out = new StringBuilder();
    int len = text.length();
    int i = 0;

    while (i < len)
    {
      char c = text.charAt(i++);
      if (Character.isHighSurrogate(c) && i < len)
      {
        char c2 = text.charAt(i);
        if (Character.isLowSurrogate(c2))
        {
          out.append("&#").append(Character.toCodePoint(c, c2)).append(';');
          i++;
          continue;
        }
      }

      if (c < 0x20 || c > 0x7e || c == '\'' || c == '"' || c == '\\')
      {
        if (c == '\n' || c == '\r')
          out.append("<br/>");
        else if (c == '"')
          out.append("\\\"");
        else if (c == '\'')
          out.append("\\'");
        else if (c == '\\')
          out.append("\\\\");
        else
        {
          out.append("&#").append((int) c).append(';');
        }
      }
      else
      {
        if (c == '<')
          out.append("&lt;");
        else if (c == '>')
          out.append("&gt;");
        else if (c == '&')
          out.append("&amp;");
        else
          out.append(c);
      }
    }
    return out.toString();
  }

  /**
   * Get the invoke code for safely updating form values with quotations, newlines and carriage returns.
   * This function makes a few substitutions to work in an hx.update, then hx.updateValue converts the
   * substitutions back.
   * (HxUtil.encodeText is not good for form values because it converts {@code \n} to {@code <br/>})
   */
  public static String getUpdateValueInvokeCode(String formName, String stringValue, String quote, HxOp op)
    throws Exception
  {
    boolean needNewLineFix = false;
    char backslash = 92; //backslash
    char SUBSTITUTE = 65533; //ascii replacement character

    String match = "\\"; //escape backward slash correctly, don't let it mess up basic letters like n and r
    if(stringValue.indexOf(match) > -1)
    {
      stringValue = TextUtil.replace(stringValue,match, "" + SUBSTITUTE + "s");
      needNewLineFix = true;
    }

    //no javascript required
    match = quote;
    if(stringValue.indexOf(quote) > -1)
    {
      stringValue = TextUtil.replace(stringValue, match, backslash + quote);
    }

    char newLine = 10; //newline
    if(stringValue.indexOf(newLine) > -1)
    {
      stringValue = TextUtil.replace(stringValue, "" + newLine, "" + SUBSTITUTE + 'n');
      needNewLineFix=true;
    }

    newLine = 13; //return carriage
    if(stringValue.indexOf(newLine) > -1)
    {
      stringValue = TextUtil.replace(stringValue, "" + newLine, "" + SUBSTITUTE + 'r');
      needNewLineFix=true;
    }

    StringBuilder b = new StringBuilder();
    b.append("hx.updateValue(").append(quote).append(formName).append(quote).append(",");
    b.append(quote).append(stringValue).append(quote).append(",");
    b.append(needNewLineFix).append(");");

    return b.toString();
  }

  /**
   * Utility for writing the right click entry for a navNode
   *
   * @param navNode
   * @param op
   * @throws Exception
   */
  public static void writeContextMenuListItem(BINavNode navNode, HxOp op)
      throws Exception
  {
    String quote = HxUtil.getOuterQuote(op);
    String onclick = "hx.hyperlink(" + quote + encodeOrdForHref(navNode.getNavOrd(), op) + quote + ");";
    String displayName = navNode.getNavDisplayName(op);
    BOrd iconOrd = null;
    if(navNode.getNavIcon() != null && navNode.getNavIcon().getOrdList().size() > 0)
      iconOrd = navNode.getNavIcon().getOrdList().get(0);
    writeContextMenuListItem(onclick, displayName, iconOrd , op);
  }


  /**
   * Utility for writing the right click entry for a navNode
   *
   * @param displayName
   * @param imageOrd
   * @param op
   * @throws Exception
   */
  public static void writeContextMenuListItem(String onclick, String displayName, BOrd imageOrd, HxOp op)
    throws Exception
  {
    String quote = HxUtil.getOuterQuote(op);
    HtmlWriter out = op.getHtmlWriter();
    String cls = "class";
    out.w("<li ").attr("class", "context-menu-item");
    out.w(" onmouseover='this.setAttribute(" + quote + cls + quote + ',' + quote + "context-menu-item hover" + quote + ");'");
    out.w(" onmouseout='this.setAttribute(" + quote + cls + quote + ',' + quote + "context-menu-item" + quote + ");'");
    out.w(" onclick='");
    out.w(onclick);
    out.w("'>");

    out.w("<span class='display'><span class='icon'>");
    if(imageOrd != null && !imageOrd.isNull())
    {
      HxUtil.makeImageJS(BImage.make(imageOrd), null, displayName, op);
    }
    out.w("</span><span class='displayName'>");
    out.safe(displayName);
    out.w("</span></span>");
    out.w("</li>");

  }

  /**
   * Write a "safe" character.  This method will escape unsafe
   * characters common in XML and HTML markup.
   *
   * @param s
   * @return safe string
   */
  public static String safe(String s)
  {
    return XWriter.safeToString(s, true);
  }

  /**
   * Get the CSRF token valid for this session and return as a query string of the form
   * <code>?csrfToken=token</code>
   * E.g. http://localhost/logout?csrfToken=49yCPqUtUZV4pgu%2B6nzKZr9yX75%2BWqG7
   *
   * @return String - A query string with csrf parameter
   */
  public static String getCsrfTokenQueryString()
  {
    NiagaraSuperSession session = SessionManager.getCurrentNiagaraSuperSession();
    try
    {
      if (session != null)
      {
        return "?csrfToken=" + URLEncoder.encode(session.getCsrfToken(), "UTF-8");
      }
    } catch (IOException ie)
    {
      BHxView.log.log(Level.INFO, "CSRF token encoding error", ie);
    }
    return "";
  }

  /**
   * After the initial HxView.write has complete, usually for a POST, add Javascript, Global, and addOnload code in the appropriate order to allow time for the javascript
   * to be retrieved before executing global and addOnload code.
   *
   * @since Niagara 4.4
   * @throws Exception
   */
  public static void addJavascriptOnload(HxOp op)
    throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("/* @noSnoop */");
    BOrd[] scripts = op.getJavaScriptOrds();
    for (int i=0; i<scripts.length; i++)
    {
      if(i== 0)
        out.w("hx.addJavaScript(['");
      else
        out.w("','");
      String javascriptUrl = WebUtil.toUri(op, op.getRequest(), scripts[i]);
      out.w(javascriptUrl);
    }
    if(scripts.length > 0)
    {
      out.w("'], function(){");
    }


    //Note: globals are running as an inner function in a POST, we've
    // provided additional documentation for this in HxOp.addGlobal().
    String[] global  = op.getGlobal();
    if (global.length > 0)
    {
      for (int i=0; i<global.length; i++)
      {
        if(global[i] != null) out.w(global[i]);
      }
    }

    //add dynamic onload code
    String[] codes = op.getOnload();
    for (int i=0; i<codes.length; i++)
    {
      if(codes[i] != null) out.w(HxUtil.unescapeJsForInvocation(codes[i]));
    }

    if(scripts.length > 0)
    {
      out.w("});");
    }
  }

  /**
   * Provide this function an OrdTarget like an HxOp and it will generate a separate
   * OrdTarget that is not based on an HxOp, this OrdTarget has a much smaller
   * memory footprint that can be used for temporary caches.
   * @param target The Original OrdTarget (could be an HxOp)
   * @return an OrdTarget that is not an HxOp
   * @since Niagara 4.2U2
   */
  public static OrdTarget getMinimalOrdTarget(OrdTarget target)
  {
    if(!(target instanceof HxOp))
    {
      return target;
    }

    OrdTarget t = target;
    while(t instanceof HxOp)
    {
      t = t.getBaseOrdTarget();
    }

    return OrdTarget.makeWithFacetsAndLanguage(t, target.get(), target.getFacets(), target.getLanguage());
  }




////////////////////////////////////////////////////////////////
// Properties
////////////////////////////////////////////////////////////////

  static String WEBKIT = "WebKit";
  static String JAVAFX = "JavaFX";
  static int pollFreq = 5000;
  static boolean spriteSheetsDisabled = true;
  static AtomicInteger imageId = new AtomicInteger(0);

  static
  {
    try
    {
      pollFreq = AccessController.doPrivileged((PrivilegedAction<Integer>)
        () -> Integer.getInteger("hx.poll.freq", 5000));
      spriteSheetsDisabled = AccessController.doPrivileged((PrivilegedAction<Boolean>)
        () -> Boolean.getBoolean("hx.spritesheets.disabled"));
    } catch (Exception ignore)
    {
    }
  }
}



