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
 * The BLonExControlEnum represents Lonworks standard enumeration ExControlT.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "exControlNone", ordinal = 0),
    @Range(value = "exControlOther", ordinal = 1),
    @Range(value = "exControlThisAddr", ordinal = 2),
    @Range(value = "exControlNul", ordinal = -1)
  }
)
public final class BLonExControlEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonExControlEnum(1568755655)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for exControlNone. */
  public static final int EX_CONTROL_NONE = 0;
  /** Ordinal value for exControlOther. */
  public static final int EX_CONTROL_OTHER = 1;
  /** Ordinal value for exControlThisAddr. */
  public static final int EX_CONTROL_THIS_ADDR = 2;
  /** Ordinal value for exControlNul. */
  public static final int EX_CONTROL_NUL = -1;

  /** BLonExControlEnum constant for exControlNone. */
  public static final BLonExControlEnum exControlNone = new BLonExControlEnum(EX_CONTROL_NONE);
  /** BLonExControlEnum constant for exControlOther. */
  public static final BLonExControlEnum exControlOther = new BLonExControlEnum(EX_CONTROL_OTHER);
  /** BLonExControlEnum constant for exControlThisAddr. */
  public static final BLonExControlEnum exControlThisAddr = new BLonExControlEnum(EX_CONTROL_THIS_ADDR);
  /** BLonExControlEnum constant for exControlNul. */
  public static final BLonExControlEnum exControlNul = new BLonExControlEnum(EX_CONTROL_NUL);

  /** Factory method with ordinal. */
  public static BLonExControlEnum make(int ordinal)
  {
    return (BLonExControlEnum)exControlNone.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonExControlEnum make(String tag)
  {
    return (BLonExControlEnum)exControlNone.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonExControlEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonExControlEnum DEFAULT = exControlNone;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonExControlEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
