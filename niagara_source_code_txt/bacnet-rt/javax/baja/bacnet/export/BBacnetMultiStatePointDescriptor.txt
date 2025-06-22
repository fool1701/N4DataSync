/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.fault.BEnumFaultAlgorithm;
import javax.baja.alarm.ext.offnormal.BEnumChangeOfStateAlgorithm;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.datatypes.BBacnetCovSubscription;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.io.RangeData;
import javax.baja.bacnet.io.RangeReference;
import javax.baja.bacnet.io.RejectException;
import javax.baja.bacnet.util.EnumRangeWrapper;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.security.PermissionException;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.BacnetConfirmedRequest;
import com.tridium.bacnet.services.confirmed.ReadRangeAck;

/**
 * BBacnetMultiStatePointDescriptor is the superclass for multi-state
 * point extensions exposing MultiStatePoints to Bacnet.
 *
 * @author Craig Gemmill on 25 Jul 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
abstract public class BBacnetMultiStatePointDescriptor
  extends BBacnetPointDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetMultiStatePointDescriptor(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetMultiStatePointDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BBacnetEnumPointDescriptor may only expose BEnumPoints.
   *
   * @param pt the exposed point
   * @return true if the Niagara point type is legal for this point type.
   */
  @Override
  protected boolean isPointTypeLegal(BControlPoint pt)
  {
    return pt instanceof BEnumPoint;
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
    BEnumPoint pt = (BEnumPoint)getPoint();
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
        return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(pt.getOut().getValue().getOrdinal()));

      case BBacnetPropertyIdentifier.NUMBER_OF_STATES:
        BEnumRange r = (BEnumRange)pt.getFacets().getFacet(BFacets.RANGE);
        if (r != null)
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(r.getOrdinals().length));
        }
        else
        {
          return new NReadPropertyResult(pId, ndx, AsnUtil.toAsnUnsigned(Integer.MAX_VALUE));
        }

      case BBacnetPropertyIdentifier.STATE_TEXT:
        return readStateText(ndx);

      default:
        return super.readProperty(pId, ndx);
    }
  }

  /**
   * Read the specified range of values of a compound property.
   *
   * @param rangeReference the range reference describing the requested range.
   * @return a byte array containing the encoded range.
   */
  @Override
  public RangeData readRange(RangeReference rangeReference)
    throws RejectException
  {
    if (rangeReference.getPropertyArrayIndex() >= 0)
    {
      if (!isArray(rangeReference.getPropertyId()))
      {
        return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY);
      }
    }

    int pId = rangeReference.getPropertyId();
    BAlarmSourceExt almExt = getAlarmExt();
    switch (pId)
    {
      case BBacnetPropertyIdentifier.ALARM_VALUES:
        if (almExt != null)
        {
          BEnumChangeOfStateAlgorithm alg = (BEnumChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
          int[] ordinals = alg.getAlarmValues().getOrdinals();
          Integer[] avals = new Integer[ordinals.length];
          for (int i = 0; i < avals.length; i++)
          {
            avals[i] = Integer.valueOf(ordinals[i]);
          }
          return readRange(rangeReference, avals, 5);
        }
        break;

      case BBacnetPropertyIdentifier.FAULT_VALUES:
        if (!BBacnetNetwork.bacnet().setAndGetShouldSupportFaults())
        {
          return new ReadRangeAck(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
        }

        if (almExt != null)
        {
          BEnumFaultAlgorithm alg = (BEnumFaultAlgorithm)almExt.getFaultAlgorithm();
          int[] validVals = alg.getValidValues().getOrdinals();
          BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
          int[] rangeVals = r.getOrdinals();
          Array<Integer> a = new Array<>(Integer.class);
          for (int i = 0; i < rangeVals.length; i++)
          {
            boolean valid = false;
            for (int j = 0; j < validVals.length; j++)
            {
              if (rangeVals[i] == validVals[j])
              {
                valid = true;
                break;
              }
            }
            if (!valid)
            {
              a.add(Integer.valueOf(rangeVals[i]));
            }
          }
          Integer[] fvals = a.trim();
          return readRange(rangeReference, fvals, 5);
        }
        break;

      default:
        int[] props = getRequiredProps();
        for (int i = 0; i < props.length; i++)
        {
          if (pId == props[i])
          {
            return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                    BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
          }
        }
        props = getOptionalProps();
        for (int i = 0; i < props.length; i++)
        {
          if (pId == props[i])
          {
            return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                    BBacnetErrorCode.PROPERTY_IS_NOT_A_LIST);
          }
        }
        break;
    }

    return new ReadRangeAck(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
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
    BEnumPoint pt = (BEnumPoint)getPoint();
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
        case BBacnetPropertyIdentifier.NUMBER_OF_STATES:
          return new NErrorType(BBacnetErrorClass.PROPERTY,
                                BBacnetErrorCode.WRITE_ACCESS_DENIED);

        case BBacnetPropertyIdentifier.STATE_TEXT:
          return writeStateText(ndx, val, pt);

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
  }

  /**
   * Execute the ReadRange for the given class and maximum encoded size
   * of the data type.
   */
  protected RangeData readRange(RangeReference ref, Integer[] list, int maxEncodedSize)
  {
    int rangeType = ref.getRangeType();
    int len = list.length;
    boolean[] rflags = new boolean[] { false, false, false };

    // Calculate the maximum allowed data length.
    int maxDataLength = -1;
    if (ref instanceof BacnetConfirmedRequest)
    {
      maxDataLength = ((BacnetConfirmedRequest) ref).getMaxDataLength()
        // We need to subtract the size of the ReadRangeAck application headers.
        - ReadRangeAck.READ_RANGE_ACK_MAX_APP_HEADER_SIZE
        // We also add back in the length of the unused fields.
        + 3 // we don't use propertyArrayIndex here
        + 5; // we don't use sequenceNumber here
    }

    if (rangeType == RangeReference.BY_POSITION)
    {
      int refNdx = (int)ref.getReferenceIndex();
      int count = ref.getCount();

      // sanity check on refNdx - should we throw an error/reject here?
      if ((refNdx > len) || (refNdx < 1))
      {
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.emptyBitString(3),
                                0,
                                new byte[0]);
      }

      Array<Integer> a = new Array<>(Integer.class);
      int itemsFound = 0;

      if (count > 0)
      {
        // Count is positive: Search from refNdx to end,
        // until we find (count) items.
        for (int i = refNdx - 1; i < len && itemsFound < count; i++)
        {
          a.add(list[i]);
          itemsFound++;
        }

        // Set firstItem result flag.
        if (refNdx == 1)
        {
          rflags[0] = true;
        }
        // Set lastItem flag temporarily - adjust later if needed.
        if ((refNdx + count - 1) >= len)
        {
          rflags[1] = true;
        }
      }
      else if (count < 0)
      {
        // Count is negative: Search from refNdx to beginning,
        // until we find (-count) items.
        count = -count;
        for (int i = refNdx - 1; i >= 0 && itemsFound < count; i--)
        {
          a.add(list[i]);
          itemsFound++;
        }

        // Reverse the array because we need to return the items
        // in their natural order.
        a = a.reverse();

        // Set firstItem result flag.
        if ((refNdx - count) <= 0)
        {
          rflags[0] = true;
        }
        // Set lastItem flag temporarily - adjust later if needed.
        if (refNdx == len)
        {
          rflags[1] = true;
        }
      }
      else
      {
        return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                                BBacnetErrorCode.INCONSISTENT_PARAMETERS);
      }

      // Iterate through the found items until we have written
      // them all, or until we don't have any more room in the
      // outgoing packet.
      Iterator<Integer> it = a.iterator();
      int itemCount = 0;
      synchronized (asnOut)
      {
        asnOut.reset();
        if (maxDataLength > 0)
        {
          while (it.hasNext())
          {
            if ((maxDataLength - asnOut.size()) < maxEncodedSize)
            {
              rflags[1] = false;
              break;
            }
            asnOut.writeUnsignedInteger(it.next().intValue());
            itemCount++;
          }
        }
        else
        {
          itemCount = itemsFound;
          while (it.hasNext())
          {
            asnOut.writeUnsignedInteger(it.next().intValue());
          }
        }

        // Set the moreItems result flag.
        if (itemCount < itemsFound)
        {
          rflags[2] = true;
        }

        // Return the ack.
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.make(rflags),
                                itemCount,
                                asnOut.toByteArray());
      }
    }
    else if (rangeType == NOT_USED)
    {
      rflags[0] = false;
      int itemCount = 0;
      synchronized (asnOut)
      {
        asnOut.reset();

        if (maxDataLength > 0)
        {
          for (int i = 0; i < len; i++)
          {
            asnOut.writeUnsignedInteger(list[i].intValue());
            itemCount++;
            if ((maxDataLength - asnOut.size()) < maxEncodedSize)
            {
              break;
            }
          }
          if (itemCount>0)
          {
            rflags[0] = true;
          }
          if (itemCount>0 && itemCount==len)
          {
            rflags[1] = true;
          }
        }
        else
        {
          itemCount = len;
          for (int i = 0; i < len; i++)
          {
            asnOut.writeUnsignedInteger(list[i].intValue());
          }

          if (itemCount > 0)
          {
            rflags[0] = true;
          }
          if (itemCount >0 && itemCount == len)
          {
            rflags[1] = true;
          }
        }

        // Set the moreItems result flag.
        if (itemCount < len)
        {
          rflags[2] = true;
        }

        // Return the ack.
        return new ReadRangeAck(getObjectId(),
                                ref.getPropertyId(),
                                NOT_USED,
                                BBacnetBitString.make(rflags),
                                itemCount,
                                asnOut.toByteArray());
      }
    }
    else
    {
      return new ReadRangeAck(BBacnetErrorClass.SERVICES,
                              BBacnetErrorCode.INCONSISTENT_PARAMETERS);
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
    v.add(BBacnetPropertyIdentifier.numberOfStates);
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
    BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
    if (r != null)
    {
      v.add(BBacnetPropertyIdentifier.stateText);
    }
  }

  /**
   * Override point for subclasses to provide additional configuration
   * constraints to allow point export.  Default implementation returns true.
   *
   * @return true if configuration is ok, false otherwise.
   */
  @Override
  protected final boolean checkPointConfiguration()
  {
    BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
    if (r != null)
    {
      int[] ords = r.getOrdinals();
      if (ords.length > 0)
      {
        // First state must be 1.
        if (ords[0] != 1)
        {
          setFaultCause("Range must be 1-N for export to BACnet.");
          return false;
        }

        // States must be contiguous.
        for (int i = 0; i < ords.length; i++)
        {
          if (ords[i] != (i + 1))
          {
            setFaultCause("State Range supports only contiguous ordinals.");
            return false;
          }
        }
      }
    }

    return true;
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
    BStatusEnum se = ((BEnumPoint)getPoint()).getOut();
    BStatus s = se.getStatus();
    if (s.isNull())
    {
      setReliability(BBacnetReliability.unreliableOther);
      setFaultCause("Invalid value for BACnet Object:" + se);
      setStatus(BStatus.makeFault(getStatus(), true));
    }
    else if (s.isFault())
    {
      //Refer BACNet spec 135-2016, 13.4.5
      setReliability(BBacnetReliability.multiStateFault);
    }
    else if (s.isDown())
    {
      setReliability(BBacnetReliability.communicationFailure);
    }
    else
    {
      int pv = se.getValue().getOrdinal();
      BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
      if (r == null)
      {
        setReliability(BBacnetReliability.unreliableOther);
        setStatus(BStatus.makeFault(getStatus(), true));
        setFaultCause(lex.getText("export.configurationFault"));
        return;
      }
      if ((pv == 0) || !r.isOrdinal(pv))
      {
        setReliability(BBacnetReliability.unreliableOther);
        setFaultCause("Value out of range:" + pv);
        setStatus(BStatus.makeFault(getStatus(), true));
        return;
      }
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
   * Get the enum range values from the passed byte array
   * @param val
   * @return enumRange wrapper
   * @throws AsnException
   */
  protected static EnumRangeWrapper getWritableEnumRange(byte[] val, BEnumRange tagRange, boolean skipOrdinals) throws AsnException
  {
    // Vector is legacy class as of 1.7. Using Synchronized Collections or CopyOnWriteArrayList
    List<BBacnetUnsigned> alarmOrdinalList;
    synchronized (asnIn)
    {
      asnIn.setBuffer(val);
      int tag = asnIn.peekTag();
      if (skipOrdinals)
      {
        alarmOrdinalList = Collections.synchronizedList(new LinkedList<>());
        for (int ordinal : tagRange.getOrdinals())
        {
          alarmOrdinalList.add(BBacnetUnsigned.make(ordinal));
        }
        while (tag != AsnInput.END_OF_DATA)
        {
          BBacnetUnsigned excludeUnsigned = asnIn.readUnsigned();
          int excludeOrdinal = excludeUnsigned.getInt();
          if (!tagRange.isOrdinal(excludeOrdinal))
          {
            log.warning("Invalid ordinal value : " + excludeOrdinal);
            return EnumRangeWrapper.make(BEnumRange.DEFAULT, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                            BBacnetErrorCode.VALUE_OUT_OF_RANGE));
          }
          else
          {
            alarmOrdinalList.remove(excludeUnsigned);
          }
          tag = asnIn.peekTag();
        }
      }
      else
      {
        alarmOrdinalList = Collections.synchronizedList(new ArrayList<>());
        while (tag != AsnInput.END_OF_DATA)
        {
          alarmOrdinalList.add(asnIn.readUnsigned());
          tag = asnIn.peekTag();
        }
      }
    }

    return makeEnumRange(tagRange, alarmOrdinalList);
  }

  /**
   * Make the enum range using the passed ordinal list
   * In case of an error the EnumRangeWrapper encapsulates an error type.
   * @param tagRange
   * @param ordinalList
   * @return
   */
  private static EnumRangeWrapper makeEnumRange(BEnumRange tagRange, List<BBacnetUnsigned> ordinalList)
  {
    Iterator<BBacnetUnsigned> it = ordinalList.iterator();
    int size = ordinalList.size();
    int[] ordinals = new int[size];
    String[] tags = new String[size];
    int counter = 0;
    while (it.hasNext())
    {
      BBacnetUnsigned bacnetUnsigned = it.next();
      int ordinal = bacnetUnsigned.getInt();
      if (tagRange.isOrdinal(ordinal))
      {
        ordinals[counter] = ordinal;
        tags[counter++] = tagRange.getTag(ordinal);
      }
      else
      {
        log.warning("Invalid ordinal value: " + ordinal);
        return EnumRangeWrapper.make(BEnumRange.DEFAULT, new NErrorType(BBacnetErrorClass.PROPERTY,
                                                                        BBacnetErrorCode.VALUE_OUT_OF_RANGE));
      }
    }

    BEnumRange enumRange = BEnumRange.make(ordinals, tags);
    return EnumRangeWrapper.make(enumRange, null);
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
    BStatusValue sv = new BStatusEnum(((BEnumPoint)getPoint()).getOut().getValue());
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

    return ((BStatusEnum)currentValue).getEnum().getOrdinal() != ((BStatusEnum)covValue).getEnum().getOrdinal();
  }

  /**
   * Check for Cov notification.
   * Multistate points check if the point's current value is different
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

    int currentValue = ((BEnumPoint)pt).getEnum().getOrdinal();
    int covValue = ((BIEnum)covSub.getLastValue()).getEnum().getOrdinal();
    return currentValue != covValue;
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Get the Status_Flags property from the BStatus
   * of the parent point.
   */
  @Override
  BStatus getStatusFlags()
  {
    int status = super.getStatusFlags().getBits();
    BEnumPoint pt = (BEnumPoint)getPoint();
    if (pt.getOut().getValue().getOrdinal() <= 0)
    {
      status |= BStatus.FAULT;
    }
    return BStatus.make(status);
  }

  /**
   * Generate the array of tags given the range and the list of ordinals.
   */
  private static String[] getTags(BEnumRange r)
  {
    int[] ordinals = r.getOrdinals();
    String[] tags = new String[ordinals.length];
    for (int i = 0; i < tags.length; i++)
    {
      tags[i] = r.getTag(ordinals[i]);
    }
    return tags;
  }

  /**
   * Find the appropriate entry in the ordinals array with the given index number.
   */
  private static int findIndex(int ndx, int[] ordinals)
  {
    for (int i = 0; i < ordinals.length; i++)
    {
      if (ordinals[i] == ndx)
      {
        return i;
      }
    }
    return -1;  // Should never get here!
  }

  private PropertyValue readStateText(int ndx)
  {
    BEnumRange range = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
    if (range == null)
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.STATE_TEXT,
        ndx,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY));
    }

    int length = range.getOrdinals().length;
    if (ndx == NOT_USED)
    {
      AsnOutputStream asnOut = AsnOutputStream.make();
      try
      {
        for (int i = 1; i <= length; i++)
        {
          asnOut.writeCharacterString(SlotPath.unescape(range.getTag(i)));
        }
        return new NReadPropertyResult(
          BBacnetPropertyIdentifier.STATE_TEXT,
          NOT_USED,
          asnOut.toByteArray());
      }
      finally
      {
        asnOut.release();
      }
    }
    else if (ndx == 0)
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.STATE_TEXT,
        0,
        AsnUtil.toAsnUnsigned(length));
    }
    else if (ndx >= 1 && ndx <= length)
    {
      try
      {
        return new NReadPropertyResult(
          BBacnetPropertyIdentifier.STATE_TEXT,
          ndx,
          AsnUtil.toAsnCharacterString(SlotPath.unescape(range.getTag(ndx))));
      }
      catch (Exception e)
      {
        return new NReadPropertyResult(
          BBacnetPropertyIdentifier.STATE_TEXT,
          ndx,
          new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
      }
    }
    else
    {
      return new NReadPropertyResult(
        BBacnetPropertyIdentifier.STATE_TEXT,
        ndx,
        new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.INVALID_ARRAY_INDEX));
    }
  }

  private NErrorType writeStateText(int ndx, byte[] val, BEnumPoint pt)
    throws BacnetException
  {
    BFacets f = pt.getFacets();
    BEnumRange r = (BEnumRange)f.getFacet(BFacets.RANGE);
    if (r != null)
    {
      try
      {
        switch (ndx)
        {
          case 0:
            return new NErrorType(BBacnetErrorClass.PROPERTY,
                                  BBacnetErrorCode.INVALID_ARRAY_INDEX);

          case -1:
            ArrayList<String> v = new ArrayList<>();
            synchronized (asnIn)
            {
              asnIn.setBuffer(val);
              int tag = asnIn.peekTag();
              while (tag != AsnInput.END_OF_DATA)
              {
                v.add(asnIn.readCharacterString());
                tag = asnIn.peekTag();
              }
            }
            if (v.size() != r.getOrdinals().length)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }
            int[] newOrdinals = new int[v.size()];
            String[] newTags = new String[v.size()];

            for (int i = 0; i < newOrdinals.length; i++)
            {
              newOrdinals[i] = i + 1;
              newTags[i] = SlotPath.escape(v.get(i));
              if (newTags[i].length() == 0)
              {
                return new NErrorType(BBacnetErrorClass.PROPERTY,
                                      BBacnetErrorCode.VALUE_OUT_OF_RANGE);
              }
            }

            BEnumRange range;
            try
            {
              range = BEnumRange.make(newOrdinals, newTags);
            }
            catch (IllegalArgumentException iae)
            {
              //duplicate tag
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

            pt.set(BControlPoint.facets,
                   BFacets.make(f, BFacets.RANGE, range),
                   BLocalBacnetDevice.getBacnetContext());
            return null;

          default:
            int[] ordinals = r.getOrdinals();
            String[] tags = getTags(r);
            if ((ndx < 1) || (ndx > ordinals.length))
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }
            int i = findIndex(ndx, ordinals);
            if (i < 0)
            {
              log.severe("MultiStatePointDescriptor.writeStateText: Index not found in ordinal list: " + ndx);
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.INVALID_ARRAY_INDEX);
            }

            tags[i] = SlotPath.escape(AsnUtil.fromAsnCharacterString(val));
            if (tags[i].length() == 0)
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

            try
            {
              range = BEnumRange.make(ordinals, tags);
            }
            catch (IllegalArgumentException iae) //duplicate tag
            {
              return new NErrorType(BBacnetErrorClass.PROPERTY,
                                    BBacnetErrorCode.VALUE_OUT_OF_RANGE);
            }

            pt.set(BControlPoint.facets,
                   BFacets.make(f, BFacets.RANGE, range),
                   BLocalBacnetDevice.getBacnetContext());

            return null;
        }
      }
      catch (PermissionException e)
      {
        log.warning("PermissionException writing stateText in object " + getObjectId() + ": " + e);
        return new NErrorType(BBacnetErrorClass.PROPERTY,
                              BBacnetErrorCode.WRITE_ACCESS_DENIED);
      }
    }
    else
    {
      return new NErrorType(BBacnetErrorClass.PROPERTY,
                            BBacnetErrorCode.UNKNOWN_PROPERTY);
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

  private static final BIcon icon = BIcon.make(BIcon.std("control/enumPoint.png"), BIcon.std("badges/export.png"));
}
