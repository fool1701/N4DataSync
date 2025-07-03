/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.Optional;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.authn.AuthenticationClient;

/**
 * BOrdSchemes provide management of a registered ord scheme ID.
 * A Scheme ID is universally unique and mapped to a BOrdScheme
 * type via the registry.  Scheme IDs are case insensitive.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 4$ $Date: 2/24/05 9:29:40 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BOrdScheme
  extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BOrdScheme(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Lookup a BOrdScheme by scheme id, or throw 
   * UnknownSchemeException if not found.
   */
  public static BOrdScheme lookup(String schemeId)
  {
    return (BOrdScheme)Sys.getRegistry().getOrdScheme(schemeId).getInstance();
  }

  /**
   * Find a BOrdScheme by scheme id.
   *
   * @param schemeId The id of the ord scheme.
   * @return Returns the ord scheme or null if the ord scheme cannot be found.
   */
  public static Optional<BOrdScheme> find(String schemeId)
  {
    TypeInfo schemeType = Sys.getRegistry().findOrdScheme(schemeId);
    if (schemeType == null)
      return Optional.empty();
    else
      return Optional.of((BOrdScheme)schemeType.getInstance());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a BOrdScheme with the specified ID.
   */
  protected BOrdScheme(String id)
  {
    this.id = TextUtil.toLowerCase(id);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the scheme identifier for this instance.  The ID 
   * is case insensitive, but always returned as lower case.
   */
  public final String getId()
  {
    return id;
  }
  
  /**
   * This method gives scheme the chance to return a custom 
   * subclass of OrdQuery with a scheme specific API.  The
   * default implementation returns an instance of BasicQuery.
   */
  public OrdQuery parse(String queryBody)
  {
    return new BasicQuery(id, queryBody);
  }

  /**
   * Parse the query and resolve it using the specified base.
   *
   * @throws SyntaxException if the query cannot be parsed 
   *   due to invalid syntax
   * @throws UnresolvedException if the ord cannot be
   *   resolved to a BObject
   */
  public abstract OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException;

  public OrdTarget resolve(OrdTarget base, OrdQuery query, AuthenticationClient client)
    throws SyntaxException, UnresolvedException
  {
    return resolve(base, query);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String id;    
  
}
