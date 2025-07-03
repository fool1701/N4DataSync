/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.control.BControlPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.enums.BPriorityLevel;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BNumber;
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
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetAnalogWritableDescriptor exposes a ControlPoint as a
 * commandable analog point.  It is the superclass for Analog Output
 * and commandable Analog Value points.
 *
 * @author Craig Gemmill on 19 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "bacnetWritable",
  type = "String",
  defaultValue = "BBacnetAnalogPointDescriptor.lexNotWritable",
  flags = Flags.READONLY | Flags.HIDDEN
)
abstract public class BBacnetAnalogWritableDescriptor
  extends BBacnetAnalogPointDescriptor
  implements BacnetWritableDescriptor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetAnalogWritableDescriptor(3397530906)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "bacnetWritable"

  /**
   * Slot for the {@code bacnetWritable} property.
   * @see #getBacnetWritable
   * @see #setBacnetWritable
   */
  public static final Property bacnetWritable = newProperty(Flags.READONLY | Flags.HIDDEN, BBacnetAnalogPointDescriptor.lexNotWritable, null);

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
  public static final Type TYPE = Sys.loadType(BBacnetAnalogWritableDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /*
   * Started.
   * 2005-07-25 CPG Keep the code in here, but commented.
   * This change clears the values from all of the BACnet inputs
   * on startup, so the values effectively do not persist, but
   * the slots themselves persist.
  public void started()
    throws Exception
  {
    super.started();
    String inSlotName = null;
    Property inSlot = null;
    BStatusNumeric bacval = new BStatusNumeric();
    bacval.setStatusNull(true);
    loadSlots();
    for (int pri=1; pri<=16; pri++)
    {
      inSlotName = "bacnetValueIn"+pri;
      inSlot = getProperty(inSlotName);
      if (inSlot != null) set(inSlot, bacval.newCopy(), null);
    }
  }
   */

  /**
   * BBacnetAnalogWritableDescriptors may only expose BNumericWritables.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected final boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BNumericWritable;
  }

  /**
   * Is this export descriptor representing a BACnet object
   * with a Commandable Present_Value property (per the Clause 19
   * prioritization procedure)?<p>
   * Writable descriptors must override this to return true.
   *
   * @return true if commmandable, otherwise false
   */
  @Override
  protected boolean isCommandable()
  {
    return true;
  }

  /**
   * The priority array and relinquish default properties
   * are required for analog outputs but optional for analog values.
   *
   * @return true if the priority array and relinquish default properties
   * should be setup as required properties
   */
  protected boolean commandabilityRequired()
  {
    return true;
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
    BNumericWritable pt = (BNumericWritable)getPoint();
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
        byte[] asn = convertToAsn(pt.getFallback().getValue());
        return new NReadPropertyResult(pId, ndx, asn);
    }

    return super.readProperty(pId, ndx);
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
    BNumericWritable pt = (BNumericWritable)getPoint();
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
          BNumber nmin = (BNumber)getPoint().getFacets().getFacet(BFacets.MIN);
          BNumber nmax = (BNumber)getPoint().getFacets().getFacet(BFacets.MAX);
          double min = (nmin != null) ? nmin.getDouble() : Double.NEGATIVE_INFINITY;
          double max = (nmax != null) ? nmax.getDouble() : Double.POSITIVE_INFINITY;
          double real = Double.NaN;

          real = convertFromAsn(val);
          if ((real < min) || (real > max))
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

          BStatusNumeric fb = pt.getFallback();
          fb.setDouble(BStatusNumeric.value,
                       real,
                       BLocalBacnetDevice.getBacnetContext());
          fb.setStatusNull(false);
          return null;
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
    if (commandabilityRequired())
    {
      v.add(BBacnetPropertyIdentifier.priorityArray);
      v.add(BBacnetPropertyIdentifier.relinquishDefault);
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
    if (!commandabilityRequired())
    {
      v.add(BBacnetPropertyIdentifier.priorityArray);
      v.add(BBacnetPropertyIdentifier.relinquishDefault);
    }
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Read the priority array.
   *
   * @param ndx the property array index:
   *            -1: return the entire array
   *            0: return the array size (16)
   *            n: return the value in slot n
   * @return a PropertyValue, containing the encoded value or an appropriate error.
   */
  protected PropertyValue readPriorityArray(int ndx)
  {
    BNumericWritable pt = (BNumericWritable)getPoint();
    if (pt == null)
    {
      return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx,
                                     new NErrorType(BBacnetErrorClass.OBJECT,
                                                    BBacnetErrorCode.TARGET_NOT_CONFIGURED));
    }
    if (ndx == NOT_USED)
    {
      // return the entire "array"
      BStatusNumeric e;
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
            appendToAsn(asnOut, e.getValue());
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
        BStatusNumeric e = pt.getLevel(BPriorityLevel.make(ndx));
        if (e.getStatus().isNull())
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, convertToAsn(e.getValue()));
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
    BNumericWritable pt = (BNumericWritable)getPoint();
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
      BStatusNumeric bacval = (BStatusNumeric)get(inSlot).newCopy();

      // Parse the property value & set bacval
      BNumber nmin = (BNumber)pt.getFacets().getFacet(BFacets.MIN);
      BNumber nmax = (BNumber)pt.getFacets().getFacet(BFacets.MAX);
      double min = (nmin != null) ? nmin.getDouble() : Double.NEGATIVE_INFINITY;
      double max = (nmax != null) ? nmax.getDouble() : Double.POSITIVE_INFINITY;
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
        else if (tag == asnType())
        {
          double real = readFromAsn(asnIn);
          if (real < min || real > max)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

          BOutOfServiceExt outOfServiceExt = getOosExt();
          if (outOfServiceExt.getOutOfService())
          {
            outOfServiceExt.set(
              BOutOfServiceExt.presentValue,
              BDouble.make(real),
              BLocalBacnetDevice.getBacnetContext());
          }

          bacval.setStatusNull(false);
          bacval.setValue(real);
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

    // First, remove all links and "bacnetValuexxx" properties.
    BNumericWritable pt = (BNumericWritable)getPoint();
    if (pt == null)
    {
      return;
    }
    BLink[] links = pt.getLinks();
    for (int i = 0; i < links.length; i++)
    {
      if (links[i].isActive())
      {
        if ((links[i].getSourceComponent() == this) &&
            links[i].getTargetSlot().getName().startsWith("in"))
        {
          pt.remove(links[i]);
        }
      }
      else
      {
        pt.remove(links[i]);
      }
    }

    BStatusNumeric[] bacnetValues = getChildren(BStatusNumeric.class);
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
      Slot tgtSlot = pt.getSlot(tgtSlotName);
      BValue value = pt.get(tgtSlotName).newCopy();
      String srcSlotName = "bacnetValue" + TextUtil.capitalize(tgtSlotName);
      BStatusNumeric sf = new BStatusNumeric();
      sf.setStatusNull(true);
      add(srcSlotName, sf, Flags.OPERATOR | Flags.READONLY);
      pt.setFlags(tgtSlot, pt.getFlags(tgtSlot) | Flags.FAN_IN);
      BLink link = new BLink(getHandleOrd(), srcSlotName, tgtSlotName, true);
      pt.add("bacnet" + tgtSlotName, link, Flags.READONLY);
      pt.set(tgtSlotName, value);
    }
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
