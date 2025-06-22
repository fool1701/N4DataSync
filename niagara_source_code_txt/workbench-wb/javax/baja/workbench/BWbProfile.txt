/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.gx.BImage;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.nav.BRootScheme;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.RegistryException;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenuItem;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BSubMenuItem;
import javax.baja.ui.BWidget;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BIMenuBar;
import javax.baja.ui.options.BUserOptions;
import javax.baja.ui.toolbar.BIToolBar;
import javax.baja.web.js.BIJavaScript;
import javax.baja.workbench.sidebar.BIWbSideBar;
import javax.baja.workbench.sidebar.BWbSideBar;
import javax.baja.workbench.tool.BWbTool;
import javax.baja.workbench.view.BWbView;

import com.tridium.nre.security.SecurityInitializer;
import com.tridium.workbench.shell.BErrorPanel;
import com.tridium.workbench.shell.BNiagaraWbFrame;
import com.tridium.workbench.shell.BNiagaraWbLocatorBar;
import com.tridium.workbench.shell.BNiagaraWbShell;
import com.tridium.workbench.shell.WbCommands;
import com.tridium.workbench.shell.WbMain;
import com.tridium.workbench.web.browser.BWebBrowser;
import com.tridium.workbench.web.browser.BWebBrowserView;

/**
 * BWbProfile is used to customize the workbench for a specific 
 * application.  All BWbProfiles must support a public constructor
 * that takes one argument of type BWbShell.  You may launch the
 * workbench with a specific profile using the command line syntax
 * of "wb -profile:{typespec}".
 *
 * @author    Brian Frank       
 * @creation  16 Mar 04
 * @version   $Revision: 32$ $Date: 7/27/10 6:59:13 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWbProfile
  extends BObject    
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbProfile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Create a new instance of workbench:WbProfile of the specified 
   * type by calling the public constructor that takes one argument 
   * of type BWbShell. 
   */
  public static BWbProfile make(BWbShell shell, Type type)
  {                    
    try
    {
      @SuppressWarnings("unchecked")
      Class<BWbProfile> cls = (Class<BWbProfile>) type.getTypeClass();
      Constructor<BWbProfile> ctor = cls.getConstructor(new Class<?>[] { BWbShell.class });
      return ctor.newInstance(new Object[] { shell });
    }
    catch(Exception e)
    {
      throw new BajaRuntimeException("Cannot call B" + type.getTypeName() + "(WbShell)", e);
    }
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
    
  /**
   * Constructor with all features enabled by default.
   */
  public BWbProfile(BWbShell shell)
  {              
    this.shell = (BNiagaraWbShell)shell;               
  }            
  
  /**
   * No argument constructor (should not be used directly)
   */
  public BWbProfile()
  {
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the shell associated with this profile.  Each
   * profile is associated with exactly one WbShell.
   */
  public final BWbShell getShell()
  {
    return shell;
  }

////////////////////////////////////////////////////////////////
// Bars
////////////////////////////////////////////////////////////////

  /**
   * Make the menu bar for the shell or return null for no menu bar.  
   * Subclasses which wish to alter the default menu bar should call 
   * super, then manipulate the default's component tree.  Refer to  
   * utils available in bajaui:AbstractBar such as strip() and keep().  
   * Run main() to dump the default menu bar.
   * <p>
   * <strong>Note: Starting in Niagara 4, the signature for makeMenuBar() has been modified to return the
   * BIMenuBar interface. Subclasses of BWbProfile that support a custom menu bar will require the menu bar
   * to implement this interface.</strong>
   * </p>
   */
  public BIMenuBar makeMenuBar()
  {
    return shell.commands.makeMenuBar();
  }

  /**
   * Make the tool bar for the shell or return null for no tool bar.
   * Subclasses which wish to alter the default tool bar should call 
   * super, then manipulate the default's component tree.  Refer to  
   * utils available in bajaui:AbstractBar such as strip() and keep().  
   * Run main() to dump the default tool bar.
   * <p>
   * <strong>Note: Starting in Niagara 4, the signature for makeToolBar() has been modified to return the
   * BIToolBar interface. Subclasses of BWbProfile that support a custom toolbar will require the toolbar
   * to implement this interface.</strong>
   * </p>
   */
  public BIToolBar makeToolBar()
  {
    return shell.commands.makeToolBar();
  }

  /**
   * Make the locator bar for the shell or return 
   * null for no locator bar.
   */
  public BWbLocatorBar makeLocatorBar()
  {
    return new BNiagaraWbLocatorBar(shell);
  }

  /**
   * Make the status bar for the shell or return 
   * null for no status bar.
   */
  public BWbStatusBar makeStatusBar()
  {
    return new BWbStatusBar(shell);
  }                       

////////////////////////////////////////////////////////////////
// Error Panel
////////////////////////////////////////////////////////////////

  /**
   * Make an error display for the specified error.
   *
   * @param shell The shell that contains the error.
   * @param msg A message describing the error.
   * @param ord The ord of the object for the current view.
   * @param ex The exception associated with the error if available.
   */
  public BWidget makeErrorDisplay(BWbShell shell, String msg, BOrd ord, Throwable ex)
  {
    return new BErrorPanel(shell, msg, ord, ex);
  }
      
////////////////////////////////////////////////////////////////
// View
////////////////////////////////////////////////////////////////
  
  /**
   * Return the application name for this profile. The appName
   * serves as a registry key to enable certain agents/views
   * that only make sense within a specific application.  See
   * AgentInfo.getAppName() for more details.  Default implementation 
   * returns null which filters out all views with a non-null
   * appName.
   */
  public String getAppName()
  {
    return null;
  }  
  
  /**
   * Return the application name(s) for this profile. The appName(s)
   * serve as registry keys to enable certain agents/views
   * that only make sense within a specific application.  See
   * AgentInfo.getAppName() for more details.  The default implementation 
   * returns no app names if the getAppName is null. If the AppName is 
   * non-null, the default implemenation is to return that single app name
   * in a one entry array.
   *
   * @since Niagara 3.4
   */
  public String[] getAppNames()
  {
    if(getAppName() != null)
      return new String[]{ getAppName()};
    else
      return new String[0];
  }  

  /**
   * Return if the workbench supports the specified WbView.
   */
  public boolean hasView(BObject target, AgentInfo agentInfo)
  {                                      
    return true;
  }

  /**
   * Return views supported by workbench on the specified object.
   * The first agent in this list is the default.
   * 
   * @since Niagara 3.6
   */
  public AgentList getAgents(BObject target)
  {                                      
    AgentList list = target.getAgents();

    for (String devView : DEV_VIEWS) {
      if (!devViewsEnabled.get(devView)) {
        list.remove(devView);
      }
    }

    if (BWebBrowser.BROWSING_DISABLED)
    {
      for (AgentInfo info : list.list())
      {
        if (info.getAgentType().is(BIJavaScript.TYPE) ||
          info.getAgentType().is(BWebBrowserView.TYPE))
        {
          list.toBottom(info);
        }
      }
    }
    
    return list;
  }
  
  /**
   * Provides the opportunity to modify or replace a view
   * prior to it being displayed.
   * 
   * @since Niagara 3.6
   */
  public BWbView customizeView(BWbView view)
  {
    return view;
  }
  
////////////////////////////////////////////////////////////////
// SideBar
////////////////////////////////////////////////////////////////

  /**
   * Return if the workbench supports WbSideBar.
   */
  public boolean hasSideBar()
  {
    return true;
  }              
  
  /**
   * Return if the specified sidebar is available.  This 
   * method is only applicable if hasSideBar() returns true.
   * Default implementation returns true if typeInfo has a
   * agent appName equal one of the profile appNames, or if typeInfo
   * does not have an appName and there are no profile appNames.
   * Run main() to dump the installed side bars.
   */
  public boolean hasSideBar(TypeInfo typeInfo)
  {
    try
    {
      
      String[] appNames = getAppNames();
      
      //return true if agentInfo is not appSpecific
      if(typeInfo.getAgentInfo().getAppName() == null)
        return true;
        
      //agent is appSpecific
      for(int i=0; i<appNames.length; i++)
      {
        //return true if app matches one of the profile appNames
        if(appNames[i].equals(typeInfo.getAgentInfo().getAppName()))
          return true;
      }
      
      //return false if agentInfo is appSpecific but none of the appNames of the WbProfile match the appName
      return false;
    }
    catch (RegistryException e) 
    {
      return getAppName() == null;
    }    
  }         
  
  /**
   * Get the list of sidebars to display by default.
   */
  public BIWbSideBar[] getDefaultSideBars()
  {
    return new BIWbSideBar[] { new com.tridium.workbench.sidebars.BNavSideBar() };
  }
  
////////////////////////////////////////////////////////////////
// Tools
////////////////////////////////////////////////////////////////
                                        
  /**
   * Return if the workbench supports WbTools.
   */
  public boolean hasTools()
  {
    return true;
  }

  /**
   * Return if the specified tool is available.  This 
   * method is only applicable if hasTools() returns true.
   * Run main() to dump the installed side bars.
   */
  public boolean hasTool(TypeInfo typeInfo)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Quick Search
////////////////////////////////////////////////////////////////

  /**
   * Return a BWidget instance for the global quick search next to the menu bar
   * if it should be visible/enabled for the current view.  This quick search
   * widget is based on finding a valid, registered BIQuickSearch
   * agent on the view or profile.  Returning null for this method will remove
   * (hide) the quick search box from the menu bar.  Note that this method could
   * be called frequently on the client (Workbench) side, so avoid or minimize
   * network calls when implementing this method (consider using caching to reduce
   * network calls).
   *
   * @param view The BWbView instance for which to retrieve a BIQuickSearch instance
   * @since Niagara 4.0
   */
  public BWidget getQuickSearch(BWbView view)
  {
    return shell.commands.getQuickSearch(view);
  }

  /**
   * Return if this workbench profile supports the specified quick search
   * on the specified WbView. By default, this is called when it is
   * discovered that the BWbView has a BIQuickSearch registered as an agent
   * on it, or the BWbProfile has a BIQuickSearch registered agent. Note that
   * this method could be called frequently on the client (Workbench) side, so
   * avoid or minimize network calls when implementing this method (consider
   * using caching to reduce network calls).
   *
   * @param quickSearchInfo The AgentInfo for the BIQuickSearch type that is
   *                        registered as an agent on the BWbView or BWbProfile
   * @param view The BWbView instance for which to determine whether the
   *             quickSearchInfo is a valid BIQuickSearch implementation in
   *             this profile
   * @since Niagara 4.0
   */
  public boolean hasQuickSearch(AgentInfo quickSearchInfo, BWbView view)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////
  
  /**
   * Return the icon which should be used for the shell's
   * frame window (not applicable in applets).  The default
   * icon is configured in brand.properties via the key
   * "workbench.icon".
   */
  public BImage getFrameIcon()
  {
    if (SecurityInitializer.getInstance().isFips())
    {
      BIcon wbFipsIcon = BIcon.make(BIcon.make(WbMain.brandIcon), BIcon.make(WbMain.brandFIPSBadge));
      return BImage.make(wbFipsIcon);
    }
    else
    {
      return BImage.make(WbMain.brandIcon);
    }
  }

  /**
   * Return the title which should be used for the shell's
   * frame window (not applicable in applets).  The default
   * title text is configured in brand.properties via the 
   * key "workbench.title".
   */
  public String getFrameTitle()
  {
    if (SecurityInitializer.getInstance().isFips())
      return WbMain.brandTitle + " " + WbMain.FIPS_TITLE_SUFFIX;
    else
      return WbMain.brandTitle;
  }

  /**
   * Return if the specified UserOptions type is available.  
   * Run main() to dump the installed UserOptions.
   */
  public boolean hasUserOptions(TypeInfo typeInfo)
  {           
    return true;
  }                   
  
  /**
   * Get the ord to use for startup.
   */
  public BOrd getStartOrd()
  {                   
    return WbCommands.getAboutOrd();
  }
  
  /**
   * Get the ord to use when a new session is opened.
   * When the session is opened, the default
   * view for the returned ord will be displayed.
   *
   * @param session A newly opened session.
   * @param def The default ord for the session type.
   */
  public BOrd getOpenOrd(BISession session, BOrd def)
  {
    return def;
  }

  /**
   * Get the ord to use for the home command.
   */
  public BOrd getHomeOrd()
  {                   
    return shell.commands.getHomeOrd();
  }                      
  
  /**
   * Get the ord used to root navigation.
   */
  public BOrd getNavRootOrd()
  {                                      
    return BRootScheme.ORD;
  }  

  /**
   * Return true if this ord can be hyperlinked to, or false
   * if it cannot.
   */  
  public boolean canHyperlink(BOrd ord)
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Main
////////////////////////////////////////////////////////////////
    
  /**
   * This main provides a console utility to dump the menu bar, 
   * tool bar, sidebars, and tools installed on the local machine.
   */
  public static void main(String[] args)
  {                              
    BWbProfile p = new BNiagaraWbFrame(WbMain.defaultProfileType).getProfile();

    System.out.println("MenuBar");
    BIMenu[] menus = p.makeMenuBar().getMenus();
    for(int i=0; i<menus.length; ++i)
    {         
      System.out.println("  " + menus[i].asWidget().getName());
      dumpMenu(menus[i], "  ");
    }

    System.out.println("ToolBar");
    BComponent[] toolBar = p.makeToolBar().asWidget().getChildComponents();
    for(int i=0; i<toolBar.length; ++i)
    {
      if (toolBar[i] instanceof BSeparator)
       System.out.println("  ---");
      else
       System.out.println("  " + toolBar[i].getName());
    }
    
    System.out.println("SideBars");
    TypeInfo[] sideBars = BWbSideBar.getInstalled();
    for(int i=0; i<sideBars.length; ++i)
      System.out.println("  " + sideBars[i]);    
      
    System.out.println("Tools");
    TypeInfo[] tools = BWbTool.getInstalled();
    for(int i=0; i<tools.length; ++i)
      System.out.println("  " + tools[i]);    
      
    System.out.println("UserOptions");
    TypeInfo[] options = Sys.getRegistry().getConcreteTypes(BUserOptions.TYPE.getTypeInfo());
    for(int i=0; i<options.length; ++i)
      System.out.println("  " + options[i]);    
  }                      
  
  static void dumpMenu(BIMenu menu, String indent)
  { 
    String itemIndent = indent + "  ";               
    System.out.println(indent + "{");
    
    BComponent[] items = menu.asWidget().getChildComponents();
    for(int i=0; i<items.length; ++i)
    {              
      BComponent item = items[i];
      if (item instanceof BSeparator)    
      {
        System.out.println(itemIndent + "---");
      }
      else if (items[i] instanceof BSubMenuItem)
      {
        System.out.println(itemIndent + item.getName());
        dumpMenu(((BSubMenuItem)items[i]).getMenu(), itemIndent);
      }
      else if (items[i] instanceof BMenuItem)
      {                                      
        BMenuItem menuItem = (BMenuItem)item;
        if (menuItem.getCommand() instanceof WbCommands.ToolCommand) continue;
        if (menuItem.getCommand() instanceof WbCommands.SideBarCommand) continue;        
        System.out.println(itemIndent + item.getName());
      }
      else
      {
        System.out.println(itemIndent + item.getName() + " [" + item.getType() + "]");
      }
    }
    System.out.println(indent + "}");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BNiagaraWbShell shell;

  private static final String[] DEV_VIEWS = {
    "bql:CollectionView",
    "bajaui:CollectionView"
  };

  private static final Map<String, Boolean> devViewsEnabled = new HashMap<>();
  static {
    for (String s : DEV_VIEWS) {
      devViewsEnabled.put(s, AccessController.doPrivileged((PrivilegedAction<Boolean>) () ->
        Boolean.getBoolean("profile.enableDevView." + s)));
    }
  }
}
