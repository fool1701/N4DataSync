/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import java.util.*;
import javax.bajax.analytics.AnalyticContext;

/**
 * A chronologically ordered stream of AnalyticValues.
 * <p>
 * Row instances {@link AnalyticValue} are reused for performance
 * reasons, so they must not be cached across calls to next().
 * </p>
 * <p>
 *  Data Processing/Logic blocks should subclass {@link javax.bajax.analytics.algorithm.BlockTrend}
 * </p>
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface AnalyticTrend extends Iterator<AnalyticValue>
{

  /**
   * The context which full describes the trend.
   */
  AnalyticContext getContext();

  /**
   * Whether or not next() can be called.  Can be called multiple times
   * before calling next() without skipping any values.
   */
  boolean hasNext();

  /**
   * Returns the next value in the series.  The value can be modified,
   * but do not cache the return value, do not hold a reference to it.
   * AnalyticTrends will reuse the same {@link AnalyticValue} instance across
   * calls to next() for performance reasons.  Do not call this method
   * unless a preceding call to hasNext() returns true.
   */
  AnalyticValue next();

  /**
   * Sets the context and returns this.
   */
  AnalyticTrend setContext(AnalyticContext cx);


  /**
   * Closes the trend and releases resources.
   */
  void close();


}
