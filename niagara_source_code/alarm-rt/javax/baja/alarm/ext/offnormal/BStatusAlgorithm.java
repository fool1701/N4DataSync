/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import java.util.Map;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStatusAlgorithm allows alarming based on the ControlsPoint's status value.
 *
 * @author    Blake M Puhak
 * @creation  15 Dec 04
 * @version   $Revision: 6$ $Date: 7/7/11 1:17:54 PM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 Set of allowed offnormal statuses.  "Disabled" and "Stale" are excluded because
 BAlarmSourceExt does not evaluate alarm algorithms if the control point's status is one of
 these status values.
 */
@NiagaraProperty(
  name = "alarmValues",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  facets = @Facet("BFacets.make(\"filter\", BInteger.make(0xff & ~BStatus.DISABLED & ~BStatus.STALE))")
)
public class BStatusAlgorithm
  extends BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BStatusAlgorithm(3900581388)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmValues"

  /**
   * Slot for the {@code alarmValues} property.
   * Set of allowed offnormal statuses.  "Disabled" and "Stale" are excluded because
   * BAlarmSourceExt does not evaluate alarm algorithms if the control point's status is one of
   * these status values.
   * @see #getAlarmValues
   * @see #setAlarmValues
   */
  public static final Property alarmValues = newProperty(0, BStatus.ok, BFacets.make("filter", BInteger.make(0xff & ~BStatus.DISABLED & ~BStatus.STALE)));

  /**
   * Get the {@code alarmValues} property.
   * Set of allowed offnormal statuses.  "Disabled" and "Stale" are excluded because
   * BAlarmSourceExt does not evaluate alarm algorithms if the control point's status is one of
   * these status values.
   * @see #alarmValues
   */
  public BStatus getAlarmValues() { return (BStatus)get(alarmValues); }

  /**
   * Set the {@code alarmValues} property.
   * Set of allowed offnormal statuses.  "Disabled" and "Stale" are excluded because
   * BAlarmSourceExt does not evaluate alarm algorithms if the control point's status is one of
   * these status values.
   * @see #alarmValues
   */
  public void setAlarmValues(BStatus v) { set(alarmValues, v, null); }

  //endregion Property "alarmValues"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    
    if (!isRunning())
    {
      return;
    }

    executePoint();
  }  

////////////////////////////////////////////////////////////////
//  Offnormal transition checking
////////////////////////////////////////////////////////////////

  @SuppressWarnings({"rawtypes","unchecked"})
  @Override
  public void writeAlarmData(BStatusValue out, Map map)
  {
    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
    if (!isNormal(out))
    {
      map.put(BAlarmRecord.OFFNORMAL_VALUE, BString.make(out.getStatus().toString(null)));
    }
    map.put(BAlarmRecord.NUMERIC_VALUE, BInteger.make(out.getStatus().getBits()));
    map.put(BAlarmRecord.PRESENT_VALUE, BString.make(out.getStatus().toString(null)));
  }
  
  @Override
  protected boolean isNormal(BStatusValue out)
  {
    return (out.getStatus().getBits() & getAlarmValues().getBits()) == 0;
  }
}
