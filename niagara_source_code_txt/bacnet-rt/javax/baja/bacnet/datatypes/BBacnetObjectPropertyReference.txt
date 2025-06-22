/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This class represents the BBacnetObjectPropertyReference sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 31 May 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT"
)
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
public final class BBacnetObjectPropertyReference
  extends BStruct
  implements BIBacnetDataType, PropertyReference
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetObjectPropertyReference(1132499493)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(0, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Get the {@code objectId} property.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

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
  public static final Type TYPE = Sys.loadType(BBacnetObjectPropertyReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetObjectPropertyReference()
  {
  }

  /**
   * Constructor.
   */
  public BBacnetObjectPropertyReference(BBacnetObjectIdentifier objectId)
  {
    setObjectId(objectId);
  }

  /**
   * Constructor.
   *
   * @param objectId.
   * @param propertyId.
   */
  public BBacnetObjectPropertyReference(BBacnetObjectIdentifier objectId,
                                        int propertyId)
  {
    setObjectId(objectId);
    setPropertyId(propertyId);
  }

  /**
   * Constructor.
   *
   * @param objectId.
   * @param propertyId.
   * @param propertyArrayIndex.
   */
  public BBacnetObjectPropertyReference(BBacnetObjectIdentifier objectId,
                                        int propertyId,
                                        int propertyArrayIndex)
  {
    setObjectId(objectId);
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
    out.writeObjectIdentifier(OBJECT_ID_TAG, getObjectId());
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
    set(objectId, in.readObjectIdentifier(OBJECT_ID_TAG), noWrite);
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
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(cx)).append('_')
      .append(BBacnetPropertyIdentifier.tag(getPropertyId()));
    if ((cx != null) && cx.equals(nameContext))
    {
      if (isPropertyArrayIndexUsed())
        sb.append('_').append(getPropertyArrayIndex());
    }
    else
    {
      sb.append('[').append(getPropertyArrayIndex()).append(']');
    }
    return sb.toString();
  }

  /**
   * Debug string.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder(32);

    sb.append("\n  " + getObjectId().toString());
    sb.append("\n  " + BBacnetPropertyIdentifier.tag(getPropertyId()));

    if (isPropertyArrayIndexUsed())
      sb.append("[" + getPropertyArrayIndex() + "]");

    return sb.toString();
  }

  // Scott added 8/27/03 for alarming purposes

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return getObjectId().encodeToString() + '|' +
      String.valueOf(getPropertyId()) + '|' +
      String.valueOf(getPropertyArrayIndex());
  }

  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      StringTokenizer st = new StringTokenizer(s, "|");
      BBacnetObjectIdentifier temp = BBacnetObjectIdentifier.DEFAULT;
      BBacnetObjectIdentifier newObjectId = (BBacnetObjectIdentifier)(temp.decodeFromString(st.nextToken()));
      int propId = Integer.parseInt(st.nextToken());
      int propArrayIndex = Integer.parseInt(st.nextToken());
      return new BBacnetObjectPropertyReference(newObjectId, propId, propArrayIndex);
    }
    catch (Exception e)
    {
      throw new IOException("Error decoding BBacnetObjectPropertyReference " + s);
    }
  }

/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  /**
   * BBacnetObjectPropertyReference Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int OBJECT_ID_TAG = 0;
  public static final int PROPERTY_ID_TAG = 1;
  public static final int PROPERTY_ARRAY_INDEX_TAG = 2;

}
