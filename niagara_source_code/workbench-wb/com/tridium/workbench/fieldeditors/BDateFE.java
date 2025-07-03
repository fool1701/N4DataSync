/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Customized AbsTimeFE to only show/edit the year, month, and day.
 *
 * @author    Brian Frank       
 * @creation  18 Jul 01
 * @version   $Revision: 7$ $Date: 3/28/05 1:40:34 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDateFE
  extends BAbsTimeFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDateFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDateFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDateFE()
  {
  }

  public BDateFE(BAbsTime time)
  {
    this();
    loadValue(time);
  }
  
////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    BFacets facets = BFacets.make(BFacets.SHOW_DATE, BBoolean.TRUE, BFacets.SHOW_TIME_ZONE, BBoolean.FALSE);
    facets = BFacets.make(facets, BFacets.SHOW_TIME, BBoolean.FALSE);
    super.doLoadValue(value, new BasicContext(cx, facets));
  }      
}
