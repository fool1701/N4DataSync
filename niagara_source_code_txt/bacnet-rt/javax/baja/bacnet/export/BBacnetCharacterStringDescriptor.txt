/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.offnormal.BStringChangeOfStateAlgorithm;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetOptionalCharacterString;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.control.BControlPoint;
import javax.baja.control.BStringPoint;
import javax.baja.control.BStringWritable;
import javax.baja.control.enums.BPriorityLevel;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BEnum;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Knob;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetCharacterStringDescriptor is the extension that exposes
 * Bacnet CharacterString capability.
 *
 * @author Joseph Chandler
 * @since 12/7/2013
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:StringWritable"
  )
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.CHARACTER_STRING_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
@NiagaraProperty(
  name = "bacnetWritable",
  type = "String",
  defaultValue = "BBacnetPointDescriptor.lexNotWritable",
  flags = Flags.READONLY | Flags.HIDDEN
)
public class BBacnetCharacterStringDescriptor
  extends BBacnetPointDescriptor
  implements BacnetWritableDescriptor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetCharacterStringDescriptor(3627519920)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.CHARACTER_STRING_VALUE), null);

  //endregion Property "objectId"

  //region Property "bacnetWritable"

  /**
   * Slot for the {@code bacnetWritable} property.
   * @see #getBacnetWritable
   * @see #setBacnetWritable
   */
  public static final Property bacnetWritable = newProperty(Flags.READONLY | Flags.HIDDEN, BBacnetPointDescriptor.lexNotWritable, null);

  /**
   * Get the {@code bacnetWritable} property.
   * @see #bacnetWritable
   */
  public String getBacnetWritable() { return getString(bacnetWritable); }

  /**
   * Set the {@code bacnetWritable} property.
   * @see #bacnetWritable
   */
  public void setBacnetWritable(String v) { setString(bacnetWritable, v, null); }

  //endregion Property "bacnetWritable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCharacterStringDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetCharacterStringDescriptor()
  {
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetCharacterStringDescriptor may only expose a BStringPoint.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BStringPoint;
  }

  /**
   * Get the BACnetEventType reported by this object.
   */
  @Override
  public BEnum getEventType()
  {
    return BBacnetEventType.changeOfCharacterstring;
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
      return ((BAlarmSourceExt)ext).getOffnormalAlgorithm() instanceof BStringChangeOfStateAlgorithm;
    }

    return false;
  }

  @Override
  protected boolean isCommandable()
  {
    return true;
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
    BStringWritable pt = (BStringWritable)getPoint();
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
      case BBacnetPropertyIdentifier.PRIORITY_ARRAY:
        return readPriorityArray(ndx);

      case BBacnetPropertyIdentifier.RELINQUISH_DEFAULT:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(pt.getFallback().getValue()));

      case BBacnetPropertyIdentifier.PRESENT_VALUE:
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnCharacterString(pt.getOut().getValue()));

      default:
        return super.readProperty(pId, ndx);
    }
  }

  @Override
  protected PropertyValue readOptionalProperty(int pId, int ndx)
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.ALARM_VALUES:
        BAlarmSourceExt alarmExt = getAlarmExt();
        if ((alarmExt == null) || !(alarmExt.getOffnormalAlgorithm() instanceof BStringChangeOfStateAlgorithm))
        {
          return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY));
        }

        String alarmExpression = ((BStringChangeOfStateAlgorithm)alarmExt.getOffnormalAlgorithm()).getExpression();

        AsnOutputStream asnOut = new AsnOutputStream();
        new BBacnetOptionalCharacterString(alarmExpression).writeAsn(asnOut);
        byte[] val = asnOut.toByteArray();

        return new NReadPropertyResult(pId, ndx, val);
    }

    return super.readOptionalProperty(pId, ndx);
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
    BStringWritable pt = (BStringWritable)getPoint();
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
          return writePriorityArray(pri, val);

        case BBacnetPropertyIdentifier.PRIORITY_ARRAY:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.RELINQUISH_DEFAULT:
          BStatusString fb = pt.getFallback();
          fb.setString(BStatusString.value,
                       AsnUtil.fromAsnCharacterString(val),
                       BLocalBacnetDevice.getBacnetContext());
          fb.setStatusNull(false);
          return null;

        default:
          return super.writeProperty(pId, ndx, val, pri);
      }
    }
    catch (AsnException e)
    {
      log.warning("AsnException writing property " + pId + " in object " + getObjectId() + ": "  + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.INVALID_DATA_TYPE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing property " + pId + " in object " + getObjectId() + ": "  + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
    }
  }

  @Override
  protected ErrorType writeOptionalProperty(int pId, int ndx, byte[] val, int pri)
    throws BacnetException
  {
    switch (pId)
    {
      case BBacnetPropertyIdentifier.ALARM_VALUES:
        if (getAlarmExt() != null)
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.WRITE_ACCESS_DENIED);
        }
        else
        {
          return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
        }
    }

    return super.writeOptionalProperty(pId, ndx, val, pri);
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
    v.add(BBacnetPropertyIdentifier.eventState);
    v.add(BBacnetPropertyIdentifier.outOfService);
    v.add(BBacnetPropertyIdentifier.priorityArray);
    v.add(BBacnetPropertyIdentifier.relinquishDefault);
    if (getAlarmExt() != null)
    {
      v.add(BBacnetPropertyIdentifier.alarmValues);
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
    BStatusString sn = ((BStringPoint)getPoint()).getOut();
    BStatus s = sn.getStatus();
    if (s.isNull())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Invalid value for BACnet Object:" + sn);
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
   * Write to an entry in the priority array.
   *
   * @param pri the slot to write.
   * @param val the value to write.
   * @return null if ok, or an <code>ErrorType</code> indicating the error.
   */
  private ErrorType writePriorityArray(int pri, byte[] val)
    throws BacnetException
  {
    BStringWritable pt = (BStringWritable)getPoint();
    if (pt == null)
    {
      return new NErrorType(BBacnetErrorClass.OBJECT,
                            BBacnetErrorCode.TARGET_NOT_CONFIGURED);
    }
    try
    {
      // Handle no-priority case
      if (pri == NOT_USED)
      {
        pri = 16;
      }

      // Handle invalid array index
      if ((pri < 1) || (pri > 16))
      {
        return new NErrorType(BBacnetErrorClass.SERVICES,
                              BBacnetErrorCode.PARAMETER_OUT_OF_RANGE);
      }

      String inSlotName = "bacnetValueIn" + pri;
      Property inSlot = loadSlots().getProperty(inSlotName);

      // If no property, we haven't allowed write access to this one.
      if (inSlot == null)
      {
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
      BStatusString bacval = (BStatusString)get(inSlot).newCopy();

      // Parse the property value & set bacval
      synchronized (asnIn)
      {
        asnIn.setBuffer(val);
        int tag = asnIn.peekTag();
        if (tag == ASN_NULL)
        {
          BOutOfServiceExt outOfServiceExt = getOosExt();
          if (outOfServiceExt.getOutOfService())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }
          else
          {
            bacval.setStatusNull(true);
          }
        }
        else if (tag == ASN_CHARACTER_STRING)
        {
          String characterString = asnIn.readCharacterString();

          BOutOfServiceExt outOfServiceExt = getOosExt();
          if (outOfServiceExt.getOutOfService())
          {
            outOfServiceExt.set(
              BOutOfServiceExt.presentValue,
              BString.make(characterString),
              BLocalBacnetDevice.getBacnetContext());
          }

          bacval.setStatusNull(false);
          bacval.setValue(characterString);
        }
        else
        {
          throw new AsnException(AsnConst.E_BACNET_ASN_INVALID_TAG + tag);
        }
      }

      set(inSlot, bacval, BLocalBacnetDevice.getBacnetContext());
      return null;
    }
    catch (IllegalArgumentException e)
    {
      log.warning("IllegalArgumentException writing priorityArray in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.VALUE_OUT_OF_RANGE);
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException writing priorityArray in object " + getObjectId() + ": " + e);
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.WRITE_ACCESS_DENIED);
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
    BStatusValue sv = new BStatusString(((BStringPoint)getPoint()).getOut().getValue());
    sv.setStatus(getStatusFlags());
    return sv;
  }

  /**
   * Check to see if the current value requires a COV notification.
   *
   * @return true if the currentValue is different than the cov value, or the status bits have changed
   */
  @Override
  public boolean checkCov(BStatusValue currentValue, BStatusValue covValue)
  {
    return !currentValue.toString().equals(covValue.toString());
  }

  /**
   * Read the priority array.
   *
   * @param ndx the property array index:
   *            -1: return the entire array
   *            0: return the array size (16)
   *            n: return the value in slot n
   * @return a PropertyValue, containing the encoded value or an appropriate error.
   */
  private PropertyValue readPriorityArray(int ndx)
  {
    BStringWritable pt = (BStringWritable)getPoint();
    if (pt == null)
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY,
                                     ndx,
                                     new NErrorType(BBacnetErrorClass.OBJECT,
                                                    BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }
    if (ndx == NOT_USED)
    {
      // return the entire "array"
      BStatusString e;
      synchronized (asnOut)
      {
        asnOut.reset();
        for (int i = 1; i <= 16; i++)
        {
          e = pt.getLevel(BPriorityLevel.make(i));
          if (e.getStatus().isNull())
          {
            asnOut.writeNull();
          }
          else
          {
            asnOut.writeCharacterString(e.getValue());
          }
        }
        return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, asnOut.toByteArray());
      }
    }
    else if (ndx == 0)
    {
      // return the array size
      return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnUnsigned(16));
    }
    else
    {
      // return the specified element
      try
      {
        BStatusString e = pt.getLevel(BPriorityLevel.make(ndx));
        if (e.getStatus().isNull())
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnCharacterString(e.getValue()));
        }
      }
      catch (Exception e)
      {
        return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx,
                                       new NErrorType(BBacnetErrorClass.PROPERTY,
                                                      BBacnetErrorCode.INVALID_ARRAY_INDEX));
      }
    }
  }

  private void resetBacnetWritable()
  {
    StringBuilder sb = new StringBuilder();
    Knob[] knobs = getKnobs();
    for (int i = 0; i < knobs.length; i++)
    {
      BObject tgt = knobs[i].getTargetOrd().get(this);
      BObject pt = getPoint();
      if (knobs[i].getTargetSlotName().startsWith("in") &&
          tgt == pt)
      {
        sb.append(knobs[i].getTargetSlotName()).append(',');
      }
    }
    setBacnetWritable((sb.length() > 0) ? sb.substring(0, sb.length() - 1) :
                                          lexNotWritable);
  }

  @Override
  public void knobAdded(Knob knob, Context cx)
  {
    resetBacnetWritable();
  }

  @Override
  public void knobRemoved(Knob knob, Context cx)
  {
    resetBacnetWritable();
  }

  @Override
  public final void doMakeWritable(BValue writable)
  {
    if (!isRunning())
    {
      return;
    }
    BControlPoint point = getPoint();
    if (point instanceof BStringWritable)
    {
      // First, remove all links and "bacnetValuexxx" properties.
      BLink[] links = point.getLinks();
      for (int i = 0; i < links.length; i++)
      {
        if (links[i].isActive())
        {
          if ((links[i].getSourceComponent() == this) && links[i].getTargetSlot().getName().startsWith("in"))
          {
            point.remove(links[i]);
          }
        }
        else
        {
          point.remove(links[i]);
        }
      }

      BStatusString[] bacnetValues = getChildren(BStatusString.class);
      for (int i = 0; i < bacnetValues.length; i++)
      {
        if (bacnetValues[i].getName().startsWith("bacnetValueIn"))
        {
          remove(bacnetValues[i]);
        }
      }

      // Now, add in the ones we want.
      String s = ((BString)writable).getString();
      if (s.equals(lexNotWritable))
      {
        return;
      }

      StringTokenizer st = new StringTokenizer(s, ",");
      while (st.hasMoreTokens())
      {
        String tgtSlotName = st.nextToken();
        Slot tgtSlot = point.getSlot(tgtSlotName);
        String srcSlotName = "bacnetValue" + TextUtil.capitalize(tgtSlotName);
        BStatusString sf = new BStatusString();
        sf.setStatusNull(true);
        add(srcSlotName, sf, Flags.OPERATOR | Flags.READONLY);
        point.setFlags(tgtSlot, point.getFlags(tgtSlot) | Flags.FAN_IN);
        BLink link = new BLink(getHandleOrd(), srcSlotName, tgtSlotName, true);
        point.add("bacnet" + tgtSlotName, link, Flags.READONLY);
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

  private static final BIcon icon = BIcon.make(BIcon.std("control/stringPoint.png"), BIcon.std("badges/export.png"));
}
