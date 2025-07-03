/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 26 Jul 2005
 * @since Niagara 3.1
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.GROUP)",
  flags = Flags.SUMMARY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.GROUP, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "listOfGroupMembers",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BReadAccessSpecification.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.LIST_OF_GROUP_MEMBERS, ASN_BACNET_LIST)")
)
@NiagaraProperty(
  name = "presentValue",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BReadAccessResult.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_BACNET_LIST)")
)
public class BBacnetGroup
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetGroup(1601803167)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY | Flags.DEFAULT_ON_CLONE, BBacnetObjectIdentifier.make(BBacnetObjectType.GROUP), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.GROUP, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "listOfGroupMembers"

  /**
   * Slot for the {@code listOfGroupMembers} property.
   * @see #getListOfGroupMembers
   * @see #setListOfGroupMembers
   */
  public static final Property listOfGroupMembers = newProperty(0, new BBacnetListOf(BReadAccessSpecification.TYPE), makeFacets(BBacnetPropertyIdentifier.LIST_OF_GROUP_MEMBERS, ASN_BACNET_LIST));

  /**
   * Get the {@code listOfGroupMembers} property.
   * @see #listOfGroupMembers
   */
  public BBacnetListOf getListOfGroupMembers() { return (BBacnetListOf)get(listOfGroupMembers); }

  /**
   * Set the {@code listOfGroupMembers} property.
   * @see #listOfGroupMembers
   */
  public void setListOfGroupMembers(BBacnetListOf v) { set(listOfGroupMembers, v, null); }

  //endregion Property "listOfGroupMembers"

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, new BBacnetListOf(BReadAccessResult.TYPE), makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_BACNET_LIST));

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public BBacnetListOf getPresentValue() { return (BBacnetListOf)get(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(BBacnetListOf v) { set(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetGroup.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetGroup()
  {
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    return getObjectId().toString(context);
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Property changed.
   public void changed(Property p, Context cx)
   {
   super.changed(p,cx);
   if (!isRunning()) return;
   }
   */

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

}
