/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.alarm.ext.BNotifyType;
import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetNotifyType represents the Bacnet Notify Type
 * enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision: 5$ $Date: 11/28/01 6:14:21 AM$
 * @creation 07 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("alarm"),
    @Range("event"),
    @Range("ackNotification")
  }
)
public final class BBacnetNotifyType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetNotifyType(3454635984)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for alarm. */
  public static final int ALARM = 0;
  /** Ordinal value for event. */
  public static final int EVENT = 1;
  /** Ordinal value for ackNotification. */
  public static final int ACK_NOTIFICATION = 2;

  /** BBacnetNotifyType constant for alarm. */
  public static final BBacnetNotifyType alarm = new BBacnetNotifyType(ALARM);
  /** BBacnetNotifyType constant for event. */
  public static final BBacnetNotifyType event = new BBacnetNotifyType(EVENT);
  /** BBacnetNotifyType constant for ackNotification. */
  public static final BBacnetNotifyType ackNotification = new BBacnetNotifyType(ACK_NOTIFICATION);

  /** Factory method with ordinal. */
  public static BBacnetNotifyType make(int ordinal)
  {
    return (BBacnetNotifyType)alarm.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetNotifyType make(String tag)
  {
    return (BBacnetNotifyType)alarm.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetNotifyType(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetNotifyType DEFAULT = alarm;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNotifyType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    return DEFAULT.getRange().getTag(id);
  }

  /**
   * Generate the correct ordinal mapping from BNotifyType
   * to BBacnetNotifyType.
   * <p>
   * BNotifyType.alert is mapped to BBacnetNotifyType.event.
   *
   * @param notifyType the BNotifyType.
   * @return the ordinal representing the corresponding BBacnetNotifyType.
   */
  public static BBacnetNotifyType make(BNotifyType notifyType)
  {
    return new BBacnetNotifyType(notifyType.getOrdinal());
  }

  /**
   * Generate the correct ordinal mapping from BNotifyType
   * to BBacnetNotifyType.
   * <p>
   * BNotifyType.alert is mapped to BBacnetNotifyType.event.
   *
   * @param notifyType the BNotifyType.
   * @return the ordinal representing the corresponding BBacnetNotifyType.
   */
  public static int fromBNotifyType(BNotifyType notifyType)
  {
    return notifyType.getOrdinal();
  }

  /**
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }
}
