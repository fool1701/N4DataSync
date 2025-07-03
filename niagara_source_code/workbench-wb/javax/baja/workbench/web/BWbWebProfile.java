/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.web;

import java.util.Arrays;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.file.BExporter;
import javax.baja.file.BIDataFile;
import javax.baja.file.types.text.BPxFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.spy.BSpy;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenu;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BIMenuBar;
import javax.baja.ui.menu.BIMenuItem;
import javax.baja.ui.menu.BISubMenuItem;
import javax.baja.ui.toolbar.BIToolBar;
import javax.baja.user.BUser;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BServletView;
import javax.baja.web.BWebProfileConfig;
import javax.baja.web.IWebEnv;
import javax.baja.web.WebOp;
import javax.baja.web.mobile.BIMobileWebView;
import javax.baja.workbench.BWbProfile;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.BWbStatusBar;

import com.tridium.fox.sys.BFoxSession;
import com.tridium.util.ArrayUtil;
import com.tridium.web.IWebEnvProvider;
import com.tridium.web.WebEnv;
import com.tridium.web.WebProcessException;
import com.tridium.web.session.WebSessionUtil;
import com.tridium.workbench.shell.BNiagaraWbWebShell;

/**
 * BWbWebProfile is the base class for Workbench Profiles to
 * be used either in a web browser in conjunction with the 
 * Java Plugin, or with an application launched via Java 
 * Web Start.
 * <p>
 * All BWbWebProfiles must provide two public constructors,
 * one which takes a BWbShell and one which takes no arguments.
 * 
 * The general policy of subclasses should be to keep the
 * restrictions of the base class since it removes features
 * not applicable to the general desktop profiles.
 * <p>
 * BWbWebProfile provides a pair of methods to test the environment
 * the shell is running in. These methods may be useful if overriding base
 * class methods such as <code>makeToolBar()</code>. The <code>isApplet()</code>
 * method will return <code>true</code> if the shell is running as an
 * Applet inside a web browser. Conversely, the <code> isWebStart()</code> 
 * method will return <code>true</code> if the shell is running as a Java
 * Web Start application: a non-Plugin environment that runs completely
 * outside of a browser. Subclasses of BWbWebProfile may want to use these 
 * methods to tailor their functionality and appearance according to the 
 * current environment. For example, when running as an Applet, the profile 
 * will use the browser's own back and forward buttons, whereas in the Web Start 
 * environment, the profile will provide its own back and forward buttons
 * on the tool bar.
 * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 *
 * @author    Brian Frank on 16 Mar 04
 * @since     Baja 1.0
 */
@NiagaraType
@Deprecated
public abstract class BWbWebProfile
  extends BWbProfile
  implements BIWebProfile, IWebEnvProvider
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.web.BWbWebProfile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbWebProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * All BWbWebProfiles must provide two public constructors,
   * one which takes a BWbShell and one which takes no arguments.
   */
  public BWbWebProfile(BWbShell shell)
  {
    super(shell);
  }

  /**
   * All BWbWebProfiles must provide two public constructors,
   * one which takes a BWbShell and one which takes no arguments.
   */
  public BWbWebProfile()
  {
    super(null);
  }

////////////////////////////////////////////////////////////////
// BTypeConfig.IConfigurable
////////////////////////////////////////////////////////////////

  /**
   * Return the string keys of the configurable properties.
   */
  public String[] listConfig()
  {
    return new String[] {
      theme,
      showWebStartAddressBarParam,
      showWebStartStatusBarParam
    };
  }

  /**
   * Get a configurable property by String key.
   */
  public BValue getConfig(String key)
  {
    switch(key)
    {
      case theme:
        return BFoxSession.getDefaultThemeEnumForSession();

      case showWebStartAddressBarParam:
        return showWebStartAddressBarDefault;

      case showWebStartStatusBarParam:
        return showWebStartStatusBarDefault;

      default:
        return null;
    }
  }

  /**
   * Get the facets for a configurable property.
   */
  public BFacets getConfigFacets(String key)
  {
    switch(key)
    {
      case theme:
        return THEME_FACETS;

      case showWebStartAddressBarParam:
        return BFacets.NULL;

      case showWebStartStatusBarParam:
        return BFacets.NULL;

      default:
        return null;
    }
  }

  /**
   * Set a configurable property by String key.
   */
  public void setConfig(String key, BValue value)
  {
  }

////////////////////////////////////////////////////////////////
// WbProfile
////////////////////////////////////////////////////////////////

  public BIMenuBar makeMenuBar()
  {
    BIMenuBar menuBar = super.makeMenuBar();

    // keep pieces of file
    menuBar.getMenu("file").retainItems(new String[]{ "save", "print", "home", "refresh", "logoff" });
    // leave edit untouched

    // keep pieces of search
    menuBar.getMenu("search").retainItems(new String[]{ "find", "findPrev", "findNext", "replace", "goto" });

    // no bookmark
    menuBar.removeMenu("bookmarks");

    // no options
    BIMenu tools = menuBar.getMenu("tools");
    tools.removeItem("options");

    if(tools.getItemCount() == 0)
      menuBar.removeMenu("tools");

    // no window, but move sidebars up
    BIMenu window = menuBar.getMenu("window");
    BIMenuItem sidebarsParent = window.getItem("sidebars");
    menuBar.removeMenu(window);

    if(sidebarsParent instanceof BISubMenuItem)
    {
      BIMenu sidebars = ((BISubMenuItem) sidebarsParent).getSubMenu();
      ((BISubMenuItem) sidebarsParent).setSubMenu(new BMenu());
      menuBar.addMenu("sidebars", sidebars);
    }

    // no help
    menuBar.removeMenu("help");

    return menuBar;
  }

  public BIToolBar makeToolBar()
  {
    BIToolBar toolBar = super.makeToolBar();
    Arrays.stream(new String[]{"back", "forward", "upLevel", "recentOrds", "open", "saveBog"}).forEach(toolBar::removeButton);
    return toolBar;
  }

  public BWbStatusBar makeStatusBar()
  {
    return null;
  }

  public boolean hasSideBar()
  {
    return true;
  }

  public boolean hasSideBar(TypeInfo typeInfo)
  {
    String t = typeInfo.toString();
    if (t.equals("workbench:NavSideBar")) return true;
    if (t.equals("workbench:PaletteSideBar")) return true;
    if (t.equals("search:SearchSideBar")) return true;
    if (t.equals("template:TemplateSideBar")) return true;
    return false;
  }

  public boolean hasView(BObject target, AgentInfo agentInfo)
  {
    String t = agentInfo.getAgentType().toString();
    
    return !t.startsWith("program:") &&
      !t.equals("hx:HxPxView") &&
      super.hasView(target, agentInfo);
  }

  public boolean hasTools()
  {
    return false;
  }

  public BOrd getNavRootOrd()
  {
    BWbShell shell = getShell();
    if (shell instanceof BNiagaraWbWebShell)
    {
      BNiagaraWbWebShell appShell = (BNiagaraWbWebShell)shell;
      if (appShell.getFoxSession() != null)
        return appShell.getFoxSession().getNavOrd();
    }
    
    return super.getNavRootOrd();
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Returns a joined String array (strs1 + strs2)
   * 
   * @since Niagara 3.5
   */
  protected static String[] append(String[] strs1, String strs2[])
  {
    return ArrayUtil.add(strs1, strs2);
  }
  
  /**
   * Tests if the shell is running in a web browser environment using
   * the Java Plugin. Profiles may wish to use this method to tailor
   * their appearance and functionality according to the environment.
   * 
   * @return true if the shell is running as a Java Applet inside a web browser.
   * @since Niagara 3.8U1 / Niagara 4.2
   */

  @Deprecated
  protected final boolean isApplet()
  {
    BWbShell shell = getShell();

    if (!(shell instanceof BNiagaraWbWebShell))
      return false;
    
    return ((BNiagaraWbWebShell)shell).isApplet();
  }
  
  /**
   * Tests if the shell is running in the Niagara Web Start application.
   * If this method returns true, it means the profile is running outside of a
   * web browser. Profiles may wish to use this method to tailor their appearance
   * and functionality according to the environment.
   * 
   * @return true if the shell is running in a Niagara Web Start launched application.
   * 
   * @since Niagara 3.8U1 / Niagara 4.2
   */
  protected final boolean isWebStart()
  {
    BWbShell shell = getShell();

    if (!(shell instanceof BNiagaraWbWebShell))
      return false;
    
    return ((BNiagaraWbWebShell)shell).isWebStart();
  }
  
////////////////////////////////////////////////////////////////
// Web Start
////////////////////////////////////////////////////////////////

  /**
   * Enables or disables the address bar at the top of the window when
   * this profile is used within the Niagara Web Start application.
   * 
   * The address bar contains a back button, forward button, the location
   * text field and a refresh button. It is part of the Web Start application,
   * not part of the Workbench shell.
   * 
   * The return value of this method has no effect when the profile is used
   * with a Java plug-in hosted applet in a web browser environment.
   * 
   * The default behavior is for the address bar to be shown.
   * 
   * @return true if the Web Start application's address bar should be shown for a user with this profile.
   * 
   * @since Niagara 3.8U1 / Niagara 4.2
   */
  public boolean hasWebStartAddressBar()
  {
    BWbShell shell = getShell();

    if (shell instanceof BNiagaraWbWebShell)
    {
      return ((BNiagaraWbWebShell)shell).getParameter(showWebStartAddressBarParam, true);
    }

    return true;
  }
  
  /**
   * Enables or disables the status bar at the bottom of the window when this
   * profile is used with the Niagara Web Start application. 
   * 
   * The return value of this method has no effect when the profile is used
   * with a Java plug-in hosted applet in a web browser environment.
   * 
   * The default behavior is for the status bar to be shown.
   * 
   * @return true if the Web Start application's status bar should be shown for a user with this profile.
   * 
   * @since Niagara 3.8U1 / Niagara 4.2
   */
  public boolean hasWebStartStatusBar()
  {
    BWbShell shell = getShell();

    if (shell instanceof BNiagaraWbWebShell)
    {
      return ((BNiagaraWbWebShell)shell).getParameter(showWebStartStatusBarParam, true);
    }

    return true;
  }
  
////////////////////////////////////////////////////////////////
// IWebEnvProvider
////////////////////////////////////////////////////////////////
  
  public static IWebEnv webEnv()
  {
    return WebEnvHolder.INSTANCE;
  }
  public final IWebEnv getWebEnv(WebOp op) throws WebProcessException
  {
    return webEnv();
  }
  
  private static class WbWebEnv extends WebEnv
  {
    WbWebEnv()
    {
      filter = add(filter, pxView);
      filter = add(filter, servlet);
      filter = add(filter, exporter);
      filter = add(filter, wbView);
      filter = add(filter, formFactorMax);
      filter = AgentFilter.and(filter, new NoMobileFilter());
    }                           
    
    public AgentInfo getDefaultView(WebOp op, AgentList views)
    {    
      BObject obj = op.get();               
      
      // if a file or spy, use configured default   
      if (obj instanceof BIDataFile || 
          obj instanceof BSpy) 
      {
        if (obj instanceof BPxFile)
          return views.filter(AgentFilter.is(wbView)).getDefault();
        else 
          return super.getDefaultView(op, views);
      }

      // then try first px or wb view
      for(int i=0; i<views.size(); ++i)
      {                                  
        AgentInfo agent = views.get(i);
        TypeInfo agentType = agent.getAgentType();
        if (agentType.is(pxView)) return agent;
        if (wbView != null && agentType.is(wbView)) return agent;
      }

      // use default
      return super.getDefaultView(op, views);
    }
    
    public BWebProfileConfig makeWebProfileConfig()
    {
      return new BWebProfileConfig();
    }
    
    public BWebProfileConfig getWebProfileConfig(BUser user)
    {
      return (BWebProfileConfig)user.getMixIn(BWebProfileConfig.TYPE);
    }
    
    public BIWebProfile getWebProfile(WebOp op)
    {
      BUser user = op.getUser();
      BWebProfileConfig profileConfig =
        WebSessionUtil.getSession(op.getRequest()).getAttribute("profileConfig");
      if (profileConfig == null)
      {
        profileConfig = (BWebProfileConfig)user.getMixIn(BWebProfileConfig.TYPE);
        WebSessionUtil.getSession(op.getRequest()).setAttribute("profileConfig", profileConfig);
      }
      BIWebProfile profile = (BIWebProfile)profileConfig.make();
      return profile;
    }
    
    // TODO: Currently all mobile views are filtered out. This could be improved...
    private static class NoMobileFilter extends AgentFilter
    {
      public boolean include(AgentInfo agent)
      {
        return !agent.getAgentType().is(mobileView); 
      }
    }
  }  
      
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////
  
  public static final String theme = "selectedWorkbenchTheme";

  private static final TypeInfo servlet      = BServletView.TYPE.getTypeInfo();
  private static final TypeInfo exporter     = BExporter.TYPE.getTypeInfo();
  private static final TypeInfo mobileView   = BIMobileWebView.TYPE.getTypeInfo();

  private static final String showWebStartAddressBarParam = "showWebStartAddressBar";
  private static final BBoolean showWebStartAddressBarDefault = BBoolean.TRUE;

  private static final String showWebStartStatusBarParam = "showWebStartStatusBar";
  private static final BBoolean showWebStartStatusBarDefault = BBoolean.TRUE;

  private static final BFacets THEME_FACETS = BFacets.make(BFacets.FIELD_EDITOR, BString.make("workbench:FrozenEnumFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:FrozenEnumEditor"));

  private interface WebEnvHolder
  {
    IWebEnv INSTANCE = new WbWebEnv();
  }
}
