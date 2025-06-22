/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This class represents the BBacnetDeviceObjectReference sequence.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 23 July 2008
 * @since Niagara 3.4
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT_DEVICE",
  facets = @Facet("BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.DEVICE)")
)
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT"
)
public class BBacnetDeviceObjectReference
  extends BStruct
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDeviceObjectReference(682060323)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDeviceObjectReference.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetDeviceObjectReference()
  {
  }

  /**
   * Constructor.
   *
   * @param objectId.
   */
  public BBacnetDeviceObjectReference(BBacnetObjectIdentifier objectId)
  {
    setObjectId(objectId);
  }

  /**
   * Constructor.
   *
   * @param objectId.
   * @param deviceId.
   */
  public BBacnetDeviceObjectReference(BBacnetObjectIdentifier deviceId,
                                      BBacnetObjectIdentifier objectId)
  {
    setDeviceId(deviceId);
    setObjectId(objectId);
  }


////////////////////////////////////////////////////////////////
// Access Methods
////////////////////////////////////////////////////////////////

  /**
   * @return true if the deviceId is used.
   */
  public final boolean isDeviceIdUsed()
  {
    return !deviceId.isEquivalentToDefaultValue(get(deviceId));
  }


/////////////////////////////////////////////////////////////////
// Encoding Methods
/////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out)
  {
    if (isDeviceIdUsed())
      out.writeObjectIdentifier(DEVICE_ID_TAG, getDeviceId());
    out.writeObjectIdentifier(OBJECT_ID_TAG, getObjectId());
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    in.peekTag();
    if (in.isValueTag(DEVICE_ID_TAG))
      set(deviceId, in.readObjectIdentifier(DEVICE_ID_TAG), noWrite);
//  2008-07-23 ObjectType checking for deviceId - not yet...
//  BBacnetObjectIdentifier id = in.readObjectIdentifier(DEVICE_ID_TAG);
//  if (id.getObjectType() != BBacnetObjectType.DEVICE)
//  throw new AsnException("Invalid object type for DeviceObjectReference.deviceId:"+id.getObjectType());
//  set(deviceId, id, noWrite);
    else
      set(deviceId, BBacnetObjectIdentifier.DEFAULT_DEVICE, noWrite);

    set(objectId, in.readObjectIdentifier(OBJECT_ID_TAG), noWrite);
  }


/////////////////////////////////////////////////////////////////
// Utility Methods
/////////////////////////////////////////////////////////////////

  /**
   * To String.
   *
   * @return a descriptive string.
   */
  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(cx));
    if (isDeviceIdUsed())
    {
      if ((cx != null) && cx.equals(nameContext))
        sb.append('_').append(getDeviceId().toString(cx));
      else
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

    if (isDeviceIdUsed())
      sb.append("\n  " + getDeviceId().toString());
    sb.append("\n  " + getObjectId().toString());

    return sb.toString();
  }

  /**
   * Write the simple in text format.
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
   */

  /**
   * Read the simple from text format.
   * The parameter s must be in the form:
   *
   * objectType_instanceNumber_propertyId_propertyArrayIndex_deviceObjectType_deviceInstance
   * Example: 0_0_85_8_10 (AI0, PV in Dev10)
   * Example: 1_3_87_10 (AO3, PA[10] in local device)
   * Example: 1_3_87_10_8_10 (AO3, PA[10] in Dev10)
   * @param s
  public final BObject decodeFromString(String s)
  throws IOException
  {
  try
  {
  StringTokenizer st = new StringTokenizer(s, SEP);
  int numTokens = st.countTokens();
  if (numTokens < 3)
  throw new IOException("Incomplete BacnetDeviceObjectPropretyReference:"+s);
  BBacnetObjectIdentifier objId = BBacnetObjectIdentifier. make(
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
  throw new IOException("Error decoding BBacnetObjectPropertyReference "+s);
  }
  }
   */

  /**
   * public static final BBacnetDeviceObjectPropertyReference fromString(String s)
   * {
   * try
   * {
   * return (BBacnetDeviceObjectPropertyReference)REF.decodeFromString(s);
   * }
   * catch (IOException e)
   * {
   * return null;
   * }
   * }
   */


/////////////////////////////////////////////////////////////////
// Constants
/////////////////////////////////////////////////////////////////

  public static final int MAX_ENCODED_SIZE = 10;
//  private static final String SEP = "_";
//  private static final BBacnetDeviceObjectReference REF
//    = new BBacnetDeviceObjectReference();

  /**
   * BBacnetDeviceObjectReference Asn Context Tags
   * See Bacnet Clause 21.
   */
  public static final int DEVICE_ID_TAG = 0;
  public static final int OBJECT_ID_TAG = 1;

}
