/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BOrdList stores zero or more BOrds.  Its serialization
 * format is ords separated by the newline character.
 *
 * @author    Brian Frank
 * @creation  16 Apr 03
 * @version   $Revision: 9$ $Date: 1/10/11 9:43:33 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BOrdList
  extends BSimple
  implements Iterable<BOrd>
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make for a single ord.
   */
  public static BOrdList make(BOrd ord)
  {
    return new BOrdList(new BOrd[] { ord }, ord.encodeToString());
  }

  /**
   * Make for an array of ords.
   */
  public static BOrdList make(BOrd ... ords)
  {
    if (ords.length == 0)
      return NULL;
    else
      return new BOrdList(ords.clone(), null);
  }

  /**
   * Make a new list by appending the specified 
   * ord to the original list.
   */
  public static BOrdList add(BOrdList orig, BOrd ord)
  {
    BOrd[] ords = new BOrd[orig.ords.length+1];
    System.arraycopy(orig.ords, 0, ords, 0, orig.ords.length);
    ords[ords.length-1] = ord;
    return new BOrdList(ords, null);
  }

  /**
   * Make a new list by removing the ord at the specified index.
   */
  public static BOrdList remove(BOrdList orig, int index)
  {
    BOrd[] ords = new BOrd[orig.ords.length-1];
    System.arraycopy(orig.ords, 0, ords, 0, index);
    if (index < orig.ords.length)
      System.arraycopy(orig.ords, index+1, ords, index, orig.ords.length-index-1);
    return new BOrdList(ords, null);
  }

  /**
   * Convenience for {@code DEFAULT.decodeFromString(string)}
   */
  public static BOrdList make(String string)
  {                                                        
    return (BOrdList)DEFAULT.decodeFromString(string);
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BOrdList(BOrd[] ords, String string)
  {
    this.ords = ords;
    this.string = string;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the ord at the specified index.
   */
  public BOrd get(int index)
  {
    return ords[index];
  }

  /**
   * Get the number of ords in this list.
   */
  public int size()
  {
    return ords.length;
  }

  /**
   * Is this instance null?
   */
  @Override
  public boolean isNull()
  {
    return ords.length == 0;
  }

  /**
   * Get the array of ords.
   */
  public BOrd[] toArray()
  {
    return ords.clone();
  }
    
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Hash is based on hashes of individual ords.
   */
  public int hashCode()
  {
    if (hashCode == -1)   
    {
      int x = 11;
      for(int i=0; i<ords.length; ++i) x ^= ords[i].hashCode();
      hashCode = x;
    }
    return hashCode;
  }
  
  /**
   * Equality is based on equality of individual ords.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BOrdList)
    {
      BOrd[] a = this.ords;
      BOrd[] b = ((BOrdList)obj).ords;
      if (a.length != b.length) return false;
      for(int i=0; i<a.length; ++i)
        if (!a[i].equals(b[i])) return false;
      return true;
    }
    return false;
  }
    
  /**
   * BOrd is encoded as using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BOrd is decoded using readUTF().
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
      if (ords.length == 1)
      {
        string = ords[0].encodeToString();
      }
      else
      {
        StringBuilder s = new StringBuilder();
        for(int i=0; i<ords.length; ++i)
        {
          if (i > 0) s.append('\n');
          s.append(ords[i].encodeToString());
        }
        string = s.toString();
      }
    }
    return string;
  }
  
  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
  {                             
    if (s.isEmpty()) return DEFAULT;
         
    int nl = s.indexOf('\n');
    if (nl < 0) return new BOrdList(new BOrd[] { BOrd.make(s) }, s);
    
    List<BOrd> list = new ArrayList<>();
    
    StringTokenizer st = new StringTokenizer(s, "\n");
    while(st.hasMoreTokens())
      list.add(BOrd.make(st.nextToken()));
     
    BOrd[] ords = list.toArray(new BOrd[list.size()]);
    return new BOrdList(ords, s);
  }

////////////////////////////////////////////////////////////////
// Iterable
////////////////////////////////////////////////////////////////

  @Override
  public Iterator<BOrd> iterator()
  {
    return new Iterator<BOrd>()
    {
      @Override
      public boolean hasNext()
      {
        return index < ords.length;
      }

      @Override
      public BOrd next()
      {
        if (index > ords.length) throw new NoSuchElementException();
        BOrd result = ords[index];
        index++;
        return result;
      }

      private int index = 0;
    };
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public String toString(Context cx)
  {                          
    if (ords.length == 1) 
      return ords[0].toString(cx);
    
    StringBuilder buf = new StringBuilder();
    for (int i=0; i<ords.length; i++)
    {
      if (i > 0) buf.append("; ");
      buf.append(ords[i].toString(cx));
    }
    return buf.toString();
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * Null is the empty list.
   */
  public static final BOrdList NULL = new BOrdList(new BOrd[0], "");
  
  /**
   * The default is the empty list.
   */
  public static final BOrdList DEFAULT = NULL;

  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdList.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////                        
  
  private int hashCode = -1;
  private BOrd[] ords;
  private String string;

}

