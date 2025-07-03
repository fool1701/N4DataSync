/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.alarm;

import javax.baja.agent.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.alarm.db.*;

/**
 * The alarm scheme provides access to the alarm database.
 * 
 * @author    John Sublett
 * @creation  22 Sep 2004
 * @version   $Revision: 2$ $Date: 4/18/05 4:59:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "alarm"
)
@NiagaraSingleton
public class BAlarmScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmScheme(4293659472)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BAlarmScheme INSTANCE = new BAlarmScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  public BAlarmScheme()
  {
    super("alarm");
  }

  /**
   * This method gives scheme the chance to return a custom 
   * subclass of OrdQuery with a scheme specific API.  The
   * default implementation returns an instance of BasicQuery.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new AlarmQuery(queryBody);
  }
  
  /**
   * This is the subclass hook for resolve after the 
   * default implementation has mapped the ord to an 
   * instanceof BSpace.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
  {
    BISession session = toSession(base);
    BIAlarmResolver resolver = getResolver(session);
    if (resolver == null) throw new InvalidOrdBaseException("" + base.get());
    
    return resolver.resolve(session, base, (AlarmQuery)query);
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
  private BIAlarmResolver getResolver(BISession session)
  {
    if (session == null || session instanceof BLocalHost)
      return BLocalAlarmResolver.INSTANCE;

    // Get all the instances of BIBqlResolver that are
    // registered as agents on the authority type.
    AgentList agents = Sys.getRegistry().getAgents(session.getType().getTypeInfo());
    agents = agents.filter(AgentFilter.is(BIAlarmResolver.TYPE));
    if (agents.size() == 0) return null;
    return (BIAlarmResolver)agents.getDefault().getInstance();
  }
}
