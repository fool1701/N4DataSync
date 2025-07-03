/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.bacnet.BBacnetNetwork;
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
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.schedule.BControlSchedule;
import javax.baja.schedule.BStringSchedule;
import javax.baja.schedule.BWeeklySchedule;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
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
 * BBacnetStringScheduleDescriptor exposes a Niagara schedule to Bacnet.
 *
 * @author Craig Gemmill on 18 Aug 03
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "schedule:StringSchedule"
  )
)
public class BBacnetStringScheduleDescriptor
  extends BBacnetScheduleDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetStringScheduleDescriptor(3632174478)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetStringScheduleDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BBacnetStringScheduleDescriptor()
  {
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
    BStringSchedule sched = (BStringSchedule)getSchedule();
    if (sched != null && sched.getEffective().isEffective(BAbsTime.now()))
    {
      BStatusString out = sched.getOut();
      byte[] writeVal = null;
      if (out.getStatus().isNull())
      {
        writeVal = AsnUtil.toAsnNull();
      }
      else
      {
        writeVal = AsnUtil.toAsnCharacterString(out.getValue());
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
    return sched instanceof BStringSchedule;
  }

  /**
   * Get the ASN type to use in encoding the TimeValues for this schedule.
   */
  @Override
  int getAsnType()
  {
    return ASN_CHARACTER_STRING;
  }

  /**
   * Get the output property to which we link.
   *
   * @return the output property for this schedule.
   */
  @Override
  final Property getScheduleOutputProperty()
  {
    return BStringSchedule.out;
  }

  /**
   * Translate the status value to something appropriate for the changed type
   * @param statusValue
   * @return
   */
  @Override
  BStatusValue getEffectiveValueFrom(BStatusValue statusValue)
  {
    BStatusString ret = new BStatusString("", BStatus.nullStatus);
    if (statusValue instanceof BStatusNumeric)
    {
      ret.setValue(Double.toString(((BStatusNumeric) statusValue).getNumeric()));
    }
    else if (statusValue instanceof BStatusEnum)
    {
      ret.setValue(Integer.toString(((BStatusEnum) statusValue).getEnum().getOrdinal()));
    }
    else if (statusValue instanceof BStatusBoolean)
    {
      ret.setValue("");
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
    BStringSchedule sched = (BStringSchedule)getSchedule();
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
        BStatusString out;
        BAbsTime currentTime = BAbsTime.now();
        if (!sched.isEffective(currentTime) && getLastEffectiveValue() != null)
        {
          out = (BStatusString) getLastEffectiveValue();
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
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(out.getValue()));
        }

      case BBacnetPropertyIdentifier.SCHEDULE_DEFAULT:
        BStatusString sf = (BStatusString)sched.getDefaultOutput();
        if (sf.getStatus().isNull())
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(sf.getValue()));
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
  protected ErrorType writeProperty(int pId,
                                    int ndx,
                                    byte[] val,
                                    int pri)
    throws BacnetException
  {
    BStringSchedule sched = (BStringSchedule)getSchedule();
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
              switch (asnIn.peekApplicationTag())
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

                case ASN_CHARACTER_STRING:
                  BStatusString inval = (BStatusString)sched.getIn().newCopy();
                  inval.setValue(asnIn.readCharacterString());
                  inval.setStatusNull(false);
                  sched.set(BStringSchedule.in, inval, BLocalBacnetDevice.getBacnetContext());
                  return null;

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
   * Write the schedule default value for string type schedule
   * @param asnInputStream
   * @return null if no error, otherwise error type
   */
  @Override
  protected ErrorType doWriteScheduleDefaultValue(AsnInputStream asnInputStream, int applicationTag)
    throws Exception
  {
    BStringSchedule sched = (BStringSchedule) getSchedule();
    switch (applicationTag)
    {
      case ASN_CHARACTER_STRING:
        BStatusString defval = (BStatusString)sched.getDefaultOutput().newCopy();
        defval.setValue(asnIn.readCharacterString());
        defval.setStatusNull(false);
        sched.set(BControlSchedule.defaultOutput, defval, BLocalBacnetDevice.getBacnetContext());
        return null;

      default:
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.INVALID_DATA_TYPE);
    }
  }
}
