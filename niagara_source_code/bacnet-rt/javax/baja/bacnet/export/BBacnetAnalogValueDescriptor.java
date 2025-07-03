/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BControlPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;

/**
 * BBacnetAnalogValueDescriptor exposes a ControlPoint as a Bacnet
 * Analog Value Object.
 *
 * @author Craig Gemmill on 11 Aug 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:NumericPoint"
  )
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.ANALOG_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetAnalogValueDescriptor
  extends BBacnetAnalogPointDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetAnalogValueDescriptor(352971370)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.ANALOG_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAnalogValueDescriptor.class);

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
  public BStatusNumeric getBacnetValue()
  {
    throw new BajaRuntimeException("Method getBacnetValue() is deprecated!");
  }

  /**
   * @deprecated BacnetValue is no longer necessary since out-of-service
   * changes will be written directly to the point via
   * the BOutOfServiceExt.
   */
  @Deprecated
  public void setBacnetValue(BStatusNumeric v)
  {
    throw new BajaRuntimeException("Method setBacnetValue() is deprecated!");
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

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
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.ANALOG_VALUE);
    }
    return super.getSlotFacets(s);
  }

  /**
   * BBacnetAnalogInputDescriptor may only expose BNumericPoint.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected final boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BNumericPoint;
  }

////////////////////////////////////////////////////////////////
// Bacnet Access
////////////////////////////////////////////////////////////////

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
    BNumericPoint pt = (BNumericPoint)getPoint();
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
            outOfServiceExt.set(BOutOfServiceExt.presentValue, BDouble.make(convertFromAsn(val)), BLocalBacnetDevice.getBacnetContext());
            // Export checkCov() will be called by the OOSExt.
            return null;
          }
          else
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);
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

    return super.writeProperty(pId, ndx, val, pri);
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
    switch (pId)
    {
      /*
        Event enable of Analog object should be made write property as per
        the Appendix 4 of "BACnet 2011 en" AMEV specification
       */
      case BBacnetPropertyIdentifier.EVENT_ENABLE:
        BAlarmSourceExt almExt = getAlarmExt();
        if (almExt != null)
        {
          almExt.set(BAlarmSourceExt.alarmEnable,
                     BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                     BLocalBacnetDevice.getBacnetContext());
          return null;
        }
    }

    return super.writeOptionalProperty(pId, ndx, val, pri);
  }
}
