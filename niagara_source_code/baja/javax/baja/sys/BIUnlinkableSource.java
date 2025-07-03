/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A marker interface that can be used by Niagara Types that can never be the source of a link
 * in Niagara.  Enforcement of unlinkable source types occurs in
 * {@link BComponent#checkLink(BComponent, Slot, Slot, Context)}
 * and
 * {@link BLink#activate()}
 *
 * @author   Scott Hoye
 * @creation 19 Apr 2017
 * @since    Niagara 4.4
 */
@NiagaraType
public interface BIUnlinkableSource
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIUnlinkableSource(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIUnlinkableSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
