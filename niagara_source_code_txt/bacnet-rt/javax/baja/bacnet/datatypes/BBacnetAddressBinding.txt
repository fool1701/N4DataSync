/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetAddressBinding represents the BacnetAddressBinding
 * sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 13 Aug 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "deviceObjectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE)"
)
@NiagaraProperty(
  name = "deviceAddress",
  type = "BBacnetAddress",
  defaultValue = "new BBacnetAddress()"
)
public final class BBacnetAddressBinding
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetAddressBinding(2828527304)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceObjectId"

  /**
   * Slot for the {@code deviceObjectId} property.
   * @see #getDeviceObjectId
   * @see #setDeviceObjectId
   */
  public static final Property deviceObjectId = newProperty(0, BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE), null);

  /**
   * Get the {@code deviceObjectId} property.
   * @see #deviceObjectId
   */
  public BBacnetObjectIdentifier getDeviceObjectId() { return (BBacnetObjectIdentifier)get(deviceObjectId); }

  /**
   * Set the {@code deviceObjectId} property.
   * @see #deviceObjectId
   */
  public void setDeviceObjectId(BBacnetObjectIdentifier v) { set(deviceObjectId, v, null); }

  //endregion Property "deviceObjectId"

  //region Property "deviceAddress"

  /**
   * Slot for the {@code deviceAddress} property.
   * @see #getDeviceAddress
   * @see #setDeviceAddress
   */
  public static final Property deviceAddress = newProperty(0, new BBacnetAddress(), null);

  /**
   * Get the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public BBacnetAddress getDeviceAddress() { return (BBacnetAddress)get(deviceAddress); }

  /**
   * Set the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public void setDeviceAddress(BBacnetAddress v) { set(deviceAddress, v, null); }

  //endregion Property "deviceAddress"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAddressBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetAddressBinding()
  {
  }

  /**
   * Standard constructor.
   */
  public BBacnetAddressBinding(BBacnetObjectIdentifier deviceObjectId,
                               BBacnetAddress deviceAddress)
  {
    setDeviceObjectId(deviceObjectId);
    getDeviceAddress().copyFrom(deviceAddress);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getDeviceObjectId().toString(context))
      .append("_to_")
      .append(getDeviceAddress().toString(context));
    return sb.toString();
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
    out.writeObjectIdentifier(getDeviceObjectId());
    getDeviceAddress().writeAsn(out);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    set(deviceObjectId, in.readObjectIdentifier(), noWrite);
    getDeviceAddress().readAsn(in);
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  public static final int MAX_ENCODED_SIZE = 16;

}
