/*
 * Copyright 2013, Tridium Inc, All Rights Rervered.
 */
package javax.baja.web.js;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 * An interface for JavaScript resources.
 * <p>
 * A class that implements this interface provides some JavaScript
 * resources. The referenced file contains 
 * some modular JavaScript that uses the AMD format.
 * 
 * @see <a href="http://requirejs.org/docs/whyamd.html">RequireJS documentation</a>
 * @see JsInfo
 *
 * @author   Gareth Johnson
 * @creation 12 Jun 2013
 * @since    Niagara 4.0
 */
@NiagaraType
public interface BIJavaScript
    extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.js.BIJavaScript(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIJavaScript.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return the AMD JavaScript entry point.
   * 
   * @param cx
   * @return
   */
  public JsInfo getJsInfo(Context cx);
}
