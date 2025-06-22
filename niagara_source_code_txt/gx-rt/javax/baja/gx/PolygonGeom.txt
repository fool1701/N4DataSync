/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.util.Objects;

/**
 * PolygonGeom is a mutable list of coordinates stored in 
 * an x array and y array.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 12$ $Date: 4/7/04 3:07:14 PM EDT$
 * @since     Baja 1.0
 */
public final class PolygonGeom
  extends Geom
  implements IPolygonGeom
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Copy constructor
   */
  public PolygonGeom(IPolygonGeom polygon)
  {
    x    = polygon.x();
    y    = polygon.y();
    size = polygon.size();
  }

  /**
   * Constructor for x, y, and size
   */
  public PolygonGeom(double[] x, double[] y, int size)
  {
    set(x, y, size);
  }

  /**
   * Constructor for size of zero.
   */
  public PolygonGeom()
  {      
    x = new double[10];
    y = new double[10];
    size = 0;
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

  /** Get a copy of the x coordinates */
  public double[] x() { return clone(x, size); }
  
  /** Get a copy of the y coordinates */
  public double[] y()  { return clone(y, size); }

  /** The x coordinate at the specified index */
  public double x(int index) 
  { 
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    return x[index]; 
  }

  /** The y coordinate at the specified index */
  public double y(int index) 
  { 
    if (index >= size) throw new ArrayIndexOutOfBoundsException(index);
    return y[index]; 
  }

  /** The number of coordinates */
  public int size()  { return size; }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Append a point to this polygon.
   */
  public void add(double px, double py)
  {
    // Grow array if neccessary
    if (size >= x.length)
    {
      int newSize = Math.max(size*2, 4);

      double[] tempx = new double[newSize];
      double[] tempy = new double[newSize];

      System.arraycopy(x, 0, tempx, 0, size);
      System.arraycopy(y, 0, tempy, 0, size);
      
      x = tempx;
      y = tempy;
    }

    x[size] = px;
    y[size] = py;
    size++;
    dirty();
  }

  /**
   * Reset the points.
   */
  public void set(double[] x, double[] y, int size)
  {
    this.x = clone(x, size);
    this.y = clone(y, size);
    this.size = size;
    dirty();
  }            
  
  /**
   * Reset the size to 0.
   */
  public void clear()
  {
    this.size = 0;  
    dirty();
  }  
  
  /**
   * Return if the specified object is an equivalent Point.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof PolygonGeom)
    {
      PolygonGeom p = (PolygonGeom)obj;
      if (size != p.size) return false;
      for(int i=0; i<size; ++i)
        if (x[i] != p.x[i] || y[i] != p.y[i]) return false;
      return true;
    }
    return false;
  }

  /**
   * Hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y, size);
  }

  /**
   * String representation.
   */
  @Override
  public String toString() 
  {
    StringBuilder s = new StringBuilder();
    for(int i=0; i<size; ++i)
      s.append(x[i]).append(',').append(y[i]).append(' ');
    return s.toString();
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
// Attributes
////////////////////////////////////////////////////////////////  

  private double[] x;
  private double[] y;
  private int size = 0;
}
