/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;
import javax.baja.sys.BDouble;

/**
 * LineGeom stores line between two points.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 2$ $Date: 4/7/04 3:07:05 PM EDT$
 * @since     Baja 1.0
 */
public final class LineGeom
  extends Geom
  implements ILineGeom
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public LineGeom(ILineGeom r)
  {
    set(r.x1(), r.y1(), r.x2(), r.y2());
  }

  /**
   * Constructor with two points
   */
  public LineGeom(double x1, double y1, double x2, double y2)
  {
    set(x1, y1, x2, y2);
  }

  /**
   * Constructor for 0, 0, 0, 0
   */
  public LineGeom()
  {
    set(0, 0, 0, 0);
  }

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Return LINE.
   */
  public int getGeomCase() { return LINE; }

////////////////////////////////////////////////////////////////
// ILineGeom
////////////////////////////////////////////////////////////////

  /** The x coordinate of first point */
  public double x1()  { return x1; }

  /** The y coordinate of first point */
  public double y1()  { return y1; }

  /** The x coordinate of second point */
  public double x2() { return x2; }
  
  /** The y coordinate of second point */
  public double y2() { return y2; }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Reset the two points.
   */
  public void set(double x1, double y1, double x2, double y2)
  {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  /**
   * Return if the specified object is an equivalent LineGeom.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof LineGeom)
    {
      LineGeom o = (LineGeom)obj;
      return this.x1 == o.x1 && this.y1 == o.y1 &&
             this.x2 == o.x2 && this.y2 == o.y2;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x1, x2, y1, y2);
  }

  /**
   * String representation.
   */
  @Override
  public String toString() 
  {
    return BDouble.encode(x1) + "," + BDouble.encode(y1) + " " +
           BDouble.encode(x2) + "," + BDouble.encode(y2);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** The x coordinate of first point */
  public double x1;
  
  /** The y coordinate of first point */
  public double y1;

  /** The x coordinate of second point */
  public double x2;
  
  /** The y coordinate of second point */
  public double y2;
  
}
