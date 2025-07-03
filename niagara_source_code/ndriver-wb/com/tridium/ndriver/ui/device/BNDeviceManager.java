/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.device;

import javax.baja.driver.ui.device.BDeviceManager;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;

import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.ui.NMgrLearn;
import com.tridium.ndriver.ui.NMgrUtil;

/**
 * The NDeviceManager is an agent on BNNetwork and BNDeviceFolder.
 * <p>
 * This provides a fully-functional device manager for drivers that are built on
 * the nDriver framework.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "ndriver:NNetwork", "ndriver:NDeviceFolder" }
  )
)
public class BNDeviceManager
  extends BDeviceManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.device.BNDeviceManager(2887425763)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDeviceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Plugs an NDeviceModel into the standard Niagara manager framework.
   */
  @Override
  protected MgrModel makeModel()
  {
    return new NDeviceModel(this);
  }

/////////////////////////////////////////////////////////////////////////
// BAbstractManager
/////////////////////////////////////////////////////////////////////////

  /**
   * Plugs an NMgrLearn into the standard Niagara manager framework, if the
   * manager's discovery host specifies a discovery leaf type and it is
   * customized in the end-driver (otherwise, this plugs in no MgrLearn).
   */
  @Override
  protected MgrLearn makeLearn()
  {
    Type discoveryLeafType = NMgrUtil.getDiscoveryLeafType(this);

    if (discoveryLeafType == null)
    {
      return null;
    }
    else
    {
      return new NMgrLearn(this);
    }

  }

  /**
   * Plugs an NDeviceController into the standard Niagara manager framework.
   */
  @Override
  protected MgrController makeController()
  {
    return new NDeviceController(this);
  }

  /**
   * Plugs an NDeviceState into the standard Niagara manager framework.
   */
  @Override
  protected MgrState makeState()
  {
    return new NDeviceState();
  }

/////////////////////////////////////////////////////////////////////////
// BAbstractManager
/////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////
// Event Handling
/////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////
// Subscription
/////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////
// BNDeviceManager
/////////////////////////////////////////////////////////////////////////

  /**
   * Gets the corresponding BNNetwork for this device manager.
   */
  public BNNetwork getNNetwork()
  {
    return NMgrUtil.findNNetwork(this);
  }

}
