/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Interface for immutable BPolygonGeom and mutable PolygonGeom.
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 7$ $Date: 4/7/04 3:06:50 PM EDT$
 * @since     Baja 1.0
 */
public interface IPolygonGeom
  extends IGeom
{ 

  /** Get a copy of the x coordinates */
  public double[] x();

  /** Get a copy of the y coordinates */
  public double[] y();
  
  /** The x coordinate at the specified index */
  public double x(int index);

  /** The y coordinate at the specified index */
  public double y(int index);
  
  /** The number of points in the x and y points arrays */
  public int size();

}
