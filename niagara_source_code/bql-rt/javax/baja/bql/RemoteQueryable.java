/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.naming.*;
import javax.baja.sys.*;

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
  extends javax.baja.naming.RemoteQueryable
{
  /**
   * Resolve the specified BQL ord and return the result.
   */
  @Override
  public BObject bqlQuery(BOrd ord);
}
  