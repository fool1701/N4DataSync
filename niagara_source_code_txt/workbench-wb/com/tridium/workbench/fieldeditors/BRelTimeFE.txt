/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import static javax.baja.sys.BRelTime.SHOW_DAYS;

import java.util.ArrayList;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.workbench.CannotSaveException;

/**
 * BRelTimeFE allows viewing and editing of a BRelTime.
 *
 * @author    Brian Frank       
 * @creation  18 Jul 01
 * @version   $Revision: 19$ $Date: 7/1/11 10:59:55 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:RelTime"
  )
)
public class BRelTimeFE
  extends BMultiFieldFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BRelTimeFE(2299809066)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelTimeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BRelTimeFE()
  {
    bigHour = new BigHourField();
    minute = new MinuteField();
    second = new SecondField();
    regularHour = new Field()
      {
        int length() { return 2; }
        int max() { return 23; }
      };
    millisecond = new MillisecondField();
    day = new Field() 
      {
        int length() { return 3; }
        int max() { return 999; }
      };
    sign = new Field()
      {
        String string() { return (value == 0) ? "+" : "-"; }
        int length() { return 1; }
        int max() { return 1; }
      };        
  }

////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    min = Long.MIN_VALUE;
    max = Long.MAX_VALUE;
    long t = ((BRelTime)value).getMillis();

    if (cx == null)
    {
      fields = new Field[] 
      { 
        sign, 
        bigHour, 
        new LabelField("h "),
        minute, 
        new LabelField("m "),
        second, 
        new LabelField("."),
        millisecond, 
        new LabelField("s")
      };
    }
    else
    {
      BRelTime minFacet = (BRelTime)cx.getFacet(BFacets.MIN);
      if (minFacet != null)
      {
        min = minFacet.getMillis();
      }
        
      BRelTime maxFacet = (BRelTime)cx.getFacet(BFacets.MAX);
      if (maxFacet != null)
      {
        max = maxFacet.getMillis();
      }
      
      ArrayList<Field> fieldsList = new ArrayList<>();
      if(min < 0 || t < 0)
      {
        fieldsList.add(sign);
      }
      
      if (getContextShowDays(cx))
      {
        fieldsList.add(day);
        fieldsList.add(new LabelField("d "));
        fieldsList.add(regularHour);
      }
      else
      {
        fieldsList.add(bigHour);
      }
      fieldsList.add(new LabelField("h "));
      fieldsList.add(minute);
      fieldsList.add(new LabelField("m"));
      if (getBooleanFacetValue(cx, BFacets.SHOW_SECONDS, true))
      {
        fieldsList.add(new LabelField(" "));
        fieldsList.add(second);
        if (getBooleanFacetValue(cx, BFacets.SHOW_MILLISECONDS, false))
        {
          fieldsList.add(new LabelField("."));
          fieldsList.add(millisecond);
        }
        fieldsList.add(new LabelField("s"));
      }
      
      fields = new Field[fieldsList.size()];
      fieldsList.toArray(fields);
    }
    
    if (min != Long.MIN_VALUE || max != Long.MAX_VALUE)
    {
      range = "[";
      range += (min == Long.MIN_VALUE) ? "-inf" : BRelTime.make(min).toString();
      range += " - ";
      range += (max == Long.MAX_VALUE) ? "+inf" : BRelTime.make(max).toString();
      range += "]";
      
      datePickerButton = new BLabel(range);
      add(null, datePickerButton);
    }
    else
    {
      datePickerButton = null;
    }

    if (t < 0)
    {
      sign.set(1);
      t = -t;
    }
    else //NCCB-2553: BRelTimeFE shows incorrect Sign value
    {
      sign.set(0);
    }
      
    
    final long HOUR = BRelTime.MILLIS_IN_HOUR;
    final long MIN  = BRelTime.MILLIS_IN_MINUTE;
    final long SEC  = BRelTime.MILLIS_IN_SECOND;
    final long DAY  = BRelTime.MILLIS_IN_DAY;

    day.set( (int)(t / DAY) );
    regularHour.set( (int)((t % DAY) / HOUR));
    bigHour.set( (int)(t / HOUR) );
    minute.set( (int)((t % HOUR) / MIN) );
    second.set( (int)(((t % HOUR) % MIN) / SEC) );
    millisecond.set( (int)((((t % HOUR) % MIN) % SEC)) );
  }

  private static boolean getBooleanFacetValue(Context cx, String facetName, boolean def)
  {
    if (cx == null)
    {
      return def;
    }
    BBoolean value = (BBoolean)cx.getFacet(facetName);
    return (value == null) ? def : value.getBoolean();
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    long t = 0;
    if (fields[0] == day || fields[1] == day)
    {
      t += day.value * BRelTime.MILLIS_IN_DAY;
      t += regularHour.value * BRelTime.MILLIS_IN_HOUR;
    }
    else
    {
      t += bigHour.value * BRelTime.MILLIS_IN_HOUR;
    }
    t += minute.value * BRelTime.MILLIS_IN_MINUTE;
    t += second.value * BRelTime.MILLIS_IN_SECOND;
    t += millisecond.value;
    if (sign.value == 1)
    {
      t = -t;
    }
    
    if (t < min)
    {
      throw new CannotSaveException(BRelTime.make(t) + " < " + BRelTime.make(min) + " " + range);
    }
    if (t > max)
    {
      throw new CannotSaveException(BRelTime.make(t) + " > " + BRelTime.make(max) + " " + range);
    }
    return BRelTime.make(t);
  }

  /**
   * Retruns the value of the show days facet
   * @since Niagara 4.13
   * @param cx the current context
   * @return the value of the show days facet
   */
  public static boolean getContextShowDays(Context cx)
  {
    return getBooleanFacetValue(cx, SHOW_DAY, false) || getBooleanFacetValue(cx, SHOW_DAYS, false);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Field bigHour;
  private Field regularHour;
  private Field day;
  private Field minute;
  private Field second;
  private Field millisecond;
  private Field sign;
  
  private long min = Long.MIN_VALUE;
  private long max = Long.MAX_VALUE;
  private String range = "";

  public static final String SHOW_DAY = "showDay";  //If this ever changes, change BRELTIME_SHOW_DAY in weather:MoonPosition as well.
}
