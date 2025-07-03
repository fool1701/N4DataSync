/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Cursors are used to iterate through a collection of objects.
 *
 * @author <a href="mailto:jsublett@tridium.com">John Sublett</a>
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Cursor<E> extends AutoCloseable
{
  /**
   * If there is a context associated with the Cursor, then
   * return the Context instance.  Otherwise return null.
   */
  Context getContext();

  /**
   * The cursor is initially placed before the first object.  next() must be called first before calling get()
   * Calling next() advances the cursor to the next object, and
   * returns true if it is positioned on a valid object, or
   * false if the cursor has reached the end of the iteration.
   */
  boolean next();

  /**
   * Get the object at the current cursor position.
   */
  E get();


  /**
   * Cursor implements {@link AutoCloseable}, but its implementation of {@link AutoCloseable#close()}
   * should not throw a checked exception. If the close fails, then you should throw a {@link RuntimeException}.
   */
  @Override
  void close();
}
