/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.logging.Level;

import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.control.BEnumPoint;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BBacnetEnumProxyExt handles the point configuration
 * of a point of type BOOLEAN, UNSIGNED, or ENUMERATED
 * in a Bacnet device.
 * <p>
 * Enumerated property values in Bacnet devices are
 * mapped to BEnumPoints in Niagara.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 04 Jan 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 should the encoding of this point use Asn Integer (true)
 or Asn Unsigned (false)?
 */
@NiagaraProperty(
  name = "signed",
  type = "boolean",
  defaultValue = "false"
)
public class BBacnetEnumProxyExt
  extends BBacnetProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetEnumProxyExt(2610517362)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "signed"

  /**
   * Slot for the {@code signed} property.
   * should the encoding of this point use Asn Integer (true)
   * or Asn Unsigned (false)?
   * @see #getSigned
   * @see #setSigned
   */
  public static final Property signed = newProperty(0, false, null);

  /**
   * Get the {@code signed} property.
   * should the encoding of this point use Asn Integer (true)
   * or Asn Unsigned (false)?
   * @see #signed
   */
  public boolean getSigned() { return getBoolean(signed); }

  /**
   * Set the {@code signed} property.
   * should the encoding of this point use Asn Integer (true)
   * or Asn Unsigned (false)?
   * @see #signed
   */
  public void setSigned(boolean v) { setBoolean(signed, v, null); }

  //endregion Property "signed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEnumProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetEnumProxyExt()
  {
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetEnumProxyExt must be in a EnumPoint.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BEnumPoint;
  }


////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  public void fromEncodedValue(byte[] encodedValue, BStatus bacnetStatus, Context cx)
  {
    BStatusEnum dv = (BStatusEnum)getReadValue().newCopy();
    Context baseCx = cx.getBase();
    BEnum ms = ((BEnumPoint)getParentPoint()).getEnum();
    BEnumRange msr = (BEnumRange)((BEnumPoint)getParentPoint()).getEnumFacets().getFacet(BFacets.RANGE);
    if (msr == null) msr = ms.getRange();
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
                dv.setValue(msr.get(asnIn.readBoolean() ? 1 : 0));
                break;
              case ASN_UNSIGNED:
                dv.setStatusNull(false);
                dv.setValue(msr.get(asnIn.readUnsignedInt()));
                break;
              case ASN_INTEGER:
                dv.setStatusNull(false);
                dv.setValue(msr.get(asnIn.readSignedInteger()));
                break;
              case ASN_REAL:
                dv.setStatusNull(false);
                dv.setValue(msr.get((int)asnIn.readReal()));
                break;
              case ASN_DOUBLE:
                dv.setStatusNull(false);
                dv.setValue(msr.get((int)asnIn.readDouble()));
                break;
              case ASN_OCTET_STRING:
                dv.setStatusNull(false);
                dv.setValue(msr.get(asnIn.readOctetString()[0]));
                break;
              case ASN_CHARACTER_STRING:
                String cs = asnIn.readCharacterString();
                dv.setStatusNull(false);
                if (msr.isTag(cs))
                  dv.setValue(ms.getRange().get(msr.tagToOrdinal(cs)));
// 2006-12-07 CPG This was a long shot anyway, and it causes problems if the value isn't a number,
//                so remove it.
//                else
//                  dv.setValue(ms.getRange().get(Integer.parseInt(cs)));
                break;
              case ASN_BIT_STRING:
                asnIn.readBitString();
                dv.setStatusNull(false);
                break;
              case ASN_ENUMERATED:
                dv.setStatusNull(false);
                dv.setValue(msr.get(asnIn.readEnumerated()));
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
                dv.setValue(ms.getRange().get(asnIn.readInteger()));
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

    int i = ((BStatusEnum)newValue).getValue().getOrdinal();
    switch (asnType)
    {
      case ASN_NULL:
        return AsnUtil.toAsnNull();
      case ASN_BOOLEAN:
        return AsnUtil.toAsnBoolean(i != 0);
      case ASN_UNSIGNED:
        return AsnUtil.toAsnUnsigned(i);
      case ASN_INTEGER:
        return AsnUtil.toAsnInteger(i);
      case ASN_REAL:
        return AsnUtil.toAsnReal(i);
      case ASN_DOUBLE:
        return AsnUtil.toAsnDouble(i);
      case ASN_OCTET_STRING:
        return AsnUtil.toAsnOctetString(new byte[] { (byte)i });
      case ASN_CHARACTER_STRING:
        return AsnUtil.toAsnCharacterString(String.valueOf(i));
      case ASN_BIT_STRING:
        return NO_VALUE;
      case ASN_ENUMERATED:
        return AsnUtil.toAsnEnumerated(i);
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
        return (getSigned() ? AsnUtil.toAsnInteger(i) : AsnUtil.toAsnUnsigned(i));
    }
  }
}
