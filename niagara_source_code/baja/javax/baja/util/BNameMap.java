/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.Array;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BNameMap is a map of String names to BFormat values:
 * <pre>
 *  map   := "{" pairs "}"
 *  pairs := pair ( ";" pair )*
 *  pair  := name "=" value
 *  name  := escaped String
 *  value := escaped Format
 * </pre>    
 * The escape format for names and values uses the "\" character
 * to denote control characters which should be treated as part
 * of the name or value string. The control characters which must
 * be escaped include "\", "{", "}", "=", and ";".
 *
 * @author    Brian Frank
 * @creation  11 Feb 03
 * @version   $Revision: 7$ $Date: 4/1/08 11:24:05 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BNameMap
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Factory Methods
////////////////////////////////////////////////////////////////

  /**
   * Make using the specified map where the keys must be 
   * Strings, and the values must be BFormats.
   */
  public static BNameMap make(HashMap<String, BFormat> map)
  {  
    if (map.isEmpty())
    {
      return NULL;
    }

    HashMap<String, BFormat> newMap = new HashMap<>(map);
    return (BNameMap)new BNameMap(newMap).intern();
  }

  /**
   * Convenience for <code>make(orig, name, BFormat.make(value))</code>.
   */
  public static BNameMap make(BNameMap orig, String name, String value)
  {             
    return make(orig, name, BFormat.make(value));
  }

  /**
   * Create a new map from the original except add or 
   * set the specified name/value pair.
   */
  public static BNameMap make(BNameMap orig, String name, BFormat value)
  {
    HashMap<String, BFormat> map = new HashMap<>(orig.map);
    map.put(name, value);
    return (BNameMap)new BNameMap(map).intern();
  }

  /**
   * Create a new map from the original, except add or overwrite the values from
   * the <code>other</code> map.
   * 
   * @since Niagara 3.4
   */
  public static BNameMap make(BNameMap orig, BNameMap other)
  {
    HashMap<String, BFormat> map = new HashMap<>(orig.map);
    String[] otherKeys = other.list();
    for (int i = 0; i < otherKeys.length; ++i)
      map.put(otherKeys[i], other.get(otherKeys[i]));
    return (BNameMap)new BNameMap(map).intern();
  }

  /**
   * Create a new map from the original except remove the 
   * name/value pair.
   */
  public static BNameMap remove(BNameMap orig, String name)
  {
    HashMap<String, BFormat> map = new HashMap<>(orig.map);
    map.remove(name);
    return (BNameMap)new BNameMap(map).intern();
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BNameMap(HashMap<String, BFormat> map)
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
  public BFormat get(String name)
  {
    return map.get(name);
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
   * BNameMap uses its encodeToString() value's hash code.
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
   * Equality is based on both maps containing 
   * the same keys and values.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BNameMap)
    {
      BNameMap x = (BNameMap)obj;
      if (map.size() != x.map.size()) return false;
      Iterator<String> it = map.keySet().iterator();
      while(it.hasNext())
      {
        String key = it.next();
        BFormat val = map.get(key);
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
    return encodeToString();
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
  {        
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      s.append('{');
      Iterator<String> it = map.keySet().iterator();
      while(it.hasNext())
      {
        String  key = it.next();
        BFormat val = map.get(key);
        escape(s, key);
        s.append('=');
        escape(s, val.getFormat());
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
      int lastDelim = ';';
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
      HashMap<String, BFormat> map = new HashMap<>();
      for(int i=0; i<acc.size(); i+=2)
      {
        String key = acc.get(i);
        BFormat val = BFormat.make(acc.get(i+1));
        map.put(key, val);
      }      
      
      // done
      BNameMap x = (BNameMap) new BNameMap(map).intern();
      x.string = s;
      return x;
    }
    catch(Exception e)
    {               
      throw new IOException("Invalid BNameMap: " + s);
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
  public static final BNameMap NULL = new BNameMap(new HashMap<String, BFormat>());

  /**
   * The map is NULL.
   */
  public static final BNameMap DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNameMap.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
      
  private String string;
  private HashMap<String, BFormat> map;
  private int hashCode = -1;
}
