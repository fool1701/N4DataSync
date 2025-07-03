/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;
import com.tridium.gx.parser.Token;

/**
 * BLayout stores an explicit layout as x, y, width, and height.
 * All four dimensions may be absolute or expressed as percentage 
 * of the parent pane.  Width and height may also be set to use
 * their preferred size.  The string format:
 * <pre>
 *  layout  := fill | x "," y "," w "," h
 *  x, y    := abs | percent
 *  w, h    := abs | percent | pref
 *  abs     := number
 *  percent := number "%"
 *  pref    := "pref"
 *
 * Examples:
 *   10,10,200,100
 *   0,0,50%,100%
 *   10%,20,pref,pref
 * </pre>       
 *
 * @author    Brian Frank
 * @creation  1 Apr 04
 * @version   $Revision: 5$ $Date: 9/30/08 5:09:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BLayout
  extends BSimple
{
////////////////////////////////////////////////////////////////
// Units
////////////////////////////////////////////////////////////////

  public static final int ABS     = 0;
  public static final int PERCENT = 1;
  public static final int PREF    = 2;
  /**
   * This is the constant for fill - 0,0,100%,100%
   */
  public static final BLayout FILL = make(0, ABS, 0, ABS, 100, PERCENT, 100, PERCENT);

  /**
   * The default layout is FILL.
   */
  public static final BLayout DEFAULT = FILL;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BLayout(2979906276)1.0$ @*/
/* Generated Thu Nov 18 13:07:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLayout.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a layout using the specified four dimensions and 
   * their corresponding unit constants.
   */                   
  public static BLayout make(
    double x, int xUnit, double y, int yUnit,
    double w, int wUnit, double h, int hUnit)
  {  
    if (!isXY(xUnit) || !isXY(yUnit) || !isWH(wUnit) || !isWH(hUnit))
      throw new IllegalArgumentException();
    
    BLayout layout = new BLayout();
    layout.x = x;
    layout.y = y;
    layout.w = w;
    layout.h = h;
    layout.xUnit = xUnit;
    layout.yUnit = yUnit;
    layout.wUnit = wUnit;
    layout.hUnit = hUnit;
    return layout;
  }    

  /**
   * Make a layout using the specified four dimensions and 
   * the ABS unit constant.
   */                   
  public static BLayout makeAbs(double x, double y, double w, double h) 
  {  
    return make(x, ABS, y, ABS, w, ABS, h, ABS);
  }
  
  static boolean isXY(int unit) { return unit == ABS || unit == PERCENT; }                  
  static boolean isWH(int unit) { return unit == ABS || unit == PERCENT || unit == PREF; }                  

  /**
   * Make a layout from its string encoding.
   */
  public static BLayout make(String s)
  {                                
    try
    {
      Parser parser = new Parser(s);
      BLayout layout = new BLayout();
      
      // fill
      if (parser.cur.id("fill"))
        return FILL;
      
      // x
      layout.x = parser.cur.num;
      if (parser.cur.num())
        layout.xUnit = ABS;
      else if (parser.cur.dimen("%"))
        layout.xUnit = PERCENT;
      else
        throw new Exception();

      // y      
      parser.consume();
      parser.match(Token.COMMA);
      layout.y = parser.cur.num;
      if (parser.cur.num())
        layout.yUnit = ABS;
      else if (parser.cur.dimen("%"))
        layout.yUnit = PERCENT;
      else
        throw new Exception();

      // width      
      parser.consume();
      parser.match(Token.COMMA);
      layout.w = parser.cur.num;
      if (parser.cur.num())
        layout.wUnit = ABS;
      else if (parser.cur.dimen("%"))
        layout.wUnit = PERCENT;
      else if (parser.cur.id("pref"))
        layout.wUnit = PREF;
      else
        throw new Exception();
      
      // height      
      parser.consume();
      parser.match(Token.COMMA);
      layout.h = parser.cur.num;
      if (parser.cur.num())
        layout.hUnit = ABS;
      else if (parser.cur.dimen("%"))
        layout.hUnit = PERCENT;
      else if (parser.cur.id("pref"))
        layout.hUnit = PREF;
      else
        throw new Exception();
        
      return layout;
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException(s);
    }
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor
   */
  private BLayout()
  {
  }              
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
  
  /**
   * Get the x scalar.
   */
  public double getX() { return x; }

  /**
   * Get the y scalar.
   */
  public double getY() { return y; }

  /**
   * Get the width scalar.
   */
  public double getWidth() { return w; }

  /**
   * Get the height scalar.
   */
  public double getHeight() { return h; }

  /**
   * Get the unit constant for x.
   */
  public int getXUnit() { return xUnit; }

  /**
   * Get the unit constant for y.
   */
  public int getYUnit() { return yUnit; }

  /**
   * Get the unit constant for width.
   */
  public int getWidthUnit() { return wUnit; }

  /**
   * Get the unit constant for height.
   */
  public int getHeightUnit() { return hUnit; }
    
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////
  
  /**
   * BLayout hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(x);
    hash = (hash * 37L) + Double.doubleToRawLongBits(y);
    hash = (hash * 37L) + Double.doubleToRawLongBits(w);
    hash = (hash * 37L) + Double.doubleToRawLongBits(h);
    hash = (hash * 37L) + xUnit;
    hash = (hash * 37L) + yUnit;
    hash = (hash * 37L) + wUnit;
    hash = (hash * 37L) + hUnit;
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Equality is based on equivalent x,y,w,h along with each
   * corresponding unit.
   */
  public boolean equals(Object object)
  {
    if (object instanceof BLayout)
    {
      BLayout o = (BLayout)object;
      return x == o.x && y == o.y &&
             w == o.w && h == o.h &&
             xUnit == o.xUnit && yUnit == o.yUnit && 
             wUnit == o.wUnit && hUnit == o.hUnit;
    }
    return false;
  }
  
  /**
   * BLayout is serialized writeUTF() of its string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BLayout is unserialized using readUTF() of its string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
  {
    if (string == null)
    {               
      if (this == FILL) return "fill";
      StringBuilder s = new StringBuilder();
      
      // x
      s.append(BDouble.encode(x));
      if (xUnit == PERCENT) s.append('%');
      s.append(',');
      
      // y
      s.append(BDouble.encode(y));
      if (yUnit == PERCENT) s.append('%');
      s.append(',');
      
      // width
      if (wUnit == PREF) s.append("pref");
      else
      {
        s.append(BDouble.encode(w));
        if (wUnit == PERCENT) s.append('%');
      }
      s.append(',');
      
      // height
      if (hUnit == PREF) s.append("pref");
      else
      {
        s.append(BDouble.encode(h));
        if (hUnit == PERCENT) s.append('%');
      }
      
      string = s.toString();    
    }
    return string;
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }

  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  double x, y, w, h;
  int xUnit, yUnit, wUnit, hUnit;
  String string;  
}
