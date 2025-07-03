/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;

import javax.baja.bacnet.datatypes.*;

/**
 * This interface specifies the methods for decoding Niagara
 * quantities from BACnet ASN.1 primitives.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 09 May 02
 * @since Niagara 3 Bacnet 1.0
 */

public interface AsnInput
{

  ////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////
  int available();
//  /**
//   * This method is used to redirect the data
//   * reader to a new input stream without having to
//   * create a new instance.
//   * @param buffer the buffer to use in reading Asn-encoded data.
//   */
//  void setBuffer(byte[] buffer);
//
//  /**
//   * This method is used to redirect the data
//   * reader to a new input stream without having to
//   * create a new instance.
//   * @param buffer the buffer to use in reading Asn-encoded data.
//   * @param newPos the position in the buffer at which to start reading.
//   */
//  void setBuffer(byte[] buffer, int newPos);
//
//  /**
//   * Get the current position in the buffer of the reader.
//   * @returns the index of the next byte to be read.
//   */
//  int getPos();
//

  /**
   * Examines the number of the next tag in
   * the input stream. Does not actually remove
   * the tag from the stream.
   * Note that the tag number alone does not contain
   * enough information to determine if this
   * is the desired tag.  A check for a value tag
   * vs opening/closing tag must also be made.
   *
   * @return the next tag in the stream.
   */
  int peekTag()
    throws AsnException;

  /**
   * Is the current tag an application tag of the given tag number?
   *
   * @param tagNumber
   * @return true if the current tag is the given application tag.
   */
  boolean isApplicationTag(int tagNumber);

  /**
   * Is the current tag a context tag of the given tag number?
   *
   * @param tagNumber
   * @return true if the current tag is the given application tag.
   */
  boolean isContextTag(int tagNumber);

  /**
   * Returns true if current tag is an opening tag.
   *
   * @param tagNumber the given tag number.
   * @return true if the current tag is an opening tag.
   */
  boolean isOpeningTag(int tagNumber);

  /**
   * Verify next tag is opening tag with specified tagNumber. In not valid
   * throw AsnException, if valid skip the tag.
   *
   * @param tagNumber
   * @throws AsnException if next tag not specified opening tag
   */
  public void skipOpeningTag(int tagNumber)
    throws AsnException;

  /**
   * Returns true if current tag is a closing tag.
   *
   * @param tagNumber the given tag number.
   * @return true if the current tag is a closing tag.
   */
  boolean isClosingTag(int tagNumber);

  /**
   * Verify next tag is closing tag with specified tagNumber. In not valid
   * throw AsnException, if valid skip the tag.
   *
   * @param tagNumber
   * @throws AsnException if next tag not specified closing tag
   */
  public void skipClosingTag(int tagNumber)
    throws AsnException;


  /**
   * Is the current tag a value tag with this tag number?
   * Note this will return true for application AND context tags
   * that match the supplied tag number.
   *
   * @param tagNumber the given tag number.
   * @return true if current tag is a value tag
   * with the given tag number.
   */
  boolean isValueTag(int tagNumber);

  /**
   * Get the current application tag number by peeking the tag.
   *
   * @return the current tag number if it is an application tag,
   * or -1 if it is a context tag.
   */
  int peekApplicationTag()
    throws AsnException;

  /**
   * Get the data length specified for the current data.
   *
   * @return the Asn.1 data length from the LVT field.
   */
  int getDataLength();

  /**
   * Skips the next tag in the Asn data stream.
   *
   * @return tagNumber of next tag or END_OF_DATA
   * if entire stream has been read.
   */
  int skipTag()
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Null Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a null value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a null value.
   */
  BBacnetNull readNull()
    throws AsnException;

  /**
   * Reads a null value from the Asn data stream.
   *
   * @param contextTag the given contextTag.
   * @throws AsnException if next value in
   *                      data stream is not a null value.
   * @throws AsnException if value in data
   *                      stream does not have given context tag.
   */
  BBacnetNull readNull(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Boolean Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a boolean value from the Asn data stream.
   *
   * @return boolean value.
   * @throws AsnException if next value in
   *                      data stream is not a boolean value.
   */
  boolean readBoolean()
    throws AsnException;

  /**
   * Reads a boolean value with the given context
   * tag from the Asn data stream.
   *
   * @param contextTag the given context tag.
   * @throws AsnException if value in
   *                      data stream is not a boolean value.
   * @throws AsnException if value in data
   *                      stream does not have given context tag.
   * @return boolean value.
   */
  boolean readBoolean(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Generic Integer Values
////////////////////////////////////////////////////////////////

  /**
   * Reads an integer value (signed or unsigned)
   * from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an integer.
   * @throws AsnException if value is too
   *                      large to be represented as a Java int.
   * @return integer value.
   */
  int readInteger()
    throws AsnException;

////////////////////////////////////////////////////////////////
//  Unsigned Integer Values
////////////////////////////////////////////////////////////////

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * Because Java's int is signed, and cannot represent the
   * full range of values, the result is returned as a long.
   * This is only necessary for values that can be greater than
   * 0x80000000L (2147483648).  Values that are constrained to be
   * less than this (e.g., sequence number in BBacnetTimeStamp)
   * can use readUnsignedInt().
   *
   * @throws AsnException if next value in
   *                      data stream is not an unsigned integer.
   * @throws AsnException if value is too
   *                      large to be represented in a long.
   * @return integer value.
   */
  long readUnsignedInteger()
    throws AsnException;

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * Because Java's int is signed, and cannot represent the
   * full range of values, the result is returned as a long.
   * This is only necessary for values that can be greater than
   * 0x80000000L (2147483648).  Values that are constrained to be
   * less than this (e.g., sequence number in BBacnetTimeStamp)
   * can use readUnsignedInt().
   *
   * @param contextTag the given context tag.
   * @return integer value.
   * @throws AsnException if next value in
   *                      data stream is not proper type.
   * @throws AsnException if value does not
   *                      have proper context tag.
   * @throws AsnException if value is too
   *                      large to be represented in a long.
   */
  long readUnsignedInteger(int contextTag)
    throws AsnException;

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * This method is a convenience for Unsigned values that are
   * constrained to be within the valid range of a Java int.
   *
   * @throws AsnException if next value in
   *                      data stream is not an unsigned integer.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   * @return integer value.
   */
  int readUnsignedInt()
    throws AsnException;

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * This method is a convenience for Unsigned values that are
   * constrained to be within the valid range of a Java int.
   *
   * @param contextTag the given context tag.
   * @return integer value.
   * @throws AsnException if next value in
   *                      data stream is not proper type.
   * @throws AsnException if value does not
   *                      have proper context tag.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   */
  int readUnsignedInt(int contextTag)
    throws AsnException;

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * This method is a convenience for Unsigned values that are
   * constrained to be within the valid range of a Java int.
   *
   * @throws AsnException if next value in
   *                      data stream is not an unsigned integer.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   * @return a BUnsigned with the integer value.
   */
  BBacnetUnsigned readUnsigned()
    throws AsnException;

  /**
   * Reads an unsigned integer value from the Asn data stream.
   * This method is a convenience for Unsigned values that are
   * constrained to be within the valid range of a Java int.
   *
   * @param contextTag the given context tag.
   * @return a BUnsigned with the integer value.
   * @throws AsnException if next value in
   *                      data stream is not proper type.
   * @throws AsnException if value does not
   *                      have proper context tag.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   */
  BBacnetUnsigned readUnsigned(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
// Signed Integer Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a signed integer value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a signed integer.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   * @return integer value.
   */
  int readSignedInteger()
    throws AsnException;

  /**
   * Reads a signed integer value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not proper type
   * @throws AsnException if value does not
   *                      have proper context tag
   * @throws AsnException if value is too
   *                      large to be represented in an int
   * @return integer value
   */
  int readSignedInteger(int contextTag)
    throws AsnException;

  /**
   * Reads a signed integer value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a signed integer.
   * @throws AsnException if value is too
   *                      large to be represented in an int.
   * @return integer value.
   */
  BInteger readSigned()
    throws AsnException;

  /**
   * Reads a signed integer value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not proper type
   * @throws AsnException if value does not
   *                      have proper context tag
   * @throws AsnException if value is too
   *                      large to be represented in an int
   * @return integer value
   */
  BInteger readSigned(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Real Number Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a real (float) value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not proper type
   * @throws AsnException if value does not
   *                      have proper length
   * @return value
   */
  float readReal()
    throws AsnException;


  /**
   * Reads a real (float) value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is a real
   * @throws AsnException if value does not
   *                      have proper context tag
   * @throws AsnException if value does not
   *                      have proper length
   * @return value
   */
  float readReal(int contextTag)
    throws AsnException;

  /**
   * Reads a real (float) value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not proper type
   * @throws AsnException if value does not
   *                      have proper length
   * @return value
   */
  BFloat readFloat()
    throws AsnException;


  /**
   * Reads a real (float) value from the
   * Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is a real
   * @throws AsnException if value does not
   *                      have proper context tag
   * @throws AsnException if value does not
   *                      have proper length
   * @return value
   */
  BFloat readFloat(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Double Precision Real Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a double precision real value (double) from the Asn data stream.
   *
   * @throws AsnException if next value in data stream is not a double.
   * @throws AsnException if value does not have proper length.
   * @return value.
   */
  double readDouble()
    throws AsnException;

  /**
   * Reads a double precision real value (double) with the given context tag
   * from the Asn data stream.
   *
   * @param contextTag
   * @throws AsnException if next value in data stream is a not a double.
   * @throws AsnException if value does not have proper context tag.
   * @throws AsnException if value does not have proper length.
   * @return value
   */
  double readDouble(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Octet String Values
////////////////////////////////////////////////////////////////

  /**
   * Reads an octet string from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an octet string
   * @return array of bytes
   */
  byte[] readOctetString()
    throws AsnException;

  /**
   * Reads an octet string from the Asn data stream.
   *
   * @param contextTag tag
   * @throws AsnException if next value in
   *                      data stream is not a octet string
   * @throws AsnException if the next value
   *                      does not have the specified context tag
   * @return array of bytes
   */
  byte[] readOctetString(int contextTag)
    throws AsnException;

  /**
   * Reads an octet string from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an octet string
   * @return a BBacnetOctetString
   */
  BBacnetOctetString readBacnetOctetString()
    throws AsnException;

  /**
   * Reads an octet string from the Asn data stream.
   *
   * @param contextTag tag
   * @throws AsnException if next value in
   *                      data stream is not a octet string
   * @throws AsnException if the next value
   *                      does not have the specified context tag
   * @return a BBacnetOctetString
   */
  BBacnetOctetString readBacnetOctetString(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Character String Values
////////////////////////////////////////////////////////////////

//  /**
//   * Reads the encoding type of a character string.
//   * @returns a BCharacterSetEncoding representing
//   *          the encoding used for the string.
//   * @throws AsnException is the next value in the data stream
//   *         is not a character string.
//   */
//  BCharacterSetEncoding peekEncoding()
//    throws AsnException;

//  /**
//   * Reads the encoding type of a character string.
//   * @param contextTag
//   * @returns a BCharacterSetEncoding representing
//   *          the encoding used for the string.
//   * @throws AsnException is the next value in the data stream
//   *         is not a character string.
//   */
//  BCharacterSetEncoding peekEncoding(int contextTag)
//    throws AsnException;

  /**
   * Reads character string from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a character string
   * @throws AsnException if string is encoded
   *                      using an unsupported format
   * @return array of bytes
   */
  String readCharacterString()
    throws AsnException;

  /**
   * Reads a character string with the given
   * context tag from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a character string
   * @throws AsnException if the next value
   *                      does not have the specified context tag
   * @throws AsnException if string is encoded
   *                      using an unsupported format
   * @return array of bytes
   */
  String readCharacterString(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Bit String Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a bit string from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a bit string.
   * @return array of booleans representing bits.
   */
  BBacnetBitString readBitString()
    throws AsnException;

  /**
   * Reads a bit string from the Asn data stream.
   *
   * @param contextTag
   * @throws AsnException if next value in
   *                      data stream is not a bit string.
   * @throws AsnException if the next value
   *                      does not have the specified context tag.
   * @return array of booleans representing bits.
   */
  BBacnetBitString readBitString(int contextTag)
    throws AsnException;

//  /**
//   * Convenience method to read status flags into a BStatus.
//   * @returns BStatus constructed from encoded Status_Flags.
//   * @throws AsnException if next value in
//   *         data stream is not a bit string.
//   */
//  BStatus readStatusFlags()
//    throws AsnException;
//
//  /**
//   * Convenience method to read status flags into a BStatus.
//   * @param contextTag
//   * @returns BStatus constructed from encoded Status_Flags.
//   * @throws AsnException if next value in
//   *         data stream is not a bit string.
//   */
//  BStatus readStatusFlags(int contextTag)
//    throws AsnException;


////////////////////////////////////////////////////////////////
//  Enumerated Values
////////////////////////////////////////////////////////////////

  /**
   * Reads an enumerated value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an enumeration
   * @throws AsnException if enumeration is too
   *                      large to be represented in an int
   * @return enumerated value as an int
   */
  int readEnumerated()
    throws AsnException;

  /**
   * Reads an enumerated value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an enumeration
   * @throws AsnException if value does not
   *                      have proper context tag
   * @throws AsnException if enumeration is too
   *                      large to be represented in an int
   * @return value
   */
  int readEnumerated(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Date Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a date value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a Date.
   */
  BBacnetDate readDate()
    throws AsnException;

  /**
   * Reads a context-tagged date value from the Asn data stream.
   *
   * @param contextTag
   * @throws AsnException if next value in
   *                      data stream is not a Date.
   * @throws AsnException if value does not
   *                      have proper context tag.
   */
  BBacnetDate readDate(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Time Values
////////////////////////////////////////////////////////////////

  /**
   * Reads a time value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not a Time.
   */
  BBacnetTime readTime()
    throws AsnException;

  /**
   * Reads a context-tagged time value from the Asn data stream.
   *
   * @param contextTag
   * @throws AsnException if next value in
   *                      data stream is not a Time.
   * @throws AsnException if value does not
   *                      have proper context tag.
   */
  BBacnetTime readTime(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Object Identifier Values
////////////////////////////////////////////////////////////////

  /**
   * Reads an Object Identifier value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an object id.
   * @return value as a BBacnetObjectIdentifier
   */
  BBacnetObjectIdentifier readObjectIdentifier()
    throws AsnException;

  /**
   * Reads a context-tagged Object Identifier value from the Asn data stream.
   *
   * @throws AsnException if next value in
   *                      data stream is not an Object Id.
   * @throws AsnException if value does not
   *                      have proper context tag.
   * @return value as a BBacnetObjectIdentifier.
   */
  BBacnetObjectIdentifier readObjectIdentifier(int contextTag)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Constructed Data
////////////////////////////////////////////////////////////////

  byte[] readContextTaggedData()
    throws AsnException;

  /**
   * Returns the encoded value surrounded by the
   * given opening / closing tag number.
   */
  byte[] readEncodedValue(int tagNumber)
    throws AsnException;


////////////////////////////////////////////////////////////////
//  Utility Methods
////////////////////////////////////////////////////////////////

  /**
   * Reads an array of bytes from the input stream.
   * Overrides read() in InputStream, which
   * throws an IOException.
   */
  int read(byte[] array);


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  /**
   * This constant is returned when the
   * end of data has been reached
   */
  int END_OF_DATA = -1;

}