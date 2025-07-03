/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.baja.agent.BAbstractPxView;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BPane;
import javax.baja.ui.util.WebProperty;
import javax.baja.util.BTypeSpec;
import javax.baja.xml.XWriter;

/**
 * PxEncoder is used to encode a Presentation XML
 * file which stores a widget tree and its bindings.
 *
 * @author    Brian Frank on 14 May 04
 * @version   $Revision: 21$ $Date: 6/8/10 1:12:09 PM EDT$
 * @since     Baja 1.0
 */
public class PxEncoder
  extends XWriter
{                        

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with File.
   */
  public PxEncoder(File file)
    throws IOException
  {
    super(file);
  }

  /**
   * Constructor with OutputStream.
   */
  public PxEncoder(OutputStream out)
    throws IOException     
  {
    super(out);
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////
  
  /**
   * Get the current value of the preserveIdentities flag.  
   * See setPreserveIdentities() for a full description.
   */
  public boolean getPreserveIdentities()
  {                              
    return preserveIdentities;
  }
  
  /**
   * Set the preserveIdentities flag which is false by
   * default.  If set to true, then encode the names and 
   * handles of all slots.  If false then only encode
   * names if needed and never handles.  Return this.
   */
  public PxEncoder setPreserveIdentities(boolean preserve)
  {
    this.preserveIdentities = preserve;              
    return this;
  }
  
  /**
   * Convenience for {@code encodeDocument(widget, null)}.
   */
  public void encodeDocument(BWidget widget)
    throws IOException
  {                         
    encodeDocument(widget, null, null);
  }

  /**
   * Convenience for {@code encodeDocument(widget, properties, null, view);}
   */
  public void encodeDocument(
    BWidget widget, 
    PxProperty[] properties,
    BAbstractPxView view)
    throws IOException
  {                                      
    encodeDocument(widget, properties, null, view);
  }

  /**
   * Encode a complete XML document which serializes the
   * widget.  If encoding a document being used as an AbstractPxView, 
   * the view's media type spec will also be encoded into the document.
   */
  public void encodeDocument(
    BWidget widget, 
    PxProperty[] properties,
    PxLayer[] layers,
    BAbstractPxView view)
    throws IOException
  {
    encodeDocumentWithMedia(widget, properties, layers,
      view != null ? view.getMedia() : null);
  }

  /**
   * Encode a complete XML document which serializes the
   * widget.  If encoding a document being used as an AbstractPxView,
   * the specified media type spec will be also encoded into the document.
   * @since Niagara 4.6
   */
  public void encodeDocumentWithMedia(
    BWidget widget,
    PxProperty[] properties,
    PxLayer[] layers,
    BTypeSpec media)
    throws IOException
  {
    w("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    w("<!-- Niagara Presentation XML -->\n");
    w("<px version=\"1.0\"");
    if (media != null) w(" media=\"").w(media).w("\"");
    w(">\n");
    encodeImport(widget);
    encodeProperties(widget, properties);
    encodeLayers(widget, layers);
    encodeContent(widget);
    w("</px>\n");
    flush();
  }

////////////////////////////////////////////////////////////////
// Import
////////////////////////////////////////////////////////////////
  
  /**
   * Encode the import section.
   */
  private void encodeImport(BWidget widget)
  {                                       
    HashMap<String, String> map = new HashMap<>();
    scanModules(map, widget); 
    String[] modules = map.keySet().toArray(new String[map.size()]);
    SortUtil.sort(modules);            
    
    w("<import>\n");
    for(int i=0; i<modules.length; ++i)
      w("  <module name=\"").w(modules[i]).w("\"/>\n");
    w("</import>\n");
  }                     
  
  /**
   * Recursively scan the widget tree to build
   * an all inclusive list of modules used.
   */
  private void scanModules(HashMap<String, String> map, BObject value)
  {                         
    String module = value.getType().getModule().getModuleName();
    map.put(module, "");
    if (value instanceof BComplex)
    {
      SlotCursor<Property> c = ((BComplex)value).getProperties();
      while(c.next())
        if (c.getTypeAccess() == Slot.BOBJECT_TYPE && !Flags.isRemoveOnClone((BComplex)value, c.property()))
          scanModules(map, c.get());
    }
  }

////////////////////////////////////////////////////////////////
// Properties
////////////////////////////////////////////////////////////////

  /**
   * Encode the properties section.
   */
  private void encodeProperties(BWidget root, PxProperty[] props)
    throws IOException
  {                                       
    // skip section if no properties defined
    if (props == null) return;
    if (props.length == 0) return;
    
    w("<properties>\n\n");
    for (int i = 0; i < props.length; i++)
    {
      PxProperty prop = props[i];

      w("  <property");
      w(" name=\"").w(prop.getName()).w("\"");
      w(" type=\"").w(prop.getTypeSpec().toString()).w("\"");
      w(" value=\"").w(((BSimple)prop.getValue()).encodeToString()).w("\"");
      w(">\n");

      SlotPath[] targets = prop.getTargets();
      for (int j=0; j<targets.length; j++)
      {
        w("    <target");
        w(" ord=\"").w(targets[j]).w("\"");
        w("/>\n");
      }

      w("  </property>\n\n");
    }
    w("</properties>\n");
  }                     

////////////////////////////////////////////////////////////////
// Layers
////////////////////////////////////////////////////////////////

  /**
   * Encode the layers section.
   */
  private void encodeLayers(BWidget root, PxLayer[] layers)
  throws IOException
  {                                       
    // skip section if no layers defined
    if (layers == null) return;
    if (layers.length == 0) return;
    
    w("<layers>\n");
    for (int i = 0; i < layers.length; i++)
    {
      PxLayer layer = layers[i];

      w("  <layer");
      w(" name=\"").w(layer.getName()).w("\"");
      w(" status=\"").w(layer.getStatus().encodeToString()).w("\"");
      w("/>\n");
    }
    w("</layers>\n");
  }                     

////////////////////////////////////////////////////////////////
// Content
////////////////////////////////////////////////////////////////
  
  /**
   * Encode the content section.
   */
  private void encodeContent(BWidget widget)
    throws IOException
  {
    w("<content>\n");    
    encodeToElem(widget, null, widget, 0);
    w("</content>\n");
  }                          
  
  /**
   * Encode a value as an element.
   */
  private void encodeToElem(BComplex clx, Property prop, BValue v, int indent)
    throws IOException
  {                 
    // encode <type
    String type = v.getType().getTypeName();
    indent(indent).w("<").w(type);
    
    // if a frozen property or a non-component we need to encode name
    if (prop != null && (prop.isFrozen() || !v.isComponent() || preserveIdentities))
      w(" name=\"").w(prop.getName()).w("\"");                  
    
    if (preserveIdentities && v.isComponent() && v.asComponent().getHandle() != null)
      w(" h=\"").w(v.asComponent().getHandle()).w("\"");   
    
    // Encode the flags and facets of a bajaux Property
    if (prop != null && WebProperty.isWebProperty(clx, prop))
    {
      int flags = clx.getFlags(prop);
      w(" f=\"").safe(Flags.encodeToString(flags)).w("\"");
      
      BFacets facets = clx.getSlotFacets(prop);
      if(!facets.isEmpty())
        w(" ft=\"").safe(facets.encodeToString()).w("\"");
    }

    // encode simple/complex data
    if (v.isSimple())
    {                                  
      BSimple simple = (BSimple)v;
      w(" value=\"").safe(simple.encodeToString()).w("\"/>\n");
    }
    else
    {                
      boolean emptyElem = encodeProps((BComplex)v, indent+2);
      if (emptyElem) w("/>\n");
      else indent(indent).w("</").w(type).w(">\n");
    }
  }          
  
  /**
   * Encode the child properties of a complex.
   */
  private boolean encodeProps(BComplex c, int indent)
    throws IOException
  {                 
    Property[] pa = c.getPropertiesArray();

    // encode attributes
    for (int i = 0; i < pa.length; i++)
    {
      Property prop = pa[i];
      
      if (Flags.isRemoveOnClone(c, prop))
        continue;
      
      BValue value = c.get(prop);

      if (encodeAs(c, prop, value) == ATTRIB)
      {
        w(' ').attr(
          prop.getName(), 
          value.asSimple().encodeToString());
      }
    }
    
    // encode sub elements
    boolean emptyElem = true;  
    boolean extraWhitespace = c instanceof BPane;

    for (int i = 0; i < pa.length; i++)
    {
      Property prop = pa[i];
      
      if (Flags.isRemoveOnClone(c, prop))
        continue;
      
      BValue value = c.get(prop);

      if (encodeAs(c, prop, value) == SUBELEM)
      {
        // if this is the first child element, then
        // close the parent's start tag
        if (emptyElem)
        { 
          w(">\n");
          if (extraWhitespace) w("\n"); 
          emptyElem = false;
        }                           

        // encode the child as its own element
        encodeToElem(c, prop, value, indent);         
        if (extraWhitespace) w("\n");
      }
    }       

    return emptyElem;
  }

  /**
   * Return one of NEVER, ATTRIB, or SUBELEM.
   */
  private int encodeAs(BComplex c, Property prop, BValue propVal)
  {    
    // frozen property
    if (prop.isFrozen()) 
    {
      // never encode transients, default values, or binding overrides
      if (Flags.isTransient(c, prop)) return NEVER;
      if (WebProperty.isWebProperty(c, prop)) return SUBELEM;
      if (prop.isEquivalentToDefaultValue(propVal)) return NEVER;
      if (c instanceof BWidget && ((BWidget)c).isOverriddenByBinding(prop))
        return NEVER;
    }
    // dynamic property
    else
    {
      // never encode transients
      if (Flags.isTransient(c, prop))
        return NEVER;

      // PxIncludes are a special case
      if (c instanceof BPxInclude)
      {
        BPxInclude px = (BPxInclude) c;

        // never encode the root
        if (propVal == px.root) return NEVER;
      }
      // anything else
      else
      {
        if (WebProperty.isWebProperty(c, prop))
          return SUBELEM;
      }
    }

    // ATTRIBS are frozen, non-abstract, and simple.
    // SUBELEMS are anything else.
    return (prop.isFrozen() &&               
        !prop.getType().isAbstract() &&  
        propVal.isSimple()) ? 
      ATTRIB : SUBELEM;               
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final int NEVER   = 0;
  private static final int ATTRIB  = 1;
  private static final int SUBELEM = 2;

  private boolean preserveIdentities; 
}
