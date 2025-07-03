/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSerialDataBits represents the number of databits
 * for a serial port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  22 Mar 02
 * @version   $Revision: 5$ $Date: 3/31/04 11:52:47 AM EST$  
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "dataBits8", ordinal = 8),
    @Range(value = "dataBits7", ordinal = 7),
    @Range(value = "dataBits6", ordinal = 6),
    @Range(value = "dataBits5", ordinal = 5)
  }
)
public final class BSerialDataBits
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialDataBits(129916101)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for dataBits8. */
  public static final int DATA_BITS_8 = 8;
  /** Ordinal value for dataBits7. */
  public static final int DATA_BITS_7 = 7;
  /** Ordinal value for dataBits6. */
  public static final int DATA_BITS_6 = 6;
  /** Ordinal value for dataBits5. */
  public static final int DATA_BITS_5 = 5;

  /** BSerialDataBits constant for dataBits8. */
  public static final BSerialDataBits dataBits8 = new BSerialDataBits(DATA_BITS_8);
  /** BSerialDataBits constant for dataBits7. */
  public static final BSerialDataBits dataBits7 = new BSerialDataBits(DATA_BITS_7);
  /** BSerialDataBits constant for dataBits6. */
  public static final BSerialDataBits dataBits6 = new BSerialDataBits(DATA_BITS_6);
  /** BSerialDataBits constant for dataBits5. */
  public static final BSerialDataBits dataBits5 = new BSerialDataBits(DATA_BITS_5);

  /** Factory method with ordinal. */
  public static BSerialDataBits make(int ordinal)
  {
    return (BSerialDataBits)dataBits8.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSerialDataBits make(String tag)
  {
    return (BSerialDataBits)dataBits8.getRange().get(tag);
  }

  /** Private constructor. */
  private BSerialDataBits(int ordinal)
  {
    super(ordinal);
  }

  public static final BSerialDataBits DEFAULT = dataBits8;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialDataBits.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
