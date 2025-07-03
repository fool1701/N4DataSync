/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;
import javax.baja.xml.*;

/**
 * BRdbmsScheme is used to identify BRdbms instances which 
 * are mounted and connected during resolution.
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 2$ $Date: 3/8/05 9:30:49 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BRdbmsScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbmsScheme(2979906276)1.0$ @*/
/* Generated Sat Jan 29 17:54:41 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  protected BRdbmsScheme(String id)
  {
    super(id);
  }

////////////////////////////////////////////////////////////////
// abstract
////////////////////////////////////////////////////////////////

  public abstract void pickle(BISession session, XElem pickle);
  public abstract BISession unpickle(XElem pickle);
  
  public abstract String getAuthName();
  public abstract BRdbms newDatabase(BHost host, RdbmsQuery query);

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return the BRdbms for address.
   */
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    BRdbmsSession session = BRdbmsSession.make(
      (BHost)base.get(), (RdbmsQuery) query, this);
    
    try
    {
      // ensure we are connected
      session.connect();
      return new OrdTarget(base, session);
    }
    catch(AuthenticationException e)
    {
      // let this propogate so that the workbench can 
      // prompt the user with the authentication dialog
      throw e;
    }
    catch(Throwable e)
    {
      // some other problem - wrap with UnresolvedException
      // workbench will try to map to meaningful error message
      throw new UnresolvedException(query.toString(), e);
    }
  }
}
