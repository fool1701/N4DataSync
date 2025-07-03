/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nav.BNavContainer;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

import com.tridium.util.BSessionInfo;

/**
 * BSession
 *
 * @author    Brian Frank
 * @creation  15 May 03
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:01 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSession
  extends BNavContainer
  implements BISession
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BSession(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSession.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  protected BSession(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

  protected BSession(String name)
  {
    super(name);
  }

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////
  
  /**
   * Get the Context to use for this session.  Default 
   * implementation returns null.
   */
  @Override
  public Context getSessionContext()
  {
    return null;
  }
  
  public BSessionInfo getSessionInfo()
  {
    return null;
  }
}
