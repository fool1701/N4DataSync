/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.offnormal.BEnumCommandFailureAlgorithm;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;

/**
 * BBacnetMultiStateOutputDescriptor exposes a ControlPoint as a Bacnet
 * Multi State Output Object.
 *
 * @author Craig Gemmill on 26 Jul 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:EnumWritable"
  )
)
@NiagaraProperty(
  name = "deviceType",
  type = "String",
  defaultValue = ""
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.MULTI_STATE_OUTPUT)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetMultiStateOutputDescriptor
  extends BBacnetMultiStateWritableDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetMultiStateOutputDescriptor(126872143)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(0, "", null);

  /**
   * Get the {@code deviceType} property.
   * @see #deviceType
   */
  public String getDeviceType() { return getString(deviceType); }

  /**
   * Set the {@code deviceType} property.
   * @see #deviceType
   */
  public void setDeviceType(String v) { setString(deviceType, v, null); }

  //endregion Property "deviceType"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.MULTI_STATE_OUTPUT), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetMultiStateOutputDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get slot facets.
   *
   * @param s
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.MULTI_STATE_OUTPUT);
    }
    return super.getSlotFacets(s);
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  public BEnum getEventType()
  {
    return BBacnetEventType.commandFailure;
  }

  /**
   * Is the given alarm source ext a valid extension for
   * exporting BACnet alarm properties?  This determines if the
   * given alarm source extension follows the appropriate algorithm
   * defined for the intrinsic alarming of a particular object
   * type as required by the BACnet specification.<p>
   * BACnet MultistateOutput points use a CommandFailure alarm algorithm.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    if (ext instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) ext).getOffnormalAlgorithm() instanceof BEnumCommandFailureAlgorithm;
    }

    return false;
  }

////////////////////////////////////////////////////////////////
// Bacnet Access
////////////////////////////////////////////////////////////////

  /**
   * Subclass override method to add required properties.
   * NOTE: You MUST call super.addRequiredProps(v) first!
   *
   * @param v Vector containing required propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addRequiredProps(Vector v)
  {
    super.addRequiredProps(v);
    v.add(BBacnetPropertyIdentifier.priorityArray);
    v.add(BBacnetPropertyIdentifier.relinquishDefault);
  }

  /**
   * Subclass override method to add optional properties.
   * NOTE: You MUST call super.addOptionalProps(v) first!
   *
   * @param v Vector containing optional propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addOptionalProps(Vector v)
  {
    super.addOptionalProps(v);
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      v.add(BBacnetPropertyIdentifier.feedbackValue);
    }
    v.add(BBacnetPropertyIdentifier.deviceType);
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    int pId = rangeReference.getPropertyId();
    int[] props = getRequiredProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }
    props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Read the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing either the encoded value or the error.
   */
  @Override
  protected PropertyValue readOptionalProperty(int pId, int ndx)
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      BEnumCommandFailureAlgorithm alg = (BEnumCommandFailureAlgorithm)almExt.getOffnormalAlgorithm();
      if (pId == BBacnetPropertyIdentifier.FEEDBACK_VALUE)
      {
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(alg.getFeedbackValue().getValue().getOrdinal()));
      }
    }
    switch (pId)
    {
      case BBacnetPropertyIdentifier.DEVICE_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDeviceType()));
    }

    return super.readOptionalProperty(pId, ndx);
  }

  /**
   * Set the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  protected ErrorType writeOptionalProperty(int pId,
                                            int ndx,
                                            byte[] val,
                                            int pri)
    throws BacnetException
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.DEVICE_TYPE:
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      if (pId == BBacnetPropertyIdentifier.FEEDBACK_VALUE)
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
      else if (pId == BBacnetPropertyIdentifier.EVENT_ENABLE)
      {
        almExt.set(BAlarmSourceExt.alarmEnable,
                   BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                   BLocalBacnetDevice.getBacnetContext());
        return null;
      }
    }

    return super.writeOptionalProperty(pId, ndx, val, pri);
  }
}
