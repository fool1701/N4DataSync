/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.BAlarmState;
import javax.baja.alarm.ext.BOffnormalAlgorithm;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetDateTime;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetSetpointReference;
import javax.baja.bacnet.datatypes.BBacnetTimeStamp;
import javax.baja.bacnet.enums.BBacnetEngineeringUnits;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetEventType;
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
import javax.baja.control.BNumericPoint;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Knob;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
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

/**
 * BBacnetLoopDescriptor is the extension that allows a kitControl
 * BLoopPoint to be exposed to Bacnet.
 *
 * @author Craig Gemmill on 31 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitControl:LoopPoint"
  )
)
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.LOOP)",
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
@NiagaraProperty(
  name = "covIncrement",
  type = "float",
  defaultValue = "0.0F"
)
/*
 This property indicates the maximum period of time between
 updates to the Present_Value in hundredths of a second when
 the input is not overridden and not out-of-service.
 */
@NiagaraProperty(
  name = "updateInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(UPDATE_INTERVAL)",
  flags = Flags.READONLY
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
public class BBacnetLoopDescriptor
  extends BBacnetEventSource
  implements BIBacnetCovSource,
             BacnetPropertyListProvider
{
  public static final long UPDATE_INTERVAL = 10000;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetLoopDescriptor(999080771)1.0$ @*/
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
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.LOOP), null);

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

  //region Property "covIncrement"

  /**
   * Slot for the {@code covIncrement} property.
   * @see #getCovIncrement
   * @see #setCovIncrement
   */
  public static final Property covIncrement = newProperty(0, 0.0F, null);

  /**
   * Get the {@code covIncrement} property.
   * @see #covIncrement
   */
  public float getCovIncrement() { return getFloat(covIncrement); }

  /**
   * Set the {@code covIncrement} property.
   * @see #covIncrement
   */
  public void setCovIncrement(float v) { setFloat(covIncrement, v, null); }

  //endregion Property "covIncrement"

  //region Property "updateInterval"

  /**
   * Slot for the {@code updateInterval} property.
   * This property indicates the maximum period of time between
   * updates to the Present_Value in hundredths of a second when
   * the input is not overridden and not out-of-service.
   * @see #getUpdateInterval
   * @see #setUpdateInterval
   */
  public static final Property updateInterval = newProperty(Flags.READONLY, BRelTime.make(UPDATE_INTERVAL), null);

  /**
   * Get the {@code updateInterval} property.
   * This property indicates the maximum period of time between
   * updates to the Present_Value in hundredths of a second when
   * the input is not overridden and not out-of-service.
   * @see #updateInterval
   */
  public BRelTime getUpdateInterval() { return (BRelTime)get(updateInterval); }

  /**
   * Set the {@code updateInterval} property.
   * This property indicates the maximum period of time between
   * updates to the Present_Value in hundredths of a second when
   * the input is not overridden and not out-of-service.
   * @see #updateInterval
   */
  public void setUpdateInterval(BRelTime v) { set(updateInterval, v, null); }

  //endregion Property "updateInterval"

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
  public static final Type TYPE = Sys.loadType(BBacnetLoopDescriptor.class);

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
  public final void started()
    throws Exception
  {
    super.started();

    // Export the loop and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    checkConfiguration();

    // Increment the Device object's Database_Revision for created objects.
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
    if (getOosExt() != null)
    {
      removeOutOfServiceExt();
    }

    local.unsubscribe(this, point);

    // Clear the local copies.
    point = null;
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted objects.
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
   * Removed.
   */
  @Override
  public final void removed(Property p, BValue oldValue, Context cx)
  {
    super.removed(p, oldValue, cx);
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
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
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
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldName = getObjectName();
      if (configOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(pointOrd))
    {
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
  }

////////////////////////////////////////////////////////////////
//  Actions
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

  /**
   * Send a Cov notification.
   */
  public void doSendCovNotification(BBacnetCovSubscription covSub)
  {
    BNumericPoint pt = getPoint();

    // sanity check - if we missed the end of life, just remove it now
    if (covSub.getTimeRemaining() < 0)
    {
      removeCovSubscription(covSub);
      return;
    }

    if (log.isLoggable(Level.FINE))
    {
      log.fine("Sending Cov Notification: pt=" + pt + ", covSub=" + covSub);
    }
    Cov cov = new Cov(covSub, this, pt);

    BBacnetNetwork.bacnet().postAsync(cov);
    if (covSub.isCovProperty())
    {
      covSub.setLastPropValue(getCurrentCovValue(covSub));
      covSub.setLastStatusBits(getStatusFlags().getBits() & BACNET_SBITS_MASK);
    }
    else
    {
      covSub.setLastValue(getCurrentStatusValue());
    }
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
      if (covSub.isCovProperty())
      {
        try
        {
          PropertyValue pv = readProperty(covSub.getMonitoredPropertyReference().getPropertyId(),
                                          covSub.getMonitoredPropertyReference().getPropertyArrayIndex());
          PropertyInfo pi = BBacnetNetwork.localDevice().getPropertyInfo(getObjectId().getObjectType(),
                                                                         covSub.getMonitoredPropertyReference().getPropertyId());
          BValue cv = AsnUtil.asnToValue(pi, pv.getPropertyValue());

          int cs = getStatusFlags().getBits() & BACNET_SBITS_MASK;
          if (cs != covSub.getLastStatusBits())
          {
            sendCovNotification(covSub);
            return;
          }

          if (pi.getAsnType() == ASN_REAL)
          {
            double diff = Math.abs(((BINumeric)cv).getNumeric() - ((BINumeric)covSub.getLastPropValue()).getNumeric());
            double covIncrement = covSub.getCovIncrement();
            if (Double.isNaN(covIncrement))
            {
              if (covSub.getMonitoredPropertyReference().getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE)
              {
                BDouble d = (BDouble)this.get("covIncrement");
                covIncrement = (d != null) ? d.getDouble() : 0.0D;
              }
              else
              {
                covIncrement = 0.0D;
              }
            }
            if (diff >= covIncrement)
            {
              sendCovNotification(covSub);
            }
          }
          else
          {
            if (!cv.equals(covSub.getLastPropValue()))
            {
              sendCovNotification(covSub);
            }
          }
        }
        catch (AsnException e)
        {
          logger.warning("AsnException occurred in doCheckCov: " + e);
        }
      }
      else
      {
        if (checkCov(getCurrentStatusValue(), covSub.getLastValue()))
        {
          sendCovNotification(covSub);
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
//  BIBacnetExportObject
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
  public void checkConfiguration()
  {
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    // quit if fatal fault
    if (isFatalFault())
    {
      setStatus(BStatus.makeFault(getStatus(), true));
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
      setFaultCause("Cannot find exported loop");
      cfgOk = false;
    }
    else
    {
      local.subscribe(this, point);
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      cfgOk = false;
    }

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
      }
    }

    // Set the exported flag.
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

////////////////////////////////////////////////////////////////
//  BBacnetEventSource
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
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    if (ext instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) ext).getOffnormalAlgorithm().getType().toString().equals("kitControl:LoopAlarmAlgorithm");
    }
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  @Deprecated
  protected void updateAlarmInhibit()
  {
  }

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
  public BEnum getEventType()
  {
    return BBacnetEventType.floatingLimit;
  }

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
    return getPoint().getOutProperty();
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
      logger.warning("AsnException occurred in getCurrentCovValue in object " + getObjectId() + ": " + e);
      return null;
    }
  }

  final BStatusValue getCurrentStatusValue()
  {
    BStatusValue sv = (BStatusValue)getPoint().getOutStatusValue().newCopy(true);
    sv.setStatus(this.getStatusFlags());
    return sv;
  }

  /**
   * Check to see if the current value requires a COV notification.
   */
  boolean checkCov(BStatusValue currentValue, BStatusValue covValue)
  {
    if (currentValue.getStatus().getBits() != covValue.getStatus().getBits())
    {
      return true;
    }

    return Math.abs(((BINumeric)currentValue).getNumeric() - ((BINumeric)covValue).getNumeric()) >= getCovIncrement();
  }

////////////////////////////////////////////////////////////////
//  Bacnet Request Execution
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
          props = REQUIRED_PROPS;
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
          props = REQUIRED_PROPS;
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
  public final RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    getPoint();
    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.OTHER);
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
  public final ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getPoint();
    int[] props = REQUIRED_PROPS;
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
  public final ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    getPoint();
    int[] props = REQUIRED_PROPS;
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
//  Bacnet Support
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
    switch (propertyId)
    {
      case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
      case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
      case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return true;
    }

    return false;
  }

  /**
   * Get the Status_Flags property from the BStatus
   * of the parent point.
   */
  BStatus getStatusFlags()
  {
    getPoint();
    int status = ((point == null) ? BStatus.FAULT : point.getStatus().getBits());
    if ((point != null && point.getStatus().isDown()))
    {
      status |= BStatus.FAULT;
    }
    if ((point != null && ((BStatusBoolean)point.get("loopEnable")).getValue()) && !getOosExt().getOutOfService())
    {
      status &= ~(BStatus.DISABLED);
    }
    else
    {
      status |= BStatus.DISABLED;
    }
    if (getReliability() != BBacnetReliability.noFaultDetected)
    {
      status |= BStatus.FAULT;
    }
    return BStatus.make(status);
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

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, getOptionalProps());
  }

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

    BUnit u;
    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));

        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

        case BBacnetPropertyIdentifier.OBJECT_TYPE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

        case BBacnetPropertyIdentifier.PRESENT_VALUE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(point.getOut().getValue()));

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

        case BBacnetPropertyIdentifier.OUTPUT_UNITS:
          u = (BUnit)point.getFacets().getFacet(BFacets.UNITS);
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.make(u)));

        case BBacnetPropertyIdentifier.MANIPULATED_VARIABLE_REFERENCE:
          return new NReadPropertyResult(pId, ndx, getManipulatedVariableReference());

        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_REFERENCE:
          return new NReadPropertyResult(pId, ndx, getControlledVariableReference());

        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BStatusNumeric)point.get("controlledVariable")).getValue()));

        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_UNITS:
          u = (BUnit)((BFacets)point.get("inputFacets")).getFacet(BFacets.UNITS);
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.make(u)));

        case BBacnetPropertyIdentifier.SETPOINT_REFERENCE:
          return new NReadPropertyResult(pId, ndx, getSetpointReference());

        case BBacnetPropertyIdentifier.SETPOINT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BStatusNumeric)point.get("setpoint")).getValue()));

        case BBacnetPropertyIdentifier.ACTION:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(((BEnum)point.get("loopAction")).getOrdinal()));

        case BBacnetPropertyIdentifier.PROPORTIONAL_CONSTANT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("proportionalConstant")).getDouble()));

        case BBacnetPropertyIdentifier.PROPORTIONAL_CONSTANT_UNITS:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.NO_UNITS));

        case BBacnetPropertyIdentifier.INTEGRAL_CONSTANT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("integralConstant")).getDouble()));

        case BBacnetPropertyIdentifier.INTEGRAL_CONSTANT_UNITS:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.PER_MINUTE));

        case BBacnetPropertyIdentifier.DERIVATIVE_CONSTANT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("derivativeConstant")).getDouble()));

        case BBacnetPropertyIdentifier.DERIVATIVE_CONSTANT_UNITS:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.SECONDS));

        case BBacnetPropertyIdentifier.BIAS:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("bias")).getDouble()));

        case BBacnetPropertyIdentifier.MAXIMUM_OUTPUT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("maximumOutput")).getDouble()));

        case BBacnetPropertyIdentifier.MINIMUM_OUTPUT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)point.get("minimumOutput")).getDouble()));

        case BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(getPriorityForWriting()));

        case BBacnetPropertyIdentifier.COV_INCREMENT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(getCovIncrement()));

        case BBacnetPropertyIdentifier.UPDATE_INTERVAL:
          //Update_interval should return in hunderdths of a second ie., centi seconds.
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned((getUpdateInterval().getMillis()) / 10));

        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          return readPropertyList(ndx);

        default:
          return readOptionalProperty(pId, ndx);
      }
    }
    catch (NullPointerException e)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                              BBacnetErrorCode.UNKNOWN_PROPERTY));
    }
  }

  private PropertyValue readEventState()
  {
    if (!getEventDetectionEnable())
    {
      return makeEventStatePropertyValue(BBacnetEventState.NORMAL);
    }

    BAlarmSourceExt alarmExt = getAlarmExt();
    if (alarmExt == null)
    {
      // Object does not support event reporting, set to Normal.
      return makeEventStatePropertyValue(BBacnetEventState.NORMAL);
    }

    BAlarmState alarmState = alarmExt.getAlarmState();
    if (alarmState.isOffnormal())
    {
      double cv = ((BStatusNumeric) point.get("controlledVariable")).getValue();
      double set = ((BStatusNumeric) point.get("setpoint")).getValue();
      alarmState = cv > set ? BAlarmState.highLimit : BAlarmState.lowLimit;
    }

    return makeEventStatePropertyValue(BBacnetEventState.fromBAlarmState(alarmState));
  }

  private static PropertyValue makeEventStatePropertyValue(int eventState)
  {
    return new NReadPropertyResult(
      BBacnetPropertyIdentifier.EVENT_STATE,
      NOT_USED,
      AsnUtil.toAsnEnumerated(eventState));
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
      BOffnormalAlgorithm alg = almExt.getOffnormalAlgorithm();
      try
      {
        switch (pId)
        {
          case BBacnetPropertyIdentifier.TIME_DELAY:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(almExt.getTimeDelay().getMillis() / BRelTime.MILLIS_IN_SECOND));

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

          case BBacnetPropertyIdentifier.DEADBAND:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)alg.get("deadband")).getDouble()));

          case BBacnetPropertyIdentifier.ERROR_LIMIT:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnReal(((BDouble)alg.get("errorLimit")).getDouble()));

          case BBacnetPropertyIdentifier.EVENT_ENABLE:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(almExt.getAlarmEnable())));

          case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getEventDetectionEnable()));

          case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
            return readAckedTransitions(almExt.getAckedTransitions());
          case BBacnetPropertyIdentifier.NOTIFY_TYPE:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getNotifyType()));

          case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
            return readEventTimeStamps(almExt.getLastOffnormalTime(), almExt.getLastFaultTime(), almExt.getLastToNormalTime(), ndx);

          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
            return readEventMessageTexts(ndx);
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
            return readEventMessageTextsConfig(
              almExt.getToOffnormalText().getFormat(),
              almExt.getToFaultText().getFormat(),
              almExt.getToNormalText().getFormat(),
              ndx);
        }
      }
      catch (NullPointerException e)
      {
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.UNKNOWN_PROPERTY));
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
        NOT_USED,
        AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(eventTrans)));
    }
    else
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, NOT_USED, AsnUtil.toAsnBitString(ACKED_TRANS_DEFAULT));
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

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);

        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.PRESENT_VALUE:
          if (getOosExt().getOutOfService())
          {
            getOosExt().set(BOutOfServiceExt.presentValue, BDouble.make(AsnUtil.fromAsnReal(val)), BLocalBacnetDevice.getBacnetContext());
            return null;
          }
          else
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.STATUS_FLAGS:
        case BBacnetPropertyIdentifier.EVENT_STATE:
        case BBacnetPropertyIdentifier.RELIABILITY:
        case BBacnetPropertyIdentifier.UPDATE_INTERVAL:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
          getOosExt().setBoolean(BOutOfServiceExt.outOfService, AsnUtil.fromOnlyAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.OUTPUT_UNITS:
        case BBacnetPropertyIdentifier.MANIPULATED_VARIABLE_REFERENCE:
        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_REFERENCE:
        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE:
        case BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_UNITS:
        case BBacnetPropertyIdentifier.SETPOINT_REFERENCE:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.SETPOINT:
          BLink[] links = point.getLinks(point.getProperty("setpoint"));
          if (links.length > 0)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
          else
          {
            point.set(point.getProperty("setpoint"),
                      new BStatusNumeric(AsnUtil.fromAsnReal(val)),
                      BLocalBacnetDevice.getBacnetContext());
            return null;
          }

        case BBacnetPropertyIdentifier.ACTION:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.PROPORTIONAL_CONSTANT:
          point.set(point.getProperty("proportionalConstant"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.PROPORTIONAL_CONSTANT_UNITS:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.INTEGRAL_CONSTANT:
          point.set(point.getProperty("integralConstant"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.INTEGRAL_CONSTANT_UNITS:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.DERIVATIVE_CONSTANT:
          point.set(point.getProperty("derivativeConstant"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.DERIVATIVE_CONSTANT_UNITS:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.BIAS:
          point.set(point.getProperty("bias"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.MAXIMUM_OUTPUT:
          point.set(point.getProperty("maximumOutput"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.MINIMUM_OUTPUT:
          point.set(point.getProperty("minimumOutput"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
          return null;

        case BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.COV_INCREMENT:
          setFloat(covIncrement, AsnUtil.fromAsnReal(val), BLocalBacnetDevice.getBacnetContext());
          checkCov();
          return null;

        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);

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
    catch (NullPointerException e)
    {
      log.warning("Exception writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
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
        BOffnormalAlgorithm alg = almExt.getOffnormalAlgorithm();
        switch (pId)
        {
          case BBacnetPropertyIdentifier.TIME_DELAY:
            almExt.set(BAlarmSourceExt.timeDelay,
                       BRelTime.make(AsnUtil.fromAsnUnsignedInteger(val) * BRelTime.MILLIS_IN_SECOND),
                       BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.DEADBAND:
            alg.set(alg.getProperty("deadband"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.ERROR_LIMIT:
            alg.set(alg.getProperty("errorLimit"),
                    BDouble.make(AsnUtil.fromAsnReal(val)),
                    BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
            int ncinst = AsnUtil.fromAsnUnsignedInt(val);
            if (ncinst > BBacnetObjectIdentifier.MAX_INSTANCE_NUMBER)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BBacnetNotificationClassDescriptor ncd = (BBacnetNotificationClassDescriptor)BBacnetNetwork.localDevice().lookupBacnetObject(BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS, ncinst));
            if (ncd == null)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            BAlarmClass ac = ncd.getAlarmClass();
            almExt.setAlarmClass(ac.getName());
            return null;

          case BBacnetPropertyIdentifier.EVENT_ENABLE:
            almExt.set(BAlarmSourceExt.alarmEnable,
                       BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                       BLocalBacnetDevice.getBacnetContext());
            return null;
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS_CONFIG:
            return writeEventMessageTextsConfig(ndx, val, almExt);
          case BBacnetPropertyIdentifier.EVENT_DETECTION_ENABLE:
            setBoolean(eventDetectionEnable, AsnUtil.fromAsnBoolean(val), BLocalBacnetDevice.getBacnetContext());
            return null;
          case BBacnetPropertyIdentifier.ACKED_TRANSITIONS:
          case BBacnetPropertyIdentifier.NOTIFY_TYPE:
          case BBacnetPropertyIdentifier.EVENT_TIME_STAMPS:
          case BBacnetPropertyIdentifier.EVENT_MESSAGE_TEXTS:
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }
      }
    }
    catch (OutOfRangeException e)
    {
      log.warning("OutOfRangeException writing property " + pId + " in object " + getObjectId() + ": " + e);
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
    catch (NullPointerException e)
    {
      log.warning("Exception writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
    }

    return new NErrorType(BBacnetErrorClass.PROPERTY,
                          BBacnetErrorCode.UNKNOWN_PROPERTY);
  }

  /**
   * Get all the optional properties for this object.
   *
   * @return the list as an array of BDiscretes.
   */
  private int[] getOptionalProps()
  {
    ArrayList<BBacnetPropertyIdentifier> v = new ArrayList<>();
    v.add(BBacnetPropertyIdentifier.description);
    v.add(BBacnetPropertyIdentifier.reliability);
    v.add(BBacnetPropertyIdentifier.proportionalConstant);
    v.add(BBacnetPropertyIdentifier.proportionalConstantUnits);
    v.add(BBacnetPropertyIdentifier.integralConstant);
    v.add(BBacnetPropertyIdentifier.integralConstantUnits);
    v.add(BBacnetPropertyIdentifier.derivativeConstant);
    v.add(BBacnetPropertyIdentifier.derivativeConstantUnits);
    v.add(BBacnetPropertyIdentifier.bias);
    v.add(BBacnetPropertyIdentifier.maximumOutput);
    v.add(BBacnetPropertyIdentifier.minimumOutput);
    v.add(BBacnetPropertyIdentifier.covIncrement);
    v.add(BBacnetPropertyIdentifier.updateInterval);

    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      BOffnormalAlgorithm alg = almExt.getOffnormalAlgorithm();
      if (alg.getType().getTypeName().equals("LoopAlarmAlgorithm"))
      {
        v.add(BBacnetPropertyIdentifier.timeDelay);
        v.add(BBacnetPropertyIdentifier.notificationClass);
        v.add(BBacnetPropertyIdentifier.eventEnable);
        v.add(BBacnetPropertyIdentifier.ackedTransitions);
        v.add(BBacnetPropertyIdentifier.notifyType);
        v.add(BBacnetPropertyIdentifier.eventTimeStamps);
        v.add(BBacnetPropertyIdentifier.eventMessageTexts);
        v.add(BBacnetPropertyIdentifier.eventMessageTextsConfig);
        v.add(BBacnetPropertyIdentifier.deadband);
        v.add(BBacnetPropertyIdentifier.errorLimit);
        v.add(BBacnetPropertyIdentifier.eventDetectionEnable);
      }
    }

    optionalProps = new int[v.size()];
    for (int i = 0; i < optionalProps.length; i++)
    {
      optionalProps[i] = ((BEnum) v.get(i)).getOrdinal();
    }

    return optionalProps;
  }

////////////////////////////////////////////////////////////////
// Access methods
////////////////////////////////////////////////////////////////

  /**
   * Check if the exported object's state is valid.
   */
  @Override
  void checkValid()
  {
    if (configOk())
    {
      validate();
    }
  }

  /**
   * Is the point configured properly?
   */
  private synchronized boolean configOk()
  {
    return configOk;
  }

  /**
   * Override point for subclasses to validate their exposed point's
   * current state.  Default implementation does nothing.  Some points may
   * set the BACnet status flags to fault if the Niagara value is disallowed
   * for the exposed BACnet object type.
   */
  private void validate()
  {
    BStatusNumeric sn = (getPoint()).getOut();
    BStatus s = sn.getStatus();
    if (s.isNull())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Invalid value for BACnet Object:" + sn);
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    else if ((s.isFault()) || s.isDown())
    {
      setReliability(BBacnetReliability.unreliableOther);
    }
    else
    {
      setReliability(BBacnetReliability.noFaultDetected);
      if (configOk())
      {
        setStatus(BStatus.makeFault(getStatus(), false));
        setFaultCause("");
      }
      else
      {
        setStatus(BStatus.makeFault(getStatus(), true));
        setFaultCause(lex.getText("export.configurationFault"));
      }
    }
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
  public final BNumericPoint getPoint()
  {
    if (point == null)
    {
      return findPoint();
    }
    return point;
  }

  /**
   * Get the BAlarmSourceExtension that gives this point
   * alarming capability.
   */
  private BAlarmSourceExt getAlarmExt()
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
        return ext;
      }
    }

    //None found
    return null;
  }

  private BOutOfServiceExt getOosExt()
  {
    BControlPoint point = getPoint();
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
   * @return
   */
  private static boolean isCommandable()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  private BNumericPoint findPoint()
  {
    try
    {
      if (!pointOrd.isEquivalentToDefaultValue(getPointOrd()))
      {
        BObject o = getPointOrd().get(this);
        if ((o instanceof BNumericPoint) && o.getType().toString().equals("kitControl:LoopPoint"))
        {
          point = (BNumericPoint) o;
        }
        else
        {
          point = null;
        }
      }
    }
    catch (Exception e)
    {
      log.warning("Unable to resolve point ord for " + this + ": " + getPointOrd());
      point = null;
    }

    if ((point == null) && isRunning())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Cannot find exported loop");
      setStatus(BStatus.makeFault(getStatus(), true));
    }

    return point;
  }

  private byte[] getManipulatedVariableReference()
  {
    BBacnetObjectPropertyReference opr = NULL_OPR;
    Knob[] knobs = point.getKnobs(point.getProperty("out"));
    if (knobs.length > 0)
    {
      BOrd tgtOrd = knobs[0].getTargetOrd();
      BBacnetObjectIdentifier tgtId = BBacnetNetwork.localDevice().lookupBacnetObjectId(tgtOrd);
      if (tgtId != null)
      {
        opr = new BBacnetObjectPropertyReference(tgtId);
      }
    }
    return AsnUtil.toAsn(opr);
  }

  private byte[] getControlledVariableReference()
  {
    BBacnetObjectPropertyReference opr = NULL_OPR;
    BLink[] links = point.getLinks(point.getSlot("controlledVariable"));
    if (links.length > 0)
    {
      BOrd tgtOrd = links[0].getSourceComponent().getAbsoluteOrd();
      BBacnetObjectIdentifier tgtId = BBacnetNetwork.localDevice().lookupBacnetObjectId(tgtOrd);
      if (tgtId != null)
      {
        opr = new BBacnetObjectPropertyReference(tgtId);
      }
    }
    return AsnUtil.toAsn(opr);
  }

  private byte[] getSetpointReference()
  {
    BLink[] links = point.getLinks(point.getProperty("setpoint"));
    if (links.length > 0)
    {
      BOrd srcOrd = links[0].getSourceComponent().getAbsoluteOrd();
      BBacnetObjectIdentifier srcId = BBacnetNetwork.localDevice().lookupBacnetObjectId(srcOrd);
      if (srcId != null)
      {
        BBacnetSetpointReference spr = new BBacnetSetpointReference(new BBacnetObjectPropertyReference(srcId));
        return AsnUtil.toAsn(spr);
      }
    }
    return new byte[0];
  }

  private int getPriorityForWriting()
  {
    Knob[] knobs = point.getKnobs(point.getProperty("out"));
    if (knobs.length == 0)
    {
      return 16;
    }
    String s = knobs[0].getTargetSlotName();
    try
    {
      return Integer.parseInt(s.substring(2));
    }
    catch (Exception e)
    {
      return 16;
    }
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

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetLoopDescriptor", 2);
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
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make(BIcon.std("control/numericPoint.png"), BIcon.std("badges/export.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BNumericPoint point;

  private int[] optionalProps;

  private int lastStatusBits = -1;

  private int oldNotifyType;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;
  private boolean configOk;

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  static final AsnInputStream asnIn = new AsnInputStream();
  static final AsnOutputStream asnOut = new AsnOutputStream();
  static Logger log = Logger.getLogger("bacnet.server");
  static Lexicon lex = Lexicon.make("bacnet");

  private static final BBacnetObjectPropertyReference NULL_OPR = new BBacnetObjectPropertyReference();

  private static final int[] REQUIRED_PROPS =
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.PRESENT_VALUE,
      BBacnetPropertyIdentifier.STATUS_FLAGS,
      BBacnetPropertyIdentifier.EVENT_STATE,
      BBacnetPropertyIdentifier.OUT_OF_SERVICE,
      BBacnetPropertyIdentifier.OUTPUT_UNITS,
      BBacnetPropertyIdentifier.MANIPULATED_VARIABLE_REFERENCE,
      BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_REFERENCE,
      BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_VALUE,
      BBacnetPropertyIdentifier.CONTROLLED_VARIABLE_UNITS,
      BBacnetPropertyIdentifier.SETPOINT_REFERENCE,
      BBacnetPropertyIdentifier.SETPOINT,
      BBacnetPropertyIdentifier.ACTION,
      BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING,
    };
}
