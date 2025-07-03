/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
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
 * BEllipseGeom stores an ellipse by specifying its bounding box.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 4$ $Date: 9/30/08 5:09:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BEllipseGeom
  extends BGeom
  implements IEllipseGeom
{ 
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public static BEllipseGeom make(double x, double y, double width, double height) 
  {
    return new BEllipseGeom(x, y, width, height);
  }

  /**
   * Constructor.
   */
  public static BEllipseGeom make(IEllipseGeom r) 
  {
    if (r instanceof BEllipseGeom) return (BEllipseGeom)r;
    return make(r.x(), r.y(), r.width(), r.height());
  }
  
  /**
   * Make from string encoding.
   */
  public static BEllipseGeom make(String s)
  {               
    Parser parser = new Parser(s);
    BEllipseGeom x = parser.parseEllipse();
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
  private BEllipseGeom(double x, double y, double width, double height) 
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
   * Return ELLIPSE.
   */
  public int getGeomCase() { return ELLIPSE; }

////////////////////////////////////////////////////////////////
// IEllipseGeom
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
   * Is this the null ellipse.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BEllipseGeom hash code.
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
   * Return if the specified object is an equivalent BEllipseGeom.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BEllipseGeom)
    {
      BEllipseGeom r = (BEllipseGeom)obj;
      return this.x == r.x && this.y == r.y &&
             this.width == r.width && this.height == r.height;
    }
    return false;
  }

  /**
  * Encode as "x,y,width,height"
  */
  public String encodeToString()
    throws IOException
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
  public static final BEllipseGeom DEFAULT = new BEllipseGeom(0, 0, 0, 0);

  /**
   * The null instance.
   */
  public static final BEllipseGeom NULL = new BEllipseGeom(0, 0, 0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BEllipseGeom(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEllipseGeom.class);

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
