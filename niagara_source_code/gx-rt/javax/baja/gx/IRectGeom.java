/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Interface for immutable BRectGeom and mutable RectGeom.
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 8$ $Date: 4/7/04 3:06:55 PM EDT$
 * @since     Baja 1.0
 */
public interface IRectGeom
  extends IGeom
{ 

  /** The x coordinate */
  public double x();

  /** The y coordinate */
  public double y();

  /** The width */
  public double width();
  
  /** The height */
  public double height();
    
}
