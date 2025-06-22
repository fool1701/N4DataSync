/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BEllipse renders a ellipse geometry.
 *
 * @author    Brian Frank
 * @creation  5 Apr 04
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:29 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The geometry of the Ellipse.
 */
@NiagaraProperty(
  name = "geom",
  type = "BEllipseGeom",
  defaultValue = "BEllipseGeom.NULL"
)
public class BEllipse
  extends BShape
{          

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BEllipse(3113212824)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "geom"

  /**
   * Slot for the {@code geom} property.
   * The geometry of the Ellipse.
   * @see #getGeom
   * @see #setGeom
   */
  public static final Property geom = newProperty(0, BEllipseGeom.NULL, null);

  /**
   * Get the {@code geom} property.
   * The geometry of the Ellipse.
   * @see #geom
   */
  public BEllipseGeom getGeom() { return (BEllipseGeom)get(geom); }

  /**
   * Set the {@code geom} property.
   * The geometry of the Ellipse.
   * @see #geom
   */
  public void setGeom(BEllipseGeom v) { set(geom, v, null); }

  //endregion Property "geom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEllipse.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Construct with geometry, fill, stroke, and pen.
   */
  public BEllipse(IEllipseGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    super(BEllipseGeom.make(geom), fill, stroke, pen);   
  } 

  /**
   * Construct with geometry.
   */
  public BEllipse(IEllipseGeom geom)
  {                        
    super(BEllipseGeom.make(geom));   
  } 

  /**
   * Default constructor.
   */
  public BEllipse()
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
    setGeom((BEllipseGeom)geom);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/ellipse.png");

}
