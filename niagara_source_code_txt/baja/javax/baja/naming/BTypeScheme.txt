/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeNotFoundException;

/**
 * An ORD scheme that resolves a type spec (moduleName:typeName)
 * to a {@link javax.baja.util.BTypeSpec}.
 *
 * @author Gareth Johnson on 17/09/2015.
 * @since Niagara 4.1
 */
@NiagaraType(
  ordScheme = "type"
)
@NiagaraSingleton
public final class BTypeScheme extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BTypeScheme(3267618176)1.0$ @*/
/* Generated Fri Jun 03 09:05:16 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BTypeScheme INSTANCE = new BTypeScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BTypeScheme()
  {
    super("type");
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    String typeSpec = query.getBody();
    try
    {
      return new OrdTarget(base, Sys.getRegistry().getType(typeSpec).getTypeSpec());
    }
    catch(TypeNotFoundException e)
    {
      throw new UnresolvedException("Could not resolve type: " + typeSpec, e);
    }
  }
}
