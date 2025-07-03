/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.ext;

import javax.baja.control.BPointExtension;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAbstractProxyExt is the base class for all proxy extensions
 * which give BControlPoint's the behavior to model a point
 * external to the VM.
 *
 * @author    Brian Frank
 * @creation  5 Dec 01
 * @version   $Revision: 11$ $Date: 12/11/09 3:15:10 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BAbstractProxyExt
  extends BPointExtension
{            
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.ext.BAbstractProxyExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:36:16 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Called to check that proxy extension's status 
   * value types match the parent point.
   */
  public void checkStatusValueTypes()
  {          
  }                  
  
  /**
   * The ProxyExt is always the first extension executed.
   * The working variable passed in is the result of the input
   * calculation (the value to write if applicable).  The working 
   * variable returned from this method is the value to feed 
   * thru the rest of the extensions and into out (usually the 
   * value last read).
   */
  public void onExecute(BStatusValue out, Context cx) 
  {
  }

  /**
   * Callback when containing point is subscribed.
   */
  public void pointSubscribed() 
  {
  }
  
  /**
   * Callback when containing point is unsubscribed.
   */
  public void pointUnsubscribed() 
  {
  }

  /**
   * Callback when any of the WritablePoint's command
   * actions are invoked: emergencyOverride, emergencyAuto,
   * override, auto, or set.  This hook is used by
   * driver:ProxyExt to force a write to the external
   * device.  This callback is invoked before any changes
   * are made to the point which cause a re-execute.
   */
  public void writablePointActionInvoked()
  {                          
  }
 
}
