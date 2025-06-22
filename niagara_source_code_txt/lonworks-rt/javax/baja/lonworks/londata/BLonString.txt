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
import javax.baja.sys.*;

/**
 * BLonString extends BLonPrimitive to
 * represent a string element in a lonworks
 * nv, nci, or config data structure.
 *
 * @author    Robert Adams
 * @creation  8 June 01
 * @version   $Revision: 9$ $Date: 9/28/01 11:21:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonString
  extends BLonPrimitive
{
  /**
   * The default string constant is "".
   */
  public static final BLonString DEFAULT = new BLonString("");

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonString(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonString.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static BLonString make(String value) 
  {
    return new BLonString(value);
  }
  
   /**
   * Create a BLonString with the given value.
   *
   * @throws NullPointerException if value 
   *    is <code>null</code>.
   */
  private BLonString(String value) 
  {
    if (value == null) value = "";
    else this.value = value;
  }

  /**
   * @return the String value.
   */
  public String getString()
  {
    return value;
  }

  /**
   * BLonString uses its String value's hash code. 
   */
  public int hashCode()
  {
    return value.hashCode();
  }
  
  /**
   * BLonString equality is based on String value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonString)
    {
      return value.equals( ((BLonString)obj).value );
    }
    return false;
  }

  /**
   * Compares this object with the specified object for 
   * order. Returns a negative integer, zero, or a positive 
   * integer as this object is less than, equal to, or greater 
   * than the specified object.
   */
  public int compareTo(Object obj)
  {
    return value.compareTo( ((BLonString)obj).getString() );
  }

  /**
   * Get the BTypeSpec for this type's BPlugin.
  public BTypeSpec getPluginTypeSpec()
  {
    return pluginType;
  }
  static final BTypeSpec pluginType = new BTypeSpec("jade", "1.0", "StringPlugin");
   */
  
  /**
   * To string method.
   */
  public String toString(Context context)
  {
    return value;
  }
  
  /**
   * BLonString is encoded as using writeUTF().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(value);
  }
  
  /**
   * BLonString is decoded using readUTF().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    String s = in.readUTF();
    if (s.equals("")) return DEFAULT;
    return new BLonString(s);
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
    throws IOException
  {
    return value;
  }
  
  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (s.equals("")) return DEFAULT;
    return new BLonString(s);
  }  

  private String  value;
  
////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////

  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value
    // convert to appropriate datatyp in stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.ST: out.writeCharArray(value,e.getLength());  break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonString.");
    }
  }


  /**
   *  Translates from network bytes. Sets the
   *  value of the object to the state represented
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    String val;
     
    // get appropriate data type from stream
    switch(e.getElemtype().getOrdinal())
    {
      case BLonElementType.ST: val = in.readCharArray(e.getLength());  break;
      default:
         throw new InvalidTypeException("Invalid datatype for LonString.");
    }
    
    // If the value didn't change return original
    if(value.equals(val)) return this;

    return make(val);
  }

////////////////////////////////////////////////////////////
//  Api Overrides
////////////////////////////////////////////////////////////
  /** Get the value of this element as a <code>float</code>. If string
   *  is not a valid float then return Float.NaN  */
  public double getDataAsDouble()
  {
    double f = Double.NaN;
    try { f = Double.valueOf(value).doubleValue(); } catch(Throwable e){}
    return f; 
  }
  /** Create a {@code BLonString} from a <code>float</code>. */
  public BLonPrimitive  makeFromDouble(double value, BLonElementQualifiers e){ return make(Double.toString(value)); }

  /** Get the value of this element as a <code>boolean</code>. */
  public boolean getDataAsBoolean() { return Boolean.valueOf(value).booleanValue(); }
  /** Create a {@code BLonString} from a <code>boolean</code>.  */
  public BLonPrimitive  makeFromBoolean(boolean boolValue) 
    { return make(boolValue ? "true" : "false" ); }

  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString() { return value; }
  /** Create a {@code BLonString} from a <code>String</code>. */
  public BLonPrimitive  makeFromString(String stringValue)  { return make(stringValue); }

  /**
   *  @return a <code>BEnum</code> created using the current value as the tag. 
   */
  public BEnum getDataAsEnum(BEnum en)
  {
    BEnumRange enRng = en.getRange();
     if(!enRng.isTag(value))
     {
       String s = "";
       try { s =  en.encodeToString(); } catch(Throwable e) {}
       throw new BajaRuntimeException("Cannot map tag (" + value + ") to enum.\n" + s);
     }
     return enRng.get(value); 
  } 

  /**
   * Return a new <code>BLonString</code> with the tag of the specified <code>BEnum</code>.
   */
  public BLonPrimitive makeFromEnum(BEnum v)
  {
    return new BLonString(v.getTag());
  }

}
