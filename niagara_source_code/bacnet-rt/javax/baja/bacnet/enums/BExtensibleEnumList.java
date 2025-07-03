/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import java.util.ArrayList;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.xml.XElem;

/**
 * BExtensibleEnumList is a container for managing a device's knowledge of
 * any proprietary extensions to any of the extensible enumerations
 * are defined by Bacnet.  It contains one BDynamicEnum for
 * each extensible enumeration type, available for use within the device.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 15 May 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "errorClassFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetErrorClass.TYPE))"
)
@NiagaraProperty(
  name = "errorCodeFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetErrorCode.TYPE))"
)
@NiagaraProperty(
  name = "abortReasonFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetAbortReason.TYPE))"
)
@NiagaraProperty(
  name = "deviceStatusFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetDeviceStatus.TYPE))"
)
@NiagaraProperty(
  name = "engineeringUnitsFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetEngineeringUnits.TYPE))"
)
@NiagaraProperty(
  name = "eventStateFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetEventState.TYPE))"
)
@NiagaraProperty(
  name = "eventTypeFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetEventType.TYPE))"
)
@NiagaraProperty(
  name = "lifeSafetyModeFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyMode.TYPE))"
)
@NiagaraProperty(
  name = "lifeSafetyOperationFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyOperation.TYPE))"
)
@NiagaraProperty(
  name = "lifeSafetyStateFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyState.TYPE))"
)
@NiagaraProperty(
  name = "maintenanceFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetMaintenance.TYPE))"
)
@NiagaraProperty(
  name = "objectTypeFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetObjectType.TYPE))"
)
@NiagaraProperty(
  name = "programErrorFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetProgramError.TYPE))"
)
@NiagaraProperty(
  name = "propertyIdFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetPropertyIdentifier.TYPE))"
)
@NiagaraProperty(
  name = "reliabilityFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetReliability.TYPE))"
)
@NiagaraProperty(
  name = "rejectReasonFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetRejectReason.TYPE))"
)
@NiagaraProperty(
  name = "silencedStateFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetSilencedState.TYPE))"
)
@NiagaraProperty(
  name = "vtClassFacets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum(BEnumRange.make(BBacnetVtClass.TYPE))"
)
public class BExtensibleEnumList
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BExtensibleEnumList(1750037286)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "errorClassFacets"

  /**
   * Slot for the {@code errorClassFacets} property.
   * @see #getErrorClassFacets
   * @see #setErrorClassFacets
   */
  public static final Property errorClassFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetErrorClass.TYPE)), null);

  /**
   * Get the {@code errorClassFacets} property.
   * @see #errorClassFacets
   */
  public BFacets getErrorClassFacets() { return (BFacets)get(errorClassFacets); }

  /**
   * Set the {@code errorClassFacets} property.
   * @see #errorClassFacets
   */
  public void setErrorClassFacets(BFacets v) { set(errorClassFacets, v, null); }

  //endregion Property "errorClassFacets"

  //region Property "errorCodeFacets"

  /**
   * Slot for the {@code errorCodeFacets} property.
   * @see #getErrorCodeFacets
   * @see #setErrorCodeFacets
   */
  public static final Property errorCodeFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetErrorCode.TYPE)), null);

  /**
   * Get the {@code errorCodeFacets} property.
   * @see #errorCodeFacets
   */
  public BFacets getErrorCodeFacets() { return (BFacets)get(errorCodeFacets); }

  /**
   * Set the {@code errorCodeFacets} property.
   * @see #errorCodeFacets
   */
  public void setErrorCodeFacets(BFacets v) { set(errorCodeFacets, v, null); }

  //endregion Property "errorCodeFacets"

  //region Property "abortReasonFacets"

  /**
   * Slot for the {@code abortReasonFacets} property.
   * @see #getAbortReasonFacets
   * @see #setAbortReasonFacets
   */
  public static final Property abortReasonFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetAbortReason.TYPE)), null);

  /**
   * Get the {@code abortReasonFacets} property.
   * @see #abortReasonFacets
   */
  public BFacets getAbortReasonFacets() { return (BFacets)get(abortReasonFacets); }

  /**
   * Set the {@code abortReasonFacets} property.
   * @see #abortReasonFacets
   */
  public void setAbortReasonFacets(BFacets v) { set(abortReasonFacets, v, null); }

  //endregion Property "abortReasonFacets"

  //region Property "deviceStatusFacets"

  /**
   * Slot for the {@code deviceStatusFacets} property.
   * @see #getDeviceStatusFacets
   * @see #setDeviceStatusFacets
   */
  public static final Property deviceStatusFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetDeviceStatus.TYPE)), null);

  /**
   * Get the {@code deviceStatusFacets} property.
   * @see #deviceStatusFacets
   */
  public BFacets getDeviceStatusFacets() { return (BFacets)get(deviceStatusFacets); }

  /**
   * Set the {@code deviceStatusFacets} property.
   * @see #deviceStatusFacets
   */
  public void setDeviceStatusFacets(BFacets v) { set(deviceStatusFacets, v, null); }

  //endregion Property "deviceStatusFacets"

  //region Property "engineeringUnitsFacets"

  /**
   * Slot for the {@code engineeringUnitsFacets} property.
   * @see #getEngineeringUnitsFacets
   * @see #setEngineeringUnitsFacets
   */
  public static final Property engineeringUnitsFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetEngineeringUnits.TYPE)), null);

  /**
   * Get the {@code engineeringUnitsFacets} property.
   * @see #engineeringUnitsFacets
   */
  public BFacets getEngineeringUnitsFacets() { return (BFacets)get(engineeringUnitsFacets); }

  /**
   * Set the {@code engineeringUnitsFacets} property.
   * @see #engineeringUnitsFacets
   */
  public void setEngineeringUnitsFacets(BFacets v) { set(engineeringUnitsFacets, v, null); }

  //endregion Property "engineeringUnitsFacets"

  //region Property "eventStateFacets"

  /**
   * Slot for the {@code eventStateFacets} property.
   * @see #getEventStateFacets
   * @see #setEventStateFacets
   */
  public static final Property eventStateFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetEventState.TYPE)), null);

  /**
   * Get the {@code eventStateFacets} property.
   * @see #eventStateFacets
   */
  public BFacets getEventStateFacets() { return (BFacets)get(eventStateFacets); }

  /**
   * Set the {@code eventStateFacets} property.
   * @see #eventStateFacets
   */
  public void setEventStateFacets(BFacets v) { set(eventStateFacets, v, null); }

  //endregion Property "eventStateFacets"

  //region Property "eventTypeFacets"

  /**
   * Slot for the {@code eventTypeFacets} property.
   * @see #getEventTypeFacets
   * @see #setEventTypeFacets
   */
  public static final Property eventTypeFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetEventType.TYPE)), null);

  /**
   * Get the {@code eventTypeFacets} property.
   * @see #eventTypeFacets
   */
  public BFacets getEventTypeFacets() { return (BFacets)get(eventTypeFacets); }

  /**
   * Set the {@code eventTypeFacets} property.
   * @see #eventTypeFacets
   */
  public void setEventTypeFacets(BFacets v) { set(eventTypeFacets, v, null); }

  //endregion Property "eventTypeFacets"

  //region Property "lifeSafetyModeFacets"

  /**
   * Slot for the {@code lifeSafetyModeFacets} property.
   * @see #getLifeSafetyModeFacets
   * @see #setLifeSafetyModeFacets
   */
  public static final Property lifeSafetyModeFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyMode.TYPE)), null);

  /**
   * Get the {@code lifeSafetyModeFacets} property.
   * @see #lifeSafetyModeFacets
   */
  public BFacets getLifeSafetyModeFacets() { return (BFacets)get(lifeSafetyModeFacets); }

  /**
   * Set the {@code lifeSafetyModeFacets} property.
   * @see #lifeSafetyModeFacets
   */
  public void setLifeSafetyModeFacets(BFacets v) { set(lifeSafetyModeFacets, v, null); }

  //endregion Property "lifeSafetyModeFacets"

  //region Property "lifeSafetyOperationFacets"

  /**
   * Slot for the {@code lifeSafetyOperationFacets} property.
   * @see #getLifeSafetyOperationFacets
   * @see #setLifeSafetyOperationFacets
   */
  public static final Property lifeSafetyOperationFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyOperation.TYPE)), null);

  /**
   * Get the {@code lifeSafetyOperationFacets} property.
   * @see #lifeSafetyOperationFacets
   */
  public BFacets getLifeSafetyOperationFacets() { return (BFacets)get(lifeSafetyOperationFacets); }

  /**
   * Set the {@code lifeSafetyOperationFacets} property.
   * @see #lifeSafetyOperationFacets
   */
  public void setLifeSafetyOperationFacets(BFacets v) { set(lifeSafetyOperationFacets, v, null); }

  //endregion Property "lifeSafetyOperationFacets"

  //region Property "lifeSafetyStateFacets"

  /**
   * Slot for the {@code lifeSafetyStateFacets} property.
   * @see #getLifeSafetyStateFacets
   * @see #setLifeSafetyStateFacets
   */
  public static final Property lifeSafetyStateFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetLifeSafetyState.TYPE)), null);

  /**
   * Get the {@code lifeSafetyStateFacets} property.
   * @see #lifeSafetyStateFacets
   */
  public BFacets getLifeSafetyStateFacets() { return (BFacets)get(lifeSafetyStateFacets); }

  /**
   * Set the {@code lifeSafetyStateFacets} property.
   * @see #lifeSafetyStateFacets
   */
  public void setLifeSafetyStateFacets(BFacets v) { set(lifeSafetyStateFacets, v, null); }

  //endregion Property "lifeSafetyStateFacets"

  //region Property "maintenanceFacets"

  /**
   * Slot for the {@code maintenanceFacets} property.
   * @see #getMaintenanceFacets
   * @see #setMaintenanceFacets
   */
  public static final Property maintenanceFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetMaintenance.TYPE)), null);

  /**
   * Get the {@code maintenanceFacets} property.
   * @see #maintenanceFacets
   */
  public BFacets getMaintenanceFacets() { return (BFacets)get(maintenanceFacets); }

  /**
   * Set the {@code maintenanceFacets} property.
   * @see #maintenanceFacets
   */
  public void setMaintenanceFacets(BFacets v) { set(maintenanceFacets, v, null); }

  //endregion Property "maintenanceFacets"

  //region Property "objectTypeFacets"

  /**
   * Slot for the {@code objectTypeFacets} property.
   * @see #getObjectTypeFacets
   * @see #setObjectTypeFacets
   */
  public static final Property objectTypeFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetObjectType.TYPE)), null);

  /**
   * Get the {@code objectTypeFacets} property.
   * @see #objectTypeFacets
   */
  public BFacets getObjectTypeFacets() { return (BFacets)get(objectTypeFacets); }

  /**
   * Set the {@code objectTypeFacets} property.
   * @see #objectTypeFacets
   */
  public void setObjectTypeFacets(BFacets v) { set(objectTypeFacets, v, null); }

  //endregion Property "objectTypeFacets"

  //region Property "programErrorFacets"

  /**
   * Slot for the {@code programErrorFacets} property.
   * @see #getProgramErrorFacets
   * @see #setProgramErrorFacets
   */
  public static final Property programErrorFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetProgramError.TYPE)), null);

  /**
   * Get the {@code programErrorFacets} property.
   * @see #programErrorFacets
   */
  public BFacets getProgramErrorFacets() { return (BFacets)get(programErrorFacets); }

  /**
   * Set the {@code programErrorFacets} property.
   * @see #programErrorFacets
   */
  public void setProgramErrorFacets(BFacets v) { set(programErrorFacets, v, null); }

  //endregion Property "programErrorFacets"

  //region Property "propertyIdFacets"

  /**
   * Slot for the {@code propertyIdFacets} property.
   * @see #getPropertyIdFacets
   * @see #setPropertyIdFacets
   */
  public static final Property propertyIdFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetPropertyIdentifier.TYPE)), null);

  /**
   * Get the {@code propertyIdFacets} property.
   * @see #propertyIdFacets
   */
  public BFacets getPropertyIdFacets() { return (BFacets)get(propertyIdFacets); }

  /**
   * Set the {@code propertyIdFacets} property.
   * @see #propertyIdFacets
   */
  public void setPropertyIdFacets(BFacets v) { set(propertyIdFacets, v, null); }

  //endregion Property "propertyIdFacets"

  //region Property "reliabilityFacets"

  /**
   * Slot for the {@code reliabilityFacets} property.
   * @see #getReliabilityFacets
   * @see #setReliabilityFacets
   */
  public static final Property reliabilityFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetReliability.TYPE)), null);

  /**
   * Get the {@code reliabilityFacets} property.
   * @see #reliabilityFacets
   */
  public BFacets getReliabilityFacets() { return (BFacets)get(reliabilityFacets); }

  /**
   * Set the {@code reliabilityFacets} property.
   * @see #reliabilityFacets
   */
  public void setReliabilityFacets(BFacets v) { set(reliabilityFacets, v, null); }

  //endregion Property "reliabilityFacets"

  //region Property "rejectReasonFacets"

  /**
   * Slot for the {@code rejectReasonFacets} property.
   * @see #getRejectReasonFacets
   * @see #setRejectReasonFacets
   */
  public static final Property rejectReasonFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetRejectReason.TYPE)), null);

  /**
   * Get the {@code rejectReasonFacets} property.
   * @see #rejectReasonFacets
   */
  public BFacets getRejectReasonFacets() { return (BFacets)get(rejectReasonFacets); }

  /**
   * Set the {@code rejectReasonFacets} property.
   * @see #rejectReasonFacets
   */
  public void setRejectReasonFacets(BFacets v) { set(rejectReasonFacets, v, null); }

  //endregion Property "rejectReasonFacets"

  //region Property "silencedStateFacets"

  /**
   * Slot for the {@code silencedStateFacets} property.
   * @see #getSilencedStateFacets
   * @see #setSilencedStateFacets
   */
  public static final Property silencedStateFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetSilencedState.TYPE)), null);

  /**
   * Get the {@code silencedStateFacets} property.
   * @see #silencedStateFacets
   */
  public BFacets getSilencedStateFacets() { return (BFacets)get(silencedStateFacets); }

  /**
   * Set the {@code silencedStateFacets} property.
   * @see #silencedStateFacets
   */
  public void setSilencedStateFacets(BFacets v) { set(silencedStateFacets, v, null); }

  //endregion Property "silencedStateFacets"

  //region Property "vtClassFacets"

  /**
   * Slot for the {@code vtClassFacets} property.
   * @see #getVtClassFacets
   * @see #setVtClassFacets
   */
  public static final Property vtClassFacets = newProperty(0, BFacets.makeEnum(BEnumRange.make(BBacnetVtClass.TYPE)), null);

  /**
   * Get the {@code vtClassFacets} property.
   * @see #vtClassFacets
   */
  public BFacets getVtClassFacets() { return (BFacets)get(vtClassFacets); }

  /**
   * Set the {@code vtClassFacets} property.
   * @see #vtClassFacets
   */
  public void setVtClassFacets(BFacets v) { set(vtClassFacets, v, null); }

  //endregion Property "vtClassFacets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExtensibleEnumList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BExtensibleEnumList()
  {
  }

  public BExtensibleEnumList(XElem xlm)
  {
    XElem[] enums = xlm.elems();
    for (int i = 0; i < enums.length; i++)
    {
      XElem[] vals = enums[i].elems("value");
      String enumName = enums[i].name();
      Property enumProp = loadSlots().getProperty(enumName + "Facets");
      BFacets enumFacets = (BFacets)get(enumProp);
      BEnumRange r = (BEnumRange)enumFacets.get(BFacets.RANGE);

      int[] newOrds = new int[vals.length];
      String[] newTags = new String[vals.length];
      for (int j = 0; j < vals.length; j++)
      {
        newTags[j] = vals[j].get("n");
        newOrds[j] = vals[j].geti("v");
      }
      ArrayList<Integer> vOrds = new ArrayList<>();
      ArrayList<String> vTags = new ArrayList<>();
      int[] ords = r.getOrdinals();
      String[] tags = getTags(r);
      for (int j = 0; j < ords.length; j++)
      {
        if (r.isDynamicOrdinal(ords[j]))
        {
          vOrds.add(Integer.valueOf(ords[j]));
          vTags.add(tags[j]);
        }
      }
      int[] mergeOrds = new int[vOrds.size() + newOrds.length];
      String[] mergeTags = new String[vTags.size() + newTags.length];
      for (int j = 0; j < vOrds.size(); j++)
        mergeOrds[j] = vOrds.get(j).intValue();
      System.arraycopy(newOrds, 0, mergeOrds, vOrds.size(), newOrds.length);
      for (int j = 0; j < vTags.size(); j++)
        mergeTags[j] = vTags.get(j);
      System.arraycopy(newTags, 0, mergeTags, vTags.size(), newTags.length);

      set(enumProp, BFacets.makeEnum(BEnumRange.make(r.getFrozenType(), mergeOrds, mergeTags)));
    }
  }


////////////////////////////////////////////////////////////////
// BDynamicEnum Access
////////////////////////////////////////////////////////////////

  /**
   * Convenience method for error-class.
   *
   * @return the error-class enumeration.
   */
  public BDynamicEnum getErrorClass()
  {
    return BDynamicEnum.make(0, getErrorClassRange());
  }

  /**
   * Convenience method for error-code.
   *
   * @return the error-code enumeration.
   */
  public BDynamicEnum getErrorCode()
  {
    return BDynamicEnum.make(0, getErrorCodeRange());
  }

  /**
   * Convenience method for BacnetAbortReason.
   *
   * @return the BacnetAbortReason enumeration.
   */
  public BDynamicEnum getAbortReason()
  {
    return BDynamicEnum.make(0, getAbortReasonRange());
  }

  /**
   * Convenience method for BacnetDeviceStatus.
   *
   * @return the BacnetDeviceStatus enumeration.
   */
  public BDynamicEnum getDeviceStatus()
  {
    return BDynamicEnum.make(0, getDeviceStatusRange());
  }

  /**
   * Convenience method for BacnetEngineeringUnits.
   *
   * @return the BacnetEngineeringUnits enumeration.
   */
  public BDynamicEnum getEngineeringUnits()
  {
    return BDynamicEnum.make(0, getEngineeringUnitsRange());
  }

  /**
   * Convenience method for BacnetEventState.
   *
   * @return the BacnetEventState enumeration.
   */
  public BDynamicEnum getEventState()
  {
    return BDynamicEnum.make(0, getEventStateRange());
  }

  /**
   * Convenience method for BacnetEventType.
   *
   * @return the BacnetEventType enumeration.
   */
  public BDynamicEnum getEventType()
  {
    return BDynamicEnum.make(0, getEventTypeRange());
  }

  /**
   * Convenience method for BacnetLifeSafetyMode.
   *
   * @return the BacnetLifeSafetyMode enumeration.
   */
  public BDynamicEnum getLifeSafetyMode()
  {
    return BDynamicEnum.make(0, getLifeSafetyModeRange());
  }

  /**
   * Convenience method for BacnetLifeSafetyState.
   *
   * @return the BacnetLifeSafetyState enumeration.
   */
  public BDynamicEnum getLifeSafetyState()
  {
    return BDynamicEnum.make(0, getLifeSafetyStateRange());
  }

  /**
   * Convenience method for BacnetLifeSafetyOperation.
   *
   * @return the BacnetLifeSafetyOperation enumeration.
   */
  public BDynamicEnum getLifeSafetyOperation()
  {
    return BDynamicEnum.make(0, getLifeSafetyOperationRange());
  }

  /**
   * Convenience method for BacnetMaintenance.
   *
   * @return the BacnetMaintenance enumeration.
   */
  public BDynamicEnum getMaintenance()
  {
    return BDynamicEnum.make(0, getMaintenanceRange());
  }

  /**
   * Convenience method for BacnetObjectType.
   *
   * @return the BacnetObjectType enumeration.
   */
  public BDynamicEnum getObjectType()
  {
    return BDynamicEnum.make(0, getObjectTypeRange());
  }

  /**
   * Convenience method for BacnetProgramError.
   *
   * @return the BacnetProgramError enumeration.
   */
  public BDynamicEnum getProgramError()
  {
    return BDynamicEnum.make(0, getProgramErrorRange());
  }

  /**
   * Convenience method for BacnetPropertyId.
   *
   * @return the BacnetPropertyId enumeration.
   */
  public BDynamicEnum getPropertyId()
  {
    return BDynamicEnum.make(0, getPropertyIdRange());
  }

  /**
   * Convenience method for BacnetReliability.
   *
   * @return the BacnetReliability enumeration.
   */
  public BDynamicEnum getReliability()
  {
    return BDynamicEnum.make(0, getReliabilityRange());
  }

  /**
   * Convenience method for BacnetRejectReason.
   *
   * @return the BacnetRejectReason enumeration.
   */
  public BDynamicEnum getRejectReason()
  {
    return BDynamicEnum.make(0, getRejectReasonRange());
  }

  /**
   * Convenience method for BacnetSilencedState.
   *
   * @return the BacnetSilencedState enumeration.
   */
  public BDynamicEnum getSilencedState()
  {
    return BDynamicEnum.make(0, getSilencedStateRange());
  }

  /**
   * Convenience method for BacnetVtClass.
   *
   * @return the BacnetVtClass enumeration.
   */
  public BDynamicEnum getVtClass()
  {
    return BDynamicEnum.make(0, getVtClassRange());
  }


////////////////////////////////////////////////////////////////
// Range Access
////////////////////////////////////////////////////////////////

  /**
   * Convenience method for error-class range.
   *
   * @return the known range for the error-class enumeration.
   */
  public BEnumRange getErrorClassRange()
  {
    return (BEnumRange)getErrorClassFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for error-code range.
   *
   * @return the known range for the error-code enumeration.
   */
  public BEnumRange getErrorCodeRange()
  {
    return (BEnumRange)getErrorCodeFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetAbortReason range.
   *
   * @return the known range for the BacnetAbortReason enumeration.
   */
  public BEnumRange getAbortReasonRange()
  {
    return (BEnumRange)getAbortReasonFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetDeviceStatus range.
   *
   * @return the known range for the BacnetDeviceStatus enumeration.
   */
  public BEnumRange getDeviceStatusRange()
  {
    return (BEnumRange)getDeviceStatusFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetEngineeringUnits range.
   *
   * @return the known range for the BacnetEngineeringUnits enumeration.
   */
  public BEnumRange getEngineeringUnitsRange()
  {
    return (BEnumRange)getEngineeringUnitsFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetEventState range.
   *
   * @return the known range for the BacnetEventState enumeration.
   */
  public BEnumRange getEventStateRange()
  {
    return (BEnumRange)getEventStateFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetEventType range.
   *
   * @return the known range for the BacnetEventType enumeration.
   */
  public BEnumRange getEventTypeRange()
  {
    return (BEnumRange)getEventTypeFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetLifeSafetyMode range.
   *
   * @return the known range for the BacnetLifeSafetyMode enumeration.
   */
  public BEnumRange getLifeSafetyModeRange()
  {
    return (BEnumRange)getLifeSafetyModeFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetLifeSafetyState range.
   *
   * @return the known range for the BacnetLifeSafetyState enumeration.
   */
  public BEnumRange getLifeSafetyStateRange()
  {
    return (BEnumRange)getLifeSafetyStateFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetLifeSafetyOperation range.
   *
   * @return the known range for the BacnetLifeSafetyOperation enumeration.
   */
  public BEnumRange getLifeSafetyOperationRange()
  {
    return (BEnumRange)getLifeSafetyOperationFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetMaintenance range.
   *
   * @return the known range for the BacnetMaintenance enumeration.
   */
  public BEnumRange getMaintenanceRange()
  {
    return (BEnumRange)getMaintenanceFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetObjectType range.
   *
   * @return the known range for the BacnetObjectType enumeration.
   */
  public BEnumRange getObjectTypeRange()
  {
    return (BEnumRange)getObjectTypeFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetProgramError range.
   *
   * @return the known range for the BacnetProgramError enumeration.
   */
  public BEnumRange getProgramErrorRange()
  {
    return (BEnumRange)getProgramErrorFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetPropertyId range.
   *
   * @return the known range for the BacnetPropertyId enumeration.
   */
  public BEnumRange getPropertyIdRange()
  {
    return (BEnumRange)getPropertyIdFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetReliability range.
   *
   * @return the known range for the BacnetReliability enumeration.
   */
  public BEnumRange getReliabilityRange()
  {
    return (BEnumRange)getReliabilityFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetRejectReason range.
   *
   * @return the known range for the BacnetRejectReason enumeration.
   */
  public BEnumRange getRejectReasonRange()
  {
    return (BEnumRange)getRejectReasonFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetSilencedState range.
   *
   * @return the known range for the BacnetSilencedState enumeration.
   */
  public BEnumRange getSilencedStateRange()
  {
    return (BEnumRange)getSilencedStateFacets().getFacet(BFacets.RANGE);
  }

  /**
   * Convenience method for BacnetVtClass range.
   *
   * @return the known range for the BacnetVtClass enumeration.
   */
  public BEnumRange getVtClassRange()
  {
    return (BEnumRange)getVtClassFacets().getFacet(BFacets.RANGE);
  }

  public BEnumRange getEnumRange(String type)
  {
    if (type != null)
    {
      if (type.equals(ERROR_CLASS_TYPE)) return getErrorClassRange();
      if (type.equals(ERROR_CODE_TYPE)) return getErrorCodeRange();
      if (type.equals(ABORT_REASON_TYPE)) return getAbortReasonRange();
      if (type.equals(DEVICE_STATUS_TYPE)) return getDeviceStatusRange();
      if (type.equals(ENGINEERING_UNITS_TYPE)) return getEngineeringUnitsRange();
      if (type.equals(EVENT_STATE_TYPE)) return getEventStateRange();
      if (type.equals(EVENT_TYPE_TYPE)) return getEventTypeRange();
      if (type.equals(LIFE_SAFETY_MODE_TYPE)) return getLifeSafetyModeRange();
      if (type.equals(LIFE_SAFETY_OPERATION_TYPE)) return getLifeSafetyOperationRange();
      if (type.equals(LIFE_SAFETY_STATE_TYPE)) return getLifeSafetyStateRange();
      if (type.equals(MAINTENANCE_TYPE)) return getMaintenanceRange();
      if (type.equals(OBJECT_TYPE_TYPE)) return getObjectTypeRange();
      if (type.equals(PROGRAM_ERROR_TYPE)) return getProgramErrorRange();
      if (type.equals(PROPERTY_IDENTIFIER_TYPE)) return getPropertyIdRange();
      if (type.equals(RELIABILITY_TYPE)) return getReliabilityRange();
      if (type.equals(REJECT_REASON_TYPE)) return getRejectReasonRange();
      if (type.equals(SILENCED_STATE_TYPE)) return getSilencedStateRange();
      if (type.equals(VT_CLASS_TYPE)) return getVtClassRange();
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Extensible Enumeration Management
////////////////////////////////////////////////////////////////

  public void addNewErrorClass(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getErrorClassRange(),
      enumValue,
      enumName);
    setErrorClassFacets(BFacets.makeEnum(newRange));
  }

  public void addNewErrorCode(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getErrorCodeRange(),
      enumValue,
      enumName);
    setErrorCodeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewAbortReason(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getAbortReasonRange(),
      enumValue,
      enumName);
    setAbortReasonFacets(BFacets.makeEnum(newRange));
  }

  public void addNewDeviceStatus(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getDeviceStatusRange(),
      enumValue,
      enumName);
    setDeviceStatusFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEngineeringUnits(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEngineeringUnitsRange(),
      enumValue,
      enumName);
    setEngineeringUnitsFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEventState(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEventStateRange(),
      enumValue,
      enumName);
    setEventStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEventType(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEventTypeRange(),
      enumValue,
      enumName);
    setEventTypeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyMode(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyModeRange(),
      enumValue,
      enumName);
    setLifeSafetyModeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyOperation(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyOperationRange(),
      enumValue,
      enumName);
    setLifeSafetyOperationFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyState(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyStateRange(),
      enumValue,
      enumName);
    setLifeSafetyStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewMaintenance(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getMaintenanceRange(),
      enumValue,
      enumName);
    setMaintenanceFacets(BFacets.makeEnum(newRange));
  }

  public void addNewObjectType(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getObjectTypeRange(),
      enumValue,
      enumName);
    setObjectTypeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewProgramError(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getProgramErrorRange(),
      enumValue,
      enumName);
    setProgramErrorFacets(BFacets.makeEnum(newRange));
  }

  public void addNewPropertyId(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getPropertyIdRange(),
      enumValue,
      enumName);
    setPropertyIdFacets(BFacets.makeEnum(newRange));
  }

  public void addNewReliability(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getReliabilityRange(),
      enumValue,
      enumName);
    setReliabilityFacets(BFacets.makeEnum(newRange));
  }

  public void addNewRejectReason(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getRejectReasonRange(),
      enumValue,
      enumName);
    setRejectReasonFacets(BFacets.makeEnum(newRange));
  }

  public void addNewSilencedState(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getSilencedStateRange(),
      enumValue,
      enumName);
    setSilencedStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewVtClass(String enumName, int enumValue)
  {
    BEnumRange newRange = addNewEnum(getVtClassRange(),
      enumValue,
      enumName);
    setVtClassFacets(BFacets.makeEnum(newRange));
  }

  public void addNewErrorClass(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getErrorClassRange(),
      enumValue,
      BBacnetErrorClass.tag(enumValue));
    setErrorClassFacets(BFacets.makeEnum(newRange));
  }

  public void addNewErrorCode(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getErrorCodeRange(),
      enumValue,
      BBacnetErrorCode.tag(enumValue));
    setErrorCodeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewAbortReason(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getAbortReasonRange(),
      enumValue,
      BBacnetAbortReason.tag(enumValue));
    setAbortReasonFacets(BFacets.makeEnum(newRange));
  }

  public void addNewDeviceStatus(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getDeviceStatusRange(),
      enumValue,
      BBacnetDeviceStatus.tag(enumValue));
    setDeviceStatusFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEngineeringUnits(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEngineeringUnitsRange(),
      enumValue,
      BBacnetEngineeringUnits.tag(enumValue));
    setEngineeringUnitsFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEventState(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEventStateRange(),
      enumValue,
      BBacnetEventState.tag(enumValue));
    setEventStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewEventType(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getEventTypeRange(),
      enumValue,
      BBacnetEventType.tag(enumValue));
    setEventTypeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyMode(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyModeRange(),
      enumValue,
      BBacnetLifeSafetyMode.tag(enumValue));
    setLifeSafetyModeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyOperation(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyOperationRange(),
      enumValue,
      BBacnetLifeSafetyOperation.tag(enumValue));
    setLifeSafetyOperationFacets(BFacets.makeEnum(newRange));
  }

  public void addNewLifeSafetyState(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getLifeSafetyStateRange(),
      enumValue,
      BBacnetLifeSafetyState.tag(enumValue));
    setLifeSafetyStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewMaintenance(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getMaintenanceRange(),
      enumValue,
      BBacnetMaintenance.tag(enumValue));
    setMaintenanceFacets(BFacets.makeEnum(newRange));
  }

  public void addNewObjectType(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getObjectTypeRange(),
      enumValue,
      BBacnetObjectType.tag(enumValue));
    setObjectTypeFacets(BFacets.makeEnum(newRange));
  }

  public void addNewProgramError(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getProgramErrorRange(),
      enumValue,
      BBacnetProgramError.tag(enumValue));
    setProgramErrorFacets(BFacets.makeEnum(newRange));
  }

  public void addNewPropertyId(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getPropertyIdRange(),
      enumValue,
      BBacnetPropertyIdentifier.tag(enumValue));
    setPropertyIdFacets(BFacets.makeEnum(newRange));
  }

  public void addNewReliability(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getReliabilityRange(),
      enumValue,
      BBacnetReliability.tag(enumValue));
    setReliabilityFacets(BFacets.makeEnum(newRange));
  }

  public void addNewRejectReason(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getRejectReasonRange(),
      enumValue,
      BBacnetRejectReason.tag(enumValue));
    setRejectReasonFacets(BFacets.makeEnum(newRange));
  }

  public void addNewSilencedState(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getSilencedStateRange(),
      enumValue,
      BBacnetSilencedState.tag(enumValue));
    setSilencedStateFacets(BFacets.makeEnum(newRange));
  }

  public void addNewVtClass(int enumValue)
  {
    BEnumRange newRange = addNewEnum(getVtClassRange(),
      enumValue,
      BBacnetVtClass.tag(enumValue));
    setVtClassFacets(BFacets.makeEnum(newRange));
  }

  private BEnumRange addNewEnum(BEnumRange r, int enumValue, String enumName)
  {
    if (r.isOrdinal(enumValue))
      throw new InvalidEnumException("Enum value already used by " + r.getTag(enumValue));
    if (r.isTag(enumName))
      throw new InvalidEnumException("Enum name already used by " + r.tagToOrdinal(enumName));

    int[] o = r.getOrdinals();
    ArrayList<Integer> olist = new ArrayList<>();
    ArrayList<String> tlist = new ArrayList<>();
    int count = 0;
    for (int i = 0; i < o.length; i++)
    {
      if (r.isDynamicOrdinal(o[i]))
      {
        count++;
        olist.add(Integer.valueOf(o[i]));
        tlist.add(r.getTag(o[i]));
      }
    }
    olist.add(Integer.valueOf(enumValue));
    tlist.add(enumName);
    int[] ords = new int[count + 1];
    for (int i = 0; i <= count; i++)
      ords[i] = olist.get(i).intValue();
    String[] tags = new String[count + 1];
    tlist.toArray(tags);

    return BEnumRange.make(r.getFrozenType(), ords, tags);
  }


////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  private String[] getTags(BEnumRange r)
  {
    int[] ordinals = r.getOrdinals();
    String[] tags = new String[ordinals.length];
    for (int i = 0; i < tags.length; i++)
      tags[i] = r.getTag(ordinals[i]);
    return tags;
  }

  /**
   * Incorporate extensions from list into this object.
   * Duplicates are rejected.
   */
  public void merge(BExtensibleEnumList list)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(BFacets.class))
    {
      Property p = c.property();
      if (p.isFrozen())
      {
        BFacets fThis = (BFacets)c.get();
        BEnumRange rThis = (BEnumRange)fThis.get(BFacets.RANGE);
        int[] ordsThis = rThis.getOrdinals();
        String[] tagsThis = getTags(rThis);

        BFacets fList = (BFacets)list.get(p);
        BEnumRange rList = (BEnumRange)fList.get(BFacets.RANGE);
        int[] ordsList = rList.getOrdinals();
        String[] tagsList = getTags(rList);

        // sanity check
        if (rThis.getFrozenType() != rList.getFrozenType())
          throw new IllegalStateException("Mismatch between frozen types!");

        ArrayList<Integer> vOrds = new ArrayList<>();
        ArrayList<String> vTags = new ArrayList<>();
        for (int i = 0; i < ordsThis.length; i++)
        {
          if (rThis.isDynamicOrdinal(ordsThis[i]))
          {
            vOrds.add(Integer.valueOf(ordsThis[i]));
            vTags.add(tagsThis[i]);
          }
        }
        for (int i = 0; i < ordsList.length; i++)
        {
          if (rList.isDynamicOrdinal(ordsList[i]))
          {
            vOrds.add(Integer.valueOf(ordsList[i]));
            vTags.add(tagsList[i]);
          }
        }

        int[] ords = new int[vOrds.size()];
        String[] tags = new String[vTags.size()];
        for (int i = 0; i < ords.length; i++)
          ords[i] = vOrds.get(i).intValue();
        for (int i = 0; i < tags.length; i++)
          tags[i] = vTags.get(i);


        set(p, BFacets.makeEnum(BEnumRange.make(rThis.getFrozenType(), ords, tags)));
      }
    }
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final String ERROR_CLASS_TYPE = "bacnet:BacnetErrorClass";
  private static final String ERROR_CODE_TYPE = "bacnet:BacnetErrorCode";
  private static final String ABORT_REASON_TYPE = "bacnet:BacnetAbortReason";
  private static final String DEVICE_STATUS_TYPE = "bacnet:BacnetDeviceStatus";
  private static final String ENGINEERING_UNITS_TYPE = "bacnet:BacnetEngineeringUnits";
  private static final String EVENT_STATE_TYPE = "bacnet:BacnetEventState";
  private static final String EVENT_TYPE_TYPE = "bacnet:BacnetEventType";
  private static final String LIFE_SAFETY_MODE_TYPE = "bacnet:BacnetLifeSafetyMode";
  private static final String LIFE_SAFETY_OPERATION_TYPE = "bacnet:BacnetLifeSafetyOperation";
  private static final String LIFE_SAFETY_STATE_TYPE = "bacnet:BacnetLifeSafetyState";
  private static final String MAINTENANCE_TYPE = "bacnet:BacnetMaintenance";
  private static final String OBJECT_TYPE_TYPE = "bacnet:BacnetObjectType";
  private static final String PROGRAM_ERROR_TYPE = "bacnet:BacnetProgramError";
  private static final String PROPERTY_IDENTIFIER_TYPE = "bacnet:BacnetPropertyIdentifier";
  private static final String RELIABILITY_TYPE = "bacnet:BacnetReliability";
  private static final String REJECT_REASON_TYPE = "bacnet:BBacnetRejectReason";
  private static final String SILENCED_STATE_TYPE = "bacnet:BBacnetSilencedState";
  private static final String VT_CLASS_TYPE = "bacnet:BBacnetVtClass";


////////////////////////////////////////////////////////////////
//  Niagara Enumeration List
////////////////////////////////////////////////////////////////

  public static final BExtensibleEnumList niagaraEnums = new BExtensibleEnumList();

  static
  {
    niagaraEnums.setErrorCodeFacets(BFacets.makeEnum(BBacnetErrorCode.NIAGARA_ERROR_CODES_RANGE));
  }
}
