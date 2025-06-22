/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui.device;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;

import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.ui.NMgrControllerUtil.NDeviceMgrAgentCommand;

@NiagaraType
public interface BINDeviceMgrAgent
  extends BInterface, BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.device.BINDeviceMgrAgent(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:37 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINDeviceMgrAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * This name can be either just a name or a lexicon key that defines the button text and the
   * optional button label.
   */
  String getUiName();
  
  /**
   * This method is called when the user clicks the
   * corresponding button on the device manager for this
   * agent.The developer may define any functionality
   * here.
   * 
   * NOTE: This will execute on the client-side proxy's
   * virtual machine. Any access to the server-side
   * host will therefore have to be through properties,
   * actions, etc.
   * 
   * @param a reference to the device manager
   * @param a reference to the network that the device manager is
   * operating upon
   * 
   * @return an undo/redo command artifact or null
   */
  CommandArtifact doInvoke(BNDeviceManager deviceManager, BNNetwork network);
  
  /**
   * This method is called when the auto manager
   * is created. It allows the developer to specify the MGR_CONTROLLER flags
   * that govern whether a button, menu item, toolbar item, etc. is created
   * for this agent.
   * 
   * @return
   */
  int getFlags();
  
  /**
   * The developer should review the given BNDeviceManager and consider
   * updating (eg. enable/disable) the given agentCommand and/or any other
   * commands on the manager's controller. The method is called anytime
   * there is a change of state on the device manager (eg. discovery list
   * selection change, database list selection change, database component
   * event, learn mode changed, etc.)
   * 
   * For example (to enable the agent's UI widget(s) if one database item is selected):
   *  agentCommand.setEnabled(deviceManager.getController().getSelectedRows().length == 1);
   *  
   * For example (to enable the agent's UI widget(s) if one or more database items are selected):
   *  agentCommand.setEnabled(deviceManager.getController().getSelectedRows().length > 0);
   *  
   * For example (to enable the agent's UI widget(s) if zero database items are selected):
   *  agentCommand.setEnabled(deviceManager.getController().getSelectedRows().length == 0);
   *   
   * @param deviceManager
   * 
   * @param agentCommand this is a special instance of IMgrCommand. It is
   * a reference to the corresponding GUI command (button, menu item, and/or
   * toolbar button) on the device manager.
   */
  void update(BNDeviceManager deviceManager, NDeviceMgrAgentCommand agentCommand);
}
