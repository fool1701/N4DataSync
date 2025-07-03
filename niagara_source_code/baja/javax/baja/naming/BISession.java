/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BISession is implemented by BObjects which 
 * represent a session within a host.
 *
 * @author    Brian Frank
 * @creation  1 Apr 03
 * @version   $Revision: 3$ $Date: 2/8/05 2:53:37 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BISession
  extends BINavNode
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BISession(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISession.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Connection State
////////////////////////////////////////////////////////////////

  /**
   * Does the session have an active logical connection.
   */
  public boolean isConnected();
  
  /**
   * Open a logical connection.
   */
  public void connect()
    throws Exception;

  /**
   * Terminate the connection, but leave the session 
   * mounted in the navigation tree.
   */
  public void disconnect();

  /**
   * Close is defined as a disconnect, plus the session 
   * should be unmounted from the navigation tree.
   */
  public void close();

////////////////////////////////////////////////////////////////
// Naming
////////////////////////////////////////////////////////////////

  /**
   * Get the parent host or null if unmounted.
   */
  public BHost getHost();  

  /**
   * Get the host absolute ord for this object.
   */
  public BOrd getAbsoluteOrd();   

  /**
   * Get the ord of this session within its parent host.
   */
  public BOrd getOrdInHost();   

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////
  
  /**
   * Get the Context to use for this session.
   */
  public Context getSessionContext();
}
