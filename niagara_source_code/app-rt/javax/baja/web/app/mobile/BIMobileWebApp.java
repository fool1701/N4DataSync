/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.app.mobile;

import javax.baja.app.BApp;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.app.BIWebApp;

/**
 * Interface for a Mobile Web App
 * <p>
 * This interface should only be implemented on classes that extend BApp
 * 
 * @see BApp
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public interface BIMobileWebApp
    extends BIWebApp
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.app.mobile.BIMobileWebApp(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:34:19 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIMobileWebApp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
