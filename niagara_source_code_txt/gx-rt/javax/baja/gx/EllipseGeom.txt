/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;
import javax.baja.sys.BDouble;

/**
 * EllipseGeom stores an ellipse by specifying its bounding box.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 2$ $Date: 4/7/04 3:06:07 PM EDT$
 * @since     Baja 1.0
 */
public final class EllipseGeom
  extends Geom
  implements IEllipseGeom
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public EllipseGeom(IEllipseGeom r)
  {
    set(r.x(), r.y(), r.width(), r.height());
  }

  /**
   * Constructor for x, y, width, and height
   */
  public EllipseGeom(double x, double y, double width, double height)
  {
    set(x, y, width, height);
  }

  /**
   * Constructor for 0, 0, width, and height
   */
  public EllipseGeom(double width, double height)
  {
    set(0, 0, width, height);
  }

  /**
   * Constructor for 0, 0, 0, 0
   */
  public EllipseGeom()
  {
    set(0, 0, 0, 0);
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

  /** The x coordinate of bounding box */
  public double x() { return x; }

  /** The y coordinate of bounding box */
  public double y() { return y; }

  /** The width of bounding box */
  public double width() { return width; }
  
  /** The height of bounding box */
  public double height() { return height; }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Reset the bounding box.
   */
  public void set(double x, double y, double width, double height)
  {
    this.x      = x;
    this.y      = y;
    this.width  = width;
    this.height = height;
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
   * Return if the specified object is an equivalent EllipseGeom.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof EllipseGeom)
    {
      EllipseGeom e = (EllipseGeom)obj;
      return this.x == e.x && this.y == e.y &&
             this.width == e.width && this.height == e.height;
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
