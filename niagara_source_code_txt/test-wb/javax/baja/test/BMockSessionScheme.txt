/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdQueryList;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMockSessionScheme is the ord scheme represented as "mocksession:sessionName".
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */

@NiagaraType(
  ordScheme = "mocksession"
)
@NiagaraSingleton
public class BMockSessionScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BMockSessionScheme(842758362)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BMockSessionScheme INSTANCE = new BMockSessionScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockSessionScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.  Use {@link #INSTANCE}
   */
  private BMockSessionScheme()
  {
    super("mocksession");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Parse the query and resolve it using the specified base.
   *
   * @throws javax.baja.naming.SyntaxException     if the query cannot be parsed
   *                                               due to invalid syntax
   * @throws javax.baja.naming.UnresolvedException if the ord cannot be
   *                                               resolved to a BObject
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    MockSessionQuery sessionQuery = (MockSessionQuery)query;
    return new OrdTarget(base, BMockHost.INSTANCE.makeSession(sessionQuery.getSessionName()));
  }

////////////////////////////////////////////////////////////////
// MockSessionQuery
////////////////////////////////////////////////////////////////

  public static class MockSessionQuery
    implements OrdQuery
  {
    public MockSessionQuery(String sessionName)
    {
      this.sessionName = sessionName;
    }

    public String getSessionName()
    {
      return sessionName;
    }

    /**
     * Get the scheme id of the query.
     */
    @Override
    public String getScheme()
    {
      return "mocksession";
    }

    /**
     * Get the ASCII body of the query.
     */
    @Override
    public String getBody()
    {
      return sessionName;
    }

    /**
     * Return if this OrdQuery is an host.  Host queries are
     * absolute and resolve to a BHost.  Since host queries are
     * absolute they trump any queries to their left during
     * normalization.
     */
    @Override
    public boolean isHost()
    {
      return false;
    }

    /**
     * Return if this OrdQuery is a session.  Sessions queries
     * are absolute within a host, and resolve to a BISession.
     */
    @Override
    public boolean isSession()
    {
      return true;
    }

    /**
     * This method is called during BOrd.normalize() to give
     * each query the ability to normalize itself.  The
     * index specifies the location of this query in the
     * parsed queries list.  This method allows OrdQueries to
     * merge or truncate relative ords.
     */
    @Override
    public void normalize(OrdQueryList list, int index)
    {
      list.shiftToHost(index);
    }

    private final String sessionName;
  }
}
