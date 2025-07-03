/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ping;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.BAlarmSourceInfo;
import javax.baja.alarm.BIAlarmSource;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIPingable is implemented by BDeviceNetwork and BDevice to
 * provide a standard pattern for the ping infrastructure.  The 
 * ping action is an async action that verifies communication 
 * with the external network or device.
 *
 * @author    Brian Frank
 * @creation  11 Feb 04
 * @version   $Revision: 4$ $Date: 4/6/05 1:00:05 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface  BIPingable
  extends BIStatus, BIAlarmSource
{                         
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ping.BIPingable(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIPingable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Properties
////////////////////////////////////////////////////////////////

  /**
   * Get the health property which manages ping status.
   */
  public BPingHealth getHealth();     

  /**
   * Get the status property.
   */
  public BStatus getStatus();

  /**
   * Set the status property.
   */
  public void setStatus(BStatus status);

  /**
   * This callback is invoked when the health's 
   * down status is changed.
   */
  public void updateStatus();

  /**
   * Get the ping monitor for this pingable.
   */
  public BPingMonitor getMonitor();
    
////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////
  
  /**
   * Invocation method for the ping async action.
   */
  public void ping();

  /**
   * Implementation method for the ping async action.  The result 
   * of this callback should be a call to <code>pingOk()</code> 
   * or <code>pingFail()</code>.
   */
  public void doPing()
    throws Exception;     
    
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Called when <code>doPing()</code> is successful.  May also 
   * be called when any successful communication occurs.  This
   * method is routed to <code>getHealth().pingOk()</code>.
   */
  public void pingOk();

  /**
   * Called when <code>doPing()</code> fails.  This method is 
   * routed to <code>getHealth().pingFail()</code>.
   */
  public void pingFail(String cause);  

  /**
   * Used to populate the BAlarmRecord when generating alarms.
   */
  public BAlarmSourceInfo getAlarmSourceInfo();
    
////////////////////////////////////////////////////////////////
// IAlarmSource
////////////////////////////////////////////////////////////////

  /**
   * Invocation method for <code>IAlarmSoruce.ackAlarm</code>.
   */
  public BBoolean ackAlarm(BAlarmRecord ackRequest);
  
  /**
   * Called on alarm acknowledge.  This method is routed
   * to <code>getHealth().doAckAlarm(ackRequest)</code>
   */
  public BBoolean doAckAlarm(BAlarmRecord ackRequest);
}
