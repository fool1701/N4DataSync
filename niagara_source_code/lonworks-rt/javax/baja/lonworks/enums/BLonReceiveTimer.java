/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonReceiveTimer provides enumeration for the values used
 * to set the receive timer stored in a Lonworks device address
 * table. See Neuron Chip Data Book A.3.11.
 * <p>
 * @author    Robert Adams
 * @creation  19 Feb 02
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("milliSec128"),
    @Range("milliSec192"),
    @Range("milliSec256"),
    @Range("milliSec384"),
    @Range("milliSec512"),
    @Range("milliSec768"),
    @Range("milliSec1024"),
    @Range("milliSec1536"),
    @Range("milliSec2048"),
    @Range("milliSec3072"),
    @Range("milliSec4096"),
    @Range("milliSec6144"),
    @Range("milliSec8192"),
    @Range("milliSec12288"),
    @Range("milliSec16384"),
    @Range("milliSec24576")
  }
)
public final class BLonReceiveTimer
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonReceiveTimer(2142974568)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for milliSec128. */
  public static final int MILLI_SEC_128 = 0;
  /** Ordinal value for milliSec192. */
  public static final int MILLI_SEC_192 = 1;
  /** Ordinal value for milliSec256. */
  public static final int MILLI_SEC_256 = 2;
  /** Ordinal value for milliSec384. */
  public static final int MILLI_SEC_384 = 3;
  /** Ordinal value for milliSec512. */
  public static final int MILLI_SEC_512 = 4;
  /** Ordinal value for milliSec768. */
  public static final int MILLI_SEC_768 = 5;
  /** Ordinal value for milliSec1024. */
  public static final int MILLI_SEC_1024 = 6;
  /** Ordinal value for milliSec1536. */
  public static final int MILLI_SEC_1536 = 7;
  /** Ordinal value for milliSec2048. */
  public static final int MILLI_SEC_2048 = 8;
  /** Ordinal value for milliSec3072. */
  public static final int MILLI_SEC_3072 = 9;
  /** Ordinal value for milliSec4096. */
  public static final int MILLI_SEC_4096 = 10;
  /** Ordinal value for milliSec6144. */
  public static final int MILLI_SEC_6144 = 11;
  /** Ordinal value for milliSec8192. */
  public static final int MILLI_SEC_8192 = 12;
  /** Ordinal value for milliSec12288. */
  public static final int MILLI_SEC_12288 = 13;
  /** Ordinal value for milliSec16384. */
  public static final int MILLI_SEC_16384 = 14;
  /** Ordinal value for milliSec24576. */
  public static final int MILLI_SEC_24576 = 15;

  /** BLonReceiveTimer constant for milliSec128. */
  public static final BLonReceiveTimer milliSec128 = new BLonReceiveTimer(MILLI_SEC_128);
  /** BLonReceiveTimer constant for milliSec192. */
  public static final BLonReceiveTimer milliSec192 = new BLonReceiveTimer(MILLI_SEC_192);
  /** BLonReceiveTimer constant for milliSec256. */
  public static final BLonReceiveTimer milliSec256 = new BLonReceiveTimer(MILLI_SEC_256);
  /** BLonReceiveTimer constant for milliSec384. */
  public static final BLonReceiveTimer milliSec384 = new BLonReceiveTimer(MILLI_SEC_384);
  /** BLonReceiveTimer constant for milliSec512. */
  public static final BLonReceiveTimer milliSec512 = new BLonReceiveTimer(MILLI_SEC_512);
  /** BLonReceiveTimer constant for milliSec768. */
  public static final BLonReceiveTimer milliSec768 = new BLonReceiveTimer(MILLI_SEC_768);
  /** BLonReceiveTimer constant for milliSec1024. */
  public static final BLonReceiveTimer milliSec1024 = new BLonReceiveTimer(MILLI_SEC_1024);
  /** BLonReceiveTimer constant for milliSec1536. */
  public static final BLonReceiveTimer milliSec1536 = new BLonReceiveTimer(MILLI_SEC_1536);
  /** BLonReceiveTimer constant for milliSec2048. */
  public static final BLonReceiveTimer milliSec2048 = new BLonReceiveTimer(MILLI_SEC_2048);
  /** BLonReceiveTimer constant for milliSec3072. */
  public static final BLonReceiveTimer milliSec3072 = new BLonReceiveTimer(MILLI_SEC_3072);
  /** BLonReceiveTimer constant for milliSec4096. */
  public static final BLonReceiveTimer milliSec4096 = new BLonReceiveTimer(MILLI_SEC_4096);
  /** BLonReceiveTimer constant for milliSec6144. */
  public static final BLonReceiveTimer milliSec6144 = new BLonReceiveTimer(MILLI_SEC_6144);
  /** BLonReceiveTimer constant for milliSec8192. */
  public static final BLonReceiveTimer milliSec8192 = new BLonReceiveTimer(MILLI_SEC_8192);
  /** BLonReceiveTimer constant for milliSec12288. */
  public static final BLonReceiveTimer milliSec12288 = new BLonReceiveTimer(MILLI_SEC_12288);
  /** BLonReceiveTimer constant for milliSec16384. */
  public static final BLonReceiveTimer milliSec16384 = new BLonReceiveTimer(MILLI_SEC_16384);
  /** BLonReceiveTimer constant for milliSec24576. */
  public static final BLonReceiveTimer milliSec24576 = new BLonReceiveTimer(MILLI_SEC_24576);

  /** Factory method with ordinal. */
  public static BLonReceiveTimer make(int ordinal)
  {
    return (BLonReceiveTimer)milliSec128.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonReceiveTimer make(String tag)
  {
    return (BLonReceiveTimer)milliSec128.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonReceiveTimer(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonReceiveTimer DEFAULT = milliSec128;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonReceiveTimer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
