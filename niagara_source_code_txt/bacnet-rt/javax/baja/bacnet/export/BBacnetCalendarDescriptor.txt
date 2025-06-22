/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCalendarEntry;
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
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.schedule.BAbstractSchedule;
import javax.baja.schedule.BCalendarSchedule;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.schedule.ScheduleSupport0;
import com.tridium.bacnet.schedule.ScheduleSupport4;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.server.BBacnetExportTable;

/**
 * BBacnetCalendarDescriptor is the extension that exposes Bacnet Calendar capability.
 *
 * @author Craig Gemmill on 15 Nov 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "schedule:CalendarSchedule"
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
/*
 ord to the calendar being exported.
 */
@NiagaraProperty(
  name = "calendarOrd",
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.CALENDAR)",
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
public class BBacnetCalendarDescriptor
  extends BComponent
  implements BIBacnetExportObject,
             BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetCalendarDescriptor(748117377)1.0$ @*/
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

  //region Property "calendarOrd"

  /**
   * Slot for the {@code calendarOrd} property.
   * ord to the calendar being exported.
   * @see #getCalendarOrd
   * @see #setCalendarOrd
   */
  public static final Property calendarOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.DEFAULT, BFacets.make(BFacets.TARGET_TYPE, "baja:Component"));

  /**
   * Get the {@code calendarOrd} property.
   * ord to the calendar being exported.
   * @see #calendarOrd
   */
  public BOrd getCalendarOrd() { return (BOrd)get(calendarOrd); }

  /**
   * Set the {@code calendarOrd} property.
   * ord to the calendar being exported.
   * @see #calendarOrd
   */
  public void setCalendarOrd(BOrd v) { set(calendarOrd, v, null); }

  //endregion Property "calendarOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.CALENDAR), null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCalendarDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BObject
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

    // Export the calendar and initialize the local copies.
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

    local.unsubscribe(this, calendar);

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
    else if (p.equals(calendarOrd))
    {
      checkConfiguration();
      if (getStatus().isOk())
      {
        BBacnetNetwork.localDevice().incrementDatabaseRevision();
      }
    }
    // Development hook for alternate prot.rev. support
    else if (p.getName() == "protocolRevision")
    {
      setSupport(((BInteger)get("protocolRevision")).getInt());
    }
  }

////////////////////////////////////////////////////////////////
// Development only!!
// For use in providing an earlier protocol revision to test against.
////////////////////////////////////////////////////////////////

  private static void setSupport(int protocolRevision)
  {
    if (protocolRevision >= 4)
    {
      if (!(supp instanceof ScheduleSupport4))
      {
        supp = new ScheduleSupport4();
      }
    }
    else
    {
      if (supp instanceof ScheduleSupport4)
      {
        supp = new ScheduleSupport0();
      }
    }

    log.info("Server calendar support (new) is now " + supp.getClass());
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
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.CALENDAR);
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
    return getCalendar();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getCalendarOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(calendarOrd, objectOrd, cx);
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
    local.unsubscribe(this, calendar);

    // Find the exported calendar.
    findCalendar();

    // Check the configuration.
    boolean configOk = true;
    if (calendar == null)
    {
      setFaultCause("Cannot find exported calendar");
      configOk = false;
    }
    else
    {
      local.subscribe(this, calendar);
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      configOk = false;
    }

    if (configOk)
    {
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

    // Set the config flag.
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
    getCalendar();
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
    getCalendar();
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
    getCalendar();
    if (calendar == null)
    {
      return new ReadRangeAck(BBacnetErrorClass.OBJECT,
                              BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }
    if (rangeReference.getPropertyArrayIndex() >= 0 && rangeReference.getPropertyId() != BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
    }

    switch (rangeReference.getPropertyId())
    {
      case BBacnetPropertyIdentifier.DATE_LIST:
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
        BAbstractSchedule[] dateList = calendar.getChildren(BAbstractSchedule.class);
        int len = dateList.length;

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

          Array<BAbstractSchedule> a = new Array<>(BAbstractSchedule.class);
          int itemsFound = 0;

          if (count > 0)
          {
            // Count is positive: Search from refNdx to end,
            // until we find (count) items.
            for (int i = refNdx - 1; i < len && itemsFound < count; i++)
            {
              a.add(dateList[i]);
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
              a.add(dateList[i]);
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
          Iterator<BAbstractSchedule> it = a.iterator();
          int itemCount = 0;

          synchronized (asnOut)
          {
            asnOut.reset();
            if (maxDataLength > 0)
            {
              while (it.hasNext())
              {
                if ((maxDataLength - asnOut.size()) < BBacnetCalendarEntry.MAX_ENCODED_SIZE)
                {
                  rflags[1] = false;
                  break;
                }
                supp.encodeCalendarEntry(it.next(), asnOut);
                itemCount++;
              }
            }
            else
            {
              itemCount = itemsFound;
              while (it.hasNext())
              {
                supp.encodeCalendarEntry(it.next(), asnOut);
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
                supp.encodeCalendarEntry(dateList[i], asnOut);
                itemCount++;
                if ((maxDataLength - asnOut.size()) < BBacnetCalendarEntry.MAX_ENCODED_SIZE)
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
                supp.encodeCalendarEntry(dateList[i], asnOut);
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
    getCalendar();
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
    BCalendarSchedule c = getCalendar();
    if (c == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    int propertyId = propertyValue.getPropertyId();
    if (propertyId == BBacnetPropertyIdentifier.DATE_LIST)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return addDates(propertyValue);
    }

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
    BCalendarSchedule c = getCalendar();
    if (c == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    int propertyId = propertyValue.getPropertyId();
    if (propertyId == BBacnetPropertyIdentifier.DATE_LIST)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return removeDates(propertyValue);
    }

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

////////////////////////////////////////////////////////////////
// Bacnet Support
////////////////////////////////////////////////////////////////

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, OPTIONAL_PROPS);
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
    if (calendar == null)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.OBJECT,
                                                              BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }

    // Check for array index on non-array property.
    if (ndx >= 0 && pId != BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                              BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY));
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

      case BBacnetPropertyIdentifier.PRESENT_VALUE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(calendar.getOut().getValue()));

      case BBacnetPropertyIdentifier.DATE_LIST:
        return readDateList(ndx);

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
    if (calendar == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT,
                            BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }

    // Check for array index on non-array property.
    if (ndx >= 0 && pId != BBacnetPropertyIdentifier.PROPERTY_LIST)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
    }

    try
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
        case BBacnetPropertyIdentifier.OBJECT_TYPE:
        case BBacnetPropertyIdentifier.PRESENT_VALUE:
        case BBacnetPropertyIdentifier.PROPERTY_LIST:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.OBJECT_NAME:
          return BacUtil.setObjectName(this, objectName, val);

        case BBacnetPropertyIdentifier.DATE_LIST:
          return writeDateList(val);

        case BBacnetPropertyIdentifier.DESCRIPTION:
          setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
          return null;

        default:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.UNKNOWN_PROPERTY);
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

////////////////////////////////////////////////////////////////
// Support
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
   * Get the schedule.
   */
  protected final BCalendarSchedule getCalendar()
  {
    if (calendar == null)
    {
      return findCalendar();
    }
    return calendar;
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  private BCalendarSchedule findCalendar()
  {
    try
    {
      if (!calendarOrd.isEquivalentToDefaultValue(getCalendarOrd()))
      {
        BObject o = getCalendarOrd().get(this);
        if (o instanceof BCalendarSchedule)
        {
          calendar = (BCalendarSchedule) o;
        }
        else
        {
          calendar = null;
        }
      }
    }
    catch (Exception e)
    {
      log.warning("Unable to resolve calendar ord for " + this + ":" + getCalendarOrd() + ": " + e);
      calendar = null;
    }

    if ((calendar == null) && isRunning())
    {
      setFaultCause("Cannot find exported calendar");
      setStatus(BStatus.makeFault(getStatus(), true));
    }

    return calendar;
  }

  /**
   * Read the date list.
   */
  private NReadPropertyResult readDateList(int ndx)
  {
    synchronized (asnOut)
    {
      asnOut.reset();
      supp.encodeDateList(calendar, asnOut);
      return new NReadPropertyResult(BBacnetPropertyIdentifier.DATE_LIST,
                                     ndx, asnOut.toByteArray());
    }
  }

  /**
   * Write the date list.
   */
  private ErrorType writeDateList(byte[] encodedValue)
  {
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(encodedValue);
        BCalendarSchedule newCalendar = supp.decodeDateList(asnIn);

        // Make sure to retain my cleanupExpiredEvents setting.
        boolean cleanup = calendar.getCleanupExpiredEvents();
        newCalendar.setCleanupExpiredEvents(cleanup);

        calendar.copyFrom(newCalendar, BLocalBacnetDevice.getBacnetContext());
      }
      return null;
    }
    catch (OutOfRangeException e)
    {
      log.warning("Value out of range writing datelist in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing datelist in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing datelist in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
    catch (Exception e)
    {
      log.warning("Exception writing datelist in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.OTHER);
    }
  }

  private ChangeListError addDates(PropertyValue propertyValue)
  {
    int ffen = 1; // first failed element number (1-based)
    Iterator<BAbstractSchedule> it;
    ArrayList<BAbstractSchedule> v = new ArrayList<>();
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        while (asnIn.peekTag() != AsnInput.END_OF_DATA)
        {
          BAbstractSchedule ce = supp.decodeCalendarEntry(asnIn);
          if (ce != null)
          {
            v.add(ce);
          }
          ffen++;
        }
      }
    }
    catch (AsnException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "AsnException occurred in addDates in object " + getObjectId(), e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    // Now add the decoded elements to the list,
    // using ffen again to track.
    try
    {
      it = v.iterator();
      SlotCursor<Property> sc;
      while (it.hasNext())
      {
        BAbstractSchedule ce = it.next();
        sc = calendar.getProperties();
        boolean alreadyHere = false;
        while (sc.next(BAbstractSchedule.class))
        {
          if (ce.equivalent(sc.get()))
          {
            alreadyHere = true;
            break;
          }
        }
        if (!alreadyHere)
        {
          calendar.add(null, ce, BLocalBacnetDevice.getBacnetContext());
        }
      }
      return null;
    }
    catch (PermissionException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("PermissionException adding elements to datelist in object " + getObjectId() + ": " + e);
      }

      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
      // BACnet specifies that if one add fails the property must be left in its
      // original state.  Attempt to do this here.
      // FIXX: If this will fail due to permission, it should fail on the very first one,
      //       so it should not be necessary to do anything to regain the original state.
      //       This is a little chancy, but we do not have an atomic transaction
      //       capability in the framework yet.
    }
    catch (Exception e)
    {
      log.warning("Exception adding elements to datelist in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.OTHER),
                                  ffen);
    }
  }

  private ChangeListError removeDates(PropertyValue propertyValue)
  {
    // first failed element number (1-based)
    int ffen = 1;
    ArrayList<BAbstractSchedule> v = new ArrayList<>();
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        while (asnIn.peekTag() != AsnInput.END_OF_DATA)
        {
          BAbstractSchedule ce = supp.decodeCalendarEntry(asnIn);
          if (ce != null)
          {
            v.add(ce);
          }
          ffen++;
        }
      }
    }
    catch (AsnException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "AsnException occurred in removeDates in object " + getObjectId(), e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    // Now remove the decoded elements from the list,
    // using ffen again to track.  Do this in two steps, so
    // we can abort the remove if one isn't found, without
    // having to restore the list (which might cause UI issues).
    try
    {
      BAbstractSchedule[] a = calendar.getSchedules();
      BAbstractSchedule ce;
      // Note internal list element access is zero-based.
      for (ffen = 1; ffen <= v.size(); ffen++)
      {
        ce = v.get(ffen - 1);
        boolean found = false;
        for (int i = 0; i < a.length; i++)
        {
          if (ce.equivalent(a[i]))
          {
            found = true;
            break;
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

      for (ffen = 0; ffen < v.size(); ffen++)
      {
        ce = v.get(ffen);
        for (int i = 0; i < a.length; i++)
        {
          if (ce.equivalent(a[i]))
          {
            calendar.remove(a[i]);
            break;
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
      //       so it should not be necessary to do anything to regain the original state.
      //       This is a little chancy, but we do not have an atomic transaction
      //       capability in the framework yet.
      if (log.isLoggable(Level.FINE))
      {
        log.fine("PermissionException removing elements from datelist in object " + getObjectId() + ": " + e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
    catch (Exception e)
    {
      log.warning("Exception removing elements from datelist in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.OTHER),
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
    out.trTitle("BacnetCalendarDescriptor", 2);
    out.prop("fatalFault", fatalFault);
    out.prop("calendar", calendar);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
    out.prop("supp", supp);
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

  private static final BIcon icon = BIcon.make(BIcon.std("calendar.png"), BIcon.std("badges/export.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BCalendarSchedule calendar;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;

  private static final AsnInputStream asnIn = new AsnInputStream();
  private static final AsnOutputStream asnOut = new AsnOutputStream();

  private static ScheduleSupport0 supp = new ScheduleSupport4();

  private static final Logger log = Logger.getLogger("bacnet.server");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final int[] REQUIRED_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.PRESENT_VALUE,
      BBacnetPropertyIdentifier.DATE_LIST,
    };

  private static final int[] OPTIONAL_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.DESCRIPTION
    };
}
