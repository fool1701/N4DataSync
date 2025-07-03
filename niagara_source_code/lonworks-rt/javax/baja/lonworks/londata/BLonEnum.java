/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.enums.BLonNilEnum;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;


/**
 * BLonEnum extends BLonPrimitive to
 * represent an Enumerated element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  29 May 01
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonEnum
  extends BLonPrimitive
  implements BIEnum, BINumeric, BIBoolean
{
  /** The default enum is LonNilEnum. */
  public static final BLonEnum DEFAULT = new BLonEnum(BLonNilEnum.nil);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonEnum(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Factory method for creating BLonEnum from BEnum
   */
  public static BLonEnum make(BEnum d)
  {
    return new BLonEnum(d) ;
  }

  /**
   * Private constructor.
   */
  private BLonEnum(BEnum d)
  {
    value = d;
  }

  /**
   * Test if the obj is equal in value to this BLonEnum.
   */
  public boolean equals(Object obj)
  {
    if(!(obj instanceof BLonEnum)) return false;

    BEnum objValue = ((BLonEnum)obj).value;
    return compare(objValue);
  }

  public boolean compare(BEnum d)
  {
    return ( (value.getClass() == d.getClass()) && value.equals(d) );
  }


  /**
   *
   */
  public String toString(Context context)
  {
    return value.getDisplayTag(context);
  }

  /**
   * Encode value to its text format.
   */
  public String encodeToString()
    throws IOException
  {
    return encodeClass(value) + " " + value.encodeToString();
  }

  /**
   * Read the primitive from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    int typNamLen = s.indexOf(' ');
    BEnum d = (BEnum)decodeClass(s.substring(0,typNamLen));
    return BLonEnum.make((BEnum)d.decodeFromString(s.substring(typNamLen+1)));
  }

  /**
   * BLonEnum is encoded as using writeUTF().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeClass(value));
    out.writeInt(value.getOrdinal());
  }

  /**
   * BLonEnum is decoded using readUTF().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    BEnum d = (BEnum)decodeClass(in.readUTF());
    return BLonEnum.make(d.getRange().get(in.readInt()));
  }

  private BEnum  value;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////

  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value
    int val = value.getOrdinal();

    // convert to appropriate datatype in stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.E8:
        out.writeSigned8(val);
        break;
      case BLonElementType.EB:
        out.writeBit(val, e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      case BLonElementType.ESB:
        out.writeSignedBit(val, e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      default:
        throw new InvalidTypeException("Invalid datatype for LonEnum.");
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    int ord = 0;

    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.E8:
        ord = in.readSigned8();
        break;
      case BLonElementType.EB:
        ord = in.readBit(e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      case BLonElementType.ESB:
        ord = in.readSignedBit(e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonEnum.");
    }

    // If the value didn't change return original
    if(value.getOrdinal()==ord) return this;

    return make(value.getRange().get(ord));
  }

  /** Get the value of this element as a <code>double</code>. */
  public double getDataAsDouble() { return (double)value.getOrdinal(); }

  /**
   *  Make BEnum of same type from ordinal.
   *  If ordinal is not a valid for current <code>BLonEnum</code> return null.
   */
  public BLonPrimitive makeFromDouble(double ordinal, BLonElementQualifiers e)
  {
    int ord = (int)ordinal;
    BEnum dis = value;
    if(!dis.getRange().isOrdinal(ord)) return null;
    return make(dis.getRange().get(ord));
  }

  /** Get the value of this element as a <code>boolean</code>.
   * @return If the current ordinal is 0 return true else false. */
  public boolean getDataAsBoolean() { return value.isActive(); }

  /** Create a {@code BLonEnum} from a <code>boolean</code>.
    * If true then enum with ordinal=1 else enum with ordinal=0.
    * Return null if this does not map to a valid enum. */
  public BLonPrimitive  makeFromBoolean(boolean boolValue)
  {
    int ord = boolValue ? 1 : 0;
    BEnum dis = value;
    if(!dis.getRange().isOrdinal(ord)) return null;
    return make(dis.getRange().get(ord));
  }

  /** Get the value of this element as a <code>String</code>.
   *  @return the tag of the current enum. */
  public String getDataAsString() { return value.getTag(); }

  /**
   *  Make a <code>BLonEnum</code> with specified tag.
   *  If tag is not valid for this enum return null.
   */
  public BLonPrimitive makeFromString(String tag)
  {
    BEnum dis = value;
    if(!dis.getRange().isTag(tag)) return null;
    return make(dis.getRange().get(tag));
  }

  /**
   * Get the value of this element as a <code>BEnum</code>.
   */
  public BEnum getDataAsEnum(BEnum e)
  {
    return value;
  }

  /**
   * Return a new <code>BLonEnum</code> created with the specified <code>BLonEnum</code>.
   */
  public BLonPrimitive makeFromEnum(BEnum v)
  {
    BLonPrimitive lp = makeFromOrdinal(v.getOrdinal());
    return (lp!=null) ? lp : new BLonEnum(v);     
  }
  
  /**
   * Return a <code>BLonEnum</code> with of the same <code>BLonEnum</code> range with the specified ordinal.
   */
  public BLonPrimitive makeFromOrdinal(int ord)
  {
    BEnum dis = value;
    if(!dis.getRange().isOrdinal(ord)) return null;
    return make(dis.getRange().get(ord));
  }
  
////////////////////////////////////////////////////////////////
// BIEnum,BINumeric,BIBoolean
////////////////////////////////////////////////////////////////

  /** Get the enum value. */
  public BEnum getEnum() { return value; } 

  /** Facets not accessible - return BFacets.NULL. */
  public BFacets getEnumFacets() { return BFacets.makeEnum(value.getRange()); }

  /** Get the numeric as double value.  */
  public double getNumeric() { return getDataAsDouble(); }

  /** Facets not accessible - return BFacets.NULL.  */
  public BFacets getNumericFacets() { return BFacets.NULL; }

  /** Get the boolean value. */
  public boolean getBoolean() { return getDataAsBoolean(); } 
  
  /** Facets not accessible - return BFacets.NULL.  */
  public BFacets getBooleanFacets() { return BFacets.NULL; } 



}
