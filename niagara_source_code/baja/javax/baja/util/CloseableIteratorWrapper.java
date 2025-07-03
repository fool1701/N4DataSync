/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.Iterator;

/**
 * A basic AutoCloseableIterator that wraps an iterator which doesn't need a close method
 *
 * @author Erik Test
 * @creation 2/21/2017
 * @since Niagara 4.6
 */
@SuppressWarnings("try")
public class CloseableIteratorWrapper<T> implements CloseableIterator<T>
{
  public CloseableIteratorWrapper(Iterator<T> basicIterator)
  {
    myIterator = basicIterator;
  }
  @Override
  public void close() throws Exception
  {
    // This wrapper is for existing iterators that don't need to close
    // Do nothing
  }
  @Override
  public boolean hasNext()
  {
    return myIterator.hasNext();
  }
  @Override
  public T next()
  {
    return myIterator.next();
  }

  private Iterator<T> myIterator;
}