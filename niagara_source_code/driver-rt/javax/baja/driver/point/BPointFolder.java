/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.agent.AgentList;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
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
 * BPointFolder is the standard container to use 
 * under PointDeviceExt to organize proxy points.
 *
 * @author    Brian Frank       
 * @creation  29 Jun 04
 * @version   $Revision: 3$ $Date: 5/17/10 1:42:55 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BPointFolder
  extends BFolder
  implements BIPointFolder
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BPointFolder(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IPointFolder
////////////////////////////////////////////////////////////////

  /**
   * Get the parent network.
   */
  public final BDeviceNetwork getNetwork()
  {            
    return getDevice().getNetwork();
  }

  /**
   * Get the parent device.
   */
  public final BDevice getDevice()
  {
    return getDeviceExt().getDevice();
  }

  /**
   * Get the parent point device extension.
   */
  public final BPointDeviceExt getDeviceExt()
  {            
    BComplex p = getParent();
    while(p != null)
    {
      if (p instanceof BPointDeviceExt)
        return (BPointDeviceExt)p;
      p = p.getParent();
    }
    throw new IllegalStateException();
  }
  
  /**
   * Get the type of ProxyExt for this driver.
   */
  public final Type getProxyExtType()
  {            
    return getDeviceExt().getProxyExtType();
  }

  /**
   * Get the type of PointFolder for this driver.
   */
  public final Type getPointFolderType()
  {            
    return getDeviceExt().getPointFolderType();
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
    TypeInfo pointManager = Sys.getRegistry().getType("driver:PointManager");              
    AgentList agents = super.getAgents(cx);
    
    for(int i=0; i<agents.size(); ++i)
      if (agents.get(i).getAgentType().is(pointManager))
        return agents;
        
    agents.add(pointManager.getAgentInfo());
    return PxUtil.movePxViewsToTop(agents);
  }

  public BIcon getIcon() 
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }  
  private static final BIcon icon = BIcon.std("pointFolder.png");

}
