/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import javax.baja.alarm.*;
import javax.baja.control.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BEnumChangeOfStateAlgorithm implements a change of state
 * alarm detection algorithm for multistate objects as described
 * in BACnet Clause 13.3.2.
 * <p>
 * Each algorithm instance defines a set of enumerated values
 * that should be considered "offnormal" conditions and
 * therefore generate an alarm.
 *
 * @author    Dan Giorgis
 * @creation  03 May 01
 * @version   $Revision: 27$ $Date: 10/5/07 1:31:44 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Set of all offnormal states
 */
@NiagaraProperty(
  name = "alarmValues",
  type = "BEnumRange",
  defaultValue = "BEnumRange.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:EnumAlarmRangeFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"alarm:EnumAlarmRangeEditor\"))")
)
public class BEnumChangeOfStateAlgorithm
  extends BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BEnumChangeOfStateAlgorithm(109867495)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmValues"

  /**
   * Slot for the {@code alarmValues} property.
   * Set of all offnormal states
   * @see #getAlarmValues
   * @see #setAlarmValues
   */
  public static final Property alarmValues = newProperty(0, BEnumRange.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:EnumAlarmRangeFE"), BFacets.UX_FIELD_EDITOR, BString.make("alarm:EnumAlarmRangeEditor")));

  /**
   * Get the {@code alarmValues} property.
   * Set of all offnormal states
   * @see #alarmValues
   */
  public BEnumRange getAlarmValues() { return (BEnumRange)get(alarmValues); }

  /**
   * Set the {@code alarmValues} property.
   * Set of all offnormal states
   * @see #alarmValues
   */
  public void setAlarmValues(BEnumRange v) { set(alarmValues, v, null); }

  //endregion Property "alarmValues"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumChangeOfStateAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BEnumChangeOfStateAlgorithm's grandparent must be an EnumPoint.
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BEnumPoint);
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == alarmValues)
      return BFacets.make(getPointFacets(), super.getSlotFacets(slot));

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
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value is normal
   */
  @Override
  protected boolean isNormal(BStatusValue o)
  {
    BStatusEnum out = (BStatusEnum)o;

    BDynamicEnum ms = out.getValue();
    int currentOrdinal = ms.getOrdinal();
    int[] alarmOrdinals = getAlarmValues().getOrdinals();
    for (int alarmOrdinal : alarmOrdinals)
    {
      if (currentOrdinal == alarmOrdinal)
        return false;
    }
    return true;
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
    map.put(BAlarmRecord.NUMERIC_VALUE, BInteger.make(((BStatusEnum)out).getValue().getOrdinal()));
  }


}
