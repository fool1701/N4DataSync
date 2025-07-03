/*
 * Copyright 2000-2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * RemoteAlarmRecipient iis a marker interface for AlarmRecipients that are remote
 * to the station that generates the alarm. <br>
 * Methods must be implemented as actions.
 *
 * @author    Blake M Puhak       
 * @creation  23 Aug 02
 * @version   $Revision: 3$ $Date: 3/30/05 11:35:59 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIRemoteAlarmRecipient
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BIRemoteAlarmRecipient(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIRemoteAlarmRecipient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void routeAlarm(BAlarmRecord alarm)
    throws Exception;
}
