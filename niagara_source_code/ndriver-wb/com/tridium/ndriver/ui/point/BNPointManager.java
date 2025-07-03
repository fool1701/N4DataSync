/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.point;

import javax.baja.driver.BDevice;
import javax.baja.driver.ui.point.BPointManager;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;

import com.tridium.driver.util.DrUtil;
import com.tridium.ndriver.point.BNPointDeviceExt;
import com.tridium.ndriver.ui.NMgrLearn;
import com.tridium.ndriver.ui.NMgrUtil;

/**
 * The BNPointManager is a registered Niagara agent on BNPointDeviceExt and
 * BNPointFolder. It automatically appears on them as one of their
 * 'Views'.
 * <p>
 * This provides a fully-functional point manager for drivers that are built on
 * ndriver.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "ndriver:NPointDeviceExt", "ndriver:NPointFolder" }
  )
)
public class BNPointManager
  extends BPointManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.point.BNPointManager(1769695937)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BPointManager
////////////////////////////////////////////////////////////////

  /**
   * Plugs an NPointModel into the standard Niagara manager framework.
   */
  @Override
  protected MgrModel makeModel()
  {
    return new NPointModel(this);
  }

  /**
   * Plugs an NMgrLearn into the standard Niagara manager framework.
   */
  @Override
  protected MgrLearn makeLearn()
  {
    Type discoveryLeafType = NMgrUtil.getDiscoveryLeafType(this);
    
    if (discoveryLeafType == null) // If the discovery host does not define a discovery leaf type
    {
      return null;
    }
    else
    {
      return new NMgrLearn(this);
    }

  }

  /**
   * Plugs an NPointController into the standard Niagara manager framework.
   */
  @Override
  protected MgrController makeController()
  {
    return new NPointController(this);
  }

  /**
   * Plugs an NPointState into the standard Niagara manager framework.
   */
  @Override
  protected MgrState makeState()
  {
    return new NPointState();
  }

////////////////////////////////////////////////////////////////
// BAbstractManager
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
// BNPointManager
////////////////////////////////////////////////////////////////

  /**
   * Gets the corresponding BNDevice for this device manager.
   */
  public BDevice getNDevice()
  {
    return NMgrUtil.findNDevice(this);
  }

  /**
   * Gets the corresponding BNPointDeviceExt for this device manager.
   */
  public BNPointDeviceExt getNPointDeviceExt()
  {
    return (BNPointDeviceExt)DrUtil.getParent((BComplex)getCurrentValue(), BNPointDeviceExt.TYPE);
  }
}
