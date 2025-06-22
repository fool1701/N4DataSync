/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Implemented by Servlet Views or bajaux Widgets/Editors that
 * will render full screen. For instance, a Workbench View.
 * 
 * When a Servlet View, bajaux Widget or Editor implements this interface,
 * it will render as a full screen view in Workbench and Hx.
 * 
 * @author    Gareth Johnson on 31 Dec 2013
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIFormFactorMax
    extends BIFormFactor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIFormFactorMax(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFormFactorMax.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
