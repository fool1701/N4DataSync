/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.sys.*;
import javax.baja.registry.*;
import javax.baja.security.*;

/**
 * AgentInfo provides summary information for an 
 * agent within an AgentList.
 *
 * @author    Brian Frank
 * @creation  5 May 04
 * @version   $Revision: 5$ $Date: 4/13/06 1:18:25 PM EDT$
 * @since     Baja 1.0
 */
public interface AgentInfo
{
  
  /**
   * Create an instance of the agent.
   */
  public BObject getInstance();  
  
  /**
   * Get the agent id which is used to uniquely
   * identify it in AgentLists.
   */
  public String getAgentId();
  
  /**
   * Get the TypeInfo for the instance which would be
   * returned by the {@code getInstance()}.
   */
  public TypeInfo getAgentType();     

  /**
   * If this agent is associated with a specific application
   * then return the application name key.  Otherwise return
   * null if a global available agent.  Application name is
   * used with WbProfile and HxProfile to implement application
   * specific view filtering.  An example for application specific 
   * agent declaration in module-include.xml:
   * <pre>  
   *   &lt;type name="CustomAlarmConsole" class="acme.BCustomAlarmConsole"&gt;
   *     &lt;agent app="acmeMyAppliance"&gt;
   *       &lt;on type="alarm:ConsoleRecipient"/&gt;
   *     &lt;/agent&gt;
   *   &lt;/type&gt;
   * </pre>             
   *
   * @since Niagara 3.1
   */
  public String getAppName();

  /**
   * Get an array of the TypeInfos which this
   * agent is directly registered on.
   */
  public TypeInfo[] getAgentOn();
  
  /**
   * Get the display name for the agent.
   */
  public String getDisplayName(Context cx);

  /**
   * Get the icon for the agent.
   */
  public BIcon getIcon(Context cx);

  /**
   * Get the permissions required to use this agent.
   */
  public BPermissions getRequiredPermissions();
  
}
