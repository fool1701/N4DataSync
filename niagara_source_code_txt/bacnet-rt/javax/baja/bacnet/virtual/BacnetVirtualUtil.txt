/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.sys.*;

import javax.baja.virtual.BVirtualComponent;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetNull;
import javax.baja.bacnet.datatypes.BBacnetOctetString;
import javax.baja.bacnet.datatypes.BIBacnetDataType;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * BacnetVirtualUtil provides common utility functions used by
 * the BACnet virtual point framework.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 02 Nov 2007
 * @since NiagaraAX 3.2
 */
public class BacnetVirtualUtil
  implements BacnetConst
{
  /**
   * Decode an ASN.1-encoded value.
   *
   * @param asnIn
   * @param val
   * @return
   */
  static BValue readValue(AsnInputStream asnIn, BValue val)
    throws AsnException
  {
    synchronized (asnIn)
    {
      BValue v = val;
      if (v instanceof BIBacnetDataType)
      {
        BIBacnetDataType obj = (BIBacnetDataType)v;
        obj.readAsn(asnIn);
      }
      else
      {
        int tag = asnIn.peekApplicationTag();
        switch (tag)
        {
          case ASN_NULL:
            v = BBacnetNull.DEFAULT;
            break;
          case ASN_BOOLEAN:
            v = BBoolean.make(asnIn.readBoolean());
            break;
          case ASN_UNSIGNED:
            v = asnIn.readUnsigned();
            break;
          case ASN_INTEGER:
            v = asnIn.readSigned();
            break;
          case ASN_REAL:
            v = asnIn.readFloat();
            break;
          case ASN_DOUBLE:
            v = BDouble.make(asnIn.readDouble());
            break;
          case ASN_OCTET_STRING:
            v = BBacnetOctetString.make(asnIn.readOctetString());
            break;
          case ASN_CHARACTER_STRING:
            v = BString.make(asnIn.readCharacterString());
            break;
          case ASN_BIT_STRING:
            v = asnIn.readBitString();
            break;
          case ASN_ENUMERATED:
            if (v instanceof BEnum)
              v = ((BEnum)v).getRange().get(asnIn.readEnumerated());
            else
              v = BInteger.make(asnIn.readEnumerated());
            break;
          case ASN_DATE:
            v = asnIn.readDate();
            break;
          case ASN_TIME:
            v = asnIn.readTime();
            break;
          case ASN_OBJECT_IDENTIFIER:
            v = asnIn.readObjectIdentifier();
            break;
          case ASHRAE_RESERVED_13:
          case ASHRAE_RESERVED_14:
          case ASHRAE_RESERVED_15:
            //            v = BString.make(ByteArrayUtil.toHexString(encodedValue));
            break;
          default:
            if(asnIn.isOpeningTag(DATE_TIME_TAG))
            {
              asnIn.skipOpeningTag(DATE_TIME_TAG);
              BBacnetDateTime dateTime = new BBacnetDateTime();
              dateTime.readAsn(asnIn);
              asnIn.skipClosingTag(DATE_TIME_TAG);
              v = dateTime;
            }
            else
            {
              v = AsnUtil.asnToValue(asnIn, AsnInput.END_OF_DATA);
            }
            break;
        }
      }
      return v;
    }
  }

  /**
   * Is this component in a virtual space?  This should identify virtual spaces
   * on both the client (workbench) and server (station) sides.
   *
   * @return true if this component is in a virtual space.
   */
  public static boolean isVirtual(BComponent c)
  {
    if (c.getComponentSpace() == null)
      return false;

    BComponent rc = c.getComponentSpace().getRootComponent();
    return rc instanceof BVirtualComponent;
  }

  /**
   * Get the containing BBacnetVirtualProperty if it exists.
   * Short circuit if this component is not in a virtual space.
   *
   * @param c
   * @return bacnetVirtualProperty
   */
  public static BBacnetVirtualProperty getVirtualProperty(BComponent c)
  {
    if (!isVirtual(c)) return null;
    BComplex p = c.getParent();
    while (p != null)
    {
      if (p instanceof BBacnetVirtualProperty) return (BBacnetVirtualProperty)p;
      p = p.getParent();
    }
    return null;
  }

  private static int DATE_TIME_TAG = 1;

//  private static final Log log = Log.getLog("bacnet.virtual");

}
