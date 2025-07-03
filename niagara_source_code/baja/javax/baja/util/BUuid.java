/*
 * Copyright 2000-2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.UUID;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Baja simple for a Universal Unique Identifier.
 *
 * @author    Brian Frank
 * @creation  27 Jul 00
 * @version   $Revision: 15$ $Date: 4/7/11 4:39:32 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BUuid
  extends BSimple
  implements BIComparable
{ 

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Construct a BUuid from its string representation
   * using the decodeFromString() specification.
   */
  public static BUuid make(String s)
  {
    // We must have a string formatted: 8-4-4-4-12
    //   000000000011111111112222222222333333
    //   01234567-9012-4567-9012-456789012345
    
    if (s.length() != 36 ||
        s.charAt(8) != '-' ||
        s.charAt(13) != '-' ||
        s.charAt(18) != '-' ||
        s.charAt(23) != '-')
      throw new BajaRuntimeException("Invalid format for BUuid: " + s);
    
    long mostSig  = parse(s, 0, 8) << 32 | 
                    parse(s, 9, 4) << 16 | 
                    parse(s, 14, 4);
    long leastSig = parse(s, 19, 4) << 48 |
                    parse(s, 24, 12);
                    
    return new BUuid(mostSig, leastSig);                
  }

  /**
   * Constructor with most and least significant 64 bits.
   *
   * @param mostSig the most significant 64 bits
   * @param leastSig the least significant 64 bits   
   */
  public static BUuid make(long mostSig, long leastSig)
  {
    return new BUuid(mostSig, leastSig);
  }

  /**
   * Constructor with a standard UUID.
   *
   * @param uuid the standard Java UUID
   * @since Niagara 4.11
   */
  public static BUuid make(UUID uuid)
  {
    return new BUuid(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
  }

  /**
   * Construct a Uuid from a byte array. 
   * The byte array must be 16 bytes long.  
   * The most significant byte must be at <code>bytes[15]</code>, and 
   * the least significant byte must be at <code>bytes[0]</code>.
   */
  public static BUuid make(byte[] bytes)
  {
    if (bytes.length != 16)
      throw new IllegalArgumentException(
        "Array length mismatch.");

    long most = 0;
    for (int i = 15; i >= 8; i--)
    {
      most = most << 8;
      most = most | (bytes[i] & 0xFF);
    }

    long least = 0;
    for (int i = 7; i >= 0; i--)
    {
      least = least << 8;
      least = least | (bytes[i] & 0xFF);
    }

    return new BUuid(most, least);
  }

  /**
   * Make a new universally unique identifier.
   */
  public static BUuid make()
  {
    return new BUuid();
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  private BUuid(long mostSig, long leastSig)
  {
    juuid = new UUID(mostSig, leastSig);
  }

  private BUuid()
  {
    juuid = UUID.randomUUID();
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @return the most significant 64 bits
   */
  public long getMostSignificant() { return juuid.getMostSignificantBits(); }

  /**
   * @return the least significant 64 bits
   */
  public long getLeastSignificant() { return juuid.getLeastSignificantBits(); }

  /**
   * @return a byte-array representation of this Uuid.
   * The array will be 16 bytes long.
   * The most significant byte will be at <code>bytes[15]</code>, and 
   * the least significant byte will be at <code>bytes[0]</code>.
   */
  public byte[] getBytes()
  {
    return toBytes(juuid);
  }

  /**
   * Generate a byte-array representation of a UUID described by integral components.
   * @param juuid the UUID
   * @return a byte-array representation of the UUID.
   * The array will be 16 bytes long.
   * The most significant byte will be at <code>bytes[15]</code>, and
   * the least significant byte will be at <code>bytes[0]</code>.
   * @since Niagara 4.11
   */
  public static byte[] toBytes(UUID juuid)
  {
    byte[] bytes = new byte[16];

    long a = juuid.getMostSignificantBits();
    for (int i = 8; i < 16; i++)
    {
      bytes[i] = (byte) (a & 0xFF);
      a = a >>> 8;
    }

    long b = juuid.getLeastSignificantBits();
    for (int i = 0; i < 8; i++)
    {
      bytes[i] = (byte) (b & 0xFF);
      b = b >>> 8;
    }

    return bytes;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * @return is this BUuid the null value where all
   *    sixteen bytes are equal to zero.
   */
  @Override
  public boolean isNull()
  {
    return (juuid.getMostSignificantBits() == 0) &&
           (juuid.getLeastSignificantBits() == 0);
  }
  
  /**
   * The BUUid hash code is a hash of the 128 bit value. 
   */
  public int hashCode()
  {
    return juuid.hashCode();
  }

  /**
   * Compare to another BUuid.
   */
  @Override
  public int compareTo(Object o)
  {
    BUuid u = (BUuid) o;
    return (juuid.compareTo(u.juuid));
  }
  
  /**
   * BUuids are equal if they represent the same 128 bit value.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BUuid)
    {
      BUuid x = (BUuid)obj;
      return juuid.equals(x.juuid);
    }
    return false;
  }
  
  /**
   * BUuid is serialized as 16 bytes in standard 
   * network byte order.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeLong(juuid.getMostSignificantBits());
    out.writeLong(juuid.getLeastSignificantBits());
  }
  
  /**
   * BUUid is unserialized as 16 bytes in standard
   * network byte order.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BUuid(in.readLong(), in.readLong());
  }

  /**
   * Write the primitive in text format as a 36 character 
   * string consisting of 8 hexadecimal digits followed by a 
   * hyphen, then three groups of 4 hexadecimal digits each 
   * followed by a hyphen, then 12 hexadecimal digits.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    return juuid.toString();
  }
  

  /**
   * Get a compact string representing this uuid.
   */  
  public String toCompactString()
  {
    StringBuilder s = new StringBuilder(28);

    long mostSig = juuid.getMostSignificantBits();
    long leastSig = juuid.getLeastSignificantBits();

    s.append(compactMap[(int)(mostSig >> 58) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 52) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 46) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 40) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 34) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 38) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 32) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 36) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 30) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 24) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 18) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 12) & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 6)  & 0x3F]);
    s.append(compactMap[(int)(mostSig >> 0)  & 0x3F]);

    s.append(compactMap[(int)(leastSig >> 58) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 52) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 46) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 40) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 34) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 38) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 32) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 36) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 30) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 24) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 18) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 12) & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 6)  & 0x3F]);
    s.append(compactMap[(int)(leastSig >> 0)  & 0x3F]);
    
    return s.toString();
  }
  
  static final char compactMap[] =
  {
    '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f',    
    'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',    
    'w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L',    
    'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','$','*',    
  };

  /**
   * Read the primitive from text format using the
   * same format as encodeToString().
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }
  
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  private static long parse(String s, int start, int num)
  {
    long v = 0;
    for(int i=0; i<num; i++)
    {
      v = v << 4;
      v |= hexCharToInt( s, start+i );
    }
    return v;
  }

  private static int hexCharToInt(String s, int index)
  {
    char c = s.charAt(index);
    int x = c - '0';
    if (x >= 0 && x <= 9) return x;
    x = c - 'A' + 10;
    if (x >= 10 && x <= 15) return x;
    x = c - 'a' + 10;
    if (x >= 10 && x <= 15) return x;
    throw new BajaRuntimeException("Invalid format for BUuid: " + s);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * <code>NULL</code> indicates a null identifier
   * with zeroes in all sixteen bytes.
   */
  public static final BUuid NULL = new BUuid(0, 0);

  /** Constant for BUuid.make(Long.MIN_VALUE, Long.MIN_VALUE) */
  public static final BUuid MIN = new BUuid(Long.MIN_VALUE, Long.MIN_VALUE);

  /** Constant for BUuid.make(Long.MAX_VALUE, Long.MAX_VALUE) */
  public static final BUuid MAX = new BUuid(Long.MAX_VALUE, Long.MAX_VALUE);

  /**
   * Get default constant is <code>NULL</code>
   */
  public static final BUuid DEFAULT = NULL;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUuid.class);

////////////////////////////////////////////////////////////////
// Main
////////////////////////////////////////////////////////////////  

  /**
   * Generate a new UUID and print its value to the
   * console to be cut and pasted into code.
   */
  /*
  public static void main(String[] args)
  {
    BUuid id = make();
    System.out.println();
    System.out.println(id);
    System.out.println("BUuid.make(0x" + Long.toHexString(id.mostSig) +
                       "L, 0x" + Long.toHexString(id.leastSig) + "L)");
    System.out.println();    
  }
  */

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private UUID juuid;
}
