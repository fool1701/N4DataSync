/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

/**
 * A DataTypeNotSupportedException is thrown whenever an
 * a provided data type is not in the set of supported data types
 * for a selected field.
 *
 * @author Joseph Chandler
 * @version $Revision: 2$ $Date: 12/19/01 4:35:43 PM$
 * @creation 28 Jul 00
 * @since Niagara 3.8.107
 */

public class DataTypeNotSupportedException
  extends AsnException
{
  /**
   * Constructor with specified detailed message.
   *
   * @param detailMessage the error message.
   */
  public DataTypeNotSupportedException(String detailMessage)
  {
    super(detailMessage);
  }

  public String toString()
  {
    return lex.getText("AsnException.asn") + ":" + getMessage();
  }
}
