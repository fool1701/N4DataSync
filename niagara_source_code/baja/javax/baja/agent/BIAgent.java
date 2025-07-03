/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIAgent is the interface implemented by agent types.  An
 * agent is a special BObject type that provides services for
 * other BObject types.  Agents are registered on their target
 * types via the module manifest and queried via the Registry
 * interface.
 *
 * @author    Brian Frank
 * @creation  23 Dec 02
 * @version   $Revision: 1$ $Date: 12/23/02 8:14:11 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIAgent
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.agent.BIAgent(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
