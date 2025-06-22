/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * This class represents the ReadPropertyResult sequence.
 * This is not the class used for handling ReadPropertyMultiple
 * messages during polling.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 31 May 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "propertyId",
  type = "int",
  defaultValue = "BBacnetPropertyIdentifier.PRESENT_VALUE",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "propertyArrayIndex",
  type = "int",
  defaultValue = "NOT_USED",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "value",
  type = "BValue",
  defaultValue = "BBacnetNull.DEFAULT",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "error",
  type = "BErrorType",
  defaultValue = "new BErrorType()",
  flags = Flags.READONLY
)
public final class BReadPropertyResult
  extends BComponent
  implements PropertyValue,
  BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BReadPropertyResult(3761050255)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propertyId"

  /**
   * Slot for the {@code propertyId} property.
   * @see #getPropertyId
   * @see #setPropertyId
   */
  public static final Property propertyId = newProperty(Flags.READONLY, BBacnetPropertyIdentifier.PRESENT_VALUE, null);

  /**
   * Get the {@code propertyId} property.
   * @see #propertyId
   */
  public int getPropertyId() { return getInt(propertyId); }

  /**
   * Set the {@code propertyId} property.
   * @see #propertyId
   */
  public void setPropertyId(int v) { setInt(propertyId, v, null); }

  //endregion Property "propertyId"

  //region Property "propertyArrayIndex"

  /**
   * Slot for the {@code propertyArrayIndex} property.
   * @see #getPropertyArrayIndex
   * @see #setPropertyArrayIndex
   */
  public static final Property propertyArrayIndex = newProperty(Flags.READONLY, NOT_USED, null);

  /**
   * Get the {@code propertyArrayIndex} property.
   * @see #propertyArrayIndex
   */
  public int getPropertyArrayIndex() { return getInt(propertyArrayIndex); }

  /**
   * Set the {@code propertyArrayIndex} property.
   * @see #propertyArrayIndex
   */
  public void setPropertyArrayIndex(int v) { setInt(propertyArrayIndex, v, null); }

  //endregion Property "propertyArrayIndex"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.READONLY, BBacnetNull.DEFAULT, null);

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public BValue getValue() { return get(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(BValue v) { set(value, v, null); }

  //endregion Property "value"

  //region Property "error"

  /**
   * Slot for the {@code error} property.
   * @see #getError
   * @see #setError
   */
  public static final Property error = newProperty(Flags.READONLY, new BErrorType(), null);

  /**
   * Get the {@code error} property.
   * @see #error
   */
  public BErrorType getError() { return (BErrorType)get(error); }

  /**
   * Set the {@code error} property.
   * @see #error
   */
  public void setError(BErrorType v) { set(error, v, null); }

  //endregion Property "error"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReadPropertyResult.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BReadPropertyResult()
  {
  }

  /**
   * Constructor.
   *
   * @param propertyId the property-identifier to be referenced.
   */
  public BReadPropertyResult(int propertyId)
  {
    setPropertyId(propertyId);
  }

  /**
   * Constructor.
   *
   * @param propertyId         the property-identifier to be referenced.
   * @param propertyArrayIndex the array index.
   */
  public BReadPropertyResult(int propertyId,
                             int propertyArrayIndex)
  {
    setPropertyId(propertyId);
    setPropertyArrayIndex(propertyArrayIndex);
  }

// FIXX: need value and error constructors...


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    if (p.equals(value))
    {
      propertyValue = null;
    }
    else if (p.equals(error))
    {
      if (getError().isDefault())
      {
        isError = false;
      }
      else
      {
        isError = true;
      }
      propertyValue = null;
    }
  }


////////////////////////////////////////////////////////////////
//  PropertyValue
////////////////////////////////////////////////////////////////

  public byte[] getPropertyValue()
  {
    if (propertyValue == null)
    {
      if (!isError)
        propertyValue = AsnUtil.toAsn(getValue());
    }
    return propertyValue;
  }

  public int getPriority()
  {
    return -1;
  }

  public ErrorType getPropertyAccessError()
  {
    return (ErrorType)getError();
  }

  public int getErrorClass()
  {
    return getPropertyAccessError().getErrorClass();
  }

  public int getErrorCode()
  {
    return getPropertyAccessError().getErrorCode();
  }

  public boolean isError()
  {
    return isError;
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * @return true if the property array index is used.
   */
  public boolean isPropertyArrayIndexUsed()
  {
    return (getPropertyArrayIndex() != NOT_USED);
  }

/////////////////////////////////////////////////////////////////
//  Encoding Methods
/////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeEnumerated(PROPERTY_ID_TAG, getPropertyId());

    if (isPropertyArrayIndexUsed())
      out.writeUnsignedInteger(PROPERTY_ARRAY_INDEX_TAG, getPropertyArrayIndex());

    if (isError)
    {
      out.writeOpeningTag(PROPERTY_ACCESS_ERROR_TAG);
      getError().writeAsn(out);
      out.writeClosingTag(PROPERTY_ACCESS_ERROR_TAG);
    }
    else
    {
      BValue v = getValue();
      if (v instanceof BIBacnetDataType)
      {
        out.writeOpeningTag(PROPERTY_VALUE_TAG);
        ((BIBacnetDataType)v).writeAsn(out);
        out.writeClosingTag(PROPERTY_VALUE_TAG);
      }
      else
        throw new IllegalStateException("propertyValue type " + v.getType() + " is not a BIBacnetDataType!");
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
    setInt(propertyId, in.readEnumerated(PROPERTY_ID_TAG), noWrite);

    int tag = in.peekTag();
    if (in.isValueTag(PROPERTY_ARRAY_INDEX_TAG))
    {
      setInt(propertyArrayIndex, in.readUnsignedInt(PROPERTY_ARRAY_INDEX_TAG), noWrite);
      tag = in.peekTag();
    }
    else
      setInt(propertyArrayIndex, NOT_USED, noWrite);

    if (in.isOpeningTag(PROPERTY_VALUE_TAG))
    {
      isError = false;
      propertyValue = in.readEncodedValue(PROPERTY_VALUE_TAG);
//      BValue[] pvs = AsnUtil.fromAsn(propertyValue);
//      if (pvs.length == 1)
//        set(value, pvs[0], noWrite);
//      else
//      {
//        BComponent c = new BComponent();
//        for (int i=0; i<pvs.length; i++)
//        {
//          c.add(null, pvs[i]);
//        }
//      }
      BValue pv = AsnUtil.asnToValue(propertyValue);
      set(value, pv, noWrite);
      getError().setToDefault(noWrite);
    }
    else if (in.isOpeningTag(PROPERTY_ACCESS_ERROR_TAG))
    {
      isError = true;
      propertyValue = null;
      in.skipTag();
      getError().readAsn(in);
      in.skipTag();
    }
    else
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
  }


/////////////////////////////////////////////////////////////////
//  Utility Methods
/////////////////////////////////////////////////////////////////

  /**
   * To String.
   *
   * @return a descriptive string.
   */
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(BBacnetPropertyIdentifier.tag(getPropertyId()));
    if (isPropertyArrayIndexUsed())
      sb.append(" [").append(getPropertyArrayIndex()).append("] ");
    sb.append(":");
    if (isError)
      sb.append(getError().toString(cx));
    else
      sb.append(getValue().toString(cx));

    return sb.toString();
  }

  /**
   * Debug string.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder(32);

    sb.append("\n  " + BBacnetPropertyIdentifier.tag(getPropertyId()));

    if (isPropertyArrayIndexUsed())
      sb.append("[" + getPropertyArrayIndex() + "]");

    if (isError)
      sb.append("\n  err:" + getError().toString());
    else
      sb.append("\n  val:" + getValue().toString());

    return sb.toString();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * NReadPropertyResult Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int PROPERTY_ID_TAG = 2;
  public static final int PROPERTY_ARRAY_INDEX_TAG = 3;
  public static final int PROPERTY_VALUE_TAG = 4;
  public static final int PROPERTY_ACCESS_ERROR_TAG = 5;


/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  private boolean isError = false;
  private byte[] propertyValue = null;
}
