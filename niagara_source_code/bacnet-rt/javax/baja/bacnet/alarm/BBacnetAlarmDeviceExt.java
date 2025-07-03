/*
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 */
package javax.baja.bacnet.alarm;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmService;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetAlarmConst;
import javax.baja.driver.alarm.BAlarmDeviceExt;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.server.BEventHandler;

/**
 * BBacnetAlarmDeviceExt.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 23 Jul 2004
 * @since Niagara 3 BACnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"alarm:AlarmClassFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"alarm:AlarmClassEditor\"")
  },
  override = true
)
@NiagaraProperty(
  name = "niagaraProcessId",
  type = "long",
  defaultValue = "0",
  facets = {
    @Facet(name = "BFacets.MIN", value = "BLong.make(0)"),
    @Facet(name = "BFacets.MAX", value = "BLong.make(0x7FFFFFFF)")
  }
)
public class BBacnetAlarmDeviceExt
  extends BAlarmDeviceExt
  implements BacnetAlarmConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.alarm.BBacnetAlarmDeviceExt(326402908)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(0, "defaultAlarmClass", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "alarm:AlarmClassFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "alarm:AlarmClassEditor")));

  //endregion Property "alarmClass"

  //region Property "niagaraProcessId"

  /**
   * Slot for the {@code niagaraProcessId} property.
   * @see #getNiagaraProcessId
   * @see #setNiagaraProcessId
   */
  public static final Property niagaraProcessId = newProperty(0, 0, BFacets.make(BFacets.make(BFacets.MIN, BLong.make(0)), BFacets.make(BFacets.MAX, BLong.make(0x7FFFFFFF))));

  /**
   * Get the {@code niagaraProcessId} property.
   * @see #niagaraProcessId
   */
  public long getNiagaraProcessId() { return getLong(niagaraProcessId); }

  /**
   * Set the {@code niagaraProcessId} property.
   * @see #niagaraProcessId
   */
  public void setNiagaraProcessId(long v) { setLong(niagaraProcessId, v, null); }

  //endregion Property "niagaraProcessId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetAlarmDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

/////////////////////////////////////////////////////////////////
// Constructors
/////////////////////////////////////////////////////////////////

  public BBacnetAlarmDeviceExt()
  {
  }

/////////////////////////////////////////////////////////////////
// Actions
/////////////////////////////////////////////////////////////////

  public BBoolean doAckAlarm(BAlarmRecord ackRequest)
  {
    return getEventHandler().doAckAlarm(ackRequest);
  }

  public void doRouteAlarm(BAlarmRecord record)
    throws Exception
  {
    try
    {
      // Find the alarm service and route the alarm.  The alarm record already
      // has the correct alarm class in it for the default case (processId = 0).
      // If the processId is something other than our default, then check to see if
      // we have a specialized processor for it.
      BString s = (BString)record.getAlarmFacet(BAC_PROCESS_ID);
      if (s != null)
      {
        long processId = Long.parseLong(s.getString());

        // Check to see if Niagara should handle this.
        boolean noEventProcessor = true;
        if (processId == getNiagaraProcessId())
        {
          noEventProcessor = false;
          BAlarmService as = (BAlarmService)Sys.getService(BAlarmService.TYPE);
          as.routeAlarm(record);
        }

        // Check for other processors.
        SlotCursor<Property> sc = getProperties();
        while (sc.next(BBacnetEventProcessor.class))
        {
          BBacnetEventProcessor proc = (BBacnetEventProcessor)sc.get();
          if (proc.getProcessId() == processId)
          {
            noEventProcessor = false;
            proc.routeAlarm(record);
          }
        }

        if (noEventProcessor)
        {
          logger.info("AlarmDeviceExt(procId " + getNiagaraProcessId()
            + "): no event processor for alarm record:\n" + record);
        }
      }
    }
    catch (ServiceNotFoundException e)
    {
      logger.log(Level.SEVERE, "AlarmDeviceExt.processEvent:Unable to find Alarm Service!", e);
    }
  }

/////////////////////////////////////////////////////////////////
// Methods
/////////////////////////////////////////////////////////////////

  private BEventHandler getEventHandler()
  {
    return ((BBacnetStack)((BBacnetNetwork)getNetwork()).getBacnetComm())
      .getServer().getEventHandler();
  }

/////////////////////////////////////////////////////////////////
// Attributes
/////////////////////////////////////////////////////////////////

  private static final Logger logger = Logger.getLogger("bacnet");
}
