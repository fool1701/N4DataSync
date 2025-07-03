/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.agent.BAbstractPxView;
import javax.baja.naming.BISession;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.registry.ModuleInfo;
import javax.baja.registry.TypeInfo;
import javax.baja.space.BISpace;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BObject;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.util.Version;
import javax.baja.web.BIFormFactorMax;
import javax.baja.web.BIOffline;
import javax.baja.web.BIWebOnly;
import javax.baja.workbench.view.BWbView;
import com.tridium.fox.session.FoxSession;
import com.tridium.fox.sys.BFoxClientConnection;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.fox.sys.broker.BBrokerChannel;
import com.tridium.nre.diagnostics.DiagnosticUtil;
import com.tridium.workbench.shell.WbMain;

/**
 * WbSys provides static method APIs specific to a workbench VM.
 *
 * @author    Brian Frank on 16 Apr 01
 * @version   $Revision: 10$ $Date: 8/25/09 11:36:24 AM EDT$
 * @since     Baja 1.0
 */
public final class WbSys
{ 

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
   * Returns an agent filter which retrieves all view agents for a target
   * regardless of current workbench profile.
   * 
   * @return {@link AgentFilter}
   * @since Niagara 3.7
   */
  public static AgentFilter getBaseViewsFilter()
  {
    if (baseFilter == null)
    {
      AgentFilter wb = AgentFilter.is(BWbView.TYPE);
      AgentFilter px = AgentFilter.is(BAbstractPxView.TYPE);
      AgentFilter wv = AgentFilter.and(AgentFilter.is(BIFormFactorMax.TYPE), AgentFilter.not(AgentFilter.is(BIWebOnly.TYPE)));
      AgentFilter types = AgentFilter.or(wb, px, wv);
      baseFilter = AgentFilter.and(types, WbMain.licenseFilter);
    }    
    
    return baseFilter;
  }
  
  /**
   * Returns a filtered list of views that can be used for the specified object.
   *
   * @param owner The Widget owner. This Widget is used to find the Workbench Profile for view filtering.
   * @param target The target object used for view filtering.
   * @param predicate Additional filtering of agents.
   * @return Returns a filtered list of views.
   */
  public static AgentList getFilteredViewList(BWidget owner, BObject target, Predicate<AgentInfo> predicate)
  {
    
    // figure out profile being used
    BWbProfile profile = null;
    BWidgetShell shell = owner.getShell();
    if (shell instanceof BWbShell)
      profile = ((BWbShell)shell).getProfile();
    if (profile == null)
    {
      try
      {
        //attempt to use the preferred BWbProfile subclass Constructor
        profile = BWbProfile.make(null, WbMain.defaultProfileType);
      }
      catch(Exception e)
      {
        //attempt to use default constructor in case the preferred Constructor does not handle a null WbShell
        profile = (BWbProfile)WbMain.defaultProfileType.getInstance();
      }
    }

    final BWbProfile p = profile;
    Predicate<AgentInfo> agentPredicate = getBaseViewsFilter().toPredicate()
      // Filter via profile view.
      .and(agent -> p.hasView(target, agent))
      // Filter via profile app name.
      .and(agent -> agent.getAppName() == null || Arrays.stream(p.getAppNames())
        .filter(name -> name.equals(agent.getAppName()))
        .collect(Collectors.counting()) > 0)
      .and(predicate);

    return remoteFilter(target, profile.getAgents(target).filter(agentPredicate));
  }

  /**
   * Filters out views that aren't installed on the connected online Station. If no
   * connection can be found or used then the original list of agents is returned.
   *
   * @param target The view's target we're filtering upon.
   * @param list The AgentList to be filtered.
   * @return Agents that may have been remotely filtered.
   */
  private static AgentList remoteFilter(BObject target, AgentList list)
  {
    Collection<TypeInfo> typeInfos = Arrays.stream(list.list())
      .map(AgentInfo::getAgentType)
      .filter(info -> info.is(BIFormFactorMax.TYPE))
      .collect(Collectors.toList());

    if (typeInfos.size() > 0 && (target instanceof BISpaceNode || target instanceof BISpace))
    {
      BISession session = target instanceof BISpaceNode ?
        ((BISpaceNode)target).getSession() : ((BISpace)target).getSession();

      if (session != null &&
          session instanceof BFoxSession)
      {
        BFoxSession foxSession = (BFoxSession) session;
        BFoxClientConnection connection = foxSession.getConnection();

        if (connection != null &&
          connection.isConnected())
        {

          if (connection.getRemoteVersion().compareTo(filterAgentVersion) >= 0)
          {
            Map<String, Version> filteredTypeInfos = populateCaches(connection, typeInfos);

            if (filteredTypeInfos != null)
            {
              FoxSession fsession = connection.session();
              final Map<String, Version> validCache =
                fsession.getFromCache(validRemoteTypesKey, key -> new HashMap<>());

              // Only allow valid types to be filtered through.
              list = list.filter(info -> removeAllOnlineWebWidgets(info)
                || validCache.containsKey(info.getAgentType().toString()));
            }
          }
        }
        else
        {
          list = list.filter(WbSys::removeAllOnlineWebWidgets);
        }
      }
      else
      {
        list = list.filter(WbSys::removeAllOnlineWebWidgets);
      }
    }

    return list;
  }

  private static boolean removeAllOnlineWebWidgets(AgentInfo info)
  {
    return !info.getAgentType().is(BIFormFactorMax.TYPE) ||
      info.getAgentType().is(BIOffline.TYPE);
  }


  /**
   * Check whether the all the remote ux modules are present in the client or not.
   * @deprecated since Niagara 4.6. The typeSpec is now ignored because all ux
   * versions are now checked.
   * @see javax.baja.workbench.WbSys#isRemoteVersionTheSame(FoxSession)
   */
  @Deprecated
  public static boolean isRemoteVersionTheSame(FoxSession foxSession, String typeSpec)
  {
    return isRemoteVersionTheSame(foxSession);
  }

  /**
   * Check whether the all the remote ux modules are present in the client or not.
   * This is only checked once per fox session and the result is cached.
   * @since Niagara 4.6
   */
  public static boolean isRemoteVersionTheSame(FoxSession foxSession)
  {
    try
    {
      BFoxClientConnection connection = (BFoxClientConnection) foxSession.conn();
      if (connection != null &&
        connection.isConnected())
      {
        BBrokerChannel brokerChannel = (BBrokerChannel) connection.getChannels().get("station");
        FoxSession fsession = connection.session();
        if (brokerChannel != null && fsession != null)
        {
          return fsession.getFromCache(isSameVersion, key -> DiagnosticUtil.diagnoseIfLoggable("wb.isSameVersion", () ->
          {
            try
            {
              List<ModuleInfo> moduleInfos= new ArrayList<>();
              moduleInfos.add(Sys.getRegistry().getModule("workbench", RuntimeProfile.wb));
              moduleInfos.add(Sys.getRegistry().getModule("hx", RuntimeProfile.wb));
              return isVersionTheSame(brokerChannel.getRemoteModuleVersion(Collections.singleton(RuntimeProfile.ux), moduleInfos).orElse(null), String.valueOf(connection.getRemoteHost()));
            }
            catch (Exception e)
            {
              logger.log(Level.WARNING, "BrokerChannel -> getRemoteModuleVersion", e);
            }
            return false;
          }));
        }
      }
      return false;
    }
    catch(Exception e)
    {
      logger.log(Level.FINE, "Cannot determine if remote and local versions match", e);
      return false;
    }
  }

  /**
   * Given a map of module and version information, return true if that information is all present in the local client.
   * @param remoteModuleVersions A Map with a key that is formatted as "moduleName runtimeProfile" and the value is the Vendor Version. If null the versions are unknown.
   * @param hostName The remote hostName.
   *
   * @since Niagara 4.6
   */
  public static boolean isVersionTheSame(Map<String, Version> remoteModuleVersions, String hostName)
  {
    if (remoteModuleVersions == null)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.log(Level.FINE, "Not the same version, version unknown for host " + hostName);
      }
      return false;
    }

    for (Map.Entry<String, Version> tuple : remoteModuleVersions.entrySet())
    {
      String moduleName = tuple.getKey();
      String moduleDisplayName = moduleName.replace(' ', '-');
      String[] split = moduleName.split(" ");
      Version version = tuple.getValue();
      ModuleInfo info = null;
      try
      {
        info = Sys.getRegistry().getModule(split[0], RuntimeProfile.valueOf(split[1]));
      }
      catch(ModuleNotFoundException ignore) {}

      if (info == null)
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.log(Level.FINE, "Not the same version for host " + hostName + ", client is missing module: " + moduleDisplayName);
        }
        return false;
      }
      if (!info.getVendorVersion().equals(version))
      {
        if (logger.isLoggable(Level.FINE))
        {
          logger.log(Level.FINE, "Not the same version for host " + hostName + ", client has a different version for module: " + info.getModulePartName()
            + " " + info.getVendorVersion() + " != " + version);
        }

        return false;
      }
    }
    if (logger.isLoggable(Level.FINE))
    {
      logger.log(Level.FINE, "Version is the same for host " + hostName + ", checked " + remoteModuleVersions.size() + " modules");
    }
    return true;
  }


  /**
   * Populate the valid and invalid caches with the collection of TypeInfos.
   * If a connection cannot be obtained or if there is an Exception, return null.
   * @param connection
   * @param typeInfos
   * @return the Filtered TypeInfos
   */
  private static Map<String, Version> populateCaches(BFoxClientConnection connection, Collection<TypeInfo> typeInfos)
  {

    if (connection != null &&
      connection.isConnected())
    {
      BBrokerChannel brokerChannel = (BBrokerChannel) connection.getChannels().get("station");
      FoxSession fsession = connection.session();
      if (brokerChannel != null && fsession != null)
      {
        final Map<String, Version> validCache =
          fsession.getFromCache(validRemoteTypesKey, key -> new HashMap<>());

        final Collection<String> invalidCache =
          fsession.getFromCache(invalidRemoteTypesKey, key -> new HashSet<>());

        try
        {
          Map<String, Version> filteredTypeInfos = brokerChannel.checkTypes(typeInfos.stream()
            .filter(type -> !validCache.containsKey(type.toString()) && !invalidCache.contains(type.toString()))
            .collect(Collectors.toList()));

          // Add all valid types for the array.
          filteredTypeInfos.forEach((type, version) -> validCache.merge(type, version, (t, v) -> v));

          // Update the invalid type cache by filtering out any remote types that
          // weren't returned.
          invalidCache.addAll(typeInfos.stream()
            .filter(type -> !filteredTypeInfos.containsKey(type.toString()))
            .map(TypeInfo::toString)
            .collect(Collectors.toList()));
          return filteredTypeInfos;
        }
        catch (Exception e)
        {
          logger.log(Level.SEVERE, "BrokerChannel -> Check Types", e);
        }
      }
    }
    return null;
  }

  private static final Logger logger = Logger.getLogger("wb.sys");
  private static final String validRemoteTypesKey = "validRemoteTypes";
  private static final String invalidRemoteTypesKey = "invalidRemoteTypes";
  private static final String isSameVersion = "isSameVersion";

  private static final Version filterAgentVersion = new Version("4.0.0");
  private static AgentFilter baseFilter;
}
