/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.schedule.BDateRangeSchedule;
import javax.baja.schedule.BNumericSchedule;
import javax.baja.schedule.BWeeklySchedule;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BLink;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.schedule.ScheduleSupport4;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.server.object.BObjectHandler;

/**
 * BBacnetDynamicScheduleDescriptor represents an intermediate descriptor that
 * gets created during dynamic schedule object creation in bacnet when actual type
 * of the schedule to be created cannot be inferred using the initial values.
 * Its scheduleOrd does not point to any concrete schedule object .
 * <p>
 * When its write property is called for properties that can be helpful in determining
 * the datatype, then this descriptor gets deleted and the specific typed
 * Schedule descriptor is created in its place.
 * <p>
 * @author by Mitali L Joshi on 7/7/2017.
 * @since Niagara 4.3
 */
@NiagaraType
@NiagaraProperty(
  name = "outOfService",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
@NiagaraProperty(
  name = "effective",
  type = "BDateRangeSchedule",
  defaultValue = "new BDateRangeSchedule()",
  flags = Flags.HIDDEN | Flags.OPERATOR
)
public class BBacnetDynamicScheduleDescriptor
  extends BBacnetScheduleDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetDynamicScheduleDescriptor(725005096)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "outOfService"

  /**
   * Slot for the {@code outOfService} property.
   * @see #getOutOfService
   * @see #setOutOfService
   */
  public static final Property outOfService = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code outOfService} property.
   * @see #outOfService
   */
  public boolean getOutOfService() { return getBoolean(outOfService); }

  /**
   * Set the {@code outOfService} property.
   * @see #outOfService
   */
  public void setOutOfService(boolean v) { setBoolean(outOfService, v, null); }

  //endregion Property "outOfService"

  //region Property "effective"

  /**
   * Slot for the {@code effective} property.
   * @see #getEffective
   * @see #setEffective
   */
  public static final Property effective = newProperty(Flags.HIDDEN | Flags.OPERATOR, new BDateRangeSchedule(), null);

  /**
   * Get the {@code effective} property.
   * @see #effective
   */
  public BDateRangeSchedule getEffective() { return (BDateRangeSchedule)get(effective); }

  /**
   * Set the {@code effective} property.
   * @see #effective
   */
  public void setEffective(BDateRangeSchedule v) { set(effective, v, null); }

  //endregion Property "effective"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDynamicScheduleDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BBacnetDynamicScheduleDescriptor()
  {
  }

  /**
   * Translate the status value to something appropriate for the changed type
   * @param statusValue
   * @return
   */
  @Override
  BStatusValue getEffectiveValueFrom(BStatusValue statusValue)
  {
    return new BStatusNumeric(0, BStatus.nullStatus);
  }

  /**
   * Validate the schedule's configuration.  For schedules, an event
   * cannot have a value with a null status.
   */
  @Override
  protected void validate()
  {
    markConfigurationError("Unknown schedule type");
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
  @Override
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.SCHEDULE_DEFAULT:
      case BBacnetPropertyIdentifier.PRESENT_VALUE:
      case BBacnetPropertyIdentifier.WEEKLY_SCHEDULE:
      case BBacnetPropertyIdentifier.EXCEPTION_SCHEDULE:
      case BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES:
        BObjectHandler boj = ((BBacnetStack) BBacnetNetwork.bacnet().getBacnetComm()).getServer().getObjectHandler();
        boj.setDeleteEnabled(true);
        boj.setCreateEnabled(true);
        try
        {
          Array<PropertyValue> initialValues = new Array<>(PropertyValue.class);
          initialValues.add(new NBacnetPropertyValue(pId, ndx, val, pri));
          initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.OBJECT_NAME, AsnUtil.toAsnCharacterString(getObjectName())));
          initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.DESCRIPTION, AsnUtil.toAsnCharacterString(getDescription())));
          initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, AsnUtil.toAsnUnsigned(getPriorityForWriting())));
          initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.OUT_OF_SERVICE, AsnUtil.toAsnBoolean(getOutOfService())));
          if (getSchedule() != null)
          {
            asnOut.reset();
            supp.encodeDateRange(getSchedule().getEffective(), asnOut);
            initialValues.add(new NBacnetPropertyValue(BBacnetPropertyIdentifier.EFFECTIVE_PERIOD, asnOut.toByteArray()));
          }

          BBacnetScheduleDescriptor descriptor = replaceCurrentBacnetSchedule(initialValues);
          if (descriptor == null)
          {
            logger.warning("Exception while replacing dynamic Schedule object while writing: null");
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.ABORT_OTHER);
          }
        }
        catch (Exception e)
        {
          logger.warning("Exception while replacing dynamic Schedule object while writing: " + e);
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.ABORT_OTHER);
        }
        return null;

      case BBacnetPropertyIdentifier.EFFECTIVE_PERIOD:
        BWeeklySchedule dynamicSchedule = getSchedule();
        if (dynamicSchedule == null)
        {
          logger.warning("Cannot write effective period to dynamic schedule: null");
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.ABORT_OTHER);
        }
        asnIn.setBuffer(val);
        dynamicSchedule.set(BWeeklySchedule.effective,
                            supp.decodeDateRange(asnIn),
                            BLocalBacnetDevice.getBacnetContext());
        return null;

      case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
        // First check for input link.  If one exists then no writing!
        Property pIn = getSchedule().loadSlots().getProperty("in");
        BLink[] inLinks = getSchedule().getLinks(pIn);
        if (inLinks.length > 0)
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }
        // Ok, try to set input from outOfService
        setOutOfService(AsnUtil.fromOnlyAsnBoolean(val));
        setFlags(outOfService, (getFlags(outOfService) | Flags.READONLY));
        return null;

      default:
        return super.writeProperty(pId, ndx, val, pri);
    }
  }

  /**
   * Write the present value of the schedule to non-Present_Value
   * target properties, and to any external targets.
   */
  @Override
  public void doWritePresentValue()
  {
  }

  /**
   * WRite the schedule default values
   * @param asnInputStream
   * @param applicationTag
   * @return error
   * @throws Exception
   */
  @Override
  protected ErrorType doWriteScheduleDefaultValue(AsnInputStream asnInputStream, int applicationTag)
    throws Exception
  {
    // Unlikely scenario. But the error message needs to be confirmed. Or probably we dont need one
    return new NErrorType(BBacnetErrorClass.OBJECT,
                          BBacnetErrorCode.UNKNOWN_OBJECT);
  }

  /**
   * Get the value of a property.
   *
   * @param pId the int containing property id
   * @param ndx  index.
   * @return a PropertyValue containing the encoded value or the error.
   */
  @Override
  public PropertyValue readProperty(int pId, int ndx)
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.OUT_OF_SERVICE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBoolean(getOutOfService()));

      default:
        return super.readProperty(pId,ndx);
    }
  }

  /**
   * Get the ASN type to use in encoding the TimeValues for this schedule.
   */
  @Override
  int getAsnType()
  {
    return ASN_REAL;
  }

  /**
   * Get the output property to which we link.
   *
   * @return the output property for this schedule.
   */
  @Override
  Property getScheduleOutputProperty()
  {
    return BNumericSchedule.out;
  }

  static Logger logger = Logger.getLogger("bacnet.server.schedule");

  // As AMEV is for version > 4
  ScheduleSupport4 supp = new ScheduleSupport4();
}
