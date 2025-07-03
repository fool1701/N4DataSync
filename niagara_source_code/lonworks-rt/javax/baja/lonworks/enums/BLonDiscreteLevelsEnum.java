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
 * The BLonDiscreteLevelsEnum class provides enumeration for
 * SNVT_lev_disc
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:26 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "stOff", ordinal = 0),
    @Range(value = "stLow", ordinal = 1),
    @Range(value = "stMed", ordinal = 2),
    @Range(value = "stHigh", ordinal = 3),
    @Range(value = "stOn", ordinal = 4),
    @Range(value = "stNul", ordinal = -1)
  }
)
public final class BLonDiscreteLevelsEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonDiscreteLevelsEnum(2025501261)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for stOff. */
  public static final int ST_OFF = 0;
  /** Ordinal value for stLow. */
  public static final int ST_LOW = 1;
  /** Ordinal value for stMed. */
  public static final int ST_MED = 2;
  /** Ordinal value for stHigh. */
  public static final int ST_HIGH = 3;
  /** Ordinal value for stOn. */
  public static final int ST_ON = 4;
  /** Ordinal value for stNul. */
  public static final int ST_NUL = -1;

  /** BLonDiscreteLevelsEnum constant for stOff. */
  public static final BLonDiscreteLevelsEnum stOff = new BLonDiscreteLevelsEnum(ST_OFF);
  /** BLonDiscreteLevelsEnum constant for stLow. */
  public static final BLonDiscreteLevelsEnum stLow = new BLonDiscreteLevelsEnum(ST_LOW);
  /** BLonDiscreteLevelsEnum constant for stMed. */
  public static final BLonDiscreteLevelsEnum stMed = new BLonDiscreteLevelsEnum(ST_MED);
  /** BLonDiscreteLevelsEnum constant for stHigh. */
  public static final BLonDiscreteLevelsEnum stHigh = new BLonDiscreteLevelsEnum(ST_HIGH);
  /** BLonDiscreteLevelsEnum constant for stOn. */
  public static final BLonDiscreteLevelsEnum stOn = new BLonDiscreteLevelsEnum(ST_ON);
  /** BLonDiscreteLevelsEnum constant for stNul. */
  public static final BLonDiscreteLevelsEnum stNul = new BLonDiscreteLevelsEnum(ST_NUL);

  /** Factory method with ordinal. */
  public static BLonDiscreteLevelsEnum make(int ordinal)
  {
    return (BLonDiscreteLevelsEnum)stOff.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonDiscreteLevelsEnum make(String tag)
  {
    return (BLonDiscreteLevelsEnum)stOff.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonDiscreteLevelsEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonDiscreteLevelsEnum DEFAULT = stOff;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDiscreteLevelsEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
