/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

/**
 * XContent is the super class of the various element content classes.
 *
 * @author    Brian Frank on 6 Apr 02
 * @since     Baja 1.0
 */
public abstract class XContent
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent element or null if not currently parented.
   * @return parent element, or null
   */
  public final XElem parent()
  {
    return parent;
  }

  /**
   * XContent equality is defined by the == operator.
   */
  public final boolean equals(Object obj)
  {
    return this == obj;
  }

  /**
   * Write to the XWriter.
   * @param out XWriter to write to.
   */
  public abstract void write(XWriter out);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  XElem parent;

}
