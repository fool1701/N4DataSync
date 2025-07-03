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
 * BEnumCommandFailureAlgorithm implements command failure
 * alarm detection algorithm for multistate objects as described
 * in BACnet.  If feedback and output values of the enum point
 * are not equal for more than timeDelay, an offnormal alarm is
 * generated.
 * <p>
 *
 * @author    Dan Giorgis
 * @creation  03 May 01
 * @version   $Revision: 27$ $Date: 9/8/05 11:03:53 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Feedback value
 */
@NiagaraProperty(
  name = "feedbackValue",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(BFacets.UX_FIELD_EDITOR, BString.make(\"alarm:AlgorithmEnumEditor\"))")
)
public class BEnumCommandFailureAlgorithm
  extends BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BEnumCommandFailureAlgorithm(1004310257)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "feedbackValue"

  /**
   * Slot for the {@code feedbackValue} property.
   * Feedback value
   * @see #getFeedbackValue
   * @see #setFeedbackValue
   */
  public static final Property feedbackValue = newProperty(Flags.SUMMARY, new BStatusEnum(), BFacets.make(BFacets.UX_FIELD_EDITOR, BString.make("alarm:AlgorithmEnumEditor")));

  /**
   * Get the {@code feedbackValue} property.
   * Feedback value
   * @see #feedbackValue
   */
  public BStatusEnum getFeedbackValue() { return (BStatusEnum)get(feedbackValue); }

  /**
   * Set the {@code feedbackValue} property.
   * Feedback value
   * @see #feedbackValue
   */
  public void setFeedbackValue(BStatusEnum v) { set(feedbackValue, v, null); }

  //endregion Property "feedbackValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumCommandFailureAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BEnumCommandFailureAlgorithm's grandparent must be an EnumPoint.
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
    if (slot == feedbackValue )
      return getPointFacets();
      
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
    
    if (p == feedbackValue)
      executePoint();
  }  

////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the feedback value matched output value
   */
  @Override
  protected boolean isNormal(BStatusValue o)
  {
    BStatusEnum out = (BStatusEnum)o;
    BDynamicEnum currentValue = out.getValue();
    BDynamicEnum feedbackValue = getFeedbackValue().getValue();

    boolean isNorm = currentValue.getOrdinal() == feedbackValue.getOrdinal();
    return isNorm;
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
    map.put(BAlarmRecord.NUMERIC_VALUE, BInteger.make(((BStatusEnum)out).getValue().getOrdinal()));
    map.put(BAlarmRecord.FEEDBACK_VALUE, BString.make(getFeedbackValue().valueToString(getPointFacets())));
    map.put(BAlarmRecord.FEEDBACK_NUMERIC, BInteger.make(getFeedbackValue().getEnum().getOrdinal()));
  }

}
