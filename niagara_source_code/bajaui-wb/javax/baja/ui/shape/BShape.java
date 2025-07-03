/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.shape;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.ui.ShellManager;

/**
 * BShape is the base for widgets which render a geometric shape.
 *
 * @author    Brian Frank
 * @creation  2 Apr 04
 * @version   $Revision: 5$ $Date: 3/28/05 10:32:30 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The brush used to fill the shape interior.
 */
@NiagaraProperty(
  name = "fill",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 The brush used to fill the shapes stroke.
 */
@NiagaraProperty(
  name = "stroke",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 The pen used to draw the shapes stroke.
 */
@NiagaraProperty(
  name = "pen",
  type = "BPen",
  defaultValue = "BPen.DEFAULT"
)
/*
 This topic fires a BWidgetEvent whenever the widget is clicked.
 @since Niagara 4.13
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public abstract class BShape
  extends BWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.shape.BShape(1052855177)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fill"

  /**
   * Slot for the {@code fill} property.
   * The brush used to fill the shape interior.
   * @see #getFill
   * @see #setFill
   */
  public static final Property fill = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code fill} property.
   * The brush used to fill the shape interior.
   * @see #fill
   */
  public BBrush getFill() { return (BBrush)get(fill); }

  /**
   * Set the {@code fill} property.
   * The brush used to fill the shape interior.
   * @see #fill
   */
  public void setFill(BBrush v) { set(fill, v, null); }

  //endregion Property "fill"

  //region Property "stroke"

  /**
   * Slot for the {@code stroke} property.
   * The brush used to fill the shapes stroke.
   * @see #getStroke
   * @see #setStroke
   */
  public static final Property stroke = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code stroke} property.
   * The brush used to fill the shapes stroke.
   * @see #stroke
   */
  public BBrush getStroke() { return (BBrush)get(stroke); }

  /**
   * Set the {@code stroke} property.
   * The brush used to fill the shapes stroke.
   * @see #stroke
   */
  public void setStroke(BBrush v) { set(stroke, v, null); }

  //endregion Property "stroke"

  //region Property "pen"

  /**
   * Slot for the {@code pen} property.
   * The pen used to draw the shapes stroke.
   * @see #getPen
   * @see #setPen
   */
  public static final Property pen = newProperty(0, BPen.DEFAULT, null);

  /**
   * Get the {@code pen} property.
   * The pen used to draw the shapes stroke.
   * @see #pen
   */
  public BPen getPen() { return (BPen)get(pen); }

  /**
   * Set the {@code pen} property.
   * The pen used to draw the shapes stroke.
   * @see #pen
   */
  public void setPen(BPen v) { set(pen, v, null); }

  //endregion Property "pen"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BShape.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
     

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor with geometry, fill, stroke, and pen.
   */
  protected BShape(BGeom geom, BBrush fill, BBrush stroke, BPen pen)
  {                           
    setShapeGeom(geom);                                            
    setFill(fill);
    setStroke(stroke);
    setPen(pen);
  } 

  /**
   * Constructor with geometry.
   */
  protected BShape(BGeom geom)
  {                           
    setShapeGeom(geom);
  } 

  /**
   * Default constructor.
   */
  protected BShape()
  {
  } 

////////////////////////////////////////////////////////////////
// Shape
////////////////////////////////////////////////////////////////
  
  /**
   * Get the BGeom of this shape.  Subclasses define a 
   * property called "geom" with their specified BGeom type.
   */
  public abstract BGeom getShapeGeom();

  /**
   * Set the BGeom of this shape.  Subclasses define a 
   * property called "geom" with their specified BGeom type.
   */
  public abstract void setShapeGeom(BGeom geom);
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  /**
   * Shapes do not receive input events 
   * unless hasBindings() returns true. 
   */
  public boolean receiveInputEvents()
  {
    return hasBindings();
  }
  
  /**
   * Return if the geometry contains the point.
   */                                          
  public boolean contains(double x, double y)
  {
    return getShapeGeom().contains(x, y);
  }
  
  /**
   * Paint the geometry using fill, stroke, and pen.
   */
  public void paint(Graphics g)
  {            
    boolean forceStroke = false;
    BGeom geom = getShapeGeom();
    
    BBrush fill = getFill();
    if (!fill.isNull())
    {
      g.setBrush(fill);
      g.fill(geom);
    }
    BBrush stroke = getStroke();

    if (fill.isNull() && stroke.isNull())
    {
      ShellManager manager = getShellManager();
      forceStroke = manager != null && manager.paintTranslucentShapes();
    }


    if (!stroke.isNull() || forceStroke)
    {
      g.setBrush(stroke);
      g.setPen(getPen());
      g.stroke(geom);
    }
  }

  @Override
  public void mousePressed(BMouseEvent event)
  {
    fireActionPerformed(event);
  }

////////////////////////////////////////////////////////////////
// Contain Testing
////////////////////////////////////////////////////////////////
  
  /*
  public void mouseEntered(BMouseEvent event)
  {
    realFill = getFill();
    setFill(BColor.orange.toBrush());
    repaint();
  }
  public void mouseExited(BMouseEvent event)
  {
    setFill(realFill);
    repaint();
  }                      
  BBrush realFill;      
  */

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/shape.png");
}
