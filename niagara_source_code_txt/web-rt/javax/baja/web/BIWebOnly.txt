/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.js.BIJavaScript;

/**
 * A marker interface for a Web Resource that should not be used in workbench.
 *
 *
 * @author JJ Frankovich
 * @since Niagara 4.9
 */
@NiagaraType
public interface BIWebOnly
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIWebOnly(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIWebOnly.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
