/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * The BEnum is the abstract base class of BSimples which
 * have a discrete range of values represented by a BEnumRange.  
 * This includes BBoolean, BFrozenEnum, and BDynamicEnum.  An enum 
 * is characterized by integer identifiers called "ordinals" and 
 * string identifiers called "tags".
 *
 * @author    Brian Frank
 * @creation  9 Jan 01
 * @version   $Revision: 12$ $Date: 3/23/05 8:39:04 AM EST$
 * @since     Baja 1.0
 */

@NiagaraType
@NoSlotomatic
public abstract class BEnum
  extends BSimple
  implements BIEnum, BIComparable
{       
   
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Package private constructor.
   */
  BEnum()
  {
  }

////////////////////////////////////////////////////////////////
// IEnum
////////////////////////////////////////////////////////////////

  /**
   * @return this.
   */
  @Override
  public final BEnum getEnum()
  {
    return this;
  }

  /**
   * Return {@code BFacets.make(BFacets.RANGE, getRange())}.
   */
  @Override
  public final BFacets getEnumFacets()
  {                 
    return BFacets.make(BFacets.RANGE, getRange());
  }           

////////////////////////////////////////////////////////////////
// IComparable
////////////////////////////////////////////////////////////////

  /**
   * Compare this instance to the specified BEnum instance.
   * BEnum uses the ordinal value as the basis for comparison.
   * Returns a negative integer, zero, or a positive integer as
   * the ordinal of this instance is less than, equal to, or
   * greater than the ordinal of specified instance.
   */
  @Override
  public final int compareTo(Object x)
  {
    return getOrdinal() - ((BEnum)x).getOrdinal();
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return if this enum's value is considered an 
   * active state.
   */
  public abstract boolean isActive();

  /**
   * Every enum value has an ordinal value
   * represented as a 32 bit integer.
   */
  public abstract int getOrdinal();

  /**
   * Get the String identifier of this enum value.
   */
  public abstract String getTag();
  
  /**
   * Get a user readable version of the tag identifier.
   */
  public abstract String getDisplayTag(Context cx);

  /**
   * Get the range which encapsulates the list of 
   * ordinal tag pairs for this enumeration.
   */
  public abstract BEnumRange getRange();
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnum.class);
  
}
