/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInterface;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BITunable is the interface implemented by components
 * which use the point tuning framework to control their 
 * read and writes.  All ITunable instances must create their
 * own instance of Tuning.  It is the responsibility of the 
 * ITunable component to make the following callbacks on its 
 * child Tuning:
 * <pre>{@code
 *   started         -> transition()
 *   atSteadyState   -> transition()
 *   stopped         -> transition()
 *   subscribed      -> transition()
 *   unsubscribed    -> transition()
 *   status changed  -> transition()
 *
 *   read success    -> readOk()
 *   read failure    -> readFail()
 *
 *   write success   -> writeOk()
 *   write failure   -> writeFail()
 * }</pre>
 * Note that ProxyExt automatically handles these callbacks.
 *
 * @author    Brian Frank       
 * @creation  17 Jun 04
 * @version   $Revision: 8$ $Date: 11/7/05 10:22:52 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BITunable
  extends BInterface
{          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BITunable(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BITunable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////

  /**
   * Get the tuning policy name for this component.  Typically
   * this is mapped to a tuningPolicyName property.  If mapped to 
   * a configurable property, then apply TUNING_POLICY_NAME_FACETS.
   */
  public String getTuningPolicyName();             
  public static final BFacets TUNING_POLICY_NAME_FACETS = BFacets.make(BFacets.FIELD_EDITOR, BString.make("driver:TuningPolicyNameFE"), BFacets.UX_FIELD_EDITOR, BString.make("driver:TuningPolicyEditor"));

  /**
   * Get the tuning support instance for this component.
   */
  public Tuning getTuning();      
  
  /**
   * Return true if the network/device/component has a 
   * fatal fault which prevents normal operation.
   */
  public boolean isFatalFault();       

  /**
   * Get the status of the component.  The status should have the down 
   * bit set if the network or device is down.  The status should have 
   * the disabled bit set if the  network/device/component is disabled.
   */
  public BStatus getStatus();
  
  /**
   * Return if the tunable component is current running.
   */
  public boolean isRunning();
  
  /**
   * Return if the tunable component currently desires
   * to be subscribed.  This is independent of any status
   * conditions which might be preventing actual subscription.
   */
  public boolean isSubscribedDesired();  
  
  /**
   * Return if the component is readonly, readWrite, or writeOnly.
   */
  public BReadWriteMode getMode();  

////////////////////////////////////////////////////////////////
// Callbacks from BTuning
////////////////////////////////////////////////////////////////

  /**
   * This callback is made when the component enters a subscribed 
   * state based on the current status and tuning.  The driver 
   * should register for changes or begin polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.  The result of reads should be to call the
   * readOk() or readFail() method.
   */
  public void readSubscribed(Context cx)
    throws Exception;
  
  /**
   * This callback is made when the component exits the subscribed
   * state based on the current status and tuning.  The driver
   * should unregister for changes of cease polling.  Any IO should
   * be done asynchronously on another thread - never block the
   * calling thread.
   */
  public void readUnsubscribed(Context cx)
    throws Exception;
  
  /**
   * This callback is made when a write is desired based on the
   * current status and tuning.  Any IO should be done asynchronously 
   * on another thread - never block the calling thread.  If the write 
   * is enqueued then return true and call writeOk() or writeFail() 
   * once it has been processed.  If the write is canceled immediately
   * for other reasons then return false. 
   *
   * @return true if a write is now pending
   */
  public boolean write(Context cx)
    throws Exception;         
    
  /**
   * This callback is made with a value of true when the tuning indicates 
   * that the last readOk() was too long ago to make the current value 
   * trustworthy.  This callback should result in the stale status bit 
   * being set or cleared appropriately.
   */
  public void setStale(boolean stale, Context cx);  

  /**
   * This method is used by Tunable to log a failure if 
   * <code>readSubscribed(Context)</code> raises an exception.
   */
  public void readFail(String cause);

  /**
   * This method is used by Tunable to log a write failure if 
   * <code>write(Context)</code> raises an exception.
   */
  public void writeFail(String cause);
}
