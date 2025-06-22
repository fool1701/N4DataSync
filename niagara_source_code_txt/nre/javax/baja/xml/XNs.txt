/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

/**
 * XNs models an XML namespace.  XNs are usually created as
 * attributes on XElems using the {@code XElem.defineNs()}
 * and {@code XElem.defineDefaultNs()} methods.  Two
 * XNs instances are equal if they have the same uri.
 *
 * @author    Brian Frank on 6 Apr 02
 * @since     Baja 1.0
 */
public final class XNs
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a new XNs instance with the specified
   * prefix and uri.
   *
   * @param prefix namespace prefix
   * @param uri namespace uri
   */
  public XNs(String prefix, String uri)
  {
    if (prefix == null || uri == null)
      throw new NullPointerException();

    this.prefix = prefix;
    this.uri = uri;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return if this a default XNs namespace which has a prefix of "".
   *
   * @return true if this is a default namespace
   */
  public boolean isDefault()
  {
    return prefix.equals("");
  }

  /**
   * Get the prefix used to tag elements with this namespace.
   * If this is the default namespace then return "".
   *
   * @return the prefix of this namespace
   */
  public final String prefix()
  {
    return prefix;
  }

  /**
   * Get the uri which defines a universally unique namespace.
   *
   * @return the uri of this namespace
   */
  public final String uri()
  {
    return uri;
  }

  /**
   * Return uri.
   *
   * @return the uri of this namespace
   */
  public String toString()
  {
    return uri;
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Two instances of XNs are equal if they have the
   * exact same uri characters.
   */
  public boolean equals(Object obj)
  {
    if (this == obj) return true;
    if (obj instanceof XNs)
    {
      return uri.equals(((XNs)obj).uri);
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return uri.hashCode();
  }

  /**
   * Two instances of XNs are equal if they have the
   * exact same uri characters.
   */
  static boolean equals(Object ns1, Object ns2)
  {
    if (ns1 == null) return ns2 == null;
    return ns1.equals(ns2);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  String prefix;
  String uri;
  XElem declaringElem;

}
