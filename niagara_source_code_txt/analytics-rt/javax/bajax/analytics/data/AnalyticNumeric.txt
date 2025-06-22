/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

/**
 * A numeric analytic value.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface AnalyticNumeric extends AnalyticValue
{


  /**
   * Sets the primitive and returns this.
   */
  AnalyticValue setValue(double arg);


}
