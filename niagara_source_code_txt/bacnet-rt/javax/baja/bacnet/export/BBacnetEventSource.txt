/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.logging.Logger;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.BOffnormalAlgorithm;
import javax.baja.alarm.ext.offnormal.BOutOfRangeAlgorithm;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.alarm.BBacnetStatusAlgorithm;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDestination;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BControlPoint;
import javax.baja.license.Feature;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.server.BBacnetExportFolder;
import com.tridium.bacnet.stack.server.BBacnetExportTable;
import com.tridium.bacnet.stack.server.BBacnetServerLayer;
import com.tridium.bacnet.stack.server.BEventHandler;
import com.tridium.bacnet.stack.server.BHashedEventBuffer;

/**
 * BBacnetEventSource is the base class for all BACnet export descriptors
 * that represent event-initiating BACnet objects.  A particular descriptor
 * class will implement the class regardless of whether the specific
 * object which it exposes actually supports alarming.  If the object
 * is not configured for alarming, the event source will simply return
 * null for the requested values.
 * <p>
 * This interface is used by the server layer to respond properly to
 * GetEventInformation requests.
 *
 * @author Craig Gemmill on 11 Aug 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 the status for Niagara server-side behavior.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Provides a description of a fault with server-side behavior.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 indicates whether (TRUE) or not (FALSE) intrinsic/algorithmic reporting is enabled
 */
@NiagaraProperty(
  name = "eventDetectionEnable",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.SUMMARY | Flags.DEFAULT_ON_CLONE
)
/*
 indicates whether (TRUE) or not (FALSE) the object was dynamically created.
 */
@NiagaraProperty(
  name = "dynamicallyCreated",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.SUMMARY | Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "bacnetStatusFlags",
  type = "BBacnetBitString",
  defaultValue = "BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength(\"BacnetStatusFlags\"))",
  facets = @Facet("BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS")
)
abstract public class BBacnetEventSource
  extends BComponent
  implements BIBacnetExportObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetEventSource(2380156164)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * the status for Niagara server-side behavior.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a description of a fault with server-side behavior.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "eventDetectionEnable"

  /**
   * Slot for the {@code eventDetectionEnable} property.
   * indicates whether (TRUE) or not (FALSE) intrinsic/algorithmic reporting is enabled
   * @see #getEventDetectionEnable
   * @see #setEventDetectionEnable
   */
  public static final Property eventDetectionEnable = newProperty(Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, true, null);

  /**
   * Get the {@code eventDetectionEnable} property.
   * indicates whether (TRUE) or not (FALSE) intrinsic/algorithmic reporting is enabled
   * @see #eventDetectionEnable
   */
  public boolean getEventDetectionEnable() { return getBoolean(eventDetectionEnable); }

  /**
   * Set the {@code eventDetectionEnable} property.
   * indicates whether (TRUE) or not (FALSE) intrinsic/algorithmic reporting is enabled
   * @see #eventDetectionEnable
   */
  public void setEventDetectionEnable(boolean v) { setBoolean(eventDetectionEnable, v, null); }

  //endregion Property "eventDetectionEnable"

  //region Property "dynamicallyCreated"

  /**
   * Slot for the {@code dynamicallyCreated} property.
   * indicates whether (TRUE) or not (FALSE) the object was dynamically created.
   * @see #getDynamicallyCreated
   * @see #setDynamicallyCreated
   */
  public static final Property dynamicallyCreated = newProperty(Flags.SUMMARY | Flags.DEFAULT_ON_CLONE | Flags.HIDDEN | Flags.READONLY, false, null);

  /**
   * Get the {@code dynamicallyCreated} property.
   * indicates whether (TRUE) or not (FALSE) the object was dynamically created.
   * @see #dynamicallyCreated
   */
  public boolean getDynamicallyCreated() { return getBoolean(dynamicallyCreated); }

  /**
   * Set the {@code dynamicallyCreated} property.
   * indicates whether (TRUE) or not (FALSE) the object was dynamically created.
   * @see #dynamicallyCreated
   */
  public void setDynamicallyCreated(boolean v) { setBoolean(dynamicallyCreated, v, null); }

  //endregion Property "dynamicallyCreated"

  //region Property "bacnetStatusFlags"

  /**
   * Slot for the {@code bacnetStatusFlags} property.
   * @see #getBacnetStatusFlags
   * @see #setBacnetStatusFlags
   */
  public static final Property bacnetStatusFlags = newProperty(0, BBacnetBitString.emptyBitString(BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), BacnetBitStringUtil.BACNET_STATUS_FLAGS_FACETS);

  /**
   * Get the {@code bacnetStatusFlags} property.
   * @see #bacnetStatusFlags
   */
  public BBacnetBitString getBacnetStatusFlags() { return (BBacnetBitString)get(bacnetStatusFlags); }

  /**
   * Set the {@code bacnetStatusFlags} property.
   * @see #bacnetStatusFlags
   */
  public void setBacnetStatusFlags(BBacnetBitString v) { set(bacnetStatusFlags, v, null); }

  //endregion Property "bacnetStatusFlags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventSource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Started.
   * Initialize the point name subscriber and check the export configuration.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();

    // First check for fatal faults.
    checkFatalFault();

    if (!getEventDetectionEnable())
    {
      removeEventFromEventBuffer();
    }
  }

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning())
    {
      return;
    }

    if (p.equals(eventDetectionEnable))
    {
      if (!getEventDetectionEnable())
      {
        removeEventFromEventBuffer();
      }
    }
    if (p.equals(bacnetStatusFlags))
    {
      bacnetStatusFlagChanged();
    }
  }

  /**
   * When 'bacnet Status flag' property is changed, if BBacnetStatusAlarm ext is added to the point,
   * notify the extension about the property changed.
   */
  protected void bacnetStatusFlagChanged()
  {
    BControlPoint point = getPoint();
    BAlarmSourceExt alarmExt = setStatusFlagsOnBAcnetStatusAlgo(point);

    if (null != alarmExt)
    {
      //commenting the below line as this was not right. Needs design change for supporting StatusFlag alarms
      //alarmExt.onExecute((BStatusValue)point.getOutStatusValue().newCopy(true), null);
    }
  }

  private BAlarmSourceExt setStatusFlagsOnBAcnetStatusAlgo(BControlPoint point)
  {
    if (null != point)
    {
      BAlarmSourceExt[] c = point.getChildren(BAlarmSourceExt.class);
      BAlarmSourceExt alarmSourceExt;
      for (int i = 0; i < c.length; i++)
      {
        alarmSourceExt = c[i];
        if (alarmSourceExt.getOffnormalAlgorithm().getType().is(BBacnetStatusAlgorithm.TYPE))
        {
          ((BBacnetStatusAlgorithm)alarmSourceExt.getOffnormalAlgorithm()).setStausFlags(getBacnetStatusFlags());
          return alarmSourceExt;
        }
      }
    }

    return null;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Check if the exported object's state is valid.
   */
  void checkValid()
  {
  }

  /**
   * Get the containing export folder if any.
   *
   * @return bacnetExportFolder
   */
  protected BBacnetExportFolder getSvo()
  {
    BComplex parent = this;
    while (parent != null)
    {
      if (parent instanceof BBacnetExportFolder)
      {
        return (BBacnetExportFolder) parent;
      }
      parent = parent.getParent();
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// BBacnetEventSource
////////////////////////////////////////////////////////////////

  /**
   * Is the given alarm source ext a valid extension for
   * exporting BACnet alarm properties?  This determines if the
   * given alarm source extension follows the appropriate algorithm
   * defined for the intrinsic alarming of a particular object
   * type as required by the BACnet specification.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  abstract public boolean isValidAlarmExt(BIAlarmSource ext);

  /**
   * inhibit the event notification based on eventDetectionEnable flag
   *
   * @deprecated since Niagara 4.10u2: this method is no longer; the alarmInhibit property of a
   * BAlarmSourceExt should not be based on the eventDetectionEnable property but on the
   * eventAlgorithmInhibit and/or eventAlgorithmInhibitRef properties.
   */
  @Deprecated
  abstract protected void updateAlarmInhibit();

  /**
   * Is this object currently configured to support event initiation?
   * This will return false if the exported object does not have an
   * appropriate alarm extension configured to allow Bacnet event initiation.
   *
   * @return true if this object can initiate Bacnet events.
   */
  abstract public boolean isEventInitiationEnabled();

  /**
   * Get the object identifier.
   *
   * @return the objectId, or null if event initiation is not enabled.
   */
  @Override
  abstract public BBacnetObjectIdentifier getObjectId();

  /**
   * Get the current Event_State of the object.
   * If this object does not support event initiation,
   * this will return null.
   * If the object supports event initiation, this will return
   * an appropriate event state, either as a <code>BBacnetEventState</code>,
   * or as a BDynamicEnum if it uses additionally defined event states.
   *
   * @return the object's event state, or null.
   */
  abstract public BEnum getEventState();

  /**
   * Get the point the descriptor is pointing to.
   * If the Descriptor doesn't have point reference, then return null
   * @return the control point
   */
  abstract public BControlPoint getPoint();

  /**
   * Get the current Acked_Transitions property of the object.
   * If this object does not support event initiation,
   * this will return null.
   * If the object supports event initiation, this will return
   * a bit string representing the currently acknowledged transitions.
   *
   * @return the object's acknowledged transitions, or null.
   */
  abstract public BBacnetBitString getAckedTransitions();

  /**
   * Get the event time stamps.
   *
   * @return the event time stamps, or null if event initiation is not enabled.
   */
  abstract public BBacnetTimeStamp[] getEventTimeStamps();

  /**
   * Get the notify type.
   *
   * @return the notify type, or null if event initiation is not enabled.
   */
  abstract public BBacnetNotifyType getNotifyType();

  /**
   * Get the event enable bits.
   *
   * @return the event enable bits, or null if event initiation is not enabled.
   */
  abstract public BBacnetBitString getEventEnable();

  /**
   * Get the event priorities.
   *
   * @return the event priorities, or null if event initiation is not enabled.
   */
  abstract public int[] getEventPriorities();

  /**
   * Get the Notification Class object for this event source.
   *
   * @return the <code>BacnetNotificationClassDescriptor</code> for this object.
   */
  abstract public BBacnetNotificationClassDescriptor getNotificationClass();

  /**
   * Get the BACnetEventType reported by this object.
   */
  abstract public BEnum getEventType();

////////////////////////////////////////////////////////////////
// Fatal Fault
////////////////////////////////////////////////////////////////

  private boolean fatalFault = false;

  /**
   * Is this component in a fatal fault condition?
   */
  @Override
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  private void checkFatalFault()
  {
    BBacnetExportTable exports = null;
    BLocalBacnetDevice local = null;
    BBacnetNetwork network = null;

    // short circuit if already in fatal fault
    if (fatalFault) return;

    // find local device
    BComplex parent = getParent();
    while (parent != null)
    {
      if (parent instanceof BBacnetExportTable)
      {
        exports = (BBacnetExportTable)parent;
      }
      else if (parent instanceof BLocalBacnetDevice)
      {
        local = (BLocalBacnetDevice)parent;
        break;
      }
      parent = parent.getParent();
    }

    // check mounted in local device
    if ((exports == null) || (local == null))
    {
      fatalFault = true;
      setFaultCause("Not under LocalBacnetDevice Export Table");
      return;
    }

    // check local device fatal fault
    if (local.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("LocalDevice fault: " + local.getFaultCause());
      return;
    }

    // check mounted in network
    network = (BBacnetNetwork)local.getParent();
    if (network == null)
    {
      fatalFault = true;
      setFaultCause("Not under BacnetNetwork");
      return;
    }

    // check network fatal fault
    if (network.isFatalFault())
    {
      fatalFault = true;
      setFaultCause("Network fault: " + network.getFaultCause());
      return;
    }

    // check license
    Feature feature = network.getLicenseFeature();
    boolean serverLicensed = feature.getb("export", false);
    if (!serverLicensed)
    {
      fatalFault = true;
      setFaultCause("Server capability not licensed");
      return;
    }

    // no fatal faults
    setFaultCause("");
  }

  @SuppressWarnings("fallthrough")
  protected PropertyValue readEventMessageTexts(int ndx)
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS,
        ndx,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    BBacnetObjectIdentifier deviceId = BBacnetNetwork.localDevice().getObjectId();
    BEventHandler eventHandler =
      ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler();

    AsnOutputStream out = new AsnOutputStream();
    boolean used = true;
    switch (ndx)
    {
      case 0:
        out.writeUnsignedInteger(MESSAGE_TEXTS_COUNT);
        break;

      case NOT_USED:
        used = false;

      case 1:
        out.writeCharacterString(
          readEventMessageTextFromEventBuffer(
            eventHandler,
            /* eventStateOrdinal */ BBacnetEventState.OFFNORMAL,
            deviceId,
            /* msgTextKey */ BacnetAlarmConst.BAC_TO_OFFNORMAL_MSG_TEXT));
        if (used)
        {
          break;
        }

      case 2:
        out.writeCharacterString(
          readEventMessageTextFromEventBuffer(
            eventHandler,
            /* eventStateOrdinal */ BBacnetEventState.FAULT,
            deviceId,
            /* msgTextKey */ BacnetAlarmConst.BAC_TO_FAULT_MSG_TEXT));
        if (used)
        {
          break;
        }

      case 3:
        out.writeCharacterString(
          readEventMessageTextFromEventBuffer(
            eventHandler,
            /* eventStateOrdinal */ BBacnetEventState.NORMAL,
            deviceId,
            /* msgTextKey */ BacnetAlarmConst.BAC_TO_NORMAL_MSG_TEXT));
        if (used)
        {
          break;
        }
    }

    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS,
      ndx,
      out.toByteArray());
  }

  @SuppressWarnings("fallthrough")
  protected PropertyValue readEventMessageTextsConfig(
    String toOffnormalText,
    String toFaultText,
    String toNormalText,
    int ndx)
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG,
        ndx,
        new NErrorType(
          BBacnetErrorClass.PROPERTY,
          BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    AsnOutputStream out = new AsnOutputStream();
    boolean used = true;

    switch (ndx)
    {
      case 0:
        out.writeUnsignedInteger(MESSAGE_TEXTS_COUNT);
        break;

      case NOT_USED:
        used = false;

      case 1:
        out.writeCharacterString(toOffnormalText);
        if (used)
        {
          break;
        }

      case 2:
        out.writeCharacterString(toFaultText);
        if (used)
        {
          break;
        }

      case 3:
        out.writeCharacterString(toNormalText);
    }

    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG,
      ndx,
      out.toByteArray());
  }

  protected static ErrorType writeEventMessageTextsConfig(int ndx, byte[] val, BAlarmSourceExt almExt)
    throws AsnException
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    switch (ndx)
    {
      case 0:
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

      case NOT_USED:
        BBacnetArray textsConfig = new BBacnetArray(BString.TYPE, 3);
        AsnUtil.fromAsn(BacnetConst.ASN_ANY, val, textsConfig);

        Context context = BLocalBacnetDevice.getBacnetContext();
        almExt.set(
          BAlarmSourceExt.toOffnormalText,
          BFormat.make(textsConfig.getElement(1).toString(null)),
          context);
        almExt.set(
          BAlarmSourceExt.toFaultText,
          BFormat.make(textsConfig.getElement(2).toString(null)),
          context);
        almExt.set(
          BAlarmSourceExt.toNormalText,
          BFormat.make(textsConfig.getElement(3).toString(null)),
          context);
        resetOutOfRangeTexts(almExt);
        break;

      case 1:
        almExt.set(
          BAlarmSourceExt.toOffnormalText,
          BFormat.make(AsnUtil.fromAsnCharacterString(val)),
          BLocalBacnetDevice.getBacnetContext());
        resetOutOfRangeTexts(almExt);
        break;

      case 2:
        almExt.set(
          BAlarmSourceExt.toFaultText,
          BFormat.make(AsnUtil.fromAsnCharacterString(val)),
          BLocalBacnetDevice.getBacnetContext());
        break;

      case 3:
        almExt.set(
          BAlarmSourceExt.toNormalText,
          BFormat.make(AsnUtil.fromAsnCharacterString(val)),
          BLocalBacnetDevice.getBacnetContext());
        break;
    }

    return null;
  }

  /**
   * If the offnormal algorithm is an outOfRange algorithm, reset the high and low limit text
   * because these would overwrite the MSG_TEXT alarm data field and the toOffnormalText value
   * written through BACnet would not be used.
   */
  protected static void resetOutOfRangeTexts(BAlarmSourceExt almExt)
  {
    BOffnormalAlgorithm offnormal = almExt.getOffnormalAlgorithm();
    if (offnormal instanceof BOutOfRangeAlgorithm)
    {
      BOutOfRangeAlgorithm outOfRange = (BOutOfRangeAlgorithm) offnormal;
      outOfRange.setHighLimitText(BFormat.DEFAULT);
      outOfRange.setLowLimitText(BFormat.DEFAULT);
    }
  }

  /**
   * Read the Event_Time_Stamps property from our
   * BAlarmSourceExt.
   */
  @SuppressWarnings("fallthrough")
  protected PropertyValue readEventTimeStamps(
    BAbsTime lastOffnormalTime,
    BAbsTime lastFaultTime,
    BAbsTime lastToNormalTime,
    int ndx)
  {
    if (ndx < NOT_USED || ndx > MESSAGE_TEXTS_COUNT)
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_TIME_STAMPS,
        ndx,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    BBacnetObjectIdentifier deviceId = BBacnetNetwork.localDevice().getObjectId();
    BEventHandler eh = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler();
    AsnOutputStream asnOut = new AsnOutputStream();
    boolean used = true;
    switch (ndx)
    {
      case 0:
        asnOut.writeUnsignedInteger(3);
        break;

      case NOT_USED:
        used = false;

      case 1:
        if (getAlarmRecordFromEventBuffer(eh, BBacnetEventState.OFFNORMAL, deviceId) != null)
        {
          BBacnetTimeStamp.encodeTimeStamp(lastOffnormalTime, asnOut);
        }
        else
        {
          BBacnetTimeStamp.encodeTimeStamp(BAbsTime.NULL, asnOut);
        }
        if (used)
        {
          break;
        }

      case 2:
        if (getAlarmRecordFromEventBuffer(eh, BBacnetEventState.FAULT, deviceId) != null)
        {
          BBacnetTimeStamp.encodeTimeStamp(lastFaultTime, asnOut);
        }
        else
        {
          BBacnetTimeStamp.encodeTimeStamp(BAbsTime.NULL, asnOut);
        }
        if (used)
        {
          break;
        }

      case 3:
        if (getAlarmRecordFromEventBuffer(eh, BBacnetEventState.NORMAL, deviceId) != null)
        {
          BBacnetTimeStamp.encodeTimeStamp(lastToNormalTime, asnOut);
        }
        else
        {
          BBacnetTimeStamp.encodeTimeStamp(BAbsTime.NULL, asnOut);
        }
        if (used)
        {
          break;
        }
    }
    return new NReadPropertyResult(BBacnetPropertyIdentifier.EVENT_TIME_STAMPS, ndx, asnOut.toByteArray());
  }

  /**
   * Read the Event Transition from Alarm Transition Bits only if that event(offnormal,
   * fault or normal) present in event buffer
   */
  protected BAlarmTransitionBits readEventTransition(BAlarmTransitionBits alarmTransitionBits)
  {
    int bits = 0;
    BBacnetObjectIdentifier deviceId = BBacnetNetwork.localDevice().getObjectId();
    BEventHandler eh =
      ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler();
    if ((getAlarmRecordFromEventBuffer(eh, BBacnetEventState.OFFNORMAL, deviceId) != null))
    {
      if (alarmTransitionBits.isToOffnormal())
      {
        bits |= BAlarmTransitionBits.TO_OFFNORMAL;
      }
    }
    else
    {
      bits |= BAlarmTransitionBits.TO_OFFNORMAL;

    }
    if ((getAlarmRecordFromEventBuffer(eh, BBacnetEventState.FAULT, deviceId) != null))
    {
      if (alarmTransitionBits.isToFault())
      {
        bits |= BAlarmTransitionBits.TO_FAULT;
      }
    }
    else
    {
      bits |= BAlarmTransitionBits.TO_FAULT;
    }
    if ((getAlarmRecordFromEventBuffer(eh, BBacnetEventState.NORMAL, deviceId) != null))
    {
      if (alarmTransitionBits.isToNormal())
      {
        bits |= BAlarmTransitionBits.TO_NORMAL;
      }
    }
    else
    {
      bits |= BAlarmTransitionBits.TO_NORMAL;
    }

    return BAlarmTransitionBits.make(bits);
  }

  /**
   * Override this method to update the bacnetStatusFlags slot
   */
  public void statusChanged()
  {
    //doNothing
  }

  /**
   * remove event from all event buffer for an event source.
   */
  protected void removeEventFromEventBuffer()
  {
    BEventHandler eventHandler = BBacnetServerLayer.getServerLayer().getEventHandler();
    eventHandler.removeAllRecordFromEventBuffer(
      BBacnetNetwork.localDevice().getObjectId(),
      getObjectId(),
      BBacnetDestination.LOCAL_PROCESS_ID);
    eventHandler.removeEventSummary(getObjectId());
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  private String readEventMessageTextFromEventBuffer(
    BEventHandler eventHandler,
    int eventStateOrdinal,
    BBacnetObjectIdentifier deviceId,
    String msgTextKey)
  {
    BAlarmRecord rec = getAlarmRecordFromEventBuffer(eventHandler, eventStateOrdinal, deviceId);
    if (rec != null)
    {
      return rec.getAlarmData().gets(msgTextKey, "");
    }

    return "";
  }

  private BAlarmRecord getAlarmRecordFromEventBuffer(
    BEventHandler eventHandler,
    int eventStateOrdinal,
    BBacnetObjectIdentifier deviceId)
  {
    BHashedEventBuffer eventBuffer = null;
    switch (eventStateOrdinal)
    {
      case BBacnetEventState.OFFNORMAL:
        eventBuffer = eventHandler.getToOffnormalBuffer();
        break;

      case BBacnetEventState.FAULT:
        eventBuffer = eventHandler.getToFaultBuffer();
        break;

      case BBacnetEventState.NORMAL:
        eventBuffer = eventHandler.getToNormalBuffer();
        break;
    }

    if (eventBuffer == null)
    {
      return null;
    }

    return eventBuffer.getRecord(
      deviceId,
      getObjectId(),
      BBacnetDestination.LOCAL_PROCESS_ID,
      false);
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetEventSource", 2);
    out.prop("fatalFault", fatalFault);
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  protected static final BBacnetBitString ACKED_TRANS_DEFAULT = BBacnetBitString.make(new boolean[] {
    /* toOffnormal */ true,
    /* toFault */ true,
    /* toNormal */ true });

  protected static final int MESSAGE_TEXTS_COUNT = 3;

  static Logger logger = Logger.getLogger("bacnet.server");
}
