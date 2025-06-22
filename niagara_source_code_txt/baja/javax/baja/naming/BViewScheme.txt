/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BViewScheme is a passive scheme that annotates an existing
 * ord with a view typespec.  It is used to navigate between
 * multiple user agents on a given target.  The body of a view
 * query should be a valid type spec of the user agent.
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 3$ $Date: 2/10/03 10:26:36 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "view"
)
@NiagaraSingleton
public class BViewScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BViewScheme(808776234)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BViewScheme INSTANCE = new BViewScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BViewScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BViewScheme()
  {
    super("view");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of ViewQuery.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new ViewQuery(getId(), queryBody);
  }

  /**
   * Return base.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    return base;
  }
}
