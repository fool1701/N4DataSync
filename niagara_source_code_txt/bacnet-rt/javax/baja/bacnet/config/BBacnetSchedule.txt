/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.enums.BBacnetReliability;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.BacnetBitStringUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/10/01 9:26:02 AM$
 * @creation 30 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.SCHEDULE)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.SCHEDULE, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "presentValue",
  type = "BBacnetAny",
  defaultValue = "new BBacnetAny()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_ANY)")
)
/*
 These facets are applied against the presentValue property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
@NiagaraProperty(
  name = "effectivePeriod",
  type = "BBacnetDateRange",
  defaultValue = "new BBacnetDateRange()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.EFFECTIVE_PERIOD, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "listOfObjectPropertyReferences",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetDeviceObjectPropertyReference.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES, ASN_BACNET_LIST)")
)
@NiagaraProperty(
  name = "priorityForWriting",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(16)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, ASN_UNSIGNED)")
)
public class BBacnetSchedule
  extends BBacnetCreatableObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetSchedule(798000552)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.SCHEDULE), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.SCHEDULE, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, new BBacnetAny(), makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_ANY));

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public BBacnetAny getPresentValue() { return (BBacnetAny)get(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(BBacnetAny v) { set(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the presentValue property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the presentValue property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the presentValue property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "effectivePeriod"

  /**
   * Slot for the {@code effectivePeriod} property.
   * @see #getEffectivePeriod
   * @see #setEffectivePeriod
   */
  public static final Property effectivePeriod = newProperty(0, new BBacnetDateRange(), makeFacets(BBacnetPropertyIdentifier.EFFECTIVE_PERIOD, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code effectivePeriod} property.
   * @see #effectivePeriod
   */
  public BBacnetDateRange getEffectivePeriod() { return (BBacnetDateRange)get(effectivePeriod); }

  /**
   * Set the {@code effectivePeriod} property.
   * @see #effectivePeriod
   */
  public void setEffectivePeriod(BBacnetDateRange v) { set(effectivePeriod, v, null); }

  //endregion Property "effectivePeriod"

  //region Property "listOfObjectPropertyReferences"

  /**
   * Slot for the {@code listOfObjectPropertyReferences} property.
   * @see #getListOfObjectPropertyReferences
   * @see #setListOfObjectPropertyReferences
   */
  public static final Property listOfObjectPropertyReferences = newProperty(0, new BBacnetListOf(BBacnetDeviceObjectPropertyReference.TYPE), makeFacets(BBacnetPropertyIdentifier.LIST_OF_OBJECT_PROPERTY_REFERENCES, ASN_BACNET_LIST));

  /**
   * Get the {@code listOfObjectPropertyReferences} property.
   * @see #listOfObjectPropertyReferences
   */
  public BBacnetListOf getListOfObjectPropertyReferences() { return (BBacnetListOf)get(listOfObjectPropertyReferences); }

  /**
   * Set the {@code listOfObjectPropertyReferences} property.
   * @see #listOfObjectPropertyReferences
   */
  public void setListOfObjectPropertyReferences(BBacnetListOf v) { set(listOfObjectPropertyReferences, v, null); }

  //endregion Property "listOfObjectPropertyReferences"

  //region Property "priorityForWriting"

  /**
   * Slot for the {@code priorityForWriting} property.
   * @see #getPriorityForWriting
   * @see #setPriorityForWriting
   */
  public static final Property priorityForWriting = newProperty(0, BBacnetUnsigned.make(16), makeFacets(BBacnetPropertyIdentifier.PRIORITY_FOR_WRITING, ASN_UNSIGNED));

  /**
   * Get the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public BBacnetUnsigned getPriorityForWriting() { return (BBacnetUnsigned)get(priorityForWriting); }

  /**
   * Set the {@code priorityForWriting} property.
   * @see #priorityForWriting
   */
  public void setPriorityForWriting(BBacnetUnsigned v) { set(priorityForWriting, v, null); }

  //endregion Property "priorityForWriting"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetSchedule()
  {
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context))
      .append(nameContext.equals(context) ? '_' : ':')
      .append(getPresentValue().toString(context));
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Started.
   */
  public void started()
    throws Exception
  {
    super.started();

    if (device().getProtocolRevision() >= 4)
    {
      if (get(BBacnetPropertyIdentifier.scheduleDefault.getTag()) == null)
      {
        add(BBacnetPropertyIdentifier.scheduleDefault.getTag(), new BBacnetAny(), 0,
          makeFacets(BBacnetPropertyIdentifier.SCHEDULE_DEFAULT, ASN_ANY),
          null);
      }

      if (get(BBacnetPropertyIdentifier.statusFlags.getTag()) == null)
      {
        add(BBacnetPropertyIdentifier.statusFlags.getTag(), BBacnetBitString.emptyBitString(
          BacnetBitStringUtil.getBitStringLength("BacnetStatusFlags")), 0,
          makeFacets(BBacnetPropertyIdentifier.STATUS_FLAGS, ASN_BIT_STRING,
            BacnetBitStringUtil.BACNET_STATUS_FLAGS_MAP),
          null);
      }

      if (get(BBacnetPropertyIdentifier.reliability.getTag()) == null)
      {
        add(BBacnetPropertyIdentifier.reliability.getTag(), BDynamicEnum.make(0, BEnumRange.make(BBacnetReliability.TYPE)), 0,
          makeFacets(BBacnetPropertyIdentifier.RELIABILITY, ASN_ENUMERATED),
          null);
      }

      if (get(BBacnetPropertyIdentifier.outOfService.getTag()) == null)
      {
        add(BBacnetPropertyIdentifier.outOfService.getTag(), BBoolean.FALSE, 0,
          makeFacets(BBacnetPropertyIdentifier.OUT_OF_SERVICE, ASN_BOOLEAN),
          null);
      }

      // Rebuild the polled properties list to include these ones.
      buildPolledProperties();
    }
  }

  /**
   * Property changed.
   public void changed(Property p, Context cx)
   {
   super.changed(p,cx);
   if (!isRunning()) return;
   if (p == facets) return;
   }
   */

  /**
   * Apply the "facets" property to the "presentValue" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == presentValue) return getFacets();
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  /**
   * Subclasses that have a present value property should
   * override this method and return this property.  The
   * default returns null.
   */
  public Property getPresentValueProperty()
  {
    return presentValue;
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////

  protected void addObjectInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    addPriorityForWriting(getPriorityForWriting(), listOfInitialValues);
    Property scheduleDefault = getProperty("scheduleDefault");
      if(scheduleDefault != null)
        addScheduleDefault(scheduleDefault, listOfInitialValues);
    addListOfObjectPropertyReferences(listOfObjectPropertyReferences, listOfInitialValues);
  }
}
