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
 * The BLonChillerEnum class provides enumeration for the running
 * mode of a SNVT_chiller
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:23 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "chlrOff", ordinal = 0),
    @Range(value = "chlrStart", ordinal = 1),
    @Range(value = "chlrRun", ordinal = 2),
    @Range(value = "chlrPreshutdn", ordinal = 3),
    @Range(value = "chlrService", ordinal = 4),
    @Range(value = "chlrNull", ordinal = -1)
  }
)
public final class BLonChillerEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonChillerEnum(3429469590)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for chlrOff. */
  public static final int CHLR_OFF = 0;
  /** Ordinal value for chlrStart. */
  public static final int CHLR_START = 1;
  /** Ordinal value for chlrRun. */
  public static final int CHLR_RUN = 2;
  /** Ordinal value for chlrPreshutdn. */
  public static final int CHLR_PRESHUTDN = 3;
  /** Ordinal value for chlrService. */
  public static final int CHLR_SERVICE = 4;
  /** Ordinal value for chlrNull. */
  public static final int CHLR_NULL = -1;

  /** BLonChillerEnum constant for chlrOff. */
  public static final BLonChillerEnum chlrOff = new BLonChillerEnum(CHLR_OFF);
  /** BLonChillerEnum constant for chlrStart. */
  public static final BLonChillerEnum chlrStart = new BLonChillerEnum(CHLR_START);
  /** BLonChillerEnum constant for chlrRun. */
  public static final BLonChillerEnum chlrRun = new BLonChillerEnum(CHLR_RUN);
  /** BLonChillerEnum constant for chlrPreshutdn. */
  public static final BLonChillerEnum chlrPreshutdn = new BLonChillerEnum(CHLR_PRESHUTDN);
  /** BLonChillerEnum constant for chlrService. */
  public static final BLonChillerEnum chlrService = new BLonChillerEnum(CHLR_SERVICE);
  /** BLonChillerEnum constant for chlrNull. */
  public static final BLonChillerEnum chlrNull = new BLonChillerEnum(CHLR_NULL);

  /** Factory method with ordinal. */
  public static BLonChillerEnum make(int ordinal)
  {
    return (BLonChillerEnum)chlrOff.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonChillerEnum make(String tag)
  {
    return (BLonChillerEnum)chlrOff.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonChillerEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonChillerEnum DEFAULT = chlrOff;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonChillerEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
