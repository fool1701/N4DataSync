/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import javax.bajax.analytics.*;

/**
 * How to combine multiple values.  Possible values are defined
 * in {@link AnalyticConstants} as COMBINATION_xxx.
 *
 * @author Aaron Hansen
 * @see AnalyticConstants
 * @since NA 2.0
 */
public interface Combination
  extends AnalyticConstants
{

  /**
   * One of the ordinal values defined in AnalyticConstants as
   * COMBINATION_xxx
   */
  int getOrdinal();

  String getTag();

  /**
   * Returns a new combiner for the Combination on which this
   * is invoked.
   */
  Combiner makeCombiner();

}
