/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BLoopAlarmAlgorithm implements a standard out-of-range
 * alarming algorithm
 *
 * @author    Dan Giorgis
 * @creation  25 May 01
 * @version   $Revision: 22$ $Date: 3/30/2004 3:31:03 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the error required to generate an alarm.
 */
@NiagaraProperty(
  name = "errorLimit",
  type = "double",
  defaultValue = "0"
)
/*
 Differential value applied to high and low limits before return-to-normal.
 Deadband is subtracted from highLimit and added to lowLimit.
 */
@NiagaraProperty(
  name = "deadband",
  type = "double",
  defaultValue = "0"
)
public class BLoopAlarmAlgorithm
  extends javax.baja.alarm.ext.offnormal.BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BLoopAlarmAlgorithm(303897419)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "errorLimit"

  /**
   * Slot for the {@code errorLimit} property.
   * This is the error required to generate an alarm.
   * @see #getErrorLimit
   * @see #setErrorLimit
   */
  public static final Property errorLimit = newProperty(0, 0, null);

  /**
   * Get the {@code errorLimit} property.
   * This is the error required to generate an alarm.
   * @see #errorLimit
   */
  public double getErrorLimit() { return getDouble(errorLimit); }

  /**
   * Set the {@code errorLimit} property.
   * This is the error required to generate an alarm.
   * @see #errorLimit
   */
  public void setErrorLimit(double v) { setDouble(errorLimit, v, null); }

  //endregion Property "errorLimit"

  //region Property "deadband"

  /**
   * Slot for the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #getDeadband
   * @see #setDeadband
   */
  public static final Property deadband = newProperty(0, 0, null);

  /**
   * Get the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #deadband
   */
  public double getDeadband() { return getDouble(deadband); }

  /**
   * Set the {@code deadband} property.
   * Differential value applied to high and low limits before return-to-normal.
   * Deadband is subtracted from highLimit and added to lowLimit.
   * @see #deadband
   */
  public void setDeadband(double v) { setDouble(deadband, v, null); }

  //endregion Property "deadband"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLoopAlarmAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BLoopAlarmAlgorithm's grandparent must be a
   * BLoopPoint
   */
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BLoopPoint);
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == deadband  ||
        slot == errorLimit   )
    {
      BLoopPoint point = (BLoopPoint)getParentPoint();
      if (point != null) return point.getInputFacets();
      return BFacets.DEFAULT;
    }
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value is normal
   */
  protected boolean isNormal(BStatusValue out)
  {
    BLoopPoint loopPt = (BLoopPoint)getParentPoint();

    double error = Math.abs((loopPt.getSetpoint().getValue() -
                            loopPt.getControlledVariable().getValue()));

    //  If in alarm / offnormal state, must return to
    //  to within (error - deadband)
    if (isCurrentOffnormal())
      return (error < (getErrorLimit() - getDeadband()));
    else
      return (error < getErrorLimit());

  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   * <p>
   *  The alarm data for an Out of Range alarm is given by
   *  BACnet table 13-3, Standard Object Property Values
   *  returned in notifications.
   *
   * @param cp The relevant control point.
   * @param map The map.
   */
  @SuppressWarnings({"rawtypes","unchecked"})
  @Override
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    BLoopPoint loopPt = (BLoopPoint)getParentPoint();

    map.put(BAlarmRecord.CONTROLLED_VALUE, BString.make(loopPt.getControlledVariable().valueToString(loopPt.getInputFacets())));
    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
    map.put(BAlarmRecord.SETPT_VALUE, BString.make(loopPt.getSetpoint().valueToString(loopPt.getInputFacets())));
    map.put(BAlarmRecord.DEADBAND, BString.make(BDouble.toString(getDeadband(), loopPt.getInputFacets())));
    map.put(BAlarmRecord.ERROR_LIMIT, BString.make(BDouble.toString(getErrorLimit(), loopPt.getInputFacets())));
  }
}
