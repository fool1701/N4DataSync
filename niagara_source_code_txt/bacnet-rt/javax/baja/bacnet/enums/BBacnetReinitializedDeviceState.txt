/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetReinitializedDeviceState represents the state of a device after
 * receiving a ReinitializeDevice-Request.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 23 Jul 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("coldStart"),
    @Range("warmStart"),
    @Range("startBackup"),
    @Range("endBackup"),
    @Range("startRestore"),
    @Range("endRestore"),
    @Range("abortRestore")
  }
)
public final class BBacnetReinitializedDeviceState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetReinitializedDeviceState(561458399)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for coldStart. */
  public static final int COLD_START = 0;
  /** Ordinal value for warmStart. */
  public static final int WARM_START = 1;
  /** Ordinal value for startBackup. */
  public static final int START_BACKUP = 2;
  /** Ordinal value for endBackup. */
  public static final int END_BACKUP = 3;
  /** Ordinal value for startRestore. */
  public static final int START_RESTORE = 4;
  /** Ordinal value for endRestore. */
  public static final int END_RESTORE = 5;
  /** Ordinal value for abortRestore. */
  public static final int ABORT_RESTORE = 6;

  /** BBacnetReinitializedDeviceState constant for coldStart. */
  public static final BBacnetReinitializedDeviceState coldStart = new BBacnetReinitializedDeviceState(COLD_START);
  /** BBacnetReinitializedDeviceState constant for warmStart. */
  public static final BBacnetReinitializedDeviceState warmStart = new BBacnetReinitializedDeviceState(WARM_START);
  /** BBacnetReinitializedDeviceState constant for startBackup. */
  public static final BBacnetReinitializedDeviceState startBackup = new BBacnetReinitializedDeviceState(START_BACKUP);
  /** BBacnetReinitializedDeviceState constant for endBackup. */
  public static final BBacnetReinitializedDeviceState endBackup = new BBacnetReinitializedDeviceState(END_BACKUP);
  /** BBacnetReinitializedDeviceState constant for startRestore. */
  public static final BBacnetReinitializedDeviceState startRestore = new BBacnetReinitializedDeviceState(START_RESTORE);
  /** BBacnetReinitializedDeviceState constant for endRestore. */
  public static final BBacnetReinitializedDeviceState endRestore = new BBacnetReinitializedDeviceState(END_RESTORE);
  /** BBacnetReinitializedDeviceState constant for abortRestore. */
  public static final BBacnetReinitializedDeviceState abortRestore = new BBacnetReinitializedDeviceState(ABORT_RESTORE);

  /** Factory method with ordinal. */
  public static BBacnetReinitializedDeviceState make(int ordinal)
  {
    return (BBacnetReinitializedDeviceState)coldStart.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetReinitializedDeviceState make(String tag)
  {
    return (BBacnetReinitializedDeviceState)coldStart.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetReinitializedDeviceState(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetReinitializedDeviceState DEFAULT = coldStart;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetReinitializedDeviceState.class);

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
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }
}
