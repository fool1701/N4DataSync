/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.mobile;

import javax.baja.agent.BIAgent;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Mobile Views may expose some public API that allows the View to be extended. 
 * If a Mobile View supports this, the View may be extended by extending this class
 * and registering it as an Agent on the target Mobile View.
 *
 * @author		gjohnson
 * @creation 	25 Oct 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public abstract class BMobileWebViewExt 
    extends BSingleton
    implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BMobileWebViewExt(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMobileWebViewExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return a list of ORDs that map to JavaScript resources to include in a given Mobile View. 
   * <p>
   * Please note that not all Mobile Views may support this and only 
   * CSS/APIs exposed as public should be used to extend a Mobile View.
   * 
   * @param profile
   * @param view
   * @param cx
   * @return an array of ORDS that will map to JavaScript resources to be included in a Mobile View.
   */
  public abstract BOrd[] getJavaScriptResources(BMobileWebProfile profile, BIMobileWebView view, Context cx);
  
  /**
   * Return a list of ORDs that map to CSS resources to include in a given Mobile View. 
   * <p>
   * Please note that not all Mobile Views may support this and only 
   * CSS/APIs exposed as public should be used to extend a Mobile View.
   * 
   * @param profile
   * @param view
   * @param cx
   * @return an array of ORDS that will map to CSS resources to be included in a Mobile View.
   */
  public abstract BOrd[] getCssResources(BMobileWebProfile profile, BIMobileWebView view, Context cx);
}
