/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.converters.BNumericToSimpleMap;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.BPen;
import javax.baja.gx.EllipseGeom;
import javax.baja.gx.Graphics;
import javax.baja.gx.IGeom;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.util.Lexicon;

/**
 * BAnalogMeter displays a analog meter.
 *
 * @author    Andy Frank
 * @creation  27 Oct 03
 * @version   $Revision$ $Date: 5/13/2004 6:38:04 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 The current value to display
 */
@NiagaraProperty(
  name = "value",
  type = "double",
  defaultValue = "0"
)
/*
 The text used to display the current value.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = ""
)
/*
 The start angle for the meter.
 */
@NiagaraProperty(
  name = "startAngle",
  type = "int",
  defaultValue = "240"
)
/*
 The angle the extend the meter clockwise from startAngle.
 */
@NiagaraProperty(
  name = "arcAngle",
  type = "int",
  defaultValue = "300"
)
/*
 The min value for the meter.
 */
@NiagaraProperty(
  name = "min",
  type = "double",
  defaultValue = "0"
)
/*
 The max value for the meter.
 */
@NiagaraProperty(
  name = "max",
  type = "double",
  defaultValue = "100"
)
/*
 The number of major divisions.
 */
@NiagaraProperty(
  name = "numDivisions",
  type = "int",
  defaultValue = "10"
)
/*
 The number of minor divisions.
 */
@NiagaraProperty(
  name = "numSubDivisions",
  type = "int",
  defaultValue = "5"
)
/*
 Are subdivisions displayed.
 */
@NiagaraProperty(
  name = "showSubDivisions",
  type = "boolean",
  defaultValue = "true"
)
/*
 Is the current value displayed as a label.
 */
@NiagaraProperty(
  name = "valueVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 The font used to render the value.
 */
@NiagaraProperty(
  name = "valueFont",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
/*
 Is the scale visible on the meter.
 */
@NiagaraProperty(
  name = "scaleVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 The font used to render the scale.
 */
@NiagaraProperty(
  name = "scaleFont",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
/*
 The pen to be used when rendering the needle.
 */
@NiagaraProperty(
  name = "needlePen",
  type = "BPen",
  defaultValue = "BPen.DEFAULT"
)
/*
 The color used to render the needle.
 */
@NiagaraProperty(
  name = "needleBrush",
  type = "BBrush",
  defaultValue = "BColor.red.toBrush()"
)
/*
 The brush used to render the background.
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BColor.make(0xcccccc).toBrush()"
)
/*
 The brush used to render the foreground.
 */
@NiagaraProperty(
  name = "foreground",
  type = "BBrush",
  defaultValue = "BColor.black.toBrush()"
)
/*
 If true the entire circle is filled in regardless what
 the startAngle and arcAngle are.
 */
@NiagaraProperty(
  name = "fillCircle",
  type = "boolean",
  defaultValue = "true"
)
/*
 Defines color ranges based on a set of ranges. (unimplemented)
 */
@NiagaraProperty(
  name = "ranges",
  type = "BNumericToSimpleMap",
  defaultValue = "BNumericToSimpleMap.NULL",
  flags = Flags.HIDDEN
)
public class BAnalogMeter
  extends BWidget
{   
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BAnalogMeter(2400479220)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The current value to display
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 0, null);

  /**
   * Get the {@code value} property.
   * The current value to display
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * The current value to display
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The text used to display the current value.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", null);

  /**
   * Get the {@code text} property.
   * The text used to display the current value.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The text used to display the current value.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "startAngle"

  /**
   * Slot for the {@code startAngle} property.
   * The start angle for the meter.
   * @see #getStartAngle
   * @see #setStartAngle
   */
  public static final Property startAngle = newProperty(0, 240, null);

  /**
   * Get the {@code startAngle} property.
   * The start angle for the meter.
   * @see #startAngle
   */
  public int getStartAngle() { return getInt(startAngle); }

  /**
   * Set the {@code startAngle} property.
   * The start angle for the meter.
   * @see #startAngle
   */
  public void setStartAngle(int v) { setInt(startAngle, v, null); }

  //endregion Property "startAngle"

  //region Property "arcAngle"

  /**
   * Slot for the {@code arcAngle} property.
   * The angle the extend the meter clockwise from startAngle.
   * @see #getArcAngle
   * @see #setArcAngle
   */
  public static final Property arcAngle = newProperty(0, 300, null);

  /**
   * Get the {@code arcAngle} property.
   * The angle the extend the meter clockwise from startAngle.
   * @see #arcAngle
   */
  public int getArcAngle() { return getInt(arcAngle); }

  /**
   * Set the {@code arcAngle} property.
   * The angle the extend the meter clockwise from startAngle.
   * @see #arcAngle
   */
  public void setArcAngle(int v) { setInt(arcAngle, v, null); }

  //endregion Property "arcAngle"

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * The min value for the meter.
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(0, 0, null);

  /**
   * Get the {@code min} property.
   * The min value for the meter.
   * @see #min
   */
  public double getMin() { return getDouble(min); }

  /**
   * Set the {@code min} property.
   * The min value for the meter.
   * @see #min
   */
  public void setMin(double v) { setDouble(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * The max value for the meter.
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(0, 100, null);

  /**
   * Get the {@code max} property.
   * The max value for the meter.
   * @see #max
   */
  public double getMax() { return getDouble(max); }

  /**
   * Set the {@code max} property.
   * The max value for the meter.
   * @see #max
   */
  public void setMax(double v) { setDouble(max, v, null); }

  //endregion Property "max"

  //region Property "numDivisions"

  /**
   * Slot for the {@code numDivisions} property.
   * The number of major divisions.
   * @see #getNumDivisions
   * @see #setNumDivisions
   */
  public static final Property numDivisions = newProperty(0, 10, null);

  /**
   * Get the {@code numDivisions} property.
   * The number of major divisions.
   * @see #numDivisions
   */
  public int getNumDivisions() { return getInt(numDivisions); }

  /**
   * Set the {@code numDivisions} property.
   * The number of major divisions.
   * @see #numDivisions
   */
  public void setNumDivisions(int v) { setInt(numDivisions, v, null); }

  //endregion Property "numDivisions"

  //region Property "numSubDivisions"

  /**
   * Slot for the {@code numSubDivisions} property.
   * The number of minor divisions.
   * @see #getNumSubDivisions
   * @see #setNumSubDivisions
   */
  public static final Property numSubDivisions = newProperty(0, 5, null);

  /**
   * Get the {@code numSubDivisions} property.
   * The number of minor divisions.
   * @see #numSubDivisions
   */
  public int getNumSubDivisions() { return getInt(numSubDivisions); }

  /**
   * Set the {@code numSubDivisions} property.
   * The number of minor divisions.
   * @see #numSubDivisions
   */
  public void setNumSubDivisions(int v) { setInt(numSubDivisions, v, null); }

  //endregion Property "numSubDivisions"

  //region Property "showSubDivisions"

  /**
   * Slot for the {@code showSubDivisions} property.
   * Are subdivisions displayed.
   * @see #getShowSubDivisions
   * @see #setShowSubDivisions
   */
  public static final Property showSubDivisions = newProperty(0, true, null);

  /**
   * Get the {@code showSubDivisions} property.
   * Are subdivisions displayed.
   * @see #showSubDivisions
   */
  public boolean getShowSubDivisions() { return getBoolean(showSubDivisions); }

  /**
   * Set the {@code showSubDivisions} property.
   * Are subdivisions displayed.
   * @see #showSubDivisions
   */
  public void setShowSubDivisions(boolean v) { setBoolean(showSubDivisions, v, null); }

  //endregion Property "showSubDivisions"

  //region Property "valueVisible"

  /**
   * Slot for the {@code valueVisible} property.
   * Is the current value displayed as a label.
   * @see #getValueVisible
   * @see #setValueVisible
   */
  public static final Property valueVisible = newProperty(0, true, null);

  /**
   * Get the {@code valueVisible} property.
   * Is the current value displayed as a label.
   * @see #valueVisible
   */
  public boolean getValueVisible() { return getBoolean(valueVisible); }

  /**
   * Set the {@code valueVisible} property.
   * Is the current value displayed as a label.
   * @see #valueVisible
   */
  public void setValueVisible(boolean v) { setBoolean(valueVisible, v, null); }

  //endregion Property "valueVisible"

  //region Property "valueFont"

  /**
   * Slot for the {@code valueFont} property.
   * The font used to render the value.
   * @see #getValueFont
   * @see #setValueFont
   */
  public static final Property valueFont = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code valueFont} property.
   * The font used to render the value.
   * @see #valueFont
   */
  public BFont getValueFont() { return (BFont)get(valueFont); }

  /**
   * Set the {@code valueFont} property.
   * The font used to render the value.
   * @see #valueFont
   */
  public void setValueFont(BFont v) { set(valueFont, v, null); }

  //endregion Property "valueFont"

  //region Property "scaleVisible"

  /**
   * Slot for the {@code scaleVisible} property.
   * Is the scale visible on the meter.
   * @see #getScaleVisible
   * @see #setScaleVisible
   */
  public static final Property scaleVisible = newProperty(0, true, null);

  /**
   * Get the {@code scaleVisible} property.
   * Is the scale visible on the meter.
   * @see #scaleVisible
   */
  public boolean getScaleVisible() { return getBoolean(scaleVisible); }

  /**
   * Set the {@code scaleVisible} property.
   * Is the scale visible on the meter.
   * @see #scaleVisible
   */
  public void setScaleVisible(boolean v) { setBoolean(scaleVisible, v, null); }

  //endregion Property "scaleVisible"

  //region Property "scaleFont"

  /**
   * Slot for the {@code scaleFont} property.
   * The font used to render the scale.
   * @see #getScaleFont
   * @see #setScaleFont
   */
  public static final Property scaleFont = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code scaleFont} property.
   * The font used to render the scale.
   * @see #scaleFont
   */
  public BFont getScaleFont() { return (BFont)get(scaleFont); }

  /**
   * Set the {@code scaleFont} property.
   * The font used to render the scale.
   * @see #scaleFont
   */
  public void setScaleFont(BFont v) { set(scaleFont, v, null); }

  //endregion Property "scaleFont"

  //region Property "needlePen"

  /**
   * Slot for the {@code needlePen} property.
   * The pen to be used when rendering the needle.
   * @see #getNeedlePen
   * @see #setNeedlePen
   */
  public static final Property needlePen = newProperty(0, BPen.DEFAULT, null);

  /**
   * Get the {@code needlePen} property.
   * The pen to be used when rendering the needle.
   * @see #needlePen
   */
  public BPen getNeedlePen() { return (BPen)get(needlePen); }

  /**
   * Set the {@code needlePen} property.
   * The pen to be used when rendering the needle.
   * @see #needlePen
   */
  public void setNeedlePen(BPen v) { set(needlePen, v, null); }

  //endregion Property "needlePen"

  //region Property "needleBrush"

  /**
   * Slot for the {@code needleBrush} property.
   * The color used to render the needle.
   * @see #getNeedleBrush
   * @see #setNeedleBrush
   */
  public static final Property needleBrush = newProperty(0, BColor.red.toBrush(), null);

  /**
   * Get the {@code needleBrush} property.
   * The color used to render the needle.
   * @see #needleBrush
   */
  public BBrush getNeedleBrush() { return (BBrush)get(needleBrush); }

  /**
   * Set the {@code needleBrush} property.
   * The color used to render the needle.
   * @see #needleBrush
   */
  public void setNeedleBrush(BBrush v) { set(needleBrush, v, null); }

  //endregion Property "needleBrush"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * The brush used to render the background.
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BColor.make(0xcccccc).toBrush(), null);

  /**
   * Get the {@code background} property.
   * The brush used to render the background.
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * The brush used to render the background.
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * The brush used to render the foreground.
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, BColor.black.toBrush(), null);

  /**
   * Get the {@code foreground} property.
   * The brush used to render the foreground.
   * @see #foreground
   */
  public BBrush getForeground() { return (BBrush)get(foreground); }

  /**
   * Set the {@code foreground} property.
   * The brush used to render the foreground.
   * @see #foreground
   */
  public void setForeground(BBrush v) { set(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "fillCircle"

  /**
   * Slot for the {@code fillCircle} property.
   * If true the entire circle is filled in regardless what
   * the startAngle and arcAngle are.
   * @see #getFillCircle
   * @see #setFillCircle
   */
  public static final Property fillCircle = newProperty(0, true, null);

  /**
   * Get the {@code fillCircle} property.
   * If true the entire circle is filled in regardless what
   * the startAngle and arcAngle are.
   * @see #fillCircle
   */
  public boolean getFillCircle() { return getBoolean(fillCircle); }

  /**
   * Set the {@code fillCircle} property.
   * If true the entire circle is filled in regardless what
   * the startAngle and arcAngle are.
   * @see #fillCircle
   */
  public void setFillCircle(boolean v) { setBoolean(fillCircle, v, null); }

  //endregion Property "fillCircle"

  //region Property "ranges"

  /**
   * Slot for the {@code ranges} property.
   * Defines color ranges based on a set of ranges. (unimplemented)
   * @see #getRanges
   * @see #setRanges
   */
  public static final Property ranges = newProperty(Flags.HIDDEN, BNumericToSimpleMap.NULL, null);

  /**
   * Get the {@code ranges} property.
   * Defines color ranges based on a set of ranges. (unimplemented)
   * @see #ranges
   */
  public BNumericToSimpleMap getRanges() { return (BNumericToSimpleMap)get(ranges); }

  /**
   * Set the {@code ranges} property.
   * Defines color ranges based on a set of ranges. (unimplemented)
   * @see #ranges
   */
  public void setRanges(BNumericToSimpleMap v) { set(ranges, v, null); }

  //endregion Property "ranges"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAnalogMeter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
//////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BAnalogMeter()
  {
  }
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  public void computePreferredSize()
  {
    setPreferredSize(100,100);
  }
  
  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();
    
    if (getStartAngle() <= 180 && getArcAngle() <= 180 && !getFillCircle())
    {
      double temp = h * 2;
      size = Math.min(w-1, temp-1);
      
      BFont vfont = getValueFont();
      if (vfont.isNull()) vfont = defValueFont;

      BFont sfont = getScaleFont();
      if (sfont.isNull()) sfont = defScaleFont;
      
      double diff = 0;
      if (getValueVisible()) diff = vfont.getHeight() + vfont.getDescent();
      else if (getScaleVisible()) diff = sfont.getHeight();
      //size -= (diff * 2);

      if (size / 2 + diff >= h) size = (h - diff - 1) * 2;
      
      px = (w - size) / 2;
      py = (temp - size) / 2 - diff;
      cx = px + (size / 2);
      cy = py + (size / 2);
    }
    else
    {
      size = Math.min(w-1, h-1);
      px = (w - size) / 2;
      py = (h - size) / 2;
      cx = px + (size / 2);
      cy = py + (size / 2);
    }
  
    radius = size / 2d;
    
    if (getFillCircle()) geom = new EllipseGeom(px,py,size,size);
    else
    {
//      PathGeom path = new PathGeom();
//      path.moveTo(true,cx,cy);
//      path.lineTo(false,-10,20);
//      path.arcTo(false, 30,30,0,true,true,20,0);
//      geom = path;      
      geom = null;
    } 
  }

  public void paint(Graphics g)
  {
    double value = getValue();
    double sa = getStartAngle();
    double aa = getArcAngle();
    double mn = getMin();
    double mx = getMax();

    BFont vfont = getValueFont();
    if (vfont.isNull()) vfont = defValueFont;

    BFont sfont = getScaleFont();
    if (sfont.isNull()) sfont = defScaleFont;
    
    //
    // Meter Face
    //
    if (!getBackground().isNull())
    {
      g.setBrush(getBackground());
      if (geom != null) g.fill(geom);
      //g._fillArc(px,py,size,size,startAngle,-(fillCircle ? 360 : arcAngle));
  
//      for(int i=0; i<states.size(); ++i)
//      {
//        FloatState state = (FloatState)states.get(i);
//        if (state.bg.isNull()) continue;
//        g.setBrush(state.bg);
//        int minAngle = (int)(state.min / (max - min) * arcAngle);
//        int maxAngle = (int)(state.max / (max - min) * arcAngle);
//        g._fillArc(px,py,size,size,startAngle-minAngle,-(maxAngle-minAngle));
//      }
//  
//      if (states.size() > 0)
//      {
//        int temp = (int)(radius - radius * 0.95);
//        if (temp < 2) temp = 2;
//        g.setBrush(bgColor);
//        g._fillArc(px+temp,py+temp,size-(temp*2),size-(temp*2),startAngle,-(fillCircle ? 360 : arcAngle));
//      }
    }

    g.setBrush(getForeground());
    if (geom != null) g.stroke(geom);
    //g._drawArc(px,py,size-1,size-1,startAngle,-(fillCircle ? 360 : arcAngle));
    
    // This obscures min and max scale values - XOR? Position min/max differently?
    /*
    if (!fillCircle)
    {
      double minEdge = startAngle * TO_RAD;
      double maxEdge = (startAngle - arcAngle) * TO_RAD;
      
      int dx = (int)(Math.cos(minEdge) * radius);
      int dy = (int)(Math.sin(minEdge) * radius);
      g.strokeLine(cx, cy, cx+dx, cy-dy);
      
      dx = (int)(Math.cos(maxEdge) * radius);
      dy = (int)(Math.sin(maxEdge) * radius);
      g.strokeLine(cx, cy, cx+dx, cy-dy);
    }
    */
    
    //
    // Needle
    //
    double angle = sa;
    if (value <= mn) angle = sa * TO_RAD;
    else if (value >= mx) angle = (sa - aa) * TO_RAD;
    else angle = (sa - ((value - mn) / (mx - mn) * aa)) * TO_RAD;
    int dx = (int)(Math.cos(angle) * radius);
    int dy = (int)(Math.sin(angle) * radius);

    //get current pen
    BPen currentPen = g.getPen();
    
    //Issue 18919
    g.setPen(getNeedlePen());
    g.setBrush(getNeedleBrush());
    g.strokeLine(cx, cy, cx+dx, cy-dy);
    
    //restore old pen
    g.setPen(currentPen);

    //
    // Value
    //
    String s = getText();
    g.setFont(vfont);
    g.setBrush(getForeground());
    if (getValueVisible()) 
      g.drawString(s, cx - (vfont.width(s) / 2), cy + vfont.getHeight());

    //
    // Divisions
    //
    int numDiv = getNumDivisions();
    int subDiv = getNumSubDivisions();
    double subAngle = aa / numDiv;
    double dv = (mx - mn) / numDiv;
    g.setFont(sfont);

    for (int i=0; i<=numDiv; i++)
    {
      double a = (sa - (subAngle * i)) * TO_RAD;
      double cos = Math.cos(a);
      double sin = Math.sin(a);
      double tx  = cx + (int)(cos * radius);
      double ty  = cy - (int)(sin * radius);
      double tx2 = cx + (int)(cos * radius * 0.95);
      double ty2 = cy - (int)(sin * radius * 0.95);
      g.strokeLine(tx, ty, tx2, ty2);

      if (getShowSubDivisions() && i<numDiv)
      {
        double sda = subAngle  / subDiv;
        for (int j=0; j<=subDiv; j++)
        {
          double za = (sa - (subAngle * i) - (sda * j)) * TO_RAD;
          double scos = Math.cos(za);
          double ssin = Math.sin(za);
          double stx  = cx + (int)(scos * radius);
          double sty  = cy - (int)(ssin * radius);
          double stx2 = cx + (int)(scos * radius * 0.98);
          double sty2 = cy - (int)(ssin * radius * 0.98);
          g.strokeLine(stx, sty, stx2, sty2);
        }
      }

      if (getScaleVisible())
      {
        double tx3 = cx + (int)(cos * radius * 0.90);
        double ty3 = cy - (int)(sin * radius * 0.90);
        
        String str = Integer.toString((int)(mn + (dv * i)));
        if (a == 0 || a == Math.PI) ty3 += sfont.getAscent() / 2;
        else if (a == HALF_PI) ty3 += sfont.getAscent();
        else if (a > 0 && a < Math.PI) ty3 += sfont.getAscent();
        
        if (a < 0) a = -a;

        if (a == HALF_PI || a == THREE_HALF_PI) tx3 -= sfont.width(str) / 2;
        else if (a >= 0 && a < HALF_PI) tx3 -= sfont.width(str);
        else if (a > THREE_HALF_PI && a <= TWO_PI) tx3 -= sfont.width(str);
        
        g.drawString(str, tx3, ty3);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make("kitPx");
  static BFont defValueFont = BFont.make(lex.getText("analogMeter.value.font"));
  static BFont defScaleFont = BFont.make(lex.getText("analogMeter.scale.font"));
  
  private static final double TO_RAD = 2 * Math.PI / 360;  
  private static final double HALF_PI = Math.PI / 2;
  private static final double THREE_HALF_PI = 3 * Math.PI / 2;
  private static final double TWO_PI  = 2 * Math.PI;

  private double size = 0;
  private double px   = 0;
  private double py   = 0;
  private double radius = 0f;
  private double cx = 0;
  private double cy = 0;
  private IGeom geom = null;
}
