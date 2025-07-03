/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

/**
 * A enum analytic value.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface AnalyticEnum extends AnalyticValue
{


  /**
   * Sets the primitive and returns this.
   */
  AnalyticValue setValue(int arg);


}
