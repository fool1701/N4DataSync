/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This class represents the BacnetPropertyReference sequence.
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
  defaultValue = "BBacnetPropertyIdentifier.PRESENT_VALUE"
)
@NiagaraProperty(
  name = "propertyArrayIndex",
  type = "int",
  defaultValue = "NOT_USED"
)
public final class BBacnetPropertyReference
  extends BStruct
  implements PropertyReference,
  BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetPropertyReference(2817054994)1.0$ @*/
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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPropertyReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetPropertyReference()
  {
  }

  /**
   * Constructor.
   *
   * @param propertyId the property-identifier to be referenced.
   */
  public BBacnetPropertyReference(int propertyId)
  {
    setPropertyId(propertyId);
  }

  /**
   * Constructor.
   *
   * @param propertyId         the property-identifier to be referenced.
   * @param propertyArrayIndex the array index.
   */
  public BBacnetPropertyReference(int propertyId,
                                  int propertyArrayIndex)
  {
    setPropertyId(propertyId);
    setPropertyArrayIndex(propertyArrayIndex);
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
    return BBacnetPropertyIdentifier.tag(getPropertyId()) + "[" + getPropertyArrayIndex() + "]";
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

    return sb.toString();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * NBacnetPropertyReference Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int PROPERTY_ID_TAG = 0;
  public static final int PROPERTY_ARRAY_INDEX_TAG = 1;

}
