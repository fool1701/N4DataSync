/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

/**
 * A boolean analytic value.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface AnalyticBoolean extends AnalyticValue
{


  /**
   * Sets the value and status and returns this.
   */
  AnalyticBoolean set(boolean value, int status);

  /**
   * Sets the timestamp, value and status and returns this.
   */
  AnalyticBoolean set(long timestamp, boolean value, int status);

  /**
   * Sets the primitive and returns this.
   */
  AnalyticBoolean setValue(boolean b);


}
