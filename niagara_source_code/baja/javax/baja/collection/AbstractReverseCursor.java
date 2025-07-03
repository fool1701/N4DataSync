package javax.baja.collection;/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

import javax.baja.sys.Cursor;

/**
 * A general purpose implementation of the {@link Cursor} interface that enforces closed semantics.
 * Subclasses provide implementations for {@link #advanceCursor()}, {@link #doGet()}, and {@link #getReverseCursor()}.
 *
 * @author <a href="mailto:Erik.Test@tridium.com">Erik Test</a>
 */
public abstract class AbstractReverseCursor<E> extends AbstractCursor<E>
{
  public abstract Cursor<E> getReverseCursor();
}
