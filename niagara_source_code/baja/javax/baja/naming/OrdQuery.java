/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;

/**
 * OrdQuery encapsulates a single query within a BOrd.  
 * A query is composed of a scheme id and an ASCII body.
 *
 * @author    Brian Frank
 * @creation  3 Jan 03
 * @version   $Revision: 9$ $Date: 3/28/05 9:23:01 AM EST$
 * @since     Baja 1.0
 */
public interface OrdQuery
{

  /**
   * Get the scheme id of the query.
   */
  String getScheme();
  
  /**
   * Get the ASCII body of the query.
   */
  String getBody();
  
  /**
   * Return if this OrdQuery is an host.  Host queries are
   * absolute and resolve to a BHost.  Since host queries are 
   * absolute they trump any queries to their left during 
   * normalization.
   */
  boolean isHost();

  /**
   * Return if this OrdQuery is a session.  Sessions queries 
   * are absolute within a host, and resolve to a BISession.
   */
  boolean isSession();
  
  /**
   * Starting in Niagara 4.13, this method is only called by the framework from
   * the {@link #normalize(OrdQueryList, int, Context)} default implementation
   * (if that method is not overridden by the subclass). Refer to
   * {@link #normalize(OrdQueryList, int, Context)} for a description of when
   * this method is called. It is commonly used when the Context parameter is
   * not needed to perform normalization. Note that this method may also be
   * called directly (outside of the framework usage), but that is not common.
   */
  void normalize(OrdQueryList list, int index);

  /**
   * This method is called by the framework during BOrd normalization to give
   * each query the ability to normalize itself.  The index specifies the
   * location of this query in the parsed queries list.  This method allows
   * OrdQueries to merge or truncate relative ORDs. A Context parameter is also
   * provided to give more information about the context in which this method
   * was called.  The Context parameter may be null, but, for example, it can
   * also be {@link #RESOLVING_ORD_CX} if this method is being called at BOrd
   * resolution time (see
   * {@link BOrd#resolve(BObject, Context)}). If this
   * method is not overridden by subclasses, the default behavior is to ignore
   * the Context parameter and reroute the call directly to
   * {@link #normalize(OrdQueryList, int)}.
   *
   * @since Niagara 4.13
   */
  default void normalize(OrdQueryList list, int index, Context cx)
  {
    normalize(list, index);
  }

   /**
   * Return {@code scheme + ":" + body}.
   */  
  String toString();

  /**
   * Constant Context instance passed as the final parameter to the
   * {@link #normalize(OrdQueryList, int, Context)} method only when it is
   * called at ORD resolution time.
   *
   * @since Niagara 4.13
   */
  Context RESOLVING_ORD_CX = BFacets.make("resolvingOrd", true);
}

