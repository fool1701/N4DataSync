/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

import java.io.File;
import java.io.IOException;
import com.tridium.nre.util.IElement;

/**
 * XElem models a XML Element construct.
 *
 * @author    Brian Frank on 6 Apr 02
 * @since     Baja 1.0
 */
public class XElem
  extends XContent
  implements IElement
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a new element and define a new namespace
   * attribute for the element using the specified
   * prefix and uri.
   *
   * @param nsPrefix namespace prefix
   * @param nsUri namespace URI
   * @param name element name
   */
  public XElem(String nsPrefix, String nsUri, String name)
  {
    this.ns = defineNs(nsPrefix, nsUri);
    this.name = name;
  }

  /**
   * Create a new element with the specified namespace and local name.
   *
   * @param ns Namespace
   * @param name element name
   */
  public XElem(XNs ns, String name)
  {
    this.ns = ns;
    this.name = name;
  }

  /**
   * Convenience for {@code XElem(null, name)}.
   *
   * @param name element name
   */
  public XElem(String name)
  {
    this(null, name);
  }

  /**
   * Convenience for {@code XElem(null, "unnamed")}.
   */
  public XElem()
  {
    this(null, "unnamed");
  }

////////////////////////////////////////////////////////////////
// Name
////////////////////////////////////////////////////////////////

  /**
   * Get the namespace for this element.  If no namespace
   * has been defined for this element then return null.
   *
   * @return element namespace, or null
   */
  public final XNs ns()
  {
    return ns;
  }

  /**
   * Return {@code ns().prefix()} or null if no
   * namespace is defined for this element.
   *
   * @return namespace prefix, or null
   */
  public final String prefix()
  {
    if (ns == null) return null;
    return ns.prefix;
  }

  /**
   * Return {@code ns().uri()} or null if no
   * namespace is defined for this element.
   *
   * @return namespace uri, or null
   */
  public final String uri()
  {
    if (ns == null) return null;
    return ns.uri;
  }

  /**
   * Get the local name of the element (without prefix).
   *
   * @return element name
   */
  public final String name()
  {
    return name;
  }

  /**
   * Get the qualified name of the element.  If this element
   * has no namespace or is in the default namespace return
   * the local name.  Otherwise return {@code <prefix>:<name>}.
   *
   * @return fully qualified element name
   */
  public final String qname()
  {
    if (ns == null || ns.prefix.equals("")) return name;
    return ns.prefix + ':' + name;
  }

////////////////////////////////////////////////////////////////
// Name Modification
////////////////////////////////////////////////////////////////

  /**
   * Check is this element defines a namespace for the
   * specified uri.  If we find one, then return an
   * XNs instance for it, otherwise return null.
   *
   * @param uri Uri to search namespaces for
   * @return Namespace for {@code uri}, or null
   */
  public final XNs findNs(String uri)
  {
    if (ns != null && ns.uri.equals(uri))
      return ns;

    int attrSize = attrSize();
    for(int i=0; i<attrSize; ++i)
    {
      if (uri.equals(attrValue(i)))
      {
        String name = attrName(i);
        String prefix;
        if (name.equals("xmlns"))
          prefix = "";
        else if (name.startsWith("xmlns:"))
          prefix = name.substring(6);
        else
          throw new XException("Invalid xmlns: " + name, this);
        return new XNs(prefix, uri);
      }
    }

    return null;
  }

  /**
   * Set this element's namespace.
   *
   * @param ns new namespace
   * @return the same namespace passed in (for builder-style calls)
   */
  public final XNs setNs(XNs ns)
  {
    this.ns = ns;
    return ns;
  }

  /**
   * Set this element's local name.
   *
   * @param name the new name for this element
   */
  public final void setName(String name)
  {
    if (name == null) throw new NullPointerException();
    this.name = name;
  }

  /**
   * Define the default namespace for this element and
   * add the {@code xmlns} attribute.  Return the XNs instance
   * used to identify the namespace.  Note this does not put
   * this XElem in the namespace unless setNs() is called.
   *
   * @param uri uri of the namespace
   * @return newly defined namespace
   */
  public XNs defineDefaultNs(String uri)
  {
    return defineNs("", uri);
  }

  /**
   * Define the a new namespace for this element and
   * add the {@code xmlns:<prefix>} attribute.  Return the XNs
   * instance used to identify the namespace.  Note this
   * does not put this XElem in the namespace unless setNs()
   * is called.
   *
   * @param prefix prefix for the namespace
   * @param uri uri of the namespace
   * @return newly defined namespace
   */
  public XNs defineNs(String prefix, String uri)
  {
    return defineNs(new XNs(prefix, uri));
  }

  /**
   * Define a namespace for this element by adding the
   * {@code xmlns:<prefix>} attribute.  Return the ns instance
   * passed as the parameter.  Note this does not put this
   * XElem in the namespace unless setNs() is called.
   *
   * @param ns new namespace
   * @return the same namespace
   */
  public XNs defineNs(XNs ns)
  {
    String name = ns.prefix.equals("") ?
                  "xmlns" :
                  "xmlns:" + ns.prefix;

    setAttr(name, ns.uri);

    return ns;
  }

////////////////////////////////////////////////////////////////
// Attribute Access
////////////////////////////////////////////////////////////////

  /**
   * Get the number of attributes in this element.
   *
   * @return number of attributes for this element
   */
  public final int attrSize()
  {
    return attrSize;
  }

  /**
   * Get the attribute namespace for the specified index.
   * Return null if no namespace is defined for the attribute.
   * Note that the special prefixes "xml" and "xmlns" return
   * null for their namespace, and are combined into the name.
   *
   * @param index Index (0..{@code attrSize() - 1}) of attribute
   * @return namespace for attribute
   * @throws ArrayIndexOutOfBoundsException if index &gt;= attrSize()
   */
  public final XNs attrNs(int index)
  {
    if (index >= attrSize) throw new ArrayIndexOutOfBoundsException(index);
    return (XNs)attr[index*3+1];
  }

  /**
   * Get the attribute name for the specified index.
   * Note that the special prefixes "xml" and "xmlns" return
   * null for their namespace, and are combined into the name.
   *
   * @param index Index (0..{@code attrSize() - 1}) of attribute
   * @return name of attribute
   * @throws ArrayIndexOutOfBoundsException if index &gt;= attrSize()
   */
  public final String attrName(int index)
  {
    if (index >= attrSize) throw new ArrayIndexOutOfBoundsException(index);
    return (String)attr[index*3];
  }

  /**
   * Get the attribute value for the specified index.
   *
   * @param index Index (0..{@code attrSize() - 1}) of attribute
   * @return value of attribute
   * @throws ArrayIndexOutOfBoundsException if index &gt;= attrSize()
   */
  public final String attrValue(int index)
  {
    if (index >= attrSize) throw new ArrayIndexOutOfBoundsException(index);
    return (String)attr[index*3+2];
  }

  /**
   * Get the index of the first attribute with the
   * specified namespace and local name or return -1
   * if no match.
   *
   * @param ns namespace to search
   * @param name name of attribute
   * @return index of attribute, or -1 if not found
   */
  public final int attrIndex(XNs ns, String name)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && XNs.equals(ns, attr[i+1]))
        return i/3;
    return -1;
  }

  /**
   * Get the index of the first attribute with the
   * specified name not in an explicit namespace.
   * Return -1 if no match.
   *
   * @param name name of attribute
   * @return index of attribute, or -1 if not found
   */
  public final int attrIndex(String name)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && attr[i+1] == null)
        return i/3;
    return -1;
  }

  /**
   * Get an attribute by the specified namespace and
   * local name.  If not found then throw XException.
   * Note that the specific prefixes "xml" and "xmlns"
   * are always combined into the name.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   * @return string value of attribute
   * @throws XException if an attribute with name {@code name} is not found
   */
  public final String get(XNs ns, String name)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && XNs.equals(ns, attr[i+1]))
        return (String)attr[i+2];
    throw new XException("Missing attr '" + name + "'", this);
  }

  /**
   * Get an attribute by the specified name not in an
   * explicit namespace.  If not found then throw XException.
   * Note that the specific prefixes "xml" and "xmlns"
   * are always combined into the name.
   *
   * @param name name of attribute
   * @return string value of attribute
   * @throws XException if an attribute with name {@code name} is not found
   */
  public final String get(String name)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && attr[i+1] == null)
        return (String)attr[i+2];
    throw new XException("Missing attr '" + name + "'", this);
  }

  /**
   * Get an attribute by the specified namespace and local
   * name.  If not found then return the given def value.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return string value of attribute, or {@code def}
   */
  public final String get(XNs ns, String name, String def)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && XNs.equals(ns, attr[i+1]))
        return (String)attr[i+2];
    return def;
  }

  /**
   * Get an attribute by the specified name.  If not
   * found then return the given def value.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return string value of attribute, or {@code def}
   */
  public final String get(String name, String def)
  {
    int len = attrSize*3;
    for(int i=0; i<len; i+=3)
      if (attr[i].equals(name) && attr[i+1] == null)
        return (String)attr[i+2];
    return def;
  }

  /**
   * Get a boolean attribute by the specified name.  If
   * not found then return throw an XException.  If the
   * value cannot be parsed as "true" or "false" then
   * throw an XException.
   *
   * @param name name of attribute
   * @return value of attribute coerced to boolean
   * @throws XException if attribute is not found or cannot be converted to boolean
   */
  public final boolean getb(String name)
  {
    String v = get(name);
    if (v.equals("true")) return true;
    if (v.equals("false")) return false;
    throw new XException("Invalid boolean attr '" + name + "'='" + v + "'", this);
  }

  /**
   * Get a boolean attribute by the specified name.  If
   * not found then return the given def value.  If the
   * value cannot be parsed as "true" or "false" then
   * throw an XException.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return value of attribute coerced to boolean, or def
   * @throws XException if attribute cannot be converted to boolean
   */
  public final boolean getb(String name, boolean def)
  {
    String v = get(name, null);
    if (v == null) return def;
    if (v.equals("true")) return true;
    if (v.equals("false")) return false;
    throw new XException("Invalid boolean attr '" + name + "'='" + v + "'", this);
  }

  /**
   * Get an int attribute by the specified name.  If
   * not found then return throw an XException.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @return value of attribute parsed as int
   * @throws XException if attribute is not found or cannot be parsed as int
   */
  public final int geti(String name)
  {
    String v = get(name);
    try
    {
      return Integer.parseInt(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid int attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get an int attribute by the specified name.  If
   * not found then return the given def value.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return value of attribute parsed as int
   * @throws XException if attribute cannot be parsed as int
   */
  public final int geti(String name, int def)
  {
    String v = get(name, null);
    if (v == null) return def;
    try
    {
      return Integer.parseInt(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid int attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a long attribute by the specified name.  If
   * not found then return throw an XException.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @return value of attribute parsed as long
   * @throws XException if attribute is not found or cannot be parsed as long
   */
  public final long getl(String name)
  {
    String v = get(name);
    try
    {
      return Long.parseLong(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid long attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a long attribute by the specified name.  If
   * not found then return the given def value.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return value of attribute parsed as long
   * @throws XException if attribute cannot be parsed as long
   */
  public final long getl(String name, long def)
  {
    String v = get(name, null);
    if (v == null) return def;
    try
    {
      return Long.parseLong(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid long attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a float attribute by the specified name.  If
   * not found then return throw an XException.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @return value of attribute parsed as float
   * @throws XException if attribute is not found or cannot be parsed as float
   */
  public final float getf(String name)
  {
    String v = get(name);
    try
    {
      return Float.parseFloat(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid float attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a float attribute by the specified name.  If
   * not found then return the given def value.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return value of attribute parsed as float
   * @throws XException if attribute cannot be parsed as float
   */
  public final float getf(String name, float def)
  {
    String v = get(name, null);
    if (v == null) return def;
    try
    {
      return Float.parseFloat(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid float attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a double attribute by the specified name.  If
   * not found then return throw an XException.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @return value of attribute parsed as double
   * @throws XException if attribute is not found or cannot be parsed as double
   */
  public final double getd(String name)
  {
    String v = get(name);
    try
    {
      return Double.parseDouble(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid double attr '" + name + "'='" + v + "'", this);
    }
  }

  /**
   * Get a double attribute by the specified name.  If
   * not found then return the given def value.  If the
   * value cannot be parsed then throw an XException.
   *
   * @param name name of attribute
   * @param def value to return if attribute is not found
   * @return value of attribute parsed as double
   * @throws XException if attribute cannot be parsed as double
   */
  public final double getd(String name, double def)
  {
    String v = get(name, null);
    if (v == null) return def;
    try
    {
      return Double.parseDouble(v);
    }
    catch(Exception e)
    {
      throw new XException("Invalid double attr '" + name + "'='" + v + "'", this);
    }
  }

////////////////////////////////////////////////////////////////
// Attribute Modification
////////////////////////////////////////////////////////////////

  /**
   * Set the attribute value for the first attribute
   * found with the specified namespace and local name.
   * If no attribute is found with the name then add it.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   * @param value value for attribute
   */
  public final void setAttr(XNs ns, String name, String value)
  {
    if (name == null || value == null)
      throw new NullPointerException();

    int index = attrIndex(ns, name);
    if (index != -1) attr[index*3+2] = value;
    else addAttr(ns, name, value);
  }

  /**
   * Set the attribute value for the first attribute
   * found with the specified name.  If no attribute
   * is found with the name then add it.
   *
   * @param name name of attribute
   * @param value value for attribute
   */
  public final void setAttr(String name, String value)
  {
    if (name == null || value == null)
      throw new NullPointerException();

    int index = attrIndex(name);
    if (index != -1) attr[index*3+2] = value;
    else addAttr(name, value);
  }

  /**
   * Set the attribute value at the specified index.
   *
   * @param index index of attribute (0..{@code attrSize() - 1})
   * @param value value of attribute
   */
  public final void setAttr(int index, String value)
  {
    if (value == null)
      throw new NullPointerException();

    if (index >= attrSize) throw new ArrayIndexOutOfBoundsException(index);
    attr[index*3+2] = value;
  }

  /**
   * Add the specified attribute name value pair using
   * the null namespace.
   *
   * @param name name of attribute
   * @param value value for attribute
   * @return this
   */
  public final XElem addAttr(String name, String value)
  {
    return addAttrImpl(null, name, value);
  }

  /**
   * Add the specified attribute name value pair.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   * @param value value for attribute
   * @return this
   */
  public final XElem addAttr(XNs ns, String name, String value)
  {
    return addAttrImpl(ns, name, value);
  }

  /**
   * This is used by the public addAttr() APIs and by
   * XParser to add an attribute where ns is just a prefix
   * string since we might not have resolved it yet.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   * @param value value for attribute
   * @return this
   */
  final XElem addAttrImpl(Object ns, String name, String value)
  {
    if (name == null || value == null)
      throw new NullPointerException();

    // insure capacity
    if (attrSize >= attr.length/3)
    {
      int resize = attr.length*6;
      if (resize == 0) resize = 12;
      Object[] temp = new Object[resize];
      System.arraycopy(attr, 0, temp, 0, attr.length);
      attr = temp;
    }

    int offset = attrSize*3;
    attr[offset] = name;
    attr[offset+1] = ns;
    attr[offset+2] = value;
    attrSize++;
    return this;
  }

  /**
   * Remove the first attribute with the specified namespace
   * and local name.
   *
   * @param ns namespace of attribute
   * @param name name of attribute
   */
  public final void removeAttr(XNs ns, String name)
  {
    int index = attrIndex(ns, name);
    if (index != -1) removeAttr(index);
  }

  /**
   * Remove the first attribute with the specified
   * name in the null namespace.
   *
   * @param name name of attribute
   */
  public final void removeAttr(String name)
  {
    int index = attrIndex(name);
    if (index != -1) removeAttr(index);
  }

  /**
   * Remove the attribute at the specified index.
   *
   * @param index index of attribute (0..{@code attrSize() - 1})
   */
  public final void removeAttr(int index)
  {
    if (index >= attrSize) throw new ArrayIndexOutOfBoundsException(index);

    int shift = attrSize-index-1;
    if (shift > 0)
      System.arraycopy(attr, (index+1)*3, attr, index*3, shift*3);
    attrSize--;
  }

  /**
   * Clear the attribute list to a count of 0.
   */
  public final void clearAttr()
  {
    attrSize = 0;
  }

////////////////////////////////////////////////////////////////
// Content
////////////////////////////////////////////////////////////////

  /**
   * Get the number of XContent children of this element.
   *
   * @return number of children this element has
   */
  public final int contentSize()
  {
    return contentSize;
  }

  /**
   * Get the XContent child at the specified index.
   *
   * @param index index of child (0..{@code contentSize() - 1})
   * @return child content
   * @throws ArrayIndexOutOfBoundsException if index &gt;= contentSize()
   */
  public final XContent content(int index)
  {
    if (index >= contentSize) throw new ArrayIndexOutOfBoundsException(index);
    return content[index];
  }

  /**
   * Get the array of XContent children for this element.
   *
   * @return all children of this element
   */
  public final XContent[] content()
  {
    XContent[] r = new XContent[contentSize];
    System.arraycopy(content, 0, r, 0, r.length);
    return r;
  }

  /**
   * Get the index of the specified content instance (using
   * == operator) or return -1 if the specified content is
   * not a child of this element.
   *
   * @param child child to find
   * @return index of {@code child}
   */
  public int contentIndex(XContent child)
  {
    int len = this.contentSize;
    XContent[] content = this.content;
    for(int i=0; i<len; ++i)
      if (content[i] == child)
        return i;
    return -1;
  }

////////////////////////////////////////////////////////////////
// Elem Content
////////////////////////////////////////////////////////////////

  /**
   * Get the XContent at the specified index cast to a XElem.
   *
   * @param index index of child (0..{@code contentSize() - 1})
   * @return child element
   * @throws ArrayIndexOutOfBoundsException if index &gt;= contentSize()
   */
  public final XElem elem(int index)
  {
    if (index >= contentSize) throw new ArrayIndexOutOfBoundsException(index);
    return (XElem)content[index];
  }

  /**
   * Get the array of XElem children for this element.
   *
   * @return array of all child elements
   */
  public final XElem[] elems()
  {
    int len = contentSize;
    if (len == 0) return noElem;
    XContent[] content = this.content;

    int n = 0;
    XElem[] temp = new XElem[len];
    for(int i=0; i<len; ++i)
      if (content[i] instanceof XElem)
        temp[n++] = (XElem)content[i];

    if (n == temp.length) return temp;

    XElem[] r = new XElem[n];
    System.arraycopy(temp, 0, r, 0, n);
    return r;
  }

  /**
   * Get all the children elements in the specified
   * namespace and with the specified local name.
   *
   * @param ns namespace to search
   * @param name name to search
   * @return array of elements matching namespace and name
   */
  public final XElem[] elems(XNs ns, String name)
  {
    int len = contentSize;
    if (len == 0) return noElem;
    XContent[] content = this.content;

    int n = 0;
    XElem[] temp = new XElem[len];
    for(int i=0; i<len; ++i)
    {
      if (content[i] instanceof XElem)
      {
        XElem kid = (XElem)content[i];
        if (XNs.equals(ns, kid.ns) && kid.name.equals(name))
          temp[n++] = kid;
      }
    }

    if (n == temp.length) return temp;

    XElem[] r = new XElem[n];
    System.arraycopy(temp, 0, r, 0, n);
    return r;
  }

  /**
   * Get all the children elements in the specified namespace.
   *
   * @param ns namespace
   * @return all elements in namespace {@code ns}
   */
  public final XElem[] elems(XNs ns)
  {
    int len = contentSize;
    if (len == 0) return noElem;
    XContent[] content = this.content;

    int n = 0;
    XElem[] temp = new XElem[len];
    for(int i=0; i<len; ++i)
    {
      if (content[i] instanceof XElem)
      {
        XElem kid = (XElem)content[i];
        if (XNs.equals(ns, kid.ns))
          temp[n++] = kid;
      }
    }

    if (n == temp.length) return temp;

    XElem[] r = new XElem[n];
    System.arraycopy(temp, 0, r, 0, n);
    return r;
  }

  /**
   * Get all the children elements with the specified
   * local name regardless of namespace.
   *
   * @param name name of element
   * @return array of all elements with name {@code name}
   */
  public final XElem[] elems(String name)
  {
    int len = contentSize;
    if (len == 0) return noElem;
    XContent[] content = this.content;

    int n = 0;
    XElem[] temp = new XElem[len];
    for(int i=0; i<len; ++i)
    {
      if (content[i] instanceof XElem)
      {
        XElem kid = (XElem)content[i];
        if (kid.name.equals(name))
          temp[n++] = kid;
      }
    }

    if (n == temp.length) return temp;

    XElem[] r = new XElem[n];
    System.arraycopy(temp, 0, r, 0, n);
    return r;
  }

  /**
   * Get the first child element in the specified
   * namespace and with the specified local name.  If
   * not found then return null.
   *
   * @param ns namespace to search
   * @param name name to search
   * @return First element matching namespace and name
   */
  public final XElem elem(XNs ns, String name)
  {
    int len = contentSize;
    XContent[] content = this.content;
    for(int i=0; i<len; ++i)
    {
      if (content[i] instanceof XElem)
      {
        XElem kid = (XElem)content[i];
        if (XNs.equals(ns, kid.ns) && kid.name.equals(name))
          return kid;
      }
    }
    return null;
  }

  /**
   * Get the first child element the specified local name
   * regardless of namespace.  If not found then return null.
   *
   * @param name name to search
   * @return first element with {@code name}, or null
   */
  public final XElem elem(String name)
  {
    int len = contentSize;
    XContent[] content = this.content;
    for(int i=0; i<len; ++i)
    {
      if (content[i] instanceof XElem)
      {
        XElem kid = (XElem)content[i];
        if (kid.name.equals(name))
          return kid;
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Text Content
////////////////////////////////////////////////////////////////

  /**
   * Creates and adds a text descendent.
   *
   * @param txt text to add
   * @return this
   */
  public final XElem addText(String txt)
  {
    return addContent(new XText(txt));
  }

  /**
   * If the first content child is a XText instance, then
   * return it.  Otherwise return null.
   *
   * @return text if the first child is a text content, otherwise null.
   */
  public XText text()
  {
    if (contentSize > 0 && content[0] instanceof XText)
      return (XText)content[0];
    return null;
  }

  /**
   * Get the XContent at the specified index cast to a XText.
   *
   * @param index index of child (0..{@code contentSize() - 1})
   * @return child element
   * @throws ArrayIndexOutOfBoundsException if index &gt;= contentSize()
   */
  public final XText text(int index)
  {
    if (index >= contentSize) throw new ArrayIndexOutOfBoundsException(index);
    return (XText)content[index];
  }

  /**
   * If the first content child is a XText instance, then
   * return the result of calling XText.string().  Otherwise
   * return null.
   *
   * @return content of first element if it is text, otherwise null
   */
  public String string()
  {
    if (contentSize > 0 && content[0] instanceof XText)
      return ((XText)content[0]).string();
    return null;
  }

////////////////////////////////////////////////////////////////
// Content Modification
////////////////////////////////////////////////////////////////

  /**
   * Add the specified content to the end of the content list.
   *
   * @param child content to add
   * @return this
   */
  public final XElem addContent(XContent child)
  {
    return addContent(contentSize, child);
  }

  /**
   * Insert the content instance as a child of this
   * element at the specified index.
   *
   * @param index index to insert at
   * @param child content to insert
   * @return this
   * @throws ArrayIndexOutOfBoundsException if index &gt; contentSize()
   */
  public final XElem addContent(int index, XContent child)
  {
    if (child.parent != null)
      throw new IllegalArgumentException("Content already parented");
    if (index > contentSize)
      throw new ArrayIndexOutOfBoundsException(index);

    // insure capacity
    if (contentSize >= content.length)
    {
      int resize = content.length*2;
      if (resize == 0) resize = 4;
      XContent[] temp = new XContent[resize];
      System.arraycopy(content, 0, temp, 0, content.length);
      content = temp;
    }

    // shift up if necessary
    int shift = contentSize-index;
    if (shift > 0)
      System.arraycopy(content, index, content, index+1, shift);

    content[index] = child;
    contentSize++;
    child.parent = this;
    return this;
  }

  /**
   * Replace the content instance at the specified index
   * with the new content child.
   *
   * @param index index to insert at
   * @param child content used to replace existing content
   * @throws ArrayIndexOutOfBoundsException if index &gt;= contentSize()
   */
  public final void replaceContent(int index, XContent child)
  {
    if (index >= contentSize) throw new ArrayIndexOutOfBoundsException(index);
    content[index].parent = null;
    content[index] = child;
    child.parent = this;
  }

  /**
   * Remove the specified content instance (using == operator).
   *
   * @param child content to remove
   */
  public final void removeContent(XContent child)
  {
    int index = contentIndex(child);
    if (index != -1) removeContent(index);
  }

  /**
   * Remove the content child at the specified index.
   *
   * @param index index of content to remove
   * @throws ArrayIndexOutOfBoundsException if index &gt;= contentSize()
   */
  public final void removeContent(int index)
  {
    if (index >= contentSize) throw new ArrayIndexOutOfBoundsException(index);

    content[index].parent = null;

    int shift = contentSize-index-1;
    if (shift > 0)
      System.arraycopy(content, index+1, content, index, shift);
    contentSize--;
  }

  /**
   * Get the content children and set content count to 0.
   */
  public final void clearContent()
  {
    int len = contentSize;
    for(int i=0; i<len; ++i)
      content[i].parent = null;
    contentSize = 0;
  }

////////////////////////////////////////////////////////////////
// Formatting
////////////////////////////////////////////////////////////////

  /**
   * Dump to standard out.
   */
  public void dump()
  {
    try
    {
      XWriter out = new XWriter(System.out);
      write(out, 0);
      out.flush();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Write to the specified File.
   *
   * @param file File to write to
   * @throws Exception if writing fails
   */
  public void write(File file)
    throws Exception
  {
    XWriter out = new XWriter(file);
    write(out);
    out.close();
  }

  /**
   * Write to the specified XWriter stream with indent of 0.
   *
   * @param out XWriter stream to write to
   */
  public void write(XWriter out)
  {
    write(out, 0);
  }

  /**
   * Write to the specified XWriter stream.
   *
   * @param out XWriter stream to write to
   * @param indent starting indent
   */
  public void write(XWriter out, int indent)
  {
    out.indent(indent);
    out.w('<');
    if (ns != null && !ns.prefix.equals("")) out.w(ns.prefix).w(':');
    out.w(name);

    int attrSize = this.attrSize;
    if (attrSize > 0)
    {
      Object[] attr = this.attr;
      for(int i=0; i<attrSize; ++i)
      {
       String attrName = (String)attr[i*3];
       XNs attrNs      = (XNs)attr[i*3+1];
       String attrVal  = (String)attr[i*3+2];
       out.w(' ');
       if (attrNs != null) out.w(attrNs.prefix).w(':');
       out.w(attrName).w('=').w('"').safe(attrVal).w('"');
      }
    }

    int contentSize = this.contentSize;
    if (contentSize == 0)
    {
      out.w('/').w('>').nl();
      return;
    }

    XContent[] content = this.content;
    if (contentSize == 1 && content[0] instanceof XText)
    {
      out.w('>');
      content[0].write(out);
    }
    else
    {
      out.w('>').nl();
      for(int i=0; i<contentSize; ++i)
      {
        XContent c = content[i];
        if (c instanceof XElem)
          ((XElem)c).write(out, indent+1);
        else
          c.write(out);
      }
      out.indent(indent);
    }

    out.w('<').w('/');
    if (ns != null && !ns.prefix.equals("")) out.w(ns.prefix).w(':');
    out.w(name);
    out.w('>').nl();
  }


  /**
   * Write an XML start tag, with optional self closing.
   *
   * @param out XWriter stream to write to
   * @param indent starting indent
   * @param close If true, then write the self closing {@code "/>"} at the
   *              end of the tag.
   *
   * @since Niagara 4.6
   */
  public void write(XWriter out, int indent, boolean close)
  {
    out.indent(indent);
    out.w('<');
    if (ns != null && !ns.prefix.equals("")) out.w(ns.prefix).w(':');
    out.w(name);

    int attrSize = this.attrSize;
    if (attrSize > 0)
    {
      Object[] attr = this.attr;
      for(int i=0; i<attrSize; ++i)
      {
        String attrName = (String)attr[i*3];
        XNs attrNs      = (XNs)attr[i*3+1];
        String attrVal  = (String)attr[i*3+2];
        out.w(' ');
        if (attrNs != null) out.w(attrNs.prefix).w(':');
        out.w(attrName).w('=').w('"').safe(attrVal).w('"');
      }
    }

    if (close)
    {
      out.w('/').w('>');
    } else {
      out.w('>');
    }

  }

////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////

  /**
   * Get the line number of this element in the
   * document or 0 if unknown.
   *
   * @return line number of this element, if known, or 0
   */
  public final int line()
  {
    return line;
  }

  /**
   * Make a new cloned copy of this XElem instance.
   *
   * @return shallow copy of this
   */
  public final XElem copy()
  {
    XElem copy = new XElem();
    copy.ns = ns;
    copy.name = name;
    copy.line = line;

    if (attrSize > 0)
    {
      copy.attrSize = attrSize;
      copy.attr = new Object[attrSize*3];
      System.arraycopy(attr, 0, copy.attr, 0, copy.attr.length);
    }

    if (contentSize > 0)
    {
      copy.contentSize = contentSize;
      copy.content = new XContent[contentSize];
      System.arraycopy(content, 0, copy.content, 0, copy.content.length);
    }

    return copy;
  }

  /**
   * Make a new cloned copy of this XElem instance.  Makes a
   * cloned copies of all descendents of type XElem or XText.
   *
   * @return deep ccopy of this.
   */
  public final XElem deepcopy()
  {
    XElem copy = new XElem();
    copy.ns = ns;
    copy.name = name;
    copy.line = line;

    if (attrSize > 0)
    {
      copy.attrSize = attrSize;
      copy.attr = new Object[attrSize*3];
      System.arraycopy(attr, 0, copy.attr, 0, copy.attr.length);
    }

    if (contentSize > 0)
    {
      copy.contentSize = contentSize;
      copy.content = new XContent[contentSize];
      for(int i = 0; i<contentSize; i++)
      {
        if(content[i] instanceof XElem)
          {copy.content[i] = ((XElem)content[i]).deepcopy();}
        else if(content[i] instanceof XText)
          copy.content[i] = ((XText)content[i]).copy();
        else
          copy.content[i] = content[i];
      }
    }

    return copy;
  }

  /**
   * To string returns the start tag.
   *
   * @return the start tag of this element
   */
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append('<');
    if (ns != null && !ns.prefix.equals("")) s.append(ns.prefix).append(':');
    s.append(name);

    for(int i=0; i<attrSize; ++i)
    {
     String attrName = (String)attr[i*3];
     XNs attrNs      = (XNs)attr[i*3+1];
     String attrVal  = (String)attr[i*3+2];
     s.append(' ');
     if (attrNs != null) s.append(attrNs.prefix).append(':');
     s.append(attrName).append('=').append('\'').append(attrVal).append('\'');
    }

    s.append('>');
    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Object[] noAttr = new Object[0];
  static XContent[] noContent = new XContent[0];
  static XElem[] noElem = new XElem[0];

  XNs ns;
  String name;
  Object[] attr = noAttr;   // 0=name, 1=ns, 2=value
  int attrSize;
  XContent[] content = noContent;
  int contentSize;
  int line;

}
