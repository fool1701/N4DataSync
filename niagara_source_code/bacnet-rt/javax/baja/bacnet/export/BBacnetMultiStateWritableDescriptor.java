/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.StringTokenizer;

import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.OutOfRangeException;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumWritable;
import javax.baja.control.enums.BPriorityLevel;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatusEnum;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
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
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * BBacnetMultiStateWritableDescriptor exposes a ControlPoint as a
 * commandable MultiState point.  It is the superclass for MultiState Output
 * and commandable MultiState Value points.
 *
 * @author Craig Gemmill on 25 Jul 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "bacnetWritable",
  type = "String",
  defaultValue = "BBacnetPointDescriptor.lexNotWritable",
  flags = Flags.READONLY | Flags.HIDDEN
)
abstract public class BBacnetMultiStateWritableDescriptor
  extends BBacnetMultiStatePointDescriptor
  implements BacnetWritableDescriptor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetMultiStateWritableDescriptor(285772470)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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
  public static final Type TYPE = Sys.loadType(BBacnetMultiStateWritableDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /* *
   * Started.
   * 2005-07-25 CPG   Keep the code in here, but commented.
   * This change clears the values from all of the BACnet inputs
   * on startup, so the values effectively do not persist, but
   * the slots themselves persist.
  public void started()
    throws Exception
  {
    super.started();
    String inSlotName = null;
    Property inSlot = null;
    BStatusEnum bacval = new BStatusEnum();
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
   * BBacnetEnumWritableDescriptor may only expose BEnumWritable.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected final boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BEnumWritable;
  }

  /**
   * Is this export descriptor representing a BACnet object
   * with a Commandable Present_Value property (per the Clause 19
   * prioritization procedure)?<p>
   * Writable descriptors must override this to return true.
   *
   * @return true if commandable, otherwise false
   */
  @Override
  protected boolean isCommandable()
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
    BEnumWritable pt = (BEnumWritable)getPoint();
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
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(pt.getFallback().getValue().getOrdinal()));
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
    BEnumWritable pt = (BEnumWritable)getPoint();
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
          int writeVal = AsnUtil.fromAsnUnsignedInt(val);
          BDynamicEnum ms = pt.getFallback().getValue();
          BEnumRange r = (BEnumRange)pt.getFacets().getFacet(BFacets.RANGE);
          if ((r != null) && !r.isOrdinal(writeVal))
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }
          else
          {
            BStatusEnum fb = pt.getFallback();
            fb.set(BStatusEnum.value,
                   BDynamicEnum.make(writeVal, ms.getRange()),
                   BLocalBacnetDevice.getBacnetContext());
            fb.setStatusNull(false);
            return null;
          }

        default:
          return super.writeProperty(pId, ndx, val, pri);
      }
    }
    catch (OutOfRangeException | IllegalArgumentException e)
    {
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

  /**
   * Read the priority array.
   *
   * @param the property array index:
   *            -1: return the entire array
   *            0: return the array size (16)
   *            n: return the value in slot n
   * @return a PropertyValue, containing the encoded value or an appropriate error.
   */
  private PropertyValue readPriorityArray(int ndx)
  {
    BEnumWritable pt = (BEnumWritable)getPoint();
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
      BStatusEnum e;
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
            asnOut.writeUnsignedInteger(e.getValue().getOrdinal());
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
        BStatusEnum e = pt.getLevel(BPriorityLevel.make(ndx));
        if (e.getStatus().isNull())
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnNull());
        }
        else
        {
          return new NReadPropertyResult(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ndx, AsnUtil.toAsnUnsigned(e.getValue().getOrdinal()));
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
    BEnumWritable pt = (BEnumWritable)getPoint();
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
      BStatusEnum bacval = (BStatusEnum)get(inSlotName).newCopy();

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
        else if (tag == ASN_UNSIGNED)
        {
          bacval.setStatusNull(false);
          int writeVal = asnIn.readUnsignedInt();
          //As per Bacnet Spec Rev 14 : present value should always be greater than 0 for multistate value objects.
          if (writeVal <= 0)
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

          BEnumRange range = (BEnumRange)pt.getFacets().getFacet(BFacets.RANGE);
          if (range != null && !range.isOrdinal(writeVal))
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.VALUE_OUT_OF_RANGE);
          }

          BEnumRange outRange = pt.getOut().getValue().getRange();

          BOutOfServiceExt outOfServiceExt = getOosExt();
          if (outOfServiceExt.getOutOfService())
          {
            outOfServiceExt.set(
              BOutOfServiceExt.presentValue,
              BDynamicEnum.make(writeVal, outRange),
              BLocalBacnetDevice.getBacnetContext());
          }

          bacval.setValue(outRange.get(writeVal));
        }
        //2005-08-01 CPG Removed this for BTL compliance
        //else if (tag == ASN_ENUMERATED)
        //{
        //  bacval.setStatusNull(false);
        //  bacval.setValue(ms.getRange().get(asnIn.readEnumerated()));
        //}
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
    BEnumWritable pt = (BEnumWritable)getPoint();
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

    BStatusEnum[] bacnetValues = getChildren(BStatusEnum.class);
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
      String srcSlotName = "bacnetValue" + TextUtil.capitalize(tgtSlotName);
      BStatusEnum sf = new BStatusEnum();
      sf.setStatusNull(true);
      add(srcSlotName, sf, Flags.OPERATOR | Flags.READONLY);
      pt.setFlags(tgtSlot, pt.getFlags(tgtSlot) | Flags.FAN_IN);
      BLink link = new BLink(getHandleOrd(), srcSlotName, tgtSlotName, true);
      pt.add("bacnet" + tgtSlotName, link, Flags.READONLY);
    }
  }
}
