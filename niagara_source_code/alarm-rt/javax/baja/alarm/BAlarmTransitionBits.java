/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BBitString;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BAlarmTransitionBits object contains a bit for each
 * alarm state transition type defined within Baja:
 *    toOffnormal
 *    toFault
 *    toNormal
 *
 * 
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 20$ $Date: 9/30/08 5:08:59 PM EDT$
 * @since     Baja 1.0
 */

@NiagaraType
@NoSlotomatic
public final class BAlarmTransitionBits
  extends BBitString  
{ 

////////////////////////////////////////////////////////////////
// Factory methods
////////////////////////////////////////////////////////////////

  /**
   * Construct an instance in which all days are 
   * set to the given value
   */
  public static BAlarmTransitionBits make(boolean v)
  {
    if (v)
      return ALL;
    else
      return EMPTY;
  }

  /**
   *  Construct an instance using the given bits
   */
  public static BAlarmTransitionBits make(int bits)
  {
    if (bits == ALL.bits) return ALL;
    if (bits == EMPTY.bits) return EMPTY;
    return new BAlarmTransitionBits(bits);
  }

  /**
   * Construct an instance by adding or subtracting bits from another.
   */
  public static BAlarmTransitionBits make(BAlarmTransitionBits old, BAlarmTransitionBits nBits, boolean set)
  {    
    if (set)
      return new BAlarmTransitionBits(old.getBits() | nBits.getBits());
    else
      return new BAlarmTransitionBits(old.getBits() & ~nBits.getBits());
  }
  
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   *  Construct an instance using the given bits
   */
  private BAlarmTransitionBits(int bits)
  {
    this.bits = bits;
  }
  
////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  public int getBits() { return bits; }

  public boolean includes(BSourceState state)
  {
    if (state == BSourceState.offnormal) return isToOffnormal();
    else if (state == BSourceState.fault) return isToFault();
    else if (state == BSourceState.normal) return isToNormal();
    else if (state == BSourceState.alert) return isToAlert();
    else return false;
  }

  public boolean isToOffnormal() { return (bits & TO_OFFNORMAL) != 0; }
  
  public boolean isToFault() { return (bits & TO_FAULT) != 0; }
  
  public boolean isToNormal()   { return (bits & TO_NORMAL) != 0; }
  
  public boolean isToAlert()   { return (bits & TO_ALERT) != 0; }
  

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
    return support.getOrdinals();
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
    int mask = 0;
    for(int i=0; i<ordinals.length; ++i) mask |= ordinals[i];
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

  @Override
  public boolean isNull()
  {
    return bits == 0;
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BAlarmTransitionBits hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return bits;
  }
  
  /**
   * Equality is based on direct object reference
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BAlarmTransitionBits)
      return ((BAlarmTransitionBits)obj).bits == bits;
    return false;
  }
  
  
  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    BFacets facets = null;
    if (context != null) facets = context.getFacets();
        
    if (bits == 0) return "{}";
    StringBuilder s = new StringBuilder();
    s.append('{');
    if (isToAlert() && (facets == null || facets.getb(SHOW_ALERT, true)))
      s.append("alert ");
    if (isToOffnormal() && (facets == null || facets.getb(SHOW_OFF_NORMAL, true)))
      s.append("toOffnormal ");
    if (isToFault() && (facets == null || facets.getb(SHOW_FAULT, true)))
      s.append("toFault ");
    if (isToNormal() && (facets == null || facets.getb(SHOW_NORMAL, true)))
      s.append("toNormal ");
    
    s.setCharAt(s.length()-1, '}');
    return s.toString();
  }
  ////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BDaysOfWeekBits is serialized using writeInt
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(bits);
  }
  
  /**
   * BDaysOfWeekBits is unserialized using readInt()
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readInt() );
  }

  /**
   * Text format is the bit mask in hex.
   */
  @Override
  public String encodeToString()
  {
    return Integer.toHexString(bits);
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
      return make( Integer.parseInt(s, 16) );
    }
    catch(Exception e)
    {
      throw new IOException("Invalid bits: " + s);
    }
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  

  public static final int TO_OFFNORMAL    = 0x0001;
  public static final int TO_FAULT        = 0x0002;
  public static final int TO_NORMAL       = 0x0004;
  public static final int TO_ALERT        = 0x0008;

  public static final BAlarmTransitionBits toOffnormal = new BAlarmTransitionBits(TO_OFFNORMAL);  
  public static final BAlarmTransitionBits toFault     = new BAlarmTransitionBits(TO_FAULT);  
  public static final BAlarmTransitionBits toNormal    = new BAlarmTransitionBits(TO_NORMAL);  
  public static final BAlarmTransitionBits toAlert     = new BAlarmTransitionBits(TO_ALERT);  
  
  public static final String SHOW_OFF_NORMAL  = "showOffNormal";
  public static final String SHOW_NORMAL      = "showNormal";
  public static final String SHOW_FAULT       = "showFault";
  public static final String SHOW_ALERT       = "showAlert";

  /** DEFAULT has all bits set */
  public static final BAlarmTransitionBits DEFAULT = new BAlarmTransitionBits(TO_OFFNORMAL | TO_FAULT | TO_NORMAL | TO_ALERT);
  /** EMPTY has all bits clear */
  public static final BAlarmTransitionBits EMPTY   = new BAlarmTransitionBits(0);
  /** ALL is DEFAULT */
  public static final BAlarmTransitionBits ALL     = DEFAULT;
  
  private static Support support = new Support(DEFAULT);
  static
  {
    support.add(TO_OFFNORMAL,    "toOffnormal");
    support.add(TO_FAULT,        "toFault");
    support.add(TO_NORMAL,       "toNormal");
    support.add(TO_ALERT,        "toAlert");
  }

  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmTransitionBits.class);
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int bits;
  
}
