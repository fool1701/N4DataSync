/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.naming.BOrd;
import javax.baja.naming.NullOrdException;
import javax.baja.naming.OrdQuery;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.SortUtil;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BIUnlinkable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BOrdToCategoryMap stores a table mapping BOrds to BCategoryMasks.
 *
 * @author    Brian Frank
 * @creation  19 Feb 05
 * @version   $Revision: 4$ $Date: 9/3/08 10:20:48 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BOrdToCategoryMap
  extends BSimple
  implements BIUnlinkable
{            

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  
  
  /**
   * Make a map with the specified ord and category arrays.
   */
  public static BOrdToCategoryMap make(BOrd[] ords, BCategoryMask[] cats)
  {  
    if (ords.length != cats.length)
    {
      throw new IllegalArgumentException("ords.length != masks.length");
    }
    
    // short circuit if the null map  
    if (ords.length == 0)
    {
      return NULL;
    }

    // make safe copy
    ords = ords.clone();
    cats = cats.clone();
    
    // walk each ord to be safe
    for (BOrd ord : ords)
    {
      // cannot use null ord
      if (ord.isNull())
      {
        throw new NullOrdException();
      }

      // cannot use host or session absolute (but ignore 
      // trailing slashes which are not usually allow)
      OrdQuery q = ord.parse()[0];
      if (q.isHost())
      {
        throw new IllegalArgumentException("Cannot use host absolute ord: " + ord);
      }
      if (q.isSession())
      {
        throw new IllegalArgumentException("Cannot use session absolute ord: " + ord);
      }
    }     
    
    // sort ords by length so that getAppliedCategoryMask(BOrd) works
    SortUtil.sort(ords, cats, (BOrd a, BOrd b)->b.toString().length() - a.toString().length());
    
    // everything looks ok
    return (BOrdToCategoryMap)new BOrdToCategoryMap(ords, cats, null).intern();
  }

  /**
   * Set the category mask for the object identified by the specified ord.
   *
   * @param ord The ordInSession for an object to categorize.
   * @param mask The mask for the categorizable object.
   */
  public BOrdToCategoryMap setCategoryMask(BOrd ord, BCategoryMask mask)
  {
    int index = -1;
    
    // see if the ord is already mapped
    String s = TextUtil.toLowerCase(ord.relativizeToSession().toString());
    int len = ordStrings.length;
    for(int i=0; i<len; ++i)
    {
      if (s.equals(ordStrings[i]))
      {
        index = i;
        break;
      }
    }
    
    BOrd[] newOrds;
    BCategoryMask[] newCats;
    
    // if mapped, update it
    if (index != -1)
    {
      // if mask is null, remove it
      if (mask == null || mask.isNull())
      {
        newOrds = removeElement(ords, new BOrd[ords.length-1], index);
        newCats = removeElement(cats, new BCategoryMask[cats.length-1], index);
      }
      // otherwise modify it
      else
      {
        if (cats[index].equals(mask))
        {
          return this;
        }
      
        newOrds = new BOrd[ords.length];
        System.arraycopy(ords, 0, newOrds, 0, ords.length);
        newOrds[index] = ord;
        
        newCats = new BCategoryMask[cats.length];
        System.arraycopy(cats, 0, newCats, 0, cats.length);
        newCats[index] = mask;
      }
      
      return (BOrdToCategoryMap)new BOrdToCategoryMap(newOrds, newCats, null).intern();
    }
    // otherwise, add it
    else
    {
      if (mask != null && !mask.isNull())
      {
        newOrds = new BOrd[ords.length+1];
        System.arraycopy(ords, 0, newOrds, 0, ords.length);
        newOrds[ords.length] = ord;
        
        newCats = new BCategoryMask[cats.length+1];
        System.arraycopy(cats, 0, newCats, 0, cats.length);
        newCats[cats.length] = mask;
  
        return make(newOrds, newCats);
      }
      else
      {
        return this;
      }
    }
  }

  /**
   * Copy the origArray to newArray removing the element at the specified index.
   */
  private static <T extends Object> T[] removeElement(T[] origArray, T[] newArray, int index)
  {
    if (index == -1)
    {
      throw new ArrayIndexOutOfBoundsException("-1 is not a valid array index");
    }
    
    if (index == 0)
    {
      System.arraycopy(origArray, 1, newArray, 0, origArray.length - 1);
    }
    else if (index == origArray.length-1)
    {
      System.arraycopy(origArray, 0, newArray, 0, origArray.length - 1);
    }
    else
    {
      System.arraycopy(origArray, 0, newArray, 0, index);
      System.arraycopy(origArray, index+1, newArray, index, origArray.length-(index+1));
    }
    
    return newArray;
  }


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  
  
  /**
   * Private constructor.
   */
  BOrdToCategoryMap(BOrd[] ords, BCategoryMask[] cats, String string)
  {                          
    // map fields (must pass in safe immutable array)
    this.ords   = ords;
    this.cats   = cats;                                               
    this.string = string;  
    
    // map all the ords to lowercase strings and cache the
    // result for higher performance in getAppliedCategoryMask(BOrd);
    // we also do a sanity check to make sure that ords are sorted 
    // by length (since we assume decodeFromString() is sorted)
    int lastLength = Integer.MAX_VALUE;
    ordStrings = new String[ords.length];
    ordSlashes = new String[ords.length];
    for(int i=0; i<ords.length; ++i)   
    { 
      // normalize the string's case                    
      String s = TextUtil.toLowerCase(ords[i].encodeToString());
      ordStrings[i] = s; 
      
      // also cache a version of the string with a trailing slash     
      if (s.endsWith("/") || s.endsWith("^") || s.endsWith("!") || s.endsWith("~"))
      {
        ordSlashes[i] = s;
      }
      else
      {
        ordSlashes[i] = s + "/";
      }
      
      // sanity check for sort order
      int thisLength = s.length();
      if (thisLength > lastLength)
      {
        throw new IllegalStateException();
      }
      lastLength = thisLength;
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Is this the NULL instance which has a size of zero.
   */
  @Override
  public boolean isNull()
  {
    return this == NULL;
  }

  /**
   * Get the number of ord/category items in this map.
   */
  public int size()
  {                         
    return ords.length;
  }    
  
  /**
   * Get the ord at the specified index: 0 to size()-1.
   */
  public BOrd getOrd(int index)
  {                  
    return ords[index];
  }                     

  /**
   * Get the category mask at the specified index: 0 to size()-1.
   */
  public BCategoryMask getCategoryMask(int index)
  {                                     
    return cats[index];
  }                     

  /**
   * Given an ord, search for an exact match based on comparing 
   * the specified ord to find the ord in this map.  If an
   * exact match isn't found, return null.
   */
  public BCategoryMask getCategoryMask(BOrd ord)
  {                                    
    // always normalize to session and lower case  
    String s = TextUtil.toLowerCase(ord.relativizeToSession().toString());
    
    // find exact match (case insensitive)
    int len = ordStrings.length;
    for(int i=0; i<len; ++i)
    {
      if (s.equals(ordStrings[i]))
      {
        return cats[i];
      }
    }           
    
    return null;
  }

  /**
   * Given an ord, search for the best match based on comparing 
   * the specified ord to find the ord in this map with the longest 
   * matching substring.  If no ords in the map are matches, then
   * return null.
   */
  public BCategoryMask getAppliedCategoryMask(BOrd ord)
  {            
    // always normalize to session and lower case  
    String s = TextUtil.toLowerCase(ord.relativizeToSession().toString());

    // search for first match - since the ords are
    // sorted by length (longest first) then we know 
    // the first hit is the best hit
    int len = ordSlashes.length;
    for(int i=0; i<len; ++i)
    {                 
      // first try for an exact match
      if (s.equals(ordStrings[i]))
      {
        return cats[i];
      }
      
      // then try using a trailing slash, this prevents
      // weird matches like /foo against /f
      if (s.startsWith(ordSlashes[i]))
      {
        return cats[i];
      }
    }
    
    // no luck
    return null;
  }
    
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * BOrdToCategoryMap uses its encodeToString() value's hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    try
    {
      if (hashCode == -1)
      {
        hashCode = encodeToString().hashCode();
      }
      return hashCode;
    }
    catch(Exception e) 
    { 
      return System.identityHashCode(this); 
    }
  }
  
  public boolean equals(Object obj)
  {                    
    if (obj instanceof BOrdToCategoryMap)
    {                  
      BOrdToCategoryMap x = (BOrdToCategoryMap)obj;
      if (ords.length != x.ords.length)
      {
        return false;
      }
      for(int i=0; i<ords.length; ++i)
      {
        if (!ords[i].equals(x.ords[i]))
        {
          return false;
        }
        if (!cats[i].equals(x.cats[i]))
        {
          return false;
        }
      }
      return true;
    }                                              
    return false;
  }

  @Override
  public void encode(DataOutput out)
    throws IOException
  {                      
    out.writeUTF(encodeToString());
  }
  
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {                       
    return decodeFromString(in.readUTF());
  }

  @Override
  public String encodeToString()
  {                     
    if (string == null)
    {          
      StringBuilder s = new StringBuilder();
      for(int i=0; i<ords.length; ++i)
      {
        if (i > 0)
        {
          s.append('\n');
        }
        s.append(ords[i].encodeToString()).append('=').append(cats[i]);
      }
      string = s.toString();
    }
    return string;
  }
  
  @Override
  public BObject decodeFromString(String s)
  {
    // not the most efficient, but this class should only
    // be used once in the CategoryService, so just make the
    // code simple to maintain and read
    String[] lines = TextUtil.split(s, '\n');   

    // short circuit for null
    int len = lines.length;  
    if (len == 0)
    {
      return NULL;
    }
    
    // allocate arrays
    BOrd[] ords = new BOrd[len];
    BCategoryMask[] cats = new BCategoryMask[len];
    
    // decode each line into the array index
    for(int i=0; i<lines.length; ++i)
    {           
      String line = lines[i];                            
      int eq = line.lastIndexOf('=');
      ords[i] = BOrd.make(line.substring(0, eq));
      cats[i] = BCategoryMask.make(line.substring(eq+1));
    } 
    
    // create new instance
    return new BOrdToCategoryMap(ords, cats, s).intern();
  }  

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  
  
  /**
   * The null instance has a size of zero.
   */
  public static final BOrdToCategoryMap NULL = new BOrdToCategoryMap(new BOrd[0], new BCategoryMask[0], null);

  /**
   * This is default instance is NULL.
   */
  public static final BOrdToCategoryMap DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdToCategoryMap.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final BOrd[] ords;
  private final String[] ordStrings;
  private final String[] ordSlashes;
  private final BCategoryMask[] cats;
  private String string;
  private int hashCode = -1;
} 

