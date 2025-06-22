/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.GeomPeer;
import com.tridium.gx.GxEnv;
import com.tridium.sys.schema.Fw;

/**
 * BGeom is the base class for immutable BSimple geometries.
 *
 * @author    Brian Frank       
 * @creation  10 Jan 03
 * @version   $Revision: 8$ $Date: 11/13/08 4:35:13 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BGeom
  extends BSimple
  implements IGeom
{                  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.gx.BGeom(2979906276)1.0$ @*/
/* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGeom.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

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
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Encode using writeUTF
   */
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * Decode using readUTF
   */
  public final BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
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
      case Fw.GET_PEER: return peer();
    }
    return super.fw(x, a, b, c, d);      
  }     
  
  /**
   * Get the GeomPeer from the default GxEnv.
   * 
   * @throws IllegalStateException if called from a compact3 VM
   */
  GeomPeer peer()
  {        
    if (peer == null)
      peer = GxEnv.get().makeGeomPeer(this);
    return peer;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private Object awtSupport;
  private GeomPeer peer;
}
