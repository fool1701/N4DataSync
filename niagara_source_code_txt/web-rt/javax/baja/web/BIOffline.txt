/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A marker interface for bajaux Widgets that can be loaded offline.
 * <p>
 * In offline mode, a widget loads all of its resources locally in the Workbench VM.
 * There is no Station Web Server connection. If offline mode, a web application can
 * never make any AJAX requests. BajaScript RPC calls must be used instead.
 * </p>
 * <p>
 * This interface should be used in conjunction with {@link javax.baja.web.js.BIJavaScript}
 * and {@link BIFormFactor} interfaces.
 * </p>
 *
 * @see javax.baja.web.js.BIJavaScript
 * @see BIFormFactor
 *
 * @author Gareth Johnson on 05/10/2015.
 * @since Niagara 4.1
 */
@NiagaraType
public interface BIOffline extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIOffline(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIOffline.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
