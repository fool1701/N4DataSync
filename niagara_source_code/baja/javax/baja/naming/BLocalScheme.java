/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BLocalScheme is the ord scheme represented as "local:".
 * It is a shortcut for for "host: localhost" which always
 * maps to BLocalHost.INSTANCE.
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 8$ $Date: 5/19/03 11:14:35 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "local"
)
@NiagaraSingleton
public class BLocalScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BLocalScheme(3945117512)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BLocalScheme INSTANCE = new BLocalScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BLocalScheme()
  {
    super("local");
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
   * Always return BLocalHost.INSTANCE
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    return new OrdTarget(base, BLocalHost.INSTANCE);
  }

////////////////////////////////////////////////////////////////
// LocalQuery
////////////////////////////////////////////////////////////////  

  static class LocalQuery implements OrdQuery
  {

    @Override
    public boolean isHost() { return true; }

    @Override
    public boolean isSession() { return true; }
  
    @Override
    public void normalize(OrdQueryList list, int index) { list.trim(index); }

    @Override
    public String getScheme() { return "local"; }
    
    @Override
    public String getBody() { return ""; }
    
    public String toString() { return "local:"; }
  
  }

  static final LocalQuery QUERY = new LocalQuery();

  /** This the "local:" ord. */
  public static final BOrd ORD = BOrd.make("local:");
}
