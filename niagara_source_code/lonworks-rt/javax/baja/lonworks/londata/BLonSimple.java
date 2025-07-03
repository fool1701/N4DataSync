/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonSimple extends <code>BLonPrimitive</code> to represent a 
 * <code>BSimple</code> which implements the <code>BILonNetworkSimple</code>
 * interface.<p>
 *
 * @author    Robert Adams
 * @creation  8 June 01
 * @version   $Revision: 4$ $Date: 9/28/01 11:21:35 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonSimple
  extends BLonPrimitive
{
  public static final BLonSimple DEFAULT = new BLonSimple(null);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonSimple(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonSimple.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Factory method for creating neuron id 
   * from a byte array.
   */
  public static BLonSimple make(BSimple d)
  {
    return new BLonSimple(d) ;
  }

  /**
   *  Private constructor
   */
  private BLonSimple(BSimple s)
  {
    value = s;
  }
  
  /**
   * Test if the obj is equal in value to this BLonSimple.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BLonSimple)
    {
      return value.equals( ((BLonSimple)obj).value );
    }
    return false;  
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    if (value != null)
    {
      return value.toString(context);
    }

    if (getType() != null)
    {
      return getTypeDisplayName(context);
    }
    else
    {
      return getClass().getName();
    }
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
    BSimple d = decodeClass(s.substring(0,typNamLen));
    return BLonSimple.make((BSimple)d.decodeFromString(s.substring(typNamLen+1)));
  }
  
  /**
   * BLonString is encoded as using writeUTF().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeClass(value));
    value.encode(out);
  }
  
  /**
   * BLonString is decoded using readUTF().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    BSimple d = decodeClass(in.readUTF());
    return BLonSimple.make((BSimple)d.decode(in));
  }

  BSimple value;
  
////////////////////////////////////////////////////////////////
// BLonPrimitive Overrides
////////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out, BLonElementQualifiers e)
  {
    // Get value 
    BILonNetworkSimple val = (BILonNetworkSimple)value;
    val.toOutputStream(out);
  }
  

  /**
   *  Translates from network bytes. Sets the 
   *  value of the object to the state represented 
   *  by the given bytes.
   **/
  public BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e)
  {
    BILonNetworkSimple val = (BILonNetworkSimple)value;
    BSimple newValue = (BSimple)val.fromInputStream(in);
    
    // If the value didn't change return original.
    if(newValue.equals(val)) return this;
    
    return make(newValue);
  }
  
////////////////////////////////////////////////////////////
//  Api Overrides
////////////////////////////////////////////////////////////
  /** Get the value of this element as a <code>String</code>. */
  public String getDataAsString()
  {
    String s;
    try { s = value.encodeToString(); }
    catch(Throwable e) { s = "";}
    return s;
  }  
    
  /** Create a {@code BLonSimple} from a <code>String</code>. Use
   *  the <code>decodeFromString</code> of the current value to get the new value.
   *  If unable to decode stringValue do not modify value this element. */
  public BLonPrimitive makeFromString(String stringValue) 
  {
    BSimple s;
    try { return BLonSimple.make((BSimple)value.decodeFromString(stringValue)); }
    catch(Throwable e){ return null; }
  }
  

}
