/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BServiceScheme is used to lookup a service by 
 * type "service:baja:UserService".
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 3$ $Date: 2/2/09 5:58:13 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "service"
)
@NiagaraSingleton
public class BServiceScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BServiceScheme(4174286692)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BServiceScheme INSTANCE = new BServiceScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BServiceScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BServiceScheme()
  {
    super("service");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  @Override
  public OrdQuery parse(String queryBody)
  {
    if (queryBody.length() < 3 || queryBody.indexOf(':') <= 0)
      throw new SyntaxException("Not a typespec: \"" + queryBody + "\"");
    return new ServiceQuery(queryBody);
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    BObject service = null;
    
    try
    {
      // resolve body to type
      BTypeSpec typeSpec = BTypeSpec.make(query.getBody());
      Type type = typeSpec.getResolvedType();
  
      // map base to session
      BISession session = BOrd.toSession(base.get());
      if (session == null)
        throw new InvalidOrdBaseException(""+base);
      if (!(session instanceof ServiceSession))
        throw new InvalidOrdBaseException("Session does not support services");
      
      // lookup service
      service = ((ServiceSession)session).getService(type);
    }
    catch(ModuleNotFoundException ex) {}
    catch(TypeNotFoundException ex) {}
    catch(ServiceNotFoundException ex) {}
    
    if (service == null)
    {
      throw new UnresolvedException("Service not found: " + query.getBody());
    }
    
    return new OrdTarget(base, service);
  }

////////////////////////////////////////////////////////////////
// ServiceSession
////////////////////////////////////////////////////////////////  
  
  /**
   * This interface is implemented by BISessions which 
   * can lookup a service by type.
   */ 
  public interface ServiceSession
  {
    public BComponent getService(Type type);
  }

////////////////////////////////////////////////////////////////
// ServiceQuery
////////////////////////////////////////////////////////////////  

  static class ServiceQuery implements OrdQuery
  {
    ServiceQuery(String body) { this.body = body; }
    
    @Override
    public boolean isHost() { return false; }

    @Override
    public boolean isSession() { return false; }
  
    @Override
    public void normalize(OrdQueryList list, int index) { list.shiftToSession(index); }

    @Override
    public String getScheme() { return "service"; }
    
    @Override
    public String getBody() { return body; }
    
    public String toString() { return "service:" + body; }
  
    String body;
  }
}
