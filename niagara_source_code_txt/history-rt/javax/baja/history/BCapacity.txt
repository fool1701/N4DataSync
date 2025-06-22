/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BCapacity defines the capacity of a history.  The capacity can be defined
 * in terms of record count or storage size.
 *
 * @author    John Sublett
 * @creation  03 Jul 2002
 * @version   $Revision: 8$ $Date: 3/28/11 4:23:31 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BCapacity
  extends BSimple
{
  /**
   * Create a capacity restricted by record count.
   *
   * @param maxRecords The capacity as a number of records.
   */
  public static BCapacity makeByRecordCount(int maxRecords)
  {
    return (BCapacity)(new BCapacity(RESTRICT_RECORD_COUNT, maxRecords).intern());
  }

  /**
   * Create a capacity restricted by storage size.
   *
   * @param maxSize The capacity in bytes.
   */
  public static BCapacity makeByStorageSize(long maxSize)
  {
    return (BCapacity)(new BCapacity(RESTRICT_STORAGE_SIZE, maxSize).intern());
  }

  /**
   * Create an unlimited capacity.
   */
  public static BCapacity makeUnlimited()
  {
    return UNLIMITED;
  }

  /**
   * Create a regular collection interval.
   *
   * @param restrictBy Describes how the capacity is restricted.
   */
  private BCapacity(int restrictBy, long max)
  {
    this.restrictBy = restrictBy;
    this.max = max;
  }

  /**
   * Is this capacity unlimited?  If so, the max capacity should be ignored.
   */
  public boolean isUnlimited()
  {
    return restrictBy == RESTRICT_NONE;
  }

  /**
   * Is the capacity restricted by record count?
   */
  public boolean isByRecordCount()
  {
    return restrictBy == RESTRICT_RECORD_COUNT;
  }

  /**
   * Is the capacity restricted by storage size?
   */
  public boolean isByStorageSize()
  {
    return restrictBy == RESTRICT_STORAGE_SIZE;
  }

  /**
   * Get the maximum number of records if limited by record count.
   * Deprecated - use getMaxStorage() instead.
   *
   * @return Returns the maximum number of records or -1 if no
   *   maximum is defined.  If capacity is limited by record count
   *   and the maximum is greater than Integer.MAX_VALUE, this method
   *   will return Integer.MAX_VALUE.
   */
  public int getMaxRecords()
  {
    if (restrictBy == RESTRICT_NONE) return -1;
    if (restrictBy == RESTRICT_STORAGE_SIZE)
      throw new IllegalStateException("Capacity is not restricted by record count.");

    if(max>Integer.MAX_VALUE)
      return Integer.MAX_VALUE;
    else
      return (int)max;
  }


  /**
   * Get the maximum number of bytes if limited by storage size.
   *
   * @return Returns the maximum number of bytes or -1 if no
   *   maximum is defined.
   */
  public long getMaxStorage()
  {
    if (restrictBy == RESTRICT_NONE) return -1;
    if (restrictBy == RESTRICT_RECORD_COUNT)
      throw new IllegalStateException("Capacity is not restricted by storage size.");

    return max;
  }

  /**
   * BCapacity hashCode implementation
   *
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    if (hashCode == -1)
    {
      long hash = 23L + restrictBy;
      hash = (hash * 37L) + max;
      hashCode = (int)(hash >>> 32) ^ (int)hash;
    }
    return hashCode;
  }

  /**
   * Compare this instance to the specified object for equality.
   */
  public boolean equals(Object o)
  {
    if (!(o instanceof BCapacity))
      return false;

    BCapacity other = (BCapacity)o;
    return (restrictBy == other.restrictBy) && (max == other.max);
  }

  /**
   * Encode this instance to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws java.io.IOException
  {
    out.writeInt(restrictBy);
    out.writeLong(max);
  }

  /**
   * Decode this instance from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws java.io.IOException
  {
    int restrictBy = in.readInt();
    long max = in.readLong();
    return new BCapacity(restrictBy, max).intern();
  }

  /**
   * Encode this instance to a string that can be decoded using
   * decodeFromString().
   */
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(16);
    s.append(restrictBy);
    s.append(':');
    s.append(max);
    return s.toString();
  }

  /**
   * Decode the specified string into a BCollectionInterval instance.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    int colon = s.indexOf(':');
    int restrictBy = Integer.parseInt(s.substring(0, colon));
    long max = Long.parseLong(s.substring(colon+1));

    return new BCapacity(restrictBy, max).intern();
  }

  @Override
  public String toString(Context cx)
  {
    if (restrictBy == RESTRICT_NONE)
      return unlimitedTxt.getText(cx);
    else if (restrictBy == RESTRICT_RECORD_COUNT)
      return Long.toString(max) + " " + recordsTxt.getText(cx);
    else if (restrictBy == RESTRICT_STORAGE_SIZE)
      return Long.toString(max / 1024) + " KB";
    else
      return Long.toString(max);
  }

  private static final int RESTRICT_NONE         = 0;
  private static final int RESTRICT_RECORD_COUNT = 1;
  private static final int RESTRICT_STORAGE_SIZE = 2;

  public static final BCapacity UNLIMITED = new BCapacity(RESTRICT_NONE, 0L);
  public static final BCapacity DEFAULT   = UNLIMITED;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCapacity.class);

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private static final LexiconText unlimitedTxt = LexiconText.make("history", "unlimited");
  private static final LexiconText recordsTxt = LexiconText.make("history", "records");

  public static final BFacets recordCountFacets = BFacets.make(new String[]
                                                               {
                                                                 BFacets.MIN,
                                                                 BFacets.MAX
                                                               },
                                                               new BIDataValue[]
                                                               {
                                                                 BDouble.make(0),
                                                                 BDouble.POSITIVE_INFINITY
                                                               });

  private int restrictBy;
  private long max;
  private int hashCode = -1;
}
