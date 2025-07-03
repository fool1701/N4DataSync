/*
 * Copyright 2000-2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.BCompositeAction;
import javax.baja.util.BCompositeTopic;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.util.BTimeRange;

/**
 * BAlarmRecipient is the super-class of all alarm recipients
 * in the Baja framework.  Modeled after an entry in the
 * recipient list in a BACnet Notification class.
 *
 * @author    Dan Giorgis
 * @creation  19 Feb 01
 * @version   $Revision: 44$ $Date: 3/10/11 3:10:15 PM EST$
 * @since     Baja 1.0
 */

@NiagaraType
/*
 Time during which the recipient will receive alarms.
 */
@NiagaraProperty(
  name = "timeRange",
  type = "BTimeRange",
  defaultValue = "new BTimeRange()"
)
/*
 Days during which the recipient will receive alarms.
 */
@NiagaraProperty(
  name = "daysOfWeek",
  type = "BDaysOfWeekBits",
  defaultValue = "BDaysOfWeekBits.DEFAULT"
)
/*
 Alarm transition types the recipient wishes to receive.
 */
@NiagaraProperty(
  name = "transitions",
  type = "BAlarmTransitionBits",
  defaultValue = "BAlarmTransitionBits.ALL"
)
/*
 Route Alarm Acknowledgements
 */
@NiagaraProperty(
  name = "routeAcks",
  type = "boolean",
  defaultValue = "true"
)
/*
 Route an alarm ack request
 */
@NiagaraAction(
  name = "routeAlarmAck",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.HIDDEN
)
/*
 Route an alarm record
 */
@NiagaraAction(
  name = "routeAlarm",
  parameterType = "BAlarmRecord",
  defaultValue = "new BAlarmRecord()",
  flags = Flags.SUMMARY
)
@NiagaraTopic(
  name = "newUnackedAlarm",
  eventType = "BAlarmRecord",
  flags = Flags.HIDDEN
)
public abstract class BAlarmRecipient
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmRecipient(1141607632)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "timeRange"

  /**
   * Slot for the {@code timeRange} property.
   * Time during which the recipient will receive alarms.
   * @see #getTimeRange
   * @see #setTimeRange
   */
  public static final Property timeRange = newProperty(0, new BTimeRange(), null);

  /**
   * Get the {@code timeRange} property.
   * Time during which the recipient will receive alarms.
   * @see #timeRange
   */
  public BTimeRange getTimeRange() { return (BTimeRange)get(timeRange); }

  /**
   * Set the {@code timeRange} property.
   * Time during which the recipient will receive alarms.
   * @see #timeRange
   */
  public void setTimeRange(BTimeRange v) { set(timeRange, v, null); }

  //endregion Property "timeRange"

  //region Property "daysOfWeek"

  /**
   * Slot for the {@code daysOfWeek} property.
   * Days during which the recipient will receive alarms.
   * @see #getDaysOfWeek
   * @see #setDaysOfWeek
   */
  public static final Property daysOfWeek = newProperty(0, BDaysOfWeekBits.DEFAULT, null);

  /**
   * Get the {@code daysOfWeek} property.
   * Days during which the recipient will receive alarms.
   * @see #daysOfWeek
   */
  public BDaysOfWeekBits getDaysOfWeek() { return (BDaysOfWeekBits)get(daysOfWeek); }

  /**
   * Set the {@code daysOfWeek} property.
   * Days during which the recipient will receive alarms.
   * @see #daysOfWeek
   */
  public void setDaysOfWeek(BDaysOfWeekBits v) { set(daysOfWeek, v, null); }

  //endregion Property "daysOfWeek"

  //region Property "transitions"

  /**
   * Slot for the {@code transitions} property.
   * Alarm transition types the recipient wishes to receive.
   * @see #getTransitions
   * @see #setTransitions
   */
  public static final Property transitions = newProperty(0, BAlarmTransitionBits.ALL, null);

  /**
   * Get the {@code transitions} property.
   * Alarm transition types the recipient wishes to receive.
   * @see #transitions
   */
  public BAlarmTransitionBits getTransitions() { return (BAlarmTransitionBits)get(transitions); }

  /**
   * Set the {@code transitions} property.
   * Alarm transition types the recipient wishes to receive.
   * @see #transitions
   */
  public void setTransitions(BAlarmTransitionBits v) { set(transitions, v, null); }

  //endregion Property "transitions"

  //region Property "routeAcks"

  /**
   * Slot for the {@code routeAcks} property.
   * Route Alarm Acknowledgements
   * @see #getRouteAcks
   * @see #setRouteAcks
   */
  public static final Property routeAcks = newProperty(0, true, null);

  /**
   * Get the {@code routeAcks} property.
   * Route Alarm Acknowledgements
   * @see #routeAcks
   */
  public boolean getRouteAcks() { return getBoolean(routeAcks); }

  /**
   * Set the {@code routeAcks} property.
   * Route Alarm Acknowledgements
   * @see #routeAcks
   */
  public void setRouteAcks(boolean v) { setBoolean(routeAcks, v, null); }

  //endregion Property "routeAcks"

  //region Action "routeAlarmAck"

  /**
   * Slot for the {@code routeAlarmAck} action.
   * Route an alarm ack request
   * @see #routeAlarmAck(BAlarmRecord parameter)
   */
  public static final Action routeAlarmAck = newAction(Flags.HIDDEN, new BAlarmRecord(), null);

  /**
   * Invoke the {@code routeAlarmAck} action.
   * Route an alarm ack request
   * @see #routeAlarmAck
   */
  public void routeAlarmAck(BAlarmRecord parameter) { invoke(routeAlarmAck, parameter, null); }

  //endregion Action "routeAlarmAck"

  //region Action "routeAlarm"

  /**
   * Slot for the {@code routeAlarm} action.
   * Route an alarm record
   * @see #routeAlarm(BAlarmRecord parameter)
   */
  public static final Action routeAlarm = newAction(Flags.SUMMARY, new BAlarmRecord(), null);

  /**
   * Invoke the {@code routeAlarm} action.
   * Route an alarm record
   * @see #routeAlarm
   */
  public void routeAlarm(BAlarmRecord parameter) { invoke(routeAlarm, parameter, null); }

  //endregion Action "routeAlarm"

  //region Topic "newUnackedAlarm"

  /**
   * Slot for the {@code newUnackedAlarm} topic.
   * @see #fireNewUnackedAlarm
   */
  public static final Topic newUnackedAlarm = newTopic(Flags.HIDDEN, null);

  /**
   * Fire an event for the {@code newUnackedAlarm} topic.
   * @see #newUnackedAlarm
   */
  public void fireNewUnackedAlarm(BAlarmRecord event) { fire(newUnackedAlarm, event, null); }

  //endregion Topic "newUnackedAlarm"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmRecipient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Check the timeRange and transition to see if this alarm should be routed,
   * and if so, send to the handleAlarm method.
   *
   * @param alarmRecord The alarm to route.
   */
  public final void doRouteAlarm(BAlarmRecord alarmRecord)
  {
    if (accept(alarmRecord))
      handleAlarm(alarmRecord);
  }

  /**
   * Route the alarm ack request back to the AlarmService.
   */
  public void doRouteAlarmAck(BAlarmRecord alarmRecord)
  {
    try
    {
      ((BAlarmService)Sys.getService(BAlarmService.TYPE)).doAckAlarm(alarmRecord);
    }
    catch(ServiceNotFoundException e)
    {
      logger.warning("BAlarmRecipient: cannot find AlarmService");
      e.printStackTrace();
    }
    catch(Exception e)
    {
      logger.warning("BAlarmRecipient: cannot resolve alarm source for: " + alarmRecord);
      e.printStackTrace();
    }
  }

  /**
   * Handle the AlarmRecipient specific routing of the alarm.
   */
  public abstract void handleAlarm(BAlarmRecord alarmRecord);

  /**
   * Get the AlarmClasses linked to this AlarmRecipient.
   */
  public String[] getSubscribedAlarmClasses()
  {
    String[] subscribedAlarmClasses = getSubscribedAlarmClasses("alarm");
    return subscribedAlarmClasses;
  }

  /**
   * Get the EscalatedAlarmClasses linked to this AlarmRecipient.
   * Valid levels are 1, 2 and 3.
   */
  public String[] getSubscribedEscalatedAlarmClasses(int level)
  {
    if (level < 1 || level > 3)
      return new String[0];

    return getSubscribedAlarmClasses("escalatedAlarm"+level);
  }

  /**
   * This method returns a list of subscribed alarm classes based on the
   * name of the source slot name of the links of this alarm recipient.
   * @param sourceSlotName The name of the source slot.
   * @return a String array with subscribed alarm class names
   */
  private String[] getSubscribedAlarmClasses(String sourceSlotName)
  {
    List<String> classes = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(BLink.class))
    {
      try
      {
        BLink link = (BLink)c.get();
        if (!link.isActive())
        {
          link.activate();
        }

        BComponent srcComp = link.getSourceComponent();
        if (srcComp == null || srcComp.getName() == null)
        {
          continue;
        }

        if (srcComp instanceof BAlarmClass && link.getSourceSlotName().equals(sourceSlotName))
        {
          classes.add(srcComp.getName());
          // Remember, it's the name of the alarm class, not the alarm class
          // itself.  AlarmRecords store their alarm
          // class as a string, so we need to match that
        }
        else
        {
          //check if we're dealing with nested alarm class folders
          while (srcComp instanceof BIAlarmClassFolder)
          {
            //get source component as an Alarm Class Folder
            BComponent folder = srcComp;
            String folderSlotName = link.getSourceSlotName();

            //get the link to this folder
            BStruct struct = (BStruct) folder.get(folderSlotName);
            if (struct instanceof BCompositeAction)
            {
              for (BLink srcLink : srcComp.getLinks(struct.getPropertyInParent()))
              {
                if (srcLink.getTargetSlotName().equals(((BCompositeAction) struct).getMirror().knob.getSourceSlotName()))
                {
                  srcComp = srcLink.getSourceComponent();
                  link = srcLink;
                }
              }
            }
            else if (struct instanceof BCompositeTopic)
            {
              link = ((BCompositeTopic)struct).getMirror().link;
              srcComp = link.getSourceComponent();
            }

            //check the source component of the link
            if( srcComp instanceof BAlarmClass && link.getSourceSlotName().equals(sourceSlotName) )
            {
              classes.add(srcComp.getName());
              break;
            }
          }
        }
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "alarm:BAlarmRecipient:getSubscribedAlarmClasses " + e.getMessage(),e);
      }
    }
    
   return classes.toArray(EMPTY_STRING_ARRAY);
  }

  /**
   * Check to see if the alarm falls within the time and day ranges and the transitions.
   */
  public boolean accept(BAlarmRecord rec)
  {
    BAbsTime now = rec.getTimestamp();

    // Check day of week
    if (!getDaysOfWeek().includes(now.getWeekday()))
      return false;

    // Check time range
    if (!getTimeRange().includes(now))
      return false;

    //only send alarm if transitions are set
    if (!getTransitions().includes(rec.getSourceState()))
      return false;

    //do not send if last transition was an ack.
    /**
     * if !getRouteAcks()
     *   if isAcknoweldged()
     *     if getNormalTime() != null
     *       if getNormalTime().isBefore(getAckTime())
     *         return false;
     *       else continue
     *     else return false
     *   else continue
     * else continue;
     *
     */
    if (!getRouteAcks() && rec.isAcknowledged())
    {
      if (rec.getNormalTime().isNull())
        return false;
      else if (rec.getNormalTime().isBefore(rec.getAckTime()))
        return false;
    }

    return true;
  }

  @Override
  public String toString(Context cx)
  {
    if(isMounted())
      return getName();
    else
      return TYPE.getTypeName();
  }

  private static final Logger logger = Logger.getLogger("alarm");

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://icons/x16/alarm/alarmRecipient.png");
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
}
