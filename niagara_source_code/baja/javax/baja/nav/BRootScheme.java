/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BRootScheme is the ord for "root:" which always maps
 * to BNavRoot.INSTANCE.
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 2$ $Date: 5/19/03 11:15:23 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "root"
)
@NiagaraSingleton
public class BRootScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BRootScheme(2950944526)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BRootScheme INSTANCE = new BRootScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRootScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BRootScheme()
  {
    super("root");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  @Override
  public OrdQuery parse(String queryBody)
  {
    return QUERY;
  }

  /**
   * Always return BNavRoot.INSTANCE
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    return new OrdTarget(base, BNavRoot.INSTANCE);
  }

////////////////////////////////////////////////////////////////
// RootQuery
////////////////////////////////////////////////////////////////  

  static class RootQuery implements OrdQuery
  {
    RootQuery() {} 
    
    @Override
    public boolean isHost() { return false; }

    @Override
    public boolean isSession() { return false; }
    
    @Override
    public void normalize(OrdQueryList list, int index) { list.trim(index); }

    @Override
    public String getScheme() { return "root"; }
    
    @Override
    public String getBody() { return ""; }
    
    public String toString() { return "root:"; }
  
  }

  static final RootQuery QUERY = new RootQuery();

  /** This the "root:" ord. */
  public static final BOrd ORD = BOrd.make("root:");
}
