/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.agent.AgentList;
import javax.baja.naming.BOrd;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BHierarchySpace is the space that manages access to hierarchies defined by the HierarchyService.
 * <p>
 * Please note, any changes to this class should also be reflected in the BajaScript
 * version (hierarchy-ux/rc/bs/HierarchySpace.js).
 * </p>
 *
 * @author    Andrew Saunders
 * @creation  12 Aug 2013
 * @since     Baja 4.0
 */
@NiagaraType
public class BHierarchySpace
  extends BSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchySpace(2979906276)1.0$ @*/
/* Generated Tue Jan 18 11:31:27 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchySpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BHierarchySpace()
  {
    super("hierarchy", LexiconText.make("hierarchy", "space.hierarchy"));
  }

  public BHierarchySpace(BHierarchyService hService)
  {
    super("hierarchy", LexiconText.make("hierarchy", "space.hierarchy"));
    this.hService = hService;
  }

  public BHierarchySpace(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

////////////////////////////////////////////////////////////////
// Nav
////////////////////////////////////////////////////////////////

  /**
   * Children are lazily loaded so always return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Get the child with the specified name.
   */
  @Override
  public BINavNode getNavChild(String name)
  {
    BINavNode[] children = getNavChildren();
    for (BINavNode child : children)
    {
      if (child.getNavName().equals(name))
        return child;
    }
    return null;
  }

  /**
   * Resolve the nav child with the specified name.
   */
  @Override
  public BINavNode resolveNavChild(String name)
  {
    BINavNode child = getNavChild(name);
    if (child == null)
      throw new UnresolvedException(name);
    else
      return child;
  }

  /**
   * Get all of the nav children.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return BHierarchyService.getHierarchyElems(null, this, null);
  }

  /**
   * The ord in the session is always "hierarchy:".
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
  * Get the list of agents for this BObject.  The default implementation of this method returns
  * {@code Registry.getAgents()}
  */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.add("workbench:NavContainerView");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Display
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://icons/x16/r2/tree.png");

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Dump spy info.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BOrd ordInSession = BOrd.make("hierarchy:");

  BHierarchyService hService;
}
