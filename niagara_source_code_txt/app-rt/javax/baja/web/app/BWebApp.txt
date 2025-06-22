/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.app;

import javax.baja.app.BApp;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.BWebService;

/**
 * Niagara Web App
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public abstract class BWebApp
    extends BApp
    implements BIWebApp
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.app.BWebApp(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:34:19 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebApp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// App
////////////////////////////////////////////////////////////////
  
  public Type[] getRequiredServices()
  {
    return append(super.getRequiredServices(), BWebService.TYPE);
  }
}
