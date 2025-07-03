/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

/**
 * ILambda is used for functions which take an Object and
 * return an Object.
 *
 * Note to developers: This class was written long before java.util.function
 * and lambdas became part of the Java language and API - even before generics
 * support. New API code shouldn't use this interface, as
 * {@link java.util.function.Function} is more standard and more descriptive.
 *
 * @author    Brian Frank
 * @creation  5 Feb 04
 * @version   $Revision: 2$ $Date: 2/5/04 4:25:38 PM EST$
 * @since     Baja 1.0
 */
public interface ILambda
{
  
  /**
   * Evaluate the specified object and return a resulting object. 
   */
  Object eval(Object obj);

}
