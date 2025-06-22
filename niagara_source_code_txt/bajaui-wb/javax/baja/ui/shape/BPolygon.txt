/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BPolygon renders a polygon.
 *
 * @author    Brian Frank
 * @creation  2 Apr 04
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:29 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The geometry of the polygon.
 */
@NiagaraProperty(
  name = "geom",
  type = "BPolygonGeom",
  defaultValue = "BPolygonGeom.NULL"
)
public class BPolygon
  extends BShape
{          

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BPolygon(2981336561)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "geom"

  /**
   * Slot for the {@code geom} property.
   * The geometry of the polygon.
   * @see #getGeom
   * @see #setGeom
   */
  public static final Property geom = newProperty(0, BPolygonGeom.NULL, null);

  /**
   * Get the {@code geom} property.
   * The geometry of the polygon.
   * @see #geom
   */
  public BPolygonGeom getGeom() { return (BPolygonGeom)get(geom); }

  /**
   * Set the {@code geom} property.
   * The geometry of the polygon.
   * @see #geom
   */
  public void setGeom(BPolygonGeom v) { set(geom, v, null); }

  //endregion Property "geom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPolygon.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Construct with geometry, fill, stroke, and pen.
   */
  public BPolygon(IPolygonGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    super(BPolygonGeom.make(geom), fill, stroke, pen);   
  } 

  /**
   * Construct with geometry.
   */
  public BPolygon(IPolygonGeom geom)
  {                        
    super(BPolygonGeom.make(geom));   
  } 

  /**
   * Default constructor.
   */
  public BPolygon()
  {
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
    setGeom((BPolygonGeom)geom);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/polygon.png");
}
