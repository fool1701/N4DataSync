/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/11/01 2:48:34 PM$
 * @creation 14 Feb 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_VALUE)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.BINARY_VALUE, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
public class BBacnetBinaryValue
  extends BBacnetBinary
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetBinaryValue(2388321122)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.BINARY_VALUE), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.BINARY_VALUE, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetBinaryValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   */
  public BBacnetBinaryValue()
  {
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
