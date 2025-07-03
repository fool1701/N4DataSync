/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetIntegerValueDescriptor exposes a ControlPoint as a Bacnet
 * Integer Value Descriptor.
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.INTEGER_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetIntegerValueDescriptor
  extends BBacnetAnalogValueDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetIntegerValueDescriptor(4189636731)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.INTEGER_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetIntegerValueDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

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
    return AsnUtil.toAsnInteger((int)value);
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
  public double convertFromAsn(byte[] value) throws AsnException
  {
    return AsnUtil.fromAsnSignedInteger(value);
  }
  
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addRequiredProps(Vector v)
  {
    super.addRequiredProps(v);
    v.remove(BBacnetPropertyIdentifier.outOfService);
    v.remove(BBacnetPropertyIdentifier.eventState);
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addOptionalProps(Vector v)
  {
    super.addOptionalProps(v);
    v.add(BBacnetPropertyIdentifier.outOfService);
    v.add(BBacnetPropertyIdentifier.relinquishDefault);
    v.add(BBacnetPropertyIdentifier.priorityArray);
    v.add(BBacnetPropertyIdentifier.eventState);
  }

  @Override
  protected PropertyValue readProperty(int pId, int ndx)
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.COV_INCREMENT:
        return new NReadPropertyResult(pId, AsnUtil.toAsnUnsigned((int)getCovIncrement()));
    }

    return super.readProperty(pId, ndx);
  }

  /**
   * Get the deadband value as Unsigned integer
   * @param value
   * @return deadband value
   *
   */
  @Override
  public double getDeadBandValue(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnUnsignedInteger(value);
  }

  /**
   * Get the deadband value as Asn Byte array
   * @param value
   * @return deadband bytes
   */
  @Override
  public byte[] getDeadBandBytes(double value)
  {
    return AsnUtil.toAsnUnsigned((long)value);
  }

  /**
   * get the COV increment as Unsigned value
   * @param value
   * @return cov increment
   * @throws AsnException
   */
  @Override
  protected double getCovIncrement(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnUnsignedInteger(value);
  }
}
