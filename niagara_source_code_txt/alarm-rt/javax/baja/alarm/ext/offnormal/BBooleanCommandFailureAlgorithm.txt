/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

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
 * BBooleanCommandFailureAlgorithm implements command
 * failure alarm detection algorithm for boolean
 * objects as described in BACnet.  If feedback and output
 * values of the point are not equal for timeDelay duration,
 * an offnormal alarm is generated.
 * <p>
 *
 * @author    Dan Giorgis
 * @creation  04 May 01
 * @version   $Revision: 29$ $Date: 3/23/05 11:53:24 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Feedback Value
 */
@NiagaraProperty(
  name = "feedbackValue",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.SUMMARY
)
public class BBooleanCommandFailureAlgorithm
  extends BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BBooleanCommandFailureAlgorithm(738533870)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "feedbackValue"

  /**
   * Slot for the {@code feedbackValue} property.
   * Feedback Value
   * @see #getFeedbackValue
   * @see #setFeedbackValue
   */
  public static final Property feedbackValue = newProperty(Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code feedbackValue} property.
   * Feedback Value
   * @see #feedbackValue
   */
  public BStatusBoolean getFeedbackValue() { return (BStatusBoolean)get(feedbackValue); }

  /**
   * Set the {@code feedbackValue} property.
   * Feedback Value
   * @see #feedbackValue
   */
  public void setFeedbackValue(BStatusBoolean v) { set(feedbackValue, v, null); }

  //endregion Property "feedbackValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanCommandFailureAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BBooleanCommandFailureAlgorithm's grandparent must implement
   * the BooleanPoint interface
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BBooleanPoint);
  }

////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    
    if (!isRunning()) return;
    
    if (p == feedbackValue)
      executePoint();
  }  
  
////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the feedback value matches output value
   */
  @Override
  protected boolean isNormal(BStatusValue o)
  {
    BStatusBoolean out = (BStatusBoolean)o;
    return out.getValue() == getFeedbackValue().getValue();
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   * <p>
   *  The alarm data for a Command Failure alarm is given by
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
    map.put(BAlarmRecord.FEEDBACK_VALUE,  BString.make(getFeedbackValue().valueToString(getPointFacets())));
    map.put(BAlarmRecord.FEEDBACK_NUMERIC, BInteger.make(getFeedbackValue().getEnum().getOrdinal()));
  }

}
