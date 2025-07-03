/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.util;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BPermissions;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.TypeNotFoundException;
import javax.baja.util.Lexicon;
import com.tridium.util.PxUtil;

/**
 * This is used to rename the auto manager views based on the name of the driver
 * that is built off of this framework.
 * <p>
 * For example, instead of being named 'NDeviceManager', the device manager is
 * named 'Your Driver Device Manager' 'Your Driver' is the name of a driver that
 * inherits from this framework.
 * <p>
 * This basically wraps another AgentInfo object but provides its own
 * implementation of the 'getDisplayName' method.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class AgentInfoUtil
  implements AgentInfo
{
  /**
   * Creates an instance of this class, that wraps the given AgentInfo, yet
   * provides its own implementation of the 'getDisplayName(Context)' method.
   *
   * @param nAgentInfo         the default instance of AgentInfo for a
   *                           BComponent
   * @param driverModuleName   the name of the driver that builds off of this
   *                           framework
   * @param displayNameKey     the name of the key in the driver's Lexicon from
   *                           which to lookup the text for 'Device Manager'
   * @param displayNameDefault the default text to use for the String 'Device
   *                           Manager' in the event that the driver's Lexicon
   *                           does not define the 'displayNameKey'
   */
  public AgentInfoUtil(AgentInfo nAgentInfo, String driverModuleName, String displayNameKey, String displayNameDefault)
  {
    this.nAgentInfo = nAgentInfo;
    this.driverModuleName = driverModuleName;
    this.displayNameKey = displayNameKey;
    this.displayNameDefault = displayNameDefault;
  }

////////////////////////////////////////////////////////////////////////////
// AgentInfo
////////////////////////////////////////////////////////////////////////////

  /**
   * Get the agent id which is used to uniquely identify it in AgentLists.
   */
  @Override
  public String getAgentId()
  {
    return nAgentInfo.getAgentId();
  }

  /**
   * Get an array of the TypeInfos which this agent is directly registered on.
   */
  @Override
  public TypeInfo[] getAgentOn()
  {
    return nAgentInfo.getAgentOn();
  }

  /**
   * Get the TypeInfo for the instance which would be returned by the
   * <code>getInstance()<code>.
   */
  @Override
  public TypeInfo getAgentType()
  {
    return nAgentInfo.getAgentType();
  }

  /**
   * Customized the 'displayName' as described in the Java Doc for the class
   * definition.
   */
  @Override
  public String getDisplayName(Context cx)
  {
    if (driverModuleName != null)
    {
      // Gets name of driver module
      String driverDisplayName = TextUtil.toFriendly(driverModuleName);

      if (driverDisplayName != null)
      {
        // Gets suffix name of the manager
        Lexicon driverLex = Lexicon.make(driverModuleName);
        String suffix = displayNameDefault;
        if (driverLex != null)
        {
          suffix = driverLex.get(displayNameKey, displayNameDefault);
        }

        // Returns the name of the driver module concat'ed with the name of the manager
        return driverDisplayName + ' ' + suffix;
      }
    }
    return nAgentInfo.getDisplayName(cx);
  }

  /**
   * Get the icon for the agent.
   */
  @Override
  public BIcon getIcon(Context cx)
  {
    return nAgentInfo.getIcon(cx);
  }

  /**
   * Create an instance of the agent.
   */
  @Override
  public BObject getInstance()
  {
    return nAgentInfo.getInstance();
  }

  /**
   * Get the permissions required to use this agent.
   */
  @Override
  public BPermissions getRequiredPermissions()
  {
    return nAgentInfo.getRequiredPermissions();
  }

////////////////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////////////////

  /**
   * Used by agents of auto manager views to rename manager in agentlist by
   * prepending module name.
   *
   * @param agents           The result of calling super.getAgents(cx) on the
   *                         component that calls this method.
   * @param driverModuleName The name of the module.
   * @param mgrTypeSpec      the type spec of the manager whose name will be
   *                         changed
   * @param mgrSuffixKey     the lexicon key to look up for the suffix, for
   *                         example, "DeviceManager", in case the driver
   *                         developer wishes to customize the manager's name
   *                         (he or she can do so from his or her driver's
   *                         Lexicon)
   * @param mgrSuffixDefault the default suffix. This is used if the driver
   *                         developer does not provide a 'mgrSuffixKey' in his
   *                         or her driver's Lexicon to customize the manager's
   *                         name (for example, "Device Manager")
   * @return the AgentList with the one particular agent renamed as prescribed.
   */
  public static AgentList getAgentsHelp(AgentList agents, String driverModuleName, String mgrTypeSpec, String mgrSuffixKey, String mgrSuffixDefault)
  {
    TypeInfo managerTypeInfo = Sys.getRegistry().getType(mgrTypeSpec);
    AgentInfo managerAgentInfo =
      new AgentInfoUtil(
        managerTypeInfo.getAgentInfo(),
        driverModuleName,
        mgrSuffixKey,
        mgrSuffixDefault);

    boolean containsExtendedVersionOfMgr = false;
    boolean renamedAgent = false;

    // Determines if the list of agents contains an agent whose type is a descendant of the agent type identified
    // by the given mgrTypeSpec. If so, then we need to assure that the descendant agent shows up instead of the
    // default agent.
    int insertAtIndex = 0;
    for (int i = 0; i < agents.size(); ++i)
    {
      TypeInfo agentTypeInfo = agents.get(i).getAgentType();
      if (agentTypeInfo.is(managerTypeInfo) && !agentTypeInfo.equals(managerTypeInfo))
      {
        // For workbench managers, put the extended version of the manager at the top, to make sure
        // it's higher up the list than any ux managers.
        if (mgrTypeSpec.equals(N_DEVICE_MANAGER) || mgrTypeSpec.equals(N_POINT_MANAGER))
        {
          agents.add(insertAtIndex, agents.get(i)); // #add removes the original before adding at the new index
          insertAtIndex++; // add one, to preserve the original order if there are multiple wb managers.
        }

        containsExtendedVersionOfMgr = true;
      }
    }

    // Loops through the given list of agents...
    for (int i = 0; i < agents.size(); ++i)
    {
      // If an agent from the agent list matches the agent as identified by the given mgrTypeSpec
      if (agents.get(i).getAgentType().equals(managerTypeInfo))
      {
        agents.remove(i); // Removes the AgentInfo that the Niagara core added automatically
        if (!containsExtendedVersionOfMgr)  // If no modules provide an overridden version of the agent
        {                                   // Then this adds the AgentInfo right back to the list but
          agents.add(managerAgentInfo);  // changes the name, as is the purpose of this method
          renamedAgent = true;
        }
      }
    }

    // If the for loop did not rename an AgentInfo object, then that means that somehow the Niagara
    // core did not place that the corresponding agent type into the agent list. If that is the case,
    // and if no other module defines an extended version of the agent, then this adds the appropriate
    // AgentInfo to the AgentList with given name.
    if (!renamedAgent && !containsExtendedVersionOfMgr)
    {
      agents.add(managerAgentInfo);
    }

    // Issue 12302 Do not place the default Device Manager or Point Manager above Px Pages.
    return PxUtil.movePxViewsToTop(agents);
  }

  /**
   * This method is required for AgentInfo in baja 3.1. For backwards
   * compatibility, it simply returns null.
   */
  @Override
  public String getAppName()
  {
    return null;
  }

  /**
   * Used by agents of auto manager views to refactor the agent list for a
   * Device Manager.
   * <p>
   * If automanager is true, the manager agents are changed to use the module
   * name as their prefix instead of N Driver If false the ndriver manager
   * agents are removed,
   *
   * @param agents         The result of calling super.getAgents(cx) on the
   *                       component that calls this method.
   * @param useAutoManager whether automanager is being used by the module.
   * @param moduleName     the module name of the manager
   * @return the refactored AgentList.
   * @since Niagara 4.8
   */
  public static AgentList processDeviceManagerAgents(AgentList agents, boolean useAutoManager, String moduleName)
  {
    return useAutoManager ? renameAutoManagerDeviceAgents(agents, moduleName) : removeAutoManagerDeviceAgents(agents);
  }

  /**
   * Used by agents of auto manager views to refactor the agent list for a Point
   * Manager.
   * <p>
   * If automanager is true, the manager agents are changed to use the module
   * name as their prefix instead of N Driver If false the ndriver manager
   * agents are removed,
   *
   * @param agents         The result of calling super.getAgents(cx) on the
   *                       component that calls this method.
   * @param useAutoManager whether automanager is being used by the module.
   * @param moduleName     the module name of the manager
   * @return the refactored AgentList.
   * @since Niagara 4.8
   */
  public static AgentList processPointManagerAgents(AgentList agents, boolean useAutoManager, String moduleName)
  {
    return useAutoManager ? renameAutoManagerPointAgents(agents, moduleName) : removeAutoManagerPointAgents(agents);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * This is the AgentInfo object that this object wraps. See the Java Doc with
   * the class description for more details.
   */
  protected AgentInfo nAgentInfo;

  /**
   * This is the driverModuleName that is passed to the constructor.
   */
  protected String driverModuleName;

  /**
   * This is the displayNameKey that is passed to the constructor.
   * <p>
   * This is used to look up text in the Lexicon for a driver that is built on
   * this framework.
   */
  protected String displayNameKey;

  /**
   * This is the displayNameKey that is passed to the constructor.
   * <p>
   * This is used by default, if the driver that is built on this framework does
   * not define a displayNameKey in its lexicon.
   */
  protected String displayNameDefault;

  private static AgentList removeAutoManagerDeviceAgents(AgentList agents)
  {
    agents.remove(N_DEVICE_UX_MANAGER);
    agents.remove(N_DEVICE_MANAGER);
    return agents;
  }

  private static AgentList renameAutoManagerDeviceAgents(AgentList agents, String moduleName)
  {
    try
    {
      AgentInfoUtil.getAgentsHelp(
        agents,
        moduleName,
        N_DEVICE_UX_MANAGER,
        DEVICE_UX_MGR_SUFFIX_KEY,
        DEVICE_UX_MGR_SUFFIX_DEFAULT
      );
    }
    catch (TypeNotFoundException ex)
    {
      // OK if ndriver:NDeviceUxManager doesn't exist, ie ux profile not supported
    }

    return AgentInfoUtil.getAgentsHelp(
      agents,
      moduleName,
      N_DEVICE_MANAGER,
      DEVICE_MGR_SUFFIX_KEY,
      DEVICE_MGR_SUFFIX_DEFAULT
    );
  }


  private static AgentList removeAutoManagerPointAgents(AgentList agents)
  {
    agents.remove(N_POINT_MANAGER);
    agents.remove(N_POINT_UX_MANAGER);
    return agents;
  }

  private static AgentList renameAutoManagerPointAgents(AgentList agents, String moduleName)
  {
    try
    {
      getAgentsHelp(
        agents,
        moduleName,
        N_POINT_UX_MANAGER,
        POINT_UX_MGR_SUFFIX_KEY,
        POINT_UX_MGR_SUFFIX_DEFAULT
      );
    }
    catch (TypeNotFoundException ex)
    {
      // OK if ndriver:NPointUxManager doesn't exist, ie ux profile not supported
    }

    return getAgentsHelp(
      agents,
      moduleName,
      N_POINT_MANAGER,
      POINT_MGR_SUFFIX_KEY,
      POINT_MGR_SUFFIX_DEFAULT
    );
  }

  private final static String N_DEVICE_MANAGER = "ndriver:NDeviceManager";
  private final static String DEVICE_MGR_SUFFIX_KEY = "DeviceManager";
  private final static String DEVICE_MGR_SUFFIX_DEFAULT = "Device Manager";

  private final static String N_DEVICE_UX_MANAGER = "ndriver:NDeviceUxManager";
  private final static String DEVICE_UX_MGR_SUFFIX_KEY = "DeviceUxManager";
  private final static String DEVICE_UX_MGR_SUFFIX_DEFAULT = "Device Ux Manager";

  private final static String N_POINT_MANAGER = "ndriver:NPointManager";
  private final static String POINT_MGR_SUFFIX_KEY = "PointManager";
  private final static String POINT_MGR_SUFFIX_DEFAULT = "Point Manager";

  private final static String N_POINT_UX_MANAGER = "ndriver:NPointUxManager";
  private final static String POINT_UX_MGR_SUFFIX_KEY = "PointUxManager";
  private final static String POINT_UX_MGR_SUFFIX_DEFAULT = "Point Ux Manager";
}
