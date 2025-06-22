/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.control.ext.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BElapsedActiveTimeAlarmAlgorithm implements a standard out-of-range
 * alarming algorithm
 *
 * @author    Andy Saunders
 * @creation  19 Nov 2004
 * @version   $Revision: 22$ $Date: 3/30/2004 3:31:03 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the elapsedActiveTime required to generate an alarm.
 */
@NiagaraProperty(
  name = "errorLimit",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)"
)
public class BElapsedActiveTimeAlarmAlgorithm
  extends BDiscreteTotalizerAlarmAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BElapsedActiveTimeAlarmAlgorithm(3756655203)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "errorLimit"

  /**
   * Slot for the {@code errorLimit} property.
   * This is the elapsedActiveTime required to generate an alarm.
   * @see #getErrorLimit
   * @see #setErrorLimit
   */
  public static final Property errorLimit = newProperty(0, BRelTime.make(0), null);

  /**
   * Get the {@code errorLimit} property.
   * This is the elapsedActiveTime required to generate an alarm.
   * @see #errorLimit
   */
  public BRelTime getErrorLimit() { return (BRelTime)get(errorLimit); }

  /**
   * Set the {@code errorLimit} property.
   * This is the elapsedActiveTime required to generate an alarm.
   * @see #errorLimit
   */
  public void setErrorLimit(BRelTime v) { set(errorLimit, v, null); }

  //endregion Property "errorLimit"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BElapsedActiveTimeAlarmAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context c)
  {
    if(isRunning())
    {
      if( p.equals(errorLimit))
      {
        BBooleanPoint point = (BBooleanPoint)getParentPoint();
        if(point != null)
          point.execute();
      }
    }
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.equals(errorLimit)   )
    {
      return timeFacet;
    }
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value is normal
   */
  protected boolean isNormal(BDiscreteTotalizerExt totalExt)
  {
    return ( getErrorLimit().getMillis() > totalExt.getElapsedActiveTime().getMillis() );
  }


  protected String getAlarmPresentValueString(BDiscreteTotalizerExt totalizerExt)
  {
    return totalizerExt.getElapsedActiveTime().toString(timeContext);
  }

  protected String getAlarmErrorLimitString()
  {
    return getErrorLimit().toString(timeContext);
  }


  private static BFacets timeFacet = BFacets.make(BFacets.SHOW_DATE, BBoolean.make(false), BFacets.SHOW_MILLISECONDS, BBoolean.make(false));
  private static Context timeContext = new BasicContext(new BasicContext(), timeFacet);
}
