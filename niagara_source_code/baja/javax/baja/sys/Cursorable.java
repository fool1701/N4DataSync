/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Objects implementing this interface can return a cursor over elements of type E.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Cursorable<E>
{
  /**
   * Get a cursor over elements of type E.
   */
  IterableCursor<E> cursor();
}
