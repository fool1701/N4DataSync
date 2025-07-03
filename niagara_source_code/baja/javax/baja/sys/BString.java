/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * The BString is the wrapper interface for Java
 * java.lang.String objects.  BString's do not
 * support {@code null} values.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 32$ $Date: 3/28/05 9:23:12 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BString
  extends BSimple
  implements BIComparable, BIDataValue
{ 

  /**
   * Factory.
   *
   * @throws NullPointerException if value 
   *    is {@code null}.
   */
  public static BString make(String s)
  {
    if (s.equals("")) return DEFAULT;
    return new BString(s);
  }

  /**
   * Create a BString with the given value.
   */
  private BString(String value) 
  {
    this.value = value;
  }

  /**
   * @return the String value.
   */
  public String getString()
  {
    return value;
  }

  /**
   * BString uses its String value's hash code. 
   */
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Do a string comparision taking into account null.
   */
  public static boolean equals(String a, String b)
  {
    if (a == null) return b == null;
    if (b == null) return false;
    return a.equals(b);
  }
  
  /**
   * BString equality is based on String value equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BString)
    {
      return value.equals( ((BString)obj).value );
    }
    return false;
  }

  /**
   * Compares this object with the specified object for 
   * order. Returns a negative integer, zero, or a positive 
   * integer as this object is less than, equal to, or greater 
   * than the specified object.
   */
  @Override
  public int compareTo(Object obj)
  {
    return value.compareTo( ((BString)obj).getString() );
  }
  
  /**
   * To string method.
   */
  @Override
  public String toString(Context context)
  {
    return value;
  }
  
  /**
   * BString is encoded using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(value);
  }
  
  /**
   * BString is decoded using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    String s = in.readUTF();
    return make(s);
  }

  /**
   * Write the simple in text format.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return value;
  }
  
  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }  

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }

  /**
   * The default string constant is "".
   */
  public static final BString DEFAULT = new BString("");

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BString.class);
      
  private String value;
  
}
