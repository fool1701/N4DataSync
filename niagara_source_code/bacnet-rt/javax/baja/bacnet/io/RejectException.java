/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;


import javax.baja.nre.util.TextUtil;

import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetRejectReason;

/**
 * RejectExceptions are thrown when an error is encountered that
 * should result in a transaction being rejected.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 11/29/01 1:24:01 PM$
 * @creation 31 Jul 00
 * @since Niagara 3 Bacnet 1.0
 */

public class RejectException
  extends BacnetException
{
  /**
   * Constructor
   *
   * @param rejectReason the Bacnet Reject Reason associated with this exception.
   */
  public RejectException(int rejectReason)
  {
    super(BBacnetRejectReason.tag(rejectReason));
    this.rejectReason = rejectReason;
  }

  /**
   * Returns the BBacnetRejectReason
   *
   * @return the Bacnet Reject Reason associated with this exception.
   */
  public int getRejectReason()
  {
    return rejectReason;
  }

  /**
   * To String.
   */
  public String toString()
  {
    return lex.getText("RejectException.reject") + ":" + TextUtil.toFriendly(getMessage());
  }

  private int rejectReason;
}
