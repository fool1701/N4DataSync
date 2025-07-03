/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Interface for immutable BEllipseGeom and mutable EllipseGeom.
 *
 * @author    Brian Frank       
 * @creation  5 Apr 04
 * @version   $Revision: 1$ $Date: 4/6/04 7:59:43 AM EDT$
 * @since     Baja 1.0
 */
public interface IEllipseGeom
  extends IGeom
{ 

  /** The x coordinate of bounding box */
  public double x();

  /** The y coordinate of bounding box */
  public double y();

  /** The width of bounding box */
  public double width();
  
  /** The height of bounding box */
  public double height();
      
}
