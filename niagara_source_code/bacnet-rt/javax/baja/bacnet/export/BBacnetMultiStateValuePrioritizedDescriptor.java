/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import javax.baja.alarm.BIAlarmSource;
import javax.baja.alarm.ext.BAlarmSourceExt;
import javax.baja.alarm.ext.fault.BEnumFaultAlgorithm;
import javax.baja.alarm.ext.offnormal.BEnumChangeOfStateAlgorithm;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetConfirmedServiceChoice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetEventType;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.ChangeListError;
import javax.baja.bacnet.io.ErrorType;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.bacnet.util.EnumRangeWrapper;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.security.PermissionException;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;
import com.tridium.bacnet.services.error.NChangeListError;

/**
 * BBacnetMultiStateValuePrioritizedDescriptor exposes a ControlPoint as a non-commandable Bacnet
 * Multi State Value Object.
 *
 * @author Craig Gemmill on 19 Feb 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "control:EnumWritable"
  )
)
/*
 objectId is the identifier by which this point is known
 to the Bacnet world.
 */
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.MULTI_STATE_VALUE)",
  flags = Flags.DEFAULT_ON_CLONE,
  override = true
)
public class BBacnetMultiStateValuePrioritizedDescriptor
  extends BBacnetMultiStateWritableDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BBacnetMultiStateValuePrioritizedDescriptor(1638044835)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * objectId is the identifier by which this point is known
   * to the Bacnet world.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.MULTI_STATE_VALUE), null);

  //endregion Property "objectId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetMultiStateValuePrioritizedDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

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
      return BBacnetObjectType.getObjectIdFacets(BBacnetObjectType.MULTI_STATE_VALUE);
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
   * BACnet MultistateValue points use a ChangeOfState alarm algorithm.
   *
   * @param ext
   * @return true if valid, otherwise false.
   */
  @Override
  public boolean isValidAlarmExt(BIAlarmSource ext)
  {
    if (ext instanceof BAlarmSourceExt)
    {
      return ((BAlarmSourceExt) ext).getOffnormalAlgorithm() instanceof BEnumChangeOfStateAlgorithm;
    }

    return false;
  }

////////////////////////////////////////////////////////////////
// Bacnet Access
////////////////////////////////////////////////////////////////

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
    v.add(BBacnetPropertyIdentifier.priorityArray);
    v.add(BBacnetPropertyIdentifier.relinquishDefault);
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      v.add(BBacnetPropertyIdentifier.alarmValues);

      if (BBacnetNetwork.bacnet().setAndGetShouldSupportFaults())
      {
        v.add(BBacnetPropertyIdentifier.faultValues);
      }
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
      try
      {
        if (pId == BBacnetPropertyIdentifier.ALARM_VALUES)
        {
          synchronized (asnOut)
          {
            asnOut.reset();
            BEnumChangeOfStateAlgorithm alg = (BEnumChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
            int[] vals = alg.getAlarmValues().getOrdinals();
            for (int i = 0; i < vals.length; i++)
            {
              asnOut.writeUnsignedInteger(vals[i]);
            }
            return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
          }
        }
        else if (pId == BBacnetPropertyIdentifier.FAULT_VALUES)
        {
          if (!BBacnetNetwork.bacnet().setAndGetShouldSupportFaults())
          {
            return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY));
          }

          synchronized (asnOut)
          {
            asnOut.reset();
            BEnumFaultAlgorithm alg = (BEnumFaultAlgorithm)almExt.getFaultAlgorithm();
            int[] validVals = alg.getValidValues().getOrdinals();
            BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
            int[] rangeVals = r.getOrdinals();
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
                asnOut.writeUnsignedInteger(rangeVals[i]);
              }
            }
            return new NReadPropertyResult(pId, ndx, asnOut.toByteArray());
          }
        }
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "AsnException occurred in readOptionalProperty", e);
        return new NReadPropertyResult(pId, ndx, new NErrorType(BBacnetErrorClass.DEVICE,
                                                                BBacnetErrorCode.OPERATIONAL_PROBLEM));
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
        if (pId == BBacnetPropertyIdentifier.ALARM_VALUES)
        {
          BEnumChangeOfStateAlgorithm alg = (BEnumChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
          BEnumRange alarmValueRange = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
          EnumRangeWrapper enumRangeWrapper = getWritableEnumRange(val, alarmValueRange, false);
          if (enumRangeWrapper.getErrorType() == null)
          {
            alg.set(BEnumChangeOfStateAlgorithm.alarmValues,
                    enumRangeWrapper.getEnumRange(),
                    BLocalBacnetDevice.getBacnetContext());
            return null;
          }

          return enumRangeWrapper.getErrorType();
        }
        else if (pId == BBacnetPropertyIdentifier.EVENT_ENABLE)
        {
          almExt.set(BAlarmSourceExt.alarmEnable,
                     BacnetBitStringUtil.getBAlarmTransitionBits(AsnUtil.fromAsnBitString(val)),
                     BLocalBacnetDevice.getBacnetContext());
          return null;
        }
        else if (pId == BBacnetPropertyIdentifier.FAULT_VALUES)
        {
          if (!BBacnetNetwork.bacnet().setAndGetShouldSupportFaults())
          {
            return new NErrorType(BBacnetErrorClass.PROPERTY, BBacnetErrorCode.UNKNOWN_PROPERTY);
          }

          BEnumFaultAlgorithm alg = (BEnumFaultAlgorithm)almExt.getFaultAlgorithm();
          BEnumRange faultValueRange = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
          EnumRangeWrapper enumRangeWrapper = getWritableEnumRange(val, faultValueRange, true);
          if (enumRangeWrapper.getErrorType() == null)
          {
            alg.set(BEnumFaultAlgorithm.validValues,
                    enumRangeWrapper.getEnumRange(),
                    BLocalBacnetDevice.getBacnetContext());
            return null;
          }

          return enumRangeWrapper.getErrorType();
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

    return super.writeOptionalProperty(pId, ndx, val, pri);
  }

  /**
   * Add list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to add any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError addListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      BEnumChangeOfStateAlgorithm alg = (BEnumChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
      int propertyId = propertyValue.getPropertyId();
      if (propertyId == BBacnetPropertyIdentifier.ALARM_VALUES)
      {
        // Check for array index on non-array property.
        if (propertyValue.getPropertyArrayIndex() >= 0)
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                      0);
        }

        return addAlarmValues(propertyValue, alg);
      }
      else if (propertyId == BBacnetPropertyIdentifier.FAULT_VALUES)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                    0);
      }
    }

    return super.addListElements(propertyValue);
  }

  /**
   * Remove list elements.
   *
   * @param propertyValue the PropertyValue containing the propertyId,
   *                      propertyArrayIndex, and the encoded list elements.
   * @return a ChangeListError if unable to remove any elements,
   * or null if ok.
   */
  @Override
  public final ChangeListError removeListElements(PropertyValue propertyValue)
    throws BacnetException
  {
    BAlarmSourceExt almExt = getAlarmExt();
    if (almExt != null)
    {
      BEnumChangeOfStateAlgorithm alg = (BEnumChangeOfStateAlgorithm)almExt.getOffnormalAlgorithm();
      int propertyId = propertyValue.getPropertyId();
      if (propertyId == BBacnetPropertyIdentifier.ALARM_VALUES)
      {
        // Check for array index on non-array property.
        if (propertyValue.getPropertyArrayIndex() >= 0)
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.PROPERTY,
                                                     BBacnetErrorCode.PROPERTY_IS_NOT_AN_ARRAY),
                                      0);
        }

        return removeAlarmValues(propertyValue, alg);
      }
      else if (propertyId == BBacnetPropertyIdentifier.FAULT_VALUES)
      {
        return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                    new NErrorType(BBacnetErrorClass.PROPERTY,
                                                   BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                    0);
      }
    }

    return super.removeListElements(propertyValue);
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  private ChangeListError addAlarmValues(PropertyValue propertyValue,
                                         BEnumChangeOfStateAlgorithm alg)
  {
    // First, read from the encoded value all of the elements to be added.
    ArrayList<BBacnetUnsigned> v = new ArrayList<>();
    int ffen = 1; // first failed element number (1-based)
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          v.add(asnIn.readUnsigned());
          ffen++;
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
    BEnumRange almVals = alg.getAlarmValues();
    Array<BBacnetUnsigned> a = new Array<>(BBacnetUnsigned.class); // new list of alarm ordinals
    int[] ordinals = almVals.getOrdinals();

    // First prepopulate the new list with what's already there
    for (int i = 0; i < ordinals.length; i++)
    {
      a.add(BBacnetUnsigned.make(ordinals[i]));
    }

    // Now go through and add any of the new ones that we don't already
    // have into the new list.
    try
    {
      for (int i = 0; i < v.size(); i++)
      {
        BBacnetUnsigned u = v.get(i);
        int newOrdinal = u.getInt();
        boolean found = false;
        for (int j = 0; j < ordinals.length; j++)
        {
          if (ordinals[j] == newOrdinal)
          {
            found = true;
            break;
          }
        }
        if (!found)
        {
          a.add(u);
        }
      }

      // Now make the new range & set the alarmValues
      BBacnetUnsigned[] newUVals = a.trim();
      int[] newOrdinals = new int[newUVals.length];
      String[] newTags = new String[newUVals.length];
      for (int i = 0; i < newOrdinals.length; i++)
      {
        newOrdinals[i] = newUVals[i].getInt();
        newTags[i] = r.getTag(newOrdinals[i]);
      }
      alg.set(BEnumChangeOfStateAlgorithm.alarmValues,
              BEnumRange.make(newOrdinals, newTags),
              BLocalBacnetDevice.getBacnetContext());

      return null;
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException adding elements to alarmValues in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.ADD_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
  }

  private ChangeListError removeAlarmValues(PropertyValue propertyValue,
                                            BEnumChangeOfStateAlgorithm alg)
  {
    // First, read from the encoded value all of the elements to be removed.
    ArrayList<BBacnetUnsigned> v = new ArrayList<>();
    // first failed element number (1-based)
    int ffen = 1;
    try
    {
      synchronized (asnIn)
      {
        asnIn.setBuffer(propertyValue.getPropertyValue());
        int tag = asnIn.peekTag();
        while (tag != AsnInput.END_OF_DATA)
        {
          v.add(asnIn.readUnsigned());
          ffen++;
          tag = asnIn.peekTag();
        }
      }
    }
    catch (AsnException e)
    {
      logger.log(Level.SEVERE, "AsnException occurred in removeAlarmValues", e);
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.INVALID_DATA_TYPE),
                                  ffen);
    }

    BEnumRange r = (BEnumRange)getPoint().getFacets().getFacet(BFacets.RANGE);
    BEnumRange almVals = alg.getAlarmValues();
    Array<BBacnetUnsigned> a = new Array<>(BBacnetUnsigned.class); // new list of alarm ordinals
    int[] ordinals = almVals.getOrdinals();

    // First prepopulate the new list with what's already there
    for (int i = 0; i < ordinals.length; i++)
    {
      a.add(BBacnetUnsigned.make(ordinals[i]));
    }

    // Now go through and remove any of the entries that we
    // have from the list.  If an entry is not found, complain.
    // We do not have to roll back the changes since this is done
    // with a separate object and not the database component.
    try
    {
      for (ffen = 1; ffen <= v.size(); ffen++)
      {
        BBacnetUnsigned u = v.get(ffen - 1);
        if (a.contains(u))
        {
          a.remove(u);
        }
        else
        {
          return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                      new NErrorType(BBacnetErrorClass.SERVICES,
                                                     BBacnetErrorCode.LIST_ELEMENT_NOT_FOUND),
                                      ffen);
        }
      }

      // Now make the new range & set the alarmValues
      BBacnetUnsigned[] newUVals = a.trim();
      int[] newOrdinals = new int[newUVals.length];
      String[] newTags = new String[newUVals.length];
      for (int i = 0; i < newOrdinals.length; i++)
      {
        newOrdinals[i] = newUVals[i].getInt();
        newTags[i] = r.getTag(newOrdinals[i]);
      }
      alg.set(BEnumChangeOfStateAlgorithm.alarmValues,
              BEnumRange.make(newOrdinals, newTags),
              BLocalBacnetDevice.getBacnetContext());

      return null;
    }
    catch (PermissionException e)
    {
      log.warning("PermissionException removing elements from alarmValues in object " + getObjectId() + ": " + e);
      return new NChangeListError(BacnetConfirmedServiceChoice.REMOVE_LIST_ELEMENT,
                                  new NErrorType(BBacnetErrorClass.PROPERTY,
                                                 BBacnetErrorCode.WRITE_ACCESS_DENIED),
                                  0);
    }
  }
}
