/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * BComponentEventMaskBits is a mask for BComponentEvents.
 *
 * @author    Lee Adcock
 * @creation  8 Apr 11
 * @version   $Revision: 2$ $Date: 4/11/11 3:57:03 PM EDT$
 * @since     Niagara 3.7
 */
@NiagaraType
@NoSlotomatic
public final class BComponentEventMask
  extends BBitString
{

////////////////////////////////////////////////////////////////
// Factory methods
////////////////////////////////////////////////////////////////

/**
 * Returns the EMPTY mask (no bits set).
 */
  public static BComponentEventMask make()
  {
    return EMPTY;
  }

/**
 * Returns a mask representing the bits provided.
 * <pre>{@code
 * Examples of BComponentEventMask for BComponentEvent.PROPERTY_ADDED
 *   BComponentEventMask.make(2);
 *   BComponentEventMask.make(0x01 << BComponentEvent.PROPERTY_ADDED);
 *     where BComponentEvent.PROPERTY_ADDED == 1
 * }</pre>
 */
  public static BComponentEventMask make(int bits)
  {
    return new BComponentEventMask(bits);
  }

/**
 * Returns a mask with the appropriate bit settings for all of
 * the componentEvents in the array.
 * <pre>
 * Example of BComponentEventMask for BComponentEvent.PROPERTY_ADDED and BComponentEvent.PROPERTY_CHANGED
 *   BComponentEventMask.make(new int[] {BComponentEvent.PROPERTY_ADDED, BComponentEvent.PROPERTY_CHANGED}) 
 * </pre>
 */
  public static BComponentEventMask make(int[] componentEvents)
  {
    int bits = 0;
    for(int i=0; i<componentEvents.length; i++)
      bits |= (0x01 << componentEvents[i]);

    if (bits == EMPTY.bits) return EMPTY;
    return new BComponentEventMask(bits);
  }

/**
 * Returns a mask representing the result of the "set" operation.
 * If "set" is true, the bit patterns for the two masks are combined.
 * If "set" is false, the bits in the newBits mask are removed from
 * the oldBits mask.
 */
  public static BComponentEventMask make(BComponentEventMask oldBits, BComponentEventMask newBits, boolean set)
  {
    if (set)
      return new BComponentEventMask(oldBits.bits | newBits.bits);
    else
      return new BComponentEventMask(oldBits.bits & ~newBits.bits);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  private BComponentEventMask(int bits)
  {
    this.bits = bits;
  }

////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Return true if the mask includes the specified component event
   */
  public boolean includes(int componentEvent)
  {
    return 0 != ((0x01<<componentEvent) & bits);
  }

  /**
   * Return the int representing mask's bit pattern
   */
  public int getBits()
  {
    return bits;
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

  public int hashCode()
  {
    return bits;
  }

  /**
   * Equality is based on direct object reference
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BComponentEventMask)
      return ((BComponentEventMask)obj).bits == bits;
    return false;
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

  // Default for Subscribers
  public static final BComponentEventMask PROPERTY_EVENTS   = new BComponentEventMask(
      (0x01 << BComponentEvent.PROPERTY_CHANGED) |
      (0x01 << BComponentEvent.PROPERTY_ADDED) |
      (0x01 << BComponentEvent.PROPERTY_REMOVED) |
      (0x01 << BComponentEvent.PROPERTY_RENAMED) |
      (0x01 << BComponentEvent.PROPERTIES_REORDERED) |
      (0x01 << BComponentEvent.TOPIC_FIRED) |
      (0x01 << BComponentEvent.FLAGS_CHANGED) |
      (0x01 << BComponentEvent.FACETS_CHANGED) |
      (0x01 << BComponentEvent.KNOB_ADDED) |
      (0x01 << BComponentEvent.KNOB_REMOVED) |
      (0x01 << BComponentEvent.RELATION_KNOB_ADDED) |
      (0x01 << BComponentEvent.RELATION_KNOB_REMOVED) |
      (0x01 << BComponentEvent.RECATEGORIZED)
      );

  // Default for TypeSubscribers
  public static final BComponentEventMask SELF_EVENTS   = new BComponentEventMask(
      (0x01 << BComponentEvent.COMPONENT_PARENTED) |
      (0x01 << BComponentEvent.COMPONENT_UNPARENTED) |
      (0x01 << BComponentEvent.COMPONENT_RENAMED) |
      (0x01 << BComponentEvent.COMPONENT_REORDERED) |
      (0x01 << BComponentEvent.COMPONENT_FLAGS_CHANGED) |
      (0x01 << BComponentEvent.COMPONENT_FACETS_CHANGED) |
      (0x01 << BComponentEvent.COMPONENT_STARTED) |
      (0x01 << BComponentEvent.COMPONENT_STOPPED)
      );

  public static final BComponentEventMask EMPTY   = new BComponentEventMask(0);
  public static final BComponentEventMask ALL     = new BComponentEventMask(0xFFFFFFFF);
  public static final BComponentEventMask DEFAULT = EMPTY;

  public static final Type TYPE = Sys.loadType(BComponentEventMask.class);
  @Override
  public Type getType() { return TYPE; }

  private static Support support = new Support(DEFAULT);
  static
  {
    try
    {
      String[] idStrings = BComponentEvent.getIdStrings();
      for(int i=0; i<idStrings.length; i++)
        support.add(i, idStrings[i]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int bits;
}
