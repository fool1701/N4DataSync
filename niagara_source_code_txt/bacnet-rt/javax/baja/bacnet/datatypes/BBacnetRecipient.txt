/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.worker.IBacnetAddress;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * Recipient for an alarm to be exported to Bacnet.
 * <p>
 * BBacnetRecipient represents the BacnetRecipient
 * choice.
 *
 * @author Craig Gemmill
 * @version $Revision: 3$ $Date: 12/10/01 9:26:16 AM$
 * @creation 26 Oct 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0,1)")
)
@NiagaraProperty(
  name = "device",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE)"
)
@NiagaraProperty(
  name = "address",
  type = "BBacnetAddress",
  defaultValue = "new BBacnetAddress()"
)
public final class BBacnetRecipient
  extends BStruct
  implements BIBacnetDataType, IBacnetAddress
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetRecipient(156107272)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(0, 0, BFacets.makeInt(0,1));

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

  //region Property "device"

  /**
   * Slot for the {@code device} property.
   * @see #getDevice
   * @see #setDevice
   */
  public static final Property device = newProperty(0, BBacnetObjectIdentifier.make(BBacnetObjectType.DEVICE), null);

  /**
   * Get the {@code device} property.
   * @see #device
   */
  public BBacnetObjectIdentifier getDevice() { return (BBacnetObjectIdentifier)get(device); }

  /**
   * Set the {@code device} property.
   * @see #device
   */
  public void setDevice(BBacnetObjectIdentifier v) { set(device, v, null); }

  //endregion Property "device"

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, new BBacnetAddress(), null);

  /**
   * Get the {@code address} property.
   * @see #address
   */
  public BBacnetAddress getAddress() { return (BBacnetAddress)get(address); }

  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(BBacnetAddress v) { set(address, v, null); }

  //endregion Property "address"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetRecipient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetRecipient()
  {
  }

  /**
   * Object ID constructor.
   *
   * @param device
   */
  public BBacnetRecipient(BBacnetObjectIdentifier device)
  {
    setChoice(DEVICE_TAG);
    setDevice(device);
  }

  /**
   * Address constructor.
   *
   * @param address
   */
  public BBacnetRecipient(BBacnetAddress address)
  {
    setChoice(ADDRESS_TAG);
    getAddress().copyFrom(address);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Is this a device-type recipient?
   *
   * @return true if device, false if address.
   */
  public boolean isDevice()
  {
    return getChoice() == DEVICE_TAG;
  }

  /**
   * Is this a address-type recipient?
   *
   * @return true if address, false if device.
   */
  public boolean isAddress()
  {
    return getChoice() == ADDRESS_TAG;
  }

  /**
   * Get the recipient as a BValue.
   *
   * @return the recipient.
   */
  public BValue getRecipient()
  {
    if (getChoice() == DEVICE_TAG)
      return getDevice();
    else
      return getAddress();
  }

  /**
   * Set the recipient.
   *
   * @param v the new recipient.
   */
  public void setRecipient(BValue v)
  {
    setRecipient(v, null);
  }

  /**
   * Set the recipient.
   *
   * @param v  the new recipient.
   * @param cx the context for the set.
   */
  public void setRecipient(BValue v, Context cx)
  {
    Type t = v.getType();
    if (t == BBacnetObjectIdentifier.TYPE)
    {
      setInt(choice, DEVICE_TAG, cx);
      set(device, v, cx);
    }
    else if (t == BBacnetAddress.TYPE)
    {
      setInt(choice, ADDRESS_TAG, cx);
      getAddress().copyFrom((BBacnetAddress)v, cx);
    }
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
    switch (getChoice())
    {
      case DEVICE_TAG:
        out.writeObjectIdentifier(DEVICE_TAG, getDevice());
        break;
      case ADDRESS_TAG:
        out.writeOpeningTag(ADDRESS_TAG);
        getAddress().writeAsn(out);
        out.writeClosingTag(ADDRESS_TAG);
        break;
      default:
        throw new IllegalStateException("Invalid recipient type:" + getChoice());
    }
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
    if (in.isValueTag(DEVICE_TAG))
    {
      BBacnetObjectIdentifier id = in.readObjectIdentifier(DEVICE_TAG);
      setRecipient(id, noWrite);
    }
    else if (in.isOpeningTag(ADDRESS_TAG))
    {
      in.skipTag();  // skip opening tag
      getAddress().readAsn(in);
      setInt(choice, ADDRESS_TAG, noWrite);
      in.skipTag();  // skip closing tag
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }


  public boolean equivalent(Object o)
  {
    if (o instanceof BBacnetRecipient)
    {
      BBacnetRecipient other = (BBacnetRecipient)o;
      int choice = getChoice();
      if (choice != other.getChoice())
        return false;

      switch (choice)
      {
        case DEVICE_TAG:
          return getDevice().equals(other.getDevice());
        case ADDRESS_TAG:
          int networkNumber = getAddress().getNetworkNumber();
          if (networkNumber != other.getAddress().getNetworkNumber())
            return false;

          return getAddress().macEquals(other.getAddress().getMacAddress().getAddr());
      }
    }
    return false;
  }
////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return getRecipient().toString(context);
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int DEVICE_TAG = 0;
  public static final int ADDRESS_TAG = 1;

}
