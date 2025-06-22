/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.datatypes.BBacnetUnsigned;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 25 Jul 2006
 * @since Niagara 3.2
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.AVERAGING)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.AVERAGING, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
/*
 These facets are applied against the minimumValue, averageValue,
 and maximumValue properties.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "minimumValue",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MINIMUM_VALUE, ASN_REAL)")
)
@NiagaraProperty(
  name = "averageValue",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.AVERAGE_VALUE, ASN_REAL)")
)
@NiagaraProperty(
  name = "maximumValue",
  type = "float",
  defaultValue = "0",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MAXIMUM_VALUE, ASN_REAL)")
)
@NiagaraProperty(
  name = "attemptedSamples",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ATTEMPTED_SAMPLES, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "validSamples",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.VALID_SAMPLES, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "objectPropertyReference",
  type = "BBacnetDeviceObjectPropertyReference",
  defaultValue = "new BBacnetDeviceObjectPropertyReference()",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE, ASN_CONSTRUCTED_DATA)")
)
@NiagaraProperty(
  name = "windowInterval",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.WINDOW_INTERVAL, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "windowSamples",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.WINDOW_SAMPLES, ASN_UNSIGNED)")
)
public class BBacnetAveraging
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetAveraging(4148926131)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.AVERAGING), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.AVERAGING, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the minimumValue, averageValue,
   * and maximumValue properties.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(Flags.READONLY, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the minimumValue, averageValue,
   * and maximumValue properties.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the minimumValue, averageValue,
   * and maximumValue properties.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "minimumValue"

  /**
   * Slot for the {@code minimumValue} property.
   * @see #getMinimumValue
   * @see #setMinimumValue
   */
  public static final Property minimumValue = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.MINIMUM_VALUE, ASN_REAL));

  /**
   * Get the {@code minimumValue} property.
   * @see #minimumValue
   */
  public float getMinimumValue() { return getFloat(minimumValue); }

  /**
   * Set the {@code minimumValue} property.
   * @see #minimumValue
   */
  public void setMinimumValue(float v) { setFloat(minimumValue, v, null); }

  //endregion Property "minimumValue"

  //region Property "averageValue"

  /**
   * Slot for the {@code averageValue} property.
   * @see #getAverageValue
   * @see #setAverageValue
   */
  public static final Property averageValue = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.AVERAGE_VALUE, ASN_REAL));

  /**
   * Get the {@code averageValue} property.
   * @see #averageValue
   */
  public float getAverageValue() { return getFloat(averageValue); }

  /**
   * Set the {@code averageValue} property.
   * @see #averageValue
   */
  public void setAverageValue(float v) { setFloat(averageValue, v, null); }

  //endregion Property "averageValue"

  //region Property "maximumValue"

  /**
   * Slot for the {@code maximumValue} property.
   * @see #getMaximumValue
   * @see #setMaximumValue
   */
  public static final Property maximumValue = newProperty(0, 0, makeFacets(BBacnetPropertyIdentifier.MAXIMUM_VALUE, ASN_REAL));

  /**
   * Get the {@code maximumValue} property.
   * @see #maximumValue
   */
  public float getMaximumValue() { return getFloat(maximumValue); }

  /**
   * Set the {@code maximumValue} property.
   * @see #maximumValue
   */
  public void setMaximumValue(float v) { setFloat(maximumValue, v, null); }

  //endregion Property "maximumValue"

  //region Property "attemptedSamples"

  /**
   * Slot for the {@code attemptedSamples} property.
   * @see #getAttemptedSamples
   * @see #setAttemptedSamples
   */
  public static final Property attemptedSamples = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.ATTEMPTED_SAMPLES, ASN_UNSIGNED));

  /**
   * Get the {@code attemptedSamples} property.
   * @see #attemptedSamples
   */
  public BBacnetUnsigned getAttemptedSamples() { return (BBacnetUnsigned)get(attemptedSamples); }

  /**
   * Set the {@code attemptedSamples} property.
   * @see #attemptedSamples
   */
  public void setAttemptedSamples(BBacnetUnsigned v) { set(attemptedSamples, v, null); }

  //endregion Property "attemptedSamples"

  //region Property "validSamples"

  /**
   * Slot for the {@code validSamples} property.
   * @see #getValidSamples
   * @see #setValidSamples
   */
  public static final Property validSamples = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.VALID_SAMPLES, ASN_UNSIGNED));

  /**
   * Get the {@code validSamples} property.
   * @see #validSamples
   */
  public BBacnetUnsigned getValidSamples() { return (BBacnetUnsigned)get(validSamples); }

  /**
   * Set the {@code validSamples} property.
   * @see #validSamples
   */
  public void setValidSamples(BBacnetUnsigned v) { set(validSamples, v, null); }

  //endregion Property "validSamples"

  //region Property "objectPropertyReference"

  /**
   * Slot for the {@code objectPropertyReference} property.
   * @see #getObjectPropertyReference
   * @see #setObjectPropertyReference
   */
  public static final Property objectPropertyReference = newProperty(0, new BBacnetDeviceObjectPropertyReference(), makeFacets(BBacnetPropertyIdentifier.OBJECT_PROPERTY_REFERENCE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code objectPropertyReference} property.
   * @see #objectPropertyReference
   */
  public BBacnetDeviceObjectPropertyReference getObjectPropertyReference() { return (BBacnetDeviceObjectPropertyReference)get(objectPropertyReference); }

  /**
   * Set the {@code objectPropertyReference} property.
   * @see #objectPropertyReference
   */
  public void setObjectPropertyReference(BBacnetDeviceObjectPropertyReference v) { set(objectPropertyReference, v, null); }

  //endregion Property "objectPropertyReference"

  //region Property "windowInterval"

  /**
   * Slot for the {@code windowInterval} property.
   * @see #getWindowInterval
   * @see #setWindowInterval
   */
  public static final Property windowInterval = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.WINDOW_INTERVAL, ASN_UNSIGNED));

  /**
   * Get the {@code windowInterval} property.
   * @see #windowInterval
   */
  public BBacnetUnsigned getWindowInterval() { return (BBacnetUnsigned)get(windowInterval); }

  /**
   * Set the {@code windowInterval} property.
   * @see #windowInterval
   */
  public void setWindowInterval(BBacnetUnsigned v) { set(windowInterval, v, null); }

  //endregion Property "windowInterval"

  //region Property "windowSamples"

  /**
   * Slot for the {@code windowSamples} property.
   * @see #getWindowSamples
   * @see #setWindowSamples
   */
  public static final Property windowSamples = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.WINDOW_SAMPLES, ASN_UNSIGNED));

  /**
   * Get the {@code windowSamples} property.
   * @see #windowSamples
   */
  public BBacnetUnsigned getWindowSamples() { return (BBacnetUnsigned)get(windowSamples); }

  /**
   * Set the {@code windowSamples} property.
   * @see #windowSamples
   */
  public void setWindowSamples(BBacnetUnsigned v) { set(windowSamples, v, null); }

  //endregion Property "windowSamples"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAveraging.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetAveraging()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context));
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

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
    if (slot.equals(minimumValue)) return getFacets();
    if (slot.equals(averageValue)) return getFacets();
    if (slot.equals(maximumValue)) return getFacets();
    if (slot.getName().equals("varianceValue")) return getFacets();
    return super.getSlotFacets(slot);
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

}
