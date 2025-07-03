/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.file.BIFile;
import javax.baja.gx.BBrush;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BSimple;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.TypeNotFoundException;
import javax.baja.ui.BWidget;
import javax.baja.util.BTypeSpec;
import javax.baja.xml.XElem;
import javax.baja.xml.XException;
import javax.baja.xml.XParser;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * PxDecoder decodes a Presentation XML file into 
 * its widget tree with associated bindings.
 *
 * @author    Brian Frank
 * @creation  14 May 04
 * @version   $Revision: 22$ $Date: 6/8/10 1:11:57 PM EDT$
 * @since     Baja 3.0
 */
public class PxDecoder 
  extends XParser
{
                                             
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a PxEncoder to read from the specified file.
   * 
   * @param file
   */
  public PxDecoder(BIFile file)
    throws Exception
  {
    this(file, null);
  }

  /**
   * Construct a PxEncoder to read from the specified file and Context.
   * <p>
   * The Context is passed in primarily due to PxIncludes. PxIncludes are unique
   * because they don't have any associated bindings to use for checking
   * file load security permissions.
   * 
   * @param file
   * @param cx
   */
  public PxDecoder(BIFile file, Context cx)
    throws Exception
  {
    this(file.getAbsoluteOrd(), new BufferedInputStream(file.getInputStream()), cx);
  }

  /**
   * Construct an PxDecoder from the given input stream.
   * 
   * @param baseOrd
   * @param in
   */
  public PxDecoder(BOrd baseOrd, InputStream in)
    throws Exception
  {             
    this(baseOrd, in, null);
  }
  
  /**
   * Construct an PxDecoder from the given input stream and Context.
   * <p>
   * The Context is passed in primarily due to PxIncludes. PxIncludes are unique
   * because they don't have any associated bindings to use for checking
   * file load security permissions.
   * 
   * @param baseOrd
   * @param in
   * @param cx
   */
  public PxDecoder(BOrd baseOrd, InputStream in, Context cx)
    throws Exception
  {             
    super(in);     
    this.baseOrd = baseOrd;
    this.cx = cx;
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>decodeDocument(true)</code>.
   */
  public BWidget decodeDocument()
    throws Exception
  {                            
    return decodeDocument(true);
  }

  /**
   * Decode the XML document into a BWidget, and
   * optionally close the input stream.
   */
  public BWidget decodeDocument(boolean close)
    throws Exception
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Starting decode of px file " + baseOrd);
    }
    long start = System.currentTimeMillis();
    try
    {
      // parse into memory
      XElem root = parse();

      decodeHeader(root);
      
      // decode imports
      decodeImport(root);
      
      // decode layers
      decodeLayers(root);
      
      // decode content
      BWidget rootWidget = decodeContent(root);

      // decode properties
      decodeProperties(root, rootWidget);

      // return widget
      return rootWidget;
    }
    finally
    {
      long end = System.currentTimeMillis();
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Decoded " + baseOrd + " in " + (end - start) + "ms");
      }
      if (close) close();
    }
  }
  
  /**
   * Get the PxMedia of the decoded px document or null if
   * not specified.
   */
  public TypeInfo getMedia()
  {                
    return media;
  }

  /**
   * Get the modules which the PX file is dependant  on.
   */
  public String[] getModules(){return modules;}

////////////////////////////////////////////////////////////////
// Import
////////////////////////////////////////////////////////////////

  /**
   * Decode the import section.
   */
  protected void decodeHeader(XElem root)
    throws Exception
  {
    // the root element name must be "px"
    if (!root.name().equals("px"))
      throw err("Root element must be \"px\"", root);

    // we only support 1.0 now
    String ver = root.get("version");
    if (!ver.equals("1.0"))
      throw err("Only version 1.0 is supported", root);

    // get media
    String media = root.get("media", null);
    if (media != null)
    {
      try
      {
        this.media = Sys.getRegistry().getType(media);
      }
      catch(TypeNotFoundException e)
      {
        warning("Media not found: " + media, root);
      }
    }
  }
    /**
     * Decode the import section.
     */
  protected void decodeImport(XElem root)
    throws Exception
  {                    
    // get import element
    XElem elem = root.elem("import");
    if (elem == null) throw err("Missing <import> element", root);
    
    // map module elements into module name list
    XElem[] moduleElems = elem.elems("module");
    modules = new String[moduleElems.length];
    for(int i=0; i<moduleElems.length; ++i)
      modules[i] = moduleElems[i].get("name");
  }

////////////////////////////////////////////////////////////////
// Properties
////////////////////////////////////////////////////////////////
  
  /**
   * Decode the properties section.
   */
  private void decodeProperties(XElem root, BWidget rootWidget)
    throws Exception
  {                    
    XElem elem = root.elem("properties");
    
    // short ciruit if we have no properties
    if (elem == null) 
    {
      props = new PxProperty[0];
      return;
    }
    
    // collect our properties
    XElem[] propElems = elem.elems("property");    
    props = new PxProperty[propElems.length];
    
    for (int i=0; i<propElems.length; i++)
    {
      // name, type, value
      String name = propElems[i].get("name");
      BTypeSpec type = BTypeSpec.make(propElems[i].get("type"));
      BValue value = decodeSimple(
        (BValue)type.getResolvedType().getInstance(), 
        propElems[i].get("value"));

      // targets
      XElem[] targElems = propElems[i].elems("target");    
      SlotPath[] targets = new SlotPath[targElems.length];
      for (int j=0; j<targElems.length; j++)
      {
        String path = targElems[j].get("ord");
        targets[j] = new SlotPath(path.substring("slot:".length()));
      }
      
      // make property
      props[i] = new PxProperty(name, type, value, targets);
    }
  }

////////////////////////////////////////////////////////////////
// Layers
////////////////////////////////////////////////////////////////
  
  /**
   * Decode the layers section.
   */
  protected void decodeLayers(XElem root)
    throws Exception
  {                    
    XElem layRoot = root.elem("layers");
    
    // short ciruit if we have no layers
    if (layRoot == null) 
    {
      layers = new PxLayer[0];
      return;
    }
    
    // collect our layers
    XElem[] elems = layRoot.elems("layer");    
    layers = new PxLayer[elems.length];
    
    for (int i=0; i<elems.length; i++)
    {
      String name = elems[i].get("name");
      BLayerStatus status = BLayerStatus.make(elems[i].get("status"));
      layers[i] = new PxLayer(name, status);
    }
  }

////////////////////////////////////////////////////////////////
// Content
////////////////////////////////////////////////////////////////
  
  /**
   * Decode the content section.
   */
  private BWidget decodeContent(XElem root)
    throws Exception
  {
    XElem widgetElem = getContentElem(root);
    
    // decode into value
    BValue value = decodeFromElem(widgetElem);
    if ((value == null) || (value instanceof BWidget)) // issue 11846
      return (BWidget)value;
    else
      throw err("Content type " + value.getType() + " is not a Widget", widgetElem);
  }

  protected XElem getContentElem(XElem root)
  {
    // get content element
    XElem elem = root.elem("content");
    if (elem == null) throw err("Missing <content> element", root);

    // get widget element
    XElem[] subElems = elem.elems();
    if (subElems.length != 1)
      throw err("There must be exactly one child element under <content>", elem);

    return subElems[0];
  }

  /**
   * Decode a BValue from the specified element.
   */
  private BValue decodeFromElem(XElem x)
    throws Exception
  {                             
    // map element name to a type
    TypeInfo type = toType(x);
    
    // get instance of the type
    BValue value = null;
    try       
    {
      value = (BValue)type.getInstance();  
      
      // if the value is a bajaui:PxInclude, then we need to
      // make sure that baseOrd gets propogated and any Context is set.
      if (value instanceof BPxInclude)
        value.fw(Fw.SET_BASE_ORD, baseOrd, cx, null, null);
    }
    catch(Throwable e)
    {
      throw err("Cannot create instance of " + type, x, e);
    }
    
    // if simple then decode
    if (value instanceof BSimple)
    { 
      String string = x.get("value");
      value = decodeSimple(value, string);
    }
    
    // otherwise we need to decode the attributes
    // and child elements into properties    
    else
    { 
      decodeProps(x, (BComplex)value);                                       
    }            
    
    return value;
  }          
  
  /**
   * Decode the attributes and child elements 
   * as properties on the specified complex.
   */
  private void decodeProps(XElem x, BComplex c)
    throws Exception
  {               
    String type = c.getType().getTypeName();

    // decode attributes as simples
    for(int i=0; i<x.attrSize(); ++i)
    {
      String name = x.attrName(i);       
      try
      {
        Property prop = c.getProperty(name);
        if (prop == null) 
        {
          if (name.equals("h") && c.isComponent())
            ((ComponentSlotMap)c.fw(Fw.SLOT_MAP)).setHandle(x.attrValue(i));
          continue;
        }
        BValue value = c.get(prop);
        if (!value.isSimple()) throw err("Expecting simple prop: " + name, x);
        value = decodeSimple(value, x.attrValue(i));
        c.set(prop, value);
      }
      catch(XException e)
      {
        throw e;
      }         
      catch(Exception e)
      {
        throw err("Cannot decode " + type + "." + name, x, e);
      }
    }                       
      
    // decode child elements
    XElem[] kids = x.elems();
    for(int i=0; i<kids.length; ++i)
    {                
      XElem kid = kids[i];             
      String name = kid.get("name", null);
      BValue value = decodeFromElem(kid);
      try
      {
        if (name != null && c.getProperty(name) != null)
        {
          c.set(name, value);
        }
        else
        {
          c.asComponent().add(name, value);
        }
        
        String flags = kid.get("f", null);
        String facets = kid.get("ft", null);
        
        if (flags != null)
         c.setFlags(c.getProperty(name), Flags.decodeFromString(flags));
        
        if(facets != null && !facets.isEmpty())
          c.setFacets(c.getProperty(name), (BFacets)BFacets.DEFAULT.decodeFromString(facets));
        
      }
      catch(XException e)
      {
        throw e;
      }         
      catch(Exception e)
      {
        throw err("Cannot decode " + type + "." + name, kid, e);
      }
    }         
  }
  
  /**
   * Map the element name to a type using import modules.
   */
  protected TypeInfo toType(XElem x)
  {                                       
    // first check cache            
    String typeName = x.name();
    TypeInfo type = types.get(typeName);
    if (type != null) return type;
    
    // walk modules looking for first match
    for(int i=0; i<modules.length; ++i)
    {         
      try
      {                             
        type = Sys.getRegistry().getType(modules[i] + ":" + typeName);
        types.put(typeName, type);
        return type;
      }
      catch(Exception e)
      {
      }
    }
    
    throw err("Unknown type " + typeName, x);
  }        
  
  /**
   * Decode a simple.
   */
  private BSimple decodeSimple(BValue proto, String str)
   throws Exception
  {
    BSimple val = (BSimple)((BSimple)proto).decodeFromString(str);
    
    // if the simple is an Image or something that contains
    // Images (Brush, Border, EnumToSimpleMap), then we
    // need to make sure that baseOrd gets propogated
    val.fw(Fw.SET_BASE_ORD, baseOrd, cx, null, null);

    return val;
  }                   

  /**
   * loadBrush
   */
  private void loadBrush(BBrush brush)
  {
    BBrush.Paint p = brush.getPaint();
    if (p instanceof BBrush.Image)
    {
      BImage img = ((BBrush.Image) p).getImage();
      img.setBaseOrd(baseOrd);
    }
  }
  
////////////////////////////////////////////////////////////////
// Error
////////////////////////////////////////////////////////////////

  protected XException err(String msg, XElem elem, Throwable cause)
  {
    return new XException(msg, elem, cause);
  }
  
  protected XException err(String msg, XElem elem)
  {
    return new XException(msg, elem);
  }

  protected void warning(String msg, XElem elem)
  {                                               
    String line = "";
    if (elem != null) line = " [line " + elem.line() + "]";
    System.out.println("WARNING: " + msg + line);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * return a shallow copy of the PxProperties
   */
  public PxProperty[] getPxProperties()
  {
    return (new Array<>(props)).trim();
  }
  
  /**
   * return a shallow copy of the PxLayers
   */
  public PxLayer[] getPxLayers()
  {
    return (new Array<>(layers)).trim();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected String[] modules;
  protected PxProperty[] props;
  protected PxLayer[] layers;
  protected HashMap<String, TypeInfo> types = new HashMap<>();
  protected BOrd baseOrd;
  protected TypeInfo media;
  protected Context cx;

  protected Logger log = Logger.getLogger("px.decode");
}
