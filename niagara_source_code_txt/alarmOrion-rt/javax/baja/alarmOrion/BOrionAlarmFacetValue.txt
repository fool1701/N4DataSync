/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.io.IOException;

import javax.baja.data.DataUtil;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.rdb.ddl.*;
import javax.baja.sys.*;

import com.tridium.orion.*;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.annotations.OrionProperty;

/**
 * The representation of an alarm data facet value within the orion database.
 *
 * @author Lee Adcock
 * @creation March 18, 2009
 */
@NiagaraType
@NoSlotomatic
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY|Flags.SUMMARY,
  facets = {
    @Facet("ID_KEY"),
    @Facet(name = "CLUSTERED", value = "false")
  }
)
@OrionProperty(
  name = "alarm",
  refType = "alarmOrion:OrionAlarmRecord",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "INDEXED", value = "true"),
    @Facet(name = "CLUSTERED", value = "true"),
    @Facet(name = "DESCENDING", value = "true"),
    @Facet(name = "ON_DELETE", value = "BOnDelete.CASCADE")
  }
)
@OrionProperty(
  name = "facetName",
  refType = "alarmOrion:OrionAlarmFacetName",
  flags = Flags.SUMMARY,
  facets = @Facet(name = "ON_DELETE", value = "BOnDelete.CASCADE")
)
@NiagaraProperty(
  name = "value",
  type = "String",
  defaultValue = "",
  flags = Flags.SUMMARY,
  facets = @Facet("BFacets.make(\"width\", BInteger.make(512))")
)
public class BOrionAlarmFacetValue
  extends BOrionObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmFacetValue(3797697935)1.0$ @*/
/* Generated Fri Jan 14 13:11:17 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.READONLY | Flags.SUMMARY, -1, BFacets.make(ID_KEY, BFacets.make(CLUSTERED, false)));

  /**
   * Get the {@code id} property.
   * @see #id
   */
  public int getId() { return getInt(id); }

  /**
   * Set the {@code id} property.
   * @see #id
   */
  public void setId(int v) { setInt(id, v, null); }

  //endregion Property "id"

  //region Property "alarm"

  /**
   * Slot for the {@code alarm} property.
   * @see #getAlarm
   * @see #setAlarm
   */
  public static final Property alarm = newProperty(Flags.SUMMARY, BRef.make("alarmOrion:OrionAlarmRecord"), BFacets.make(BFacets.make(BFacets.make(BFacets.make(INDEXED, true), BFacets.make(CLUSTERED, true)), BFacets.make(DESCENDING, true)), BFacets.make(ON_DELETE, BOnDelete.CASCADE)));

  /**
   * Get the {@code alarm} property.
   * @see #alarm
   */
  public BRef getAlarm() { return (BRef)get(alarm); }

  /**
   * Set the {@code alarm} property.
   * @see #alarm
   */
  public void setAlarm(BRef v) { set(alarm, v, null); }

  //endregion Property "alarm"

  /**
   * Resolve the {@code alarm} property.
   * @see #alarm
   */
  public BOrionAlarmRecord resolveAlarm(OrionSession session)
  {
    return (BOrionAlarmRecord)getAlarm().getTarget(session);
  }

  //region Property "facetName"

  /**
   * Slot for the {@code facetName} property.
   * @see #getFacetName
   * @see #setFacetName
   */
  public static final Property facetName = newProperty(Flags.SUMMARY, BRef.make("alarmOrion:OrionAlarmFacetName"), BFacets.make(ON_DELETE, BOnDelete.CASCADE));

  /**
   * Get the {@code facetName} property.
   * @see #facetName
   */
  public BRef getFacetName() { return (BRef)get(facetName); }

  /**
   * Set the {@code facetName} property.
   * @see #facetName
   */
  public void setFacetName(BRef v) { set(facetName, v, null); }

  //endregion Property "facetName"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(Flags.SUMMARY, "", BFacets.make("width", BInteger.make(512)));

  /**
   * Get the {@code value} property.
   * @see #value
   */
  public String getValue() { return getString(value); }

  /**
   * Set the {@code value} property.
   * @see #value
   */
  public void setValue(String v) { setString(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmFacetValue.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  // The following method should be used instead of the slotomatic-generated version.

  public BOrionAlarmFacetName resolveFacetName(OrionSession session)
  {
    BRef ref = getFacetName();
    
    if (!ref.isBound()) return null;

    if (!ref.isResolved())
    {
      BOrionAlarmFacetName facetName = BOrionAlarmFacetName.get(((BInteger)ref.getId()).getInt(), session);
      ref.resolve(facetName);      
      return facetName;
    } else
      return (BOrionAlarmFacetName)ref.getTarget();
  }  
  
  /**
   * Create a BOrionAlarmData object from the provided parameters.
   */
  public static BOrionAlarmFacetValue make(String key, BObject value, BOrionAlarmRecord alarm, OrionSession session)
  {
    try
    {
      return make(key, DataUtil.marshal(value), alarm, session);
    } catch (IOException ioe) {
      throw new BajaRuntimeException("Unable to marshal facet data.", ioe);
    }
  }
  
  /**
   * Create a BOrionAlarmData object from the provided parameters.
   * @param value Must be encoded BObject as returned by DataUtil.marshal
   */
  public static BOrionAlarmFacetValue make(String key, String value, BOrionAlarmRecord alarm, OrionSession session)
  {
    BOrionAlarmFacetName dataName = BOrionAlarmFacetName.get(key, session);

    BOrionAlarmFacetValue data = new BOrionAlarmFacetValue();
    data.setAlarm(BRef.make(alarm));

    data.setFacetName(BRef.make(dataName));
    data.setValue(value);
    return data;
  }  
}
