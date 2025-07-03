/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import javax.baja.bacnet.BBacnetObject;
import javax.baja.bacnet.datatypes.BBacnetCalendarEntry;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

/**
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 15 Nov 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.CALENDAR)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.CALENDAR, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "presentValue",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "datelist",
  type = "BBacnetListOf",
  defaultValue = "new BBacnetListOf(BBacnetCalendarEntry.TYPE)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.DATE_LIST, ASN_BACNET_LIST)")
)
public class BBacnetCalendar
  extends BBacnetCreatableObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetCalendar(2691179309)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.CALENDAR), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.CALENDAR, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.PRESENT_VALUE, ASN_BOOLEAN));

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public boolean getPresentValue() { return getBoolean(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(boolean v) { setBoolean(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Property "datelist"

  /**
   * Slot for the {@code datelist} property.
   * @see #getDatelist
   * @see #setDatelist
   */
  public static final Property datelist = newProperty(0, new BBacnetListOf(BBacnetCalendarEntry.TYPE), makeFacets(BBacnetPropertyIdentifier.DATE_LIST, ASN_BACNET_LIST));

  /**
   * Get the {@code datelist} property.
   * @see #datelist
   */
  public BBacnetListOf getDatelist() { return (BBacnetListOf)get(datelist); }

  /**
   * Set the {@code datelist} property.
   * @see #datelist
   */
  public void setDatelist(BBacnetListOf v) { set(datelist, v, null); }

  //endregion Property "datelist"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCalendar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetCalendar()
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
      .append(getPresentValue());
    return sb.toString();
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


  @Override
  protected void addObjectInitialValues(Array<PropertyValue> listOfInitialValues)
  {
    // No Calendar specific properties to be added as of now.
  }

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
