/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver;

import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.point.BTuningPolicyMap;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIService;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.IFuture;

import com.tridium.ndriver.datatypes.BNWorker;
import com.tridium.ndriver.util.AgentInfoUtil;

/**
 * BNNetwork is a base network class for drivers using ndriver support classes.
 * BNNetwork adds support for tuningPolicies and an async worker thread.
 *
 * @author Robert Adams
 * @creation 25 Jan 2012
 * @since Niagara 3.7
 */
@NiagaraType
/*
 A container for tuning policies which determines how
 and when proxy points are read and written.
 */
@NiagaraProperty(
  name = "tuningPolicies",
  type = "BTuningPolicyMap",
  defaultValue = "new BTuningPolicyMap()"
)
/*
 Worker thread for driver specific task that involve communication with devices
 */
@NiagaraProperty(
  name = "async",
  type = "BNWorker",
  defaultValue = "new BNWorker()",
  flags = Flags.HIDDEN
)
public abstract class BNNetwork
  extends BDeviceNetwork
  implements BIService//, BINDiscoveryHost
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.BNNetwork(3471569890)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "tuningPolicies"

  /**
   * Slot for the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #getTuningPolicies
   * @see #setTuningPolicies
   */
  public static final Property tuningPolicies = newProperty(0, new BTuningPolicyMap(), null);

  /**
   * Get the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #tuningPolicies
   */
  public BTuningPolicyMap getTuningPolicies() { return (BTuningPolicyMap)get(tuningPolicies); }

  /**
   * Set the {@code tuningPolicies} property.
   * A container for tuning policies which determines how
   * and when proxy points are read and written.
   * @see #tuningPolicies
   */
  public void setTuningPolicies(BTuningPolicyMap v) { set(tuningPolicies, v, null); }

  //endregion Property "tuningPolicies"

  //region Property "async"

  /**
   * Slot for the {@code async} property.
   * Worker thread for driver specific task that involve communication with devices
   * @see #getAsync
   * @see #setAsync
   */
  public static final Property async = newProperty(Flags.HIDDEN, new BNWorker(), null);

  /**
   * Get the {@code async} property.
   * Worker thread for driver specific task that involve communication with devices
   * @see #async
   */
  public BNWorker getAsync() { return (BNWorker)get(async); }

  /**
   * Set the {@code async} property.
   * Worker thread for driver specific task that involve communication with devices
   * @see #async
   */
  public void setAsync(BNWorker v) { set(async, v, null); }

  //endregion Property "async"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get name used for threads and logs
   */
  public abstract String getNetworkName();


////////////////////////////////////////////////////////////////
// BLoadableNetwork
////////////////////////////////////////////////////////////////

  /**
   * This passes the the given Runnable to the async worker.
   *
   * @return null always
   */
  public IFuture postAsync(Runnable r)
  {
    getAsync().post(r);
    return null;
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Gets the agent list.  Adds BNDeviceManager as "Your Driver Device Manager"
   * if one not already registered.
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    return AgentInfoUtil.processDeviceManagerAgents(super.getAgents(cx), useAutoManager(), getType().getTypeInfo().getModuleName());
  }

  /**
   * Override point to disable use of auto device manager view. Return false to
   * remove device manager view.
   */
  protected boolean useAutoManager()
  {
    return true;
  }

  /**
   * Override point to specify the subscribe depth used by auto device manager.
   *
   * @return default returns 1
   * @since 3.7.107
   */
  public int getDeviceManagerSubscribeDepth()
  {
    return 1;
  }

////////////////////////////////////////////////////////////////
//BIService
////////////////////////////////////////////////////////////////

  @Override
  public Type[] getServiceTypes()
  {
    return new Type[] { getType() };
  }

  @Override
  public void serviceStarted()
    throws Exception
  {
    updateStatus(); // Apply enabled and faults to status
  }

  @Override
  public void serviceStopped()
    throws Exception
  {
  }

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    getAsync().spy(out);
  }

////////////////////////////////////////////////////////////////
// Log support
////////////////////////////////////////////////////////////////

  /**
   * Return log named with {@code getNetworkName()}.
   */
  public final Logger log()
  {
    if (log == null)
    {
      log = Logger.getLogger(getNetworkName());
    }
    return log;
  }

  private Logger log = null;

////////////////////////////////////////////////////////////////
// Static support
////////////////////////////////////////////////////////////////
  public static final BFacets noWriteFacet = BFacets.make("noWrite", true);
  public static final BFacets groupFacet = BFacets.make("group", true);

  /**
   * This context can be used to indicate a set should not result in writing
   * data to a physical device. To use, pass in set api as the context and
   * monitor context in changed() callback to distinguish noWrite updates from
   * other changes.
   */
  public static final Context noWrite = new BasicContext(noWriteFacet)
  {
    public int hashCode()
    {
      return super.hashCode();
    }

    public boolean equals(Object obj)
    {
      return (obj != null) && (obj instanceof Context) && ((Context)obj).getFacets().getb("noWrite", false);
    }

    public String toString()
    {
      return "NDriver.noWrite";
    }
  };

  /**
   * This context can be used to indicate one of a group of sets which should
   * not result in writing data to a physical device until groupWriteFinal
   * context is seen.
   */
  public static final Context groupWrite = new BasicContext(BFacets.make(noWriteFacet, groupFacet))
  {
    public String toString()
    {
      return "NDriver.groupWrite";
    }
  };

  /**
   * This context can be used to indicate the final set in a group of sets.
   * This final write completes the group and should result in a write to the
   * physical device.
   */
  public static final Context groupWriteFinal = new BasicContext(groupFacet)
  {
    public String toString()
    {
      return "NDriver.groupWriteFinal";
    }
  };
}
