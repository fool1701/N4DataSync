/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BNotifyType is an BEnum that represents valid Baja notification types
 *
 * @author    Dan Giorgis
 * @creation   9 Nov 00
 * @version   $Revision: 12$ $Date: 6/3/04 1:10:41 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("alarm"),
    @Range("alert"),
    @Range("ackNotification")
  }
)
public final class BNotifyType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BNotifyType(1968090769)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for alarm. */
  public static final int ALARM = 0;
  /** Ordinal value for alert. */
  public static final int ALERT = 1;
  /** Ordinal value for ackNotification. */
  public static final int ACK_NOTIFICATION = 2;

  /** BNotifyType constant for alarm. */
  public static final BNotifyType alarm = new BNotifyType(ALARM);
  /** BNotifyType constant for alert. */
  public static final BNotifyType alert = new BNotifyType(ALERT);
  /** BNotifyType constant for ackNotification. */
  public static final BNotifyType ackNotification = new BNotifyType(ACK_NOTIFICATION);

  /** Factory method with ordinal. */
  public static BNotifyType make(int ordinal)
  {
    return (BNotifyType)alarm.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNotifyType make(String tag)
  {
    return (BNotifyType)alarm.getRange().get(tag);
  }

  /** Private constructor. */
  private BNotifyType(int ordinal)
  {
    super(ordinal);
  }

  public static final BNotifyType DEFAULT = alarm;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNotifyType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

//  public static final BNotifyType DEFAULT = alarm;

      
}
