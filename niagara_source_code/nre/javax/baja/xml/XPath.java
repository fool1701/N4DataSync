/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * XPath implements a (small) subset of the
 * <a href="https://www.w3.org/TR/xpath/">W3C XPath spec</a>, providing an way
 * to select/match a subset of elements in an XML document. An XPath is
 * like an "XML regex", that matches XML elements depending
 * on their contents and position in the document.
 * <p>
 * This XPath class allows matching elements by their name and/or attribute values.
 * It also supports an
 * equivalent to the "descendant-or-self::node()" (i.e. "//"
 * operator), allowing elements to be selected in a subtree, regardless of their depth.
 * <p>
 * Each portion of an XPath is represented by an XPathELem instance.
 * An XPath is constructed using a list of XPathElems, where each XPathElem
 * matches one or more parts of the XML document tree.
 * <p>
 * The easiest way to construct an XPath is to use the vararg constructor with
 * XPathElem instances. For example:
 * <p>
 * XPath path = new XPath(new XPathElem("root"), new XPathElem("a"));
 * <p>
 * Here are some W3C style XML paths that match elements in the document, and
 * the equivalent XPath construction.
 *
 * <table summary="Example XML paths and their XPath equivalents">
 * <tr align="left">
 * <th align="left" id="predef_classes">Path</th>
 * <th align="left" id="predef_matches">XPath Equivalent</th>
 * </tr>
 * <tr>
 * <td>/root</td>
 * <td>new XPath(new XPathElem("root"))</td>
 * </tr>
 * <tr>
 * <td>/root/child</td>
 * <td>new XPath(new XPathElem("root"), new XPathElem("child"))</td>
 * </tr>
 * <tr>
 * <td>//child</td>
 * <td>new XPath(new XPathElem("child", true))</td>
 * </tr>
 * <tr>
 * <td>//child[@name="foo"]</td>
 * <td>new XPath(new XPathElem("child", true).withAttr("name", "foo"))</td>
 * </tr>
 * <tr>
 * <td>/root//child[@name="foo" and @value="bar"]</td>
 * <td>new XPath(new XPathElem("root"), new XPathElem("child",
 * true).withAttr("name", "foo").withAttr("value", "bar"))</td>
 * </tr>
 * </table>
 * <p>
 * <p>
 * Once an XPath has been constructed, {@link XPath#getMatcher(List)} is called to
 * obtain an {@link XPathMatcher} instance. The XPathMatcher is used to determine
 * whether a given XElem matches the XPath.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public class XPath
{

  /**
   * The Constant logger.
   */
  private static final Logger logger = Logger.getLogger(XPath.class.getName());

  /**
   * The path elements.
   */
  private final List<XPathElem> pathElements;

  /*
   * If any of the XPathElem in this
   * XPath have matchChildren set to true,
   * then this value is also set to true.
   */
  private boolean matchChildren;

  /**
   * Instantiates a new x path.
   *
   * @param path the path
   */
  public XPath(final XPath path)
  {
    this.pathElements = new ArrayList<>(path.pathElements);
    setMatchChildren();
  }

  /**
   * Instantiates a new x path.
   *
   * @param pathElements the path
   */
  public XPath(List<XPathElem> pathElements)
  {
    if (pathElements.size() < 1)
    {
      throw new IllegalArgumentException("XPath must have one or more XPathElems");
    }
    this.pathElements = new ArrayList<>(pathElements);
    setMatchChildren();
  }

  /**
   * Instantiates a new x path.
   *
   * @param pathElements the path elements
   */
  public XPath(final XPathElem pathElement, final XPathElem... pathElements)
  {
    this.pathElements = new ArrayList<>(pathElements.length + 1);
    this.pathElements.add(pathElement);
    this.pathElements.addAll(Arrays.asList(pathElements));
    setMatchChildren();
  }

  private void setMatchChildren()
  {
    for (XPathElem pathElem : pathElements)
    {
      if (pathElem.isMatchChildren())
      {
        matchChildren = true;
        break;
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final XPath xPath = (XPath)o;

    return pathElements != null ? pathElements.equals(xPath.pathElements) : xPath.pathElements == null;
  }

  /**
   * Gets the elements.
   *
   * @return the elements
   */
  public List<XPathElem> getPathElements()
  {
    return pathElements;
  }

  /**
   * Gets an XPathMatcher for this XPath, to match against
   * the given XElemLocation.
   *
   * @param location
   * @return
   */
  public XPathMatcher getMatcher(XElemLocation location)
  {
    if (this.matchChildren)
    {
      return new DynamicXPathMatcher(this, location);
    }
    else
    {
      return new SimpleXPathMatcher(this, location);
    }
  }

  /**
   * Gets an XPathMatcher for this XPath, to match against
   * the location specified by the list of XElems.
   *
   * @param locationElements
   * @return
   */
  public XPathMatcher getMatcher(List<XElem> locationElements)
  {
    return getMatcher(new XElemLocation(locationElements));
  }

  /**
   * Gets an XPathMatcher for this XPath, to match against
   * an empty XElemLocation.
   *
   * @return
   */
  public XPathMatcher getMatcher()
  {
    return getMatcher(new XElemLocation());
  }

  /**
   * Returns the number of path elements in this XPath.
   * @return
   */
  public int size() {
    return pathElements.size();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return pathElements != null ? pathElements.hashCode() : 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "XPath{" + "pathElements=" + pathElements + '}';
  }


  private static abstract class AbstractXPathMatcher implements XPathMatcher
  {
    protected final XPath xPath;
    /**
     * Tracks the number of elements from the XElemLocation
     * that have been compared in any previous calls to matches().
     * That way, we only have to compute matches for any newly added
     * elements.
     */
    protected int numComparedElems;
    protected XElemLocation location;

    protected AbstractXPathMatcher(XPath xPath, XElemLocation location)
    {
      this.xPath = xPath;
      this.location = location;
      location.addElementRemovalListener(this::elementRemoved);
    }

    /**
     * XElemLocation element removed callback. When elements
     * are removed from the XElemLocation for this matcher,
     * reduce the number of "already compared elements"
     * correspondingly.
     *
     * @param location
     */
    protected void elementRemoved(XElemLocation location) {
      if (location.size() < numComparedElems) {
        numComparedElems = location.size();
      }
    }

    @Override
    public XElemLocation getLocation()
    {
      return location;
    }
  }

  /**
   * If an XPathElem has "matchChildren" set, it can match XElems at multiple positions
   * in the XElem list. In that case, it is necessary to use a recursive or dynamic
   * programming approach to evaluate the match.
   * <p>
   * DynamicXPathMatcher uses a dynamic programming approach, as this eliminates
   * the computational redundancy of a recursive approach, and also allows
   * previous search results to be "re-used" when the element path being matched
   * is modified by the addition or removal of elements. This is critical to
   * performance when "walking" an XML document, as the element path gains or
   * loses elements at each node in the tree.
   */
  private static class DynamicXPathMatcher extends AbstractXPathMatcher
  {
    // Matrix element values:
    private static final byte UNKNOWN = 0; // an XElem / XPathElem match that has not yet been evaluated
    private static final byte MATCH = 1; // a match between an XElem and XPathElem
    private static final byte MISMATCH = 2; // a mismatch between an XElem and XPathElem

    // Empty array constants
    private static final int EMPTY_INT_ARRAY[] = new int[0];
    private static final byte EMPTY_BYTE_ARRAY[] = new byte[0];


    /**
     * Holds the next column to be evaluated for "resuming" calculations
     * when new elements have been added to the elements lists.
     */
    private final int lastCol[];

    /**
     * The "dynamic programming" matrix for comparing
     * list of XElems with lists of XPathElems. There is
     * one row for each XPathElem, and one column for each
     * XElem. To reduce the number of allocations, the
     * matrix elements are stored in a one-dimensional array
     * in column major order.
     * <p>
     * The value of each matrix entry is one of MATCH, MISMATCH,
     * or UNKNOWN, depending on the result of he match.
     */
    private byte matchMatrix[] = EMPTY_BYTE_ARRAY;


    // A stack of row / column values. Used to hold the next
    // matrix elements to evaluate.
    private int colStack[] = EMPTY_INT_ARRAY;
    private int rowStack[] = EMPTY_INT_ARRAY;
    private int stackSize = 0;


    private DynamicXPathMatcher(XPath xPath, XElemLocation location)
    {
      super(xPath, location);
      matchMatrix = new byte[xPath.size() * location.size()];
      lastCol = new int[xPath.size()];
    }

    /**
     * Push a row/col pair onto a stack, if they were within
     * the correct range. Also, track the maximum column
     * value by row.
     *
     * @param rows
     * @param cols
     * @param row
     * @param col
     */
    private void push(int rows, int cols, int row, int col)
    {
      // check if row is valid
      if (row < rows)
      {
        // track the maximum column value by row.
        if (lastCol[row] < col)
        {
          lastCol[row] = col;
        }

        // only push the row / col if they are both valid
        if (col < cols)
        {

          rowStack[stackSize] = row;
          colStack[stackSize] = col;
          ++stackSize;
        }
      }
    }

    private int popRow()
    {
      return rowStack[--stackSize];
    }

    private int popCol()
    {
      return colStack[stackSize];
    }

    /**
     * Search for a match
     *
     * @return
     */
    @Override
    public boolean matches()
    {
      // If we have more pathElements and elements
      // there is never a match
      if (xPath.size() > location.size())
      {
        return false;
      }

      // if we have new elements that haven't yet been compared,
      // update the comparison matrix
      if (location.size() > numComparedElems)
      {
        updateMatrix();
      }

      return matchMatrix[(location.size() - 1) * xPath.size() + xPath.size() - 1] == MATCH;
    }


    protected void updateMatrix()
    {
      int rows = xPath.size();
      int cols = location.size();

      initMatrix();

      // initialize starting columns from previous search (if any)
      for (int i = 0; i < rows; ++i)
      {
        if (lastCol[i] >= i)
        {
          push(rows, cols, i, Math.min(lastCol[i], numComparedElems));
        }
      }

      while (stackSize > 0)
      {
        int row = popRow();
        int col = popCol();

        if (matchMatrix[row + col * rows] == UNKNOWN)
        {
          boolean match = xPath.pathElements.get(row).matches(location.get(col));
          matchMatrix[row + col * rows] = match ? MATCH : MISMATCH;

          if (match)
          {
            push(rows, cols, row + 1, col + 1);
          }

          if (xPath.pathElements.get(row).isMatchChildren())
          {
            push(rows, cols, row, col + 1);
          }
        }
      }

      numComparedElems = cols;
    }


    /**
     * This method ensures matchMatrix is sized appropriately, for
     * the current match operation, and clears out any old data.
     */
    private void initMatrix()
    {
      if (rowStack.length < xPath.size() + location.size())
      {
        rowStack = new int[xPath.size() + location.size()];
        colStack = new int[xPath.size() + location.size()];
      }

      // if the existing matrix is too small, allocate new matrix and copy the old data
      if (matchMatrix.length < location.size() * xPath.size())
      {
        byte[] newMatrix = new byte[location.size() * xPath.size()];
        // Note: we only copy data from active columns. not the entire row
        System.arraycopy(matchMatrix, 0, newMatrix, 0, numComparedElems * xPath.size());
        matchMatrix = newMatrix;
      }
      // otherwise, just clear data for any new columns
      else if (location.size() > numComparedElems)
      {
        Arrays.fill(matchMatrix, numComparedElems * xPath.size(), location.size() * xPath.size(), UNKNOWN);
      }
    }
  }

  /**
   * Implements an XPathMatcher when none of the XPathElems have
   * matchChildren == true
   */
  private static class SimpleXPathMatcher extends AbstractXPathMatcher
  {
    private boolean[] match;

    private SimpleXPathMatcher(XPath xPath, XElemLocation location)
    {
      super(xPath, location);
      match = new boolean[xPath.size()];
    }

    @Override
    public boolean matches()
    {
      if (location.size() > xPath.size())
      {
        return false;
      }
      for (int i = numComparedElems; i < location.size(); ++i)
      {
        if (i == 0)
        {
          match[0] = xPath.pathElements.get(0).matches(location.get(0));
        }
        else
        {
          match[i] = match[i - 1] && xPath.pathElements.get(i).matches(location.get(i));
        }
      }

      numComparedElems = location.size();

      return (location.size() == xPath.size() && match[xPath.size() - 1]);
    }
  }
}
