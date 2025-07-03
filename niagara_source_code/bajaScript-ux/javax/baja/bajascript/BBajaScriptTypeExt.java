/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bajascript;

import javax.baja.box.BBoxClientEnv;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.box.env.BBajaScriptClientEnv;
import com.tridium.json.JSONObject;

/**
 * <p>A BajaScript Type Extension is used to extend BajaScript's own Type
 * system.</p>
 *
 * <p>BajaScript has its own lazy loading Type system that associates Niagara
 * Types with JavaScript constructor functions. By extending this class and
 * implementing the {@link #getTypeExtJs(Context)} method, a user can inject
 * their own JavaScript constructors into BajaScript's Type system.</p>
 * 
 * @author    gjohnson on 27 Jan 2011
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public abstract class BBajaScriptTypeExt
    extends BSingleton
    implements BIBajaScriptTypeExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bajascript.BBajaScriptTypeExt(2979906276)1.0$ @*/
/* Generated Tue Apr 19 14:21:19 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBajaScriptTypeExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BBajaScriptTypeExt() {}
  
////////////////////////////////////////////////////////////////
// Box Type Extension
////////////////////////////////////////////////////////////////

  @Override
  public final BBoxClientEnv getClientEnv()
  {
    return BBajaScriptClientEnv.INSTANCE;
  }
    
////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * @deprecated
   * @see BIBajaScriptTypeExt#encodeToJson(Context)
   */
  @Deprecated
  public static JSONObject encodeToJson(BIBajaScriptTypeExt typeExt, Context cx)
  {
    return typeExt.encodeToJson(cx);
  }
}
