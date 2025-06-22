/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

/**
 * An OutOfRangeException is thrown whenever an
 * out of range error is detected in encoding or
 * decoding an Asn production.
 *
 * @author Joseph Chandler
 * @version $Revision: 2$ $Date: 12/19/01 4:35:43 PM$
 * @creation 28 Jul 00
 * @since Niagara 3.8.107
 */

public class OutOfRangeException
  extends AsnException
{
  /**
   * Constructor with specified detailed message.
   *
   * @param detailMessage the error message.
   */
  public OutOfRangeException(String detailMessage)
  {
    super(detailMessage);
  }

  public String toString()
  {
    return lex.getText("AsnException.asn") + ":" + getMessage();
  }
}
