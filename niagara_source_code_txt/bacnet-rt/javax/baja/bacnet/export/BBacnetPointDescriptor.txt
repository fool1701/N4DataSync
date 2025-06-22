/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetNotifyType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.control.BControlPoint;
import javax.baja.control.BPointExtension;
import javax.baja.control.enums.BPriorityLevel;
import javax.baja.history.ext.BHistoryExt;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.server.BBacnetExportFolder;
import com.tridium.bacnet.stack.server.BBacnetServerLayer;
import com.tridium.bacnet.stack.server.BOverrideMode;

/**
 * BBacnetPointDescriptor is the extension that allows a normal Baja
 * ControlPoint to be exposed to Bacnet.
 *
 * @author Craig Gemmill on 31 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
/*
 the ord to the exposed Control Point.
 */
@NiagaraProperty(
  name = "pointOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:Component\"")
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.DEFAULT",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 the name by which this object is known to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectName",
  type = "String",
  defaultValue = "",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 indicates misconfiguration
 */
@NiagaraProperty(
  name = "reliability",
  type = "BEnum",
  defaultValue = "BBacnetReliability.noFaultDetected",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 Is this descriptor disconnected from the control point which it maps?
 */
@NiagaraProperty(
  name = "outOfService",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN | Flags.READONLY
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "notifyType",
  type = "BBacnetNotifyType",
  defaultValue = "BBacnetNotifyType.alarm",
  facets = @Facet("BacUtil.makeBacnetNotifyTypeFacets()")
)
/*
 add a COV subscription for a client device.
 */
@NiagaraAction(
  name = "addCovSubscription",
  parameterType = "BBacnetCovSubscription",
  defaultValue = "new BBacnetCovSubscription()",
  flags = Flags.HIDDEN
)
/*
 remove a COV subscription for a client device.
 */
@NiagaraAction(
  name = "removeCovSubscription",
  parameterType = "BBacnetCovSubscription",
  defaultValue = "new BBacnetCovSubscription()",
  flags = Flags.HIDDEN
)
/*
 configure the BACnet writability of the point.
 */
@NiagaraAction(
  name = "makeWritable",
  parameterType = "BValue",
  defaultValue = "BString.DEFAULT",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "sendCovNotification",
  parameterType = "BBacnetCovSubscription",
  defaultValue = "new BBacnetCovSubscription()",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "checkCov",
  flags = Flags.HIDDEN | Flags.ASYNC
)
abstract public class BBacnetPointDescriptor
  extends BBacnetEventSource
  implements BIBacnetCovSource,
             BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetPointDescriptor(3941367212)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pointOrd"

  /**
   * Slot for the {@code pointOrd} property.
   * the ord to the exposed Control Point.
   * @see #getPointOrd
   * @see #setPointOrd
   */
  public static final Property pointOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.DEFAULT, BFacets.make(BFacets.TARGET_TYPE, "baja:Component"));

  /**
   * Get the {@code pointOrd} property.
   * the ord to the exposed Control Point.
   * @see #pointOrd
   */
  public BOrd getPointOrd() { return (BOrd)get(pointOrd); }

  /**
   * Set the {@code pointOrd} property.
   * the ord to the exposed Control Point.
   * @see #pointOrd
   */
  public void setPointOrd(BOrd v) { set(pointOrd, v, null); }

  //endregion Property "pointOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.DEFAULT, null);

  /**
   * Get the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #objectId
   */
  public BBacnetObjectIdentifier getObjectId() { return (BBacnetObjectIdentifier)get(objectId); }

  /**
   * Set the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #objectId
   */
  public void setObjectId(BBacnetObjectIdentifier v) { set(objectId, v, null); }

  //endregion Property "objectId"

  //region Property "objectName"

  /**
   * Slot for the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #getObjectName
   * @see #setObjectName
   */
  public static final Property objectName = newProperty(Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #objectName
   */
  public String getObjectName() { return getString(objectName); }

  /**
   * Set the {@code objectName} property.
   * the name by which this object is known to the Bacnet world.
   * @see #objectName
   */
  public void setObjectName(String v) { setString(objectName, v, null); }

  //endregion Property "objectName"

  //region Property "reliability"

  /**
   * Slot for the {@code reliability} property.
   * indicates misconfiguration
   * @see #getReliability
   * @see #setReliability
   */
  public static final Property reliability = newProperty(Flags.TRANSIENT | Flags.READONLY, BBacnetReliability.noFaultDetected, null);

  /**
   * Get the {@code reliability} property.
   * indicates misconfiguration
   * @see #reliability
   */
  public BEnum getReliability() { return (BEnum)get(reliability); }

  /**
   * Set the {@code reliability} property.
   * indicates misconfiguration
   * @see #reliability
   */
  public void setReliability(BEnum v) { set(reliability, v, null); }

  //endregion Property "reliability"

  //region Property "outOfService"

  /**
   * Slot for the {@code outOfService} property.
   * Is this descriptor disconnected from the control point which it maps?
   * @see #getOutOfService
   * @see #setOutOfService
   */
  public static final Property outOfService = newProperty(Flags.HIDDEN | Flags.READONLY, false, null);

  /**
   * Get the {@code outOfService} property.
   * Is this descriptor disconnected from the control point which it maps?
   * @see #outOfService
   */
  public boolean getOutOfService() { return getBoolean(outOfService); }

  /**
   * Set the {@code outOfService} property.
   * Is this descriptor disconnected from the control point which it maps?
   * @see #outOfService
   */
  public void setOutOfService(boolean v) { setBoolean(outOfService, v, null); }

  //endregion Property "outOfService"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, "", null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "notifyType"

  /**
   * Slot for the {@code notifyType} property.
   * @see #getNotifyType
   * @see #setNotifyType
   */
  public static final Property notifyType = newProperty(0, BBacnetNotifyType.alarm, BacUtil.makeBacnetNotifyTypeFacets());

  /**
   * Get the {@code notifyType} property.
   * @see #notifyType
   */
  public BBacnetNotifyType getNotifyType() { return (BBacnetNotifyType)get(notifyType); }

  /**
   * Set the {@code notifyType} property.
   * @see #notifyType
   */
  public void setNotifyType(BBacnetNotifyType v) { set(notifyType, v, null); }

  //endregion Property "notifyType"

  //region Action "addCovSubscription"

  /**
   * Slot for the {@code addCovSubscription} action.
   * add a COV subscription for a client device.
   * @see #addCovSubscription(BBacnetCovSubscription parameter)
   */
  public static final Action addCovSubscription = newAction(Flags.HIDDEN, new BBacnetCovSubscription(), null);

  /**
   * Invoke the {@code addCovSubscription} action.
   * add a COV subscription for a client device.
   * @see #addCovSubscription
   */
  public void addCovSubscription(BBacnetCovSubscription parameter) { invoke(addCovSubscription, parameter, null); }

  //endregion Action "addCovSubscription"

  //region Action "removeCovSubscription"

  /**
   * Slot for the {@code removeCovSubscription} action.
   * remove a COV subscription for a client device.
   * @see #removeCovSubscription(BBacnetCovSubscription parameter)
   */
  public static final Action removeCovSubscription = newAction(Flags.HIDDEN, new BBacnetCovSubscription(), null);

  /**
   * Invoke the {@code removeCovSubscription} action.
   * remove a COV subscription for a client device.
   * @see #removeCovSubscription
   */
  public void removeCovSubscription(BBacnetCovSubscription parameter) { invoke(removeCovSubscription, parameter, null); }

  //endregion Action "removeCovSubscription"

  //region Action "makeWritable"

  /**
   * Slot for the {@code makeWritable} action.
   * configure the BACnet writability of the point.
   * @see #makeWritable(BValue parameter)
   */
  public static final Action makeWritable = newAction(Flags.HIDDEN, BString.DEFAULT, null);

  /**
   * Invoke the {@code makeWritable} action.
   * configure the BACnet writability of the point.
   * @see #makeWritable
   */
  public void makeWritable(BValue parameter) { invoke(makeWritable, parameter, null); }

  //endregion Action "makeWritable"

  //region Action "sendCovNotification"

  /**
   * Slot for the {@code sendCovNotification} action.
   * @see #sendCovNotification(BBacnetCovSubscription parameter)
   */
  public static final Action sendCovNotification = newAction(Flags.HIDDEN, new BBacnetCovSubscription(), null);

  /**
   * Invoke the {@code sendCovNotification} action.
   * @see #sendCovNotification
   */
  public void sendCovNotification(BBacnetCovSubscription parameter) { invoke(sendCovNotification, parameter, null); }

  //endregion Action "sendCovNotification"

  //region Action "checkCov"

  /**
   * Slot for the {@code checkCov} action.
   * @see #checkCov()
   */
  public static final Action checkCov = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code checkCov} action.
   * @see #checkCov
   */
  public void checkCov() { invoke(checkCov, null, null); }

  //endregion Action "checkCov"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetPointDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
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
    // Export the point and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    checkConfiguration();
    reliabilityChanged();

    // Increment the Device object's Database_Revision for created points.
    if (Sys.isStationStarted())
    {
      BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
  }

  /**
   * Stopped.
   * Clean up the point name subscriber and null references.
   */
  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();

    // unexport
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);
    clearTrendReferences(local);
    if (getOosExt() != null)
    {
      removeOutOfServiceExt();
    }

    local.unsubscribe(this, point);

    // Clear the local copies.
    requiredProps = null;
    optionalProps = null;
    point = null;
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted points.
    if (local.isRunning())
    {
      local.incrementDatabaseRevision();
    }
  }

  /**
   * Added.
   * Cov subscriptions will generate a new Cov notification on add.
   */
  @Override
  public final void added(Property p, Context cx)
  {
    super.added(p, cx);
    if (!isRunning())
    {
      return;
    }
    // The sending of the notification is moved to startCovTimer()
    // to handle the resubscription case.
  }

  /**
   * Changed.
   * If the objectId changes, make sure the new ID is not already in use.
   * If it is, reset it to the current value.
   */
  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(objectId))
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      local.unexport(oldId, oldName, this);
      clearTrendReferences(local);
      checkConfiguration();
      oldId = getObjectId();
      try
      {
        ((BComponent)getParent()).rename(getPropertyInParent(), getObjectId().toString(nameContext));
      }
      catch (DuplicateSlotException ignored)
      {}
      if (configOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(objectName))
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      local.unexport(oldId, oldName, this);
      clearTrendReferences(local);
      checkConfiguration();
      oldName = getObjectName();
      if (configOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(pointOrd))
    {
      ordStatus = OrdStatus.CHANGED;
      checkConfiguration();
      if (configOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(notifyType))
    {
      if (getNotifyType() == BBacnetNotifyType.ackNotification)
      {
        log.warning("Invalid Notify Type for " + this);
        setNotifyType(BBacnetNotifyType.make(oldNotifyType));
      }
      else
      {
        oldNotifyType = getNotifyType().getOrdinal();
      }
    }
    else if (p.equals(description))
    {
      BBacnetExportFolder f = getSvo();
      if (f != null)
      {
        f.fireSubordinateAnnotationChanged(null);
      }
    }
    else if(p.equals(reliability))
    {
      reliabilityChanged();
    }
  }

  @Override
  public void statusChanged()
  {
    BStatus status = getStatusFlags();

    //If status is unchanged, don't propogate events
    if (lastStatusBits == status.getBits())
    {
      return;
    }
    lastStatusBits = status.getBits();

    setBacnetStatusFlags(BBacnetBitString.make(new boolean[] { status.isAlarm(),
                                                               status.isFault(),
                                                               status.isOverridden(),
                                                               status.isDisabled() }));
  }

  /**
   * When reliability property is changed, if BReliabilityAlarmSourceExt added to the point,
   * notify the extension about the reliability property changed.
   */
  protected void reliabilityChanged()
  {
    BControlPoint point = getPoint(false);
    if (null != point)
    {
      BReliabilityAlarmSourceExt[] c = point.getChildren(BReliabilityAlarmSourceExt.class);
      for (int i = 0; i < c.length; i++)
      {
        BReliabilityAlarmSourceExt fault = c[i];
        fault.reliabilityChanged((BBacnetReliability)getReliability());
      }
    }
  }

  /**
   * Clock Changed.
   * COV Subscriptions need to have their subscriptionEndTime adjusted by the
   * amount of the clock change.
   */
  @Override
  public void clockChanged(BRelTime shift)
    throws Exception
  {
    SlotCursor<Property> sc = getProperties();
    while (sc.next(BBacnetCovSubscription.class))
    {
      BBacnetCovSubscription covSub = (BBacnetCovSubscription)sc.get();
      covSub.setSubscriptionEndTime(covSub.getSubscriptionEndTime().add(shift));
    }
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public final void doAddCovSubscription(BBacnetCovSubscription sub)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Adding Cov subscription: " + sub + " on " + this);
    }
    Property p = add("covSubscription?", sub, Flags.TRANSIENT | Flags.READONLY);
    BBacnetNetwork.localDevice().subscribeCov(this, getPoint(), p);
  }

  public final void doRemoveCovSubscription(BBacnetCovSubscription sub)
  {
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Removing Cov subscription: " + sub + " on " + this);
    }
    Clock.Ticket ticket = sub.getTicket();
    if (ticket != null)
    {
      ticket.cancel();
    }
    sub.setTicket(null);
    Property p = getProperty(sub.getName());
    if (p != null)
    {
      remove(p);
    }

    BBacnetNetwork.localDevice().unsubscribeCov(this, getPoint(), p);
  }

  public void doMakeWritable(BValue v)
  {
  }

  /**
   * Send a Cov notification.
   */
  public void doSendCovNotification(BBacnetCovSubscription covSub)
  {
    BControlPoint pt = getPoint();

    // sanity check - if we missed the end of life, just remove it now
    if (covSub.getTimeRemaining() < 0)
    {
      removeCovSubscription(covSub);
      return;
    }

    if (log.isLoggable(Level.FINE))
    {
      log.fine("Sending Cov Notification on " + this + ": pt=" + pt + "\n  covSub=" + covSub);
    }
    Cov cov = new Cov(covSub, this, pt);

    BBacnetNetwork.bacnet().getCovWorker().sendCov(cov);
  }

  /**
   * Check Cov subscriptions to see if any require a notification.
   */
  public final void doCheckCov()
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetCovSubscription.class))
    {
      BBacnetCovSubscription covSub = (BBacnetCovSubscription)c.get();
      boolean send = false;
      if (covSub.isCovProperty())
      {
        send = checkCovProperty(covSub);
      }
      else
      {
        BStatusValue currentValue = getCurrentStatusValue();
        if (checkCov(currentValue, covSub.getLastValue()))
        {
          covSub.setLastValue(currentValue);
          send = true;
        }
      }

      if (send)
      {
        sendCovNotification(covSub);
      }
    }
  }

  public final boolean checkCovProperty(BBacnetCovSubscription covSub)
  {
    boolean send = false;

    try
    {
      PropertyValue pv = readProperty(covSub.getMonitoredPropertyReference().getPropertyId(),
                                      covSub.getMonitoredPropertyReference().getPropertyArrayIndex());

      PropertyInfo pi = BBacnetNetwork.localDevice().getPropertyInfo(getObjectId().getObjectType(),
                                                                     covSub.getMonitoredPropertyReference().getPropertyId());

      byte[] newAsnPropValue = pv.getPropertyValue();

      //If status flags have changed send notification:
      BStatus sf = getStatusFlags();
      int cs = sf.getBits() & BACNET_SBITS_MASK;
      int lsb = covSub.getLastStatusBits();
      if (cs != lsb)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("Status flags changed from: " + lsb + " to: " + cs);
        }

        send = true;
      }

      //If numeric present value must check cov increment
      if (pi.getAsnType() == ASN_REAL || pi.getAsnType() == ASN_DOUBLE)
      {
        BValue cv = AsnUtil.asnToValue(pi, newAsnPropValue);
        BINumeric newValue = (BINumeric)cv;
        BINumeric lastNumeric = (BINumeric)covSub.getLastPropValue();
        double covIncrement = getCovIncrement(covSub);
        double diff = 0;

        if (lastNumeric == null ||
            (diff = Math.abs(newValue.getNumeric() - lastNumeric.getNumeric())) >= covIncrement)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.fine("NumericCOV changed by more than diff: " + diff + " to: " + newValue.getNumeric());
          }

          covSub.setLastPropValue(cv);
          send = true;
        }
      }
      else
      {
        PropertyValue lv = covSub.getLastPropertyValue();
        if (lv == null)
        {
          //No value, send it!
          if (log.isLoggable(Level.FINE))
          {
            log.fine("No previous notifications");
          }

          send = true;
        }
        else if (!Arrays.equals(lv.getPropertyValue(), newAsnPropValue))
        {
          //asn.1 encoded byte[] changed, send it!
          if (log.isLoggable(Level.FINE))
          {
            log.fine("asn.1 encoded byte[] changed, send it!");
          }
          send = true;
        }
      }

      if (send)
      {
        covSub.setLastPropertyValue(pv);
        covSub.setLastStatusFlags(sf);
      }
    }
    catch (AsnException e)
    {
      log.log(Level.SEVERE, "AsnException occurred in checkCovProperty", e);
    }

    return send;
  }

  private double getCovIncrement(BBacnetCovSubscription covSub)
  {
    double covIncrement = covSub.getCovIncrement();
    if (Double.isNaN(covIncrement))
    {
      if (covSub.getMonitoredPropertyReference().getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE)
      {
        BNumber d = (BNumber)get("covIncrement");
        covIncrement = (d != null) ? d.getDouble() : 0.0D;
      }
      else
      {
        covIncrement = 0.0D;
      }
    }
    return covIncrement;
  }

////////////////////////////////////////////////////////////////
// BIBacnetExportObject
////////////////////////////////////////////////////////////////

  /**
   * Get the exported object.
   */
  @Override
  public final BObject getObject()
  {
    return getPoint();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getPointOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(pointOrd, objectOrd, cx);
  }

  /**
   * Check the configuration of this object.
   */
  @Override
  public synchronized void checkConfiguration()
  {
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();

    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
      configOk = false;
      return;
    }

    // Unsubscribe before possibly re-exporting. Previously called after the find method but then
    // the previous object would not be unsubscribed in the case the objectOrd is changed.
    local.unsubscribe(this, point);

    // Find the exported point.
    findPoint();

    // Check the configuration.
    boolean cfgOk = true;
    if (point == null)
    {
      setFaultCause("Cannot find exported point");
      cfgOk = false;
    }
    else
    {
      //Lock the point while exporting.
      synchronized (point)
      {
        // Check for valid object id.
        if (!getObjectId().isValid())
        {
          setFaultCause("Invalid Object ID");
          cfgOk = false;
        }
        // Allow for subclass configuration checking.
        cfgOk &= checkPointConfiguration();

        // Try to export - duplicate id & names will be checked in here.
        if (cfgOk)
        {
          String err = local.export(this);
          if (err != null)
          {
            duplicate = true;
            setFaultCause(err);
            cfgOk = false;
          }
          else
          {
            duplicate = false;
            updateTrendReferences(local, point);
          }
        }

        // Set the config flag.
        configOk = cfgOk;
        if (cfgOk)
        {
          setReliability(BBacnetReliability.noFaultDetected);
          setFaultCause("");

          // This may potentially set a fault, but the point
          // is already exported properly.
          validate();
        }
        else
        {
          setReliability(BBacnetReliability.unreliableOther);
          setStatus(BStatus.makeFault(getStatus(), true));
        }

        // Check OutOfServiceExt.
        if (configOk())
        {
          getOosExt();
        }
      }

      //Wait until after the point is exported to subscribe
      local.subscribe(this, point);
    }
  }

////////////////////////////////////////////////////////////////
// BBacnetEventSource
////////////////////////////////////////////////////////////////

  /**
   * Is this object currently configured to support event initiation?
   * This will return false if the exported object does not have an
   * appropriate alarm extension configured to allow Bacnet event initiation.
   *
   * @return true if this object can initiate Bacnet events.
   */
  @Override
  public final boolean isEventInitiationEnabled()
  {
    return getNotificationClass() != null;
  }

  /**
   * Get the current Event_State of the object.
   * If the exported object also has an alarm extension, this
   * returns the current event state as translated from the
   * alarm extension's alarm state.  Otherwise, it returns null.
   *
   * @return the object's event state if configured for alarming, or null.
   */
  @Override
  public final BEnum getEventState()
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BBacnetEventState.make(almExt.getAlarmState());
    }
  }

  /**
   * Get the current Acknowledged_Transitions property of the object.
   * If the exported object also has an alarm extension, this
   * returns the current acked transitions as translated from the
   * alarm extension's alarm transitions.  Otherwise, it returns null.
   *
   * @return the object's acknowledged transitions if configured for alarming, or null.
   */
  @Override
  public final BBacnetBitString getAckedTransitions()
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAckedTransitions());
    }
  }

  /**
   * Get the event time stamps.
   *
   * @return the event time stamps, or null if event initiation is not enabled.
   */
  @Override
  public final BBacnetTimeStamp[] getEventTimeStamps()
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      BBacnetTimeStamp[] ets = new BBacnetTimeStamp[3];
      BAbsTime t = almExt.getLastOffnormalTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[0] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[0] = new BBacnetTimeStamp(t);
      }
      t = almExt.getLastFaultTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[1] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[1] = new BBacnetTimeStamp(t);
      }
      t = almExt.getLastToNormalTime();
      if (BAbsTime.DEFAULT.equals(t))
      {
        ets[2] = new BBacnetTimeStamp(new BBacnetDateTime());
      }
      else
      {
        ets[2] = new BBacnetTimeStamp(t);
      }

      return ets;
    }
  }

  /**
   * Get the event enable bits.
   *
   * @return the event enable bits, or null if event initiation is not enabled.
   */
  @Override
  public final BBacnetBitString getEventEnable()
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    else
    {
      return BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable());
    }
  }

  /**
   * Get the event priorities.
   *
   * @return the event priorities, or null if event initiation is not enabled.
   */
  @Override
  public final int[] getEventPriorities()
  {
    BBacnetNotificationClassDescriptor nc = getNotificationClass();
    if (nc == null)
    {
      return null;
    }
    else
    {
      return nc.getEventPriorities();
    }
  }

  /**
   * Get the Notification Class object for this event source.
   *
   * @return the <code>BacnetNotificationClassDescriptor</code> for this object.
   */
  @Override
  public final BBacnetNotificationClassDescriptor getNotificationClass()
  {
    BBacnetNotificationClassDescriptor nc = null;
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt == null)
    {
      return null;
    }
    try
    {
      BAlarmService as = (BAlarmService)Sys.getService(BAlarmService.TYPE);
      BAlarmClass ac = as.lookupAlarmClass(almExt.getAlarmClass());
      BBacnetObjectIdentifier ncId = BBacnetNetwork.localDevice().lookupBacnetObjectId(ac.getHandleOrd());
      if (ncId != null)
      {
        nc = (BBacnetNotificationClassDescriptor)BBacnetNetwork.localDevice().lookupBacnetObject(ncId);
        if (nc == null)
        {
          log.warning("Can't find Notification Class Descriptor for ID " + ncId);
        }
      }
      else
      {
        log.warning("Alarm Class '" + ac + "' is used for BACnet-exposed object " + this + ", but is not exposed as a BACnet Notification Class");
      }
    }
    catch (ServiceNotFoundException e)
    {
      log.log(Level.SEVERE, "getNotificationClass on " + this + ": Unable to find alarm service", e);
    }

    return nc;
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  abstract public BEnum getEventType();

////////////////////////////////////////////////////////////////
// BIBacnetCovSource
////////////////////////////////////////////////////////////////

  /**
   * Get the export descriptor for this cov source.  Usually this.
   *
   * @return the relevant export descriptor.
   */
  @Override
  public BIBacnetExportObject getExport()
  {
    return this;
  }

  /**
   * Attempt to locate a COV subscription for the given subscriber information
   * on this object.
   *
   * @param subscriberAddress
   * @param processId
   * @param objectId
   * @return the subscription if found, or null.
   */
  @Override
  public final BBacnetCovSubscription findCovSubscription(BBacnetAddress subscriberAddress,
                                                          long processId,
                                                          BBacnetObjectIdentifier objectId)
  {
    return findSubscription(false,
                            subscriberAddress,
                            processId,
                            objectId,
                            BBacnetPropertyIdentifier.PRESENT_VALUE,
                            NOT_USED);
  }

  /**
   * Attempt to locate a COVProperty subscription for the given subscriber information
   * on this object.
   *
   * @param subscriberAddress
   * @param processId
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @return the subscription if found, or null.
   */
  @Override
  public final BBacnetCovSubscription findCovPropertySubscription(BBacnetAddress subscriberAddress,
                                                                  long processId,
                                                                  BBacnetObjectIdentifier objectId,
                                                                  int propertyId,
                                                                  int propertyArrayIndex)
  {
    return findSubscription(true,
                            subscriberAddress,
                            processId,
                            objectId,
                            propertyId,
                            NOT_USED);
  }

  /**
   * Start or restart a timer for the given COV subscription.
   *
   * @param covSub   the subscription for which to start the timer.
   * @param lifetime the lifetime, in seconds, of the subscription.
   */
  @Override
  public final void startCovTimer(BBacnetCovSubscription covSub, long lifetime)
  {
    Clock.Ticket ticket = covSub.getTicket();
    if (ticket != null)
    {
      ticket.cancel();
    }
    if (lifetime > 0)
    {
      BRelTime subLife = BRelTime.make(((int)lifetime) * BRelTime.MILLIS_IN_SECOND);
      covSub.setSubscriptionEndTime(BAbsTime.make().add(subLife));
      covSub.setTicket(Clock.schedule(this, subLife, removeCovSubscription, covSub));
    }
    sendCovNotification(covSub);
  }

  /**
   * Get the output property mapped as Present_Value for this export.
   *
   * @return the property used for Present_Value in COV notifications.
   */
  @Override
  public Property getOutProperty()
  {
    BControlPoint point = getPoint();
    return (point != null) ? point.getOutProperty() : null;
  }

  /**
   * Does this COV source support SubscribeCOV in addition to SubscribeCOVProperty?
   * This is true for input, output, value, and loop objects.
   *
   * @return true if Subscribe-COV can be used with this object.
   */
  @Override
  public boolean supportsSubscribeCov()
  {
    return true;
  }

  @Override
  public BValue getCurrentCovValue(BBacnetCovSubscription sub)
  {
    PropertyValue pv = readProperty(sub.getMonitoredPropertyReference().getPropertyId(),
                                    sub.getMonitoredPropertyReference().getPropertyArrayIndex());
    try
    {
      return AsnUtil.asnToValue(BBacnetNetwork.localDevice().getPropertyInfo(this.getObjectId().getObjectType(),
                                                                             sub.getMonitoredPropertyReference().getPropertyId()),
                                pv.getPropertyValue());
    }
    catch (AsnException e)
    {
      log.log(Level.SEVERE, "AsnException occurred in getCurrentCovValue", e);
      return null;
    }
  }

  /**
   * Get the current statusValue to use in checking for COVs.
   * Subclasses must override this to return the correct statusValue,
   * taking into account the value of outOfService, and using the
   * getStatusFlags() method to incorporate the appropriate status
   * information to report to BACnet.
   */
  abstract BStatusValue getCurrentStatusValue();

  /**
   * Check to see if the current value requires a COV notification.
   * The default implementation returns true if the current value
   * is not equal to the last value.
   */
  boolean checkCov(BStatusValue currentValue, BStatusValue covValue)
  {
    return (!currentValue.equals(covValue));
  }

////////////////////////////////////////////////////////////////
// Bacnet Request Execution
////////////////////////////////////////////////////////////////

  /**
   * Get the value of a property.
   *
   * @param ref the PropertyReference containing id and index.
   * @return a PropertyValue containing either the encoded value or the error.
   */
  @Override
  public final PropertyValue readProperty(PropertyReference ref)
    throws RejectException
  {
    getPoint();
    return readProperty(ref.getPropertyId(), ref.getPropertyArrayIndex());
  }

  /**
   * Read the value of multiple Bacnet properties.
   *
   * @param refs the list of property references.
   * @return an array of PropertyValues.
   */
  @Override
  public final PropertyValue[] readPropertyMultiple(PropertyReference[] refs)
    throws RejectException
  {
    getPoint();
    PropertyValue[] readResults = new PropertyValue[0];
    ArrayList<PropertyValue> results = new ArrayList<>(refs.length);
    for (int i = 0; i < refs.length; i++)
    {
      int[] props;
      switch (refs[i].getPropertyId())
      {
        case BBacnetPropertyIdentifier.ALL:
          props = getRequiredProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          props = getOptionalProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.REQUIRED:
          props = getRequiredProps();
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        default:
          results.add(readProperty(refs[i].getPropertyId(),
                                   refs[i].getPropertyArrayIndex()));
          break;
      }
    }

    return results.toArray(readResults);
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    getPoint();
    int pId = rangeReference.getPropertyId();
    int[] props = getRequiredProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }
    props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (pId == props[i])
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
      }
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Set the value of a property.
   *
   * @param val the PropertyValue containing the write information.
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  public final ErrorType writeProperty(PropertyValue val)
    throws BacnetException
  {
    getPoint();
    return writeProperty(val.getPropertyId(),
                         val.getPropertyArrayIndex(),
                         val.getPropertyValue(),
                         val.getPriority());
  }

  /**
   * Add list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  @Override
  public ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getPoint();
    int[] props = getRequiredProps();
    int propertyId = propertyValue.getPropertyId();
    for (int i = 0; i < props.length; i++)
    {
      if (props[i] == propertyId)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }
    props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (props[i] == propertyId)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }

    return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                new NErrorType(BBacnetErrorClass.PROPERTY,
                                               BBacnetErrorCode.UNKNOWN_PROPERTY),
                                0);
  }

  /**
   * Remove list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  @Override
  public ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getPoint();
    int[] props = getRequiredProps();
    int propertyId = propertyValue.getPropertyId();
    for (int i = 0; i < props.length; i++)
    {
      if (props[i] == propertyId)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }
    props = getOptionalProps();
    for (int i = 0; i < props.length; i++)
    {
      if (props[i] == propertyId)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                    0);
      }
    }

    return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                new NErrorType(BBacnetErrorClass.PROPERTY,
                                               BBacnetErrorCode.UNKNOWN_PROPERTY),
                                0);
  }

////////////////////////////////////////////////////////////////
// Bacnet Support
////////////////////////////////////////////////////////////////

  /**
   * Is the property referenced by this propertyId an array property?
   *
   * @param propertyId
   * @return true if it is an array property, false if not or if the
   * propertyId does not refer to a property in this object.
   */
  boolean isArray(int propertyId)
  {
    for (int i = 0; i < ARRAY_PROPS.length; i++)
    {
      if (propertyId == ARRAY_PROPS[i])
      {
        return true;
      }
    }

    return false;
  }

  private static final int[] ARRAY_PROPS = {
    BBacnetPropertyIdentifier.EVENT_TIME_STAMPS,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS,
    BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG,
    BBacnetPropertyIdentifier.PROPERTY_LIST,
    BBacnetPropertyIdentifier.PRIORITY_ARRAY,
    BBacnetPropertyIdentifier.STATE_TEXT
  };

  /**
   * Get the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing either the encoded value or the error.
   */
  protected PropertyValue readProperty(int pId, int ndx)
  {
    if (point == null)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.OBJECT,
                                                              BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }

    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
      }
    }
    else if (ndx < NOT_USED)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(
        BBacnetErrorClass.PROPERTY,
        BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }

    switch (pId)
    {
      case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));

      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return readPropertyList(ndx);

      case BBacnetPropertyIdentifier.DESCRIPTION:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDescription()));

      case BBacnetPropertyIdentifier.STATUS_FLAGS:
        return new NReadPropertyResult(pId, ndx, AsnUtil.statusToAsnStatusFlags(getStatusFlags()));

      case BBacnetPropertyIdentifier.EVENT_STATE:
        return readEventState();
      case BBacnetPropertyIdentifier.RELIABILITY:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getReliability()));

      case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getOosExt().getOutOfService()));

      default:
        return readOptionalProperty(pId, ndx);
    }
  }

  private PropertyValue readEventState()
  {
    if (!getEventDetectionEnable())
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_STATE,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    BAlarmSourceExt alarmExt = getAlarmExt();
    if (alarmExt == null)
    {
      // Object does not support event reporting, set to Normal.
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.EVENT_STATE,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnEnumerated(BBacnetEventState.NORMAL));
    }

    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_STATE,
      BacnetConst.NOT_USED,
      AsnUtil.toAsnEnumerated(BBacnetEventState.fromBAlarmState(alarmExt.getAlarmState())));
  }

  /**
   * Read the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing either the encoded value or the error.
   */
  protected PropertyValue readOptionalProperty(int pId, int ndx)
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
          BBacnetNotificationClassDescriptor nc = getNotificationClass();
          if (nc == null)
          {
            return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                    BBacnetErrorCode.UNKNOWN_PROPERTY));
          }
          else
          {
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(nc.getNotificationClass()));
          }

        case BBacnetPropertyIdentifier.EVENT_ENABLE:
          return new NReadPropertyResult(
            pId,
            ndx,
            AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable())));
        case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getEventDetectionEnable()));

        case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          return readAckedTransitions(almExt.getAckedTransitions());
        case BBacnetPropertyIdentifier.NOTIFY_TYPE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getNotifyType()));

        case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          return readEventTimeStamps(
            almExt.getLastOffnormalTime(),
            almExt.getLastFaultTime(),
            almExt.getLastToNormalTime(),
            ndx);
        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          return readEventMessageTexts(ndx);
        case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
          return readEventMessageTextsConfig(
            almExt.getToOffnormalText().getFormat(),
            almExt.getToFaultText().getFormat(),
            almExt.getToNormalText().getFormat(),
            ndx);
        case BBacnetPropertyIdentifier.TIME_DELAY:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getTimeDelay().getSeconds()));
      }
    }

    return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                            BBacnetErrorCode.UNKNOWN_PROPERTY));
  }

  private NReadPropertyResult readAckedTransitions(BAlarmTransitionBits ackedTrans)
  {
    if (getEventDetectionEnable())
    {
      BAlarmTransitionBits eventTrans = readEventTransition(ackedTrans);
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(eventTrans)));
    }
    else
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.ACKED_TRANSITIONS,
        BacnetConst.NOT_USED,
        AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
    }
  }

  /**
   * Set the value of a property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    if (point == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT,
                            BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }

    // Check for array index on non-array property.
    if (ndx >= 0)
    {
      if (!isArray(pId))
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }
    else if (ndx < NOT_USED)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX);
    }

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_NAME:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.STATUS_FLAGS:
        case BBacnetPropertyIdentifier.EVENT_STATE:
        case BBacnetPropertyIdentifier.PROPERTY_LIST:
        case BBacnetPropertyIdentifier.RELIABILITY:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
          getOosExt().setBoolean(BOutOfServiceExt.outOfService, AsnUtil.fromOnlyAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        default:
          return writeOptionalProperty(pId, ndx, val, pri);
      }
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  /**
   * Set the value of an optional property.
   * Subclasses with additional properties override this to check for
   * their properties.  If no match is found, call this superclass
   * method to check these properties.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @param val the Asn-encoded value for the property.
   * @param pri the priority level (only used for commandable properties).
   * @throws BacnetException
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeOptionalProperty(int pId,
                                            int ndx,
                                            byte[] val,
                                            int pri)
    throws BacnetException
  {
    try
    {
      BAlarmSourceExt almExt = getAlarmExt();
      if (almExt != null)
      {
        switch (pId)
        {
          case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
            int ncinst = AsnUtil.fromAsnUnsignedInt(val);
            if (ncinst > BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BBacnetObjectIdentifier ncid = BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS, ncinst);
            BBacnetNotificationClassDescriptor nc = (BBacnetNotificationClassDescriptor)BBacnetNetwork.localDevice().lookupBacnetObject(ncid);
            if (nc == null)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BAlarmClass ac = nc.getAlarmClass();
            almExt.setString(BAlarmSourceExt.alarmClass,
                             ac.getName(),
                             BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.EVENT_ENABLE:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.NOTIFY_TYPE:
            set(notifyType,
                BBacnetNotifyType.make(AsnUtil.fromAsnEnumerated(val)),
                BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
          case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
              BBacnetErrorCode.WRITE_ACCESS_DENIED);
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
            return writeEventMessageTextsConfig(ndx, val, almExt);
          case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
            setBoolean(eventDetectionEnable, AsnUtil.fromAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.TIME_DELAY:
            almExt.set(BAlarmSourceExt.timeDelay,
              BRelTime.makeSeconds((int) AsnUtil.fromAsnUnsignedInteger(val)),
              BLocalBacnetDevice.getBacnetContext());
            return null;
        }
      }
    }
    catch (OutOfRangeException | IllegalArgumentException e)
    {
      log.warning("OutOfRangeException | IllegalArgumentException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }

    // Property not found
    return new NErrorType(BBacnetErrorClass.PROPERTY,
                          BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Get all the required properties for this object.
   * Calculated once, if needed.
   *
   * @return the list as an array of ints.
   */
  public int[] getRequiredProps()
  {
    if (requiredProps == null)
    {
      Vector<BBacnetPropertyIdentifier> v = new Vector<>();
      v.add(BBacnetPropertyIdentifier.objectIdentifier);
      v.add(BBacnetPropertyIdentifier.objectName);
      v.add(BBacnetPropertyIdentifier.objectType);
      addRequiredProps(v);
      requiredProps = new int[v.size()];
      for (int i = 0; i < requiredProps.length; i++)
      {
        requiredProps[i] = ((BEnum) v.elementAt(i)).getOrdinal();
      }
    }
    return requiredProps;
  }

  /**
   * Get all the optional properties for this object.
   *
   * @return the list as an array of ints.
   */
  public int[] getOptionalProps()
  {
    Vector<BBacnetPropertyIdentifier> v = new Vector<>();
    v.add(BBacnetPropertyIdentifier.reliability);
    v.add(BBacnetPropertyIdentifier.description);
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      v.add(BBacnetPropertyIdentifier.timeDelay);
      v.add(BBacnetPropertyIdentifier.notificationClass);
      v.add(BBacnetPropertyIdentifier.eventEnable);
      v.add(BBacnetPropertyIdentifier.ackedTransitions);
      v.add(BBacnetPropertyIdentifier.notifyType);
      v.add(BBacnetPropertyIdentifier.eventTimeStamps);
      v.add(BBacnetPropertyIdentifier.eventMessageTexts);
      v.add(BBacnetPropertyIdentifier.eventMessageTextsConfig);
      v.add(BBacnetPropertyIdentifier.eventDetectionEnable);
    }
    addOptionalProps(v);
    optionalProps = new int[v.size()];
    for (int i = 0; i < optionalProps.length; i++)
    {
      optionalProps[i] = ((BEnum) v.elementAt(i)).getOrdinal();
    }
    return optionalProps;
  }

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(getRequiredProps(), getOptionalProps());
  }

  /**
   * Override method to add required properties.
   * NOTE: You MUST call super.addRequiredProps(v) first!
   *
   * @param v Vector containing required propertyIds.
   */
  protected void addRequiredProps(@SuppressWarnings("rawtypes") Vector v)
  {
  }

  /**
   * Override method to add optional properties.
   * NOTE: You MUST call super.addOptionalProps(v) first!
   *
   * @param v Vector containing optional propertyIds.
   */
  protected void addOptionalProps(@SuppressWarnings("rawtypes") Vector v)
  {
  }

////////////////////////////////////////////////////////////////
// Access methods
////////////////////////////////////////////////////////////////

  /**
   * Override point for BBacnetPointDescriptors to enforce
   * type rules for their exposed points.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  protected boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BControlPoint;
  }

  /**
   * Get the Status_Flags property from the BStatus
   * of the parent point.
   */
  BStatus getStatusFlags()
  {
    getPoint();
    BAlarmSourceExt alarmExt = getAlarmExt();
    int status = 0;
    if (alarmExt != null)
    {
      BStatus pointStatus = alarmExt.getStatus();
      status = ((point == null) ? BStatus.FAULT : pointStatus.getBits());
      if (pointStatus.isDown())
      {
        status |= BStatus.FAULT;
      }
    }
    if (getOosExt().getOutOfService())
    {
      status |= BStatus.DISABLED;
    }
    else
    {
      status &= ~(BStatus.DISABLED);
    }

    if (point.getStatus().isOverridden())
    {
      BBacnetServerLayer serverLayer = BBacnetServerLayer.getServerLayer();
      BOverrideMode overrideMode = serverLayer != null ? serverLayer.getOverrideMode() : BOverrideMode.legacy;
      if (overrideMode.getOrdinal() == BOverrideMode.ONE_ONLY)
      {
        int activeLevel = point.getStatus().geti(BStatus.ACTIVE_LEVEL, BPriorityLevel.FALLBACK);
        if (activeLevel == BPriorityLevel.LEVEL_1)
        {
          status |= BStatus.OVERRIDDEN;
        }
      }
      else
      {
        status |= BStatus.OVERRIDDEN;
      }
    }

    //Delete the commented code once all the test cases are passed on BTF
    //if (getReliability() != BBacnetReliability.noFaultDetected)
    //{
    //  status |= BStatus.FAULT;
    //}

    return BStatus.make(status);
  }

  /**
   * Override point for subclasses to provide additional configuration
   * constraints to allow point export.  Default implementation returns true.
   *
   * @return true if configuration is ok, false otherwise.
   */
  protected boolean checkPointConfiguration()
  {
    return true;
  }

  /**
   * Check if the exported object's state is valid.
   */
  @Override
  void checkValid()
  {
    if (configOk()) validate();
  }

  /**
   * Override point for subclasses to validate their exposed point's
   * current state.  Default implementation clears export status fault.
   * Some points may set the BACnet status flags to fault if the Niagara
   * value is disallowed for the exposed BACnet object type.
   */
  protected void validate()
  {
    setStatus(BStatus.makeFault(getStatus(), false));
  }

  /**
   * Is the point configured properly?
   */
  synchronized boolean configOk()
  {
    return configOk;
  }

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return getObjectName() + " [" + getObjectId() + "]";
  }

  /**
   * Find the exposed control point.
   */
  @Override
  public final BControlPoint getPoint()
  {
    if (point == null)
    {
      return findPoint();
    }
    return point;
  }

  /**
   * Allow getPoint to not try too hard to
   * find the exposed control point.
   */
  public final BControlPoint getPoint(boolean force)
  {
    if (point == null)
    {
      return findPoint(force);
    }
    return point;
  }

  /**
   * Get the BAlarmSourceExtension that gives this point
   * alarming capability.
   */
  protected BAlarmSourceExt getAlarmExt()
  {
    BControlPoint point = getPoint();
    if (point == null)
    {
      return null;
    }

    SlotCursor<Property> c = point.getProperties();
    while (c.next(BAlarmSourceExt.class))
    {
      BAlarmSourceExt ext = (BAlarmSourceExt)c.get();
      if (isValidAlarmExt(ext))
      {
        BBacnetObjectIdentifier eventEnrollmentId = BBacnetNetwork.localDevice().lookupBacnetObjectId(ext.getHandleOrd());
        if (eventEnrollmentId == null)
        {
          return ext;
        }
      }
    }

    //None found
    return null;
  }

  public BOutOfServiceExt getOosExt()
  {
    BControlPoint point = getPoint(false);
    if (point == null)
    {
      return null;
    }

    //Capture reference
    BOutOfServiceExt outOfServiceExt = null;

    //Find the first such extension available on the point
    SlotCursor<Property> c = point.getProperties();
    if (c.next(BOutOfServiceExt.class))
    {
      //Use first instance found in parent
      outOfServiceExt = (BOutOfServiceExt)c.get();
    }

    //Add if not found
    if (outOfServiceExt == null)
    {
      outOfServiceExt = new BOutOfServiceExt();
      point.add("outOfServiceExt?", outOfServiceExt);
    }

    //Update the point attributes and return
    outOfServiceExt.setExport(this);
    outOfServiceExt.setCommandable(isCommandable());
    return outOfServiceExt;
  }

  /**
   * Is this export descriptor representing a BACnet object
   * with a Commandable Present_Value property (per the Clause 19
   * prioritization procedure)?<p>
   * Writable descriptors must override this to return true.
   *
   * @return true if commandable, otherwise false
   */
  protected boolean isCommandable()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Get a String representing the BACnet writability of the point.
   */
  public String getBacnetWritable()
  {
    return lexNotWritable;
  }

  private BControlPoint findPoint()
  {
    return findPoint(true);
  }

  private BControlPoint findPoint(boolean force)
  {
    try
    {
      if (force || ordStatus == OrdStatus.CHANGED)
      {
        if (!pointOrd.isEquivalentToDefaultValue(getPointOrd()))
        {
          BObject o = getPointOrd().get(this);
          if (o instanceof BControlPoint)
          {
            point = (BControlPoint)o;
            ordStatus = OrdStatus.OK;
          }
          else
          {
            point = null;
          }

        }
        if (!isPointTypeLegal(point))
        {
          point = null;
        }
      }

    }
    catch (Exception e)
    {
      log.warning("Unable to resolve point ord for " + this + ": " + getPointOrd() + ": " + e);
      point = null;
      ordStatus = OrdStatus.INVALID;
    }

    if ((point == null) && isRunning())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Cannot find exported point");
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    return point;
  }

  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  protected void updateAlarmInhibit()
  {
  }

  /**
   * Attempt to locate a COV or COVProperty subscription for the given subscriber information
   * on this object.
   *
   * @param covProperty
   * @param subscriberAddress
   * @param processId
   * @param objectId
   * @param propertyId
   * @param propertyArrayIndex
   * @return the subscription if found, or null.
   */
  private BBacnetCovSubscription findSubscription(boolean covProperty,
                                                 BBacnetAddress subscriberAddress,
                                                 long processId,
                                                 BBacnetObjectIdentifier objectId,
                                                 int propertyId,
                                                 int propertyArrayIndex)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(BBacnetCovSubscription.class))
    {
      BBacnetCovSubscription sub = (BBacnetCovSubscription)c.get();
      if (sub.isCovProperty() == covProperty &&
          (sub.getRecipient().getRecipient().getAddress().equals(subscriberAddress.getNetworkNumber(), subscriberAddress.getMacAddress().getBytes())) &&
          (sub.getRecipient().getProcessIdentifier().getUnsigned() == processId) &&
          (getObjectId().equals(objectId)) &&
          (sub.getMonitoredPropertyReference().getPropertyId() == propertyId) &&
          (sub.getMonitoredPropertyReference().getPropertyArrayIndex() == propertyArrayIndex))
      {
        return sub;
      }
    }

    return null;
  }

  private void removeOutOfServiceExt()
  {
    BControlPoint point = getPoint();
    if (point != null)
    {
      Object[] outOfServiceExts = point.getChildren(BOutOfServiceExt.class);
      if (outOfServiceExts != null && outOfServiceExts.length > 0 &&
          outOfServiceExts[0] instanceof BOutOfServiceExt)
      {
        point.remove((BOutOfServiceExt) outOfServiceExts[0]);
      }
    }
  }

  private void updateTrendReferences(BLocalBacnetDevice local, BControlPoint point)
  {
    for (BPointExtension pointExt : point.getExtensions())
    {
      if (pointExt instanceof BHistoryExt)
      {
        BIBacnetExportObject trendDescriptor = findDescriptor(local, pointExt);
        if (trendDescriptor instanceof BBacnetTrendLogDescriptor)
        {
          BBacnetDeviceObjectPropertyReference reference = new BBacnetDeviceObjectPropertyReference(getObjectId(), BBacnetPropertyIdentifier.PRESENT_VALUE);
          ((BBacnetTrendLogDescriptor) trendDescriptor).setLogDeviceObjectPropertyReference(reference);
        }
      }
    }
  }

  private void clearTrendReferences(BLocalBacnetDevice local)
  {
    BControlPoint point = getPoint(false);
    if (point == null)
    {
      return;
    }

    for (BPointExtension pointExt : point.getExtensions())
    {
      if (pointExt instanceof BHistoryExt)
      {
        BIBacnetExportObject trendDescriptor = findDescriptor(local, pointExt);
        if (trendDescriptor instanceof BBacnetTrendLogDescriptor)
        {
          ((BBacnetTrendLogDescriptor) trendDescriptor).setLogDeviceObjectPropertyReference(new BBacnetDeviceObjectPropertyReference());
        }
      }
    }
  }

  private static BIBacnetExportObject findDescriptor(BLocalBacnetDevice local, BPointExtension pointExt)
  {
    BBacnetObjectIdentifier trendId = local.lookupBacnetObjectId(pointExt.getHandleOrd());
    if (trendId == null)
    {
      return null;
    }

    return local.lookupBacnetObject(trendId);
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
    out.trTitle("BacnetPointDescriptor", 2);
    out.prop("point", point);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
    out.prop("oldNotifyType", oldNotifyType);
    out.prop("almExt", getAlarmExt());
    out.prop("notificationClass", getNotificationClass());
    out.prop("configOk", configOk());
    out.prop("oosExt", getOosExt());
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BControlPoint point;

  private int[] requiredProps;
  private int[] optionalProps;
  private BacnetPropertyList propertyList;

  private int lastStatusBits = -1;

  private int oldNotifyType;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;
  private boolean configOk;

  private enum OrdStatus
  {
    OK, INVALID, CHANGED
  }

  private OrdStatus ordStatus = OrdStatus.OK;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  static final AsnInputStream asnIn = new AsnInputStream();
  static final AsnOutputStream asnOut = new AsnOutputStream();
  static Logger log = Logger.getLogger("bacnet.server");

  static Lexicon lex = Lexicon.make("bacnet");
  static String lexNotWritable = lex.getText("server.notWritable");
}
