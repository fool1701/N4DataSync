/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;

/**
 * Iterates property values in a SlotCursor
 *
 * @author John Sublett
 * @author Matt Boon
 * @creation 01/27/2014
 * @since Niagara 4.0
 */
public class SlotCursorIterator<T extends BValue>
  implements Iterator<T>
{
  /**
   * Constructor
   * <p>
   * NOTE: if T is a parameterized type, you'll have trouble using this constructor, because you can't
   * use ClassName.class with a parameterized ClassName.  An alternative is to do something like the following,
   * and suppress the unchecked warning it generates:
   * <pre>{@code
   * SlotCursorIterator.stream(cursor, ClassName.class)
   *   .map(v -> (ClassName<ParamType>) v)
   *   .iterator();
   * }</pre>
   *
   * @param cursor      cursor to iterate
   * @param filterClass type of property
   * @since Niagara 4.2
   */
  public SlotCursorIterator(SlotCursor<Property> cursor, Class<T> filterClass)
  {
    this.iterator = stream(cursor, filterClass).iterator();
  }

  /**
   * Constructor supporting Niagara 4.1 and earlier.  Constructing SlotCursorIterator this way will perform
   * unchecked casts, and may result in ClassCastException being thrown at runtime.
   *
   * @deprecated use {@link #SlotCursorIterator(SlotCursor, Class)} or a static utility method instead
   */
  @SuppressWarnings("unchecked")
  @Deprecated
  public SlotCursorIterator(SlotCursor<Property> cursor)
  {
    this.iterator = stream(cursor, BValue.class).map(v -> (T)v).iterator();
  }

  @Override
  public boolean hasNext()
  {
    return iterator.hasNext();
  }

  @Override
  public T next()
  {
    return iterator.next();
  }

  private final Iterator<T> iterator;

////////////////////////////////////////////////////////////////
// Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * Utility method that returns an Iterator of Property values
   *
   * @since Niagara 4.2
   */

  public static Iterator<BValue> iterator(SlotCursor<Property> cursor)
  {
    Stream<BValue> s = stream(cursor);
    return s.iterator();
  }


  /**
   * Utility method that returns a Stream of Property values having a given type.
   *
   * @since Niagara 4.2
   */
  public static <T> Iterator<T> iterator(SlotCursor<Property> cursor, Class<T> filterClass)
  {
    Stream<T> s = stream(cursor, filterClass);
    return s.iterator();
  }

  /**
   * Utility method that returns a Stream of Property values.
   *
   * @param cursor      a cursor of Properties
   * @since Niagara 4.2
   */

  public static Stream<BValue> stream(SlotCursor<Property> cursor)
  {
    return stream(cursor, BValue.class);
  }


  /**
   * Utility method that returns a Stream of Property values having a given type.
   *
   * @param cursor      a cursor of Properties
   * @param filterClass class of the desired result type, which will be used for filtering results.
   * @param <T>         Desired result type
   * @since Niagara 4.2
   */
  public static <T> Stream<T> stream(SlotCursor<Property> cursor, Class<T> filterClass)
  {
    Objects.requireNonNull(cursor);
    Objects.requireNonNull(filterClass);
    final boolean shouldFilter = filterClass != BValue.class;
    return StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(/*initial estimate size*/0L, /*characteristics*/Spliterator.NONNULL)
    {
      @SuppressWarnings("unchecked")
      // cursor.next(Class) ensures we have result of the right type, cast is safe
      @Override
      public boolean tryAdvance(Consumer<? super T> consumer)
      {
        boolean next;
        if (shouldFilter)
        {
          next = cursor.next(filterClass);
        }
        else
        {
          next = cursor.next();
        }
        if (next)
        {
          consumer.accept((T)cursor.get());
        }
        return next;
      }
    }, /*parallel*/false);
  }
}