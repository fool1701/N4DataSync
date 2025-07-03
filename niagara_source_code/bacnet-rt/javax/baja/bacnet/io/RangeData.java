/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.datatypes.BBacnetBitString;

/**
 * RangeReference contains information to reference
 * a range of values in a compound property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 13 Sep 02
 * @since Niagara 3 Bacnet 1.0
 */
public interface RangeData
  extends PropertyReference
{
  /**
   * Get the result flags for this range data.
   *
   * @return the resultFlags.
   */
  BBacnetBitString getResultFlags();

  /**
   * Does the data include the first item in the list?
   *
   * @return true if the first item is included.
   */
  boolean includesFirstItem();

  /**
   * Does the data include the last item in the list?
   *
   * @return true if the last item is included.
   */
  boolean includesLastItem();

  /**
   * Are there more items in the list that match the request?
   *
   * @return true if more items in the list match the
   * request but are not included in the data.
   */
  boolean isMoreItems();

  /**
   * Get the number of items.
   *
   * @return the item count.
   */
  long getItemCount();

  /**
   * Get the sequence number of the first item in the data.
   *
   * @return the firstSequenceNumber.
   */
  long getFirstSequenceNumber();

  /**
   * Get the item data.
   *
   * @return the items as an encoded byte array.
   */
  byte[] getItemData();

  /**
   * Get the error.
   *
   * @return an ErrorType if this is an error result,
   * or null if this is a success.
   */
  ErrorType getError();

  /**
   * Get the error class.
   *
   * @return an int representing a value in the BBacnetErrorClass
   * enumeration indicating the class of failure,
   * or null if this is a success.
   */
  int getErrorClass();

  /**
   * Get the error code.
   *
   * @return an int representing a value in the BBacnetErrorCode
   * enumeration indicating the reason for failure,
   * or null if this is a success.
   */
  int getErrorCode();

  /**
   * Is this a failure result?
   *
   * @return TRUE if this is an error result, or FALSE if it is a success.
   */
  boolean isError();

  int RESULT_FLAGS = 3;
  int ITEM_COUNT = 4;
  int ITEM_DATA_NO_SEQ_NUM = 5;
  int FIRST_SEQUENCE_NUMBER = 6;
  int ITEM_DATA = 7;
}
 