/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.util.StringTokenizer;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.*;

/**
 * This class represents the BacnetAddress data structure,
 * containing a fixed 16-bit network number and a variable
 * length MAC address.  A Bacnet device is uniquely identified by
 * the combination of network number and MAC address.
 * <p>
 * Each field can have special values.  Zero in the network
 * number field indicates the local network.  The GLOBAL_BROADCAST
 * value (0xFFFF) is used for messages which should be broadcast
 * on all Bacnet networks.  A null MAC address represents a
 * broadcast message.
 *
 * @author Craig Gemmill
 * @version $Revision: 5$ $Date: 11/20/01 9:19:57 AM$
 * @creation 21 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "addressType",
  type = "int",
  defaultValue = "0",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "networkNumber",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(0, 65535)")
)
@NiagaraProperty(
  name = "macAddress",
  type = "BBacnetOctetString",
  defaultValue = "BBacnetOctetString.DEFAULT"
)
public final class BBacnetAddress
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetAddress(2230472578)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "addressType"

  /**
   * Slot for the {@code addressType} property.
   * @see #getAddressType
   * @see #setAddressType
   */
  public static final Property addressType = newProperty(Flags.HIDDEN, 0, null);

  /**
   * Get the {@code addressType} property.
   * @see #addressType
   */
  public int getAddressType() { return getInt(addressType); }

  /**
   * Set the {@code addressType} property.
   * @see #addressType
   */
  public void setAddressType(int v) { setInt(addressType, v, null); }

  //endregion Property "addressType"

  //region Property "networkNumber"

  /**
   * Slot for the {@code networkNumber} property.
   * @see #getNetworkNumber
   * @see #setNetworkNumber
   */
  public static final Property networkNumber = newProperty(0, 0, BFacets.makeInt(0, 65535));

  /**
   * Get the {@code networkNumber} property.
   * @see #networkNumber
   */
  public int getNetworkNumber() { return getInt(networkNumber); }

  /**
   * Set the {@code networkNumber} property.
   * @see #networkNumber
   */
  public void setNetworkNumber(int v) { setInt(networkNumber, v, null); }

  //endregion Property "networkNumber"

  //region Property "macAddress"

  /**
   * Slot for the {@code macAddress} property.
   * @see #getMacAddress
   * @see #setMacAddress
   */
  public static final Property macAddress = newProperty(0, BBacnetOctetString.DEFAULT, null);

  /**
   * Get the {@code macAddress} property.
   * @see #macAddress
   */
  public BBacnetOctetString getMacAddress() { return (BBacnetOctetString)get(macAddress); }

  /**
   * Set the {@code macAddress} property.
   * @see #macAddress
   */
  public void setMacAddress(BBacnetOctetString v) { set(macAddress, v, null); }

  //endregion Property "macAddress"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAddress.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

/* Developer notes
 * 2002-02-13 CPG
 * The original creation used a BSimple, and each address was unique.
 * This was for the benefit of hashtables used to identify transactions,
 * which are hashed partly by BBacnetAddress.  But to preserve future
 * compatibility and to allow BBacnetAddress to be a BacnetDataElement,
 * it was made a BStruct.  The hash code returned by hash() is
 * NOT unique per instance!  If two BBacnetAddress objects contain the
 * same networkNumber and macAddress, they will return the same hash
 * code.  This is expected to be okay, because the hash code is used
 * for transactions, and two BBacnetAddresses with the same network
 * number and MAC address are really referencing the same BBacnetAddress
 * anyway.
 */


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Empty constructor.
   * Uses local network and null (broadcast) MAC address.
   */
  public BBacnetAddress()
  {
  }

  /**
   * Fully specified constructor.
   *
   * @param networkNumber 0 for local,
   *                      GLOBAL_BROADCAST for global broadcast,
   *                      1 to 65534 for network number.
   * @param macAddress    null for broadcast,
   *                      non-null for directed.
   */
  public BBacnetAddress(int networkNumber,
                        byte[] macAddress)
  {
    setNetworkNumber(networkNumber);// no longer needed? & NETWORK_NUMBER_MASK);
    setMacAddress(BBacnetOctetString.make(macAddress));
  }

  /**
   * Fully specified constructor.
   *
   * @param networkNumber 0 for local,
   *                      GLOBAL_BROADCAST for global broadcast,
   *                      1 to 65534 for network number.
   * @param macAddress    null for broadcast,
   *                      non-null for directed.
   */
  public BBacnetAddress(int networkNumber,
                        BBacnetOctetString macAddress)
  {
    setNetworkNumber(networkNumber);// no longer needed? & NETWORK_NUMBER_MASK);
    if (macAddress != null)
      setMacAddress(macAddress);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * Set the mac address from a byte array.
   * @param macAddress a byte array containing the mac address.
  public void setMac(byte[] mac)
  {
  set(macAddress, BBacnetOctetString.make(mac), null);
  }
   */

  /**
   * Set the mac address from a byte array.
   *
   * @param mac a byte array containing the mac address.
   * @param cx         context.
   */
  public void setMac(byte[] mac, Context cx)
  {
    set(macAddress, BBacnetOctetString.make(mac), cx);
  }

  /**
   * The hash for a <code>BBacnetAddress</code> is
   * {@code (networkNumber << 16) | (macAddress.hashCode())}.
   * This does not use the addressType field, because that is really just
   * metadata about the address.  This hash is used in place of that
   * returned by <code>hashCode()</code> to allow distinct objects with the
   * same values to return the same hash.
   *
   * @return the hash code.
   */
  public int hash()
  {
    return 31 * getNetworkNumber() + getMacAddress().hashCode();
  }

  /**
   * To string.
   *
   * @return a descriptive string.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();

    boolean nameContext = false;
    if (context != null &&
      ((nameContext = context.equals(BacnetConst.nameContext)) ||
        context.equals(BacnetConst.deviceRegistryContext)))
    {
      sb.append('_').append(getNetworkNumber()).append('_');
      sb.append(getMacAddress().toString(context));
      String str = sb.toString();
      return nameContext ? SlotPath.escape(str) : str;
    }
    else
    {
      sb.append(getNetworkNumber()).append(':');
      byte[] b = getMacAddress().getBytes();
      if (b == null)
        sb.append("null");
      else
      {
        switch (getAddressType())
        {
          case MAC_TYPE_IP:
            sb.append(b[0] & 0xFF).append('.');
            sb.append(b[1] & 0xFF).append('.');
            sb.append(b[2] & 0xFF).append('.');
            sb.append(b[3] & 0xFF).append(':');
            sb.append(((b[4] & 0xFF) << 8) | (b[5] & 0xFF));
            break;

          case MAC_TYPE_ETHERNET:
          case MAC_TYPE_MSTP:
          case MAC_TYPE_SC:
          case MAC_TYPE_UNKNOWN:
            for (int i = 0; i < b.length; i++)
              sb.append(TextUtil.byteToHexString(b[i])).append(' ');
            sb.setLength(sb.length() - 1);
            break;

          default:
            sb.append(getMacAddress().toString(context));
            break;
        }
      }

      return sb.toString();
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
    out.writeUnsignedInteger(getNetworkNumber());
    byte[] b = getMacAddress().getBytes();
    if (b == null) b = new byte[0];
    out.writeOctetString(b);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    setInt(networkNumber, in.readUnsignedInt(), noWrite);
    set(macAddress, BBacnetOctetString.make(in.readOctetString()), noWrite);
  }


////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  /**
   * Returns true if the given network number and MAC address
   * are equivalent to this object's network and address.
   *
   * @param networkNumber
   * @param macAddress
   * @return true if the network and address match.
   */
  public boolean equals(int networkNumber, byte[] macAddress)
  {
    if (networkNumber != getNetworkNumber())
      return false;

    byte[] mac = getMacAddress().getBytes();
    if ((macAddress == null) && (mac != null))
      return false;
    else if ((macAddress != null) && (mac == null))
      return false;
    else if ((macAddress == null) && (mac == null))
      return true;

    int len = mac.length;
    if (macAddress.length != len)
      return false;

    for (int i = 0; i < len; i++)
    {
      if (macAddress[i] != mac[i])
        return false;
    }

    return true;
  }

  /**
   * Compare this <code>BBacnetAddress</code>'s MAC address with
   * the given MAC address for equality.
   *
   * @param macAddress the comparison mac address.
   * @return true if the given MAC address matches this MAC address.
   */
  public boolean macEquals(byte[] macAddress)
  {
    byte[] mac = getMacAddress().getBytes();
    if ((macAddress == null) && (mac != null))
      return false;
    else if ((macAddress != null) && (mac == null))
      return false;
    else if ((macAddress == null) && (mac == null))
      return true;

    int len = mac.length;
    if (macAddress.length != len)
      return false;

    for (int i = 0; i < len; i++)
    {
      if (macAddress[i] != mac[i])
        return false;
    }

    return true;
  }

  public static final String bytesToString(int type, byte[] mac)
  {
    if (mac == null) return "null";
    StringBuilder sb = new StringBuilder();
    switch (type)
    {
      case MAC_TYPE_ETHERNET:
        if (mac.length != 6)
          throw new IllegalArgumentException("Invalid Ethernet MAC address!");
        for (int i = 0; i < 6; i++)
          sb.append(TextUtil.byteToHexString(mac[i]).toUpperCase()).append(':');
        sb.setLength(sb.length() - 1);
        break;

      case MAC_TYPE_IP:
        sb.append(0xFF & mac[0]).append('.')
          .append(0xFF & mac[1]).append('.')
          .append(0xFF & mac[2]).append('.')
          .append(0xFF & mac[3]);
        if (mac.length > 5)
        {
          sb.append(':');
          int port = (0xFF & mac[4]) << 8;
          port |= (0xFF & mac[5]);
          sb.append("0x").append(Integer.toHexString(port).toUpperCase());
        }
        break;

      case MAC_TYPE_SC:
        if (mac.length != 6)
          throw new IllegalArgumentException("Invalid BACnet/SC MAC address!");

        for (int i = 0; i < mac.length; i++)
          sb.append(TextUtil.byteToHexString(mac[i]).toUpperCase()).append(' ');
        sb.setLength(sb.length() - 1);
        break;

      case MAC_TYPE_MSTP:
      default:
        if (mac.length == 1)
        {
          int i = mac[0] & 0xFF;
          sb.append(i);
        }
        else
        {
          for (int i = 0; i < mac.length; i++)
            sb.append(TextUtil.byteToHexString(mac[i]).toUpperCase()).append(' ');
          sb.setLength(sb.length() - 1);
        }
        break;
    }
    return sb.toString();
  }

  public static final byte[] stringToBytes(int type, int len, String s)
  {
    if ((s == null) || (s.length() == 0) || s.equalsIgnoreCase("null"))
      return null;
    StringTokenizer st;
    byte[] b;
    switch (type)
    {
      case MAC_TYPE_ETHERNET:
        st = new StringTokenizer(s, " :");
        if (st.countTokens() != 6)
          throw new IllegalArgumentException("Invalid Ethernet MAC Address!");
        b = new byte[6];
        for (int i = 0; i < 6; i++)
          b[i] = (byte)Integer.parseInt(st.nextToken(), 16);
        break;

      case MAC_TYPE_IP:
        st = new StringTokenizer(s, ".: ");
        if (st.countTokens() < len)
          throw new IllegalArgumentException("Invalid BACnet/IP MAC Address!");
        switch (len)
        {
          case 4:
            b = new byte[4];
            for (int i = 0; i < 4; i++)
              b[i] = (byte)Integer.decode(st.nextToken()).intValue();
            break;
          case 5:
            b = new byte[6];
            for (int i = 0; i < 4; i++)
              b[i] = (byte)Integer.decode(st.nextToken()).intValue();
            int port = Integer.decode(st.nextToken()).intValue();
            b[4] = (byte)((port >> 8) & 0xFF);
            b[5] = (byte)(port & 0xFF);
            break;
          default:
            throw new IllegalArgumentException("Invalid length for conversion of BACnet/IP MAC address!");
        }
        break;

      case MAC_TYPE_SC:
        st = new StringTokenizer(s, " ");
        if (st.countTokens() != 6)
          throw new IllegalArgumentException("Invalid BACnet/SC MAC Address!");
        b = new byte[6];
        for (int i = 0; i < 6; i++)
          b[i] = (byte)Integer.parseInt(st.nextToken(), 16);
        break;

      case MAC_TYPE_MSTP:
      default:
        st = new StringTokenizer(s, ": ");
        int maclen = st.countTokens();
        b = new byte[maclen];
        if (maclen == 1)
          b[0] = Integer.decode(st.nextToken()).byteValue();
        else
        {
          for (int i = 0; i < b.length; i++)
            b[i] = (byte)Integer.parseInt(st.nextToken(), 16);
        }
        break;
    }
    return b;
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int LOCAL_NETWORK = 0;
  public static final int BROADCAST_NETWORK = 0xFFFF;
  public static final int NETWORK_NUMBER_MASK = 0xFFFF;

  public static final int MAC_TYPE_UNKNOWN = 0;
  public static final int MAC_TYPE_ETHERNET = 1;
  public static final int MAC_TYPE_IP = 2;
  public static final int MAC_TYPE_MSTP = 3;
  public static final int MAC_TYPE_SC = 4;

  public static final BBacnetAddress
    GLOBAL_BROADCAST_ADDRESS = new BBacnetAddress(BROADCAST_NETWORK, (BBacnetOctetString)null);

  public static final BBacnetAddress
    LOCAL_BROADCAST_ADDRESS = new BBacnetAddress(0, (BBacnetOctetString)null);

  public static final BBacnetAddress DEFAULT = LOCAL_BROADCAST_ADDRESS;
}
