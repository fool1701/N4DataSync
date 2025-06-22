/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.*;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 17 Oct 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.PROGRAM)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.PROGRAM, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "programState",
  type = "BBacnetProgramState",
  defaultValue = "BBacnetProgramState.idle",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROGRAM_STATE, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "programChange",
  type = "BBacnetProgramRequest",
  defaultValue = "BBacnetProgramRequest.ready",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PROGRAM_CHANGE, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "statusFlags",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetStatusFlags\"))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP)")
)
/*
 is the physical point represented by this object out of service?
 if TRUE, then this point's Present_Value does NOT reflect the actual state
 of the point.
 */
@NiagaraProperty(
  name = "outOfService",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OUT_OF_SERVICE, ASN_BOOLEAN)")
)
public class BBacnetProgram
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetProgram(3495543768)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.PROGRAM), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.PROGRAM, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "programState"

  /**
   * Slot for the {@code programState} property.
   * @see #getProgramState
   * @see #setProgramState
   */
  public static final Property programState = newProperty(Flags.TRANSIENT | Flags.READONLY, BBacnetProgramState.idle, makeFacets(BBacnetPropertyIdentifier.PROGRAM_STATE, ASN_ENUMERATED));

  /**
   * Get the {@code programState} property.
   * @see #programState
   */
  public BBacnetProgramState getProgramState() { return (BBacnetProgramState)get(programState); }

  /**
   * Set the {@code programState} property.
   * @see #programState
   */
  public void setProgramState(BBacnetProgramState v) { set(programState, v, null); }

  //endregion Property "programState"

  //region Property "programChange"

  /**
   * Slot for the {@code programChange} property.
   * @see #getProgramChange
   * @see #setProgramChange
   */
  public static final Property programChange = newProperty(0, BBacnetProgramRequest.ready, makeFacets(BBacnetPropertyIdentifier.PROGRAM_CHANGE, ASN_ENUMERATED));

  /**
   * Get the {@code programChange} property.
   * @see #programChange
   */
  public BBacnetProgramRequest getProgramChange() { return (BBacnetProgramRequest)get(programChange); }

  /**
   * Set the {@code programChange} property.
   * @see #programChange
   */
  public void setProgramChange(BBacnetProgramRequest v) { set(programChange, v, null); }

  //endregion Property "programChange"

  //region Property "statusFlags"

  /**
   * Slot for the {@code statusFlags} property.
   * @see #getStatusFlags
   * @see #setStatusFlags
   */
  public static final Property statusFlags = newProperty(Flags.READONLY, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING, BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP));

  /**
   * Get the {@code statusFlags} property.
   * @see #statusFlags
   */
  public BBacnetBitString getStatusFlags() { return (BBacnetBitString)get(statusFlags); }

  /**
   * Set the {@code statusFlags} property.
   * @see #statusFlags
   */
  public void setStatusFlags(BBacnetBitString v) { set(statusFlags, v, null); }

  //endregion Property "statusFlags"

  //region Property "outOfService"

  /**
   * Slot for the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #getOutOfService
   * @see #setOutOfService
   */
  public static final Property outOfService = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.OUT_OF_SERVICE, ASN_BOOLEAN));

  /**
   * Get the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #outOfService
   */
  public boolean getOutOfService() { return getBoolean(outOfService); }

  /**
   * Set the {@code outOfService} property.
   * is the physical point represented by this object out of service?
   * if TRUE, then this point's Present_Value does NOT reflect the actual state
   * of the point.
   * @see #outOfService
   */
  public void setOutOfService(boolean v) { setBoolean(outOfService, v, null); }

  //endregion Property "outOfService"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetProgram.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetProgram()
  {
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context))
      .append(nameContext.equals(context) ? '_' : ':');
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

}
