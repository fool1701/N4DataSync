/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.*;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnUtil;

/**
 * This class represents the BacnetPropertyValue sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 29 Jul 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "propertyId",
  type = "int",
  defaultValue = "BBacnetPropertyIdentifier.PRESENT_VALUE"
)
@NiagaraProperty(
  name = "propertyArrayIndex",
  type = "int",
  defaultValue = "NOT_USED"
)
@NiagaraProperty(
  name = "value",
  type = "BValue",
  defaultValue = "BBacnetNull.DEFAULT"
)
@NiagaraProperty(
  name = "priority",
  type = "int",
  defaultValue = "0"
)
public final class BBacnetPropertyValue
  extends BComponent
  implements PropertyValue,
  BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetPropertyValue(3931723263)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "propertyId"

  /**
   * Slot for the {@code propertyId} property.
   * @see #getPropertyId
   * @see #setPropertyId
   */
  public static final Property propertyId = newProperty(0, BBacnetPropertyIdentifier.PRESENT_VALUE, null);

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
  public static final Property propertyArrayIndex = newProperty(0, NOT_USED, null);

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
  public static final Property value = newProperty(0, BBacnetNull.DEFAULT, null);

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

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(0, 0, null);

  /**
   * Get the {@code priority} property.
   * @see #priority
   */
  public int getPriority() { return getInt(priority); }

  /**
   * Set the {@code priority} property.
   * @see #priority
   */
  public void setPriority(int v) { setInt(priority, v, null); }

  //endregion Property "priority"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPropertyValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetPropertyValue()
  {
  }

  /**
   * Constructor.
   *
   * @param propertyId the property-identifier to be referenced.
   * @param value      the property value.
   */
  public BBacnetPropertyValue(int propertyId,
                              BSimple value)
  {
    this(propertyId, NOT_USED, value, 0);
  }

  /**
   * Constructor.
   *
   * @param propertyId         the property-identifier to be referenced.
   * @param propertyArrayIndex the array index.
   */
  public BBacnetPropertyValue(int propertyId,
                              int propertyArrayIndex,
                              BSimple value)
  {
    this(propertyId, propertyArrayIndex, value, 0);
  }

  /**
   * Constructor.
   *
   * @param propertyId         the property-identifier to be referenced.
   */
  public BBacnetPropertyValue(int propertyId,
                              BSimple value,
                              int priority)
  {
    this(propertyId, NOT_USED, value, priority);
  }

  /**
   * Constructor.
   *
   * @param propertyId         the property-identifier to be referenced.
   * @param propertyArrayIndex the array index.
   */
  public BBacnetPropertyValue(int propertyId,
                              int propertyArrayIndex,
                              BSimple value,
                              int priority)
  {
    setPropertyId(propertyId);
    setPropertyArrayIndex(propertyArrayIndex);

//    if (value instanceof BIBacnetDataType)
    setValue(value);
//    else
//      throw new IllegalArgumentException("BacnetPropertyValue.value must be IBacnetDataType");

    if ((priority < 0) || (priority > 16))
      throw new IllegalArgumentException("BacnetPropertyValue.priority must be 1-16");
    setPriority(priority);
  }


////////////////////////////////////////////////////////////////
//  PropertyValue
////////////////////////////////////////////////////////////////

  /**
   * Get the encoded value.
   *
   * @return a byte array containing the Asn-encoded value,
   * or null if this is a failure.
   */
  public byte[] getPropertyValue()
  {
    return AsnUtil.toAsn(getValue());
  }

  /**
   * Get the error.
   *
   * @return an ErrorType if this is an error result,
   * or null if this is a success.
   */
  public ErrorType getPropertyAccessError()
  {
    return null;
  }

  /**
   * Get the error class.
   *
   * @return an int representing a value in the BBacnetErrorClass
   * enumeration indicating the class of failure,
   * or null if this is a success.
   */
  public int getErrorClass()
  {
    throw new IllegalStateException();
  }

  /**
   * Get the error code.
   *
   * @return an int representing a value in the BBacnetErrorCode
   * enumeration indicating the reason for failure,
   * or null if this is a success.
   */
  public int getErrorCode()
  {
    throw new IllegalStateException();
  }

  /**
   * Is this a failure result?
   *
   * @return TRUE if this is an error result, or FALSE if it is a success.
   */
  public boolean isError()
  {
    return false;
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * @return true if the property array index is used.
   */
  public boolean isPropertyArrayIndexUsed()
  {
    return (getPropertyArrayIndex() != NOT_USED);
  }

  /**
   * @return true if the property array index is used.
   */
  public boolean isPriorityUsed()
  {
    return (getPriority() > 0);
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

    out.writeEncodedValue(VALUE_TAG, AsnUtil.toAsn(getValue()));
    if (isPriorityUsed())
      out.writeUnsignedInteger(PRIORITY_TAG, getPriority());
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

    in.peekTag();
    if (in.isValueTag(PROPERTY_ARRAY_INDEX_TAG))
      setInt(propertyArrayIndex, in.readUnsignedInt(PROPERTY_ARRAY_INDEX_TAG), noWrite);
    else
      setInt(propertyArrayIndex, NOT_USED, noWrite);

//    BValue[] vals = AsnUtil.fromAsn(in.readEncodedValue(VALUE_TAG));
//    if (vals[0].isSimple())
//      setValue((BSimple)vals[0]);
//    else
//      throw new AsnException("BacnetPropertyValue value must be BSimple");
//    if (vals.length > 1)
//      logger.warning("BacnetPropertyValue "+this+" cannot handle multiple values!");
    BValue val = AsnUtil.asnToValue(in.readEncodedValue(VALUE_TAG));
    setValue(val);

    in.peekTag();
    if (in.isValueTag(PRIORITY_TAG))
      setInt(priority, in.readUnsignedInt(PRIORITY_TAG), noWrite);
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
      sb.append('[').append(getPropertyArrayIndex()).append("]:");
    else
      sb.append(':');
    sb.append(getValue().toString(cx));
    if (isPriorityUsed())
      sb.append(" @").append(getPriority());

    return sb.toString();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * BacnetPropertyValue Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int PROPERTY_ID_TAG = 0;
  public static final int PROPERTY_ARRAY_INDEX_TAG = 1;
  public static final int VALUE_TAG = 2;
  public static final int PRIORITY_TAG = 3;
  private static final Log logger = Log.getLog("bacnet");
}
