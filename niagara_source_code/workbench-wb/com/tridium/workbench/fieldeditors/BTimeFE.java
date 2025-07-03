/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BTime;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.timezone.BTimeZone;

/**
 * BTimeFE allows viewing and editing of a BTime, or
 * BAbsTime restricted to hour, mintue, seconds.
 *
 * @author    Brian Frank       
 * @creation  18 Jul 01
 * @version   $Revision: 6$ $Date: 1/30/08 9:32:38 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:Time", "baja:AbsTime" }
  )
)
public class BTimeFE
  extends BAbsTimeFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTimeFE(2888451822)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTimeFE()
  {
  }

  public BTimeFE(BTime time)
  {
    loadValue(time);
  }
  
  public BTimeFE(BAbsTime abs)
  {
    loadValue(abs);
  }
  
////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    BBoolean showSeconds = BBoolean.TRUE;
    BTimeZone timeZone = BTimeZone.getLocal();
    if (cx != null) 
    {
      BBoolean b = (BBoolean)cx.getFacet(BFacets.SHOW_SECONDS);
      if (b != null) showSeconds = b;           
      
      BTimeZone tz = (BTimeZone)cx.getFacet(BFacets.TIME_ZONE);
      if (tz != null) timeZone = tz;
    }
    
    BAbsTime abs;
    if (value instanceof BTime)
    {                        
      abs = BAbsTime.make(System.currentTimeMillis(), timeZone);
      abs = BAbsTime.make(abs, (BTime)value);
    }
    else
    {
      abs = (BAbsTime)value;   
    }

    BFacets facets = BFacets.make(BFacets.SHOW_DATE, BBoolean.FALSE);
    facets = BFacets.make(facets, BFacets.SHOW_TIME, BBoolean.TRUE);
    facets = BFacets.make(facets, BFacets.SHOW_SECONDS, showSeconds);
    super.doLoadValue(abs, new BasicContext(cx, facets));
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {                  
    BAbsTime abs = (BAbsTime)super.doSaveValue(value, cx);

    if (value instanceof BTime) 
    {
      return BTime.make(abs);
    }
    else
    {
      return abs;
    }
  }
}
