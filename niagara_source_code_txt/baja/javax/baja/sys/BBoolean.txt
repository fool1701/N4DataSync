/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.util.*;

/**
 * The BBoolean is the wrapper class for primitive
 * boolean objects.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 36$ $Date: 11/16/06 3:35:07 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBoolean
  extends BEnum
  implements BIBoolean, BIDataValue
{ 

////////////////////////////////////////////////////////////////
// Factory
//////////////////////////////////////////////////////////////// 
 
  /**
   * Get the BBoolean instance for the primitive boolean.
   */
  public static BBoolean make(boolean b)
  {
    return b ? TRUE : FALSE;
  }

  /**
   * Get the BBoolean instance for the string.
   */
  public static BBoolean make(String s)      
  {
    return decode(s) ? TRUE : FALSE;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BBoolean(boolean v)
  {
    this.value = v;
  }

////////////////////////////////////////////////////////////////
// Boolean
////////////////////////////////////////////////////////////////  

  /**
   * @return the boolean value.
   */
  @Override
  public boolean getBoolean()
  {
    return value;
  }

  /**
   * @return the {@code BFacets.NULL}.
   */
  @Override
  public BFacets getBooleanFacets()
  {
    return BFacets.NULL;
  }

  /**
   * @return the opposite of this boolean.
   */
  public BBoolean not()
  {
    return make(!value);
  }

  /**
   * @return the 'and' of this boolean and another.
   */
  public BBoolean and(BBoolean other)
  {
    return make(value && other.value);
  }

  /**
   * @return the 'or' of this boolean and another.
   */
  public BBoolean or(BBoolean other)
  {
    return make(value || other.value);
  }

////////////////////////////////////////////////////////////////
// Enum
////////////////////////////////////////////////////////////////  
  
  /**
   * True is 1 and false is 0.
   */
  @Override
  public int getOrdinal()
  {
    return value ? 1 : 0;
  }

  /**
   * True is "true" and false is "false".
   */
  @Override
  public String getTag()
  {
    return value ? "true" : "false";
  }
  
  /**
   * True is "true" and false is "false".
   */
  @Override
  public String getDisplayTag(Context cx)
  {
    return getTag();
  }

  /**
   * Return the range "{false=0,true=1}".
   */
  @Override
  public BEnumRange getRange()
  {                      
    return BEnumRange.BOOLEAN_RANGE;
  }                
    
  /**
   * BBoolean is active if it is true.
   */
  @Override
  public boolean isActive()
  {
    return value;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BBoolean hashcode is either 1 or 0.
   */
  public int hashCode()
  {
    if (value) return 1;
    else return 0;
  }
  
  /**
   * BBoolean equality is based on boolean value equality.
   */
  public boolean equals(Object obj)
  {
    // since we only allow two instances, we can 
    // use the simple reference equality operator
    return this == obj;
  }

  /**
   * Route to {@code BBoolean.toString(boolean, Context)}.
   */
  @Override
  public String toString(Context context)
  {
    return toString(value, context);
  }  
    
  /**
   * BBoolean is serialized using writeBoolean().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeBoolean(value);
  }
  
  /**
   *  BBoolean is unserialized using readBoolean().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return in.readBoolean() ? TRUE : FALSE;
  }
  
  /**
   * Route to {@code BBoolean.encode(boolean)}.
   */               
  @Override
  public String encodeToString()
    throws IOException
  {
    return encode(value);
  }

  /**
   * Route to {@code BBoolean.decode(String)}.
   */               
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {                       
    return make(s);
  }
  
  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }
  
////////////////////////////////////////////////////////////////
// Primitive Encoding
////////////////////////////////////////////////////////////////  

  /**
   * Encode a boolean primitive value to its
   * text format "true" or "false".
   */
  public static String encode(boolean value)
  {
    return value ? "true" : "false";
  }

  /**
   * Decode text format directly to a boolean primitive.
   */
  public static boolean decode(String s)
  {
    if (s.equals("true")) return true;
    else if (s.equals("false")) return false;
    else throw new IllegalArgumentException("Invalid boolean: " + s);
  }

////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////  

  /**
   * Format the boolean value using the specified Context.
   * Use BFacet.TRUE_TEXT or BFacet.FALSE_TEXT if specified,
   * otherwise return "true" or "false".
   */
  public static String toString(boolean value, Context context)
  {
    if (context != null)
    {
      if (value)
      {
        BObject text = context.getFacet(BFacets.TRUE_TEXT);
        if (text != null) return BFormat.format(text.toString(), null, context);
      }
      else
      {
        BObject text = context.getFacet(BFacets.FALSE_TEXT);
        if (text != null) return BFormat.format(text.toString(), null, context);
      }
    }
    return Lexicon.make("baja", context).getText(value ? "true" : "false");
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The {@code true} constant.
   */
  public static final BBoolean TRUE = new BBoolean(true);
  
  /**
   * The {@code false} constant.
   */
  public static final BBoolean FALSE = new BBoolean(false);
  
  /**
   * The default constant is {@code FALSE}.
   */
  public static final BBoolean DEFAULT = FALSE;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBoolean.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////          
          
  private boolean value;  
  BDynamicEnum asDynamic;
}
