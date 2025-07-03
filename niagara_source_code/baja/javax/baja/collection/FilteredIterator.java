/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A FilteredIterator wraps an existing Iterator and filters the results
 * using a predicate.  The resulting iterator will include only those
 * elements which pass the predicate test (i.e. test(T) returns true).
 *
 * @author John Sublett
 * @creation 2/17/14
 * @since Niagara 4.0
 */
public class FilteredIterator<T>
  implements Iterator<T>
{
  /**
   * Create a new FilteredIterator that filters the specified sub-iterator
   * using the specified predicate filter.
   *
   * @param filter The predicate for testing the elements of the inner Iterator.
   *               The result includes all elements for which test(T) returns true.
   * @param inner  An Iterator to filter.
   */
  public FilteredIterator(Predicate<T> filter, Iterator<T> inner)
  {
    this.filter = filter;
    this.inner = inner;
  }

  @Override
  public boolean hasNext()
  {
    if (current != null)
    {
      return true;
    }

    //noinspection LoopConditionNotUpdatedInsideLoop
    while (inner.hasNext())
    {
      current = nextImpl();
      if (current != null && filter.test(current))
      {
        return true;
      }
    }

    current = null;
    return false;
  }

  @Override
  public T next()
  {
    T result = nextImpl();
    if (result != null)
    {
      return result;
    }
    else
    {
      throw new NoSuchElementException();
    }
  }


  /**
   * next() implementation that returns null when no element is available.  This is
   * used by both hasNext() and next().
   * @return Returns the next element in the Iterator or null if the end has been
   *   reached.
   */
  private T nextImpl()
  {
    T result;

    if (current == null)
    {
      while (inner.hasNext())
      {
        result = inner.next();
        if (filter.test(result))
        {
          return result;
        }
      }

      return null;
    }
    else
    {
      result = current;
      current = null;
      return result;
    }
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Predicate<T> filter;
  private Iterator<T> inner;
  private T current;
}
