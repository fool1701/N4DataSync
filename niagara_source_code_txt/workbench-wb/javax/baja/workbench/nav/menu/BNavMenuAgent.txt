/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.nav.menu;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentList;
import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BMenu;
import javax.baja.ui.BWidget;
import javax.baja.util.Lexicon;

import com.tridium.util.ThrowableUtil;

/**
 * BNavMenuAgent is used to build customized context sensitive 
 * menus for a given BINavNode.  To make a menu:
 * <pre>
 *  BMenu menu = BNavMenuAgent.makeFor(navNode).makeMenu(owner, navNode);
 * </pre>
 * See NavMenuUtil for common utilities.
 *
 * @author    Brian Frank       
 * @creation  3 Feb 03
 * @version   $Revision: 7$ $Date: 5/4/05 8:24:38 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraSingleton
public class BNavMenuAgent
  extends BSingleton
  implements BIAgent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.nav.menu.BNavMenuAgent(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BNavMenuAgent INSTANCE = new BNavMenuAgent();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavMenuAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BNavMenuAgent() {}

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make a BNavMenuAgent for the specified target.
   */
  public static BNavMenuAgent makeFor(BObject target)
  {
    try
    {
      AgentList list = target.getAgents().filter(agentFilter);
      if (list.size() == 0) return INSTANCE;
      return (BNavMenuAgent)list.getDefault().getInstance();
    }
    catch(Exception e)
    {
      System.out.println("BNavMenuAgent.makeFor: " + target.getType());
      e.printStackTrace();
    }
    return INSTANCE;
  }  

////////////////////////////////////////////////////////////////
// Make Menu 
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>makeMenu(owner, target, true, null)</code>.
   */
  public final BMenu makeMenu(BWidget owner, BObject target)
  {
    return makeMenu(owner, target, true, null);
  }

  /**
   * Make the the menu.  If showError is true and there is an 
   * exception then display an error dialog and then rethrow
   * the exception.
   */
  public final BMenu makeMenu(BWidget owner, BObject target, boolean showError, Context cx)
  { 
    try
    { 
      return doMakeMenu(owner, target, cx);
    }
    catch(Throwable e)
    {     
      if (showError) 
        BDialog.error(owner, BDialog.TITLE_ERROR, Lexicon.make("workbench").getText("NavMenuAgent.makeMenu.error"), e);
      
      throw ThrowableUtil.toRuntime(e);
    }
  }

  /**
   * Override for subclasses.  Return null for no menu.  The 
   * default implementation returns null.
   */
  protected BMenu doMakeMenu(BWidget owner, BObject target, Context cx)
  {
    return null;
  }
     
  static AgentFilter agentFilter = AgentFilter.is(TYPE);
   
}
