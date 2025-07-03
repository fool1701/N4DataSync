/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.BacnetException;

/**
 * An AsnException is thrown whenever a error is
 * detected in encoding or decoding an Asn production.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 12/19/01 4:35:43 PM$
 * @creation 28 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */

public class AsnException
  extends BacnetException
{
  /**
   * Constructor with error code.
   * @param code the error code.
  public AsnException(int code)
  {
  super(codeToMessage(code));
  this.code = code;
  }
   */

  /**
   * Constructor with specified detailed message.
   *
   * @param detailMessage the error message.
   */
  public AsnException(String detailMessage)
  {
    super(detailMessage);
  }

  /**
   * Constructor with error code and detail message.
   */

  public String toString()
  {
    return lex.getText("AsnException.asn") + ":" + getMessage();
  }

//  public int getCode() { return code; }

//  private int code = NO_CODE;

//  public static final int NO_CODE = -1;

//  // Possible future expansion/enhancement of this API.
//  public static final String codeToMessage(int code)
//  {
//    return "Asn Exception code: "+code;
//  }
}
