/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.logging.Level;

import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetStringProxyExt handles the point configuration
 * of a point of generic type in a Bacnet device.
 * <p>
 * It is the default for types NULL, OCTET_STRING, CHARACTER_STRING,
 * BIT_STRING, DATE, TIME, and OBJECT_IDENTIFIER.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 12 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public class BBacnetStringProxyExt
  extends BBacnetProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetStringProxyExt(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetStringProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetStringProxyExt()
  {
  }


////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  public void fromEncodedValue(byte[] encodedValue, BStatus bacnetStatus, Context cx)
  {
    BStatusString dv = (BStatusString)getReadValue().newCopy();
    Context baseCx = cx.getBase();
    try
    {
      // Set the status flags first, so the null
      // status set during the value set isn't wiped out here.
      // If no status flags exist, just clear the down bit because
      // we got a value, and the only failure that can happen is a
      // conversion error now.
      if (bacnetStatus == null)
        dv.setStatusDown(false);
      else
        dv.setStatus(bacnetStatus);

      // Set the value, if it exists.
      if (encodedValue != null)
      {
        if ((baseCx == PollListEntry.pointCx)
          || (cx == covContext)
          || (cx == PollListEntry.pointCx)
          || (cx == PollListEntry.forceCx))
        {
          dataSize = encodedValue.length;
          AsnInputStream asnIn = AsnInputStream.make(encodedValue);
          try
          {
            int tag = asnIn.peekApplicationTag();

            // Set the data type.
            if (getDataType().length() == 0)
              setDataType(AsnUtil.getAsnTypeName(tag));

            switch (tag)
            {
              case ASN_NULL:
                dv.setStatusNull(true);
                break;
              case ASN_BOOLEAN:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readBoolean() ? "true" : "false");
                break;
              case ASN_UNSIGNED:
                dv.setStatusNull(false);
                dv.setValue(String.valueOf(asnIn.readUnsignedInteger()));
                break;
              case ASN_INTEGER:
                dv.setStatusNull(false);
                dv.setValue(String.valueOf(asnIn.readSignedInteger()));
                break;
              case ASN_REAL:
                dv.setStatusNull(false);
                dv.setValue(String.valueOf(asnIn.readReal()));
                break;
              case ASN_DOUBLE:
                dv.setStatusNull(false);
                dv.setValue(String.valueOf(asnIn.readDouble()));
                break;
              case ASN_OCTET_STRING:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readBacnetOctetString().toString());
                break;
              case ASN_CHARACTER_STRING:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readCharacterString());
                break;
              case ASN_BIT_STRING:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readBitString().toString());
                break;
              case ASN_ENUMERATED:
                dv.setStatusNull(false);
                dv.setValue(String.valueOf(asnIn.readEnumerated()));
                break;
              case ASN_DATE:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readDate().toString());
                break;
              case ASN_TIME:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readTime().toString());
                break;
              case ASN_OBJECT_IDENTIFIER:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readObjectIdentifier().toString());
                break;
              case ASHRAE_RESERVED_13:
              case ASHRAE_RESERVED_14:
              case ASHRAE_RESERVED_15:
                dv.setStatusNull(false);
                break;
              default:  // context tag
                dv.setStatusNull(false);
                PropertyInfo info = device().getPropertyInfo(getObjectId().getObjectType(),
                  getPropertyId().getOrdinal());
                BValue v = AsnUtil.asnToValue(info, encodedValue);
                if (log.isLoggable(Level.FINE))
                  log.fine("StringPxExt(" + this + ").fromEncodedValue: ev="
                    + ByteArrayUtil.toHexString(encodedValue) + "\n v=" + v + " [" + v.getType() + "]");
                if (info.isArray())
                {
                  int index = getPropertyArrayIndex();
                  if (index > 0)
                  {
                    // The one element we read will be the first element in this array,
                    // regardless of which element in the actual array it was.
                    // Remember, one-based for BACnet!!
                    v = ((BBacnetArray)v).getElement(1);
                    if (log.isLoggable(Level.FINE))
                      log.fine("setting dv:" + v + " [" + v.getType() + "]");
                    dv.setValue(v.toString());
                  }
                  else if (index == 0)
                  {
                    dv.setValue(String.valueOf(((BBacnetArray)v).getSize()));
                  }
                  else
                  {
                    dv.setValue(v.toString());
                  }
                }
                else
                {
                  dv.setValue(v.toString());
                }
                break;
            }
          }
          finally
          {
            asnIn.release();
          }
        }
        else
        {
          if (cx instanceof PollListEntry)
          {
            // read metadata using ple pid, etc.
            readMetaData(encodedValue, cx, dv);
          }
        }
      }
      readOk(dv);
      setLastReadError(null);
      updateReadStatus(cx);
    }
    catch (AsnException e)
    {
      readFail(e.toString());
      setLastReadError(ERROR_DEVICE_OTHER);
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "Exception decoding value for " + this + ":" + e, e);
    }
  }

  public byte[] toEncodedValue(BStatusValue newValue)
  {
    // Handle null priority writes separately.
    if (newValue == null) return AsnUtil.toAsnNull();

    String s = ((BStatusString)newValue).getValue();
    switch (asnType)
    {
      case ASN_NULL:
        return AsnUtil.toAsnNull();
      case ASN_BOOLEAN:
        return AsnUtil.toAsnBoolean(s.equalsIgnoreCase("true"));
      case ASN_UNSIGNED:
        return AsnUtil.toAsnUnsigned(Long.parseLong(s));
      case ASN_INTEGER:
        return AsnUtil.toAsnInteger(Integer.parseInt(s));
      case ASN_REAL:
        return AsnUtil.toAsnReal(Float.parseFloat(s));
      case ASN_DOUBLE:
        return AsnUtil.toAsnDouble(Double.parseDouble(s));
      case ASN_OCTET_STRING:
        return AsnUtil.toAsnOctetString(new byte[] { Byte.parseByte(s) });
      case ASN_CHARACTER_STRING:
        return AsnUtil.toAsnCharacterString(s);
      case ASN_BIT_STRING:
        return NO_VALUE;
      case ASN_ENUMERATED:
        return AsnUtil.toAsnEnumerated(Integer.parseInt(s));
      case ASN_DATE:
        return NO_VALUE;
      case ASN_TIME:
        return NO_VALUE;
      case ASN_OBJECT_IDENTIFIER:
        return NO_VALUE;
      case ASHRAE_RESERVED_13:
        return NO_VALUE;
      case ASHRAE_RESERVED_14:
        return NO_VALUE;
      case ASHRAE_RESERVED_15:
        return NO_VALUE;
      default:
        return AsnUtil.toAsnCharacterString(s);
    }
  }
}
