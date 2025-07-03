/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import java.util.Map;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFaultAlgorithm is the super-class of all fault-detection
 * mechanisms defined by Niagara.  The default implementation
 * will never generate any toFault alarms.
  *
 * @author    Dan Giorgis
 * @creation  13 Nov 00
 * @version   $Revision: 21$ $Date: 2/14/06 2:21:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFaultAlgorithm
  extends BAlarmAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BFaultAlgorithm(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFaultAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BFaultAlgorithm's parent must be a BAlarmSourceExt
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BAlarmSourceExt;
  }

  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
//  Fault transistion checking
////////////////////////////////////////////////////////////////

  /**
   *  Check for transitions to or from the fault state.
   *  Returns BAlarmState.fault on a to-fault transition,
   *  BAlarmState.normal on a to-normal transition and null
   *  if no change in state occured.
   */
  public BAlarmState checkFault(BStatusValue out)
  {
    return null;
  }

////////////////////////////////////////////////////////////////
//  Override Points
////////////////////////////////////////////////////////////////

  /**
   *  Check for a normal / fault alarm transition.  Return
   * new alarm state or null if no change.
   */
  public final BAlarmState checkAlarmState(BStatusValue out)
  {
    return null;
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Map.
   *
   * @param out The relevant control point status value
   * @param map The map.
   */
  @SuppressWarnings("rawtypes")
  public void writeAlarmData(BStatusValue out, Map map)
  {
  }
}
