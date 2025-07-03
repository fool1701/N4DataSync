/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonFileStatusEnum class provides enumeration for SNVT_file_status
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:42 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "fsXferOk", ordinal = 0),
    @Range(value = "fsLookupOk", ordinal = 1),
    @Range(value = "fsOpenFail", ordinal = 2),
    @Range(value = "fsLookupErr", ordinal = 3),
    @Range(value = "fsXferUnderway", ordinal = 4),
    @Range(value = "fsIoErr", ordinal = 5),
    @Range(value = "fsTimeoutErr", ordinal = 6),
    @Range(value = "fsWindowErr", ordinal = 7),
    @Range(value = "fsAuthErr", ordinal = 8),
    @Range(value = "fsAccessUnavail", ordinal = 9),
    @Range(value = "fsSeekInvalid", ordinal = 10),
    @Range(value = "fsSeekWake", ordinal = 11),
    @Range(value = "fsNul", ordinal = -1)
  },
  defaultValue = "fsNul"
)
public final class BLonFileStatusEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonFileStatusEnum(2613536183)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for fsXferOk. */
  public static final int FS_XFER_OK = 0;
  /** Ordinal value for fsLookupOk. */
  public static final int FS_LOOKUP_OK = 1;
  /** Ordinal value for fsOpenFail. */
  public static final int FS_OPEN_FAIL = 2;
  /** Ordinal value for fsLookupErr. */
  public static final int FS_LOOKUP_ERR = 3;
  /** Ordinal value for fsXferUnderway. */
  public static final int FS_XFER_UNDERWAY = 4;
  /** Ordinal value for fsIoErr. */
  public static final int FS_IO_ERR = 5;
  /** Ordinal value for fsTimeoutErr. */
  public static final int FS_TIMEOUT_ERR = 6;
  /** Ordinal value for fsWindowErr. */
  public static final int FS_WINDOW_ERR = 7;
  /** Ordinal value for fsAuthErr. */
  public static final int FS_AUTH_ERR = 8;
  /** Ordinal value for fsAccessUnavail. */
  public static final int FS_ACCESS_UNAVAIL = 9;
  /** Ordinal value for fsSeekInvalid. */
  public static final int FS_SEEK_INVALID = 10;
  /** Ordinal value for fsSeekWake. */
  public static final int FS_SEEK_WAKE = 11;
  /** Ordinal value for fsNul. */
  public static final int FS_NUL = -1;

  /** BLonFileStatusEnum constant for fsXferOk. */
  public static final BLonFileStatusEnum fsXferOk = new BLonFileStatusEnum(FS_XFER_OK);
  /** BLonFileStatusEnum constant for fsLookupOk. */
  public static final BLonFileStatusEnum fsLookupOk = new BLonFileStatusEnum(FS_LOOKUP_OK);
  /** BLonFileStatusEnum constant for fsOpenFail. */
  public static final BLonFileStatusEnum fsOpenFail = new BLonFileStatusEnum(FS_OPEN_FAIL);
  /** BLonFileStatusEnum constant for fsLookupErr. */
  public static final BLonFileStatusEnum fsLookupErr = new BLonFileStatusEnum(FS_LOOKUP_ERR);
  /** BLonFileStatusEnum constant for fsXferUnderway. */
  public static final BLonFileStatusEnum fsXferUnderway = new BLonFileStatusEnum(FS_XFER_UNDERWAY);
  /** BLonFileStatusEnum constant for fsIoErr. */
  public static final BLonFileStatusEnum fsIoErr = new BLonFileStatusEnum(FS_IO_ERR);
  /** BLonFileStatusEnum constant for fsTimeoutErr. */
  public static final BLonFileStatusEnum fsTimeoutErr = new BLonFileStatusEnum(FS_TIMEOUT_ERR);
  /** BLonFileStatusEnum constant for fsWindowErr. */
  public static final BLonFileStatusEnum fsWindowErr = new BLonFileStatusEnum(FS_WINDOW_ERR);
  /** BLonFileStatusEnum constant for fsAuthErr. */
  public static final BLonFileStatusEnum fsAuthErr = new BLonFileStatusEnum(FS_AUTH_ERR);
  /** BLonFileStatusEnum constant for fsAccessUnavail. */
  public static final BLonFileStatusEnum fsAccessUnavail = new BLonFileStatusEnum(FS_ACCESS_UNAVAIL);
  /** BLonFileStatusEnum constant for fsSeekInvalid. */
  public static final BLonFileStatusEnum fsSeekInvalid = new BLonFileStatusEnum(FS_SEEK_INVALID);
  /** BLonFileStatusEnum constant for fsSeekWake. */
  public static final BLonFileStatusEnum fsSeekWake = new BLonFileStatusEnum(FS_SEEK_WAKE);
  /** BLonFileStatusEnum constant for fsNul. */
  public static final BLonFileStatusEnum fsNul = new BLonFileStatusEnum(FS_NUL);

  /** Factory method with ordinal. */
  public static BLonFileStatusEnum make(int ordinal)
  {
    return (BLonFileStatusEnum)fsXferOk.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonFileStatusEnum make(String tag)
  {
    return (BLonFileStatusEnum)fsXferOk.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonFileStatusEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonFileStatusEnum DEFAULT = fsNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFileStatusEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
