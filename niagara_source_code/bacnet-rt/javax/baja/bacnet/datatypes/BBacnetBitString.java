/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BBacnetBitString represents a bit string value in a Bacnet property.
 * It is represented in Niagara as an array of boolean values.  Facets can
 * be applied to a slot containing a BBacnetBitString to provide names for
 * the various bits.
 *
 * @author Craig Gemmill
 * @version $Revision: 14$ $Date: 12/13/01 3:37:26 PM$
 * @creation 20 Jun 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBacnetBitString
  extends BSimple
{

  /**
   * Private constructor.
   * Note that a copy of the bits is NOT made, because this
   * is only called from within BBacnetBitString.java.  All
   * places that call the constructor are using either a copy
   * of the original bits, or a specially constructed array.
   *
   * @param bits the array of booleans.
   * @param tags the bit names.
   */
  private BBacnetBitString(boolean[] bits, BFacets tags)
  {
    this.bits = bits;
    this.tags = tags;
    getHashCode();
  }

  /**
   * Factory method.
   *
   * @param bits the array of boolean bit values.
   * @return a BBacnetBitString with these bits set.
   */
  public static BBacnetBitString make(boolean[] bits, BFacets tags)
  {
    boolean[] mybits = new boolean[bits.length];
    System.arraycopy(bits, 0, mybits, 0, mybits.length);
    return new BBacnetBitString(mybits, tags);
  }

  /**
   * Factory method.
   *
   * @param bits the array of boolean bit values.
   * @return a BBacnetBitString with these bits set.
   */
  public static BBacnetBitString make(boolean[] bits)
  {
    boolean[] mybits = new boolean[bits.length];
    System.arraycopy(bits, 0, mybits, 0, mybits.length);
    return new BBacnetBitString(mybits, BFacets.NULL);
  }

  /**
   * Factory method for "setBit" functionality.
   * Makes a new bit string exactly like the current one, but with the
   * given bit set to the specified state.
   *
   * @param index    the bit to be set.
   * @param newState the new state of the bit.
   * @return a new bit string with the specified bit set.
   */
  public static BBacnetBitString make(BBacnetBitString bs, int index, boolean newState)
  {
    boolean[] newbits = new boolean[bs.bits.length];
    System.arraycopy(bs.bits, 0, newbits, 0, bs.bits.length);
    newbits[index] = newState;
    return new BBacnetBitString(newbits, bs.tags);
  }


////////////////////////////////////////////////////////////////
//  BSimple
////////////////////////////////////////////////////////////////

  /**
   * BBacnetBitString equality is based on all bits being equal.
   * The tags are not included in the comparison.
   *
   * @param obj the comparison object.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBacnetBitString)
    {
      boolean[] comp = ((BBacnetBitString)obj).bits;
      if (bits.length != comp.length)
        return false;
      for (int i = 0; i < bits.length; i++)
      {
        if (comp[i] != bits[i])
          return false;
      }
      return true;
    }
    return false;
  }

  /**
   * To String.
   * The standard serialization of BBacnetBitString supports facets inclusion.
   */
  public String toString(Context context)
  {
    return getActiveTags(context);
  }

  /**
   * Hash code.
   * The hash code for a BBacnetBitString is calculated by
   * calculating the integer formed by setting all of the corresponding
   * bits in an int.  For bit strings longer than 32 bits, the
   * low 32 bits are XOR'd with the next 32 bits.
   */
  public int hashCode()
  {
    return hashCode;
  }

  /**
   * BBacnetBitString is serialized using writeBoolean().
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(bits.length);
    for (int i = 0; i < bits.length; i++)
      out.writeBoolean(bits[i]);
    tags.encode(out);
  }

  /**
   * BBacnetBitString is unserialized using readBoolean().
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    int len = in.readInt();
    boolean[] bits = new boolean[len];
    for (int i = 0; i < len; i++)
      bits[i] = in.readBoolean();
    BFacets tags = (BFacets)BFacets.DEFAULT.decode(in);
    return new BBacnetBitString(bits, tags);
  }

  /**
   * Write the simple in text format.
   */
  public String encodeToString()
    throws IOException
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getCompleteString());
    sb.append(':');
    sb.append(tags.encodeToString());
    return sb.toString();
  }

  /**
   * Read the simple from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    BFacets tags = BFacets.NULL;
    int len = s.indexOf(":");
    int tstart = -1;
    if (len < 0)
      len = s.length();
    else
      tstart = len + 1;

    boolean[] bits = new boolean[len];
    for (int i = 0; i < len; i++)
      bits[i] = (s.charAt(i) == '1');

    if (tstart > 0)
    {
      String t = s.substring(len + 1);
      tags = (BFacets)BFacets.DEFAULT.decodeFromString(t);
    }
    return new BBacnetBitString(bits, tags);
  }


//////////////////////////////////////////////////////////////
//  Access
//////////////////////////////////////////////////////////////

  /**
   * @return the bit string length.
   */
  public int length()
  {
    return bits.length;
  }

  /**
   * @param index the location of the bit to get.
   * @return the boolean value of the bit.
   * @throws IllegalArgumentException if index is out of range.
   */
  public boolean getBit(int index)
  {
    try
    {
      return bits[index];
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException(
        "Invalid index (" + index + ") in getBit()!!");
    }
  }

  /**
   * Get (a copy of) the bits array.
   *
   * @return a boolean array indicating the status of each bit.
   */
  public boolean[] getBits()
  {
    boolean[] copy = new boolean[bits.length];
    System.arraycopy(bits, 0, copy, 0, bits.length);
    return copy;
  }

  /**
   * Instance setBit method.
   * Makes a new bit string exactly like the current one, but with the
   * given bit set to the specified state.
   * @param index the bit to be set.
   * @param newState the new state of the bit.
   * @return a new bit string with the specified bit set.
  private BBacnetBitString setBit(int index, boolean newState)
  {
  boolean[] newbits = new boolean[bits.length];
  System.arraycopy(bits, 0, newbits, 0, bits.length);
  newbits[index] = newState;
  return new BBacnetBitString(newbits, tags);
  }
   */

  /**
   * Get the list of tags for the active bits.  This uses the
   * supplied context, or if null, it attempts to use the local
   * context.  If both are null, it simply displays the bitstring.
   *
   * @param context the tag context.
   * @return the tag names for the active bits in this bitstring.
   */
  public String getActiveTags(Context context)
  {
    Context cx = context;
    if (cx == null) cx = tags;
    if ((cx != null) && !cx.equals(BFacets.NULL))
    {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bits.length; i++)
        if (bits[i])
        {
          BObject bitName = cx.getFacet("bit" + String.valueOf(i));
          if (bitName == null)
            return getCompleteString();

          sb.append(bitName);
          sb.append(";");
        }
      if (sb.length() > 0)
        return sb.substring(0, sb.length() - 1);
      else
        return "none";
    }

    return getCompleteString();
  }

  public String getCompleteTagList(Context context)
  {
    Context cx = context;
    if (cx == null) cx = tags;
    if ((cx != null) && !cx.equals(BFacets.NULL))
    {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bits.length; i++)
      {
        sb.append(cx.getFacet("bit" + String.valueOf(i)));
        sb.append("=" + bits[i] + ";");
      }

      if (sb.length() > 0)
        return sb.substring(0, sb.length() - 1);
      else
        return "none";
    }

    return getCompleteString();

  }

  public String getCompleteString()
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bits.length; i++)
      sb.append(bits[i] ? '1' : '0');
    return sb.toString();
  }

//////////////////////////////////////////////////////////////
//  Utility
//////////////////////////////////////////////////////////////

  /**
   * Create an empty bit string of the given length.
   *
   * @param len
   * @return empty bit string.
   */
  public static BBacnetBitString emptyBitString(int len)
  {
    return make(new boolean[len]);
  }

  private void getHashCode()
  {
    int len = bits.length;
    int n = len / 32;

    for (int i = 0; i < n; i++)
    {
      int hc = 0;
      for (int j = 0; j < 32; j++)
      {
        int ndx = i * 32 + j;
        if (ndx >= len) break;
        if (bits[ndx]) hc |= 1 << j;
      }
      hashCode ^= hc;
    }
  }


//////////////////////////////////////////////////////////////
//  Constants
//////////////////////////////////////////////////////////////


  public static final BBacnetBitString DEFAULT = new BBacnetBitString(new boolean[0], BFacets.NULL);

  public Type getType()
  {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BBacnetBitString.class);


//////////////////////////////////////////////////////////////
//  Attributes
//////////////////////////////////////////////////////////////

  private boolean[] bits;
  private BFacets tags;
  private int hashCode;
}
