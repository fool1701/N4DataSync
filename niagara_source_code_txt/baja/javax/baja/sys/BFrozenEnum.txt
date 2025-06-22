/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

import com.tridium.sys.schema.*;

/**
 * The BFrozenEnum is the base class of strong typed enums
 * defined at compile time.  The range of BFrozenEnums is 
 * defined using introspection rules.
 *
 *<pre>
 *&#64;NiagaraType
 *&#64;NiagaraEnum(
 *  range = {
 *    &#64;Range("tag0"),
 *    &#64;Range("tag1")
 *  },
 *  default = "tag1"
 *)
 *public final class BMyEnum
 *  extends BFrozenEnum
 *{
 *}
 *</pre>
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 44$ $Date: 3/28/05 9:23:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BFrozenEnum
  extends BEnum
{
  protected BFrozenEnum()
  {
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a BFrozenEnum with its ordinal value.  
   * No range checking is performed;  it is assumed
   * that subclasses only provide a private constructor
   * and declare all instances statically.
   *
   * @param ordinal int cursor into range.
   */
  protected BFrozenEnum(int ordinal)
  {
    this.ordinal = ordinal;
  }

////////////////////////////////////////////////////////////////
// Enum
////////////////////////////////////////////////////////////////

  /**
   * Default implementation of BEnum is to return false
   * for an ordinal of zero and true for all non-zero ordinals.
   */
  @Override
  public boolean isActive()
  {
    return ordinal != 0;
  }        

  /**
   * @return The ordinal which uniquely identifies this enum.
   */
  @Override
  public final int getOrdinal()
  {
    return ordinal;
  }
              
  /**
   * Get the String identifier of this enum value.
   */
  @Override
  public final String getTag()
  {
    return ((EnumType)getType()).getTag(ordinal);
  }
  
  /**
   * Get a user readable version of the tag identifier.  
   * Display name is resolved according to the same rules as 
   * {@code Slot.getDisplayName()}.  Display tags are
   * defined in the modules lexicon using the tag name as 
   * the key.  If undefined in the lexicon, then result of
   * {@code TextUtil.toFriendly(tag)} is used.
   */
  @Override
  public String getDisplayTag(Context cx)
  {
    return ((EnumType)getType()).getDisplayTag(ordinal, cx);
  }

  /**
   * Get the range which is the set of instances. 
   */
  @Override
  public final BEnumRange getRange()
  {
    return ((EnumType)getType()).getRange(true);
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////
  
  /**
   * @return integer hash code which is the ordinal.
   */
  public final int hashCode()
  {
    return ordinal;
  }

  /**
   * @return true if obj has the same class type
   *   as this, with identical ordinals.
   */
  public final boolean equals(Object obj)
  {
    return getClass() == obj.getClass() &&
           ordinal == ((BFrozenEnum)obj).ordinal;
  }
  
  /**
   * @return String representation of this BEnum.
   */
  @Override
  public String toString(Context context)
  {
    return getDisplayTag(context);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////
    
  /**
   * BEnum is serialized using writeInt() of
   * the ordinal value.  
   */
  @Override
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(ordinal);
  }
  
  /**
   * BEnum is unserialized using readInt() of
   * the ordinal value.
   */
  @Override
  public final BObject decode(DataInput in)
    throws IOException
  {
    return getRange().get(in.readInt());
  }

  /**
   * Write the primitive in text format using the name.
   */
  @Override
  public final String encodeToString()
    throws IOException
  {
    return getTag();
  }

  /**
   * Read the primitive from text format.
   */
  @Override
  public final BObject decodeFromString(String s)
    throws IOException
  {
    return getRange().get(s);
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFrozenEnum.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int ordinal;        
  BDynamicEnum asDynamic;
}
