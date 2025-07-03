/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

/**
 * IFilter tests an object to determine if it meets some criteria.
 *
 * Note to developers: This class was written long before java.util.function
 * and lambdas became part of the Java language and API - even before generics
 * support. New API code shouldn't use this interface, as
 * {@link java.util.function.Predicate} is more standard and more descriptive.
 *
 * @author    Andy Frank
 * @creation  12 Sep 2003
 * @version   $Revision: 1$ $Date: 9/12/03 3:04:49 PM EDT$
 * @since     Baja 1.0
 */
public interface IFilter
{
  /**
   * Return true if object is accepted by this filter, false otherwise.
   */
  boolean accept(Object obj);
}
