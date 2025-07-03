/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.point;

import javax.baja.agent.AgentList;
import javax.baja.driver.point.BPointFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.util.AgentInfoUtil;

@NiagaraType
public class BNPointFolder
  extends BPointFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.point.BNPointFolder(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Gets the agent list.  Adds BNPointManager as "Your Driver Point Manager" if
   * one not already registered.
   *
   * @see AgentInfoUtil#getAgentsHelp
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    return AgentInfoUtil.processPointManagerAgents(super.getAgents(cx), useAutoManager(), getType().getTypeInfo().getModuleName());
  }

  /**
   * Override point to disable use of auto point manager view. Return false to
   * remove point manager view.
   */
  protected boolean useAutoManager()
  {
    return true;
  }

  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNPointDeviceExt ||
      parent instanceof BNPointFolder;
  }
}
