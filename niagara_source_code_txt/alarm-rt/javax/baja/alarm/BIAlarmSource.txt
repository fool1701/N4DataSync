/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BIAlarmSource interface must be implemented by all
 * BObjects capable of generating alarms 
 * <p>
 * This interface must be implemented by declaring the
 * following actions on the BIAlarmSource:
 * <pre>
 *    ackAlarm(ackRequest: BAlarmRecord): boolean     
 *      -- Acknowledge the alarm matching this ack request
 *      flags = readonly, hidden
 * </pre>
 *
 * @author    Dan Giorgis
 * @creation  19 Feb 01
 * @version   $Revision: 3$ $Date: 6/16/04 11:10:56 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIAlarmSource
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BIAlarmSource(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIAlarmSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Acknowledge the alarm matching the given acknowledge 
   * request.
   * Classes implementing AlarmSource must implement the ackAlarm 
   * method as an action.
   *
   * @param ackRequest The acknowledgement request alarm record.
   * @return true if alarm acked (clear ack bit in status), false if stale ack
   */
  public BBoolean ackAlarm(BAlarmRecord ackRequest);   
}
