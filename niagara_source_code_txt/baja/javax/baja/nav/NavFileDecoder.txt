/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import java.io.*;
import java.util.*;
import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.file.*;
import javax.baja.xml.*;

/**
 * NavFileDecoder decodes a nav XML file into a tree of BNavFileNodes.
 * The format of a nav file is a root element called "nav" with a 
 * version attribute of "1.0".  A tree of nodes is defined using the
 * "node" element with "name", "ord", and "icon" attributes.
 *
 * @author    Brian Frank
 * @creation  1 Sept 04
 * @version   $Revision: 6$ $Date: 3/28/05 9:23:02 AM EST$
 * @since     Baja 3.0
 */
public class NavFileDecoder 
  extends XParser
{

////////////////////////////////////////////////////////////////
// Cache
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for <code>get((BIFile)ord.get())</code>.
   */
  public static BNavFileSpace load(BOrd ord)
    throws Exception
  {                      
    BObject file = ord.get();
    if (file instanceof BIFile)
      return load((BIFile)file);
    else
      throw new ClassCastException("Not IFile: " + ord + " -> " + file.getType());
  }
  
  /**
   * This method is used to access NavFile's as parsed  NavFileSpaces 
   * via a cache.  If the ord hasn't been loaded yet or the file
   * has changed since last loaded it is parsed, otherwise return the 
   * cached nav tree.
   */
  public static BNavFileSpace load(BIFile navFile)
    throws Exception
  {   
    String key = navFile.getAbsoluteOrd().toString();                   
    CacheItem c = cache.get(key);
    if (c == null || c.isOutOfDate())
    {   
      c = new CacheItem();                       
      c.file = navFile;
      c.lastModified = navFile.getLastModified().getMillis();
      c.space = new NavFileDecoder(navFile).decodeDocument(); 
      cache.put(key, c);
    }
    return c.space;
  }                      
  
  static Map<String, CacheItem> cache = new HashMap<>();
  
  static class CacheItem
  {                  
    boolean isOutOfDate() { return lastModified != file.getLastModified().getMillis(); }     
    
    BIFile file;        
    long lastModified;
    BNavFileSpace space;
  }
                                             
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a NavFileDecoder to read from the specified file.
   */
  public NavFileDecoder(BIFile file)
    throws Exception
  {
    this(file.getSession().getAbsoluteOrd(), new BufferedInputStream(file.getInputStream()));
  }

  /**
   * Construct an NavFileDecoder from the given input stream.
   */
  public NavFileDecoder(BOrd baseOrd, InputStream in)
    throws Exception
  {             
    super(in);     
    this.baseOrd = baseOrd;
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>decodeDocument(true)</code>.
   */
  public BNavFileSpace decodeDocument()
    throws Exception
  {                            
    return decodeDocument(true);
  }

  /**
   * Decode the XML document into a BNavFileSpace which contains
   * a BNavFileNode tree. Optionally close the input stream.
   */
  public BNavFileSpace decodeDocument(boolean close)
    throws Exception
  {
    try
    {
      // parse into memory
      XElem root = parse();
  
      // the root element name must be "nav"
      if (!root.name().equals("nav"))
        throw err("Root element must be \"nav\"", root);
        
      // we only support 1.0 now
      String ver = root.get("version");
      if (!ver.equals("1.0"))
        throw err("Only version 1.0 is supported", root);
      
      // decode root node
      XElem node = root.elem("node");
      if (node == null)
        throw err("Missing root <node>", root);
        
      return new BNavFileSpace(decodeNode(node));
    }
    finally
    {
      if (close) close();
    }
  }

////////////////////////////////////////////////////////////////
// Content
////////////////////////////////////////////////////////////////
  
  /**
   * Recursively decode the XML element into a BFileNavNode.
   */
  private BNavFileNode decodeNode(XElem xml)
    throws Exception
  {                  
    // get attributes
    String name    = null;
    String ordStr  = null;
    String iconStr = null;
    for(int i=0; i<xml.attrSize(); ++i)
    {
      String attrName = xml.attrName(i);
      String attrValue = xml.attrValue(i);
      if (attrName.equals("name"))      name    = attrValue;
      else if (attrName.equals("ord"))  ordStr  = attrValue;
      else if (attrName.equals("icon")) iconStr = attrValue;
    }
    
    // build normalized ord
    BOrd ord = BOrd.make(baseOrd, ordStr);
    ord = ord.normalize();

    // build normalized icon
    BIcon icon = null;
    if (iconStr != null) 
    {
      if (iconStr.startsWith("module:"))
        icon = BIcon.make(iconStr);
      else
        icon = BIcon.make(BOrd.make(baseOrd, iconStr).normalize());
    }
    
    // build node                       
    BNavFileNode node = new BNavFileNode(name, ord, icon);                      
  
    // decode child nodes
    XElem[] kids = xml.elems("node");
    for(int i=0; i<kids.length; ++i)
      node.addNavChild(decodeNode(kids[i]));
    
    // all done  
    return node;
  }

////////////////////////////////////////////////////////////////
// Error
////////////////////////////////////////////////////////////////

  XException err(String msg, XElem elem, Throwable cause)
  {
    return new XException(msg, elem, cause);
  }
  
  XException err(String msg, XElem elem)
  {
    return new XException(msg, elem);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BOrd baseOrd;

}
