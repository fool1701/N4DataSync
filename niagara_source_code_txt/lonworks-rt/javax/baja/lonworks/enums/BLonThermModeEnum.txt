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
 * The BLonThermModeEnum class provides enumeration for SNVT_them_mode
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "thermNoControl", ordinal = 0),
    @Range(value = "thermInOut", ordinal = 1),
    @Range(value = "thermModulating", ordinal = 2),
    @Range(value = "thermNul", ordinal = -1)
  }
)
public final class BLonThermModeEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonThermModeEnum(963925084)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for thermNoControl. */
  public static final int THERM_NO_CONTROL = 0;
  /** Ordinal value for thermInOut. */
  public static final int THERM_IN_OUT = 1;
  /** Ordinal value for thermModulating. */
  public static final int THERM_MODULATING = 2;
  /** Ordinal value for thermNul. */
  public static final int THERM_NUL = -1;

  /** BLonThermModeEnum constant for thermNoControl. */
  public static final BLonThermModeEnum thermNoControl = new BLonThermModeEnum(THERM_NO_CONTROL);
  /** BLonThermModeEnum constant for thermInOut. */
  public static final BLonThermModeEnum thermInOut = new BLonThermModeEnum(THERM_IN_OUT);
  /** BLonThermModeEnum constant for thermModulating. */
  public static final BLonThermModeEnum thermModulating = new BLonThermModeEnum(THERM_MODULATING);
  /** BLonThermModeEnum constant for thermNul. */
  public static final BLonThermModeEnum thermNul = new BLonThermModeEnum(THERM_NUL);

  /** Factory method with ordinal. */
  public static BLonThermModeEnum make(int ordinal)
  {
    return (BLonThermModeEnum)thermNoControl.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonThermModeEnum make(String tag)
  {
    return (BLonThermModeEnum)thermNoControl.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonThermModeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonThermModeEnum DEFAULT = thermNoControl;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonThermModeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
