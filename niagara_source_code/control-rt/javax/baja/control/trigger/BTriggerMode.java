/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.control.trigger;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A BTriggerMode determines when a BTimeTrigger will fire its trigger.
 *
 * @author    John Sublett
 * @creation  07 Jan 2004
 * @version   $Revision: 4$ $Date: 7/24/08 9:45:10 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BTriggerMode
  extends BSimple
{
  public static final Type TYPE = Sys.loadType(BTriggerMode.class);
  public Type getType() { return TYPE; }

  /**
   * Get the display name for this trigger mode.
   */
  public String getDisplayName(Context cx)
  {
    return getType().getDisplayName(cx);
  }

  /**
   * Make a scheduler for this trigger mode.  This method must
   * create a new instance each time it is called.
   */
  public abstract TriggerScheduler makeScheduler(BTimeTrigger trigger);

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  /**
   * Override so that BTriggerModeFE is always the default.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("control:TriggerModeFE");
    return agents;
  }
}
