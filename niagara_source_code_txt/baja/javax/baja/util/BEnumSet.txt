/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.util.ArrayUtil;

/**
 * The BEnumSet is a dynamic list of enum ordinals, which may
 * customized with an EnumRange.  Enum set's string format
 * is a list of ords separated by commas, with an option
 * range specified as "@range":
 * <pre>
 *   set      = ordinals [ "@" range ]
 *   ordinals = ordinal ( "," ordinals)
 * </pre>
 *
 * @author    Brian Frank
 * @creation  27 Jul 04
 * @version   $Revision: 2$ $Date: 1/25/08 4:04:07 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BEnumSet
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a BEnumSet with the specified ordinals and range.
   */
  public static BEnumSet make(int[] ordinal, BEnumRange range)
  {
    if (range == null) range = BEnumRange.NULL;
    return (BEnumSet)(new BEnumSet(ordinal, range).intern());
  }

  /**
   * Create an BEnumSet with the specified ordinals with a
   * range equal to BEnumRange.NULL.
   */
  public static BEnumSet make(int[] ordinals)
  {
    return make(ordinals, BEnumRange.NULL);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor with sorted ordinals.
   */
  private BEnumSet(int[] ordinals, BEnumRange range) 
  { 
    // always store a safe sorted copy of the ordinals
    ordinals = ordinals.clone();
    ArrayUtil.sort(ordinals);                      
                
    this.ordinals = ordinals;
    this.range = range;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the array of ordinals.
   */
  public int[] getOrdinals()
  {         
    return ordinals.clone();
  }
      
  /**
   * Does this set contain the specified ordinal.
   */
  public boolean contains(int ordinal)
  {                                                   
    return ArrayUtil.binarySearch(ordinals, ordinal) != -1;
  }               
  
  /**
   * Return the range associated with this set.
   */
  public BEnumRange getRange()
  {                                            
    return range;
  }  
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
 
  /**
   * Return true if the ordinals have a length of zero.
   */
  @Override
  public boolean isNull()
  {
    return ordinals.length == 0;
  }
  
  /**
   * BEnumSet uses its encodeToString() value's hash code.
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
   * Return if obj is an equivalent BEnumSet.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BEnumSet)
    {
      BEnumSet x = (BEnumSet)obj;
      if (ordinals.length != x.ordinals.length) return false;
      for(int i=0; i<ordinals.length; ++i)
        if (ordinals[i] != x.ordinals[i]) return false;
      return range.equals(x.range);
    }
    return false;
  }
  
  /**
   * To string method.
   */
  @Override
  public String toString(Context context)
  {                
    // use context or my own range
    BEnumRange range = this.range;                     
    if (context != null)
    {
      BEnumRange r = (BEnumRange)context.getFacet(BFacets.RANGE);
      if (r != null) range = r;
    }                                 
    
    // encode each ordinal's tag
    StringBuilder s = new StringBuilder();
    for(int i=0; i<ordinals.length; ++i)
    {
      if (i > 0) s.append(',');
      s.append(range.getDisplayTag(ordinals[i], context));
    }                   
    
    return s.toString();
  }
  
  /**
   * Encoded as using writeUTF(encodeToString()).
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * Decoded using decodeFromString(readUTF()).
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Write the simple in text format.
   */
  @Override
  public String encodeToString()
    throws IOException
  {                          
    StringBuilder s = new StringBuilder();
    
    for(int i=0; i<ordinals.length; ++i)
    {
      if (i > 0) s.append(',');
      s.append(ordinals[i]);
    } 
                          
    if (!range.isNull())
      s.append('@').append(range.encodeToString());
      
    return s.toString();
  }
  
  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {         
    // parse range if specified
    BEnumRange range = null;           
    int at = s.indexOf('@');
    if (at >= 0)
    {
      range = (BEnumRange)BEnumRange.DEFAULT.decodeFromString(s.substring(at+1));
      s = s.substring(0, at);
    }                       
    
    // parse ordinals
    int[] temp = new int[s.length()/2 + 1];
    int n=0;
    StringTokenizer st = new StringTokenizer(s, ",");
     while(st.hasMoreTokens())
       temp[n++] = Integer.parseInt(st.nextToken());
    
    // trim array
    int[] ordinals = new int[n];
    System.arraycopy(temp, 0, ordinals, 0, n);
    
    return make(ordinals, range);
  }  

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * Null is the empty set.
   */
  public static final BEnumSet NULL = make(new int[0], BEnumRange.NULL);

  /**
   * The default set is NULL.
   */
  public static final BEnumSet DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumSet.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
      
  private int[] ordinals;
  private BEnumRange range;
  private int hashCode = -1;
}
