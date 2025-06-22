/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;

/**
 * BSpectrumBinding is used to animate a widget's brush property by
 * mapping a numeric value into a color range defined by by lowColor,
 * midColor, and highColor.
 *
 * @author    Brian Frank       
 * @creation  31 Aug 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:Widget"
  )
)
/*
 Slot name of widget property to animate
 */
@NiagaraProperty(
  name = "widgetProperty",
  type = "String",
  defaultValue = "fill"
)
/*
 The color to display when bound target is less than setpoint-extent/2
 */
@NiagaraProperty(
  name = "lowColor",
  type = "BColor",
  defaultValue = "BColor.blue"
)
/*
 The color to display when bound target is right at setpoint
 */
@NiagaraProperty(
  name = "midColor",
  type = "BColor",
  defaultValue = "BColor.white"
)
/*
 The color to display when bound target is greater than setpoint+extent/2
 */
@NiagaraProperty(
  name = "highColor",
  type = "BColor",
  defaultValue = "BColor.red"
)
/*
 The value to use for the midpoint of the range.  This variable
 can be animated using a SpectrumSetpointBinding.
 */
@NiagaraProperty(
  name = "setpoint",
  type = "double",
  defaultValue = "50"
)
/*
 This is the total range of the bound value which maps from low to high.
 */
@NiagaraProperty(
  name = "extent",
  type = "double",
  defaultValue = "100"
)
public class BSpectrumBinding
  extends BBinding
{                          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BSpectrumBinding(733835298)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "widgetProperty"

  /**
   * Slot for the {@code widgetProperty} property.
   * Slot name of widget property to animate
   * @see #getWidgetProperty
   * @see #setWidgetProperty
   */
  public static final Property widgetProperty = newProperty(0, "fill", null);

  /**
   * Get the {@code widgetProperty} property.
   * Slot name of widget property to animate
   * @see #widgetProperty
   */
  public String getWidgetProperty() { return getString(widgetProperty); }

  /**
   * Set the {@code widgetProperty} property.
   * Slot name of widget property to animate
   * @see #widgetProperty
   */
  public void setWidgetProperty(String v) { setString(widgetProperty, v, null); }

  //endregion Property "widgetProperty"

  //region Property "lowColor"

  /**
   * Slot for the {@code lowColor} property.
   * The color to display when bound target is less than setpoint-extent/2
   * @see #getLowColor
   * @see #setLowColor
   */
  public static final Property lowColor = newProperty(0, BColor.blue, null);

  /**
   * Get the {@code lowColor} property.
   * The color to display when bound target is less than setpoint-extent/2
   * @see #lowColor
   */
  public BColor getLowColor() { return (BColor)get(lowColor); }

  /**
   * Set the {@code lowColor} property.
   * The color to display when bound target is less than setpoint-extent/2
   * @see #lowColor
   */
  public void setLowColor(BColor v) { set(lowColor, v, null); }

  //endregion Property "lowColor"

  //region Property "midColor"

  /**
   * Slot for the {@code midColor} property.
   * The color to display when bound target is right at setpoint
   * @see #getMidColor
   * @see #setMidColor
   */
  public static final Property midColor = newProperty(0, BColor.white, null);

  /**
   * Get the {@code midColor} property.
   * The color to display when bound target is right at setpoint
   * @see #midColor
   */
  public BColor getMidColor() { return (BColor)get(midColor); }

  /**
   * Set the {@code midColor} property.
   * The color to display when bound target is right at setpoint
   * @see #midColor
   */
  public void setMidColor(BColor v) { set(midColor, v, null); }

  //endregion Property "midColor"

  //region Property "highColor"

  /**
   * Slot for the {@code highColor} property.
   * The color to display when bound target is greater than setpoint+extent/2
   * @see #getHighColor
   * @see #setHighColor
   */
  public static final Property highColor = newProperty(0, BColor.red, null);

  /**
   * Get the {@code highColor} property.
   * The color to display when bound target is greater than setpoint+extent/2
   * @see #highColor
   */
  public BColor getHighColor() { return (BColor)get(highColor); }

  /**
   * Set the {@code highColor} property.
   * The color to display when bound target is greater than setpoint+extent/2
   * @see #highColor
   */
  public void setHighColor(BColor v) { set(highColor, v, null); }

  //endregion Property "highColor"

  //region Property "setpoint"

  /**
   * Slot for the {@code setpoint} property.
   * The value to use for the midpoint of the range.  This variable
   * can be animated using a SpectrumSetpointBinding.
   * @see #getSetpoint
   * @see #setSetpoint
   */
  public static final Property setpoint = newProperty(0, 50, null);

  /**
   * Get the {@code setpoint} property.
   * The value to use for the midpoint of the range.  This variable
   * can be animated using a SpectrumSetpointBinding.
   * @see #setpoint
   */
  public double getSetpoint() { return getDouble(setpoint); }

  /**
   * Set the {@code setpoint} property.
   * The value to use for the midpoint of the range.  This variable
   * can be animated using a SpectrumSetpointBinding.
   * @see #setpoint
   */
  public void setSetpoint(double v) { setDouble(setpoint, v, null); }

  //endregion Property "setpoint"

  //region Property "extent"

  /**
   * Slot for the {@code extent} property.
   * This is the total range of the bound value which maps from low to high.
   * @see #getExtent
   * @see #setExtent
   */
  public static final Property extent = newProperty(0, 100, null);

  /**
   * Get the {@code extent} property.
   * This is the total range of the bound value which maps from low to high.
   * @see #extent
   */
  public double getExtent() { return getDouble(extent); }

  /**
   * Set the {@code extent} property.
   * This is the total range of the bound value which maps from low to high.
   * @see #extent
   */
  public void setExtent(double v) { setDouble(extent, v, null); }

  //endregion Property "extent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpectrumBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BBinding
////////////////////////////////////////////////////////////////

  public BValue getOnWidget(Property prop) 
  { 
    if (prop.getName().equals(getWidgetProperty()) && isBound())
    {                                                          
      BObject target = get();
      if (target instanceof BINumeric)
      {
        double value = ((BINumeric)target).getNumeric();
        BColor color = solveColor(value);
        
        if (prop.getType().is(BBrush.TYPE))
          return color.toBrush();
        
        if (prop.getType().is(BColor.TYPE))
          return color;
      }
    }
        
    return super.getOnWidget(prop);
  }   
        
////////////////////////////////////////////////////////////////
// Color Solver
////////////////////////////////////////////////////////////////
  
  /**
   * Solve the color to use for the specified value.
   */
  BColor solveColor(double value)
  {
    int red, blue, green, alpha;
    double mid = getSetpoint();
    double delta = getExtent()/2.0;
    BColor lowColor = getLowColor();
    BColor midColor = getMidColor();
    BColor highColor = getHighColor();

    // solve for the color using a linear equation y = mx + b,
    // the y axis is the value being monitored, and the x
    // axis is the color (red, green, or blue)
    if (value < mid)
    {
      // handle current over boundary
      if (value < mid-delta) return lowColor;

      double mRed = (midColor.getRed() - lowColor.getRed())/delta;
      double bRed = midColor.getRed() - mRed*mid;
      red = (int)(mRed*value + bRed);

      double mGreen = (midColor.getGreen() - lowColor.getGreen())/delta;
      double bGreen = midColor.getGreen() - mGreen*mid;
      green = (int)(mGreen*value + bGreen);

      double mBlue = (midColor.getBlue() - lowColor.getBlue())/delta;
      double bBlue = midColor.getBlue() - mBlue*mid;
      blue = (int)(mBlue*value + bBlue);

      double mAlpha = (midColor.getAlpha() - lowColor.getAlpha())/delta;
      double bAlpha = midColor.getAlpha() - mAlpha*mid;
      alpha = (int)(mAlpha*value + bAlpha);
    }

    else
    {
      // handle current over boundary
      if (value > mid+delta) return highColor;

      double mRed = (highColor.getRed() - midColor.getRed())/delta;
      double bRed = midColor.getRed() - mRed*mid;
      red = (int)(mRed*value + bRed);

      double mGreen = (highColor.getGreen() - midColor.getGreen())/delta;
      double bGreen = midColor.getGreen() - mGreen*mid;
      green = (int)(mGreen*value + bGreen);

      double mBlue = (highColor.getBlue() - midColor.getBlue())/delta;
      double bBlue = midColor.getBlue() - mBlue*mid;
      blue = (int)(mBlue*value + bBlue);

      double mAlpha = (highColor.getAlpha() - midColor.getAlpha())/delta;
      double bAlpha = midColor.getAlpha() - mAlpha*mid;
      alpha = (int)(mAlpha*value + bAlpha);
    }

    return BColor.make(red, green, blue, alpha);
  }  
  
}
