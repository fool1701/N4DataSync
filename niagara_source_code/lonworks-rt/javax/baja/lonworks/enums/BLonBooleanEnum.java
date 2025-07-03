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
 * The BLonBooleanEnum represents Lonworks standard enumeration BooleanT.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "boolFalse", ordinal = 0),
    @Range(value = "boolTrue", ordinal = 1),
    @Range(value = "boolNul", ordinal = -1)
  }
)
public final class BLonBooleanEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonBooleanEnum(1630548491)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for boolFalse. */
  public static final int BOOL_FALSE = 0;
  /** Ordinal value for boolTrue. */
  public static final int BOOL_TRUE = 1;
  /** Ordinal value for boolNul. */
  public static final int BOOL_NUL = -1;

  /** BLonBooleanEnum constant for boolFalse. */
  public static final BLonBooleanEnum boolFalse = new BLonBooleanEnum(BOOL_FALSE);
  /** BLonBooleanEnum constant for boolTrue. */
  public static final BLonBooleanEnum boolTrue = new BLonBooleanEnum(BOOL_TRUE);
  /** BLonBooleanEnum constant for boolNul. */
  public static final BLonBooleanEnum boolNul = new BLonBooleanEnum(BOOL_NUL);

  /** Factory method with ordinal. */
  public static BLonBooleanEnum make(int ordinal)
  {
    return (BLonBooleanEnum)boolFalse.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonBooleanEnum make(String tag)
  {
    return (BLonBooleanEnum)boolFalse.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonBooleanEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonBooleanEnum DEFAULT = boolFalse;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonBooleanEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
