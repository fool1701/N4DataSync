/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.time;

import javax.bajax.analytics.*;

/**
 * A period of time, primarily used to align timestamps in a trend.
 * Ordinal values are defined in AnalyticConstants as INTERVAL_xxx.
 *
 * @author Aaron Hansen
 * @see AnalyticConstants
 * @since NA 2.0
 */
public interface Interval
  extends AnalyticConstants
{


  /**
   * Returns the number of intervals for the given time range.
   */
  int count(long start, long end);

  /**
   * One of the ordinal values defined in AnalyticConstants as
   * INTERVAL_xxx
   */
  int getOrdinal();

  String getTag();

  /**
   * The approximate number of ms in the interval.
   */
  long millis();

  /**
   * Returns the start of next interval for the given timestamp.
   */
  long next(long timestamp);


}
