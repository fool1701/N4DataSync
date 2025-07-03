/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComplex;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

import com.tridium.util.PxUtil;
/**
 * BDeviceFolder is the standard container to use 
 * under DeviceNetwork to organize Devices.
 *
 * @author    Brian Frank       
 * @creation  12 Sept 04
 * @version   $Revision: 4$ $Date: 5/17/10 1:42:55 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BDeviceFolder
  extends BFolder
  implements BIDeviceFolder
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.BDeviceFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IDeviceFolder
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public final BDeviceNetwork getNetwork()
  {            
    BComplex p = getParent();
    while(p != null)
    {
      if (p instanceof BDeviceNetwork)
        return (BDeviceNetwork)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }
  
  /**
   * Get the type of Device for this driver.
   */
  public final Type getDeviceType()
  {            
    return getNetwork().getDeviceType();
  }

  /**
   * Get the type of DeviceFolder for this driver.
   */
  public final Type getDeviceFolderType()
  {            
    return getNetwork().getDeviceFolderType();
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return "";
  }

  public AgentList getAgents(Context cx)
  { 
    TypeInfo deviceManager = Sys.getRegistry().getType("driver:DeviceManager");              
    AgentList agents = super.getAgents(cx);
    
    for(int i=0; i<agents.size(); ++i)
      if (agents.get(i).getAgentType().is(deviceManager))
        return agents;
        
    agents.add(deviceManager.getAgentInfo());
    return PxUtil.movePxViewsToTop(agents);
  }

  public BIcon getIcon() 
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }  
  private static final BIcon icon = BIcon.std("deviceFolder.png");

}
