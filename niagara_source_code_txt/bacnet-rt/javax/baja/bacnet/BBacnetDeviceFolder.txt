/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import javax.baja.agent.AgentList;
import javax.baja.driver.BDeviceFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBacnetDeviceFolder.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 13 Sep 2004
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
public class BBacnetDeviceFolder
  extends BDeviceFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.BBacnetDeviceFolder(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:31 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

//  public void added(Property p, Context cx)
//  {
//    super.added(p, cx);
//
//    if (!isRunning() || (cx == Context.decoding)) return;
//    if (get(p) instanceof BBacnetDevice)
//      ((BBacnetDevice)get(p)).upload(new BUploadParameters(false));
//  }

  /**
   * BBacnetDeviceFolder may only be placed under a BBacnetNetwork or BBacnetDeviceFolder.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetNetwork || parent instanceof BBacnetDeviceFolder;
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @return the BBacnetDevice containing this BBacnetPointDeviceExt.
   */
  public final BBacnetNetwork network()
  {
    return (BBacnetNetwork)getNetwork();
  }


////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the agent list.  Remove Device Manager and Network Summary.
   */
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.remove("driver:DeviceManager");
    agents.toBottom("bacnetEDE:EdeBacnetDeviceManager");
    return agents;
  }

  public String toString(Context cx)
  {
    return "BacnetDeviceFolder:" + getName();
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

}
