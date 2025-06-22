/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bajascript;

import java.util.Collection;

import javax.baja.box.BIBoxTypeExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.js.BIWebResource;
import javax.baja.web.js.JsInfo;

import com.tridium.json.JSONObject;


/**
 * A BajaScript Type Extension is used to extend BajaScript's own Type system.
 *
 * @author    gjohnson on 27 Jan 2011
 * @version   1
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIBajaScriptTypeExt
    extends BIBoxTypeExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bajascript.BIBajaScriptTypeExt(2979906276)1.0$ @*/
/* Generated Tue Apr 19 14:21:19 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIBajaScriptTypeExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return the JavaScript information associated with this Type Extension.
   * 
   * @param cx the security context used when requesting the type extension's
   * JavaScript
   * @return info on the type extension's JavaScript implementation, or null
   * if no type extension should be loaded
   */
  JsInfo getTypeExtJs(Context cx);

  /**
   * <p>Encode and return this BajaScript Type extension to JSON. The generated
   * JSON will/must adhere to the following schema:</p>
   *
   * <pre>
   * {
   *   "title": "BIBajaScriptTypeExt JSON encoding",
   *   "type": "object",
   *   "properties": {
   *     "js": {
   *       "type": "string",
   *       "description": "RequireJS ID of the type extension implementation"
   *     },
   *     "dg": {
   *       "type": "array",
   *       "items": { "type": "string[]" },
   *       "description": "Array of arrays of RequireJS IDs of built/minified files that should be loaded before requiring the primary module ID"
   *     }
   *   }
   * }
   * </pre>
   *
   * @since Niagara 4.2
   * @param cx the security context used when requesting the type extension's
   * JavaScript
   * @return JSON information about the type extension
   * @see JsInfo#resolveDependencies() which can provide the array of dependencies
   * @see BIWebResource#resolve(Collection) for information about generating the array of dependencies by hand
   */
  @Override
  default JSONObject encodeToJson(Context cx)
  {
    JsInfo typeExtJs = getTypeExtJs(cx);
    JSONObject obj = new JSONObject();

    obj.put("js", typeExtJs.getJsId());
    BIWebResource.DependencyGraph graph = typeExtJs.resolveDependencies();
    if (!graph.isEmpty())
    {
      obj.put("dg", graph.toJSON());
    }

    return obj;
  }
}
