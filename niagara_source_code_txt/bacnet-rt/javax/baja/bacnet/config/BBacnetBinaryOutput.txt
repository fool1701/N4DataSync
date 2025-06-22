/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetPriorityValue;
import javax.baja.bacnet.enums.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision: 9$ $Date: 12/11/01 2:48:34 PM$
 * @creation 18 Jul 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_OUTPUT)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.BINARY_OUTPUT, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
/*
 polarity describes the relationship between the physical state
 of the point and the logical state represented by the presentValue
 property.
 */
@NiagaraProperty(
  name = "polarity",
  type = "BBacnetPolarity",
  defaultValue = "BBacnetPolarity.normal",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.POLARITY, ASN_ENUMERATED)")
)
@NiagaraProperty(
  name = "priorityArray",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetPriorityValue.TYPE, 16)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ASN_BACNET_ARRAY)")
)
@NiagaraProperty(
  name = "relinquishDefault",
  type = "BBacnetBinaryPv",
  defaultValue = "BBacnetBinaryPv.inactive",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.RELINQUISH_DEFAULT, ASN_ENUMERATED)")
)
public class BBacnetBinaryOutput
  extends BBacnetBinary
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetBinaryOutput(4092360992)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_OUTPUT), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.BINARY_OUTPUT, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "polarity"

  /**
   * Slot for the {@code polarity} property.
   * polarity describes the relationship between the physical state
   * of the point and the logical state represented by the presentValue
   * property.
   * @see #getPolarity
   * @see #setPolarity
   */
  public static final Property polarity = newProperty(0, BBacnetPolarity.normal, makeFacets(BBacnetPropertyIdentifier.POLARITY, ASN_ENUMERATED));

  /**
   * Get the {@code polarity} property.
   * polarity describes the relationship between the physical state
   * of the point and the logical state represented by the presentValue
   * property.
   * @see #polarity
   */
  public BBacnetPolarity getPolarity() { return (BBacnetPolarity)get(polarity); }

  /**
   * Set the {@code polarity} property.
   * polarity describes the relationship between the physical state
   * of the point and the logical state represented by the presentValue
   * property.
   * @see #polarity
   */
  public void setPolarity(BBacnetPolarity v) { set(polarity, v, null); }

  //endregion Property "polarity"

  //region Property "priorityArray"

  /**
   * Slot for the {@code priorityArray} property.
   * @see #getPriorityArray
   * @see #setPriorityArray
   */
  public static final Property priorityArray = newProperty(0, new BBacnetArray(BBacnetPriorityValue.TYPE, 16), makeFacets(BBacnetPropertyIdentifier.PRIORITY_ARRAY, ASN_BACNET_ARRAY));

  /**
   * Get the {@code priorityArray} property.
   * @see #priorityArray
   */
  public BBacnetArray getPriorityArray() { return (BBacnetArray)get(priorityArray); }

  /**
   * Set the {@code priorityArray} property.
   * @see #priorityArray
   */
  public void setPriorityArray(BBacnetArray v) { set(priorityArray, v, null); }

  //endregion Property "priorityArray"

  //region Property "relinquishDefault"

  /**
   * Slot for the {@code relinquishDefault} property.
   * @see #getRelinquishDefault
   * @see #setRelinquishDefault
   */
  public static final Property relinquishDefault = newProperty(0, BBacnetBinaryPv.inactive, makeFacets(BBacnetPropertyIdentifier.RELINQUISH_DEFAULT, ASN_ENUMERATED));

  /**
   * Get the {@code relinquishDefault} property.
   * @see #relinquishDefault
   */
  public BBacnetBinaryPv getRelinquishDefault() { return (BBacnetBinaryPv)get(relinquishDefault); }

  /**
   * Set the {@code relinquishDefault} property.
   * @see #relinquishDefault
   */
  public void setRelinquishDefault(BBacnetBinaryPv v) { set(relinquishDefault, v, null); }

  //endregion Property "relinquishDefault"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinaryOutput.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   */
  public BBacnetBinaryOutput()
  {
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Apply the "facets" property to the "presentValue" property.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == priorityArray) return getFacets();
    if (slot == relinquishDefault) return getFacets();
//    if (slot.equals(priorityArray)) return getFacets();
    return super.getSlotFacets(slot);
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

}
