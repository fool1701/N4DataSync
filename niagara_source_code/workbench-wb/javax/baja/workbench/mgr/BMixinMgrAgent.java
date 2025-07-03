/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.List;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Provides abstract managers with columns that should be displayed for
 * a BIMixIn type that's "mixed in" with its rows.  The BMixinMgrAgent
 * type must be registered as an agent on the mixin type.
 * 
 * For example, an abstract manager class manages objects of type "Foo", and
 * for "Foo" the "Bar" mixin is enabled.   A BMixinMgrAgent subtype "BarColumns"
 * is registered against the "Bar" type, and the "BarColumns" implementation
 * returns the editable/listable columns that are to be displayed and edited with 
 * the manager.
 * 
 * @author    Matt Boon
 * @creation  22 May 06
 * @version   $Revision: 6$ $Date: 8/16/07 3:41:02 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BMixinMgrAgent
  extends BObject
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BMixinMgrAgent(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMixinMgrAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Returns an array of columns that should be shown for the mixin
   * on which this object's type is registered as an agent.  If no columns
   * should be added, returns an array with zero length.
   */
  public abstract MgrColumn[] getColumns();

  /**
   * Callback that controls whether this agent requires an exact match
   * to the target type.  If true is returned, the agent will only match
   * the exact type of the target.  If false, the agent will match
   * the target type and all subtypes.  The default implementation returns
   * false.
   */
  public boolean requireExactTypeMatch()
  {
    return false;
  }

  /**
   * For the given mixin instance, add additional descendant components
   * to the given subscription list for the manager view in order for it to
   * function properly. The default behavior is to add nothing extra to the
   * subscription list.
   *
   * @since Niagara 4.8
   */
  public void addDescendentsToManagerSubscription(BIMixIn mixin,
                                                  List<BComponent> subscriptionList)
  {

  }



}
