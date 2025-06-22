/*
 * @copyright 2012 Tridium Inc.
 */
package com.tridium.ndriver.ui.point;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrController;

import com.tridium.ndriver.ui.NMgrControllerUtil.NPointMgrAgentCommand;

/**
 * The developer may extend this class and declare their type
 * as an agent on their point device extension. This will add
 * a button, menu item, and/or toolbar item to the
 * corresponding point manager.
 *
 * When the ui item is clicked, the doInvoke method on an instance
 * of this class will be called on the client-side.
 */
@NiagaraType
public abstract class BNPointMgrAgent
  extends BStruct
  implements BINPointMgrAgent
{
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.ui.point.BNPointMgrAgent(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:37 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPointMgrAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BINPointMgrAgent - Default Implementations
////////////////////////////////////////////////////////////////

  /**
   * By default, the getFlags method returns MgrController.BARS
   * to make this BNPointMgrAgent appear in the menu bar, toolbar,
   * and button panel (action bar) of the point manager or
   * device manager.
   */
  @Override
  public int getFlags()
  {
    return MgrController.BARS;
  }

  /**
   * The developer should review the given BNPointManager and consider
   * updating (eg. enable/disable) the given agentCommand and/or any other
   * commands on the manager's controller. The method is called anytime
   * there is a change of state on the point manager (eg. discovery list
   * selection change, database list selection change, database component
   * event, learn mode changed, etc.)
   *
   * For example (to enable the agent's UI widget(s) if one database item is selected):
   *  agentCommand.setEnabled(pointManager.getController().getSelectedRows().length == 1);
   *
   * For example (to enable the agent's UI widget(s) if one or more database items are selected):
   *  agentCommand.setEnabled(pointManager.getController().getSelectedRows().length > 0);
   *
   * For example (to enable the agent's UI widget(s) if zero database items are selected):
   *  agentCommand.setEnabled(pointManager.getController().getSelectedRows().length == 0);
   *
   * @param pointManager
   *
   * @param agentCommand this is a special instance of IMgrCommand. It is
   * a reference to the corresponding GUI command (button, menu item, and/or
   * toolbar button) on the device manager.
   */
  @Override
  public void update(BNPointManager pointManager, NPointMgrAgentCommand agentCommand)
  {

  }

}
