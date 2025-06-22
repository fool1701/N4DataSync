/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import static javax.baja.bacnet.enums.BBacnetPropertyIdentifier.PRESENT_VALUE;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BIRemoteAlarmSource;
import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.util.BIBacnetPollable;
import javax.baja.bacnet.util.PollListEntry;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.control.BControlPoint;
import javax.baja.control.BIWritablePoint;
import javax.baja.control.enums.BPriorityLevel;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.driver.util.BPollFrequency;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.history.BIBacnetTrendLogExt;
import com.tridium.bacnet.job.BacnetDiscoveryUtil;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.BBacnetStack;

/**
 * BBacnetProxyExt contains all of the information necessary
 * to access a data value from a Bacnet device.
 * <p>
 * Each proxy point that represents a Bacnet data quantity will
 * have a BBacnetProxyExt to describe how to access the point.
 *
 * @author Craig Gemmill
 * @creation 14 Dec 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 Facets of device value being read and/or written.
 */
@NiagaraProperty(
  name = "deviceFacets",
  type = "BFacets",
  defaultValue = "BFacets.NULL",
  flags = Flags.READONLY,
  override = true
)
/*
 the Bacnet Object_Identifier of the containing object.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT"
)
/*
 the Bacnet Property_Identifier of the referenced property.
 */
@NiagaraProperty(
  name = "propertyId",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue)"
)
/*
 the property array index, if used.
 */
@NiagaraProperty(
  name = "propertyArrayIndex",
  type = "int",
  defaultValue = "NOT_USED"
)
/*
 the Asn primitive application data type.
 */
@NiagaraProperty(
  name = "dataType",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "readStatus",
  type = "String",
  defaultValue = "BBacnetProxyExt.UNSUBSCRIBED",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraProperty(
  name = "writeStatus",
  type = "String",
  defaultValue = "BBacnetProxyExt.READONLY",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "forceRead"
)
@NiagaraAction(
  name = "forceWrite"
)
@NiagaraAction(
  name = "subscribeCov",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "subscribeCovProperty",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "ackAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  returnType = "BBoolean",
  flags = Flags.HIDDEN
)
abstract public class BBacnetProxyExt
  extends BProxyExt
  implements BacnetConst,
             BIBacnetPollable,
             BIRemoteAlarmSource
{
  static Lexicon lex = Lexicon.make("bacnet");
  static String COV = lex.getText("point.cov");
  static String COVP = lex.getText("point.covp");
  static String POLLED = lex.getText("point.polled");
  static String COV_PENDING = lex.getText("point.covPending");
  static String COVP_PENDING = lex.getText("point.covpPending");
  static String COV_FAILED = lex.getText("point.covFailed");
  static String UNSUBSCRIBED = lex.getText("point.unsubscribed");
  static String WRITABLE = lex.getText("point.writable");
  static String READONLY = lex.getText("point.readonly");
  static String OK = lex.getText("point.ok");


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.point.BBacnetProxyExt(2994843597)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceFacets"

  /**
   * Slot for the {@code deviceFacets} property.
   * Facets of device value being read and/or written.
   * @see #getDeviceFacets
   * @see #setDeviceFacets
   */
  public static final Property deviceFacets = newProperty(Flags.READONLY, BFacets.NULL, null);

  //endregion Property "deviceFacets"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * the Bacnet Object_Identifier of the containing object.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(0, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Get the {@code objectId} property.
   * the Bacnet Object_Identifier of the containing object.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * the Bacnet Object_Identifier of the containing object.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "propertyId"

  /**
   * Slot for the {@code propertyId} property.
   * the Bacnet Property_Identifier of the referenced property.
   * @see #getPropertyId
   * @see #setPropertyId
   */
  public static final Property propertyId = newProperty(0, BDynamicEnum.make(BBacnetPropertyIdentifier.presentValue), null);

  /**
   * Get the {@code propertyId} property.
   * the Bacnet Property_Identifier of the referenced property.
   * @see #propertyId
   */
  public BDynamicEnum getPropertyId() { return (BDynamicEnum)get(propertyId); }

  /**
   * Set the {@code propertyId} property.
   * the Bacnet Property_Identifier of the referenced property.
   * @see #propertyId
   */
  public void setPropertyId(BDynamicEnum v) { set(propertyId, v, null); }

  //endregion Property "propertyId"

  //region Property "propertyArrayIndex"

  /**
   * Slot for the {@code propertyArrayIndex} property.
   * the property array index, if used.
   * @see #getPropertyArrayIndex
   * @see #setPropertyArrayIndex
   */
  public static final Property propertyArrayIndex = newProperty(0, NOT_USED, null);

  /**
   * Get the {@code propertyArrayIndex} property.
   * the property array index, if used.
   * @see #propertyArrayIndex
   */
  public int getPropertyArrayIndex() { return getInt(propertyArrayIndex); }

  /**
   * Set the {@code propertyArrayIndex} property.
   * the property array index, if used.
   * @see #propertyArrayIndex
   */
  public void setPropertyArrayIndex(int v) { setInt(propertyArrayIndex, v, null); }

  //endregion Property "propertyArrayIndex"

  //region Property "dataType"

  /**
   * Slot for the {@code dataType} property.
   * the Asn primitive application data type.
   * @see #getDataType
   * @see #setDataType
   */
  public static final Property dataType = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code dataType} property.
   * the Asn primitive application data type.
   * @see #dataType
   */
  public String getDataType() { return getString(dataType); }

  /**
   * Set the {@code dataType} property.
   * the Asn primitive application data type.
   * @see #dataType
   */
  public void setDataType(String v) { setString(dataType, v, null); }

  //endregion Property "dataType"

  //region Property "readStatus"

  /**
   * Slot for the {@code readStatus} property.
   * @see #getReadStatus
   * @see #setReadStatus
   */
  public static final Property readStatus = newProperty(Flags.TRANSIENT | Flags.READONLY, BBacnetProxyExt.UNSUBSCRIBED, null);

  /**
   * Get the {@code readStatus} property.
   * @see #readStatus
   */
  public String getReadStatus() { return getString(readStatus); }

  /**
   * Set the {@code readStatus} property.
   * @see #readStatus
   */
  public void setReadStatus(String v) { setString(readStatus, v, null); }

  //endregion Property "readStatus"

  //region Property "writeStatus"

  /**
   * Slot for the {@code writeStatus} property.
   * @see #getWriteStatus
   * @see #setWriteStatus
   */
  public static final Property writeStatus = newProperty(Flags.TRANSIENT | Flags.READONLY, BBacnetProxyExt.READONLY, null);

  /**
   * Get the {@code writeStatus} property.
   * @see #writeStatus
   */
  public String getWriteStatus() { return getString(writeStatus); }

  /**
   * Set the {@code writeStatus} property.
   * @see #writeStatus
   */
  public void setWriteStatus(String v) { setString(writeStatus, v, null); }

  //endregion Property "writeStatus"

  //region Action "forceRead"

  /**
   * Slot for the {@code forceRead} action.
   * @see #forceRead()
   */
  public static final Action forceRead = newAction(0, null);

  /**
   * Invoke the {@code forceRead} action.
   * @see #forceRead
   */
  public void forceRead() { invoke(forceRead, null, null); }

  //endregion Action "forceRead"

  //region Action "forceWrite"

  /**
   * Slot for the {@code forceWrite} action.
   * @see #forceWrite()
   */
  public static final Action forceWrite = newAction(0, null);

  /**
   * Invoke the {@code forceWrite} action.
   * @see #forceWrite
   */
  public void forceWrite() { invoke(forceWrite, null, null); }

  //endregion Action "forceWrite"

  //region Action "subscribeCov"

  /**
   * Slot for the {@code subscribeCov} action.
   * @see #subscribeCov()
   */
  public static final Action subscribeCov = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code subscribeCov} action.
   * @see #subscribeCov
   */
  public void subscribeCov() { invoke(subscribeCov, null, null); }

  //endregion Action "subscribeCov"

  //region Action "subscribeCovProperty"

  /**
   * Slot for the {@code subscribeCovProperty} action.
   * @see #subscribeCovProperty()
   */
  public static final Action subscribeCovProperty = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code subscribeCovProperty} action.
   * @see #subscribeCovProperty
   */
  public void subscribeCovProperty() { invoke(subscribeCovProperty, null, null); }

  //endregion Action "subscribeCovProperty"

  //region Action "ackAlarm"

  /**
   * Slot for the {@code ackAlarm} action.
   * @see #ackAlarm(BAlarmRecord parameter)
   */
  public static final Action ackAlarm = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code ackAlarm} action.
   * @see #ackAlarm
   */
  public BBoolean ackAlarm(BAlarmRecord parameter) { return (BBoolean)invoke(ackAlarm, parameter, null); }

  //endregion Action "ackAlarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetProxyExt()
  {
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Read the point's value from BACnet.
   */
  public void doForceRead()
  {
    try
    {
      network().postAsync(new PointCmd(PointCmd.READ_POINT, this));
      if (isCOVProperty())
      {
        network().postAsync(new PointCmd(PointCmd.SUBSCRIBE_COVP_POINT, this));
      }
      else if (isCOV())
      {
        network().postAsync(new PointCmd(PointCmd.SUBSCRIBE_COV_POINT, this));
      }
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Failed to read " + this + ":" + e);
      }
    }
  }

  /**
   * Force a write of the point's writeValue.
   */
  public void doForceWrite()
  {
    write(null);
  }

  /**
   * Subscribe for COV updates.
   */
  public void doSubscribeCov()
  {
    // Skip if subscription not needed or we are already waiting to subscribe.
    if (!isSubscribedDesired())
    {
      return;
    }

    switch (subState)
    {
      // For these cases, go to PENDING
      case SUB_STATE_UNSUB:             // initial subscription attempt
      case SUB_STATE_POLLED:            // tuning policy change, device.useCov change
        setSubState(SUB_STATE_FIRST_COV_PENDING);
        break;

      // For these cases, keep COV but identify pending
      case SUB_STATE_COV:               // already COV, just resubscribing
        setSubState(SUB_STATE_COV_PENDING);
        break;

      // For these cases, we want to skip everything
      case SUB_STATE_FIRST_COV_PENDING: // somehow invoked twice? don't repeat
        return;

      // For these cases, we want to leave the sub state alone
      case SUB_STATE_POLLED_PENDING:    // failed COV, retrying subscription
      case SUB_STATE_COV_PENDING:       // ???
        break;
    }
    submitSubscribeCmd(PointCmd.SUBSCRIBE_COV_POINT);
  }

  /**
   * Subscribe for COVP updates.
   */
  public void doSubscribeCovProperty()
  {
    // Skip if subscription not needed or we are already waiting to subscribe.
    if (!isSubscribedDesired())
    {
      return;
    }

    switch (subState)
    {
      // For these cases, go to PENDING
      case SUB_STATE_UNSUB:             // initial subscription attempt
      case SUB_STATE_POLLED:            // tuning policy change, device.useCov change
        setSubState(SUB_STATE_FIRST_COVP_PENDING);
        break;

      // For these cases, keep COV but identify pending
      case SUB_STATE_COVP:               // already COV, just resubscribing
        setSubState(SUB_STATE_COVP_PENDING);
        break;

      // For these cases, we want to skip everything
      case SUB_STATE_FIRST_COVP_PENDING: // somehow invoked twice? don't repeat
        return;

      // For these cases, we want to leave the sub state alone
      case SUB_STATE_POLLED_PENDING:     // failed COVP, retrying subscription
      case SUB_STATE_COVP_PENDING:       // ???
        break;
    }
    submitSubscribeCmd(PointCmd.SUBSCRIBE_COVP_POINT);
  }

  private void submitSubscribeCmd(int pointCmd)
  {
    boolean postFailed = false;
    try
    {
      network().postAsync(new PointCmd(pointCmd, this));
    }
    catch (Exception e)
    {
      postFailed = true;
      setSubState(SUB_STATE_POLLED_PENDING);
      pollService = (BBacnetPoll)network().getPollService(this);
      pollService.subscribe(this);
    }

    if (pointCmd == PointCmd.SUBSCRIBE_COVP_POINT)
    {
      scheduleResubscribeProperty(postFailed);
    }
    else
    {
      scheduleResubscribe(postFailed);
    }
  }

////////////////////////////////////////////////////////////////
// BIAlarmSource
////////////////////////////////////////////////////////////////

  public BBoolean doAckAlarm(BAlarmRecord alarm)
  {
    return ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler().doAckAlarm(alarm);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();

    setDebug();
    setAsnType();
    setDataSize();
    discoverPrioritizedPresentValue();
    if (getParentPoint().isWritablePoint())
    {
      setWriteStatus(WRITABLE);
    }
    points().registerPoint(this);
  }

  /**
   * Stopped.
   */
  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();

    points().unregisterPoint(this);
  }

  /**
   * Property added.
   */
  @Override
  public void added(Property p, Context cx)
  {
    super.added(p, cx);
    if (!isRunning())
    {
      return;
    }

    if (get(p) instanceof BBacnetDeviceObjectPropertyReference)
    {
      readUnsubscribed(null);
      readSubscribed(null);
    }
    else if (p.getName().equals("debug"))
    {
      setDebug();
    }
  }

  /**
   * Property removed.
   */
  @Override
  public void removed(Property p, BValue v, Context cx)
  {
    super.removed(p, v, cx);

    if (v instanceof BBacnetDeviceObjectPropertyReference)
    {
      readUnsubscribed(null);
      readSubscribed(null);
    }
    else if (p.getName().equals("debug"))
    {
      debug = false;
    }
  }

  /**
   * Property changed.
   */
  @Override
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning())
    {
      return;
    }
    if ((p.equals(objectId)) || (p.equals(propertyId)))
    {
      PropertyInfo info = device().getPropertyInfo(getObjectId().getObjectType(), getPropertyId().getOrdinal());
      if (info != null) setDataType(AsnUtil.getAsnTypeName(info.getAsnType()));

      if (isSubscribed())
      {
        // Need to do this to reinit device-level polling and avoid exceptions.
        readUnsubscribed(null);
        readSubscribed(null);
      }
      setAsnType();
      setDataSize();
      BFacets f = getDeviceFacets();
      f = BFacets.makeRemove(f, PRIORITIZED_PRESENT_VALUE);
      setDeviceFacets(f);
      discoverPrioritizedPresentValue();
      points().reregisterPoint(this);
    }
    else if (p.equals(propertyArrayIndex))
    {
      if (isSubscribed())
      {
        // Need to do this to reinit device-level polling and avoid exceptions.
        readUnsubscribed(null);
        readSubscribed(null);
      }
      points().reregisterPoint(this);
    }
    else if (p.equals(dataType))
    {
      setAsnType();
      setDataSize();
    }
    else if (p.equals(tuningPolicyName))
    {
      if (isSubscribed())
      {
        // Need to do this to reinit device-level polling and avoid exceptions.
        readUnsubscribed(null);
        readSubscribed(null);
      }
    }
    else if (p.equals(deviceFacets))
    {
      priPV = (BBoolean)getDeviceFacets().get(PRIORITIZED_PRESENT_VALUE);
    }
    else if (p.getName().equals("debug"))
    {
      setDebug();
    }
    else
    {
      BValue v = get(p);
      if (v instanceof BBacnetDeviceObjectPropertyReference)
      {
        if (isSubscribed())
        {
          readUnsubscribed(null);
          readSubscribed(null);
        }
      }
    }
  }

  /**
   * Get the device type this proxy extension belongs under.
   */
  @Override
  public Type getDeviceExtType()
  {
    return BBacnetPointDeviceExt.TYPE;
  }

  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  @Override
  public BReadWriteMode getMode()
  {
    return getParentPoint().isWritablePoint() ? BReadWriteMode.readWrite : BReadWriteMode.readonly;
  }

  /**
   * This method is called when a read from the device
   * fails due to a configuration or fault error.
   */
  @Override
  public void readFail(String cause)
  {
    setReadStatus(cause);
    super.readFail(cause);
  }

  /**
   * This method is called when a write to the device
   * fails for any reason.
   */
  @Override
  public void writeFail(String cause)
  {
    setWriteStatus(cause);
    super.writeFail(cause);
  }

  @Override
  public void pointFacetsChanged()
  {
    super.pointFacetsChanged();
    if (getParentPoint().isSubscribed())
    {
      readUnsubscribed(null);
      readSubscribed(null);
    }

    //Clear the cached status flags value
    useStatusFlags = null;
  }

  /**
   * Check here if the ext being added is a trend log ext, in which case we
   * will need to rebuild the poll list entries to make sure to include the
   * Status_Flags.
   */
  @Override
  protected boolean isSiblingLegal(BComponent sibling)
  {
    // Clear ples - this will force rebuild on next subscribe.
    if (sibling instanceof BIBacnetTrendLogExt)
    {
      ples = null;
    }

    return super.isSiblingLegal(sibling);
  }

////////////////////////////////////////////////////////////////
// BITunable
////////////////////////////////////////////////////////////////

  /**
   * This callback is made when the component enters a subscribed
   * state based on the current status and tuning.  The driver
   * should register for changes or begin polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.  The result of reads should be to call the
   * readOk() or readFail() method.
   */
  @Override
  public final void readSubscribed(Context cx)
  {
    if (!isRunning())
    {
      return;
    }

    BBacnetDevice device = device();
    if (useCov() &&
        device.canAddCov() &&
        BBacnetObjectType.canSupportCov(getObjectId().getObjectType(), device) &&
        getPropertyId().getOrdinal() == PRESENT_VALUE)
    {
      subscribeCov();
      forceRead();
    }
    else if (useCovProperty() &&
             device.canAddCovProperty())
    {
      subscribeCovProperty();
      forceRead();
    }
    else
    {
      pollService = (BBacnetPoll)network().getPollService(this);
      pollService.subscribe(this);
      setSubState(SUB_STATE_POLLED);
    }
  }

  /**
   * This callback is made when the component exits the subscribed
   * state based on the current status and tuning.  The driver
   * should unregister for changes of cease polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.
   */
  @Override
  public final void readUnsubscribed(Context cx)
  {
    if (isCOVProperty())
    {
      if (network().isRunning())
      {
        device().unsubscribeCovProperty(this);
      }
    }
    else if (isCOV())
    {
      if (network().isRunning())
      {
        device().unsubscribeCov(this);
      }
    }
    else
    {
      pollService = (BBacnetPoll)network().getPollService(this);
      pollService.unsubscribe(this);
    }
    ples = null;
    if (ticket != null)
    {
      ticket.cancel();
    }
    ticket = null;
    setSubState(SUB_STATE_UNSUB);
  }

  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  Any IO should be done asynchronously
   * on another thread - never block the calling thread.  If the write
   * is enqueued then return true and call writeOk() or writeFail()
   * once it has been processed.  If the write is canceled immediately
   * for other reasons then return false.
   * <p>
   * There are several cases of BACnet proxy points, which require different
   * handling of the write mechanism:
   * <b>non-priority properties</b>: properties such as High_Limit are not
   * prioritized.  Niagara returns without writing if the active level is
   * fallback, or writes the value if there is a non-fallback level.
   * <b>priority array properties</b>: points mapping slots in the priority array
   * will write null if the active level is fallback, or the value if non-fallback.
   * <b>present value of prioritized points</b>: points mapping the present value
   * of priority-type points (analog output, analog values with prioritization, etc.)
   * will write at the priority of the active level, unless it is fallback, in
   * which case it will write nothing.  When the active level changes, the old
   * level is cleared, and the new level is written.
   *
   * @return true if a write is now pending
   */
  @Override
  public boolean write(Context cx)
  {
    try
    {
      BStatusValue writeValue;
      int writeLevel = 0;
      if (getActiveLevel() == BPriorityLevel.FALLBACK &&
          getWriteValue().getStatus().isNull())
      {
        if (isPriorityArrayPoint())
        {
          writeValue = null;
        }
        else if (isPrioritizedPresentValue() && network().setAndGetWriteOnFacetChange().getBoolean())
        {
          writeValue = null; // Is this right?  post write separately to clear & then return?
        }
        else
        {
          return false;
        }
      }
      else
      {
        writeValue = (BStatusValue)getWriteValue().newCopy();
        if (isPrioritizedPresentValue())
        {
          writeLevel = getActiveLevel();  // what about none=0?
        }
      }

      // Now, if we got this far, write the value by posting an async event.
      network().postWrite(new PointCmd(PointCmd.WRITE_POINT,
                                       this,
                                       writeValue,
                                       lastWriteLevel,
                                       writeLevel));

      if (isPrioritizedPresentValue())
      {
        lastWriteLevel = writeLevel;
      }
    }
    catch (Throwable e)
    {
      String postWriteFailed = lex.getText("point.writeFail");
      writeFail(postWriteFailed + ":" + e);
      log.log(Level.INFO, "Error writing BACnet point " + this + " in " + device() + ": " + e, e);
    }

    // return false always to prevent point getting stuck
    return false;
  }

////////////////////////////////////////////////////////////////
// BIBacnetPollable
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetDevice containing this BBacnetProxyExt.
   */
  @Override
  public final BBacnetDevice device()
  {
    return (BBacnetDevice)getDevice();
  }

  /**
   * Get the pollable type of this object.
   *
   * @return one of the pollable types defined in BIBacnetPollable.
   */
  @Override
  public final int getPollableType()
  {
    return BACNET_POLLABLE_PROXY_EXT;
  }

  /**
   * Poll.
   *
   * @deprecated As of 3.2
   */
  @Override
  @Deprecated
  public boolean poll()
  {
    // Bail if device is down or disabled, or objectId is bad.
    if (!device().getEnabled() ||
        device().getStatus().isDown())
    {
      return false;
    }

    if (!getObjectId().isValid())
    {
      return false;
    }

    return device().poll(this);
  }

  /**
   * Get the poll frequency of this point.
   */
  @Override
  public BPollFrequency getPollFrequency()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getPollFrequency();
  }

  /**
   * Get the list of poll list entries for this pollable.
   * The first entry for points must be the configured property.
   *
   * @return the list of poll list entries.
   */
  @Override
  public final PollListEntry[] getPollListEntries()
  {
    if (ples == null)
    {
      Array<PollListEntry> a = new Array<>(PollListEntry.class);
      a.add(new PollListEntry(this));

      boolean sfAdded = false;
      BFacets facets = getParentPoint().getFacets();
      if (useStatusFlags())
      {
        a.add(new PollListEntry(this, BBacnetPropertyIdentifier.STATUS_FLAGS));
        sfAdded = true;
      }

      BBoolean f = (BBoolean)facets.getFacet(READ_EVENT_STATE);
      if ((f != null) && f.getBoolean())
      {
        a.add(new PollListEntry(this, BBacnetPropertyIdentifier.EVENT_STATE));
      }
      f = (BBoolean)facets.getFacet(READ_PRIORITY_ARRAY);
      if ((f != null) && f.getBoolean())
      {
        a.add(new PollListEntry(this, BBacnetPropertyIdentifier.PRIORITY_ARRAY));
      }
      f = (BBoolean)facets.getFacet(READ_RELIABILITY);
      if ((f != null) && f.getBoolean())
      {
        a.add(new PollListEntry(this, BBacnetPropertyIdentifier.RELIABILITY));
      }

      // Check for references to any other objects or properties.
      SlotCursor<Property> sc = getProperties();
      while (sc.next(BBacnetDeviceObjectPropertyReference.class))
      {
        BBacnetDeviceObjectPropertyReference dopr = (BBacnetDeviceObjectPropertyReference)sc.get();
        BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
        if (!dopr.isDeviceIdUsed() || (deviceId.hashCode() == device().getObjectId().hashCode()))
        {
          a.add(new PollListEntry(dopr.getObjectId(),
                                  dopr.getPropertyId(),
                                  dopr.getPropertyArrayIndex(),
                                  device(),
                                  this));
        }
        else
        {
          BBacnetDevice dev = network().doLookupDeviceById(deviceId);
          if (dev != null)
          {
            a.add(new PollListEntry(dopr.getObjectId(),
                                    dopr.getPropertyId(),
                                    dopr.getPropertyArrayIndex(),
                                    dev,
                                    this));
          }
          else
          {
            throw new IllegalStateException("Cannot find BACnet device for point metadata:" + this + " ref=" + dopr);
          }
        }
      }

      // If we have a trend log ext on this point, we need to get Status_Flags
      // also, according to 135-2004m-12.
      BIBacnetTrendLogExt[] logs = getParentPoint().getChildren(BIBacnetTrendLogExt.class);
      if (logs.length > 0 && !sfAdded)
      {
        a.add(new PollListEntry(this, BBacnetPropertyIdentifier.STATUS_FLAGS));
      }

      // Check for references to objects and properties within this de
      // Clean up the array and return it.
      ples = a.trim();
    }

    return ples;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetDevice containing this BBacnetDevice.
   */
  public final BBacnetPointDeviceExt points()
  {
    return (BBacnetPointDeviceExt)getDeviceExt();
  }

  public boolean useCov()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getUseCov();
  }

  public boolean useConfirmedCov()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getUseConfirmedCov();
  }

  public int getCovSubscriptionLifetime()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getCovSubscriptionLifetime();
  }

  public boolean useCovProperty()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getUseCovProperty();
  }

  public boolean useConfirmedCovProperty()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getUseConfirmedCovProperty();
  }

  public int getCovPropertySubscriptionLifetime()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getCovPropertySubscriptionLifetime();
  }

  public BDouble getCovPropertyIncrement()
  {
    return BDouble.make(((BBacnetTuningPolicy)getTuningPolicy()).getCovPropertyIncrement());
  }

  public boolean getAcceptUnsolicitedCov()
  {
    return ((BBacnetTuningPolicy)getTuningPolicy()).getAcceptUnsolicitedCov();
  }

  public void tuningChanged(BBacnetTuningPolicy policy, Context cx)
  {
    if ((policy == null) || (policy == getTuningPolicy()))
    {
      if (isSubscribed())
      {
        // Reinitialize device-level polling and COV subscriptions.
        readUnsubscribed(cx);
        readSubscribed(cx);
      }
    }
  }

  /**
   * To String.
   */
  @Override
  public String toString(Context cx)
  {
    char sep = (cx == nameContext) ? '_' : ':';
    return getObjectId().toString(cx) + sep +
           getPropertyId() + sep +
           getPropertyArrayIndex() + sep +
           getDataType();
  }

  @Override
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder(super.toDebugString());
    sb.append(" dev=").append(device().getObjectId().toString())
      .append(" oid=").append(getObjectId().toString())
      .append(" pid=").append(getPropertyId())
      .append(" ndx=").append(getPropertyArrayIndex())
      .append(" asn=").append(getDataType())
      .append(" run=").append(isRunning())
      .append(" sub=").append(isSubscribed());
    return sb.toString();
  }

  /**
   * For objectId, get the facets from the device's object type facets.
   */
  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (!isMounted())
    {
      return super.getSlotFacets(slot);
    }
    BBacnetDevice device = bacnetDevice();
    if (device != null)
    {
      if (slot.equals(objectId))
      {
        return device.getEnumerationList().getObjectTypeFacets();
      }
      if (slot.equals(propertyId))
      {
        return device.getEnumerationList().getPropertyIdFacets();
      }
    }
    return super.getSlotFacets(slot);
  }

  private BBacnetDevice bacnetDevice = null;

  private BBacnetDevice bacnetDevice()
  {
    if (bacnetDevice == null)
    {
      BComplex dev = this.getParent();
      while (dev != null)
      {
        if (dev instanceof BBacnetDevice)
        {
          bacnetDevice = (BBacnetDevice)dev;
          break;
        }
        dev = dev.getParent();
      }
    }
    return bacnetDevice;
  }

  /**
   * Is this point subscribed for COV notifications?
   */
  public final boolean isCOV()
  {
    return (subState == SUB_STATE_COV) || (subState == SUB_STATE_COV_PENDING);
  }

  /**
   * Is this proxyExt attempting to SubscribeCOV?
   *
   * @return true if pending first attempt or polled while waiting for retries to succeed.
   */
  public final boolean isCOVPending()
  {
    return (subState == SUB_STATE_FIRST_COV_PENDING) || (subState == SUB_STATE_POLLED_PENDING) || (subState == SUB_STATE_COV_PENDING);
  }

  /**
   * Is this point subscribed for COV notifications?
   */
  public final boolean isCOVProperty()
  {
    return (subState == SUB_STATE_COVP) || (subState == SUB_STATE_COVP_PENDING);
  }

  /**
   * Is this proxyExt attempting to SubscribeCOVProperty?
   *
   * @return true if pending first attempt or polled while waiting for retries to succeed.
   */
  public final boolean isCOVPropertyPending()
  {
    return (subState == SUB_STATE_FIRST_COVP_PENDING) || (subState == SUB_STATE_POLLED_PENDING) || (subState == SUB_STATE_COVP_PENDING);
  }


  /**
   * Is this proxyExt attempt to SubscribeCOVProperty failed?
   *
   * @return true if pending first attempt or polled while waiting for retries to succeed.
   */
  public final boolean isCOVPropertyFailed()
  {
    return (subState == SUB_STATE_POLLED_PENDING);
  }

  /**
   * Is this proxyExt being polled?
   *
   * @return true if polled or polled while waiting for subscribeCov retries to succeed.
   */
  public final boolean isPolled()
  {
    return (subState == SUB_STATE_POLLED) || (subState == SUB_STATE_POLLED_PENDING);
  }

  /**
   * Set the COV flag.
   *
   * @deprecated Use {@link #setSubState(int)} instead
   */
  @Deprecated
  public void setCOV(boolean cov)
  {
    if (cov)
    {
      subState = SUB_STATE_COV;
    }
  }

  /**
   * Set the COV state.
   *
   * @param subState
   */
  public final void setSubState(int subState)
  {
    this.subState = subState;
    switch (subState)
    {
      case SUB_STATE_UNSUB:
        setReadStatus(UNSUBSCRIBED);
        break;

      case SUB_STATE_POLLED:
        setReadStatus(POLLED);
        break;

      case SUB_STATE_COV:
        setReadStatus(COV);
        break;

      case SUB_STATE_FIRST_COV_PENDING:
        if (!isCOV())
        {
          setReadStatus(COV_PENDING);
        }
        break;

      case SUB_STATE_FIRST_COVP_PENDING:
        if (!isCOVProperty())
        {
          setReadStatus(COVP_PENDING);
        }
        break;

      case SUB_STATE_POLLED_PENDING:
        setReadStatus(POLLED);
        break;

      case SUB_STATE_COV_PENDING:
        break;

      case SUB_STATE_COVP:
        setReadStatus(COVP);
        break;

      case SUB_STATE_COVP_PENDING:
        break;
    }
  }

  /**
   * Get the last read error that occurred while reading this point.
   */
  public ErrorType getLastReadError()
  {
    return lastReadError;
  }

  /**
   * Set the last read error that occurred while reading this point.
   */
  public void setLastReadError(ErrorType e)
  {
    lastReadError = e;
  }

  /**
   * Should status flags be applied to this point?
   *
   * @return true if the BACnet standard specifies Status_Flags for this property.
   */
  public boolean useStatusFlags()
  {
    return getPropertyId().getOrdinal() == PRESENT_VALUE &&
           BBacnetObjectType.hasStatusFlags(getObjectId().getObjectType(), device()) &&
           statusFlagsEnabled();
  }

  /*
   * Check for the status flags facet.
   */
  private boolean statusFlagsEnabled()
  {
    if (useStatusFlags != null)
    {
      return useStatusFlags.getBoolean();
    }

    BControlPoint parent = getParentPoint();
    if (parent != null)
    {
      BFacets facets = parent.getFacets();
      if (facets != null)
      {
        BObject facet = facets.getFacet(READ_STATUS_FLAGS);
        if (facet instanceof BBoolean)
        {
          useStatusFlags = (BBoolean)facet;
          return useStatusFlags.getBoolean();
        }
      }
    }
    return false;
  }

  /**
   * Is this point referencing a priority-array entry?
   */
  public boolean isPriorityArrayPoint()
  {
    return (getPropertyId().getOrdinal() == BBacnetPropertyIdentifier.PRIORITY_ARRAY);
  }

  /**
   * Is this point referencing the present-value of a priority-type point?
   */
  public boolean isPrioritizedPresentValue()
  {
    if (priPV != null)
    {
      return priPV.getBoolean();
    }
    log.severe("\n***\n***\n***\n\npriPV is NULL in " + this + "!  How did we get here?? thd: " + Thread.currentThread().getName());
    Thread.dumpStack();
    return true;
  }

  /**
   * Get the expected size in bytes of the encoded data value for this point.
   */
  public int getDataSize()
  {
    return dataSize;
  }

  /**
   * Set the prioritized present value flag.
   */
  public void setPrioritizedPresentValue(boolean prioritizedPV)
  {
    this.priPV = BBoolean.make(prioritizedPV);
    if (!prioritizedPV)
    {
      lastWriteLevel = 0;
    }
    BFacets f = getDeviceFacets();
    f = BFacets.make(f, PRIORITIZED_PRESENT_VALUE, (prioritizedPV ? BBoolean.TRUE : BBoolean.FALSE));
    setDeviceFacets(f);
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////

  abstract public void fromEncodedValue(byte[] encodedValue, BStatus status, Context cx);

  abstract public byte[] toEncodedValue(BStatusValue newValue);

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * update read status on based on the context.
   *
   * @param cx           PollListEntry.pointCx.
   */
  protected void updateReadStatus(Context cx)
  {
    if (cx == covContext)
    {
      if (isCOVProperty())
      {
        setReadStatus(COVP);
      }
      else if (isCOV())
      {
        setReadStatus(COV);
      }
    }
    else if (isPolled())
    {
      if ((cx == PollListEntry.pointCx || cx.getBase() == PollListEntry.pointCx))
      {
        setReadStatus(POLLED);
      }
    }
  }

  /**
   * Read metadata from a secondary PollListEntry.
   *
   * @param encodedValue
   * @param cx           must be a PollListEntry.
   * @param dv
   */
  protected void readMetaData(byte[] encodedValue, Context cx, BStatusValue dv)
    throws AsnException
  {
    BStatus s = dv.getStatus();
    PollListEntry ple = (PollListEntry)cx;
    BBacnetObjectIdentifier objectId = ple.getObjectId();
    if (objectId.hashCode() == getObjectId().hashCode())
    {
      int propId = ple.getPropertyId();
      switch (propId)
      {
        case BBacnetPropertyIdentifier.STATUS_FLAGS:
          s = AsnUtil.asnStatusFlagsToBStatus(encodedValue);
          break;

        case BBacnetPropertyIdentifier.EVENT_STATE:
          int esord = AsnUtil.fromAsnEnumerated(encodedValue);
          String estag = BBacnetEventState.tag(esord);
          s = BStatus.make(s, EVENT_STATE_STATUS_FACET, estag);
          break;

        case BBacnetPropertyIdentifier.PRIORITY_ARRAY:
          s = BStatus.make(s, PRIORITY_ARRAY_STATUS_FACET, BString.make("def"));
          AsnInputStream asnIn = AsnInputStream.make(encodedValue);
          try
          {
            int tag = 0;
            int activeLevel = 0;
            for (activeLevel = 1; activeLevel <= 16; activeLevel++)
            {
              tag = asnIn.skipTag();
              if (tag != ASN_NULL)
              {
                s = BStatus.make(s, PRIORITY_ARRAY_STATUS_FACET, BInteger.make(activeLevel));
                break;
              }
            }
          }
          finally
          {
            asnIn.release();
          }
          break;

        case BBacnetPropertyIdentifier.RELIABILITY:
          int rord = AsnUtil.fromAsnEnumerated(encodedValue);
          String rtag = BBacnetReliability.tag(rord);
          s = BStatus.make(s, RELIABILITY_STATUS_FACET, rtag);
          break;

        default:
          BValue val = AsnUtil.asnToValue(device().getPropertyInfo(ple.getObjectId().getObjectType(), propId), encodedValue);
          String key = BBacnetPropertyIdentifier.tag(propId);
          s = BStatus.make(s, key, val.toString());
          break;
      }
    }
    else
    {
      if (objectId.getObjectType() == BBacnetObjectType.EVENT_ENROLLMENT)
      {
        int propId = ple.getPropertyId();
        switch (propId)
        {
          case BBacnetPropertyIdentifier.EVENT_STATE:
            int esord = AsnUtil.fromAsnEnumerated(encodedValue);
            String estag = BBacnetEventState.tag(esord);
            s = BStatus.make(s, "EE" + objectId.getInstanceNumber(), estag);
            break;

          default:
            BValue val = AsnUtil.asnToValue(device().getPropertyInfo(BBacnetObjectType.EVENT_ENROLLMENT, propId), encodedValue);
            String key = BBacnetPropertyIdentifier.tag(propId);
            s = BStatus.make(s, "EE" + objectId.getInstanceNumber() + "_" + key, val.toString());
            break;
        }
      }
      else
      {
        String key = ple.getObjectId().toString(BacnetConst.facetsContext) + "_" + BBacnetPropertyIdentifier.tag(ple.getPropertyId());
        BValue val = AsnUtil.asnToValue(device().getPropertyInfo(ple.getObjectId().getObjectType(), ple.getPropertyId()), encodedValue);
        s = BStatus.make(s, key, val.toString());
      }
    }
    dv.setStatus(s);
  }

  /**
   * @return the BBacnetNetwork containing this BBacnetDevice.
   */
  protected final BBacnetNetwork network()
  {
    return (BBacnetNetwork)getNetwork();
  }

  /**
   * Get the address of the containing device.
   */
  protected BBacnetAddress getDeviceAddress()
  {
    return ((BBacnetDevice)getDevice()).getAddress();
  }

  /**
   * The active level of the point's executeResult.
   * This is used to determine if we need to write the value, or a NULL
   * to relinquish command at this level of a prioritized point's priority array.
   */
  private int getActiveLevel()
  {
    return getWriteValue().getStatus().geti(BStatus.ACTIVE_LEVEL, BPriorityLevel.FALLBACK);
  }

  /**
   * Schedule a resubsciption for Cov notification.
   * The parent device's Cov subscription lifetime is used, subject to
   * the following modifications:
   * - If this value is less than or equal to zero,
   * force a resubscription at least once per day.
   * - The minium lifetime is 5 minutes.
   * - The resubscribe time is calculated from the subscription lifetime
   * by using a safety factor of 2 for resubscriptions.
   */
  private void scheduleResubscribe(boolean postFailed)
  {
    if (ticket != null)
    {
      ticket.cancel();
    }

    BRelTime resubscribeTime = calculateResubscribeTime(postFailed, getCovSubscriptionLifetime());
    ticket = Clock.schedule(this, resubscribeTime, subscribeCov, null);
  }

  private void scheduleResubscribeProperty(boolean postFailed)
  {
    if (ticket != null)
    {
      ticket.cancel();
    }

    BRelTime resubscribeTime = calculateResubscribeTime(postFailed, getCovPropertySubscriptionLifetime());
    ticket = Clock.schedule(this, resubscribeTime, subscribeCovProperty, null);
  }

  public static BRelTime calculateResubscribeTime(boolean postFailed, int subscriptionLifeTime)
  {
    if (postFailed)
    {
      // Try again in 10 seconds.
      return RELTIME_ON_POST_FAILURE;
    }
    else
    {
      int subLife = subscriptionLifeTime;
      if (subLife <= 0)
      {
        return ONCE_A_DAY_RELTIME;  // resubscribe once a day at least
      }
      if (subLife <= 5)
      {
        return MINIMUM_RELTIME;  // min resubscribe time: 5 minutes
      }
      return BRelTime.make(subLife * RESUBSCRIPTION_FACTOR);
    }
  }

  /**
   * Set the expected data size based on the Asn type.
   * This is a prediction based on the typical maximum size of encoded
   * primitive data.  The actual data size will be set
   * once we receive a read response.
   */
  private void setDataSize()
  {
    switch (asnType)
    {
      case ASN_NULL:
        dataSize = 1;
        break;

      case ASN_BOOLEAN:
        dataSize = 2;
        break;

      case ASN_DOUBLE:
        dataSize = 9;
        break;

      case ASN_ENUMERATED:
        dataSize = 3;
        break;

      case ASN_CHARACTER_STRING:
        dataSize = 20;
        break;

      default:
        dataSize = 5;
        break;
    }
  }

  /**
   * Set the ASN type that governs the encoding of the point's value.
   */
  private void setAsnType()
  {
    asnType = AsnUtil.getAsnType(getDataType());
  }

  /**
   * Set the prioritized present value flag.
   */
  public void discoverPrioritizedPresentValue()
  {
    discoverPrioritizedPresentValue(false);
  }

  /**
   * Set the prioritized present value flag.
   *
   * @param force discovery of priPv if true,
   *              otherwise reset priPv from device facets if false
   */
  public void discoverPrioritizedPresentValue(boolean force)
  {
    int otyp = getObjectId().getObjectType();
    switch (otyp)
    {
      case BBacnetObjectType.ANALOG_OUTPUT: // 1
      case BBacnetObjectType.BINARY_OUTPUT: // 4
      case BBacnetObjectType.MULTI_STATE_OUTPUT: // 14
        priPV = BBoolean.make(getPropertyId().getOrdinal() == PRESENT_VALUE);
        break;

      case BBacnetObjectType.ANALOG_VALUE:  // 2
      case BBacnetObjectType.LARGE_ANALOG_VALUE: // 46
      case BBacnetObjectType.BINARY_VALUE:  // 5
      case BBacnetObjectType.MULTI_STATE_VALUE:  // 19
      case BBacnetObjectType.CHARACTER_STRING_VALUE: //40
      case BBacnetObjectType.INTEGER_VALUE: // 45
      case BBacnetObjectType.POSITIVE_INTEGER_VALUE:
        if (getPropertyId().getOrdinal() == PRESENT_VALUE)
        {
          BFacets f = getDeviceFacets();
          BBoolean ppv = (BBoolean)f.getFacet(PRIORITIZED_PRESENT_VALUE);
          if (getParentPoint() instanceof BIWritablePoint &&
            (ppv == null || force))
          {
            network().postWrite(new Runnable()
                                {
                                  @Override
                                  public void run()
                                  {
                                    checkForPriorityArray();
                                  }
                                });
          }
          else
          {
            priPV = ppv;
          }
        }
        else
        {
          priPV = BBoolean.FALSE;
        }
        break;

      default:
        priPV = BBoolean.FALSE;
        break;
    }
  }

  private void checkForPriorityArray()
  {
    // Start with a true value, as the fallback if we encounter problems,
    // because we must have things defined one way or the other after this.
    BFacets f = getDeviceFacets();
    BBoolean priArr = BacnetDiscoveryUtil.checkForPriorityArray(getObjectId(), device());
    setDeviceFacets(BFacets.make(f, PRIORITIZED_PRESENT_VALUE, priArr));
  }

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////

  /**
   * Spy.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetProxyExt", 2);
    out.prop("asnType", asnType);
    out.prop("dataSize", dataSize);
    out.prop("lastReadError", lastReadError);
    out.prop("ticket", ticket);
    out.prop("prioritizedPV", priPV);
    out.prop("lastWriteLevel", lastWriteLevel);
    out.prop("subState", subState);
    out.prop("pollService", pollService);
    if (ples != null)
    {
      out.prop("ples", ples.length);
      for (int i = 0; i < ples.length; i++)
      {
        out.prop("ples[" + i + "]:", ples[i].debugString());
      }
    }
    out.endProps();
  }

  void dbg(String s)
  {
    if (debug)
    {
      System.out.println("BACPxExtDBG{" + this + "}:" + s);
    }
  }

  private boolean debug = false;

  private void setDebug()
  {
    BBoolean dbg = (BBoolean)get("debug");
    if (dbg != null)
    {
      debug = dbg.getBoolean();
    }
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int SUB_STATE_UNSUB = 0;  // unsubscribed
  public static final int SUB_STATE_POLLED = 1;  // polled (cov not possible or desired)
  public static final int SUB_STATE_COV = 2;  // subscribed for cov
  public static final int SUB_STATE_FIRST_COV_PENDING = 3;  // first cov attempt pending
  public static final int SUB_STATE_POLLED_PENDING = 4;  // cov/covp failed, polled pending cov retry
  public static final int SUB_STATE_COV_PENDING = 5;  // cov ok, resubscribing
  public static final int SUB_STATE_FIRST_COVP_PENDING = 6;  // first cov attempt pending
  public static final int SUB_STATE_COVP = 7;  // subscribed for covp
  public static final int SUB_STATE_COVP_PENDING = 8;  // covp ok, resubscribing
  public static final int SUB_STATE_COVP_FAILED = 9;  // covp failed, polled pending cov retry

  public static final byte[] NO_VALUE = new byte[0];

  public static final String READ_STATUS_FLAGS = "statusFlags";
  public static final String READ_EVENT_STATE = "eventState";
  public static final String READ_PRIORITY_ARRAY = "priorityArray";
  public static final String READ_RELIABILITY = "reliability";

  public static final String STATUS_FLAGS_STATUS_FACET = "statusFlags";
  public static final String EVENT_STATE_STATUS_FACET = "state";
  public static final String PRIORITY_ARRAY_STATUS_FACET = "bac";
  public static final String RELIABILITY_STATUS_FACET = "reliability";

  // Facets
  public static final String PRIORITIZED_PRESENT_VALUE = "priPV";

  static final ErrorType ERROR_DEVICE_OTHER = new NErrorType(BBacnetErrorClass.DEVICE, BBacnetErrorCode.OTHER);

  public static final Context covContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:covContext";
    }
  };

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Clock.Ticket ticket = null;
  private ErrorType lastReadError;
  protected int dataSize = 0;
  protected int asnType = 0;
  private BBoolean priPV = BBoolean.FALSE;
  private BBoolean useStatusFlags = null;
  private int lastWriteLevel = 0;
  private BBacnetPoll pollService;

  private int subState = SUB_STATE_UNSUB;

  private PollListEntry[] ples = null;

  static Logger log = Logger.getLogger("bacnet.point");

  private static final int DAY_IN_MINUTES = 1440; // 24(hrs) * 60(min)
  private static final long RESUBSCRIPTION_FACTOR = 30000L; // 60sec/min * 1000ms/sec / 2 (safety factor)
  private static final long MINIMUM_SUBSCRIPTION_LIFETIME = 5; // in minutes
  private static final int INTERVAL_ON_POST_FAILURE = 10;// in seconds

  private static final BRelTime RELTIME_ON_POST_FAILURE = BRelTime.makeSeconds(INTERVAL_ON_POST_FAILURE);
  private static final BRelTime ONCE_A_DAY_RELTIME = BRelTime.make(DAY_IN_MINUTES * RESUBSCRIPTION_FACTOR);
  private static final BRelTime MINIMUM_RELTIME = BRelTime.make(MINIMUM_SUBSCRIPTION_LIFETIME * RESUBSCRIPTION_FACTOR);
}
