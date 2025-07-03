/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import javax.baja.category.BCategoryMask;
import javax.baja.file.BIFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.security.BIPasswordValidator;
import javax.baja.security.BPassword;
import javax.baja.security.PasswordEncodingContext;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BModule;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.ModuleException;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeNotFoundException;
import javax.baja.util.Version;
import javax.baja.xml.XElem;
import javax.baja.xml.XException;
import javax.baja.xml.XParser;
import com.tridium.nre.security.EncryptionKeySource;
import com.tridium.nre.security.SecretChars;
import com.tridium.nre.security.io.BogPasswordObjectEncoder;
import com.tridium.nre.util.IElement;
import com.tridium.sys.Nre;
import com.tridium.sys.module.NModule;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.util.SimpleFactory;

/**
 * ValueDocDecoder creates an in-memory graph of BObjects from
 * serialized document representation.
 * <p>
 * The format of the document can vary (XMLB and JSON) by implementing
 * IDecoderPlugin
 * <p>
 * This class is designed to supersede BogDecoder which is constrained to just
 * decoding BOG XML
 *
 * @see IDecoderPlugin
 * @see ValueDocEncoder
 *
 * @author    Gareth Johnson
 * creation  07 Jan 11
 * @since     Niagara 3.7
 */
public class ValueDocDecoder
  implements AutoCloseable
{

////////////////////////////////////////////////////////////////
// Unmarshal
////////////////////////////////////////////////////////////////

  /**
   * Unmarshal the a value from an XML string.
   * The XML is not a full XML document, but rather a
   * single element - see decode().
   */
  public static BValue unmarshal(String xml)
    throws Exception
  {
    return BogDecoderPlugin.unmarshal(xml);
  }

  /**
   * Unmarshal the a value from an XML string.
   * The XML is not a full XML document, but rather a
   * single element - see decode().
   * 
   * @since Niagara 4.0
   */
  public static BValue unmarshal(String xml, Context cx)
    throws Exception
  {
    return BogDecoderPlugin.unmarshal(xml, cx);
  }
  
  /**
   * Unmarshal the a value from an XML string.
   * The XML is not a full XML document, but rather a
   * single element - see decode().  This method also
   * has a ITypeResolver parameter which specifies an alternate
   * way to resolve types during the decode process.
   *
   * @see ITypeResolver
   */
  public static BValue unmarshal(String xml, ITypeResolver typeResolver)
    throws Exception
  {
    return BogDecoderPlugin.unmarshal(xml, typeResolver);
  }

  /**
   * Unmarshal the a value from an XML string.
   * The XML is not a full XML document, but rather a
   * single element - see decode().  This method also
   * has a ITypeResolver parameter which specifies an alternate
   * way to resolve types during the decode process.
   *
   * @see ITypeResolver
   * @since Niagara 4.0
   */
  public static BValue unmarshal(String xml, ITypeResolver typeResolver, Context cx)
    throws Exception
  {
    return BogDecoderPlugin.unmarshal(xml, typeResolver, cx);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a decoder with the given decoder plug-in
   */
  public ValueDocDecoder(IDecoderPlugin plugin)
    throws Exception
  {
    this(plugin, null);
  }

  /**
   * Construct a decoder with the given decoder plug-in and context
   *
   * @since Niagara 4.0
   */
  public ValueDocDecoder(IDecoderPlugin plugin, Context context)
    throws Exception
  {
    this.plugin = plugin;
    this.context = context;
  }

  /**
   * Construct a BOG XML Decoder to read from a BIFile
   * resolved by the given BOrd.
   */
  public ValueDocDecoder(BOrd ord)
    throws Exception
  {
    this(new BogDecoderPlugin(ord), null);
  }

  /**
   * Construct a BOG XML Decoder to read from the specified file.
   */
  public ValueDocDecoder(BIFile file)
    throws Exception
  {
    this(new BogDecoderPlugin(file), null);
  }

  /**
   * Construct a BOG XML Decoder to read from the specified file.
   * @since Niagara 4.0
   */
  public ValueDocDecoder(BIFile file, Context context)
    throws Exception
  {
    this(new BogDecoderPlugin(file), context);
  }

  /**
   * Construct a BOG XML Decoder to read from the specified file.
   */
  public ValueDocDecoder(File file)
    throws Exception
  {
    this(new BogDecoderPlugin(file), null);
  }

  /**
   * Construct a BOG XML Decoder from the given input stream.
   */
  public ValueDocDecoder(InputStream in)
    throws Exception
  {
    this(in, null);
  }

  /**
   * Construct a BOG XML Decoder from the given input stream.
   */
  public ValueDocDecoder(InputStream in, Context context)
    throws Exception
  {
    this(new BogDecoderPlugin(in), context);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Returns the TypeResolver instance used to take String attribute
   * elements parsed from the bog file and determine how to
   * resolve the module/type.
   */
  public final ITypeResolver getTypeResolver()
  {
    return plugin.getTypeResolver();
  }

  /**
   * Set the type resolver.
   */
  public final void setTypeResolver(ITypeResolver typeResolver)
  {
    plugin.setTypeResolver(typeResolver);
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>decodeDocument(true)</code>.
   */
  public BValue decodeDocument()
    throws Exception
  {
    return decodeDocument(true);
  }

  /**
   * Decode the document into a BValue.
   */
  public BValue decodeDocument(boolean close)
    throws Exception
  {
    try
    {
      return plugin.decodeDocument(this);
    }
    finally
    {
      if (close) plugin.close();
    }
  }

  /**
   * Decode the current element into a BValue.  The
   * parser must currently be positioned on a "p" element.
   * When this method completes it is positioned on the
   * node following the "p" element.
   */
  public BValue decode()
    throws Exception
  {
    return parseSlot(null, true);
  }

  /**
   * @return the decoder plug-in
   */
  public final IDecoderPlugin getPlugin()
  {
    return plugin;
  }

  /**
   * @return the current element
   */
  public final IElement elem()
  {
    return plugin.elem();
  }

  /**
   * Advance the parser to the next node and return the node type.
   * If no more data to parse then return EOF.
   */
  public final int next() throws Exception
  {
    return plugin.next();
  }

  /**
   * Convenience for <code>skip(depth())</code>.
   */
  public final void skip() throws Exception
  {
    plugin.skip();
  }

  /**
   * Skip parses all the content until reaching the end tag
   * of the specified depth.  When this method returns, the
   * next call to <code>next()</code> will return the element
   * or text immediately following the end tag.
   */
  public final void skip(int depth) throws Exception
  {
    plugin.skip(depth);
  }

  /**
   * Get the current node type constant which is always the
   * result of the last call to next().  This constant may be
   * ELEM_START, ELEM_END, TEXT, or EOF.
   */
  public final int type()
  {
    return plugin.type();
  }

  /**
   * @return the line number the decoder is at
   */
  public final int line()
  {
    return plugin.line();
  }

  /**
   * @return the column number the decoder is at
   */
  public final int column()
  {
    return plugin.column();
  }

  /**
   * Close the decoder
   */
  @Override
  public final void close()
  {
    plugin.close();
  }

  /**
   * Get the depth of the current element with the document
   * root being a depth of one.  A depth of 0 indicates
   * a position before or after the root element.
   */
  public final int depth()
  {
    return plugin.depth();
  }

  /**
   * Get the character encoding of the underlying input stream.
   */
  public final String getEncoding()
    throws IOException
  {
    return plugin.getEncoding();
  }

  /**
   * Get the bog version.  Supported versions are:
   * <ul>
   * <li>1.0 - original implementation</li>
   * <li>4.0 - Niagara 4.0</li>
   * </ul>
   * @return the {@link javax.baja.util.Version} used in this bog.
   */
  public final Version getVersion()
    throws IOException
  {
    return plugin.getVersion();
  }

  /**
   * Return if the stream was zipped.
   */
  public final boolean isZipped()
    throws IOException
  {
    return plugin.isZipped();
  }

  /**
   * Convenience for <code>parse(true)</code>.
   */
  public final IElement parse()
    throws Exception
  {
    return plugin.parse();
  }

  /**
   * Parse the entire next element into memory as a tree
   * of elements and optionally close the underlying input
   * stream.
   */
  public IElement parse(boolean close)
    throws Exception
  {
    return plugin.parse(close);
  }

  /**
   * Convenience for <code>parseCurrent(false)</code>.
   */
  public final IElement parseCurrent()
    throws Exception
  {
    return plugin.parseCurrent();
  }

  /**
   * Parse the entire current element into memory as a tree
   * of elements and optionally close the underlying input
   * stream.
   */
  public final IElement parseCurrent(boolean close)
    throws Exception
  {
    return plugin.parseCurrent(close);
  }

////////////////////////////////////////////////////////////////
// Logs
////////////////////////////////////////////////////////////////

  /**
   * Create an error exception and return it
   */
  public final RuntimeException err(String msg, Throwable cause)
  {
    return plugin.err(msg, cause);
  }

  /**
   * Create an error exception and return it
   */
  public final RuntimeException err(String msg)
  {
    return plugin.err(msg);
  }

  /**
   * Log and warning and skip the next element
   */
  public final void warningAndSkip(String msg) throws RuntimeException
  {
    plugin.warningAndSkip(msg);
  }

  /**
   * Log a warning
   */
  public final void warning(String msg)
  {
    plugin.warning(msg);
  }

  /**
   * Get the log installed on this decoder for
   * reporting warnings and errors.
   */
  public final Logger getLog()
  {
    return plugin.getLog();
  }

  /**
   * Set the log installed on this decoder for
   * reporting warnings and errors.
   */
  public final void setLog(Logger log)
  {
    plugin.setLog(log);
  }

  /**
   * Get the number of warnings logged during decoding.
   */
  public final int getWarningCount()
  {
    return plugin.getWarningCount();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * This callback is invoked whenever the start tag of an
   * unknown child element is encountered under a BComponent
   * encoding.  If this method is overridden it is required
   * that upon completion the parser is positioned on the start
   * tag of the element immediately following the current
   * element; see skip().
   */
  protected void decodingComponent(BComponent c)
    throws Exception
  {
    plugin.warningAndSkip("Unknown element <" + plugin.elem().name() + "> for decodingComponent");
  }

  /**
   * Called after parsing the slots of a BComponent. Allows subclasses to
   * augment the parent object in whatever way is needed.
   * <p>
   * One use case is to allow reading in the slots from another file given a
   * file name in the "encoderFile" facet.
   *
   * @since Niagara 4.0
   */
  @SuppressWarnings("UnusedParameters")
  protected void parsingSlots(BComponent parent)
    throws Exception
  {
  }

////////////////////////////////////////////////////////////////
// Parse
////////////////////////////////////////////////////////////////

  /**
   * Walk all the child slot elements in parentElem and
   * set/modify/add the slots on the specified parent.
   */
  private void parseSlots(BObject parent)
    throws Exception
  {
    while(true)
    {
      int ptype = next();
      if (ptype == XParser.EOF) throw new EOFException();
      if (ptype == XParser.ELEM_END) return;
      if (ptype != XParser.ELEM_START) throw err("Expected element start");
      parseSlot((BComplex)parent, false);
    }
  }

  /**
   * Process the specified slotElem which defines a
   * slot definition for the given parent object.
   */
  @SuppressWarnings("ConstantConditions")
  private BValue parseSlot(BComplex parent, boolean failFast)
    throws Exception
  {
    // must be at start element
    if (type() != XParser.ELEM_START) throw err("Expected element start (" + type() + ")");
    IElement slotElem = plugin.elem();

    // check element name, must be p, a, or t
    String ename = slotElem.name().intern();
    switch(ename)
    {
      case "p":
      case "a":
      case "t":
        break;
      default:
        if (parent instanceof BComponent)
          decodingComponent((BComponent)parent);
        else
        {
          plugin.warningAndSkip("Unknown element <" + ename + ">");
        }
        if(failFast)
          throw err("Unknown element <" + ename + ">");
        return null;
    }

    // get attributes - we do this in one big loop so that we
    // don't continually scan the attribute list looking for
    // each individual attribute; we can also optimize to
    // use == for string comparisons, because XParser automatically
    // maps one char tokens into an interned string
    String name      = null;
    String handle    = null;
    String module    = null;
    String type      = null;
    String value     = null;
    String flagStr   = null;
    BFacets facets   = BFacets.NULL;
    String catsStr   = null;
    boolean stub     = false;
    int attrSize = slotElem.attrSize();
    for(int i=0; i<attrSize; ++i)
    {
      String attrName = slotElem.attrName(i);
      String attrVal  = slotElem.attrValue(i);
      switch(attrName)
      {
        case "n": name = attrVal; break;
        case "h": handle = attrVal; break;
        case "v": value = attrVal; break;
        case "t": type = attrVal; break;
        case "m": module = attrVal; break;
        case "f": flagStr = attrVal; break;
        case "x": facets = decodeFacets(attrVal); break;
        case "c": catsStr = attrVal; break;
        case "stub": stub = attrVal.equals("true"); break;
      }
    }
    int flags = decodeFlags(flagStr);

    // load the module if we need to
    if (module != null)
    {
      if (plugin.getTypeResolver().loadModule(this, parent, name, module, type)==null)
      {
        // The module couldn't be loaded, but didn't throw an exception either,
        // so that means we should skip it
        try
        {
          // Skip the next element, but actually parse it and scan it
          // to check for any module declarations that might be skipped.
          IElement toSkip = plugin.parseCurrent(false);

          if (toSkip instanceof BogElement)
          {
            XElem elem = ((BogElement)toSkip).getXmlElement();
            LinkedList<XElem> todo = new LinkedList<>();
            todo.add(elem);
            while (!todo.isEmpty())
            {
              XElem x = todo.removeFirst();
              String xname = x.get("n", null);
              String xmodule = x.get("m", null);
              String xtype = x.get("t", null);
              if (xmodule != null)
              {
                plugin.getLog().fine("Loading module for skipped child element "+x);
                plugin.getTypeResolver().loadModule(this, null, xname, xmodule, xtype);
              }
              Collections.addAll(todo, x.elems());
            }
          }
        }
        catch(RuntimeException e)
        { // Any problems trying to skip should throw an exception
          plugin.warning("Missing module " + (module.indexOf('=')!=-1?module.substring(module.indexOf('=')+1):module));
          throw e;
        }
        catch(Exception e)
        { // Any problems trying to skip should throw an exception
          plugin.warning("Missing module " + module);
          throw plugin.err("Missing module " + (module.indexOf('=')!=-1?module.substring(module.indexOf('=')+1):module));
        }

        // Log a missing module trace message then just return null
        // unless we are failing fast
        if(failFast)
          throw plugin.err("Missing module " + (module.indexOf('=')!=-1?module.substring(module.indexOf('=')+1):module));
        plugin.getLog().fine("Missing module: " + module);
      }
    }

    // figure out the context of this object in parent
    Slot slot = null;
    if (parent != null)
    {
      if (name == null) throw plugin.err("Missing \"n\" name attribute");
      slot = parent.getSlot(name);
    }

    // if this is an existing slot
    if (slot != null)
    {
      // set the flags, but only actually if 'f' attr was specified
      // because otherwise we would be setting it to zero
      if (flagStr != null)
        parent.setFlags(slot, flags, Context.decoding);

      // if not a property we are done
      if (!slot.isProperty()) { plugin.skip(); return null; }
    }

    // if slot is null, this can only be a dynamic property
    else
    {
      if (ename.equals("a")) { plugin.warningAndSkip("Missing frozen action: " + name); return null; }
      if (ename.equals("t")) { plugin.warningAndSkip("Missing frozen topic: " + name); return null; }
    }

    // at this point we must have a property slot
    Property prop = (Property)slot;

    // handle special primitive cases like boolean, int, and
    // string to save creating the wrapper BSimple object
    if (decodePrimitive(parent, prop, value))
    {
      if(failFast)
        throw err("Encountered unexpected primitive");
      return null;
    }

    // get an instance of the property
    BValue object = null;
    try
    {
      object = plugin.getTypeResolver().newInstance(this, parent, name, prop, type);
    }
    // Try to replace the type string with the full module name to find the object
    catch(XException e)
    {
      try
      {
        // If we have a "key=module" already, use it to get the module's full name
        if (module != null)
        {
          String fullModuleName = module.substring(module.indexOf('=') + 1);
          String fullTypeName = fullModuleName + type.substring(type.indexOf(':'));
          object = plugin.getTypeResolver().newInstance(this, parent, name, prop, fullTypeName);
          // If we found the module, store the key/moduleName for future reference
          if (object != null)
            moduleKeyMap.put(module.substring(0, module.indexOf('=')), fullModuleName);
        }
        // If we don't have a "key=module" already, check the map
        else
        {
          String fullModuleName = moduleKeyMap.get(type.substring(0, type.indexOf(':')));
          String fullTypeName = fullModuleName + type.substring(type.indexOf(':'));
          if (fullModuleName != null)
            object = plugin.getTypeResolver().newInstance(this, parent, name, prop, fullTypeName);
        }
      }
      catch (Exception ignore)
      {}

      if (object == null)
        throw e;
    }

    if (object == null)
    {
      if(failFast)
        throw err("Unable to instantiate "+(type.indexOf(':')!=-1?type.substring(type.indexOf(':')+1):type));
      return null;
    }

    // if a simple, then decode the value
    if (object.isSimple() && value != null)
    {
      //if object type is blacklisted set value to default encoding
      if (isTypeBlackListed(object.getType()))
      {
        value = ((BSimple)object.getType().getInstance()).encodeToString();
      }

      object = decodeSimple(object, value);
    }

    // if a component then init slot map
    if (object.isComponent())
    {
      // set handle
      ((ComponentSlotMap)object.fw(Fw.SLOT_MAP)).setHandle(handle);

      // if fully loaded set brokerLoaded flag
      if (!stub)
        ((ComponentSlotMap)object.fw(Fw.SLOT_MAP)).setBrokerPropsLoaded(true);

      // if specified set category mask
      if (catsStr != null)
        ((BComponent)object).setCategoryMask(BCategoryMask.make(catsStr), Context.decoding);
    }

    // set/add the slot on the parent
    if (parent != null)
    {
      if (prop != null)
      {
        if (!prop.isFrozen())
          throw plugin.err("Duplicate slot " + parent.getType().getTypeName() + "." + name);

        // set value
        try
        {
          parent.set(prop, object, Context.decoding);
        }
        catch(Exception e)
        {
          plugin.warningAndSkip("Cannot set property " + parent.getType().getTypeName() + "." + name + ": " + e);
        }
      }
      else if (parent.isComponent())
      {
        // add
        parent.asComponent().add(name, object, flags, facets, Context.decoding);
      }
      else
      {
        plugin.warningAndSkip("Missing slot " + parent.getType().getTypeName() + "." + name);
      }
    }

    // dive down
    parseSlots(object);

    // call subclass for BComponent objects
    if (object.isComponent())
    {
      parsingSlots((BComponent) object);
    }

    // return the object parsed
    return object;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * This method gives us an opportunity to decode the special
   * built-in primitives like boolean, int, and String.  This
   * method short circuits the decoding and set for frozen
   * primitive properties.  Return true if short circuited.
   */
  private boolean decodePrimitive(BComplex parent, Property prop, String value)
    throws Exception
  {
    // don't bother unless we have an existing
    // frozen property with primitive type access
    if (prop == null || value == null) return false;
    if (prop.getTypeAccess() == Slot.BOBJECT_TYPE) return false;

    // switch on primitive type to do decode and set
    switch(prop.getTypeAccess())
    {
      case Slot.BOOLEAN_TYPE:
        parent.setBoolean(prop, BBoolean.decode(value), Context.decoding);
        break;
      case Slot.INT_TYPE:
        parent.setInt(prop, BInteger.decode(value), Context.decoding);
        break;
      case Slot.LONG_TYPE:
        parent.setLong(prop, BLong.decode(value), Context.decoding);
        break;
      case Slot.FLOAT_TYPE:
        parent.setFloat(prop, BFloat.decode(value), Context.decoding);
        break;
      case Slot.DOUBLE_TYPE:
        parent.setDouble(prop, BDouble.decode(value), Context.decoding);
        break;
      case Slot.STRING_TYPE:
        parent.setString(prop, value, Context.decoding);
        break;
      default:
        throw new IllegalStateException(""+prop.getTypeAccess());
    }

    // since we will be short circuiting the call to parseSlots()
    // at the end of parseSlot(), we need to go ahead and consume
    // the element's virtual end tag
    int ptype = next();
    if (ptype != XParser.ELEM_END)
      throw err("Expecting end of p element for simple property " + prop.getName());
    return true;
  }

  /**
   * Decode the string value of a simple.
   */
  protected BSimple decodeSimple(BObject obj, String value)
  {
    try
    {
      return simpleFactory.make(obj.getType(), value, context);
    }
    catch(Exception e)
    {
      throw plugin.err("Invalid " + obj.getType().getTypeName() + ": '" + value + "'", e);
    }
  }

  /**
   * Decode the flags.
   */
  private int decodeFlags(String str)
  {
    if (str == null) return 0;
    else return Flags.decodeFromString(str);
  }

  /**
   * Decode the facets.
   */
  private BFacets decodeFacets(String str)
  {
    try
    {
      if (str == null) return BFacets.NULL;
      return (BFacets) simpleFactory.make(BFacets.TYPE, str, context);
    }
    catch(Exception e)
    {
      throw plugin.err("Invalid facets " + str, e);
    }
  }

  /**
   * Return true if the specified Type is black listed and shouldn't be decoded.
   * <p>
   * Currently this is limited to Simple Types.
   *
   * @param type the Type to check.
   * @return true if the Type is black listed and should never be decoded.
   */
  public boolean isTypeBlackListed(Type type)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// ITypeResolver
////////////////////////////////////////////////////////////////

  /**
   * The ITypeResolver interface is used to take String attribute
   * elements parsed from the document and determine how to
   * resolve a module/type/instance.
   */
  public interface ITypeResolver
  {
    /**
     * Load a module into memory for the type of a property being
     * decoded
     *
     * @param decoder The ValueDocDecoder instance for which to load the module
     * @param parent The parent for the property about to be added.
     * @param propName The name of the property
     * @param moduleStr The Module String
     * @param typeStr The Type String
     *
     * @return BModule, or null if unable to load.
     */
    BModule loadModule(ValueDocDecoder decoder, BComplex parent, String propName, String moduleStr, String typeStr);

    /**
     * Given a frozen property and/or a type attribute,
     * create a BValue new instance.  If there is a fatal error
     * then throw an exception.  If there is a recoverable
     * error then log a warning and return null.
     *
     * @param decoder The ValueDocDecoder instance for which the property is being decoded
     * @param parent The parent for the property about to be added.
     * @param propName The name of the property
     * @param prop The frozen property
     * @param typeStr The Type String to load
     *
     * @return BValue is a new instance of the property for the proper type.
     */
    BValue newInstance(ValueDocDecoder decoder, BComplex parent, String propName, Property prop, String typeStr);

    /**
     * In order to support legacy connections, there are special cases when the decoder
     * encounters types that have unsupported legacy encodings (ie. legacy BPasswords) that
     * need to be skipped during decode instead of failing fast. This method is used to set
     * the decoder in and out of such a mode (if the ITypeResolver implementation supports it).
     * The default mode should always be to fail fast when a type with an invalid legacy
     * encoding is encountered during decode, but if this method is called with a true
     * argument, then the mode will change to skip over types with an invalid legacy encoding.
     *
     * @param skipLegacyEncodings If true, indicates that the decoding process should skip over any
     *             (invalid) legacy encoded type.  If false, the decoding process
     *             should fail fast on any (invalid) legacy type encountered.
     *
     * @since Niagara 4.0
     */
    default void setSkipLegacyEncodings(boolean skipLegacyEncodings) {}

    /**
     * Returns true when this type resolver is in a mode where it will skip decoding any
     * types with an unsupported legacy encoding (ie. legacy BPasswords).  Returns false
     * when this type resolver is in a mode to fail fast when decoding any
     * legacy encoded types.
     *
     * @since Niagara 4.0
     */
    default boolean getSkipLegacyEncodings()
    {
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// Decoder Plugin
////////////////////////////////////////////////////////////////

  /**
   * A value document's format can vary (XML or JSON). A decoder plug-in is
   * used to support these different formats
   */
  public interface IDecoderPlugin
    extends AutoCloseable
  {
    BValue decodeDocument(ValueDocDecoder decoder) throws Exception;

    int next() throws Exception;
    IElement elem();
    void skip() throws Exception;
    void skip(int depth) throws Exception;
    int type();

    int line();
    int column();
    @Override
    void close();
    int depth();

    String getEncoding() throws IOException;
    boolean isZipped() throws IOException;
    default Version getVersion() throws IOException
    {
      return Version.NULL;
    }
    IElement parse() throws Exception;
    IElement parse(boolean close) throws Exception;
    IElement parseCurrent() throws Exception;
    IElement parseCurrent(boolean close) throws Exception;

    ITypeResolver getTypeResolver();
    void setTypeResolver(ITypeResolver typeResolver);

    RuntimeException err(String msg, Throwable cause);
    RuntimeException err(String msg);
    void warningAndSkip(String msg) throws RuntimeException;
    void warning(String msg);
    Logger getLog();
    void setLog(Logger log);
    int getWarningCount();

    /**
     * If the document contains encrypted data that's encoded using a reversible algorithm, and
     * the algorithm uses a key created from a pass phrase, return a validator that can
     * verify that a given pass phrase matches the one that was used to encode the document.
     *
     * @since Niagara 4.0
     */
    default BIPasswordValidator getPassPhraseValidator()
    {
      return BPassword.DEFAULT;
    }
  }

////////////////////////////////////////////////////////////////
// BOG Decoder Plugin
////////////////////////////////////////////////////////////////

  public static BogPasswordObjectEncoder getBogEncodingInfo(File file)
    throws Exception
  {
    try (InputStream inRaw = new FileInputStream(file);
         InputStream in = new BufferedInputStream(inRaw))
    {
      BogDecoderPlugin plugin = new BogDecoderPlugin(in);
      plugin.readHeader();
      return plugin.getPasswordObjectEncoder();
    }
  }

  /**
   * BOG (Baja Object Graph) XML decoder plug-in
   * <p>
   * This plug-in is used to decode convention BOG XML
   */
  public static final class BogDecoderPlugin
      implements IDecoderPlugin
  {
    public BogDecoderPlugin(InputStream in) throws Exception
    {
      this(in, null);
    }

    public BogDecoderPlugin(BOrd ord) throws Exception
    {
      this((BIFile)ord.resolve().get());
    }

    public BogDecoderPlugin(BIFile file) throws Exception
    {
      this(new BufferedInputStream(file.getInputStream()));
    }

    public BogDecoderPlugin(File file) throws Exception
    {
      this(new BufferedInputStream(new FileInputStream(file)));
    }

    public BogDecoderPlugin(InputStream in, Context cx) throws Exception
    {
      parser = XParser.make(in);
      initPasswordHandling(cx);
    }

    public BogDecoderPlugin(BOrd ord, Context cx) throws Exception
    {
      this((BIFile)ord.resolve().get(), cx);
    }

    public BogDecoderPlugin(BIFile file, Context cx) throws Exception
    {
      this(new BufferedInputStream(file.getInputStream()), cx);
    }

    public BogDecoderPlugin(File file, Context cx) throws Exception
    {
      this(new BufferedInputStream(new FileInputStream(file)), cx);
    }

    private void initPasswordHandling(Context pluginContext) throws Exception
    {
      if (pluginContext != null)
      {
        try
        {
          PasswordEncodingContext pContext = PasswordEncodingContext.find(pluginContext).orElse(null);
          if (pContext != null && pContext.hasEncryptionKey())
          {
            // hasEncryptionKey checks that the encryption key is present
            //noinspection OptionalGetWithoutIsPresent
            passwordObjectEncoder = AccessController.doPrivileged(
              (PrivilegedExceptionAction<BogPasswordObjectEncoder>) () ->
                BogPasswordObjectEncoder.make(pContext.getEncryptionKey().get()));
            // TODO readHeader will overwrite this
          }
        }
        catch (PrivilegedActionException pae)
        {
          if (pae.getCause() instanceof IOException)
          {
            throw (IOException)pae.getCause();
          }
          else
          {
            throw new IOException(pae.getCause());
          }
        }
      }
    }

    public Version readHeader() throws Exception
    {
      // get root element
      next();
      IElement root = elem();

      // the root element name must be "bajaObjectGraph"
      if (!root.name().equals("bajaObjectGraph"))
      {
        throw err("Root element must be \"bajaObjectGraph\"");
      }

      // Supported versions:
      // 1.0 - Niagara AX
      // 4.0 - Niagara 4
      String ver = root.get("version");
      if (!(ver.equals("1.0") || ver.equals("4.0")))
      {
        throw err("Unsupported version (" + ver + "): supported versions are 1.0, 4.0");
      }
      version = new Version(ver);

      passwordObjectEncoder = BogPasswordObjectEncoder.parseBogHeader(root, reversibleEncodingKeySource);
      passPhraseValidator = BIPasswordValidator.fromPBEValidator(passwordObjectEncoder.getPbeEncodingInfo());

      return version;
    }

    @Override
    public BIPasswordValidator getPassPhraseValidator()
    {
      return passPhraseValidator;
    }

    public BogPasswordObjectEncoder getPasswordObjectEncoder()
    {
      return passwordObjectEncoder;
    }
    public void setPasswordObjectEncoder(BogPasswordObjectEncoder value)
    {
      Objects.requireNonNull(value);
      this.passwordObjectEncoder = value;
    }

    public void setReversibleEncodingKeySource(EncryptionKeySource value)
    {
      reversibleEncodingKeySource = value;
    }

    public void setPassPhrase(Optional<BPassword> value)
    {
      passPhrase = value;
    }

    @Override
    public BValue decodeDocument(ValueDocDecoder decoder) throws Exception
    {
      readHeader();

      // update the decoder's context to provide facets with password decoding information
      if (!passwordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
      {
        PasswordEncodingContext pContext = PasswordEncodingContext.from(decoder.context);

        try
        {
          AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() ->
          {
            if (passwordObjectEncoder.getKeySource().equals(EncryptionKeySource.shared))
            {
              pContext.setDecryptionKey(EncryptionKeySource.external, Optional.of(passwordObjectEncoder.passPhraseToKey(null)));
              pContext.setEncryptionUndefined();
            }
            else if (passwordObjectEncoder.getKeySource().equals(EncryptionKeySource.external))
            {
              pContext.setEncryptionUndefined();
              if (passPhrase.isPresent())
              {
                if (!getPassPhraseValidator().validate(AccessController.doPrivileged((PrivilegedAction<String>)passPhrase.get()::getValue)))
                {
                  // TODO options for other configurable actions to take when pass phrase is unavailable or doesn't match?
                  throw new BajaRuntimeException("Encryption pass phrase does not match");
                }

                try (SecretChars secretChars = passPhrase.get().getSecretChars())
                {
                  pContext.setDecryptionKey(EncryptionKeySource.external, Optional.of(passwordObjectEncoder.passPhraseToKey(secretChars)));
                }
              }
              else
              {
                pContext.setDecryptionKey(EncryptionKeySource.external, Optional.empty());
              }
            }
            return null;
          });
        }
        catch (PrivilegedActionException pae)
        {
          if (pae.getCause() instanceof RuntimeException)
          {
            throw (RuntimeException)pae.getCause();
          }
          else
          {
            throw new BajaRuntimeException(pae.getCause());
          }
        }


        if (decoder.context == null) decoder.context = pContext;
      }
      else // use keyring
      {
        if (decoder.context == null)
        {
          decoder.context = PasswordEncodingContext.makeKeyring();
        }
        else 
        {
          decoder.context = PasswordEncodingContext.updateForKeyring(decoder.context);
        }
      }

      // parse value
      next();
      BValue value = decoder.decode();

      // we expect to be at end now
      next();
      if (type() != XParser.ELEM_END || !elem().name().equals("bajaObjectGraph"))
      {
        throw err("Expected end tag for \"bajaObjectGraph\"");
      }

      return value;
    }

    @Override
    public int next() throws Exception { return parser.next(); }
    @Override
    public IElement elem()
    {
      return BogElement.make(parser.elem());
    }
    @Override
    public void skip() throws Exception { parser.skip(); }
    @Override
    public void skip(int depth) throws Exception { parser.skip(depth); }
    @Override
    public int type() { return parser.type(); }

    @Override
    public int line() { return parser.line(); }
    @Override
    public int column() { return parser.column(); }
    @Override
    public void close()
    {
      parser.close();
      passwordObjectEncoder.close();
    }
    @Override
    public int depth() { return parser.depth(); }

    @Override
    public String getEncoding() throws IOException { return parser.getEncoding(); }
    @Override
    public boolean isZipped() throws IOException { return parser.isZipped(); }
    @Override
    public Version getVersion() throws IOException { return version; }
    @Override
    public IElement parse() throws Exception { return BogElement.make(parser.parse()); }
    @Override
    public IElement parse(boolean close) throws Exception { return BogElement.make(parser.parse(close)); }
    @Override
    public IElement parseCurrent() throws Exception { return BogElement.make(parser.parseCurrent()); }
    @Override
    public IElement parseCurrent(boolean close) throws Exception { return BogElement.make(parser.parseCurrent(close)); }

    @Override
    public ITypeResolver getTypeResolver() { return typeResolver; }
    @Override
    public void setTypeResolver(ITypeResolver typeResolver)
    {
      if (typeResolver == null)
      {
        this.typeResolver = defaultTypeResolver;
      }
      else
      {
        this.typeResolver = typeResolver;
      }
    }

    @Override
    public RuntimeException err(String msg, Throwable cause) { return new XException(msg, parser.line(), parser.column(), cause); }
    @Override
    public RuntimeException err(String msg) { return new XException(msg, parser.line(), parser.column()); }

    @Override
    public void warningAndSkip(String msg) throws RuntimeException
    {
      warning(msg);

      try
      {
        skip();
      }
      catch(XException e)
      {
        throw e;
      }
      catch(Exception e)
      {
        throw new XException(e);
      }
    }

    @Override
    public void warning(String msg) throws RuntimeException
    {
      log.warning(msg + " [" + parser.line() + ':' + parser.column() + "]");
      warningCount++;
    }

    @Override
    public Logger getLog() { return log; }
    @Override
    public void setLog(Logger log) { this.log = log; }
    @Override
    public int getWarningCount() { return warningCount; }

    public XParser getXmlParser() { return parser; }

    /**
     * Unmarshal a value from an XML string
     */
    public static BValue unmarshal(String xml) throws Exception
    {
      return unmarshal(xml, defaultTypeResolver, PasswordEncodingContext.makeNone());
    }

    /**
     * Unmarshal a value from an XML string
     */
    public static BValue unmarshal(String xml, ITypeResolver typeResolver) throws Exception
    {
      return unmarshal(xml, typeResolver, PasswordEncodingContext.makeNone());
    }

    /**
     * Unmarshal a value from an XML string
     * 
     * @since Niagara 4.0
     */
    public static BValue unmarshal(String xml, Context cx) throws Exception
    {
      return unmarshal(xml, defaultTypeResolver, cx);
    }
    
    /**
     * Unmarshal a value from an XML string
     * 
     * @since Niagara 4.0
     */
    public static BValue unmarshal(String xml, ITypeResolver typeResolver, Context cx)
      throws Exception
    {
      BogDecoderPlugin plugin = new BogDecoderPlugin(new ByteArrayInputStream(xml.getBytes()), cx);
      ValueDocDecoder decoder = new ValueDocDecoder(plugin, cx);
      decoder.plugin.setTypeResolver(typeResolver);
      decoder.plugin.next();
      return decoder.decode();
    }

    private final XParser parser;
    private final Map<String, Map<RuntimeProfile, NModule>> modules = new HashMap<>();
    private final Map<String, String> moduleNamesBySymbol = new HashMap<>();

    private ITypeResolver typeResolver = new BogTypeResolver();
    public static final ITypeResolver defaultTypeResolver = new BogTypeResolver();

    private static final Logger defaultLog = Logger.getLogger("sys.xml");
    private Logger log = defaultLog;
    private int warningCount;

    protected BIPasswordValidator passPhraseValidator = BPassword.DEFAULT;
    protected EncryptionKeySource reversibleEncodingKeySource = EncryptionKeySource.undefined;
    protected BogPasswordObjectEncoder passwordObjectEncoder = BogPasswordObjectEncoder.makeNone();
    private   Optional<BPassword> passPhrase = Optional.empty();
    protected Version version;
  }

  /**
   * A BOG document element
   */
  public final static class BogElement
      implements IElement
  {
    private BogElement(XElem elem) { this.elem = elem; }
    public static BogElement make(XElem elem) { return elem == null ? null : new BogElement(elem); }

    @Override
    public String name() { return elem.name(); }

    @Override
    public String get(String attrName) { return elem.get(attrName); }
    @Override
    public String get(String attrName, String def) { return elem.get(attrName, def); }

    @Override
    public int geti(String attrName) { return elem.geti(attrName); }
    @Override
    public int geti(String attrName, int def) { return elem.geti(attrName, def); }

    @Override
    public double getd(String attrName) { return elem.getd(attrName); }
    @Override
    public double getd(String attrName, double def) { return elem.getd(attrName, def); }

    @Override
    public float getf(String attrName) { return elem.getf(attrName); }
    @Override
    public float getf(String attrName, float def) { return elem.getf(attrName, def); }

    @Override
    public long getl(String attrName) { return elem.getl(attrName); }
    @Override
    public long getl(String attrName, long def) { return elem.getl(attrName, def); }

    @Override
    public int attrSize() { return elem.attrSize(); }
    @Override
    public String attrName(int index) { return elem.attrName(index); }
    @Override
    public String attrValue(int index) { return elem.attrValue(index); }

    @Override
    public com.tridium.nre.util.IElement copy() { return BogElement.make(elem.copy()); }

    @Override
    public String toString() { return elem.toString(); }

    public XElem getXmlElement() { return elem; }

    private final XElem elem;
  }

  /**
   * BOG Type Resolver
   * <p>
   * BOG has 'm' and 't' attributes that are used to resolve Type Specifications
   * when decoding a BOG document
   */
  public static class BogTypeResolver
      implements ITypeResolver
  {
    /**
     * Load a module into memory for the type of a property being
     * decoded.  The module should be loaded into the given BogDecoder's
     * module map (see getModuleMap()).
     * The String parameter
     * should be of the form "key=moduleName".  The module
     * loaded should be stored in memory for subsequent lookup
     * by the newInstance() method.
     *
     * @param decoder The BogDecoder instance for which to load
     * the module
     * @param parent The parent for the property about to be added.
     * @param propName The name of the property
     * @param moduleStr The encoded module String of the form "moduleKey=moduleName"
     * @param ignored not processed
     *
     * @return BModule
     */
    @Override
    public BModule loadModule(ValueDocDecoder decoder, BComplex parent, String propName, String moduleStr, String ignored)
    {
      String name;
      String key;
      try
      {
        int equals  = moduleStr.indexOf('=');
        key  = moduleStr.substring(0, equals).trim();
        name = moduleStr.substring(equals+1).trim();

        // load the module into the BogDecoder's memory map
        NModule[] moduleParts = Nre.getModuleManager().loadModuleParts(name);
        Map<RuntimeProfile,NModule> map = new HashMap<>();
        for (NModule part : moduleParts)
        {
          map.put(part.getRuntimeProfile(),part);
        }
        getModuleMap(decoder).put(key, map);
        getModuleNameMap(decoder).put(key, name);
        return moduleParts[0].bmodule();
      }
      catch(ModuleException e)
      {
        throw decoder.plugin.err("Cannot load module '" + moduleStr + "'", e);
      }
      catch(Exception e)
      {
        throw decoder.plugin.err("Invalid module attribute '" + moduleStr + "'");
      }
    }

    /**
     * Given a frozen property and/or a type attribute,
     * create a BValue new instance.  If there is a fatal error
     * then throw an XException.  If there is a recoverable
     * error then log a warning and return null.
     *
     * @param decoder The BogDecoder instance for which the property is being decoded
     * @param parent The parent for the property about to be added.
     * @param propName The name of the property
     * @param prop The frozen property
     * @param typeStr The encoded property type string of the form "moduleKey:typeName", where
     * the moduleKey should be a key identifying a module loaded in memory
     * by a previous call to the loadModule() method.  The typeName identifies
     * a unique type within the module.
     *
     * @return BValue is a new instance of the property for the proper type.
     */
    @Override
    public BValue newInstance(ValueDocDecoder decoder, BComplex parent, String propName, Property prop, String typeStr)
    {
      // if type is null, then fallback to the type of the property's
      // default value; if there isn't a property then 99% of the time
      // this means that a frozen property was removed
      if (typeStr == null)
      {
        if (prop != null)
        {
          return prop.getDefaultValue();
        }
        else
        {
          decoder.plugin.warningAndSkip("Missing frozen property: " + propName);
          return null;
        }
      }

      try
      {
        int x = typeStr.indexOf(':');
        if (x <= 0)
          throw decoder.plugin.err("Invalid typespec '" + typeStr + "'");

        String tkey = typeStr.substring(0, x);
        String tname = typeStr.substring(x+1);
        Map<RuntimeProfile,NModule> byProfile = getModuleMap(decoder).get(tkey);
        if (byProfile != null)
        {
          for (NModule module : byProfile.values())
          {
            if (module.hasType(tname))
            {
              return typeResolverNewInstance(module, tname);
            }
          }
          Iterator<NModule> i = getModuleMap(decoder).get(tkey).values().iterator();
          if (i.hasNext())
          {
            return ValueDocDecoder.newSwapInstance(i.next().getModuleName(), tname);
          }
          throw new TypeNotFoundException(typeStr);
        }
        else
        {
          throw decoder.plugin.err("Undeclared module symbol: " + tkey, new ModuleNotFoundException(tkey));
        }
      }
      catch(XException e)
      {
        throw e;
      }
      catch(TypeNotFoundException e)
      {
        // consider this a recoverable error by skipping element
        decoder.plugin.warningAndSkip("Type \"" + e.getMessage() + "\" not found: " + propName);
        return null;
      }
      catch(Throwable e)
      {
        throw decoder.plugin.err("Cannot instantiate type '" + typeStr + "'", e);
      }
    }

    /**
     * Given a BogDecoder instance, return its module memory map.
     */
    public Map<String, Map<RuntimeProfile,NModule>> getModuleMap(ValueDocDecoder decoder)
    {
      return ((BogDecoderPlugin)decoder.plugin).modules;
    }

    public NModule[] getMappedModules(ValueDocDecoder decoder, String moduleName)
    {
      if (getModuleMap(decoder).containsKey(moduleName))
      {
        ArrayList<NModule> nModulesList = new ArrayList<>();

        getModuleMap(decoder).get(moduleName).values().forEach(nModulesList::add);

        return nModulesList.toArray(new NModule[nModulesList.size()]);
      }
      return null;
    }

    public NModule getMappedModule(ValueDocDecoder decoder, String moduleName, RuntimeProfile profile)
    {
      if (getModuleMap(decoder).containsKey(moduleName))
      {
        return getModuleMap(decoder).get(moduleName).get(profile);
      }
      return null;
    }

    public void updateModuleMap(ValueDocDecoder decoder, NModule[] modules)
    {
      for (NModule module : modules)
      {
        Map<RuntimeProfile,NModule> byProfile = getModuleMap(decoder).get(module.getModuleName());
        if (byProfile == null)
        {
          getModuleMap(decoder).put(module.getModuleName(), byProfile = new HashMap<>());
        }
        byProfile.put(module.getRuntimeProfile(), module);
      }
    }

    /**
     * Given a BogDecoder instance, return its module name map.
     */
    public Map<String, String> getModuleNameMap(ValueDocDecoder decoder)
    {
      return ((BogDecoderPlugin)decoder.plugin).moduleNamesBySymbol;
    }
  }

  public static BValue typeResolverNewInstance(NModule module, String typeName)
  {
    Type type;
    try
    {
      type = module.getType(typeName);
    }
    catch(TypeNotFoundException e)
    {
      return newSwapInstance(module.getModuleName(), typeName);
    }

    return (BValue)type.getInstance();
  }

  static BValue newSwapInstance(String moduleName, String typeName)
  {
    // This a hack to workaround a Niagara Virtuals issue that was added in 3.7 (NCCB-3339).
    String typeString = String.format("%s:%s", moduleName, typeName);
    Type type;
    if (decodeTypeSwap)
    {
      String typeSwap = typeSwapMap.get(typeString);
      if (typeSwap != null)
      {
        type = Sys.getType(typeSwap);
      }
      else
      {
        throw new TypeNotFoundException(typeString);
      }
    }
    else
    {
      throw new TypeNotFoundException(typeString);
    }
    return (BValue)type.getInstance();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final SimpleFactory simpleFactory = new SimpleFactory();
  protected final IDecoderPlugin plugin;
  private Context context = null;

  private static final Map<String,String> typeSwapMap = new HashMap<>();
  static
  {
    typeSwapMap.put("niagaraDriver:NiagaraVirtualGateway", "niagaraDriver:NiagaraVirtualDeviceExt");
  }
  private static final boolean decodeTypeSwap = AccessController.doPrivileged((PrivilegedAction<String>) () ->
    System.getProperty("niagara.decodeTypeSwap", "true")).equals("true");

  private Map<String, String> moduleKeyMap = new HashMap<>();
}
