/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This class represents the BBacnetDeviceObjectPropertyReference sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 6 June 02
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
@NiagaraProperty(
  name = "deviceId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT_DEVICE",
  facets = @Facet("BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.DEVICE)")
)
public class BBacnetDeviceObjectPropertyReference
  extends BStruct
  implements BIBacnetDataType, PropertyReference
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference(1380998558)1.0$ @*/
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

  //region Property "deviceId"

  /**
   * Slot for the {@code deviceId} property.
   * @see #getDeviceId
   * @see #setDeviceId
   */
  public static final Property deviceId = newProperty(0, BBacnetObjectIdentifier.DEFAULT_DEVICE, BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.DEVICE));

  /**
   * Get the {@code deviceId} property.
   * @see #deviceId
   */
  public BBacnetObjectIdentifier getDeviceId() { return (BBacnetObjectIdentifier)get(deviceId); }

  /**
   * Set the {@code deviceId} property.
   * @see #deviceId
   */
  public void setDeviceId(BBacnetObjectIdentifier v) { set(deviceId, v, null); }

  //endregion Property "deviceId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDeviceObjectPropertyReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetDeviceObjectPropertyReference()
  {
  }

  /**
   * Constructor.
   *
   * @param objectId.
   */
  public BBacnetDeviceObjectPropertyReference(BBacnetObjectIdentifier objectId)
  {
    setObjectId(objectId);
  }

  /**
   * Constructor.
   *
   * @param objectId.
   * @param propertyId.
   */
  public BBacnetDeviceObjectPropertyReference(BBacnetObjectIdentifier objectId,
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
  public BBacnetDeviceObjectPropertyReference(BBacnetObjectIdentifier objectId,
                                              int propertyId,
                                              int propertyArrayIndex)
  {
    setObjectId(objectId);
    setPropertyId(propertyId);
    setPropertyArrayIndex(propertyArrayIndex);
  }

  /**
   * Constructor.
   *
   * @param objectId.
   * @param propertyId.
   * @param propertyArrayIndex.
   * @param deviceId.
   */
  public BBacnetDeviceObjectPropertyReference(BBacnetObjectIdentifier objectId,
                                              int propertyId,
                                              int propertyArrayIndex,
                                              BBacnetObjectIdentifier deviceId)
  {
    setObjectId(objectId);
    setPropertyId(propertyId);
    setPropertyArrayIndex(propertyArrayIndex);
    setDeviceId(deviceId);
  }


////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /**
   * @return true if the property array index is used.
   */
  public final boolean isPropertyArrayIndexUsed()
  {
    return (getPropertyArrayIndex() != NOT_USED);
  }

  /**
   * @return true if the deviceId is used.
   */
  public final boolean isDeviceIdUsed()
  {
    return !deviceId.isEquivalentToDefaultValue(get(deviceId));
  }


/////////////////////////////////////////////////////////////////
//  Encoding Methods
/////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out)
  {
    out.writeObjectIdentifier(OBJECT_ID_TAG, getObjectId());
    out.writeEnumerated(PROPERTY_ID_TAG, getPropertyId());

    if (isPropertyArrayIndexUsed())
      out.writeUnsignedInteger(PROPERTY_ARRAY_INDEX_TAG, getPropertyArrayIndex());
    if (isDeviceIdUsed())
      out.writeObjectIdentifier(DEVICE_ID_TAG, getDeviceId());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    set(objectId, in.readObjectIdentifier(OBJECT_ID_TAG), noWrite);
    setInt(propertyId, in.readEnumerated(PROPERTY_ID_TAG), noWrite);

    in.peekTag();
    if (in.isValueTag(PROPERTY_ARRAY_INDEX_TAG))
      setInt(propertyArrayIndex, in.readUnsignedInt(PROPERTY_ARRAY_INDEX_TAG), noWrite);
    else
      setInt(propertyArrayIndex, NOT_USED, noWrite);

    in.peekTag();
    if (in.isValueTag(DEVICE_ID_TAG))
      set(deviceId, in.readObjectIdentifier(DEVICE_ID_TAG), noWrite);
// 2007-08-23 ObjectType checking for deviceId - not yet...
//      BBacnetObjectIdentifier id = in.readObjectIdentifier(DEVICE_ID_TAG);
//      if (id.getObjectType() != BBacnetObjectType.DEVICE)
//        throw new AsnException("Invalid object type for DeviceObjectPropertyReference.deviceId:"+id.getObjectType());
//      set(deviceId, id, noWrite);
    else
      set(deviceId, BBacnetObjectIdentifier.DEFAULT_DEVICE, noWrite);
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
      if (isDeviceIdUsed())
        sb.append('_').append(getDeviceId().toString(cx));
    }
    else
    {
      sb.append('[').append(getPropertyArrayIndex()).append(']');
      if (isDeviceIdUsed())
        sb.append(" in ").append(getDeviceId().toString(cx));
    }
    return sb.toString();
  }

  /**
   * Debug string.
   */
  public final String toDebugString()
  {
    StringBuilder sb = new StringBuilder(32);

    sb.append("\n  " + getObjectId().toString());
    sb.append("\n  " + BBacnetPropertyIdentifier.tag(getPropertyId()));

    if (isPropertyArrayIndexUsed())
      sb.append("[" + getPropertyArrayIndex() + "]");
    if (isDeviceIdUsed())
      sb.append("\n  " + getDeviceId().toString());

    return sb.toString();
  }

  /**
   * Write the simple in text format.
   *
   * @throws IOException
   */
  public final String encodeToString()
    throws IOException
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().getObjectType())
      .append(SEP).append(getObjectId().getInstanceNumber())
      .append(SEP).append(getPropertyId());
    if (isPropertyArrayIndexUsed())
      sb.append(SEP).append(getPropertyArrayIndex());
    if (isDeviceIdUsed())
      sb.append(SEP).append(getDeviceId().getObjectType())
        .append(SEP).append(getDeviceId().getInstanceNumber());
    return sb.toString();
  }

  /**
   * Read the simple from text format.
   * The parameter s must be in the form:
   * <p>
   * objectType_instanceNumber_propertyId_propertyArrayIndex_deviceObjectType_deviceInstance
   * Example: 0_0_85_8_10 (AI0, PV in Dev10)
   * Example: 1_3_87_10 (AO3, PA[10] in local device)
   * Example: 1_3_87_10_8_10 (AO3, PA[10] in Dev10)
   *
   * @param s
   */
  public final BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      StringTokenizer st = new StringTokenizer(s, SEP);
      int numTokens = st.countTokens();
      if (numTokens < 3)
        throw new IOException("Incomplete BacnetDeviceObjectPropretyReference:" + s);
      BBacnetObjectIdentifier objId = BBacnetObjectIdentifier.make(
        Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
      int propId = Integer.parseInt(st.nextToken());
      int index = NOT_USED;
      BBacnetObjectIdentifier devId = BBacnetObjectIdentifier.DEFAULT_DEVICE;
      if ((numTokens == 4) || (numTokens == 6))
        index = Integer.parseInt(st.nextToken());
      if (numTokens > 4)
        devId = BBacnetObjectIdentifier.make(
          Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

      return new BBacnetDeviceObjectPropertyReference(objId, propId, index, devId);
    }
    catch (Exception e)
    {
      throw new IOException("Error decoding BBacnetObjectPropertyReference " + s);
    }
  }

  public static final BBacnetDeviceObjectPropertyReference fromString(String s)
  {
    try
    {
      return (BBacnetDeviceObjectPropertyReference)REF.decodeFromString(s);
    }
    catch (IOException e)
    {
      logger.log(Level.SEVERE, "BBacnetDeviceObjectPropertyReference fromString failure", e);
      return null;
    }
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  public static final int MAX_ENCODED_SIZE = 16;
  private static final String SEP = "_";
  private static final BBacnetDeviceObjectPropertyReference REF
    = new BBacnetDeviceObjectPropertyReference();

  /**
   * BBacnetDeviceObjectPropertyReference Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int OBJECT_ID_TAG = 0;
  public static final int PROPERTY_ID_TAG = 1;
  public static final int PROPERTY_ARRAY_INDEX_TAG = 2;
  public static final int DEVICE_ID_TAG = 3;

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");

}
