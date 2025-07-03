/**
 * Copyright 2014 - All Rights Reserved.
 */
package javax.baja.ui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.baja.data.BIDataValue;
import javax.baja.naming.SlotPath;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BDouble;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BNumber;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.ui.BWidget;
import com.tridium.json.JSONObject;
import com.tridium.json.JSONUtil;

/**
 * A class for holding a Web Property information.
 * 
 * @author Gareth Johnson
 */
public final class WebProperty
{
  public WebProperty(String name, 
                     boolean hidden,
                     boolean readonly,
                     boolean trans,
                     String typeSpec,
                     String encValue,
                     Object metadata)
  {
    this.name = name;
    this.hidden = hidden;
    this.readonly = readonly;
    this.trans = trans;
    this.typeSpec = typeSpec.length() == 0 ? "baja:String" : typeSpec;
    this.encValue = encValue;
    this.metadata = metadata;
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  public String getName() { return name; }
  
  public TypeInfo getTypeInfo()
  { 
    if (info == null)
      info = Sys.getRegistry().getType(this.typeSpec);

    return info;
  }
  
  public BValue getValue() 
      throws IOException
  { 
    if (value == null) value = decodeValue(this.encValue, getTypeInfo());
    return value;
  }
  
  public int getFlags()
  {
    int flags = webPropertySlotFlag;
    if (isHidden()) flags |= Flags.HIDDEN;
    if (isReadonly()) flags |= Flags.READONLY;
    if (isTransient()) flags |= Flags.TRANSIENT;
    return flags;
  }

  public BFacets getFacets()
  {
    return decodeMetadata(metadata);
  }
  
  public boolean isHidden() { return hidden; }
  public boolean isReadonly() { return readonly; }
  public boolean isTransient() { return trans; }
  
////////////////////////////////////////////////////////////////
// Property Synchronization
////////////////////////////////////////////////////////////////

  /**
   * Synchronize the Web Properties with the BWebWidget Properties.
   * @param widget
   * @param webProps The widget properties
   * @return the Widget Properties used for iteration
   */
  public static Map<String, Object> sync(BWidget widget, Iterable<WebProperty> webProps)
  {
    return sync( widget, webProps, false);
  }
  /**
   * Synchronize the Web Properties with the BWebWidget Properties and provide option for using the legacyJSONStructure.
   * @param widget
   * @param webProps The widget properties
   * @param legacyJSONStructure When communicating with a client whose's Niagara is 4.3 or less, make sure to set this to true.
   * @return the Widget Properties used for iteration
   */
  public static Map<String, Object> sync(BWidget widget, Iterable<WebProperty> webProps, boolean legacyJSONStructure)
  {
    ArrayList<String> toRemove = new ArrayList<>();
    Property[] nprops = widget.getPropertiesArray();
    for(Property p : nprops)
    {
      if (p.isDynamic() && 
          WebProperty.isWebProperty(widget, p) && 
          !widget.isOverriddenByBinding(p))
      {
        toRemove.add(p.getName());
      }
    }
    
    Map<String, Object> resp = null;
    
    for (WebProperty wp : webProps)
    {
      try
      {
        String name = SlotPath.escape(wp.getName());
        toRemove.remove(name);
        
        int flags = wp.getFlags();
        BFacets facets = wp.getFacets();

        Property p = widget.getProperty(name);
        if (p == null)
        {
          p = widget.add(SlotPath.escape(name),
                         wp.getValue(),
                         flags, facets,
                         null);
          
          resp = addToResp(resp, widget, p, legacyJSONStructure);
        }
        else if (widget.get(p).isSimple())
        {
          if (widget.getFlags(p) != flags)
            widget.setFlags(p, flags);
          
          if(!widget.getSlotFacets(p).equals(facets))
            widget.setFacets(p, facets);
          
          resp = addToResp(resp, widget, p, legacyJSONStructure);
        }
      }
      catch(IOException ex)
      {
        Logger.getLogger("webWidget")
              .log(Level.SEVERE, "Could not load Web Property", ex);
      }
    }

    // Remove any Properties that are no longer being used.
    toRemove.forEach(widget::remove);

    // Return the Widget Properties used for iteration
    return resp == null ? Collections.emptyMap() : resp;
  }
  
  /**
   * Add Web Properties To Response and provide option for using legacyJSONStructure.
   * @param resp
   * @param widget
   * @param prop
   * @param legacyJSONStructure When communicating with a client whose's Niagara is 4.3 or less, make sure to set this to true.
   * @return
   * @throws IOException
   */
  private static Map<String, Object> addToResp(Map<String, Object> resp, BWidget widget, Property prop, boolean legacyJSONStructure)
      throws IOException
  {
    if (resp == null) resp = new HashMap<>();
    if(legacyJSONStructure)
    {
      resp.put(SlotPath.unescape(prop.getName()), encodeValue(widget.get(prop))); //r43 version
    }
    else
    {
      resp.put(SlotPath.unescape(prop.getName()), encodeProperty(widget, prop)); //r44
    }
    return resp;
  }
  
  public static Property setProperty(BWidget widget, String name, String value)
  {
    Property prop = widget.getProperty(SlotPath.escape(name));
    
    // Bail the Property change under these conditions
    if (prop == null) return null;
    if (!WebProperty.isWebProperty(widget, prop)) return null;
    if (widget.isOverriddenByBinding(prop)) return prop;
    
    try
    {
      BValue val = WebProperty.decodeValue(value,
                                           widget.get(prop).getType().getTypeInfo());
      
      if (!widget.get(prop).equivalent(val))
        widget.set(prop, val);
    }
    catch(IOException ignore) {}
    
    return prop;
  }
  
  public static Property setFacets(BWidget widget, String name, Object metadata)
  {
    Property prop = widget.getProperty(SlotPath.escape(name));

    // Bail the Property change under these conditions
    if (prop == null) return null;
    if (!WebProperty.isWebProperty(widget, prop)) return null;
    if (widget.isOverriddenByBinding(prop)) return prop;
    
    BFacets facets = decodeMetadata(metadata);
    if (!widget.getSlotFacets(prop).equals(facets))
      widget.setFacets(prop, facets);
    
    return prop;
  }

  /**
   * Get the Property Changes for a widget.
   *
   * @param widget
   * @return the property changes
   * @throws IOException
   */
  public static Map<String, Object> getPropertyChanges(BWidget widget)
      throws IOException
  {
    return getPropertyChanges(widget, false);
  }

  /**
   * Get the Property Changes for a widget and provide option for using the legacyJSONStructure.
   * @param widget
   * @param legacyJSONStructure When communicating with a client whose's Niagara is 4.3 or less, make sure to set this to true.
   * @return
   * @throws IOException
   * @since Niagara 4.4
   */
  public static Map<String, Object> getPropertyChanges(BWidget widget, boolean legacyJSONStructure)
      throws IOException
  {
    try
    {
      return Arrays.stream(widget.getPropertiesArray())
        .filter(prop -> isWebProperty(widget, prop))
        .collect(Collectors.toMap(prop -> SlotPath.unescape(prop.getName()), prop -> {
          try
          {
            if(legacyJSONStructure)
            {
              return encodeValue(widget.get(prop));//r43 version
            }
            else
            {
              return encodeProperty(widget, prop); //r44 version
            }


          }
          catch(IOException e)
          {
            throw new RuntimeException(e);
          }
        }));
    }
    catch(RuntimeException e)
    {
      Throwable t = e.getCause();

      if (t != null && t instanceof IOException)
        throw (IOException)t;
      else
        throw e;
    }
  }
  
////////////////////////////////////////////////////////////////
// Encode/Decode
////////////////////////////////////////////////////////////////
  
  public static BValue decodeValue(String encValue, TypeInfo typeInfo)
      throws IOException
  {    
    // Decode via tag if this is an enum.
    if (typeInfo.is(BEnum.TYPE))
    {
      BEnum enm = (BEnum)typeInfo.getInstance();
      return enm.getRange().get(encValue);
    }
    else
    {
      return (BValue)typeInfo.getInstance()
                             .asSimple()
                             .decodeFromString(encValue);
    }
  }

  /**
   * Encodes a property's value and facets to a JSON object
   * @param w BWidget
   * @param prop Property to encode
   * @return JSONObject
   * @throws IOException
   */
  public static JSONObject encodeProperty(BWidget w, Property prop) throws IOException
  {
    JSONObject encoding = new JSONObject();
    BFacets facets = w.getSlotFacets(prop);
    
    encoding.put(valueAttr, encodeValue(w.get(prop)));
    
    if(!facets.isEmpty())
      encoding.put(metadataAttr, encodeFacets(facets));
    
    return encoding;
  }
  
  public static Object encodeValue(BValue value)
      throws IOException
  {
    if (value instanceof BInteger)
      return ((BNumber)value).getInt();
    else if (value instanceof BLong)
      return ((BNumber)value).getLong();
    else if (value instanceof BFloat)
      return ((BNumber)value).getFloat();
    else if (value instanceof BDouble)
      return ((BNumber)value).getDouble();
    else if (value instanceof BBoolean)
      return ((BBoolean)value).getBoolean();
    else if (value instanceof BEnum)
      return ((BEnum)value).getTag();
    else
      return value.asSimple().encodeToString();
  }

  /**
   * Encodes a BFacets object to it's corresponding JSONObject metadata representation
   * @param facets BFacets
   * @return JSONObject - A JSONObject representing a set of facet key/value pairs
   */
  public static JSONObject encodeFacets(BFacets facets)
  {
    JSONObject metadata = new JSONObject();
    Stream.of(facets.list()).forEach(key -> {
      try
      {
        BValue value = facets.get(key).asValue();
        String typeSpec = value.getType().getTypeSpec().encodeToString();
        JSONObject metaObj = new JSONObject();
        metaObj.put(typeSpecAttr, typeSpec).put(valueAttr, encodeValue(value));
        metadata.put(key, metaObj);
      }
      catch (IOException e)
      {
        log.log(Level.SEVERE, "Error encoding facets", e);
      }
    });
    
    return metadata;
  }


  /**
   * Decodes a metadata object to it's corresponding BFacets representation. The metadata provided
   * is expected to represent an iterable object of key/value pairs (typically a JSONObject)
   * @param metadata Object
   * @return BFacets - BFacets object decoded from the specified metadata
   */
  @SuppressWarnings("unchecked")
  public static BFacets decodeMetadata(Object metadata)
  {
    BFacets facets = BFacets.DEFAULT;
    ArrayList<String> keys = new ArrayList<>();
    ArrayList<BIDataValue> values = new ArrayList<>();

    //each key in the metadata object maps to an object containing a value/typespec pair
    if(metadata instanceof JSONObject)
    {
        JSONObject md = (JSONObject)metadata;
        md.keys().forEachRemaining(key -> {
        String name = key.toString();
        JSONObject valueObj = md.getJSONObject(name);
        String typeSpec = JSONUtil.getString(valueObj, typeSpecAttr);
        String encValue = JSONUtil.getString(valueObj, valueAttr);
        try
        {
          BValue value = decodeValue(encValue, Sys.getRegistry().getType(typeSpec));
          if(value instanceof BIDataValue)
          {
            keys.add(name);
            values.add((BIDataValue)value);
          }
        }
        catch (IOException e)
        {
          log.log(Level.SEVERE, "Error decoding metadata", e);
        }
      });

      facets = BFacets.make(keys.toArray(new String[0]), values.toArray(new BIDataValue[0]));
    }

    return facets;
  }
  
  public static boolean isWebProperty(BComplex clx, Property prop)
  {
    return (clx.getFlags(prop) & webPropertySlotFlag) != 0 &&
           clx.get(prop).isSimple();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  public static final int webPropertySlotFlag = Flags.USER_DEFINED_3;
  private static final Logger log = Logger.getLogger("bajaui");
  
  // Names used in JSON encoding of a Web Property
  public static final String nameAttr = "name";
  public static final String hiddenAttr = "hidden";
  public static final String readonlyAttr = "readonly";
  public static final String transientAttr = "transient";
  public static final String typeSpecAttr = "typeSpec";
  public static final String valueAttr = "value";
  public static final String metadataAttr = "metadata";
  
  private final String name;
  private final String typeSpec;
  private final String encValue;
  private final boolean hidden;
  private final boolean readonly;
  private final boolean trans;
  private final Object metadata;
  
  private BValue value;
  private TypeInfo info;
}
