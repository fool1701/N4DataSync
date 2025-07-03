/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.Array;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;

/**
 * The BFacetsMap, modeled after baja:NameMap, is a map of String names 
 * to BFacets values:
 * <pre>
 *  map   := "{" pairs "}"
 *  pairs := pair ( ";" pair )*
 *  pair  := escaped name "=" value
 *  name  := escaped String
 *  value := escaped Facets
 * </pre>    
 * The escape format for names and values uses the "\" character
 * to denote control characters which should be treated as part
 * of the name or value string. The control characters which must
 * be escaped include "\", "{", "}", "=", and ";".
 * 
 * @author Matt Boon
 * @creation Apr 23, 2010
 * @since Energy Management 1.0
 *
 */
@NiagaraType
@NoSlotomatic
public final class BFacetsMap
  extends BSimple
{ 
////////////////////////////////////////////////////////////////
// Factory Methods
////////////////////////////////////////////////////////////////

  /**
   * Make using the specified map where the keys must be 
   * Strings, and the values must be BFacets.
   */
  @SuppressWarnings("unchecked")
  public static BFacetsMap make(HashMap<String,BFacets> map)
  {  
    if (map.isEmpty()) return NULL;
                               
    return (BFacetsMap)(new BFacetsMap((HashMap<String,BFacets>)map.clone()).intern());
  }

  /**
   * Convenience for <code>make(orig, name, BFacets.make(value))</code>.
   */
  public static BFacetsMap make(BFacetsMap orig, String name, String value) 
    throws IOException
  {             
    return make(orig, name, BFacets.make(value));
  }

  /**
   * Create a new map from the original except add or 
   * set the specified name/value pair.
   */
  @SuppressWarnings("unchecked")
  public static BFacetsMap make(BFacetsMap orig, String name, BFacets value)
  {
    HashMap<String,BFacets> map = (HashMap<String,BFacets>)orig.map.clone();
    map.put(name, value);
    return (BFacetsMap)(new BFacetsMap(map).intern());
  }

  /**
   * Create a new map from the original, except add or overwrite the values from
   * the <code>other</code> map.
   */
  @SuppressWarnings("unchecked")
  public static BFacetsMap make(BFacetsMap orig, BFacetsMap other)
  {
    HashMap<String,BFacets> map = (HashMap<String,BFacets>)orig.map.clone();
    String[] otherKeys = other.list();
    for (String otherKey : otherKeys)
    {
      map.put(otherKey, other.get(otherKey));
    }
    return (BFacetsMap)(new BFacetsMap(map).intern());
  }

  /**
   * Create a new map from the original except remove the 
   * name/value pair.
   */
  @SuppressWarnings("unchecked")
  public static BFacetsMap remove(BFacetsMap orig, String name)
  {
    HashMap<String,BFacets> map = (HashMap<String,BFacets>)orig.map.clone();
    map.remove(name);
    return (BFacetsMap)(new BFacetsMap(map).intern());
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BFacetsMap(HashMap<String,BFacets> map)
  {
    this.map = map;   
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the name keys.
   */
  public String[] list()
  {
    return map.keySet().toArray(new String[map.size()]);
  }  

  /**
   * Get value for the specified name.
   */
  public BFacets get(String name)
  {
    BFacets result = map.get(name);
    return (result == null) ? BFacets.NULL : result;
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
 
  /**
   * Return true if the map has a length of zero.
   */
  @Override
  public boolean isNull()
  {
    return map.isEmpty();
  }
  
  /**
   * BFacetsMap uses its encodeToString() value's hash code.
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
   * Equality is based on both maps containing 
   * the same keys and values.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BFacetsMap)
    {
      BFacetsMap x = (BFacetsMap)obj;
      if (map.size() != x.map.size()) return false;
      for (String key : map.keySet())
      {
        BFacets val = map.get(key);
        if (!val.equals(x.map.get(key))) return false;
      }
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
    try
    {
      return encodeToString();
    }
    catch (IOException e)
    {
      throw new BajaRuntimeException(e);
    }
  }
  
  /**
   * Encode using writeUTF(encodeToString()).
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
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      s.append('{');
      for (String key : map.keySet())
      {
        BFacets val = map.get(key);
        escape(s, key);
        s.append('=');
        escape(s, val.encodeToString());
        s.append(';');
      }
      s.append('}');
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
      // stripping this first let us add stuff in the future
      int start = s.indexOf('{');
      int end = s.lastIndexOf('}');
      if (start+1 == end) return NULL;
      String body = s.substring(start+1, end);
      
      // walk thru finding name=value pairs and unescaping
      Array<String> acc = new Array<>(String.class);
      int len = body.length();              
      StringBuilder buf = new StringBuilder();
      int lastDelim = -1;
      for(int i=0; i<len; ++i)
      {                       
        char c = body.charAt(i);
        if (c == '\\') buf.append(body.charAt(++i));
        else if (c != '=' && c != ';') buf.append(c);                           
        else
        {          
          if (c == lastDelim) throw new Exception();
          lastDelim = c;
          acc.add(buf.toString());
          buf.setLength(0);
        }     
      }            

      // create map of name/value pairs
      HashMap<String,BFacets> map = new HashMap<>();
      for(int i=0; i<acc.size(); i+=2)
      {
        String key = acc.get(i);
        BFacets val = BFacets.make(acc.get(i+1));
        map.put(key, val);
      }      
      
      // done
      BFacetsMap x = (BFacetsMap)(new BFacetsMap(map).intern());
      x.string = s;
      return x;
    }
    catch(Exception e)
    {       
      e.printStackTrace();
      throw new IOException("Invalid BFacetsMap: " + s);
    }
  }           
  
  /**
   * Write the string but escape control characters.
   */
  private void escape(StringBuilder buf, String s)
  {                  
    int len = s.length();  
    for(int i=0; i<len; ++i)
    {
      char c = s.charAt(i);
      switch(c)
      {                       
        case '\\': buf.append("\\\\"); break;
        case '{':  buf.append("\\{"); break;
        case '}':  buf.append("\\}"); break;
        case '=':  buf.append("\\="); break;
        case ';':  buf.append("\\;"); break;
        default:   buf.append(c);    break;
      }
    }
  }    
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * Null is the empty map.
   */
  public static final BFacetsMap NULL = new BFacetsMap(new HashMap<>());

  /**
   * The map is NULL.
   */
  public static final BFacetsMap DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFacetsMap.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
      
  private String string;
  private HashMap<String,BFacets> map;
  private int hashCode = -1;
}
