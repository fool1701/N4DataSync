/*
 * Copyright 2003, Tridium Inc., All rights reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BBitString;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTrendFlags is the set of flags that can be set for a trend record.  They
 * provide extra context information about the record data.
 *
 * @author    John Sublett
 * @creation
 * @version   $Revision: 11$ $Date: 12/10/08 12:56:45 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BTrendFlags
  extends BBitString
{
  /**
   * Get the byte containing the flag bits.
   */
  public byte getBits() { return bits; }

  /**
   * Get a new instance that is equal to this instance with the specified bit
   * set to the new state.
   */
  public BTrendFlags set(int ordinal, boolean newState)
  {
    byte newBits = getBits();
    if (newState)
      newBits |= (byte)(0xFF & ordinal);
    else
      newBits &= ~((byte)(0xFF & ordinal));

    return make(newBits);
  }

////////////////////////////////////////////////////////////////
// Factory methods
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance in which all days are true
   */
  public static BTrendFlags make()
  {
    return BTrendFlags.DEFAULT;
  }

  /**
   * Construct an instance in which all days are
   * set to the given value
   */
  public static BTrendFlags make(int bits)
  {
    if (bits == 0)
      return DEFAULT;
    else
      return (BTrendFlags)(new BTrendFlags((byte)(0xFF & bits)).intern());
  }

  /**
   * Construct an instance in which all days are
   * set to the given value
   */
  public static BTrendFlags make(byte bits)
  {
    if (bits == 0)
      return DEFAULT;
    else
      return (BTrendFlags)(new BTrendFlags(bits).intern());
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance in with no flags set.
   */
  private BTrendFlags()
  {
    bits = 0;
  }

  /**
   *  Construct an instance using the given bits
   */
  private BTrendFlags(byte bits)
  {
    this.bits = bits;
  }

////////////////////////////////////////////////////////////////
// BBitString
////////////////////////////////////////////////////////////////

  /**
   * Return if the bit specified by the given ordinal is set.
   */
  @Override
  public boolean getBit(int ordinal)
  {
    return (bits & ordinal) != 0;
  }

  /**
   * Return if the bit specified by the given tag is set.
   */
  @Override
  public boolean getBit(String tag)
  {
    return getBit(tagToOrdinal(tag));
  }

  /**
   * Get an array enumerating the list of all known
   * ordinal values of this bitstring instance.
   */
  @Override
  public int[] getOrdinals()
  {
    return new int[] { START, OUT_OF_ORDER, HIDDEN, MODIFIED, INTERPOLATED };
  }

  /**
   * Is the specified ordinal value included in this
   * bitstring's range of valid ordinals.
   */
  @Override
  public boolean isOrdinal(int ordinal)
  {
    return support.isOrdinal(ordinal);
  }

  /**
   * Get the tag identifier for an ordinal value.
   */
  @Override
  public String getTag(int ordinal)
  {
    return support.getTag(ordinal);
  }

  /**
   * Get the user readable tag for an ordinal value.
   */
  @Override
  public String getDisplayTag(int ordinal, Context cx)
  {
    return support.getDisplayTag(ordinal, cx);
  }

  /**
   * Get the BBitString instance which maps to the
   * specified set of ordinal values.
   */
  @Override
  public BBitString getInstance(int[] ordinals)
  {
    byte mask = 0;
    for(int i=0; i<ordinals.length; ++i) mask |= (byte)(0xFF & ordinals[i]);
    return make(mask);
  }

  /**
   * Return true if the specified tag is contained by the range.
   */
  @Override
  public boolean isTag(String tag)
  {
    return support.isTag(tag);
  }

  /**
   * Get the ordinal associated with the specified tag.
   */
  @Override
  public int tagToOrdinal(String tag)
  {
    return support.tagToOrdinal(tag);
  }

  /**
   * Return if true if no bits set.
   */
  @Override
  public boolean isEmpty()
  {
    return bits == 0;
  }

  /**
   * The empty tag is "none".
   */
  @Override
  public String getEmptyTag()
  {
    return "none";
  }

////////////////////////////////////////////////////////////////
// Comparsion
////////////////////////////////////////////////////////////////

  /**
   * BTrendFlags uses its bits as the hash code.
   *
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return (int)bits;
  }

  /**
   * Equality is based on bitmask equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BTrendFlags) // issue 11846
    {
      return ((BTrendFlags)obj).bits == bits;
    }

    return false;
  }


////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BTrendFlags is serialized using writeByte
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(bits);
  }

  /**
   * BTrendFlags is unserialized using readByte()
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BTrendFlags( in.readByte() ).intern();
  }

  /**
   * Text format is the bit mask in hex.
   */
  @Override
  public String encodeToString()
  {
    return Integer.toString(0xFF & bits, 16);
  }

  /**
   * Read the bit mask as hex.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      int i = Integer.parseInt(s, 16);
      byte b = (byte)(i & 0xFF);
      if (b == 0)
        return DEFAULT;
      else
        return new BTrendFlags(b).intern();
    }
    catch(Exception e)
    {
      throw new IOException("Invalid bits: " + s);
    }
  }

  @Override
  public BIDataValue toDataValue()
  {
    return BInteger.make(bits & 0xFF);
  }

////////////////////////////////////////////////////////////////
// Formatting
////////////////////////////////////////////////////////////////

  @Override
  public String toString(Context cx)
  {
    if (bits == 0) return "{ }";
    StringBuilder s = new StringBuilder();
    s.append('{');

    boolean first = true;
    if (getBit(START))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(START, cx));
    }

    if (getBit(OUT_OF_ORDER))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(OUT_OF_ORDER, cx));
    }

    if (getBit(HIDDEN))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(HIDDEN, cx));
    }

    if (getBit(MODIFIED))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(MODIFIED, cx));
    }

    if (getBit(INTERPOLATED))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(INTERPOLATED, cx));
    }

    if (getBit(RESERVED_0))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(RESERVED_0, cx));
    }

    if (getBit(RESERVED_1))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(RESERVED_1, cx));
    }

    if (getBit(RESERVED_2))
    {
      if (!first) { s.append(","); }
      first = false;
      s.append(support.getDisplayTag(RESERVED_2, cx));
    }

    s.append('}');

    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final byte START        = 0x01;
  public static final byte OUT_OF_ORDER = 0x02;
  public static final byte HIDDEN       = 0x04;
  public static final byte MODIFIED     = 0x08;
  public static final byte INTERPOLATED = 0x10;
  public static final byte RESERVED_0   = 0x20;
  public static final byte RESERVED_1   = 0x40;
  public static final byte RESERVED_2   = (byte)0x80;

  public static final BTrendFlags start        = new BTrendFlags(START);
  public static final BTrendFlags outOfOrder   = new BTrendFlags(OUT_OF_ORDER);
  public static final BTrendFlags hidden       = new BTrendFlags(HIDDEN);
  public static final BTrendFlags modified     = new BTrendFlags(MODIFIED);
  public static final BTrendFlags interpolated = new BTrendFlags(INTERPOLATED);

  public static final BTrendFlags DEFAULT = new BTrendFlags((byte)0);

  private static Support support = new Support(DEFAULT);
  static
  {
    support.add(START,        "start");
    support.add(OUT_OF_ORDER, "outOfOrder");
    support.add(HIDDEN,       "hidden");
    support.add(MODIFIED,     "modified");
    support.add(INTERPOLATED, "interpolated");
    support.add(RESERVED_0,   "reserved0");
    support.add(RESERVED_1,   "reserved1");
    support.add(RESERVED_2,   "reserved2");
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final Type TYPE = Sys.loadType(BTrendFlags.class);
  @Override
  public Type getType() { return TYPE; }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private byte bits;
}
