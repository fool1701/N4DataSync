/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnDataTypeNotSupportedException;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.DataTypeNotSupportedException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyReference;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.bacnet.util.SpecialEventDetails;
import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.schedule.BAbstractSchedule;
import javax.baja.schedule.BCalendarSchedule;
import javax.baja.schedule.BCompositeSchedule;
import javax.baja.schedule.BDailySchedule;
import javax.baja.schedule.BDaySchedule;
import javax.baja.schedule.BScheduleReference;
import javax.baja.schedule.BTimeSchedule;
import javax.baja.schedule.BWeeklySchedule;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.bacnet.BacUtil;
import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.schedule.ScheduleSupport0;
import com.tridium.bacnet.schedule.ScheduleSupport4;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.BacnetServicePrimitive;
import com.tridium.bacnet.services.confirmed.CreateObjectAck;
import com.tridium.bacnet.services.confirmed.CreateObjectRequest;
import com.tridium.bacnet.services.confirmed.DeleteObjectAck;
import com.tridium.bacnet.services.confirmed.DeleteObjectRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;
import com.tridium.bacnet.services.error.CreateObjectError;
import com.tridium.bacnet.services.error.DeleteObjectError;
import com.tridium.bacnet.services.error.NChangeListError;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;
import com.tridium.bacnet.stack.server.BBacnetExportTable;
import com.tridium.bacnet.stack.server.object.BObjectHandler;

/**
 * BBacnetScheduleDescriptor exposes a Niagara schedule to Bacnet.
 *
 * @author Craig Gemmill on 18 Aug 03
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
 ord to the schedule being exported.
 */
@NiagaraProperty(
  name = "scheduleOrd",
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
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.SCHEDULE)",
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
  name = "listOfObjectPropertyReferences",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetDeviceObjectPropertyReference.TYPE)"
)
@NiagaraProperty(
  name = "priorityForWriting",
  type = "int",
  defaultValue = "16",
  facets = @Facet("BFacets.makeInt(1, 16)")
)
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
/*
 indicates misconfiguration
 */
@NiagaraProperty(
  name = "reliability",
  type = "BBacnetReliability",
  defaultValue = "BBacnetReliability.noFaultDetected",
  flags = Flags.TRANSIENT | Flags.READONLY
)
@NiagaraAction(
  name = "writePresentValue",
  flags = Flags.HIDDEN | Flags.ASYNC
)
abstract public class BBacnetScheduleDescriptor
  extends BComponent
  implements BIBacnetExportObject,
             BacnetPropertyListProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetScheduleDescriptor(3502858208)1.0$ @*/
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

  //region Property "scheduleOrd"

  /**
   * Slot for the {@code scheduleOrd} property.
   * ord to the schedule being exported.
   * @see #getScheduleOrd
   * @see #setScheduleOrd
   */
  public static final Property scheduleOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.DEFAULT, BFacets.make(BFacets.TARGET_TYPE, "baja:Component"));

  /**
   * Get the {@code scheduleOrd} property.
   * ord to the schedule being exported.
   * @see #scheduleOrd
   */
  public BOrd getScheduleOrd() { return (BOrd)get(scheduleOrd); }

  /**
   * Set the {@code scheduleOrd} property.
   * ord to the schedule being exported.
   * @see #scheduleOrd
   */
  public void setScheduleOrd(BOrd v) { set(scheduleOrd, v, null); }

  //endregion Property "scheduleOrd"

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.SCHEDULE), null);

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

  //region Property "listOfObjectPropertyReferences"

  /**
   * Slot for the {@code listOfObjectPropertyReferences} property.
   * @see #getListOfObjectPropertyReferences
   * @see #setListOfObjectPropertyReferences
   */
  public static final Property listOfObjectPropertyReferences = newProperty(0, new BBacnetListOf(BBacnetDeviceObjectPropertyReference.TYPE), null);

  /**
   * Get the {@code listOfObjectPropertyReferences} property.
   * @see #listOfObjectPropertyReferences
   */
  public BBacnetListOf getListOfObjectPropertyReferences() { return (BBacnetListOf)get(listOfObjectPropertyReferences); }

  /**
   * Set the {@code listOfObjectPropertyReferences} property.
   * @see #listOfObjectPropertyReferences
   */
  public void setListOfObjectPropertyReferences(BBacnetListOf v) { set(listOfObjectPropertyReferences, v, null); }

  //endregion Property "listOfObjectPropertyReferences"

  //region Property "priorityForWriting"

  /**
   * Slot for the {@code priorityForWriting} property.
   * @see #getPriorityForWriting
   * @see #setPriorityForWriting
   */
  public static final Property priorityForWriting = newProperty(0, 16, BFacets.makeInt(1, 16));

  /**
   * Get the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public int getPriorityForWriting() { return getInt(priorityForWriting); }

  /**
   * Set the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public void setPriorityForWriting(int v) { setInt(priorityForWriting, v, null); }

  //endregion Property "priorityForWriting"

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
  public BBacnetReliability getReliability() { return (BBacnetReliability)get(reliability); }

  /**
   * Set the {@code reliability} property.
   * indicates misconfiguration
   * @see #reliability
   */
  public void setReliability(BBacnetReliability v) { set(reliability, v, null); }

  //endregion Property "reliability"

  //region Action "writePresentValue"

  /**
   * Slot for the {@code writePresentValue} action.
   * @see #writePresentValue()
   */
  public static final Action writePresentValue = newAction(Flags.HIDDEN | Flags.ASYNC, null);

  /**
   * Invoke the {@code writePresentValue} action.
   * @see #writePresentValue
   */
  public void writePresentValue() { invoke(writePresentValue, null, null); }

  //endregion Action "writePresentValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetScheduleDescriptor.class);

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
  public void started()
    throws Exception
  {
    super.started();

    // First check for fatal faults.
    checkFatalFault();

    // Export the schedule and initialize the local copies.
    oldId = getObjectId();
    oldName = getObjectName();
    checkConfiguration();
    validateReferences();

    // Increment the Device object's Database_Revision for created objects.
    if (Sys.isStationStarted() && configOk())
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
    local.unsubscribe(this, schedule);
    local.unexport(oldId, oldName, this);

    // Clear the local copies.
    schedule = null;
    oldId = null;
    oldName = null;

    // Increment the Device object's Database_Revision for deleted objects.
    if (configOk() && local.isRunning())
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
    else if (p.equals(scheduleOrd))
    {
      checkConfiguration();
      if (configOk()) BBacnetNetwork.localDevice().incrementDatabaseRevision();
    }
    else if (p.equals(priorityForWriting))
    {
      BBacnetNetwork.bacnet().postAsync(new Runnable()
      {
        @Override
        public void run()
        {
          writePresentValue();
        }
      });
    }
    else if (p.equals(listOfObjectPropertyReferences))
    {
      BBacnetNetwork.bacnet().postAsync(new Runnable()
      {
        @Override
        public void run()
        {
          resolveTargetReferences();
          try
          {
            Thread.sleep(5000);
          }
          catch (InterruptedException ignored)
          {}
          writePresentValue();
        }
      });
    }
    // Development hook for alternate prot.rev. support
    else if (p.getName() == "protocolRevision")
    {
      setSupport(((BInteger)get("protocolRevision")).getInt());
    }
    else
    {
      super.changed(p, cx);
    }
  }

  @Override
  public void atSteadyState()
    throws Exception
  {
    super.atSteadyState();
    resolveTargetReferences();
  }

///////////////////////////////////////
// Development only!!
// For use in providing an earlier protocol revision to test against.
///////////////////////////////////////

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
    if (log.isLoggable(Level.FINE))
    {
      log.fine("Server schedule support (new) is now " + supp.getClass());
    }
  }

  /**
   * Get slot facets.
   *
   * @param s slot
   * @return the appropriate slot facets.
   */
  @Override
  public final BFacets getSlotFacets(Slot s)
  {
    if (s == objectId)
    {
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.SCHEDULE);
    }
    return super.getSlotFacets(s);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  @Override
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(writePresentValue))
    {
      resolveTargetReferences();
      return BBacnetNetwork.bacnet().postAsync(new Invocation(this, action, arg, cx));
    }

    return super.post(action, arg, cx);
  }

  /**
   * Write the present value of the schedule to non-Present_Value
   * target properties, and to any external targets.
   */
  abstract public void doWritePresentValue();

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
    return getSchedule();
  }

  /**
   * Get the BOrd to the exported object.
   */
  @Override
  public final BOrd getObjectOrd()
  {
    return getScheduleOrd();
  }

  /**
   * Set the BOrd to the exported object.
   *
   * @param objectOrd
   */
  @Override
  public final void setObjectOrd(BOrd objectOrd, Context cx)
  {
    set(scheduleOrd, objectOrd, cx);
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
    local.unsubscribe(this, schedule);

    // Find the exported schedule.
    findSchedule();

    // Check the configuration.
    boolean cfgOk = true;
    if (schedule == null)
    {
      setFaultCause("Cannot find exported schedule");
      cfgOk = false;
    }
    else
    {
      local.subscribe(this, schedule);
    }

    // Check for valid object id.
    if (!getObjectId().isValid())
    {
      setFaultCause("Invalid Object ID");
      cfgOk = false;
    }

    cfgOk &= checkScheduleConfiguration();

    if (cfgOk)
    {
      // Try to export - duplicate id & names will be checked in here.
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
      setFaultCause("");

      // This may potentially set a fault, but the schedule
      // is already exported properly.
      validate();
    }
    else
    {
      setStatus(BStatus.makeFault(getStatus(), true));
    }
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
    getSchedule();
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
    getSchedule();
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
    getSchedule();
    if (schedule == null)
    {
      return new ReadRangeAck(BBacnetErrorClass.OBJECT,
                              BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }
    if (rangeReference.getPropertyArrayIndex() >= 0)
    {
      if (!isArray(rangeReference.getPropertyId()))
      {
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }
    switch (rangeReference.getPropertyId())
    {
      case BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES:
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
        BBacnetDeviceObjectPropertyReference[] list = getListOfObjectPropertyReferences().getChildren(BBacnetDeviceObjectPropertyReference.class);

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

          Array<BBacnetDeviceObjectPropertyReference> a = new Array<>(BBacnetDeviceObjectPropertyReference.class);
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
          Iterator<BBacnetDeviceObjectPropertyReference> it = a.iterator();
          int itemCount = 0;

          synchronized (asnOut)
          {
            asnOut.reset();
            if (maxDataLength > 0)
            {
              while (it.hasNext())
              {
                if ((maxDataLength - asnOut.size()) < BBacnetDeviceObjectPropertyReference.MAX_ENCODED_SIZE)
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
                if ((maxDataLength - asnOut.size()) < BBacnetDeviceObjectPropertyReference.MAX_ENCODED_SIZE)
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

  @Override
  public final ErrorType writeProperty(PropertyValue val)
    throws BacnetException
  {
    getSchedule();
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
    if (getSchedule() == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return addScheduleTargets(propertyValue.getPropertyValue());
    }
    else
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.SERVICES,
                                                 BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
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
    if (getSchedule() == null)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.OBJECT,
                                                 BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                  0);
    }

    if (propertyValue.getPropertyId() == BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES)
    {
      // Check for array index on non-array property.
      if (propertyValue.getPropertyArrayIndex() >= 0)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                    0);
      }

      return removeScheduleTargets(propertyValue.getPropertyValue());
    }
    else
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.SERVICES,
                                                 BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST),
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
    if (propertyId == BBacnetPropertyIdentifier.WEEKLY_SCHEDULE)
    {
      return true;
    }
    if (propertyId == BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE)
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
    if (schedule == null)
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

      case BBacnetPropertyIdentifier.OBJECT_NAME:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(getObjectName()));

      case BBacnetPropertyIdentifier.OBJECT_TYPE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(getObjectId().getObjectType()));

      case BBacnetPropertyIdentifier.PROPERTY_LIST:
        return readPropertyList(ndx);

      case BBacnetPropertyIdentifier.EFFECTIVE_PERIOD:
        synchronized (asnOut)
        {
          asnOut.reset();
          supp.encodeDateRange(schedule.getEffective(), asnOut);
          return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
        }

      case BBacnetPropertyIdentifier.WEEKLY_SCHEDULE:
        return readWeeklySchedule(ndx);

      case BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE:
        return readExceptionSchedule(ndx);

      case BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES:
        return readListOfObjectPropertyReferences(ndx);

      case BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(getPriorityForWriting()));

      case BBacnetPropertyIdentifier.STATUS_FLAGS:
        return new NReadPropertyResult(pId, ndx, AsnUtil.statusToAsnStatusFlags(((BStatusValue)schedule.get("out")).getStatus()));

      case BBacnetPropertyIdentifier.RELIABILITY:
        int rel = getReliability().getOrdinal();
        if (((BStatusValue)schedule.get("out")).getStatus().isFault())
        {
          rel = BBacnetReliability.CONFIGURATION_ERROR;
        }
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(rel));

      case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(((BStatusValue)schedule.get("out")).getStatus().isDisabled()));

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
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    if (schedule == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT, BBacnetErrorCode.TARGET_NOT_CONFIGURED);
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
      synchronized (asnIn)
      {
        asnIn.setBuffer(val);
        switch (pId)
        {
          case BBacnetPropertyIdentifier.OBJECT_IDENTIFIER:
          case BBacnetPropertyIdentifier.OBJECT_TYPE:
          case BBacnetPropertyIdentifier.PROPERTY_LIST:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.OBJECT_NAME:
            return BacUtil.setObjectName(this, objectName, val);

          case BBacnetPropertyIdentifier.EFFECTIVE_PERIOD:
            schedule.set(BWeeklySchedule.effective,
                         supp.decodeDateRange(asnIn),
                         BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.WEEKLY_SCHEDULE:
            return writeWeeklySchedule(ndx);

          case BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE:
            return writeExceptionSchedule(ndx);

          case BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES:
            return writeListOfObjectPropertyReferences(pId, ndx, val, pri);

          case BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING:
            int pfw = asnIn.readUnsignedInt();
            if ((pfw < 1) || (pfw > 16))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            setInt(priorityForWriting, pfw, BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.STATUS_FLAGS:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.RELIABILITY:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
            // First check for input link.  If one exists then no writing!
            Property pIn = schedule.loadSlots().getProperty("in");
            BLink[] inLinks = schedule.getLinks(pIn);
            if (inLinks.length > 0)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.WRITE_ACCESS_DENIED);
            }
            // Ok, try to set input from outOfService
            boolean outOfService = asnIn.readBoolean();
            BStatusValue svi = (BStatusValue)schedule.get(pIn).newCopy();
            Property pOut = schedule.loadSlots().getProperty("out");
            BStatusValue svo = (BStatusValue)schedule.get(pOut).newCopy();
            svi.setStatusNull(!outOfService);
            svi.setStatusDisabled(outOfService);
            svi.setValueValue(svo.getValueValue());
            schedule.set(pIn, svi, BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.DESCRIPTION:
            setString(description, AsnUtil.fromAsnCharacterString(val), BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.SCHEDULE_DEFAULT:
            return writeScheduleDefaultValue(val);

          default:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.UNKNOWN_PROPERTY);
        }
      }
    }
    catch (OutOfRangeException ore)
    {
      log.warning("Value out of range exception writing property " + pId + " in object " + getObjectId() + ": " + ore);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (AsnDataTypeNotSupportedException e)
    {
      log.warning(bacnetLexicon.getText("BacnetSchedule.typechange.warning", new Object[]
                                                                                  {
                                                                                    getAsnType(),
                                                                                    e.getAsnType()
                                                                                  }));
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INCONSISTENT_PARAMETERS);
    }
    catch (DataTypeNotSupportedException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.DATATYPE_NOT_SUPPORTED);
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
   * Override point for subclasses to provide additional configuration
   * constraints to allow point export.  Default implementation returns true.
   *
   * @return true if configuration is ok, false otherwise.
   */
  protected boolean checkScheduleConfiguration()
  {
    return true;
  }

  /**
   * Check if the exported object's state is valid.
   */
  void checkValid()
  {
    if (configOk()) validate();
  }

  /**
   * Validate the schedule's configuration.  For schedules, an event
   * cannot have a value with a null status.
   */
  protected void validate()
  {
    BWeeklySchedule ws = getSchedule();
    for (int i = 0; i < 7; i++)
    {
      BDaySchedule ds = ws.get(BWeekday.make(i));
      BTimeSchedule[] times = ds.getTimesInOrder();
      for (int j = 0; j < times.length; j++)
      {
        if (times[j].getEffectiveValue().getStatus().isNull())
        {
          markConfigurationError("Exported schedule value cannot be null");
          return;
        }
      }
    }
    markNoFaultDetected();
  }

  /**
   * Write the default value for the schedule
   * @param val
   * @return null if no error, otherwise error code
   */
  protected ErrorType writeScheduleDefaultValue(byte[] val)
  {
    BWeeklySchedule sched = getSchedule();
    if (sched == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT,BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }
    synchronized (asnIn)
    {
      asnIn.setBuffer(val);
      try
      {
        int applicationTag = asnIn.peekApplicationTag();
        if (applicationTag == ASN_NULL)
        {
          sched.getDefaultOutput().set(BStatusValue.status,
                                       BStatus.make(sched.getDefaultOutput().getStatus(), BStatus.NULL, true),
                                       BLocalBacnetDevice.getBacnetContext());
          return null;
        }
        if (!isEqual(applicationTag, getAsnType()))
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.INCONSISTENT_PARAMETERS);
        }
        return doWriteScheduleDefaultValue(asnIn, applicationTag);
      }
      catch (OutOfRangeException e)
      {
        log.warning("Value out of range writing property " + BBacnetPropertyIdentifier.SCHEDULE_DEFAULT + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.VALUE_OUT_OF_RANGE);
      }
      catch (DataTypeNotSupportedException e)
      {
        log.warning("Datatype not supported writing property " + BBacnetPropertyIdentifier.SCHEDULE_DEFAULT + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.DATATYPE_NOT_SUPPORTED);
      }
      catch (AsnException e)
      {
        log.warning("AsnException writing property " + BBacnetPropertyIdentifier.SCHEDULE_DEFAULT + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.INVALID_DATA_TYPE);
      }
      catch (PermissionException e)
      {
        log.warning("PermissionException writing property " + BBacnetPropertyIdentifier.SCHEDULE_DEFAULT + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
      catch (Exception e)
      {
        log.warning("Exception writing property " + BBacnetPropertyIdentifier.SCHEDULE_DEFAULT + " in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.OTHER);
      }
    }
  }

  /**
   * Needs to be overridden by typed schedule descriptors
   * @param asnInputStream
   * @return null if no error, otherwise errortype
   */
  protected abstract ErrorType doWriteScheduleDefaultValue(AsnInputStream asnInputStream, int applicationTag)
    throws Exception;

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

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Get the schedule.
   */
  protected final BWeeklySchedule getSchedule()
  {
    if (schedule == null)
    {
      return findSchedule();
    }
    return schedule;
  }

  /**
   * Mark the schedule descriptor to `fault` state.
   * @param faultCause
   */
  public void markConfigurationError(String faultCause)
  {
    setReliability(BBacnetReliability.configurationError);
    setFaultCause(faultCause);
    setStatus(BStatus.makeFault(getStatus(), true));
  }

  /**
   * Mark the schedule descriptor to `noFaultDetected` state.
   */
  public void markNoFaultDetected()
  {
    setReliability(BBacnetReliability.noFaultDetected);
    setFaultCause("");
    setStatus(BStatus.ok);
  }

  /**
   * If the dynamic property LAST_EFFECTIVE_VALUE exists, its value is returned.
   */
  public BStatusValue getLastEffectiveValue()
  {
    return (BStatusValue) get(LAST_EFFECTIVE_VALUE);
  }
  /**
   * Creates the LAST_EFFECTIVE_VALUE dynamic property.
   */
  public void setLastEffectiveValue(BStatusValue o)
  {
    if (get(LAST_EFFECTIVE_VALUE) == null)
    {
      add(LAST_EFFECTIVE_VALUE, o, Flags.OPERATOR | Flags.HIDDEN);
    }
    else
    {
      set(LAST_EFFECTIVE_VALUE, o);
    }
  }

  private BWeeklySchedule findSchedule()
  {
    try
    {
      if (!scheduleOrd.isEquivalentToDefaultValue(getScheduleOrd()))
      {
        BObject o = getScheduleOrd().get(this);
        if (o instanceof BWeeklySchedule)
        {
          schedule = (BWeeklySchedule) o;
        }
        else
        {
          schedule = null;
        }
      }
      if (!isScheduleTypeLegal(schedule))
      {
        schedule = null;
      }
    }
    catch (Exception e)
    {
      log.warning("Unable to resolve schedule ord for " + this + ": " + getScheduleOrd() + ": " + e);
      schedule = null;
    }
    if ((schedule == null) && isRunning())
    {
      setFaultCause("Cannot find exported schedule");
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    return schedule;
  }

  /**
   * Override point for BBacnetScheduleDescriptors to enforce
   * type rules for their exposed schedules.
   *
   * @param sched the exposed schedule
   * @return true if the Niagara schedule type is legal for this schedule type.
   */
  boolean isScheduleTypeLegal(BWeeklySchedule sched)
  {
    return true;
  }

  /**
   * Get the ASN type to use in encoding the TimeValues for this schedule.
   */
  abstract int getAsnType();

  /**
   * Get the output property to which we link.
   *
   * @return the output property for this schedule.
   */
  abstract Property getScheduleOutputProperty();

  abstract BStatusValue getEffectiveValueFrom(BStatusValue statusValue);

  /**
   * Read the weekly schedule.
   */
  private PropertyValue readWeeklySchedule(int ndx)
  {
    synchronized (asnOut)
    {
      asnOut.reset();
      switch (ndx)
      {
        case 0:
          return new NReadPropertyResult(BBacnetPropertyIdentifier.WEEKLY_SCHEDULE, ndx, AsnUtil.toAsnUnsigned(7));

        case -1:
          for (int i = BAC_MONDAY; i <= BAC_SUNDAY; i++)
          {
            supp.encodeDailySchedule(schedule.get(BWeekday.make(i % 7)),
                                                  schedule.getDefaultOutput(),
                                                  asnOut,
                                                  getAsnType());
          }
          return new NReadPropertyResult(BBacnetPropertyIdentifier.WEEKLY_SCHEDULE, ndx, asnOut.toByteArray());

        case BAC_MONDAY:
        case BAC_TUESDAY:
        case BAC_WEDNESDAY:
        case BAC_THURSDAY:
        case BAC_FRIDAY:
        case BAC_SATURDAY:
        case BAC_SUNDAY:
          supp.encodeDailySchedule(schedule.get(BWeekday.make(ndx % 7)),
                                                schedule.getDefaultOutput(),
                                                asnOut,
                                                getAsnType());
          return new NReadPropertyResult(BBacnetPropertyIdentifier.WEEKLY_SCHEDULE, ndx, asnOut.toByteArray());

        default:
          return new NReadPropertyResult(BBacnetPropertyIdentifier.WEEKLY_SCHEDULE, ndx, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                                                        BBacnetErrorCode.INVALID_ARRAY_INDEX));
      }
    }
  }

  /**
   * Read the special event schedule.
   */
  private PropertyValue readExceptionSchedule(int ndx)
  {
    try
    {
      synchronized (asnOut)
      {
        asnOut.reset();
        switch (ndx)
        {
          case 0:
            SlotCursor<Property> c = schedule.getSpecialEvents().getProperties();
            int cnt = 0;
            while (c.next(BDailySchedule.class))
            {
              cnt += 1;
            }
            return new NReadPropertyResult(BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE, ndx, AsnUtil.toAsnUnsigned(cnt));

          case -1:
            supp.encodeExceptionScheduleWithIdx(schedule.getSpecialEvents(),
                                                schedule.getDefaultOutput(),
                                                asnOut,
                                                getAsnType(),
                                                BBacnetNetwork.localDevice().getObjectId());
            return new NReadPropertyResult(BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE, ndx, asnOut.toByteArray());

          default:
            if (ndx < 0)
              return new NReadPropertyResult(BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE, ndx,
                                             new NErrorType(BBacnetErrorClass.PROPERTY,
                                                            BBacnetErrorCode.INVALID_ARRAY_INDEX));
            supp.encodeSpecialEvent(ndx,
                                    schedule.getSpecialEvents(),
                                    schedule.getDefaultOutput(),
                                    asnOut,
                                    getAsnType(),
                                    BBacnetNetwork.localDevice().getObjectId());
            return new NReadPropertyResult(BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE, ndx, asnOut.toByteArray());
        }
      }
    }
    catch (Exception e)
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE, ndx,
                                     new NErrorType(BBacnetErrorClass.PROPERTY,
                                                    BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }
  }

  /**
   * Write the weekly schedule.
   * Note: The ASN Input Stream MUST have already been pre-initialized with the
   * write value!
   */
  private ErrorType writeWeeklySchedule(int ndx)
    throws BacnetException
  {
    try
    {
      synchronized (asnIn)
      {
        switch (ndx)
        {
          case 0:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);

          case -1:
            try
            {
              List<BDaySchedule> schedules = new ArrayList<>();
              for (int i = BAC_MONDAY; i <= BAC_SUNDAY; i++)
              {
                schedules.add(supp.decodeDailySchedule(schedule.getDefaultOutput(),
                                                       asnIn,
                                                       getAsnType()));
              }

              return AsnUtil.peekTagAndPerform(asnIn, AsnInput.END_OF_DATA, BBacnetErrorCode.INVALID_ARRAY_INDEX, () ->
                {
                  for (int i = BAC_MONDAY; i <= BAC_SUNDAY; i++)
                  {
                    ((BDailySchedule) schedule.getWeek()
                      .get(BWeekday.make(i % 7).getTag()))
                      .set(BDailySchedule.day,
                        schedules.get(i - 1),
                        BLocalBacnetDevice.getBacnetContext());
                  }
                });
            }
            catch (AsnException e)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }

          case BAC_MONDAY:
          case BAC_TUESDAY:
          case BAC_WEDNESDAY:
          case BAC_THURSDAY:
          case BAC_FRIDAY:
          case BAC_SATURDAY:
          case BAC_SUNDAY:
            ((BDailySchedule)schedule.getWeek().get(BWeekday.make(ndx % 7).getTag())).set(BDailySchedule.day,
                                                                                          supp.decodeDailySchedule(schedule.getDefaultOutput(),
                                                                                                                   asnIn,
                                                                                                                   getAsnType()),
                                                                                          BLocalBacnetDevice.getBacnetContext());
            //In case of successful write of weekly schedule, set no fault
            return null;

          default:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.INVALID_ARRAY_INDEX);
        }
      }
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing weeklySchedule in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  /**
   * Write the special event schedule.
   * Note: The ASN Input Stream MUST have already been pre-initialized with the
   * write value!
   */
  private ErrorType writeExceptionSchedule(int ndx)
    throws BacnetException
  {
    BCompositeSchedule newSchedule = null;
    BCompositeSchedule oldSchedule = null;
    Property specialEvents = schedule.getSchedule().loadSlots().getProperty("specialEvents");

    try
    {
      synchronized (asnIn)
      {
        oldSchedule = schedule.getSpecialEvents();
        switch (ndx)
        {
          case 0:
            writeExceptionSchSize(oldSchedule, asnIn.readInteger());
            return null;

          case -1:
            newSchedule = supp.decodeExceptionSchedule(schedule.getDefaultOutput(),
                                                       asnIn,
                                                       BBacnetNetwork.localDevice().getObjectId(),
                                                       getAsnType());

            switchOrds(newSchedule.getChildren(BDailySchedule.class));
            schedule.getSchedule().set(specialEvents, newSchedule, BLocalBacnetDevice.getBacnetContext());
            return null;

          default:
            BDailySchedule specialEvent = supp.decodeSpecialEvent(schedule.getDefaultOutput(),
                                                                  asnIn,
                                                                  BBacnetNetwork.localDevice().getObjectId(),
                                                                  getAsnType(),
                                                                  ndx);
            switchOrds(new BDailySchedule[] { specialEvent });

            if (ndx < 1)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }
            BDailySchedule[] se = oldSchedule.getChildren(BDailySchedule.class);

            if (ndx > se.length)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }
            else
            {
              se[ndx - 1].copyFrom(specialEvent, BLocalBacnetDevice.getBacnetContext());
              return null;
            }
        }
      }
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing exceptionSchedule in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  private static void writeExceptionSchSize(BCompositeSchedule specialEvents, int newSize)
  {
    if (specialEvents != null)
    {
      BDailySchedule[] se = specialEvents.getChildren(BDailySchedule.class);
      if (se != null)
      {
        Arrays.sort(se, specialEventBnIdxComparator);
        int sizeChange = se.length - newSize;
        if (sizeChange > 0)
        {
          for (int i = 0; i < sizeChange; i++)
          {
            specialEvents.remove(se[(se.length - 1) - i]);
          }
        }
        else
        {
          for (int i = 0; i < -sizeChange; i++)
          {
            BDailySchedule newSch = new BDailySchedule();
            newSch.add(ScheduleSupport0.BACNET_IDX, BInteger.make(se.length + i));
            specialEvents.add(null, newSch);
          }
        }
      }
    }
  }

  private void switchOrds(BDailySchedule[] specialEvents)
  {
    if (specialEvents == null)
    {
      return;
    }
    for (int i = 0; i < specialEvents.length; i++)
    {
      BDailySchedule specialEvent = specialEvents[i];

      // If the event was specified using a BACnet Calendar Reference,
      // switch the ord to a Niagara slot path ord, so it can be resolved
      // on the client (workbench) side.
      if (specialEvent.getDays() instanceof BScheduleReference)
      {
        BOrd ref = ((BScheduleReference)specialEvent.getDays()).getRef();
        BComponent c = ref.get(this).asComponent();
        if (c instanceof BBacnetCalendarDescriptor)
        {
          BCalendarSchedule cal = (BCalendarSchedule)((BBacnetCalendarDescriptor)c).getObject();
          ((BScheduleReference)specialEvent.getDays()).setRef(cal.getSlotPathOrd());
        }
      }
    }
  }

  /**
   * Read the list of target references.
   */
  private PropertyValue readListOfObjectPropertyReferences(int ndx)
  {
    synchronized (asnOut)
    {
      asnOut.reset();
      getListOfObjectPropertyReferences().writeAsn(asnOut);
      return new NReadPropertyResult(BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES,
                                     ndx,
                                     asnOut.toByteArray());
    }
  }

  /**
   * Write the list of target references.
   * Niagara links are NOT exposed to BACnet.  However, internal references
   * may still be put into the list.  Niagara will accomplish writes to ALL
   * targets in the list using WriteProperty.  For external writes, this is
   * a BACnet WriteProperty-Request that is sent using the stack.  For internal
   * writes, we use the writeProperty() API of BIBacnetExportObject.  Note
   * that the target reference <i>must</i> be BACnet-writable, or this write
   * will fail.
   * <b>NOTE:</b>This assumes that the class field <code>asnIn</code> has been
   * initialized with the source data already!
   */
  private ErrorType writeListOfObjectPropertyReferences(int pId, int ndx, byte[] val, int pri)
    throws BacnetException
  {
    List<BBacnetDeviceObjectPropertyReference> objectPropertyReferenceList = Collections.synchronizedList(new ArrayList<BBacnetDeviceObjectPropertyReference>());
    int firstAsnType = -1;
    try
    {
      synchronized (asnIn)
      {
        int tag = asnIn.peekTag();
        int currentScheduleAsnType = getAsnType();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDeviceObjectPropertyReference deviceObjectPropertyReference = new BBacnetDeviceObjectPropertyReference();
          deviceObjectPropertyReference.readAsn(asnIn);
          if (deviceObjectPropertyReference.isDeviceIdUsed() &&
              !(deviceObjectPropertyReference.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId())))
          {
            if (deviceObjectPropertyReference.getDeviceId().getObjectType() != BBacnetObjectType.DEVICE)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
          }
          PropertyInfo propertyInfo = getPropertyInfo(deviceObjectPropertyReference);
          // Update the firts ASN type. This will be used as reference to arrive at whether the list
          // of passed object value property references are homogeneous.
          if (firstAsnType == -1)
          {
            firstAsnType = propertyInfo.getAsnType();
          }
          boolean hasError = Boolean.TRUE;
          if (propertyInfo != null)
          {
            // If the values passed to the list of object value property references correspond to
            // the current schedule descriptor type, add them to the writable list
            if (isEqual(propertyInfo.getAsnType(), currentScheduleAsnType))
            {
              objectPropertyReferenceList.add(deviceObjectPropertyReference);
              hasError = Boolean.FALSE;
            }
            // If the values passed to the list of object value property references correspond to
            // the current schedule descriptor type, do not add them to the writable list.
            // This may however be enabled in future and worthwhile considering it as an error
            // with inconsistent parameters as opposed to invalid data type.
            // However we need to log a warning since the type of the schedule descriptor is to be changed.
            else if (isEqual(propertyInfo.getAsnType(), firstAsnType))
            {
              hasError = Boolean.FALSE;
              log.warning(getLexicon().getText("BacnetSchedule.typechange.warning", new Object[] { currentScheduleAsnType, firstAsnType}));
            }
          }
          if (hasError)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.DATATYPE_NOT_SUPPORTED);
          }
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    // In case the group was heterogeneous, data type error would have been thrown already.

    // This is the case when the list of object value properties to be written are the same
    // as the schedule type
    if (isEqual(firstAsnType, getAsnType()))
    {
      return updateCurrentBacnetSchedule(objectPropertyReferenceList);
    }
    // In case the schedule type is different from the List of object value props to be written, throw an error
    else
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INCONSISTENT_PARAMETERS);
    }
  }

  /**
   * Update the current bacnet schedule using the list of object property value references.
   * @param objectPropertyReferences
   * @return
   */
  private ErrorType updateCurrentBacnetSchedule(List<BBacnetDeviceObjectPropertyReference> objectPropertyReferences)
  {
    // Clear the list, as this is a complete replacement.
    BBacnetListOf listOf = getListOfObjectPropertyReferences();
    listOf.removeAll();
    try
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      Iterator<BBacnetDeviceObjectPropertyReference> it = objectPropertyReferences.iterator();
      while (it.hasNext())
      {
        BBacnetDeviceObjectPropertyReference r = it.next();

        // For each reference that is local to this device, make sure that the
        // target point exists and is properly configured.
        if (!r.isDeviceIdUsed() ||
            r.getDeviceId().equals(local.getObjectId()))
        {
          BIBacnetExportObject o = local.lookupBacnetObject(r.getObjectId());
          if (o == null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }
          BObject obj = o.getObject();
          if (obj == null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.TARGET_NOT_CONFIGURED);
          }
        }

        // Internal Links are no longer represented in the list of target
        // references.  Internal writes may still be configured by BACnet
        // clients; they are only represented in this list, rather than
        // being also made into Links.
        BacnetDescriptorUtil.parseLogDeviceObjectProperty(null, r);

        // Now add this reference to the list.
        listOf.addListElement(r, BLocalBacnetDevice.getBacnetContext());
      }

      return null;
    }
    catch (PermissionException e)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
    catch (Exception e)
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.OTHER);
    }
  }

  /**
   * Replace the current bacnet schedule with a different type by copyting over applicable properties from
   * the original type.
   * @param pId
   * @param ndx
   * @param val
   * @param pri
   * @return
   */
  private ErrorType replaceCurrentBacnetSchedule(int pId, int ndx, byte[] val, int pri)
  {
    Array<PropertyValue> initialValues = new Array<>(PropertyValue.class);
    initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.OBJECT_NAME, AsnUtil.toAsnCharacterString(getObjectName())));
    initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.DESCRIPTION, AsnUtil.toAsnCharacterString(getDescription())));
    initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, AsnUtil.toAsnUnsigned(getPriorityForWriting())));
    initialValues.add(new NBacnetPropertyValue(pId, ndx, val, pri));
    Map<Integer, List<BTimeSchedule>> timeScheduleMap = null;
    Map<String, SpecialEventDetails> specialEventMap = null;
    if(getSchedule() != null)
    {
      // Write the 'effective period' value
      asnOut.reset();
      supp.encodeDateRange(getSchedule().getEffective(), asnOut);
      initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.EFFECTIVE_PERIOD, asnOut.toByteArray()));

      // Write the ' Out Of Service' flag value.
      byte[] outOfServiceFlag = AsnUtil.toAsnBoolean(((BStatusValue)schedule.get("out")).getStatus().isDisabled());

      try
      {
        BWeeklySchedule weeklySchedule = getSchedule();
        timeScheduleMap = getTimeScheduleMap(weeklySchedule);
        specialEventMap = getSpecialEventMap(weeklySchedule);
      }
      catch (Exception e)
      {
        // Log a warning and continue or throw an error ?
        log.warning("Failed to copy schedule time lines: " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.ABORT_OTHER);
      }
      initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.OUT_OF_SERVICE, outOfServiceFlag));
    }

    BBacnetScheduleDescriptor descriptor = replaceCurrentBacnetSchedule(initialValues);
    // A schedule descriptor is successfully created. Just return a null ErrorType.
    if (descriptor != null)
    {
      BWeeklySchedule schedule = descriptor.getSchedule();
      if(schedule != null)
      {
        // Copy the weekly schedule
        updateDailySchedule(timeScheduleMap, descriptor);
        // Copy the exception schedule
        updateSpecialEvents(specialEventMap, descriptor);
      }
      return null;
    }
    else
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.ABORT_OTHER);
    }
  }

  /**
   * Update the special events in the newly created schedule
   * @param specialEventMap
   * @param descriptor
   */
  private static void updateSpecialEvents(Map<String, SpecialEventDetails> specialEventMap,
                                          BBacnetScheduleDescriptor descriptor)
  {
    BWeeklySchedule schedule = descriptor.getSchedule();
    BCompositeSchedule specialEvents = schedule.getSpecialEvents();
    Collection<SpecialEventDetails> specialEventDetails = specialEventMap.values();
    for (SpecialEventDetails details : specialEventDetails)
    {
      List<BTimeSchedule> timeSchedules = details.getTimeSchedules();
      BDailySchedule dailySchedule = new BDailySchedule();
      for (BTimeSchedule timeSchedule : timeSchedules)
      {
        timeSchedule.setEffectiveValue(descriptor.getEffectiveValueFrom(timeSchedule.getEffectiveValue()));
        dailySchedule.getDay().add(timeSchedule);
      }
      dailySchedule.setDays(details.getDaysSchedule());
      specialEvents.add(dailySchedule.getName(), dailySchedule);
    }
  }

  /**
   * Update the weekly schedule in the newly created schedule
   * @param timeScheduleMap
   * @param descriptor
   */
  private static void updateDailySchedule(Map<Integer, List<BTimeSchedule>> timeScheduleMap,
                                          BBacnetScheduleDescriptor descriptor)
  {
    BWeeklySchedule schedule = descriptor.getSchedule();
    for (int i = BAC_MONDAY; i <= BAC_SUNDAY; i++)
    {
      BDaySchedule daySchedule = schedule.get(BWeekday.make((i-1) % 7));
      List<BTimeSchedule> list = timeScheduleMap.get((i -1) % 7);
      if (list == null)
      {
        continue;
      }
      for (BTimeSchedule timeSchedule : list)
      {
        timeSchedule.setEffectiveValue(descriptor.getEffectiveValueFrom(timeSchedule.getEffectiveValue()));
        daySchedule.add(timeSchedule);
      }
    }
  }

  /**
   * Returns the special event map for the given composite schedule
   * @param weeklySchedule
   * @return
   */
  private static Map<String, SpecialEventDetails> getSpecialEventMap(BWeeklySchedule weeklySchedule)
  {
    Map<String, SpecialEventDetails> specialEventMap = new LinkedHashMap<>();
    BCompositeSchedule specialEvents = weeklySchedule.getSpecialEvents();
    SlotCursor<Property> slotCursor = specialEvents.getProperties();
    while (slotCursor.next(BDailySchedule.class))
    {
      List<BTimeSchedule> specialEventList = new LinkedList<>();
      BDailySchedule dailySchedule = (BDailySchedule) slotCursor.get();
      BDaySchedule daySchedule = dailySchedule.getDay();
      BTimeSchedule[] timeSchedules = daySchedule.getTimesInOrder();
      for (BTimeSchedule timeSchedule : timeSchedules)
      {
        specialEventList.add((BTimeSchedule) timeSchedule.newCopy(true));
      }
      BAbstractSchedule abstractSchedule = dailySchedule.getDays();
      SpecialEventDetails specialEventDetails = new SpecialEventDetails((BAbstractSchedule) abstractSchedule.newCopy(true), specialEventList);
      specialEventMap.put(dailySchedule.getName(), specialEventDetails);
      abstractSchedule.newCopy(true);
    }
    return specialEventMap;
  }

  /**
   * Returns the time schedule map for the given weekly schedule
   * @param weeklySchedule
   * @return
   */
  private static Map<Integer, List<BTimeSchedule>> getTimeScheduleMap(BWeeklySchedule weeklySchedule)
  {
    Map<Integer, List<BTimeSchedule>> timeScheduleMap = new LinkedHashMap<>();
    for (int i = BAC_MONDAY; i <= BAC_SUNDAY; i++)
    {
      List<BTimeSchedule> timeScheduleList = new ArrayList<>();
      BDaySchedule daySchedule = weeklySchedule.get(BWeekday.make((i - 1) % 7));
      BTimeSchedule[] timeSchedules = daySchedule.getTimesInOrder();
      for (BTimeSchedule timeSchedule : timeSchedules)
      {
        timeScheduleList.add((BTimeSchedule) timeSchedule.newCopy(true));
      }
      timeScheduleMap.put((i - 1), timeScheduleList);
    }
    return timeScheduleMap;
  }

  /**
   * Replace the current bacnet schedule using a new schedule that uses the passed
   * initial values.
   */
  protected BBacnetScheduleDescriptor replaceCurrentBacnetSchedule(Array<PropertyValue> initialValues)
  {
    BBacnetObjectIdentifier objectId = getObjectId();
    try
    {
      BObjectHandler objectHandler = ((BBacnetStack) BBacnetNetwork.bacnet().getBacnetComm()).getServer().getObjectHandler();
      objectHandler.setDeleteEnabled(true);
      objectHandler.setCreateEnabled(true);

      BacnetServicePrimitive deleteResponse = objectHandler.receiveRequest(
        BacnetConfirmedServiceChoice.DELETE_OBJECT,
        new DeleteObjectRequest(objectId),
        /* sourceAddress */ null);
      if (!(deleteResponse instanceof DeleteObjectAck))
      {
        if (log.isLoggable(Level.FINE))
        {
          ErrorType error = null;
          if (deleteResponse instanceof DeleteObjectError)
          {
            error = ((DeleteObjectError)deleteResponse).getError();
          }
          log.fine("When replacing a BACnet schedule, failed to delete the existing schedule" +
            "; object ID: " + objectId +
            ", error: " + error);
        }
        return null;
      }

      BacnetServicePrimitive createResponse = objectHandler.receiveRequest(
        BacnetConfirmedServiceChoice.CREATE_OBJECT,
        new CreateObjectRequest(objectId, initialValues),
        /* sourceAddress */ null);
      if (!(createResponse instanceof CreateObjectAck))
      {
        if (log.isLoggable(Level.FINE))
        {
          ErrorType error = null;
          if (createResponse instanceof CreateObjectError)
          {
            error = ((CreateObjectError)createResponse).getError();
          }
          log.fine("When replacing a BACnet schedule, failed to create a new schedule" +
            "; object ID: " + getObjectId() +
            ", error: " + error +
            ", initial values: " + initialValues);
        }
        return null;
      }

      return (BBacnetScheduleDescriptor) BBacnetNetwork.localDevice().lookupBacnetObject(objectId);
    }
    catch (Exception e)
    {
      String message = "Exception while replacing BACnet schedule with object ID " + objectId +
        "; exception: " + e;
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.WARNING, message, e);
      }
      else
      {
        log.warning(message);
      }
      return null;
    }
  }

  private void resolveTargetReferences()
  {
    SlotCursor<Property> c = getListOfObjectPropertyReferences().getProperties();
    while (c.next(BBacnetDeviceObjectPropertyReference.class))
    {
      BBacnetDeviceObjectPropertyReference dopr = (BBacnetDeviceObjectPropertyReference)c.get();
      if (dopr.isDeviceIdUsed() && !(dopr.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId())))
      {
        BBacnetObjectIdentifier deviceId = dopr.getDeviceId();
        if (deviceId.isValid() && (deviceId.getObjectType() == BBacnetObjectType.DEVICE))
        {
          if (BBacnetNetwork.bacnet().doLookupDeviceById(deviceId) == null)
          {
            try
            {
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient().whoIs(BBacnetAddress.GLOBAL_BROADCAST_ADDRESS,
                                                                                        deviceId.getInstanceNumber(),
                                                                                        deviceId.getInstanceNumber());
            }
            catch (BacnetException e)
            {
              log.warning("Unable to determine address for Schedule target reference: " + this + " target=" + deviceId + ": " + e);
            }
          }
        }
      }
    }
  }

  /**
   * Add elements to our List_Of_Object_Property_References.
   *
   * @throws RejectException
   */
  private ChangeListError addScheduleTargets(byte[] scheduleTargets)
    throws RejectException
  {
    ArrayList<BBacnetDeviceObjectPropertyReference> v = new ArrayList<>();
    int ffen = 1;
    try
    {
      // First read in the list of references to be added.
      synchronized (asnIn)
      {
        asnIn.setBuffer(scheduleTargets);
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDeviceObjectPropertyReference r = new BBacnetDeviceObjectPropertyReference();
          r.readAsn(asnIn);
          if (r.isDeviceIdUsed() &&
              !(r.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId())))
          {
            if (r.getDeviceId().getObjectType() != BBacnetObjectType.DEVICE)
            {
              return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                          new NErrorType(BBacnetErrorClass.PROPERTY,
                                                         BBacnetErrorCode.VALUE_OUT_OF_RANGE),
                                          ffen);
            }
          }
          if (isValidReference(r))
          {
            v.add(r);
          }
          else
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.DATATYPE_NOT_SUPPORTED),
                                        ffen);
          }
          ffen++;
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    ffen = 1;
    try
    {
      BLocalBacnetDevice local = BBacnetNetwork.localDevice();
      Iterator<BBacnetDeviceObjectPropertyReference> it = v.iterator();
      while (it.hasNext())
      {
        BBacnetDeviceObjectPropertyReference r = it.next();

        // For each reference that is local to this device, make sure that the
        // target point exists and is properly configured.
        if (!r.isDeviceIdUsed() || r.getDeviceId().equals(local.getObjectId()))
        {
          BIBacnetExportObject o = local.lookupBacnetObject(r.getObjectId());
          if (o == null)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.VALUE_OUT_OF_RANGE),
                                        ffen);
          }
          BObject obj = o.getObject();
          if (obj == null)
          {
            return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                        new NErrorType(BBacnetErrorClass.PROPERTY,
                                                       BBacnetErrorCode.TARGET_NOT_CONFIGURED),
                                        ffen);
          }

          // Internal Links are no longer represented in the list of target
          // references.  Internal writes may still be configured by BACnet
          // clients; they are only represented in this list, rather than
          // being also made into Links.
        }

        // External references need no further work.

        // Now add this reference to the list.
        getListOfObjectPropertyReferences().addListElement(r, BLocalBacnetDevice.getBacnetContext());
        ffen++;
      }

      // Move target reference resolution and PV write to changed() to handle
      // local modification case.
      return null;
    }
    catch (PermissionException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  ffen);
    }
    catch (Exception e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.OTHER),
                                  ffen);
    }
  }

  /**
   * Remove elements from our List_Of_Object_Property_References.
   *
   * @throws RejectException
   */
  private ChangeListError removeScheduleTargets(byte[] scheduleTargets)
    throws RejectException
  {
    int ffen = 1;
    ArrayList<BBacnetDeviceObjectPropertyReference> v = new ArrayList<>();
    try
    {
      // First read in the list of references to be added.
      synchronized (asnIn)
      {
        asnIn.setBuffer(scheduleTargets);
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetDeviceObjectPropertyReference r = new BBacnetDeviceObjectPropertyReference();
          r.readAsn(asnIn);
          // Do not need to check isValidReference(r) here, because invalid ones won't match.
          v.add(r);
          tag = asnIn.peekTag();
          ffen++;
        }
      }
    }
    catch (AsnException e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "AsnException occurred in removeScheduleTargets", e);
      }
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    // Then try to find the referenced objects & remove.
    // This is done in two steps, so we can abort properly if one
    // element is not found.  First, make sure all the references
    // exist in the list.
    for (ffen = 1; ffen <= v.size(); ffen++)
    {
      BBacnetDeviceObjectPropertyReference r = v.get(ffen - 1);
      if (!getListOfObjectPropertyReferences().contains(r))
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.SERVICES,
                                                   BBacnetErrorCode.LIST_ELEMENT_NOT_FOUND),
                                    ffen);
      }
    }

    try
    {
      // Then find the referenced objects & remove.
      Iterator<BBacnetDeviceObjectPropertyReference> it = v.iterator();
      while (it.hasNext())
      {
        BBacnetDeviceObjectPropertyReference r = it.next();

        getListOfObjectPropertyReferences().removeListElement(r, BLocalBacnetDevice.getBacnetContext());
        ffen++;
      }
      return null;
    }
    catch (PermissionException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  ffen);
    }
    catch (Exception e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.OTHER),
                                  ffen);
    }
  }

  /**
   * Are the ASN type of Referenced object and ASN Type of Schedule Type are equal?
   * @param ansTypeOfRefObj ASN Type of DOPR
   * @param asnTypeOfSchedule ASN Type of Schedule Data Type
   * @return true if equal, otherwise false
   */
  protected boolean isEqual(int ansTypeOfRefObj, int asnTypeOfSchedule)
  {
    return ansTypeOfRefObj == asnTypeOfSchedule;
  }

  /**
   * Is this a valid BACnetDeviceObjectPropertyReference for this Schedule?
   *
   * @param ref
   * @return false if invalid, true if valid or indeterminate.
   */
  private boolean isValidReference(BBacnetDeviceObjectPropertyReference ref)
  {
    PropertyInfo pi = getPropertyInfo(ref);
    // If we have a PropertyInfo, confirm the ASN type matches.  If not, we can't say so let it go.
    if (pi != null)
    {
      return isEqual(pi.getAsnType(), getAsnType());
    }
    else
    {
      return true;
    }
  }

  /**
   * Get the property info from the device object property reference.
   *
   * @param ref
   * @return
   */
  private static PropertyInfo getPropertyInfo(BBacnetDeviceObjectPropertyReference ref)
  {
    PropertyInfo pi = BBacnetNetwork.localDevice().getPropertyInfo(ref.getObjectId().getObjectType(), ref.getPropertyId());
    if (ref.isDeviceIdUsed() && !ref.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId()))
    {
      BBacnetDevice device = BBacnetNetwork.bacnet().doLookupDeviceById(ref.getDeviceId());
      if (device != null)
      {
        pi = device.getPropertyInfo(ref.getObjectId().getObjectType(), ref.getPropertyId());
      }
    }
    return pi;
  }

  private void validateReferences()
  {
    BBacnetListOf list = getListOfObjectPropertyReferences();
    BBacnetDeviceObjectPropertyReference[] refs = list.getChildren(BBacnetDeviceObjectPropertyReference.class);

    for (int i = 0; i < refs.length; i++)
    {
      BBacnetDeviceObjectPropertyReference dopr = refs[i];
      if (!isValidReference(dopr))
      {
        list.remove(dopr);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make(BIcon.std("schedule.png"), BIcon.std("badges/export.png"));

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
    out.trTitle("BacnetScheduleDescriptor", 2);
    out.prop("fatalFault", fatalFault);
    out.prop("schedule", schedule);
    out.prop("supp", supp);
    out.prop("oldId", oldId);
    out.prop("oldName", oldName);
    out.prop("duplicate", duplicate);
    out.prop("configOk", configOk());
    out.prop("isValid", isValid);
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  static BBacnetClientLayer client()
  {
    return ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BWeeklySchedule schedule;
  private BBacnetObjectIdentifier oldId = null;
  private String oldName = null;
  private boolean duplicate = false;
  private boolean configOk;
  private boolean isValid;

  final static AsnInputStream asnIn = new AsnInputStream();
  final static AsnOutputStream asnOut = new AsnOutputStream();

  private static ScheduleSupport0 supp = new ScheduleSupport4();

  static Logger log = Logger.getLogger("bacnet.server");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final int[] REQUIRED_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.PRESENT_VALUE,
      BBacnetPropertyIdentifier.EFFECTIVE_PERIOD,
      BBacnetPropertyIdentifier.SCHEDULE_DEFAULT,
      BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES,
      BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING,
      BBacnetPropertyIdentifier.STATUS_FLAGS,
      BBacnetPropertyIdentifier.RELIABILITY,
      BBacnetPropertyIdentifier.OUT_OF_SERVICE,
    };

  private static final int[] OPTIONAL_PROPS = new int[]
    {
      BBacnetPropertyIdentifier.DESCRIPTION,
      BBacnetPropertyIdentifier.WEEKLY_SCHEDULE,
      BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE
    };

  @Override
  public int[] getPropertyList()
  {
    return BacnetPropertyList.makePropertyList(REQUIRED_PROPS, OPTIONAL_PROPS);
  }

  private static final Comparator<Object> specialEventBnIdxComparator = new Comparator<Object>()
  {
    @Override
    public int compare(Object o1, Object o2)
    {
      if ((o1 == null) || (o2 == null))
      {
        throw new NullPointerException();
      }
      if ((o1 instanceof BDailySchedule) && (o2 instanceof BDailySchedule))
      {
        BDailySchedule ds1 = (BDailySchedule)o1;
        BDailySchedule ds2 = (BDailySchedule)o2;
        BInteger bnIdx1 = (BInteger)ds1.get(ScheduleSupport0.BACNET_IDX);
        if (bnIdx1 == null)
        {
          bnIdx1 = NO_BN_IDX;
        }

        BInteger bnIdx2 = (BInteger)ds2.get(ScheduleSupport0.BACNET_IDX);
        if (bnIdx2 == null)
        {
          bnIdx2 = NO_BN_IDX;
        }

        return bnIdx1.getInt() - bnIdx2.getInt();
      }
      throw new ClassCastException("Cannot compare " + o1.getClass() + " and " + o2.getClass());
    }
  };

  private static final BInteger NO_BN_IDX = BInteger.make(-1);
  protected static final String LAST_EFFECTIVE_VALUE = "leValue";
}
