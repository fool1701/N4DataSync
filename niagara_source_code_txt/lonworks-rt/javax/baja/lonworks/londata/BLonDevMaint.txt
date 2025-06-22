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
 *   This class file represents SNVT_dev_maint.
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
  name = "pumpCtrlServiceRequired",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlBearingsChange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlBearingsLubricate",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlShaftsealChange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved147",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 1, 0, 4, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved207",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 8, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved307",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 8, null)")
)
@NiagaraProperty(
  name = "valvePosMotorMaint",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "valvePosPackingChange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "valvePosElectronicsCheck",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "valvePosPositioningCheck",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "valvePosLubricationCheck",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReturnCheck",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null)")
)
@NiagaraProperty(
  name = "valvePosBatteryCheck",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved17",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved207",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 8, null)")
)
@NiagaraProperty(
  name = "valvePosReserved306",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 1, 7, null)")
)
@NiagaraProperty(
  name = "valvePosGeneralMaint",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 0, 1, null)")
)
public class BLonDevMaint
  extends BLonData
{  
  /*
 <DevMaint type="XTypeDef">
  <elem n="deviceSelect" qual="e8" enumDef="DeviceSelectT"/>
  <elem n="pumpCtrlServiceRequired   " qual="ub byt=1 bit=7 len=1"/>
  <elem n="pumpCtrlBearingsChange    " qual="ub byt=1 bit=6 len=1"/>
  <elem n="pumpCtrlBearingsLubricate " qual="ub byt=1 bit=5 len=1"/>
  <elem n="pumpCtrlShaftsealChange   " qual="ub byt=1 bit=4 len=1"/>
  <elem n="pumpCtrlReserved147       " qual="ub byt=1 bit=0 len=4"/>
  <elem n="pumpCtrlReserved207       " qual="ub byt=2 bit=0 len=8"/>
  <elem n="pumpCtrlReserved307       " qual="ub byt=3 bit=0 len=8"/>
  <elem n="valvePosMotorMaint        " qual="ub byt=1 bit=7 len=1"/>
  <elem n="valvePosPackingChange     " qual="ub byt=1 bit=6 len=1"/>
  <elem n="valvePosElectronicsCheck  " qual="ub byt=1 bit=5 len=1"/>
  <elem n="valvePosPositioningCheck  " qual="ub byt=1 bit=4 len=1"/>
  <elem n="valvePosLubricationCheck  " qual="ub byt=1 bit=3 len=1"/>
  <elem n="valvePosReturnCheck       " qual="ub byt=1 bit=2 len=1"/>
  <elem n="valvePosBatteryCheck      " qual="ub byt=1 bit=1 len=1"/>
  <elem n="valvePosReserved17        " qual="ub byt=1 bit=0 len=1"/>
  <elem n="valvePosReserved207       " qual="ub byt=2 bit=0 len=8"/>
  <elem n="valvePosReserved306       " qual="ub byt=3 bit=1 len=7"/>
  <elem n="valvePosGeneralMaint      " qual="ub byt=3 bit=0 len=1"/>
  <typeScope v="0,175"/>
 </DevMaint>
  */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonDevMaint(2509166064)1.0$ @*/
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

  //region Property "pumpCtrlServiceRequired"

  /**
   * Slot for the {@code pumpCtrlServiceRequired} property.
   * @see #getPumpCtrlServiceRequired
   * @see #setPumpCtrlServiceRequired
   */
  public static final Property pumpCtrlServiceRequired = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code pumpCtrlServiceRequired} property.
   * @see #pumpCtrlServiceRequired
   */
  public BLonBoolean getPumpCtrlServiceRequired() { return (BLonBoolean)get(pumpCtrlServiceRequired); }

  /**
   * Set the {@code pumpCtrlServiceRequired} property.
   * @see #pumpCtrlServiceRequired
   */
  public void setPumpCtrlServiceRequired(BLonBoolean v) { set(pumpCtrlServiceRequired, v, null); }

  //endregion Property "pumpCtrlServiceRequired"

  //region Property "pumpCtrlBearingsChange"

  /**
   * Slot for the {@code pumpCtrlBearingsChange} property.
   * @see #getPumpCtrlBearingsChange
   * @see #setPumpCtrlBearingsChange
   */
  public static final Property pumpCtrlBearingsChange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code pumpCtrlBearingsChange} property.
   * @see #pumpCtrlBearingsChange
   */
  public BLonBoolean getPumpCtrlBearingsChange() { return (BLonBoolean)get(pumpCtrlBearingsChange); }

  /**
   * Set the {@code pumpCtrlBearingsChange} property.
   * @see #pumpCtrlBearingsChange
   */
  public void setPumpCtrlBearingsChange(BLonBoolean v) { set(pumpCtrlBearingsChange, v, null); }

  //endregion Property "pumpCtrlBearingsChange"

  //region Property "pumpCtrlBearingsLubricate"

  /**
   * Slot for the {@code pumpCtrlBearingsLubricate} property.
   * @see #getPumpCtrlBearingsLubricate
   * @see #setPumpCtrlBearingsLubricate
   */
  public static final Property pumpCtrlBearingsLubricate = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code pumpCtrlBearingsLubricate} property.
   * @see #pumpCtrlBearingsLubricate
   */
  public BLonBoolean getPumpCtrlBearingsLubricate() { return (BLonBoolean)get(pumpCtrlBearingsLubricate); }

  /**
   * Set the {@code pumpCtrlBearingsLubricate} property.
   * @see #pumpCtrlBearingsLubricate
   */
  public void setPumpCtrlBearingsLubricate(BLonBoolean v) { set(pumpCtrlBearingsLubricate, v, null); }

  //endregion Property "pumpCtrlBearingsLubricate"

  //region Property "pumpCtrlShaftsealChange"

  /**
   * Slot for the {@code pumpCtrlShaftsealChange} property.
   * @see #getPumpCtrlShaftsealChange
   * @see #setPumpCtrlShaftsealChange
   */
  public static final Property pumpCtrlShaftsealChange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code pumpCtrlShaftsealChange} property.
   * @see #pumpCtrlShaftsealChange
   */
  public BLonBoolean getPumpCtrlShaftsealChange() { return (BLonBoolean)get(pumpCtrlShaftsealChange); }

  /**
   * Set the {@code pumpCtrlShaftsealChange} property.
   * @see #pumpCtrlShaftsealChange
   */
  public void setPumpCtrlShaftsealChange(BLonBoolean v) { set(pumpCtrlShaftsealChange, v, null); }

  //endregion Property "pumpCtrlShaftsealChange"

  //region Property "pumpCtrlReserved147"

  /**
   * Slot for the {@code pumpCtrlReserved147} property.
   * @see #getPumpCtrlReserved147
   * @see #setPumpCtrlReserved147
   */
  public static final Property pumpCtrlReserved147 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 1, 0, 4, null));

  /**
   * Get the {@code pumpCtrlReserved147} property.
   * @see #pumpCtrlReserved147
   */
  public BLonFloat getPumpCtrlReserved147() { return (BLonFloat)get(pumpCtrlReserved147); }

  /**
   * Set the {@code pumpCtrlReserved147} property.
   * @see #pumpCtrlReserved147
   */
  public void setPumpCtrlReserved147(BLonFloat v) { set(pumpCtrlReserved147, v, null); }

  //endregion Property "pumpCtrlReserved147"

  //region Property "pumpCtrlReserved207"

  /**
   * Slot for the {@code pumpCtrlReserved207} property.
   * @see #getPumpCtrlReserved207
   * @see #setPumpCtrlReserved207
   */
  public static final Property pumpCtrlReserved207 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 8, null));

  /**
   * Get the {@code pumpCtrlReserved207} property.
   * @see #pumpCtrlReserved207
   */
  public BLonFloat getPumpCtrlReserved207() { return (BLonFloat)get(pumpCtrlReserved207); }

  /**
   * Set the {@code pumpCtrlReserved207} property.
   * @see #pumpCtrlReserved207
   */
  public void setPumpCtrlReserved207(BLonFloat v) { set(pumpCtrlReserved207, v, null); }

  //endregion Property "pumpCtrlReserved207"

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

  //region Property "valvePosMotorMaint"

  /**
   * Slot for the {@code valvePosMotorMaint} property.
   * @see #getValvePosMotorMaint
   * @see #setValvePosMotorMaint
   */
  public static final Property valvePosMotorMaint = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code valvePosMotorMaint} property.
   * @see #valvePosMotorMaint
   */
  public BLonBoolean getValvePosMotorMaint() { return (BLonBoolean)get(valvePosMotorMaint); }

  /**
   * Set the {@code valvePosMotorMaint} property.
   * @see #valvePosMotorMaint
   */
  public void setValvePosMotorMaint(BLonBoolean v) { set(valvePosMotorMaint, v, null); }

  //endregion Property "valvePosMotorMaint"

  //region Property "valvePosPackingChange"

  /**
   * Slot for the {@code valvePosPackingChange} property.
   * @see #getValvePosPackingChange
   * @see #setValvePosPackingChange
   */
  public static final Property valvePosPackingChange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code valvePosPackingChange} property.
   * @see #valvePosPackingChange
   */
  public BLonBoolean getValvePosPackingChange() { return (BLonBoolean)get(valvePosPackingChange); }

  /**
   * Set the {@code valvePosPackingChange} property.
   * @see #valvePosPackingChange
   */
  public void setValvePosPackingChange(BLonBoolean v) { set(valvePosPackingChange, v, null); }

  //endregion Property "valvePosPackingChange"

  //region Property "valvePosElectronicsCheck"

  /**
   * Slot for the {@code valvePosElectronicsCheck} property.
   * @see #getValvePosElectronicsCheck
   * @see #setValvePosElectronicsCheck
   */
  public static final Property valvePosElectronicsCheck = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code valvePosElectronicsCheck} property.
   * @see #valvePosElectronicsCheck
   */
  public BLonBoolean getValvePosElectronicsCheck() { return (BLonBoolean)get(valvePosElectronicsCheck); }

  /**
   * Set the {@code valvePosElectronicsCheck} property.
   * @see #valvePosElectronicsCheck
   */
  public void setValvePosElectronicsCheck(BLonBoolean v) { set(valvePosElectronicsCheck, v, null); }

  //endregion Property "valvePosElectronicsCheck"

  //region Property "valvePosPositioningCheck"

  /**
   * Slot for the {@code valvePosPositioningCheck} property.
   * @see #getValvePosPositioningCheck
   * @see #setValvePosPositioningCheck
   */
  public static final Property valvePosPositioningCheck = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code valvePosPositioningCheck} property.
   * @see #valvePosPositioningCheck
   */
  public BLonBoolean getValvePosPositioningCheck() { return (BLonBoolean)get(valvePosPositioningCheck); }

  /**
   * Set the {@code valvePosPositioningCheck} property.
   * @see #valvePosPositioningCheck
   */
  public void setValvePosPositioningCheck(BLonBoolean v) { set(valvePosPositioningCheck, v, null); }

  //endregion Property "valvePosPositioningCheck"

  //region Property "valvePosLubricationCheck"

  /**
   * Slot for the {@code valvePosLubricationCheck} property.
   * @see #getValvePosLubricationCheck
   * @see #setValvePosLubricationCheck
   */
  public static final Property valvePosLubricationCheck = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null));

  /**
   * Get the {@code valvePosLubricationCheck} property.
   * @see #valvePosLubricationCheck
   */
  public BLonBoolean getValvePosLubricationCheck() { return (BLonBoolean)get(valvePosLubricationCheck); }

  /**
   * Set the {@code valvePosLubricationCheck} property.
   * @see #valvePosLubricationCheck
   */
  public void setValvePosLubricationCheck(BLonBoolean v) { set(valvePosLubricationCheck, v, null); }

  //endregion Property "valvePosLubricationCheck"

  //region Property "valvePosReturnCheck"

  /**
   * Slot for the {@code valvePosReturnCheck} property.
   * @see #getValvePosReturnCheck
   * @see #setValvePosReturnCheck
   */
  public static final Property valvePosReturnCheck = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null));

  /**
   * Get the {@code valvePosReturnCheck} property.
   * @see #valvePosReturnCheck
   */
  public BLonBoolean getValvePosReturnCheck() { return (BLonBoolean)get(valvePosReturnCheck); }

  /**
   * Set the {@code valvePosReturnCheck} property.
   * @see #valvePosReturnCheck
   */
  public void setValvePosReturnCheck(BLonBoolean v) { set(valvePosReturnCheck, v, null); }

  //endregion Property "valvePosReturnCheck"

  //region Property "valvePosBatteryCheck"

  /**
   * Slot for the {@code valvePosBatteryCheck} property.
   * @see #getValvePosBatteryCheck
   * @see #setValvePosBatteryCheck
   */
  public static final Property valvePosBatteryCheck = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null));

  /**
   * Get the {@code valvePosBatteryCheck} property.
   * @see #valvePosBatteryCheck
   */
  public BLonBoolean getValvePosBatteryCheck() { return (BLonBoolean)get(valvePosBatteryCheck); }

  /**
   * Set the {@code valvePosBatteryCheck} property.
   * @see #valvePosBatteryCheck
   */
  public void setValvePosBatteryCheck(BLonBoolean v) { set(valvePosBatteryCheck, v, null); }

  //endregion Property "valvePosBatteryCheck"

  //region Property "valvePosReserved17"

  /**
   * Slot for the {@code valvePosReserved17} property.
   * @see #getValvePosReserved17
   * @see #setValvePosReserved17
   */
  public static final Property valvePosReserved17 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null));

  /**
   * Get the {@code valvePosReserved17} property.
   * @see #valvePosReserved17
   */
  public BLonBoolean getValvePosReserved17() { return (BLonBoolean)get(valvePosReserved17); }

  /**
   * Set the {@code valvePosReserved17} property.
   * @see #valvePosReserved17
   */
  public void setValvePosReserved17(BLonBoolean v) { set(valvePosReserved17, v, null); }

  //endregion Property "valvePosReserved17"

  //region Property "valvePosReserved207"

  /**
   * Slot for the {@code valvePosReserved207} property.
   * @see #getValvePosReserved207
   * @see #setValvePosReserved207
   */
  public static final Property valvePosReserved207 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 8, null));

  /**
   * Get the {@code valvePosReserved207} property.
   * @see #valvePosReserved207
   */
  public BLonFloat getValvePosReserved207() { return (BLonFloat)get(valvePosReserved207); }

  /**
   * Set the {@code valvePosReserved207} property.
   * @see #valvePosReserved207
   */
  public void setValvePosReserved207(BLonFloat v) { set(valvePosReserved207, v, null); }

  //endregion Property "valvePosReserved207"

  //region Property "valvePosReserved306"

  /**
   * Slot for the {@code valvePosReserved306} property.
   * @see #getValvePosReserved306
   * @see #setValvePosReserved306
   */
  public static final Property valvePosReserved306 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 1, 7, null));

  /**
   * Get the {@code valvePosReserved306} property.
   * @see #valvePosReserved306
   */
  public BLonFloat getValvePosReserved306() { return (BLonFloat)get(valvePosReserved306); }

  /**
   * Set the {@code valvePosReserved306} property.
   * @see #valvePosReserved306
   */
  public void setValvePosReserved306(BLonFloat v) { set(valvePosReserved306, v, null); }

  //endregion Property "valvePosReserved306"

  //region Property "valvePosGeneralMaint"

  /**
   * Slot for the {@code valvePosGeneralMaint} property.
   * @see #getValvePosGeneralMaint
   * @see #setValvePosGeneralMaint
   */
  public static final Property valvePosGeneralMaint = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 3, 0, 1, null));

  /**
   * Get the {@code valvePosGeneralMaint} property.
   * @see #valvePosGeneralMaint
   */
  public BLonBoolean getValvePosGeneralMaint() { return (BLonBoolean)get(valvePosGeneralMaint); }

  /**
   * Set the {@code valvePosGeneralMaint} property.
   * @see #valvePosGeneralMaint
   */
  public void setValvePosGeneralMaint(BLonBoolean v) { set(valvePosGeneralMaint, v, null); }

  //endregion Property "valvePosGeneralMaint"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDevMaint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(deviceSelect      , out);

    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveToOutputStream(pumpCtrlServiceRequired   , out);
      primitiveToOutputStream(pumpCtrlBearingsChange    , out);
      primitiveToOutputStream(pumpCtrlBearingsLubricate , out);
      primitiveToOutputStream(pumpCtrlShaftsealChange   , out);
      primitiveToOutputStream(pumpCtrlReserved147       , out);
      primitiveToOutputStream(pumpCtrlReserved207       , out);
      primitiveToOutputStream(pumpCtrlReserved307       , out);
    }  
    else
    {
      primitiveToOutputStream(valvePosMotorMaint       , out);
      primitiveToOutputStream(valvePosPackingChange    , out);
      primitiveToOutputStream(valvePosElectronicsCheck , out);
      primitiveToOutputStream(valvePosPositioningCheck , out);
      primitiveToOutputStream(valvePosLubricationCheck , out);
      primitiveToOutputStream(valvePosReturnCheck      , out);
      primitiveToOutputStream(valvePosBatteryCheck     , out);
      primitiveToOutputStream(valvePosReserved17       , out);
      primitiveToOutputStream(valvePosReserved207      , out);
      primitiveToOutputStream(valvePosReserved306      , out);
      primitiveToOutputStream(valvePosGeneralMaint     , out);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(deviceSelect     , in);
    
    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveFromInputStream(pumpCtrlServiceRequired   , in);
      primitiveFromInputStream(pumpCtrlBearingsChange    , in);
      primitiveFromInputStream(pumpCtrlBearingsLubricate , in);
      primitiveFromInputStream(pumpCtrlShaftsealChange   , in);
      primitiveFromInputStream(pumpCtrlReserved147       , in);
      primitiveFromInputStream(pumpCtrlReserved207       , in);
      primitiveFromInputStream(pumpCtrlReserved307       , in);
    }
    else
    {
      primitiveFromInputStream(valvePosMotorMaint       , in);
      primitiveFromInputStream(valvePosPackingChange    , in);
      primitiveFromInputStream(valvePosElectronicsCheck , in);
      primitiveFromInputStream(valvePosPositioningCheck , in);
      primitiveFromInputStream(valvePosLubricationCheck , in);
      primitiveFromInputStream(valvePosReturnCheck      , in);
      primitiveFromInputStream(valvePosBatteryCheck     , in);
      primitiveFromInputStream(valvePosReserved17       , in);
      primitiveFromInputStream(valvePosReserved207      , in);
      primitiveFromInputStream(valvePosReserved306      , in);
      primitiveFromInputStream(valvePosGeneralMaint     , in);
    }
  }  


}      
