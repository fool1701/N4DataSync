/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BIRemoteAlarmSource is a marker interface that is implemented by BObjects 
 * that handle alarms sent between stations. 
 * <p>
 * ackAlarm should return immediately after sucessfully sending the alarm 
 * to the remote station. BAlarmService.routeAlarm() should then be called 
 * when the ackNotification is received from the remote station.
 *
 * @author    Blake M Puhak
 * @creation  19 Jan 04
 * @version   $Revision: 2$ $Date: 3/30/05 11:35:59 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIRemoteAlarmSource
  extends BIAlarmSource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BIRemoteAlarmSource(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIRemoteAlarmSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
