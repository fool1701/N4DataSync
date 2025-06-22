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
 * BLonRepeatTimer provides enumeration for the values used
 * to set the repeat and transmit timers stored in a Lonworks 
 * device address table. See Neuron Chip Data Book A.3.11.
 *
 * @author    Robert Adams
 * @creation  19 Feb 02
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("milliSec16"),
    @Range("milliSec24"),
    @Range("milliSec32"),
    @Range("milliSec48"),
    @Range("milliSec64"),
    @Range("milliSec96"),
    @Range("milliSec128"),
    @Range("milliSec192"),
    @Range("milliSec256"),
    @Range("milliSec384"),
    @Range("milliSec512"),
    @Range("milliSec768"),
    @Range("milliSec1024"),
    @Range("milliSec1536"),
    @Range("milliSec2048"),
    @Range("milliSec3072")
  }
)
public final class BLonRepeatTimer
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonRepeatTimer(4148331571)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for milliSec16. */
  public static final int MILLI_SEC_16 = 0;
  /** Ordinal value for milliSec24. */
  public static final int MILLI_SEC_24 = 1;
  /** Ordinal value for milliSec32. */
  public static final int MILLI_SEC_32 = 2;
  /** Ordinal value for milliSec48. */
  public static final int MILLI_SEC_48 = 3;
  /** Ordinal value for milliSec64. */
  public static final int MILLI_SEC_64 = 4;
  /** Ordinal value for milliSec96. */
  public static final int MILLI_SEC_96 = 5;
  /** Ordinal value for milliSec128. */
  public static final int MILLI_SEC_128 = 6;
  /** Ordinal value for milliSec192. */
  public static final int MILLI_SEC_192 = 7;
  /** Ordinal value for milliSec256. */
  public static final int MILLI_SEC_256 = 8;
  /** Ordinal value for milliSec384. */
  public static final int MILLI_SEC_384 = 9;
  /** Ordinal value for milliSec512. */
  public static final int MILLI_SEC_512 = 10;
  /** Ordinal value for milliSec768. */
  public static final int MILLI_SEC_768 = 11;
  /** Ordinal value for milliSec1024. */
  public static final int MILLI_SEC_1024 = 12;
  /** Ordinal value for milliSec1536. */
  public static final int MILLI_SEC_1536 = 13;
  /** Ordinal value for milliSec2048. */
  public static final int MILLI_SEC_2048 = 14;
  /** Ordinal value for milliSec3072. */
  public static final int MILLI_SEC_3072 = 15;

  /** BLonRepeatTimer constant for milliSec16. */
  public static final BLonRepeatTimer milliSec16 = new BLonRepeatTimer(MILLI_SEC_16);
  /** BLonRepeatTimer constant for milliSec24. */
  public static final BLonRepeatTimer milliSec24 = new BLonRepeatTimer(MILLI_SEC_24);
  /** BLonRepeatTimer constant for milliSec32. */
  public static final BLonRepeatTimer milliSec32 = new BLonRepeatTimer(MILLI_SEC_32);
  /** BLonRepeatTimer constant for milliSec48. */
  public static final BLonRepeatTimer milliSec48 = new BLonRepeatTimer(MILLI_SEC_48);
  /** BLonRepeatTimer constant for milliSec64. */
  public static final BLonRepeatTimer milliSec64 = new BLonRepeatTimer(MILLI_SEC_64);
  /** BLonRepeatTimer constant for milliSec96. */
  public static final BLonRepeatTimer milliSec96 = new BLonRepeatTimer(MILLI_SEC_96);
  /** BLonRepeatTimer constant for milliSec128. */
  public static final BLonRepeatTimer milliSec128 = new BLonRepeatTimer(MILLI_SEC_128);
  /** BLonRepeatTimer constant for milliSec192. */
  public static final BLonRepeatTimer milliSec192 = new BLonRepeatTimer(MILLI_SEC_192);
  /** BLonRepeatTimer constant for milliSec256. */
  public static final BLonRepeatTimer milliSec256 = new BLonRepeatTimer(MILLI_SEC_256);
  /** BLonRepeatTimer constant for milliSec384. */
  public static final BLonRepeatTimer milliSec384 = new BLonRepeatTimer(MILLI_SEC_384);
  /** BLonRepeatTimer constant for milliSec512. */
  public static final BLonRepeatTimer milliSec512 = new BLonRepeatTimer(MILLI_SEC_512);
  /** BLonRepeatTimer constant for milliSec768. */
  public static final BLonRepeatTimer milliSec768 = new BLonRepeatTimer(MILLI_SEC_768);
  /** BLonRepeatTimer constant for milliSec1024. */
  public static final BLonRepeatTimer milliSec1024 = new BLonRepeatTimer(MILLI_SEC_1024);
  /** BLonRepeatTimer constant for milliSec1536. */
  public static final BLonRepeatTimer milliSec1536 = new BLonRepeatTimer(MILLI_SEC_1536);
  /** BLonRepeatTimer constant for milliSec2048. */
  public static final BLonRepeatTimer milliSec2048 = new BLonRepeatTimer(MILLI_SEC_2048);
  /** BLonRepeatTimer constant for milliSec3072. */
  public static final BLonRepeatTimer milliSec3072 = new BLonRepeatTimer(MILLI_SEC_3072);

  /** Factory method with ordinal. */
  public static BLonRepeatTimer make(int ordinal)
  {
    return (BLonRepeatTimer)milliSec16.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonRepeatTimer make(String tag)
  {
    return (BLonRepeatTimer)milliSec16.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonRepeatTimer(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonRepeatTimer DEFAULT = milliSec16;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonRepeatTimer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final int[] TIMES = new int[]{16,24,32,48,64,96,128,192,256,384,512,768,1024,1536,2048,3072};
  
  public int getTime() {return TIMES[getOrdinal()];}
  
}
