/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Super interface for all Form Factors. This interface shouldn't
 * be implemented directly. Instead implement {@link BIFormFactorMini},
 * {@link BIFormFactorCompact} or {@link BIFormFactorMax}.
 * 
 * @author    Gareth Johnson on 31 Dec 2013
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIFormFactor
    extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIFormFactor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFormFactor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
