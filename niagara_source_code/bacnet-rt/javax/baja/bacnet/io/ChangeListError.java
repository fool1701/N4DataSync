/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


/**
 * The ChangeListError sequence is returned in response to
 * AddListElement and RemoveListElement requests when the
 * request cannot be executed.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 04 Sep 03
 * @since Niagara 3 Bacnet 1.0
 */
public interface ChangeListError
{
  /**
   * Get the first element number that failed to be
   * added.
   *
   * @return the firstFailedElementNumber.
   */
  long getFirstFailedElementNumber();

  /**
   * Encode the ChangeList-Error to Asn.
   *
   * @param out
   */
  public void writeAsn(AsnOutput out);

  /**
   * Decode the ChangeList-Error from Asn.
   *
   * @param in
   */
  public void readAsn(AsnInput in)
    throws AsnException;

  int ERROR_TYPE_TAG = 0;
  int FIRST_FAILED_ELEMENT_NUMBER_TAG = 1;
}
