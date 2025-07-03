/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.js;

import java.io.IOException;
import java.io.Writer;

import javax.baja.file.BIFile;
import javax.baja.file.BajaFileUtil;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Used to configure <a href="http://requirejs.org/docs/api.html#config">RequireJS</a>.
 *
 * @author  Gareth Johnson on 01/12/2015.
 * @since Niagara 4.2
 */
@NiagaraType
public interface BIRequireJsConfig
  extends BIJavaScript
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.js.BIRequireJsConfig(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRequireJsConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Write out the JavaScript to configure RequireJS.
   * <p>
   * By default, this will call a exported JavaScript function with
   * the require object, the module prefix and whether the WebDev is enabled
   * for JavaScript.
   * </p>
   * <pre><code>
   * function config(require, modulePrefix, isWebDevJs) {
   *   require.paths.coolJs = modulePrefix + "myModule/rc/myJs" + (isWebDevJs ? "" : ".min");
   * }
   * </code></pre>
   * <p>
   * It's not typical to override this method. Instead supply a JavaScript file that takes the
   * aforementioned function parameters.
   * </p>
   *
   * @param out Used to write out the JavaScript.
   * @param modulePrefix Used as a prefix to the module resource being referenced.
   * @param isWebDevJs true if the 'js' WebDev is currently enabled.
   * @param cx The current Context the originating call.
   * @throws IOException
   */
  default void write(Writer out, String modulePrefix, boolean isWebDevJs, Context cx)
    throws IOException
  {
    JsInfo jsInfo = getJsInfo(cx);
    String js = BajaFileUtil.readString((BIFile)jsInfo.getJs().get());

    out.write(String.format("(function(){" +
      "return (%s);" +
    "})()(require,'%s',%s);", js, modulePrefix, isWebDevJs));
  }
}
