/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import com.tridiumx.analytics.data.*;
import javax.bajax.analytics.*;
import javax.baja.sys.*;

/**
 * Analytics equivalent of {@link javax.baja.status.BStatusValue} with a timestamp, and uses
 * primitives for performance reasons.  Do not cache {@link AnalyticValue} that you
 * did not create, trends will reuse the same instance for performance reasons.
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public interface AnalyticValue extends AnalyticConstants
{


  /**
   * Combines (bitwise or) the status of the argument into the status
   * of this value and returns this.
   */
  AnalyticValue andStatus(AnalyticValue arg);

  /**
   * Copy the internal state of the argument.
   */
  AnalyticValue copy(AnalyticValue val);

  /**
   * Copies the status and return this.
   */
  AnalyticValue copyStatus(AnalyticValue arg);

  /**
   * Copies the timestamp and return this.
   */
  AnalyticValue copyTimestamp(AnalyticValue arg);

  /**
   * See AnalyticConstants.STATUS_*
   */
  int getStatus();

  /**
   * The best known time for the value.
   */
  long getTimestamp();

  /**
   * See AnalyticConstants VALUE_*, for fast switch statements.
   */
  int getValueType();

  /**
   * The value as an object.
   */
  Object getValue();

  /**
   * False if the status is disabled, down, fault, null, or stale.
   */
  default boolean isValid()
  {
    return Values.isValid(getStatus());
  }

  /**
   * Makes and initializes a value for the given prototype value.
   */
  static AnalyticValue make(Object value)
  {
    return Values.make(value);
  }

  /**
   * Returns a new boolean instance.
   */
  static AnalyticBoolean makeBoolean()
  {
    return Values.makeBoolean();
  }

  /**
   * Returns a new enum instance.
   */
  static AnalyticEnum makeEnum()
  {
    return Values.makeEnum();
  }

  /**
   * Returns a new numeric instance.
   */
  static AnalyticNumeric makeNumeric()
  {
    return Values.makeNumeric();
  }

  /**
   * Returns a new numeric instance.
   */
  static AnalyticValue makeString()
  {
    return Values.makeString();
  }

  /**
   * Create a clone of this instance.
   */
  AnalyticValue newCopy();

  /**
   * Combines (bitwise or) the status of the argument into the status
   * of this value and returns this.
   */
  AnalyticValue orStatus(AnalyticValue arg);

  /**
   * Sets the status and return this.
   */
  AnalyticValue setStatus(int arg);

  /**
   * Sets the timestamp and return this.
   */
  AnalyticValue setTimestamp(long arg);

  /**
   * Sets the value as an object and return this.
   */
  AnalyticValue setValue(Object arg);

  /**
   * True if the value and status equals the given.
   */
  boolean statusValueEquals(AnalyticValue arg);

  /**
   * Access the value as a primitive. If the primitive value is not the
   * same type, it should be converted as best possible.
   */
  boolean toBoolean();

  /**
   * Access the value as a primitive. If the primitive value is not the
   * same type, it should be converted as best possible.
   */
  int toEnum();

  /**
   * Access the value as a primitive. If the primitive value is not the
   * same type, it should be converted as best possible.
   */
  double toNumeric();

  /**
   * The string representation of the value, excluding the status
   * and timestamp.
   */
  String toString();

  /**
   * A formatted String for display purposes.
   */
  String toString(Context cx);

  /**
   * Whether or not the values are equal.  If the argument is
   * a different type, it will be converted.
   */
  boolean valueEquals(AnalyticValue value);


}
