/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
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
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetPositiveIntegerValuePrioritizedDescriptor exposes a ControlPoint as a Bacnet
 * Positive Integer Value Prioritized Descriptor.
 *
 * @author Joseph Chandler on 19 Feb 02
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.POSITIVE_INTEGER_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetPositiveIntegerValuePrioritizedDescriptor
  extends BBacnetAnalogWritableDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetPositiveIntegerValuePrioritizedDescriptor(3650661479)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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
  public static final Type TYPE = Sys.loadType(BBacnetPositiveIntegerValuePrioritizedDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
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

  @Override
  public int asnType()
  {
    return ASN_UNSIGNED;
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
    return AsnUtil.toAsnUnsigned((long)value);
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
  public double convertFromAsn(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnUnsignedInteger(value);
  }

  /**
   * @param out   asn.1 byte stream to append the numeric value
   * @param value asn.1 byte array containing a number
   */
  @Override
  public void appendToAsn(AsnOutputStream out, double value)
  {
    out.writeUnsignedInteger((long)value);
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
    return in.readUnsignedInteger();
  }

  /**
   * Subclass override method to add required properties.
   * outOfService property is required for AnalogPointDescriptor but not for IntegerValue commendable
   * Remove those optional properties from required
   *
   * @param v Vector containing required propertyIds.
   */
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

  /**
   * @param s the
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.POSITIVE_INTEGER_VALUE);
    }
    return super.getSlotFacets(s);
  }
}
