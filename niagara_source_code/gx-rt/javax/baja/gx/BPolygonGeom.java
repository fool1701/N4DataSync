/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;

/**
 * BPolygonGeom stores a list immutable of coordinates.
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 13$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BPolygonGeom
  extends BGeom
  implements IPolygonGeom
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy from existing polygon.
   */
  public static BPolygonGeom make(IPolygonGeom polygon)
  {
    if (polygon instanceof BPolygonGeom) return (BPolygonGeom)polygon;
    return new BPolygonGeom(polygon.x(), polygon.y(), polygon.size());
  }

  /**
   * Constructor with size of x.length.
   */
  public static BPolygonGeom make(double[] x, double[] y) 
  {
    return make(x, y, x.length);
  }

  /**
   * Constructor.
   */
  public static BPolygonGeom make(double[] x, double[] y, int size) 
  {
    return new BPolygonGeom(clone(x, size), clone(y, size), size);
  }

  /**
   * Make from string encoding.
   */
  public static BPolygonGeom make(String s)
  {               
    Parser parser = new Parser(s);
    BPolygonGeom x = parser.parsePolygon();
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
  private BPolygonGeom(double[] x, double[] y, int size) 
  {
    this.x = x;
    this.y = y;
    this.size = size;
  }

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Return POLYGON.
   */
  public int getGeomCase() { return POLYGON; }

////////////////////////////////////////////////////////////////
// IPolygonGeom
////////////////////////////////////////////////////////////////  

  /** Get a copy of x coordinates */
  public double[] x() { return clone(x, size); }
  
  /** Get a copy of y coordinate */
  public double[] y()  { return clone(y, size); }

  /** The x coordinate at the specified index */
  public double x(int index) { return x[index]; }

  /** The y coordinate at the specified index */
  public double y(int index) { return y[index]; }
  
  /** Get the number of coordinates */
  public int size() { return size; }

////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////  

  /**
   * Is this the null instance.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BPolygonGeom hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    int len = size(); 
    long hash = 23L + len;
    for (int i = 0; i < len; i++)
    {
      hash = (hash * 37L) + Double.doubleToRawLongBits(x[i]);
      hash = (hash * 37L) + Double.doubleToRawLongBits(y[i]);
    }
    return (int)(hash >>> 32) ^ (int)hash;
  }
  
  /**
   * Return if the specified object is an equivalent BPolygonGeom.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BPolygonGeom)
    {
      BPolygonGeom p = (BPolygonGeom)obj;
      int size = size();
      if (size != p.size()) return false;
      for(int i=0; i<size; ++i)
        if (x[i] != p.x[i] || y[i] != p.y[i]) return false;
      return true;
    }
    return false;
  }

  /**
  * Encode as "x1,y1 x2,y2.."
  */
  public String encodeToString()
    throws IOException
  {
    if (isNull()) return "null";
    StringBuilder s = new StringBuilder();
    for(int i=0; i<size; ++i)
      s.append(x[i]).append(',').append(y[i]).append(' ');
    return s.toString();
  }

  /**
   * Decode from string.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }
  
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  private static double[] clone(double[] a, int size)
  {
    double[] c = new double[size];
    System.arraycopy(a, 0, c, 0, size);
    return c;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default is a polygon of size 0.
   */
  public static final BPolygonGeom DEFAULT = new BPolygonGeom(new double[0], new double[0], 0);

  /**
   * The null instance.
   */
  public static final BPolygonGeom NULL = new BPolygonGeom(new double[0], new double[0], 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BPolygonGeom(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPolygonGeom.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private double[] x;
  private double[] y;
  private int size;
  
}
