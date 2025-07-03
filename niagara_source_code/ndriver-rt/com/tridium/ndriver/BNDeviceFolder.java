/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver;

import javax.baja.agent.AgentList;
import javax.baja.driver.BDeviceFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.util.AgentInfoUtil;

@NiagaraType
public class BNDeviceFolder
  extends BDeviceFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.BNDeviceFolder(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Gets the agent list.  Adds BNDeviceManager as "Your Driver Device Manager"
   * if one not already registered.
   *
   * @see AgentInfoUtil.getAgentsHelp
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    return AgentInfoUtil.processDeviceManagerAgents(super.getAgents(cx), useAutoManager(), getType().getTypeInfo().getModuleName());
  }

  /**
   * Override point to disable use of auto device manager view. Return false to
   * remove device manager view.
   */
  protected boolean useAutoManager()
  {
    return true;
  }
}
