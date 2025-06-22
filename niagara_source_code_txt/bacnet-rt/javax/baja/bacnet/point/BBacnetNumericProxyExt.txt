/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.logging.Level;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.control.BNumericPoint;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetNumericProxyExt handles the point configuration
 * of a point of type UNSIGNED, INTEGER, REAL, DOUBLE, or ENUMERATED
 * in a Bacnet device.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 04 Jan 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public class BBacnetNumericProxyExt
  extends BBacnetProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetNumericProxyExt(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNumericProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetNumericProxyExt()
  {
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetNumericProxyExt must be in a NumericPoint.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNumericPoint;
  }


////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  public void fromEncodedValue(byte[] encodedValue, BStatus bacnetStatus, Context cx)
  {
    BStatusNumeric dv = (BStatusNumeric)getReadValue().newCopy();
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
                dv.setValue(asnIn.readBoolean() ? 1.0D : 0.0D);
                break;
              case ASN_UNSIGNED:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readUnsignedInteger());
                break;
              case ASN_INTEGER:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readSignedInteger());
                break;
              case ASN_REAL:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readReal());
                break;
              case ASN_DOUBLE:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readDouble());
                break;
              case ASN_OCTET_STRING:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readOctetString()[0]);
                break;
              case ASN_CHARACTER_STRING:
                dv.setStatusNull(false);
                dv.setValue(Double.parseDouble(asnIn.readCharacterString()));
                break;
              case ASN_BIT_STRING:
                asnIn.readBitString();
                dv.setStatusNull(false);
                break;
              case ASN_ENUMERATED:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readEnumerated());
                break;
              case ASN_DATE:
                asnIn.readDate();
                dv.setStatusNull(false);
                break;
              case ASN_TIME:
                asnIn.readTime();
                dv.setStatusNull(false);
                break;
              case ASN_OBJECT_IDENTIFIER:
                asnIn.readObjectIdentifier();
                dv.setStatusNull(false);
                break;
              case ASHRAE_RESERVED_13:
              case ASHRAE_RESERVED_14:
              case ASHRAE_RESERVED_15:
                dv.setStatusNull(false);
                break;
              default:
                dv.setStatusNull(false);
                dv.setValue(asnIn.readReal());
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

    double f = ((BStatusNumeric)newValue).getValue();
    switch (asnType)
    {
      case ASN_NULL:
        return AsnUtil.toAsnNull();
      case ASN_BOOLEAN:
        return AsnUtil.toAsnBoolean(f == 0.0);
      case ASN_UNSIGNED:
        return AsnUtil.toAsnUnsigned((long)f);
      case ASN_INTEGER:
        return AsnUtil.toAsnInteger((int)f);
      case ASN_REAL:
        return AsnUtil.toAsnReal(f);
      case ASN_DOUBLE:
        return AsnUtil.toAsnDouble(f);
      case ASN_OCTET_STRING:
        return AsnUtil.toAsnOctetString(new byte[] { (byte)f });
      case ASN_CHARACTER_STRING:
        return AsnUtil.toAsnCharacterString(String.valueOf(f));
      case ASN_BIT_STRING:
        return NO_VALUE;
      case ASN_ENUMERATED:
        return AsnUtil.toAsnEnumerated((int)f);
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
        return AsnUtil.toAsnReal(f);
    }
  }
}
