/*
 * Copyright 2021, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.db;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Enum to specify the Workbench notification behavior when the
 * {@link BArchiveHistoryProvider#maxArchiveResultsPerQuery} limit is exceeded
 * for a history query made from a remote Workbench user.
 *
 * @author Scott Hoye on 02/11/2021
 * @since Niagara 4.11
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "notifyOncePerQueryRangePerSession", ordinal = 0),
    @Range(value = "neverNotify", ordinal = 1),
    @Range(value = "alwaysNotify", ordinal = 2)
  }
)
public final class BArchiveLimitNotificationBehavior
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.db.BArchiveLimitNotificationBehavior(2872795293)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for notifyOncePerQueryRangePerSession. */
  public static final int NOTIFY_ONCE_PER_QUERY_RANGE_PER_SESSION = 0;
  /** Ordinal value for neverNotify. */
  public static final int NEVER_NOTIFY = 1;
  /** Ordinal value for alwaysNotify. */
  public static final int ALWAYS_NOTIFY = 2;

  /** BArchiveLimitNotificationBehavior constant for notifyOncePerQueryRangePerSession. */
  public static final BArchiveLimitNotificationBehavior notifyOncePerQueryRangePerSession = new BArchiveLimitNotificationBehavior(NOTIFY_ONCE_PER_QUERY_RANGE_PER_SESSION);
  /** BArchiveLimitNotificationBehavior constant for neverNotify. */
  public static final BArchiveLimitNotificationBehavior neverNotify = new BArchiveLimitNotificationBehavior(NEVER_NOTIFY);
  /** BArchiveLimitNotificationBehavior constant for alwaysNotify. */
  public static final BArchiveLimitNotificationBehavior alwaysNotify = new BArchiveLimitNotificationBehavior(ALWAYS_NOTIFY);

  /** Factory method with ordinal. */
  public static BArchiveLimitNotificationBehavior make(int ordinal)
  {
    return (BArchiveLimitNotificationBehavior)notifyOncePerQueryRangePerSession.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BArchiveLimitNotificationBehavior make(String tag)
  {
    return (BArchiveLimitNotificationBehavior)notifyOncePerQueryRangePerSession.getRange().get(tag);
  }

  /** Private constructor. */
  private BArchiveLimitNotificationBehavior(int ordinal)
  {
    super(ordinal);
  }

  public static final BArchiveLimitNotificationBehavior DEFAULT = notifyOncePerQueryRangePerSession;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveLimitNotificationBehavior.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
