/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import static javax.baja.bacnet.datatypes.BBacnetDate.UNSPECIFIED;

import static com.tridium.bacnet.util.BacnetAlarmRecipientUtil.getEventObjectId;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.AlarmDbConnection;
import javax.baja.alarm.BAckState;
import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmRecipient;
import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmService;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.BSourceState;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventState;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.export.BBacnetEventSource;
import javax.baja.bacnet.export.BIBacnetExportObject;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.virtual.BBacnetVirtualProperty;
import javax.baja.bacnet.virtual.BacnetVirtualUtil;
import javax.baja.category.BCategoryMask;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BTime;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTimeRange;

import com.tridium.bacnet.history.BBacnetTrendLogAlarmSourceExt;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.client.AsyncEventNotificationRequest;

/**
 * BBacnetDestination represents the Bacnet Destination
 * sequence, used in the Recipient_List of Notification
 * Class objects.
 *
 * @author    Craig Gemmill
 * @creation  31 Jul 01
 * @version   $Revision: 5$ $Date: 12/10/01 9:26:07 AM$
 * @since     Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "timeRange",
  type = "BTimeRange",
  defaultValue = "new BTimeRange(BTime.make(0, 0, 0, 0), BTime.make(23, 59, 59, 999))",
  override = true
)
@NiagaraProperty(
  name = "transitions",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.make(BAlarmTransitionBits.TO_OFFNORMAL | BAlarmTransitionBits.TO_FAULT | BAlarmTransitionBits.TO_NORMAL)",
  override = true
)
@NiagaraProperty(
  name = "routeAcks",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "recipient",
  type = "BBacnetRecipient",
  defaultValue = "new BBacnetRecipient()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "processIdentifier",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)"
)
@NiagaraProperty(
  name = "issueConfirmedNotifications",
  type = "boolean",
  defaultValue = "false"
)
public class BBacnetDestination
  extends BAlarmRecipient
  implements BIBacnetDataType,
             BacnetAlarmConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetDestination(358557917)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "timeRange"

  /**
   * Slot for the {@code timeRange} property.
   * @see #getTimeRange
   * @see #setTimeRange
   */
  public static final Property timeRange = newProperty(0, new BTimeRange(BTime.make(0, 0, 0, 0), BTime.make(23, 59, 59, 999)), null);

  //endregion Property "timeRange"

  //region Property "transitions"

  /**
   * Slot for the {@code transitions} property.
   * @see #getTransitions
   * @see #setTransitions
   */
  public static final Property transitions = newProperty(0, BAlarmTransitionBits.make(BAlarmTransitionBits.TO_OFFNORMAL | BAlarmTransitionBits.TO_FAULT | BAlarmTransitionBits.TO_NORMAL), null);

  //endregion Property "transitions"

  //region Property "routeAcks"

  /**
   * Slot for the {@code routeAcks} property.
   * @see #getRouteAcks
   * @see #setRouteAcks
   */
  public static final Property routeAcks = newProperty(Flags.READONLY, true, null);

  //endregion Property "routeAcks"

  //region Property "recipient"

  /**
   * Slot for the {@code recipient} property.
   * @see #getRecipient
   * @see #setRecipient
   */
  public static final Property recipient = newProperty(Flags.SUMMARY, new BBacnetRecipient(), null);

  /**
   * Get the {@code recipient} property.
   * @see #recipient
   */
  public BBacnetRecipient getRecipient() { return (BBacnetRecipient)get(recipient); }

  /**
   * Set the {@code recipient} property.
   * @see #recipient
   */
  public void setRecipient(BBacnetRecipient v) { set(recipient, v, null); }

  //endregion Property "recipient"

  //region Property "processIdentifier"

  /**
   * Slot for the {@code processIdentifier} property.
   * @see #getProcessIdentifier
   * @see #setProcessIdentifier
   */
  public static final Property processIdentifier = newProperty(0, BBacnetUnsigned.make(0), null);

  /**
   * Get the {@code processIdentifier} property.
   * @see #processIdentifier
   */
  public BBacnetUnsigned getProcessIdentifier() { return (BBacnetUnsigned)get(processIdentifier); }

  /**
   * Set the {@code processIdentifier} property.
   * @see #processIdentifier
   */
  public void setProcessIdentifier(BBacnetUnsigned v) { set(processIdentifier, v, null); }

  //endregion Property "processIdentifier"

  //region Property "issueConfirmedNotifications"

  /**
   * Slot for the {@code issueConfirmedNotifications} property.
   * @see #getIssueConfirmedNotifications
   * @see #setIssueConfirmedNotifications
   */
  public static final Property issueConfirmedNotifications = newProperty(0, false, null);

  /**
   * Get the {@code issueConfirmedNotifications} property.
   * @see #issueConfirmedNotifications
   */
  public boolean getIssueConfirmedNotifications() { return getBoolean(issueConfirmedNotifications); }

  /**
   * Set the {@code issueConfirmedNotifications} property.
   * @see #issueConfirmedNotifications
   */
  public void setIssueConfirmedNotifications(boolean v) { setBoolean(issueConfirmedNotifications, v, null); }

  //endregion Property "issueConfirmedNotifications"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDestination.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BBacnetDestination()
  {
  }


////////////////////////////////////////////////////////////////
//  BAlarmRecipient implementation
////////////////////////////////////////////////////////////////

  /**
   * Handle an alarm.
   * @param alarmRecord the alarm.
   */
  public final void handleAlarm(BAlarmRecord alarmRecord)
  {
    // The time has already been validated, as has the transition type.
    // Validate that the object initiating the alarm
    // has been exposed to Bacnet.
    try
    {
      boolean traceOn = log.isLoggable(Level.FINE);
      if (traceOn)
        log.fine("handleAlarm on "+SlotPath.unescape(getName())+":"+alarmRecord+"\n alarmData="+alarmRecord.getAlarmData()+"\n uuid="+alarmRecord.getUuid());

      // First, resolve the alarm source.
      // The source is an alarm extension on the actual object.
      // We need to get the exporting BIBacnetExportObject if it exists.
      BObject alarmSource = alarmRecord.getSource().get(0).get(this);
      BComponent eventObject = alarmSource.asComplex().getParent().asComponent();
      
      BBacnetObjectIdentifier eventObjectId = getEventObjectId(alarmSource);

      if (eventObjectId == null)
      {
        log.warning("Alarm "+alarmRecord.getUuid()+" not sent to "+getRecipient()
          +": object "+eventObject.getName()+" not exposed to BACnet!");
        return;
      }

      // toState should ALWAYS be here for our alarms; use sourceState as backup
      BString toSt = ((BString)alarmRecord.getAlarmFacet(BAlarmRecord.TO_STATE));
      BEnum toState = (toSt != null) ?
        BBacnetEventState.make(toSt.getString()) :
        BBacnetEventState.make(alarmRecord.getSourceState());

      BString fromSt = ((BString)alarmRecord.getAlarmFacet(BAlarmRecord.FROM_STATE));
      BEnum fromState = (fromSt != null) ?
        BBacnetEventState.make(fromSt.getString()) :
        BBacnetEventState.make(alarmRecord.getSourceState());

      boolean ackAlarmAndNormal = false;
//    FIXX: remove BAC_STATE_ACKED & make the necessary decisions based on other info
//    We CANNOT use this facet, because it is NOT set when the ack source is Niagara!
      BInteger stateAcked = (BInteger)alarmRecord.getAlarmFacet(BAC_STATE_ACKED);

      BAlarmService alarmService = (BAlarmService)Sys.getService(BAlarmService.TYPE);
      BAlarmClass alarmClass = alarmService.lookupAlarmClass(alarmRecord.getAlarmClass());
      BSourceState newToState = alarmRecord.getSourceState();
//    FIXX: NCCB-10507
      /* Adding a new facet to handle the transition for this particular destination
       * and each destination handles it's own updating.
       * facet = key: "destinationPath". for eg: if the bacnet destination has name bacnetClient,
       *                                         the key is "\bacnetClient"
       *         value: alarm record's toState. for eg: if the toState is toNormal the ordinal value
       *                                                of normal BSourceState ie, O is added.
       */
      String destinationPath =  this.getSlotPathOrd().toString(null).substring(alarmService.getSlotPathOrd().toString(null).length());
      BInteger oldToState = (BInteger)alarmRecord.getAlarmFacet(SlotPath.escape(destinationPath));

      //Decision of alarm or acknowledgement is based on the state change
      boolean stateChanged = oldToState != null ? oldToState.getInt()!= newToState.getOrdinal() : true;
      alarmRecord.addAlarmFacet(SlotPath.escape(destinationPath), BInteger.make(newToState.getOrdinal()));
      if (traceOn) log.fine("stateChanged="+stateChanged+" ar:"+ackDump(alarmRecord));

      if(stateChanged)
      {
        /* If the stateAcked facet exists, then
         *  - one of the (multiple) outstanding alarm states has been acked
         *  - we are receiving this alarm because the "semi-acked" alarm has
         *    been routed to us.  Note that ackAlarm() has NOT been called yet.
         * If stateAcked does not exist, this is a NEW alarm, so
         *  - add the appropriate bit to the acksRequired bit string.
         */
        if (traceOn) log.fine("stateAcked="+stateAcked+" ar:"+ackDump(alarmRecord));
        if (stateAcked == null)
        {
          if (traceOn) log.fine(" new alarm - add acksReq to BAC_ACK_REQUIRED");
          int acksReq = 0;

          if(alarmSource instanceof BAlarmSourceExt)
          {
            BInteger acksReqFacet = (BInteger) alarmRecord.getAlarmFacet(BAC_ACK_REQUIRED);
            if (acksReqFacet != null) acksReq = acksReqFacet.getInt();
          }

          BAlarmTransitionBits acAckReq = alarmClass.getAckRequired();
          if (BBacnetEventState.isOffnormal(toState))
          {
            if (acAckReq.isToOffnormal())
            {
              alarmRecord.removeAlarmFacet(BAC_OFFNORMAL_ACKED);
              acksReq |= TO_OFFNORMAL_BIT;
            }
          }
          else if (BBacnetEventState.isFault(toState))
          {
            if (acAckReq.isToFault())
            {
              acksReq |= TO_FAULT_BIT;
            }
          }
          else if (BBacnetEventState.isNormal(toState))
          {
            boolean isNormalAlarmEnabledForTrendLogExt = (alarmSource instanceof BBacnetTrendLogAlarmSourceExt) &&
            !((BBacnetTrendLogAlarmSourceExt)alarmSource).getAlarmEnable().isToNormal();
            boolean isNormalAlarmEnabledForAlarmSrcLogExt = (alarmSource instanceof BAlarmSourceExt) &&
              (!((BAlarmSourceExt)alarmSource).getAlarmEnable().isToNormal());

            if(isNormalAlarmEnabledForTrendLogExt || isNormalAlarmEnabledForAlarmSrcLogExt)
            {
             return;
            }

            if (acAckReq.isToNormal())
            {
              acksReq |= TO_NORMAL_BIT;
            }
          }
          alarmRecord.addAlarmFacet(BAC_ACK_REQUIRED, BInteger.make(acksReq));
        } // end of non-stateAcked processing

        // Add this destination to the alarm's list of notification recipients
        addToNotifyList(alarmRecord);
        try (AlarmDbConnection conn = alarmService.getAlarmDb().getDbConnection(null))
        {
          conn.update(alarmRecord);
        }

      }
      else if((alarmRecord.isAcknowledged() && alarmClass.getAckRequired().includes(alarmRecord.getSourceState())) ||
        alarmRecord.getAckState() == BAckState.ackPending)
      {
        // Get the alarm record out of the appropriate event buffer.
        // Use OUR device objectId because WE initiated the original event.
        BBacnetObjectIdentifier deviceId = BBacnetNetwork.localDevice().getObjectId();

        if (BBacnetEventState.isNormal(toState))
        {
          BBoolean offnormalAcked = (BBoolean)alarmRecord.getAlarmFacet(BAC_OFFNORMAL_ACKED);
          if (offnormalAcked != null)
            ackAlarmAndNormal = !offnormalAcked.getBoolean();
          else
          {
//            BString fromSt = ((BString)alarmRecord.getAlarmFacet(BAlarmRecord.FROM_STATE));
            ackAlarmAndNormal = toSt == null || !toSt.equals(fromSt);
          }
        }
        else if (BBacnetEventState.isOffnormal(toState))
        {
          alarmRecord.addAlarmFacet(BAC_OFFNORMAL_ACKED, BBoolean.TRUE);
          try (AlarmDbConnection conn = alarmService.getAlarmDb().getDbConnection(null))
          {
            conn.update(alarmRecord);
          }
        }
        else
        {
          // fault acknowledgment processing
        }

        if (traceOn) log.fine("acknowledged - stateAcked="+stateAcked+" ar:"+ackDump(alarmRecord)+", toState(alarm)="+toState);
        if (stateAcked != null)
        {
          toState = BBacnetEventState.make(stateAcked.getInt());
        }

        // Get record from the event handler's event buffer.  Do not remove
        // until we confirm this is the last notification recipient.
        BAlarmRecord rec = ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler()
          .getRecordFromEventBuffer(toState.getOrdinal(),
                                    deviceId,
                                    eventObjectId,
                                    LOCAL_PROCESS_ID,
                                    alarmRecord.getUuid(),
                                    false);
        if (traceOn) log.fine("dest "+getName()+": rec="+((rec!=null)?rec.getUuid().toString():"null"));
        if (rec == null)
        {
          if (traceOn) log.fine("Skipping event notification: No matching record in event buffer for "+deviceId
                                 +" "+eventObjectId+" "+LOCAL_PROCESS_ID+" with UUID "+alarmRecord.getUuid());
          return;
        }

//        // Remove this destination from the notification list.  If no more
//        // recipients exist, remove the record from the event buffer.
//        if (!removeFromNotifyList(alarmRecord))
//        {
//          ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getServer().getEventHandler()
//            .getRecordFromEventBuffer(toState.getOrdinal(),
//                                      deviceId,
//                                      eventObjectId,
//                                      LOCAL_PROCESS_ID,
//                                      alarmRecord.getUuid(),
//                                      true);  // remove event
//          if (traceOn) log.trace("REMOVED record from event buffer...");
//        }
        removeFromNotifyList(alarmRecord);
      } // end of acknowledged alarm case
      else
      {
        return;
      }

      // Skip sending event notification if, EventDetectionEnable is set to false for an event source.
      BBacnetEventSource evtSrc = null;
      try
      {
        evtSrc = (BBacnetEventSource)BBacnetNetwork.localDevice().lookupBacnetObject(eventObjectId);
      }
      catch (ClassCastException e)
      {
        if (traceOn)
          log.fine("BBacnetObjectIdentifier is not an BacnetEventSource");
      }
      if (evtSrc != null && !evtSrc.getEventDetectionEnable())
      {
        log.fine("Do not send event notification for an when event source, eventdetectionenable is false");
        return;
      }

      // Skip sending alarm if this was a stale ack.
      BBoolean stale = (BBoolean)alarmRecord.getAlarmFacet(BAC_STALE_ACK);
      if ((stale != null) && stale.getBoolean())
      {
        log.fine("Skipping event notification for stale ack (BAC_STALE_ACK)");
        return;
      }

      if (newToState.equals(BSourceState.normal) &&
          alarmSource instanceof BAlarmSourceExt &&
          !((BAlarmSourceExt)alarmSource).isLastNormalRecord(alarmRecord))
      {
        log.fine("Skipping event notification for to-normal transition of record that is not " +
          "the alarm source's last normal record");
        return;
      }

      if(checkForEventTypeBasedSpecialHandling(fromState, toState, eventObjectId.getObjectType()))
      {
          faultToOffNormalTransition(alarmRecord, eventObjectId, eventObject, ackAlarmAndNormal, stateChanged);
      }
      else
      {
        // Then post the request to send a BACnet alarm.  Validation will
        // be handled in the request execution.
        AsyncEventNotificationRequest request =
          new AsyncEventNotificationRequest(alarmRecord,
            eventObjectId,
            eventObject,
            getProcessIdentifier().getUnsigned(),
            getRecipient(),
            getIssueConfirmedNotifications(),
            ackAlarmAndNormal
          );
        request.setAlarm(stateChanged);
        sendNotification(request);
      }
    }
    catch (Exception e)
    {
      log.log(Level.SEVERE,"Exception handling alarm in "+getName()+":"+ e,e);
    }
  }

  private boolean checkForEventTypeBasedSpecialHandling(BEnum fromState, BEnum toState, int objectType)
  {
    return
      (
        (BBacnetEventState.isFault(fromState) && BBacnetEventState.isOffnormal(toState))
          &&
        (
          objectType == BBacnetObjectType.MULTI_STATE_VALUE ||
          objectType == BBacnetObjectType.MULTI_STATE_INPUT ||
          objectType == BBacnetObjectType.MULTI_STATE_OUTPUT
        )
      );
  }

  private void faultToOffNormalTransition(BAlarmRecord alarmRecord, BBacnetObjectIdentifier eventObjectId, BComponent eventObject, boolean ackAlarmAndNormal, boolean stateChanged)
  {
    BBacnetObjectIdentifier oid = BBacnetNetwork.localDevice().lookupBacnetObjectId(eventObject.getHandleOrd());
    BIBacnetExportObject descriptor = BBacnetNetwork.localDevice().lookupBacnetObject(oid);

    int[] eventPriorities = ((BBacnetEventSource)descriptor).getEventPriorities();

    int eventPriority = eventPriorities[2];
    alarmRecord.setPriority(eventPriority);
    notification(alarmRecord, eventObjectId, eventObject, ackAlarmAndNormal, stateChanged, BBacnetEventState.fault, BBacnetEventState.normal);

    eventPriority = eventPriorities[0];
    alarmRecord.setPriority(eventPriority);
    notification(alarmRecord, eventObjectId, eventObject, ackAlarmAndNormal, stateChanged, BBacnetEventState.normal, BBacnetEventState.offnormal);
  }

  private void notification(BAlarmRecord alarmRecord, BBacnetObjectIdentifier eventObjectId, BComponent eventObject, boolean ackAlarmAndNormal, boolean stateChanged, BEnum from, BEnum to)
  {
    BOrd source = alarmRecord.getSource().get(0);
    String alarmClass = alarmRecord.getAlarmClass();
    BFacets alarmData = (BFacets)alarmRecord.getAlarmData().newCopy();

    alarmData = BFacets.make(alarmData, BAlarmRecord.FROM_STATE, BString.make(from.getTag()));
    alarmData = BFacets.make(alarmData, BAlarmRecord.TO_STATE, BString.make(to.getTag()));
    BAlarmRecord alarmRecord_transition = new BAlarmRecord(source, alarmClass, alarmData);
    alarmRecord_transition.setPriority(alarmRecord.getPriority());

    AsyncEventNotificationRequest request =
      new AsyncEventNotificationRequest(alarmRecord_transition,
        eventObjectId,
        eventObject,
        getProcessIdentifier().getUnsigned(),
        getRecipient(),
        getIssueConfirmedNotifications(),
        ackAlarmAndNormal
      );
    request.setAlarm(stateChanged);
    sendNotification(request);
  }

  protected void sendNotification(AsyncEventNotificationRequest request)
  {
    BBacnetNetwork.bacnet().postAsync(request);
  }

////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Is the specified recipient equal to this destination's recipient?
   * @param recip
   * @return true if the recipient is equal.
   */
  public final boolean recipientEquals(BBacnetRecipient recip)
  {
    return getRecipient().equivalent(recip);
  }

  /**
   * Compare if all of this object's properties are
   * equal to the specified object.
   */
  public final boolean destinationEquals(BBacnetDestination dest)
    { return destinationEquals(dest, false); }

  public final boolean destinationEquals(BBacnetDestination dest, boolean compareMillis)
  {
    if (dest == null) return false;

    int mask = BAlarmTransitionBits.ALL.getBits() & ~BAlarmTransitionBits.TO_ALERT;
    int mybits = getTransitions().getBits() & mask;
    int dbits = dest.getTransitions().getBits() & mask;
    if (timeRangesEquivalent(getTimeRange(), dest.getTimeRange(), compareMillis)
      && getDaysOfWeek().equals(dest.getDaysOfWeek())
      && (mybits == dbits)
      && (getTransitions().getBits() == dest.getTransitions().getBits())
      && getRecipient().equivalent(dest.getRecipient())
      && getProcessIdentifier().equals(dest.getProcessIdentifier())
      && (getIssueConfirmedNotifications() == (dest.getIssueConfirmedNotifications())))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public final boolean timeRangesEquivalent(BTimeRange tr1, BTimeRange tr2, boolean compareMillis)
  {
    if (compareMillis)
    {
      return tr1.equivalent(tr2);
    }
    else
    {
      return tr1.getStartTime().getHour() == tr2.getStartTime().getHour()
          && tr1.getStartTime().getMinute() == tr2.getStartTime().getMinute()
          && tr1.getStartTime().getSecond() == tr2.getStartTime().getSecond()
          && tr1.getEndTime().getHour() == tr2.getEndTime().getHour()
          && tr1.getEndTime().getMinute() == tr2.getEndTime().getMinute()
          && tr1.getEndTime().getSecond() == tr2.getEndTime().getSecond();
    }
  }


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public final void started()
  {
    if (getParent() instanceof BBacnetListOf)
      client = true;
    if (Sys.atSteadyState() && isRunning())
      resolveRecipient();
  }

  /**
   * Bubble changes up to the parent component.
   */
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (p.equals(recipient))
    {
      resolveRecipient();
    }
    BComplex parent = getParent();
    if (parent != null)
      parent.asComponent().changed(getPropertyInParent(), cx);
    // vfixx: throw changed w/ GCC context?
  }

  /**
   * On startup, discover the address of the recipient if it is a device-type
   * recipient, and not already mapped in our network.
   */
  public final void atSteadyState()
    throws Exception
  {
    super.atSteadyState();

    resolveRecipient();
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
    if (vp != null) vp.childSubscribed(this);
  }

  /**
   * BBacnetDestinations can only be placed directly in the Alarm Service.
   * This aids in looking them up for handling acknowledgments.
   */
  public final boolean isParentLegal(BComponent parent)
    { return (parent instanceof BAlarmService) || (parent instanceof BBacnetListOf); }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public final BCategoryMask getAppliedCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getAppliedCategoryMask();
    return super.getAppliedCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public final BCategoryMask getCategoryMask()
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getCategoryMask();
    return super.getCategoryMask();
  }

  /**
   * Override to route to the virtual parent when we are in a virtual space.
   */
  public final BPermissions getPermissions(Context cx)
  {
    if (BacnetVirtualUtil.isVirtual(this)) return getParent().asComponent().getPermissions(cx);
    return super.getPermissions(cx);
  }

  /**
   * Check to see if the alarm falls within the time and day
   * ranges and the transitions.
   * This overrides the superclass behavior to comply with the
   * specific requirements of the BACnet standard.
   */
  public boolean accept(BAlarmRecord rec)
  {
    // The timestamp to use should be the timestamp of the
    // current update, not the original alarm, per BTL.
    // The commented code would set the timestamp to accept
    // ack notifications if the original event which was acked
    // was within the valid time period.
//    BAbsTime ts = rec.isAcknowledged() ?
//                    rec.getTimestamp() :
//                    BAbsTime.now();
    BAbsTime ts = BAbsTime.now();

    // Check day of week
    if (!getDaysOfWeek().includes(ts.getWeekday()))
      return false;

    // Check time range
    if (!getTimeRange().includes(ts))
      return false;

    //only send alarm if transitions are set
    if (!getTransitions().includes(rec.getSourceState()))
      return false;

// routeAcks is always true for BBacnetDestination    
//    //do not send if last transition was an ack.
//    /**
//     * if !getRouteAcks()
//     *   if isAcknowledged()
//     *     if getNormalTime() != null
//     *       if getNormalTime().isBefore(getAckTime())
//     *         return false;
//     *       else continue
//     *     else return false
//     *   else continue
//     * else continue;
//     *
//     */
//    if (!getRouteAcks() && rec.isAcknowledged())
//    {
//      if (rec.getNormalTime().isNull())
//        return false;
//      else if (rec.getNormalTime().isBefore(rec.getAckTime()))
//        return false;
//    }

    return true;
  }


////////////////////////////////////////////////////////////////
//  Support
////////////////////////////////////////////////////////////////

  private void resolveRecipient()
  {
    // We don't need to resolve to address for destinations contained in other devices.
    // If this is a destination for one of our NCs, resolve device id to address.
    if (!client)
    {
      if (getRecipient().isDevice())
      {
        BBacnetObjectIdentifier deviceId = getRecipient().getDevice();
        if (deviceId.isValid() && (deviceId.getObjectType() == BBacnetObjectType.DEVICE))
        {
          if (BBacnetNetwork.bacnet().doLookupDeviceById(deviceId) == null)
          {
            try
            {
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                .whoIs(BBacnetAddress.GLOBAL_BROADCAST_ADDRESS,
                       deviceId.getInstanceNumber(),
                       deviceId.getInstanceNumber());
            }
            catch (BacnetException e)
            {
              log.log(Level.WARNING,"Unable to determine address for Bacnet Destination "+getName()+": "+getRecipient(),e);
            }
          }
        }
      }
      else
      {
        BBacnetAddress address = getRecipient().getAddress();
        if (BBacnetNetwork.bacnet().doLookupDeviceByAddress(address) == null)
        {
          try
          {
            ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
              .whoIs(address);
          }
          catch (BacnetException e)
          {
            log.log(Level.SEVERE,"Unable to resolve address for Bacnet Destination "+getName()+": "+getRecipient(),e);
          }
        }
      }
    }
  }

  /**
   * Add this destination to the alarm's list of notification recipients.
   * @param r
   */
  private void addToNotifyList(BAlarmRecord r)
  {
    BString notify = (BString)r.getAlarmFacet(BAC_NOTIFY_LIST);
    if (notify == null)
    {
      notify = BString.make(getHandle().toString());
    }
    else
    {
      notify = BString.make(notify.getString()+";"+getHandle().toString());
    }
    r.addAlarmFacet(BAC_NOTIFY_LIST, notify);
  }

  /**
   * Remove this destination from the list of notification recipients.
   * @param r
   * @return true if the record has more notification recipients
   */
  private boolean removeFromNotifyList(BAlarmRecord r)
  {
    BString notify = (BString)r.getAlarmFacet(BAC_NOTIFY_LIST);
    String h = getHandle().toString();
    if (notify != null)
    {
      StringTokenizer st = new StringTokenizer(notify.getString(), ";");
      StringBuilder sb = new StringBuilder();
      while (st.hasMoreTokens())
      {
        String tok = st.nextToken();
        if (!h.equals(tok))
          sb.append(tok).append(";");
      }
      if (sb.length() == 0)
      {
        r.removeAlarmFacet(BAC_NOTIFY_LIST);
        return false;
      }
      else
      {
        r.addAlarmFacet(BAC_NOTIFY_LIST, BString.make(sb.toString()));
        return true;
      }
    }
    else
      return false;
  }


////////////////////////////////////////////////////////////////
// BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   * @param out the AsnOutput stream.
   */
  public final void writeAsn(AsnOutput out)
  {
    out.writeBitString(BacnetBitStringUtil.getBacnetDaysOfWeek(getDaysOfWeek()).getBits());
    out.writeTime(getTimeRange().getStartTime());
    out.writeTime(getTimeRange().getEndTime());
    getRecipient().writeAsn(out);
    out.writeUnsigned(getProcessIdentifier());
    out.writeBoolean(getIssueConfirmedNotifications());
    out.writeBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(getTransitions()).getBits());
  }

  /**
   * Read the value from the Asn input stream.
   * @param in the AsnInput stream.
   */
  public final void readAsn(AsnInput in)
    throws AsnException
  {
    try
    {
      set(daysOfWeek, BacnetBitStringUtil.getBDaysOfWeekBits(in.readBitString()), noWrite);
    }catch(IllegalArgumentException iae)
    {
      throw new AsnException(BBacnetErrorCode.invalidDataType.getTag());
    }

    BBacnetTime startTime = in.readTime();
    BBacnetTime endTime = in.readTime();

    if(startTime.getHour() == UNSPECIFIED || startTime.getMinute() == UNSPECIFIED || startTime.getSecond() == UNSPECIFIED || startTime.getHundredth() == UNSPECIFIED)
      throw new AsnException(BBacnetErrorCode.valueOutOfRange.getTag());
    if(endTime.getHour() == UNSPECIFIED || endTime.getMinute() == UNSPECIFIED || endTime.getSecond() == UNSPECIFIED || endTime.getHundredth() == UNSPECIFIED)
      throw new AsnException(BBacnetErrorCode.valueOutOfRange.getTag());

    getTimeRange().set(BTimeRange.startTime, BBacnetTime.getBTime(startTime,true), noWrite);
    getTimeRange().set(BTimeRange.endTime, BBacnetTime.getBTime(endTime,false), noWrite);
    getRecipient().readAsn(in);
    set(processIdentifier, in.readUnsigned(), noWrite);
    setBoolean(issueConfirmedNotifications, in.readBoolean(), noWrite);
    set(transitions, BacnetBitStringUtil.getBAlarmTransitionBits(in.readBitString()), noWrite);
  }

  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getRecipient().toString(cx))
      .append(" pId=").append(getProcessIdentifier())
      .append(" conf=").append(getIssueConfirmedNotifications())
      .append(" times=").append(getTimeRange().toString(cx))
      .append(" days=").append(getDaysOfWeek().toString(cx))
      .append(" trans=").append(getTransitions().toString(cx));
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out) throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetDestination", 2);
    out.prop("client", client);
    out.prop("eventStateRange", eventStateRange);
    out.prop("virtual", BacnetVirtualUtil.isVirtual(this));
    out.endProps();
  }


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private boolean client = false;
  private BEnumRange eventStateRange = BBacnetEventState.DEFAULT.getRange();
  private final Logger log = Logger.getLogger("bacnet.server");


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final long LOCAL_PROCESS_ID = 0;
  public static final int MAX_ENCODED_SIZE = 35;

  private String ackDump(BAlarmRecord alarmRecord) { return alarmRecord.getSourceState()+"/"+alarmRecord.getAckState()+"/"+alarmRecord.getAckRequired(); }
}
