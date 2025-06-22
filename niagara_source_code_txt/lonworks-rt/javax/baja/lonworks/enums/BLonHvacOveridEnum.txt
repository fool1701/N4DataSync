/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BLonHvacOveridEnum class provides enumeration for SNVT_hvac_overid
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "hvoOff", ordinal = 0),
    @Range(value = "hvoPosition", ordinal = 1),
    @Range(value = "hvoFlowValue", ordinal = 2),
    @Range(value = "hvoFlowPercent", ordinal = 3),
    @Range(value = "hvoOpen", ordinal = 4),
    @Range(value = "hvoClose", ordinal = 5),
    @Range(value = "hvoMinimum", ordinal = 6),
    @Range(value = "hvoMaximum", ordinal = 7),
    @Range(value = "hvoUnused8", ordinal = 8),
    @Range(value = "hvoUnused9", ordinal = 9),
    @Range(value = "hvoUnused10", ordinal = 10),
    @Range(value = "hvoUnused11", ordinal = 11),
    @Range(value = "hvoUnused12", ordinal = 12),
    @Range(value = "hvoUnused13", ordinal = 13),
    @Range(value = "hvoUnused14", ordinal = 14),
    @Range(value = "hvoUnused15", ordinal = 15),
    @Range(value = "hvoUnused16", ordinal = 16),
    @Range(value = "hvoPosition1", ordinal = 17),
    @Range(value = "hvoFlowValue1", ordinal = 18),
    @Range(value = "hvoFlowPercent1", ordinal = 19),
    @Range(value = "hvoOpen1", ordinal = 20),
    @Range(value = "hvoClosed1", ordinal = 21),
    @Range(value = "hvoMinimum1", ordinal = 22),
    @Range(value = "hvoMaximum1", ordinal = 23),
    @Range(value = "hvoUnused24", ordinal = 24),
    @Range(value = "hvoUnused25", ordinal = 25),
    @Range(value = "hvoUnused26", ordinal = 26),
    @Range(value = "hvoUnused27", ordinal = 27),
    @Range(value = "hvoUnused28", ordinal = 28),
    @Range(value = "hvoUnused29", ordinal = 29),
    @Range(value = "hvoUnused30", ordinal = 30),
    @Range(value = "hvoUnused31", ordinal = 31),
    @Range(value = "hvoUnused32", ordinal = 32),
    @Range(value = "hvoPosition2", ordinal = 33),
    @Range(value = "hvoFlowValue2", ordinal = 34),
    @Range(value = "hvoFlowPercent2", ordinal = 35),
    @Range(value = "hvoOpen2", ordinal = 36),
    @Range(value = "hvoClosed2", ordinal = 37),
    @Range(value = "hvoMinimum2", ordinal = 38),
    @Range(value = "hvoMaximum2", ordinal = 39),
    @Range(value = "hvoUnused40", ordinal = 40),
    @Range(value = "hvoUnused41", ordinal = 41),
    @Range(value = "hvoUnused42", ordinal = 42),
    @Range(value = "hvoUnused43", ordinal = 43),
    @Range(value = "hvoUnused44", ordinal = 44),
    @Range(value = "hvoUnused45", ordinal = 45),
    @Range(value = "hvoUnused46", ordinal = 46),
    @Range(value = "hvoUnused47", ordinal = 47),
    @Range(value = "hvoUnused48", ordinal = 48),
    @Range(value = "hvoNul", ordinal = -1)
  },
  defaultValue = "hvoNul"
)
public final class BLonHvacOveridEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonHvacOveridEnum(1999641536)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for hvoOff. */
  public static final int HVO_OFF = 0;
  /** Ordinal value for hvoPosition. */
  public static final int HVO_POSITION = 1;
  /** Ordinal value for hvoFlowValue. */
  public static final int HVO_FLOW_VALUE = 2;
  /** Ordinal value for hvoFlowPercent. */
  public static final int HVO_FLOW_PERCENT = 3;
  /** Ordinal value for hvoOpen. */
  public static final int HVO_OPEN = 4;
  /** Ordinal value for hvoClose. */
  public static final int HVO_CLOSE = 5;
  /** Ordinal value for hvoMinimum. */
  public static final int HVO_MINIMUM = 6;
  /** Ordinal value for hvoMaximum. */
  public static final int HVO_MAXIMUM = 7;
  /** Ordinal value for hvoUnused8. */
  public static final int HVO_UNUSED_8 = 8;
  /** Ordinal value for hvoUnused9. */
  public static final int HVO_UNUSED_9 = 9;
  /** Ordinal value for hvoUnused10. */
  public static final int HVO_UNUSED_10 = 10;
  /** Ordinal value for hvoUnused11. */
  public static final int HVO_UNUSED_11 = 11;
  /** Ordinal value for hvoUnused12. */
  public static final int HVO_UNUSED_12 = 12;
  /** Ordinal value for hvoUnused13. */
  public static final int HVO_UNUSED_13 = 13;
  /** Ordinal value for hvoUnused14. */
  public static final int HVO_UNUSED_14 = 14;
  /** Ordinal value for hvoUnused15. */
  public static final int HVO_UNUSED_15 = 15;
  /** Ordinal value for hvoUnused16. */
  public static final int HVO_UNUSED_16 = 16;
  /** Ordinal value for hvoPosition1. */
  public static final int HVO_POSITION_1 = 17;
  /** Ordinal value for hvoFlowValue1. */
  public static final int HVO_FLOW_VALUE_1 = 18;
  /** Ordinal value for hvoFlowPercent1. */
  public static final int HVO_FLOW_PERCENT_1 = 19;
  /** Ordinal value for hvoOpen1. */
  public static final int HVO_OPEN_1 = 20;
  /** Ordinal value for hvoClosed1. */
  public static final int HVO_CLOSED_1 = 21;
  /** Ordinal value for hvoMinimum1. */
  public static final int HVO_MINIMUM_1 = 22;
  /** Ordinal value for hvoMaximum1. */
  public static final int HVO_MAXIMUM_1 = 23;
  /** Ordinal value for hvoUnused24. */
  public static final int HVO_UNUSED_24 = 24;
  /** Ordinal value for hvoUnused25. */
  public static final int HVO_UNUSED_25 = 25;
  /** Ordinal value for hvoUnused26. */
  public static final int HVO_UNUSED_26 = 26;
  /** Ordinal value for hvoUnused27. */
  public static final int HVO_UNUSED_27 = 27;
  /** Ordinal value for hvoUnused28. */
  public static final int HVO_UNUSED_28 = 28;
  /** Ordinal value for hvoUnused29. */
  public static final int HVO_UNUSED_29 = 29;
  /** Ordinal value for hvoUnused30. */
  public static final int HVO_UNUSED_30 = 30;
  /** Ordinal value for hvoUnused31. */
  public static final int HVO_UNUSED_31 = 31;
  /** Ordinal value for hvoUnused32. */
  public static final int HVO_UNUSED_32 = 32;
  /** Ordinal value for hvoPosition2. */
  public static final int HVO_POSITION_2 = 33;
  /** Ordinal value for hvoFlowValue2. */
  public static final int HVO_FLOW_VALUE_2 = 34;
  /** Ordinal value for hvoFlowPercent2. */
  public static final int HVO_FLOW_PERCENT_2 = 35;
  /** Ordinal value for hvoOpen2. */
  public static final int HVO_OPEN_2 = 36;
  /** Ordinal value for hvoClosed2. */
  public static final int HVO_CLOSED_2 = 37;
  /** Ordinal value for hvoMinimum2. */
  public static final int HVO_MINIMUM_2 = 38;
  /** Ordinal value for hvoMaximum2. */
  public static final int HVO_MAXIMUM_2 = 39;
  /** Ordinal value for hvoUnused40. */
  public static final int HVO_UNUSED_40 = 40;
  /** Ordinal value for hvoUnused41. */
  public static final int HVO_UNUSED_41 = 41;
  /** Ordinal value for hvoUnused42. */
  public static final int HVO_UNUSED_42 = 42;
  /** Ordinal value for hvoUnused43. */
  public static final int HVO_UNUSED_43 = 43;
  /** Ordinal value for hvoUnused44. */
  public static final int HVO_UNUSED_44 = 44;
  /** Ordinal value for hvoUnused45. */
  public static final int HVO_UNUSED_45 = 45;
  /** Ordinal value for hvoUnused46. */
  public static final int HVO_UNUSED_46 = 46;
  /** Ordinal value for hvoUnused47. */
  public static final int HVO_UNUSED_47 = 47;
  /** Ordinal value for hvoUnused48. */
  public static final int HVO_UNUSED_48 = 48;
  /** Ordinal value for hvoNul. */
  public static final int HVO_NUL = -1;

  /** BLonHvacOveridEnum constant for hvoOff. */
  public static final BLonHvacOveridEnum hvoOff = new BLonHvacOveridEnum(HVO_OFF);
  /** BLonHvacOveridEnum constant for hvoPosition. */
  public static final BLonHvacOveridEnum hvoPosition = new BLonHvacOveridEnum(HVO_POSITION);
  /** BLonHvacOveridEnum constant for hvoFlowValue. */
  public static final BLonHvacOveridEnum hvoFlowValue = new BLonHvacOveridEnum(HVO_FLOW_VALUE);
  /** BLonHvacOveridEnum constant for hvoFlowPercent. */
  public static final BLonHvacOveridEnum hvoFlowPercent = new BLonHvacOveridEnum(HVO_FLOW_PERCENT);
  /** BLonHvacOveridEnum constant for hvoOpen. */
  public static final BLonHvacOveridEnum hvoOpen = new BLonHvacOveridEnum(HVO_OPEN);
  /** BLonHvacOveridEnum constant for hvoClose. */
  public static final BLonHvacOveridEnum hvoClose = new BLonHvacOveridEnum(HVO_CLOSE);
  /** BLonHvacOveridEnum constant for hvoMinimum. */
  public static final BLonHvacOveridEnum hvoMinimum = new BLonHvacOveridEnum(HVO_MINIMUM);
  /** BLonHvacOveridEnum constant for hvoMaximum. */
  public static final BLonHvacOveridEnum hvoMaximum = new BLonHvacOveridEnum(HVO_MAXIMUM);
  /** BLonHvacOveridEnum constant for hvoUnused8. */
  public static final BLonHvacOveridEnum hvoUnused8 = new BLonHvacOveridEnum(HVO_UNUSED_8);
  /** BLonHvacOveridEnum constant for hvoUnused9. */
  public static final BLonHvacOveridEnum hvoUnused9 = new BLonHvacOveridEnum(HVO_UNUSED_9);
  /** BLonHvacOveridEnum constant for hvoUnused10. */
  public static final BLonHvacOveridEnum hvoUnused10 = new BLonHvacOveridEnum(HVO_UNUSED_10);
  /** BLonHvacOveridEnum constant for hvoUnused11. */
  public static final BLonHvacOveridEnum hvoUnused11 = new BLonHvacOveridEnum(HVO_UNUSED_11);
  /** BLonHvacOveridEnum constant for hvoUnused12. */
  public static final BLonHvacOveridEnum hvoUnused12 = new BLonHvacOveridEnum(HVO_UNUSED_12);
  /** BLonHvacOveridEnum constant for hvoUnused13. */
  public static final BLonHvacOveridEnum hvoUnused13 = new BLonHvacOveridEnum(HVO_UNUSED_13);
  /** BLonHvacOveridEnum constant for hvoUnused14. */
  public static final BLonHvacOveridEnum hvoUnused14 = new BLonHvacOveridEnum(HVO_UNUSED_14);
  /** BLonHvacOveridEnum constant for hvoUnused15. */
  public static final BLonHvacOveridEnum hvoUnused15 = new BLonHvacOveridEnum(HVO_UNUSED_15);
  /** BLonHvacOveridEnum constant for hvoUnused16. */
  public static final BLonHvacOveridEnum hvoUnused16 = new BLonHvacOveridEnum(HVO_UNUSED_16);
  /** BLonHvacOveridEnum constant for hvoPosition1. */
  public static final BLonHvacOveridEnum hvoPosition1 = new BLonHvacOveridEnum(HVO_POSITION_1);
  /** BLonHvacOveridEnum constant for hvoFlowValue1. */
  public static final BLonHvacOveridEnum hvoFlowValue1 = new BLonHvacOveridEnum(HVO_FLOW_VALUE_1);
  /** BLonHvacOveridEnum constant for hvoFlowPercent1. */
  public static final BLonHvacOveridEnum hvoFlowPercent1 = new BLonHvacOveridEnum(HVO_FLOW_PERCENT_1);
  /** BLonHvacOveridEnum constant for hvoOpen1. */
  public static final BLonHvacOveridEnum hvoOpen1 = new BLonHvacOveridEnum(HVO_OPEN_1);
  /** BLonHvacOveridEnum constant for hvoClosed1. */
  public static final BLonHvacOveridEnum hvoClosed1 = new BLonHvacOveridEnum(HVO_CLOSED_1);
  /** BLonHvacOveridEnum constant for hvoMinimum1. */
  public static final BLonHvacOveridEnum hvoMinimum1 = new BLonHvacOveridEnum(HVO_MINIMUM_1);
  /** BLonHvacOveridEnum constant for hvoMaximum1. */
  public static final BLonHvacOveridEnum hvoMaximum1 = new BLonHvacOveridEnum(HVO_MAXIMUM_1);
  /** BLonHvacOveridEnum constant for hvoUnused24. */
  public static final BLonHvacOveridEnum hvoUnused24 = new BLonHvacOveridEnum(HVO_UNUSED_24);
  /** BLonHvacOveridEnum constant for hvoUnused25. */
  public static final BLonHvacOveridEnum hvoUnused25 = new BLonHvacOveridEnum(HVO_UNUSED_25);
  /** BLonHvacOveridEnum constant for hvoUnused26. */
  public static final BLonHvacOveridEnum hvoUnused26 = new BLonHvacOveridEnum(HVO_UNUSED_26);
  /** BLonHvacOveridEnum constant for hvoUnused27. */
  public static final BLonHvacOveridEnum hvoUnused27 = new BLonHvacOveridEnum(HVO_UNUSED_27);
  /** BLonHvacOveridEnum constant for hvoUnused28. */
  public static final BLonHvacOveridEnum hvoUnused28 = new BLonHvacOveridEnum(HVO_UNUSED_28);
  /** BLonHvacOveridEnum constant for hvoUnused29. */
  public static final BLonHvacOveridEnum hvoUnused29 = new BLonHvacOveridEnum(HVO_UNUSED_29);
  /** BLonHvacOveridEnum constant for hvoUnused30. */
  public static final BLonHvacOveridEnum hvoUnused30 = new BLonHvacOveridEnum(HVO_UNUSED_30);
  /** BLonHvacOveridEnum constant for hvoUnused31. */
  public static final BLonHvacOveridEnum hvoUnused31 = new BLonHvacOveridEnum(HVO_UNUSED_31);
  /** BLonHvacOveridEnum constant for hvoUnused32. */
  public static final BLonHvacOveridEnum hvoUnused32 = new BLonHvacOveridEnum(HVO_UNUSED_32);
  /** BLonHvacOveridEnum constant for hvoPosition2. */
  public static final BLonHvacOveridEnum hvoPosition2 = new BLonHvacOveridEnum(HVO_POSITION_2);
  /** BLonHvacOveridEnum constant for hvoFlowValue2. */
  public static final BLonHvacOveridEnum hvoFlowValue2 = new BLonHvacOveridEnum(HVO_FLOW_VALUE_2);
  /** BLonHvacOveridEnum constant for hvoFlowPercent2. */
  public static final BLonHvacOveridEnum hvoFlowPercent2 = new BLonHvacOveridEnum(HVO_FLOW_PERCENT_2);
  /** BLonHvacOveridEnum constant for hvoOpen2. */
  public static final BLonHvacOveridEnum hvoOpen2 = new BLonHvacOveridEnum(HVO_OPEN_2);
  /** BLonHvacOveridEnum constant for hvoClosed2. */
  public static final BLonHvacOveridEnum hvoClosed2 = new BLonHvacOveridEnum(HVO_CLOSED_2);
  /** BLonHvacOveridEnum constant for hvoMinimum2. */
  public static final BLonHvacOveridEnum hvoMinimum2 = new BLonHvacOveridEnum(HVO_MINIMUM_2);
  /** BLonHvacOveridEnum constant for hvoMaximum2. */
  public static final BLonHvacOveridEnum hvoMaximum2 = new BLonHvacOveridEnum(HVO_MAXIMUM_2);
  /** BLonHvacOveridEnum constant for hvoUnused40. */
  public static final BLonHvacOveridEnum hvoUnused40 = new BLonHvacOveridEnum(HVO_UNUSED_40);
  /** BLonHvacOveridEnum constant for hvoUnused41. */
  public static final BLonHvacOveridEnum hvoUnused41 = new BLonHvacOveridEnum(HVO_UNUSED_41);
  /** BLonHvacOveridEnum constant for hvoUnused42. */
  public static final BLonHvacOveridEnum hvoUnused42 = new BLonHvacOveridEnum(HVO_UNUSED_42);
  /** BLonHvacOveridEnum constant for hvoUnused43. */
  public static final BLonHvacOveridEnum hvoUnused43 = new BLonHvacOveridEnum(HVO_UNUSED_43);
  /** BLonHvacOveridEnum constant for hvoUnused44. */
  public static final BLonHvacOveridEnum hvoUnused44 = new BLonHvacOveridEnum(HVO_UNUSED_44);
  /** BLonHvacOveridEnum constant for hvoUnused45. */
  public static final BLonHvacOveridEnum hvoUnused45 = new BLonHvacOveridEnum(HVO_UNUSED_45);
  /** BLonHvacOveridEnum constant for hvoUnused46. */
  public static final BLonHvacOveridEnum hvoUnused46 = new BLonHvacOveridEnum(HVO_UNUSED_46);
  /** BLonHvacOveridEnum constant for hvoUnused47. */
  public static final BLonHvacOveridEnum hvoUnused47 = new BLonHvacOveridEnum(HVO_UNUSED_47);
  /** BLonHvacOveridEnum constant for hvoUnused48. */
  public static final BLonHvacOveridEnum hvoUnused48 = new BLonHvacOveridEnum(HVO_UNUSED_48);
  /** BLonHvacOveridEnum constant for hvoNul. */
  public static final BLonHvacOveridEnum hvoNul = new BLonHvacOveridEnum(HVO_NUL);

  /** Factory method with ordinal. */
  public static BLonHvacOveridEnum make(int ordinal)
  {
    return (BLonHvacOveridEnum)hvoOff.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonHvacOveridEnum make(String tag)
  {
    return (BLonHvacOveridEnum)hvoOff.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonHvacOveridEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonHvacOveridEnum DEFAULT = hvoNul;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonHvacOveridEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
