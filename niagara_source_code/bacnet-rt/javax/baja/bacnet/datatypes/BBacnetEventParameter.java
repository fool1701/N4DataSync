/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetLifeSafetyState;
import javax.baja.bacnet.enums.access.BBacnetAccessEvent;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetEventParameter represents the BACnetEventParameter
 * choice.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 29 Jul 2005
 * @since Niagara 3.1
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "BBacnetEventType.NONE",
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "\"bacnet:BacnetEventTypeFE\"")
)
public final class BBacnetEventParameter
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetEventParameter(2917125664)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, BBacnetEventType.NONE, BFacets.make(BFacets.FIELD_EDITOR, "bacnet:BacnetEventTypeFE"));

  /**
   * Get the {@code choice} property.
   * @see #choice
   */
  public int getChoice() { return getInt(choice); }

  /**
   * Set the {@code choice} property.
   * @see #choice
   */
  public void setChoice(int v) { setInt(choice, v, null); }

  //endregion Property "choice"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetEventParameter()
  {
  }

  public static BBacnetEventParameter makeChangeOfState(BRelTime timeDelay, BBacnetListOf listOfValues)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.CHANGE_OF_STATE);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(LIST_OF_VALUES_SLOT_NAME, listOfValues);
    return eventParams;
  }

  public static BBacnetEventParameter makeCommandFailure(BRelTime timeDelay, BBacnetDeviceObjectPropertyReference feedbackRef)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.COMMAND_FAILURE);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(FEEDBACK_PROPERTY_REFERENCE_SLOT_NAME, feedbackRef);
    return eventParams;
  }

  public static BBacnetEventParameter makeFloatingLimit(
    BRelTime timeDelay,
    BBacnetDeviceObjectPropertyReference setpointRef,
    float lowDiffLimit,
    float highDiffLimit,
    float deadband)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.FLOATING_LIMIT);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(SETPOINT_REFERENCE_SLOT_NAME, setpointRef);
    eventParams.add(LOW_DIFF_LIMIT_SLOT_NAME, BFloat.make(lowDiffLimit));
    eventParams.add(HIGH_DIFF_LIMIT_SLOT_NAME, BFloat.make(highDiffLimit));
    eventParams.add(DEADBAND_SLOT_NAME, BFloat.make(deadband));
    return eventParams;
  }

  public static BBacnetEventParameter makeOutOfRange(
    BRelTime timeDelay,
    float lowLimit,
    float highLimit,
    float deadband)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.OUT_OF_RANGE);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(LOW_LIMIT_SLOT_NAME, BFloat.make(lowLimit));
    eventParams.add(HIGH_LIMIT_SLOT_NAME, BFloat.make(highLimit));
    eventParams.add(DEADBAND_SLOT_NAME, BFloat.make(deadband));
    return eventParams;
  }

  public static BBacnetEventParameter makeBufferReady(long notificationThreshold, long previousNotificationCount)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.BUFFER_READY);
    eventParams.add(NOTIFICATION_THRESHOLD_SLOT_NAME, BBacnetUnsigned.make(notificationThreshold));
    eventParams.add(PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME, BBacnetUnsigned.make(previousNotificationCount));
    return eventParams;
  }

  public static BBacnetEventParameter makeSignedOutOfRange(BRelTime timeDelay, double lowLimit, double highLimit, double deadband)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.SIGNED_OUT_OF_RANGE);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(LOW_LIMIT_SLOT_NAME, BDouble.make(lowLimit));
    eventParams.add(HIGH_LIMIT_SLOT_NAME, BDouble.make(highLimit));
    eventParams.add(DEADBAND_SLOT_NAME, BDouble.make(deadband));
    return eventParams;
  }
  
  public static BBacnetEventParameter makeChangeOfCharacterString(BRelTime timeDelay, BBacnetListOf listOfValues)
  {
    BBacnetEventParameter eventParams = new BBacnetEventParameter();
    eventParams.setChoice(BBacnetEventType.CHANGE_OF_CHARACTERSTRING);
    eventParams.addTimeDelay(timeDelay);
    eventParams.add(LIST_OF_ALARM_VALUES_SLOT_NAME, listOfValues);
    return eventParams;
  }

  private void addTimeDelay(BRelTime timeDelay)
  {
    add(
      BBacnetEventParameter.TIME_DELAY_SLOT_NAME,
      BBacnetUnsigned.make(timeDelay.getSeconds()),
      /* flags */ 0,
      BFacets.makeInt(BUnit.getUnit("second")),
      /* context */ null);
  }

////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;
    BComplex parent = getParent();
    if (parent != null)
      parent.asComponent().changed(getPropertyInParent(), cx);
    // vfixx: throw changed w/ GCC context?
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public final void subscribed()
  {
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childSubscribed(this);
  }

  /**
   * Callback when the component leaves the subscribed state.
   */
  public final void unsubscribed()
  {
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childUnsubscribed(this);
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getAppliedCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this))
      return getParent().asComponent().getAppliedCategoryMask();
    return super.getAppliedCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getCategoryMask();
    return super.getCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BPermissions getPermissions(Context cx)
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getPermissions(cx);
    return super.getPermissions(cx);
  }


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeOpeningTag(getChoice());
    try
    {
      switch (getChoice())
      {
        case CHANGE_OF_BITSTRING_TAG:
          out.writeUnsigned(CHANGE_OF_BITSTRING_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeBitString(CHANGE_OF_BITSTRING_BITMASK_TAG, (BBacnetBitString)get("bitmask"));

          out.writeOpeningTag(CHANGE_OF_BITSTRING_LIST_OF_BITSTRING_VALUES_TAG);
          ((BBacnetListOf)get("listOfBitstringValues")).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_BITSTRING_LIST_OF_BITSTRING_VALUES_TAG);
          break;
        case CHANGE_OF_STATE_TAG:
          out.writeUnsigned(CHANGE_OF_STATE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));

          out.writeOpeningTag(CHANGE_OF_STATE_LIST_OF_VALUES_TAG);
          ((BBacnetListOf)get(LIST_OF_VALUES_SLOT_NAME)).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_STATE_LIST_OF_VALUES_TAG);
          break;
        case CHANGE_OF_VALUE_TAG:
          out.writeUnsigned(CHANGE_OF_VALUE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeOpeningTag(CHANGE_OF_VALUE_COV_CRITERIA_TAG);
          int tag2 = ((BInteger)get("covCriteria")).getInt();
          switch (tag2)
          {
            case CHANGE_OF_VALUE_BITMASK_TAG:
              out.writeBitString(CHANGE_OF_VALUE_BITMASK_TAG, (BBacnetBitString)get("bitmask"));
              break;
            case CHANGE_OF_VALUE_REFERENCED_PROPERTY_INCREMENT_TAG:
              out.writeReal(CHANGE_OF_VALUE_REFERENCED_PROPERTY_INCREMENT_TAG, (BFloat)get("referencedPropertyIncrement"));
              break;
            default:
              throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag2);
          }
          out.writeClosingTag(CHANGE_OF_VALUE_COV_CRITERIA_TAG);
          break;
        case COMMAND_FAILURE_TAG:
          out.writeUnsigned(COMMAND_FAILURE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeOpeningTag(COMMAND_FAILURE_FEEDBACK_PROPERTY_REFERENCE_TAG);
          ((BBacnetDeviceObjectPropertyReference)get(FEEDBACK_PROPERTY_REFERENCE_SLOT_NAME)).writeAsn(out);
          out.writeClosingTag(COMMAND_FAILURE_FEEDBACK_PROPERTY_REFERENCE_TAG);
          break;
        case FLOATING_LIMIT_TAG:
          out.writeUnsigned(FLOATING_LIMIT_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeOpeningTag(FLOATING_LIMIT_SETPOINT_REFERENCE_TAG);
          ((BBacnetDeviceObjectPropertyReference)get(SETPOINT_REFERENCE_SLOT_NAME)).writeAsn(out);
          out.writeClosingTag(FLOATING_LIMIT_SETPOINT_REFERENCE_TAG);
          out.writeReal(FLOATING_LIMIT_LOW_DIFF_LIMIT_TAG, (BFloat)get(LOW_DIFF_LIMIT_SLOT_NAME));
          out.writeReal(FLOATING_LIMIT_HIGH_DIFF_LIMIT_TAG, (BFloat)get(HIGH_DIFF_LIMIT_SLOT_NAME));
          out.writeReal(FLOATING_LIMIT_DEADBAND_TAG, (BFloat)get(DEADBAND_SLOT_NAME));
          break;
        case OUT_OF_RANGE_TAG:
          out.writeUnsigned(OUT_OF_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeReal(OUT_OF_RANGE_LOW_LIMIT_TAG, (BFloat)get(LOW_LIMIT_SLOT_NAME));
          out.writeReal(OUT_OF_RANGE_HIGH_LIMIT_TAG, (BFloat)get(HIGH_LIMIT_SLOT_NAME));
          out.writeReal(OUT_OF_RANGE_DEADBAND_TAG, (BFloat)get(DEADBAND_SLOT_NAME));
          break;
        case COMPLEX_EVENT_TYPE_TAG:
          throw new IllegalStateException("Complex Event Type not supported!");
        case BUFFER_READY_DEPRECATED_TAG:
          out.writeUnsigned(BUFFER_READY_DEPRECATED_NOTIFICATION_THRESHOLD_TAG, (BBacnetUnsigned)get(NOTIFICATION_THRESHOLD_SLOT_NAME));
          out.writeUnsigned(BUFFER_READY_DEPRECATED_PREVIOUS_NOTIFICATION_COUNT_TAG, (BBacnetUnsigned)get(PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME));
          break;
        case CHANGE_OF_LIFE_SAFETY_TAG:
          out.writeUnsigned(CHANGE_OF_LIFE_SAFETY_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));

          out.writeOpeningTag(CHANGE_OF_LIFE_SAFETY_LIST_OF_LIFE_SAFETY_ALARM_VALUES_TAG);
          ((BBacnetListOf)get("listOfLifeSafetyAlarmValues")).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_LIFE_SAFETY_LIST_OF_LIFE_SAFETY_ALARM_VALUES_TAG);

          out.writeOpeningTag(CHANGE_OF_LIFE_SAFETY_LIST_ALARM_VALUES_TAG);
          ((BBacnetListOf)get(LIST_OF_ALARM_VALUES_SLOT_NAME)).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_LIFE_SAFETY_LIST_ALARM_VALUES_TAG);

          out.writeOpeningTag(CHANGE_OF_LIFE_SAFETY_MODE_PROPERTY_REFERENCES_TAG);
          ((BBacnetDeviceObjectPropertyReference)get("modePropertyReference")).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_LIFE_SAFETY_MODE_PROPERTY_REFERENCES_TAG);
          break;
        case EXTENDED_TAG:
          out.writeUnsigned(EXTENDED_VENDOR_ID_TAG, (BBacnetUnsigned)get("vendorId"));
          out.writeUnsigned(EXTENDED_EXTENDED_EVENT_TYPE_TAG, (BBacnetUnsigned)get("extendedEventType"));
          out.writeEncodedValue(EXTENDED_PARAMETERS_TAG, ((BBlob)get("parameters")).copyBytes());
          break;
        case BUFFER_READY_TAG:
          out.writeUnsigned(BUFFER_READY_NOTIFICATION_THRESHOLD_TAG, (BBacnetUnsigned)get(NOTIFICATION_THRESHOLD_SLOT_NAME));
          out.writeUnsigned(BUFFER_READY_PREVIOUS_NOTIFICATION_COUNT_TAG, (BBacnetUnsigned)get(PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME));
          break;
        case UNSIGNED_RANGE_TAG:
          out.writeUnsigned(UNSIGNED_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeUnsigned(UNSIGNED_RANGE_LOW_LIMIT_TAG, (BBacnetUnsigned)get(LOW_LIMIT_SLOT_NAME));
          out.writeUnsigned(UNSIGNED_RANGE_HIGH_LIMIT_TAG, (BBacnetUnsigned)get(HIGH_LIMIT_SLOT_NAME));
          break;
        case ACCESS_EVENT_TAG:
          out.writeOpeningTag(LIST_OF_ACCESS_EVENTS_TAG);
          for (BBacnetAccessEvent accessEvent : getChildren(BBacnetAccessEvent.class))
          {
            out.writeEnumerated(accessEvent);
          }
          out.writeClosingTag(LIST_OF_ACCESS_EVENTS_TAG);

          BBacnetDeviceObjectPropertyReference accessEventTimeReference =
            (BBacnetDeviceObjectPropertyReference)get("accessEventTimeReference");

          out.writeOpeningTag(ACCESS_EVENT_TIME_REFERENCE_TAG);
          accessEventTimeReference.writeAsn(out);
          out.writeClosingTag(ACCESS_EVENT_TIME_REFERENCE_TAG);

          break;
        case DOUBLE_OOR_TAG:
          out.writeUnsigned(OUT_OF_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeDouble(OUT_OF_RANGE_LOW_LIMIT_TAG, (BDouble)get(LOW_LIMIT_SLOT_NAME));
          out.writeDouble(OUT_OF_RANGE_HIGH_LIMIT_TAG, (BDouble)get(HIGH_LIMIT_SLOT_NAME));
          out.writeDouble(OUT_OF_RANGE_DEADBAND_TAG, (BDouble)get(DEADBAND_SLOT_NAME));
          break;
        case SIGNED_OOR_TAG:
          out.writeUnsigned(OUT_OF_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeSignedInteger(OUT_OF_RANGE_LOW_LIMIT_TAG, ((BDouble)get(LOW_LIMIT_SLOT_NAME)).getInt());
          out.writeSignedInteger(OUT_OF_RANGE_HIGH_LIMIT_TAG, ((BDouble)get(HIGH_LIMIT_SLOT_NAME)).getInt());
          out.writeUnsignedInteger(OUT_OF_RANGE_DEADBAND_TAG, ((BDouble)get(DEADBAND_SLOT_NAME)).getInt());
          break;
        case UNSIGNED_OOR_TAG:
          out.writeUnsigned(OUT_OF_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeUnsignedInteger(OUT_OF_RANGE_LOW_LIMIT_TAG, ((BDouble)get(LOW_LIMIT_SLOT_NAME)).getInt());
          out.writeUnsignedInteger(OUT_OF_RANGE_HIGH_LIMIT_TAG, ((BDouble)get(HIGH_LIMIT_SLOT_NAME)).getInt());
          out.writeUnsignedInteger(OUT_OF_RANGE_DEADBAND_TAG, ((BDouble)get(DEADBAND_SLOT_NAME)).getInt());
          break;
        case CHANGE_OF_CHAR_STR_TAG:
          out.writeUnsigned(OUT_OF_RANGE_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          out.writeOpeningTag(CHANGE_OF_CHAR_STR_ALARM_VALUES_TAG);
          ((BBacnetListOf)get(LIST_OF_ALARM_VALUES_SLOT_NAME)).writeAsn(out);
          out.writeClosingTag(CHANGE_OF_CHAR_STR_ALARM_VALUES_TAG);
          break;
        case CHANGE_OF_STATUS_FLAGS_TAG:
          out.writeUnsigned(COSF_TIME_DELAY_TAG, (BBacnetUnsigned) get(TIME_DELAY_SLOT_NAME));
          BBacnetBitString statusFlags = (BBacnetBitString)get(STATUS_FLAGS_SLOT_NAME);
          if (statusFlags != null)
          {
            out.writeBitString(COSF_SELECTED_FLAGS_TAG, statusFlags);
          }
          else
          {
            out.writeBitString(COSF_SELECTED_FLAGS_TAG,
              BacnetBitStringUtil.DEFAULT_STATUS);
          }
          break;
        case CHANGE_OF_RELIABILITY_FLAGS_TAG:
          throw new IllegalStateException("Change Of Reliability not supported!");
        case BBacnetEventType.NONE:
          out.writeNull();
          break;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Exception occurred in writeAsn", e);
    }
    out.writeClosingTag(getChoice());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if ((tag < 0) || (tag > MAX_TAG))
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    // If choice changed remove anything that used to be here.
    if (tag != getChoice()) removeAll(noWrite);

    // Set the choice
    setInt(choice, tag, noWrite);
    in.skipOpeningTag(tag);

    // Add in the new stuff.
    Property p;
//    BComponent c;
    int tag2;
    switch (tag)
    {
      case CHANGE_OF_BITSTRING_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_BITSTRING_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, "bitmask",
          in.readBitString(CHANGE_OF_BITSTRING_BITMASK_TAG),
          noWrite);
        BBacnetListOf lo = new BBacnetListOf(BBacnetBitString.TYPE);
        lo.readAsn(AsnInputStream.make(in.readEncodedValue(CHANGE_OF_BITSTRING_LIST_OF_BITSTRING_VALUES_TAG)));
        BacUtil.setOrAdd(this, "listOfBitstringValues", lo, noWrite);
        break;
      case CHANGE_OF_STATE_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_STATE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);

        BBacnetListOf changeOfStateValues = new BBacnetListOf(BBacnetPropertyStates.TYPE);
        changeOfStateValues.readAsn(AsnInputStream.make(in.readEncodedValue(CHANGE_OF_STATE_LIST_OF_VALUES_TAG)));
        BacUtil.setOrAdd(this, LIST_OF_VALUES_SLOT_NAME, changeOfStateValues, noWrite);
        break;
      case CHANGE_OF_VALUE_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_VALUE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        in.skipTag(); // cov-criteria opening tag
        tag2 = in.peekTag();
        BacUtil.setOrAdd(this, "covCriteria", BInteger.make(tag2), noWrite);
        switch (tag2)
        {
          case CHANGE_OF_VALUE_BITMASK_TAG:
            BacUtil.setOrAdd(this, "bitmask",
              in.readBitString(CHANGE_OF_VALUE_BITMASK_TAG),
              noWrite);
            if (get("referencedPropertyIncrement") != null)
              remove("referencedPropertyIncrement", noWrite);
            break;
          case CHANGE_OF_VALUE_REFERENCED_PROPERTY_INCREMENT_TAG:
            BacUtil.setOrAdd(this, "referencedPropertyIncrement",
              in.readFloat(CHANGE_OF_VALUE_REFERENCED_PROPERTY_INCREMENT_TAG),
              noWrite);
            if (get("bitmask") != null) remove("bitmask", noWrite);
            break;
          default:
            throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
        }
        in.skipTag(); // cov-criteria closing tag
        break;
      case COMMAND_FAILURE_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(COMMAND_FAILURE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        p = BacUtil.setOrAdd(this, FEEDBACK_PROPERTY_REFERENCE_SLOT_NAME,
          new BBacnetDeviceObjectPropertyReference(),
          noWrite);
        in.skipTag(); // feedback-property-reference opening tag
        ((BIBacnetDataType)get(p)).readAsn(in);
        in.skipTag(); // feedback-property-reference closing tag
        break;
      case FLOATING_LIMIT_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(FLOATING_LIMIT_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        p = BacUtil.setOrAdd(this, SETPOINT_REFERENCE_SLOT_NAME,
          new BBacnetDeviceObjectPropertyReference(),
          noWrite);
        in.skipTag(); // setpoint-reference opening tag
        ((BIBacnetDataType)get(p)).readAsn(in);
        in.skipTag(); // setpoint-reference closing tag
        BacUtil.setOrAdd(this, LOW_DIFF_LIMIT_SLOT_NAME,
          in.readFloat(FLOATING_LIMIT_LOW_DIFF_LIMIT_TAG),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_DIFF_LIMIT_SLOT_NAME,
          in.readFloat(FLOATING_LIMIT_HIGH_DIFF_LIMIT_TAG),
          noWrite);
        BacUtil.setOrAdd(this, DEADBAND_SLOT_NAME,
          in.readFloat(FLOATING_LIMIT_DEADBAND_TAG),
          noWrite);
        break;
      case OUT_OF_RANGE_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(OUT_OF_RANGE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, LOW_LIMIT_SLOT_NAME,
          in.readFloat(OUT_OF_RANGE_LOW_LIMIT_TAG),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_LIMIT_SLOT_NAME,
          in.readFloat(OUT_OF_RANGE_HIGH_LIMIT_TAG),
          noWrite);
        BacUtil.setOrAdd(this, DEADBAND_SLOT_NAME,
          in.readFloat(OUT_OF_RANGE_DEADBAND_TAG),
          noWrite);
        break;
      case COMPLEX_EVENT_TYPE_TAG:
        BValue[] alarmParameters = AsnUtil.fromAsn(in, COMPLEX_EVENT_TYPE_TAG);
        for (int i = 0; i < alarmParameters.length; i++)
        {
          BacUtil.setOrAdd(this, null, alarmParameters[i], noWrite);
        }
        break;
      case BUFFER_READY_DEPRECATED_TAG:
        BacUtil.setOrAdd(this, NOTIFICATION_THRESHOLD_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(BUFFER_READY_DEPRECATED_NOTIFICATION_THRESHOLD_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(BUFFER_READY_DEPRECATED_PREVIOUS_NOTIFICATION_COUNT_TAG)),
          noWrite);
        break;
      case CHANGE_OF_LIFE_SAFETY_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_LIFE_SAFETY_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);

        BBacnetListOf lss = new BBacnetListOf(BBacnetLifeSafetyState.TYPE);
        lss.readAsn(AsnInputStream.make(in.readEncodedValue(CHANGE_OF_LIFE_SAFETY_LIST_OF_LIFE_SAFETY_ALARM_VALUES_TAG)));
        BacUtil.setOrAdd(this, "listOfLifeSafetyAlarmValues", lss, noWrite);

        BBacnetListOf lav = new BBacnetListOf(BBacnetLifeSafetyState.TYPE);
        lav.readAsn(AsnInputStream.make(in.readEncodedValue(CHANGE_OF_LIFE_SAFETY_LIST_ALARM_VALUES_TAG)));
        BacUtil.setOrAdd(this, LIST_OF_ALARM_VALUES_SLOT_NAME, lav, noWrite);

        in.skipOpeningTag(CHANGE_OF_LIFE_SAFETY_MODE_PROPERTY_REFERENCES_TAG);
        BBacnetDeviceObjectPropertyReference objPropRef = new BBacnetDeviceObjectPropertyReference();
        objPropRef.readAsn(in);
        BacUtil.setOrAdd(this, "modePropertyReference", objPropRef, noWrite);
        in.skipTag(); // setpoint-reference closing tag
        break;
      case EXTENDED_TAG:
        BacUtil.setOrAdd(this, "vendorId",
          BBacnetUnsigned.make(in.readUnsignedInteger(EXTENDED_VENDOR_ID_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, "extendedEventType",
          BBacnetUnsigned.make(in.readUnsignedInteger(EXTENDED_EXTENDED_EVENT_TYPE_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, "parameters",
          BBlob.make(in.readEncodedValue(EXTENDED_PARAMETERS_TAG)),
          noWrite);
        break;
      case BUFFER_READY_TAG:
        BacUtil.setOrAdd(this, NOTIFICATION_THRESHOLD_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(BUFFER_READY_NOTIFICATION_THRESHOLD_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(BUFFER_READY_PREVIOUS_NOTIFICATION_COUNT_TAG)),
          noWrite);
        break;
      case UNSIGNED_RANGE_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_VALUE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, LOW_LIMIT_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(UNSIGNED_RANGE_LOW_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_LIMIT_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(UNSIGNED_RANGE_HIGH_LIMIT_TAG)),
          noWrite);
        break;
      case ACCESS_EVENT_TAG:
        in.skipOpeningTag(LIST_OF_ACCESS_EVENTS_TAG);
        int i = 0;
        in.peekTag();
        while (!in.isClosingTag(LIST_OF_ACCESS_EVENTS_TAG))
        {
          BacUtil.setOrAdd(
            this,
            ACCESS_EVENT_PREFIX + i,
            BBacnetAccessEvent.make(in.readEnumerated()),
            noWrite);
          i++;
          in.peekTag();
        }
        in.skipClosingTag(LIST_OF_ACCESS_EVENTS_TAG);

        in.skipOpeningTag(ACCESS_EVENT_TIME_REFERENCE_TAG);
        BBacnetDeviceObjectPropertyReference accessEventTimeReference = new BBacnetDeviceObjectPropertyReference();
        accessEventTimeReference.readAsn(in);
        BacUtil.setOrAdd(
          this,
          "accessEventTimeReference",
          accessEventTimeReference,
          noWrite);
        in.skipClosingTag(ACCESS_EVENT_TIME_REFERENCE_TAG);
        break;
      case DOUBLE_OOR_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(OUT_OF_RANGE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, LOW_LIMIT_SLOT_NAME,
          BDouble.make(in.readDouble(OUT_OF_RANGE_LOW_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_LIMIT_SLOT_NAME,
          BDouble.make(in.readDouble(OUT_OF_RANGE_HIGH_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, DEADBAND_SLOT_NAME,
          BDouble.make(in.readDouble(OUT_OF_RANGE_DEADBAND_TAG)),
          noWrite);
        break;
      case SIGNED_OOR_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(OUT_OF_RANGE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, LOW_LIMIT_SLOT_NAME,
          BDouble.make(in.readSignedInteger(OUT_OF_RANGE_LOW_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_LIMIT_SLOT_NAME,
          BDouble.make(in.readSignedInteger(OUT_OF_RANGE_HIGH_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, DEADBAND_SLOT_NAME,
          BDouble.make(in.readUnsignedInteger(OUT_OF_RANGE_DEADBAND_TAG)),
          noWrite);
        break;
      case UNSIGNED_OOR_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(OUT_OF_RANGE_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);
        BacUtil.setOrAdd(this, LOW_LIMIT_SLOT_NAME,
          BDouble.make(in.readUnsignedInt(OUT_OF_RANGE_LOW_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, HIGH_LIMIT_SLOT_NAME,
          BDouble.make(in.readUnsignedInt(OUT_OF_RANGE_HIGH_LIMIT_TAG)),
          noWrite);
        BacUtil.setOrAdd(this, DEADBAND_SLOT_NAME,
          BDouble.make(in.readUnsignedInt(OUT_OF_RANGE_DEADBAND_TAG)),
          noWrite);
        break;

        /*
         * change-of-characterstring [17] SEQUENCE {
         *            time-delay           [0] Unsigned,
         *            list-of-alarm-values [1] SEQUENCE OF CharacterString
         * }
         */
      case CHANGE_OF_CHAR_STR_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(CHANGE_OF_CHAR_STR_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);

        BBacnetListOf charStrValues = new BBacnetListOf(BString.TYPE);
        charStrValues.readAsn(AsnInputStream.make(in.readEncodedValue(CHANGE_OF_CHAR_STR_ALARM_VALUES_TAG)));
        BacUtil.setOrAdd(this, LIST_OF_ALARM_VALUES_SLOT_NAME, charStrValues, noWrite);
        break;
      case CHANGE_OF_STATUS_FLAGS_TAG:
        BacUtil.setOrAdd(this, TIME_DELAY_SLOT_NAME,
          BBacnetUnsigned.make(in.readUnsignedInteger(COSF_TIME_DELAY_TAG)),
          0,
          BFacets.makeInt(BUnit.getUnit("second")),
          noWrite);

        BacUtil.setOrAdd(this, STATUS_FLAGS_SLOT_NAME,
          in.readBitString(COSF_SELECTED_FLAGS_TAG),
          0,
          null,
          noWrite);
        break;
      case CHANGE_OF_RELIABILITY_FLAGS_TAG:
        throw new IllegalStateException("Change Of Reliability not supported!");
      case BBacnetEventType.NONE:
        in.readNull();
        break;
    }
    in.skipClosingTag(tag);
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetEventParameter", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");

  private static final int MAX_TAG = 20;

  public static final String TIME_DELAY_SLOT_NAME = "timeDelay";
  public static final String LIST_OF_VALUES_SLOT_NAME = "listOfValues";
  public static final String LIST_OF_ALARM_VALUES_SLOT_NAME = "listOfAlarmValues";
  public static final String FEEDBACK_PROPERTY_REFERENCE_SLOT_NAME = "feedbackPropertyReference";
  public static final String SETPOINT_REFERENCE_SLOT_NAME = "setpointReference";
  public static final String LOW_DIFF_LIMIT_SLOT_NAME = "lowDiffLimit";
  public static final String HIGH_DIFF_LIMIT_SLOT_NAME = "highDiffLimit";
  public static final String LOW_LIMIT_SLOT_NAME = "lowLimit";
  public static final String HIGH_LIMIT_SLOT_NAME = "highLimit";
  public static final String DEADBAND_SLOT_NAME = "deadband";
  public static final String NOTIFICATION_THRESHOLD_SLOT_NAME = "notificationThreshold";
  public static final String PREVIOUS_NOTIFICATION_COUNT_SLOT_NAME = "previousNotificationCount";
  public static final String STATUS_FLAGS_SLOT_NAME = "statusFlags";

  /**
   * change-of-bitstring
   */
  public static final int CHANGE_OF_BITSTRING_TAG = 0;
  public static final int CHANGE_OF_BITSTRING_TIME_DELAY_TAG = 0;
  public static final int CHANGE_OF_BITSTRING_BITMASK_TAG = 1;
  public static final int CHANGE_OF_BITSTRING_LIST_OF_BITSTRING_VALUES_TAG = 2;

  /**
   * change-of-state
   */
  public static final int CHANGE_OF_STATE_TAG = 1;
  public static final int CHANGE_OF_STATE_TIME_DELAY_TAG = 0;
  public static final int CHANGE_OF_STATE_LIST_OF_VALUES_TAG = 1;

  /**
   * change-of-value
   */
  public static final int CHANGE_OF_VALUE_TAG = 2;
  public static final int CHANGE_OF_VALUE_TIME_DELAY_TAG = 0;
  public static final int CHANGE_OF_VALUE_COV_CRITERIA_TAG = 1;
  public static final int CHANGE_OF_VALUE_BITMASK_TAG = 0;
  public static final int CHANGE_OF_VALUE_REFERENCED_PROPERTY_INCREMENT_TAG = 1;

  /**
   * command-failure
   */
  public static final int COMMAND_FAILURE_TAG = 3;
  public static final int COMMAND_FAILURE_TIME_DELAY_TAG = 0;
  public static final int COMMAND_FAILURE_FEEDBACK_PROPERTY_REFERENCE_TAG = 1;

  /**
   * floating-limit
   */
  public static final int FLOATING_LIMIT_TAG = 4;
  public static final int FLOATING_LIMIT_TIME_DELAY_TAG = 0;
  public static final int FLOATING_LIMIT_SETPOINT_REFERENCE_TAG = 1;
  public static final int FLOATING_LIMIT_LOW_DIFF_LIMIT_TAG = 2;
  public static final int FLOATING_LIMIT_HIGH_DIFF_LIMIT_TAG = 3;
  public static final int FLOATING_LIMIT_DEADBAND_TAG = 4;

  /**
   * out-of-range
   */
  public static final int OUT_OF_RANGE_TAG = 5;
  public static final int OUT_OF_RANGE_TIME_DELAY_TAG = 0;
  public static final int OUT_OF_RANGE_LOW_LIMIT_TAG = 1;
  public static final int OUT_OF_RANGE_HIGH_LIMIT_TAG = 2;
  public static final int OUT_OF_RANGE_DEADBAND_TAG = 3;

  /**
   * complex-event-type
   */
  public static final int COMPLEX_EVENT_TYPE_TAG = 6;

  /**
   * buffer-ready (deprecated)
   */
  public static final int BUFFER_READY_DEPRECATED_TAG = 7;
  public static final int BUFFER_READY_DEPRECATED_NOTIFICATION_THRESHOLD_TAG = 0;
  public static final int BUFFER_READY_DEPRECATED_PREVIOUS_NOTIFICATION_COUNT_TAG = 1;

  /**
   * change-of-life-safety
   */
  public static final int CHANGE_OF_LIFE_SAFETY_TAG = 8;
  public static final int CHANGE_OF_LIFE_SAFETY_TIME_DELAY_TAG = 0;
  public static final int CHANGE_OF_LIFE_SAFETY_LIST_OF_LIFE_SAFETY_ALARM_VALUES_TAG = 1;
  public static final int CHANGE_OF_LIFE_SAFETY_LIST_ALARM_VALUES_TAG = 2;
  public static final int CHANGE_OF_LIFE_SAFETY_MODE_PROPERTY_REFERENCES_TAG = 3;

  /**
   * extended
   */
  public static final int EXTENDED_TAG = 9;
  public static final int EXTENDED_VENDOR_ID_TAG = 0;
  public static final int EXTENDED_EXTENDED_EVENT_TYPE_TAG = 1;
  public static final int EXTENDED_PARAMETERS_TAG = 2;
  public static final int EXTENDED_REFERENCE_TAG = 0;

  /**
   * buffer-ready
   */
  public static final int BUFFER_READY_TAG = 10;
  public static final int BUFFER_READY_NOTIFICATION_THRESHOLD_TAG = 0;
  public static final int BUFFER_READY_PREVIOUS_NOTIFICATION_COUNT_TAG = 1;

  /**
   * unsigned-range
   */
  public static final int UNSIGNED_RANGE_TAG = 11;
  public static final int UNSIGNED_RANGE_TIME_DELAY_TAG = 0;
  public static final int UNSIGNED_RANGE_LOW_LIMIT_TAG = 1;
  public static final int UNSIGNED_RANGE_HIGH_LIMIT_TAG = 2;

  /**
   * -- enumeration value 12 is reserved for future addenda
   */

  public static final String ACCESS_EVENT_PREFIX = "accessEvent";
  public static final int ACCESS_EVENT_TAG = 13;
  public static final int LIST_OF_ACCESS_EVENTS_TAG = 0;
  public static final int ACCESS_EVENT_TIME_REFERENCE_TAG = 1;

  public static final int DOUBLE_OOR_TAG = 14;
  public static final int SIGNED_OOR_TAG = 15;
  public static final int UNSIGNED_OOR_TAG = 16;

  public static final String CHANGE_OF_CHAR_STR_PREFIX = "chrstr";
  public static final int CHANGE_OF_CHAR_STR_TAG = 17;
  public static final int CHANGE_OF_CHAR_STR_TIME_DELAY_TAG = 0;
  public static final int CHANGE_OF_CHAR_STR_ALARM_VALUES_TAG = 1;
  public static final int MAX_ALARM_VALUES = 50;

  public static final int CHANGE_OF_STATUS_FLAGS_TAG = 18;
  public static final int CHANGE_OF_RELIABILITY_FLAGS_TAG = 19;
  public static final int COSF_TIME_DELAY_TAG = 0;
  public static final int COSF_SELECTED_FLAGS_TAG = 1;

  public static final int EP_EVENT_TYPE_NONE = 20;

}
