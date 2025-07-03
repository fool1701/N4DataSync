/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BPath renders a general path.
 *
 * @author    Brian Frank
 * @creation  5 Apr 04
 * @version   $Revision: 5$ $Date: 3/28/05 10:32:29 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The geometry of the path.
 */
@NiagaraProperty(
  name = "geom",
  type = "BPathGeom",
  defaultValue = "BPathGeom.NULL"
)
public class BPath
  extends BShape
{          

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BPath(3989305999)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "geom"

  /**
   * Slot for the {@code geom} property.
   * The geometry of the path.
   * @see #getGeom
   * @see #setGeom
   */
  public static final Property geom = newProperty(0, BPathGeom.NULL, null);

  /**
   * Get the {@code geom} property.
   * The geometry of the path.
   * @see #geom
   */
  public BPathGeom getGeom() { return (BPathGeom)get(geom); }

  /**
   * Set the {@code geom} property.
   * The geometry of the path.
   * @see #geom
   */
  public void setGeom(BPathGeom v) { set(geom, v, null); }

  //endregion Property "geom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPath.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Construct with geometry, fill, stroke, and pen.
   */
  public BPath(IPathGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    super(BPathGeom.make(geom), fill, stroke, pen);   
  } 

  /**
   * Construct with geometry.
   */
  public BPath(IPathGeom geom)
  {                        
    super(BPathGeom.make(geom));   
  } 

  /**
   * Default constructor.
   */
  public BPath()
  {
  } 

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  /**
   * Return if the geometry contains the point.
   */        
  /*                                  
  public boolean contains(double x, double y)
  {
//System.out.println("BPath.contains: " + x + ", " + y + ", " + 
//getGeom().support(GxEnv.AWT).getClass().getName());

    // getShapeGeom().peer() is a java.awt.geom.GeneralPath, but
    // java.awt.geom.GeneralPath.contains(x, y) will not work here,
    // because of its idiosyncratic behaviour.
    //
    // Instead, we return true if the point is "near"
    // the flattened version of the path

    Point[] pnts = AwtUtil.flatten(getGeom(), GeomUtil.NEAR);
    double dist = GeomUtil.dist(new Point(x, y), pnts);

    return (dist <= GeomUtil.NEAR);
  }          
  */
  
////////////////////////////////////////////////////////////////
// Shape
////////////////////////////////////////////////////////////////
  
  /**
   * Return the geom property.
   */
  public BGeom getShapeGeom()
  {           
    return getGeom();
  }

  /**
   * Set geom property.
   */
  public void setShapeGeom(BGeom geom)
  {
    setGeom((BPathGeom)geom);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/path.png");
}
