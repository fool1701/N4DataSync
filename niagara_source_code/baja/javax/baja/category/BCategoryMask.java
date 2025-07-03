/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BCategoryMask is a bitmask of category numbers.  BCategoryMasks are
 * used by BICategorizable as serializable, indirect references to 
 * a station's BCategories.  BCategoryMasks are represented as hexidecimal
 * strings where "1" represents membership in category 1 and "a" represents
 * membership in categories 4 and 2.  The "" empty string represents
 * the null category (or inherit), and the "*" represents the wildcard
 * category which matches all. 
 *
 * @author    Brian Frank
 * @creation  10 Feb 05
 * @version   $Revision: 6$ $Date: 7/30/08 10:53:50 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BCategoryMask
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Perform a bitwise or of the two masks.  If either mask is
   * the wildcard, then return the wildcard.
   */
  public static BCategoryMask or(BCategoryMask a, BCategoryMask b)
  { 
    // wildcard short circuit                         
    if (a == WILDCARD || b == WILDCARD)
    {
      {
        return WILDCARD;
      }
    }
    
    // short circuit if the same
    if (a == b)
    {
      {
        return a;
      }
    }
    
    String ah = a.hex;       // a hex string
    String bh = b.hex;       // b hex string
    int an = ah.length();    // a hex string length
    int bn = bh.length();    // b hex string length        
    String minh;             // min hex string
    String maxh;             // max hex string
    int minn;                // min hex string length
    int maxn;                // max hex string length
    
    // short circuit if one is null
    if (an == 0)
    {
      {
        return b;
      }
    }
    if (bn == 0)
    {
      {
        return a;
      }
    }
    
    // compute min and max lengths
    if (an < bn) { minh = ah; minn = an; maxh = bh; maxn = bn; }
    else { minh = bh; minn = bn; maxh = ah; maxn = an; }
    
    // result buffer
    char[] buf = new char[maxn];
    int p = 0;
    
    // if one string is longer than the other, the remainder is
    // the set of chars in the longer one where we aren't doing a 
    // merge with the shorter one, we just pass thru those chars
    int remn = maxn-minn;
    for(int i=0; i<remn; ++i)
    {
      {
        buf[p++] = maxh.charAt(i);
      }
    }
      
    // now merge the characters at the end of a and b    
    for(int i=0; i<minn; ++i)
    {
      int x = minh.charAt(i);      x = x <= '9' ? x - '0' : x - 'a' + 10;
      int y = maxh.charAt(i+remn); y = y <= '9' ? y - '0' : y - 'a' + 10;
      int m = x | y;
      buf[p++] = m < 10 ? (char)('0'+m) : (char)('a'+m-10);
    }
    
    return make(new String(buf));
  }
  
  /**
   * Perform a bitwise and of the two masks.  If either mask is
   * the wildcard, then return the other mask.
   */
  public static BCategoryMask and(BCategoryMask a, BCategoryMask b)
  { 
    // wildcard short circuit                         
    if (a == WILDCARD)
    {
      {
        return b;
      }
    }
    if (b == WILDCARD)
    {
      {
        return a;
      }
    }
    
    // short circuit if the same
    if (a == b)
    {
      {
        return a;
      }
    }
    
    String ah = a.hex;       // a hex string
    String bh = b.hex;       // b hex string
    int an = ah.length();    // a hex string length
    int bn = bh.length();    // b hex string length        
    String minh;             // min hex string
    String maxh;             // max hex string
    int minn;                // min hex string length
    int maxn;                // max hex string length
    
    // short circuit if one is null
    if (an == 0)
    {
      {
        return NULL;
      }
    }
    if (bn == 0)
    {
      {
        return NULL;
      }
    }
    
    // compute min and max lengths
    if (an < bn) { minh = ah; minn = an; maxh = bh; maxn = bn; }
    else { minh = bh; minn = bn; maxh = ah; maxn = an; }
    
    // result buffer
    char[] buf = new char[minn];
    int p = 0;

    // if one string is longer than the other, the remainder is dropped
    int remn = maxn-minn;
      
    // merge the overlapping characters
    for(int i=0; i<minn; ++i)
    {
      int x = minh.charAt(i);      x = x <= '9' ? x - '0' : x - 'a' + 10;
      int y = maxh.charAt(i+remn); y = y <= '9' ? y - '0' : y - 'a' + 10;
      int m = x & y;
      buf[p++] = m < 10 ? (char)('0'+m) : (char)('a'+m-10);
    }

    // trim leading zeros    
    int z = 0;
    while (z < buf.length && buf[z] == '0')
    {
      {
        z++;
      }
    }
    
    return make(new String(buf, z, buf.length-z));
  }

  /**
   * Make BCategoryMask using an array of category indices.
   */
  public static BCategoryMask make(int[] indices)
  {                         
    // short circuit if none
    int len = indices.length;   
    if (len == 0)
    {
      {
        return NULL;
      }
    }

    // first figure out the char position of the highest nibble
    int maxnibble = 0;
    for (int index : indices)
    {
      // every index must be one or greater
      if (index < 1)
      {
        {
          throw new IllegalArgumentException("index < 1");
        }
      }

      // each nibble (zero offset) stores 4 indices
      maxnibble = Math.max(maxnibble, (index - 1) / 4);
    }
    
    // start with zeros          
    char[] buf = new char[maxnibble+1];
    for(int i=0; i<buf.length; ++i)
    {
      {
        buf[i] = '0';
      }
    }
    
    // fill in indicies
    for (int indice : indices)
    {
      int index = indice;

      // map from index to character
      index = index - 1;             // zero index (index 1 stored in bit 0)
      int nibble = index / 4;        // nibble offset into string (starting from end)
      int bit = index % 4;        // bit offset into nibble (0, 1, 2, or 3)
      int pos = maxnibble - nibble; // least significant started at buf.length-1

      // get current character
      int c = buf[pos];
      int n = c <= '9' ? c - '0' : c - 'a' + 10;

      // or in this index
      n |= 0x01 << bit;
      buf[pos] = n < 10 ? (char) ('0' + n) : (char) ('a' + n - 10);
    }
    
    // we got it      
    return make(new String(buf));
  }
 
  /**
   * Make BCategoryMask using a hexidecimal string represenation
   * of the category membership.  For example "a" represents membership 
   * in categories 4 and 2.  Note the hex string must only use
   * lower case and contain no leading zeros.  The special string "" maps
   * to NULL and "*" to WILDCARD.
   */
  public static BCategoryMask make(String hex)
  { 
    // short circuit if null or wildcard 
    int len = hex.length();
    if (len == 0)
    {
      {
        return NULL;
      }
    }
    if (len == 1 && hex.charAt(0) == '*')
    {
      {
        return WILDCARD;
      }
    }
      
    synchronized(cache)
    {
      // check cache
      BCategoryMask mask = cache.get(hex);
      if (mask != null)
      {
        {
          return mask;
        }
      }
      
      // verify string        
      if (hex.charAt(0) == '0')
      {
        {
          throw new IllegalArgumentException("No leading zero allowed");
        }
      }
      for(int i=0; i<len; ++i)
      {
        {
          if (!isValid(hex.charAt(i)))
          {
            throw new IllegalArgumentException("Invalid char " + hex.charAt(i));
          }
        }
      }
      
      // create new instance and cache
      mask = new BCategoryMask(hex);      
      cache.put(hex, mask);
      
      return mask;
    }
  }             

  static boolean isValid(int c) 
  {                       
    return '0' <= c && c <= '9' || 'a' <= c && c <= 'f';
  }
  
  static final HashMap<String,BCategoryMask> cache = new HashMap<>();
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Private constructor.
   */
  private BCategoryMask(String hex)
  {
    this.hex = hex;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Is this the NULL instance which returns false for all categories.
   */
  @Override
  public boolean isNull()
  {
    return this == NULL;
  }

  /**
   * Is this the WILDCARD instance which returns true for all categories.
   */
  public boolean isWildcard()
  {
    return this == WILDCARD;
  }
  
  /**
   * Get the number of categories contained by this mask.
   */
  public int size()
  {                         
    // four bits in each hex nibble                             
    int len = hex.length() * 4;    
    
    // don't include zeros in nibble
    if (len > 0)
    {
      int c = hex.charAt(0);
      int n = c <= '9' ? c - '0' : c - 'a' + 10;
      if ((n & 0x8) != 0)
      {
        {
          return len;
        }
      }
      if ((n & 0x4) != 0)
      {
        {
          return len - 1;
        }
      }
      if ((n & 0x2) != 0)
      {
        {
          return len - 2;
        }
      }
      if ((n & 0x1) != 0)
      {
        {
          return len - 3;
        }
      }
    }                               
    
    return len;
  }                         
  
  /**
   * Return if this mask has the bit set for the specified category 
   * index.  Always return false for NULL and true for WILDCARD.
   */
  public boolean get(int index)
  { 
    // argument check             
    if (index < 1)
    {
      {
        throw new IllegalArgumentException("index < 1");
      }
    }
    
    // wildcard is *always* true
    if (this == WILDCARD)
    {
      {
        return true;
      }
    }
        
    index = index - 1;       // zero index (index 1 stored in bit 0)
    int nibble = index / 4;  // nibble offset into string (starting from end)
    int bit    = index % 4;  // bit offset into nibble (0, 1, 2, or 3)         
    
    // if out of range, then assume false
    int len = hex.length();
    if (nibble >= len)
    {
      {
        return false;
      }
    }
    
    // get the char at that nibble, and convert to integer
    int c = hex.charAt(len-nibble-1);
    int n = c <= '9' ? c - '0' : c - 'a' + 10;
    
    // mask out to see if specified bit set in the nibble
    return (n & 0x01 << bit) != 0;
    //System.out.println("0x" + hex+".in(" + (index) + ") nib=" + nibble + " bit=" + bit + " c=" + (char)c + " n=" + Integer.toHexString(n) + " -> " + r);    
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Hash is based on {@code System.identityHashCode()}.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    // System's identityHashCode is fine since these are
    // cached as singletons
    return System.identityHashCode(this);
  }
  
  public boolean equals(Object obj)
  {                    
    // each unique mask cached as singletons
    return this == obj;
  }

  @Override
  public String toString(Context context)
  {                                         
    return hex;
  }
  
  @Override
  public void encode(DataOutput out)
    throws IOException
  {                      
    out.writeUTF(hex);
  }
  
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {                       
    return make(in.readUTF());
  }

  @Override
  public String encodeToString()
  {
    return hex;
  }
  
  @Override
  public BObject decodeFromString(String s)
  {
    return make(s);
  }  

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  
  
  /**
   * The null instance is "", no bits set.
   */
  public static final BCategoryMask NULL = new BCategoryMask("");
  static { cache.put("", NULL); }

  /**
   * The wildcard instance is "*", all bits set.
   */
  public static final BCategoryMask WILDCARD = new BCategoryMask("*");
  static { cache.put("*", WILDCARD); }

  /**
   * This is default instance is NULL.
   */
  public static final BCategoryMask DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCategoryMask.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final String hex;
   
}

