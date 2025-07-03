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
 * The BLonFileRequestEnum class provides enumeration for SNVT_file_req
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 2$ $Date: 9/5/01 12:55:53 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "openToSend", ordinal = 0),
    @Range(value = "openToReceive", ordinal = 1),
    @Range(value = "closeFile", ordinal = 2),
    @Range(value = "closeDeleteFile", ordinal = 3),
    @Range(value = "directoryLookup", ordinal = 4),
    @Range(value = "openToSendRa", ordinal = 5),
    @Range(value = "openToReceiveRa", ordinal = 6),
    @Range(value = "nul", ordinal = -1)
  },
  defaultValue = "nul"
)
public final class BLonFileRequestEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonFileRequestEnum(2317764148)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for openToSend. */
  public static final int OPEN_TO_SEND = 0;
  /** Ordinal value for openToReceive. */
  public static final int OPEN_TO_RECEIVE = 1;
  /** Ordinal value for closeFile. */
  public static final int CLOSE_FILE = 2;
  /** Ordinal value for closeDeleteFile. */
  public static final int CLOSE_DELETE_FILE = 3;
  /** Ordinal value for directoryLookup. */
  public static final int DIRECTORY_LOOKUP = 4;
  /** Ordinal value for openToSendRa. */
  public static final int OPEN_TO_SEND_RA = 5;
  /** Ordinal value for openToReceiveRa. */
  public static final int OPEN_TO_RECEIVE_RA = 6;
  /** Ordinal value for nul. */
  public static final int NUL = -1;

  /** BLonFileRequestEnum constant for openToSend. */
  public static final BLonFileRequestEnum openToSend = new BLonFileRequestEnum(OPEN_TO_SEND);
  /** BLonFileRequestEnum constant for openToReceive. */
  public static final BLonFileRequestEnum openToReceive = new BLonFileRequestEnum(OPEN_TO_RECEIVE);
  /** BLonFileRequestEnum constant for closeFile. */
  public static final BLonFileRequestEnum closeFile = new BLonFileRequestEnum(CLOSE_FILE);
  /** BLonFileRequestEnum constant for closeDeleteFile. */
  public static final BLonFileRequestEnum closeDeleteFile = new BLonFileRequestEnum(CLOSE_DELETE_FILE);
  /** BLonFileRequestEnum constant for directoryLookup. */
  public static final BLonFileRequestEnum directoryLookup = new BLonFileRequestEnum(DIRECTORY_LOOKUP);
  /** BLonFileRequestEnum constant for openToSendRa. */
  public static final BLonFileRequestEnum openToSendRa = new BLonFileRequestEnum(OPEN_TO_SEND_RA);
  /** BLonFileRequestEnum constant for openToReceiveRa. */
  public static final BLonFileRequestEnum openToReceiveRa = new BLonFileRequestEnum(OPEN_TO_RECEIVE_RA);
  /** BLonFileRequestEnum constant for nul. */
  public static final BLonFileRequestEnum nul = new BLonFileRequestEnum(NUL);

  /** Factory method with ordinal. */
  public static BLonFileRequestEnum make(int ordinal)
  {
    return (BLonFileRequestEnum)openToSend.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonFileRequestEnum make(String tag)
  {
    return (BLonFileRequestEnum)openToSend.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonFileRequestEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonFileRequestEnum DEFAULT = nul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFileRequestEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
