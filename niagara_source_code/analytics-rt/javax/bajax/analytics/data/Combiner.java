/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import java.util.function.*;
import javax.bajax.analytics.*;

/**
 * Combines analytics values according to a specific Combination.  A combiner
 * will ignore values with invalid status, unless all of the values being
 * combined have invalid status.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface Combiner
  extends AnalyticConstants, Consumer<AnalyticValue>
{

  /**
   * Consumer implementation, simply calls update()
   */
  void accept(AnalyticValue arg);

  /**
   * The number of values combined.
   */
  int getCount();

  /**
   * The current state of the combiner.
   */
  AnalyticValue getValue();

  /**
   * Defines how the Combiner aggregates values, 
   * see AnalyticConstants.COMBINATION_*.
   */
  int getCombination();

  /**
   * Clears the state of the Combiner; the count will be zero and
   * the current value will be null (getAnalyticValue().isNull()==true).
   */
  Combiner reset();

  /**
   * Updates the combination with the given value.  Returns true
   * if the state of the combiner incorporates the given value.  For 
   * example, for a "first" combination, the first element will return 
   * true, but all others will return false.
   */
  boolean update(AnalyticValue arg);

}
