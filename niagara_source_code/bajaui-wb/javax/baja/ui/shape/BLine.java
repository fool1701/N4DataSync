/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.gx.util.GeomUtil;

/**
 * BLine renders a line between two points.
 *
 * @author    Brian Frank
 * @creation  2 Apr 04
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:29 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The geometry of the Line.
 */
@NiagaraProperty(
  name = "geom",
  type = "BLineGeom",
  defaultValue = "BLineGeom.NULL"
)
public class BLine
  extends BShape
{          

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BLine(2294975734)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "geom"

  /**
   * Slot for the {@code geom} property.
   * The geometry of the Line.
   * @see #getGeom
   * @see #setGeom
   */
  public static final Property geom = newProperty(0, BLineGeom.NULL, null);

  /**
   * Get the {@code geom} property.
   * The geometry of the Line.
   * @see #geom
   */
  public BLineGeom getGeom() { return (BLineGeom)get(geom); }

  /**
   * Set the {@code geom} property.
   * The geometry of the Line.
   * @see #geom
   */
  public void setGeom(BLineGeom v) { set(geom, v, null); }

  //endregion Property "geom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLine.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Construct with geometry, fill, stroke, and pen.
   */
  public BLine(ILineGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    super(BLineGeom.make(geom), fill, stroke, pen);   
  } 

  /**
   * Construct with geometry.
   */
  public BLine(ILineGeom geom)
  {                        
    super(BLineGeom.make(geom));   
  } 

  /**
   * Default constructor.
   */
  public BLine()
  {
  } 
     
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  /**
   * Return if the geometry contains the point.
   */                                          
  public boolean contains(double x, double y)
  {
    // getShapeGeom().peer() is a java.awt.geom.Line2D, but
    // java.awt.geom.Line2D.contains(x, y) will not work here,
    // since it is wired to always return false.
    //
    // Instead, we return true if the point is "near"
    // the line, using an arbitrary value to measure "nearness".

    BLineGeom geom = getGeom();
    double dist = GeomUtil.dist(
      new Point(x, y),
      new Point(geom.x1, geom.y1),
      new Point(geom.x2, geom.y2));

    return (dist <= GeomUtil.NEAR);
  }
  
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
    setGeom((BLineGeom)geom);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/line.png");
}
