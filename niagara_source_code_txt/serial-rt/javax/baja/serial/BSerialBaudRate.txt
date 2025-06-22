/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSerialBaudRate represents the possible baud rate choices
 * for a comm port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  23 Sep 02
 * @version   $Revision: 6$ $Date: 5/23/05 1:20:35 PM EDT$  
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "baud50", ordinal = 50),
    @Range(value = "baud75", ordinal = 75),
    @Range(value = "baud110", ordinal = 110),
    @Range(value = "baud134", ordinal = 134),
    @Range(value = "baud150", ordinal = 150),
    @Range(value = "baud200", ordinal = 200),
    @Range(value = "baud300", ordinal = 300),
    @Range(value = "baud600", ordinal = 600),
    @Range(value = "baud1200", ordinal = 1200),
    @Range(value = "baud1800", ordinal = 1800),
    @Range(value = "baud2400", ordinal = 2400),
    @Range(value = "baud4800", ordinal = 4800),
    @Range(value = "baud9600", ordinal = 9600),
    @Range(value = "baud19200", ordinal = 19200),
    @Range(value = "baud38400", ordinal = 38400),
    @Range(value = "baud57600", ordinal = 57600),
    @Range(value = "baud76800", ordinal = 76800),
    @Range(value = "baud115200", ordinal = 115200)
  }
)
public final class BSerialBaudRate
  extends BBaudRate
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialBaudRate(3762940293)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for baud50. */
  public static final int BAUD_50 = 50;
  /** Ordinal value for baud75. */
  public static final int BAUD_75 = 75;
  /** Ordinal value for baud110. */
  public static final int BAUD_110 = 110;
  /** Ordinal value for baud134. */
  public static final int BAUD_134 = 134;
  /** Ordinal value for baud150. */
  public static final int BAUD_150 = 150;
  /** Ordinal value for baud200. */
  public static final int BAUD_200 = 200;
  /** Ordinal value for baud300. */
  public static final int BAUD_300 = 300;
  /** Ordinal value for baud600. */
  public static final int BAUD_600 = 600;
  /** Ordinal value for baud1200. */
  public static final int BAUD_1200 = 1200;
  /** Ordinal value for baud1800. */
  public static final int BAUD_1800 = 1800;
  /** Ordinal value for baud2400. */
  public static final int BAUD_2400 = 2400;
  /** Ordinal value for baud4800. */
  public static final int BAUD_4800 = 4800;
  /** Ordinal value for baud9600. */
  public static final int BAUD_9600 = 9600;
  /** Ordinal value for baud19200. */
  public static final int BAUD_19200 = 19200;
  /** Ordinal value for baud38400. */
  public static final int BAUD_38400 = 38400;
  /** Ordinal value for baud57600. */
  public static final int BAUD_57600 = 57600;
  /** Ordinal value for baud76800. */
  public static final int BAUD_76800 = 76800;
  /** Ordinal value for baud115200. */
  public static final int BAUD_115200 = 115200;

  /** BSerialBaudRate constant for baud50. */
  public static final BSerialBaudRate baud50 = new BSerialBaudRate(BAUD_50);
  /** BSerialBaudRate constant for baud75. */
  public static final BSerialBaudRate baud75 = new BSerialBaudRate(BAUD_75);
  /** BSerialBaudRate constant for baud110. */
  public static final BSerialBaudRate baud110 = new BSerialBaudRate(BAUD_110);
  /** BSerialBaudRate constant for baud134. */
  public static final BSerialBaudRate baud134 = new BSerialBaudRate(BAUD_134);
  /** BSerialBaudRate constant for baud150. */
  public static final BSerialBaudRate baud150 = new BSerialBaudRate(BAUD_150);
  /** BSerialBaudRate constant for baud200. */
  public static final BSerialBaudRate baud200 = new BSerialBaudRate(BAUD_200);
  /** BSerialBaudRate constant for baud300. */
  public static final BSerialBaudRate baud300 = new BSerialBaudRate(BAUD_300);
  /** BSerialBaudRate constant for baud600. */
  public static final BSerialBaudRate baud600 = new BSerialBaudRate(BAUD_600);
  /** BSerialBaudRate constant for baud1200. */
  public static final BSerialBaudRate baud1200 = new BSerialBaudRate(BAUD_1200);
  /** BSerialBaudRate constant for baud1800. */
  public static final BSerialBaudRate baud1800 = new BSerialBaudRate(BAUD_1800);
  /** BSerialBaudRate constant for baud2400. */
  public static final BSerialBaudRate baud2400 = new BSerialBaudRate(BAUD_2400);
  /** BSerialBaudRate constant for baud4800. */
  public static final BSerialBaudRate baud4800 = new BSerialBaudRate(BAUD_4800);
  /** BSerialBaudRate constant for baud9600. */
  public static final BSerialBaudRate baud9600 = new BSerialBaudRate(BAUD_9600);
  /** BSerialBaudRate constant for baud19200. */
  public static final BSerialBaudRate baud19200 = new BSerialBaudRate(BAUD_19200);
  /** BSerialBaudRate constant for baud38400. */
  public static final BSerialBaudRate baud38400 = new BSerialBaudRate(BAUD_38400);
  /** BSerialBaudRate constant for baud57600. */
  public static final BSerialBaudRate baud57600 = new BSerialBaudRate(BAUD_57600);
  /** BSerialBaudRate constant for baud76800. */
  public static final BSerialBaudRate baud76800 = new BSerialBaudRate(BAUD_76800);
  /** BSerialBaudRate constant for baud115200. */
  public static final BSerialBaudRate baud115200 = new BSerialBaudRate(BAUD_115200);

  /** Factory method with ordinal. */
  public static BSerialBaudRate make(int ordinal)
  {
    return (BSerialBaudRate)baud50.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSerialBaudRate make(String tag)
  {
    return (BSerialBaudRate)baud50.getRange().get(tag);
  }

  /** Private constructor. */
  private BSerialBaudRate(int ordinal)
  {
    super(ordinal);
  }

  public static final BSerialBaudRate DEFAULT = baud50;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialBaudRate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
