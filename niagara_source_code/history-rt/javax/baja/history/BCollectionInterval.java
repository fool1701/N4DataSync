/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BCollectionInterval describes the interval on which history records
 * are collected.
 *
 * @author    John Sublett
 * @creation  03 Jul 2002
 * @version   $Revision: 6$ $Date: 1/25/08 4:04:10 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BCollectionInterval
  extends BSimple
{
  /**
   * Create a regular collection interval.
   */
  public static BCollectionInterval make(BRelTime interval)
  {
    if (interval == REGULAR_1_MIN.getInterval())
      return REGULAR_1_MIN;
    else if (interval == REGULAR_5_MIN.getInterval())
      return REGULAR_5_MIN;
    else if (interval == REGULAR_15_MIN.getInterval())
      return REGULAR_15_MIN;
    else if (interval == REGULAR_30_MIN.getInterval())
      return REGULAR_30_MIN;
    else if (interval == REGULAR_1_HOUR.getInterval())
      return REGULAR_1_HOUR;
    else
      return (BCollectionInterval)(new BCollectionInterval(interval).intern());
  }


  /**
   * Create a BCollectionInterval for an irregular interval.
   */
  public static BCollectionInterval makeIrregular()
  {
    BCollectionInterval result = new BCollectionInterval(BRelTime.MINUTE);
    result.irregular = true;

    return (BCollectionInterval)(result.intern());
  }

  /**
   * Create a regular collection interval.
   */
  private BCollectionInterval(BRelTime interval)
  {
    irregular = false;
    this.interval = interval;
  }

  /**
   * Is this collection interval irregular?  If so, the interval should
   * be ignored.
   */
  public boolean isIrregular()
  {
    return irregular;
  }

  /**
   * Get the interval if not irregular.
   */
  public BRelTime getInterval()
  {
    return interval;
  }

  /**
   * BCollectionInterval hashCode implementation
   *
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    if (hashCode == -1)
    {
      long hash = 23L + (irregular?1L:0L);
      hash = (hash * 37L) + interval.hashCode();
      hashCode = (int)(hash >>> 32) ^ (int)hash;
    }
    return hashCode;
  }

  /**
   * Compare this instance to the specified object for equality.
   */
  public boolean equals(Object o)
  {
    if (!(o instanceof BCollectionInterval))
      return false;

    BCollectionInterval other = (BCollectionInterval)o;
    return (irregular == other.irregular) && interval.equals(other.interval);
  }

  /**
   * Encode this instance to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws java.io.IOException
  {
    out.writeBoolean(irregular);
    interval.encode(out);
  }

  /**
   * Decode this instance from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws java.io.IOException
  {
    boolean irregular = in.readBoolean();
    BRelTime interval = (BRelTime)BRelTime.DEFAULT.decode(in);

    BCollectionInterval result = new BCollectionInterval(interval);
    result.irregular = irregular;

    return result.intern();
  }

  /**
   * Encode this instance to a string that can be decoded using
   * decodeFromString().
   */
  @Override
  public String encodeToString()
  {
    StringBuilder s = new StringBuilder(16);
    s.append(irregular ? "true" : "false");
    s.append(':');
    s.append(interval.encodeToString());
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
    String boolStr = s.substring(0, colon);
    boolean irregular = boolStr.equals("true");
    BRelTime interval =
      (BRelTime)BRelTime.DEFAULT.decodeFromString(s.substring(colon+1));

    BCollectionInterval result = new BCollectionInterval(interval);
    result.irregular = irregular;

    return result.intern();
  }

  @Override
  public String toString(Context cx)
  {
    if (irregular)
      return Lexicon.make("history").getText("irregular");
    else
      return interval.toString(cx);
  }

  public static final BCollectionInterval DEFAULT        = new BCollectionInterval(BRelTime.MINUTE);
  public static final BCollectionInterval REGULAR_1_MIN  = DEFAULT;
  public static final BCollectionInterval REGULAR_5_MIN  = new BCollectionInterval(BRelTime.makeMinutes(5));
  public static final BCollectionInterval REGULAR_15_MIN = new BCollectionInterval(BRelTime.makeMinutes(15));
  public static final BCollectionInterval REGULAR_30_MIN = new BCollectionInterval(BRelTime.makeMinutes(30));
  public static final BCollectionInterval REGULAR_1_HOUR = new BCollectionInterval(BRelTime.makeHours(1));
  public static final BCollectionInterval IRREGULAR = makeIrregular();

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCollectionInterval.class);

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private boolean  irregular;
  private BRelTime interval = BRelTime.DEFAULT;
  private int hashCode = -1;

}
