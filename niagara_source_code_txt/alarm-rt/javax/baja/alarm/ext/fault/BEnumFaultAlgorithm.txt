/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.fault;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BEnumFaultAlgorithm implements a change of state
 * fault detection algorithm for enum objects as described
 * in BACnet Clause 13.3.2.
 * <p>
 * Each algorithm instance defines a set of enumerated values
 * that should be considered "normal" conditions and any other
 * conditions therefore generate a fault alarm.
 *
 * @author    Blake M Puhak
 * @creation  08 Nov 04
 * @version   $Revision: 5$ $Date: 3/23/05 11:53:23 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Set of all offnormal states
 */
@NiagaraProperty(
  name = "validValues",
  type = "BEnumRange",
  defaultValue = "BEnumRange.make(new String[]{\"INITIAL_VALUE\"})",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"alarm:EnumAlarmRangeFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"alarm:EnumAlarmRangeEditor\"))")
)
public class BEnumFaultAlgorithm
  extends BTwoStateFaultAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.fault.BEnumFaultAlgorithm(618889115)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "validValues"

  /**
   * Slot for the {@code validValues} property.
   * Set of all offnormal states
   * @see #getValidValues
   * @see #setValidValues
   */
  public static final Property validValues = newProperty(0, BEnumRange.make(new String[]{"INITIAL_VALUE"}), BFacets.make(BFacets.FIELD_EDITOR, BString.make("alarm:EnumAlarmRangeFE"), BFacets.UX_FIELD_EDITOR, BString.make("alarm:EnumAlarmRangeEditor")));

  /**
   * Get the {@code validValues} property.
   * Set of all offnormal states
   * @see #validValues
   */
  public BEnumRange getValidValues() { return (BEnumRange)get(validValues); }

  /**
   * Set the {@code validValues} property.
   * Set of all offnormal states
   * @see #validValues
   */
  public void setValidValues(BEnumRange v) { set(validValues, v, null); }

  //endregion Property "validValues"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumFaultAlgorithm.class);

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

  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == validValues)
      return BFacets.make(getPointFacets(), super.getSlotFacets(slot));

    return super.getSlotFacets(slot);
  }
  
////////////////////////////////////////////////////////////////
//  
////////////////////////////////////////////////////////////////

  @Override
  public void started()
  {
    if (getValidValues().equals(BEnumRange.make(new String[]{"INITIAL_VALUE"})))
      setValidValues((BEnumRange)getPointFacets().get("range"));
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
    int[] validOrdinals = getValidValues().getOrdinals();
    for (int validOrdinal : validOrdinals)
    {
      if (currentOrdinal == validOrdinal)
        return true;
    }
    return false;
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
