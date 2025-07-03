/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BRect renders a rectangle shape.
 *
 * @author    Brian Frank
 * @creation  2 Apr 04
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:30 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The geometry of the rectangle.
 */
@NiagaraProperty(
  name = "geom",
  type = "BRectGeom",
  defaultValue = "BRectGeom.NULL"
)
public class BRect
  extends BShape
{          

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BRect(471137104)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "geom"

  /**
   * Slot for the {@code geom} property.
   * The geometry of the rectangle.
   * @see #getGeom
   * @see #setGeom
   */
  public static final Property geom = newProperty(0, BRectGeom.NULL, null);

  /**
   * Get the {@code geom} property.
   * The geometry of the rectangle.
   * @see #geom
   */
  public BRectGeom getGeom() { return (BRectGeom)get(geom); }

  /**
   * Set the {@code geom} property.
   * The geometry of the rectangle.
   * @see #geom
   */
  public void setGeom(BRectGeom v) { set(geom, v, null); }

  //endregion Property "geom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Rectangle with geometry, fill, stroke, and pen.
   */
  public BRect(IRectGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    super(BRectGeom.make(geom), fill, stroke, pen);   
  } 

  /**
   * Rectangle with geometry.
   */
  public BRect(IRectGeom geom)
  {                        
    super(BRectGeom.make(geom));   
  } 

  /**
   * Rectangle constructor.
   */
  public BRect()
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
    setGeom((BRectGeom)geom);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/rect.png");
}
