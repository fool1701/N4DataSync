/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import javax.baja.naming.BOrd;
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
 * BMockHostScheme is the ord scheme represented as "mockhost:"
 * which always maps to BMockHost.INSTANCE
 *
 * @author Matt Boon
 * @since Niagara 4.o
 */

@NiagaraType(
  ordScheme = "mockhost"
)
@NiagaraSingleton
public class BMockHostScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BMockHostScheme(4040228900)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BMockHostScheme INSTANCE = new BMockHostScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockHostScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  private BMockHostScheme()
  {
    super("mockhost");
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
    return new OrdTarget(base, BMockHost.INSTANCE);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final MockHostQuery QUERY = new MockHostQuery();
  public static final BOrd ORD = BOrd.make("mockhost:");

////////////////////////////////////////////////////////////////
// MockHostQuery
////////////////////////////////////////////////////////////////

  public static class MockHostQuery
    implements OrdQuery
  {
    public MockHostQuery() {}

    /**
     * Get the scheme id of the query.
     */
    @Override
    public String getScheme()
    {
      return "mockhost";
    }

    /**
     * Get the ASCII body of the query.
     */
    @Override
    public String getBody()
    {
      return "";
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
      return true;
    }

    /**
     * Return if this OrdQuery is a session.  Sessions queries
     * are absolute within a host, and resolve to a BISession.
     */
    @Override
    public boolean isSession()
    {
      return false;
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
      list.trim(index);
    }
  }


}
