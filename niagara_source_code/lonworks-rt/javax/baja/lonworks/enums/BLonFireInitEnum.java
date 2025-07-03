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
 * The BLonFireInitEnum class provides enumeration for SNVT_fire_init
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:29 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "fiUndefined", ordinal = 0),
    @Range(value = "fiThermalFixed", ordinal = 1),
    @Range(value = "fiSmokeIon", ordinal = 2),
    @Range(value = "fiMultiIonThermal", ordinal = 3),
    @Range(value = "fiSmokePhoto", ordinal = 4),
    @Range(value = "fiMultiPhotoThermal", ordinal = 5),
    @Range(value = "fiMultiPhotoIon", ordinal = 6),
    @Range(value = "fiMultiPhotoIonThermal", ordinal = 7),
    @Range(value = "fiThermalRor", ordinal = 8),
    @Range(value = "fiMultiThermalRor", ordinal = 9),
    @Range(value = "fiManualPull", ordinal = 10),
    @Range(value = "fiWaterFlow", ordinal = 11),
    @Range(value = "fiWaterFlowTamper", ordinal = 12),
    @Range(value = "fiStatusOnly", ordinal = 13),
    @Range(value = "fiManualCall", ordinal = 14),
    @Range(value = "fiFiremanCall", ordinal = 15),
    @Range(value = "fiUniveral", ordinal = 16),
    @Range(value = "fiNul", ordinal = -1)
  }
)
public final class BLonFireInitEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonFireInitEnum(4166010586)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for fiUndefined. */
  public static final int FI_UNDEFINED = 0;
  /** Ordinal value for fiThermalFixed. */
  public static final int FI_THERMAL_FIXED = 1;
  /** Ordinal value for fiSmokeIon. */
  public static final int FI_SMOKE_ION = 2;
  /** Ordinal value for fiMultiIonThermal. */
  public static final int FI_MULTI_ION_THERMAL = 3;
  /** Ordinal value for fiSmokePhoto. */
  public static final int FI_SMOKE_PHOTO = 4;
  /** Ordinal value for fiMultiPhotoThermal. */
  public static final int FI_MULTI_PHOTO_THERMAL = 5;
  /** Ordinal value for fiMultiPhotoIon. */
  public static final int FI_MULTI_PHOTO_ION = 6;
  /** Ordinal value for fiMultiPhotoIonThermal. */
  public static final int FI_MULTI_PHOTO_ION_THERMAL = 7;
  /** Ordinal value for fiThermalRor. */
  public static final int FI_THERMAL_ROR = 8;
  /** Ordinal value for fiMultiThermalRor. */
  public static final int FI_MULTI_THERMAL_ROR = 9;
  /** Ordinal value for fiManualPull. */
  public static final int FI_MANUAL_PULL = 10;
  /** Ordinal value for fiWaterFlow. */
  public static final int FI_WATER_FLOW = 11;
  /** Ordinal value for fiWaterFlowTamper. */
  public static final int FI_WATER_FLOW_TAMPER = 12;
  /** Ordinal value for fiStatusOnly. */
  public static final int FI_STATUS_ONLY = 13;
  /** Ordinal value for fiManualCall. */
  public static final int FI_MANUAL_CALL = 14;
  /** Ordinal value for fiFiremanCall. */
  public static final int FI_FIREMAN_CALL = 15;
  /** Ordinal value for fiUniveral. */
  public static final int FI_UNIVERAL = 16;
  /** Ordinal value for fiNul. */
  public static final int FI_NUL = -1;

  /** BLonFireInitEnum constant for fiUndefined. */
  public static final BLonFireInitEnum fiUndefined = new BLonFireInitEnum(FI_UNDEFINED);
  /** BLonFireInitEnum constant for fiThermalFixed. */
  public static final BLonFireInitEnum fiThermalFixed = new BLonFireInitEnum(FI_THERMAL_FIXED);
  /** BLonFireInitEnum constant for fiSmokeIon. */
  public static final BLonFireInitEnum fiSmokeIon = new BLonFireInitEnum(FI_SMOKE_ION);
  /** BLonFireInitEnum constant for fiMultiIonThermal. */
  public static final BLonFireInitEnum fiMultiIonThermal = new BLonFireInitEnum(FI_MULTI_ION_THERMAL);
  /** BLonFireInitEnum constant for fiSmokePhoto. */
  public static final BLonFireInitEnum fiSmokePhoto = new BLonFireInitEnum(FI_SMOKE_PHOTO);
  /** BLonFireInitEnum constant for fiMultiPhotoThermal. */
  public static final BLonFireInitEnum fiMultiPhotoThermal = new BLonFireInitEnum(FI_MULTI_PHOTO_THERMAL);
  /** BLonFireInitEnum constant for fiMultiPhotoIon. */
  public static final BLonFireInitEnum fiMultiPhotoIon = new BLonFireInitEnum(FI_MULTI_PHOTO_ION);
  /** BLonFireInitEnum constant for fiMultiPhotoIonThermal. */
  public static final BLonFireInitEnum fiMultiPhotoIonThermal = new BLonFireInitEnum(FI_MULTI_PHOTO_ION_THERMAL);
  /** BLonFireInitEnum constant for fiThermalRor. */
  public static final BLonFireInitEnum fiThermalRor = new BLonFireInitEnum(FI_THERMAL_ROR);
  /** BLonFireInitEnum constant for fiMultiThermalRor. */
  public static final BLonFireInitEnum fiMultiThermalRor = new BLonFireInitEnum(FI_MULTI_THERMAL_ROR);
  /** BLonFireInitEnum constant for fiManualPull. */
  public static final BLonFireInitEnum fiManualPull = new BLonFireInitEnum(FI_MANUAL_PULL);
  /** BLonFireInitEnum constant for fiWaterFlow. */
  public static final BLonFireInitEnum fiWaterFlow = new BLonFireInitEnum(FI_WATER_FLOW);
  /** BLonFireInitEnum constant for fiWaterFlowTamper. */
  public static final BLonFireInitEnum fiWaterFlowTamper = new BLonFireInitEnum(FI_WATER_FLOW_TAMPER);
  /** BLonFireInitEnum constant for fiStatusOnly. */
  public static final BLonFireInitEnum fiStatusOnly = new BLonFireInitEnum(FI_STATUS_ONLY);
  /** BLonFireInitEnum constant for fiManualCall. */
  public static final BLonFireInitEnum fiManualCall = new BLonFireInitEnum(FI_MANUAL_CALL);
  /** BLonFireInitEnum constant for fiFiremanCall. */
  public static final BLonFireInitEnum fiFiremanCall = new BLonFireInitEnum(FI_FIREMAN_CALL);
  /** BLonFireInitEnum constant for fiUniveral. */
  public static final BLonFireInitEnum fiUniveral = new BLonFireInitEnum(FI_UNIVERAL);
  /** BLonFireInitEnum constant for fiNul. */
  public static final BLonFireInitEnum fiNul = new BLonFireInitEnum(FI_NUL);

  /** Factory method with ordinal. */
  public static BLonFireInitEnum make(int ordinal)
  {
    return (BLonFireInitEnum)fiUndefined.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonFireInitEnum make(String tag)
  {
    return (BLonFireInitEnum)fiUndefined.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonFireInitEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonFireInitEnum DEFAULT = fiUndefined;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFireInitEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
