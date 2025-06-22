/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.time;

/**
 * Time ranges are made up of an inclusive start time and an exclusive
 * end time.  For example, a time range of 1pm to 2pm includes 1pm but
 * excludes 2pm.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface TimeRange
{

  /**
   * The first excluded time after the time range ends, based on the
   * current real time.
   */
  long getEnd();

  /**
   * The first excluded time after the time range ends, based on the
   * provided time.
   */
  long getEnd(long from);

  /**
   * The inclusive start time, based on the current real time.
   */
  long getStart();

  /**
   * The inclusive start time, based on the provided time.
   */
  long getStart(long from);


}
