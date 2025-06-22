/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.datatypes.BBacnetOctetString;

/**
 * FileData contains information about data that is either read from
 * or written to a BACnet File object using the AtomicReadFile or
 * AtomicWriteFile service requests.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 28 Dec 2006
 * @since Niagara 3.2
 */
public interface FileData
{
  /**
   * @return true if the file is at the end.
   */
  boolean isEndOfFile();

  /**
   * Get the file access method, according to the BACnet defined values
   * for stream or record access.
   *
   * @return the access method used to retrieve or set this file data.
   */
  int getAccessMethod();

  /**
   * @return the file start position where the read began, or the write is to begin.
   */
  int getFileStart();

  /**
   * Only valid for record based access.
   * Throws IllegalStateException if used in a stream based situation.
   *
   * @return the record count written to or read from this file.
   */
  long getRecordCount();

  /**
   * Only valid for stream based access.
   * Throws IllegalStateException if used in a record based situation.
   * Get the file data read from or written to this file.
   *
   * @return a byte array containing the contents of the BBacnetOctetString.
   */
  byte[] getFileData();

  /**
   * Only valid for record based access.
   * Throws IllegalStateException if used in a stream based situation.
   * Get the file data read from or written to this file.
   *
   * @return an array of BBacnetOctetString containing the records.
   */
  BBacnetOctetString[] getFileRecordData();

  /**
   * Stream Access tag.
   */
  int STREAM_ACCESS = 0;

  /**
   * Record Access tag.
   */
  int RECORD_ACCESS = 1;

}
