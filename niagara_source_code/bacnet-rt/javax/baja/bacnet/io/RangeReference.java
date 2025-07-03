/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.datatypes.BBacnetDateTime;

/**
 * RangeReference contains information to request
 * a range of values in a compound property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 13 Sep 02
 * @since Niagara 3 Bacnet 1.0
 */
public interface RangeReference
  extends PropertyReference
{
  /**
   * Get the rangeType.
   *
   * @return the rangeType.
   */
  int getRangeType();

  /**
   * Get the reference index.
   * <p>
   * Interpretation depends on the value of rangeType.
   *
   * @return the reference index.
   */
  long getReferenceIndex();

  /**
   * Get the reference time.
   * <p>
   * Only meaningful if rangeType is BY_TIME.
   * If rangeType is BY_POSITION or BY_SEQUENCE_NUMBER, this
   * will be null.
   *
   * @return the reference index.
   */
  BBacnetDateTime getReferenceTime();

  /**
   * Get the number of items requested.
   *
   * @return the count of requested items.
   */
  int getCount();

  int BY_POSITION = 3;
  int BY_TIME_DEPRECATED = 4;
  int TIME_RANGE_DEPRECATED = 5;
  int BY_SEQUENCE_NUMBER = 6;
  int BY_TIME = 7;
}
 