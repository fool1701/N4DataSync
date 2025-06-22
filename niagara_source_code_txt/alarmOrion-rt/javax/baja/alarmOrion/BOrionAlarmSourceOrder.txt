/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.ddl.*;
import javax.baja.sys.*;

import com.tridium.orion.*;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.annotations.OrionProperty;
import com.tridium.orion.sql.PropertyValue;

@NiagaraType
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY | Flags.SUMMARY,
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
  name = "alarmSource",
  refType = "alarmOrion:OrionAlarmSource",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "ON_DELETE", value = "BOnDelete.CASCADE"),
    @Facet(name = "AUTO_RESOLVE", value = "true")
  }
)
/*
 The path to the source of the alarm.
 */
@NiagaraProperty(
  name = "sourceOrder",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
public class BOrionAlarmSourceOrder
  extends BOrionObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmSourceOrder(1270945110)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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

  //region Property "alarmSource"

  /**
   * Slot for the {@code alarmSource} property.
   * @see #getAlarmSource
   * @see #setAlarmSource
   */
  public static final Property alarmSource = newProperty(Flags.SUMMARY, BRef.make("alarmOrion:OrionAlarmSource"), BFacets.make(BFacets.make(ON_DELETE, BOnDelete.CASCADE), BFacets.make(AUTO_RESOLVE, true)));

  /**
   * Get the {@code alarmSource} property.
   * @see #alarmSource
   */
  public BRef getAlarmSource() { return (BRef)get(alarmSource); }

  /**
   * Set the {@code alarmSource} property.
   * @see #alarmSource
   */
  public void setAlarmSource(BRef v) { set(alarmSource, v, null); }

  //endregion Property "alarmSource"

  /**
   * Resolve the {@code alarmSource} property.
   * @see #alarmSource
   */
  public BOrionAlarmSource resolveAlarmSource(OrionSession session)
  {
    return (BOrionAlarmSource)getAlarmSource().getTarget(session);
  }

  //region Property "sourceOrder"

  /**
   * Slot for the {@code sourceOrder} property.
   * The path to the source of the alarm.
   * @see #getSourceOrder
   * @see #setSourceOrder
   */
  public static final Property sourceOrder = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code sourceOrder} property.
   * The path to the source of the alarm.
   * @see #sourceOrder
   */
  public int getSourceOrder() { return getInt(sourceOrder); }

  /**
   * Set the {@code sourceOrder} property.
   * The path to the source of the alarm.
   * @see #sourceOrder
   */
  public void setSourceOrder(int v) { setInt(sourceOrder, v, null); }

  //endregion Property "sourceOrder"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmSourceOrder.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create a BOrionAlarmSourceOrder object for the specified alarm.
   */
  public static BOrionAlarmSourceOrder make(BOrd ord, int index, BOrionAlarmRecord alarm, OrionSession session)
  {
    
    BOrionAlarmSource alarmSource = (BOrionAlarmSource)session.read(BOrionAlarmSource.ORION_TYPE, new PropertyValue(BOrionAlarmSource.source, ord));
    if(alarmSource==null)
    {
      alarmSource = new BOrionAlarmSource();
      alarmSource.setSource(ord);
      session.insert(alarmSource);
    }
    BOrionAlarmSourceOrder sourceOrder = new BOrionAlarmSourceOrder();
    sourceOrder.setAlarmSource(BRef.make(alarmSource));
    sourceOrder.setSourceOrder(index);
    sourceOrder.setAlarm(BRef.make(alarm));
    return sourceOrder;
  }
}
