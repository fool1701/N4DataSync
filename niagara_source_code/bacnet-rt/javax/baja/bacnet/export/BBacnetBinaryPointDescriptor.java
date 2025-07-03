/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import static javax.baja.bacnet.enums.BBacnetPropertyIdentifier.POLARITY;

import java.util.Vector;
import java.util.logging.Level;

import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPolarity;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.control.ext.BDiscreteTotalizerExt;
import javax.baja.driver.point.BDefaultProxyConversion;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.conv.BReversePolarityConversion;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetBinaryPointDescriptor is the superclass for binary-type
 * point extensions exposing BooleanPoints to Bacnet.
 *
 * @author Craig Gemmill on 19 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
abstract public class BBacnetBinaryPointDescriptor
  extends BBacnetPointDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetBinaryPointDescriptor(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinaryPointDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetBinaryPointDescriptor may only expose BBooleanPoint.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BBooleanPoint;
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
    BDiscreteTotalizerExt totExt = getTotalizerExt();
    if (totExt != null)
    {
      switch (pId)
      {
        case BBacnetPropertyIdentifier.CHANGE_OF_STATE_TIME:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toBacnetDateTime(totExt.getChangeOfStateTime()));

        case BBacnetPropertyIdentifier.CHANGE_OF_STATE_COUNT:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(totExt.getChangeOfStateCount()));

        case BBacnetPropertyIdentifier.TIME_OF_STATE_COUNT_RESET:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toBacnetDateTime(totExt.getTimeOfStateCountReset()));

        case BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(totExt.getElapsedActiveTime().getSeconds()));

        case BBacnetPropertyIdentifier.TIME_OF_ACTIVE_TIME_RESET:
          return new NReadPropertyResult(pId, ndx, AsnUtil.toBacnetDateTime(totExt.getTimeOfActiveTimeReset()));
      }
    }
    BBooleanPoint pt = (BBooleanPoint)getPoint();
    switch (pId)
    {
      case BBacnetPropertyIdentifier.ACTIVE_TEXT:
        BString tt = (BString)pt.getFacets().getFacet(BFacets.TRUE_TEXT);
        if (tt != null)
        {
          String trueText = BFormat.format(tt.toString(), null, null);
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(trueText));
        }
        break;

      case BBacnetPropertyIdentifier.INACTIVE_TEXT:
        BString ft = (BString)pt.getFacets().getFacet(BFacets.FALSE_TEXT);
        if (ft != null)
        {
          String falseText = BFormat.format(ft.toString(), null, null);
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(falseText));
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
    BDiscreteTotalizerExt totExt = getTotalizerExt();
    try
    {
      if (totExt != null)
      {
        switch (pId)
        {
          case BBacnetPropertyIdentifier.CHANGE_OF_STATE_TIME:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.CHANGE_OF_STATE_COUNT:
            if (AsnUtil.fromAsnUnsignedInteger(val) != 0)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            totExt.invoke(BDiscreteTotalizerExt.resetChangeOfStateCount, null, BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.TIME_OF_STATE_COUNT_RESET:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);

          case BBacnetPropertyIdentifier.ELAPSED_ACTIVE_TIME:
            if (AsnUtil.fromAsnUnsignedInteger(val) != 0)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            totExt.invoke(BDiscreteTotalizerExt.resetElapsedActiveTime, null, BLocalBacnetDevice.getBacnetContext());
            return null;

          case BBacnetPropertyIdentifier.TIME_OF_ACTIVE_TIME_RESET:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }
      }
      BBooleanPoint pt = (BBooleanPoint)getPoint();
      switch (pId)
      {
        case BBacnetPropertyIdentifier.ACTIVE_TEXT:
          BString tt = (BString)pt.getFacets().getFacet(BFacets.TRUE_TEXT);
          if (tt != null)
          {
            pt.set(BControlPoint.facets,
                   BFacets.make(pt.getFacets(), BFacets.TRUE_TEXT, BString.make(AsnUtil.fromAsnCharacterString(val))),
                   BLocalBacnetDevice.getBacnetContext());
            return null;
          }
          break;

        case BBacnetPropertyIdentifier.INACTIVE_TEXT:
          BString ft = (BString)pt.getFacets().getFacet(BFacets.FALSE_TEXT);
          if (ft != null)
          {
            pt.set(BControlPoint.facets,
                   BFacets.make(pt.getFacets(), BFacets.FALSE_TEXT, BString.make(AsnUtil.fromAsnCharacterString(val))),
                   BLocalBacnetDevice.getBacnetContext());
            return null;
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
    BControlPoint pt = getPoint();
    BString tt = (BString)pt.getFacets().getFacet(BFacets.TRUE_TEXT);
    if (tt != null)
    {
      v.add(BBacnetPropertyIdentifier.activeText);
    }
    BString ft = (BString)pt.getFacets().getFacet(BFacets.FALSE_TEXT);
    if (ft != null)
    {
      v.add(BBacnetPropertyIdentifier.inactiveText);
    }
    BDiscreteTotalizerExt tot = getTotalizerExt();
    if (tot != null)
    {
      v.add(BBacnetPropertyIdentifier.changeOfStateTime);
      v.add(BBacnetPropertyIdentifier.changeOfStateCount);
      v.add(BBacnetPropertyIdentifier.timeOfStateCountReset);
      v.add(BBacnetPropertyIdentifier.elapsedActiveTime);
      v.add(BBacnetPropertyIdentifier.timeOfActiveTimeReset);
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
    BStatusBoolean sb = ((BBooleanPoint)getPoint()).getOut();
    BStatus s = sb.getStatus();
    if (s.isNull())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Invalid value for BACnet Object:" + sb);
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    else if (s.isFault())
    {
      setReliability(BBacnetReliability.unreliableOther);
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
    BStatusValue sv = new BStatusBoolean(((BBooleanPoint)getPoint()).getOut().getValue());
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

    return ((BStatusBoolean)currentValue).getBoolean() != ((BStatusBoolean)covValue).getBoolean();
  }

  /**
   * Check for Cov notification.
   * Binary points check if the point's current value is different
   * than the last Cov value.
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

    boolean currentValue = ((BBooleanPoint)pt).getBoolean();
    boolean covValue = ((BIBoolean)covSub.getLastValue()).getBoolean();
    return currentValue != covValue;
  }

  private BDiscreteTotalizerExt getTotalizerExt()
  {
    BControlPoint pt = getPoint();
    if (pt == null)
    {
      return null;
    }

    SlotCursor<Property> c = pt.getProperties();
    if (c.next(BDiscreteTotalizerExt.class))
    {
      return (BDiscreteTotalizerExt) c.get();
    }

    return null;
  }

  PropertyValue readPolarityProperty(BBooleanPoint pt)
  {
    BAbstractProxyExt proxyExt = pt.getProxyExt();
    if (proxyExt instanceof BProxyExt &&
        ((BProxyExt) proxyExt).getConversion() instanceof BReversePolarityConversion)
    {
      return new NReadPropertyResult(POLARITY, AsnUtil.toAsnEnumerated(BBacnetPolarity.REVERSE));
    }

    return new NReadPropertyResult(POLARITY, AsnUtil.toAsnEnumerated(BBacnetPolarity.NORMAL));
  }

  protected ErrorType writePolarityProperty(BBooleanPoint pt, byte[] val)
    throws BacnetException
  {
    BAbstractProxyExt proxyExt = pt.getProxyExt();
    if (proxyExt instanceof BProxyExt)
    {
      if (AsnUtil.fromAsnEnumerated(val) == BBacnetPolarity.REVERSE)
      {
        ((BProxyExt) proxyExt).setConversion(BReversePolarityConversion.DEFAULT);
      }
      else
      {
        ((BProxyExt) proxyExt).setConversion(BDefaultProxyConversion.DEFAULT);
      }
      return null;
    }
    else
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Cannot write the Polarity property when the associated point's proxy ext is not" +
          " instanceof BProxyExt; object ID: " + getObjectId() + ", object name: " + getObjectName());
      }
      return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
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

  private static final BIcon icon = BIcon.make(BIcon.std("control/booleanPoint.png"), BIcon.std("badges/export.png"));
}
