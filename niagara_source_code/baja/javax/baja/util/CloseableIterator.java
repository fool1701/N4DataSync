/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.Iterator;

/**
 * An auto-closeable Iterator interface that is returned by BIQueryHandler.
 *
 * As documented for the {@link AutoCloseable#close()} method that this
 * interface inherits, even though the close() method is declared to throw
 * {@link Exception}, implementers should throw more specific exceptions
 * (excluding {@link InterruptedException}), or declare close() to throw no
 * exceptions at all. This is to prevent the possibility of throwing an
 * {@link InterruptedException}, which causes compilation warnings and can lead
 * to undesirable side effects.
 *
 * In cases where the CloseableIterator interface class is used in a
 * try-with-resources block, assuming the underlying concrete implementation of
 * the iterator cannot throw {@link InterruptedException}, it is ok to suppress
 * compilation warnings by adding the @SuppressWarnings("try") annotation to the
 * method.
 *
 * @author Erik Test
 * @creation 2/21/2017
 * @since Niagara 4.6
 */
@SuppressWarnings("try")
public interface CloseableIterator<T> extends AutoCloseable, Iterator<T>
{
}
