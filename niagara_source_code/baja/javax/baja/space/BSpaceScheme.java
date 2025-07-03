/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.agent.*;
import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BSpaceScheme is an abstract scheme used for space ord
 * schemes which have different implementations based on
 * the ord base.  A space scheme works as follows:
 * <ol>
 * <li>Map the base to an BISession using toSession()</li>
 * <li>Check if the session has a cached BSpace mounted as
 *     as nav child using the scheme id for the name.</li>
 * <li>If there is no cached BSpace, then we create one using
 *     makeSpaceForSession() and cache it by adding it
 *     as a nav child to the session with a nav name
 *     equal to the scheme id.</li>
 * <li>Route to resolve</li>
 * </ol>
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 9$ $Date: 3/28/05 9:23:04 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSpaceScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BSpaceScheme(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpaceScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with scheme id.
   */
  protected BSpaceScheme(String schemeId)
  {
    super(schemeId);
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * See documentation in class header for how resolve works.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    // map base to session
    BISession session = toSession(base.get());
    
    // get or create space cached on authority
    BSpace space = (BSpace)session.getNavChild(getNavChildSpaceId());
    if (space == null)
    {
      try
      {
        space = makeSpaceForSession(session);
        ((BINavContainer)session).addNavChild(space);
      }
      catch(RuntimeException e)
      {
        // it is possible (especially under local:) that the 
        // space got mounted in a static initializer, but 
        // not instantiated b/c it has a private constructor; 
        // if this is the case, then that is ok
        space = (BSpace)session.getNavChild(getNavChildSpaceId());
        if (space == null) throw e;
      }
    }
    
    // let subclass do the rest
    return resolve(base, query, space);
  }
  
  /**
   * Given an object, map it to a session.  If no session can 
   * not be infered from the object then throw InvalidOrdBaseException.
   */
  public BISession toSession(BObject base)
  {
    BISession session = BOrd.toSession(base);
    if (session != null) return session;
    throw new InvalidOrdBaseException(""+base);
  }
  
  /**
   * Instanstiate the correct type of BSpace
   */
  public BSpace makeSpaceForSession(BISession session)
  {
    Type spaceType = getSpaceType();
    try
    {
      AgentList agents = session.asObject().getAgents();
      agents = agents.filter(AgentFilter.is(spaceType));
      return (BSpace)agents.getDefault().getInstance();
    }
    catch(NoSuchAgentException e)
    {
      throw new InvalidOrdBaseException("Cannot find impl of " + spaceType + " for " + session.getType());
    }
  }

  /**
   * Returns a nav child name for a target space within a session's nav children.
   *
   * @return the default implementation returns <code>getId()</code>
   *
   * @since Niagara 4.4
   */
  protected String getNavChildSpaceId()
  {
    return getId();
  }
  
  /**
   * Get the subtype of BSpace which this scheme
   * is used to identify.
   */
  public abstract Type getSpaceType();
  
  /**
   * This is the subclass hook for resolve after the 
   * default implementation has mapped the ord to an 
   * instanceof BSpace.
   */
  public abstract OrdTarget resolve(OrdTarget base, OrdQuery query, BSpace space);
}
