/* 
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 * 
 */

package javax.baja.driver.util;

import javax.baja.control.trigger.BIntervalTriggerMode;
import javax.baja.control.trigger.BTimeTrigger;
import javax.baja.driver.BDeviceExt;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.Action;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.util.ComponentTreeCursor;

/**
 * DeviceExt that manages failure retries and status for a set of 
 * descriptors.
 * <p><b>Retry</b><br>
 * The retry action is invoked by the retry trigger for the interval configured
 * by a user.  The rety action simply calls excute on any descriptors whose
 * status is fault.
 * 
 * @author John Sublett, Aaron Hansen
 * @creation Feb 2004
 * @version $Revision: 20$ $Date: 3/23/05 11:53:26 AM EST$
 */
@NiagaraType
@NiagaraProperty(
  name = "retryTrigger",
  type = "BTimeTrigger",
  defaultValue = "new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(15)))"
)
@NiagaraAction(
  name = "retry",
  flags = Flags.SUMMARY
)
public class BDescriptorDeviceExt 
  extends BDeviceExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BDescriptorDeviceExt(785829987)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "retryTrigger"

  /**
   * Slot for the {@code retryTrigger} property.
   * @see #getRetryTrigger
   * @see #setRetryTrigger
   */
  public static final Property retryTrigger = newProperty(0, new BTimeTrigger(BIntervalTriggerMode.make(BRelTime.makeMinutes(15))), null);

  /**
   * Get the {@code retryTrigger} property.
   * @see #retryTrigger
   */
  public BTimeTrigger getRetryTrigger() { return (BTimeTrigger)get(retryTrigger); }

  /**
   * Set the {@code retryTrigger} property.
   * @see #retryTrigger
   */
  public void setRetryTrigger(BTimeTrigger v) { set(retryTrigger, v, null); }

  //endregion Property "retryTrigger"

  //region Action "retry"

  /**
   * Slot for the {@code retry} action.
   * @see #retry()
   */
  public static final Action retry = newAction(Flags.SUMMARY, null);

  /**
   * Invoke the {@code retry} action.
   * @see #retry
   */
  public void retry() { invoke(retry, null, null); }

  //endregion Action "retry"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDescriptorDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BDescriptorDeviceExt() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /**
   * If not disabled, calls execute on descriptors who have failed state.
   */
  public final void doRetry()
  {
    BDescriptor[] ary = getDescriptors();
    for (int i = ary.length; --i >= 0; )
    {
      if (ary[i].getStatus().isFault())
        ary[i].execute();
    }
  }

  /**
   * All descendant descriptors.
   */
  public BDescriptor[] getDescriptors()
  {
    ComponentTreeCursor cur = new ComponentTreeCursor(this,null);
    Array<BDescriptor> ret = new Array<>(BDescriptor.class);
    while (cur.next(BDescriptor.class))
      ret.add((BDescriptor)cur.get());
    return ret.trim();
  }

  /**
   * All descendant descriptors in the given state.
   */
  public BDescriptor[] getDescriptors(BDescriptorState state)
  {
    ComponentTreeCursor cur = new ComponentTreeCursor(this,null);
    Array<BDescriptor> ret = new Array<>(BDescriptor.class);
    BDescriptor tmp;
    while (cur.next(BDescriptor.class))
    {
      tmp = (BDescriptor) cur.get();
      if (tmp.getState() == state)
        ret.add(tmp);
    }
    return ret.trim();
  }

  public void started()
    throws Exception
  {                 
    super.started();
    BTimeTrigger t = getRetryTrigger();
    linkTo("retryLink",t,BTimeTrigger.fireTrigger,retry);
  }

  public void stopped()
    throws Exception
  {
    super.stopped();
    Property retryLink = getProperty("retryLink");
    if (retryLink != null) remove(retryLink);
  }

  /**
   * Updates the status of all descriptors in my subtree.
   */
  public void updateStatus()
  {
    BDescriptor[] ary = getDescriptors();
    for (int i = ary.length; --i >= 0; )
      ary[i].updateStatus();
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BDescriptorDeviceExt
