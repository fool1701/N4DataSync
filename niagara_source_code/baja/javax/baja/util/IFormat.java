/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import javax.baja.sys.Context;

/**
 * IFormat formats an object into a string.
 *
 * @author    John Sublett
 * @creation  22 Sep 2003
 * @version   $Revision: 1$ $Date: 9/23/03 11:33:48 AM EDT$
 * @since     Baja 1.0
 */
public interface IFormat
{
  /**
   * Get a string representation of the specified object.
   *
   * Starting in Niagara 4.13, the second parameter was switched from a BFacets to a Context to
   *  provide better localization support.
   *
   * @param obj The object to format into a string.
   * @param context The context to be used when formatting the object into a string.
   */
  public String format(Object obj, Context context);
}
