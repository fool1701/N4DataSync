/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmPriorities;
import javax.baja.alarm.BAlarmRecipient;
import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDestination;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Knob;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.datatypes.BEventSaver;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.server.BBacnetExportTable;

/**
 * BBacnetNotificationClassDescriptor is the extension that allows a BAlarmClass
 * to be exposed to Bacnet as a Notification_Class object.
 * The user drops this extension on the point, and then configures
 * the extension to expose the point properly.
 *
 * @author Craig Gemmill on 31 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "alarm:AlarmClass"
  )
)
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
@NiagaraProperty(
  name = "alarmClassOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS)",
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
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "eventSaver",
  type = "BAlarmRecipient",
  defaultValue = "new BEventSaver()"
)
public class BBacnetNotificationClassDescriptor
  extends BComponent
  implements BIBacnetExportObject,
             BacnetAlarmConst,
             BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetNotificationClassDescriptor(2602244090)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "alarmClassOrd"

  /**
   * Slot for the {@code alarmClassOrd} property.
   * @see #getAlarmClassOrd
   * @see #setAlarmClassOrd
   */
  public static final Property alarmClassOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.NULL, null);

  /**
   * Get the {@code alarmClassOrd} property.
   * @see #alarmClassOrd
   */
  public BOrd getAlarmClassOrd() { return (BOrd)get(alarmClassOrd); }

  /**
   * Set the {@code alarmClassOrd} property.
   * @see #alarmClassOrd
   */
  public void setAlarmClassOrd(BOrd v) { set(alarmClassOrd, v, null); }

  //endregion Property "alarmClassOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.NOTIFICATION_CLASS), null);

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

  //region Property "eventSaver"

  /**
   * Slot for the {@code eventSaver} property.
   * @see #getEventSaver
   * @see #setEventSaver
   */
  public static final Property eventSaver = newProperty(0, new BEventSaver(), null);

  /**
   * Get the {@code eventSaver} property.
   * @see #eventSaver
   */
  public BAlarmRecipient getEventSaver() { return (BAlarmRecipient)get(eventSaver); }

  /**
   * Set the {@code eventSaver} property.
   * @see #eventSaver
   */
  public void setEventSaver(BAlarmRecipient v) { set(eventSaver, v, null); }

  //endregion Property "eventSaver"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNotificationClassDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Register with the Bacnet service when this component is started.
   */
  @Override
  public final void started()
    throws Exception
  {
    super.started();

    // First check for fatal faults.
    checkFatalFault();

    // Export the alarm class and initialize the local copies.
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
   * Unregister with the Bacnet service when this component is stopped.
   */
  @Override
  public final void stopped()
    throws Exception
  {
    super.stopped();

    // unexport
    BLocalBacnetDevice local = BBacnetNetwork.localDevice();
    local.unexport(oldId, oldName, this);

    local.unsubscribe(this, ac);

    // Clear the local copies.
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted objects.
    if (local.isRunning())
    {
      local.incrementDatabaseRevision();
    }
  }

  /**
   * Property Changed.
   * If the objectId changes, make sure the new ID is not already in use.
   * If it is, reset it to the current value.
   */
  @Override
  public final void changed(Property p, Context cx)
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
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(objectName))
    {
      BBacnetNetwork.localDevice().unexport(oldId, oldName, this);
      checkConfiguration();
      oldName = getObjectName();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    else if (p.equals(alarmClassOrd))
    {
      checkConfiguration();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
      super.changed(p, cx);
    }
  }

  /**
   * Get slot facets.
   *
   * @param s
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.NOTIFICATION_CLASS);
    }
    return super.getSlotFacets(s);
  }

////////////////////////////////////////////////////////////////
// BIBacnetExportObject
////////////////////////////////////////////////////////////////

  /**
   * Get the exported object.
   *
   * @return the actual exported object by resolving the object ord.
   */
  @Override
  public final BObject getObject()
  {
    return getAlarmClass();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getAlarmClassOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(alarmClassOrd, objectOrd, cx);
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
    local.unsubscribe(this, ac);

    // Find the exported alarm class.
    findAlarmClass();

    // Check the configuration.
    boolean configOk = true;
    if (ac == null)
    {
      setFaultCause("Cannot find exported alarm class");
      configOk = false;
    }
    else
    {
      local.subscribe(this, ac);
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      configOk = false;
    }

    if (configOk)
    {
      // Link the event saver to the exported alarm class.
      BEventSaver eventSaver = (BEventSaver)getEventSaver();
      BLink[] links = getEventSaver().getLinks(BAlarmRecipient.routeAlarm);
      for (int i = 0; i < links.length; i++)
      {
        eventSaver.remove(links[i]);
      }

      BLink link = new BLink(ac.getHandleOrd(), "alarm", "routeAlarm", true);
      eventSaver.add("link", link, Flags.HIDDEN);

      // Try to export - duplicate id & names will be checked in here.
      String err = local.export(this);
      if (err != null)
      {
        duplicate = true;
        setFaultCause(err);
        configOk = false;
      }
      else
      {
        duplicate = false;
      }
    }

    // Set the exported flag.
    if (configOk)
    {
      setFaultCause("");
    }
    setStatus(BStatus.makeFault(getStatus(), !configOk));
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
    getAlarmClass();
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
    getAlarmClass();
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
          props = OPTIONAL_PROPS;
          for (int j = 0; j < props.length; j++)
          {
            results.add(readProperty(props[j], NOT_USED));
          }
          break;

        case BBacnetPropertyIdentifier.OPTIONAL:
          props = OPTIONAL_PROPS;
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
    getAlarmClass();
    if (ac == null)
    {
      return new ReadRangeAck(BBacnetErrorClass.OBJECT,
                              BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }
    if ((rangeReference.getPropertyArrayIndex()) >= 0)
    {
      if (!isArray(rangeReference.getPropertyId()))
      {
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }

    switch (rangeReference.getPropertyId())
    {
      case BBacnetPropertyIdentifier.RECIPIENT_LIST:
        int rangeType = rangeReference.getRangeType();

        // Calculate the maximum allowed data length.
        int maxDataLength = -1;
        if (rangeReference instanceof BacnetConfirmedRequest)
        {
          maxDataLength = ((BacnetConfirmedRequest) rangeReference).getMaxDataLength()
            // We need to subtract the size of the ReadRangeAck application headers.
            - ReadRangeAck.READ_RANGE_ACK_MAX_APP_HEADER_SIZE
            // We also add back in the length of the unused fields.
            + 3 // we don't use propertyArrayIndex here
            + 5; // we don't use sequenceNumber here
        }

        boolean[] rflags = new boolean[] { false, false, false };
        BBacnetDestination[] list = getRecipientList();
        int len = list.length;

        if (rangeType == RangeReference.BY_POSITION)
        {
          int refNdx = (int)rangeReference.getReferenceIndex();
          int count = rangeReference.getCount();

          // sanity check on refNdx - should we throw an error/reject here?
          if ((refNdx > len) || (refNdx < 1))
          {
            return new ReadRangeAck(getObjectId(),
                                    rangeReference.getPropertyId(),
                                    NOT_USED,
                                    BBacnetBitString.emptyBitString(3),
                                    0,
                                    new byte[0]);
          }

          Array<BBacnetDestination> a = new Array<>(BBacnetDestination.class);
          int itemsFound = 0;

          if (count > 0)
          {
            // Count is positive: Search from refNdx to end,
            // until we find (count) items.
            for (int i = refNdx - 1; i < len && itemsFound < count; i++)
            {
              a.add(list[i]);
              itemsFound++;
            }

            // Set firstItem result flag.
            if (refNdx == 1)
            {
              rflags[0] = true;
            }
            // Set lastItem flag temporarily - adjust later if needed.
            if ((refNdx + count - 1) >= len)
            {
              rflags[1] = true;
            }
          }

          else if (count < 0)
          {
            // Count is negative: Search from refNdx to beginning,
            // until we find (-count) items.
            count = -count;
            for (int i = refNdx - 1; i >= 0 && itemsFound < count; i--)
            {
              a.add(list[i]);
              itemsFound++;
            }

            // Reverse the array because we need to return the items
            // in their natural order.
            a = a.reverse();

            // Set firstItem result flag.
            if ((refNdx - count) <= 0)
            {
              rflags[0] = true;
            }
            // Set lastItem flag temporarily - adjust later if needed.
            if (refNdx == len)
            {
              rflags[1] = true;
            }
          }
          else
          {
            return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                    BBacnetErrorCode.INCONSISTENT_PARAMETERS);
          }

          // Iterate through the found items until we have written
          // them all, or until we don't have any more room in the
          // outgoing packet.
          Iterator<BBacnetDestination> it = a.iterator();
          int itemCount = 0;

          synchronized (asnOut)
          {
            asnOut.reset();
            if (maxDataLength > 0)
            {
              while (it.hasNext())
              {
                if ((maxDataLength - asnOut.size()) < BBacnetDestination.MAX_ENCODED_SIZE)
                {
                  rflags[1] = false;
                  break;
                }
                it.next().writeAsn(asnOut);
                itemCount++;
              }
            }
            else
            {
              itemCount = itemsFound;
              while (it.hasNext())
              {
                it.next().writeAsn(asnOut);
              }
            }

            // Set the moreItems result flag.
            if (itemCount < itemsFound)
            {
              rflags[2] = true;
            }

            // Return the ack.
            return new ReadRangeAck(getObjectId(),
                                    rangeReference.getPropertyId(),
                                    NOT_USED,
                                    BBacnetBitString.make(rflags),
                                    itemCount,
                                    asnOut.toByteArray());
          }
        }
        else if (rangeType == NOT_USED)
        {
          rflags[0] = false;
          int itemCount = 0;
          synchronized (asnOut)
          {
            asnOut.reset();

            if (maxDataLength > 0)
            {
              for (int i = 0; i < len; i++)
              {
                list[i].writeAsn(asnOut);
                itemCount++;
                if ((maxDataLength - asnOut.size()) < BBacnetDestination.MAX_ENCODED_SIZE)
                {
                  break;
                }
              }
              if (itemCount > 0)
              {
                rflags[0] = true;
              }
              if (itemCount > 0 && itemCount == len)
              {
                rflags[1] = true;
              }
            }
            else
            {
              itemCount = len;
              for (int i = 0; i < len; i++)
              {
                list[i].writeAsn(asnOut);
              }
              if (itemCount > 0)
              {
                rflags[0] = true;
              }
              if (itemCount > 0 && itemCount == len)
              {
                rflags[1] = true;
              }
            }

            // Set the moreItems result flag.
            if (itemCount < len)
            {
              rflags[2] = true;
            }

            // Return the ack.
            return new ReadRangeAck(getObjectId(),
                                    rangeReference.getPropertyId(),
                                    NOT_USED,
                                    BBacnetBitString.make(rflags),
                                    itemCount,
                                    asnOut.toByteArray());
          }
        }
        else
        {
          return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                  BBacnetErrorCode.INCONSISTENT_PARAMETERS);
        }

      default:
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
    }
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
    getAlarmClass();
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
    BAlarmClass ac = getAlarmClass();
    if (ac == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.RECIPIENT_LIST)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return addRecipients(propertyValue);
    }
    else
    {
      int propertyId = propertyValue.getPropertyId();
      for (int i = 0; i < REQUIRED_PROPS.length; i++)
      {
        if (propertyId == REQUIRED_PROPS[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }
      for (int i = 0; i < OPTIONAL_PROPS.length; i++)
      {
        if (propertyId == OPTIONAL_PROPS[i])
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
    BAlarmClass ac = getAlarmClass();
    if (ac == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.RECIPIENT_LIST)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return removeRecipients(propertyValue);
    }
    else
    {
      int propertyId = propertyValue.getPropertyId();
      for (int i = 0; i < REQUIRED_PROPS.length; i++)
      {
        if (propertyId == REQUIRED_PROPS[i])
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
                                      0);
        }
      }
      for (int i = 0; i < OPTIONAL_PROPS.length; i++)
      {
        if (propertyId == OPTIONAL_PROPS[i])
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
    if (propertyId == BBacnetPropertyIdentifier.PRIORITY)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return true;
    }

    return false;
  }

  /**
   * Get the value of a property.
   *
   * @param pId the requested property-identifier.
   * @param ndx the property array index (-1 if not specified).
   * @return a PropertyValue containing the encoded value or the error.
   */
  protected PropertyValue readProperty(int pId, int ndx)
  {
    BAlarmClass ac = getAlarmClass();
    if (ac == null)
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

    switch (pId)
    {
      case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnObjectId(getObjectId()));

      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return readPropertyList(ndx);

      case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(getObjectId().getInstanceNumber()));

      case BBacnetPropertyIdentifier.PRIORITY:
        BAlarmPriorities pri = ac.getPriority();
        switch (ndx)
        {
          case 0:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(3));

          case 1:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(pri.getToOffnormal()));

          case 2:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(pri.getToFault()));

          case 3:
            return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(pri.getToNormal()));

          case NOT_USED:
            synchronized (asnOut)
            {
              asnOut.reset();
              asnOut.writeUnsignedInteger(pri.getToOffnormal());
              asnOut.writeUnsignedInteger(pri.getToFault());
              asnOut.writeUnsignedInteger(pri.getToNormal());
              return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
            }

          default:
            return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                    BBacnetErrorCode.INVALID_ARRAY_INDEX));
        }

      case BBacnetPropertyIdentifier.ACK_REQUIRED:
        BAlarmTransitionBits bits = ac.getAckRequired();
        synchronized (asnOut)
        {
          asnOut.reset();
          asnOut.writeBitString(BacnetBitStringUtil.getBacnetEventTransitionBits(bits));
          return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
        }

      case BBacnetPropertyIdentifier.RECIPIENT_LIST:
        BBacnetDestination[] list = getRecipientList();
        synchronized (asnOut)
        {
          asnOut.reset();
          for (int i = 0; i < list.length; i++)
          {
            list[i].writeAsn(asnOut);
          }
          return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
        }

      case BBacnetPropertyIdentifier.DESCRIPTION:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getDescription()));

      default:
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                BBacnetErrorCode.UNKNOWN_PROPERTY));
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
   * @throws BacnetException
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    BAlarmClass ac = getAlarmClass();
    if (ac == null)
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
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);

        case BBacnetPropertyIdentifier.NOTIFICATION_CLASS:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.PRIORITY:
          BAlarmPriorities apri = ac.getPriority();
          try
          {
            switch (ndx)
            {
              case 0:
                return new NErrorType(BBacnetErrorClass.PROPERTY,
                                      BBacnetErrorCode.INVALID_ARRAY_INDEX);

              case 1:
                ac.set(BAlarmClass.priority,
                       BAlarmPriorities.make(AsnUtil.fromAsnUnsignedInt(val), apri.getToFault(), apri.getToNormal()),
                       BLocalBacnetDevice.getBacnetContext());
                return null;

              case 2:
                ac.set(BAlarmClass.priority,
                       BAlarmPriorities.make(apri.getToOffnormal(), AsnUtil.fromAsnUnsignedInt(val), apri.getToNormal()),
                       BLocalBacnetDevice.getBacnetContext());
                return null;

              case 3:
                ac.set(BAlarmClass.priority,
                       BAlarmPriorities.make(apri.getToOffnormal(), apri.getToFault(), AsnUtil.fromAsnUnsignedInt(val)),
                       BLocalBacnetDevice.getBacnetContext());
                return null;

              case NOT_USED:
                synchronized (asnIn)
                {
                  asnIn.setBuffer(val);
                  try
                  {
                    int toOffNormal = asnIn.readUnsignedInt();
                    int toFault = asnIn.readUnsignedInt();
                    int toNormal = asnIn.readUnsignedInt();

                    if ((toOffNormal < 0 ||
                         toFault < 0 ||
                         toNormal < 0) ||
                        (toOffNormal > MAX_PRIORITY ||
                         toFault > MAX_PRIORITY ||
                         toNormal > MAX_PRIORITY))
                    {
                      return new NErrorType(BBacnetErrorClass.PROPERTY,
                                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
                    }

                    return AsnUtil.peekTagAndPerform(asnIn,
                                                     AsnInput.END_OF_DATA,
                                                     BBacnetErrorCode.INVALID_ARRAY_INDEX,
                                                     () -> ac.setPriority(BAlarmPriorities.make(toOffNormal, toFault, toNormal))
                    );
                  }
                  catch (Exception e)
                  {
                    return new NErrorType(BBacnetErrorClass.PROPERTY,
                                          BBacnetErrorCode.INVALID_ARRAY_INDEX);
                  }
                }

              default:
                return new NErrorType(BBacnetErrorClass.PROPERTY,
                                      BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }
          }
          catch (IllegalStateException e)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }
          catch (IllegalArgumentException e)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

        case BBacnetPropertyIdentifier.ACK_REQUIRED:
          synchronized (asnIn)
          {
            asnIn.setBuffer(val);
            ac.set(BAlarmClass.ackRequired,
                   BacnetBitStringUtil.getBAlarmTransitionBits(asnIn.readBitString()),
                   BLocalBacnetDevice.getBacnetContext());
          }
          return null;

        case BBacnetPropertyIdentifier.RECIPIENT_LIST:
          return writeRecipientList(val);

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        default:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.UNKNOWN_PROPERTY);
      }
    }
    catch (OutOfRangeException e)
    {
      log.warning("Out Of Range Exception writing property " + pId + " in object " + getObjectId() + ": " + e);
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
  }

////////////////////////////////////////////////////////////////
// Access methods
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  @Override
  public String toString(Context c)
  {
    return getObjectName() + " [" + getObjectId() + "]";
  }

  /**
   * Get the notification class for this extension.
   */
  public int getNotificationClass()
  {
    return getObjectId().getInstanceNumber();
  }

  /**
   * Get the event priorities for Bacnet.
   *
   * @return an array of three event priorities to be used
   * for TO-OFFNORMAL, TO-FAULT, and TO-NORMAL transitions.
   */
  public int[] getEventPriorities()
  {
    BAlarmPriorities pri = getAlarmClass().getPriority();
    return new int[] { pri.getToOffnormal(), pri.getToFault(), pri.getToNormal() };
  }

  /**
   * Get the Recipient_List of destinations in this Notification Class.
   */
  public BBacnetDestination[] getRecipientList()
  {
    synchronized (recipientList)
    {
      if (recipientListChanged)
      {
        buildRecipientList();
      }
      return recipientList;
    }
  }

  public void recipientListChanged()
  {
    synchronized (recipientList)
    {
      recipientListChanged = true;
    }
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  public final BAlarmClass getAlarmClass()
  {
    if (ac == null)
    {
      return findAlarmClass();
    }
    return ac;
  }

  private BAlarmClass findAlarmClass()
  {
    try
    {
      if (!alarmClassOrd.isEquivalentToDefaultValue(getAlarmClassOrd()))
      {
        BObject o = getAlarmClassOrd().get(this);
        if (o instanceof BAlarmClass)
        {
          ac = (BAlarmClass) o;
        }
        else
        {
          ac = null;
        }
      }
      return ac;
    }
    catch (Exception e)
    {
      log.warning("Unable to resolve alarm class ord for " + this + ": " + getAlarmClassOrd() + ": " + e);
      ac = null;
    }

    if ((ac == null) && isRunning())
    {
      setFaultCause("Cannot find exported alarm class");
      setStatus(BStatus.makeFault(getStatus(), true));
    }

    return ac;
  }

  private void buildRecipientList()
  {
    synchronized (recipientList)
    {
      Array<BBacnetDestination> a = new Array<>(BBacnetDestination.class);
      Knob[] srcLinks = getAlarmClass().getKnobs(BAlarmClass.alarm);
      for (int i = 0; i < srcLinks.length; i++)
      {
        if (srcLinks[i].getTargetComponent() instanceof BBacnetDestination)
        {
          a.add((BBacnetDestination)srcLinks[i].getTargetComponent());
        }
      }
      recipientList = a.trim();
      recipientListChanged = false;
    }
  }

  private ErrorType writeRecipientList(byte[] encodedList)
  {
    // First, read from the encoded value all of the elements to be added.
    ArrayList<BBacnetDestination> v = new ArrayList<>();
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(encodedList);
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDestination d = new BBacnetDestination();
          d.readAsn(asnIn);
          v.add(d);
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      if (BBacnetErrorCode.valueOutOfRange.getTag().equals(e.getMessage()))
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
      }

      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }

    // Now for each destination being written, check if it already exists in
    // the station *AND* is linked to our alarm class.  If not, add this
    // destination to the service, and link it.
    try
    {
      BAlarmClass ac = getAlarmClass();
      Knob[] knobs = ac.getKnobs(BAlarmClass.alarm);
      int len = knobs.length;
      boolean[] toKeep = new boolean[len];
      for (int i = 0; i < v.size(); i++)
      {
        boolean knobFound = false;
        BBacnetDestination dest = v.get(i);
        for (int j = 0; j < len; j++)
        {
          if (knobs[j].getTargetComponent() instanceof BBacnetDestination)
          {
            if (dest.destinationEquals((BBacnetDestination)knobs[j].getTargetComponent()))
            {
              knobFound = true;
              toKeep[j] = true;
              break;
            }
          }
        }
        if (!knobFound)
        {
          BBacnetDestination linkDest = null;
          BComponent alarmService = getAlarmClass().getParent().asComponent();
          BBacnetDestination[] dests = alarmService.getChildren(BBacnetDestination.class);
          for (int k = 0; k < dests.length; k++)
          {
            if (dest.destinationEquals(dests[k]))
            {
              // We found the destination, but it's not linked.
              // Mark the destination for adding a link.
              linkDest = dests[k];
              break;
            }
          }

          // We didn't find a matching destination in the alarm service, so
          // just add the written one in and mark it for linking.
          if (linkDest == null)
          {
            alarmService.add(null, dest, BLocalBacnetDevice.getBacnetContext());
            linkDest = dest;
          }

          // Now link the destination to our alarm class.
          BLink link = new BLink(getAlarmClass().getHandleOrd(),
                                 "alarm",
                                 "routeAlarm",
                                 true);
          linkDest.add(null, link, BLocalBacnetDevice.getBacnetContext());
        }
      }

      // Now we need to remove any links to BBacnetDestinations that were not
      // in the list of written recipients.
      for (int i = 0; i < len; i++)
      {
        if (!toKeep[i])
        {
          BComponent target = knobs[i].getTargetComponent();
          if (!(target instanceof BBacnetDestination)) continue;
          BLink[] tgtLinks = target.getLinks(BAlarmRecipient.routeAlarm);
          for (int j = 0; j < tgtLinks.length; j++)
          {
            if (tgtLinks[j].getSourceComponent() == getAlarmClass())
            {
              target.remove(tgtLinks[j]);
              break;
            }
          }
        }
      }

      return null;
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing elements to recipientList in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  private NChangeListError addRecipients(PropertyValue propertyValue)
  {
    BAlarmClass ac = getAlarmClass();
    // First, read from the encoded value all of the elements to be added.
    ArrayList<BBacnetDestination> v = new ArrayList<>();
    int ffen = 1; // first failed element number (1-based)
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDestination d = new BBacnetDestination();
          d.readAsn(asnIn);
          v.add(d);
          ffen++;
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "AsnException occurred in addRecipients", e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    // Then, try to add them into Niagara as linked BBacnetDestinations.
    try
    {
      Knob[] knobs = ac.getKnobs(BAlarmClass.alarm);
      int len = knobs.length;
      for (int i = 0; i < v.size(); i++)
      {
        boolean knobFound = false;
        BBacnetDestination dest = v.get(i);
        for (int j = 0; j < len; j++)
        {
          if (knobs[j].getTargetComponent() instanceof BBacnetDestination)
          {
            if (dest.destinationEquals((BBacnetDestination)knobs[j].getTargetComponent()))
            {
              knobFound = true;
              break;
            }
          }
        }
        if (!knobFound)
        {
          BBacnetDestination linkDest = null;
          BComponent alarmService = getAlarmClass().getParent().asComponent();
          BBacnetDestination[] dests = alarmService.getChildren(BBacnetDestination.class);
          for (int k = 0; k < dests.length; k++)
          {
            if (dest.destinationEquals(dests[k]))
            {
              // We found the destination, but it's not linked.
              // Mark the destination for adding a link.
              linkDest = dests[k];
              break;
            }
          }

          // We didn't find a matching destination in the alarm service, so
          // just add the written one in and mark it for linking.
          if (linkDest == null)
          {
            alarmService.add(null, dest, BLocalBacnetDevice.getBacnetContext());
            linkDest = dest;
          }

          // Now link the destination to our alarm class.
          BLink link = new BLink(getAlarmClass().getHandleOrd(),
                                 "alarm",
                                 "routeAlarm",
                                 true);
          linkDest.add(null, link, BLocalBacnetDevice.getBacnetContext());
        }
      }

      return null;
    }
    catch (PermissionException e)
    {
      // BACnet specifies that if one add fails the property must be left in its
      // original state.  Attempt to do this here.
      // FIXX: If this will fail due to permission, it should fail on the very first one,
      // so it should not be necessary to do anything to regain the original state.
      // This is a little chancy, but we do not have an atomic transaction
      // capability in the framework yet.
      log.warning("PermissionException adding elements to recipientList in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
  }

  private NChangeListError removeRecipients(PropertyValue propertyValue)
  {
    BAlarmClass ac = getAlarmClass();
    // First, read from the encoded value all of the elements to be removed.
    ArrayList<BBacnetDestination> v = new ArrayList<>();
    int ffen = 1; // first failed element number (1-based)
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDestination d = new BBacnetDestination();
          d.readAsn(asnIn);
          v.add(d);
          ffen++;
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "AsnException occurred in removeRecipients", e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    // Then, try to remove them.  We do not remove the BacnetDestination
    // components, just the link to them.  This is done in two steps, first
    // determining existence, so we can abort easily if one element is not
    // found.
    Knob[] knobs = ac.getKnobs(BAlarmClass.alarm);
    int len = knobs.length;
    for (ffen = 1; ffen <= v.size(); ffen++)
    {
      BBacnetDestination dest = v.get(ffen - 1);
      boolean found = false;
      for (int j = 0; j < len; j++)
      {
        if (knobs[j].getTargetComponent() instanceof BBacnetDestination)
        {
          if (dest.destinationEquals((BBacnetDestination)knobs[j].getTargetComponent()))
          {
            found = true;
            break;
          }
        }
      }
      if (!found)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.LIST_ELEMENT_NOT_FOUND),
                                    ffen);
      }
    }

    try
    {
      for (int i = 0; i < v.size(); i++)
      {
        BBacnetDestination dest = v.get(i);
        for (int j = 0; j < len; j++)
        {
          if (knobs[j].getTargetComponent() instanceof BBacnetDestination)
          {
            if (dest.destinationEquals((BBacnetDestination)knobs[j].getTargetComponent()))
            {
              BComponent target = knobs[j].getTargetComponent();
              BLink[] tgtLinks = target.getLinks(BAlarmRecipient.routeAlarm);
              for (int k = 0; k < tgtLinks.length; k++)
              {
                if (tgtLinks[k].getSourceComponent() == getAlarmClass())
                {
                  target.remove(tgtLinks[k]);
                  break;
                }
              }
              break;
            }
          }
        }
      }

      return null;
    }
    catch (PermissionException e)
    {
      // BACnet specifies that if one remove fails the property must be left in its
      // original state.  Attempt to do this here.
      // FIXX: If this will fail due to permission, it should fail on the very first one,
      // so it should not be necessary to do anything to regain the original state.
      // This is a little chancy, but we do not have an atomic transaction
      // capability in the framework yet.
      log.warning("PermissionException removing elements to recipientList in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
  }

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
    if (fatalFault)
    {
      return;
    }

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

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.trTitle("BacnetNotificationClassDescriptor", 2);
    out.prop("fatalFault", fatalFault);
    out.prop("ac", ac);
    out.prop("recipientListChanged", recipientListChanged);
    out.trTitle("Recipient List", 2);
    for (int i = 0; i < recipientList.length; i++)
    {
      out.prop("  " + i, recipientList[i]);
    }
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
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

  private static final BIcon icon = BIcon.make(BIcon.std("alarm.png"), BIcon.std("badges/export.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BAlarmClass ac;

  private boolean recipientListChanged = true;
  private BBacnetDestination[] recipientList = new BBacnetDestination[0];
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final AsnInputStream asnIn = new AsnInputStream();
  private static final AsnOutputStream asnOut = new AsnOutputStream();
  private static final int MAX_PRIORITY = 255;

  static Logger log = Logger.getLogger("bacnet.server");

  private static final int[] REQUIRED_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.NOTIFICATION_CLASS,
      BBacnetPropertyIdentifier.PRIORITY,
      BBacnetPropertyIdentifier.ACK_REQUIRED,
      BBacnetPropertyIdentifier.RECIPIENT_LIST
    };

  private static final int[] OPTIONAL_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.DESCRIPTION
    };

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, OPTIONAL_PROPS);
  }
}
