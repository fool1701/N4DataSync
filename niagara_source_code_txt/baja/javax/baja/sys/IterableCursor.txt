/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An iterable Cursor that's used to iterate through a collection of objects.
 *
 * @since Niagara 4.0
 */
public interface IterableCursor<E> extends Cursor<E>, Iterable<E>
{
  /**
   * Returns an iterator for the cursor.
   *
   * @see #iterator(Cursor)
   *
   * @return An iterator.
   */
  @Override
  default Iterator<E> iterator()
  {
    return iterator(this);
  }
  
  /**
   * Returns a spliterator for the cursor.
   *
   * @see #spliterator(Cursor)
   *
   * @return A spliterator.
   */
  @Override
  default Spliterator<E> spliterator()
  {
    return spliterator(this);
  }

  /**
   * Returns a stream for the cursor. Note: the cursor will not be closed when the stream is closed.
   * To achieve that, use {@link #stream(boolean)} with closeCursor set to true.
   *
   * @see #stream(Cursor)
   *
   * @return A cursor.
   */
  default Stream<E> stream()
  {
    return stream(this);
  }

  /**
   * Returns a stream for the cursor. Optionally, the cursor can be closed when the stream is
   * closed.
   *
   * @since Niagara 4.12
   *
   * @see #stream(Cursor, boolean)
   *
   * @param closeCursor Whether to close the cursor when the stream is closed.
   * @return A cursor.
   */
  default Stream<E> stream(boolean closeCursor)
  {
    return stream(this, closeCursor);
  }
  
  /**
   * Returns an iterator for a cursor.
   *
   * @since Niagara 4.4
   *
   * @param cursor Used to create an iterator.
   * @param <V> The type of iterator.
   * @return An iterator.
   */
  static <V> Iterator<V> iterator(Cursor<V> cursor)
  {
    return stream(cursor).iterator();
  }
  
  /**
   * Returns a spliterator for the cursor.
   *
   * @since Niagara 4.4
   *
   * @param cursor Used to create a spliterator.
   * @param <V> The type of spliterator.
   * @return A spliterator.
   */
  static <V> Spliterator<V> spliterator(Cursor<V> cursor)
  {
    return new Spliterators.AbstractSpliterator<V>(/*initial estimate size*/0L, /*characteristics*/0)
    {
      @Override
      public boolean tryAdvance(Consumer<? super V> consumer)
      {
        try
        {
          boolean next = cursor.next();
          if (next)
          {
            consumer.accept(cursor.get());
          }
          return next;
        }
        catch(CursorException ignore) {}
        return false;
      }
    };
  }
  
  /**
   * Returns a stream for the cursor. Note: the cursor will not be closed when the stream is closed.
   * To achieve that, use {@link #stream(Cursor, boolean)} with closeCursor set to true.
   *
   * @since Niagara 4.4
   *
   * @param cursor Used to create the stream.
   * @param <V> The type of stream.
   * @return A stream.
   */
  static <V> Stream<V> stream(Cursor<V> cursor)
  {
    return StreamSupport.stream(spliterator(cursor), /*parallel*/false);
  }

  /**
   * Returns a stream for the cursor. Optionally, the cursor can be closed when the stream is
   * closed.
   *
   * @since Niagara 4.12
   *
   * @param cursor Used to create the stream.
   * @param closeCursor Whether to close the cursor when the stream is closed.
   * @param <V> The type of stream.
   * @return A stream.
   */
  static <V> Stream<V> stream(Cursor<V> cursor, boolean closeCursor)
  {
    if (closeCursor)
    {
      return stream(cursor).onClose(() -> cursor.close());
    }
    else
    {
      return stream(cursor);
    }
  }
}
