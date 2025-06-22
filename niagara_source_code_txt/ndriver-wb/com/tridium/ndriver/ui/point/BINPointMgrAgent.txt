/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.point;

import javax.baja.driver.BDevice;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;

import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.point.BNPointDeviceExt;
import com.tridium.ndriver.ui.NMgrControllerUtil.NPointMgrAgentCommand;

@NiagaraType
public interface BINPointMgrAgent
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.point.BINPointMgrAgent(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:37 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINPointMgrAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * This name can be either just a name or a lexicon key that defines the
   * button text and the optional button label.
   */
  String getUiName();

  /**
   * This method is called when the user clicks the corresponding button on the
   * point manager for this agent. The developer may define any functionality
   * here.
   * <p>
   * NOTE: This will execute on the client-side proxy's virtual machine. Any
   * access to the server-side host will therefore have to be through
   * properties, actions, etc.
   *
   * @param deviceManager a reference to the point manager
   * @param network       a reference to the network that is above the point
   *                      device extension that the point manager is operating
   *                      upon.
   * @param device        a reference to the device that is above the point
   *                      device ext that the point manager is operating upon
   * @param ptDevExt      a reference to the point-device-ext that the point
   *                      manager is operating upon.
   * @return an undo/redo command artifact or null
   */
  CommandArtifact doInvoke(BNPointManager deviceManager, BNNetwork network, BDevice device, BNPointDeviceExt ptDevExt);

  /**
   * This method is called when the point manager is created. It allows the
   * developer to specify the MGR_CONTROLLER flags that govern whether a button,
   * menu item, toolbar item, etc. is created for this agent.
   *
   * @return
   */
  int getFlags();

  /**
   * The developer should review the given BNPointManager and consider updating
   * (eg. enable/disable) the given agentCommand and/or any other commands on
   * the manager's controller. The method is called anytime there is a change of
   * state on the point manager (eg. discovery list selection change, database
   * list selection change, database component event, learn mode changed, etc.)
   * <p>
   * For example (to enable the agent's UI widget(s) if one database item is
   * selected): agentCommand.setEnabled(pointManager.getController().getSelectedRows().length
   * == 1);
   * <p>
   * For example (to enable the agent's UI widget(s) if one or more database
   * items are selected): agentCommand.setEnabled(pointManager.getController().getSelectedRows().length
   * > 0);
   * <p>
   * For example (to enable the agent's UI widget(s) if zero database items are
   * selected): agentCommand.setEnabled(pointManager.getController().getSelectedRows().length
   * == 0);
   *
   * @param pointManager point manager to update
   * @param agentCommand this is a special instance of IMgrCommand. It is a
   *                     reference to the corresponding GUI command (button,
   *                     menu item, and/or toolbar button) on the device
   *                     manager.
   */
  void update(BNPointManager pointManager, NPointMgrAgentCommand agentCommand);
}
