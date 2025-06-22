/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.DataTypeNotSupportedException;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.schedule.BBooleanSchedule;
import javax.baja.schedule.BControlSchedule;
import javax.baja.schedule.BWeeklySchedule;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyValue;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.stack.DeviceRegistry;

/**
 * BBacnetBooleanScheduleDescriptor exposes a Niagara schedule to Bacnet.
 *
 * @author Craig Gemmill on 18 Aug 03
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "schedule:BooleanSchedule"
  )
)
@NiagaraProperty(
  name = "scheduleDataType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BOOLEAN_IDX, BOOL_DATA_TYPE_RANGE)"
)
public class BBacnetBooleanScheduleDescriptor
  extends BBacnetScheduleDescriptor
{
  public static final int BOOLEAN_IDX = 0;
  public static final int ENUMERATED_IDX = 1;

  public static final BEnumRange BOOL_DATA_TYPE_RANGE = BEnumRange.make(new String[]
    {
      bacnetLexicon.get("BacnetBooleanScheduleDescriptor.boolean"),
      bacnetLexicon.get("BacnetBooleanScheduleDescriptor.enumerated")
    });

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetBooleanScheduleDescriptor(204273136)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scheduleDataType"

  /**
   * Slot for the {@code scheduleDataType} property.
   * @see #getScheduleDataType
   * @see #setScheduleDataType
   */
  public static final Property scheduleDataType = newProperty(0, BDynamicEnum.make(BOOLEAN_IDX, BOOL_DATA_TYPE_RANGE), null);

  /**
   * Get the {@code scheduleDataType} property.
   * @see #scheduleDataType
   */
  public BEnum getScheduleDataType() { return (BEnum)get(scheduleDataType); }

  /**
   * Set the {@code scheduleDataType} property.
   * @see #scheduleDataType
   */
  public void setScheduleDataType(BEnum v) { set(scheduleDataType, v, null); }

  //endregion Property "scheduleDataType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBooleanScheduleDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private boolean isAsnBoolean()
  {
    return getScheduleDataType().getOrdinal() == BOOLEAN_IDX;
  }

  /**
   * Constructor.
   */
  public BBacnetBooleanScheduleDescriptor()
  {
    setScheduleDataType(BDynamicEnum.make(getScheduleDataType().getOrdinal(), BOOL_DATA_TYPE_RANGE));
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Write the present value of the schedule to non-Present_Value
   * target properties, and to any external targets.
   */
  @Override
  public void doWritePresentValue()
  {
    BBooleanSchedule sched = (BBooleanSchedule)getSchedule();
    if (sched != null && sched.getEffective().isEffective(BAbsTime.now()))
    {
      BStatusBoolean out = sched.getOut();
      byte[] booleanWriteVal = AsnUtil.toAsnNull();
      byte[] enumeratedWriteVal = AsnUtil.toAsnNull();
      if (!out.getStatus().isNull())
      {
        booleanWriteVal = AsnUtil.toAsnBoolean(out.getValue());
        enumeratedWriteVal = AsnUtil.toAsnEnumerated(out.getValue() ? 1 : 0);
      }
      BBacnetAddress addr;
      SlotCursor<Property> c = getListOfObjectPropertyReferences().getProperties();
      while (c.next(BBacnetDeviceObjectPropertyReference.class))
      {
        BBacnetDeviceObjectPropertyReference ref = (BBacnetDeviceObjectPropertyReference)c.get();
        byte[] writeVal = booleanWriteVal;
        PropertyInfo pi = BBacnetNetwork.localDevice().getPropertyInfo(ref.getObjectId().getObjectType(), ref.getPropertyId());
        if (ref.isDeviceIdUsed() && !(ref.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId())))
        {
          // determine which writeVal to use
          BBacnetDevice device = BBacnetNetwork.bacnet().doLookupDeviceById(ref.getDeviceId());
          if (device != null)
          {
            pi = device.getPropertyInfo(ref.getObjectId().getObjectType(), ref.getPropertyId());
          }
          if (pi != null)
          {
            switch (pi.getAsnType())
            {
              case ASN_ENUMERATED:
                writeVal = enumeratedWriteVal;
                break;

              default:
                // use booleanWriteVal
                break;
            }
          }
          addr = DeviceRegistry.getDeviceAddress(ref.getDeviceId());
          if (addr == null)
          {
            try
            {
              BacnetDescriptorUtil.parseLogDeviceObjectProperty(null, ref);
            }
            catch (Exception e)
            {
              log.warning("BacnetException parsing device object property " + ref + ": " + e);
            }
            addr = DeviceRegistry.getDeviceAddress(ref.getDeviceId());
          }

          if (addr != null)
          {
            try
            {
              client().writeProperty(addr,
                                     ref.getObjectId(),
                                     ref.getPropertyId(),
                                     ref.getPropertyArrayIndex(),
                                     writeVal,
                                     getPriorityForWriting());
            }
            catch (BacnetException e)
            {
              log.warning("BacnetException writing schedule output to " + ref + ": " + e);
            }
          }
          else
          {
            log.warning("Unable to write Schedule output " + out + " to " + ref + ": unable to resolve device address");
          }
        }
        else
        {
          // isDeviceIdUsed()
          BIBacnetExportObject o = BBacnetNetwork.localDevice().lookupBacnetObject(ref.getObjectId());
          if (pi != null)
          {
            switch (pi.getAsnType())
            {
              case ASN_ENUMERATED:
                writeVal = enumeratedWriteVal;
                break;

              default:
                // use booleanWriteVal
                break;
            }
          }
          try
          {
            ErrorType err = o.writeProperty(new NBacnetPropertyValue(ref.getPropertyId(), ref.getPropertyArrayIndex(), writeVal, getPriorityForWriting()));
            if (err != null)
            {
              throw new ErrorException(err);
            }
          }
          catch (Exception e)
          {
            log.warning("Unable to write schedule output " + out + " from " + this + " to local object " + ref + ": " + e);
          }
        }
      }

      setLastEffectiveValue((BStatusValue) out.newCopy());
    }
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override point for BBacnetScheduleDescriptors to enforce
   * type rules for their exposed schedules.
   *
   * @param sched the exposed schedule
   * @return true if the Niagara schedule type is legal for this schedule type.
   */
  @Override
  final boolean isScheduleTypeLegal(BWeeklySchedule sched)
  {
    return sched instanceof BBooleanSchedule;
  }

  @Override
  protected boolean isEqual(int ansTypeOfRefObj, int asnTypeOfSchedule)
  {
    if (ansTypeOfRefObj == asnTypeOfSchedule)
    {
      return true;
    }

    if (ansTypeOfRefObj == ASN_ENUMERATED)
    {
      this.setScheduleDataType(BDynamicEnum.make(ENUMERATED_IDX,
                                                 BOOL_DATA_TYPE_RANGE));
      return true;

    }
    else if (ansTypeOfRefObj == ASN_BOOLEAN)
    {
      this.setScheduleDataType(BDynamicEnum.make(BOOLEAN_IDX,
                                                 BOOL_DATA_TYPE_RANGE));
      return true;
    }

    return false;
  }

  /**
   * Get the ASN type to use in encoding the TimeValues for this schedule.
   */
  @Override
  int getAsnType()
  {
    switch (getScheduleDataType().getOrdinal())
    {
      case BOOLEAN_IDX:
        return ASN_BOOLEAN;

      case ENUMERATED_IDX:
        return ASN_ENUMERATED;

      default:
        throw new IllegalStateException("Invalid Schedule Data Type for " + this + ":" + getScheduleDataType().getOrdinal());
    }
  }

  private byte[] encodeToAsn(boolean value)
  {
    if (isAsnBoolean())
    {
      return AsnUtil.toAsnBoolean(value);
    }
    else
    {
      return AsnUtil.toAsnEnumerated(value);
    }
  }

  /**
   * Get the output property to which we link.
   *
   * @return the output property for this schedule.
   */
  @Override
  final Property getScheduleOutputProperty()
  {
    return BBooleanSchedule.out;
  }

  /**
   * Translate the status value to something appropriate for the changed type
   * @param statusValue
   * @return
   */
  @Override
  BStatusValue getEffectiveValueFrom(BStatusValue statusValue)
  {
    BStatusBoolean ret = new BStatusBoolean(false, BStatus.nullStatus);
    if (statusValue instanceof BStatusNumeric)
    {
      ret.setValue(((BStatusNumeric) statusValue).getValue() > 0);
    }
    else if (statusValue instanceof BStatusEnum)
    {
      ret.setValue(((BStatusEnum) statusValue).getValue().getOrdinal() == 0);
    }
    else if (statusValue instanceof BStatusBoolean)
    {
      ret = (BStatusBoolean) statusValue.newCopy(true);
    }

    return ret;
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
  @Override
  protected PropertyValue readProperty(int pId, int ndx)
  {
    BBooleanSchedule sched = (BBooleanSchedule)getSchedule();
    if (sched == null)
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
      case BBacnetPropertyIdentifier.PRESENT_VALUE:
        BAbsTime currentTime = BAbsTime.now();
        BStatusBoolean out;
        if (!sched.isEffective(currentTime) && getLastEffectiveValue() != null)
        {
          out = (BStatusBoolean) getLastEffectiveValue();
        }
        else
        {
          out = sched.getOut();
        }
        if (out.getStatus().isNull())
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, encodeToAsn(out.getValue()));
        }

      case BBacnetPropertyIdentifier.SCHEDULE_DEFAULT:
        BStatusBoolean sb = (BStatusBoolean)sched.getDefaultOutput();
        if (sb.getStatus().isNull())
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, encodeToAsn(sb.getValue()));
        }

      default:
        return super.readProperty(pId, ndx);
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
  @Override
  @SuppressWarnings("fallthrough")
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    BBooleanSchedule sched = (BBooleanSchedule)getSchedule();
    if (sched == null)
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
      synchronized (asnIn)
      {
        asnIn.setBuffer(val);
        switch (pId)
        {
          case BBacnetPropertyIdentifier.PRESENT_VALUE:
            if (((BStatusValue)sched.get("out")).getStatus().isDisabled())
            {
              int applicationTag = asnIn.peekApplicationTag();
              switch (applicationTag)
              {
                case ASN_NULL:
                  // If I set the input null, the schedule's schedule will override.
                  // Set the input to non-null to allow me to override, and then
                  // set the output null.
                  // FIXX: This should be done with an atomic transaction, which
                  //       does not currently exist in the framework.
                  sched.getIn().set(BStatusValue.status,
                                    BStatus.make(sched.getIn().getStatus(), BStatus.NULL, false),
                                    BLocalBacnetDevice.getBacnetContext());
                  sched.getOut().set(BStatusValue.status,
                                     BStatus.make(sched.getOut().getStatus(), BStatus.NULL, true),
                                     BLocalBacnetDevice.getBacnetContext());
                  return null;

                case ASN_BOOLEAN:
                  if (isAsnBoolean())
                  {
                    BStatusBoolean inval = (BStatusBoolean)sched.getIn().newCopy();
                    inval.setValue(asnIn.readBoolean());
                    inval.setStatusNull(false);
                    sched.set(BBooleanSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                    return null;
                  }

                case ASN_ENUMERATED:
                  if (!isAsnBoolean())
                  {
                    BStatusBoolean inval = (BStatusBoolean)sched.getIn().newCopy();
                    inval.setValue(AsnUtil.fromOnlyBinaryPv(asnIn));
                    inval.setStatusNull(false);
                    sched.set(BBooleanSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                    return null;
                  }
                  break;

                default:
                  return new NErrorType(BBacnetErrorClass.PROPERTY,
                                        BBacnetErrorCode.INVALID_DATA_TYPE);
              }
            }
            else
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.WRITE_ACCESS_DENIED);
            }
            return null;

          default:
            return super.writeProperty(pId, ndx, val, pri);
        }
      }
    }
    catch (OutOfRangeException oore)
    {
      log.warning("Value out of range writing property " + pId + " in object " + getObjectId() + ": " + oore);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (DataTypeNotSupportedException dtnse)
    {
      log.warning("Datatype not supported writing property " + pId + " in object " + getObjectId() + ": " + dtnse);
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
    catch (Exception e)
    {
      log.warning("Exception writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.OTHER);
    }
  }

  /**
   * Write the schedule default value for boolean type schedule
   * @param asnInputStream
   * @return null if no error, otherwise errorcode
   */
  @Override
  @SuppressWarnings("fallthrough")
  protected ErrorType doWriteScheduleDefaultValue(AsnInputStream asnInputStream, int applicationTag)
    throws Exception
  {
    BBooleanSchedule sched = (BBooleanSchedule) getSchedule();
    switch (applicationTag)
    {
      case ASN_BOOLEAN:
        if (isAsnBoolean())
        {
          BStatusBoolean defval = (BStatusBoolean)sched.getDefaultOutput().newCopy();
          defval.setValue(asnIn.readBoolean());
          defval.setStatusNull(false);
          sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
          return null;
        }

      case ASN_ENUMERATED:
        if (!isAsnBoolean())
        {
          BStatusBoolean defval = (BStatusBoolean)sched.getDefaultOutput().newCopy();
          defval.setValue(AsnUtil.fromOnlyBinaryPv(asnIn));
          defval.setStatusNull(false);
          sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
          return null;
        }

      default:
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.INVALID_DATA_TYPE);
    }
  }
}
