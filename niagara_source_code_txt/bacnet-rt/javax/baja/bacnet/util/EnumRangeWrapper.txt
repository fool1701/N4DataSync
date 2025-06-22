/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.bacnet.io.ErrorType;
import javax.baja.sys.BEnumRange;

/**
 * Wrap the BEnumRange and potential ErrorType while
 * creating the enum range
 */
public class EnumRangeWrapper
{
  private BEnumRange enumRange;

  private ErrorType errorType;

  /**
   * Returns the value of enum range
   *
   * @return enumRange
   */
  public BEnumRange getEnumRange()
  {
    return enumRange;
  }

  /**
   * Set the value of enum range
   *
   * @param enumRange
   */
  public void setEnumRange(BEnumRange enumRange)
  {
    this.enumRange = enumRange;
  }

  /**
   * Get the error type associated with enum range creation
   *
   * @return errorType
   */
  public ErrorType getErrorType()
  {
    return errorType;
  }

  /**
   * Mark the error type.
   *
   * @param errorType
   */
  public void markError(ErrorType errorType)
  {
    this.errorType = errorType;
  }

  /**
   * factory method to make instance of EnumRangeWrapper
   * @param enumRange
   * @param errorType
   * @return enumRangeWrapper
   */
  public static EnumRangeWrapper make(BEnumRange enumRange, ErrorType errorType)
  {
    EnumRangeWrapper enumRangeWrapper = new EnumRangeWrapper();
    enumRangeWrapper.setEnumRange(enumRange);
    enumRangeWrapper.markError(errorType);
    return enumRangeWrapper;
  }
}
