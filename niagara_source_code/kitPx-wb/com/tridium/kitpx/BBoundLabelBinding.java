/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.converters.BFixedSimple;
import javax.baja.converters.BINumericToSimple;
import javax.baja.converters.BNumericToSimpleMap;
import javax.baja.converters.BPassThrough;
import javax.baja.gx.BColor;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BValueBinding;

import com.tridium.kitpx.enums.BStatusEffect;

/**
 * BBoundLabelBinding is designed to be used with BoundLabel to 
 * provide common functionality such as status color coding.
 *
 * @author    Brian Frank       
 * @creation  11 Sept 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:BoundLabel"
  )
)
@NiagaraProperty(
  name = "statusEffect",
  type = "BStatusEffect",
  defaultValue = "BStatusEffect.colorAndBlink"
)
public class BBoundLabelBinding
  extends BValueBinding
{                          
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BBoundLabelBinding(628491306)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "statusEffect"

  /**
   * Slot for the {@code statusEffect} property.
   * @see #getStatusEffect
   * @see #setStatusEffect
   */
  public static final Property statusEffect = newProperty(0, BStatusEffect.colorAndBlink, null);

  /**
   * Get the {@code statusEffect} property.
   * @see #statusEffect
   */
  public BStatusEffect getStatusEffect() { return (BStatusEffect)get(statusEffect); }

  /**
   * Set the {@code statusEffect} property.
   * @see #statusEffect
   */
  public void setStatusEffect(BStatusEffect v) { set(statusEffect, v, null); }

  //endregion Property "statusEffect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBoundLabelBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BValue getOnWidget(Property prop)  
  {                                        
    // if bound to a IStatus and using status effects
    BStatusEffect effect = getStatusEffect();
    if (isBound() && get() instanceof BIStatus && effect != BStatusEffect.none) 
    {
      // check status
      BStatus status = ((BIStatus)get()).getStatus();
      String name = prop.getName();
      
      if (name.equals("blink"))
      {
        if (status.isUnackedAlarm() && effect == BStatusEffect.colorAndBlink)
          return BBoolean.TRUE;
      }
      else if (name.equals("foreground"))
      {
        BColor fg = (BColor)status.getForegroundColor(null);
        if (fg != null) return fg.toBrush();
      }
      else if (name.equals("background"))
      {
        BColor bg = (BColor)status.getBackgroundColor(null);
        if (bg != null) return bg.toBrush();
      }  
      //Handle Borders as special case to account for BStatusBoolean value
      else if (name.equals("border") && get() instanceof BStatusBoolean)
      {
        
        BValue override = get(prop.getName());
        
        //check for fixed simple
        if( override instanceof BFixedSimple)
        {
          BFixedSimple fs = (BFixedSimple)override;
          return fs.getValue();
        }
        
        //check for pass through
        else if( override instanceof BPassThrough)
        {
          BPassThrough pt = (BPassThrough)override;
          BObject from = get();
          BObject to   = prop.getDefaultValue().newCopy();
          return (BValue)pt.convert(from, to);
        }
        
        //we can only deal with a numeric to simple override
        else if (override instanceof BINumericToSimple)
        {                       
          BStatusBoolean boolStat = (BStatusBoolean)get();
          BINumericToSimple converter = (BINumericToSimple)override;
          
          //The map retrieved from the BINumericToSimple converter contains
          //the mapping of ordinal values to associated Border objects to use.
          //The maximums and minimums are each an array of the lowest and highest
          //values of each RANGE included in the map. So, if there are 3 borders
          //associated in the map with a range of 0-5,6-10,11-15, the minimums 
          //array will hold the values [0,6,15] (though not necessarily in that order)
          BNumericToSimpleMap map = converter.getMap();
          double[] maximums = map.getMaximums();
          double[] minimums = map.getMinimums();
          
          
          //if our boolean status is true, return the border associated with
          //the highest range value of all ranges. Otherwise, return the border
          //associated with the lowest range value of all the ranges. The 
          //assumption is that the lowest range value should be associated as
          //FALSE or OFF, and the highest range should be associated with 
          //TRUE or ON (all the way)
          double numeric = 0;
          if( boolStat.getValue() )
            numeric = getHighest(maximums);
          else
            numeric = getLowest(minimums);
          
          return map.get(numeric);
        }
      }
    }
    return super.getOnWidget(prop);
  }
  
  /**
   * Get the lowest value from the array of minimum values.
   * 
   * @param minimums
   * @return
   */
  private double getLowest(double[] minimums)
  {
    //sanity check
    if( minimums.length < 1 ) return 0;
    
    double lowest = minimums[0];
    for( int i = 1; i < minimums.length; i++ )
    {
      if( minimums[i] < lowest)
        lowest = minimums[i];
    }
    return lowest;
  }
          
  /**
   * Get the greatest value from the array of maximum values
   * @param maximums
   * @return
   */
  private double getHighest(double[] maximums)
  {
    //sanity check
    if( maximums.length < 1 ) return 0;
    
    double biggest = maximums[0];
    for( int i = 1; i < maximums.length; i++ )
    {
      if( maximums[i] > biggest)
        biggest = maximums[i];
    }
    return biggest;
  }
          
}
