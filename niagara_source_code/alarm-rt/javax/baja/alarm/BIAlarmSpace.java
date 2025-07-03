/**
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BISpace;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Common interface for all AlarmSpace implementations.
 *
 * @author Blake Puhak
 * @creation 11 June 2014
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIAlarmSpace
  extends BISpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BIAlarmSpace(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIAlarmSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Open a connection to the alarm space.  All interactions with an AlarmDb
   * are managed through a AlarmDbConnection.
   *
   * @param context context of the connection
   * @return Returns a connection to the alarm database.
   */
  AlarmSpaceConnection getConnection(Context context);
}
