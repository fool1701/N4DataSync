/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents an entry in an XPath. Each XPathEntry can match an XML element by
 * name and/or by the values of the attributes of the element. In addition, an
 * element may match just the current element, or the current element and any
 * children of that element. (i.e. the W3C XPath "descendant-or-self::node()"
 * (i.e. "//" operator).
 * <p>
 * Several constructors are provided that allow different matching criteria to
 * be specified.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public class XPathElem
{

  /**
   * The Constant logger.
   */
  private static final Logger logger = Logger.getLogger(XPathElem.class.getName());
  /**
   * Represents a match to any value (wildcard match). We use a single
   * null character to differentiate this from a match to an empty string.
   */
  private static final String ANY_VALUE = "\u0000";
  /**
   * The element name.
   */
  private final String elementName;
  /**
   * The match children.
   */
  private final boolean matchChildren;
  /**
   * The attribute values.
   */
  private Map<String, String> attributeValues;

  /**
   * Instantiates a new XPathElem. This constructor creates an XPathElem that will
   * match an element with any name, and any attributes.
   */
  public XPathElem()
  {
    this(null, null, false);
  }

  /**
   * Instantiates a new XPathElem.
   *
   * @param matchChildren If true, then this XPathElem will match an element, or any of the
   *                      children of the element.
   */
  public XPathElem(final boolean matchChildren)
  {
    this(null, null, matchChildren);
  }

  /**
   * Instantiates a new XPathElem.
   *
   * @param elementName Matches the name of the element, or if set to XPathElem.ANY, matches
   *                    any name.
   */
  public XPathElem(final String elementName)
  {
    this(elementName, null, false);
  }

  /**
   * Instantiates a new XPathElem.
   *
   * @param elementName   Matches the name of the element, or if set to XPathElem.ANY, matches
   *                      any name.
   * @param matchChildren If true, then this XPathElem will match an element, or any of the
   *                      children of the element.
   */
  public XPathElem(final String elementName, final boolean matchChildren)
  {
    this(elementName, null, matchChildren);
  }

  /**
   * Instantiates a new XPathElem.
   *
   * @param elementName     Matches the name of the element, or if set to XPathElem.ANY, matches
   *                        any name.
   * @param attributeValues A map of required attribute names and values. Matches an element
   *                        only if all attributes are present, and have the specified values.
   *                        Setting attributeValues to null or empty will match all attributes.
   * @param matchChildren   If true, then this XPathElem will match an element, or any of the
   *                        children of the element.
   */
  public XPathElem(final String elementName, final Map<String, String> attributeValues, final boolean matchChildren)
  {
    this.elementName = elementName;
    this.attributeValues = attributeValues;
    this.matchChildren = matchChildren;
  }

  // private void validateName(String name) {
  // if (!validNamePattern.matcher(name).matches()) {
  // throw new IllegalArgumentException(name + " is not a valid XPathElem name");
  // }
  // }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final XPathElem that = (XPathElem)o;

    if (matchChildren != that.matchChildren) return false;
    if (elementName != null ? !elementName.equals(that.elementName) : that.elementName != null)
      return false;
    return attributeValues != null ? attributeValues.equals(that.attributeValues) : that.attributeValues == null;
  }

  public String getName()
  {
    return elementName;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    int result = elementName != null ? elementName.hashCode() : 0;
    result = 31 * result + (attributeValues != null ? attributeValues.hashCode() : 0);
    result = 31 * result + (matchChildren ? 1 : 0);
    return result;
  }

  /**
   * Checks if this XPathElem also matches child elements.
   *
   * @return true, if is match children
   */
  public boolean isMatchChildren()
  {
    return matchChildren;
  }

  // private static final Pattern validNamePattern =
  // Pattern.compile("(?i)^(?!xml)(?-i)[\\p{Alpha}_][\\p{Alnum}-_.]*");

  /**
   * Returns true of this XPathElem matches the given XElem.
   *
   * @param element the element
   * @return true, if successful
   */
  public boolean matches(final XElem element)
  {
    if (elementName != null && !elementName.equals(element.qname()))
    {
      return false;
    }

    // return now if no attributes are defined
    if (attributeValues == null)
    {
      return true;
    }

    // keep track of how many attributeValues matched
    int matchCount = 0;

    for (int index = 0; index < element.attrSize(); ++index)
    {
      final XNs ns = element.attrNs(index);

      // add namespace prefix, if a namespace is defined
      final String name = ns == null ? element.attrName(index) : ns.prefix() + ":" + element.attrName(index);

      final String requiredValue = attributeValues.get(name);
      final String actualValue = element.attrValue(index);

      if (requiredValue != null)
      {
        if (!requiredValue.equals(actualValue) && !requiredValue.equals(ANY_VALUE))
        {
          return false;
        }
        ++matchCount;
      }
    }

    // not a match unless all attributeValues were matched
    if (matchCount < attributeValues.size())
    {
      return false;
    }

    return true;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("XPathElem{");
    sb.append("elementName='").append(elementName).append('\'');
    sb.append(", attributeValues=").append(attributeValues);
    sb.append(", matchChildren=").append(matchChildren);
    sb.append('}');
    return sb.toString();
  }

  /**
   * Adds a required attribute value to this XPathElem. The
   * attribute must be present, and have the specified value.
   *
   * @param name  the name of the required attribute
   * @param value the required value of the attribute
   * @return this XPathElem to allow method chaining.
   */
  public XPathElem withAttr(final String name, final String value)
  {
    if (attributeValues == null)
    {
      attributeValues = new HashMap<>();
    }
    attributeValues.put(name, value);
    return this;
  }

  /**
   * Adds a required attribute value to this XPathElem. The
   * attribute must be present, but can have any value.
   *
   * @param name the name of the required attribute
   * @return this XPathElem to allow method chaining.
   */
  public XPathElem withAttr(final String name)
  {
    if (attributeValues == null)
    {
      attributeValues = new HashMap<>();
    }
    attributeValues.put(name, ANY_VALUE);
    return this;
  }
}
