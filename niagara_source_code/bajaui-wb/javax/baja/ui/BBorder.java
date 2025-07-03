/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.io.*;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

import com.tridium.gx.parser.*;
import com.tridium.sys.schema.*;
import com.tridium.ui.theme.*;

/**
 * BBorder defines a border style for the four sides 
 * of a border. Each side may have a width, style and
 * a BBrush to define how it is rendered.
 *
 * <pre>
 *  border := all | sides
 *  all    := [style] || [width] || [brush]
 *  sides  := [top] || [right] || [bottom] || [left]
 *  top    := "top(" all ")"
 *  right  := "right(" all ")"
 *  bottom := "bottom(" all ")"
 *  left   := "left(" all ")" 
 *  width  := double
 *  style  := "none" | "solid" | "dotted" | "dashed" | 
 *            "groove" | "ridge" | "inset" | "outset" 
 *  brush  := any BBrush format
 *
 * Examples:
 *  blue
 *  2 blue
 *  groove
 *  5 dashed red
 *  top(1 solid black) bottom(1 solid green)
 * </pre>
 *
 * @author    Andy Frank       
 * @creation  25 Mar 04
 * @version   $Revision: 10$ $Date: 6/22/10 2:39:14 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBorder
  extends BSimple
{
  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for none. */
  public static final int SOLID = 1;
  /** Ordinal value for none. */
  public static final int DOTTED = 2;
  /** Ordinal value for none. */
  public static final int DASHED = 3;
  /** Ordinal value for none. */
  public static final int GROOVE = 4;
  /** Ordinal value for none. */
  public static final int RIDGE = 5;
  /** Ordinal value for none. */
  public static final int INSET = 6;
  /** Ordinal value for none. */
  public static final int OUTSET = 7;

  /** BBorder constant for none. */
  public static final BBorder none = BBorder.make(BBorder.NONE);
  /** BBorder constant for solid. */
  public static final BBorder solid = BBorder.make(BBorder.SOLID);
  /** BBorder constant for dotted. */
  public static final BBorder dotted = BBorder.make(BBorder.DOTTED);
  /** BBorder constant for dashed. */
  public static final BBorder dashed = BBorder.make(BBorder.DASHED);
  /** BBorder constant for groove. */
  public static final BBorder groove = BBorder.make(BBorder.GROOVE);
  /** BBorder constant for ridge. */
  public static final BBorder ridge = BBorder.make(BBorder.RIDGE);
  /** BBorder constant for inset. */
  public static final BBorder inset = BBorder.make(BBorder.INSET);
  /** BBorder constant for outset. */
  public static final BBorder outset = BBorder.make(BBorder.OUTSET);

  /**
   * The default border is 1 width solid black.
   */
  public static final BBorder DEFAULT = BBorder.make(1, SOLID, BColor.black.toBrush());

  /**
   * The null instance.
   */
  public static final BBorder NULL = BBorder.make(0, NONE, BBrush.NULL);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BBorder(2979906276)1.0$ @*/
/* Generated Thu Nov 18 13:07:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBorder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Set all sides to <code>style</code>, using a width of 1, and 
   * a brush of <code>BColor.black</code>.
   */
  public static BBorder make(int style)
  {
    return BBorder.make(1, style, BColor.black.toBrush());
  }

  /**
   * Modify an existing border to use the specified brush
   */  
  public static BBorder make(BBorder border, BBrush brush)
  {
    return BBorder.make(
        border.topWidth, border.topStyle, brush, 
        border.rightWidth, border.rightStyle, brush, 
        border.bottomWidth, border.bottomStyle, brush, 
        border.leftWidth, border.leftStyle, brush);
  }
  
  /**
   * Set all sides <code>width, style, brush</code>.
   */
  public static BBorder make(double width, int style, BBrush brush) 
  {
    return new BBorder(
      width, style, brush, 
      width, style, brush,
      width, style, brush,
      width, style, brush);
  }

  /**
   * Set each side <code>width, style, brush</code>.
   */
  public static BBorder make(
    double topWidth,    int topStyle,    BBrush topBrush, 
    double rightWidth,  int rightStyle,  BBrush rightBrush,
    double bottomWidth, int bottomStyle, BBrush bottomBrush,
    double leftWidth,   int leftStyle,   BBrush leftBrush)
  {
    return new BBorder(
      topWidth,    topStyle,    topBrush, 
      rightWidth,  rightStyle,  rightBrush,
      bottomWidth, bottomStyle, bottomBrush,
      leftWidth,   leftStyle,   leftBrush);
  }
  
  /**
   * Make border from string encoding.
   */
  public static BBorder make(String s)
  {
    if (s.equals("null")) return NULL;    
    Parser p = new Parser(s);
    
    Side top    = new Side();
    Side right  = new Side();
    Side bottom = new Side();    
    Side left   = new Side();
    
    boolean foundSide = false;
    for (int i=0; i<4; i++)
    {
      if (p.cur.type != Token.FUNCTION) break;
      if (p.cur.str.equals("top")) 
      {
        p.match(Token.FUNCTION);        
        top = parseSide(p);
        p.match(Token.RPAREN);
        foundSide = true;
      }
      else if (p.cur.str.equals("right")) 
      {
        p.match(Token.FUNCTION);
        right = parseSide(p);
        p.match(Token.RPAREN);
        foundSide = true;
      }
      else if (p.cur.str.equals("bottom")) 
      {
        p.match(Token.FUNCTION);
        bottom = parseSide(p);
        p.match(Token.RPAREN);
        foundSide = true;
      }
      else if (p.cur.str.equals("left")) 
      {
        p.match(Token.FUNCTION);
        left = parseSide(p);
        p.match(Token.RPAREN);
        foundSide = true;
      }
    }
    
    if (!foundSide)
    {
      Side side = parseSide(p);      
      return BBorder.make(side.width, side.style, side.brush);    
    }    

    return new BBorder(top.width, top.style, top.brush,
      right.width, right.style, right.brush,
      bottom.width, bottom.style, bottom.brush,
      left.width, left.style, left.brush);
  }  
  
////////////////////////////////////////////////////////////////
// Parsing
////////////////////////////////////////////////////////////////
  
  /**
   * Only used for parsing.
   */
  private static class Side
  {
    double width = 0;
    int style    = NONE;
    BBrush brush = BBrush.NULL;
  }
  
  /**
   * Parse the string into a Side.
   */
  private static Side parseSide(Parser p)
  {
    Side side  = new Side();
    side.width = -1;
    side.style = -1;
    side.brush = null;    
    
    for (int i=0; i<3; i++)
    {
      if (side.width == -1) side.width = parseWidth(p);
      if (side.style == -1) side.style = parseStyle(p);
      if (side.brush == null) side.brush = parseBrush(p);
    }
    
    if (side.width == -1) side.width = 1;
    if (side.style == -1) side.style = SOLID;
    if (side.brush == null) side.brush = BColor.black.toBrush();
    return side;
  }
  
  /**
   * Parse and return width, or -1 if current 
   * token is not a width.
   */
  private static double parseWidth(Parser p)
  {
    if (p.cur.type == Token.NUM) 
      return p.matchNum();
    return -1;
  }
  
  /**
   * Parse and return style, or -1 if current
   * token is not a style.
   */  
  private static int parseStyle(Parser p)
  {
    if (p.cur.type == Token.ID)
    {
      String s = p.cur.str;
      if (s.equals("none"))   { p.consume(); return NONE;   }
      if (s.equals("solid"))  { p.consume(); return SOLID;  }
      if (s.equals("dotted")) { p.consume(); return DOTTED; }
      if (s.equals("dashed")) { p.consume(); return DASHED; }
      if (s.equals("groove")) { p.consume(); return GROOVE; }
      if (s.equals("ridge"))  { p.consume(); return RIDGE;  }
      if (s.equals("inset"))  { p.consume(); return INSET;  }
      if (s.equals("outset")) { p.consume(); return OUTSET; }
    }
    return -1;
  }
  
  /**
   * Parse and return brush, or null if current 
   * token is not a brush.
   */
  private static BBrush parseBrush(Parser p)
  {
    if (p.cur.type == Token.ID || p.cur.type == Token.HASH ||
        p.cur.type == Token.FUNCTION)
      return p.parseBrush();    
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BBorder(
    double topWidth,    int topStyle,    BBrush topBrush, 
    double rightWidth,  int rightStyle,  BBrush rightBrush,
    double bottomWidth, int bottomStyle, BBrush bottomBrush,
    double leftWidth,   int leftStyle,   BBrush leftBrush)
  {
    this.topWidth = w(topStyle, topWidth);
    this.topStyle = topStyle;
    this.topBrush = topBrush;
    
    this.rightWidth = w(rightStyle, rightWidth);
    this.rightStyle = rightStyle;
    this.rightBrush = rightBrush;    
    
    this.bottomWidth = w(bottomStyle, bottomWidth);
    this.bottomStyle = bottomStyle;
    this.bottomBrush = bottomBrush;
    
    this.leftWidth = w(leftStyle, leftWidth);
    this.leftStyle = leftStyle;
    this.leftBrush = leftBrush;    
  }
  
  private double w(int style, double width)
  {
    if (style == NONE) return 0;
    if (style == INSET || style == OUTSET) return 1;
    if (style == GROOVE || style == RIDGE) return 2;    
    return width;
  }

////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////
  
  /**
   * Paint the border.  The border will fit exactly
   * inside the bounds given to this method.
   */
  public void paint(Graphics g, double x, double y, double width, double height)
  {
    paint(g, x, y, width, height, -1, -1);
  }
  
  /**
   * Paint the border.  If {@code gapX >= 0}, allow for a gap across
   * the top border.  The border will fit exactly inside the
   * bounds given to this method.
   */
  public void paint(Graphics g, double x, double y, 
    double width, double height, double gapX, double gapWidth)
  {
    g.push();
    try
    {
      double ax = x - 1;
      double ay = y - 1;
      double bx = x + width;
      double by = y + height;
      
      if (adjust(leftStyle, leftWidth)) ax += (leftWidth / 2);
      if (adjust(topStyle,  topWidth))  ay += (topWidth  / 2);
      if (adjust(rightStyle,  rightWidth))  bx -= (rightWidth  / 2) - 1;
      if (adjust(bottomStyle, bottomWidth)) by -= (bottomWidth / 2) - 1;
      
      if (leftStyle == GROOVE || leftStyle == RIDGE) ax += 1;
      if (topStyle  == GROOVE || topStyle  == RIDGE) ay += 1;
      if (rightStyle  == GROOVE || rightStyle  == RIDGE) bx -= 1;
      if (bottomStyle == GROOVE || bottomStyle == RIDGE) by -= 1;
           
      paintTop(g,ax,ay,bx,by,gapX,gapWidth);
      paintLeft(g,ax,ay,bx,by);
      paintBottom(g,ax,ay,bx,by);
      paintRight(g,ax,ay,bx,by);    
    }
    finally { g.pop(); }
  }
  
  /**
   * Return true if bounds should be adjusted 
   * because of pen width.
   */
  private boolean adjust(int style, double w)
  {
    boolean a = (style == SOLID) || (style == DOTTED) || (style == DASHED);
    boolean b = w > 1;
    return (a & b);
  }

  /**
   * Paint top border.
   */
  private void paintTop(Graphics g, double ax, double ay, 
    double bx, double by, double gapX, double gapWidth)
  {
    if (gapX < 0) helper(g,ax,ay,bx,by);    
    else
    {
      helper(g, ax, ay, gapX-2, by);
      helper(g, gapX+gapWidth+1, ay, bx, by);
    }    
  }
  private void helper(Graphics g, double ax, double ay, double bx, double by)
  {  
    int s = topStyle;
    if (s == BBorder.NONE) return;
    else if (s == BBorder.GROOVE || s == BBorder.RIDGE)
    {
      g.setPen(solidPen);
      g.setBrush(s == BBorder.GROOVE ? shadow : hlight);
      g.strokeLine(ax,ay-1,bx,ay-1);
      g.setBrush(s == BBorder.GROOVE ? hlight : shadow);
      g.strokeLine(ax+1,ay,bx,ay);
    }
    else
    {
      if (s == BBorder.INSET)
      {
        g.setPen(solidPen);
        g.setBrush(shadow);
      }
      else if (s == BBorder.OUTSET)
      {
        g.setPen(solidPen);
        g.setBrush(hlight);
      }
      else
      {
        if (s == BBorder.SOLID)  g.setPen(makePen(topWidth, BBorder.SOLID));
        if (s == BBorder.DOTTED) g.setPen(makePen(topWidth, BBorder.DOTTED));
        if (s == BBorder.DASHED) g.setPen(makePen(topWidth, BBorder.DASHED));
        g.setBrush(topBrush);
      }      
      g.strokeLine(ax, ay, bx, ay);
    }
  }
  
  /**
   * Paint left border.
   */
  private void paintLeft(Graphics g, double ax, double ay, double bx, double by)
  {  
    int s = leftStyle;
    if (s == BBorder.NONE) return;
    else if (s == BBorder.GROOVE || s == BBorder.RIDGE)
    {
      g.setPen(solidPen);
      g.setBrush(s == BBorder.GROOVE ? shadow : hlight);
      g.strokeLine(ax-1, ay-1, ax-1, by);
      g.setBrush(s == BBorder.GROOVE ? hlight : shadow);
      g.strokeLine(ax, ay, ax, by);
    }
    else
    {
      if (s == BBorder.INSET)
      {
        g.setPen(solidPen);
        g.setBrush(shadow);
      }
      else if (s == BBorder.OUTSET)
      {
        g.setPen(solidPen);
        g.setBrush(hlight);
      }
      else
      {
        if (s == BBorder.SOLID)  g.setPen(makePen(leftWidth, BBorder.SOLID));
        if (s == BBorder.DOTTED) g.setPen(makePen(leftWidth, BBorder.DOTTED));
        if (s == BBorder.DASHED) g.setPen(makePen(leftWidth, BBorder.DASHED));
        g.setBrush(leftBrush);
      }
      g.strokeLine(ax, ay, ax, by);
    }
  }

  /**
   * Paint bottom border.
   */
  private void paintBottom(Graphics g, double ax, double ay, double bx, double by)
  {
    int s = bottomStyle;    
    if (s == BBorder.NONE) return;
    else if (s == BBorder.GROOVE || s == BBorder.RIDGE)
    {
      g.setPen(solidPen);
      g.setBrush(s == BBorder.GROOVE ? shadow : hlight);
      g.strokeLine(ax, by, bx, by);
      g.setBrush(s == BBorder.GROOVE ? hlight : shadow);
      g.strokeLine(ax, by+1, bx, by+1);
    }
    else
    {
      if (s == BBorder.INSET)
      {
        g.setPen(solidPen);
        g.setBrush(hlight);
        ax++;
      }  
      else if (s == BBorder.OUTSET)
      {
        g.setPen(solidPen);
        g.setBrush(shadow);
      }
      else
      {
        if (s == BBorder.SOLID)  g.setPen(makePen(bottomWidth, BBorder.SOLID));
        if (s == BBorder.DOTTED) g.setPen(makePen(bottomWidth, BBorder.DOTTED));
        if (s == BBorder.DASHED) g.setPen(makePen(bottomWidth, BBorder.DASHED));
        g.setBrush(bottomBrush);        
      }
      g.strokeLine(ax, by, bx, by);
    }
  }

  /**
   * Paint right border.
   */
  private void paintRight(Graphics g, double ax, double ay, double bx, double by)
  {
    int s = rightStyle;
    if (s == BBorder.NONE) return;
    else if (s == BBorder.GROOVE || s == BBorder.RIDGE)
    {
      g.setPen(solidPen);
      g.setBrush(s == BBorder.GROOVE ? shadow : hlight);
      g.strokeLine(bx, ay, bx, by);
      g.setBrush(s == BBorder.GROOVE ? hlight : shadow);
      g.strokeLine(bx+1, ay-1, bx+1, by+1);
    }
    else
    {
      if (s == BBorder.INSET) 
      {
        g.setPen(solidPen);
        g.setBrush(hlight);
        ay++;
      }
      else if (s == BBorder.OUTSET)
      {
        g.setPen(solidPen);
        g.setBrush(shadow);
      }
      else
      {
        if (s == BBorder.SOLID)  g.setPen(makePen(rightWidth, BBorder.SOLID));
        if (s == BBorder.DOTTED) g.setPen(makePen(rightWidth, BBorder.DOTTED));
        if (s == BBorder.DASHED) g.setPen(makePen(rightWidth, BBorder.DASHED));
        g.setBrush(rightBrush);        
      }
      g.strokeLine(bx, ay, bx, by);
    }
  }
  
  /**
   * Convenience method to make a pen.
   */
  private BPen makePen(double width, int style)
  {                  
    final int cap = BPen.CAP_SQUARE;      
    final int join = BPen.JOIN_BEVEL;
    switch(style)
    {
      case BBorder.SOLID:
        if (width == 1) return solidPen;
        return BPen.make(width, cap, join, null);
      case BBorder.DOTTED:             
        if (width == 1) return dottedPen;
        return BPen.make(width, cap, join, _dotted);
      case BBorder.DASHED:
        if (width == 1) return dashedPen;
        return BPen.make(width, cap, join, _dashed);
    }          
    throw new IllegalArgumentException();
  }

////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null border.
   */
  public boolean isNull()
  {
    if(this == NULL || this.equals(NULL))
      return true;
    
    return false;
  }
  
  /**
   * BBorder hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(topWidth);
    hash = (hash * 37L) + topStyle;
    hash = (hash * 37L) + topBrush.hashCode();
    hash = (hash * 37L) + Double.doubleToRawLongBits(leftWidth);
    hash = (hash * 37L) + leftStyle;
    hash = (hash * 37L) + leftBrush.hashCode();
    hash = (hash * 37L) + Double.doubleToRawLongBits(bottomWidth);
    hash = (hash * 37L) + bottomStyle;
    hash = (hash * 37L) + bottomBrush.hashCode();
    hash = (hash * 37L) + Double.doubleToRawLongBits(rightWidth);
    hash = (hash * 37L) + rightStyle;
    hash = (hash * 37L) + rightBrush.hashCode();
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BInsets.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBorder)
    {
      BBorder b = (BBorder)obj;
      return 
        this.topWidth == b.topWidth && 
        this.topStyle == b.topStyle && 
        this.topBrush.equals(b.topBrush) &&
        this.leftWidth == b.leftWidth && 
        this.leftStyle == b.leftStyle && 
        this.leftBrush.equals(b.leftBrush) &&
        this.bottomWidth == b.bottomWidth && 
        this.bottomStyle == b.bottomStyle && 
        this.bottomBrush.equals(b.bottomBrush) &&
        this.rightWidth == b.rightWidth && 
        this.rightStyle == b.rightStyle && 
        this.rightBrush.equals(b.rightBrush);        
    }
    return false;
  }

  /**
   * Encode using writeUTF
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * Decode using readUTF
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
  * Encode to string.
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    
    // Uniform sides    
    if ((topWidth == rightWidth && topWidth == bottomWidth && topWidth == leftWidth) &&
        (topStyle == rightStyle && topStyle == bottomStyle && topStyle == leftStyle) &&
        (topBrush.equals(rightBrush) && topBrush.equals(bottomBrush) && topBrush.equals(leftBrush)))
      return encodeSide(topWidth, topStyle, topBrush);
    
    // Distinct sides
    StringBuilder sb = new StringBuilder();
    if (topStyle != NONE)
    {   
      sb.append("top(");
      sb.append(encodeSide(topWidth, topStyle, topBrush));
      sb.append(") ");
    }
    if (rightStyle != NONE)
    {
      sb.append("right(");
      sb.append(encodeSide(rightWidth, rightStyle, rightBrush));
      sb.append(") ");
    }
    if (bottomStyle != NONE)
    {
      sb.append("bottom(");
      sb.append(encodeSide(bottomWidth, bottomStyle, bottomBrush));
      sb.append(") ");      
    }
    if (leftStyle != NONE)
    {
      sb.append("left(");
      sb.append(encodeSide(leftWidth, leftStyle, leftBrush));
      sb.append(") ");
    }
    return sb.toString().trim();         
  }
  
  /**
   * Encode a side as "(width style brush)".
   */
  private String encodeSide(double width, int style, BBrush brush)
    throws IOException
  {
    if (isNull()) return "null";
    StringBuilder sb = new StringBuilder();
    sb.append(width).append(" ");
    switch (style)
    {
      case NONE:   sb.append("none");   break;
      case SOLID:  sb.append("solid");  break;
      case DOTTED: sb.append("dotted"); break;
      case DASHED: sb.append("dashed"); break;
      case GROOVE: sb.append("groove"); break;
      case RIDGE:  sb.append("ridge");  break;
      case INSET:  sb.append("inset");  break;
      case OUTSET: sb.append("outset"); break;
    }
    sb.append(" ").append(brush.encodeToString());
    return sb.toString();
  }
  
  /**
   * Decode from string.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return BBorder.make(s);
  }

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {      
      case Fw.SET_BASE_ORD: setBaseOrd(a); return null;
    }
    return super.fw(x, a, b, c, d);      
  }     

  private void setBaseOrd(Object baseOrd)
  {                     
    topBrush   .fw(Fw.SET_BASE_ORD, baseOrd, null, null, null);
    leftBrush  .fw(Fw.SET_BASE_ORD, baseOrd, null, null, null);
    bottomBrush.fw(Fw.SET_BASE_ORD, baseOrd, null, null, null);
    rightBrush .fw(Fw.SET_BASE_ORD, baseOrd, null, null, null);
  }
    
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  
  private static final BBrush shadow = Theme.widget().getControlShadow();
  private static final BBrush hlight = Theme.widget().getControlHighlight();
  private static final int cap = BPen.CAP_SQUARE;
  private static final int join = BPen.JOIN_BEVEL;
  private static final double[] _dotted = {2, 2};
  private static final double[] _dashed = {8, 2};
  private static final BPen solidPen  = BPen.make(1, cap, join, null);
  private static final BPen dottedPen = BPen.make(1, cap, join, _dotted);
  private static final BPen dashedPen = BPen.make(1, cap, join, _dashed);  

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  /** Border width of top border. */  
  public final double topWidth;
  
  /** Border width of left border. */  
  public final double leftWidth;

  /** Border width of bottom border. */  
  public final double bottomWidth;

  /** Border width of right border. */  
  public final double rightWidth;  
  
    
  /** Style used for top of border. */
  public final int topStyle;

  /** Style used for left of border. */
  public final int leftStyle;

  /** Style used for bottom of border. */
  public final int bottomStyle;

  /** Style used for right of border. */
  public final int rightStyle;
  
  
  /** Brush used for top of border. */
  public final BBrush topBrush;

  /** Brush used for left of border. */
  public final BBrush leftBrush;

  /** Brush used for bottom of border. */
  public final BBrush bottomBrush;

  /** Brush used for right of border. */
  public final BBrush rightBrush;
}
