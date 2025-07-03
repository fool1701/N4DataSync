/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import javax.baja.alarm.*;
import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BBooleanChangeOfStateAlgorithm implements a change of
 * state alarm detection algorithm for boolean objects
 * as described in BACnet Clause 13.3.2.
 *
 * @author    Dan Giorgis
 * @creation  03 May 01
 * @version   $Revision: 27$ $Date: 8/12/05 11:26:22 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Set of all offnormal states
 */
@NiagaraProperty(
  name = "alarmValue",
  type = "boolean",
  defaultValue = "true"
)
public class BBooleanChangeOfStateAlgorithm
  extends BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BBooleanChangeOfStateAlgorithm(3072605402)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmValue"

  /**
   * Slot for the {@code alarmValue} property.
   * Set of all offnormal states
   * @see #getAlarmValue
   * @see #setAlarmValue
   */
  public static final Property alarmValue = newProperty(0, true, null);

  /**
   * Get the {@code alarmValue} property.
   * Set of all offnormal states
   * @see #alarmValue
   */
  public boolean getAlarmValue() { return getBoolean(alarmValue); }

  /**
   * Set the {@code alarmValue} property.
   * Set of all offnormal states
   * @see #alarmValue
   */
  public void setAlarmValue(boolean v) { setBoolean(alarmValue, v, null); }

  //endregion Property "alarmValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanChangeOfStateAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BBooleanChangeOfStateAlgorithm's grandparent must implement
   * the BooleanPoint interface
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BBooleanPoint);
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == alarmValue) return getPointFacets();
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    
    if (!isRunning()) return;

    executePoint();
  }  

////////////////////////////////////////////////////////////////
//  Offnormal transition checking
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value
   */
  @Override
  protected boolean isNormal(BStatusValue o)
  {
    BStatusBoolean out = (BStatusBoolean)o;
    if (out.getStatus().isNull()) return true;
    return out.getValue() != getAlarmValue();
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   * <p>
   *  The alarm data for a Change Of State alarm is given by
   *  BACnet table 13-3, Standard Object Property Values
   *  returned in notifications.
   *
   * @param out The relevant control point status value
   * @param map The map.
   */
  @Override
  @SuppressWarnings({"rawtypes","unchecked"})
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
    map.put(BAlarmRecord.NUMERIC_VALUE, BInteger.make(((BStatusBoolean)out).getEnum().getOrdinal()));
  }
}
