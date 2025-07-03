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
 *   This class file represents SNVT_dev_status.
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
  name = "pumpCtrlDeviceFault",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSupplyFault",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved12",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSpeedLow",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSpeedHigh",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved15",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlSetptOutOfRange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved17",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlLocalControl",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved21",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlRunning",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 5, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved23",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlRemotePress",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlRemoteFlow",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlRemoteTemp",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 1, 1, null)")
)
@NiagaraProperty(
  name = "pumpCtrlReserved27",
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
  name = "valvePosRunning",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null)")
)
@NiagaraProperty(
  name = "valvePosAdapting",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null)")
)
@NiagaraProperty(
  name = "valvePosInitializing",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null)")
)
@NiagaraProperty(
  name = "valvePosLocalControl",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null)")
)
@NiagaraProperty(
  name = "valvePosSetptOutOfRange",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null)")
)
@NiagaraProperty(
  name = "valvePosRemoteCtrlSignal",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved167",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 1, 0, false, 0F, 2, null)")
)
@NiagaraProperty(
  name = "valvePosHwEmergency",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null)")
)
@NiagaraProperty(
  name = "valvePosSwEmergency",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null)")
)
@NiagaraProperty(
  name = "valvePosReserved227",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 0, false, 0F, 6, null)")
)
@NiagaraProperty(
  name = "valvePosReserved307",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 8, null)")
)
public class BLonDevStatus
  extends BLonData
{  
  /*
 <DevStatus type="XTypeDef">
  <elem n="deviceSelect" qual="e8" enumDef="DeviceSelectT"/>
  <elem n="pumpCtrlDeviceFault      " qual="ub byt=1 bit=7 len=1"/>
  <elem n="pumpCtrlSupplyFault      " qual="ub byt=1 bit=6 len=1"/>
  <elem n="pumpCtrlReserved12       " qual="ub byt=1 bit=5 len=1"/>
  <elem n="pumpCtrlSpeedLow         " qual="ub byt=1 bit=4 len=1"/>
  <elem n="pumpCtrlSpeedHigh        " qual="ub byt=1 bit=3 len=1"/>
  <elem n="pumpCtrlReserved15       " qual="ub byt=1 bit=2 len=1"/>
  <elem n="pumpCtrlSetptOutOfRange  " qual="ub byt=1 bit=1 len=1"/>
  <elem n="pumpCtrlReserved17       " qual="ub byt=1 bit=0 len=1"/>
  <elem n="pumpCtrlLocalControl     " qual="ub byt=2 bit=7 len=1"/>
  <elem n="pumpCtrlReserved21       " qual="ub byt=2 bit=6 len=1"/>
  <elem n="pumpCtrlRunning          " qual="ub byt=2 bit=5 len=1"/>
  <elem n="pumpCtrlReserved23       " qual="ub byt=2 bit=4 len=1"/>
  <elem n="pumpCtrlRemotePress      " qual="ub byt=2 bit=3 len=1"/>
  <elem n="pumpCtrlRemoteFlow       " qual="ub byt=2 bit=2 len=1"/>
  <elem n="pumpCtrlRemoteTemp       " qual="ub byt=2 bit=1 len=1"/>
  <elem n="pumpCtrlReserved27       " qual="ub byt=2 bit=0 len=1"/>
  <elem n="pumpCtrlReserved307      " qual="ub byt=3 bit=0 len=8"/>
  <elem n="valvePosRunning          " qual="ub byt=1 bit=7 len=1"/>
  <elem n="valvePosAdapting         " qual="ub byt=1 bit=6 len=1"/>
  <elem n="valvePosInitializing     " qual="ub byt=1 bit=5 len=1"/>
  <elem n="valvePosLocalControl     " qual="ub byt=1 bit=4 len=1"/>
  <elem n="valvePosSetptOutOfRange  " qual="ub byt=1 bit=3 len=1"/>
  <elem n="valvePosRemoteCtrlSignal " qual="ub byt=1 bit=2 len=1"/>
  <elem n="valvePosReserved167      " qual="ub byt=1 bit=0 len=2 max=1.0"/>
  <elem n="valvePosHwEmergency      " qual="ub byt=2 bit=7 len=1"/>
  <elem n="valvePosSwEmergency      " qual="ub byt=2 bit=6 len=1"/>
  <elem n="valvePosReserved227      " qual="ub byt=2 bit=0 len=6 max=1.0"/>
  <elem n="valvePosReserved307      " qual="ub byt=3 bit=0 len=8"/>
  <typeScope v="0,173"/>
 </DevStatus>
  */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonDevStatus(1642219609)1.0$ @*/
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

  //region Property "pumpCtrlDeviceFault"

  /**
   * Slot for the {@code pumpCtrlDeviceFault} property.
   * @see #getPumpCtrlDeviceFault
   * @see #setPumpCtrlDeviceFault
   */
  public static final Property pumpCtrlDeviceFault = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code pumpCtrlDeviceFault} property.
   * @see #pumpCtrlDeviceFault
   */
  public BLonBoolean getPumpCtrlDeviceFault() { return (BLonBoolean)get(pumpCtrlDeviceFault); }

  /**
   * Set the {@code pumpCtrlDeviceFault} property.
   * @see #pumpCtrlDeviceFault
   */
  public void setPumpCtrlDeviceFault(BLonBoolean v) { set(pumpCtrlDeviceFault, v, null); }

  //endregion Property "pumpCtrlDeviceFault"

  //region Property "pumpCtrlSupplyFault"

  /**
   * Slot for the {@code pumpCtrlSupplyFault} property.
   * @see #getPumpCtrlSupplyFault
   * @see #setPumpCtrlSupplyFault
   */
  public static final Property pumpCtrlSupplyFault = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code pumpCtrlSupplyFault} property.
   * @see #pumpCtrlSupplyFault
   */
  public BLonBoolean getPumpCtrlSupplyFault() { return (BLonBoolean)get(pumpCtrlSupplyFault); }

  /**
   * Set the {@code pumpCtrlSupplyFault} property.
   * @see #pumpCtrlSupplyFault
   */
  public void setPumpCtrlSupplyFault(BLonBoolean v) { set(pumpCtrlSupplyFault, v, null); }

  //endregion Property "pumpCtrlSupplyFault"

  //region Property "pumpCtrlReserved12"

  /**
   * Slot for the {@code pumpCtrlReserved12} property.
   * @see #getPumpCtrlReserved12
   * @see #setPumpCtrlReserved12
   */
  public static final Property pumpCtrlReserved12 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code pumpCtrlReserved12} property.
   * @see #pumpCtrlReserved12
   */
  public BLonBoolean getPumpCtrlReserved12() { return (BLonBoolean)get(pumpCtrlReserved12); }

  /**
   * Set the {@code pumpCtrlReserved12} property.
   * @see #pumpCtrlReserved12
   */
  public void setPumpCtrlReserved12(BLonBoolean v) { set(pumpCtrlReserved12, v, null); }

  //endregion Property "pumpCtrlReserved12"

  //region Property "pumpCtrlSpeedLow"

  /**
   * Slot for the {@code pumpCtrlSpeedLow} property.
   * @see #getPumpCtrlSpeedLow
   * @see #setPumpCtrlSpeedLow
   */
  public static final Property pumpCtrlSpeedLow = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code pumpCtrlSpeedLow} property.
   * @see #pumpCtrlSpeedLow
   */
  public BLonBoolean getPumpCtrlSpeedLow() { return (BLonBoolean)get(pumpCtrlSpeedLow); }

  /**
   * Set the {@code pumpCtrlSpeedLow} property.
   * @see #pumpCtrlSpeedLow
   */
  public void setPumpCtrlSpeedLow(BLonBoolean v) { set(pumpCtrlSpeedLow, v, null); }

  //endregion Property "pumpCtrlSpeedLow"

  //region Property "pumpCtrlSpeedHigh"

  /**
   * Slot for the {@code pumpCtrlSpeedHigh} property.
   * @see #getPumpCtrlSpeedHigh
   * @see #setPumpCtrlSpeedHigh
   */
  public static final Property pumpCtrlSpeedHigh = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null));

  /**
   * Get the {@code pumpCtrlSpeedHigh} property.
   * @see #pumpCtrlSpeedHigh
   */
  public BLonBoolean getPumpCtrlSpeedHigh() { return (BLonBoolean)get(pumpCtrlSpeedHigh); }

  /**
   * Set the {@code pumpCtrlSpeedHigh} property.
   * @see #pumpCtrlSpeedHigh
   */
  public void setPumpCtrlSpeedHigh(BLonBoolean v) { set(pumpCtrlSpeedHigh, v, null); }

  //endregion Property "pumpCtrlSpeedHigh"

  //region Property "pumpCtrlReserved15"

  /**
   * Slot for the {@code pumpCtrlReserved15} property.
   * @see #getPumpCtrlReserved15
   * @see #setPumpCtrlReserved15
   */
  public static final Property pumpCtrlReserved15 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null));

  /**
   * Get the {@code pumpCtrlReserved15} property.
   * @see #pumpCtrlReserved15
   */
  public BLonBoolean getPumpCtrlReserved15() { return (BLonBoolean)get(pumpCtrlReserved15); }

  /**
   * Set the {@code pumpCtrlReserved15} property.
   * @see #pumpCtrlReserved15
   */
  public void setPumpCtrlReserved15(BLonBoolean v) { set(pumpCtrlReserved15, v, null); }

  //endregion Property "pumpCtrlReserved15"

  //region Property "pumpCtrlSetptOutOfRange"

  /**
   * Slot for the {@code pumpCtrlSetptOutOfRange} property.
   * @see #getPumpCtrlSetptOutOfRange
   * @see #setPumpCtrlSetptOutOfRange
   */
  public static final Property pumpCtrlSetptOutOfRange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 1, 1, null));

  /**
   * Get the {@code pumpCtrlSetptOutOfRange} property.
   * @see #pumpCtrlSetptOutOfRange
   */
  public BLonBoolean getPumpCtrlSetptOutOfRange() { return (BLonBoolean)get(pumpCtrlSetptOutOfRange); }

  /**
   * Set the {@code pumpCtrlSetptOutOfRange} property.
   * @see #pumpCtrlSetptOutOfRange
   */
  public void setPumpCtrlSetptOutOfRange(BLonBoolean v) { set(pumpCtrlSetptOutOfRange, v, null); }

  //endregion Property "pumpCtrlSetptOutOfRange"

  //region Property "pumpCtrlReserved17"

  /**
   * Slot for the {@code pumpCtrlReserved17} property.
   * @see #getPumpCtrlReserved17
   * @see #setPumpCtrlReserved17
   */
  public static final Property pumpCtrlReserved17 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 0, 1, null));

  /**
   * Get the {@code pumpCtrlReserved17} property.
   * @see #pumpCtrlReserved17
   */
  public BLonBoolean getPumpCtrlReserved17() { return (BLonBoolean)get(pumpCtrlReserved17); }

  /**
   * Set the {@code pumpCtrlReserved17} property.
   * @see #pumpCtrlReserved17
   */
  public void setPumpCtrlReserved17(BLonBoolean v) { set(pumpCtrlReserved17, v, null); }

  //endregion Property "pumpCtrlReserved17"

  //region Property "pumpCtrlLocalControl"

  /**
   * Slot for the {@code pumpCtrlLocalControl} property.
   * @see #getPumpCtrlLocalControl
   * @see #setPumpCtrlLocalControl
   */
  public static final Property pumpCtrlLocalControl = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null));

  /**
   * Get the {@code pumpCtrlLocalControl} property.
   * @see #pumpCtrlLocalControl
   */
  public BLonBoolean getPumpCtrlLocalControl() { return (BLonBoolean)get(pumpCtrlLocalControl); }

  /**
   * Set the {@code pumpCtrlLocalControl} property.
   * @see #pumpCtrlLocalControl
   */
  public void setPumpCtrlLocalControl(BLonBoolean v) { set(pumpCtrlLocalControl, v, null); }

  //endregion Property "pumpCtrlLocalControl"

  //region Property "pumpCtrlReserved21"

  /**
   * Slot for the {@code pumpCtrlReserved21} property.
   * @see #getPumpCtrlReserved21
   * @see #setPumpCtrlReserved21
   */
  public static final Property pumpCtrlReserved21 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null));

  /**
   * Get the {@code pumpCtrlReserved21} property.
   * @see #pumpCtrlReserved21
   */
  public BLonBoolean getPumpCtrlReserved21() { return (BLonBoolean)get(pumpCtrlReserved21); }

  /**
   * Set the {@code pumpCtrlReserved21} property.
   * @see #pumpCtrlReserved21
   */
  public void setPumpCtrlReserved21(BLonBoolean v) { set(pumpCtrlReserved21, v, null); }

  //endregion Property "pumpCtrlReserved21"

  //region Property "pumpCtrlRunning"

  /**
   * Slot for the {@code pumpCtrlRunning} property.
   * @see #getPumpCtrlRunning
   * @see #setPumpCtrlRunning
   */
  public static final Property pumpCtrlRunning = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 5, 1, null));

  /**
   * Get the {@code pumpCtrlRunning} property.
   * @see #pumpCtrlRunning
   */
  public BLonBoolean getPumpCtrlRunning() { return (BLonBoolean)get(pumpCtrlRunning); }

  /**
   * Set the {@code pumpCtrlRunning} property.
   * @see #pumpCtrlRunning
   */
  public void setPumpCtrlRunning(BLonBoolean v) { set(pumpCtrlRunning, v, null); }

  //endregion Property "pumpCtrlRunning"

  //region Property "pumpCtrlReserved23"

  /**
   * Slot for the {@code pumpCtrlReserved23} property.
   * @see #getPumpCtrlReserved23
   * @see #setPumpCtrlReserved23
   */
  public static final Property pumpCtrlReserved23 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 4, 1, null));

  /**
   * Get the {@code pumpCtrlReserved23} property.
   * @see #pumpCtrlReserved23
   */
  public BLonBoolean getPumpCtrlReserved23() { return (BLonBoolean)get(pumpCtrlReserved23); }

  /**
   * Set the {@code pumpCtrlReserved23} property.
   * @see #pumpCtrlReserved23
   */
  public void setPumpCtrlReserved23(BLonBoolean v) { set(pumpCtrlReserved23, v, null); }

  //endregion Property "pumpCtrlReserved23"

  //region Property "pumpCtrlRemotePress"

  /**
   * Slot for the {@code pumpCtrlRemotePress} property.
   * @see #getPumpCtrlRemotePress
   * @see #setPumpCtrlRemotePress
   */
  public static final Property pumpCtrlRemotePress = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 3, 1, null));

  /**
   * Get the {@code pumpCtrlRemotePress} property.
   * @see #pumpCtrlRemotePress
   */
  public BLonBoolean getPumpCtrlRemotePress() { return (BLonBoolean)get(pumpCtrlRemotePress); }

  /**
   * Set the {@code pumpCtrlRemotePress} property.
   * @see #pumpCtrlRemotePress
   */
  public void setPumpCtrlRemotePress(BLonBoolean v) { set(pumpCtrlRemotePress, v, null); }

  //endregion Property "pumpCtrlRemotePress"

  //region Property "pumpCtrlRemoteFlow"

  /**
   * Slot for the {@code pumpCtrlRemoteFlow} property.
   * @see #getPumpCtrlRemoteFlow
   * @see #setPumpCtrlRemoteFlow
   */
  public static final Property pumpCtrlRemoteFlow = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 2, 1, null));

  /**
   * Get the {@code pumpCtrlRemoteFlow} property.
   * @see #pumpCtrlRemoteFlow
   */
  public BLonBoolean getPumpCtrlRemoteFlow() { return (BLonBoolean)get(pumpCtrlRemoteFlow); }

  /**
   * Set the {@code pumpCtrlRemoteFlow} property.
   * @see #pumpCtrlRemoteFlow
   */
  public void setPumpCtrlRemoteFlow(BLonBoolean v) { set(pumpCtrlRemoteFlow, v, null); }

  //endregion Property "pumpCtrlRemoteFlow"

  //region Property "pumpCtrlRemoteTemp"

  /**
   * Slot for the {@code pumpCtrlRemoteTemp} property.
   * @see #getPumpCtrlRemoteTemp
   * @see #setPumpCtrlRemoteTemp
   */
  public static final Property pumpCtrlRemoteTemp = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 1, 1, null));

  /**
   * Get the {@code pumpCtrlRemoteTemp} property.
   * @see #pumpCtrlRemoteTemp
   */
  public BLonBoolean getPumpCtrlRemoteTemp() { return (BLonBoolean)get(pumpCtrlRemoteTemp); }

  /**
   * Set the {@code pumpCtrlRemoteTemp} property.
   * @see #pumpCtrlRemoteTemp
   */
  public void setPumpCtrlRemoteTemp(BLonBoolean v) { set(pumpCtrlRemoteTemp, v, null); }

  //endregion Property "pumpCtrlRemoteTemp"

  //region Property "pumpCtrlReserved27"

  /**
   * Slot for the {@code pumpCtrlReserved27} property.
   * @see #getPumpCtrlReserved27
   * @see #setPumpCtrlReserved27
   */
  public static final Property pumpCtrlReserved27 = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 0, 1, null));

  /**
   * Get the {@code pumpCtrlReserved27} property.
   * @see #pumpCtrlReserved27
   */
  public BLonBoolean getPumpCtrlReserved27() { return (BLonBoolean)get(pumpCtrlReserved27); }

  /**
   * Set the {@code pumpCtrlReserved27} property.
   * @see #pumpCtrlReserved27
   */
  public void setPumpCtrlReserved27(BLonBoolean v) { set(pumpCtrlReserved27, v, null); }

  //endregion Property "pumpCtrlReserved27"

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

  //region Property "valvePosRunning"

  /**
   * Slot for the {@code valvePosRunning} property.
   * @see #getValvePosRunning
   * @see #setValvePosRunning
   */
  public static final Property valvePosRunning = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 7, 1, null));

  /**
   * Get the {@code valvePosRunning} property.
   * @see #valvePosRunning
   */
  public BLonBoolean getValvePosRunning() { return (BLonBoolean)get(valvePosRunning); }

  /**
   * Set the {@code valvePosRunning} property.
   * @see #valvePosRunning
   */
  public void setValvePosRunning(BLonBoolean v) { set(valvePosRunning, v, null); }

  //endregion Property "valvePosRunning"

  //region Property "valvePosAdapting"

  /**
   * Slot for the {@code valvePosAdapting} property.
   * @see #getValvePosAdapting
   * @see #setValvePosAdapting
   */
  public static final Property valvePosAdapting = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 6, 1, null));

  /**
   * Get the {@code valvePosAdapting} property.
   * @see #valvePosAdapting
   */
  public BLonBoolean getValvePosAdapting() { return (BLonBoolean)get(valvePosAdapting); }

  /**
   * Set the {@code valvePosAdapting} property.
   * @see #valvePosAdapting
   */
  public void setValvePosAdapting(BLonBoolean v) { set(valvePosAdapting, v, null); }

  //endregion Property "valvePosAdapting"

  //region Property "valvePosInitializing"

  /**
   * Slot for the {@code valvePosInitializing} property.
   * @see #getValvePosInitializing
   * @see #setValvePosInitializing
   */
  public static final Property valvePosInitializing = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 5, 1, null));

  /**
   * Get the {@code valvePosInitializing} property.
   * @see #valvePosInitializing
   */
  public BLonBoolean getValvePosInitializing() { return (BLonBoolean)get(valvePosInitializing); }

  /**
   * Set the {@code valvePosInitializing} property.
   * @see #valvePosInitializing
   */
  public void setValvePosInitializing(BLonBoolean v) { set(valvePosInitializing, v, null); }

  //endregion Property "valvePosInitializing"

  //region Property "valvePosLocalControl"

  /**
   * Slot for the {@code valvePosLocalControl} property.
   * @see #getValvePosLocalControl
   * @see #setValvePosLocalControl
   */
  public static final Property valvePosLocalControl = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 4, 1, null));

  /**
   * Get the {@code valvePosLocalControl} property.
   * @see #valvePosLocalControl
   */
  public BLonBoolean getValvePosLocalControl() { return (BLonBoolean)get(valvePosLocalControl); }

  /**
   * Set the {@code valvePosLocalControl} property.
   * @see #valvePosLocalControl
   */
  public void setValvePosLocalControl(BLonBoolean v) { set(valvePosLocalControl, v, null); }

  //endregion Property "valvePosLocalControl"

  //region Property "valvePosSetptOutOfRange"

  /**
   * Slot for the {@code valvePosSetptOutOfRange} property.
   * @see #getValvePosSetptOutOfRange
   * @see #setValvePosSetptOutOfRange
   */
  public static final Property valvePosSetptOutOfRange = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 3, 1, null));

  /**
   * Get the {@code valvePosSetptOutOfRange} property.
   * @see #valvePosSetptOutOfRange
   */
  public BLonBoolean getValvePosSetptOutOfRange() { return (BLonBoolean)get(valvePosSetptOutOfRange); }

  /**
   * Set the {@code valvePosSetptOutOfRange} property.
   * @see #valvePosSetptOutOfRange
   */
  public void setValvePosSetptOutOfRange(BLonBoolean v) { set(valvePosSetptOutOfRange, v, null); }

  //endregion Property "valvePosSetptOutOfRange"

  //region Property "valvePosRemoteCtrlSignal"

  /**
   * Slot for the {@code valvePosRemoteCtrlSignal} property.
   * @see #getValvePosRemoteCtrlSignal
   * @see #setValvePosRemoteCtrlSignal
   */
  public static final Property valvePosRemoteCtrlSignal = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 1, 2, 1, null));

  /**
   * Get the {@code valvePosRemoteCtrlSignal} property.
   * @see #valvePosRemoteCtrlSignal
   */
  public BLonBoolean getValvePosRemoteCtrlSignal() { return (BLonBoolean)get(valvePosRemoteCtrlSignal); }

  /**
   * Set the {@code valvePosRemoteCtrlSignal} property.
   * @see #valvePosRemoteCtrlSignal
   */
  public void setValvePosRemoteCtrlSignal(BLonBoolean v) { set(valvePosRemoteCtrlSignal, v, null); }

  //endregion Property "valvePosRemoteCtrlSignal"

  //region Property "valvePosReserved167"

  /**
   * Slot for the {@code valvePosReserved167} property.
   * @see #getValvePosReserved167
   * @see #setValvePosReserved167
   */
  public static final Property valvePosReserved167 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 1, 0, false, 0F, 2, null));

  /**
   * Get the {@code valvePosReserved167} property.
   * @see #valvePosReserved167
   */
  public BLonFloat getValvePosReserved167() { return (BLonFloat)get(valvePosReserved167); }

  /**
   * Set the {@code valvePosReserved167} property.
   * @see #valvePosReserved167
   */
  public void setValvePosReserved167(BLonFloat v) { set(valvePosReserved167, v, null); }

  //endregion Property "valvePosReserved167"

  //region Property "valvePosHwEmergency"

  /**
   * Slot for the {@code valvePosHwEmergency} property.
   * @see #getValvePosHwEmergency
   * @see #setValvePosHwEmergency
   */
  public static final Property valvePosHwEmergency = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 7, 1, null));

  /**
   * Get the {@code valvePosHwEmergency} property.
   * @see #valvePosHwEmergency
   */
  public BLonBoolean getValvePosHwEmergency() { return (BLonBoolean)get(valvePosHwEmergency); }

  /**
   * Set the {@code valvePosHwEmergency} property.
   * @see #valvePosHwEmergency
   */
  public void setValvePosHwEmergency(BLonBoolean v) { set(valvePosHwEmergency, v, null); }

  //endregion Property "valvePosHwEmergency"

  //region Property "valvePosSwEmergency"

  /**
   * Slot for the {@code valvePosSwEmergency} property.
   * @see #getValvePosSwEmergency
   * @see #setValvePosSwEmergency
   */
  public static final Property valvePosSwEmergency = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 2, 6, 1, null));

  /**
   * Get the {@code valvePosSwEmergency} property.
   * @see #valvePosSwEmergency
   */
  public BLonBoolean getValvePosSwEmergency() { return (BLonBoolean)get(valvePosSwEmergency); }

  /**
   * Set the {@code valvePosSwEmergency} property.
   * @see #valvePosSwEmergency
   */
  public void setValvePosSwEmergency(BLonBoolean v) { set(valvePosSwEmergency, v, null); }

  //endregion Property "valvePosSwEmergency"

  //region Property "valvePosReserved227"

  /**
   * Slot for the {@code valvePosReserved227} property.
   * @see #getValvePosReserved227
   * @see #setValvePosReserved227
   */
  public static final Property valvePosReserved227 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 1, 1F, 0F, true, 2, 0, false, 0F, 6, null));

  /**
   * Get the {@code valvePosReserved227} property.
   * @see #valvePosReserved227
   */
  public BLonFloat getValvePosReserved227() { return (BLonFloat)get(valvePosReserved227); }

  /**
   * Set the {@code valvePosReserved227} property.
   * @see #valvePosReserved227
   */
  public void setValvePosReserved227(BLonFloat v) { set(valvePosReserved227, v, null); }

  //endregion Property "valvePosReserved227"

  //region Property "valvePosReserved307"

  /**
   * Slot for the {@code valvePosReserved307} property.
   * @see #getValvePosReserved307
   * @see #setValvePosReserved307
   */
  public static final Property valvePosReserved307 = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 8, null));

  /**
   * Get the {@code valvePosReserved307} property.
   * @see #valvePosReserved307
   */
  public BLonFloat getValvePosReserved307() { return (BLonFloat)get(valvePosReserved307); }

  /**
   * Set the {@code valvePosReserved307} property.
   * @see #valvePosReserved307
   */
  public void setValvePosReserved307(BLonFloat v) { set(valvePosReserved307, v, null); }

  //endregion Property "valvePosReserved307"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDevStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
   
  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(deviceSelect      , out);

    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveToOutputStream(pumpCtrlDeviceFault         , out);
      primitiveToOutputStream(pumpCtrlSupplyFault         , out);
      primitiveToOutputStream(pumpCtrlReserved12          , out);
      primitiveToOutputStream(pumpCtrlSpeedLow            , out);
      primitiveToOutputStream(pumpCtrlSpeedHigh           , out);
      primitiveToOutputStream(pumpCtrlReserved15          , out);
      primitiveToOutputStream(pumpCtrlSetptOutOfRange     , out);
      primitiveToOutputStream(pumpCtrlReserved17          , out);
      primitiveToOutputStream(pumpCtrlLocalControl        , out);
      primitiveToOutputStream(pumpCtrlReserved21          , out);
      primitiveToOutputStream(pumpCtrlRunning             , out);
      primitiveToOutputStream(pumpCtrlReserved23          , out);
      primitiveToOutputStream(pumpCtrlRemotePress         , out);
      primitiveToOutputStream(pumpCtrlRemoteFlow          , out);
      primitiveToOutputStream(pumpCtrlRemoteTemp          , out);
      primitiveToOutputStream(pumpCtrlReserved27          , out);
      primitiveToOutputStream(pumpCtrlReserved307         , out);
    }  
    else
    {
      primitiveToOutputStream(valvePosRunning               , out);
      primitiveToOutputStream(valvePosAdapting              , out);
      primitiveToOutputStream(valvePosInitializing          , out);
      primitiveToOutputStream(valvePosLocalControl          , out);
      primitiveToOutputStream(valvePosSetptOutOfRange       , out);
      primitiveToOutputStream(valvePosRemoteCtrlSignal      , out);
      primitiveToOutputStream(valvePosReserved167           , out);
      primitiveToOutputStream(valvePosHwEmergency           , out);
      primitiveToOutputStream(valvePosSwEmergency           , out);
      primitiveToOutputStream(valvePosReserved227           , out);
      primitiveToOutputStream(valvePosReserved307           , out);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(deviceSelect     , in);
    
    int f = getDeviceSelect().getEnum().getOrdinal();
    if(f == BLonDeviceSelectEnum.DV_PUMP_CTRL)
    {
      primitiveFromInputStream(pumpCtrlDeviceFault    , in);
      primitiveFromInputStream(pumpCtrlSupplyFault    , in);
      primitiveFromInputStream(pumpCtrlReserved12     , in);
      primitiveFromInputStream(pumpCtrlSpeedLow       , in);
      primitiveFromInputStream(pumpCtrlSpeedHigh      , in);
      primitiveFromInputStream(pumpCtrlReserved15     , in);
      primitiveFromInputStream(pumpCtrlSetptOutOfRange, in);
      primitiveFromInputStream(pumpCtrlReserved17     , in);
      primitiveFromInputStream(pumpCtrlLocalControl   , in);
      primitiveFromInputStream(pumpCtrlReserved21     , in);
      primitiveFromInputStream(pumpCtrlRunning        , in);
      primitiveFromInputStream(pumpCtrlReserved23     , in);
      primitiveFromInputStream(pumpCtrlRemotePress    , in);
      primitiveFromInputStream(pumpCtrlRemoteFlow     , in);
      primitiveFromInputStream(pumpCtrlRemoteTemp     , in);
      primitiveFromInputStream(pumpCtrlReserved27     , in);
      primitiveFromInputStream(pumpCtrlReserved307    , in);
    }
    else
    {
      primitiveFromInputStream(valvePosRunning            , in);
      primitiveFromInputStream(valvePosAdapting           , in);
      primitiveFromInputStream(valvePosInitializing       , in);
      primitiveFromInputStream(valvePosLocalControl       , in);
      primitiveFromInputStream(valvePosSetptOutOfRange    , in);
      primitiveFromInputStream(valvePosRemoteCtrlSignal   , in);
      primitiveFromInputStream(valvePosReserved167        , in);
      primitiveFromInputStream(valvePosHwEmergency        , in);
      primitiveFromInputStream(valvePosSwEmergency        , in);
      primitiveFromInputStream(valvePosReserved227        , in);
      primitiveFromInputStream(valvePosReserved307        , in);
    }
  }  


}      
