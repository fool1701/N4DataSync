/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIpScheme is used to identify BIpHosts as "{@code ip:<hostname>}".
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:23:00 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "ip"
)
@NiagaraSingleton
public class BIpScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BIpScheme(805752493)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BIpScheme INSTANCE = new BIpScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIpScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BIpScheme()
  {
    super("ip");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  @Override
  public OrdQuery parse(String queryBody)
  {
    return new IpQuery(queryBody);
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    String hostname = query.getBody();
    BHost host = BHost.getHost(hostname);
    if (host == null)
    {
      host = new BIpHost(hostname);
      BHost.mount(host);
    }
    return new OrdTarget(base, host);
  }

////////////////////////////////////////////////////////////////
// IpQuery
////////////////////////////////////////////////////////////////

  static class IpQuery implements OrdQuery
  {
  
    IpQuery(String hostname) { this.hostname = hostname; }
  
    @Override
    public boolean isHost() { return true; }

    @Override
    public boolean isSession() { return false; }
  
    @Override
    public void normalize(OrdQueryList list, int index) { list.trim(index); }

    @Override
    public String getScheme() { return "ip"; }
    
    @Override
    public String getBody() { return hostname; }
    
    public String toString() { return "ip:" + hostname; }
  
    final String hostname;
  }
}
