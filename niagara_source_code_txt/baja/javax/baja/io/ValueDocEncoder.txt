/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

import com.tridium.nre.security.EncryptionKeySource;
import com.tridium.nre.security.SecretChars;
import com.tridium.nre.security.io.BogPasswordObjectEncoder;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

import javax.baja.category.BCategoryMask;
import javax.baja.naming.SlotPath;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BPassword;
import javax.baja.security.BPermissions;
import javax.baja.security.PasswordEncodingContext;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLink;
import javax.baja.sys.BLong;
import javax.baja.sys.BModule;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelation;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.Version;
import javax.baja.virtual.BVirtualComponentSpace;
import javax.baja.xml.XWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * ValueDocEncoder is used to encode a BValue tree to a document
 * <p>
 * By default, the format can be BOG (XML) or BSON (JSON) but
 * can be extended further by implementing IEncoderPlugin
 * <p>
 * This class is designed to replace the existing BogEncoder that is
 * solely XML based
 * <p>
 * Note, that care has been taken to ensure that exactly the same XML produced
 * by BogEncoder is also produced by this class since we don't want unreadable
 * or larger BOG files impacting customer's file systems
 *
 * @see IEncoderPlugin
 * @see ValueDocDecoder
 *
 * @author    Gareth Johnson
 * @creation  07 Jan 11
 * @since     Niagara 3.7
 */
public class ValueDocEncoder implements AutoCloseable
{

////////////////////////////////////////////////////////////////
// Marshal
////////////////////////////////////////////////////////////////

  /**
   * Marshal the specified value into an XML String.
   * This XML is not a full document - see encode().
   */
  public static String marshal(BValue value)
    throws IOException
  {
    return BogEncoderPlugin.marshal(value);
  }

  /**
   * Marshal the specified value into an XML String.
   * This XML is not a full document - see encode().
   * 
   * @since Niagara 4.0
   */
  public static String marshal(BValue value, Context cx)
    throws IOException
  {
    return BogEncoderPlugin.marshal(value, cx);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct encoder with plugin and encodeTransients set to false.
   */
  public ValueDocEncoder(IEncoderPlugin plugin)
    throws IOException
  {
    this(plugin, null);
  }

  /**
   * Construct encoder with plugin and encodeTransients set to false.
   */
  public ValueDocEncoder(IEncoderPlugin plugin, Context context)
    throws IOException
  {
    this.plugin = plugin;
    this.context = context;
  }

  /**
   * Construct a BOG XML encoder with file and encodeTransients set to false.
   */
  public ValueDocEncoder(File file)
    throws IOException
  {
    this(new BogEncoderPlugin(file), null);
  }

  /**
   * Construct a BOG XML encoder with output stream and encodeTransients set to false.
   */
  public ValueDocEncoder(OutputStream out)
    throws IOException
  {
    this(new BogEncoderPlugin(out), null);
  }

  /**
   * Construct a BOG XML encoder with file and encodeTransients set to false.
   */
  public ValueDocEncoder(File file, Context context)
    throws IOException
  {
    this(new BogEncoderPlugin(file, context), context);
  }

  /**
   * Construct a BOG XML encoder with output stream and encodeTransients set to false.
   */
  public ValueDocEncoder(OutputStream out, Context context)
    throws IOException
  {
    this(new BogEncoderPlugin(out, context), context);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Get the context passed to the constructor.
   */
  public Context getContext()
  {
    return context;
  }

  /**
   * Return a merged Context from the parent and slot.
   *
   * @return Context
   */
  public static Context getMergedContext(BComplex parent, Slot slot, Context cx)
  {
    // Null arguments can rarely occur for the recursive
    // call to getMergedContext() in the BStruct case.  If another thread
    // removes/replaces a struct value from its parent at just the right time,
    // then it could happen.  Checking for null here suppresses the nuisance
    // exceptions
    if ((parent == null) || (slot == null))
      return cx;

    BFacets facets = parent.getSlotFacets(slot);

    if (cx == null)
      cx = facets;
    else if (facets != null && !facets.isNull())
    {
      if (facets.getPickle() == null)
      {
        facets = BFacets.makePickle(facets, BFacets.SKIP_INTERN_PICKLE);
      }
      cx = new BasicContext(cx, facets);
    }

    // If this is a Struct then attempt to merge the facets up to the parent Component
    if (parent.isStruct() && parent.getParent() != null)
      return getMergedContext(parent.getParent(), parent.getPropertyInParent(), cx);
    else
      return cx;
  }

  /**
   * Is this a SyncEncoder?
   */
  public boolean isSyncEncoder()
  {
    return false;
  }

  /**
   * Is the encode transients flag set.
   */
  public boolean isEncodeTransients()
  {
    return encodeTransients;
  }

  /**
   * Set the encode transients flag.  This flag is used to
   * control whether all properties are serialized, or only
   * non-transient properties.
   *
   * @return the old value of encode transients
   */
  public boolean setEncodeTransients(boolean encodeTransients)
  {
    boolean old = this.encodeTransients;
    this.encodeTransients = encodeTransients;
    return old;
  }

  /**
   * Is the encode comments flag set.
   */
  public boolean isEncodeComments()
  {
    return encodeComments;
  }

  /**
   * Set the encode encodeComments flag.   If set to true, then
   * comments are included to help navigate its structure.
   *
   * @return the old value of encode transients
   */
  public boolean setEncodeComments(boolean encodeComments)
  {
    boolean old = this.encodeComments;
    this.encodeComments = encodeComments;
    return old;
  }

  /**
   * @return the encoder plug-in
   */
  public final IEncoderPlugin getPlugin()
  {
    return plugin;
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Return a list of exceptions encountered during the most recent encoding
   * @since Niagara 4.0
   */
  public Stream<Exception> getUnhandledEncodingExceptions()
  {
    return unhandledEncodingExceptions.stream();
  }

  /**
   * Encode a complete document which serializes the
   * value.  The generated document includes a prolog and
   * the document start and end tags.  See encode()
   * for details about the encoding of the value.
   */
  public void encodeDocument(BValue value)
    throws IOException
  {
    unhandledEncodingExceptions.clear();
    plugin.encodeDocument(this, value);
    plugin.flush();
  }

  /**
   * Convenience for <code>encode(null, value, Integer.MAX_VALUE)</code>.
   */
  public void encode(BValue value)
    throws IOException
  {
    encode(null, value, Integer.MAX_VALUE);
  }

  /**
   * Encode the specified value to the document stream.  This
   * method does not generate a complete document, but
   * rather only a single element.  The depth parameter
   * is used to specify how deep to serialize the BComponent
   * tree.  A value of zero means component itself, one is
   * children components, two is grandchildren components, etc.
   * Simples and Structs are always serialized in their
   * entirety.
   * <p>
   * If the encoder's context contains a valid user, then
   * security permissions are applied during encoding.
   */
  public void encode(String name, BValue value, int depth)
    throws IOException
  {
    // check root security
    BPermissions permissions = BPermissions.all;
    if (context != null && context.getUser() != null && value instanceof BComponent)
    {
      permissions = getPermissionsFor(value.asComponent());
      if (!permissions.hasOperatorRead())
        throw new SecurityException("Missing op read permission on value");
    }

    // write the p element
    plugin.start("p");

    if (name != null) wname(name);
    if (value instanceof  BComponent)
    {
      BComponent comp = (BComponent)value;

      Object handle = comp.getHandle();
      if (handle != null) whandle(handle);

      BCategoryMask cats = comp.getCategoryMask();
      if (!cats.isNull()) wcategories(cats);

      if (((comp.getComponentSpace() instanceof BVirtualComponentSpace) &&
          (!((ComponentSlotMap)comp.fw(Fw.SLOT_MAP)).isBrokerPropsLoaded())))
        plugin.attr("stub", true);
    }
    wtype(value);

    Context cx = getContext();

    if (value.isComplex() && value.asComplex().getParent() != null)
    {
      BComplex complex = value.asComplex();
      cx = getMergedContext(complex.getParent(), complex.getPropertyInParent(), getContext());
    }

    encodeValue(value, depth, permissions, cx);
  }

  /**
   * Flush the encoder
   */
  public final void flush() throws IOException
  {
    plugin.flush();
  }

  /**
   * Close the encoder
   */
  @Override
  public final void close() throws IOException
  {
    plugin.close();
    // invoke callback so subclasses can clean up resources
    doClose();
  }

  /**
   * Start an element
   */
  public final IEncoderPlugin start(String name) throws IOException
  {
    return plugin.start(name);
  }

  /**
   * Start an array (used in JSON encoding)
   */
  public final IEncoderPlugin startArray(String name) throws IOException
  {
    return plugin.startArray(name);
  }

  /**
   * End an array (used in JSON encoding)
   */
  public final IEncoderPlugin endArray() throws IOException
  {
    return plugin.endArray();
  }

  /**
   * End an element
   */
  public final IEncoderPlugin end() throws IOException
  {
    return plugin.end();
  }

  /**
   * End an element with a name
   */
  public final IEncoderPlugin end(String name) throws IOException
  {
    return plugin.end(name);
  }

  /**
   * End the list of attributes (used in XML encoding)
   */
  public final IEncoderPlugin endAttr() throws IOException
  {
    return plugin.endAttr();
  }

  /**
   * Create a key for a value (used in JSON encoding)
   */
  public final IEncoderPlugin key(String key) throws IOException
  {
    return plugin.key(key);
  }

  /**
   * Create a boolean key/value pair attribute
   */
  public final IEncoderPlugin attr(String key, boolean val) throws IOException
  {
    return plugin.attr(key, val);
  }

  /**
   * Create a double key/value pair attribute
   */
  public final IEncoderPlugin attr(String key, double val) throws IOException
  {
    return plugin.attr(key, val);
  }

  /**
   * Create a String key/value pair attribute
   */
  public final IEncoderPlugin attr(String key, String str) throws IOException
  {
    return plugin.attr(key, str);
  }

  /**
   * Create a safe key/value pair attribute
   */
  public final IEncoderPlugin attrSafe(String key, String str) throws IOException
  {
    return plugin.attrSafe(key, str);
  }

  /**
   * Create a comment (used in XML encoding)
   */
  public final IEncoderPlugin comment(String text) throws IOException
  {
    return plugin.comment(text);
  }

  /**
   * Increment the current indent count (used in XML encoding)
   */
  public final IEncoderPlugin incrementIndent() throws IOException
  {
    return plugin.incrementIndent();
  }

  /**
   * Decrement the current indent count (used in XML encoding)
   */
  public final IEncoderPlugin decrementIndent() throws IOException
  {
    return plugin.decrementIndent();
  }

  /**
   * Indent according to the current indent count (used in XML encoding)
   */
  public final IEncoderPlugin indent() throws IOException
  {
    return plugin.indent();
  }

  /**
   * @return the current indent count
   */
  public final int getIndent()
  {
    return plugin.getIndent();
  }

  /**
   * Create new line (used in XML encoding)
   */
  public final IEncoderPlugin newLine() throws IOException
  {
    return plugin.newLine();
  }

  /**
   * Return if this encoder is being used to generate a PKZIP file
   * containing the XML document. See <code>setZipped()</code>
   * <p>
   * This feature applies to XML encoding
   */
  public boolean isZipped()
  {
    return plugin.isZipped();
  }

  /**
   * If set to true, then the encoder generates a compressed PKZIP
   * file with one entry called "file.xml".  This method cannot be
   * called once bytes have been written.  Zipped encoders should
   * only be used with stand alone files, it should not be used in
   * streams mixed with other data.  This feature is used in conjunction
   * with XParser, which automatically detects plain text XML versus
   * PKZIP documents.
   * <p>
   * This feature applies to XML encoding
   */
  public void setZipped(boolean zipped)
    throws IOException
  {
    plugin.setZipped(zipped);
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * This callback is made after encoding a BComponent, but
   * before the end tag is written.  It gives a subclass a
   * chance to encode additional information.  Subclasses
   * should not use any element names which would collide
   * with the standard encoding.
   */
  protected void encodingComponent(BComponent c)
    throws IOException
  {
  }

  /**
   * This callback is made after encoding a BComponent
   * start element for a stubbed component.  A stub
   * is written when the max depth has been reached
   * and this encoder is not recursing into the slots
   * of the specified component.
   */
  protected void encodingComponentStub(BComponent c)
    throws IOException
  {
  }

  /**
   * This callback is made just before a value is encoded.
   */
  protected void encodingValue(BValue val, Context cx)
    throws IOException
  {
  }

  /**
   * This callback is made when a Slot is encoded.
   */
  protected void encodingSlot(BComplex parent, Slot slot)
    throws IOException
  {
  }

  /**
   * This callback is made when facets are encoded.
   */
  protected void encodingFacets(BFacets facets)
    throws IOException
  {
  }
  
  /**
   * This callback is made to allow the subclass to encode the slot (and
   * children if any). Returning true tells the caller that the subclass has
   * encoded the children and that the caller should not do so.
   * 
   * @return true if encoding the value has been handled by the subclass
   * @throws IOException
   * 
   * @since Niagara 4.0
   */
  protected boolean encodePropertyValue(BComplex parent, Property prop, int depth, BPermissions permissions, Context context)
    throws IOException
  {
    return false;
  }

  /**
   * This callback is made after an encoder is closed, to allow
   * subclasses to release implementation specific resources.
   *
   * @throws IOException
   *
   * @since Niagara 4.10u3
   */
  protected void doClose()
    throws IOException
  {
  }

////////////////////////////////////////////////////////////////
// Encoding
////////////////////////////////////////////////////////////////

  @SuppressWarnings("ConstantConditions")
  private void encodeSlot(BComplex parent, Slot slot, int depth, BPermissions permissions)
    throws IOException
  {
    if (slot == null) return;

    // get slot flags
    int flags = parent.getFlags(slot);

    // skip transients if not encoding transients
    boolean flagsOnly = false;
    if (!encodeTransients && ((flags & Flags.TRANSIENT) != 0))
    {
      // if this is a frozen property with non-default flags, then
      // we make a special exception to just persist the new flag
      if (slot.isProperty() && slot.isFrozen() && flags != slot.getDefaultFlags())
      {
        flagsOnly = true;
      }
      else
      {
        return;
      }
    }

    // if property, then get value and see if it
    // is a non-component default (we must serialize
    // all components so that the handle persists)
    Property prop = null;
    BValue value = null;
    boolean skipValue;
    if (slot.isProperty() && !flagsOnly)
    {
      // cast to slot to property
      prop = (Property)slot;

      // check the special case of a frozen primitive with default
      // flags - if so, we can quickly write the element and short circuit
      // BSimple wrapper creation, and all the other crud that happens
      // in the rest of this method
      if (encodePrimitive(parent, prop, flags))
        return;

      // get the property value, and check if we might
      // be able to skip it - we can skip frozen properties
      // that haven't been changed from their default;
      // also make a special exception for control:NullProxyExt
      // since there tend to be a whole lot of them, and we
      // shouldn't ever care about writing it and maintain
      // its handle
      value = parent.get(prop);
      skipValue = canSkipEncodingProperty(parent, prop, value, permissions);
    }
    else
    {
      // assume actions and topics don't get written
      // unless they have had their flags set (this else
      // clause also handles flagsOnly)
      if (slot instanceof Property)
      {
        value = parent.get((Property)slot);
      }
      skipValue = true;
    }

    // do security check if we have a context
    if (context != null && context.getUser() != null)
    {
      // compute the security mask which tells us if we this slot
      // is accessible or if it should be forced as readonly
      int security = getSecurityMask(permissions, slot, value, flags);

      // if the slot isn't accessible
      if ((security & SECURITY_INACCESSIBLE) != 0)
      {
        if (slot.isFrozen())
        {
          // Don't revert the value back to default for frozen admin properties because that would
          // break px views, facets, etc.
          flags |= Flags.HIDDEN;
        }
        else return;
      }

      // force the readonly flag
      if ((security & SECURITY_READONLY) != 0)
        flags |= Flags.READONLY;
    }

    // we can skip frozen slots that have default flags & value
    if (flags == slot.getDefaultFlags() && skipValue)
      return;

    // is this a dynamic component
    boolean isDynamicComponent = slot.isDynamic() && value instanceof BComponent;

    // indent
    plugin.indent();

    // if this is a dynamic component add comment
    if (isDynamicComponent && encodeComments)
    {
      SlotPath path = ((BComponent)value).getSlotPath();
      if (path != null)
      {
        plugin.comment(path.getBody());
        plugin.indent();
      }
    }

    // write out p, a, or t element start
    if (slot.isProperty()) plugin.start("p");
    else if (slot.isAction()) plugin.start("a");
    else if (slot.isTopic()) plugin.start("t");
    else throw new IllegalStateException();

    // name
    wname(slot.getName());

    // give subclasses their callback
    encodingSlot(parent, slot);

    // flags if necessary
    if ( ((slot.isDynamic() && flags != 0) || (flags != slot.getDefaultFlags()))
         && parent.isComponent())
      wflags(flags);

    // facets if necessary
    BFacets facets = slot.getFacets();
    if (slot.isDynamic() && !facets.isNull())
      wfacets(facets);

    // if component
    if (value instanceof BComponent)
    {
      BComponent comp = (BComponent)value;

      // handle if not null
      Object handle = comp.getHandle();
      if (handle != null) whandle(handle);

      // categories if not null
      BCategoryMask cats = comp.getCategoryMask();
      if (!cats.isNull()) wcategories(cats);

      // stub attribute if we've reached depth limit
      // 12/15/06 - S. Hoye added check for isBrokerPropsLoaded, as this
      // is needed for the virtual partial loading case.
      if ((depth == 0) ||
          ((comp.getComponentSpace() instanceof BVirtualComponentSpace) &&
          (!((ComponentSlotMap)comp.fw(Fw.SLOT_MAP)).isBrokerPropsLoaded())))
      {
        plugin.attr("stub", true);
      }
    }

    // write property if necessary
    if (prop != null && !skipValue)
    {
      wtype(value);

      Context mergedContext = getMergedContext(parent, prop, getContext());
  
      final int encodeDepth = depth - 1;
      
      // let the subclass encode the property if it wants to
      boolean handledBySubclass =  encodePropertyValue(parent, prop, encodeDepth, permissions, mergedContext);

      if (!handledBySubclass)
      {
        // use the default encoder to encode the value
        encodeValue(value, encodeDepth, permissions, mergedContext);
      }
    }
    else
    {
      plugin.end().newLine();
    }
  }

  private boolean encodePrimitive(BComplex parent, Property prop, int flags)
    throws IOException
  {
    if (prop.isDynamic() || flags != prop.getDefaultFlags()) return false;
    if (prop.getTypeAccess() == Slot.BOBJECT_TYPE) return false;
    if (isSyncEncoder()) return false;

    String s;
    boolean safe = false;
    switch(prop.getTypeAccess())
    {
      case Slot.BOOLEAN_TYPE:
        boolean b = parent.getBoolean(prop);
        if (b == ((BBoolean)prop.getDefaultValue()).getBoolean()) return true;
        s = BBoolean.encode(b);
        break;
      case Slot.INT_TYPE:
        int i = parent.getInt(prop);
        if (i == ((BInteger)prop.getDefaultValue()).getInt()) return true;
        s = BInteger.encode(i);
        break;
      case Slot.LONG_TYPE:
        long l = parent.getLong(prop);
        if (l == ((BLong)prop.getDefaultValue()).getLong()) return true;
        s = BLong.encode(l);
        break;
      case Slot.FLOAT_TYPE:
        float f = parent.getFloat(prop);
        if (f == ((BFloat)prop.getDefaultValue()).getFloat()) return true;
        s = BFloat.encode(f);
        break;
      case Slot.DOUBLE_TYPE:
        double d = parent.getDouble(prop);
        if (d == ((BDouble)prop.getDefaultValue()).getDouble()) return true;
        s = BDouble.encode(d);
        break;
      case Slot.STRING_TYPE:
        s = parent.getString(prop);
        safe = true;
        if (Objects.equals(s, ((BString)prop.getDefaultValue()).getString())) return true;
        break;
      default:
        throw new IllegalStateException(""+prop.getTypeAccess());
    }

    plugin.indent().start("p");
    wname(prop.getName());

    if (safe)
      plugin.attrSafe("v", s);
    else
      plugin.attr("v", s);

    encodingValue(parent.get(prop), getMergedContext(parent, prop, getContext()));

    plugin.end().newLine();
    return true;
  }

  private void encodeValue(BValue value, int depth, BPermissions permissions, Context cx)
    throws IOException
  {
    // give subclasses their callback
    encodingValue(value, cx);
    
    // If the Type is black listed and a Simple then just get it's default instance.
    if (isTypeBlackListed(value.getType()) && value.isSimple())
      value = (BValue)value.getType().getInstance();
    
    if (value.isSimple())
    {
      wvalue((BSimple)value);
      plugin.end().newLine();
    }
    else if (value.isComponent() && depth < 0)
    {
      plugin.endAttr();
      encodingComponentStub((BComponent)value);
      plugin.end("p").newLine();
    }
    else
    {
      // if this is a component, then we need to dive
      // down and use its permissions for the slots
      if (value.isComponent())
        permissions = getPermissionsFor(value.asComponent());

      // encode a select number of structs specially
      // to create compact elements and as a means of
      // unrolling the complex property iteration
      if (encodeStructValue(value)) return;

      // close my start tag so I can write sub-elements
      plugin.endAttr().newLine().incrementIndent();
      BComplex complex = (BComplex)value;

      // encode the slots
      SlotCursor<Slot> c = complex.getSlots();
      boolean first = true;
      while(c.next())
      {
        if (first)
        {
          plugin.startArray("s");
          first = false;
        }

        encodeSlot(complex, c.slot(), depth, permissions);
      }

      if (!first) plugin.endArray();

      // give subclasses their callback
      if (complex.isComponent())
        encodingComponent(complex.asComponent());

      plugin.decrementIndent().indent().end("p").newLine();
    }
  }

  /**
   * Return true if encoding the property can be skipped.
   *
   * @param complex The complex the property is a member of.
   * @param prop The property to test.
   * @param value The value of the property.
   * @param permissions The security permissions.
   * @return Returns true if the property should be skipped.
   */
  @SuppressWarnings("unused")
  protected boolean canSkipEncodingProperty(BComplex complex,
                                            Property prop,
                                            BValue value,
                                            BPermissions permissions)
  {
    // By default, never skip encoding dynamic properties.
    if (!prop.isFrozen())
    {
      return false;
    }

    if (value.isComponent())
    {
      return !isSyncEncoder() && value.getType().getTypeInfo() == nullProxyExt;
    }
    else
    {
      return prop.isEquivalentToDefaultValue(value);
    }
  }

////////////////////////////////////////////////////////////////
// Optimizations
////////////////////////////////////////////////////////////////

  private boolean encodeStructValue(BValue value)
    throws IOException
  {
    if (value instanceof BStatusValue) return encodeStatusValue(value);
    if (value instanceof BLink) return encodeLink(value);
    return value instanceof BRelation && encodeRelation(value);
  }

  private boolean encodeLink(BValue value)
    throws IOException
  {
    if (value.getType() != BLink.TYPE) return false;

    BLink link = (BLink)value;

    plugin.endAttr().startArray("s");

    // endpoint ORD
    plugin.start("p").attr("n", "sourceOrd").attrSafe("v", link.getEndpointOrd().encodeToString());
    encodingSlot(link, BRelation.sourceOrd);
    encodingValue(link.getEndpointOrd(), getMergedContext(link, BRelation.sourceOrd, getContext()));
    plugin.end();

    // relation Tags
    plugin.start("p").attr("n", "relationTags").attrSafe("v", link.getRelationTags().encodeToString());
    encodingSlot(link, BRelation.relationTags);
    encodingValue(link.get(BRelation.relationTags), getMergedContext(link, BRelation.relationTags, getContext()));
    plugin.end();

    // relation Id
    plugin.start("p").attr("n", "relationId").attrSafe("v", link.getRelationId());
    encodingSlot(link, BRelation.relationId);
    encodingValue(link.get(BRelation.relationId), getMergedContext(link, BRelation.relationId, getContext()));
    plugin.end();

    // Source Slot Name
    plugin.start("p").attr("n", "sourceSlotName").attrSafe("v", link.getSourceSlotName());
    encodingSlot(link, BLink.sourceSlotName);
    encodingValue(link.get(BLink.sourceSlotName), getMergedContext(link, BLink.sourceSlotName, getContext()));
    plugin.end();

    // Target Slot Name
    plugin.start("p").attr("n", "targetSlotName").attrSafe("v", link.getTargetSlotName());
    encodingSlot(link, BLink.targetSlotName);
    encodingValue(link.get(BLink.targetSlotName), getMergedContext(link, BLink.targetSlotName, getContext()));
    plugin.end();

    if (!link.getEnabled())
    {
      plugin.start("p").attr("n", "enabled").attr("v", false);
      encodingSlot(link, BLink.enabled);
      encodingValue(link.get(BLink.enabled), getMergedContext(link, BLink.enabled, getContext()));
      plugin.end();
    }

    plugin.endArray().end("p").newLine();
    return true;
  }

  private boolean encodeRelation(BValue value)
    throws IOException
  {
    if (value.getType() != BRelation.TYPE) return false;

    BRelation bRelation = (BRelation)value;

    plugin.endAttr().startArray("s");

    // endpoint ORD
    plugin.start("p").attr("n", "sourceOrd").attrSafe("v", bRelation.getEndpointOrd().encodeToString());
    encodingSlot(bRelation, BRelation.sourceOrd);
    encodingValue(bRelation.getEndpointOrd(), getMergedContext(bRelation, BRelation.sourceOrd, getContext()));
    plugin.end();

    // relation Tags
    plugin.start("p").attr("n", "relationTags").attrSafe("v", bRelation.getRelationTags().encodeToString());
    encodingSlot(bRelation, BRelation.relationTags);
    encodingValue(bRelation.get(BRelation.relationTags), getMergedContext(bRelation, BRelation.relationTags, getContext()));
    plugin.end();

    // relation Id
    plugin.start("p").attr("n", "relationId").attrSafe("v", bRelation.getRelationId());
    encodingSlot(bRelation, BRelation.relationId);
    encodingValue(bRelation.get(BRelation.relationId), getMergedContext(bRelation, BRelation.relationId, getContext()));
    plugin.end();

    plugin.endArray().end("p").newLine();
    return true;
  }

  private boolean encodeStatusValue(BValue value)
    throws IOException
  {
    BStatus status;
    if (value.getType() == BStatusNumeric.TYPE)
    {
      BStatusNumeric x = (BStatusNumeric)value;
      plugin.endAttr().startArray("s").start("p").attr("n", "value").attr("v", BDouble.encode(x.getValue()));
      encodingSlot(x, BStatusNumeric.value);
      encodingValue(x.get(BStatusNumeric.value), getMergedContext(x, BStatusNumeric.value, getContext()));
      plugin.end();
      status = x.getStatus();
    }
    else if (value.getType() == BStatusBoolean.TYPE)
    {
      BStatusBoolean x = (BStatusBoolean)value;
      plugin.endAttr().startArray("s").start("p").attr("n", "value").attr("v", BBoolean.encode(x.getValue()));
      encodingSlot(x, BStatusBoolean.value);
      encodingValue(x.get(BStatusBoolean.value), getMergedContext(x, BStatusBoolean.value, getContext()));
      plugin.end();
      status = x.getStatus();
    }
    else if (value.getType() == BStatusEnum.TYPE)
    {
      BStatusEnum x = (BStatusEnum)value;
      plugin.endAttr().startArray("s").start("p").attr("n", "value").attr("v", x.getValue().encodeToString());
      encodingSlot(x, BStatusEnum.value);
      encodingValue(x.get(BStatusEnum.value), getMergedContext(x, BStatusEnum.value, getContext()));
      plugin.end();
      status = x.getStatus();
    }
    else if (value.getType() == BStatusString.TYPE)
    {
      BStatusString x = (BStatusString)value;
      plugin.endAttr().startArray("s").start("p").attr("n", "value").attrSafe("v", x.getValue());
      encodingSlot(x, BStatusString.value);
      encodingValue(x.get(BStatusString.value), getMergedContext(x, BStatusString.value, getContext()));
      plugin.end();
      status = x.getStatus();
    }
    else
    {
      return false;
    }

    if (!status.equals(BStatus.DEFAULT))
    {
      plugin.start("p").attr("n", "status").attrSafe("v", status.encodeToString());
      encodingSlot(value.asComplex(), BStatusValue.status);
      encodingValue(status, getMergedContext(value.asComplex(), BStatusValue.status, getContext()));
      plugin.end();
    }

    plugin.endArray().end("p").newLine();
    return true;
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////

  /**
   * Given a BComponent, return the set of permissions
   * available based on the current context.  If context
   * doesn't contain a user, then this method returns
   * <code>BPermissions.all</code>.
   */
  public BPermissions getPermissionsFor(BComponent c)
  {
    // bail if no context
    if (context == null) return BPermissions.all;

    // bail if context doesn't have a user
    BUser user = context.getUser();
    if (user == null) return BPermissions.all;

    // cache permissions for a specific component
    // because they are expensive to compute
    if (permissionsCache == null) permissionsCache = new HashMap<>();
    BPermissions p = permissionsCache.get(c);
    if (p == null)
    {
      p = c.getPermissions(context);
      permissionsCache.put(c, p);
    }
    return p;
  }

  /**
   * Given a slot and a set of permissions return a mask that
   * tells us if a) this slot is inaccessible or b) this slot
   * is readonly.
   */
  private int getSecurityMask(BPermissions permissions, Slot slot, BValue value, int flags)
  {
    // these the values we computing
    boolean inaccessible = false;
    boolean readonly = false;

    // if this is a component, then we check it's own
    // permissions rather than using its parents
    if (slot.isProperty() && value.isComponent())
    {
      if (!getPermissionsFor(value.asComponent()).has(BPermissions.operatorRead))
        inaccessible = true;
    }

    // otherwise we see if we use invoke permissions if this
    // if an action (this must be checked before properties
    // because dynamic actions are both)
    else if (slot.isAction())
    {
      if ((flags & Flags.OPERATOR) != 0)
      {
        if (!permissions.has(BPermissions.operatorInvoke))
          inaccessible = true;
      }
      else
      {
        if (!permissions.has(BPermissions.adminInvoke))
          inaccessible = true;
      }
    }

    // otherwise we check read/write permissions
    else
    {
      if ((flags & Flags.OPERATOR) != 0)
      {
        if (!permissions.has(BPermissions.operatorRead))
          inaccessible = true;
        else if (!permissions.has(BPermissions.operatorWrite))
          readonly = true;
      }
      else
      {
        if (!permissions.has(BPermissions.adminRead))
          inaccessible = true;
        else if (!permissions.has(BPermissions.adminWrite))
          readonly = true;
      }
    }

    // return result as a mask
    /*System.out.println("getSecurityMask " + permissions + " " + slot.getName() + " inaccessible=" + inaccessible + " readonly=" + readonly);*/
    int mask = 0;
    if (inaccessible) mask |= SECURITY_INACCESSIBLE;
    if (readonly) mask |= SECURITY_READONLY;
    return mask;
  }

////////////////////////////////////////////////////////////////
// Attribute Pairs
////////////////////////////////////////////////////////////////

  private ValueDocEncoder wname(String name)
    throws IOException
  {
    plugin.attr("n", name);
    return this;
  }

  private ValueDocEncoder wtype(BObject object)
    throws IOException
  {
    plugin.encodeType(object.getType());
    return this;
  }

  private ValueDocEncoder wflags(int flags)
    throws IOException
  {
    plugin.attr("f", Flags.encodeToString(flags));
    return this;
  }

  private ValueDocEncoder whandle(Object handle)
    throws IOException
  {
    plugin.attr("h", handle.toString());
    return this;
  }

  private ValueDocEncoder wcategories(BCategoryMask cats)
    throws IOException
  {
    plugin.attr("c", cats.encodeToString());
    return this;
  }

  private ValueDocEncoder wfacets(BFacets facets)
    throws IOException
  {
    encodingFacets(facets);
    plugin.attrSafe("x", facets.encodeToString());
    return this;
  }

  private ValueDocEncoder wvalue(BSimple simple)
    throws IOException
  {
    try
    {
      String s = encodeSimple(simple);
      plugin.attrSafe("v", s);
    }
    catch(Exception e)
    {
      if (!plugin.processEncodingException(simple, e))
      {
        unhandledEncodingExceptions.add(e);
        String s = "?";
        try
        {
          s = simple.toString();
        }
        catch (Exception ignored)
        {
        }
        log.log(Level.SEVERE, "Encoding " + simple.getType() + " \"" + s + "\"", e);

        attr("err", simple.getType() + ".encodeToString()");
      }
    }
    return this;
  }

  protected String encodeSimple(BSimple simple)
    throws IOException
  {
    if (simple instanceof BIContextEncodable)
      return ((BIContextEncodable)simple).encodeToString(context);
    
    return simple.encodeToString();
  }
  
  /**
   * Return true if the Type is blacklisted.
   * <p>
   * Currently this is limited to Simple Types.
   * 
   * @param type the Type to check.
   * @return true if the value is black listed and shouldn't be encoded.
   */
  public boolean isTypeBlackListed(Type type)
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Encoder Plugin
////////////////////////////////////////////////////////////////

  /**
   * The document format can vary by objects implementing this interface
   */
  public interface IEncoderPlugin
    extends AutoCloseable
  {
    IEncoderPlugin encodeDocument(ValueDocEncoder encoder, BValue value) throws IOException;

    IEncoderPlugin start(String name) throws IOException;

    IEncoderPlugin startArray(String name) throws IOException;
    IEncoderPlugin endArray() throws IOException;

    IEncoderPlugin end() throws IOException;
    IEncoderPlugin end(String name) throws IOException;
    IEncoderPlugin endAttr() throws IOException;

    IEncoderPlugin key(String key) throws IOException;
    IEncoderPlugin attr(String key, boolean val) throws IOException;
    IEncoderPlugin attr(String key, double val) throws IOException;
    IEncoderPlugin attr(String key, String str) throws IOException;
    IEncoderPlugin attrSafe(String key, String str) throws IOException;

    /**
     * Encoder will call this method when the encoding of an object fails with an exception
     *
     * @param object object whose encoding has failed
     * @param e exception that was thrown as a result of the encoding failure
     * @return true if the exception was handled by the plugin and doesn't need additional handling
     *         by the encoder.   If false, the exception is unhandled and will be logged and added
     *         to a list that can be retrieved using {@link ValueDocEncoder#getUnhandledEncodingExceptions()}
     *         after encoding completes
     * @throws IOException will be thrown if the plugin determines that the entire encoding operation
     *         should fail
     *
     * @since Niagara 4.0
     */
    default boolean processEncodingException(BObject object, Exception e) throws IOException
    {
      throw e instanceof IOException ? (IOException)e : new IOException(e);
    }

    IEncoderPlugin value(String str) throws IOException;

    IEncoderPlugin comment(String text) throws IOException;

    IEncoderPlugin incrementIndent() throws IOException;
    IEncoderPlugin decrementIndent() throws IOException;
    IEncoderPlugin indent() throws IOException;
    int getIndent();
    IEncoderPlugin newLine() throws IOException;

    IEncoderPlugin encodeType(Type type) throws IOException;

    void flush() throws IOException;
    @Override
    void close() throws IOException;

    boolean isZipped();
    void setZipped(boolean zipped) throws IOException;

    default Version version()
    {
      return Version.NULL;
    }
    default void setVersion(Version version) throws IOException
    {
      throw new UnsupportedOperationException();
    }
  }

////////////////////////////////////////////////////////////////
// BOG (Baja Object Graph) Encoder Plug-in
////////////////////////////////////////////////////////////////

  public static final class BogEncoderPlugin
      implements IEncoderPlugin
  {
    public BogEncoderPlugin(File file) throws IOException
    {
      this(file, null);
    }

    public BogEncoderPlugin(OutputStream out) throws IOException
    {
      this(out, null);
    }

    public BogEncoderPlugin(File file, Context pluginContext) throws IOException
    {
      w = new XWriter(file);
      initPasswordHandling(pluginContext);
    }

    public BogEncoderPlugin(OutputStream out, Context pluginContext) throws IOException
    {
      w = new XWriter(out);
      initPasswordHandling(pluginContext);
    }

    private void initPasswordHandling(Context pluginContext) throws IOException
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
            bogPasswordObjectEncoder = AccessController.doPrivileged(
              (PrivilegedExceptionAction<BogPasswordObjectEncoder>) () ->
                BogPasswordObjectEncoder.make(pContext.getEncryptionKey().get()));
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

    @Override
    public IEncoderPlugin encodeDocument(ValueDocEncoder encoder, BValue value) throws IOException
    {
      w.w("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

      if (!this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
      {
        PasswordEncodingContext pContext = PasswordEncodingContext.from(encoder.context);


        if (this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.none))
        {
          try
          {
            AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() ->
            {
              pContext.setEncryptionKey(EncryptionKeySource.none, Optional.empty());
              return null;
            });
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
        else if (this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.shared))
        {
          try
          {
            AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() ->
            {
              pContext.setEncryptionKey(EncryptionKeySource.external, Optional.of(bogPasswordObjectEncoder.passPhraseToKey(null)));
              return null;
            });
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
        else if (this.passPhrase.isPresent())
        {
          try
          {
            AccessController.doPrivileged((PrivilegedExceptionAction<Void>)() ->
            {
              try (SecretChars secretChars = passPhrase.get().getSecretChars())
              {
                bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeExternal(secretChars);

                // We have an encryption key, so it will be used to encode all passwords that use reversible encoders,
                // even if those encoders used a different key to load this file

                pContext.setEncryptionKey(EncryptionKeySource.external, Optional.of(bogPasswordObjectEncoder.getPassPhraseEncodingKey()));
                return null;
              }
            });
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
        else if (this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.external))
        {
          try
          {
            AccessController.doPrivileged((PrivilegedExceptionAction<Void>)()->
              {
                if (this.bogPasswordObjectEncoder.getPassPhraseEncodingKey() == null)
                {
                  pContext.setEncryptionKey(EncryptionKeySource.external, Optional.empty());
                }
                else
                {
                  pContext.setEncryptionKey(EncryptionKeySource.external, Optional.of(this.bogPasswordObjectEncoder.getPassPhraseEncodingKey()));
                }
                return null;
              });
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
        if (encoder.context == null) encoder.context = pContext;
      }

      getBogPasswordObjectEncoder().writeBogHeader(w, version.toString());

      encoder.encode(value);
      w.w("</bajaObjectGraph>\n");
      return this;
    }

    @Override
    public IEncoderPlugin start(String name) throws IOException
    {
      w.w("<").w(name);
      return this;
    }

    @Override
    public IEncoderPlugin startArray(String name) throws IOException { return this; }
    @Override
    public IEncoderPlugin endArray() throws IOException { return this; }

    @Override
    public IEncoderPlugin end() throws IOException
    {
      w.w("/>");
      return this;
    }

    @Override
    public IEncoderPlugin end(String name) throws IOException
    {
      w.w("</").w(name).w(">");
      return this;
    }

    @Override
    public IEncoderPlugin endAttr() throws IOException
    {
      w.w(">");
      return this;
    }

    @Override
    public IEncoderPlugin newLine() throws IOException
    {
      w.w("\n");
      return this;
    }

    @Override
    public IEncoderPlugin key(String key) throws IOException
    {
      // Do nothing for encoding single attribute values
      return this;
    }

    @Override
    public IEncoderPlugin attr(String key, boolean val) throws IOException
    {
      attr(key, String.valueOf(val));
      return this;
    }

    @Override
    public IEncoderPlugin attr(String key, double val) throws IOException
    {
      attr(key, String.valueOf(val));
      return this;
    }

    @Override
    public IEncoderPlugin attr(String key, String str) throws IOException
    {
      w.w(" ").w(key).w("=\"").w(str).w("\"");
      return this;
    }

    @Override
    public IEncoderPlugin attrSafe(String key, String str) throws IOException
    {
      w.w(" ").w(key).w("=\"").safe(str).w("\"");
      return this;
    }

    /**
     * Returns true if an exception thrown while encoding a single object should cause the entire
     * operation to fail.   The value defaults to true, and can be modified using {@link #setFailOnEncodingExceptions(boolean)}
     *
     * @since Niagara 4.0
     */
    public boolean failOnEncodingExceptions()
    {
      return failOnEncodingExceptions;
    }

    /**
     * Configure the plugin to determine whether encoding exceptions cause immediate failures
     *
     * @since Niagara 4.0
     */
    public void setFailOnEncodingExceptions(boolean value)
    {
      failOnEncodingExceptions = value;
    }

    /**
     * If {@link #setFailOnEncodingExceptions(boolean)} has been used to override the default of true,
     * then return false, otherwise throw an {@link IOException} that wraps the given exception
     *
     * @param object object whose encoding has failed
     * @param e exception that was thrown as a result of the encoding failure
     * @return true if the exception was handled by the plugin and doesn't need additional handling
     *         by the encoder.   If false, the exception is unhandled and will be logged and added
     *         to a list that can be retrieved using {@link ValueDocEncoder#getUnhandledEncodingExceptions()}
     *         after encoding completes
     * @throws IOException will be thrown if the plugin determines that the entire encoding operation
     *         should fail
     *
     * @since Niagara 4.0
     */
    @Override
    public boolean processEncodingException(BObject object, Exception e)
      throws IOException
    {
      if (failOnEncodingExceptions())
      {
        throw e instanceof IOException ? (IOException)e : new IOException(e);
      }
      else
      {
        return false;
      }
    }

    @Override
    public IEncoderPlugin value(String str) throws IOException
    {
      // Do nothing for encoding values
      return this;
    }

    @Override
    public IEncoderPlugin comment(String text) throws IOException
    {
      w.w("<!-- ").w(text).w(" -->\n");
      return this;
    }

    @Override
    public IEncoderPlugin incrementIndent() throws IOException
    {
      ++indent;
      return this;
    }

    @Override
    public IEncoderPlugin decrementIndent() throws IOException
    {
      --indent;
      return this;
    }

    @Override
    public IEncoderPlugin indent() throws IOException
    {
      w.indent(indent);
      return this;
    }

    @Override
    public int getIndent()
    {
      return indent;
    }

    @Override
    public IEncoderPlugin encodeType(Type type) throws IOException
    {
      BModule module = type.getModule();
      String key = modules.get(module.getModuleName());

      // if we don't have a key for this module yet,
      // generate one and write out the module attribute
      if (key == null)
      {
        key = newModuleKey(module);
        attr("m", key + "=" + module.getModuleName());
      }

      attr("t", key + ":" + type.getTypeName());
      return this;
    }

    @Override
    public void flush() throws IOException
    {
      w.flush();
    }

    @Override
    public void close() throws IOException
    {
      w.close();
      bogPasswordObjectEncoder.close();
    }

    @Override
    public boolean isZipped()
    {
      return w.isZipped();
    }

    @Override
    public void setZipped(boolean zipped) throws IOException
    {
      w.setZipped(zipped);
    }

    @Override
    public Version version()
    {
      return version;
    }

    @Override
    public void setVersion(Version version) throws IOException
    {
      this.version = new Version(version.toString());
    }

    private String newModuleKey(BModule module)
    {
      String key = module.getPreferredSymbol();
      for(int i=0; keys.get(key) != null; ++i)
        key = "" + key.charAt(0) + i;

      keys.put(key, key);
      modules.put(module.getModuleName(), key);
      return key;
    }

    public void setBogPasswordObjectEncoder(BogPasswordObjectEncoder value)
    {
      bogPasswordObjectEncoder = value;
    }

    public BogPasswordObjectEncoder getBogPasswordObjectEncoder()
    {
      return bogPasswordObjectEncoder;
    }

    public void setPassPhrase(Optional<BPassword> value)
    {
      passPhrase = value;
    }

    /**
     * Marshal the specified value into an XML String.
     * This XML is not a full document - see encode().
     */
    public static String marshal(BValue value) throws IOException
    {
      return marshal(value, PasswordEncodingContext.makeNone());
    }

    /**
     * Marshal the specified value into an XML String.
     * This XML is not a full document - see encode().
     * 
     * @since Niagara 4.0
     */
    public static String marshal(BValue value, Context cx) throws IOException
    {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ValueDocEncoder encoder = new ValueDocEncoder(new BogEncoderPlugin(out, cx), cx);
      encoder.encode(value);
      encoder.close();
      return new String(out.toByteArray());
    }

    public XWriter getWriter()
    {
      return w;
    }

    // This is used to keep track of current indentation.
    protected int indent = 0;
    private final XWriter w;

    private final HashMap<String, String> modules = new HashMap<>(33);
    private final HashMap<String, String> keys = new HashMap<>(33);
    private BogPasswordObjectEncoder bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeNone();
    private Optional<BPassword> passPhrase = Optional.empty();  // TODO: can we use SecretBytes here?
    private boolean failOnEncodingExceptions = true;

    protected Version version = BOG_VERSION_4;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Version BOG_VERSION_1 = new Version("1.0");
  public  static final Version BOG_VERSION_4 = new Version("4.0");
  private static final int SECURITY_INACCESSIBLE = 0x01;
  private static final int SECURITY_READONLY     = 0x02;
  private static final Logger log = Logger.getLogger("sys.xml");

  private List<Exception> unhandledEncodingExceptions = new ArrayList<>();

  private static TypeInfo nullProxyExt = null;
  static
  {
    try { nullProxyExt = Sys.getRegistry().getType("control:NullProxyExt"); } catch(Exception ignored) {}
  }

  private boolean encodeTransients;
  private boolean encodeComments = true;
  private Context context;
  private HashMap<BComponent, BPermissions> permissionsCache;

  protected final IEncoderPlugin plugin;
}
