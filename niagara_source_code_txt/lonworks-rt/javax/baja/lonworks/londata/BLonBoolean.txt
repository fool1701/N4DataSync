/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonBoolean extends BLonPrimitive to
 * represent a boolean element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  29 May 01
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:39 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonBoolean
  extends BLonPrimitive
  implements BIBoolean, BIEnum
{
  public static final BLonBoolean TRUE = new BLonBoolean(true);
  public static final BLonBoolean FALSE = new BLonBoolean(false);
  public static final BLonBoolean DEFAULT = FALSE;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonBoolean(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonBoolean.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Factory method for creating neuron id 
   * from a byte array.
   */
  public static BLonBoolean make(boolean b)
  {
    return (b) ? TRUE : FALSE ;
  }
 
  /**
   * Private constructor.
   */
  private BLonBoolean(boolean b) 
  {
    value = b;
  }
  
  /**
   * @return the float value.
  public boolean getBoolean()
  {
    return value;
  }

  
  /**
   * Get the BTypeSpec for this type's BPlugin.
  public BTypeSpec getPluginTypeSpec()
  {
    return pluginType;
  }
  static final BTypeSpec pluginType = BTypeSpec.make("lonworks", "LonBooleanPlugin");
   */

  /**
   * Test if the obj is equal in value to this BLonBoolean.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BLonBoolean))
      return false;
    
    return value == ((BLonBoolean)obj).value;
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    return value ? "true" : "false";
  }
  
  /**
   * Encode a boolean primitive value to its
   * text format "true" or "false".
   */
  public String encodeToString()
  {
    return value ? "true" : "false";
  }

  /**
   * Read the primitive from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (s.equals("true")) return TRUE;
    else if (s.equals("false")) return FALSE;
    else throw new IOException("Invalid boolean: " + s);
  }
  
  /**
   * Decode text format directly to a boolean primitive.
   */
  public static boolean booleanFromString(String s)
    throws IOException
  {
    if (s.equals("true")) return true;
    else if (s.equals("false")) return false;
    else throw new IOException("Invalid boolean: " + s);
  }
  /**
   * BLonString is encoded as using writeUTF().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BLonString is decoded using readUTF().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  boolean value;

////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////

  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value
    boolean val = value;
//System.out.println("val = " + val);

    // convert to appropriate datatyp in stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.B8:
        out.writeBoolean(val);
        break;
      case BLonElementType.BB:
        out.writeBooleanBit(val, e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      default:
        throw new InvalidTypeException("Invalid datatype for BLonBoolean.");
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    boolean val;

    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.B8:
        val = in.readBoolean();
        break;
      case BLonElementType.BB:
        val = in.readBooleanBit(e.getByteOffset(), e.getBitOffset(), e.getSize());
        break;
      default:
        throw new InvalidTypeException("Invalid datatype for BLonBoolean.");
    }
    
    return make(val);
  }
  
  /** Get the value of this element as a <code>float</code>. 
    * If true then return 1.0 else return 0.0.*/
  public double getDataAsDouble() { return value ? 1.0 : 0.0; }
  /** Create a BLonBoolean from a <code>float</code>.   
   * If {@code floatValue>0.0} set value to true else false.*/
  public BLonPrimitive  makeFromDouble(double floatValue, BLonElementQualifiers e) { return make(floatValue>0.0); }

  /** Get the value of this element as a <code>boolean</code>. */
  public boolean getDataAsBoolean() { return value; }
  /** Create a BLonBoolean from a <code>boolean</code>.  */
  public BLonPrimitive  makeFromBoolean(boolean boolValue) {return make(boolValue); }

  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return (value ? "true" : "false"); }
  /** Create a BLonBoolean from a <code>String</code>. If stringValue
   *  is not a valid boolean then do not modify this element. */
  public BLonPrimitive  makeFromString(String stringValue) 
  {
    boolean b = false;
    try { b = Boolean.valueOf(stringValue).booleanValue(); } 
    catch(Throwable e){ return null; }
    return make(b); 
  }

////////////////////////////////////////////////////////////////
// BIBoolean,BIEnum
////////////////////////////////////////////////////////////////

  /** Get the boolean value. */
  public boolean getBoolean() { return getDataAsBoolean(); } 
  
  /** Facets not accessible - return BFacets.NULL. */
  public BFacets getBooleanFacets() { return BFacets.NULL; } 

  /** Get the enum value. */
  public BEnum getEnum() { return value ? BBoolean.TRUE : BBoolean.FALSE; } 

  /** Facets not accessible - return BFacets.NULL. */
  public BFacets getEnumFacets() { return BFacets.makeBoolean(); }


}
