/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


/**
 * ErrorType is an interface representing the Bacnet Error sequence.
 * It is called ErrorType instead of Error to avoid conflict with
 * java.lang.Error.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 14 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
public interface ErrorType
{
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
   * Encode the property value data to Asn.
   *
   * @param out the Asn encoder.
   */
  void writeEncoded(AsnOutput out);

  /**
   * Decode the property value data from Asn.
   *
   * @param in the Asn decoder.
   * @throws AsnException if there is an Asn error.
   */
  void readEncoded(AsnInput in)
    throws AsnException;
}
 