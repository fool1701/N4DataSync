/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.List;

/**
 * An XPathMatcher to matches an XPath against an XElemLocation.
 *
 * To obtain an XPathMatcher for a particular XPath instance, call
 * {@link XPath#getMatcher()} or {@link XPath#getMatcher(XElemLocation)}.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public interface XPathMatcher
{
  /**
   * Returns the XElemLocation that this XPathMatcher
   * matches against.
   */
  XElemLocation getLocation();

  /**
   * Returns true if the list of elements in this
   * XPathMatcher matches the XPath from
   * which this matcher was created.
   * @return
   */
  boolean matches();
}
