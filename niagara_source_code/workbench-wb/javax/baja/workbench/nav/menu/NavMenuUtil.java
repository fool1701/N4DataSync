/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.menu;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.gx.BImage;
import javax.baja.naming.BISession;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.ui.BActionMenuItem;
import javax.baja.ui.BHyperlinkMode;
import javax.baja.ui.BMenu;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.CommandEvent;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.Subject;
import javax.baja.ui.commands.HyperlinkCommand;
import javax.baja.ui.commands.InvokeActionCommand;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BFolder;
import javax.baja.workbench.BWbLocatorBar;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.WbSys;
import com.tridium.file.types.bog.BBogFile;
import com.tridium.util.ObjectUtil;
import com.tridium.util.PxUtil;
import com.tridium.workbench.px.PxEditorApi;
import com.tridium.workbench.shell.WbCommands;

/**
 * NavMenuUtil provides common routes for building common
 * types of nav node menus.
 *
 * @author    Brian Frank       
 * @creation  3 Feb 03
 * @version   $Revision: 31$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
public class NavMenuUtil
{ 

////////////////////////////////////////////////////////////////
// Menu Factories
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>BNavMenuAgent.makeFor(target).makeMenu(owner, target)</code>.
   */
  public static BMenu makeMenu(BWidget owner, BObject target)
  {                                                                                            
    return BNavMenuAgent.makeFor(target).makeMenu(owner, target);
  }

  /**
   * Given a Subject containing a selection set of BObjects, return
   * a merged menu of the NavMenus for each object in the selection.  
   * See <code>merge(BMenu, BMenu)</code> for semantics of a merge.  
   * Return null if there is no merge solution.
   */
  public static BMenu makeMenu(BWidget owner, Subject subject)
  {                                
    // batch lease, so that BComponentMenuAgent 
    // doesn't end up doing it individually
    subject.lease();
    
    BMenu result = null;
    for(int i=0; i<subject.size(); ++i)
    {                            
      Object x = subject.get(i);
      
      // if selection is not BObject, then there can be no menu
      if (!(x instanceof BObject)) return null;
      
      // make nav menu for object, if this object type doesn't
      // support a nav menu, then there can be no menu
      BObject o = (BObject)x;  
      BMenu menu = BNavMenuAgent.makeFor(o).makeMenu(owner, o);
      if (menu == null) return null;
      
      // if this is the first object to be processed, we just 
      // use the menu, otherwise, we need to merge this object's
      // menu with our current merge result - if the merge produces
      // null, then there can be no menu
      if (result == null) 
      {
        result = menu;
      }
      else 
      {
        result = merge(result, menu);
        if (result == null) return null;
      }        
    }           
    return result;
  }

////////////////////////////////////////////////////////////////
// Merge
////////////////////////////////////////////////////////////////

  /**
   * Given two menus, merge them into one single menu.  Merging
   * menus is based on the <code>Command.merge()</code> method which 
   * allows two instances of a given Command from the different menus 
   * to be collapsed into one Command which performs both tasks.
   * Menu item's are only merged if the have the same slot name and
   * map to Commands which can be merged.  Any items which cannot
   * be merged are stripped from the menu.  If merged menu has no
   * items then return null. 
   */
  public static BMenu merge(BMenu a, BMenu b)
  { 
    if (a == null || b == null) return null;
    
    BMenu m = new BMenu();                                  
    boolean hasItems = false;
    
    // try to match each kid in a with something in b
    BComponent[] akids = a.getChildComponents();
    for(int i=0; i<akids.length; ++i)
    {                                                  
      // find match by name in b
      String name = akids[i].getName();
      BValue bval = b.get(name);
      if (!(bval instanceof BComponent)) continue;
      
      // we have two items which match by name now
      BComponent akid = akids[i];
      BComponent bkid = (BComponent)bval;
      
      // if not same menu item type, then bail
      if (akid.getClass() != bkid.getClass()) continue;
      
      // if both separators, then add separator
      if (akid instanceof BSeparator)
      {
        m.add(name, new BSeparator());
        continue;
      }          
      
      // if both are action menu items, then check commands
      if (akid instanceof BActionMenuItem)
      {
        Command acmd = ((BActionMenuItem)akid).getCommand();
        Command bcmd = ((BActionMenuItem)bkid).getCommand();
        if (acmd != null && bcmd != null)
        {
          Command mcmd = acmd.merge(bcmd);
          if (mcmd != null) { m.add(name, new BActionMenuItem(mcmd)); hasItems = true; }
        }
      }                         
    }   
    
    // if no child components return null
    if (!hasItems) return null;
    
    // normalize and return
    m.removeConsecutiveSeparators();
    return m;
  }

////////////////////////////////////////////////////////////////
// Views Menu
////////////////////////////////////////////////////////////////

  /**
   * Build a views menu for the specified target.
   */
  public static BMenu makeViewsMenu(BWidget owner, BObject target)
  {
    BINavNode navNode = (BINavNode)target;
    BOrd baseOrd = navNode.getNavOrd();
    return makeViewsMenu(owner, target, baseOrd);
  }
  
  /**
   * Build a views menu for the specified target.
   */
  public static BMenu makeViewsMenu(BWidget owner, BObject target, BOrd baseOrd)
  {                      
    final UiLexicon lex = UiLexicon.bajaui();
    final String lexViews     = lex.getText("menu.views.label");
    //final String lexSpyLocal  = lex.getText("menu.views.spyLocal.label");
    //final String lexSpyRemote = lex.getText("menu.views.spyRemote.label");
  
    BPermissions permissions = BPermissions.all;
    if (target instanceof BIProtected)
      permissions = ((BIProtected)target).getPermissions(null);
      
    boolean isComp = target instanceof BComponent;

    AgentList agentList = WbSys.getFilteredViewList(owner,
      target,
      disable ? AgentFilter.has(permissions).toPredicate() : agent -> true);

    AgentInfo[] agents = agentList.list();

    BMenu menu = new BMenu(lexViews);
    try
    {               
      // check if PxEditor is registered agent
      boolean pxEditable = false;    
      ArrayList<AgentInfo> pxViewEditors = null;
      
      // view list 
      for(int i=0; i<agents.length; ++i)
      {                                                 
        AgentInfo agent = agents[i];

        // save all editors on specific PxViews for last
        if (agent instanceof PxUtil.PxEditor)
        {                    
          if (pxViewEditors == null) pxViewEditors = new ArrayList<>();
          pxViewEditors.add(agent);            
          continue;
        }                                        

        // if this is PxEditor that means that this
        // components adding and editing px views
        if (isComp && pxEditor != null && agent.getAgentType().is(pxEditor))
        {
          pxEditable = true;
          continue;
        }
        
        menu.add("v"+i, makeHyperlinkCommand(owner, baseOrd, agent));
      }           

      
      // if px editable when add NewView command and 
      // all the specific px view editors
      if (pxEditable)
      {             
        BComponent comp = (BComponent)target;
        menu.add("sep0", new BSeparator());
        menu.add("newView", new PxEditorApi.NewViewCommand(owner, comp));
        for(int i=0; pxViewEditors != null && i<pxViewEditors.size(); ++i)
          menu.add("edit"+i, makeHyperlinkCommand(owner, baseOrd, pxViewEditors.get(i)));
      }       
      
      // if this is a BogFile, then add another section 
      // for the views on the root component
      if (target instanceof BBogFile)
      {                                
        BOrd bogOrd = BOrd.make(baseOrd, "bog:|slot:/");                         
        BFolder folder = new BFolder();
        AgentInfo[] folderAgents = WbSys.getFilteredViewList(owner, folder, agent -> true).list();
        menu.add("sep1", new BSeparator());
        for(int i=0; i<folderAgents.length; ++i)
        {                            
          AgentInfo a = folderAgents[i];
          if (pxEditor != null && a.getAgentType().is(pxEditor)) continue;
          menu.add("bog"+i, makeHyperlinkCommand(owner, bogOrd, a));
        }
      }         
      
      // check if owner is inside a BWbLocatorWb
      boolean inLocator = false;
      for(BWidget w = owner; w != null; w = w.getParentWidget())
        if (w instanceof BWbLocatorBar) { inLocator = true; break; }
      
      // help pages    
      if (!inLocator)
      {
        menu.add("sep2", new BSeparator());                    
        
        Command guideHelp = new WbCommands.NavHelpCommand(owner, "menu.views.guide", target);
        Command refHelp = new WbCommands.NavBajadocCommand(owner, "menu.views.bajadoc", target);
        menu.add("helpGuide", new BActionMenuItem(guideHelp), null);
        menu.add("helpRef", new BActionMenuItem(refHelp), null);
        
        // spy pages      
        if (permissions.hasAdminRead() || !disable)
        {                         
          menu.add("spyLocal", new SpyLocalCommand(owner, target));
          if (target instanceof BISpaceNode)
          { 
            BISession session = ((BISpaceNode)target).getSession();
            if (session != null && session != BLocalHost.INSTANCE)
              menu.add("spyRemote", new SpyRemoteCommand(owner, target));
          }
        }    
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return menu;
  }
  
  static Command makeHyperlinkCommand(BWidget owner, BOrd baseOrd, AgentInfo agent)
  {
    String text = agent.getDisplayName(null);
    BImage icon = BImage.make(agent.getIcon(null));
    if (icon == null) icon = viewIcon;
    String agentId = agent.getAgentId();
    BOrd ord = BOrd.make(baseOrd, "view:" + agentId);
    return new HyperlinkCommand(owner, icon, text, ord);
  }

////////////////////////////////////////////////////////////////
// Actions Menu
////////////////////////////////////////////////////////////////
  
  /**
   * Build a action menu for the specified component.
   */
  public static BMenu makeActionsMenu(BWidget owner, BComponent target)
  {
    final UiLexicon lex = UiLexicon.bajaui();
    final String lexActions   = lex.getText("menu.actions.label");
    
    BPermissions permissions = BPermissions.all;
    if (target instanceof BIProtected)
      permissions = target.getPermissions(null);
      
    target.loadSlots();  
    Slot[] slots = target.getSlotsArray();

    BMenu menu = new BMenu(lexActions);
    boolean disable = true;
    for(int i=0; i<slots.length; ++i)
    {                   
      Slot slot = slots[i];
      if (!slot.isAction()) continue;
      Action action = (Action)slot;
      
      // skip hidden and inaccessible actions 
      if (NavMenuUtil.disable)
      {
        int flags = target.getFlags(action);
        if ((flags & Flags.HIDDEN) != 0) continue;
        if ((flags & Flags.OPERATOR) != 0)
        {
          if (!permissions.has(BPermissions.OPERATOR_INVOKE)) continue;
        }
        else
        {
          if (!permissions.has(BPermissions.ADMIN_INVOKE)) continue;
        }
      }
      
      Command cmd = new InvokeActionCommand(owner, target, action);
      menu.add("a"+i, new BActionMenuItem(cmd), null);
      disable = false;
    }
    if (disable) menu.setEnabled(false);
    return menu;
  }                  

  
////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  static class SpyLocalCommand extends Command
  {     
    SpyLocalCommand(BWidget owner, BObject target) 
    { 
      super(owner, UiLexicon.bajaui(), "commands.spyLocal"); 
      this.target = target;              
    }

    public CommandArtifact doInvoke(CommandEvent evt)
    {        
      BWbShell shell = (BWbShell)getShell();
      BOrd ord = ObjectUtil.toSpyLocal(target);      
      if (ord != null)
      {
        BHyperlinkMode mode = BHyperlinkMode.replace;
        if (evt != null)
        {
          mode = evt.isControlDown() ? BHyperlinkMode.newTab : BHyperlinkMode.replace;
        }
        
        shell.hyperlink(new HyperlinkInfo(ord, mode));
      }      
      return null;
    } 
     
    BObject target;
  }

  static class SpyRemoteCommand extends Command
  {     
    SpyRemoteCommand(BWidget owner, BObject target) 
    { 
      super(owner, UiLexicon.bajaui(), "commands.spyRemote"); 
      this.target = target;
    }              
    
    public CommandArtifact doInvoke(CommandEvent evt)
    {        
      BWbShell shell = (BWbShell)getShell();
      BOrd ord = ObjectUtil.toSpyRemote(target);
      if (ord != null)
      {
        BHyperlinkMode mode = BHyperlinkMode.replace;
        if (evt != null)
        {
          mode = evt.isControlDown() ? BHyperlinkMode.newTab : BHyperlinkMode.replace;
        }
        
        shell.hyperlink(new HyperlinkInfo(ord, mode));
      }      
      return null;
    } 
    
    BObject target;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  static TypeInfo pxEditor = null;
  static
  {
    try 
    {  
      pxEditor = Sys.getRegistry().getType("pxEditor:PxEditor"); 
    } 
    catch(Exception e) 
    {
    }
  }      
  
  
  // this flag allows us to force enablement (disable disable 
  // so to speak) - it allows us to test fox security checks
  static boolean disable =
    AccessController.doPrivileged((PrivilegedAction<String>)
      () -> System.getProperty("workbench.disableIfNoPermission", "true"))
      .equals("true");
  
  static BImage viewIcon = BImage.make("module://icons/x16/views/view.png"); 
    
}

