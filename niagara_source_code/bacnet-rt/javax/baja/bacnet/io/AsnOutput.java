/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.sys.*;

import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BCharacterSetEncoding;

/**
 * This interface specifies the methods for encoding Niagara
 * quantities into BACnet ASN.1 primitives.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 09 May 02
 * @since Niagara 3 Bacnet 1.0
 */

public interface AsnOutput
{

/////////////////////////////////////////////////////////
//  Null Values
/////////////////////////////////////////////////////////

  /**
   * Writes a Null to the output stream.
   * See Bacnet Clause 20.2.2
   */
  void writeNull();

  /**
   * Writes a context-tagged Null value to the output stream.
   * See Bacnet Clause 20.2.2
   */
  void writeNull(int contextTag);


/////////////////////////////////////////////////////////////////
//  Booleans
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged boolean value to the output stream.
   * See Bacnet Clause 20.2.3
   * for encoding rules.
   *
   * @param value to be written
   */
  void writeBoolean(boolean value);

  /**
   * Writes a context-tagged boolean value to the output stream.
   * See Bacnet Clause 20.2.3 for encoding rules.
   *
   * @param contextTag tag to be used
   * @param value      to be written
   */
  void writeBoolean(int contextTag, boolean value);

  /**
   * Writes an application-tagged boolean value to the output stream.
   * See Bacnet Clause 20.2.3
   * for encoding rules.
   *
   * @param value to be written
   */
  void writeBoolean(BBoolean value);

  /**
   * Writes a context-tagged boolean value to the output stream.
   * See Bacnet Clause 20.2.3 for encoding rules.
   *
   * @param contextTag tag to be used
   * @param value      to be written
   */
  void writeBoolean(int contextTag, BBoolean value);


/////////////////////////////////////////////////////////////////
//  Unsigned Integers
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged unsigned integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeUnsignedInteger(long value);

  /**
   * Writes a context-tagged unsigned integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeUnsignedInteger(int contextTag, long value);

  /**
   * Writes an application-tagged unsigned integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeUnsigned(BBacnetUnsigned value);

  /**
   * Writes a context-tagged unsigned integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeUnsigned(int contextTag, BBacnetUnsigned value);


/////////////////////////////////////////////////////////////////
//  Signed Integers
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged signed integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeSignedInteger(int value);

  /**
   * Writes a context-tagged signed integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeSignedInteger(int contextTag, int value);

  /**
   * Writes an application-tagged signed integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeSignedInteger(BInteger value);

  /**
   * Writes a context-tagged signed integer value
   * to the output stream.<p>
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param value to be written.
   */
  void writeSignedInteger(int contextTag, BInteger value);


/////////////////////////////////////////////////////////////////
//  Real Numbers
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged real number to the output stream.
   * See Bacnet Clause 20.2.6 for encoding rules.
   *
   * @param value value to be written
   */
  void writeReal(double value);

  /**
   * Writes a context-tagged real number (float) value to the output stream.
   * See Bacnet Clause 20.2.6 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      value to be written
   */
  void writeReal(int contextTag, double value);

  /**
   * Writes an application-tagged real number to the output stream.
   * See Bacnet Clause 20.2.6 for encoding rules.
   *
   * @param value value to be written
   */
  void writeReal(BNumber value);

  /**
   * Writes a context-tagged real number (float) value to the output stream.
   * See Bacnet Clause 20.2.6 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      value to be written
   */
  void writeReal(int contextTag, BNumber value);


/////////////////////////////////////////////////////////////////
//  Double Precision Real Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged double precision number to the output stream.
   * See Bacnet Clause 20.2.7 for encoding rules.
   *
   * @param value value to be written
   */
  void writeDouble(double value);

  /**
   * Writes a context-tagged double precision number to the output stream.
   * See Bacnet Clause 20.2.7 for encoding rules.
   *
   * @param value value to be written
   */
  void writeDouble(int contextTag, double value);

  /**
   * Writes an application-tagged double precision number to the output stream.
   * See Bacnet Clause 20.2.7 for encoding rules.
   *
   * @param value value to be written
   */
  void writeDouble(BNumber value);

  /**
   * Writes a context-tagged double precision number to the output stream.
   * See Bacnet Clause 20.2.7 for encoding rules.
   *
   * @param value value to be written
   */
  void writeDouble(int contextTag, BNumber value);


/////////////////////////////////////////////////////////////////
//  Octet String Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged byte array to the output stream.
   * See Bacnet Clause 20.2.8 for encoding rules.
   *
   * @param octetString octet string to be encoded
   */
  void writeOctetString(byte[] octetString);

  /**
   * Writes a context-tagged octet string (byte array) value to the output stream.
   * See Bacnet Clause 20.2.8 for encoding rules.
   *
   * @param contextTag  context tag to use
   * @param octetString octet string to be encoded
   */
  void writeOctetString(int contextTag, byte[] octetString);

  /**
   * Writes an application-tagged byte array to the output stream.
   * See Bacnet Clause 20.2.8 for encoding rules.
   *
   * @param octetString octet string to be encoded
   */
  void writeOctetString(BBacnetOctetString octetString);

  /**
   * Writes a context-tagged octet string (byte array) value to the output stream.
   * See Bacnet Clause 20.2.8 for encoding rules.
   *
   * @param contextTag  context tag to use
   * @param octetString octet string to be encoded
   */
  void writeOctetString(int contextTag, BBacnetOctetString octetString);


/////////////////////////////////////////////////////////////////
//  Character String Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged character string
   * to the output stream using the local device's
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param value to be written
   */
  void writeCharacterString(String value);

  /**
   * Writes an application-tagged character string
   * to the output stream using the specified
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param value
   * @param encoding
   */
  void writeCharacterString(String value, BCharacterSetEncoding encoding);

  /**
   * Writes a context-tagged character string
   * to the output stream using the local device's
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      string to be encoded
   */
  void writeCharacterString(int contextTag, String value);

  /**
   * Writes a context-tagged character string
   * to the output stream using the specified
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      string to be encoded
   */
  void writeCharacterString(int contextTag, String value, BCharacterSetEncoding encoding);

  /**
   * Writes an application-tagged character string
   * to the output stream using the local device's
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param value to be written
   */
  void writeCharacterString(BString value);

  /**
   * Writes an application-tagged character string
   * to the output stream using the specified
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param value
   * @param encoding
   */
  void writeCharacterString(BString value, BCharacterSetEncoding encoding);

  /**
   * Writes a context-tagged character string
   * to the output stream using the local device's
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      string to be encoded
   */
  void writeCharacterString(int contextTag, BString value);

  /**
   * Writes a context-tagged character string
   * to the output stream using the specified
   * encoding. See Bacnet Clause 20.2.9 for
   * encoding rules.
   *
   * @param contextTag context tag to use
   * @param value      string to be encoded
   */
  void writeCharacterString(int contextTag, BString value, BCharacterSetEncoding encoding);


/////////////////////////////////////////////////////////////////
//  Bit String Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged bit string value to the output stream.
   * See Bacnet Clause 20.2.10 for encoding rules.
   *
   * @param bitString bits to be encoded
   */
  void writeBitString(boolean[] bitString);

  /**
   * Writes a context-tagged bit string value to the output stream.
   * See Bacnet Clause 20.2.10 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param bitString  bits to encode
   */
  void writeBitString(int contextTag, boolean[] bitString);

  /**
   * Writes an application-tagged bit string value to the output stream.
   * See Bacnet Clause 20.2.10 for encoding rules.
   *
   * @param bitString bits to be encoded
   */
  void writeBitString(BBacnetBitString bitString);

  /**
   * Writes a context-tagged bit string value to the output stream.
   * See Bacnet Clause 20.2.10 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param bitString  bits to encode
   */
  void writeBitString(int contextTag, BBacnetBitString bitString);


/////////////////////////////////////////////////////////////////
//  Enumerated Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged enumerated value to the output stream.
   * See Bacnet Clause 20.2.11 for encoding rules.
   *
   * @param enumValue enum value to encode
   */
  void writeEnumerated(int enumValue);

  /**
   * Writes a context-tagged enumerated value to the output stream.
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param enumValue  value to encode
   */
  void writeEnumerated(int contextTag, int enumValue);

  /**
   * Writes an application-tagged enumerated value to the output stream.
   * See Bacnet Clause 20.2.11 for encoding rules.
   *
   * @param e enum to encode
   */
  void writeEnumerated(BEnum e);

  /**
   * Writes a context-tagged enumerated value to the output stream.
   * See Bacnet Clause 20.2.4 for encoding rules.
   *
   * @param contextTag context tag to use
   * @param e  enum to encode
   */
  void writeEnumerated(int contextTag, BEnum e);


/////////////////////////////////////////////////////////////////
//  Date Values
/////////////////////////////////////////////////////////////////

  /**
   * Write application-tagged date values to the output stream.
   *
   * @param year
   * @param month
   * @param day
   * @param weekday
   */
  void writeDate(int year, int month, int day, int weekday);

  /**
   * Write context-tagged date values to the output stream.
   *
   * @param contextTag
   * @param year
   * @param month
   * @param day
   * @param weekday
   */
  void writeDate(int contextTag, int year, int month, int day, int weekday);

  /**
   * Writes an application-tagged Date value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Date bytes.
   *
   * @param date the BBacnetDate to be written
   */
  void writeDate(BBacnetDate date);

  /**
   * Writes a context-tagged Date value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   *
   * @param contextTag
   * @param date       the BBacnetDate to be written
   */
  void writeDate(int contextTag, BBacnetDate date);

  /**
   * Writes an application-tagged Date value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Date bytes.
   *
   * @param date the BAbsTime to be written
   */
  void writeDate(BAbsTime date);

  /**
   * Writes a context-tagged Date value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   *
   * @param contextTag
   * @param date       the BAbsTime to be written
   */
  void writeDate(int contextTag, BAbsTime date);


/////////////////////////////////////////////////////////////////
//  Time Values
/////////////////////////////////////////////////////////////////

  /**
   * Write application-tagged time values to the output stream.
   *
   * @param hour
   * @param minute
   * @param second
   * @param hundredth
   */
  void writeTime(int hour, int minute, int second, int hundredth);

  /**
   * Write context-tagged time values to the output stream.
   *
   * @param contextTag
   * @param hour
   * @param minute
   * @param second
   * @param hundredth
   */
  void writeTime(int contextTag, int hour, int minute, int second, int hundredth);

  /**
   * Writes an application-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param time BBacnetTime to be written
   */
  void writeTime(BBacnetTime time);

  /**
   * Writes a context-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param contextTag
   * @param time       BBacnetTime to be written
   */
  void writeTime(int contextTag, BBacnetTime time);

  /**
   * Writes an application-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param time BTime to be written
   */
  void writeTime(BTime time);

  /**
   * Writes a context-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param contextTag
   * @param time       BTime to be written
   */
  void writeTime(int contextTag, BTime time);

  /**
   * Writes an application-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param time BAbsTime to be written
   */
  void writeTime(BAbsTime time);

  /**
   * Writes a context-tagged Time value to the output stream.
   * See Bacnet Clause 20.2.12 for encoding rules.
   * See Bacnet Clause 21, section Application Types,
   * for the format of the Time bytes.
   *
   * @param contextTag
   * @param time       BAbsTime to be written
   */
  void writeTime(int contextTag, BAbsTime time);


/////////////////////////////////////////////////////////////////
//  Object Identifier Values
/////////////////////////////////////////////////////////////////

  /**
   * Writes an application-tagged object id value to the output stream.
   * See Bacnet Clause 20.2.14 for encoding rules.
   *
   * @param objectType
   * @param instanceNumber
   */
  void writeObjectIdentifier(int objectType, int instanceNumber);

  /**
   * Writes a context-tagged object id value to the output stream.
   * See Bacnet Clause 20.2.14 for encoding rules.
   *
   * @param contextTag
   * @param objectType
   * @param instanceNumber
   */
  void writeObjectIdentifier(int contextTag, int objectType, int instanceNumber);

  /**
   * Writes an application-tagged object id value to the output stream.
   * See Bacnet Clause 20.2.14 for encoding rules.
   *
   * @param objectId object ID to be encoded
   */
  void writeObjectIdentifier(BBacnetObjectIdentifier objectId);

  /**
   * Writes a context-tagged object id value to the output stream.
   * See Bacnet Clause 20.2.14 for encoding rules.
   *
   * @param contextTag
   * @param objectId
   */
  void writeObjectIdentifier(int contextTag, BBacnetObjectIdentifier objectId);


/////////////////////////////////////////////////////////////////
//  Previously Encoded Values
/////////////////////////////////////////////////////////////////

  /**
   * Write a previously encoded value to the
   * data stream, without a tag number.
   * <p>
   * 2001-08-29:
   * This is used for writing priority array elements
   * right now.  Perhaps additional uses will emerge...
   */
  void writeEncodedValue(byte[] encodedValue);

  /**
   * Writes a previously encoded value to the
   * data stream using the given tag number for
   * the opening and closing tags.
   *
   * @param tagNumber    tag number to use
   * @param encodedValue array of bytes containing
   *                     encoded value
   */
  void writeEncodedValue(int tagNumber, byte[] encodedValue);


/////////////////////////////////////////////////////////////////
//  Opening / Closing Tags
/////////////////////////////////////////////////////////////////

  /**
   * Writes an opening tag with the given tag
   * number to the data stream.
   * See Bacnet clause 20.2.1.3.2
   */
  public void writeOpeningTag(int tagNumber);

  /**
   * Writes a closing tag with the given tag
   * number to the data stream.
   * See Bacnet clause 20.2.1.3.2
   */
  public void writeClosingTag(int tagNumber);


/////////////////////////////////////////////////////////////////
//  Utility Methods
/////////////////////////////////////////////////////////////////

  /**
   * Writes an array of bytes to the output stream.
   * Overrides write() in OutputStream, which
   * throws an IOException.
   */
  void write(byte[] array);

}
