/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bql;

import javax.baja.agent.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bql.*;

/**
 * BBqlScheme provides support for the "bql" ord scheme.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 18$ $Date: 7/22/08 2:42:46 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "bql"
)
@NiagaraSingleton
public class BBqlScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bql.BBqlScheme(1425415134)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BBqlScheme INSTANCE = new BBqlScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBqlScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BBqlScheme()
  {
    super("bql");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  @Override
  public OrdQuery parse(String queryText)
  {
    try
    {
      return BqlQuery.make(queryText);
    }
    catch(RuntimeException e)
    {
      throw new SyntaxException(queryText, e);
    }
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
  {
    BISession session = toSession(base);
    BIBqlResolver resolver = getResolver(session);
    if (resolver == null) throw new InvalidOrdBaseException("" + base.get());
    
    String bqlText = ((BqlQuery)query).getUnescaped();
    if ((bqlText == null) || (bqlText.length() == 0))
      return new OrdTarget(base, (BObject)resolver);
    else
      return resolver.resolve(session, base, (BqlQuery)query);
  }

  /**
   * Get the authority for the specified base.
   */
  private BISession toSession(OrdTarget baseTarget)
  {
    if (baseTarget == null) throw new InvalidOrdBaseException("null");
    
    OrdTarget target = baseTarget;
    BISession session = null;
    while ((target != null) && ((session = BOrd.toSession(target.get())) == null))
      target = target.getBaseOrdTarget();
    
    if (session != null) return session;
    
    throw new InvalidOrdBaseException(""+baseTarget.get());
  }

  /**
   * Get a resolver for the specified authority.
   */
  private BIBqlResolver getResolver(BISession session)
  {

    // Get all the instances of BIBqlResolver that are
    // registered as agents on the authority type.
    AgentList agents = Sys.getRegistry().getAgents(session.getType().getTypeInfo());
    agents = agents.filter(AgentFilter.is(BIBqlResolver.TYPE));
    if (agents.size() == 0)
    {
      return BLocalBqlResolver.INSTANCE;
    }
    return (BIBqlResolver)agents.getDefault().getInstance();
  }
}
