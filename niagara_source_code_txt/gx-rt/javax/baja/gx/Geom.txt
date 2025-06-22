/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import com.tridium.gx.GeomPeer;
import com.tridium.gx.GxEnv;
import com.tridium.sys.schema.Fw;

/**
 * Geom is the base class for mutable geometries.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 6$ $Date: 5/27/04 11:41:25 AM EDT$
 * @since     Baja 1.0
 */
public abstract class Geom
  implements IGeom
{               

////////////////////////////////////////////////////////////////
// IGeom
////////////////////////////////////////////////////////////////
  
  /**
   * Get the bounding rectangle of this geometry.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final IRectGeom bounds()   
  {
    return peer().bounds();
  }
  
  /**
   * Return if this geometry contains the specified point.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final boolean contains(double x, double y)
  {
    return peer().contains(x, y);
  }

  /**
   * Return if this geometry contains the specified geometry.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final boolean contains(IGeom geom)
  {
    return peer().contains(geom);
  }
  
  /**
   * Return if this geometry contains the specified rectangle.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final boolean contains(double x, double y, double w, double h)
  {
    return peer().contains(x, y, w, h);
  }

  /**
   * Return if this geometry intersects the specified geometry.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final boolean intersects(IGeom geom)
  {
    return peer().intersects(geom);
  }

  /**
   * Return if this geometry intersects the specified rectangle.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final boolean intersects(double x, double y, double w, double h)
  {
    return peer().intersects(x, y, w, h);
  }

  /**
   * Return the intersection of this geometry 
   * with the specified geometry.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final IGeom intersection(IGeom geom)
  {
    return peer().intersection(geom);
  }

  /**
   * Return the intersection of this geometry 
   * with the specified rectangle.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  public final IGeom intersection(double x, double y, double w, double h)
  {
    return peer().intersection(x, y, w, h);
  }

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {      
      case Fw.GET_AWT: return awtSupport;
      case Fw.SET_AWT: awtSupport = a; return null;
    }  
    return null;
  }     
  
  /**
   * Get the FontPeer from the default GxEnv.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  GeomPeer peer()
  {        
    if (peer == null)
      peer = GxEnv.get().makeGeomPeer(this);
    return peer;
  }

  /**
   * Invalidate the peer.
   */          
  final void dirty() 
  { 
    peer = null;
    awtSupport = null;
  } 
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private GeomPeer peer;
  private Object awtSupport;
  
}
