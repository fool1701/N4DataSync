/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.neql;

import javax.baja.naming.OrdQuery;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.query.BQueryScheme;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The "neql" ord scheme is a QueryScheme for the Niagara Entity Query Language.
 * Like BQL, NEQL is an SQL-like query language for querying Niagara systems
 * for Niagara entities.  It is much simpler than BQL in that it only operates
 * on the Entity API and can only inspect tags and entity relationships.
 *
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
@NiagaraType(
  ordScheme = "neql"
)
@NiagaraSingleton
public class BNeqlScheme
  extends BQueryScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.neql.BNeqlScheme(3619750604)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BNeqlScheme INSTANCE = new BNeqlScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNeqlScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BNeqlScheme()
  {
    super("neql");
  }

  @Override
  public OrdQuery parse(String queryBody)
  {
    return new NeqlQuery(queryBody);
  }
}
