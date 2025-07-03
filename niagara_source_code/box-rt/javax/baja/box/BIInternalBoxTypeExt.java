/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.box;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * An internal BOX Type Extension is used to extend a BOX Client's Type System.
 * If a Type is marked as internal then it is representing itself as a Type.
 *
 * @author   Gareth Johnson
 * @creation 20 Sep 2012
 * @since    Niagara 4.0
 */
@NiagaraType
public interface BIInternalBoxTypeExt
    extends BIBoxTypeExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.box.BIInternalBoxTypeExt(2979906276)1.0$ @*/
/* Generated Thu Nov 18 16:22:08 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIInternalBoxTypeExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
