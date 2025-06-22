/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;
import javax.baja.sys.BDouble;

/**
 * RectGeom stores an x and y coordinate as well as a width and height.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 13$ $Date: 10/5/04 2:12:28 PM EDT$
 * @since     Baja 1.0
 */
public final class RectGeom
  extends Geom
  implements IRectGeom
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public RectGeom(IRectGeom r)
  {
    set(r.x(), r.y(), r.width(), r.height());
  }

  /**
   * Constructor for x, y, width, and height
   */
  public RectGeom(double x, double y, double width, double height)
  {
    set(x, y, width, height);
  }

  /**
   * Constructor for 0, 0, width, and height
   */
  public RectGeom(double width, double height)
  {
    set(0, 0, width, height);
  }

  /**
   * Constructor for 0, 0, 0, 0
   */
  public RectGeom()
  {
    set(0, 0, 0, 0);
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
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Reset the rectangle fields.
   */
  public void set(double x, double y, double width, double height)
  {
    this.x      = x;
    this.y      = y;
    this.width  = width;
    this.height = height;
  }

  /**
   * Reset the rectangle fields.
   */
  public void set(IRectGeom r)
  {
    set(r.x(), r.y(), r.width(), r.height());                          
  }

  /**
   * Translate the point by the specified x and y distance.
   */
  public void translate(double dx, double dy) 
  {
    this.x += dx;
    this.y += dy;
  } 

  /**
   * Return if the specified object is an equivalent RectGeom.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof RectGeom)
    {
      RectGeom r = (RectGeom)obj;
      return this.x == r.x && this.y == r.y &&
             this.width == r.width && this.height == r.height;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y, width, height);
  }

  /**
   * String representation.
   */
  @Override
  public String toString() 
  {
    return BDouble.encode(x) + "," + BDouble.encode(y) + "," +
           BDouble.encode(width) + "," + BDouble.encode(height);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  

  /**
   * Get the intersecting rectangle between rectangles 
   * a and b.  If result is null create a new RectGeom.
   * @return result
   */
  public static RectGeom intersection(IRectGeom a, IRectGeom b, RectGeom result) 
  {
    return intersection(a.x(), a.y(), a.width(), a.height(),
                        b.x(), b.y(), b.width(), b.height(),
                        result);
  }                     
  
  /**
   * Get the intersecting rectangle between rectangles 
   * a and b.  If result is null create a new RectGeom.
   * @return result
   */
  public static RectGeom intersection(double ax, double ay, double aw, double ah, 
                                      double bx, double by, double bw, double bh,   
                                      RectGeom result)
  {              
    double ax2 = ax + aw;
    double ay2 = ay + ah;
    double bx2 = bx + bw;
    double by2 = by + bh;
    if (ax < bx) ax = bx;
    if (ay < by) ay = by;
    if (ax2 > bx2) ax2 = bx2;
    if (ay2 > by2) ay2 = by2;
    ax2 -= ax;
    ay2 -= ay;
    if (result == null) result = new RectGeom();
    result.set(ax, ay, ax2, ay2);
    return result;
  }              

  /**
   * Get the bounding rectangle of the union 
   * between rectangles a and b.  If result is
   * null create a new RectGeom.
   * @return result
   */
  public static RectGeom bounds(IRectGeom a, IRectGeom b, RectGeom result) 
  {
    return bounds(a.x(), a.y(), a.width(), a.height(),
                  b.x(), b.y(), b.width(), b.height(),
                  result);
  }

  /**
   * Get the bounding rectangle of the union 
   * between rectangles a and b.  If result is
   * null create a new RectGeom.
   * @return result
   */
  public static RectGeom bounds(IRectGeom a, 
                                double bx, double by, double bw, double bh, 
                                RectGeom result) 
  {
    return bounds(a.x(), a.y(), a.width(), a.height(),
                  bx, by, bw, bh,
                  result);
  }

  /**
   * Get the bounding rectangle of the union 
   * between rectangles a and b.  If result is
   * null create a new RectGeom.
   * @return result
   */
  public static RectGeom bounds(double ax, double ay, double aw, double ah, 
                                double bx, double by, double bw, double bh, 
                                RectGeom result)
  {
    double x1 = Math.min(ax, bx);
    double x2 = Math.max(ax + aw, bx + bw);
    double y1 = Math.min(ay, by);
    double y2 = Math.max(ay + ah, by + bh);
    
    if (result == null) result = new RectGeom();
    result.set(x1, y1, x2 - x1, y2 - y1);
    return result;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public double x;
  
  /** The y coordinate */
  public double y;

  /** The width */
  public double width;

  /** The height */
  public double height;
  
}
