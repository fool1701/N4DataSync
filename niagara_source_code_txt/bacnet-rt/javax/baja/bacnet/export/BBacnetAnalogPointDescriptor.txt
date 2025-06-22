/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.Vector;

import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.fault.BOutOfRangeFaultAlgorithm;
import javax.baja.alarm.ext.offnormal.BOutOfRangeAlgorithm;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.enums.BBacnetEngineeringUnits;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BControlPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BDouble;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BIcon;
import javax.baja.sys.BNumber;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetAnalogPointDescriptor is the superclass for analog-type
 * point extensions exposing NumericPoints to Bacnet.
 *
 * @author Craig Gemmill on 19 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "covIncrement",
  type = "double",
  defaultValue = "1.0"
)
abstract public class BBacnetAnalogPointDescriptor
  extends BBacnetPointDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetAnalogPointDescriptor(731051824)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "covIncrement"

  /**
   * Slot for the {@code covIncrement} property.
   * @see #getCovIncrement
   * @see #setCovIncrement
   */
  public static final Property covIncrement = newProperty(0, 1.0, null);

  /**
   * Get the {@code covIncrement} property.
   * @see #covIncrement
   */
  public double getCovIncrement() { return getDouble(covIncrement); }

  /**
   * Set the {@code covIncrement} property.
   * @see #covIncrement
   */
  public void setCovIncrement(double v) { setDouble(covIncrement, v, null); }

  //endregion Property "covIncrement"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAnalogPointDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetAnalogPointDescriptor may only expose BNumericPoint.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BNumericPoint;
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  public BEnum getEventType()
  {
    return BBacnetEventType.outOfRange;
  }

  /**
   * Is the given alarm source ext a valid extension for
   * exporting BACnet alarm properties?  This determines if the
   * given alarm source extension follows the appropriate algorithm
   * defined for the intrinsic alarming of a particular object
   * type as required by the BACnet specification.<p>
   * All BACnet Analog points use an OutOfRange alarm algorithm.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    if (ext instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) ext).getOffnormalAlgorithm() instanceof BOutOfRangeAlgorithm;
    }
    return false;
  }

  public int asnType()
  {
    return ASN_REAL;
  }

  /**
   * Get the deadband value as Unsigned integer
   * @param value
   * @return deadBand value
   */
  public double getDeadBandValue(byte[] value)
    throws AsnException
  {
    return convertFromAsn(value);
  }

  /**
   * Get the deadband value as Asn Byte array
   * @param value
   * @return deadband bytes
   */
  public byte[] getDeadBandBytes(double value)
  {
    return convertToAsn(value);
  }
  /**
   * Override hook for LAV
   *
   * @param value double value to convert to asn.1
   * @return byte[] containing the required asn.1 formatted numeric value
   */
  public byte[] convertToAsn(double value)
  {
    return AsnUtil.toAsnReal(value);
  }

  /**
   * Override hook for LAV
   *
   * @param value asn.1 byte array containing a number
   * @return the number decoded from the byte[]
   * @throws AsnException if the array does not contain a properly
   */
  public double convertFromAsn(byte[] value)
    throws AsnException
  {
    return AsnUtil.fromAsnReal(value);
  }

  /**
   * Override hook for LAV
   *
   * @param out   asn.1 byte stream to append the numeric value
   * @param value asn.1 byte array containing a number
   */
  public void appendToAsn(AsnOutputStream out, double value)
  {
    out.writeReal(value);
  }

  /**
   * Override hook for LAV
   *
   * @throws AsnException if an unexpected ASN_TYPE is encountered
   */
  public double readFromAsn(AsnInputStream in)
    throws AsnException
  {
    return in.readReal();
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
    BNumericPoint pt = (BNumericPoint)getPoint();
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
        return new NReadPropertyResult(pId, ndx, convertToAsn(pt.getOut().getValue()));

      case BBacnetPropertyIdentifier.UNITS:
        BUnit u = (BUnit)pt.getFacets().getFacet(BFacets.UNITS);
        if (u != null)
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.make(u)));
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnEnumerated(BBacnetEngineeringUnits.NO_UNITS));
        }

      case BBacnetPropertyIdentifier.COV_INCREMENT:
        return new NReadPropertyResult(pId, ndx, convertToAsn(getCovIncrement()));

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
        case BBacnetPropertyIdentifier.UNITS:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.COV_INCREMENT:
          BNumber nmin = (BNumber)getPoint().getFacets().getFacet(BFacets.MIN);
          BNumber nmax = (BNumber)getPoint().getFacets().getFacet(BFacets.MAX);
          double min = (nmin != null) ? nmin.getDouble() : Double.NEGATIVE_INFINITY;
          double max = (nmax != null) ? nmax.getDouble() : Double.POSITIVE_INFINITY;
          double inc = getCovIncrement(val);
          if ((inc < 0.0) || (inc > (max - min)))
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }
          set(covIncrement, BDouble.make(inc), BLocalBacnetDevice.getBacnetContext());
          checkCov();
          return null;

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
   * get the COV increment as real value
   * @param value
   * @return cov increment
   * @throws AsnException
   */
  protected double getCovIncrement(byte[] value)
    throws AsnException
  {
    return convertFromAsn(value);
  }

  /**
   * Subclass override method to add required properties.
   * NOTE: You MUST call super.addRequiredProps(v) first!
   *
   * @param v Vector containing required propertyIds.
   */
  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void addRequiredProps(Vector v)
  {
    super.addRequiredProps(v);
    v.add(BBacnetPropertyIdentifier.presentValue);
    v.add(BBacnetPropertyIdentifier.statusFlags);
    v.add(BBacnetPropertyIdentifier.eventState);
    v.add(BBacnetPropertyIdentifier.outOfService);
    v.add(BBacnetPropertyIdentifier.units);
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
      v.add(BBacnetPropertyIdentifier.highLimit);
      v.add(BBacnetPropertyIdentifier.lowLimit);
      v.add(BBacnetPropertyIdentifier.deadband);
      v.add(BBacnetPropertyIdentifier.limitEnable);
    }

    BControlPoint pt = getPoint();
    BNumber min = (BNumber)pt.getFacets().getFacet(BFacets.MIN);
    if (min != null)
    {
      v.add(BBacnetPropertyIdentifier.minPresValue);
    }
    BNumber max = (BNumber)pt.getFacets().getFacet(BFacets.MAX);
    if (max != null)
    {
      v.add(BBacnetPropertyIdentifier.maxPresValue);
    }
    BNumber prec = (BNumber)pt.getFacets().getFacet(BFacets.PRECISION);
    if (prec != null)
    {
      v.add(BBacnetPropertyIdentifier.resolution);
    }

    v.add(BBacnetPropertyIdentifier.covIncrement);
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
      BOutOfRangeAlgorithm alg = (BOutOfRangeAlgorithm)almExt.getOffnormalAlgorithm();
      switch (pId)
      {
        case BBacnetPropertyIdentifier.HIGH_LIMIT:
          return new NReadPropertyResult(pId, ndx, convertToAsn(alg.getHighLimit()));

        case BBacnetPropertyIdentifier.LOW_LIMIT:
          return new NReadPropertyResult(pId, ndx, convertToAsn(alg.getLowLimit()));

        case BBacnetPropertyIdentifier.DEADBAND:
          return new NReadPropertyResult(pId, ndx, getDeadBandBytes(alg.getDeadband()));

        case BBacnetPropertyIdentifier.LIMIT_ENABLE:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnBitString(BacnetBitStringUtil.getBacnetLimitEnable(alg.getLimitEnable())));
      }
    }

    BNumericPoint pt = (BNumericPoint)getPoint();
    if (pt == null)
    {
      return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.OBJECT,
                                                              BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }
    switch (pId)
    {
      case BBacnetPropertyIdentifier.MIN_PRES_VALUE:
        BNumber min = (BNumber)pt.getFacets().getFacet(BFacets.MIN);
        if (min != null)
        {
          return new NReadPropertyResult(pId, ndx, convertToAsn(min.getDouble()));
        }
        break;

      case BBacnetPropertyIdentifier.MAX_PRES_VALUE:
        BNumber max = (BNumber)pt.getFacets().getFacet(BFacets.MAX);
        if (max != null)
        {
          return new NReadPropertyResult(pId, ndx, convertToAsn(max.getDouble()));
        }
        break;

      case BBacnetPropertyIdentifier.RESOLUTION:
        BNumber prec = (BNumber)pt.getFacets().getFacet(BFacets.PRECISION);
        if (prec != null)
        {
          return new NReadPropertyResult(pId, ndx, convertToAsn(Math.pow(10.0, -(double)prec.getFloat())));
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
    try
    {
      if (almExt != null)
      {
        BOutOfRangeAlgorithm alg = (BOutOfRangeAlgorithm)almExt.getOffnormalAlgorithm();
        BNumber nmin = (BNumber)getPoint().getFacets().getFacet(BFacets.MIN);
        BNumber nmax = (BNumber)getPoint().getFacets().getFacet(BFacets.MAX);
        double min = (nmin != null) ? nmin.getDouble() : Double.NEGATIVE_INFINITY;
        double max = (nmax != null) ? nmax.getDouble() : Double.POSITIVE_INFINITY;
        switch (pId)
        {
          case BBacnetPropertyIdentifier.HIGH_LIMIT:
            double hl = convertFromAsn(val);
            if ((hl < min) || (hl > max))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            alg.setDouble(BOutOfRangeAlgorithm.highLimit,
                          convertFromAsn(val),
                          BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.LOW_LIMIT:
            double ll = convertFromAsn(val);
            if ((ll < min) || (ll > max))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            alg.setDouble(BOutOfRangeAlgorithm.lowLimit,
                          convertFromAsn(val),
                          BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.DEADBAND:
            double db = getDeadBandValue(val);
            double hi = alg.getHighLimit();
            double lo = alg.getLowLimit();
            if ((db < 0.0) || (db > (hi - lo)))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            alg.setDouble(BOutOfRangeAlgorithm.deadband,
                          db,
                          BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.LIMIT_ENABLE:
            BBacnetBitString le = AsnUtil.fromAsnBitString(val);
            alg.set(BOutOfRangeAlgorithm.limitEnable,
                    BacnetBitStringUtil.getBLimitEnable(le),
                    BLocalBacnetDevice.getBacnetContext());
            return null;
        }
      }
      switch (pId)
      {
        case BBacnetPropertyIdentifier.MAX_PRES_VALUE:
          BNumber max = (BNumber)getPoint().getFacets().getFacet(BFacets.MAX);
          if (max != null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
          break;

        case BBacnetPropertyIdentifier.MIN_PRES_VALUE:
          BNumber min = (BNumber)getPoint().getFacets().getFacet(BFacets.MIN);
          if (min != null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
          break;

        case BBacnetPropertyIdentifier.RESOLUTION:
          BNumber prec = (BNumber)getPoint().getFacets().getFacet(BFacets.PRECISION);
          if (prec != null)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
          }
      }
      return super.writeOptionalProperty(pId, ndx, val, pri);
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
   * Override point for subclasses to validate their exposed point's
   * current state.  Default implementation does nothing.  Some points may
   * set the BACnet status flags to fault if the Niagara value is disallowed
   * for the exposed BACnet object type.
   */
  @Override
  protected void validate()
  {
    BStatusNumeric sn = ((BNumericPoint)getPoint()).getOut();
    BStatus s = sn.getStatus();
    if (s.isNull())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Invalid value for BACnet Object:" + sn);
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    else if (s.isFault())
    {
      // Refer BACNet spec 135-2016, 13.4.7
      BAlarmSourceExt alarmExt = getAlarmExt();
      if (alarmExt != null && alarmExt.getFaultAlgorithm() instanceof BOutOfRangeFaultAlgorithm)
      {
        BOutOfRangeFaultAlgorithm outOfRange = (BOutOfRangeFaultAlgorithm) alarmExt.getFaultAlgorithm();
        double pointValue = ((BNumericPoint)getPoint()).getNumeric();
        if (pointValue < outOfRange.getLowLimit())
        {
          setReliability(BBacnetReliability.underRange);
        }
        else if (pointValue > outOfRange.getHighLimit())
        {
          setReliability(BBacnetReliability.overRange);
        }
        else
        {
          setReliability(BBacnetReliability.unreliableOther);
        }
      }
      else
      {
        setReliability(BBacnetReliability.unreliableOther);
      }
    }
    else if (s.isDown())
    {
      setReliability(BBacnetReliability.communicationFailure);
    }
    else
    {
      setReliability(BBacnetReliability.noFaultDetected);
      if (configOk())
      {
        setStatus(BStatus.makeFault(getStatus(), false));
        setFaultCause("");
      }
      else
      {
        setStatus(BStatus.makeFault(getStatus(), true));
        setFaultCause(lex.getText("export.configurationFault"));
      }
    }
  }

  /**
   * Get the current statusValue to use in checking for COVs.
   * Subclasses must override this to return the correct statusValue,
   * taking into account the value of outOfService, and using the
   * getStatusFlags() method to incorporate the appropriate status
   * information to report to BACnet.
   */
  @Override
  BStatusValue getCurrentStatusValue()
  {
    BStatusValue sv = new BStatusNumeric(((BNumericPoint)getPoint()).getOut().getValue());
    sv.setStatus(this.getStatusFlags());
    return sv;
  }

  /**
   * Check to see if the current value requires a COV notification.
   */
  @Override
  boolean checkCov(BStatusValue currentValue, BStatusValue covValue)
  {
    if (currentValue.getStatus().getBits() != covValue.getStatus().getBits())
    {
      return true;
    }

    // Handle NaNs.
    double cur = ((BStatusNumeric)currentValue).getNumeric();
    double lst = ((BStatusNumeric)covValue).getNumeric();
    if (Double.isNaN(cur))
    {
      return !Double.isNaN(lst);
    }
    else if (Double.isNaN(lst))
    {
      return true;
    }

    return Math.abs(((BStatusNumeric)currentValue).getNumeric() - ((BStatusNumeric)covValue).getNumeric()) >= getCovIncrement();
  }

  /**
   * Check for Cov notification.
   * Analog points check if the difference between the point's current
   * value and the last Cov value is greater than COV_Increment.
   *
   * @deprecated
   */
  @Deprecated
  boolean checkCov(BControlPoint pt, BBacnetCovSubscription covSub)
  {
    if (pt.getStatus().getBits() != covSub.getLastValue().getStatus().getBits())
    {
      return true;
    }

    double currentValue = ((BNumericPoint)pt).getNumeric();
    double covValue = ((BINumeric)covSub.getLastValue()).getNumeric();
    return Math.abs(currentValue - covValue) >= getCovIncrement();
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.make(BIcon.std("control/numericPoint.png"), BIcon.std("badges/export.png"));
}
