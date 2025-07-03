/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.naming.*;
import javax.baja.sys.*;

/**
 * A Queryable object knows how to provide a cursor for iterating
 * through objects that satisfy a query.
 *
 * @author    John Sublett
 * @creation  18 Apr 2002
 * @version   $Revision: 9$ $Date: 3/28/05 10:03:22 AM EST$
 * @since     Baja 1.0
 */
public interface Queryable
  extends javax.baja.naming.Queryable
{
  /**
   * Get a collection of objects that satisfy the specified query.
   * The returned collection may include a superset of the objects
   * that satisfy the query.  The query processor will guarantee
   * that the end result only contains objects that satisfy the query.
   *
   * @param q The query that qualifies the result set.
   * @return Returns a collection for iterating through the qualified result.
   */
  @Override
  public BObject bqlQuery(OrdTarget base, OrdQuery q);
}