/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


/**
 * PropertyReference contains information to reference
 * a property to be read.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 14 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
public interface PropertyReference
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
   * Encode the property reference data to Asn.
   *
   * @param out the Asn encoder.
   */
  void writeAsn(AsnOutput out);

  /**
   * Decode the property reference data from Asn.
   *
   * @param in the Asn decoder.
   * @throws AsnException if there is an Asn error.
   */
  void readAsn(AsnInput in)
    throws AsnException;
}
 