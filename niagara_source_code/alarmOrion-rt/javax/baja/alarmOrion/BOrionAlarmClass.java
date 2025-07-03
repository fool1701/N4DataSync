/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import javax.baja.alarm.BAlarmClass;
import javax.baja.alarm.BAlarmService;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.alarmOrion.OrionObjectCache;
import com.tridium.alarmOrion.SkipLocalAlarmClassUpdates;
import com.tridium.orion.BOrionObject;
import com.tridium.orion.OrionCursor;
import com.tridium.orion.OrionSession;
import com.tridium.orion.OrionType;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.sql.PropertyValue;

/**
 * The representation of an alarm class within the orion database.
 *
 * @author Lee Adcock on March 18, 2009
 */
@NiagaraType
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY | Flags.SUMMARY,
  facets = @Facet("ID_KEY")
)
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "UNIQUE", value = "true"),
    @Facet(name = "WIDTH", value = "64")
  }
)
@NiagaraProperty(
  name = "unackedAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "openAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "inAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "totalAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "timeOfLastAlarm",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.SUMMARY
)
public class BOrionAlarmClass
  extends BOrionObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmClass(1151820771)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.READONLY | Flags.SUMMARY, -1, ID_KEY);

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

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(Flags.SUMMARY, "", BFacets.make(BFacets.make(UNIQUE, true), BFacets.make(WIDTH, 64)));

  /**
   * Get the {@code alarmClass} property.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "unackedAlarmCount"

  /**
   * Slot for the {@code unackedAlarmCount} property.
   * @see #getUnackedAlarmCount
   * @see #setUnackedAlarmCount
   */
  public static final Property unackedAlarmCount = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code unackedAlarmCount} property.
   * @see #unackedAlarmCount
   */
  public int getUnackedAlarmCount() { return getInt(unackedAlarmCount); }

  /**
   * Set the {@code unackedAlarmCount} property.
   * @see #unackedAlarmCount
   */
  public void setUnackedAlarmCount(int v) { setInt(unackedAlarmCount, v, null); }

  //endregion Property "unackedAlarmCount"

  //region Property "openAlarmCount"

  /**
   * Slot for the {@code openAlarmCount} property.
   * @see #getOpenAlarmCount
   * @see #setOpenAlarmCount
   */
  public static final Property openAlarmCount = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code openAlarmCount} property.
   * @see #openAlarmCount
   */
  public int getOpenAlarmCount() { return getInt(openAlarmCount); }

  /**
   * Set the {@code openAlarmCount} property.
   * @see #openAlarmCount
   */
  public void setOpenAlarmCount(int v) { setInt(openAlarmCount, v, null); }

  //endregion Property "openAlarmCount"

  //region Property "inAlarmCount"

  /**
   * Slot for the {@code inAlarmCount} property.
   * @see #getInAlarmCount
   * @see #setInAlarmCount
   */
  public static final Property inAlarmCount = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code inAlarmCount} property.
   * @see #inAlarmCount
   */
  public int getInAlarmCount() { return getInt(inAlarmCount); }

  /**
   * Set the {@code inAlarmCount} property.
   * @see #inAlarmCount
   */
  public void setInAlarmCount(int v) { setInt(inAlarmCount, v, null); }

  //endregion Property "inAlarmCount"

  //region Property "totalAlarmCount"

  /**
   * Slot for the {@code totalAlarmCount} property.
   * @see #getTotalAlarmCount
   * @see #setTotalAlarmCount
   */
  public static final Property totalAlarmCount = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code totalAlarmCount} property.
   * @see #totalAlarmCount
   */
  public int getTotalAlarmCount() { return getInt(totalAlarmCount); }

  /**
   * Set the {@code totalAlarmCount} property.
   * @see #totalAlarmCount
   */
  public void setTotalAlarmCount(int v) { setInt(totalAlarmCount, v, null); }

  //endregion Property "totalAlarmCount"

  //region Property "timeOfLastAlarm"

  /**
   * Slot for the {@code timeOfLastAlarm} property.
   * @see #getTimeOfLastAlarm
   * @see #setTimeOfLastAlarm
   */
  public static final Property timeOfLastAlarm = newProperty(Flags.SUMMARY, BAbsTime.NULL, null);

  /**
   * Get the {@code timeOfLastAlarm} property.
   * @see #timeOfLastAlarm
   */
  public BAbsTime getTimeOfLastAlarm() { return (BAbsTime)get(timeOfLastAlarm); }

  /**
   * Set the {@code timeOfLastAlarm} property.
   * @see #timeOfLastAlarm
   */
  public void setTimeOfLastAlarm(BAbsTime v) { set(timeOfLastAlarm, v, null); }

  //endregion Property "timeOfLastAlarm"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmClass.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the alarm class of the specified name.  If one does not exist, create it.
   * The results are cached to speed up multiple requests for the same object.
   */
  public static BOrionAlarmClass get(String alarmClassName, OrionSession session)
  {
    if (ORION_ALARM_CLASS_CACHE.contains(alarmClassName))
    {
      return (BOrionAlarmClass)ORION_ALARM_CLASS_CACHE.get(alarmClassName);
    }

    OrionCursor cursor = session.select(BOrionAlarmClass.ORION_TYPE, new PropertyValue(BOrionAlarmClass.alarmClass, BString.make(alarmClassName)));
    BOrionAlarmClass alarmClass;
    if (cursor.next())
    {
      alarmClass = (BOrionAlarmClass)cursor.get();
      cursor.close();
    }
    else
    {
      alarmClass = new BOrionAlarmClass();
      alarmClass.setAlarmClass(alarmClassName);
      session.insert(alarmClass);
    }

    ORION_ALARM_CLASS_CACHE.put(alarmClassName, alarmClass);

    return alarmClass;
  }

  public void afterDelete(OrionSession session)
  {
    ORION_ALARM_CLASS_CACHE.remove(getAlarmClass());
  }

  public void afterInsert(OrionSession session)
  {
    ORION_ALARM_CLASS_CACHE.put(getAlarmClass(), this);
    updateAlarmClass(session);
  }

  public void afterUpdate(OrionSession session)
  {
    ORION_ALARM_CLASS_CACHE.update(getAlarmClass(), this);
    updateAlarmClass(session);
  }

  /**
   * Update the alarm class component with the alarm statistics stored
   * within the database.
   * <p>
   * This method was deprecated in Niagara 4.13 and {@link #updateAlarmClass(OrionSession)} should be used instead.
   *
   * @deprecated in Niagara 4.13.  Will be removed in Niagara 5.
   */
  @Deprecated
  public void updateAlarmClass()
  {
    updateAlarmClass(null);
  }

  /**
   * Update the alarm class component with the alarm statistics stored
   * within the database.
   *
   * @since Niagara 4.13
   */
  public void updateAlarmClass(OrionSession session)
  {
    if (session != null && session.getBase() instanceof SkipLocalAlarmClassUpdates)
    {
      return;
    }

    BAlarmService service = (BAlarmService)Sys.getService(BAlarmService.TYPE);
    BAlarmClass[] alarmClasses = service.getAlarmClasses();
    for (BAlarmClass aClass : alarmClasses)
    {
      if (aClass.getName().equals(getAlarmClass()))
      {
        aClass.setUnackedAlarmCount(getUnackedAlarmCount());
        aClass.setOpenAlarmCount(getOpenAlarmCount());
        aClass.setInAlarmCount(getInAlarmCount());
        aClass.setTotalAlarmCount(getTotalAlarmCount());
        aClass.setTimeOfLastAlarm(getTimeOfLastAlarm());
        break;
      }
    }
  }

  private static final OrionObjectCache ORION_ALARM_CLASS_CACHE = new OrionObjectCache(100);
}
