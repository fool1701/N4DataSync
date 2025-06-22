/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.box;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 * A BOX Type Ext is a Type used to extend the BOX Client's Type System.
 * 
 * 
 * @author    gjohnson
 * @creation  27 Jan 2011
 * @version   1
 * @since     Niagara 3.8
 */
@NiagaraType
public interface BIBoxTypeExt
    extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.box.BIBoxTypeExt(2979906276)1.0$ @*/
/* Generated Thu Nov 18 16:22:08 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBoxTypeExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return the client environment for the Box Type Extension.
   * 
   * @return client environment name
   */
  BBoxClientEnv getClientEnv();
  
  /**
   * Encode the Type Extension to JSON.
   */
  Object encodeToJson(Context cx) throws Exception;
}
