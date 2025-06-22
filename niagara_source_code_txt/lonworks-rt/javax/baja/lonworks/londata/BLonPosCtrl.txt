/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonCamActEnum;
import javax.baja.lonworks.enums.BLonCamFuncEnum;
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
import javax.baja.units.UnitDatabase;


/**
 *   This class file represents SNVT_pos_ctrl.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "receiverId",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, null)")
)
@NiagaraProperty(
  name = "controllerId",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, null)")
)
@NiagaraProperty(
  name = "controllerPrio",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 100, 1, null)")
)
@NiagaraProperty(
  name = "camFunction",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonCamFuncEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "camAction",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonCamActEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "valueNumber",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 7)")
)
@NiagaraProperty(
  name = "valueAbsposPan",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.s16, -359.97998F, 360.0F, 0.02F, 32767.0F, 7, 0, UnitDatabase.getUnit(\"degrees angular\"))")
)
@NiagaraProperty(
  name = "valueAbsposTilt",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.s16, -359.97998F, 360.0F, 0.02F, 32767.0F, 9, 0, UnitDatabase.getUnit(\"degrees angular\"))")
)
@NiagaraProperty(
  name = "valueAbsposZoom",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.s16, -163.84F, 163.83F, 0.0050F, 32767.0F, 11, 0, UnitDatabase.getUnit(\"percent\"))")
)
public class BLonPosCtrl
  extends BLonData
{  
  /*
    <elem n="receiverId     " qual="u16"/>
    <elem n="controllerId   " qual="u16"/>
    <elem n="controllerPrio " qual="u8 max=100.0"/>
    <elem n="function       " qual="e8" enumDef="CamFuncT"/>
    <elem n="action         " qual="e8" enumDef="CamActT"/>
    <elem n="valueNumber    " qual="u8 byt=7"/>
    <elem n="valueAbsposPan " qual="ref type=AngleDeg byt=7"/>
    <elem n="valueAbsposTilt" qual="ref type=AngleDeg byt=9"/>
    <elem n="valueAbsposZoom" qual="ref type=LevPercent byt=11"/>

 <AngleDeg type="XTypeDef">
  <elem n="angleDeg" qual="s16 res=0.02 min=-359.97998 max=360.0 invld=32767.0" engUnit="degrees angular"/>
  <typeScope v="0,104"/>
 </AngleDeg>
 
 <LevPercent type="XTypeDef">
  <elem n="levPercent" qual="s16 res=0.0050 min=-163.84 max=163.83 invld=32767.0" engUnit="percent"/>
  <typeScope v="0,81"/>
 </LevPercent>

  */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonPosCtrl(1969407562)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "receiverId"

  /**
   * Slot for the {@code receiverId} property.
   * @see #getReceiverId
   * @see #setReceiverId
   */
  public static final Property receiverId = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, null));

  /**
   * Get the {@code receiverId} property.
   * @see #receiverId
   */
  public BLonFloat getReceiverId() { return (BLonFloat)get(receiverId); }

  /**
   * Set the {@code receiverId} property.
   * @see #receiverId
   */
  public void setReceiverId(BLonFloat v) { set(receiverId, v, null); }

  //endregion Property "receiverId"

  //region Property "controllerId"

  /**
   * Slot for the {@code controllerId} property.
   * @see #getControllerId
   * @see #setControllerId
   */
  public static final Property controllerId = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, null));

  /**
   * Get the {@code controllerId} property.
   * @see #controllerId
   */
  public BLonFloat getControllerId() { return (BLonFloat)get(controllerId); }

  /**
   * Set the {@code controllerId} property.
   * @see #controllerId
   */
  public void setControllerId(BLonFloat v) { set(controllerId, v, null); }

  //endregion Property "controllerId"

  //region Property "controllerPrio"

  /**
   * Slot for the {@code controllerPrio} property.
   * @see #getControllerPrio
   * @see #setControllerPrio
   */
  public static final Property controllerPrio = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 100, 1, null));

  /**
   * Get the {@code controllerPrio} property.
   * @see #controllerPrio
   */
  public BLonFloat getControllerPrio() { return (BLonFloat)get(controllerPrio); }

  /**
   * Set the {@code controllerPrio} property.
   * @see #controllerPrio
   */
  public void setControllerPrio(BLonFloat v) { set(controllerPrio, v, null); }

  //endregion Property "controllerPrio"

  //region Property "camFunction"

  /**
   * Slot for the {@code camFunction} property.
   * @see #getCamFunction
   * @see #setCamFunction
   */
  public static final Property camFunction = newProperty(0, BLonEnum.make(BLonCamFuncEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code camFunction} property.
   * @see #camFunction
   */
  public BLonEnum getCamFunction() { return (BLonEnum)get(camFunction); }

  /**
   * Set the {@code camFunction} property.
   * @see #camFunction
   */
  public void setCamFunction(BLonEnum v) { set(camFunction, v, null); }

  //endregion Property "camFunction"

  //region Property "camAction"

  /**
   * Slot for the {@code camAction} property.
   * @see #getCamAction
   * @see #setCamAction
   */
  public static final Property camAction = newProperty(0, BLonEnum.make(BLonCamActEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code camAction} property.
   * @see #camAction
   */
  public BLonEnum getCamAction() { return (BLonEnum)get(camAction); }

  /**
   * Set the {@code camAction} property.
   * @see #camAction
   */
  public void setCamAction(BLonEnum v) { set(camAction, v, null); }

  //endregion Property "camAction"

  //region Property "valueNumber"

  /**
   * Slot for the {@code valueNumber} property.
   * @see #getValueNumber
   * @see #setValueNumber
   */
  public static final Property valueNumber = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 7));

  /**
   * Get the {@code valueNumber} property.
   * @see #valueNumber
   */
  public BLonFloat getValueNumber() { return (BLonFloat)get(valueNumber); }

  /**
   * Set the {@code valueNumber} property.
   * @see #valueNumber
   */
  public void setValueNumber(BLonFloat v) { set(valueNumber, v, null); }

  //endregion Property "valueNumber"

  //region Property "valueAbsposPan"

  /**
   * Slot for the {@code valueAbsposPan} property.
   * @see #getValueAbsposPan
   * @see #setValueAbsposPan
   */
  public static final Property valueAbsposPan = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.s16, -359.97998F, 360.0F, 0.02F, 32767.0F, 7, 0, UnitDatabase.getUnit("degrees angular")));

  /**
   * Get the {@code valueAbsposPan} property.
   * @see #valueAbsposPan
   */
  public BLonFloat getValueAbsposPan() { return (BLonFloat)get(valueAbsposPan); }

  /**
   * Set the {@code valueAbsposPan} property.
   * @see #valueAbsposPan
   */
  public void setValueAbsposPan(BLonFloat v) { set(valueAbsposPan, v, null); }

  //endregion Property "valueAbsposPan"

  //region Property "valueAbsposTilt"

  /**
   * Slot for the {@code valueAbsposTilt} property.
   * @see #getValueAbsposTilt
   * @see #setValueAbsposTilt
   */
  public static final Property valueAbsposTilt = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.s16, -359.97998F, 360.0F, 0.02F, 32767.0F, 9, 0, UnitDatabase.getUnit("degrees angular")));

  /**
   * Get the {@code valueAbsposTilt} property.
   * @see #valueAbsposTilt
   */
  public BLonFloat getValueAbsposTilt() { return (BLonFloat)get(valueAbsposTilt); }

  /**
   * Set the {@code valueAbsposTilt} property.
   * @see #valueAbsposTilt
   */
  public void setValueAbsposTilt(BLonFloat v) { set(valueAbsposTilt, v, null); }

  //endregion Property "valueAbsposTilt"

  //region Property "valueAbsposZoom"

  /**
   * Slot for the {@code valueAbsposZoom} property.
   * @see #getValueAbsposZoom
   * @see #setValueAbsposZoom
   */
  public static final Property valueAbsposZoom = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.s16, -163.84F, 163.83F, 0.0050F, 32767.0F, 11, 0, UnitDatabase.getUnit("percent")));

  /**
   * Get the {@code valueAbsposZoom} property.
   * @see #valueAbsposZoom
   */
  public BLonFloat getValueAbsposZoom() { return (BLonFloat)get(valueAbsposZoom); }

  /**
   * Set the {@code valueAbsposZoom} property.
   * @see #valueAbsposZoom
   */
  public void setValueAbsposZoom(BLonFloat v) { set(valueAbsposZoom, v, null); }

  //endregion Property "valueAbsposZoom"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPosCtrl.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(receiverId      , out);
    primitiveToOutputStream(controllerId    , out);
    primitiveToOutputStream(controllerPrio  , out);
    primitiveToOutputStream(camFunction     , out);
    primitiveToOutputStream(camAction       , out);

    int f = getCamFunction().getEnum().getOrdinal();
    if(f == BLonCamFuncEnum.CMF_ABS)
    {
      primitiveToOutputStream(valueAbsposPan  , out);
      primitiveToOutputStream(valueAbsposTilt , out);
      primitiveToOutputStream(valueAbsposZoom , out);
    
    }  
    else
    {
      primitiveToOutputStream(valueNumber     , out);
      out.writeUnsigned8(0);
      out.writeUnsigned16(0);
      out.writeUnsigned16(0);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(receiverId     , in);
    primitiveFromInputStream(controllerId   , in);
    primitiveFromInputStream(controllerPrio , in);
    primitiveFromInputStream(camFunction    , in);
    primitiveFromInputStream(camAction      , in);
    
    int f = getCamFunction().getEnum().getOrdinal();
    if(f == BLonCamFuncEnum.CMF_ABS)
    {
      primitiveFromInputStream(valueAbsposPan , in);
      primitiveFromInputStream(valueAbsposTilt, in);
      primitiveFromInputStream(valueAbsposZoom, in);
    }
    else
    {
      primitiveFromInputStream(valueNumber    , in);
    }
  }  


}      
