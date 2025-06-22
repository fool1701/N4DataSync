/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This is a marker interface used to indicate that a class implements an 
 * aggregate function.  See the developer docs for details on how
 * to create user-defined aggregate functions.
 *
 * @author    Matthew Giannini
 * @creation  October 8, 2007
 * @version   $Revision: 1$ $Date: 9/22/09 10:53:39 AM EDT$
 * @since     Baja 3.3
 */
@NiagaraType
public interface BIAggregator extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bql.BIAggregator(2979906276)1.0$ @*/
/* Generated Wed Jan 26 10:50:35 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIAggregator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
