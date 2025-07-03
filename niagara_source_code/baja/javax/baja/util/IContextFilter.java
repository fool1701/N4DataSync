/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import javax.baja.nre.util.IFilter;
import javax.baja.sys.Context;

/**
 * IContextFilter is a filter that can yield different results
 * based on context.
 *
 * @author    John Sublett
 */
public interface IContextFilter
  extends IFilter
{
  /**
   * Test whether the specified object meets the filter requirements.
   *
   * @param obj The object to test.
   * @param cx  The context in which the filter is evaluated.
   */
  boolean accept(Object obj, Context cx);
}
