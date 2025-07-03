/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBacnetObjectIdentifier represents an object-identifier value in a Bacnet property.
 * For convenience,
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 25 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBacnetObjectIdentifier
  extends BSimple
  implements BIComparable
{
  /**
   * Private constructor.
   *
   * @param objectType     int
   * @param instanceNumber
   */
  private BBacnetObjectIdentifier(int objectType, int instanceNumber)
  {
    this.objectType = objectType;
    this.instanceNumber = instanceNumber;
    getHashCode();
  }

  /**
   * Factory method.
   *
   * @param objectType
   */
  public static BBacnetObjectIdentifier make(int objectType)
  {
    return new BBacnetObjectIdentifier(objectType, -1);
  }

  /**
   * Fully specified constructor with object type.
   *
   * @param objectType     int
   * @param instanceNumber
   */
  public static BBacnetObjectIdentifier make(int objectType, int instanceNumber)
  {
    return new BBacnetObjectIdentifier(objectType, instanceNumber);
  }

  /**
   * Fully specified constructor with object type.
   * @since Niagara 4.10u8
   * @since Niagara 4.13u3
   * @since Niagara 4.14
   */
  public static BBacnetObjectIdentifier make(BBacnetObjectType objectType, int instanceNumber)
  {
    return new BBacnetObjectIdentifier(objectType.getOrdinal(), instanceNumber);
  }

  /**
   * Factory method.
   *
   * @param objectId
   */
  public static BBacnetObjectIdentifier makeId(int objectId)
  {
    int objectType = (objectId >> OBJECT_TYPE_SHIFT) & OBJECT_TYPE_MASK_SHIFTED;
    int instanceNumber = objectId & INSTANCE_NUMBER_MASK;
    return new BBacnetObjectIdentifier(objectType, instanceNumber);
  }


////////////////////////////////////////////////////////////////
//  BSimple
////////////////////////////////////////////////////////////////

  /**
   * BBacnetObjectIdentifier equality is based on the type and instance.
   *
   * @param obj comparison object.
   * @return true if the objects represent the same Bacnet Object Identifier.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBacnetObjectIdentifier)
      return ((((BBacnetObjectIdentifier)obj).objectType == objectType) &&
        (((BBacnetObjectIdentifier)obj).instanceNumber == instanceNumber));
    return false;
  }

  /**
   * To String.
   */
  public String toString(Context context)
  {
    char sep = SEP;
    StringBuilder sb = new StringBuilder();
    try
    {
      if (context != null)
      {
        if (context.equals(BacnetConst.nameContext)
          || context.equals(BacnetConst.facetsContext))
          sep = NAME_SEP;
        BEnumRange r = (BEnumRange)context.getFacet(BFacets.RANGE);
        if (r != null)
          sb.append(r.getTag(objectType))
            .append(sep)
            .append(instanceNumber);
        else
          sb.append(BBacnetObjectType.tag(objectType))
            .append(sep)
            .append(instanceNumber);
        if (context.equals(BacnetConst.nameContext)
          || context.equals(BacnetConst.facetsContext))
          return SlotPath.escape(sb.toString());
        else
          return sb.toString();
      }
      return encodeToString();
    }
    catch (IOException e)
    {
      return e.toString();
    }
  }

  /**
   * Get a short-form string for this object ("AO1", "BO3", etc)
   */
  public String toShortString()
  {
    if (instanceNumber == UNCONFIGURED_INSTANCE_NUMBER) return "";
    return BBacnetObjectType.getShortTag(objectType) + instanceNumber;
  }

  /**
   * Hash code.
   * The hash code for a BBacnetObjectIdentifier is its integer value.
   */
  public int hashCode()
  {
    return hashCode;
  }

  /**
   * BBacnetObjectIdentifier is serialized using calls to writeInt().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(objectType);
    out.writeInt(instanceNumber);
  }

  /**
   * BBacnetObjectIdentifier is unserialized using calls to readInt().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BBacnetObjectIdentifier(in.readInt(), in.readInt());
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return BBacnetObjectType.tag(objectType) + SEP + String.valueOf(instanceNumber);
  }

  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      StringTokenizer st = new StringTokenizer(s, ":_ ");
      int objectType = BBacnetObjectType.ordinal(st.nextToken());
      int instanceNumber = Integer.parseInt(st.nextToken());
      return new BBacnetObjectIdentifier(objectType, instanceNumber);
    }
    catch (Exception e)
    {
      throw new IOException("Error decoding BBacnetObjectIdentifier " + s);
    }
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Is this id valid?
   * Validity test is a check that both the object type and
   * instance number are nonegative.
   *
   * @return true if the id is valid, or false otherwise.
   */
  public boolean isValid()
  {
    return objectType >= 0 &&
      instanceNumber >= 0 &&
      instanceNumber < UNCONFIGURED_INSTANCE_NUMBER;
  }

  /**
   * Get the object type.
   */
  public int getObjectType()
  {
    return objectType;
  }

  /**
   * Get the instance number.
   */
  public int getInstanceNumber()
  {
    return instanceNumber;
  }

  /**
   * Get the integer id value.
   */
  public int getId()
  {
    return hashCode();
  }

  /**
   * Get a new id of the same type, but with the given instance number.
   */
  public BBacnetObjectIdentifier newId(int newInstanceNumber)
  {
    return new BBacnetObjectIdentifier(this.objectType, newInstanceNumber);
  }


////////////////////////////////////////////////////////////////
// Comparable
////////////////////////////////////////////////////////////////

  public int compareTo(Object o)
  {
    if (!(o instanceof BBacnetObjectIdentifier))
      throw new ClassCastException();

    BBacnetObjectIdentifier id = (BBacnetObjectIdentifier)o;
    long my = hashCode();
    long his = id.hashCode();
    return (int)(my - his);
  }

  private void getHashCode()
  {
    hashCode = ((objectType << OBJECT_TYPE_SHIFT) & OBJECT_TYPE_MASK) | (instanceNumber & INSTANCE_NUMBER_MASK);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int OBJECT_TYPE_MASK = 0xFFC00000;
  public static final int OBJECT_TYPE_MASK_SHIFTED = 0x000003FF;
  public static final int INSTANCE_NUMBER_MASK = 0x003FFFFF;
  public static final int OBJECT_TYPE_SHIFT = 22;

  // Instance limits for objectIds.
  // Note that the high limit is one less than the actual limit,
  // because the value 4194303 is reserved as of Bacnet2001 Addendum a,
  // to represent an unconfigured value.
  public static final int MIN_INSTANCE_NUMBER = 0;
  public static final int MAX_INSTANCE_NUMBER = 4194302;
  public static final int UNCONFIGURED_INSTANCE_NUMBER = 4194303;

  private static final char SEP = ':';
  private static final char NAME_SEP = '_';

  /**
   * The default objectId is analog_input:0.
   */
  public static final BBacnetObjectIdentifier DEFAULT = new BBacnetObjectIdentifier(0, -1);
  public static final BBacnetObjectIdentifier DEFAULT_DEVICE = new BBacnetObjectIdentifier(8, -1);

//  public static final BBacnetObjectIdentifier DEFAULT_ANALOG_INPUT = new BBacnetObjectIdentifier(0, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_ANALOG_OUTPUT = new BBacnetObjectIdentifier(1, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_ANALOG_VALUE = new BBacnetObjectIdentifier(2, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_BINARY_INPUT = new BBacnetObjectIdentifier(3, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_BINARY_OUTPUT = new BBacnetObjectIdentifier(4, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_BINARY_VALUE = new BBacnetObjectIdentifier(5, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_CALENDAR = new BBacnetObjectIdentifier(6, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_COMMAND = new BBacnetObjectIdentifier(7, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_EVENT_ENROLLMENT = new BBacnetObjectIdentifier(9, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_FILE = new BBacnetObjectIdentifier(10, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_GROUP = new BBacnetObjectIdentifier(11, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_LOOP = new BBacnetObjectIdentifier(12, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_MULTI_STATE_INPUT = new BBacnetObjectIdentifier(13, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_MULTI_STATE_OUTPUT = new BBacnetObjectIdentifier(14, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_NOTIFICATION_CLASS = new BBacnetObjectIdentifier(15, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_PROGRAM = new BBacnetObjectIdentifier(16, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_SCHEDULE = new BBacnetObjectIdentifier(17, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_AVERAGING = new BBacnetObjectIdentifier(18, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_MULTI_STATE_VALUE = new BBacnetObjectIdentifier(19, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_TREND_LOG = new BBacnetObjectIdentifier(20, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_LIFE_SAFETY_POINT = new BBacnetObjectIdentifier(21, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_LIFE_SAFETY_ZONE = new BBacnetObjectIdentifier(22, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_ACCUMULATOR = new BBacnetObjectIdentifier(23, -1);
//  public static final BBacnetObjectIdentifier DEFAULT_PULSE_CONVERTER = new BBacnetObjectIdentifier(24, -1);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetObjectIdentifier.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final int objectType;
  private final int instanceNumber;
  private int hashCode;
}