/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.LARGE_ANALOG_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetLargeAnalogValuePrioritizedDescriptor
  extends BBacnetAnalogWritableDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetLargeAnalogValuePrioritizedDescriptor(3691200386)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.LARGE_ANALOG_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLargeAnalogValuePrioritizedDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  @Override
  public int asnType()
  {
    return ASN_DOUBLE;
  }

  @Override
  public byte[] convertToAsn(double value)
  {
    return AsnUtil.toAsnDouble(value);
  }

  @Override
  public double convertFromAsn(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnDouble(value);
  }

  /**
   * @param out   asn.1 byte stream to append the numeric value
   * @param value asn.1 byte array containing a number
   */
  @Override
  public void appendToAsn(AsnOutputStream out, double value)
  {
    out.writeDouble(value);
  }

  /**
   * @param in   asn.1 byte stream to read the numeric value from
   * @throws AsnException if an unexpected ASN_TYPE is encountered
   */
  @Override
  public double readFromAsn(AsnInputStream in)
    throws AsnException
  {
    return in.readDouble();
  }

  /**
   * Subclass override method to add required properties.
   * outOfService property is required for AnalogPointDescriptor but not for LargeAnalogValue commendable
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
   * @param s
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.LARGE_ANALOG_VALUE);
    }

    return super.getSlotFacets(s);
  }
}
