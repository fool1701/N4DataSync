/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/**
 *
 */
package javax.baja.bacnet.alarm;

import java.util.logging.Logger;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author cgemmill
 */
@NiagaraType
@NiagaraProperty(
  name = "processId",
  type = "long",
  defaultValue = "1",
  facets = {
    @Facet(name = "BFacets.MIN", value = "BLong.make(0)"),
    @Facet(name = "BFacets.MAX", value = "BLong.make(0x7FFFFFFF)")
  }
)
public class BBacnetEventProcessor
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.alarm.BBacnetEventProcessor(1030790138)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "processId"

  /**
   * Slot for the {@code processId} property.
   * @see #getProcessId
   * @see #setProcessId
   */
  public static final Property processId = newProperty(0, 1, BFacets.make(BFacets.make(BFacets.MIN, BLong.make(0)), BFacets.make(BFacets.MAX, BLong.make(0x7FFFFFFF))));

  /**
   * Get the {@code processId} property.
   * @see #processId
   */
  public long getProcessId() { return getLong(processId); }

  /**
   * Set the {@code processId} property.
   * @see #processId
   */
  public void setProcessId(long v) { setLong(processId, v, null); }

  //endregion Property "processId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEventProcessor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Route the alarm.
   * This is the override point for event processors.
   * Here a subclass may either process the alarm itself, or route it
   * to another class for processing.  Note that the Niagara alarm system
   * will already have processed the alarm IF its processId matches the
   * niagaraProcessId in the AlarmDeviceExt.
   */
  public void routeAlarm(BAlarmRecord record)
  {
    logger.info("BacnetEventProcessor(" + getProcessId() + "):routeAlarm::\n" + record);
  }

  private static final Logger logger = Logger.getLogger("bacnet.alarm");
}
