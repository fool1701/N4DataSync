/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.point;

import javax.baja.agent.AgentList;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.discover.BINDiscoveryHost;
import com.tridium.ndriver.discover.BNDiscoveryJob;
import com.tridium.ndriver.discover.BNDiscoveryPreferences;
import com.tridium.ndriver.util.AgentInfoUtil;

/**
 * BNPointDeviceExt is an BPointDeviceExt that adds a auto manager view and
 * implements BINDiscoverHost.
 */
@NiagaraType
/*
 This saves the last set of discovery preferences that the user provided
 on the point manager.
 Descendants should re-define this property and provide a custom default
 Value whose class extends BNDiscoveryPreferences.
 */
@NiagaraProperty(
  name = "discoveryPreferences",
  type = "BNDiscoveryPreferences",
  defaultValue = "new BNDiscoveryPreferences()"
)
/*
 Submits a auto discovery job and returns its BOrd
 */
@NiagaraAction(
  name = "submitDiscoveryJob",
  parameterType = "BNDiscoveryPreferences",
  defaultValue = "new BNDiscoveryPreferences()",
  returnType = "BOrd",
  flags = Flags.HIDDEN
)
public abstract class BNPointDeviceExt
  extends BPointDeviceExt
  implements BINDiscoveryHost
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.point.BNPointDeviceExt(3410551367)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "discoveryPreferences"

  /**
   * Slot for the {@code discoveryPreferences} property.
   * This saves the last set of discovery preferences that the user provided
   * on the point manager.
   * Descendants should re-define this property and provide a custom default
   * Value whose class extends BNDiscoveryPreferences.
   * @see #getDiscoveryPreferences
   * @see #setDiscoveryPreferences
   */
  public static final Property discoveryPreferences = newProperty(0, new BNDiscoveryPreferences(), null);

  /**
   * Get the {@code discoveryPreferences} property.
   * This saves the last set of discovery preferences that the user provided
   * on the point manager.
   * Descendants should re-define this property and provide a custom default
   * Value whose class extends BNDiscoveryPreferences.
   * @see #discoveryPreferences
   */
  public BNDiscoveryPreferences getDiscoveryPreferences() { return (BNDiscoveryPreferences)get(discoveryPreferences); }

  /**
   * Set the {@code discoveryPreferences} property.
   * This saves the last set of discovery preferences that the user provided
   * on the point manager.
   * Descendants should re-define this property and provide a custom default
   * Value whose class extends BNDiscoveryPreferences.
   * @see #discoveryPreferences
   */
  public void setDiscoveryPreferences(BNDiscoveryPreferences v) { set(discoveryPreferences, v, null); }

  //endregion Property "discoveryPreferences"

  //region Action "submitDiscoveryJob"

  /**
   * Slot for the {@code submitDiscoveryJob} action.
   * Submits a auto discovery job and returns its BOrd
   * @see #submitDiscoveryJob(BNDiscoveryPreferences parameter)
   */
  public static final Action submitDiscoveryJob = newAction(Flags.HIDDEN, new BNDiscoveryPreferences(), null);

  /**
   * Invoke the {@code submitDiscoveryJob} action.
   * Submits a auto discovery job and returns its BOrd
   * @see #submitDiscoveryJob
   */
  public BOrd submitDiscoveryJob(BNDiscoveryPreferences parameter) { return (BOrd)invoke(submitDiscoveryJob, parameter, null); }

  //endregion Action "submitDiscoveryJob"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPointDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Uses the value of property deviceDiscoveryParams as the default parameter
   * for the submitDeviceDiscoveryJob action.
   */
  @Override
  public BValue getActionParameterDefault(Action action)
  {
    if (action.equals(submitDiscoveryJob))
    {
      return getDiscoveryPreferences();
    }
    else
    {
      return super.getActionParameterDefault(action);
    }
  }

  /**
   * Gets the agent list.  Adds BNPointManager as "Your Driver Point Manager" if
   * one not already registered.
   *
   * @see AgentInfoUtil#getAgentsHelp
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    return AgentInfoUtil.processPointManagerAgents(super.getAgents(cx), useAutoManager(), getType().getTypeInfo().getModuleName());
  }

  /**
   * Override point to disable use of auto point manager view. Return false to
   * remove point manager view.
   */
  protected boolean useAutoManager()
  {
    return true;
  }
  
////////////////////////////////////////////////////////////////
// BNPointDeviceExt
////////////////////////////////////////////////////////////////

  /**
   * Submits a BNDiscoveryJob to the Job Service.
   *
   * @param preferences User specified preferences
   * @return BOrd of discovery job.
   */
  public BOrd doSubmitDiscoveryJob(BNDiscoveryPreferences preferences)
  {
    setDiscoveryPreferences((BNDiscoveryPreferences)preferences.newCopy());
    // Instantiates an instance of BNDiscoveryJob passing it a reference to this network
    BNDiscoveryJob job = new BNDiscoveryJob(this);
    // Passes the discovery parameters to the job
    job.setDiscoveryPreferences((BNDiscoveryPreferences)preferences.newCopy());
    // Submits the job and returns the Ord 
    return job.submit(null);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
