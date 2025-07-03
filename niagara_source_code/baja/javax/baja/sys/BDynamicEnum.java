/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * BDynamicEnum stores an ordinal state variable as 
 * a 32-bit integer.  An instance of BEnumRange
 * may be used to specify the range.  String format
 * of BDynamicEnum:
 * <pre>
 *   enum = ordinal [ "@" range ]
 * </pre>
 *
 * @author    Brian Frank
 * @creation  13 Dec 01
 * @version   $Revision: 25$ $Date: 1/25/08 4:04:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDynamicEnum
  extends BEnum
  implements BIDataValue
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a BDynamicEnum with the specified ordinal and range.
   */
  public static BDynamicEnum make(int ordinal, BEnumRange range)
  {
    if (range == null) range = BEnumRange.NULL;
    return (BDynamicEnum)(new BDynamicEnum(ordinal, range).intern());
  }

  /**
   * Create a BDynamicEnum with the specified ordinal with a
   * range equal to BEnumRange.NULL.
   */
  public static BDynamicEnum make(int ordinal)
  {
    return make(ordinal, BEnumRange.NULL);
  }

  /**
   * Create a BDynamicEnum using the enum's ordinal and range.
   */
  public static BDynamicEnum make(BEnum e)
  {                             
    if (e instanceof BDynamicEnum) 
    {
      return (BDynamicEnum)e;
    }
      
    if (e instanceof BFrozenEnum)
    {
      BFrozenEnum frozen = (BFrozenEnum)e;
      if (frozen.asDynamic == null) frozen.asDynamic = make(e.getOrdinal(), e.getRange());
      return frozen.asDynamic;
    } 

    if (e instanceof BBoolean)
    {                   
      BBoolean bool = (BBoolean)e;
      if (bool.asDynamic == null) bool.asDynamic = make(e.getOrdinal(), e.getRange());
      return bool.asDynamic;
    } 
    
    throw new IllegalStateException();
  }

  /**
   * Returns a valid DynamicEnum based on the first ordinal available in the EnumRange.
   *
   * @since Niagara 4.9
   */
  public static BDynamicEnum make(BEnumRange range)
  {
    int[] ordinals = range.getOrdinals();
    int defaultOrdinal;

    if  (ordinals==null || ordinals.length==0)
    {
      defaultOrdinal = 0;
    }
    else
    {
      defaultOrdinal = ordinals[0];
    }

    return make(defaultOrdinal, range);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BDynamicEnum(int ordinal, BEnumRange range)
  {
    this.ordinal = ordinal;
    this.range = range;
  }
  
////////////////////////////////////////////////////////////////
// Enum
////////////////////////////////////////////////////////////////

  /**
   * Return false for zero and true for all non-zero ordinals.
   */
  @Override
  public boolean isActive()
  {
    return ordinal != 0;
  }

  /**
   * Get the ordinal integer identifier.
   */
  @Override
  public int getOrdinal()
  {
    return ordinal;
  }

  /**
   * Route to {@code getRange().getTag(int)}.
   */
  @Override
  public String getTag()
  {
    return range.getTag(ordinal);
  }
  
  /**
   * Route to {@code getRange().getDisplayTag(int, cx)}.
   */
  @Override
  public String getDisplayTag(Context cx)
  {
    return range.getDisplayTag(ordinal, cx);
  }
  
  /**
   * Return the range.
   */
  @Override
  public BEnumRange getRange()
  {
    return range;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * If the context contains a value for BFacets.RANGE, then
   * that is used to provide a tag for the ordinal.  Otherwise
   * attempt to use the range passed to the factory method.
   */
  @Override
  public String toString(Context context)
  {
    if (context != null)
    {
      BEnumRange r = (BEnumRange)context.getFacet(BFacets.RANGE);
      if (r != null)
        return r.getDisplayTag(ordinal, context);
    }
    return getDisplayTag(context);
  }

  /**
   * BDynamicEnum uses its encodeToString() value's hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    try
    {
      if (hashCode == -1) 
        hashCode = encodeToString().hashCode();
      return hashCode;
    }
    catch(Exception e) 
    { 
      return System.identityHashCode(this); 
    }
  }
  
  /**
   * Equality is based on ordinal and range.
   */
  public boolean equals(Object o)
  {
    if (o instanceof BDynamicEnum)
    {
      BDynamicEnum x = (BDynamicEnum)o;
      if (ordinal != x.ordinal) return false;
      return range.equals(x.range);
    }
    return false;
  }
    
  /**
   * BDynamicEnum is serialized using writeUTF() 
   * of the string format.
   */
  @Override
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BDynamicEnum is serialized using readUTF() 
   * of the string format.
   */
  @Override
  public final BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Write the BDynamicEnum's value and range 
   * according to the serialization BNF.
   */
  @Override
  public final String encodeToString()
    throws IOException
  {
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      s.append(ordinal);
      if (!range.isNull())
      {
        s.append('@');
        s.append(range.encodeToString());
      }
      string = s.toString();
    }
    return string;
  }

  /**
   * Read the BDynamicEnum's value and range 
   * according to the serialization BNF.
   */
  @Override
  public final BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      String o = s, r = null;
      int at = s.indexOf('@');
      if (at > 0) 
      { 
        o = s.substring(0, at); 
        r = s.substring(at+1);
      }
      
      int ordinal = Integer.parseInt(o);
      BEnumRange range = BEnumRange.NULL;
      if (r != null) range = (BEnumRange)range.decodeFromString(r);
      
      BDynamicEnum result = make(ordinal, range);
      result.string = s;
      return result;
    }
    catch(IOException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new IOException(s);
    }
  }
  
  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }
  
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /** The default is an ordinal value of 0 with no range. */
  public static final BDynamicEnum DEFAULT = make(0);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicEnum.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private int ordinal;
  private BEnumRange range;
  private String string;
  private int hashCode = -1;
    
}
