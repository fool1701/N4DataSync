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
 * The BLonGfciStatusEnum represents Lonworks standard enumeration GfciStatusT.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "gfciUnknown", ordinal = 0),
    @Range(value = "gfciNormal", ordinal = 1),
    @Range(value = "gfciTripped", ordinal = 2),
    @Range(value = "gfciTestFailed", ordinal = 3),
    @Range(value = "gfciTestPassed", ordinal = 4),
    @Range(value = "gfciTestNow", ordinal = 5),
    @Range(value = "gfciNul", ordinal = -1)
  }
)
public final class BLonGfciStatusEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonGfciStatusEnum(47843290)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for gfciUnknown. */
  public static final int GFCI_UNKNOWN = 0;
  /** Ordinal value for gfciNormal. */
  public static final int GFCI_NORMAL = 1;
  /** Ordinal value for gfciTripped. */
  public static final int GFCI_TRIPPED = 2;
  /** Ordinal value for gfciTestFailed. */
  public static final int GFCI_TEST_FAILED = 3;
  /** Ordinal value for gfciTestPassed. */
  public static final int GFCI_TEST_PASSED = 4;
  /** Ordinal value for gfciTestNow. */
  public static final int GFCI_TEST_NOW = 5;
  /** Ordinal value for gfciNul. */
  public static final int GFCI_NUL = -1;

  /** BLonGfciStatusEnum constant for gfciUnknown. */
  public static final BLonGfciStatusEnum gfciUnknown = new BLonGfciStatusEnum(GFCI_UNKNOWN);
  /** BLonGfciStatusEnum constant for gfciNormal. */
  public static final BLonGfciStatusEnum gfciNormal = new BLonGfciStatusEnum(GFCI_NORMAL);
  /** BLonGfciStatusEnum constant for gfciTripped. */
  public static final BLonGfciStatusEnum gfciTripped = new BLonGfciStatusEnum(GFCI_TRIPPED);
  /** BLonGfciStatusEnum constant for gfciTestFailed. */
  public static final BLonGfciStatusEnum gfciTestFailed = new BLonGfciStatusEnum(GFCI_TEST_FAILED);
  /** BLonGfciStatusEnum constant for gfciTestPassed. */
  public static final BLonGfciStatusEnum gfciTestPassed = new BLonGfciStatusEnum(GFCI_TEST_PASSED);
  /** BLonGfciStatusEnum constant for gfciTestNow. */
  public static final BLonGfciStatusEnum gfciTestNow = new BLonGfciStatusEnum(GFCI_TEST_NOW);
  /** BLonGfciStatusEnum constant for gfciNul. */
  public static final BLonGfciStatusEnum gfciNul = new BLonGfciStatusEnum(GFCI_NUL);

  /** Factory method with ordinal. */
  public static BLonGfciStatusEnum make(int ordinal)
  {
    return (BLonGfciStatusEnum)gfciUnknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonGfciStatusEnum make(String tag)
  {
    return (BLonGfciStatusEnum)gfciUnknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonGfciStatusEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonGfciStatusEnum DEFAULT = gfciUnknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonGfciStatusEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
