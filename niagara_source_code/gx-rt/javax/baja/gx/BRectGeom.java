/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;

/**
 * BRectGeom stores an x and y coordinate plus a width and height
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 14$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BRectGeom
  extends BGeom
  implements IRectGeom
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BRectGeom make(double x, double y, double width, double height) 
  {
    return new BRectGeom(x, y, width, height);
  }

  /**
   * Constructor.
   */
  public static BRectGeom make(IRectGeom r) 
  {
    if (r instanceof BRectGeom) return (BRectGeom)r;
    return make(r.x(), r.y(), r.width(), r.height());
  }

  /**
   * Make from string encoding.
   */
  public static BRectGeom make(String s)
  {               
    Parser parser = new Parser(s);
    BRectGeom x = parser.parseRect();
    if (x == null || !parser.isEnd())
      throw new IllegalArgumentException(s);
    return x;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BRectGeom(double x, double y, double width, double height) 
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Return RECT.
   */
  public int getGeomCase() { return RECT; }

////////////////////////////////////////////////////////////////
// IRectGeom
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public double x() { return x; }
  
  /** The y coordinate */
  public double y() { return y; }

  /** The width */
  public double width() { return width; }
  
  /** The height */
  public double height() { return height; }
  
////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null rectangle.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BRectGeom hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    long hash = 23L + Double.doubleToRawLongBits(x);
    hash = (hash * 37L) + Double.doubleToRawLongBits(y);
    hash = (hash * 37L) + Double.doubleToRawLongBits(width);
    hash = (hash * 37L) + Double.doubleToRawLongBits(height);
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BRectGeom.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BRectGeom)
    {
      BRectGeom r = (BRectGeom)obj;
      return this.x == r.x && this.y == r.y &&
             this.width == r.width && this.height == r.height;
    }
    return false;
  }

  /**
  * Encode as "x,y,width,height"
  */
  public String encodeToString()
  {
    if (isNull()) return "null";
    StringBuilder sb = new StringBuilder();
    sb.append(BDouble.encode(x)).append(',')
      .append(BDouble.encode(y)).append(',')
      .append(BDouble.encode(width)).append(',')
      .append(BDouble.encode(height));
    return sb.toString();         
  }

  /**
   * Decode as "x,y,width,height"
   */
  public BObject decodeFromString(String s)
    throws IOException
  {                
    return make(s);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default is 0, 0, 0, 0.
   */
  public static final BRectGeom DEFAULT = new BRectGeom(0, 0, 0, 0);

  /**
   * The null instance.
   */
  public static final BRectGeom NULL = new BRectGeom(0, 0, 0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BRectGeom(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRectGeom.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public final double x;
  
  /** The y coordinate */
  public final double y;

  /** The width */
  public final double width;

  /** The height */
  public final double height;
    
}
