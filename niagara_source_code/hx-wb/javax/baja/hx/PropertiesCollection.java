/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import javax.baja.io.HtmlWriter;
import javax.baja.sys.BajaRuntimeException;

/**
 * This is a collection of name value pairs that can be
 * easily written to a javascript friendly string.
 */
public abstract class PropertiesCollection
{
  LinkedHashMap<String, String> properties = new LinkedHashMap<>();
  HashSet<String> unsafeProperties = new LinkedHashSet<String>();
  
  public PropertiesCollection() 
  { }

  public void add(String name, String value)
  {
    if(!value.equals(properties.get(name)))
      properties.put(name, value);
  }
  

  /**
   * When adding a dom property, the value will NOT be escaped or quoted.
   * @param name
   * @param value
   */
  public void addUnsafe(String name, String value)
  {
    if(!value.equals(properties.get(name)))
      properties.put(name, value);

    unsafeProperties.add(name);
  }

  /**
   * Turn snoop on or off for this PropertiesCollection.
   * @param snoop
   * @since 4.3U1
   */
  public void setSnoopEnabled(boolean snoop)
  {
    this.snoop = snoop;
  }
  
  public void add(PropertiesCollection add)
  {
    Object[] keys = add.properties.keySet().toArray();
    for(int i=0; i<keys.length; i++)
      add((String)keys[i], add.properties.get(keys[i]));
  }
  
  public String get(String name)
  {
    return properties.get(name);
  }
  
  public void append(String name, String value)
  {
    if(properties.containsKey(name))
      properties.put(name, properties.get(name)+" "+value);
    else
      add(name, value);
  }

  public void appendUnsafe(String name, String value)
  {
    if(properties.containsKey(name))
    {
      properties.put(name, properties.get(name) + " " + value);
      unsafeProperties.add(name);
    }
    else
      addUnsafe(name, value);
  }
  
  /**
   * Convenience for write(op.getPath(), op);
   */
  public void write(HxOp op)
    throws IOException
  {
    write(op.getPath(), op);
  }

  public void write(String path, HxOp op)
    throws IOException
  {
    if(properties.isEmpty())
      return;
  
    HtmlWriter out = op.getHtmlWriter();
    if(!snoop) { out.w("/* @noSnoop */"); }
    out.w("px.setProperties('").w(path).w("',").w(doWrite()).w(");\n");
  }  
    
  protected String doWrite()
  {
    StringBuilder out = new StringBuilder();
    out.append("[");

    Object[] keys = properties.keySet().toArray();
    for(int i=0; i<keys.length; i++)
    {
      try
      {
        if (i > 0) out.append(",");
        String key = (String)keys[i];
        String value = properties.get(key);
        boolean decode = false;
        if (unsafeProperties.contains(key))
        {
          value = URLEncoder.encode(value, "UTF-8")
            .replace("+", "%20");
          decode = true;
        }
        else
        {
          value = HxUtil.encodeText(value);
        }
        out
          .append("{")
          .append("\"key\":\"").append(key).append("\",")
          .append("\"value\":\"").append(value).append("\",")
          .append("\"decode\":").append(decode)
          .append("}");

      } catch (Exception e) {
        throw new BajaRuntimeException("Unable to encode HxPx property value.", e);
      }
    }
    out.append("]");
    return out.toString();
  }
  
  public String toString()
  {
    StringBuilder out = new StringBuilder();

    Object[] keys = properties.keySet().toArray();
    for(int i=0; i<keys.length; i++)
    {
      try
      {
        String key = (String)keys[i];
        out.append(key).append(": ").append(HxUtil.encodeText(properties.get(key))).append(";");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return out.toString();
  }  

  public static class Properties
    extends PropertiesCollection
  {  
  }  
  
  public static class Events
    extends PropertiesCollection
  {
    public void write(String path ,HxOp op)
      throws IOException
    {
      if(properties.isEmpty())
        return;

      HtmlWriter out = op.getHtmlWriter();
      if(!snoop) { out.w("/* @noSnoop */"); }
      out.w("px.setEvents('").w(path).w("',").w(doWrite()).w(");\n");
    }    
  }
  
  public static class Styles
    extends PropertiesCollection
  {
    public void write(String path ,HxOp op)
      throws IOException
    {
      if(properties.isEmpty())
        return;

      HtmlWriter out = op.getHtmlWriter();
      if(!snoop) { out.w("/* @noSnoop */"); }
      out.w("px.setStyle('").w(path).w("',").w(doWrite()).w(");\n");
    }    
  }

  boolean snoop=true;
}
