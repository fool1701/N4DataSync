/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetDeviceObjectPropertyReference;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetTrendLogMultiple augments BBacnetTrendLog.
 * <p>
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 09 Sep 2009
 * @since Niagara 3.5
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG_MULTIPLE)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.TREND_LOG_MULTIPLE, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "logDeviceObjectProperty",
  type = "BBacnetArray",
  defaultValue = "new BBacnetArray(BBacnetDeviceObjectPropertyReference.TYPE)",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY, ASN_BACNET_ARRAY)")
)
public class BBacnetTrendLogMultiple
  extends BBacnetTrendLog
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetTrendLogMultiple(2426414704)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.TREND_LOG_MULTIPLE), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.TREND_LOG_MULTIPLE, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "logDeviceObjectProperty"

  /**
   * Slot for the {@code logDeviceObjectProperty} property.
   * @see #getLogDeviceObjectProperty
   * @see #setLogDeviceObjectProperty
   */
  public static final Property logDeviceObjectProperty = newProperty(Flags.READONLY, new BBacnetArray(BBacnetDeviceObjectPropertyReference.TYPE), makeFacets(BBacnetPropertyIdentifier.LOG_DEVICE_OBJECT_PROPERTY, ASN_BACNET_ARRAY));

  /**
   * Get the {@code logDeviceObjectProperty} property.
   * @see #logDeviceObjectProperty
   */
  public BBacnetArray getLogDeviceObjectProperty() { return (BBacnetArray)get(logDeviceObjectProperty); }

  /**
   * Set the {@code logDeviceObjectProperty} property.
   * @see #logDeviceObjectProperty
   */
  public void setLogDeviceObjectProperty(BBacnetArray v) { set(logDeviceObjectProperty, v, null); }

  //endregion Property "logDeviceObjectProperty"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetTrendLogMultiple.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
