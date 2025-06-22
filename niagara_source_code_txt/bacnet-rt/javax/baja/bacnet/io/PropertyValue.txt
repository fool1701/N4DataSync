/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


/**
 * PropertyValue contains the results of reading a property value,
 * or the value to be written to a property.
 * <p>
 * The result can be either a byte array containing the Asn-encoded
 * value of the property, or a combination of a BBacnetErrorClass and
 * a BBacnetErrorCode containing the reason why a read was unable
 * to be performed.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 12 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
public interface PropertyValue
{
  /**
   * Get the propertyId.
   *
   * @return the propertyID.
   */
  int getPropertyId();

  /**
   * Get the property array index.
   *
   * @return the array index.
   */
  int getPropertyArrayIndex();

  /**
   * Get the encoded value.
   *
   * @return a byte array containing the Asn-encoded value,
   * or null if this is a failure.
   */
  byte[] getPropertyValue();

  /**
   * Get the priority.
   *
   * @return the priority associated with this value.
   */
  int getPriority();

  /**
   * Get the error.
   *
   * @return an ErrorType if this is an error result,
   * or null if this is a success.
   */
  ErrorType getPropertyAccessError();

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

  /**
   * Encode the property value data to Asn.
   *
   * @param out the Asn encoder.
   */
  void writeAsn(AsnOutput out);

  /**
   * Decode the property value data from Asn.
   *
   * @param in the Asn decoder.
   * @throws AsnException if there is an Asn error.
   */
  void readAsn(AsnInput in)
    throws AsnException, RejectException;
}
 