/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.BFacets;
import javax.baja.sys.BNumber;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetPositiveIntegerValueDescriptor exposes a ControlPoint as a Bacnet
 * Positive Integer Value Descriptor.
 *
 * @author Joseph Chandler on 15 Apr 15
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:NumericPoint"
  )
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.POSITIVE_INTEGER_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetPositiveIntegerValueDescriptor
  extends BBacnetAnalogValueDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetPositiveIntegerValueDescriptor(893755623)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.POSITIVE_INTEGER_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPositiveIntegerValueDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Set the BACnet status flags to fault if the Niagara
   * value is disallowed for the exposed BACnet object type.
   */
  @Override
  protected final void validate()
  {
    super.validate();

    BNumber min = (BNumber)getPoint().getFacets().getFacet(BFacets.MIN);
    if (min != null)
    {
      if (setFaultOnOutOfRangeValue(min))
      {
        setFaultCause(lex.getText("export.configurationFault.postiveIntergerValue.minValue",
                      new Object[] {BBacnetUnsigned.MIN_UNSIGNED_VALUE, BBacnetUnsigned.MAX_UNSIGNED_VALUE}));
        return;
      }
    }
    BNumber max = (BNumber)getPoint().getFacets().getFacet(BFacets.MAX);
    if (max != null)
    {
      if (setFaultOnOutOfRangeValue(max))
      {
        setFaultCause(lex.getText("export.configurationFault.postiveIntergerValue.maxValue",
                      new Object[] {BBacnetUnsigned.MIN_UNSIGNED_VALUE, BBacnetUnsigned.MAX_UNSIGNED_VALUE}));
        return;
      }
    }
  }

  private boolean setFaultOnOutOfRangeValue(BNumber min)
  {
    if (min.getLong() < BBacnetUnsigned.MIN_UNSIGNED_VALUE ||
        min.getLong() > BBacnetUnsigned.MAX_UNSIGNED_VALUE)
    {
      setReliability(BBacnetReliability.unreliableOther);
      setStatus(BStatus.makeFault(getStatus(), true));
      return true;
    }
    return false;
  }


  /**
   * Override hook for IntegerValue
   * This is a lossy conversion
   *
   * @param value double value to convert to asn.1
   * @return byte[] containing the required asn.1 formatted numeric value
   */
  @Override
  public byte[] convertToAsn(double value)
  {
    return AsnUtil.toAsnUnsigned((long)value);
  }

  /**
   * Override hook for IntegerValue
   * <p>
   * This is a limited conversion to 2^53
   *
   * @param value asn.1 byte array containing a number
   * @return the number decoded from the byte[]
   * @throws AsnException if the array does not contain a properly
   */
  @Override
  public double convertFromAsn(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnUnsignedInteger(value);
  }

  /**
   * Subclass override method to add required properties.
   * outOfService property is required for AnalogPointDescriptor but not for IntegerValue
   * Remove those optional properties from required
   *
   * @param v Vector containing required propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addRequiredProps(Vector v)
  {
    super.addRequiredProps(v);
  }

  /**
   * Subclass override method to add optional properties.
   * NOTE: You MUST call super.addOptionalProps(v) first!
   * @param v Vector containing optional propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addOptionalProps(Vector v)
  {
    super.addOptionalProps(v);
    v.add(BBacnetPropertyIdentifier.outOfService);
    v.add(BBacnetPropertyIdentifier.priorityArray);
    v.add(BBacnetPropertyIdentifier.relinquishDefault);
    v.add(BBacnetPropertyIdentifier.eventState);
  }
}
