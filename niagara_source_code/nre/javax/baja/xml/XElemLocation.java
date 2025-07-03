/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * An XElemLocation represents the location of a element in an XML document.
 * The location is specified as an ordered list of the elements that contain
 * the target element, starting with the root element, up to and including
 * the target element.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public class XElemLocation
{
  private List<XElem> elements;
  private List<Consumer<XElemLocation>> removalListeners = null;

  /**
   * Create an empty XElemLocation
   */
  public XElemLocation()
  {
    this.elements = new ArrayList<>();
  }

  /**
   * Create an XElemLocation using the provided list of elmements.
   *
   * @param elements
   */
  public XElemLocation(List<XElem> elements)
  {
    this.elements = new ArrayList<>(elements);
  }

  /**
   * Create an XElemLocation using the provided array of elmements.
   *
   * @param elements
   */
  public XElemLocation(XElem... elements)
  {
    this.elements = new ArrayList<>();
    this.elements.addAll(Arrays.asList(elements));
  }

  /**
   * Add a new element to end of the list of XElems in the XElemLocation.
   *
   * @param element
   */
  public void addElement(XElem element)
  {
    elements.add(element);
  }

  /**
   * Removes the XElem farthest from the root (i.e. the last element in the
   * XElemLocation)
   */
  public void removeElement()
  {
    if (elements.size() == 0)
    {
      throw new IllegalStateException("attempted to remove an XElem from an empty XElemLocation");
    }
    elements.remove(elements.size() - 1);
    callRemovalListeners();
  }

  private void callRemovalListeners()
  {
    if (removalListeners != null)
    {
      removalListeners.forEach(listener -> listener.accept(this));
    }
  }

  /**
   * Returns the number of XElems in this XElemLocation
   *
   * @return
   */
  public int size()
  {
    return elements.size();
  }

  /**
   * Returns the XElem at the given index. Index must be in the range
   * 0 to size() -1, where 0 is the root element, and size() - 1 is
   * the leaf / target element.
   *
   * @param index The index of the XElem to return.
   * @return
   */
  public XElem get(int index)
  {
    return elements.get(index);
  }

  /**
   * Remove all XElems from this location.
   */
  public void clear()
  {
    while (size() > 0)
    {
      removeElement();
    }
  }

  /**
   * Add a listener that is called when an element is removed from this XElemLocation.
   * @param listener
   */
  public void addElementRemovalListener(Consumer<XElemLocation> listener)
  {
    if (removalListeners == null)
    {
      removalListeners = new ArrayList<>();
    }
    removalListeners.add(listener);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    XElemLocation that = (XElemLocation)o;

    return elements != null ? elements.equals(that.elements) : that.elements == null;
  }

  @Override
  public int hashCode()
  {
    return elements != null ? elements.hashCode() : 0;
  }

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("XElemLocation{");
    sb.append("elements=").append(elements);
    sb.append('}');
    return sb.toString();
  }

}
