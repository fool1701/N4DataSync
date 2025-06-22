/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import javax.baja.nre.util.TextUtil;

/**
 * BBitSet is an array of bits.
 *
 * @author    Brian Frank
 * @creation  20 Aug 02
 * @version   $Revision: 3$ $Date: 1/25/08 4:04:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBitSet
  extends BSimple
{
  
  /**
   * Construct a new bitset by setting or clearing the specified bit.
   */
  public static BBitSet make(BBitSet orig, int index, boolean state)
  {
    if (orig.getBit(index) == state) return orig;
    
    int byteIndex = index >> 3;
    int bitMask = 1 << (index % 8);
    
    int len = Math.max(orig.bits.length, byteIndex+1);
    byte[] bits = new byte[len];
    System.arraycopy(orig.bits, 0, bits, 0, orig.bits.length);
    
    if (state)
      bits[byteIndex] |= bitMask;
    else
      bits[byteIndex] &= ~bitMask;
    
    return (BBitSet)(new BBitSet(bits).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BBitSet(byte[] bits)
  {
    this.bits = bits;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * If the specified bit is set return true, 
   * otherwise return false.
   */
  public boolean getBit(int index)
  {
    int byteIndex = index >> 3;
    int bitMask = 1 << (index % 8);
    
    if (byteIndex >= bits.length) return false;
    return (bits[byteIndex] & bitMask) != 0;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * Hashcode for BBitSet is based on the algorthm
   * used by the JDK for java.lang.String and 
   * java.rmi.MarshalledObject.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    if (hashCode == 0)
    {
      int h = 31;
      byte[] v = bits;
      for(int i=0; i<v.length; ++i)
        h = 31*h + v[i];
      hashCode = h;
    }
    return hashCode;
  }
  
  /**
   * BBitSet equality is based on equality of set bits.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBitSet)
    {
      BBitSet x = (BBitSet)obj;
      int min = Math.min(bits.length, x.bits.length);
      for(int i=0; i<min; ++i)
        if (bits[i] != x.bits[i]) return false;
      BBitSet more = min == bits.length ? x : this;
      for(int i=min; i<more.bits.length; ++i)
        if (more.bits[i] != 0)
          return false;  
      return true;
    }
    return false;
  }

  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    return "0x" + encodeToString();
  }  
    
  /**
   * BBitSet is serialized using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BBitSet is unserialized using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }
  
  /**
   * Encode to the string format which is a hex string of the bits.
   */               
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(bits.length*2 + 2);
    boolean nonzero = false;
    for(int i=bits.length-1; i>=0; --i)
    {
      int b = bits[i] & 0xff;
      if (!nonzero) 
      {
        if (b == 0) continue;
        else nonzero = true;
      }
      if (b < 0x10) s.append('0');
      else s.append(hexChars[(b >> 4) & 0xf]);
      s.append(hexChars[b & 0xf]);
    }
    if (!nonzero) return "00";
    return s.toString();
  }

  /**
   * Read the simple from its string format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      byte[] bits = new byte[s.length()/2];
      for(int i=0; i<s.length(); i += 2)
      {
        int hi = TextUtil.hexCharToInt(s.charAt(i));
        int lo = TextUtil.hexCharToInt(s.charAt(i+1));
        bits[bits.length-i/2-1] = (byte)(hi << 4 | lo);
      }
      return new BBitSet(bits).intern();
    }
    catch(RuntimeException e)
    {
      throw new IOException("Invalid BBitSet: " + s);   
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static char[] hexChars = { '0', '1', '2', '3', '4', '5', 
    '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  
  /** The empty set has all bits cleared */
  public static final BBitSet EMPTY = (BBitSet)(new BBitSet(new byte[0]).intern());

  /** The default is EMPTY */
  public static final BBitSet DEFAULT = EMPTY;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBitSet.class);
          
  private byte[] bits;
  private int hashCode = 0;
  
}