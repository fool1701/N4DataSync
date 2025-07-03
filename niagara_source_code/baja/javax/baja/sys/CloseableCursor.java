/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * CloseableCursors are Cursors which have an internal
 * resource, such as a database connection, that can be released
 * by calling the close() method.
 *
 * @author    Mike Jarmy
 * @creation  09 May 2012
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:13 AM EST$
 * @since     Baja 4.0
 */
public interface CloseableCursor<T> extends IterableCursor<T>
{
  /**
   * Close any internal resources held by this Cursor.
   * Once close() is called, this Cursor should not be 
   * used for anything else.
   */
  @Override
  public void close();
}
