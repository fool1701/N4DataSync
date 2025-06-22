/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.naming;

import javax.baja.sys.BObject;
import javax.baja.sys.Context;

/**
 * A RemoteQueryable object is an object that can take a BQL ord,
 * process the query remotely and return the result.
 *
 * @author    John Sublett
 * @creation  16 Oct 2003
 * @version   $Revision: 2$ $Date: 3/28/05 10:03:22 AM EST$
 * @since     Baja 1.0
 */
public interface RemoteQueryable
{
  /**
   * Resolve the specified BQL ord and return the result.
   */
  public BObject bqlQuery(BOrd ord);

  /**
   * Resolve the specified BQL ord and return the result using the additional
   * Context information provided.
   *
   * @since Niagara 4.11
   */
  default BObject bqlQuery(BOrd ord, Context cx)
  {
    return bqlQuery(ord);
  }
}