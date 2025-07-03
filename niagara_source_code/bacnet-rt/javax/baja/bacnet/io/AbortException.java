/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


import javax.baja.nre.util.TextUtil;

import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetAbortReason;

/**
 * AbortExceptions are thrown when an error is encountered that
 * should result in a transaction being aborted.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 11/29/01 1:24:00 PM$
 * @creation 31 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */

public class AbortException
  extends BacnetException
{
  /**
   * Constructor
   *
   * @param abortReason the Bacnet Abort Reason associated with this exception.
   */
  public AbortException(int abortReason)
  {
    super(BBacnetAbortReason.tag(abortReason));
    this.abortReason = abortReason;
  }

  /**
   * Returns the BBacnetAbortReason
   *
   * @return the Bacnet Abort Reason associated with this exception.
   */
  public int getAbortReason()
  {
    return abortReason;
  }

  /**
   * To String.
   */
  public String toString()
  {
    return lex.getText("AbortException.abort") + ":" + TextUtil.toFriendly(getMessage());
  }

  private int abortReason;
}
