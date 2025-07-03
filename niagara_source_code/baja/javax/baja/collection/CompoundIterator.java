/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.baja.util.CloseableIterator;

/**
 * A CompoundIterator concatentates two iterators.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
@SuppressWarnings("try")
public class CompoundIterator<T>
  implements CloseableIterator<T>
{
  public CompoundIterator(Iterator<T>[] subs)
  {
    this.subs = subs;
    if (subs.length != 0)
    {
      {
        iterator = subs[0];
      }
    }
  }

  @Override
  public boolean hasNext()
  {
    while (index < subs.length && !iterator.hasNext())
    {
      index++;
      if (index < subs.length)
      {
        {
          iterator = subs[index];
        }
      }
    }

    return index < subs.length;
  }

  @Override
  public T next()
  {
    if (hasNext())
    {
      {
        return iterator.next();
      }
    }
    else
    {
      {
        throw new NoSuchElementException();
      }
    }
  }

  /**
   * Closes any sub iterators that are AutoCloseable.
   *
   * Any subclasses that override this method should remember to call super.close().
   *
   * @since Niagara 4.6
   */
  @Override
  public void close() throws Exception
  {
    // always close() all applicable sub iterators, and if any Exceptions are thrown along the way,
    // delay throwing them until after all sub iterators are processed.
    Exception e = null;
    for(Iterator<T> iterator : subs)
    {
      try
      {
        if (iterator instanceof AutoCloseable)
        {
          ((AutoCloseable)iterator).close();
        }
      }
      catch(Exception ex)
      {
        if (e == null)
        { // Only store a reference to the first exception thrown
          e = ex;
        }
      }
    }

    if (e != null)
    { // If we had any exceptions along the way, throw it now since we've finished processing all
      throw e;
    }
  }

  private Iterator<T>[] subs;
  private Iterator<T> iterator;
  private int index;
}
