/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.enums.BLonRailAudioSensorTypeEnum;
import javax.baja.lonworks.enums.BLonRailAudioTypeEnum;
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
 *   This class file represents SNVT_rac_ctrl.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "audioLine",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 0, 5, 3,  null)")
)
@NiagaraProperty(
  name = "duplexFull",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 0, 4, 1,  null)")
)
@NiagaraProperty(
  name = "destP2P",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.bb, 0, 3, 1,  null)")
)
@NiagaraProperty(
  name = "reserved",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 0, 0, 3,  null)")
)
@NiagaraProperty(
  name = "audioType",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonRailAudioTypeEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "addrInitUnitId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 8, 1F, 0F, true, 2, 4, false, 0F, 4, null)")
)
@NiagaraProperty(
  name = "addrInitLocation",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 4,  null)")
)
@NiagaraProperty(
  name = "addrInitCarId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 3, 5,  null)")
)
@NiagaraProperty(
  name = "addrInitReserved",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 3,  null)")
)
@NiagaraProperty(
  name = "addrInitAudioSensorType",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "addrTalkUnitId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 5, 4, 4,  null)")
)
@NiagaraProperty(
  name = "addrTalkLocation",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 5, 0, 4,  null)")
)
@NiagaraProperty(
  name = "addrTalkCarId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 6, 3, 5,  null)")
)
@NiagaraProperty(
  name = "addrTalkReserved",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 6, 0, 3,  null)")
)
@NiagaraProperty(
  name = "addrTalkAudioSensorType",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "addrDestP2PUnitId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 8, 4, 4,  null)")
)
@NiagaraProperty(
  name = "addrDestP2PLocation",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 8, 0, 4,  null)")
)
@NiagaraProperty(
  name = "addrDestP2PCarId",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 9, 3, 5,  null)")
)
@NiagaraProperty(
  name = "addrDestP2PReserved",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub, 9, 0, 3,  null)")
)
@NiagaraProperty(
  name = "addrDestP2PAudioSensorType",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "addrDestP2MMaskUnit",
  type = "BLonFloat",
  defaultValue = "BLonFloat  .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 8, null)")
)
@NiagaraProperty(
  name = "addrDestP2MMaskCar",
  type = "BLonString",
  defaultValue = "BLonString .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.st, 4, null)")
)
@NiagaraProperty(
  name = "addrDestP2MMaskLocation",
  type = "BLonString",
  defaultValue = "BLonString .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.st, 2, null)")
)
@NiagaraProperty(
  name = "addrDestP2MMaskAudio",
  type = "BLonString",
  defaultValue = "BLonString .DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.st, 3, null)")
)
public class BLonRacCtrl
  extends BLonData
{  
  /*
 <RacCtrl type="XTypeDef">
  <elem n="audioLine                  " qual="ub byt=0 bit=5 len=3 max=7.0"/>
  <elem n="duplexFull                 " qual="ub byt=0 bit=4 len=1"/>
  <elem n="destP2P                    " qual="ub byt=0 bit=3 len=1"/>
  <elem n="reserved                   " qual="ub byt=0 bit=0 len=3"/>
  <elem n="audioType                  " qual="e8" enumDef="RailAudioTypeT"/>
  <elem n="addrInitUnitId             " qual="ub byt=2 bit=4 len=4 max=8.0"/>
  <elem n="addrInitLocation           " qual="ub byt=2 bit=0 len=4"/>
  <elem n="addrInitCarId              " qual="ub byt=3 bit=3 len=5"/>
  <elem n="addrInitReserved           " qual="ub byt=3 bit=0 len=3"/>
  <elem n="addrInitAudioSensorType    " qual="e8" enumDef="RailAudioSensorTypeT"/>
  <elem n="addrTalkUnitId             " qual="ub byt=5 bit=4 len=4"/>
  <elem n="addrTalkLocation           " qual="ub byt=5 bit=0 len=4"/>
  <elem n="addrTalkCarId              " qual="ub byt=6 bit=3 len=5"/>
  <elem n="addrTalkReserved           " qual="ub byt=6 bit=0 len=3"/>
  <elem n="addrTalkAudioSensorType    " qual="e8" enumDef="RailAudioSensorTypeT"/>
  <elem n="addrDestP2PUnitId          " qual="ub byt=8 bit=4 len=4"/>
  <elem n="addrDestP2PLocation        " qual="ub byt=8 bit=0 len=4"/>
  <elem n="addrDestP2PCarId           " qual="ub byt=9 bit=3 len=5"/>
  <elem n="addrDestP2PReserved        " qual="ub byt=9 bit=0 len=3"/>
  <elem n="addrDestP2PAudioSensorType " qual="e8" enumDef="RailAudioSensorTypeT"/>
  <elem n="addrDestP2MMaskUnit        " qual="u8 byt=8"/>
  <elem n="addrDestP2MMaskCar         " qual="st len=4"/>
  <elem n="addrDestP2MMaskLocation    " qual="st len=2"/>
  <elem n="addrDestP2MMaskAudio       " qual="st len=3"/>
  <typeScope v="0,181"/>
 </RacCtrl>
  */

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonRacCtrl(1383888025)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "audioLine"

  /**
   * Slot for the {@code audioLine} property.
   * @see #getAudioLine
   * @see #setAudioLine
   */
  public static final Property audioLine = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 0, 5, 3,  null));

  /**
   * Get the {@code audioLine} property.
   * @see #audioLine
   */
  public BLonFloat getAudioLine() { return (BLonFloat)get(audioLine); }

  /**
   * Set the {@code audioLine} property.
   * @see #audioLine
   */
  public void setAudioLine(BLonFloat v) { set(audioLine, v, null); }

  //endregion Property "audioLine"

  //region Property "duplexFull"

  /**
   * Slot for the {@code duplexFull} property.
   * @see #getDuplexFull
   * @see #setDuplexFull
   */
  public static final Property duplexFull = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 0, 4, 1,  null));

  /**
   * Get the {@code duplexFull} property.
   * @see #duplexFull
   */
  public BLonBoolean getDuplexFull() { return (BLonBoolean)get(duplexFull); }

  /**
   * Set the {@code duplexFull} property.
   * @see #duplexFull
   */
  public void setDuplexFull(BLonBoolean v) { set(duplexFull, v, null); }

  //endregion Property "duplexFull"

  //region Property "destP2P"

  /**
   * Slot for the {@code destP2P} property.
   * @see #getDestP2P
   * @see #setDestP2P
   */
  public static final Property destP2P = newProperty(0, BLonBoolean.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.bb, 0, 3, 1,  null));

  /**
   * Get the {@code destP2P} property.
   * @see #destP2P
   */
  public BLonBoolean getDestP2P() { return (BLonBoolean)get(destP2P); }

  /**
   * Set the {@code destP2P} property.
   * @see #destP2P
   */
  public void setDestP2P(BLonBoolean v) { set(destP2P, v, null); }

  //endregion Property "destP2P"

  //region Property "reserved"

  /**
   * Slot for the {@code reserved} property.
   * @see #getReserved
   * @see #setReserved
   */
  public static final Property reserved = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 0, 0, 3,  null));

  /**
   * Get the {@code reserved} property.
   * @see #reserved
   */
  public BLonFloat getReserved() { return (BLonFloat)get(reserved); }

  /**
   * Set the {@code reserved} property.
   * @see #reserved
   */
  public void setReserved(BLonFloat v) { set(reserved, v, null); }

  //endregion Property "reserved"

  //region Property "audioType"

  /**
   * Slot for the {@code audioType} property.
   * @see #getAudioType
   * @see #setAudioType
   */
  public static final Property audioType = newProperty(0, BLonEnum.make(BLonRailAudioTypeEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code audioType} property.
   * @see #audioType
   */
  public BLonEnum getAudioType() { return (BLonEnum)get(audioType); }

  /**
   * Set the {@code audioType} property.
   * @see #audioType
   */
  public void setAudioType(BLonEnum v) { set(audioType, v, null); }

  //endregion Property "audioType"

  //region Property "addrInitUnitId"

  /**
   * Slot for the {@code addrInitUnitId} property.
   * @see #getAddrInitUnitId
   * @see #setAddrInitUnitId
   */
  public static final Property addrInitUnitId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, false, 0F,true, 8, 1F, 0F, true, 2, 4, false, 0F, 4, null));

  /**
   * Get the {@code addrInitUnitId} property.
   * @see #addrInitUnitId
   */
  public BLonFloat getAddrInitUnitId() { return (BLonFloat)get(addrInitUnitId); }

  /**
   * Set the {@code addrInitUnitId} property.
   * @see #addrInitUnitId
   */
  public void setAddrInitUnitId(BLonFloat v) { set(addrInitUnitId, v, null); }

  //endregion Property "addrInitUnitId"

  //region Property "addrInitLocation"

  /**
   * Slot for the {@code addrInitLocation} property.
   * @see #getAddrInitLocation
   * @see #setAddrInitLocation
   */
  public static final Property addrInitLocation = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 2, 0, 4,  null));

  /**
   * Get the {@code addrInitLocation} property.
   * @see #addrInitLocation
   */
  public BLonFloat getAddrInitLocation() { return (BLonFloat)get(addrInitLocation); }

  /**
   * Set the {@code addrInitLocation} property.
   * @see #addrInitLocation
   */
  public void setAddrInitLocation(BLonFloat v) { set(addrInitLocation, v, null); }

  //endregion Property "addrInitLocation"

  //region Property "addrInitCarId"

  /**
   * Slot for the {@code addrInitCarId} property.
   * @see #getAddrInitCarId
   * @see #setAddrInitCarId
   */
  public static final Property addrInitCarId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 3, 5,  null));

  /**
   * Get the {@code addrInitCarId} property.
   * @see #addrInitCarId
   */
  public BLonFloat getAddrInitCarId() { return (BLonFloat)get(addrInitCarId); }

  /**
   * Set the {@code addrInitCarId} property.
   * @see #addrInitCarId
   */
  public void setAddrInitCarId(BLonFloat v) { set(addrInitCarId, v, null); }

  //endregion Property "addrInitCarId"

  //region Property "addrInitReserved"

  /**
   * Slot for the {@code addrInitReserved} property.
   * @see #getAddrInitReserved
   * @see #setAddrInitReserved
   */
  public static final Property addrInitReserved = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 3, 0, 3,  null));

  /**
   * Get the {@code addrInitReserved} property.
   * @see #addrInitReserved
   */
  public BLonFloat getAddrInitReserved() { return (BLonFloat)get(addrInitReserved); }

  /**
   * Set the {@code addrInitReserved} property.
   * @see #addrInitReserved
   */
  public void setAddrInitReserved(BLonFloat v) { set(addrInitReserved, v, null); }

  //endregion Property "addrInitReserved"

  //region Property "addrInitAudioSensorType"

  /**
   * Slot for the {@code addrInitAudioSensorType} property.
   * @see #getAddrInitAudioSensorType
   * @see #setAddrInitAudioSensorType
   */
  public static final Property addrInitAudioSensorType = newProperty(0, BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code addrInitAudioSensorType} property.
   * @see #addrInitAudioSensorType
   */
  public BLonEnum getAddrInitAudioSensorType() { return (BLonEnum)get(addrInitAudioSensorType); }

  /**
   * Set the {@code addrInitAudioSensorType} property.
   * @see #addrInitAudioSensorType
   */
  public void setAddrInitAudioSensorType(BLonEnum v) { set(addrInitAudioSensorType, v, null); }

  //endregion Property "addrInitAudioSensorType"

  //region Property "addrTalkUnitId"

  /**
   * Slot for the {@code addrTalkUnitId} property.
   * @see #getAddrTalkUnitId
   * @see #setAddrTalkUnitId
   */
  public static final Property addrTalkUnitId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 5, 4, 4,  null));

  /**
   * Get the {@code addrTalkUnitId} property.
   * @see #addrTalkUnitId
   */
  public BLonFloat getAddrTalkUnitId() { return (BLonFloat)get(addrTalkUnitId); }

  /**
   * Set the {@code addrTalkUnitId} property.
   * @see #addrTalkUnitId
   */
  public void setAddrTalkUnitId(BLonFloat v) { set(addrTalkUnitId, v, null); }

  //endregion Property "addrTalkUnitId"

  //region Property "addrTalkLocation"

  /**
   * Slot for the {@code addrTalkLocation} property.
   * @see #getAddrTalkLocation
   * @see #setAddrTalkLocation
   */
  public static final Property addrTalkLocation = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 5, 0, 4,  null));

  /**
   * Get the {@code addrTalkLocation} property.
   * @see #addrTalkLocation
   */
  public BLonFloat getAddrTalkLocation() { return (BLonFloat)get(addrTalkLocation); }

  /**
   * Set the {@code addrTalkLocation} property.
   * @see #addrTalkLocation
   */
  public void setAddrTalkLocation(BLonFloat v) { set(addrTalkLocation, v, null); }

  //endregion Property "addrTalkLocation"

  //region Property "addrTalkCarId"

  /**
   * Slot for the {@code addrTalkCarId} property.
   * @see #getAddrTalkCarId
   * @see #setAddrTalkCarId
   */
  public static final Property addrTalkCarId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 6, 3, 5,  null));

  /**
   * Get the {@code addrTalkCarId} property.
   * @see #addrTalkCarId
   */
  public BLonFloat getAddrTalkCarId() { return (BLonFloat)get(addrTalkCarId); }

  /**
   * Set the {@code addrTalkCarId} property.
   * @see #addrTalkCarId
   */
  public void setAddrTalkCarId(BLonFloat v) { set(addrTalkCarId, v, null); }

  //endregion Property "addrTalkCarId"

  //region Property "addrTalkReserved"

  /**
   * Slot for the {@code addrTalkReserved} property.
   * @see #getAddrTalkReserved
   * @see #setAddrTalkReserved
   */
  public static final Property addrTalkReserved = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 6, 0, 3,  null));

  /**
   * Get the {@code addrTalkReserved} property.
   * @see #addrTalkReserved
   */
  public BLonFloat getAddrTalkReserved() { return (BLonFloat)get(addrTalkReserved); }

  /**
   * Set the {@code addrTalkReserved} property.
   * @see #addrTalkReserved
   */
  public void setAddrTalkReserved(BLonFloat v) { set(addrTalkReserved, v, null); }

  //endregion Property "addrTalkReserved"

  //region Property "addrTalkAudioSensorType"

  /**
   * Slot for the {@code addrTalkAudioSensorType} property.
   * @see #getAddrTalkAudioSensorType
   * @see #setAddrTalkAudioSensorType
   */
  public static final Property addrTalkAudioSensorType = newProperty(0, BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code addrTalkAudioSensorType} property.
   * @see #addrTalkAudioSensorType
   */
  public BLonEnum getAddrTalkAudioSensorType() { return (BLonEnum)get(addrTalkAudioSensorType); }

  /**
   * Set the {@code addrTalkAudioSensorType} property.
   * @see #addrTalkAudioSensorType
   */
  public void setAddrTalkAudioSensorType(BLonEnum v) { set(addrTalkAudioSensorType, v, null); }

  //endregion Property "addrTalkAudioSensorType"

  //region Property "addrDestP2PUnitId"

  /**
   * Slot for the {@code addrDestP2PUnitId} property.
   * @see #getAddrDestP2PUnitId
   * @see #setAddrDestP2PUnitId
   */
  public static final Property addrDestP2PUnitId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 8, 4, 4,  null));

  /**
   * Get the {@code addrDestP2PUnitId} property.
   * @see #addrDestP2PUnitId
   */
  public BLonFloat getAddrDestP2PUnitId() { return (BLonFloat)get(addrDestP2PUnitId); }

  /**
   * Set the {@code addrDestP2PUnitId} property.
   * @see #addrDestP2PUnitId
   */
  public void setAddrDestP2PUnitId(BLonFloat v) { set(addrDestP2PUnitId, v, null); }

  //endregion Property "addrDestP2PUnitId"

  //region Property "addrDestP2PLocation"

  /**
   * Slot for the {@code addrDestP2PLocation} property.
   * @see #getAddrDestP2PLocation
   * @see #setAddrDestP2PLocation
   */
  public static final Property addrDestP2PLocation = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 8, 0, 4,  null));

  /**
   * Get the {@code addrDestP2PLocation} property.
   * @see #addrDestP2PLocation
   */
  public BLonFloat getAddrDestP2PLocation() { return (BLonFloat)get(addrDestP2PLocation); }

  /**
   * Set the {@code addrDestP2PLocation} property.
   * @see #addrDestP2PLocation
   */
  public void setAddrDestP2PLocation(BLonFloat v) { set(addrDestP2PLocation, v, null); }

  //endregion Property "addrDestP2PLocation"

  //region Property "addrDestP2PCarId"

  /**
   * Slot for the {@code addrDestP2PCarId} property.
   * @see #getAddrDestP2PCarId
   * @see #setAddrDestP2PCarId
   */
  public static final Property addrDestP2PCarId = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 9, 3, 5,  null));

  /**
   * Get the {@code addrDestP2PCarId} property.
   * @see #addrDestP2PCarId
   */
  public BLonFloat getAddrDestP2PCarId() { return (BLonFloat)get(addrDestP2PCarId); }

  /**
   * Set the {@code addrDestP2PCarId} property.
   * @see #addrDestP2PCarId
   */
  public void setAddrDestP2PCarId(BLonFloat v) { set(addrDestP2PCarId, v, null); }

  //endregion Property "addrDestP2PCarId"

  //region Property "addrDestP2PReserved"

  /**
   * Slot for the {@code addrDestP2PReserved} property.
   * @see #getAddrDestP2PReserved
   * @see #setAddrDestP2PReserved
   */
  public static final Property addrDestP2PReserved = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub, 9, 0, 3,  null));

  /**
   * Get the {@code addrDestP2PReserved} property.
   * @see #addrDestP2PReserved
   */
  public BLonFloat getAddrDestP2PReserved() { return (BLonFloat)get(addrDestP2PReserved); }

  /**
   * Set the {@code addrDestP2PReserved} property.
   * @see #addrDestP2PReserved
   */
  public void setAddrDestP2PReserved(BLonFloat v) { set(addrDestP2PReserved, v, null); }

  //endregion Property "addrDestP2PReserved"

  //region Property "addrDestP2PAudioSensorType"

  /**
   * Slot for the {@code addrDestP2PAudioSensorType} property.
   * @see #getAddrDestP2PAudioSensorType
   * @see #setAddrDestP2PAudioSensorType
   */
  public static final Property addrDestP2PAudioSensorType = newProperty(0, BLonEnum.make(BLonRailAudioSensorTypeEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code addrDestP2PAudioSensorType} property.
   * @see #addrDestP2PAudioSensorType
   */
  public BLonEnum getAddrDestP2PAudioSensorType() { return (BLonEnum)get(addrDestP2PAudioSensorType); }

  /**
   * Set the {@code addrDestP2PAudioSensorType} property.
   * @see #addrDestP2PAudioSensorType
   */
  public void setAddrDestP2PAudioSensorType(BLonEnum v) { set(addrDestP2PAudioSensorType, v, null); }

  //endregion Property "addrDestP2PAudioSensorType"

  //region Property "addrDestP2MMaskUnit"

  /**
   * Slot for the {@code addrDestP2MMaskUnit} property.
   * @see #getAddrDestP2MMaskUnit
   * @see #setAddrDestP2MMaskUnit
   */
  public static final Property addrDestP2MMaskUnit = newProperty(0, BLonFloat  .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 8, null));

  /**
   * Get the {@code addrDestP2MMaskUnit} property.
   * @see #addrDestP2MMaskUnit
   */
  public BLonFloat getAddrDestP2MMaskUnit() { return (BLonFloat)get(addrDestP2MMaskUnit); }

  /**
   * Set the {@code addrDestP2MMaskUnit} property.
   * @see #addrDestP2MMaskUnit
   */
  public void setAddrDestP2MMaskUnit(BLonFloat v) { set(addrDestP2MMaskUnit, v, null); }

  //endregion Property "addrDestP2MMaskUnit"

  //region Property "addrDestP2MMaskCar"

  /**
   * Slot for the {@code addrDestP2MMaskCar} property.
   * @see #getAddrDestP2MMaskCar
   * @see #setAddrDestP2MMaskCar
   */
  public static final Property addrDestP2MMaskCar = newProperty(0, BLonString .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.st, 4, null));

  /**
   * Get the {@code addrDestP2MMaskCar} property.
   * @see #addrDestP2MMaskCar
   */
  public BLonString getAddrDestP2MMaskCar() { return (BLonString)get(addrDestP2MMaskCar); }

  /**
   * Set the {@code addrDestP2MMaskCar} property.
   * @see #addrDestP2MMaskCar
   */
  public void setAddrDestP2MMaskCar(BLonString v) { set(addrDestP2MMaskCar, v, null); }

  //endregion Property "addrDestP2MMaskCar"

  //region Property "addrDestP2MMaskLocation"

  /**
   * Slot for the {@code addrDestP2MMaskLocation} property.
   * @see #getAddrDestP2MMaskLocation
   * @see #setAddrDestP2MMaskLocation
   */
  public static final Property addrDestP2MMaskLocation = newProperty(0, BLonString .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.st, 2, null));

  /**
   * Get the {@code addrDestP2MMaskLocation} property.
   * @see #addrDestP2MMaskLocation
   */
  public BLonString getAddrDestP2MMaskLocation() { return (BLonString)get(addrDestP2MMaskLocation); }

  /**
   * Set the {@code addrDestP2MMaskLocation} property.
   * @see #addrDestP2MMaskLocation
   */
  public void setAddrDestP2MMaskLocation(BLonString v) { set(addrDestP2MMaskLocation, v, null); }

  //endregion Property "addrDestP2MMaskLocation"

  //region Property "addrDestP2MMaskAudio"

  /**
   * Slot for the {@code addrDestP2MMaskAudio} property.
   * @see #getAddrDestP2MMaskAudio
   * @see #setAddrDestP2MMaskAudio
   */
  public static final Property addrDestP2MMaskAudio = newProperty(0, BLonString .DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.st, 3, null));

  /**
   * Get the {@code addrDestP2MMaskAudio} property.
   * @see #addrDestP2MMaskAudio
   */
  public BLonString getAddrDestP2MMaskAudio() { return (BLonString)get(addrDestP2MMaskAudio); }

  /**
   * Set the {@code addrDestP2MMaskAudio} property.
   * @see #addrDestP2MMaskAudio
   */
  public void setAddrDestP2MMaskAudio(BLonString v) { set(addrDestP2MMaskAudio, v, null); }

  //endregion Property "addrDestP2MMaskAudio"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonRacCtrl.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(audioLine                  , out);
    primitiveToOutputStream(duplexFull                 , out);
    primitiveToOutputStream(destP2P                    , out);
    primitiveToOutputStream(reserved                   , out);
    primitiveToOutputStream(audioType                  , out);
    primitiveToOutputStream(addrInitUnitId             , out);
    primitiveToOutputStream(addrInitLocation           , out);
    primitiveToOutputStream(addrInitCarId              , out);
    primitiveToOutputStream(addrInitReserved           , out);
    primitiveToOutputStream(addrInitAudioSensorType    , out);
    primitiveToOutputStream(addrTalkUnitId             , out);
    primitiveToOutputStream(addrTalkLocation           , out);
    primitiveToOutputStream(addrTalkCarId              , out);
    primitiveToOutputStream(addrTalkReserved           , out);
    primitiveToOutputStream(addrTalkAudioSensorType    , out);

    if(getDestP2P().getBoolean()) // This is just a quess - can't find any documentation
    {
      primitiveToOutputStream(addrDestP2PUnitId          , out);
      primitiveToOutputStream(addrDestP2PLocation        , out);
      primitiveToOutputStream(addrDestP2PCarId           , out);
      primitiveToOutputStream(addrDestP2PReserved        , out);
      primitiveToOutputStream(addrDestP2PAudioSensorType , out);
    }  
    else
    {
      primitiveToOutputStream(addrDestP2MMaskUnit      , out);
      primitiveToOutputStream(addrDestP2MMaskCar       , out);
      primitiveToOutputStream(addrDestP2MMaskLocation  , out);
      primitiveToOutputStream(addrDestP2MMaskAudio     , out);
    }  
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(audioLine                  , in);
    primitiveFromInputStream(duplexFull                 , in);
    primitiveFromInputStream(destP2P                    , in);
    primitiveFromInputStream(reserved                   , in);
    primitiveFromInputStream(audioType                  , in);
    primitiveFromInputStream(addrInitUnitId             , in);
    primitiveFromInputStream(addrInitLocation           , in);
    primitiveFromInputStream(addrInitCarId              , in);
    primitiveFromInputStream(addrInitReserved           , in);
    primitiveFromInputStream(addrInitAudioSensorType    , in);
    primitiveFromInputStream(addrTalkUnitId             , in);
    primitiveFromInputStream(addrTalkLocation           , in);
    primitiveFromInputStream(addrTalkCarId              , in);
    primitiveFromInputStream(addrTalkReserved           , in);
    primitiveFromInputStream(addrTalkAudioSensorType    , in);
    
    if(getDestP2P().getBoolean())
    {
      primitiveFromInputStream(addrDestP2PUnitId          , in);
      primitiveFromInputStream(addrDestP2PLocation        , in);
      primitiveFromInputStream(addrDestP2PCarId           , in);
      primitiveFromInputStream(addrDestP2PReserved        , in);
      primitiveFromInputStream(addrDestP2PAudioSensorType , in);
    }
    else
    {
      primitiveFromInputStream(addrDestP2MMaskUnit        , in);
      primitiveFromInputStream(addrDestP2MMaskCar         , in);
      primitiveFromInputStream(addrDestP2MMaskLocation    , in);
      primitiveFromInputStream(addrDestP2MMaskAudio       , in);
    }
  }  


}      
