/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.neql;

import javax.baja.naming.BOrdScheme;
import javax.baja.naming.BasicQuery;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The "namespace" ord scheme is a scheme that sets the "namespace" facet
 * in the resulting OrdTarget but otherwise leaves the base OrdTarget
 * unchanged.
*
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
@NiagaraType(
  ordScheme = "namespace"
)
@NiagaraSingleton
public class BNamespaceScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.neql.BNamespaceScheme(343337283)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BNamespaceScheme INSTANCE = new BNamespaceScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNamespaceScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BNamespaceScheme()
  {
    super("namespace");
  }

  @Override
  public OrdQuery parse(String queryBody)
  {
    String namespace = queryBody.trim();
    if (namespace.length() == 0)
      throw new SyntaxException("Namespace cannot be blank.");

    return new BasicQuery(SCHEME_ID, namespace);
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    String namespace = query.getBody();
    if ((namespace != null) && (namespace.length() != 0))
    {
      BFacets nsFacets = BFacets.make(BFacets.NAMESPACE, namespace);
      return OrdTarget.makeWithFacets(base, nsFacets);
    }
    else
      throw new UnresolvedException("Namespace cannot be blank.");
  }

  public static final String SCHEME_ID = "namespace";
}
