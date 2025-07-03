/*
 * Copyright 2016, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIValidator;

/**
 * @author Peter Kronenberg
 * @creation 11/3/2016
 * @since 4.3
 *
 * Marker interface for validators of BScheduless.
 * A class that implements this interface can be added as a dynamic property to a Schedule.
 * It will be called upon saving and determine if the Schedule meets locally-determined criteria
 */
@NiagaraType
public interface BIScheduleValidator extends BIValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BIScheduleValidator(2979906276)1.0$ @*/
/* Generated Sat Jan 29 19:39:56 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIScheduleValidator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
