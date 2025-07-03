/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;

/**
 * Point stores an x and y coordinate.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 3$ $Date: 3/22/04 11:12:11 AM EST$
 * @since     Baja 1.0
 */
public final class Point
  implements IPoint
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public Point(IPoint pt)
  {
    set(pt.x(), pt.y());
  }

  /**
   * Constructor for x, y
   */
  public Point(double x, double y)
  {
    set(x, y);
  }

  /**
   * Constructor for 0, 0
   */
  public Point()
  {
    set(0, 0);
  }

////////////////////////////////////////////////////////////////
// IPoint
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public double x() { return x; }
  
  /** The y coordinate */
  public double y()  { return y; }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Reset the point fields.
   */
  public void set(double x, double y)
  {
    this.x = x;
    this.y = y;
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
   * Return if the specified object is an equivalent Point.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Point)
    {
      Point pt = (Point)obj;
      return this.x == pt.x && this.y == pt.y;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * String representation.
   */
  @Override
  public String toString() 
  {
    return String.valueOf(x) + "," + String.valueOf(y);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate */
  public double x;
  
  /** The y coordinate */
  public double y;
  
}
