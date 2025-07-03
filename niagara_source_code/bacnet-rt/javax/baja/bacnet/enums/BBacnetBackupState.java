/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetBackupState represents the possible states
 * of a BACnet device with respect to backup and restore
 * procedures.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 14 Jun 2006
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("idle"),
    @Range("preparingForBackup"),
    @Range("preparingForRestore"),
    @Range("performingABackup"),
    @Range("performingARestore"),
    @Range("backupFailure"),
    @Range("restoreFailure")
  }
)
public final class BBacnetBackupState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetBackupState(4016132058)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for idle. */
  public static final int IDLE = 0;
  /** Ordinal value for preparingForBackup. */
  public static final int PREPARING_FOR_BACKUP = 1;
  /** Ordinal value for preparingForRestore. */
  public static final int PREPARING_FOR_RESTORE = 2;
  /** Ordinal value for performingABackup. */
  public static final int PERFORMING_ABACKUP = 3;
  /** Ordinal value for performingARestore. */
  public static final int PERFORMING_ARESTORE = 4;
  /** Ordinal value for backupFailure. */
  public static final int BACKUP_FAILURE = 5;
  /** Ordinal value for restoreFailure. */
  public static final int RESTORE_FAILURE = 6;

  /** BBacnetBackupState constant for idle. */
  public static final BBacnetBackupState idle = new BBacnetBackupState(IDLE);
  /** BBacnetBackupState constant for preparingForBackup. */
  public static final BBacnetBackupState preparingForBackup = new BBacnetBackupState(PREPARING_FOR_BACKUP);
  /** BBacnetBackupState constant for preparingForRestore. */
  public static final BBacnetBackupState preparingForRestore = new BBacnetBackupState(PREPARING_FOR_RESTORE);
  /** BBacnetBackupState constant for performingABackup. */
  public static final BBacnetBackupState performingABackup = new BBacnetBackupState(PERFORMING_ABACKUP);
  /** BBacnetBackupState constant for performingARestore. */
  public static final BBacnetBackupState performingARestore = new BBacnetBackupState(PERFORMING_ARESTORE);
  /** BBacnetBackupState constant for backupFailure. */
  public static final BBacnetBackupState backupFailure = new BBacnetBackupState(BACKUP_FAILURE);
  /** BBacnetBackupState constant for restoreFailure. */
  public static final BBacnetBackupState restoreFailure = new BBacnetBackupState(RESTORE_FAILURE);

  /** Factory method with ordinal. */
  public static BBacnetBackupState make(int ordinal)
  {
    return (BBacnetBackupState)idle.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetBackupState make(String tag)
  {
    return (BBacnetBackupState)idle.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetBackupState(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetBackupState DEFAULT = idle;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBackupState.class);

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
