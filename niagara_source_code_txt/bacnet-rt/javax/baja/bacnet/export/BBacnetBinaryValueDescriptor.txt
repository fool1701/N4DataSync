/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.offnormal.BBooleanChangeOfStateAlgorithm;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BBooleanPoint;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatusBoolean;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetBinaryValueDescriptor exposes a ControlPoint as a Bacnet
 * Binary Value Object.
 *
 * @author Craig Gemmill on 11 Aug 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:BooleanPoint"
  )
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetBinaryValueDescriptor
  extends BBacnetBinaryPointDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetBinaryValueDescriptor(809499929)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinaryValueDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Deprecated Methods
////////////////////////////////////////////////////////////////

  /**
   * @deprecated BacnetValue is no longer necessary since out-of-service
   * changes will be written directly to the point via
   * the BOutOfServiceExt.
   */
  @Deprecated
  public BStatusBoolean getBacnetValue()
  {
    throw new BajaRuntimeException("Method getBacnetValue() is deprecated!");
  }

  /**
   * @deprecated BacnetValue is no longer necessary since out-of-service
   * changes will be written directly to the point via
   * the BOutOfServiceExt.
   */
  @Deprecated
  public void setBacnetValue(BStatusBoolean v)
  {
    throw new BajaRuntimeException("Method setBacnetValue() is deprecated!");
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

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
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.BINARY_VALUE);
    }
    return super.getSlotFacets(s);
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  public BEnum getEventType()
  {
    return BBacnetEventType.changeOfState;
  }

  /**
   * Is the given alarm source ext a valid extension for
   * exporting BACnet alarm properties?  This determines if the
   * given alarm source extension follows the appropriate algorithm
   * defined for the intrinsic alarming of a particular object
   * type as required by the BACnet specification.<p>
   * BACnet BinaryValue points use a ChangeOfState alarm algorithm.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    if (ext instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) ext).getOffnormalAlgorithm() instanceof BBooleanChangeOfStateAlgorithm;
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Bacnet Access
////////////////////////////////////////////////////////////////

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
    BBooleanPoint pt = (BBooleanPoint)getPoint();
    if (pt == null)
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
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(pt.getOut().getValue()));

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
    BBooleanPoint pt = (BBooleanPoint)getPoint();
    if (pt == null)
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
        case BBacnetPropertyIdentifier.PRESENT_VALUE:
          BOutOfServiceExt outOfServiceExt = getOosExt();
          if (outOfServiceExt.getOutOfService())
          {
            synchronized (asnIn)
            {
              asnIn.setBuffer(val);
              int tag = asnIn.peekTag();
              if (tag == ASN_ENUMERATED)
              {
                int pv = asnIn.readEnumerated();
                if (pv == 0)
                {
                  outOfServiceExt.set(BOutOfServiceExt.presentValue, BBoolean.FALSE, BLocalBacnetDevice.getBacnetContext());
                }
                else if (pv == 1)
                {
                  outOfServiceExt.set(BOutOfServiceExt.presentValue, BBoolean.TRUE, BLocalBacnetDevice.getBacnetContext());
                }
                else
                {
                  return new NErrorType(BBacnetErrorClass.PROPERTY,
                                        BBacnetErrorCode.VALUE_OUT_OF_RANGE);
                }
              }
              else
              {
                throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
              }
            }
            // Export checkCov() will be called by the OOSExt.
            return null;
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

  /**
   * Subclass override method to add optional properties.
   * NOTE: You MUST call super.addOptionalProps(v) first!
   *
   * @param v Vector containing optional propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addOptionalProps(Vector v)
  {
    super.addOptionalProps(v);
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      v.add(BBacnetPropertyIdentifier.alarmValue);
    }
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
  @Override
  protected PropertyValue readOptionalProperty(int pId, int ndx)
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      BBooleanChangeOfStateAlgorithm alg = (BBooleanChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
      if (pId == BBacnetPropertyIdentifier.ALARM_VALUE)
      {
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(alg.getAlarmValue()));
      }
    }
    return super.readOptionalProperty(pId, ndx);
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
   * @return null if everything goes OK, or
   * an ErrorType describing the error if not.
   */
  @Override
  protected ErrorType writeOptionalProperty(int pId,
                                            int ndx,
                                            byte[] val,
                                            int pri)
    throws BacnetException
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      try
      {
        BBooleanChangeOfStateAlgorithm alg = (BBooleanChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
        switch (pId)
        {
          case BBacnetPropertyIdentifier.ALARM_VALUE:
            alg.setBoolean(BBooleanChangeOfStateAlgorithm.alarmValue,
                           AsnUtil.fromOnlyBinaryPv(val), BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.EVENT_ENABLE:
            almExt.set(BAlarmSourceExt.alarmEnable,
                       BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                       BLocalBacnetDevice.getBacnetContext());
            return null;
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
    }

    return super.writeOptionalProperty(pId, ndx, val, pri);
  }
}
