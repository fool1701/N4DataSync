/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.naming.*;

/**
 * The BNameList is a Baja simple which contains a list
 * of names separated by a semicolon.  Names are escaped
 * using SlotPath.  It provides a standard way to config 
 * simple name based groups.
 *
 * @author    Brian Frank
 * @creation  19 Mar 02
 * @version   $Revision: 6$ $Date: 8/26/08 4:23:13 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BNameList
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Factory Methods
////////////////////////////////////////////////////////////////

  /**
   * Make a BNameList with one name.
   */
  public static BNameList make(String name)
  {
    return make(new String[] { name });
  }

  /**
   * Make a BNameList with the specified array of names.
   */
  public static BNameList make(String[] names)
  {
    if (names.length == 0) return NULL;
    
    String[] safe = new String[names.length];
    for(int i=0; i<names.length; ++i)
    {
      String name = safe[i] = names[i];
      SlotPath.verifyValidName(name);
    }
    return (BNameList)(new BNameList(safe).intern());
  }
  
  /**
   * Create a new BNameList which is the union of 
   * the two name lists with no duplicates.
   */
  public static BNameList union(BNameList a, BNameList b)
  {
    if (a.isNull()) return b;
    if (b.isNull()) return a;
    
    HashMap<String, String> map = new HashMap<>();
    String[] an = a.names;
    String[] bn = b.names;
    for(int i=0; i<an.length; ++i) map.put(an[i], an[i]);
    for(int i=0; i<bn.length; ++i) map.put(bn[i], bn[i]);
    
    String[] names = map.values().toArray(new String[map.size()]);
    return (BNameList)(new BNameList(names).intern());
  }

  /**
   * Create a new BNameList which is the intersection 
   * of the two name lists.
   */
  public static BNameList intersection(BNameList a, BNameList b)
  {
    if (a.isNull()) return NULL;
    if (b.isNull()) return NULL;
    
    ArrayList<String> v = new ArrayList<>();
    String[] an = a.names;
    for(int i=0; i<an.length; ++i) 
      if (b.contains(an[i])) v.add(an[i]);
    
    String[] names = v.toArray(new String[v.size()]);
    return (BNameList)(new BNameList(names).intern());
  }

  /**
   * Create a new BNameList which has all the names in <code>a</code> 
   * that are not in <code>b</code>. In set theory notation, the result
   * is equivalent to <code>(a - b)</code>.
   * 
   * @since Niagara 3.4
   */
  public static BNameList difference(BNameList a, BNameList b)
  {
    if (a.isNull()) return NULL;
    if (b.isNull()) return a;

    HashSet<String> bSet = new HashSet<>(b.names.length);
    for (int i = 0; i < b.names.length; ++i) bSet.add(b.names[i]);

    ArrayList<String> diff = new ArrayList<>();
    for (int i = 0; i < a.names.length; ++i)
      if (!bSet.contains(a.names[i])) diff.add(a.names[i]);
    return (BNameList)(new BNameList(diff.toArray(new String[diff.size()])).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BNameList(String[] names) 
  {
    this.names = names;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the array of names.
   */
  public String[] getNames()
  {
    String[] copy = new String[names.length];
    System.arraycopy(names, 0, copy, 0, copy.length);
    return copy;
  }
      
  /**
   * Does this name list contain the specified name.
   */
  public boolean contains(String name)
  {
    String[] names = this.names;
    for(int i=0; i<names.length; ++i)
      if (names[i].equals(name))
        return true;
    return false;
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
 
  /**
   * Return true if the list has a length of zero.
   */
  @Override
  public boolean isNull()
  {
    return names.length == 0;
  }
  
  /**
   * BNameList uses its encodeToString() value's hash code.
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
   * BNameList equality is based on both BNameLists 
   * containing the same names, but not necessarily 
   * in the same order.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BNameList)
    {
      BNameList x = (BNameList)obj;
      if (names.length != x.names.length) return false;
      for(int i=0; i<names.length; ++i)
        if (!x.contains(names[i])) return false;
      return true;
    }
    return false;
  }
  
  /**
   * To string method.
   */
  @Override
  public String toString(Context context)
  {
    return encodeToString();
  }
  
  /**
   * BNameList is encoded as using writeUTF(encodeToString()).
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BNameList is decoded using decodeFromString(readUTF()).
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
  {        
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      for(int i=0; i<names.length; ++i)
      {
        if (i > 0) s.append(';');
        s.append(names[i]);
      }
      string = s.toString();
    }
    return string;
  }
  
  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      ArrayList<String> v = new ArrayList<>();
      StringTokenizer st = new StringTokenizer(s, ";");
      while(st.hasMoreTokens())
      {
        String name = st.nextToken().trim();
        SlotPath.verifyValidName(name);
        v.add(name);
      }
      
      BNameList list = (BNameList)(new BNameList(v.toArray(new String[v.size()])).intern());
      list.string = s;
      return list;
    }
    catch(IllegalNameException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      throw new IOException("Invalid BNameList: " + s);
    }
  }  

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * Null is the empty list.
   */
  public static final BNameList NULL = new BNameList(new String[0]);

  /**
   * The default name list is NULL.
   */
  public static final BNameList DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNameList.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
      
  private String string;
  private String[] names;
  private int hashCode = -1;
}
