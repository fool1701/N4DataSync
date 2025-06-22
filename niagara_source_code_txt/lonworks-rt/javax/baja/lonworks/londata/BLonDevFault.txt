/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonDeviceSelectEnum;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_dev_fault
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceSelect",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonDeviceSelectEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfVoltageLow",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfVoltageHigh",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfPhase",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfNoFluid",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfPressLow",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfPressHigh",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfReserved16",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSfReserved17",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfMotorTemp",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfMotorFailure",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfPumpBlocked",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 5, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfElectTemp",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfElectFailureNf",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfElectFailure",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfSensorFailure",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 1, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlDfReserved27",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 0, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved307",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 8, null)")
)
@NiagaraProperty(
  name = "valvePosDfValveBlocked",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfBlockedDirectionOpen",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfBlockedDirectionClose",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfPositionError",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfStrokeOutOfRange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfInitialization",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfVibrationCavitation",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null)")
)
@NiagaraProperty(
  name = "valvePosDfEdTooHigh",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved102",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 5, false, 0F, 3, null )")
)
@NiagaraProperty(
  name = "valvePosEeOscillating",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null)")
)
@NiagaraProperty(
  name = "valvePosEeValveTooLarge",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null)")
)
@NiagaraProperty(
  name = "valvePosEeValveTooSmall",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved267",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 0, false, 0F, 2, null )")
)
@NiagaraProperty(
  name = "valvePosReserved307",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 7, 1, null)")
)
@NiagaraProperty(
  name = "valvePosSfVoltageOutOfRange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 6, 1, null)")
)
@NiagaraProperty(
  name = "valvePosSfElectronicHighTemp",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 5, 1, null)")
)
@NiagaraProperty(
  name = "valvePosSfFrictionalResistance",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 4, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved446",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 3, 1, false, 0F,3, null )")
)
@NiagaraProperty(
  name = "valvePosGeneralFault",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 0, 1, null)")
)
public class BLonDevFault
  extends BLonData
{  
  /*
 <DevFault type="XTypeDef">
  <elem n="deviceSelect" qual="e8" enumDef="DeviceSelectT"/>
  <elem n="pumpCtrlSfVoltageLow           "qual="ub byt=1 bit=7 len=1"/>
  <elem n="pumpCtrlSfVoltageHigh          "qual="ub byt=1 bit=6 len=1"/>
  <elem n="pumpCtrlSfPhase                "qual="ub byt=1 bit=5 len=1"/>
  <elem n="pumpCtrlSfNoFluid              "qual="ub byt=1 bit=4 len=1"/>
  <elem n="pumpCtrlSfPressLow             "qual="ub byt=1 bit=3 len=1"/>
  <elem n="pumpCtrlSfPressHigh            "qual="ub byt=1 bit=2 len=1"/>
  <elem n="pumpCtrlSfReserved16           "qual="ub byt=1 bit=1 len=1"/>
  <elem n="pumpCtrlSfReserved17           "qual="ub byt=1 bit=0 len=1"/>
  <elem n="pumpCtrlDfMotorTemp            "qual="ub byt=2 bit=7 len=1"/>
  <elem n="pumpCtrlDfMotorFailure         "qual="ub byt=2 bit=6 len=1"/>
  <elem n="pumpCtrlDfPumpBlocked          "qual="ub byt=2 bit=5 len=1"/>
  <elem n="pumpCtrlDfElectTemp            "qual="ub byt=2 bit=4 len=1"/>
  <elem n="pumpCtrlDfElectFailureNf       "qual="ub byt=2 bit=3 len=1"/>
  <elem n="pumpCtrlDfElectFailure         "qual="ub byt=2 bit=2 len=1"/>
  <elem n="pumpCtrlDfSensorFailure        "qual="ub byt=2 bit=1 len=1"/>
  <elem n="pumpCtrlDfReserved27           "qual="ub byt=2 bit=0 len=1"/>
  <elem n="pumpCtrlReserved307            "qual="ub byt=3 bit=0 len=8"/>
  <elem n="valvePosDfValveBlocked         "qual="ub byt=1 bit=7 len=1"/>
  <elem n="valvePosDfBlockedDirectionOpen "qual="ub byt=1 bit=6 len=1"/>
  <elem n="valvePosDfBlockedDirectionClose"qual="ub byt=1 bit=5 len=1"/>
  <elem n="valvePosDfPositionError        "qual="ub byt=1 bit=4 len=1"/>
  <elem n="valvePosDfStrokeOutOfRange     "qual="ub byt=1 bit=3 len=1"/>
  <elem n="valvePosDfInitialization       "qual="ub byt=1 bit=2 len=1"/>
  <elem n="valvePosDfVibrationCavitation  "qual="ub byt=1 bit=1 len=1"/>
  <elem n="valvePosDfEdTooHigh            "qual="ub byt=1 bit=0 len=1"/>
  <elem n="valvePosReserved102            "qual="ub byt=2 bit=5 len=3 max=1.0"/>
  <elem n="valvePosEeOscillating          "qual="ub byt=2 bit=4 len=1"/>
  <elem n="valvePosEeValveTooLarge        "qual="ub byt=2 bit=3 len=1"/>
  <elem n="valvePosEeValveTooSmall        "qual="ub byt=2 bit=2 len=1"/>
  <elem n="valvePosReserved267            "qual="ub byt=2 bit=0 len=2 max=1.0"/>
  <elem n="valvePosReserved307            "qual="ub byt=3 bit=7 len=1"/>
  <elem n="valvePosSfVoltageOutOfRange    "qual="ub byt=3 bit=6 len=1"/>
  <elem n="valvePosSfElectronicHighTemp   "qual="ub byt=3 bit=5 len=1"/>
  <elem n="valvePosSfFrictionalResistance "qual="ub byt=3 bit=4 len=1"/>
  <elem n="valvePosReserved446            "qual="ub byt=3 bit=1 len=3 max=1.0"/>
  <elem n="valvePosGeneralFault           "qual="ub byt=3 bit=0 len=1"/>
  <typeScope v="0,174"/>
 </DevFault>
  */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonDevFault(667259071)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceSelect"

  /**
   * Slot for the {@code deviceSelect} property.
   * @see #getDeviceSelect
   * @see #setDeviceSelect
   */
  public static final Property deviceSelect = newProperty(0, BLonEnum.make(BLonDeviceSelectEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code deviceSelect} property.
   * @see #deviceSelect
   */
  public BLonEnum getDeviceSelect() { return (BLonEnum)get(deviceSelect); }

  /**
   * Set the {@code deviceSelect} property.
   * @see #deviceSelect
   */
  public void setDeviceSelect(BLonEnum v) { set(deviceSelect, v, null); }

  //endregion Property "deviceSelect"

  //region Property "pumpCtrlSfVoltageLow"

  /**
   * Slot for the {@code pumpCtrlSfVoltageLow} property.
   * @see #getPumpCtrlSfVoltageLow
   * @see #setPumpCtrlSfVoltageLow
   */
  public static final Property pumpCtrlSfVoltageLow = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code pumpCtrlSfVoltageLow} property.
   * @see #pumpCtrlSfVoltageLow
   */
  public BLonBoolean getPumpCtrlSfVoltageLow() { return (BLonBoolean)get(pumpCtrlSfVoltageLow); }

  /**
   * Set the {@code pumpCtrlSfVoltageLow} property.
   * @see #pumpCtrlSfVoltageLow
   */
  public void setPumpCtrlSfVoltageLow(BLonBoolean v) { set(pumpCtrlSfVoltageLow, v, null); }

  //endregion Property "pumpCtrlSfVoltageLow"

  //region Property "pumpCtrlSfVoltageHigh"

  /**
   * Slot for the {@code pumpCtrlSfVoltageHigh} property.
   * @see #getPumpCtrlSfVoltageHigh
   * @see #setPumpCtrlSfVoltageHigh
   */
  public static final Property pumpCtrlSfVoltageHigh = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code pumpCtrlSfVoltageHigh} property.
   * @see #pumpCtrlSfVoltageHigh
   */
  public BLonBoolean getPumpCtrlSfVoltageHigh() { return (BLonBoolean)get(pumpCtrlSfVoltageHigh); }

  /**
   * Set the {@code pumpCtrlSfVoltageHigh} property.
   * @see #pumpCtrlSfVoltageHigh
   */
  public void setPumpCtrlSfVoltageHigh(BLonBoolean v) { set(pumpCtrlSfVoltageHigh, v, null); }

  //endregion Property "pumpCtrlSfVoltageHigh"

  //region Property "pumpCtrlSfPhase"

  /**
   * Slot for the {@code pumpCtrlSfPhase} property.
   * @see #getPumpCtrlSfPhase
   * @see #setPumpCtrlSfPhase
   */
  public static final Property pumpCtrlSfPhase = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code pumpCtrlSfPhase} property.
   * @see #pumpCtrlSfPhase
   */
  public BLonBoolean getPumpCtrlSfPhase() { return (BLonBoolean)get(pumpCtrlSfPhase); }

  /**
   * Set the {@code pumpCtrlSfPhase} property.
   * @see #pumpCtrlSfPhase
   */
  public void setPumpCtrlSfPhase(BLonBoolean v) { set(pumpCtrlSfPhase, v, null); }

  //endregion Property "pumpCtrlSfPhase"

  //region Property "pumpCtrlSfNoFluid"

  /**
   * Slot for the {@code pumpCtrlSfNoFluid} property.
   * @see #getPumpCtrlSfNoFluid
   * @see #setPumpCtrlSfNoFluid
   */
  public static final Property pumpCtrlSfNoFluid = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code pumpCtrlSfNoFluid} property.
   * @see #pumpCtrlSfNoFluid
   */
  public BLonBoolean getPumpCtrlSfNoFluid() { return (BLonBoolean)get(pumpCtrlSfNoFluid); }

  /**
   * Set the {@code pumpCtrlSfNoFluid} property.
   * @see #pumpCtrlSfNoFluid
   */
  public void setPumpCtrlSfNoFluid(BLonBoolean v) { set(pumpCtrlSfNoFluid, v, null); }

  //endregion Property "pumpCtrlSfNoFluid"

  //region Property "pumpCtrlSfPressLow"

  /**
   * Slot for the {@code pumpCtrlSfPressLow} property.
   * @see #getPumpCtrlSfPressLow
   * @see #setPumpCtrlSfPressLow
   */
  public static final Property pumpCtrlSfPressLow = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null));

  /**
   * Get the {@code pumpCtrlSfPressLow} property.
   * @see #pumpCtrlSfPressLow
   */
  public BLonBoolean getPumpCtrlSfPressLow() { return (BLonBoolean)get(pumpCtrlSfPressLow); }

  /**
   * Set the {@code pumpCtrlSfPressLow} property.
   * @see #pumpCtrlSfPressLow
   */
  public void setPumpCtrlSfPressLow(BLonBoolean v) { set(pumpCtrlSfPressLow, v, null); }

  //endregion Property "pumpCtrlSfPressLow"

  //region Property "pumpCtrlSfPressHigh"

  /**
   * Slot for the {@code pumpCtrlSfPressHigh} property.
   * @see #getPumpCtrlSfPressHigh
   * @see #setPumpCtrlSfPressHigh
   */
  public static final Property pumpCtrlSfPressHigh = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null));

  /**
   * Get the {@code pumpCtrlSfPressHigh} property.
   * @see #pumpCtrlSfPressHigh
   */
  public BLonBoolean getPumpCtrlSfPressHigh() { return (BLonBoolean)get(pumpCtrlSfPressHigh); }

  /**
   * Set the {@code pumpCtrlSfPressHigh} property.
   * @see #pumpCtrlSfPressHigh
   */
  public void setPumpCtrlSfPressHigh(BLonBoolean v) { set(pumpCtrlSfPressHigh, v, null); }

  //endregion Property "pumpCtrlSfPressHigh"

  //region Property "pumpCtrlSfReserved16"

  /**
   * Slot for the {@code pumpCtrlSfReserved16} property.
   * @see #getPumpCtrlSfReserved16
   * @see #setPumpCtrlSfReserved16
   */
  public static final Property pumpCtrlSfReserved16 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null));

  /**
   * Get the {@code pumpCtrlSfReserved16} property.
   * @see #pumpCtrlSfReserved16
   */
  public BLonBoolean getPumpCtrlSfReserved16() { return (BLonBoolean)get(pumpCtrlSfReserved16); }

  /**
   * Set the {@code pumpCtrlSfReserved16} property.
   * @see #pumpCtrlSfReserved16
   */
  public void setPumpCtrlSfReserved16(BLonBoolean v) { set(pumpCtrlSfReserved16, v, null); }

  //endregion Property "pumpCtrlSfReserved16"

  //region Property "pumpCtrlSfReserved17"

  /**
   * Slot for the {@code pumpCtrlSfReserved17} property.
   * @see #getPumpCtrlSfReserved17
   * @see #setPumpCtrlSfReserved17
   */
  public static final Property pumpCtrlSfReserved17 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null));

  /**
   * Get the {@code pumpCtrlSfReserved17} property.
   * @see #pumpCtrlSfReserved17
   */
  public BLonBoolean getPumpCtrlSfReserved17() { return (BLonBoolean)get(pumpCtrlSfReserved17); }

  /**
   * Set the {@code pumpCtrlSfReserved17} property.
   * @see #pumpCtrlSfReserved17
   */
  public void setPumpCtrlSfReserved17(BLonBoolean v) { set(pumpCtrlSfReserved17, v, null); }

  //endregion Property "pumpCtrlSfReserved17"

  //region Property "pumpCtrlDfMotorTemp"

  /**
   * Slot for the {@code pumpCtrlDfMotorTemp} property.
   * @see #getPumpCtrlDfMotorTemp
   * @see #setPumpCtrlDfMotorTemp
   */
  public static final Property pumpCtrlDfMotorTemp = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null));

  /**
   * Get the {@code pumpCtrlDfMotorTemp} property.
   * @see #pumpCtrlDfMotorTemp
   */
  public BLonBoolean getPumpCtrlDfMotorTemp() { return (BLonBoolean)get(pumpCtrlDfMotorTemp); }

  /**
   * Set the {@code pumpCtrlDfMotorTemp} property.
   * @see #pumpCtrlDfMotorTemp
   */
  public void setPumpCtrlDfMotorTemp(BLonBoolean v) { set(pumpCtrlDfMotorTemp, v, null); }

  //endregion Property "pumpCtrlDfMotorTemp"

  //region Property "pumpCtrlDfMotorFailure"

  /**
   * Slot for the {@code pumpCtrlDfMotorFailure} property.
   * @see #getPumpCtrlDfMotorFailure
   * @see #setPumpCtrlDfMotorFailure
   */
  public static final Property pumpCtrlDfMotorFailure = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null));

  /**
   * Get the {@code pumpCtrlDfMotorFailure} property.
   * @see #pumpCtrlDfMotorFailure
   */
  public BLonBoolean getPumpCtrlDfMotorFailure() { return (BLonBoolean)get(pumpCtrlDfMotorFailure); }

  /**
   * Set the {@code pumpCtrlDfMotorFailure} property.
   * @see #pumpCtrlDfMotorFailure
   */
  public void setPumpCtrlDfMotorFailure(BLonBoolean v) { set(pumpCtrlDfMotorFailure, v, null); }

  //endregion Property "pumpCtrlDfMotorFailure"

  //region Property "pumpCtrlDfPumpBlocked"

  /**
   * Slot for the {@code pumpCtrlDfPumpBlocked} property.
   * @see #getPumpCtrlDfPumpBlocked
   * @see #setPumpCtrlDfPumpBlocked
   */
  public static final Property pumpCtrlDfPumpBlocked = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 5, 1, null));

  /**
   * Get the {@code pumpCtrlDfPumpBlocked} property.
   * @see #pumpCtrlDfPumpBlocked
   */
  public BLonBoolean getPumpCtrlDfPumpBlocked() { return (BLonBoolean)get(pumpCtrlDfPumpBlocked); }

  /**
   * Set the {@code pumpCtrlDfPumpBlocked} property.
   * @see #pumpCtrlDfPumpBlocked
   */
  public void setPumpCtrlDfPumpBlocked(BLonBoolean v) { set(pumpCtrlDfPumpBlocked, v, null); }

  //endregion Property "pumpCtrlDfPumpBlocked"

  //region Property "pumpCtrlDfElectTemp"

  /**
   * Slot for the {@code pumpCtrlDfElectTemp} property.
   * @see #getPumpCtrlDfElectTemp
   * @see #setPumpCtrlDfElectTemp
   */
  public static final Property pumpCtrlDfElectTemp = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null));

  /**
   * Get the {@code pumpCtrlDfElectTemp} property.
   * @see #pumpCtrlDfElectTemp
   */
  public BLonBoolean getPumpCtrlDfElectTemp() { return (BLonBoolean)get(pumpCtrlDfElectTemp); }

  /**
   * Set the {@code pumpCtrlDfElectTemp} property.
   * @see #pumpCtrlDfElectTemp
   */
  public void setPumpCtrlDfElectTemp(BLonBoolean v) { set(pumpCtrlDfElectTemp, v, null); }

  //endregion Property "pumpCtrlDfElectTemp"

  //region Property "pumpCtrlDfElectFailureNf"

  /**
   * Slot for the {@code pumpCtrlDfElectFailureNf} property.
   * @see #getPumpCtrlDfElectFailureNf
   * @see #setPumpCtrlDfElectFailureNf
   */
  public static final Property pumpCtrlDfElectFailureNf = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null));

  /**
   * Get the {@code pumpCtrlDfElectFailureNf} property.
   * @see #pumpCtrlDfElectFailureNf
   */
  public BLonBoolean getPumpCtrlDfElectFailureNf() { return (BLonBoolean)get(pumpCtrlDfElectFailureNf); }

  /**
   * Set the {@code pumpCtrlDfElectFailureNf} property.
   * @see #pumpCtrlDfElectFailureNf
   */
  public void setPumpCtrlDfElectFailureNf(BLonBoolean v) { set(pumpCtrlDfElectFailureNf, v, null); }

  //endregion Property "pumpCtrlDfElectFailureNf"

  //region Property "pumpCtrlDfElectFailure"

  /**
   * Slot for the {@code pumpCtrlDfElectFailure} property.
   * @see #getPumpCtrlDfElectFailure
   * @see #setPumpCtrlDfElectFailure
   */
  public static final Property pumpCtrlDfElectFailure = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null));

  /**
   * Get the {@code pumpCtrlDfElectFailure} property.
   * @see #pumpCtrlDfElectFailure
   */
  public BLonBoolean getPumpCtrlDfElectFailure() { return (BLonBoolean)get(pumpCtrlDfElectFailure); }

  /**
   * Set the {@code pumpCtrlDfElectFailure} property.
   * @see #pumpCtrlDfElectFailure
   */
  public void setPumpCtrlDfElectFailure(BLonBoolean v) { set(pumpCtrlDfElectFailure, v, null); }

  //endregion Property "pumpCtrlDfElectFailure"

  //region Property "pumpCtrlDfSensorFailure"

  /**
   * Slot for the {@code pumpCtrlDfSensorFailure} property.
   * @see #getPumpCtrlDfSensorFailure
   * @see #setPumpCtrlDfSensorFailure
   */
  public static final Property pumpCtrlDfSensorFailure = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 1, 1, null));

  /**
   * Get the {@code pumpCtrlDfSensorFailure} property.
   * @see #pumpCtrlDfSensorFailure
   */
  public BLonBoolean getPumpCtrlDfSensorFailure() { return (BLonBoolean)get(pumpCtrlDfSensorFailure); }

  /**
   * Set the {@code pumpCtrlDfSensorFailure} property.
   * @see #pumpCtrlDfSensorFailure
   */
  public void setPumpCtrlDfSensorFailure(BLonBoolean v) { set(pumpCtrlDfSensorFailure, v, null); }

  //endregion Property "pumpCtrlDfSensorFailure"

  //region Property "pumpCtrlDfReserved27"

  /**
   * Slot for the {@code pumpCtrlDfReserved27} property.
   * @see #getPumpCtrlDfReserved27
   * @see #setPumpCtrlDfReserved27
   */
  public static final Property pumpCtrlDfReserved27 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 0, 1, null));

  /**
   * Get the {@code pumpCtrlDfReserved27} property.
   * @see #pumpCtrlDfReserved27
   */
  public BLonBoolean getPumpCtrlDfReserved27() { return (BLonBoolean)get(pumpCtrlDfReserved27); }

  /**
   * Set the {@code pumpCtrlDfReserved27} property.
   * @see #pumpCtrlDfReserved27
   */
  public void setPumpCtrlDfReserved27(BLonBoolean v) { set(pumpCtrlDfReserved27, v, null); }

  //endregion Property "pumpCtrlDfReserved27"

  //region Property "pumpCtrlReserved307"

  /**
   * Slot for the {@code pumpCtrlReserved307} property.
   * @see #getPumpCtrlReserved307
   * @see #setPumpCtrlReserved307
   */
  public static final Property pumpCtrlReserved307 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 8, null));

  /**
   * Get the {@code pumpCtrlReserved307} property.
   * @see #pumpCtrlReserved307
   */
  public BLonFloat getPumpCtrlReserved307() { return (BLonFloat)get(pumpCtrlReserved307); }

  /**
   * Set the {@code pumpCtrlReserved307} property.
   * @see #pumpCtrlReserved307
   */
  public void setPumpCtrlReserved307(BLonFloat v) { set(pumpCtrlReserved307, v, null); }

  //endregion Property "pumpCtrlReserved307"

  //region Property "valvePosDfValveBlocked"

  /**
   * Slot for the {@code valvePosDfValveBlocked} property.
   * @see #getValvePosDfValveBlocked
   * @see #setValvePosDfValveBlocked
   */
  public static final Property valvePosDfValveBlocked = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code valvePosDfValveBlocked} property.
   * @see #valvePosDfValveBlocked
   */
  public BLonBoolean getValvePosDfValveBlocked() { return (BLonBoolean)get(valvePosDfValveBlocked); }

  /**
   * Set the {@code valvePosDfValveBlocked} property.
   * @see #valvePosDfValveBlocked
   */
  public void setValvePosDfValveBlocked(BLonBoolean v) { set(valvePosDfValveBlocked, v, null); }

  //endregion Property "valvePosDfValveBlocked"

  //region Property "valvePosDfBlockedDirectionOpen"

  /**
   * Slot for the {@code valvePosDfBlockedDirectionOpen} property.
   * @see #getValvePosDfBlockedDirectionOpen
   * @see #setValvePosDfBlockedDirectionOpen
   */
  public static final Property valvePosDfBlockedDirectionOpen = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code valvePosDfBlockedDirectionOpen} property.
   * @see #valvePosDfBlockedDirectionOpen
   */
  public BLonBoolean getValvePosDfBlockedDirectionOpen() { return (BLonBoolean)get(valvePosDfBlockedDirectionOpen); }

  /**
   * Set the {@code valvePosDfBlockedDirectionOpen} property.
   * @see #valvePosDfBlockedDirectionOpen
   */
  public void setValvePosDfBlockedDirectionOpen(BLonBoolean v) { set(valvePosDfBlockedDirectionOpen, v, null); }

  //endregion Property "valvePosDfBlockedDirectionOpen"

  //region Property "valvePosDfBlockedDirectionClose"

  /**
   * Slot for the {@code valvePosDfBlockedDirectionClose} property.
   * @see #getValvePosDfBlockedDirectionClose
   * @see #setValvePosDfBlockedDirectionClose
   */
  public static final Property valvePosDfBlockedDirectionClose = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code valvePosDfBlockedDirectionClose} property.
   * @see #valvePosDfBlockedDirectionClose
   */
  public BLonBoolean getValvePosDfBlockedDirectionClose() { return (BLonBoolean)get(valvePosDfBlockedDirectionClose); }

  /**
   * Set the {@code valvePosDfBlockedDirectionClose} property.
   * @see #valvePosDfBlockedDirectionClose
   */
  public void setValvePosDfBlockedDirectionClose(BLonBoolean v) { set(valvePosDfBlockedDirectionClose, v, null); }

  //endregion Property "valvePosDfBlockedDirectionClose"

  //region Property "valvePosDfPositionError"

  /**
   * Slot for the {@code valvePosDfPositionError} property.
   * @see #getValvePosDfPositionError
   * @see #setValvePosDfPositionError
   */
  public static final Property valvePosDfPositionError = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code valvePosDfPositionError} property.
   * @see #valvePosDfPositionError
   */
  public BLonBoolean getValvePosDfPositionError() { return (BLonBoolean)get(valvePosDfPositionError); }

  /**
   * Set the {@code valvePosDfPositionError} property.
   * @see #valvePosDfPositionError
   */
  public void setValvePosDfPositionError(BLonBoolean v) { set(valvePosDfPositionError, v, null); }

  //endregion Property "valvePosDfPositionError"

  //region Property "valvePosDfStrokeOutOfRange"

  /**
   * Slot for the {@code valvePosDfStrokeOutOfRange} property.
   * @see #getValvePosDfStrokeOutOfRange
   * @see #setValvePosDfStrokeOutOfRange
   */
  public static final Property valvePosDfStrokeOutOfRange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null));

  /**
   * Get the {@code valvePosDfStrokeOutOfRange} property.
   * @see #valvePosDfStrokeOutOfRange
   */
  public BLonBoolean getValvePosDfStrokeOutOfRange() { return (BLonBoolean)get(valvePosDfStrokeOutOfRange); }

  /**
   * Set the {@code valvePosDfStrokeOutOfRange} property.
   * @see #valvePosDfStrokeOutOfRange
   */
  public void setValvePosDfStrokeOutOfRange(BLonBoolean v) { set(valvePosDfStrokeOutOfRange, v, null); }

  //endregion Property "valvePosDfStrokeOutOfRange"

  //region Property "valvePosDfInitialization"

  /**
   * Slot for the {@code valvePosDfInitialization} property.
   * @see #getValvePosDfInitialization
   * @see #setValvePosDfInitialization
   */
  public static final Property valvePosDfInitialization = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null));

  /**
   * Get the {@code valvePosDfInitialization} property.
   * @see #valvePosDfInitialization
   */
  public BLonBoolean getValvePosDfInitialization() { return (BLonBoolean)get(valvePosDfInitialization); }

  /**
   * Set the {@code valvePosDfInitialization} property.
   * @see #valvePosDfInitialization
   */
  public void setValvePosDfInitialization(BLonBoolean v) { set(valvePosDfInitialization, v, null); }

  //endregion Property "valvePosDfInitialization"

  //region Property "valvePosDfVibrationCavitation"

  /**
   * Slot for the {@code valvePosDfVibrationCavitation} property.
   * @see #getValvePosDfVibrationCavitation
   * @see #setValvePosDfVibrationCavitation
   */
  public static final Property valvePosDfVibrationCavitation = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null));

  /**
   * Get the {@code valvePosDfVibrationCavitation} property.
   * @see #valvePosDfVibrationCavitation
   */
  public BLonBoolean getValvePosDfVibrationCavitation() { return (BLonBoolean)get(valvePosDfVibrationCavitation); }

  /**
   * Set the {@code valvePosDfVibrationCavitation} property.
   * @see #valvePosDfVibrationCavitation
   */
  public void setValvePosDfVibrationCavitation(BLonBoolean v) { set(valvePosDfVibrationCavitation, v, null); }

  //endregion Property "valvePosDfVibrationCavitation"

  //region Property "valvePosDfEdTooHigh"

  /**
   * Slot for the {@code valvePosDfEdTooHigh} property.
   * @see #getValvePosDfEdTooHigh
   * @see #setValvePosDfEdTooHigh
   */
  public static final Property valvePosDfEdTooHigh = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null));

  /**
   * Get the {@code valvePosDfEdTooHigh} property.
   * @see #valvePosDfEdTooHigh
   */
  public BLonBoolean getValvePosDfEdTooHigh() { return (BLonBoolean)get(valvePosDfEdTooHigh); }

  /**
   * Set the {@code valvePosDfEdTooHigh} property.
   * @see #valvePosDfEdTooHigh
   */
  public void setValvePosDfEdTooHigh(BLonBoolean v) { set(valvePosDfEdTooHigh, v, null); }

  //endregion Property "valvePosDfEdTooHigh"

  //region Property "valvePosReserved102"

  /**
   * Slot for the {@code valvePosReserved102} property.
   * @see #getValvePosReserved102
   * @see #setValvePosReserved102
   */
  public static final Property valvePosReserved102 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 5, false, 0F, 3, null ));

  /**
   * Get the {@code valvePosReserved102} property.
   * @see #valvePosReserved102
   */
  public BLonFloat getValvePosReserved102() { return (BLonFloat)get(valvePosReserved102); }

  /**
   * Set the {@code valvePosReserved102} property.
   * @see #valvePosReserved102
   */
  public void setValvePosReserved102(BLonFloat v) { set(valvePosReserved102, v, null); }

  //endregion Property "valvePosReserved102"

  //region Property "valvePosEeOscillating"

  /**
   * Slot for the {@code valvePosEeOscillating} property.
   * @see #getValvePosEeOscillating
   * @see #setValvePosEeOscillating
   */
  public static final Property valvePosEeOscillating = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null));

  /**
   * Get the {@code valvePosEeOscillating} property.
   * @see #valvePosEeOscillating
   */
  public BLonBoolean getValvePosEeOscillating() { return (BLonBoolean)get(valvePosEeOscillating); }

  /**
   * Set the {@code valvePosEeOscillating} property.
   * @see #valvePosEeOscillating
   */
  public void setValvePosEeOscillating(BLonBoolean v) { set(valvePosEeOscillating, v, null); }

  //endregion Property "valvePosEeOscillating"

  //region Property "valvePosEeValveTooLarge"

  /**
   * Slot for the {@code valvePosEeValveTooLarge} property.
   * @see #getValvePosEeValveTooLarge
   * @see #setValvePosEeValveTooLarge
   */
  public static final Property valvePosEeValveTooLarge = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null));

  /**
   * Get the {@code valvePosEeValveTooLarge} property.
   * @see #valvePosEeValveTooLarge
   */
  public BLonBoolean getValvePosEeValveTooLarge() { return (BLonBoolean)get(valvePosEeValveTooLarge); }

  /**
   * Set the {@code valvePosEeValveTooLarge} property.
   * @see #valvePosEeValveTooLarge
   */
  public void setValvePosEeValveTooLarge(BLonBoolean v) { set(valvePosEeValveTooLarge, v, null); }

  //endregion Property "valvePosEeValveTooLarge"

  //region Property "valvePosEeValveTooSmall"

  /**
   * Slot for the {@code valvePosEeValveTooSmall} property.
   * @see #getValvePosEeValveTooSmall
   * @see #setValvePosEeValveTooSmall
   */
  public static final Property valvePosEeValveTooSmall = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null));

  /**
   * Get the {@code valvePosEeValveTooSmall} property.
   * @see #valvePosEeValveTooSmall
   */
  public BLonBoolean getValvePosEeValveTooSmall() { return (BLonBoolean)get(valvePosEeValveTooSmall); }

  /**
   * Set the {@code valvePosEeValveTooSmall} property.
   * @see #valvePosEeValveTooSmall
   */
  public void setValvePosEeValveTooSmall(BLonBoolean v) { set(valvePosEeValveTooSmall, v, null); }

  //endregion Property "valvePosEeValveTooSmall"

  //region Property "valvePosReserved267"

  /**
   * Slot for the {@code valvePosReserved267} property.
   * @see #getValvePosReserved267
   * @see #setValvePosReserved267
   */
  public static final Property valvePosReserved267 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 0, false, 0F, 2, null ));

  /**
   * Get the {@code valvePosReserved267} property.
   * @see #valvePosReserved267
   */
  public BLonFloat getValvePosReserved267() { return (BLonFloat)get(valvePosReserved267); }

  /**
   * Set the {@code valvePosReserved267} property.
   * @see #valvePosReserved267
   */
  public void setValvePosReserved267(BLonFloat v) { set(valvePosReserved267, v, null); }

  //endregion Property "valvePosReserved267"

  //region Property "valvePosReserved307"

  /**
   * Slot for the {@code valvePosReserved307} property.
   * @see #getValvePosReserved307
   * @see #setValvePosReserved307
   */
  public static final Property valvePosReserved307 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 7, 1, null));

  /**
   * Get the {@code valvePosReserved307} property.
   * @see #valvePosReserved307
   */
  public BLonBoolean getValvePosReserved307() { return (BLonBoolean)get(valvePosReserved307); }

  /**
   * Set the {@code valvePosReserved307} property.
   * @see #valvePosReserved307
   */
  public void setValvePosReserved307(BLonBoolean v) { set(valvePosReserved307, v, null); }

  //endregion Property "valvePosReserved307"

  //region Property "valvePosSfVoltageOutOfRange"

  /**
   * Slot for the {@code valvePosSfVoltageOutOfRange} property.
   * @see #getValvePosSfVoltageOutOfRange
   * @see #setValvePosSfVoltageOutOfRange
   */
  public static final Property valvePosSfVoltageOutOfRange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 6, 1, null));

  /**
   * Get the {@code valvePosSfVoltageOutOfRange} property.
   * @see #valvePosSfVoltageOutOfRange
   */
  public BLonBoolean getValvePosSfVoltageOutOfRange() { return (BLonBoolean)get(valvePosSfVoltageOutOfRange); }

  /**
   * Set the {@code valvePosSfVoltageOutOfRange} property.
   * @see #valvePosSfVoltageOutOfRange
   */
  public void setValvePosSfVoltageOutOfRange(BLonBoolean v) { set(valvePosSfVoltageOutOfRange, v, null); }

  //endregion Property "valvePosSfVoltageOutOfRange"

  //region Property "valvePosSfElectronicHighTemp"

  /**
   * Slot for the {@code valvePosSfElectronicHighTemp} property.
   * @see #getValvePosSfElectronicHighTemp
   * @see #setValvePosSfElectronicHighTemp
   */
  public static final Property valvePosSfElectronicHighTemp = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 5, 1, null));

  /**
   * Get the {@code valvePosSfElectronicHighTemp} property.
   * @see #valvePosSfElectronicHighTemp
   */
  public BLonBoolean getValvePosSfElectronicHighTemp() { return (BLonBoolean)get(valvePosSfElectronicHighTemp); }

  /**
   * Set the {@code valvePosSfElectronicHighTemp} property.
   * @see #valvePosSfElectronicHighTemp
   */
  public void setValvePosSfElectronicHighTemp(BLonBoolean v) { set(valvePosSfElectronicHighTemp, v, null); }

  //endregion Property "valvePosSfElectronicHighTemp"

  //region Property "valvePosSfFrictionalResistance"

  /**
   * Slot for the {@code valvePosSfFrictionalResistance} property.
   * @see #getValvePosSfFrictionalResistance
   * @see #setValvePosSfFrictionalResistance
   */
  public static final Property valvePosSfFrictionalResistance = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 4, 1, null));

  /**
   * Get the {@code valvePosSfFrictionalResistance} property.
   * @see #valvePosSfFrictionalResistance
   */
  public BLonBoolean getValvePosSfFrictionalResistance() { return (BLonBoolean)get(valvePosSfFrictionalResistance); }

  /**
   * Set the {@code valvePosSfFrictionalResistance} property.
   * @see #valvePosSfFrictionalResistance
   */
  public void setValvePosSfFrictionalResistance(BLonBoolean v) { set(valvePosSfFrictionalResistance, v, null); }

  //endregion Property "valvePosSfFrictionalResistance"

  //region Property "valvePosReserved446"

  /**
   * Slot for the {@code valvePosReserved446} property.
   * @see #getValvePosReserved446
   * @see #setValvePosReserved446
   */
  public static final Property valvePosReserved446 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 3, 1, false, 0F,3, null ));

  /**
   * Get the {@code valvePosReserved446} property.
   * @see #valvePosReserved446
   */
  public BLonFloat getValvePosReserved446() { return (BLonFloat)get(valvePosReserved446); }

  /**
   * Set the {@code valvePosReserved446} property.
   * @see #valvePosReserved446
   */
  public void setValvePosReserved446(BLonFloat v) { set(valvePosReserved446, v, null); }

  //endregion Property "valvePosReserved446"

  //region Property "valvePosGeneralFault"

  /**
   * Slot for the {@code valvePosGeneralFault} property.
   * @see #getValvePosGeneralFault
   * @see #setValvePosGeneralFault
   */
  public static final Property valvePosGeneralFault = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 0, 1, null));

  /**
   * Get the {@code valvePosGeneralFault} property.
   * @see #valvePosGeneralFault
   */
  public BLonBoolean getValvePosGeneralFault() { return (BLonBoolean)get(valvePosGeneralFault); }

  /**
   * Set the {@code valvePosGeneralFault} property.
   * @see #valvePosGeneralFault
   */
  public void setValvePosGeneralFault(BLonBoolean v) { set(valvePosGeneralFault, v, null); }

  //endregion Property "valvePosGeneralFault"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDevFault.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
   
  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(deviceSelect      , out);

    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveToOutputStream(pumpCtrlSfVoltageLow      , out);
      primitiveToOutputStream(pumpCtrlSfVoltageHigh     , out);
      primitiveToOutputStream(pumpCtrlSfPhase           , out);
      primitiveToOutputStream(pumpCtrlSfNoFluid         , out);
      primitiveToOutputStream(pumpCtrlSfPressLow        , out);
      primitiveToOutputStream(pumpCtrlSfPressHigh       , out);
      primitiveToOutputStream(pumpCtrlSfReserved16      , out);
      primitiveToOutputStream(pumpCtrlSfReserved17      , out);
      primitiveToOutputStream(pumpCtrlDfMotorTemp       , out);
      primitiveToOutputStream(pumpCtrlDfMotorFailure    , out);
      primitiveToOutputStream(pumpCtrlDfPumpBlocked     , out);
      primitiveToOutputStream(pumpCtrlDfElectTemp       , out);
      primitiveToOutputStream(pumpCtrlDfElectFailureNf  , out);
      primitiveToOutputStream(pumpCtrlDfElectFailure    , out);
      primitiveToOutputStream(pumpCtrlDfSensorFailure   , out);
      primitiveToOutputStream(pumpCtrlDfReserved27      , out);
      primitiveToOutputStream(pumpCtrlReserved307       , out);
    }  
    else
    {
      primitiveToOutputStream(valvePosDfValveBlocked            , out);
      primitiveToOutputStream(valvePosDfBlockedDirectionOpen    , out);
      primitiveToOutputStream(valvePosDfBlockedDirectionClose   , out);
      primitiveToOutputStream(valvePosDfPositionError           , out);
      primitiveToOutputStream(valvePosDfStrokeOutOfRange        , out);
      primitiveToOutputStream(valvePosDfInitialization          , out);
      primitiveToOutputStream(valvePosDfVibrationCavitation     , out);
      primitiveToOutputStream(valvePosDfEdTooHigh               , out);
      primitiveToOutputStream(valvePosReserved102               , out);
      primitiveToOutputStream(valvePosEeOscillating             , out);
      primitiveToOutputStream(valvePosEeValveTooLarge           , out);
      primitiveToOutputStream(valvePosEeValveTooSmall           , out);
      primitiveToOutputStream(valvePosReserved267               , out);
      primitiveToOutputStream(valvePosReserved307               , out);
      primitiveToOutputStream(valvePosSfVoltageOutOfRange       , out);
      primitiveToOutputStream(valvePosSfElectronicHighTemp      , out);
      primitiveToOutputStream(valvePosSfFrictionalResistance    , out);
      primitiveToOutputStream(valvePosReserved446               , out);
      primitiveToOutputStream(valvePosGeneralFault              , out);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(deviceSelect     , in);
    
    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveFromInputStream(pumpCtrlSfVoltageLow      , in);
      primitiveFromInputStream(pumpCtrlSfVoltageHigh     , in);
      primitiveFromInputStream(pumpCtrlSfPhase           , in);
      primitiveFromInputStream(pumpCtrlSfNoFluid         , in);
      primitiveFromInputStream(pumpCtrlSfPressLow        , in);
      primitiveFromInputStream(pumpCtrlSfPressHigh       , in);
      primitiveFromInputStream(pumpCtrlSfReserved16      , in);
      primitiveFromInputStream(pumpCtrlSfReserved17      , in);
      primitiveFromInputStream(pumpCtrlDfMotorTemp       , in);
      primitiveFromInputStream(pumpCtrlDfMotorFailure    , in);
      primitiveFromInputStream(pumpCtrlDfPumpBlocked     , in);
      primitiveFromInputStream(pumpCtrlDfElectTemp       , in);
      primitiveFromInputStream(pumpCtrlDfElectFailureNf  , in);
      primitiveFromInputStream(pumpCtrlDfElectFailure    , in);
      primitiveFromInputStream(pumpCtrlDfSensorFailure   , in);
      primitiveFromInputStream(pumpCtrlDfReserved27      , in);
      primitiveFromInputStream(pumpCtrlReserved307       , in);
    }
    else
    {
      primitiveFromInputStream(valvePosDfValveBlocked             , in);
      primitiveFromInputStream(valvePosDfBlockedDirectionOpen     , in);
      primitiveFromInputStream(valvePosDfBlockedDirectionClose    , in);
      primitiveFromInputStream(valvePosDfPositionError            , in);
      primitiveFromInputStream(valvePosDfStrokeOutOfRange         , in);
      primitiveFromInputStream(valvePosDfInitialization           , in);
      primitiveFromInputStream(valvePosDfVibrationCavitation      , in);
      primitiveFromInputStream(valvePosDfEdTooHigh                , in);
      primitiveFromInputStream(valvePosReserved102                , in);
      primitiveFromInputStream(valvePosEeOscillating              , in);
      primitiveFromInputStream(valvePosEeValveTooLarge            , in);
      primitiveFromInputStream(valvePosEeValveTooSmall            , in);
      primitiveFromInputStream(valvePosReserved267                , in);
      primitiveFromInputStream(valvePosReserved307                , in);
      primitiveFromInputStream(valvePosSfVoltageOutOfRange        , in);
      primitiveFromInputStream(valvePosSfElectronicHighTemp       , in);
      primitiveFromInputStream(valvePosSfFrictionalResistance     , in);
      primitiveFromInputStream(valvePosReserved446                , in);
      primitiveFromInputStream(valvePosGeneralFault               , in);
    }
  }  


}      
