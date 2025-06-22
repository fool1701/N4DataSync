/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BWebLogFileFormat
 *
 * @author    Brian Frank
 * @creation  28 Jul 03
 * @version   $Revision: 5$ $Date: 8/16/07 4:07:12 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("commonLogFormat"),
    @Range("extendedCommonLogFormat"),
    @Range("extendedLogFormat")
  }
)
public final class BWebLogFileFormat
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebLogFileFormat(1621688802)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for commonLogFormat. */
  public static final int COMMON_LOG_FORMAT = 0;
  /** Ordinal value for extendedCommonLogFormat. */
  public static final int EXTENDED_COMMON_LOG_FORMAT = 1;
  /** Ordinal value for extendedLogFormat. */
  public static final int EXTENDED_LOG_FORMAT = 2;

  /** BWebLogFileFormat constant for commonLogFormat. */
  public static final BWebLogFileFormat commonLogFormat = new BWebLogFileFormat(COMMON_LOG_FORMAT);
  /** BWebLogFileFormat constant for extendedCommonLogFormat. */
  public static final BWebLogFileFormat extendedCommonLogFormat = new BWebLogFileFormat(EXTENDED_COMMON_LOG_FORMAT);
  /** BWebLogFileFormat constant for extendedLogFormat. */
  public static final BWebLogFileFormat extendedLogFormat = new BWebLogFileFormat(EXTENDED_LOG_FORMAT);

  /** Factory method with ordinal. */
  public static BWebLogFileFormat make(int ordinal)
  {
    return (BWebLogFileFormat)commonLogFormat.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BWebLogFileFormat make(String tag)
  {
    return (BWebLogFileFormat)commonLogFormat.getRange().get(tag);
  }

  /** Private constructor. */
  private BWebLogFileFormat(int ordinal)
  {
    super(ordinal);
  }

  public static final BWebLogFileFormat DEFAULT = commonLogFormat;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebLogFileFormat.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
