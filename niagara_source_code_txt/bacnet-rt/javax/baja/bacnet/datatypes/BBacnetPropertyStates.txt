/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetAction;
import javax.baja.bacnet.enums.BBacnetBackupState;
import javax.baja.bacnet.enums.BBacnetBinaryPv;
import javax.baja.bacnet.enums.BBacnetDeviceStatus;
import javax.baja.bacnet.enums.BBacnetEngineeringUnits;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetFileAccessMethod;
import javax.baja.bacnet.enums.BBacnetLifeSafetyMode;
import javax.baja.bacnet.enums.BBacnetLifeSafetyOperation;
import javax.baja.bacnet.enums.BBacnetLifeSafetyState;
import javax.baja.bacnet.enums.BBacnetMaintenance;
import javax.baja.bacnet.enums.BBacnetNodeType;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetPolarity;
import javax.baja.bacnet.enums.BBacnetProgramError;
import javax.baja.bacnet.enums.BBacnetProgramRequest;
import javax.baja.bacnet.enums.BBacnetProgramState;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.enums.BBacnetRestartReason;
import javax.baja.bacnet.enums.BBacnetShedState;
import javax.baja.bacnet.enums.BBacnetSilencedState;
import javax.baja.bacnet.enums.BBacnetWriteStatus;
import javax.baja.bacnet.enums.access.BBacnetAccessCredentialDisable;
import javax.baja.bacnet.enums.access.BBacnetAccessCredentialDisableReason;
import javax.baja.bacnet.enums.access.BBacnetAccessEvent;
import javax.baja.bacnet.enums.access.BBacnetAccessZoneOccupancyState;
import javax.baja.bacnet.enums.access.BBacnetDoorAlarmState;
import javax.baja.bacnet.enums.access.BBacnetDoorSecuredStatus;
import javax.baja.bacnet.enums.access.BBacnetDoorStatus;
import javax.baja.bacnet.enums.access.BBacnetDoorValue;
import javax.baja.bacnet.enums.access.BBacnetLockStatus;
import javax.baja.bacnet.enums.lighting.BBacnetLightingInProgress;
import javax.baja.bacnet.enums.lighting.BBacnetLightingOperation;
import javax.baja.bacnet.enums.lighting.BBacnetLightingTransition;
import javax.baja.bacnet.enums.security.BBacnetSecurityLevel;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.asn.AsnConst;

/**
 * BBacnetPropertyStates represents the BACnetPropertyStates
 * choice.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 23 Apr 04
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "choice",
  type = "int",
  defaultValue = "0",
  flags = Flags.HIDDEN
)
public final class BBacnetPropertyStates
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetPropertyStates(246626917)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "choice"

  /**
   * Slot for the {@code choice} property.
   * @see #getChoice
   * @see #setChoice
   */
  public static final Property choice = newProperty(Flags.HIDDEN, 0, null);

  /**
   * Get the {@code choice} property.
   * @see #choice
   */
  public int getChoice() { return getInt(choice); }

  /**
   * Set the {@code choice} property.
   * @see #choice
   */
  public void setChoice(int v) { setInt(choice, v, null); }

  //endregion Property "choice"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPropertyStates.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static BBacnetPropertyStates makeBinaryPv(boolean value)
  {
    BBacnetPropertyStates propertyStates = new BBacnetPropertyStates();
    propertyStates.setChoice(BINARY_VALUE_TAG);
    propertyStates.add(BINARY_VALUE_SLOT_NAME, BBacnetBinaryPv.make(value));
    return propertyStates;
  }

  public static BBacnetPropertyStates makeUnsigned(long value)
  {
    BBacnetPropertyStates propertyStates = new BBacnetPropertyStates();
    propertyStates.setChoice(UNSIGNED_VALUE_TAG);
    propertyStates.add(UNSIGNED_VALUE_SLOT_NAME, BBacnetUnsigned.make(value));
    return propertyStates;
  }

////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;
    BComplex parent = getParent();
    if (parent != null)
      parent.asComponent().changed(getPropertyInParent(), cx);
    // vfixx: throw changed w/ GCC context?
  }

  /**
   * Callback when the component enters the subscribed state.
   */
  public final void subscribed()
  {
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childSubscribed(this);
  }

  /**
   * Callback when the component leaves the subscribed state.
   */
  public final void unsubscribed()
  {
    BBacnetVirtualProperty vp = BacnetVirtualUtil.getVirtualProperty(this);
    if (vp != null) vp.childUnsubscribed(this);
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getAppliedCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this))
      return getParent().asComponent().getAppliedCategoryMask();
    return super.getAppliedCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BCategoryMask getCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getCategoryMask();
    return super.getCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public BPermissions getPermissions(Context cx)
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getPermissions(cx);
    return super.getPermissions(cx);
  }

  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getTag());
    int choice = getChoice();
    switch (choice)
    {
      case BOOLEAN_VALUE_TAG:
        sb.append((BBoolean)get(BOOLEAN_VALUE_SLOT_NAME));
        break;
      case UNSIGNED_VALUE_TAG:
        sb.append((BBacnetUnsigned)get(UNSIGNED_VALUE_SLOT_NAME));
        break;
      case BINARY_VALUE_TAG:
      case EVENT_TYPE_TAG:
      case POLARITY_TAG:
      case PROGRAM_CHANGE_TAG:
      case PROGRAM_STATE_TAG:
      case REASON_FOR_HALT_TAG:
      case RELIABILITY_TAG:
      case STATE_TAG:
      case SYSTEM_STATUS_TAG:
      case UNITS_TAG:
      case LIFE_SAFETY_MODE_TAG:
      case LIFE_SAFETY_STATE_TAG:
      case RESTART_REASON_TAG:
      case DOOR_ALARM_TAG:
      case ACTION_TAG:
      case DOOR_SECURED_STATUS_TAG:
      case DOOR_STATUS_TAG:
      case DOOR_VALUE_TAG:
      case FILE_ACCESS_METHOD_TAG:
      case LOCK_STATUS_TAG:
      case LIFE_SAFETY_OPERATION_TAG:
      case MAINTENANCE_TAG:
      case NODE_TYPE_TAG:
      case NOTIFY_TYPE_TAG:
      case SECURITY_LEVEL_TAG:
      case SHED_STATE_TAG:
      case SILENCED_STATE_TAG:
      case RESERVED29_TAG:
      case ACCESS_EVENT_TAG:
      case ZONE_OCCUPANCY_TAG:
      case ACCESS_CREDENTIAL_DISABLE_REASON_TAG:
      case ACCESS_CREDENTIAL_DISABLE_TAG:
      case AUTHENTICATION_STATUS_TAG:
      case BACKUP_STATE_TAG:
      case WRITE_STATUS_TAG:
      case LIGHTING_IN_PROGRESS_TAG:
      case LIGHTING_OPERATION_TAG:
      case LIGHTING_TRANSITION_TAG:
        sb.append((BEnum)get(slotNames[choice]));
        break;
      default:
        break;
    }
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    int choice = getChoice();
    switch (choice)
    {
      case BOOLEAN_VALUE_TAG:
        out.writeBoolean(BOOLEAN_VALUE_TAG, (BBoolean)get(BOOLEAN_VALUE_SLOT_NAME));
        break;
      case UNSIGNED_VALUE_TAG:
        out.writeUnsigned(UNSIGNED_VALUE_TAG, (BBacnetUnsigned)get(UNSIGNED_VALUE_SLOT_NAME));
        break;
      case BINARY_VALUE_TAG:
      case EVENT_TYPE_TAG:
      case POLARITY_TAG:
      case PROGRAM_CHANGE_TAG:
      case PROGRAM_STATE_TAG:
      case REASON_FOR_HALT_TAG:
      case RELIABILITY_TAG:
      case STATE_TAG:
      case SYSTEM_STATUS_TAG:
      case UNITS_TAG:
      case LIFE_SAFETY_MODE_TAG:
      case LIFE_SAFETY_STATE_TAG:
      case RESTART_REASON_TAG:
      case DOOR_ALARM_TAG:
      case ACTION_TAG:
      case DOOR_SECURED_STATUS_TAG:
      case DOOR_STATUS_TAG:
      case DOOR_VALUE_TAG:
      case FILE_ACCESS_METHOD_TAG:
      case LOCK_STATUS_TAG:
      case LIFE_SAFETY_OPERATION_TAG:
      case MAINTENANCE_TAG:
      case NODE_TYPE_TAG:
      case NOTIFY_TYPE_TAG:
      case SECURITY_LEVEL_TAG:
      case SHED_STATE_TAG:
      case SILENCED_STATE_TAG:
      case RESERVED29_TAG:
      case ACCESS_EVENT_TAG:
      case ZONE_OCCUPANCY_TAG:
      case ACCESS_CREDENTIAL_DISABLE_REASON_TAG:
      case ACCESS_CREDENTIAL_DISABLE_TAG:
      case AUTHENTICATION_STATUS_TAG:
      case BACKUP_STATE_TAG:
      case WRITE_STATUS_TAG:
      case LIGHTING_IN_PROGRESS_TAG:
      case LIGHTING_OPERATION_TAG:
      case LIGHTING_TRANSITION_TAG:
        out.writeEnumerated(choice, (BEnum)get(slotNames[choice]));
        break;

      default:
        break;
    }
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    int tag = in.peekTag();
    if ((tag < 0) || (tag > MAX_TAG))
      throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);

    setInt(choice, tag, noWrite);

    removeAll(noWrite);

    switch (tag)
    {
      case BOOLEAN_VALUE_TAG:
        add(BOOLEAN_VALUE_SLOT_NAME, BBoolean.make(in.readBoolean(tag)), noWrite);
        break;
      case BINARY_VALUE_TAG:
        add(BINARY_VALUE_SLOT_NAME, BBacnetBinaryPv.make(in.readEnumerated(tag)), noWrite);
        break;
      case EVENT_TYPE_TAG:
        add("eventType", BDynamicEnum.make(in.readEnumerated(tag), BBacnetEventType.DEFAULT.getRange()), noWrite);
        break;
      case POLARITY_TAG:
        add("polarity", BBacnetPolarity.make(in.readEnumerated(tag)), noWrite);
        break;
      case PROGRAM_CHANGE_TAG:
        add("programChange", BBacnetProgramRequest.make(in.readEnumerated(tag)), noWrite);
        break;
      case PROGRAM_STATE_TAG:
        add("programState", BBacnetProgramState.make(in.readEnumerated(tag)), noWrite);
        break;
      case REASON_FOR_HALT_TAG:
        add("reasonForHalt", BDynamicEnum.make(in.readEnumerated(tag), BBacnetProgramError.DEFAULT.getRange()), noWrite);
        break;
      case RELIABILITY_TAG:
        add("reliability", BDynamicEnum.make(in.readEnumerated(tag), BBacnetReliability.DEFAULT.getRange()), noWrite);
        break;
      case STATE_TAG:
        add("state", BDynamicEnum.make(in.readEnumerated(tag), BBacnetEventState.DEFAULT.getRange()), noWrite);
        break;
      case SYSTEM_STATUS_TAG:
        add("systemStatus", BDynamicEnum.make(in.readEnumerated(tag), BBacnetDeviceStatus.DEFAULT.getRange()), noWrite);
        break;
      case UNITS_TAG:
        add("units", BDynamicEnum.make(in.readEnumerated(tag), BBacnetEngineeringUnits.DEFAULT.getRange()), noWrite);
        break;
      case UNSIGNED_VALUE_TAG:
        add(UNSIGNED_VALUE_SLOT_NAME, BBacnetUnsigned.make(in.readUnsignedInteger(tag)), noWrite);
        break;
      case LIFE_SAFETY_MODE_TAG:
        add("lifeSafetyMode", BDynamicEnum.make(in.readEnumerated(tag), BBacnetLifeSafetyMode.DEFAULT.getRange()), noWrite);
        break;
      case LIFE_SAFETY_STATE_TAG:
        add("lifeSafetyState", BDynamicEnum.make(in.readEnumerated(tag), BBacnetLifeSafetyState.DEFAULT.getRange()), noWrite);
        break;
      case RESTART_REASON_TAG:
        add("restartReason", BDynamicEnum.make(in.readEnumerated(tag), BBacnetRestartReason.DEFAULT.getRange()), noWrite);
        break;
      case DOOR_ALARM_TAG:
        addEnum(BBacnetDoorAlarmState.DEFAULT, in, tag);
        break;
      case ACTION_TAG:
        addEnum(BBacnetAction.DEFAULT, in, tag);
        break;
      case DOOR_SECURED_STATUS_TAG:
        addEnum(BBacnetDoorSecuredStatus.DEFAULT, in, tag);
        break;
      case DOOR_STATUS_TAG:
        addEnum(BBacnetDoorStatus.DEFAULT, in, tag);
        break;
      case DOOR_VALUE_TAG:
        addEnum(BBacnetDoorValue.DEFAULT, in, tag);
        break;
      case FILE_ACCESS_METHOD_TAG:
        addEnum(BBacnetFileAccessMethod.DEFAULT, in, tag);
        break;
      case LOCK_STATUS_TAG:
        addEnum(BBacnetLockStatus.DEFAULT, in, tag);
        break;
      case LIFE_SAFETY_OPERATION_TAG:
        addEnum(BBacnetLifeSafetyOperation.DEFAULT, in, tag);
        break;
      case MAINTENANCE_TAG:
        addEnum(BBacnetMaintenance.DEFAULT, in, tag);
        break;
      case NODE_TYPE_TAG:
        addEnum(BBacnetNodeType.DEFAULT, in, tag);
        break;
      case NOTIFY_TYPE_TAG:
        addEnum(BBacnetNotifyType.DEFAULT, in, tag);
        break;
      case SECURITY_LEVEL_TAG:
        addEnum(BBacnetSecurityLevel.DEFAULT, in, tag);
        break;
      case SHED_STATE_TAG:
        addEnum(BBacnetShedState.DEFAULT, in, tag);
        break;
      case SILENCED_STATE_TAG:
        addEnum(BBacnetSilencedState.DEFAULT, in, tag);
        break;
      case ACCESS_EVENT_TAG:
        addEnum(BBacnetAccessEvent.DEFAULT, in, tag);
        break;
      case ZONE_OCCUPANCY_TAG:
        addEnum(BBacnetAccessZoneOccupancyState.DEFAULT, in, tag);
        break;
      case ACCESS_CREDENTIAL_DISABLE_REASON_TAG:
        addEnum(BBacnetAccessCredentialDisableReason.DEFAULT, in, tag);
        break;
      case ACCESS_CREDENTIAL_DISABLE_TAG:
        addEnum(BBacnetAccessCredentialDisable.DEFAULT, in, tag);
        break;
      case BACKUP_STATE_TAG:
        addEnum(BBacnetBackupState.DEFAULT, in, tag);
        break;
      case WRITE_STATUS_TAG:
        addEnum(BBacnetWriteStatus.DEFAULT, in, tag);
        break;
      case LIGHTING_IN_PROGRESS_TAG:
        addEnum(BBacnetLightingInProgress.DEFAULT, in, tag);
        break;
      case LIGHTING_OPERATION_TAG:
        addEnum(BBacnetLightingOperation.DEFAULT, in, tag);
        break;
      case LIGHTING_TRANSITION_TAG:
        addEnum(BBacnetLightingTransition.DEFAULT, in, tag);
        break;

      default:
        in.skipTag();
        break;
    }

  }

  private final void addEnum(BEnum defEnum, AsnInput in, int tag)
    throws AsnException
  {
    add(slotNames[tag], BDynamicEnum.make(in.readEnumerated(tag), defEnum.getRange()), noWrite);
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetPropertyStates", 2);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


/////////////////////////////////////////////////////////////////
//  Constants
/////////////////////////////////////////////////////////////////

  private static final int MAX_TAG = 254;

  public static final int BOOLEAN_VALUE_TAG = 0;
  public static final int BINARY_VALUE_TAG = 1;
  public static final int EVENT_TYPE_TAG = 2;
  public static final int POLARITY_TAG = 3;
  public static final int PROGRAM_CHANGE_TAG = 4;
  public static final int PROGRAM_STATE_TAG = 5;
  public static final int REASON_FOR_HALT_TAG = 6;
  public static final int RELIABILITY_TAG = 7;
  public static final int STATE_TAG = 8;
  public static final int SYSTEM_STATUS_TAG = 9;
  public static final int UNITS_TAG = 10;
  public static final int UNSIGNED_VALUE_TAG = 11;
  public static final int LIFE_SAFETY_MODE_TAG = 12;
  public static final int LIFE_SAFETY_STATE_TAG = 13;
  public static final int RESTART_REASON_TAG = 14;
  public static final int DOOR_ALARM_TAG = 15;
  public static final int ACTION_TAG = 16;
  public static final int DOOR_SECURED_STATUS_TAG = 17;
  public static final int DOOR_STATUS_TAG = 18;
  public static final int DOOR_VALUE_TAG = 19;
  public static final int FILE_ACCESS_METHOD_TAG = 20;
  public static final int LOCK_STATUS_TAG = 21;
  public static final int LIFE_SAFETY_OPERATION_TAG = 22;
  public static final int MAINTENANCE_TAG = 23;
  public static final int NODE_TYPE_TAG = 24;
  public static final int NOTIFY_TYPE_TAG = 25;
  public static final int SECURITY_LEVEL_TAG = 26;
  public static final int SHED_STATE_TAG = 27;
  public static final int SILENCED_STATE_TAG = 28;
  public static final int RESERVED29_TAG = 29;
  public static final int ACCESS_EVENT_TAG = 30;
  public static final int ZONE_OCCUPANCY_TAG = 31;
  public static final int ACCESS_CREDENTIAL_DISABLE_REASON_TAG = 32;
  public static final int ACCESS_CREDENTIAL_DISABLE_TAG = 33;
  public static final int AUTHENTICATION_STATUS_TAG = 34;

  // from Addendum 135-2008n, for Backup & Restore
  public static final int BACKUP_STATE_TAG = 36;
  public static final int WRITE_STATUS_TAG = 37;
  public static final int LIGHTING_IN_PROGRESS_TAG = 38;
  public static final int LIGHTING_OPERATION_TAG = 39;
  public static final int LIGHTING_TRANSITION_TAG = 40;

  private static final int MAX_DEFINED_CHOICE = LIGHTING_TRANSITION_TAG;
  private static final int MAX_ASHRAE_CHOICE = 63;

  private static final Lexicon lex = Lexicon.make("bacnet");

  private static String[] tags = new String[]
    {
/* 0 */   lex.getText("BacnetPropertyStates.booleanValue"),
/* 1 */   lex.getText("BacnetPropertyStates.binaryValue"),
/* 2 */   lex.getText("BacnetPropertyStates.eventType"),
/* 3 */   lex.getText("BacnetPropertyStates.polarity"),
/* 4 */   lex.getText("BacnetPropertyStates.programChange"),
/* 5 */   lex.getText("BacnetPropertyStates.programState"),
/* 6 */   lex.getText("BacnetPropertyStates.reasonForHalt"),
/* 7 */   lex.getText("BacnetPropertyStates.reliability"),
/* 8 */   lex.getText("BacnetPropertyStates.state"),
/* 9 */   lex.getText("BacnetPropertyStates.systemStatus"),
/* 10 */  lex.getText("BacnetPropertyStates.units"),
/* 11 */  lex.getText("BacnetPropertyStates.unsignedValue"),
/* 12 */  lex.getText("BacnetPropertyStates.lifeSafetyMode"),
/* 13 */  lex.getText("BacnetPropertyStates.lifeSafetyState"),
/* 14 */  lex.getText("BacnetPropertyStates.restartReason"),
/* 15 */  lex.getText("BacnetPropertyStates.doorAlarmState"),
/* 16 */  lex.getText("BacnetPropertyStates.action"),
/* 17 */  lex.getText("BacnetPropertyStates.doorSecuredStatus"),
/* 18 */  lex.getText("BacnetPropertyStates.doorStatus"),
/* 19 */  lex.getText("BacnetPropertyStates.doorValue"),
/* 20 */  lex.getText("BacnetPropertyStates.fileAccessMethod"),
/* 21 */  lex.getText("BacnetPropertyStates.lockStatus"),
/* 22 */  lex.getText("BacnetPropertyStates.lifeSafetyOperation"),
/* 23 */  lex.getText("BacnetPropertyStates.maintenance"),
/* 24 */  lex.getText("BacnetPropertyStates.nodeType"),
/* 25 */  lex.getText("BacnetPropertyStates.notifyType"),
/* 26 */  lex.getText("BacnetPropertyStates.securityLevel"),
/* 27 */  lex.getText("BacnetPropertyStates.shedState"),
/* 28 */  lex.getText("BacnetPropertyStates.silencedState"),
/* 29 */  lex.getText("BacnetPropertyStates.reserved29"),
/* 30 */  lex.getText("BacnetPropertyStates.accessEvent"),
/* 31 */  lex.getText("BacnetPropertyStates.zoneOccupancyState"),
/* 32 */  lex.getText("BacnetPropertyStates.accessCredentialDisableReason"),
/* 33 */  lex.getText("BacnetPropertyStates.accessCredentialDisable"),
/* 34 */  lex.getText("BacnetPropertyStates.authenticationStatus"),
/* 35 */  lex.getText("BacnetPropertyStates.reserved35"),
/* 36 */  lex.getText("BacnetPropertyStates.backupState"),
/* 37 */  lex.getText("BacnetPropertyStates.writeStatus"),
/* 38 */  lex.getText("BacnetPropertyStates.lightingInProgress"),
/* 39 */  lex.getText("BacnetPropertyStates.lightingOperation"),
/* 40 */  lex.getText("BacnetPropertyStates.lightingTransition"),
//---------------------------------------------------------------------
/* 41 */  lex.getText("BacnetPropertyStates.ashrae"),
/* 42 */  lex.getText("BacnetPropertyStates.proprietary"),
/* 43 */  lex.getText("BacnetPropertyStates.invalid")
    };

  private static final int ASHRAE_CHOICE_INDEX = 41;
  private static final int PROPRIETARY_CHOICE_INDEX = 42;
  private static final int INVALID_CHOICE_INDEX = 43;

  public static final String BOOLEAN_VALUE_SLOT_NAME = "booleanValue";
  public static final String BINARY_VALUE_SLOT_NAME = "binaryValue";
  public static final String UNSIGNED_VALUE_SLOT_NAME = "unsignedValue";

  private static final String[] slotNames = {
    /* 0 */   BOOLEAN_VALUE_SLOT_NAME,
    /* 1 */   BINARY_VALUE_SLOT_NAME,
    /* 2 */   "eventType",
    /* 3 */   "polarity",
    /* 4 */   "programChange",
    /* 5 */   "programState",
    /* 6 */   "reasonForHalt",
    /* 7 */   "reliability",
    /* 8 */   "state",
    /* 9 */   "systemStatus",
    /* 10 */  "units",
    /* 11 */  UNSIGNED_VALUE_SLOT_NAME,
    /* 12 */  "lifeSafetyMode",
    /* 13 */  "lifeSafetyState",
    /* 14 */  "restartReason",
    /* 15 */  "doorAlarmState",
    /* 16 */  "action",
    /* 17 */  "doorSecuredStatus",
    /* 18 */  "doorStatus",
    /* 19 */  "doorValue",
    /* 20 */  "fileAccessMethod",
    /* 21 */  "lockStatus",
    /* 22 */  "lifeSafetyOperation",
    /* 23 */  "maintenance",
    /* 24 */  "nodeType",
    /* 25 */  "notifyType",
    /* 26 */  "securityLevel",
    /* 27 */  "shedState",
    /* 28 */  "silencedState",
    /* 29 */  "reserved29",
    /* 30 */  "accessEvent",
    /* 31 */  "zoneOccupancyState",
    /* 32 */  "accessCredentialDisableReason",
    /* 33 */  "accessCredentialDisable",
    /* 34 */  "authenticationStatus",
    /* 35 */  "reserved35",
    /* 36 */  "backupState",
    /* 37 */  "reserved37",
    /* 38 */  "lightingInProgress",
    /* 39 */  "lightingOperation",
    /* 40 */  "lightingTransition",

  };


  private String getTag()
  {
    int ch = getChoice();
    if ((ch < 0) || (ch > 254)) ch = INVALID_CHOICE_INDEX;
    if (ch <= MAX_DEFINED_CHOICE) return tags[ch];
    ch = (ch <= MAX_ASHRAE_CHOICE) ? ASHRAE_CHOICE_INDEX : PROPRIETARY_CHOICE_INDEX;
    return tags[ch];
  }
}
