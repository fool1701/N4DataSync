/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
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
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetLargeAnalogValueDescriptor exposes a ControlPoint as a writable (non-commandable) Bacnet
 * Analog Value Object.
 *
 * @author Craig Gemmill on 19 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:NumericWritable"
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
public class BBacnetIntegerValuePrioritizedDescriptor
  extends BBacnetAnalogWritableDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetIntegerValuePrioritizedDescriptor(2790209830)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BBacnetIntegerValuePrioritizedDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  @Override
  public int asnType()
  {
    return ASN_INTEGER;
  }

  /**
   * This is a loss-y conversion
   *
   * @param value double value to convert to asn.1
   * @return truncated and asn.1 encoded integer
   */
  @Override
  public byte[] convertToAsn(double value)
  {
    return AsnUtil.toAsnInteger((int)value);
  }

  /**
   * Read a bacnet integer and convert into a double
   * ~precise up to +/- 2^53
   *
   * @param value asn.1 byte array containing a number
   * @return converted double value
   * @throws AsnException
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
    v.remove(BBacnetPropertyIdentifier.priorityArray);
    v.remove(BBacnetPropertyIdentifier.relinquishDefault);
    v.remove(BBacnetPropertyIdentifier.eventState);
  }

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

  /**
   * @param out   asn.1 byte stream to append the numeric value
   * @param value asn.1 byte array containing a number
   */
  @Override
  public void appendToAsn(AsnOutputStream out, double value)
  {
    out.writeSignedInteger((int)value);
  }

  /**
   * @param in asn.1 byte stream read a numeric value from
   * @return bacnet integer truncated to java double
   * @throws AsnException if an unexpected ASN_TYPE is encountered
   */
  @Override
  public double readFromAsn(AsnInputStream in)
    throws AsnException
  {
    return in.readSignedInteger();
  }

  /**
   * Get slot facets.
   *
   * @param s the
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.INTEGER_VALUE);
    }
    return super.getSlotFacets(s);
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
