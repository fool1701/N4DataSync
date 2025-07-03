/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.collection;

import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.CursorException;
import javax.baja.sys.IterableCursor;

/**
 * A general purpose implementation of the {@link Cursor} interface that enforces closed semantics.
 * Subclasses only need to provide implementations for {@link #advanceCursor()} and {@link #doGet()}.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public abstract class AbstractCursor<E> implements IterableCursor<E>
{
  /** If true, then {@link #next()}, and {@link #get()} will throw CursorExceptions */
  protected volatile boolean closed;

  /**
   * Default implementation returns {@link Context#NULL}.
   */
  @Override
  public Context getContext()
  {
    return Context.NULL;
  }

  @Override
  public final boolean next()
  {
    if (closed)
    {
      throw new CursorException("closed");
    }
    return advanceCursor();
  }

  /**
   * {@link #next()} is final to enforce closed semantics. If the cursor is not closed,
   * then this method is called to advance the cursor to the next item.
   *
   * @return true if cursor has advanced and is positioned on a valid item.
   * @see Cursor#next()
   */
  protected abstract boolean advanceCursor();

  @Override
  public final E get()
  {
    if (closed)
    {
      throw new CursorException("closed");
    }
    return doGet();
  }

  /**
   * {@link #get()} is final to enforce closed semantics. If the cursor is not closed then
   * this method is called to get the item at the current cursor position.
   *
   * @return the item at the current cursor position.
   * @see Cursor#get()
   */
  protected abstract E doGet();

  @Override
  public final void close()
  {
    closeCursor();
    closed = true;
  }

  /**
   * Callback just before cursor is closed. Default implementation does nothing. When this
   * method returns the {@link #closed} field will be set to {@code true}.
   */
  protected void closeCursor()
  {
  }
}
