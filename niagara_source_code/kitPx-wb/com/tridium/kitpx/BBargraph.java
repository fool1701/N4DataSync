/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BOrientation;
import javax.baja.util.Lexicon;

/**
 * BBargraph
 *
 * @author    Brian Frank
 * @creation  26 Dec 01
 * @version   $Revision$ $Date: 5/13/2004 6:38:08 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 The current value for the graph.
 */
@NiagaraProperty(
  name = "value",
  type = "double",
  defaultValue = "0"
)
/*
 The minimum value to display on the graph.
 */
@NiagaraProperty(
  name = "min",
  type = "double",
  defaultValue = "0"
)
/*
 The maximum value to display on the graph.
 */
@NiagaraProperty(
  name = "max",
  type = "double",
  defaultValue = "100"
)
/*
 The text value used to display current value.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = ""
)
/*
 Toggle the visibility of the current value on the graph.
 */
@NiagaraProperty(
  name = "valueVisible",
  type = "boolean",
  defaultValue = "true"
)
/*
 The font used to render the current value.
 */
@NiagaraProperty(
  name = "valueFont",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
/*
 Brush used to fill the current value
 of the graph.
 */
@NiagaraProperty(
  name = "fill",
  type = "BBrush",
  defaultValue = "BColor.make(0x666699).toBrush()"
)
/*
 Brush used for outline and text of graph.
 */
@NiagaraProperty(
  name = "foreground",
  type = "BBrush",
  defaultValue = "BColor.black.toBrush()"
)
/*
 Brush used to fill the background of the graph.
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Orientation defines whether the graph is horizonal or vertical.
 */
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.vertical"
)
/*
 The increment to use for scale divisions.
 Auto-scaling will occur if this value is set to "0".
 */
@NiagaraProperty(
  name = "scale",
  type = "double",
  defaultValue = "10"
)
/*
 Font used to render major scale values.
 */
@NiagaraProperty(
  name = "scaleFont",
  type = "BFont",
  defaultValue = "BFont.NULL"
)
/*
 Is the scale displayed.
 */
@NiagaraProperty(
  name = "scaleVisible",
  type = "boolean",
  defaultValue = "true"
)
public class BBargraph
  extends BWidget
{   
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BBargraph(707754392)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The current value for the graph.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 0, null);

  /**
   * Get the {@code value} property.
   * The current value for the graph.
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * The current value for the graph.
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Property "min"

  /**
   * Slot for the {@code min} property.
   * The minimum value to display on the graph.
   * @see #getMin
   * @see #setMin
   */
  public static final Property min = newProperty(0, 0, null);

  /**
   * Get the {@code min} property.
   * The minimum value to display on the graph.
   * @see #min
   */
  public double getMin() { return getDouble(min); }

  /**
   * Set the {@code min} property.
   * The minimum value to display on the graph.
   * @see #min
   */
  public void setMin(double v) { setDouble(min, v, null); }

  //endregion Property "min"

  //region Property "max"

  /**
   * Slot for the {@code max} property.
   * The maximum value to display on the graph.
   * @see #getMax
   * @see #setMax
   */
  public static final Property max = newProperty(0, 100, null);

  /**
   * Get the {@code max} property.
   * The maximum value to display on the graph.
   * @see #max
   */
  public double getMax() { return getDouble(max); }

  /**
   * Set the {@code max} property.
   * The maximum value to display on the graph.
   * @see #max
   */
  public void setMax(double v) { setDouble(max, v, null); }

  //endregion Property "max"

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The text value used to display current value.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", null);

  /**
   * Get the {@code text} property.
   * The text value used to display current value.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The text value used to display current value.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "valueVisible"

  /**
   * Slot for the {@code valueVisible} property.
   * Toggle the visibility of the current value on the graph.
   * @see #getValueVisible
   * @see #setValueVisible
   */
  public static final Property valueVisible = newProperty(0, true, null);

  /**
   * Get the {@code valueVisible} property.
   * Toggle the visibility of the current value on the graph.
   * @see #valueVisible
   */
  public boolean getValueVisible() { return getBoolean(valueVisible); }

  /**
   * Set the {@code valueVisible} property.
   * Toggle the visibility of the current value on the graph.
   * @see #valueVisible
   */
  public void setValueVisible(boolean v) { setBoolean(valueVisible, v, null); }

  //endregion Property "valueVisible"

  //region Property "valueFont"

  /**
   * Slot for the {@code valueFont} property.
   * The font used to render the current value.
   * @see #getValueFont
   * @see #setValueFont
   */
  public static final Property valueFont = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code valueFont} property.
   * The font used to render the current value.
   * @see #valueFont
   */
  public BFont getValueFont() { return (BFont)get(valueFont); }

  /**
   * Set the {@code valueFont} property.
   * The font used to render the current value.
   * @see #valueFont
   */
  public void setValueFont(BFont v) { set(valueFont, v, null); }

  //endregion Property "valueFont"

  //region Property "fill"

  /**
   * Slot for the {@code fill} property.
   * Brush used to fill the current value
   * of the graph.
   * @see #getFill
   * @see #setFill
   */
  public static final Property fill = newProperty(0, BColor.make(0x666699).toBrush(), null);

  /**
   * Get the {@code fill} property.
   * Brush used to fill the current value
   * of the graph.
   * @see #fill
   */
  public BBrush getFill() { return (BBrush)get(fill); }

  /**
   * Set the {@code fill} property.
   * Brush used to fill the current value
   * of the graph.
   * @see #fill
   */
  public void setFill(BBrush v) { set(fill, v, null); }

  //endregion Property "fill"

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * Brush used for outline and text of graph.
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, BColor.black.toBrush(), null);

  /**
   * Get the {@code foreground} property.
   * Brush used for outline and text of graph.
   * @see #foreground
   */
  public BBrush getForeground() { return (BBrush)get(foreground); }

  /**
   * Set the {@code foreground} property.
   * Brush used for outline and text of graph.
   * @see #foreground
   */
  public void setForeground(BBrush v) { set(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * Brush used to fill the background of the graph.
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code background} property.
   * Brush used to fill the background of the graph.
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * Brush used to fill the background of the graph.
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * Orientation defines whether the graph is horizonal or vertical.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.vertical, null);

  /**
   * Get the {@code orientation} property.
   * Orientation defines whether the graph is horizonal or vertical.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * Orientation defines whether the graph is horizonal or vertical.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * The increment to use for scale divisions.
   * Auto-scaling will occur if this value is set to "0".
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, 10, null);

  /**
   * Get the {@code scale} property.
   * The increment to use for scale divisions.
   * Auto-scaling will occur if this value is set to "0".
   * @see #scale
   */
  public double getScale() { return getDouble(scale); }

  /**
   * Set the {@code scale} property.
   * The increment to use for scale divisions.
   * Auto-scaling will occur if this value is set to "0".
   * @see #scale
   */
  public void setScale(double v) { setDouble(scale, v, null); }

  //endregion Property "scale"

  //region Property "scaleFont"

  /**
   * Slot for the {@code scaleFont} property.
   * Font used to render major scale values.
   * @see #getScaleFont
   * @see #setScaleFont
   */
  public static final Property scaleFont = newProperty(0, BFont.NULL, null);

  /**
   * Get the {@code scaleFont} property.
   * Font used to render major scale values.
   * @see #scaleFont
   */
  public BFont getScaleFont() { return (BFont)get(scaleFont); }

  /**
   * Set the {@code scaleFont} property.
   * Font used to render major scale values.
   * @see #scaleFont
   */
  public void setScaleFont(BFont v) { set(scaleFont, v, null); }

  //endregion Property "scaleFont"

  //region Property "scaleVisible"

  /**
   * Slot for the {@code scaleVisible} property.
   * Is the scale displayed.
   * @see #getScaleVisible
   * @see #setScaleVisible
   */
  public static final Property scaleVisible = newProperty(0, true, null);

  /**
   * Get the {@code scaleVisible} property.
   * Is the scale displayed.
   * @see #scaleVisible
   */
  public boolean getScaleVisible() { return getBoolean(scaleVisible); }

  /**
   * Set the {@code scaleVisible} property.
   * Is the scale displayed.
   * @see #scaleVisible
   */
  public void setScaleVisible(boolean v) { setBoolean(scaleVisible, v, null); }

  //endregion Property "scaleVisible"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBargraph.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constuctor
////////////////////////////////////////////////////////////////

  public BBargraph()
  {
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void paint(Graphics g)
  {
    double w = getWidth();
    double h = getHeight();
        
    BBrush bg = getBackground();
    if (!bg.isNull())
    {
      g.setBrush(bg);
      g.fillRect(0,0,w,h);
    }
    
    if (getOrientation() == BOrientation.horizontal)
      paintHoriz(g);
    else
      paintVert(g);
      
    BBrush fg = getForeground();
    if (!fg.isNull())
    {
      g.setBrush(fg);
      g.strokeRect(0,0,w-1,h-1);
    }    
  }

////////////////////////////////////////////////////////////////
// Horizontal
////////////////////////////////////////////////////////////////
  
  private void paintHoriz(Graphics g) {
    double w = getWidth();
    double h = getHeight();
    double v = getValue();
    double min = getMin();
    double max = getMax();
    
    if (max <= min) return ;
    if (v < min) v = min;
    if (v > max) v = max;
    
    double x = ((v-min) / (max-min)) * w;
    g.setBrush(getFill());
    g.fillRect(0, 0, x, h);
    
    if (getValueVisible()) {
      String s = getText();
      BFont font = getValueFont();
      if (font.isNull()) font = defValueFont;
      
      double tx = x - font.width(s) - 3;
      double ty = (h - font.getAscent()) / 2 + font.getAscent();
      if (tx < 2) tx = x + 3;
      
      g.setBrush(getForeground());
      g.setFont(font);
      g.drawString(s, tx, ty);
    }
    
    double scale = getScale();
    if( scale == 0.0 ) {
      scale = calcAutoScale( min, max );
    }
    
    if (getScaleVisible() && (scale > 0.0)) {
      double num = (max-min) / scale;
      double space = w / num;
      long roundMultiplier = 10;
      
      BFont font = getScaleFont();
      if (font.isNull()) font = defScaleFont;
      
      g.setBrush(getForeground());
      g.setFont(font);
      
      roundMultiplier = calcRoundMultiplier(scale);
      for (int i=0; i<num; i++) {
        double dx = i * space;
        g.strokeLine(dx, h-5, dx, h);
        
        if (i>0 && i<num) {
          String s = "" + (double)((long)(((i*scale)+min)*roundMultiplier))/roundMultiplier;
          if(s.endsWith(".0"))
            s = s.substring(0, s.length()-2);
          double tx = dx - (font.width(s) / 2);
          double ty = h - 8;
          g.drawString(s, tx, ty);
        }
        
      }
    }
  }
  
////////////////////////////////////////////////////////////////
// Vertical
////////////////////////////////////////////////////////////////
  
  private void paintVert(Graphics g) {
    double w = getWidth();
    double h = getHeight();
    double v = getValue();
    double min = getMin();
    double max = getMax();
    
    if(max <= min) return ;
    if (v < min) v = min;
    if (v > max) v = max;
    
    double y = h - ((v-min) / (max-min)) * h;
    g.setBrush(getFill());
    g.fillRect(0, y, w, h-y);
    
    if (getValueVisible()) {
      String s = getText();
      BFont font = getValueFont();
      if (font.isNull()) font = defValueFont;
      
      double tx = (w - font.width(s)) / 2;
      double ty = y + font.getAscent() + 3;
      if (ty > (h-2)) ty = y - 4;
      
      g.setBrush(getForeground());
      g.setFont(font);
      g.drawString(s, tx, ty);
    }
    
    double scale = getScale();
    if(scale == 0.0) {
      scale = calcAutoScale(min,max);
    }
    
    if (getScaleVisible() && (scale > 0.0)) {
      double num = (max-min) / scale;
      double space = h / num;
      long roundMultiplier = 10;
      
      BFont font = getScaleFont();
      if (font.isNull()) font = defScaleFont;
      
      g.setBrush(getForeground());
      g.setFont(font);
      
      roundMultiplier = calcRoundMultiplier(scale);
      for (int i=0; i<num; i++) {
        double dy = i * space;
        g.strokeLine(w-5, dy, w, dy);
        
        if (i>0 && i<num) {
          String s = "" + (double)((long)((((num-i)*scale)+min)*roundMultiplier))/roundMultiplier;
          if(s.endsWith(".0"))
            s = s.substring(0, s.length()-2);
          double tx = w - font.width(s) - 8;
          double ty = dy + (font.getAscent() / 2)-1;
          g.drawString(s, tx, ty);
        }
      }
    }
  }
  
  /**
   * there are two cases to handle -
   * 1) delta > 1.0
   * 2) delta <= 1.0
   *
   * In both cases we attempt to squeeze to a range of [MIN_TICKS, MAX_TICKS],
   * but ultimately it is more important to have less than MAX_TICKS than
   * to ensure we have more than MIN_TICKS
   *
   * DETAILS: tickCount is guaranteed to be no more than 10, hence we only
   * have to check if tickCount is less than our arbitrarily chosen
   * value of MIN_TICKS.  However, in the case we need to adjust the
   * tickIncrement because (tickCount < MIN_TICKS), it is possible that we
   * may overshoot MAX_TICKS during the correction;  hence the need to 
   * "squeeze".
   *
   */
  private double calcAutoScale(double min, double max) {
    final int MIN_TICKS = 4;
    final int MAX_TICKS= 10;
    
    double delta = max - min;
    double log10 = Math.log(delta) * LOG10E;
    
    double tickIncrement = Math.max(
            (float)Math.pow(10,(long)log10), Float.MIN_VALUE );
    int tickCount = (int)(delta / tickIncrement);
    
    // at the completion of this block of code we are guaranteed
    // that (tickCount < MAX_TICKS) -- which is the primary goal.
    if( delta >  1.0 ) {
      if( tickCount < MIN_TICKS ) {
        tickIncrement /= 10.0;
        while(tickCount < MIN_TICKS) {
          tickIncrement *= 2;
          tickCount = (int)( delta / tickIncrement ) + 1;
        }
        // squeeze
        while(tickCount > MAX_TICKS) {
          tickIncrement *= 2;
          tickCount = (int)( delta / tickIncrement ) + 1;
        }
      }
    } else { // <= 1.0
      if( tickCount < MIN_TICKS ) {
        tickIncrement /= 10.0;
        while( tickCount < MIN_TICKS ) {
          tickIncrement /= 2;
          tickCount = (int)( delta / tickIncrement ) + 1;
        }
        // squeeze
        while(tickCount > MAX_TICKS) {
          tickIncrement *= 2;
          tickCount = (int)( delta / tickIncrement ) + 1;
        }
      }
    }
    
    return tickIncrement;
  }
  
  /**
   * returns a power of 10 that can be used to do reasonable
   * roudning of decimal numbers when printing the ticks
   * on the bargraph.
   */
  private long calcRoundMultiplier(double scale) {
    long multiplier = 10;
    if(scale < 1.0) {
      while( (int)scale < 1.0 ) {
        scale /= 0.1;
        multiplier *= 10;
      }
    }
    return multiplier;
  }
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////
  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("charts/bar.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

	private static final double LOG10E = 0.4342944819018;
	
  static Lexicon lex = Lexicon.make("kitPx");
  static BFont defValueFont = BFont.make(lex.getText("bargraph.value.font"));
  static BFont defScaleFont = BFont.make(lex.getText("bargraph.scale.font"));
}
