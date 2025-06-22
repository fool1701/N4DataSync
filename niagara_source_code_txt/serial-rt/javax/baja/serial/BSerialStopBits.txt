/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSerialStopBits represents the stop bit settings
 * for a serial port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  22 Mar 02
 * @version   $Revision: 5$ $Date: 3/31/04 11:52:56 AM EST$  
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "stopBit1", ordinal = 1),
    @Range(value = "stopBits2", ordinal = 2)
  }
)
public final class BSerialStopBits
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialStopBits(3377356785)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for stopBit1. */
  public static final int STOP_BIT_1 = 1;
  /** Ordinal value for stopBits2. */
  public static final int STOP_BITS_2 = 2;

  /** BSerialStopBits constant for stopBit1. */
  public static final BSerialStopBits stopBit1 = new BSerialStopBits(STOP_BIT_1);
  /** BSerialStopBits constant for stopBits2. */
  public static final BSerialStopBits stopBits2 = new BSerialStopBits(STOP_BITS_2);

  /** Factory method with ordinal. */
  public static BSerialStopBits make(int ordinal)
  {
    return (BSerialStopBits)stopBit1.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSerialStopBits make(String tag)
  {
    return (BSerialStopBits)stopBit1.getRange().get(tag);
  }

  /** Private constructor. */
  private BSerialStopBits(int ordinal)
  {
    super(ordinal);
  }

  public static final BSerialStopBits DEFAULT = stopBit1;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialStopBits.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
