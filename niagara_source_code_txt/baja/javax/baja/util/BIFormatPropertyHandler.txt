/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIFormatPropertyHandler 
 *
 * Used in BFormat to retrieve Properties from components that handle the Slot names differently
 *
 * @author		gjohnson
 * @creation 	12 Aug 2008
 * @version 	1
 * @since 		Niagara 3.4
 */
@NiagaraType
public interface BIFormatPropertyHandler extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BIFormatPropertyHandler(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFormatPropertyHandler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Property getFormatPropertyByName(String name);
}
