/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.driver.BDevice;
import javax.baja.nre.util.Array;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.ui.CommandArtifact;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrController.IMgrCommand;
import javax.baja.workbench.mgr.MgrController.MgrCommand;
import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.discover.BINDiscoveryHost;
import com.tridium.ndriver.discover.BNDiscoveryPreferences;
import com.tridium.ndriver.point.BNPointDeviceExt;
import com.tridium.ndriver.ui.device.BINDeviceMgrAgent;
import com.tridium.ndriver.ui.device.BNDeviceManager;
import com.tridium.ndriver.ui.point.BINPointMgrAgent;
import com.tridium.ndriver.ui.point.BNPointManager;

/**
 * This class contains the like functionality that is shared between the device
 * controller and the point controller.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public final class NMgrControllerUtil
{
  /**
   * Cannot instantiate! Access static methods only.
   */
  private NMgrControllerUtil() {}

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * This call-back is made by the NDeviceController and NPointController when
   * the user clicks the "Discover" button.
   */
  public static CommandArtifact doDiscover(BAbstractManager mgr, Context cx)
    throws Exception
  {
    BINDiscoveryHost host = NMgrUtil.findDiscoveryHost(mgr);

    // Splits the manager into learn mode 
    mgr.getController().learnMode.setSelected(true);

    // Gets the discovery preferences from the network or point-device-extension
    BNDiscoveryPreferences prefs =
      (BNDiscoveryPreferences)host.getDiscoveryPreferences().newCopy();

    BNDiscoveryPreferences discoveryJobPrefs = prefs;

    // If doNotAsk flag is not set give user chance to modify discovery preferences
    if (!prefs.getDoNotAskAgain())
    {
      discoveryJobPrefs = (BNDiscoveryPreferences)BWbFieldEditor.dialog(mgr,
        NMgrUtil.LEX.get("discoveryParameters"), prefs);
    }

    if (discoveryJobPrefs != null)
    {
      // Submits a discovery job, gives it the discovery preferences, and registers the job with the NMgrLearn
      mgr.getLearn().setJob(host.submitDiscoveryJob(discoveryJobPrefs));
    }

    return null;
  }

  /**
   * Makes the commands to adds to the device manager.
   *
   * @param superCommands the standard commands for the device manager.
   * @param mgr           the device manager
   * @return a new array consisting of the superCommands plus
   * NDeviceMgrAgentCommands for each 'BINDeviceMgrAgent' agent that is declared
   * on the network.
   */
  public static IMgrCommand[] makeCommands(IMgrCommand[] superCommands, BNDeviceManager mgr)
  {
    Array<IMgrCommand> deviceMgrCmdAgents = new Array<>(IMgrCommand.class);

    // Find agents on the network
    BNNetwork network = NMgrUtil.findNNetwork(mgr);
    AgentList agents = network.getAgents().filter(AgentFilter.is(BINDeviceMgrAgent.TYPE));

    // Add 
    AgentInfo[] infoA = agents.list();
    for (int i = 0; i < infoA.length; i++)
    {
      BINDeviceMgrAgent mgrAgent = (BINDeviceMgrAgent)infoA[i].getInstance();
      deviceMgrCmdAgents.add(NDeviceMgrAgentCommand.make(mgr, mgrAgent));
    }

    // Returns an array consisting of the superCommands and the NDeviceMgrAgentCommand's for each BINDeviceMgrAgent
    return MgrController.append(superCommands, deviceMgrCmdAgents.trim());
  }

  /**
   * Makes the commands that need for the point manager.
   *
   * @param superCommands the standard commands for the point manager.
   * @param mgr           the point manager
   * @return a new array consisting of the superCommands plus
   * NPointMgrAgentCommands for each 'BINPointMgrAgent' agent that is declared
   * on the point-device-ext or on the device.
   */
  public static IMgrCommand[] makeCommands(IMgrCommand[] superCommands, BNPointManager mgr)
  {
    Array<IMgrCommand> pointMgrCmdAgents = new Array<>(IMgrCommand.class);

    // Finds any agents that are on the device
    BDevice device = NMgrUtil.findNDevice(mgr);
    AgentList devAgents = device.getAgents().filter(AgentFilter.is(BINPointMgrAgent.TYPE));

    AgentInfo[] agentInfoA = devAgents.list();

    for (int i = 0; i < agentInfoA.length; i++)
    {
      BINPointMgrAgent pointMgrAgent = (BINPointMgrAgent)agentInfoA[i].getInstance();
      pointMgrCmdAgents.add(NPointMgrAgentCommand.make(mgr, pointMgrAgent));
    }

    // Finds any agents that are on the point-device-ext
    BNPointDeviceExt ptDevExt = NMgrUtil.findNPointDeviceExt(mgr);
    if (ptDevExt != null)
    {
      AgentList devExtAgents = ptDevExt.getAgents().filter(AgentFilter.is(BINPointMgrAgent.TYPE));

      AgentInfo[] agentsOnPtDevExt = devExtAgents.list();

      for (int i = 0; i < agentsOnPtDevExt.length; i++)
      {
        BINPointMgrAgent pointMgrAgent = (BINPointMgrAgent)agentsOnPtDevExt[i].getInstance();
        pointMgrCmdAgents.add(NPointMgrAgentCommand.make(mgr, pointMgrAgent));
      }
    }
    // Returns an array consisting of the superCommands and the NPointMgrAgentCommand's for each BINPointMgrAgent
    return MgrController.append(superCommands, pointMgrCmdAgents.trim());
  }

  public static void updateCommands(BAbstractManager mgr)
  {
    IMgrCommand[] mgrCommands = mgr.getController().getCommands();

    // Enables or disables all NDeviceMgrAgentCommands or NPointMgrAgentCommands appropriately
    for (int i = 0; i < mgrCommands.length; i++)
    {
      if (mgrCommands[i] instanceof NDeviceMgrAgentCommand)
      {
        NDeviceMgrAgentCommand deviceMgrCommand = (NDeviceMgrAgentCommand)mgrCommands[i];
        deviceMgrCommand.agent.update((BNDeviceManager)mgr, deviceMgrCommand);
      }
      else if (mgrCommands[i] instanceof NPointMgrAgentCommand)
      {
        NPointMgrAgentCommand pointMgrCommand = (NPointMgrAgentCommand)mgrCommands[i];
        pointMgrCommand.agent.update((BNPointManager)mgr, pointMgrCommand);
      }
    }
  }

////////////////////////////////////////////////////////////////
// NDeviceMgrAgentCommand
////////////////////////////////////////////////////////////////

  public static class NDeviceMgrAgentCommand
    extends MgrCommand
  {
    BINDeviceMgrAgent agent; // The agent that defines the doInvoke functionality

    public static NDeviceMgrAgentCommand make(BAbstractManager manager, BINDeviceMgrAgent devMgrAgent)
    {
      // Gets the string that the agent defines as the "Ui Name"
      String uiName = devMgrAgent.getUiName();

      // If the agent does not specify one, then let's use a question mark or two as the button label. The
      // Developer should be able to notice this early in testing his or her driver.
      if (uiName == null)
      {
        return new NDeviceMgrAgentCommand(manager, devMgrAgent, "??");
      }
      else
      {
        // Gets the developer's lexicon
        Lexicon lex = Sys.loadModule(devMgrAgent.getType().getTypeSpec().getModuleName()).getLexicon();

        // If the developer provides no lexicon, then the best we can do is use the exact uiName
        // From the agent
        if (lex == null)
        {
          return new NDeviceMgrAgentCommand(manager, devMgrAgent, uiName);
        }
        else
        {
          // Checks if the lexicon specifies a lexicon base (which means the lexicon defines a .label and possibly a
          // .toolbar image, etc).
          String lexLabel = lex.get(uiName + ".label");

          // If no lexicon key with a ".label" after the uiName, then let's use either the lexicon's text
          // For the uiName itself or the uiName itself if all else fails [lex.getText(...) accomplishes this]
          if (lexLabel == null)
          {
            return new NDeviceMgrAgentCommand(manager, devMgrAgent, lex.getText(uiName));
          }

          // Else, there is indeed a key with a ".label" so we use a different form of the NDeviceMgrAgentCommand's
          // Constructor that asks its core superclass to use the .label, .toolbar, etc. from the particular lexicon
          else
          {
            return new NDeviceMgrAgentCommand(manager, lex, devMgrAgent);
          }
        }
      }
    }

    NDeviceMgrAgentCommand(BAbstractManager manager, BINDeviceMgrAgent mgrAgent, String label)
    {
      super(manager, label);
      this.agent = mgrAgent;
      setFlags(mgrAgent.getFlags());
    }

    NDeviceMgrAgentCommand(BAbstractManager manager, Lexicon lex, BINDeviceMgrAgent mgrAgent)
    {
      super(manager, lex, mgrAgent.getUiName());
      this.agent = mgrAgent;
      setFlags(agent.getFlags());
    }

    /**
     * This method is called when the corresponding user-interface item on the
     * manager is clicked.
     */
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      // This MgrCommand's owner will be the device manager
      BNDeviceManager devMgr = (BNDeviceManager)getOwner();

      // Calls the doInvoke method on the instance of the BINDeviceMgrAgent
      CommandArtifact retVal = agent.doInvoke(devMgr, NMgrUtil.findNNetwork(devMgr));

      // Updates the MgrCommands to reflect any changes that could have happened
      // in the 'doInvoke' callback
      devMgr.getController().updateCommands();

      return retVal;

    }

    /**
     * @return the agent, this will be an instance of the developer's agent
     * class.
     */
    public BINDeviceMgrAgent getAgent()
    {
      return agent;
    }
  }

////////////////////////////////////////////////////////////////
// NPointMgrAgentCommand
////////////////////////////////////////////////////////////////

  public static class NPointMgrAgentCommand
    extends MgrCommand
  {
    BINPointMgrAgent agent; // The agent that defines the doInvoke functionality

    public static NPointMgrAgentCommand make(BAbstractManager manager, BINPointMgrAgent agent)
    {
      // Gets the string that the agent defines as the "Ui Name"
      String uiName = agent.getUiName();

      // If the agent does not specify one, then let's use a question mark or two as the button label. The
      // Developer should be able to notice this early in testing his or her driver.
      if (uiName == null)
      {
        return new NPointMgrAgentCommand(manager, agent, "??");
      }
      else
      {
        // Gets the developer's lexicon
        Lexicon lex = Sys.loadModule(agent.getType().getTypeSpec().getModuleName()).getLexicon();

        // If the developer provides no lexicon, then the best we can do is use the exact uiName
        // From the agent
        if (lex == null)
        {
          return new NPointMgrAgentCommand(manager, agent, uiName);
        }
        else
        {
          // Checks if the lexicon specifies a lexicon base (which means the lexicon defines a .label and possibly a
          // .toolbar image, etc).
          String lexLabel = lex.get(uiName + ".label");

          // If no lexicon key with a ".label" after the uiName, then let's use either the lexicon's text
          // For the uiName itself or the uiName itself if all else fails [lex.getText(...) accomplishes this]
          if (lexLabel == null)
          {
            return new NPointMgrAgentCommand(manager, agent, lex.getText(uiName));
          }

          // Else, there is indeed a key with a ".label" so we use a different form of the NDeviceMgrAgentCommand's
          // Constructor that asks its core superclass to use the .label, .toolbar, etc. from the particular lexicon
          else
          {
            return new NPointMgrAgentCommand(manager, lex, agent);
          }
        }
      }
    }

    NPointMgrAgentCommand(BAbstractManager manager, BINPointMgrAgent agent, String label)
    {
      super(manager, label);
      this.agent = agent;
      setFlags(agent.getFlags());
    }

    NPointMgrAgentCommand(BAbstractManager manager, Lexicon lex, BINPointMgrAgent agent)
    {
      super(manager, lex, agent.getUiName());
      this.agent = agent;
      setFlags(agent.getFlags());
    }

    /**
     * This method is called when the corresponding user-interface item on the
     * manager is clicked.
     */
    @Override
    public CommandArtifact doInvoke() throws Exception
    {
      // This MgrCommand's owner will be the point manager
      BNPointManager pointMgr = (BNPointManager)getOwner();

      // Calls the doInvoke method on the instance of the BINPointMgrAgent
      CommandArtifact retVal = agent.doInvoke(pointMgr, NMgrUtil.findNNetwork(pointMgr), NMgrUtil.findNDevice(pointMgr), NMgrUtil.findNPointDeviceExt(pointMgr));

      // Updates the MgrCommands to reflect any changes that could have happened
      // in the 'doInvoke' callback
      pointMgr.getController().updateCommands();

      return retVal;
    }
  }
}
