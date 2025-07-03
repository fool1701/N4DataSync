/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.tool;

import java.util.HashMap;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.util.BTypeSpec;
import javax.baja.workbench.BWbShell;

import com.tridium.workbench.nav.BToolSpace;

/**
 * BWbTool is a plugin which provides a stand alone utility which 
 * is automatically made available in the workbench environment.  
 * The BWbNavNodeTool itself is singleton which may be used to 
 * register one or more WbViews to work with the tool.  When the
 * user selects the tool from the menu, the default WbView is
 * displayed.  Every WbNavNodeTool is given its own BComponentSpace
 * which is named "tool:{typespec}", and the WbNavNodeTool itself
 * has an ord of "tool:{typespec}|slot:/".
 *
 * @author    Brian Frank       
 * @creation  14 Oct 03
 * @version   $Revision: 5$ $Date: 3/28/05 1:41:01 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbNavNodeTool
  extends BWbTool
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.tool.BWbNavNodeTool(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbNavNodeTool.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make the singleton for the specified type spec.
   */
  public static BWbNavNodeTool make(String typeSpec)
  {                 
    BWbNavNodeTool tool = singletons.get(typeSpec);
    if (tool == null)
    {           
      tool = (BWbNavNodeTool)BTypeSpec.make(typeSpec).getInstance();
      singletons.put(typeSpec, tool);
      BToolSpace.mount(tool);
    }                
    return tool;
  }
  static HashMap<String, BWbNavNodeTool> singletons = new HashMap<>();
    
////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////
  
  /**
   * By default hide all the agents registered on
   * the super classes.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    AgentList supers = Sys.getRegistry().getAgents(TYPE.getTypeInfo());
    agents.remove(supers); 
    if (agents.size() == 0)
      agents.add("workbench:PropertySheet");
    return agents;
  }
  
  /**
   * Invoke the tool by hyperlinking to the default view
   * on this nav node type.
   */
  public CommandArtifact invoke(BWbShell shell)
    throws Exception
  {                            
    shell.hyperlink(getNavOrd());
    return null;
  }
   
}
