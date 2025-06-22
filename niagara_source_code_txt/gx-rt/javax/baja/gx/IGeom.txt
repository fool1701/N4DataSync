/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

/**
 * Base interface for immutable BGeoms and mutable Geoms.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 7$ $Date: 3/29/05 9:37:55 AM EST$
 * @since     Baja 1.0
 */
public interface IGeom
{                                    

  public static final int LINE    = 1;
  public static final int RECT    = 2;
  public static final int ELLIPSE = 3;
  public static final int POLYGON = 4;
  public static final int PATH    = 5;
  
  /**
   * Get an integer constant for the geometry 
   * to use in switch statements.
   */   
  public int getGeomCase();  
  
  /**
   * Get the bounding rectangle of this geometry.
   */
  public IRectGeom bounds();   
  
  /**
   * Return if this geometry contains the specified point.
   */
  public boolean contains(double x, double y);

  /**
   * Return if this geometry contains the specified geometry.
   */
  public boolean contains(IGeom geom);
  
  /**
   * Return if this geometry contains the specified rectangle.
   */
  public boolean contains(double x, double y, double w, double h);

  /**
   * Return if this geometry intersects the specified geometry.
   */
  public boolean intersects(IGeom geom);

  /**
   * Return if this geometry intersects the specified rectangle.
   */
  public boolean intersects(double x, double y, double w, double h);

  /**
   * Return the intersection of this geometry 
   * with the specified geometry.
   */
  public IGeom intersection(IGeom geom);

  /**
   * Return the intersection of this geometry 
   * with the specified rectangle.
   */
  public IGeom intersection(double x, double y, double w, double h);

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d);
  
}
