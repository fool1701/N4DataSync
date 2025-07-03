/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.view;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.toolbar.BIToolBar;
import javax.baja.ui.transfer.BTransferWidget;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.BWbShell;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.Binder;
import com.tridium.workbench.util.WbUtil;

/**
 * BWbView is a plugin used to view and edit BObjects as the
 * main "working pane" of the workbench.
 *
 * @author    Brian Frank
 * @creation  16 May 02
 * @version   $Revision: 15$ $Date: 11/21/06 12:24:20 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbView
  extends BWbEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.view.BWbView(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get an array of BMenus that this plugin exposes.
   * These menus will be merged with the application
   * menus when this plugin is the active plugin in
   * its plugin context.
   *
   * @return array of menus to merge, or null if no
   *    menus are required.  The default is to
   *    return null.
   */
  public BIMenu[] getViewMenus()
  {
    return null;
  }
  
  /**
   * Get the toolbar this plugin exposes.  This toolbar
   * will be merged with the application toolbar when this
   * plugin is the active plugin in its plugin context.
   *
   * @return BToolBar to merge, or null if no
   *    toolbar is required.  The default is to
   *    return null.
   */
  public BIToolBar getViewToolBar()
  {
    return null;
  }
  
  
  /**
   * Get a BWidget to display to the right of the status bar.
   * If there is no status bar in the shell then this method
   * is ignored.  The widget returned is displayed to the right
   * of the normal status bar and should have a preferred width
   * no wider than necessary.  The preferred height of the widget
   * is ignored and forced to the height of the normal status 
   * bar.
   *
   * @return BWidget to append to the normal status bar,
   *   or null if no supplement is required.
   */
  public BWidget getViewStatusBarSupplement()
  {
    return null;
  }
  
  /**
   * Prime is called when the BPlugin should prepare 
   * itself for focus.
   */
  public void prime()
  {
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Activated is called when the view is first 
   * loaded as the BWbShell's active view.
   */
  public void activated()
  {
  }
  
  /**
   * Deactivated is called when the view is 
   * being unloaded from the BWbShell.
   */
  public void deactivated()
  {                            
  }
  
  /**
   * Set the modified flag to false, then disable the
   * shell's save button.
   */
  public void clearModified()
  {                        
    super.clearModified();
    
    BWidgetShell shell = getShell();
    if (shell instanceof BWbShell)
    {
      BWbShell wbShell = (BWbShell)shell;
      if (wbShell.getActiveView() == this)
        ((com.tridium.workbench.shell.BNiagaraWbShell)wbShell).clearModified(this);
    }
  }

////////////////////////////////////////////////////////////////
// Command Constants
////////////////////////////////////////////////////////////////

  public static final int CUT           = 0;
  public static final int COPY          = 1;
  public static final int PASTE         = 2;
  public static final int DUPLICATE     = 3;
  public static final int DELETE        = 4;
  public static final int RENAME        = 5;
  public static final int FIND          = 6;
  public static final int FIND_PREV     = 7;
  public static final int FIND_NEXT     = 8;
  public static final int REPLACE       = 9;
  public static final int GOTO          = 10;
  public static final int PASTE_SPECIAL = 11;
  
  static final int COUNT = 12;
  static final String[] STRINGS =
  {
    "cut",          // 0
    "copy",         // 1
    "paste",        // 2
    "duplicate",    // 3
    "delete",       // 4
    "rename",       // 5
    "find",         // 6
    "findPrev",     // 7
    "findNext",     // 8
    "replace",      // 9
    "goto",         // 10
    "pasteSpecial", // 11
  };
  
////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  /**
   * Get a non-localized string for the specified command id.
   */
  public static String commandIdToString(int id)
  {
    try
    {
      return STRINGS[id];
    }
    catch(Exception e)
    {
      return "invalid[" + id + "]";
    }
  }

  /**
   * Is the specified plugin command enabled.
   */
  public final boolean isCommandEnabled(int id)
  {
    return this.enabled[id];
  }

  /**
   * Set the specified plugin command enabled state.
   */
  public final void setCommandEnabled(int id, boolean enabled)
  {
    if (this.enabled[id] == enabled) return;
    this.enabled[id] = enabled;
    statesChanged();
  }
  
  /**
   * Invoke the specified command.
   */
  public CommandArtifact invokeCommand(int id)
    throws Exception
  {
    BTransferWidget w = transferWidget;
    if (w != null)
    {
      switch(id)
      {
        case CUT:           return w.doCut();
        case COPY:          return w.doCopy();
        case PASTE:         return w.doPaste();
        case PASTE_SPECIAL: return w.doPasteSpecial();
        case DUPLICATE:     return w.doDuplicate();
        case DELETE:        return w.doDelete();
        case RENAME:        return w.doRename();
      }
    }
    
    throw new UnsupportedOperationException(commandIdToString(id));
  }

////////////////////////////////////////////////////////////////
// BTransferWidget
////////////////////////////////////////////////////////////////  
 
  /**
   * Get the transfer widget which is used to map the
   * the cut, copy, paste, duplicate, delete calls.
   */
  public final BTransferWidget getTransferWidget()
  {
    return transferWidget;
  }

  /**
   * Set the transfer widget which is used to map the
   * the cut, copy, paste, duplicate, delete calls.
   */
  public final void setTransferWidget(BTransferWidget transferWidget)
  {
    this.transferWidget = transferWidget;
    updateTransferWidgetStates();
  }
  
  /**
   * If a transfer widget is installed for this deluxe 
   * plugin, then update the plugin command states based
   * on the transfer widget's enable states.
   */
  public void updateTransferWidgetStates()
  {
    BTransferWidget w = transferWidget;
    
    if (w == null) 
    {
      enabled[CUT]           = false;
      enabled[COPY]          = false;
      enabled[PASTE]         = false;
      enabled[PASTE_SPECIAL] = false;
      enabled[DUPLICATE]     = false;
      enabled[DELETE]        = false;
      enabled[RENAME]        = false;
    }
    else
    {
      enabled[CUT]           = w.isCutEnabled();
      enabled[COPY]          = w.isCopyEnabled();
      enabled[PASTE]         = w.isPasteEnabled();
      enabled[PASTE_SPECIAL] = w.isPasteSpecialEnabled();
      enabled[DUPLICATE]     = w.isDuplicateEnabled();
      enabled[DELETE]        = w.isDeleteEnabled();
      enabled[RENAME]        = w.isRenameEnabled();
    }
    
    statesChanged();
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * When states changed, inform parent shell so 
   * that it can update its commands.
   */
  private void statesChanged()
  {
    BWidgetShell shell = getShell();
    if (shell instanceof BWbShell)
    {
      BWbShell wbShell = (BWbShell)shell;
      if (wbShell.getActiveView() == this)
        ((com.tridium.workbench.shell.BNiagaraWbShell)wbShell).updateCommandStates(this);
    }
  }         
  
////////////////////////////////////////////////////////////////
// Binder
////////////////////////////////////////////////////////////////
  
  /**
   * WbView implementation of Binder
   */
  class WbViewBinder extends Binder
  {                 
    WbViewBinder(BWbView view)
    {
      super(view);
    }
    
    public String toString()
    {
      return toDebugString();
    }
    
    public void event(BComponentEvent event)
    {        
      super.event(event);
      route(event);       
      routeToSubViews(event);
    }
  }           
  
  /**
   * Get the binder for this view.
   */
  Binder getBinder()
  {              
    // most of the time I am the top level view
    // and have the binder cached on me
    if (binder != null) return binder; 
    
    return WbUtil.getBinder(getParent());
  }
  
  
  /**
   * Hook for WbComponentView to handle component eventing.
   */
  void route(BComponentEvent event)
  {
  }                

  /**
   * Route to any sub-WbComponentViews we may have under us.
   */
  void routeToSubViews(BComponentEvent event)
  {                                               
    if (binder == null) return;
    BBinding[] bindings = binder.getAllBindings();
    for(int i=0; i<bindings.length; ++i)
      if (bindings[i] instanceof BWbViewBinding)
        ((BWbView)bindings[i].getWidget()).route(event);
  }                
  
////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("workbench:WbViewBinding");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Framework Access
////////////////////////////////////////////////////////////////

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {   
      case Fw.MAKE_BINDER:                  
        return binder = new WbViewBinder(this);
      case Fw.GET_BINDER: 
        return binder;              
      case Fw.SHOW_ACCELERATORS: 
        return this;
      case Fw.ACTIVATED:    
        return null;
      case Fw.DEACTIVATED: 
      {
        if( null != binder)
          binder.stop();
        binder = null;
        return null;
      }
    }
    return super.fw(x, a, b, c, d);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
   
  private boolean[] enabled = new boolean[COUNT];   
  private BTransferWidget transferWidget;
  protected Binder binder;
}
