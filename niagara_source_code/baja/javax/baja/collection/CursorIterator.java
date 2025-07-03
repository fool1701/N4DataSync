/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;

/**
 * Wraps a Cursor in an Iterator implementation.
*
 * @author John Sublett, Peter Kronenberg
 * @creation 01/27/2014, 01/19/2016
 * @since Niagara 4.0
 */
public class CursorIterator<T>
  implements Iterator<T>
{
  public CursorIterator(Cursor<T> cursor)
  {
    this.cursor = cursor;
    hasMore = true;
  }

  /**
   * Get the context from the cursor.
   *
   * @return Returns the context from the cursor.
   */
  public Context getContext()
  {
    return cursor.getContext();
  }

  /**
   *
   * @return Returns true if the iterator has additional elements.
   */
  @Override
  public boolean hasNext()
  {

    calledHasNext = true;
    hasMore = cursor.next();
    return hasMore;
  }

  /**
   * Get the next element from the iterator.
   *
   * @return Returns the next element from the iterator and advances the iterator.
   */
  @Override
  public T next()
  {
    if (!hasMore || !calledHasNext) throw new NoSuchElementException();
    return cursor.get();
  }


  @Override
  public void remove()
  {
    throw new UnsupportedOperationException();
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Cursor<T> cursor;
  private boolean hasMore;
  private boolean calledHasNext;
}