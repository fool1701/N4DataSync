/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Interface for immutable BLineGeom and mutable LineGeom.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 1$ $Date: 4/6/04 7:59:43 AM EDT$
 * @since     Baja 1.0
 */
public interface ILineGeom
  extends IGeom
{ 

  /** The x coordinate of first point */
  public double x1();

  /** The y coordinate of first point */
  public double y1();

  /** The x coordinate of second point */
  public double x2();
  
  /** The y coordinate of second point */
  public double y2();
      
}
