/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetAddress;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.schedule.BControlSchedule;
import javax.baja.schedule.BEnumSchedule;
import javax.baja.schedule.BWeeklySchedule;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
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
 * BBacnetEnumScheduleDescriptor exposes a Niagara schedule to Bacnet.
 *
 * @author Craig Gemmill on 18 Aug 03
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "schedule:EnumSchedule"
  )
)
@NiagaraProperty(
  name = "scheduleDataType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(LOCAL_UNSIGNED, ENUM_DATA_TYPE_RANGE)"
)
public class BBacnetEnumScheduleDescriptor
  extends BBacnetScheduleDescriptor
{

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static final int LOCAL_UNSIGNED = 0;
  private static final int LOCAL_ENUMERATED = 1;
  private static final int LOCAL_INTEGER = 2;

  private static final BEnumRange ENUM_DATA_TYPE_RANGE = BEnumRange.make(new String[]
    {
      AsnUtil.getAsnTypeName(BacnetConst.ASN_UNSIGNED),
      AsnUtil.getAsnTypeName(BacnetConst.ASN_ENUMERATED),
      AsnUtil.getAsnTypeName(BacnetConst.ASN_INTEGER)
    });

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetEnumScheduleDescriptor(217065082)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scheduleDataType"

  /**
   * Slot for the {@code scheduleDataType} property.
   * @see #getScheduleDataType
   * @see #setScheduleDataType
   */
  public static final Property scheduleDataType = newProperty(0, BDynamicEnum.make(LOCAL_UNSIGNED, ENUM_DATA_TYPE_RANGE), null);

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
  public static final Type TYPE = Sys.loadType(BBacnetEnumScheduleDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BBacnetEnumScheduleDescriptor()
  {
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  @Override
  public void started()
    throws Exception
  {
    super.started();
    setScheduleDataType(BDynamicEnum.make(getScheduleDataType().getOrdinal(), ENUM_DATA_TYPE_RANGE));
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  private boolean isUnsigned()
  {
    return getScheduleDataType().getOrdinal() == LOCAL_UNSIGNED;
  }

  private boolean isEnumerated()
  {
    return getScheduleDataType().getOrdinal() == LOCAL_ENUMERATED;
  }

  private boolean isInteger()
  {
    return getScheduleDataType().getOrdinal() == LOCAL_INTEGER;
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
    BEnumSchedule sched = (BEnumSchedule)getSchedule();
    byte[] writeVal;
    if (sched != null && sched.getEffective().isEffective(BAbsTime.now()))
    {
      BStatusEnum out = sched.getOut();
      writeVal = AsnUtil.toAsnNull();
      if (!out.getStatus().isNull())
      {
        switch (getScheduleDataType().getOrdinal())
        {
          case LOCAL_UNSIGNED:
            writeVal = AsnUtil.toAsnUnsigned(out.getValue().getOrdinal());
            break;

          case LOCAL_ENUMERATED:
            writeVal = AsnUtil.toAsnEnumerated(out.getValue().getOrdinal());
            break;

          case LOCAL_INTEGER:
            writeVal = AsnUtil.toAsnInteger(out.getValue().getOrdinal());
            break;

          default:
            throw new IllegalStateException("Invalid Schedule Data Type for " + this + ":" + getScheduleDataType().getOrdinal());
        }
      }
      BBacnetAddress addr;
      SlotCursor<Property> c = getListOfObjectPropertyReferences().getProperties();
      while (c.next(BBacnetDeviceObjectPropertyReference.class))
      {
        BBacnetDeviceObjectPropertyReference ref = (BBacnetDeviceObjectPropertyReference)c.get();
        if (ref.isDeviceIdUsed() && !(ref.getDeviceId().equals(BBacnetNetwork.localDevice().getObjectId())))
        {
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
          BIBacnetExportObject o = BBacnetNetwork.localDevice().lookupBacnetObject(ref.getObjectId());
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
//  Overrides
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
    return sched instanceof BEnumSchedule;
  }

  @Override
  protected boolean isEqual(int ansTypeOfRefObj, int asnTypeOfSchedule)
  {
    if (ansTypeOfRefObj == asnTypeOfSchedule)
    {
      return true;
    }
    if (ansTypeOfRefObj == ASN_UNSIGNED)
    {
      this.setScheduleDataType(BDynamicEnum.make(ENUM_DATA_TYPE_RANGE.getOrdinals()[0],
                                                 ENUM_DATA_TYPE_RANGE));
      return true;
    }
    else if (ansTypeOfRefObj == ASN_ENUMERATED)
    {
      this.setScheduleDataType(BDynamicEnum.make(ENUM_DATA_TYPE_RANGE.getOrdinals()[1],
                                                 ENUM_DATA_TYPE_RANGE));
      return true;
    }
    else if (ansTypeOfRefObj == ASN_INTEGER)
    {
      this.setScheduleDataType(BDynamicEnum.make(ENUM_DATA_TYPE_RANGE.getOrdinals()[2],
                                                 ENUM_DATA_TYPE_RANGE));
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
      case LOCAL_UNSIGNED:
        return ASN_UNSIGNED;

      case LOCAL_ENUMERATED:
        return ASN_ENUMERATED;

      case LOCAL_INTEGER:
        return ASN_INTEGER;

      default:
        throw new IllegalStateException("Invalid Schedule Data Type for " + this + ":" + getScheduleDataType().getOrdinal());
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
    return BEnumSchedule.out;
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
    BEnumSchedule sched = (BEnumSchedule)getSchedule();
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
        BStatusEnum out;
        BAbsTime currentTime = BAbsTime.now();
        if (!sched.isEffective(currentTime) && getLastEffectiveValue() != null)
        {
          out = (BStatusEnum) getLastEffectiveValue();
        }
        else
        {
          out = sched.getOut();
        }
        return new NReadPropertyResult(pId, ndx, encodeAsn(out));

      case BBacnetPropertyIdentifier.SCHEDULE_DEFAULT:
        BStatusEnum sms = (BStatusEnum)sched.getDefaultOutput();
        return new NReadPropertyResult(pId, ndx, encodeAsn(sms));

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
    BEnumSchedule sched = (BEnumSchedule)getSchedule();
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
              BStatusEnum inval = (BStatusEnum)sched.getIn().newCopy();
              BDynamicEnum ms = inval.getValue();
              switch (asnIn.peekApplicationTag())
              {
                case ASN_NULL:
                  // If I set the input null, the schedule's schedule will override.
                  // Since input should already be non-null when outOfService, try
                  // just setting output to null.
                  sched.getOut().set(BStatusValue.status,
                                     BStatus.make(sched.getOut().getStatus(), BStatus.NULL, true),
                                     BLocalBacnetDevice.getBacnetContext());
                  return null;

                case ASN_UNSIGNED:
                  if (isUnsigned())
                  {
                    inval.setValue(BDynamicEnum.make(asnIn.readUnsignedInt(), ms.getRange()));
                    sched.set(BEnumSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                    return null;
                  }

                case ASN_INTEGER:
                  if (isInteger())
                  {
                    inval.setValue(BDynamicEnum.make(asnIn.readInteger(), ms.getRange()));
                    sched.set(BEnumSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                    return null;
                  }

                case ASN_ENUMERATED:
                  if (isEnumerated())
                  {
                    inval.setValue(BDynamicEnum.make(asnIn.readEnumerated(), ms.getRange()));
                    sched.set(BEnumSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                    return null;
                  }
              }

              // If we haven't returned yet we are out of service, but the
              // data type didn't match.
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_DATA_TYPE);
            }
            else
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.WRITE_ACCESS_DENIED);
            }

          default:
            return super.writeProperty(pId, ndx, val, pri);
        }
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
    catch (Exception e)
    {
      log.warning("Exception writing property " + pId + " in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.OTHER);
    }
  }

  /**
   * Write the schedule default value for enum type schedule
   * @param asnInputStream
   * @return null if no error, otherwise error code
   */
  @Override
  @SuppressWarnings("fallthrough")
  protected ErrorType doWriteScheduleDefaultValue(AsnInputStream asnInputStream, int applicationTag)
    throws Exception
  {
    BEnumSchedule sched = (BEnumSchedule) getSchedule();
    BStatusEnum defval = (BStatusEnum)sched.getDefaultOutput().newCopy();
    BDynamicEnum ms = defval.getValue();
    switch (applicationTag)
    {
      case ASN_UNSIGNED:
        if (isUnsigned())
        {
          defval.setValue(BDynamicEnum.make(asnIn.readUnsignedInt(), ms.getRange()));
          defval.setStatusNull(false);
          sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
          return null;
        }

      case ASN_INTEGER:
        if (isInteger())
        {
          defval.setValue(BDynamicEnum.make(asnIn.readInteger(), ms.getRange()));
          defval.setStatusNull(false);
          sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
          return null;
        }

      case ASN_ENUMERATED:
        if (isEnumerated())
        {
          defval.setValue(BDynamicEnum.make(asnIn.readEnumerated(), ms.getRange()));
          defval.setStatusNull(false);
          sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
          return null;
        }
    }

    // If we haven't returned here, the value for Schedule_Default
    // was the wrong data type.
    return new NErrorType(BBacnetErrorClass.PROPERTY,
                          BBacnetErrorCode.INVALID_DATA_TYPE);
  }

  /**
   * Translate the status value to something appropriate for the changed type
   * @param statusValue
   * @return
   */
  @Override
  BStatusValue getEffectiveValueFrom(BStatusValue statusValue)
  {
    BEnumSchedule schedule = (BEnumSchedule) getSchedule();
    BEnumRange range = (BEnumRange) schedule.getFacets().get(BFacets.RANGE);
    BStatusEnum ret = new BStatusEnum(BDynamicEnum.make(0, range), BStatus.nullStatus);

    if (statusValue instanceof BStatusEnum)
    {
      ret = (BStatusEnum) statusValue.newCopy(true);
    }
    else
    {
      ret.setValue(BDynamicEnum.make(0, range));
    }

    return ret;
  }

  private byte[] encodeAsn(BStatusEnum se)
  {
    if (se.getStatus().isNull())
    {
      return AsnUtil.toAsnNull();
    }
    int ordinal = se.getValue().getOrdinal();
    switch (getScheduleDataType().getOrdinal())
    {
      case LOCAL_UNSIGNED:
        return AsnUtil.toAsnUnsigned(ordinal);

      case LOCAL_ENUMERATED:
        return AsnUtil.toAsnEnumerated(ordinal);

      case LOCAL_INTEGER:
        return AsnUtil.toAsnInteger(ordinal);

      default:
        throw new IllegalStateException("Invalid Schedule Data Type for " + this + ":" + getScheduleDataType().getOrdinal());
    }
  }
}
